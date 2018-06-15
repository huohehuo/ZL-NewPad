package com.fangzuo.assist.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.miniprinter.App;
import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.LoginSpAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Dao.User;
import com.fangzuo.assist.R;
import com.fangzuo.assist.RxSerivce.MySubscribe;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.JsonCreater;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.ShareUtil;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.assist.widget.LoadingUtil;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.UserDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {


    private Button mBtnLogin;
    private Button mBtnSetting;
    private LoginActivity mContext;
    private CommonListener commonListener;
    @BindView(R.id.sp_login)Spinner spinner;
    private DaoSession session;
    private String userName="";
    private String userID="";
    private List<User> users;
    private CheckBox mCbisOL;
    private BasicShareUtil share;
    private boolean isOL;
    private String userPass;
    private EditText edPass;
    private EditText mEtPassword;
    private TextView mTvVersion;
    LoginSpAdapter ada;
    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        share = BasicShareUtil.getInstance(mContext);
        mEtPassword = findViewById(R.id.ed_pass);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnSetting = findViewById(R.id.btn_setting);
        mCbisOL = findViewById(R.id.isOL);
        mTvVersion = findViewById(R.id.ver);
        getPermisssion();
        getListUser();
        mTvVersion.setText("ZL for Pad Ver:"+getVersionName());
    }

    private void getListUser() {
        final Gson gson = new Gson();
        final ProgressDialog pg = new ProgressDialog(mContext);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在预加载..请稍后...");
        ArrayList<Integer> chooseAll = new ArrayList<>();
        chooseAll.add(12);
        pg.show();
        String json = JsonCreater.DownLoadDataWithIp(share,chooseAll);
        App.getRService().downloadData(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                Lg.e("user:"+commonResponse.returnJson);
                DownloadReturnBean dBean = gson.fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                UserDao userDao = session.getUserDao();
                userDao.deleteAll();
                userDao.detachAll();
                userDao.insertInTx(dBean.User);
                pg.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                pg.dismiss();
            }
        });
    }

    @Override
    public void initData() {
        commonListener = new CommonListener();
        getUserInfo();
//        mCbisOL.setChecked(BasicShareUtil.getInstance(mContext).getIsOL());
        mCbisOL.setChecked(Hawk.get(Config.IsOnLine,false));
    }

    private void getUserInfo() {
        session = GreenDaoManager.getmInstance(mContext).getDaoSession();
        UserDao userDao = session.getUserDao();
        users = userDao.loadAll();
        ada = new LoginSpAdapter(mContext, users);
        spinner.setAdapter(ada);
        ada.notifyDataSetChanged();
        if(users.size()>0){
            userName = users.get(0).FName;
            userID = users.get(0).FUserID;
            ShareUtil.getInstance(mContext).setUserName(userName);
            ShareUtil.getInstance(mContext).setUserID(userID);
            Hawk.put(Config.UserName,userName);
            Hawk.put(Config.UserId,userID);
        }

    }

    @Override
    public void initListener() {
        mCbisOL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isOL = b;
                Hawk.put(Config.IsOnLine,b);
                BasicShareUtil.getInstance(mContext).setIsOL(b);
                session = GreenDaoManager.getmInstance(mContext).getDaoSession();
                UserDao userDao = session.getUserDao();
                users = userDao.loadAll();
                ada = new LoginSpAdapter(mContext, users);
                spinner.setAdapter(ada);
                ada.notifyDataSetChanged();
            }
        });
        mBtnLogin.setOnClickListener(commonListener);
        mBtnSetting.setOnClickListener(commonListener);
        mBtnSetting.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setPositiveButton("确定",null);
                ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startNewActivity(TestActivity.class,R.anim.activity_slide_left_in,R.anim.activity_slide_left_out,false,null);
                    }
                });
                ab.create().show();
                return false;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("login",ada.getItem(i).toString());
                User user =(User)ada.getItem(i);
                if ("Administrator".equals(user.FName)){
                    Lg.e("超级权限用户");
                    Hawk.put(Config.USER_PERMIT,"1-2-3-4-5-6-7-8-9");
                }else{
                    Hawk.put(Config.USER_PERMIT,user.FPermit);
                }
//                Hawk.put(Config.USER_PERMIT,user.FPermit);

                userName = users.get(i).FName;
                userID = users.get(i).FUserID;
                userPass = users.get(i).FPassWord;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void OnReceive(String code) {

    }




    private class CommonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_login:
                    if (!"000".equals(Hawk.get(Config.OrderCode_PdaNo_s,"000"))){
                        Login();
                    }else{
                        Toast.showText(mContext,"不存在PDA唯一码，请卸载重装，并重新注册");
                    }
                    break;
                case R.id.btn_setting:
                    Bundle b = new Bundle();
                    b.putInt("flag",0);
                    startNewActivity(SettingMenuActivity.class,R.anim.activity_slide_left_in,R.anim.activity_slide_left_out,true,null);
                    break;
            }
        }
    }

    private void Login() {
        if(!userID.equals("")&&!userName.equals("")){
            if(mEtPassword.getText().toString().equals(userPass)){
                ShareUtil.getInstance(mContext).setUserName(userName);
                ShareUtil.getInstance(mContext).setUserID(userID);
                startNewActivity(MenuActivity.class,R.anim.activity_slide_left_in,R.anim.activity_slide_left_out,false,null);
            }

        }
    }



    private void getPermisssion() {
        String[] perm = {
                android.Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if(!EasyPermissions.hasPermissions(mContext,perm)){
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perm);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i("permisssion", "获取成功的权限" + perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i("permisssion", "获取失败的权限" + perms);
    }


}
