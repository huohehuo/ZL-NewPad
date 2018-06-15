package com.fangzuo.assist.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fangzuo.assist.ABase.BaseFragment;
import com.fangzuo.assist.Activity.CheckOLineActivity;
import com.fangzuo.assist.Activity.LookActivity;
import com.fangzuo.assist.Adapter.ACheckAdapter;
import com.fangzuo.assist.Adapter.BCheckAdapter;
import com.fangzuo.assist.Adapter.CheckOlineBAdapter;
import com.fangzuo.assist.Adapter.SecondRvAdapter;
import com.fangzuo.assist.Adapter.SecondRvForLongAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.SecondCheck;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.EventBusUtil;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.VibratorUtil;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.loopj.android.http.AsyncHttpClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckOLineBFragment extends BaseFragment {


    @BindView(R.id.ry_check_b)
    EasyRecyclerView ryCheckB;

    Unbinder unbinder;
    private DaoSession daoSession;
    private FragmentActivity mContext;
    private T_mainDao t_mainDao;
    private T_DetailDao t_detailDao;
    private int tag;
    private FirstCheck getfirst;
    private SecondRvAdapter secondRvAdapte;
//    private CheckOlineBAdapter secondRvForLongAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SecondCheck items;
    private CheckOLineActivity lookActivity;
    private CheckOLineCFragment thirdFragment;
    private ArrayList<SecondCheck> container;

    private BCheckAdapter adapter;
    public CheckOLineBFragment() {
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
        lookActivity = ((CheckOLineActivity) activity);
        tag = lookActivity.getTag();
        getfirst = lookActivity.getfirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //判断短材还是长材，是否显示长度这一栏
        View view=inflater.inflate(R.layout.fragment_check_oline_b,container,false);
//        if (tag==Config.CHOOSE_SECOND_FRAGMENT_SHORT){
//            view = inflater.inflate(R.layout.fragment_blank, container, false);
//        }else if (tag == Config.CHOOSE_SECOND_FRAGMENT_LONG){
//            view = inflater.inflate(R.layout.fragment_blank_long, container, false);
//        }else{
//            view = inflater.inflate(R.layout.fragment_blank_long, container, false);
//        }
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        return view;
    }

    @Override
    protected void initView() {
        mContext = getActivity();


        ryCheckB.setAdapter(adapter = new BCheckAdapter(getActivity()));
        ryCheckB.setLayoutManager(new LinearLayoutManager(getActivity()));

        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        t_mainDao = daoSession.getT_mainDao();
        t_detailDao = daoSession.getT_DetailDao();

//        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//        container = new ArrayList<>();
//        secondRvForLongAdapter = new CheckOlineBAdapter(mContext,container);
//        rv.setLayoutManager(mLinearLayoutManager);
//        rv.setItemAnimator(new DefaultItemAnimator());
//        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
//        rv.setAdapter(secondRvForLongAdapter);
    }

    @Override
    protected void initData() {
        Asynchttp.post(mContext, BasicShareUtil.getInstance(mContext).getBaseURL() + WebApi.CheckLikeB, getfirst.orderID, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                    Lg.e("二级数据："+cBean.returnJson);
                final DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                if (dBean.secondChecks.size() >= 1) {
                    adapter.clear();
                    adapter.addAll(dBean.secondChecks);
                } else {
                    adapter.clear();
                }
//                ryCheckA.setRefreshing(false);
//                if (dBean.products.size() == 1) {
//                    getProductOL(dBean, 0);
//                    default_unitID = dBean.products.get(0).FUnitID;
//                } else if (dBean.products.size() > 1) {
//                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                    ab.setTitle("请选择物料");
//                    View v = LayoutInflater.from(mContext).inflate(R.layout.pd_alert, null);
//                    ListView lv = v.findViewById(R.id.lv_alert);
//                    productselectAdapter1 = new ProductselectAdapter1(mContext, dBean.products);
//                    lv.setAdapter(productselectAdapter);
//                    ab.setView(v);
//                    final AlertDialog alertDialog = ab.create();
//                    alertDialog.show();
//                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            getProductOL(dBean, i);
//                            default_unitID = dBean.products.get(i).FUnitID;
//                            alertDialog.dismiss();
//                        }
//                    });
//                }

            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext, Msg);
//                ryCheckA.setRefreshing(false);
            }
        });

    }

    @Override
    protected void initListener() {
        ryCheckB.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                items = adapter.getAllData().get(position);
                Log.e("BlankFragment", items.orderID+"");
                thirdFragment = new CheckOLineCFragment();
                lookActivity.setOrderID(items.orderID);
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).add(R.id.fragmenthost, thirdFragment).commit();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).remove(CheckOLineBFragment.this).commit();
                EventBusUtil.sendEvent(new ClassEvent(1+"",items));

//                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                    ab.setTitle("请选择物料");
//                    View v = LayoutInflater.from(mContext).inflate(R.layout.pd_alert, null);
//                    ListView lv = v.findViewById(R.id.lv_alert);
//                    productselectAdapter1 = new ProductselectAdapter1(mContext, dBean.products);
//                    lv.setAdapter(productselectAdapter);
//                    ab.setView(v);
//                    final AlertDialog alertDialog = ab.create();
//                    alertDialog.show();
//                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            getProductOL(dBean, i);
//                            default_unitID = dBean.products.get(i).FUnitID;
//                            alertDialog.dismiss();
//                        }
//                    });

            }
        });

//            secondRvForLongAdapter.setOnItemClickListener(new CheckOlineBAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    items = secondRvForLongAdapter.getItems(position);
//                    Log.e("BlankFragment", items.FInterID+"");
//                    thirdFragment = new ThirdFragment();
//                    lookActivity.setOrderID(items.FInterID);
//                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).add(R.id.fragmenthost, thirdFragment).commit();
//                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in,R.anim.activity_slide_left_out).remove(CheckOLineBFragment.this).commit();
//                    EventBusUtil.sendEvent(new ClassEvent(1+"",items));
//                }
//
//                @Override
//                public void onItemLongClick(View view, int position) {
//                }
//            });


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
//            lookActivity.initList();
        }
    }
}
