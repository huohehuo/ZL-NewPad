package com.fangzuo.assist.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fangzuo.assist.ABase.BaseFragment;
import com.fangzuo.assist.Activity.LookActivity;
import com.fangzuo.assist.Adapter.SecondRvAdapter;
import com.fangzuo.assist.Adapter.SecondRvForLongAdapter;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.SecondCheck;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.EventBusUtil;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment {


    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private DaoSession daoSession;
    private FragmentActivity mContext;
    private T_mainDao t_mainDao;
    private T_DetailDao t_detailDao;
    private int tag;
    private FirstCheck getfirst;
    private SecondRvAdapter secondRvAdapte;
    private SecondRvForLongAdapter secondRvForLongAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SecondCheck items;
    private LookActivity lookActivity;
    private ThirdFragment thirdFragment;

    public BlankFragment() {
        // Required empty public constructor
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(ClassEvent event) {
        if (event != null) {
            switch (event.Msg){

            }
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        lookActivity = ((LookActivity) activity);
        tag = lookActivity.getTag();
        getfirst = lookActivity.getfirst();
        Lg.e("Blank:"+getfirst.toString());
        lookActivity.setOrderID(getfirst.orderID);
        lookActivity.setClientName(getfirst.clientName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //判断短材还是长材，是否显示长度这一栏
        View view;
        if (tag==Config.CHOOSE_SECOND_FRAGMENT_SHORT || tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT_RED){
            view = inflater.inflate(R.layout.fragment_blank, container, false);
        }else if (tag == Config.CHOOSE_SECOND_FRAGMENT_LONG || tag == Config.CHOOSE_SECOND_FRAGMENT_LONG_RED){
            view = inflater.inflate(R.layout.fragment_blank_long, container, false);
        }else{
            view = inflater.inflate(R.layout.fragment_blank_long, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        return view;
    }

    @Override
    protected void initView() {
        mContext = getActivity();
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        t_mainDao = daoSession.getT_mainDao();
        t_detailDao = daoSession.getT_DetailDao();
    }
    private List<T_Detail> t_details;
    @Override
    protected void initData() {
        ArrayList<SecondCheck> container = new ArrayList<>();

//        t_details = t_detailDao
//                .queryBuilder()
//                .where(T_DetailDao.Properties.Activity.eq(tag),
//                        T_DetailDao.Properties.FOrderId.eq(getfirst.orderID))
//                .orderAsc(T_DetailDao.Properties.FIdentity)
//                .build()
//                .list();
//
//        for (int i=0;i<t_details.size();i++) {
//            SecondCheck secondCheck = new SecondCheck();
//            secondCheck.diameter =      t_details.get(i).diameter;
//            secondCheck.itemID =        t_details.get(i).FProductId;
//            secondCheck.orderID =       t_details.get(i).FOrderId+"";
//            secondCheck.productname =   t_details.get(i).FProductName;
//            secondCheck.TRadical =      t_details.get(i).radical;
//            secondCheck.TVoleum =       t_details.get(i).FQuantity;
//            secondCheck.model =         t_details.get(i).model;
//            secondCheck.length =        t_details.get(i).length;
//            container.add(secondCheck);
//        }




        Log.e(TAG,"获取二级数据:"+container.toString());
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        if (tag== Config.CHOOSE_SECOND_FRAGMENT_SHORT || tag== Config.CHOOSE_SECOND_FRAGMENT_SHORT_RED){
            Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                            "MODEL," +
                            "LENGTH," +
                            "FRED_BLUE," +
                            "DIAMETER, " +
                            "FORDER_ID," +
                            "FPRODUCT_NAME," +
                            "FPRODUCT_ID," +
                            "SUM(FQUANTITY) AS FQTY," +
                            "SUM(RADICAL) AS RADICAL " +
                            "FROM T__DETAIL WHERE " +
                            "ACTIVITY=? AND " +
                            "FORDER_ID = ? AND " +
                            "FPRODUCT_ID = ? " +
                            "GROUP BY " +
                            "FPRODUCT_NAME,FPRODUCT_ID,DIAMETER ORDER BY cast(DIAMETER as int)",
                    new String[]{tag + "", getfirst.orderID, getfirst.itemID});
            while (cursor.moveToNext()) {
                SecondCheck secondCheck = new SecondCheck();
                secondCheck.diameter = cursor.getString(cursor.getColumnIndex("DIAMETER"));
                secondCheck.itemID = cursor.getString(cursor.getColumnIndex("FPRODUCT_ID"));
                secondCheck.orderID = cursor.getString(cursor.getColumnIndex("FORDER_ID"));
                secondCheck.productname = cursor.getString(cursor.getColumnIndex("FPRODUCT_NAME"));
                secondCheck.TRadical = cursor.getString(cursor.getColumnIndex("RADICAL"));
                secondCheck.TVoleum = cursor.getString(cursor.getColumnIndex("FQTY"));
                secondCheck.model = cursor.getString(cursor.getColumnIndex("MODEL"));
                secondCheck.length = cursor.getString(cursor.getColumnIndex("LENGTH"));
                secondCheck.FRedBlue = cursor.getString(cursor.getColumnIndex("FRED_BLUE"));
                container.add(secondCheck);
            }
            secondRvAdapte = new SecondRvAdapter(mContext,container);
            rv.setLayoutManager(mLinearLayoutManager);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            rv.setAdapter(secondRvAdapte);
        }else{

                    t_details = t_detailDao
                .queryBuilder()
                .where(T_DetailDao.Properties.Activity.eq(tag),
                        T_DetailDao.Properties.FOrderId.eq(getfirst.orderID))
                .orderAsc(T_DetailDao.Properties.Diameter)
                .build()
                .list();

        for (int i=0;i<t_details.size();i++) {
            SecondCheck secondCheck = new SecondCheck();
            secondCheck.diameter =      t_details.get(i).diameter+"";
            secondCheck.itemID =        t_details.get(i).FProductId;
            secondCheck.orderID =       t_details.get(i).FOrderId+"";
            secondCheck.productname =   t_details.get(i).FProductName;
            secondCheck.TRadical =      t_details.get(i).radical;
            secondCheck.TVoleum =       t_details.get(i).FQuantity;
            secondCheck.model =         t_details.get(i).model;
            secondCheck.length =        t_details.get(i).length;
            secondCheck.FRedBlue =        t_details.get(i).FRedBlue;
            container.add(secondCheck);
        }
//            Cursor cursorLong = daoSession.getDatabase().rawQuery("SELECT " +
//                            "MODEL," +
//                            "FRED_BLUE," +
//                            "SUM(LENGTH) AS FLENGTH," +
//                            "DIAMETER, " +
//                            "FORDER_ID," +
//                            "FPRODUCT_NAME," +
//                            "FPRODUCT_ID," +
//                            "SUM(FQUANTITY) AS FQTY," +
//                            "SUM(RADICAL) AS RADICAL " +
//                            "FROM T__DETAIL WHERE " +
//                            "ACTIVITY=? AND " +
//                            "FORDER_ID = ? AND " +
//                            "FPRODUCT_ID = ? " +
//                            "GROUP BY " +
//                            "FPRODUCT_NAME,FPRODUCT_ID,DIAMETER,LENGTH ORDER BY cast(DIAMETER as int)",
//                    new String[]{tag + "", getfirst.orderID, getfirst.itemID});
//            while (cursorLong.moveToNext()) {
//                SecondCheck secondCheck = new SecondCheck();
//                secondCheck.diameter = cursorLong.getString(cursorLong.getColumnIndex("DIAMETER"));
//                secondCheck.itemID = cursorLong.getString(cursorLong.getColumnIndex("FPRODUCT_ID"));
//                secondCheck.orderID = cursorLong.getString(cursorLong.getColumnIndex("FORDER_ID"));
//                secondCheck.productname = cursorLong.getString(cursorLong.getColumnIndex("FPRODUCT_NAME"));
//                secondCheck.TRadical = cursorLong.getString(cursorLong.getColumnIndex("RADICAL"));
//                secondCheck.TVoleum = cursorLong.getString(cursorLong.getColumnIndex("FQTY"));
//                secondCheck.model = cursorLong.getString(cursorLong.getColumnIndex("MODEL"));
//                secondCheck.length = cursorLong.getString(cursorLong.getColumnIndex("FLENGTH"));
//                secondCheck.FRedBlue = cursorLong.getString(cursorLong.getColumnIndex("FRED_BLUE"));
//                container.add(secondCheck);
//            }
            secondRvForLongAdapter = new SecondRvForLongAdapter(mContext,container);
            rv.setLayoutManager(mLinearLayoutManager);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            rv.setAdapter(secondRvForLongAdapter);
        }


    }

    @Override
    protected void initListener() {

        if (tag== Config.CHOOSE_SECOND_FRAGMENT_SHORT || tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT_RED){
            secondRvAdapte.setOnItemClickListener(new SecondRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    items = secondRvAdapte.getItems(position);
                    Log.e("BlankFragment", items.orderID+"");
                    thirdFragment = new ThirdFragment();
                    lookActivity.setOrderID(items.orderID);
                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).add(R.id.fragmenthost, thirdFragment).commit();
                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).remove(BlankFragment.this).commit();
                    EventBusUtil.sendEvent(new ClassEvent(1+"",items));
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    items = secondRvAdapte.getItems(position);
                    int tr = 0;
                    StringBuilder s = new StringBuilder();
                    Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                            "FIDENTITY," +
                            "SUM(RADICAL) AS RADICAL " +
                            "FROM T__DETAIL " +
                            "WHERE FORDER_ID = ? GROUP BY " +
                            "FIDENTITY ORDER BY cast(DIAMETER as int)", new String[]{items.orderID});
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
        }else{
            secondRvForLongAdapter.setOnItemClickListener(new SecondRvForLongAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    items = secondRvForLongAdapter.getItems(position);
                    Log.e("BlankFragment", items.orderID+"");
                    thirdFragment = new ThirdFragment();
                    lookActivity.setOrderID(items.orderID);
                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).add(R.id.fragmenthost, thirdFragment).commit();
                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).remove(BlankFragment.this).commit();
                    EventBusUtil.sendEvent(new ClassEvent(1+"",items));
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    items = secondRvForLongAdapter.getItems(position);
                    int tr = 0;
                    StringBuilder s = new StringBuilder();
                    Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                            "FIDENTITY," +
                            "SUM(RADICAL) AS RADICAL " +
                            "FROM T__DETAIL " +
                            "WHERE FORDER_ID = ? GROUP BY " +
                            "FIDENTITY ORDER BY cast(DIAMETER as int)", new String[]{items.orderID});
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

    }

    @Override
    protected void OnReceive(String barCode) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBusUtil.unregister(this);
    }

    public void pressBack(){
        if(thirdFragment!=null&&thirdFragment.isAdded()){
            thirdFragment.PressThirdBack();
        }else{
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).remove(this).commit();
            lookActivity.initList();
        }
    }
}
