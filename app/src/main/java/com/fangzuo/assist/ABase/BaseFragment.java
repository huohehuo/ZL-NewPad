package com.fangzuo.assist.ABase;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fangzuo.assist.Utils.ShareUtil;

/**
 * Created by NB on 2017/7/28.
 */

public abstract class BaseFragment extends Fragment {
    public String TAG=getClass().getSimpleName();
    public ShareUtil share;
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        FragmentActivity mContext = getActivity();
        super.onCreate(savedInstanceState);
        share = ShareUtil.getInstance(mContext);
        registerBroadCast(mScanDataReceiver);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initListener();
    }
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    protected abstract void OnReceive(String barCode);
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
        getActivity().registerReceiver(mScanDataReceiver, scanDataIntentFilter);

    }

    public final void startNewActivity(Class<? extends Activity> target,
                                       Bundle mBundle) {
        Intent mIntent = new Intent(getActivity(), target);
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        startActivity(mIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mScanDataReceiver);
    }
}
