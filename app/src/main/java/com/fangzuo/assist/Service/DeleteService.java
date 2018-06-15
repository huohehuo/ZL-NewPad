package com.fangzuo.assist.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.miniprinter.App;
import com.fangzuo.assist.Dao.BackGoodsBean;
import com.fangzuo.assist.Dao.TempDetail;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.greendao.gen.BackGoodsBeanDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.TempDetailDao;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DeleteService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.fangzuo.assist.Service.action.FOO";
    private static final String ACTION_BAZ = "com.fangzuo.assist.Service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.fangzuo.assist.Service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.fangzuo.assist.Service.extra.PARAM2";

    public DeleteService() {
        super("DeleteService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void deleteTempDetailByTag(Context context, int activity) {
        Intent intent = new Intent(context, DeleteService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, activity);
//        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void deleteBackGoodsByTag(Context context, int activity) {
        Intent intent = new Intent(context, DeleteService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, activity);
//        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final int param1 = intent.getIntExtra(EXTRA_PARAM1,0);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1);
            } else if (ACTION_BAZ.equals(action)) {
                final int param1 = intent.getIntExtra(EXTRA_PARAM1,0);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1);
            }
        }
    }

    private TempDetailDao tempDetailDao;
    private DaoSession daoSession;
    private BackGoodsBeanDao backGoodsBeanDao;

    @Override
    public void onCreate() {
        super.onCreate();
        daoSession = GreenDaoManager.getmInstance(App.getContext()).getDaoSession();
//        t_detailDao = daoSession.getT_DetailDao();
//        t_mainDao = daoSession.getT_mainDao();
    }

    private void handleActionFoo(int activity) {
        tempDetailDao = daoSession.getTempDetailDao();
        daoSession.getTempDetailDao()
                .deleteInTx(tempDetailDao.queryBuilder().where(
                TempDetailDao.Properties.Activity.eq(activity)
        ).build().list());
        Lg.e("Service","删除了TempDetail");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(int tag) {
        backGoodsBeanDao = daoSession.getBackGoodsBeanDao();
        daoSession.getBackGoodsBeanDao().deleteInTx(backGoodsBeanDao.queryBuilder().where(
                BackGoodsBeanDao.Properties.Activity.eq(tag)
        ).build().list());
        Lg.e("Service","删除了BackGoods");

    }
}
