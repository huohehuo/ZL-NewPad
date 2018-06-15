package com.fangzuo.assist.Activity;

import android.posapi.PosApi;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.fangzuo.assist.ABase.BaseActivity;
import com.example.miniprinter.App;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.PrintQueue;
import com.fangzuo.assist.Utils.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrintTestActivity extends BaseActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.print)
    Button print;
    private PosApi mPosApi;
    private PrintQueue mPrintQueue;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_print_test);
//Power on
        ButterKnife.bind(this);

        mContext = this;
        try {
            FileWriter localFileWriterOn = new FileWriter(new File("/proc/gpiocontrol/set_sam"));
            localFileWriterOn.write("1");
            localFileWriterOn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //init
        mPosApi = App.getInstance().getPosApi();
        mPosApi.setOnComEventListener(mCommEventListener);
        //get interface
        mPosApi.initDeviceEx("/dev/ttyMT2");
        mPrintQueue = new PrintQueue(this, mPosApi);
        mPrintQueue.init();
        mPrintQueue.setOnPrintListener(new PrintQueue.OnPrintListener() {
            @Override
            public void onGetState(int state) {
                switch (state) {
                    case 0:
                        Toast.showText(mContext, "有纸");

                        break;
                    case 1:

                        Toast.showText(mContext, "没有纸");

                        break;

                }
            }

            @Override
            public void onPrinterSetting(int state) {
                switch (state) {
                    case 0:
                        Toast.showText(mContext, "有纸");
                        break;
                    case 1:
                        Toast.showText(mContext, "没有纸");
                        break;
                    case 2:
                        Toast.showText(mContext, "Detected black mark");
                        break;
                }
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                Toast.showText(mContext, "打印完成");
            }

            @Override
            public void onFailed(int state) {
                // TODO Auto-generated method stub
                switch (state) {
                    case PosApi.ERR_POS_PRINT_NO_PAPER:
                        Toast.showText(mContext, "打印失败，错误原因:无纸");
                        break;
                    case PosApi.ERR_POS_PRINT_FAILED:
                        Toast.showText(mContext, "打印失败，错误原因:未知");
                        break;
                    case PosApi.ERR_POS_PRINT_VOLTAGE_LOW:
                        Toast.showText(mContext, "打印失败，错误原因:低电压");
                        break;
                    case PosApi.ERR_POS_PRINT_VOLTAGE_HIGH:
                        Toast.showText(mContext, "打印失败，错误原因:高电压");
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void OnReceive(String code) {

    }

    PosApi.OnCommEventListener mCommEventListener = new PosApi.OnCommEventListener() {
        @Override
        public void onCommState(int cmdFlag, int state, byte[] resp, int respLen) {
            // TODOuto- Agenerated method stub
            switch (cmdFlag) {
                case PosApi.POS_INIT:
                    if (state == PosApi.COMM_STATUS_SUCCESS) {
                        Toast.showText(mContext, "打印机初始化成功");
                    } else {
                        Toast.showText(mContext, "打印机初始化失败");
                    }
                    break;
            }
        }


    };

    private void addPrintTextWithSize(int size, int concentration, byte[] data) {
        if (data == null)
            return;
        // 2 size Font
        byte[] _2x = new byte[]{0x1b, 0x57, 0x02};
        // 1 size Font
        byte[] _1x = new byte[]{0x1b, 0x57, 0x01};
        byte[] mData = null;
        if (size == 1) {
            mData = new byte[3 + data.length];
            System.arraycopy(_1x, 0, mData, 0, _1x.length);
            System.arraycopy(data, 0, mData, _1x.length, data.length);
            mPrintQueue.addText(concentration, mData);
        } else if (size == 2) {
            mData = new byte[3 + data.length];
            System.arraycopy(_2x, 0, mData, 0, _2x.length);
            System.arraycopy(data, 0, mData, _2x.length, data.length);
            mPrintQueue.addText(concentration, mData);
        }

    }

    private void printTextWithInput() {
        try {
            int concentration = 60;
            if (TextUtils.isEmpty(et.getText().toString())) {
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(et.getText());
            sb.append("\n");
            byte[] text = null;
            text = sb.toString().getBytes("GBK");

            addPrintTextWithSize(1, concentration, text);

            mPrintQueue.printStart();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mPosApi != null) {
            mPosApi.closeDev();
        }
        try {
            FileWriter localFileWriterOn = new FileWriter(new File("/proc/gpiocontrol/set_sam"));
            localFileWriterOn.write("0");
            localFileWriterOn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.print)
    public void onViewClicked() {
        printTextWithInput();
    }
}
