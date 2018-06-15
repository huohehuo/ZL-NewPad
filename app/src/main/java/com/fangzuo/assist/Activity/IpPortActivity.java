package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.TimeBean;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IpPortActivity extends BaseActivity {


    @BindView(R.id.ed_ip)
    EditText edIp;
    @BindView(R.id.ed_port)
    EditText edPort;
    @BindView(R.id.btn_save)
    Button btnSave;
    private BasicShareUtil share;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_ip_port);
        mContext = this;
        ButterKnife.bind(this);
        share = BasicShareUtil.getInstance(mContext);
        if(!share.getIP().equals("")){
            edIp.setText(share.getIP());
        }

        if(!share.getPort().equals("")){
            edPort.setText(share.getPort());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btnSave.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String json = "http://"+share.getIP()+":"+share.getPort()+"/apk/assist.apk";

                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("设置默认打印次数");
                View v = LayoutInflater.from(mContext).inflate(R.layout.item_dg_print_num, null);
                final EditText etNum = v.findViewById(R.id.ed_num);
                etNum.setText(json);
                ab.setView(v);
                ab.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Asynchttp.post(mContext, share.getBaseURL() + "VersionSet", json, new Asynchttp.Response() {
                            @Override
                            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                                Toast.showText(mContext,"设置下载链接成功");
                            }

                            @Override
                            public void onFailed(String Msg, AsyncHttpClient client) {
                                Toast.showText(mContext, "设置下载链接失败："+Msg);
                            }
                        });
                    }
                });
                ab.setNegativeButton("取消",null);
                ab.setCancelable(false);
                ab.create().show();

                return false;
            }
        });
    }

    @Override
    protected void OnReceive(String code) {
        Toast.showText(mContext,code);
    }


    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        if(!edPort.getText().toString().equals("")&&!edIp.getText().toString().equals("")){
            share.setIP(edIp.getText().toString());
            share.setPort(edPort.getText().toString());
            finish();
        }
    }

}
