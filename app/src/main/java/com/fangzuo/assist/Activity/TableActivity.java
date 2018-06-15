package com.fangzuo.assist.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.FirstRvAdapter;
import com.fangzuo.assist.Adapter.Padapter;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.Dao.T_main;
import com.fangzuo.assist.Fragment.BlankFragment;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.EventBusInfoCode;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.VibratorUtil;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.PDSubDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TableActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.btn_print)
    Button btnPrint;
    @BindView(R.id.fragmenthost)
    FrameLayout fragmenthost;
    private DaoSession daoSession;
    private List<T_Detail> list;
    private List<T_main> list1;
    private T_DetailDao t_detailDao;
    private T_mainDao t_mainDao;
    private List<Boolean> isCheck;
    private int activity;
    private PDSubDao pdSubDao;
    private List<T_main> t_mains;
    private ArrayList<FirstCheck> fContainer;

    private LinearLayoutManager mLinearLayoutManager;
    private FirstRvAdapter firstRvAdapter;
    private ArrayList<FirstCheck> container;
    private FirstCheck items;
    private String order;
    private BlankFragment blankFragment;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_table);
        mContext = this;
        ButterKnife.bind(this);
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        t_mainDao = daoSession.getT_mainDao();
        t_detailDao = daoSession.getT_DetailDao();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.FIRSTFRAGMENT:
                Toast.showText(mContext, "FIRSTFRAGMENT");
                break;
        }
    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        activity = extras.getInt("activity");
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        container = new ArrayList<>();
        firstRvAdapter = new FirstRvAdapter(mContext, container);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        rv.setAdapter(firstRvAdapter);
        initList();
    }

    public void initList() {
        container.clear();
        t_mains = t_mainDao.queryBuilder().where(T_mainDao.Properties.Activity.eq(activity)).build().list();
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT MODEL,FORDER_ID,FPRODUCT_NAME,FPRODUCT_ID,SUM(FQUANTITY) AS " +
                "FQTY,SUM(RADICAL) AS RADICAL FROM T__DETAIL WHERE ACTIVITY=? GROUP BY FORDER_ID," +
                "FPRODUCT_NAME,FPRODUCT_ID ORDER BY FORDER_ID DESC", new String[]{activity + ""});
        fContainer = new ArrayList<>();
        while (cursor.moveToNext()) {
            FirstCheck f = new FirstCheck();
            f.orderID = cursor.getString(cursor.getColumnIndex("FORDER_ID"));
            f.productname = cursor.getString(cursor.getColumnIndex("FPRODUCT_NAME"));
            f.TRadical = cursor.getString(cursor.getColumnIndex("RADICAL"));
            f.TVoleum = cursor.getString(cursor.getColumnIndex("FQTY"));
            f.itemID = cursor.getString(cursor.getColumnIndex("FPRODUCT_ID"));
            f.model = cursor.getString(cursor.getColumnIndex("MODEL"));
            fContainer.add(f);
        }
        container.addAll(fContainer);
        firstRvAdapter.notifyDataSetChanged();

        Log.e("TableActivity", "fContainer.size():" + fContainer.size());

    }

    @Override
    protected void initListener() {
        btnPrint.setOnClickListener(new View.OnClickListener() {
            LinearLayoutManager mLinearLayoutManager2;

            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(mContext).inflate(R.layout.item_choosetoprint,null);
                mLinearLayoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

                ListView r = v.findViewById(R.id.rv_choose);
                final Padapter padapter = new Padapter(container,mContext);
                r.setAdapter(padapter);
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("选择打印单据").setView(v).create().show();
                r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        items = padapter.getFirst(i);
                        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                        VibratorUtil.Vibrate(mContext,1000);
                        ab.setTitle("打印");
                        ab.setMessage("是否生成打印订单号:" + items.orderID + "的小票").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Bundle b = new Bundle();
                                b.putInt("tag", activity);
                                b.putSerializable("item", items);
                                startNewActivity(PrintActivity.class, R.anim.activity_fade_in, R.anim.activity_fade_out, false, b);
                            }
                        }).setNegativeButton("取消", null).create().show();
                    }
                });
        }
        });
        firstRvAdapter.setOnItemClickListener(new FirstRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                items = firstRvAdapter.getItems(position);
                blankFragment = new BlankFragment();
                transaction.setCustomAnimations(R.anim.activity_slide_right_in, R.anim.activity_slide_left_out).add(R.id.fragmenthost, blankFragment).commit();
                VibratorUtil.Vibrate(mContext,500);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                items = firstRvAdapter.getItems(position);
                int tr = 0;
                StringBuilder s = new StringBuilder();
                Cursor cursor = daoSession.getDatabase().rawQuery("SELECT FIDENTITY" +
                        ",SUM(RADICAL) AS RADICAL FROM T__DETAIL WHERE ACTIVITY=? AND FORDER_ID = ? GROUP BY " +
                        "FIDENTITY ORDER BY cast(DIAMETER as int)", new String[]{activity + "", items.orderID});
                while (cursor.moveToNext()){
                    String bao = cursor.getString(cursor.getColumnIndex("FIDENTITY"));
                    String r = cursor.getString(cursor.getColumnIndex("RADICAL"));
                    tr += Integer.parseInt(r);
                            s.append("报数:").append(bao).append("   ").append("总根数:").append(r).append("\n");
                }
                s.append("根数汇总:").append(tr);
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("汇总");
                ab.setMessage(s);
                ab.setPositiveButton("确定",null).create().show();
            }
        });
    }

    @Override
    protected void OnReceive(String code) {

    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        onBackPressed();
    }


    public ArrayList<FirstCheck> gettDetails() {
        return fContainer;
    }

    public FirstCheck getfirst() {
        return items;
    }

    public int getTag() {
        return activity;
    }

    public void setOrderID(String orderid) {
        this.order = orderid;
    }

    public String getOrderID() {
        return order;
    }

    public void setf(BlankFragment blankFragment) {
        blankFragment = blankFragment;
    }

    @Override
    public void onBackPressed() {
        if (blankFragment != null && blankFragment.isAdded()) {
            blankFragment.pressBack();
        } else {
            super.onBackPressed();
        }
    }


}
