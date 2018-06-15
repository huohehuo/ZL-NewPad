package com.fangzuo.assist.MVContract.presenter;

import android.content.Context;

import com.example.miniprinter.App;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.MVContract.base.IPresenter;
import com.fangzuo.assist.MVContract.view.DownLoadView;
import com.fangzuo.assist.MVContract.view.TestView;
import com.fangzuo.assist.RxSerivce.MySubscribe;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.JsonCreater;

import java.util.ArrayList;


public class DownLoadPresenter implements IPresenter {

    private Context mContext;
    private DownLoadView view;
    public DownLoadPresenter(DownLoadView view){
        this.view = view;
    }

    public void getUser(BasicShareUtil share){
        ArrayList<Integer> choose = new ArrayList<>();
        choose.add(12);
        String json = JsonCreater.DownLoadDataWithIp(share,choose);
        App.getRService().getTest(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                view.getBackData(commonResponse, Config.IO_type_Test);
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showError(e.toString(), Config.IO_type_Test);
            }
        });
    }

    public void getTestDataBase(String json){
        App.getRService().getTestDataBase("", new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                view.getBackData(commonResponse, Config.IO_type_TestDataBase);
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showError(e.toString(), Config.IO_type_TestDataBase);
            }
        });

    }

    public void SetProp(String json){
        App.getRService().SetProp(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                view.getBackData(commonResponse, Config.IO_type_SetProp);
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showError(e.toString(), Config.IO_type_SetProp);
            }
        });

    }

    public void connectToSQL(String json){
        App.getRService().connectToSQL(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                view.getBackData(commonResponse, Config.IO_type_connectToSQL);
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showError(e.toString(), Config.IO_type_connectToSQL);
            }
        });

    }

    @Override
    public void onCreate() {

    }
}
