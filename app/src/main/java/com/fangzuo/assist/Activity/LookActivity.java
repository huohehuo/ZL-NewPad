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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.FirstRvAdapter;
import com.fangzuo.assist.Adapter.Padapter;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.Dao.T_main;
import com.fangzuo.assist.Fragment.BlankFragment;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Service.BackDataService;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.EventBusInfoCode;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.VibratorUtil;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.PDSubDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.orhanobut.hawk.Hawk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LookActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.fragmenthost)
    FrameLayout fragmenthost;
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private DaoSession daoSession;
    private List<T_Detail> t_details;
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
    private ArrayList<FirstCheck> clickList;
    private FirstCheck items;
    private String order;
    private BlankFragment blankFragment;
    private DecimalFormat df;
    public static String allTjAndGs = "";
    LinearLayoutManager mLinearLayoutManager2;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_table);
        mContext = this;
        ButterKnife.bind(this);
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        t_mainDao = daoSession.getT_mainDao();
        t_detailDao = daoSession.getT_DetailDao();
        tvTitle.setText("查单");
        tvTitle.setTextSize(28);
        tvRight.setText("单号打印");
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
        df = new DecimalFormat("######0.000");

        container = new ArrayList<>();
        fContainer = new ArrayList<>();
        clickList = new ArrayList<>();
        firstRvAdapter = new FirstRvAdapter(mContext, container);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        rv.setAdapter(firstRvAdapter);
        initList();


    }

    public void initList() {
        container.clear();
        fContainer.clear();
        t_mains = t_mainDao.queryBuilder().where(
                T_mainDao.Properties.Activity.eq(activity)
        ).build().list();

//        List<T_main> mains=t_mainDao.queryBuilder().where(
//                T_mainDao.Properties.Activity.eq(activity),
//                T_mainDao.Properties.OrderId.eq(order)
//        ).build().list();

//         t_details= t_detailDao.queryBuilder().where(
//                T_DetailDao.Properties.Activity.eq(activity)
//        ).build().list();
//        fContainer = new ArrayList<>();
//        for (int i=0;i<t_details.size();i++){
//            FirstCheck f = new FirstCheck();
//            f.orderID = t_details.get(i).FOrderId+"";
//            f.productname = t_details.get(i).FProductName;
//            f.TRadical = t_details.get(i).radical;
//            f.TVoleum = t_details.get(i).FQuantity;
//            f.itemID = t_details.get(i).FProductId;
//            f.model = t_details.get(i).model;
//            f.clientName= t_details.get(i).clientName;
//            fContainer.add(f);
//        }
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                "FORDER_ID," +
                "CLIENT_NAME," +
                "MODEL," +
                "FRED_BLUE," +
                "LENGTH," +
                "FPRODUCT_NAME," +
                "FPRODUCT_ID," +
                "SUM(FQUANTITY) AS FQTY," +
                "SUM(RADICAL) AS RADICAL " +
                "FROM T__DETAIL " +
                "WHERE ACTIVITY=? GROUP BY FORDER_ID," +
                "FPRODUCT_NAME,FPRODUCT_ID ORDER BY FORDER_ID DESC", new String[]{activity + ""});

        while (cursor.moveToNext()) {

            FirstCheck f = new FirstCheck();
            f.orderID = cursor.getString(cursor.getColumnIndex("FORDER_ID"));
            f.productname = cursor.getString(cursor.getColumnIndex("FPRODUCT_NAME"));
            f.TRadical = cursor.getString(cursor.getColumnIndex("RADICAL"));
            f.TVoleum = cursor.getString(cursor.getColumnIndex("FQTY"));
            f.itemID = cursor.getString(cursor.getColumnIndex("FPRODUCT_ID"));
            f.model = cursor.getString(cursor.getColumnIndex("MODEL"));
            f.length = cursor.getString(cursor.getColumnIndex("LENGTH"));
            f.clientName = cursor.getString(cursor.getColumnIndex("CLIENT_NAME"));
            f.FRedBlue = cursor.getString(cursor.getColumnIndex("FRED_BLUE"));
            fContainer.add(f);
        }
//        Log.e(TAG,"查找到："+fContainer.toString());
        container.addAll(fContainer);
        firstRvAdapter.notifyDataSetChanged();

        Log.e("LookActivity", "fContainer.size():" + fContainer.size());
        Log.e("LookActivity", "fContainer:" + fContainer.toString());

    }


    @Override
    protected void initListener() {

        firstRvAdapter.setOnItemClickListener(new FirstRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                items = firstRvAdapter.getItems(position);
                getAllTjAndGsData(items);
                blankFragment = new BlankFragment();
                transaction.setCustomAnimations(R.anim.activity_slide_right_in, R.anim.activity_slide_left_out).add(R.id.fragmenthost, blankFragment).commit();
                VibratorUtil.Vibrate(mContext, 500);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                items = firstRvAdapter.getItems(position);
                int tr = 0;
                double zongQTY = 0.00;

                StringBuilder s = new StringBuilder();
                Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                        "FIDENTITY," +
                        "SUM(RADICAL) AS RADICAL," +
                        "SUM(FQUANTITY) AS FQTY " +
                        "FROM T__DETAIL " +
                        "WHERE ACTIVITY=? AND FORDER_ID = ? GROUP BY " +
                        "FIDENTITY ORDER BY cast(FIDENTITY as int)", new String[]{activity + "", items.orderID});
                while (cursor.moveToNext()) {
                    String bao = cursor.getString(cursor.getColumnIndex("FIDENTITY"));
                    String r = cursor.getString(cursor.getColumnIndex("RADICAL"));
                    String vivo = cursor.getString(cursor.getColumnIndex("FQTY"));
                    zongQTY += Double.parseDouble(cursor.getString(cursor.getColumnIndex("FQTY")));
                    tr += Integer.parseInt(r);
                    s.append("\n报数:").append(bao).append("   ").append("总根数:").append(r)
                            .append("\n").append("总体积:").append(vivo);
                }
                s.append("\n\n根数汇总:").append(tr);
                s.append("\n体积汇总:").append(df.format(zongQTY));
                allTjAndGs = "总根数:" + tr + "    总体积:" + df.format(zongQTY);
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("汇总");
                ab.setMessage(s);
                ab.setPositiveButton("确定", null).create().show();
            }
        });
    }

    //tong
    private void getAllTjAndGsData(FirstCheck items) {
        int tr = 0;
        double zongQTY = 0.00;
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                "FIDENTITY," +
                "SUM(RADICAL) AS RADICAL," +
                "SUM(FQUANTITY) AS FQTY " +
                "FROM T__DETAIL " +
                "WHERE ACTIVITY=? AND FORDER_ID = ? GROUP BY " +
                "FIDENTITY ORDER BY cast(FIDENTITY as int)", new String[]{activity + "", items.orderID});
        while (cursor.moveToNext()) {
            String r = cursor.getString(cursor.getColumnIndex("RADICAL"));
            zongQTY += Double.parseDouble(cursor.getString(cursor.getColumnIndex("FQTY")));
            tr += Integer.parseInt(r);
        }
        allTjAndGs = "总根数:" + tr + "    总体积:" + df.format(zongQTY);

    }

    @Override
    protected void OnReceive(String code) {

    }

    @OnClick({R.id.btn_back, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.tv_right:
                View v = LayoutInflater.from(mContext).inflate(R.layout.item_choosetoprint, null);
                mLinearLayoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

                ListView r = v.findViewById(R.id.rv_choose);
                final Padapter padapter = new Padapter(container, mContext);
                r.setAdapter(padapter);
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("选择打印单据").setView(v).create().show();
                r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        items = padapter.getFirst(i);
                        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                        VibratorUtil.Vibrate(mContext, 1000);
                        ab.setTitle("打印");
                        ab.setMessage("是否生成打印订单号:" + items.orderID + "的小票")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        getAllTjAndGsData(items);
                                        Bundle b = new Bundle();
                                        b.putInt("tag", activity);
                                        b.putSerializable("item", items);
                                        startNewActivity(PrintActivity.class, R.anim.activity_fade_in, R.anim.activity_fade_out, false, b);
                                    }
                                }).setNegativeButton("取消", null).create().show();
                    }
                });
                break;
        }
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public void startActivityTo(EditText editText) {
        Bundle b = new Bundle();
        b.putString("search", editText.getText().toString());
        b.putInt("where", Info.SEARCHCLIENT);
        startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTCLIRNT, b);
    }
    public void startActivityToBackGoods(String id,int activity) {
        Bundle b = new Bundle();
        b.putString("orderId", id);
        b.putInt("activity", activity);
        startNewActivityForResult(BackGoodsActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTCLIRNT, b);

    }

    String clientId = "";
    String clientName = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("code", requestCode + "" + "    " + resultCode);
        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//                Bundle b = data.getExtras();
//                String message = b.getString("result");
//                edCode.setText(message);
//                Toast.showText(mContext, message);
//                edCode.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
//            }
        } else if (requestCode == Info.SEARCHFORRESULTCLIRNT) {
            if (resultCode == Info.SEARCHFORRESULTCLIRNT) {
                final List<T_main> mains = t_mainDao.queryBuilder().where(
                        T_mainDao.Properties.Activity.eq(activity),
                        T_mainDao.Properties.OrderId.eq(order)
                ).build().list();
                t_details = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.Activity.eq(activity),
                        T_DetailDao.Properties.FOrderId.eq(order)
                ).build().list();
                Bundle b = data.getExtras();
                clientId = b.getString("001");
                clientName = b.getString("002");
                for (int i = 0; i < mains.size(); i++) {
                    mains.get(i).supplierId = clientId;
                    mains.get(i).supplier = clientName;
                    t_mainDao.update(mains.get(i));
                }
                for (int i = 0; i < t_details.size(); i++) {
                    t_details.get(i).clientName = clientName;
                    t_detailDao.update(t_details.get(i));
                }
                if (activity == Config.CHOOSE_SECOND_FRAGMENT_SHORT) {
                    share.setSClientID(clientId);
                    share.setSClient(clientName);
                    Toast.showText(mContext, "更新成功");
                }
                if (activity == Config.CHOOSE_SECOND_FRAGMENT_LONG) {
                    share.setLClient(clientName);
                    share.setLClientID(clientId);
                    Toast.showText(mContext, "更新成功");
                }
                if (activity == Config.CHOOSE_SECOND_FRAGMENT_SHORT_RED) {
                    Hawk.put(Config.Short_red_ClientId_s, clientId);
                    Hawk.put(Config.Short_red_ClientName_s, clientName);
                    Toast.showText(mContext, "更新成功");
                }
                if (activity == Config.CHOOSE_SECOND_FRAGMENT_LONG_RED) {
                    Hawk.put(Config.Long_red_ClientId_s, clientId);
                    Hawk.put(Config.Long_red_ClientName_s, clientName);
                    Toast.showText(mContext, "更新成功");
                }

            }
        }
    }



}
