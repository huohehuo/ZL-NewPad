package com.fangzuo.assist.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Beans.BlueToothBean;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.ThirdCheck;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.WebApi;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.lvrenyang.io.BTPrinting;
import com.lvrenyang.io.IOCallBack;
import com.lvrenyang.io.Pos;
import com.orhanobut.hawk.Hawk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OLinePrintActivity extends BaseActivity implements IOCallBack {

    @BindView(R.id.tv_print)
    TextView tvPrint;
    private ArrayList<FirstCheck> fContainer;
    private ArrayList<ThirdCheck> printDataList;
    private String TV;
    private String TR;
    private DecimalFormat df;
    private BroadcastReceiver broadcastReceiver = null;
    private IntentFilter intentFilter = null;
    ExecutorService es = Executors.newScheduledThreadPool(30);
    Pos mPos = new Pos();
    BTPrinting mBt = new BTPrinting();
    OLinePrintActivity mActivity;
    public static int nPrintWidth = 576;            //单据类型width
    public static boolean bCutter = false;          //切刀
    public static boolean bDrawer = false;          //钱箱
    public static boolean bBeeper = true;           //蜂鸣器
    public static int nPrintCount = 1;              //打印次数
    public static int nCompressMethod = 0;          //图形压缩
    public static boolean bAutoPrint = false;       //自动打印
    public static int nPrintContent = 2;            //打印内容，少量，中等，大量？
    public static boolean bCheckReturn = false;     //检查返回

    private boolean isConnect = false;
    private BlueToothBean bean;

    private String storageName="";
    private String json;
    private String client;
    private String vanNo;
    private String driver;
    private String printDate;
    private String orderID;
    private String maker;
    private String allV;
    private String allG;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_oline_print);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        nPrintCount = Integer.valueOf(Hawk.get(Config.Print_num_Str,"1"));
        mPos.Set(mBt);
        mBt.SetCallBack(this);

        Bundle b = getIntent().getExtras();
        df = new DecimalFormat("#0.000");
        json = b.getString("json");
        allG = b.getString("genshu");
        allV = b.getString("tiji");

//        btnPrint.setEnabled(false);
//        btnPrint.setText("正在初始化...");
        fContainer = new ArrayList<>();
        printDataList = new ArrayList<>();
        bean = Hawk.get(Config.OBJ_BLUETOOTH, new BlueToothBean("", ""));

        es.submit(new TaskOpen(mBt, bean.getAddress(), mActivity));
    }



    @Override
    protected void initData() {

        DownloadReturnBean dBean = new Gson().fromJson(json, DownloadReturnBean.class);
        if (dBean.thirdChecks.size() >= 1) {
            client      = dBean.thirdChecks.get(0).FClientName;
            vanNo       = dBean.thirdChecks.get(0).FVanNo;
            driver      = dBean.thirdChecks.get(0).FDriverName;
            printDate   = dBean.thirdChecks.get(0).FDate;
            storageName = dBean.thirdChecks.get(0).FStorageName;
            orderID     = dBean.thirdChecks.get(0).FOrderId;
            maker       = dBean.thirdChecks.get(0).FMaker;
            printDataList = dBean.thirdChecks;
            for (int i = 0; i < dBean.thirdChecks.size(); i++) {

                FirstCheck f = new FirstCheck();
                f.orderID       = dBean.thirdChecks.get(i).FOrderId;
                f.productname   = dBean.thirdChecks.get(i).productname;
                f.TRadical      = dBean.thirdChecks.get(i).radical;
                f.TVoleum       = dBean.thirdChecks.get(i).voleum;
                f.itemID        = dBean.thirdChecks.get(i).FInterID;
                f.model         = dBean.thirdChecks.get(i).model;
                f.diameter      = dBean.thirdChecks.get(i).diameter;
                f.length        = dBean.thirdChecks.get(i).length;
                fContainer.add(f);
            }
//        es.submit(new TaskPrint(mPos));
        }
    }

    @Override
    protected void initListener() {
//当断开连接时，重新扫描蓝牙，并自动连接打印机
//        btnTry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isConnect=false;
//                mActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mActivity.btnPrint.setText("正在重新连接");
//                        mActivity.btnTry.setVisibility(View.GONE);
//                    }
//                });
//                if (!isConnect){
//                    initWifiAndBT();
//
//                }
//            }
//        });
    }



    //打印短材
    private void printShort(Pos pos) {
        pos.POS_FeedLine();     //走纸一行
        pos.POS_S_Align(1);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
        pos.POS_S_TextOut("-------"+Hawk.get(Config.Print_Head_Text_Str)+"-------\n",
                0, 0, 1, 0, 0x00);
        pos.POS_FeedLine();     //走纸一行
        pos.POS_S_Align(0);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
        pos.POS_S_TextOut("仓库:" + storageName  + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("日期:" + printDate + "  客户:" + client + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("车    号：" + vanNo + "     ", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("司机姓名：" + driver + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("订单号：" + orderID + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("----------------------------\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("长度  径级  根数  |   长度  径级  根数 \n", 0, 0, 0, 0, 0x00);
        String A = "";
        String B = "";
        String C = "";
        Double e = 0d;
        int f = 0;
        Double g = 0d;
        int h = 0;
        for (int i = 0; i < printDataList.size(); i++) {
//            e += Double.parseDouble(fContainer.get(i).TVoleum);
//            g += Double.parseDouble(fContainer.get(i).TVoleum);
//            f += Integer.parseInt(fContainer.get(i).TRadical);
//            h += Integer.parseInt(fContainer.get(i).TRadical);
            ThirdCheck t_detail = printDataList.get(i);
//            A = t_detail.diameter + "            ";
//            A = A.substring(0, 3);
//            B = t_detail.TRadical + "            ";
//            B = B.substring(0, 3);
//            C = t_detail.length + "             ";
//            C = C.substring(0, 5);
            pos.POS_S_TextOut(printDataList.get(i).length + "    " + (int)Math.ceil(Double.parseDouble(printDataList.get(i).diameter)) + "    " + (int)Math.ceil(Double.parseDouble(printDataList.get(i).radical)) + "    |   ", 0, 0, 0, 0, 0x00);


            if (i % 2 != 0) {
                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
            }
            Double tj = 0d;
            int fa = 0;
            Double gs = 0d;
            int ha = 0;
            for (int j = 0; j < printDataList.size(); j++) {
                tj += Double.parseDouble(printDataList.get(j).voleum);
                gs += Double.parseDouble(printDataList.get(j).radical);
            }
            if (i < printDataList.size() - 2 && !printDataList.get(i).FInterID.equals(printDataList.get(i + 1).FInterID)) {
                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("体积：" + df.format(tj) + "  " + "根数： " + gs, 0, 0, 0, 0, 0x00);
                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
                e = 0d;
                f = 0;
            } else if (i == printDataList.size() - 1) {
                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
                pos.POS_FeedLine();
                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("体积：" + df.format(tj) + "  " + "根数：" + gs, 0, 0, 0, 0, 0x00);
                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
                e = 0d;
                f = 0;
            }
        }




//        for (int i = 0; i < fContainer.size(); i++) {
//            e += Double.parseDouble(fContainer.get(i).TVoleum);
//            g += Double.parseDouble(fContainer.get(i).TVoleum);
//            f += Integer.parseInt(fContainer.get(i).TRadical);
//            h += Integer.parseInt(fContainer.get(i).TRadical);
//            FirstCheck t_detail = fContainer.get(i);
//            A = t_detail.diameter + "            ";
//            A = A.substring(0, 3);
//            B = t_detail.TRadical + "            ";
//            B = B.substring(0, 3);
//            C = t_detail.length + "             ";
//            C = C.substring(0, 5);
//            pos.POS_S_TextOut(C + "  " + A + "  " + B + "    |   ", 0, 0, 0, 0, 0x00);
//
//
//            if (i % 2 != 0) {
//                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
//            }
//            if (i < fContainer.size() - 2 && !fContainer.get(i).itemID.equals(fContainer.get(i + 1).itemID)) {
//                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("体积：" + df.format(e) + "  " + "根数： " + f, 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
//                e = 0d;
//                f = 0;
//            } else if (i == fContainer.size() - 1) {
//                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
//                pos.POS_FeedLine();
//                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("体积：" + df.format(e) + "  " + "根数：" + f, 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
//                e = 0d;
//                f = 0;
//            }
//        }

        pos.POS_FeedLine();
        pos.POS_S_TextOut("总体积:" + df.format(Double.parseDouble(allV)) + " " + "总根数：" + allG, 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_S_TextOut("现场人员确认:" + maker + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_S_TextOut("客户确认", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_S_TextOut("客户电话：\n", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_S_TextOut("司机电话：", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_S_TextOut("销售电话："+Hawk.get(Config.Print_Phone_Text_Str)+"\n" , 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("地址："+Hawk.get(Config.Print_Adress_Text_Str), 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();


    }


    private void initWifiAndBT() {
        /* 启动WIFI */
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        switch (wifiManager.getWifiState()) {
            case WifiManager.WIFI_STATE_DISABLED:
                wifiManager.setWifiEnabled(true);
                break;
            default:
                break;
        }

        /* 启动蓝牙 */
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (null != adapter) {
            if (!adapter.isEnabled()) {
                if (!adapter.enable()) {
                    finish();
                    return;
                }
            }
        }
        if (!adapter.isEnabled()) {
            if (adapter.enable()) {
                while (!adapter.isEnabled())
                    ;
                Log.v(TAG, "Enable BluetoothAdapter");
            } else {
                finish();
            }
        }

        adapter.cancelDiscovery();
        adapter.startDiscovery();
        initBroadcast();
    }

    private void initBroadcast() {
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String action = intent.getAction();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    if (device == null)
                        return;
                    final String address = device.getAddress();
                    Log.e("获取address：", address);
                    String name = device.getName();
                    if (name == null)
                        name = "BT";
                    else if (name.equals(address))
                        name = "BT";
                    //获取本地保存的adress地址，若有，直接连接打印机
//                    bean = Hawk.get(Config.OBJ_BLUETOOTH, new BlueToothBean("", ""));
                    if (bean.getAddress().equals(address)) {
                        Log.e("蓝牙：", "查找到 打印机：" + address);
//                        android.widget.Toast.makeText(context, "正在连接打印机", Toast.LENGTH_SHORT).show();
//                        es.submit(new TaskOpen(mBt,address, mActivity));
                        es.submit(new TaskOpen(mBt, address, mActivity));
                        isConnect = true;
                        //es.submit(new TaskTest(mPos, mBt, address, mActivity));
                    }
                } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED
                        .equals(action)) {
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                        .equals(action)) {
                }

            }

        };
        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, intentFilter);
    }
    //连接打印机
    public class TaskOpen implements Runnable {
        BTPrinting bt = null;
        String address = null;
        Context context = null;

        public TaskOpen(BTPrinting bt, String address, Context context) {
            this.bt = bt;
            this.address = address;
            this.context = context;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            bt.Open(address, context);
//            bt.Listen(address,3000,context);
        }
    }

    //执行打印步骤
    static int dwWriteIndex = 1;

    public class TaskPrint implements Runnable {
        Pos pos = null;

        public TaskPrint(Pos pos) {
            this.pos = pos;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub

            final boolean bPrintResult = PrintTicket(
                    nPrintWidth,
                    bCutter,
                    bDrawer,
                    bBeeper,
                    nPrintCount,
                    nPrintContent,
                    nCompressMethod,
                    bCheckReturn);
            final boolean bIsOpened = pos.GetIO().IsOpened();

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(mActivity.getApplicationContext(), bPrintResult ? "打印成功" : "打印失败", Toast.LENGTH_SHORT).show();
                    mActivity.tvPrint.setText("打印成功");
                }
            });

        }


        public boolean PrintTicket(
                int nPrintWidth,
                boolean bCutter,
                boolean bDrawer,
                boolean bBeeper,
                int nPrintCount,
                int nPrintContent,
                int nCompressMethod,
                boolean bCheckReturn
        ) {
            boolean bPrintResult = false;

            byte[] status = new byte[1];
            if (!bCheckReturn || (bCheckReturn && pos.POS_QueryStatus(status, 3000, 2))) {

                for (int i = 0; i < nPrintCount; ++i) {
                    //如果已经连接到打印机
                    if (!pos.GetIO().IsOpened()) {
                        break;
                    }

                    //nOrgx:            指定x方向（水平）的起始点位置离右边界的点数
                    //nWidthTimes:      指定字符的宽度方向上的放大倍数  0-1
                    //nHeightTimes:     指定字符高度向上的放大倍数
                    //nFontType:        指定字符的字体类型  0x00 标准ASCII 12X24; 0x01 压缩ASCII 9X17
                    //nFontStyle:       指定字符的字体风格，可以多个 0x00正常,0x08加粗,0x80 1点粗下划线,
                    //                  0x100 2点粗下划线，0x400 反显，黑底白字，0x1000 每个字符顺时针旋转90度
                    if (nPrintContent >= 1) {
                        printShort(pos);

//                        pos.POS_FeedLine();     //走纸一行
//                        pos.POS_S_Align(1);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
//                        pos.POS_S_TextOut("====测试成功====\n",
//                                0, 1, 1, 0, 0x100);
//                        pos.POS_FeedLine();     //走纸一行
//                        pos.POS_FeedLine();     //走纸一行
                    }

                    if (bCheckReturn) {
                        int dwTicketIndex = dwWriteIndex++;
                        bPrintResult = pos.POS_TicketSucceed(dwTicketIndex, 30000);
                    } else {
                        bPrintResult = pos.GetIO().IsOpened();
                    }
                }

            }
            return bPrintResult;

        }
    }

    //关闭BTPrinting
    public class TaskClose implements Runnable {
        BTPrinting bt = null;

        public TaskClose(BTPrinting bt) {
            this.bt = bt;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            bt.Close();
        }

    }

    @Override
    public void OnOpen() {
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(mActivity, "打印机配置成功", Toast.LENGTH_SHORT).show();
//                mActivity.btnPrint.setText("确认单据并打印");
//                mActivity.btnTry.setVisibility(View.GONE);
                es.submit(new TaskPrint(mPos));

            }
        });
    }

    @Override
    public void OnOpenFailed() {
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(mActivity, "打印机配置失败", Toast.LENGTH_SHORT).show();
//                mActivity.btnPrint.setEnabled(false);
//                mActivity.btnPrint.setText("初始化打印机失败");
//                mActivity.btnTry.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void OnClose() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(mActivity, "已断开连接", Toast.LENGTH_SHORT).show();
//                mActivity.btnPrint.setEnabled(false);
//                mActivity.btnPrint.setText("打印机已断开");
//                mActivity.btnTry.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        es.submit(new TaskClose(mBt));

        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void OnReceive(String code) {

    }
}
