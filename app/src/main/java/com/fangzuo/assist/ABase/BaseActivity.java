package com.fangzuo.assist.ABase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.EventBusUtil;
import com.fangzuo.assist.Utils.ShareUtil;
import com.nineoldandroids.view.ViewHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NB on 2017/8/1.
 */

public abstract class BaseActivity extends FragmentActivity {
    private static final String ACTION_DISPLAY_SCAN_RESULT = "techain.intent.action.DISPLAY_SCAN_RESULT";
    public String TAG=getClass().getSimpleName();
    public Context mContext;
    public ShareUtil share;
    private IntentFilter scanDataIntentFilter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        share = ShareUtil.getInstance(mContext);
        registerBroadCast(mScanDataReceiver);
        initView();
        initData();
        initListener();
    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(ClassEvent event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    protected void receiveEvent(ClassEvent event) {

    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void OnReceive(String code);

    private BroadcastReceiver mScanDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.android.scanservice.scancontext")) {
                String str = intent.getStringExtra("Scan_context");
                OnReceive(str);
            }
        }
    };
    public void registerBroadCast(BroadcastReceiver mScanDataReceiver) {
        IntentFilter scanDataIntentFilter = new IntentFilter();
        scanDataIntentFilter.addAction("com.android.scanservice.scancontext");
        registerReceiver(mScanDataReceiver, scanDataIntentFilter);

    }


    public String getVersionName() {
        //androidmanifest中获取版本名称
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);

            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            System.out.println("versionName=" + versionName + ";versionCode="
                    + versionCode);

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }

        return "";
    }

    public int getVersionCode() {
        //androidmanifest中获取版本名称
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);

            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            System.out.println("versionName=" + versionName + ";versionCode="
                    + versionCode);

            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }

        return 0;
    }

    public final void startNewActivity(Class<? extends Activity> target,
                                       int enterAnim, int exitAnim, boolean isFinish, Bundle mBundle) {
        Intent mIntent = new Intent(this, target);
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        startActivity(mIntent);
        overridePendingTransition(enterAnim, exitAnim);
        if (isFinish) {
            finish();
        }
    }


    protected final void startNewActivityForResult(Class<? extends Activity> target, int enterAnim, int exitAnim, int requestCode, Bundle mBundle) {
        Intent mIntent = new Intent(this, target);
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        startActivityForResult(mIntent, requestCode);
        overridePendingTransition(enterAnim, exitAnim);
    }

    public void initDrawer(final DrawerLayout mDrawer) {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
        mDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawer.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float right = 0.8f + (1 - slideOffset) * 0.2f;


                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - scale;
                    ViewHelper.setScaleX(mMenu, leftScale);//drawer
                    ViewHelper.setScaleY(mMenu, leftScale);//drawer
                    ViewHelper.setAlpha(mMenu, 0.8f + 0.2f * (leftScale));
                    ViewHelper.setTranslationX(mContent, (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, right);
                    ViewHelper.setScaleY(mContent, right);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    public void setfocus(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.findFocus();
    }

    public String getTime(boolean b) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(b ? "yyyy-MM-dd" : "yyyyMMdd");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }

    public String getTimesecond() {
        Date curDate = new Date();
        Long time = curDate.getTime();
        return time + "";
    }

    public String getBaseUrl() {
        return BasicShareUtil.getInstance(mContext).getBaseURL();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScanDataReceiver != null) {
            unregisterReceiver(mScanDataReceiver);
            Log.e("OndestoryBase", "onDestroy");
        }
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }
}
