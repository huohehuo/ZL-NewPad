package com.fangzuo.assist.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Config;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrintSetActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_head)
    EditText etHead;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_adress)
    EditText etAdress;
    @BindView(R.id.et_other_text)
    EditText etOther;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_print_set);
        ButterKnife.bind(this);
        tvTitle.setText("打印文本设置");
        tvRight.setText("保存");
        if ("".equals(Hawk.get(Config.Print_Other_Text_Str, ""))){
            etOther.setText("1.若双方未签订购销协议，本销售结算单与购销协议具有同等法律效力。\n" +
                    "2.数量以买卖双方代表现场验收检尺为准，自买方代表签字后即视为验收合格，不得退货、返货。\n" +
                    "3.付款时间：买方应在本结算单签字后，当日内付清该批货款。\n" +
                    "4.若买方未按时付款，自逾期之日起， 每日按总金额的 2% 向卖方支付违约金。\n" +
                    "5.若双方发生纠纷，由卖方所在地人民法院解决。\n");
            Hawk.put(Config.Print_Other_Text_Str, etOther.getText().toString());
        }
    }

    @Override
    protected void initData() {
        etHead.setText(Hawk.get(Config.Print_Head_Text_Str, "广东中梁客户确认单"));
        etPhone.setText(Hawk.get(Config.Print_Phone_Text_Str, "潘生 13811719983"));
        etAdress.setText(Hawk.get(Config.Print_Adress_Text_Str, "沙田大道与进港南路交叉口北200米"));
        etOther.setText(Hawk.get(Config.Print_Other_Text_Str, ""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Hawk.put(Config.Print_Head_Text_Str, etHead.getText().toString());
        Hawk.put(Config.Print_Phone_Text_Str, etPhone.getText().toString());
        Hawk.put(Config.Print_Adress_Text_Str, etAdress.getText().toString());
        Hawk.put(Config.Print_Other_Text_Str, etOther.getText().toString());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void OnReceive(String code) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_back,R.id.tv_right})
    public void onClick() {
        finish();
    }
}
