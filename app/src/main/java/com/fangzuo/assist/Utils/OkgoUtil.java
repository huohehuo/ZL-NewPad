package com.fangzuo.assist.Utils;

import android.content.Context;

import com.fangzuo.assist.Beans.CommonResponse;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okrx2.adapter.ObservableResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 王璐阳 on 2018/4/10.
 */

public class OkgoUtil {
    private static OkgoUtil mInstance;
    private Context context;
    private final BasicShareUtil share;

    private OkgoUtil(Context context) {
        this.context = context;
        share = BasicShareUtil.getInstance(context);
    }

    public static synchronized OkgoUtil getInstance(Context context){
        if(mInstance==null){
            return new OkgoUtil(context);
        }
        return mInstance;
    }

    public void post(String api, String json, final ApiResponse apiResponse){
        HttpParams params = new HttpParams();
        params.put("json",json);
        params.put("sqlip",share.getDatabaseIp());
        params.put("sqlport",share.getDatabasePort());
        params.put("sqluser",share.getDataBaseUser());
        params.put("sqlpass",share.getDataBasePass());
        params.put("sqlname",share.getDataBase());
        params.put("version",share.getVersion());
        OkGo.<CommonResponse>post(share.getBaseURL()+api)
                .params(params)
                .converter(new CommonResponseConverter())
                .adapt(new ObservableResponse<CommonResponse>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<com.lzy.okgo.model.Response<CommonResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        apiResponse.doBefore(d);
                    }

                    @Override
                    public void onNext(com.lzy.okgo.model.Response<CommonResponse> commonResponse) {
                        CommonResponse body = commonResponse.body();
                        if(body.state){
                            apiResponse.onSucceed(body.returnJson);
                        }else{
                            apiResponse.onFailed(body.returnJson);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponse.onFailed("网络错误或后台错误");
                    }

                    @Override
                    public void onComplete() {
                        apiResponse.onComplete();
                    }
                });
    }


    private class CommonResponseConverter implements Converter<CommonResponse>{

        @Override
        public CommonResponse convertResponse(Response response) throws Throwable {
            ResponseBody body = response.body();
            if(body==null){
                CommonResponse commonResponse = new CommonResponse();
                commonResponse.state = false;
                commonResponse.returnJson = "接口未找到或404";
                return commonResponse;
            }
            return new Gson().fromJson(body.string(),CommonResponse.class);
        }
    }


    public interface ApiResponse{
        void doBefore(Disposable d);
        void onSucceed(String returnJson);
        void onFailed(String errorMsg);
        void onComplete();
    }
}
