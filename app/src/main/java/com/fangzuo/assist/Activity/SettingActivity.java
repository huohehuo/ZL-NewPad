package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.ConnectResponseBean;
import com.fangzuo.assist.MVContract.presenter.TestPresenter;
import com.fangzuo.assist.MVContract.view.TestView;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Server.WebAPI;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.CallBack;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.DataBaseAdapter;
import com.fangzuo.assist.Utils.DownLoadData;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.JsonCreater;
import com.fangzuo.assist.Utils.RetrofitUtil;
import com.fangzuo.assist.Utils.SnackBarUtil;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.greendao.gen.DaoMaster;
import com.fangzuo.greendao.gen.DaoSession;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;


public class SettingActivity extends BaseActivity implements TestView{

    private DataBaseAdapter adapter;
    private ListView mLvDataBase;
    private EditText mEtUserName;
    private EditText mEtPassword;
    private EditText mEtServerIP;
    private EditText mEtServerPort;
    private Button mBtnConn;
    private Button mBtnProp;
    private Button mBtnDownload;
    private SettingActivity mContext;
    private CommonListener commonListener;
    private Gson gson;
    private ProgressDialog pg;
    private ArrayList<ConnectResponseBean.DataBaseList> container;
    private BasicShareUtil share;
    private String chooseDatabase;
    private long nowTime;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private DaoSession session;
    private TestPresenter presenter;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    long nowTime = (long) msg.obj;
                    int size = msg.arg1;
                    long endTime = System.currentTimeMillis();
                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                    ab.setTitle("下载完成");
                    ab.setMessage("耗时:" + (endTime - nowTime) + "ms" + ",共插入" + size + "条数据");
                    ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startNewActivity(LoginActivity.class,R.anim.activity_slide_left_in,R.anim.activity_slide_left_out, true, null);
                        }
                    });
                    ab.create().show();
                    break;
            }
        }
    };
    private int size;
    private int flag=1;
    private CoordinatorLayout containerView;


    @Override
    public void initView() {
        setContentView(R.layout.activity_setting);
        containerView = findViewById(R.id.container);
        mLvDataBase = findViewById(R.id.lv_database);
        mEtUserName = findViewById(R.id.ed_username);
        mEtPassword = findViewById(R.id.ed_pass);
        mEtServerIP = findViewById(R.id.ed_serverip);
        mEtServerPort = findViewById(R.id.ed_port);
        mBtnConn = findViewById(R.id.btn_connect);
        mBtnProp = findViewById(R.id.btn_prop);
        mBtnDownload = findViewById(R.id.btn_download);

    }

    @Override
    public void initData() {
        mContext = this;
        gson = new Gson();
        presenter = new TestPresenter(this);
        container = new ArrayList<>();
        commonListener = new CommonListener();
        pg = new ProgressDialog(mContext);
        pg.setMessage("请稍后...");
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        share = BasicShareUtil.getInstance(mContext);
        if (!"".equals(share.getDatabaseIp())){
            mEtServerIP.setText(share.getDatabaseIp());
            mEtServerPort.setText(share.getDatabasePort());
            mEtUserName.setText(share.getDataBaseUser());
            mEtPassword.setText(share.getDataBasePass());
        }else{
            mEtServerIP.setText("192.168.0.172");
            mEtServerPort.setText("1433");
            mEtUserName.setText("sa");
            mEtPassword.setText("Abc123");
        }

        session = GreenDaoManager.getmInstance(mContext).getDaoSession();
        ab = new AlertDialog.Builder(mContext);
    }

    @Override
    public void initListener() {
        mBtnConn.setOnClickListener(commonListener);
        mBtnProp.setOnClickListener(commonListener);
        mBtnDownload.setOnClickListener(commonListener);
        mLvDataBase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    adapter.setIsCheck(i);
                    adapter.notifyDataSetChanged();
                }
                chooseDatabase = container.get(i).dataBaseName;
                Toast.showText(mContext, chooseDatabase);

            }
        });

    }

    @Override
    protected void OnReceive(String code) {

    }

    @Override
    public void getBackData(CommonResponse commonResponse, String type) {
        if (type.equals(Config.IO_type_SetProp)){
            if (isClearData){
                deleteData();
            }
            pg.dismiss();
            ab.setTitle("配置结果");
            ab.setMessage("配置成功，请继续下一步操作");
            ab.setPositiveButton("确认", null);
            ab.create().show();
            share.setVersion(commonResponse.returnJson);
            share.setDataBase(chooseDatabase);
        }else if (type.equals(Config.IO_type_connectToSQL)){
            share.setDatabaseIp(mEtServerIP.getText().toString());
            share.setDatabasePort(mEtServerPort.getText().toString());
            share.setDataBaseUser(mEtUserName.getText().toString());
            share.setDataBasePass(mEtPassword.getText().toString());
            pg.dismiss();
            ConnectResponseBean connectBean = gson.fromJson(commonResponse.returnJson, ConnectResponseBean.class);
            container.clear();
            ConnectResponseBean connectResponseBean = new ConnectResponseBean();
            ConnectResponseBean.DataBaseList dBean = connectResponseBean.new DataBaseList();
            dBean.name = "账套";
            dBean.dataBaseName = "数据库";
            container.add(dBean);
            container.addAll(connectBean.DataBaseList);
            adapter = new DataBaseAdapter(mContext, container);
            mLvDataBase.setAdapter(adapter);
            Toast.showText(mContext, "获取了" + connectBean.DataBaseList.size() + "条数据");
        }
    }

    @Override
    public void showError(String error, String type) {
        if (type.equals(Config.IO_type_SetProp)){
            pg.dismiss();
            ab.setTitle("配置结果");
            ab.setMessage(error);
            ab.setPositiveButton("确认", null);
            ab.setNegativeButton("重试", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    prop();
                }
            });
            ab.create().show();
        }else if (type.equals(Config.IO_type_connectToSQL)){
            pg.dismiss();
            SnackBarUtil.LongSnackbar(containerView, error, SnackBarUtil.Alert).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connectToSQL();
                }
            }).show();
        }
    }

    private class CommonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_connect:
                    connectToSQL();
                    break;
                case R.id.btn_prop:
                    prop();
                    break;
                case R.id.btn_download:
                    DownLoadData.getInstance(mContext,containerView,handler).alertToChoose();
                    break;
            }
        }
    }


    AlertDialog.Builder ab;
    private void prop() {
        if (null ==chooseDatabase){
            Toast.showText(mContext,"请选择数据库");
            return;
        }
        AlertDialog.Builder ab1 = new AlertDialog.Builder(mContext);
        ab1.setTitle("是否配置");
        ab1.setMessage("配置将会清空所有数据（包括已做单据）");
        ab1.setPositiveButton("清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setprop(true);
            }
        });
        ab1.setNeutralButton("不清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setprop(false);
            }
        });
        ab1.setNegativeButton("取消",null);

        ab1.create().show();

    }

    private boolean isClearData = false;
    private void setprop(final boolean isClear){
        pg.show();
        String json = JsonCreater.ConnectSQL(share.getDatabaseIp(), share.getDatabasePort(), share.getDataBaseUser(), share.getDataBasePass(), chooseDatabase);
        isClearData = isClear;
        presenter.SetProp(json);


//        RetrofitUtil.getInstance(mContext).createReq(WebAPI.class).SetProp(RetrofitUtil.getParams(mContext,json))
//                .enqueue(new CallBack() {
//                    @Override
//                    public void onSucceed(CommonResponse cBean) {
//                        if(isClear){
//                            deleteData();
//                        }
//                        pg.dismiss();
//                        ab.setTitle("配置结果");
//                        ab.setMessage("配置成功，请继续下一步操作");
//                        ab.setPositiveButton("确认", null);
//                        ab.create().show();
//                        share.setVersion(cBean.returnJson);
//                        share.setDataBase(chooseDatabase);
//                    }
//
//                    @Override
//                    public void OnFail(String Msg) {
//                        pg.dismiss();
//                        ab.setTitle("配置结果");
//                        ab.setMessage(Msg);
//                        ab.setPositiveButton("确认", null);
//                        ab.setNegativeButton("重试", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                prop();
//                            }
//                        });
//                        ab.create().show();
//                    }
//                });
    }
    private void deleteData() {
        session.getTempDetailDao().deleteAll();
        session.getChangePriceDao().detachAll();
        session.getBibieDao().deleteAll();
        session.getBarCodeDao().deleteAll();
        session.getT_DetailDao().deleteAll();
        session.getT_mainDao().deleteAll();
        session.getClientDao().deleteAll();
        session.getDepartmentDao().deleteAll();
        session.getEmployeeDao().deleteAll();
        session.getGetGoodsDepartmentDao().deleteAll();
        session.getInStorageNumDao().deleteAll();
        session.getInStoreTypeDao().deleteAll();
        session.getPayTypeDao().deleteAll();
        session.getPDMainDao().deleteAll();
        session.getPDSubDao().deleteAll();
        session.getPriceMethodDao().deleteAll();
        session.getProductDao().deleteAll();
        session.getPurchaseMethodDao().deleteAll();
        session.getPushDownMainDao().deleteAll();
        session.getPushDownSubDao().deleteAll();
        session.getStorageDao().deleteAll();
        session.getSuppliersDao().deleteAll();
        session.getUnitDao().deleteAll();
        session.getUserDao().deleteAll();
        session.getWanglaikemuDao().deleteAll();
        session.getWaveHouseDao().deleteAll();
        session.getYuandanTypeDao().deleteAll();
    }

    private void connectToSQL() {
        pg.show();
        String json = JsonCreater.ConnectSQL(mEtServerIP.getText().toString(), mEtServerPort.getText().toString(),
                mEtUserName.getText().toString(), mEtPassword.getText().toString(), Info.DATABASESETTING);

        presenter.connectToSQL(json);

//
//        Asynchttp.post(mContext,getBaseUrl()+WebApi.CONNECTSQL, json, new Asynchttp.Response() {
//
//
//            @Override
//            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
//                share.setDatabaseIp(mEtServerIP.getText().toString());
//                share.setDatabasePort(mEtServerPort.getText().toString());
//                share.setDataBaseUser(mEtUserName.getText().toString());
//                share.setDataBasePass(mEtPassword.getText().toString());
//                pg.dismiss();
//                ConnectResponseBean connectBean = gson.fromJson(cBean.returnJson, ConnectResponseBean.class);
//                container.clear();
//                ConnectResponseBean connectResponseBean = new ConnectResponseBean();
//                ConnectResponseBean.DataBaseList dBean = connectResponseBean.new DataBaseList();
//                dBean.name = "账套";
//                dBean.dataBaseName = "数据库";
//                container.add(dBean);
//                container.addAll(connectBean.DataBaseList);
//                adapter = new DataBaseAdapter(mContext, container);
//                mLvDataBase.setAdapter(adapter);
//                Toast.showText(mContext, "获取了" + connectBean.DataBaseList.size() + "条数据");
//            }
//
//            @Override
//            public void onFailed(String Msg, AsyncHttpClient client) {
//                pg.dismiss();
//                SnackBarUtil.LongSnackbar(containerView, Msg, SnackBarUtil.Alert).setAction("重试", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        connectToSQL();
//                    }
//                }).show();
//
//
//            }
//        });
    }


}
