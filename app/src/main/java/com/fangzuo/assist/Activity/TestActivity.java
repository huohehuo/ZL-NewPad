package com.fangzuo.assist.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.MVContract.presenter.TestPresenter;
import com.fangzuo.assist.MVContract.view.TestView;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Server.WebAPI;
import com.fangzuo.assist.Utils.CallBack;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.RetrofitUtil;
import com.orhanobut.hawk.Hawk;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity implements TestView {


    @BindView(R.id.test_tomcat)
    Button testTomcat;
    @BindView(R.id.test_database)
    Button testDatabase;
    @BindView(R.id.tv_result)
    TextView tvResult;
    private TestActivity mContext;
    @BindColor(R.color.green)
    int green;
    @BindColor(R.color.red)
    int red;
    @BindView(R.id.tv_pdaNo)
    TextView tvPdaNo;

    private TestPresenter presenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);
        mContext = this;
        presenter = new TestPresenter(this);
        tvPdaNo.setText("PDA 单据唯一编号："+ Hawk.get(Config.OrderCode_PdaNo_s,"000"));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void OnReceive(String code) {

    }
    private long nowtime;
    private void testTomcat(){
        nowtime =System.currentTimeMillis();
        presenter.getTest("");


//        final long nowtime =System.currentTimeMillis();
//        RetrofitUtil.getInstance(mContext).createReq(WebAPI.class).
//                TestTomcat(RetrofitUtil.getParams(mContext,"")).enqueue(new CallBack() {
//            @Override
//            public void onSucceed(CommonResponse cBean) {
//                long endTime = System.currentTimeMillis();
//                tvResult.setTextColor(green);
//                tvResult.setText("结果:获取150kb数据所需时间"+(endTime-nowtime)+"获取数据:"+cBean.returnJson);
//
//            }
//
//            @Override
//            public void OnFail(String Msg) {
//                tvResult.setTextColor(red);
//                tvResult.setText(Msg);
//            }
//        });
    }

    private void testDataBase(){
        presenter.getTestDataBase("");

//        RetrofitUtil.getInstance(mContext).createReq(WebAPI.class).TestDataBase(RetrofitUtil.getParams(mContext,""))
//                .enqueue(new CallBack() {
//                    @Override
//                    public void onSucceed(CommonResponse cBean) {
//                        tvResult.setText("服务器测试结果:\r\n"+cBean.returnJson);
//                    }
//
//                    @Override
//                    public void OnFail(String Msg) {
//                        tvResult.setText("服务器测试结果:"+Msg);
//                    }
//                });
    }
    @OnClick({R.id.test_tomcat, R.id.test_database})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_tomcat:
                testTomcat();
                break;
            case R.id.test_database:
                testDataBase();
                break;
        }
    }

    @Override
    public void getBackData(CommonResponse commonResponse,String type) {
        if (Config.IO_type_Test.equals(type)){
            long endTime = System.currentTimeMillis();
            tvResult.setTextColor(green);
            tvResult.setText("结果:获取150kb数据所需时间"+(endTime-nowtime)+"获取数据:"+commonResponse.returnJson);
        }else{
            tvResult.setText("服务器测试结果:\r\n"+commonResponse.returnJson);
        }
    }

    @Override
    public void showError(String error,String type) {
        if (Config.IO_type_Test.equals(type)) {
            tvResult.setTextColor(red);
            tvResult.setText(error);
        }else{
            tvResult.setText("服务器测试结果:"+error);
        }


    }
}
