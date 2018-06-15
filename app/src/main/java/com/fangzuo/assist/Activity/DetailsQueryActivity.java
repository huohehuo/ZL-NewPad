package com.fangzuo.assist.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.miniprinter.App;
import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.DetailsStorageAdapter;
import com.fangzuo.assist.Adapter.DetailsStorageBAdapter;
import com.fangzuo.assist.Adapter.DetailsStorageCAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.StorageCheckA;
import com.fangzuo.assist.R;
import com.fangzuo.assist.RxSerivce.MySubscribe;
import com.fangzuo.assist.Utils.CommonUtil;
import com.fangzuo.assist.Utils.DoubleUtil;
import com.fangzuo.assist.Utils.Lg;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsQueryActivity extends BaseActivity {

    @BindView(R.id.ry_storage)
    EasyRecyclerView ryStorage;
    @BindView(R.id.ry_storage_b)
    EasyRecyclerView ryStorageB;
    @BindView(R.id.ry_storage_c)
    EasyRecyclerView ryStorageC;
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.v_b)
    View vB;
    @BindView(R.id.ll_b)
    LinearLayout llB;
    @BindView(R.id.v_c)
    View vC;
    @BindView(R.id.ll_c)
    LinearLayout llC;

    DetailsStorageAdapter adapter;
    DetailsStorageBAdapter adapterB;
    DetailsStorageCAdapter adapterC;
    private String storageId;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_details_query);
        ButterKnife.bind(this);
        tvTitle.setText("仓库查询");

        ryStorage.setAdapter(adapter = new DetailsStorageAdapter(this));
//        ryStorage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ryStorage.setLayoutManager(new LinearLayoutManager(this));
        //            binding.ryMark.setLayoutManager(new LinearLayoutManager(getActivity()));
//            binding.ryMark.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        ryStorageB.setAdapter(adapterB = new DetailsStorageBAdapter(this));
        ryStorageB.setLayoutManager(new LinearLayoutManager(this));
        ryStorageC.setAdapter(adapterC = new DetailsStorageCAdapter(this));
        ryStorageC.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        App.getRService().getStorage("", new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                Lg.e("A|" + commonResponse.returnJson);
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (dBean.storageCheckAS.size() >= 1) {
                    adapter.clear();
                    //过滤体积为0的仓库
                    for (StorageCheckA s:dBean.storageCheckAS) {
                        if (!CommonUtil.checkHasABC(s.FVolume) && Double.parseDouble(s.FVolume)>0){
                            adapter.add(s);
                        }
                    }
                    updateText();
                } else {
//                    tvMsg.setText("无数据");
                    adapter.clear();
                }
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }
    private void updateText(){
        double gs=0d;
        double tj=0d;
        for (int i = 0; i < adapter.getAllData().size(); i++) {
            gs= gs+Double.parseDouble(adapter.getAllData().get(i).FRadical);
            if (!CommonUtil.checkHasABC(adapter.getAllData().get(i).FVolume)){
                tj= tj+Double.parseDouble(adapter.getAllData().get(i).FVolume);
            }
        }
        tvText.setText("汇总："+tj+"立方   "+gs+"根");
    }


    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                llB.setVisibility(View.VISIBLE);
                tvRight.setText("当前仓库："+adapter.getAllData().get(position).FName+"  ");
                storageId = adapter.getAllData().get(position).FStorageId;
                changeB(adapter.getAllData().get(position).FStorageId);
            }
        });
        adapterB.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                llC.setVisibility(View.VISIBLE);
                changeC(storageId);
            }
        });
    }

    private void changeB(String id) {
        ryStorageB.setRefreshing(true);
        App.getRService().getStorageB(id, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                Lg.e("B|" + commonResponse.returnJson);
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (dBean.storageCheckBS.size() >= 1) {
                    adapterB.clear();
                    adapterB.addAll(dBean.storageCheckBS);
                } else {
//                    tvMsg.setText("无数据");
                    adapterB.clear();
                }
                ryStorageB.setRefreshing(false);

            }

            @Override
            public void onError(Throwable e) {
                ryStorageB.setRefreshing(false);
            }
        });
    }

    private void changeC(String id) {
        ryStorageC.setRefreshing(true);
        App.getRService().getStorageC(id, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                Lg.e("C|" + commonResponse.returnJson);
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (dBean.storageCheckCS.size() >= 1) {
                    adapterC.clear();
                    adapterC.addAll(dBean.storageCheckCS);
                } else {
//                    tvMsg.setText("无数据");
                    adapterC.clear();
                }
                ryStorageC.setRefreshing(false);

            }

            @Override
            public void onError(Throwable e) {
                ryStorageC.setRefreshing(false);

            }
        });
    }


    @Override
    protected void OnReceive(String code) {

    }

    @OnClick({R.id.btn_back, R.id.tv_right,R.id.v_b, R.id.v_c})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_right:
                break;
            case R.id.v_b:
                llB.setVisibility(View.GONE);
                tvRight.setText("");
                break;
            case R.id.v_c:
                llC.setVisibility(View.GONE);
                break;
        }
    }

}
