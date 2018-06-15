package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miniprinter.App;
import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.TimeBean;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.CommonUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.MD5;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.hawk.Hawk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.ipchange)
    Button ipchange;
    private SplashActivity mContext;
    @BindView(R.id.iv_try)
    ImageView mIvTry;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    private BasicShareUtil instance;
    private long serverTime;
    private String register_code;
    private String newRegister;
    private String lastRegister;

    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);
        mContext = this;
        ButterKnife.bind(mContext);

        if (getNewMac() != null && !getNewMac().equals("")) {
            tvCode.setText("注册码：" + MD5.getMD5(getNewMac()));
            Lg.e("注册码："+ MD5.getMD5(getNewMac()));
            register_code = MD5.getMD5(getNewMac()) + "fzkj601";
            newRegister = MD5.getMD5(register_code);
            lastRegister = MD5.getMD5(newRegister);
        } else {
            Toast.showText(mContext, "请链接WIFI");
        }


    }

    private void RegisterState() {
        Asynchttp.post(mContext, getBaseUrl() + WebApi.REGISTER, lastRegister, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                Lg.e("register:"+cBean.returnJson);
                DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                if (null!=dBean.registers.get(0).PdaNo && "".equals(dBean.registers.get(0).PdaNo)){
                        getPdaNo();
                }else{
                    CommonUtil.setPdaNo(dBean.registers.get(0).PdaNo);
                    instance.setRegisterState(true);
                    startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);

                } }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext, "请联系软件供应商注册"+Msg);

//                if (Msg.equals("1")) {
//                    instance.setRegisterState(false);
//                } else {
//                    startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);
//                }
            }
        });
    }

    private void getPdaNo(){
        Asynchttp.post(mContext, getBaseUrl() + WebApi.GetPdaNo, lastRegister, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                Lg.e("register_getPdaNo:"+cBean.returnJson);
                DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                if (null!=dBean.registers.get(0).PdaNo){
                    CommonUtil.setPdaNo(dBean.registers.get(0).PdaNo);
                    instance.setRegisterState(true);
                    startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);
                }else{
                    Toast.showText(mContext,"获取唯一码失败，请退出重进");
                }
            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext,"获取PDA唯一编码失败"+Msg);
            }
        });
    }


    @Override
    public void initData() {
        instance = BasicShareUtil.getInstance(mContext);
        CheckServer();
        getServerTime();
        if (serverTime > instance.getTryTime()) {
            instance.setTryState(2);
        }

        if ("000".equals(Hawk.get(Config.OrderCode_PdaNo_s,"000"))){
            RegisterState();
        }else{
            startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);
        }
//        if (instance.getRegisterState()) {
//            RegisterState();
//        }
//        if (instance.getRegisterState()) {
//            Log.e("RegisterState", instance.getRegisterState() + "");
//            startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);
//        } else if (instance.getTryState() == 1) {
//            Log.e("TryState", instance.getTryState() + "");
//            startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);
//        }

    }


    @Override
    public void initListener() {
        ipchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(IpPortActivity.class,0,0,false,null);
            }
        });
    }

    @Override
    protected void OnReceive(String code) {

    }


    @OnClick(R.id.btn_register)
    public void Register(View view) {
        instance.setMAC(lastRegister);
        Asynchttp.post(mContext, getBaseUrl() + WebApi.REGISTER, lastRegister, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                Lg.e("register:"+cBean.returnJson);
                DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                if (null==dBean.registers.get(0).PdaNo || "".equals(dBean.registers.get(0).PdaNo)){
                    getPdaNo();
                }else{
                    CommonUtil.setPdaNo(dBean.registers.get(0).PdaNo);
                    instance.setRegisterState(true);
                    Toast.showText(mContext, "注册成功");
                    startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);
                }}

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext, Msg);
                if (Msg.equals("1")) {
                    Toast.showText(mContext, "请联系软件供应商注册");
                }
            }
        });
    }

    @OnClick(R.id.iv_try)
    public void getTry(View view) {
        if (instance.getTryState() == 0) {
            getServerTime();
            if (getFileFromSD().equals("")) {
                long tryTime = serverTime + 2592000000L;
                writeSdCard("1");
                instance.setTryTime(tryTime);
                instance.setTryState(1);
                startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);
            } else if (getFileFromSD().equals("1")) {
                Toast.showText(mContext, "每台机器只能申请一次试用");
            }
        } else if (instance.getTryState() == 2) {
            Toast.showText(mContext, "每台机器只能申请一次试用");
        }
    }

    private void getServerTime() {
        Asynchttp.post(mContext, instance.getBaseURL() + "GetServerTime", "", new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                TimeBean tBean = new Gson().fromJson(cBean.returnJson, TimeBean.class);
                serverTime = tBean.time;
            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext, Msg);
            }
        });
    }

    private static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private void CheckServer() {
        if ((instance.getIP()).equals("") || (instance.getPort()).equals("")) {
            AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
            ab.setTitle("未获取到服务器,请输入服务器地址");
            View v = LayoutInflater.from(mContext).inflate(R.layout.ipport, null);
            final EditText mEdIp = v.findViewById(R.id.ed_ip);
            final EditText mEdPort = v.findViewById(R.id.ed_port);

            mEdIp.setText("192.168.0.19");
            mEdPort.setText("8080");

            ab.setView(v);
            ab.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!(mEdIp.getText().toString()).equals("") && !(mEdPort.getText().toString()).equals("")) {
                        instance.setIP(mEdIp.getText().toString());
                        instance.setPort(mEdPort.getText().toString());
                    } else {
                        System.exit(0);
                    }
                }
            });
            ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.exit(0);
                }
            });
            ab.setCancelable(false);
            ab.create().show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void writeSdCard(String person) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "json"
                + File.separator + "0x8b69a33.txt");
        // 文件夹不存在的话，就创建文件夹
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 写入内存卡
        PrintStream outputStream = null;
        try {
            outputStream = new PrintStream(new FileOutputStream(file));
            outputStream.print(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private String getFileFromSD() {
        String result = "";
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "json"
                + File.separator + "0x8b69a33.txt");
        if (file.exists()) {
            try {
                FileInputStream f = new FileInputStream(file);
                BufferedReader bis = new BufferedReader(new InputStreamReader(f));
                String line = "";
                while ((line = bis.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;

    }


}
