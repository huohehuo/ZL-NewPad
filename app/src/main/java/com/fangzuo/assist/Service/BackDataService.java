package com.fangzuo.assist.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.miniprinter.App;
import com.fangzuo.assist.Adapter.SearchAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Dao.Product;
import com.fangzuo.assist.RxSerivce.MySubscribe;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.greendao.gen.BackGoodsBeanDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.ProductDao;
import com.fangzuo.greendao.gen.TempDetailDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BackDataService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.fangzuo.assist.Service.action.FOO";
    private static final String ACTION_BAZ = "com.fangzuo.assist.Service.action.BAZ";
    private static final String ACTION_PUSH = "com.fangzuo.assist.Service.action.push";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.fangzuo.assist.Service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.fangzuo.assist.Service.extra.PARAM2";
    private Context mContext;

    public BackDataService() {
        super("BackDataService");
        mContext = App.getContext();
    }


    public static void getProducts(Context context) {
        Intent intent = new Intent(context, BackDataService.class);
        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void saveProductOfLong(Context context, String returnjson) {
        Intent intent = new Intent(context, BackDataService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, returnjson);
        context.startService(intent);
    }

    public static void push(Context context, String json) {
        Intent intent = new Intent(context, BackDataService.class);
        intent.setAction(ACTION_PUSH);
        intent.putExtra(EXTRA_PARAM1, json);
        context.startService(intent);
    }

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        daoSession = GreenDaoManager.getmInstance(App.getContext()).getDaoSession();
//        t_detailDao = daoSession.getT_DetailDao();
//        t_mainDao = daoSession.getT_mainDao();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo();
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionBaz(param1);
            }else if (ACTION_PUSH.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionPUSH(param1);
            }
        }
    }

    private void handleActionFoo() {
        App.getRService().getProducts("", new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                Lg.e("Service--searchProduct",commonResponse.returnJson);
                if (dBean.products.size() > 0) {
                    ProductDao productDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getProductDao();
                    productDao.deleteAll();
                    productDao.insertOrReplaceInTx(dBean.products);
                    productDao.detachAll();
                    Lg.e("ok");
                } else {
                    Toast.showText(mContext, "物料无数据");
                }
            }

            @Override
            public void onError(Throwable e) {
                //                Toast.showText(mContext, Msg);
                Lg.e("Service出错。。。"+e.toString());
            }
        });
    }

    private void handleActionBaz(String param1) {
        Product product = new Gson().fromJson(param1,Product.class);
        ProductDao productDao = daoSession.getProductDao();
        List<Product> products =productDao.queryBuilder().where(
                ProductDao.Properties.FItemID.eq(product.FItemID)
        ).build().list();
        if (products.size()==0){
            productDao.insert(product);
        }
    }
    private void handleActionPUSH(String json) {
        App.getRService().getChangePrice(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }
}
