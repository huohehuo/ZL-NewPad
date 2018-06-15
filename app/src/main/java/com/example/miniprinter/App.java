package com.example.miniprinter;

import android.app.Application;
import android.content.Context;
import android.posapi.PosApi;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.fangzuo.assist.Activity.Crash.CrashHandler;
import com.fangzuo.assist.RxSerivce.RService;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.orhanobut.hawk.Hawk;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 王璐阳 on 2018/1/9.
 */


public class App extends MultiDexApplication {
    private static Context mContext;
    private String mCurDev = "";
    public static boolean isDebug=true;
    static App instance = null;

    private static OkHttpClient           okHttpClient;
    private static okhttp3.logging.HttpLoggingInterceptor interceptor;
    private static Gson gson;

    private static Retrofit retrofit;
    public static String NowUrl;
    public static boolean isChangeIp=false;
    private static RService mService;//本地retrofit方法
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //PosSDK mSDK = null;
//    PosApi mPosApi = null;
    public App(){
        super.onCreate();
        instance = this;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        mContext = this;
//        mPosApi = PosApi.getInstance(this);
        initOkGo();
        Hawk.init(mContext).build();
//        Fragmentation.builder()
//                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(BuildConfig.DEBUG)
//                .install();


        NowUrl = BasicShareUtil.getInstance(mContext).getBaseURL();
        //retrofit的基本初始化相关
        gson = new Gson();
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(5000, TimeUnit.SECONDS);
//        builder.readTimeout(20, TimeUnit.SECONDS);
//        builder.writeTimeout(20, TimeUnit.SECONDS);
//        builder.retryOnConnectionFailure(true);
        interceptor = new okhttp3.logging.HttpLoggingInterceptor();
        interceptor.setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .build();

        //这里的baseurl,注意要有实际格式的链接，不然会崩
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BasicShareUtil.getInstance(mContext).getBaseURL())
                .build();
        mService = new RService();


    }
    public static  App getInstance(){
        if(instance==null){
            instance =new App();
        }
        return instance;
    }
    public static Context getContext(){
        return mContext;
    }

    public String getCurDevice() {
        return mCurDev;
    }

    public void setCurDevice(String mCurDev) {
        this.mCurDev = mCurDev;
    }

    public PosApi getPosApi(){
//        return mPosApi;
        return null;
    }
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);



        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this)                 //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为;

    }


    //获取Service对象，当ip发生变化时，更换Serivce对象
    public static RService getRService() {
        if (TextUtils.equals(BasicShareUtil.getInstance(App.getContext()).getBaseURL(),App.NowUrl)){
            isChangeIp=false;
            return mService;
        }else{
            isChangeIp=true;
            RService mSerivce = new RService();
            setRService(mSerivce);
            String changeUrl=BasicShareUtil.getInstance(App.getContext()).getBaseURL();
            App.NowUrl=changeUrl;
            return mSerivce;
        }
    }

    public static void setRService(RService mService) {
        App.mService = mService;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        App.retrofit = retrofit;
    }
    public static OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

}
