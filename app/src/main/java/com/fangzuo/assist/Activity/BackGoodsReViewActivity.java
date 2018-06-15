package com.fangzuo.assist.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.BackGoodsReViewAdapter;
import com.fangzuo.assist.Dao.BackGoodsBean;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.greendao.gen.BackGoodsBeanDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackGoodsReViewActivity extends BaseActivity {

    @BindView(R.id.cancle)
    View cancle;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ry_backgoods)
    EasyRecyclerView ryBackgoods;
    @BindView(R.id.tv_summarize)
    TextView tvSummarize;

    BackGoodsReViewAdapter adapter;
    private BackGoodsBeanDao backGoodsBeanDao;
    private DaoSession daoSession;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_back_goods_re_view);
        ButterKnife.bind(this);
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        backGoodsBeanDao = daoSession.getBackGoodsBeanDao();

        ryBackgoods.setAdapter(adapter = new BackGoodsReViewAdapter(this));
        ryBackgoods.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        List<BackGoodsBean> list = backGoodsBeanDao.queryBuilder().orderDesc(BackGoodsBeanDao.Properties.FIndex).build().list();
        Lg.e("review："+list.toString());
        double gs=0d;
        double tj=0d;
        for (BackGoodsBean b:list) {
            if (!"0".equals(b.radical)){
                adapter.add(b);
                gs = gs+Double.parseDouble(b.radical);
                tj = tj+Double.parseDouble(b.FQuantity);
            }
        }
        tvSummarize.setText("汇总----- 根数："+gs+"   体积："+tj);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void OnReceive(String code) {

    }


    @OnClick({R.id.cancle, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancle:
                finish();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
