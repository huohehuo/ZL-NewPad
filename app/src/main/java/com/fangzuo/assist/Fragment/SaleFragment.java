package com.fangzuo.assist.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fangzuo.assist.ABase.BaseFragment;
import com.fangzuo.assist.Activity.AccountCheckActivity;
import com.fangzuo.assist.Activity.ChangePriceActivity;
import com.fangzuo.assist.Activity.CheckOLineActivity;
import com.fangzuo.assist.Activity.DetailsQueryActivity;
import com.fangzuo.assist.Activity.LongTreeOutLForRedActivity;
import com.fangzuo.assist.Activity.ShortTreeOutForRedActivity;
import com.fangzuo.assist.Activity.SoldOutActivity;
import com.fangzuo.assist.Activity.StorageCheckActivity;
import com.fangzuo.assist.Activity.ShortTreeOutActivity;
import com.fangzuo.assist.Activity.LongTreeOutLActivity;
import com.fangzuo.assist.Adapter.GridViewAdapter;
import com.fangzuo.assist.Beans.SettingList;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.GetSettingList;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Lg;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SaleFragment extends BaseFragment {
    @BindView(R.id.gv)
    GridView gv;
    Unbinder unbinder;
    private FragmentActivity mContext;

    public SaleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sale, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void initView() {
        mContext = getActivity();
    }

    @Override
    protected void OnReceive(String barCode) {

    }
    GridViewAdapter ada;
    @Override
    protected void initData() {
        String getPermit= Hawk.get(Config.USER_PERMIT,"");
        Lg.e("listitem"+getPermit);
        String[] aa = getPermit.split("\\-"); // 这样才能得到正确的结果
        for (int i = 0; i < aa.length; i++) {
            Log.e("test",aa[i]);
        }
        ada = new GridViewAdapter(mContext, GetSettingList.getSaleList(aa));
        gv.setAdapter(ada);
        ada.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SettingList tv= (SettingList) ada.getItem(i);
                Log.e("listitem",tv.tv);
                switch (tv.tag){
                    case "1":
                        startNewActivity(ShortTreeOutActivity.class,null);
                        break;
                    case "2":
                        startNewActivity(LongTreeOutLActivity.class,null);
                        break;
                    case "3":
                        startNewActivity(ShortTreeOutForRedActivity.class,null);
                        break;
                    case "4":
                        startNewActivity(LongTreeOutLForRedActivity.class,null);
                        break;
                    case "5":
                        startNewActivity(StorageCheckActivity.class,null);
                        break;
                    case "6":
                        startNewActivity(CheckOLineActivity.class,null);
                        break;
                    case "7":
                        startNewActivity(ChangePriceActivity.class,null);
                        break;
                    case "8":
                        startNewActivity(DetailsQueryActivity.class,null);
                        break;
                    case "9":
                        startNewActivity(AccountCheckActivity.class,null);
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
