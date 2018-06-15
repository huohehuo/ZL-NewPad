package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fangzuo.assist.Adapter.BTAdapter;
import com.fangzuo.assist.Beans.BlueToothBean;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Config;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lvrenyang.io.BTPrinting;
import com.lvrenyang.io.IOCallBack;
import com.lvrenyang.io.Pos;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrintOutTestActivity extends AppCompatActivity implements View.OnClickListener, IOCallBack {

    @BindView(R.id.tv_bluetooth)
    TextView tvBluetooth;
    @BindView(R.id.ry_bluetooth)
    EasyRecyclerView ryBl;

    @BindView(R.id.buttonPrint)
    Button btnPrint;
    @BindView(R.id.ll)
    LinearLayout ll;
    private BroadcastReceiver broadcastReceiver = null;
    private IntentFilter intentFilter = null;

    PrintOutTestActivity mActivity;

    ExecutorService es = Executors.newScheduledThreadPool(30);
    Pos mPos = new Pos();
    BTPrinting mBt = new BTPrinting();

    private static String TAG = "SearchBTActivity";

    public static int nPrintWidth = 576;            //单据类型width
    public static boolean bCutter = false;          //切刀
    public static boolean bDrawer = false;          //钱箱
    public static boolean bBeeper = true;           //蜂鸣器
    public static int nPrintCount = 1;              //打印次数
    public static int nCompressMethod = 0;          //图形压缩
    public static boolean bAutoPrint = false;       //自动打印
    public static int nPrintContent = 2;            //打印
    public static boolean bCheckReturn = false;     //检查返回
    private BTAdapter btAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_out_test);
        ButterKnife.bind(this);

        mActivity = this;
        nPrintCount = Integer.valueOf(Hawk.get(Config.Print_num_Str,"1"));
        ryBl.setAdapter(btAdapter = new BTAdapter(this));
        ryBl.setLayoutManager(new LinearLayoutManager(this));
//            ryBl.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
//        ryBl.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        btAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                es.submit(new TaskOpen(mBt, btAdapter.getAllData().get(position).getAddress(), mActivity));
                Toast.makeText(mActivity, "正在配置:"+btAdapter.getAllData().get(position).getAddress(), Toast.LENGTH_SHORT).show();
                Hawk.put(Config.OBJ_BLUETOOTH,btAdapter.getAllData().get(position));
            }
        });

        ryBl.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                btAdapter.removeAll();
                //检测是否开启蓝牙
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (null == adapter) {
                    finish();
                    ryBl.setRefreshing(false);
                    return;
                }
                if (!adapter.isEnabled()) {
                    if (adapter.enable()) {
                        while (!adapter.isEnabled())
                            ;
                        Log.v(TAG, "Enable BluetoothAdapter");
                    } else {
                        finish();
                        ryBl.setRefreshing(false);
                    }
                }

                adapter.cancelDiscovery();
                adapter.startDiscovery();
                ryBl.setRefreshing(false);
            }
        });

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

        btnPrint.setEnabled(false);

        mPos.Set(mBt);
        mBt.SetCallBack(this);

        BlueToothBean bean=Hawk.get(Config.OBJ_BLUETOOTH,new BlueToothBean("",""));
        tvBluetooth.setText("已配置的打印机\n"+"名称："+bean.getName()+"\n地址："+bean.getAddress());
        initBroadcast();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uninitBroadcast();
        es.submit(new TaskClose(mBt));
    }

//    public void onClick(View arg0) {
//        // TODO Auto-generated method stub
//        switch (arg0.getId()) {
////            case R.id.buttonSearch: {
////                //检测是否开启蓝牙
////                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
////                if (null == adapter) {
////                    finish();
////                    break;
////                }
////                if (!adapter.isEnabled()) {
////                    if (adapter.enable()) {
////                        while (!adapter.isEnabled())
////                            ;
////                        Log.v(TAG, "Enable BluetoothAdapter");
////                    } else {
////                        finish();
////                        break;
////                    }
////                }
////
////                adapter.cancelDiscovery();
////                adapter.startDiscovery();
////                break;
////            }
//
//            case R.id.buttonDisconnect:
//                es.submit(new TaskClose(mBt));
//                break;
//
//            case R.id.buttonPrint:
//                btnPrint.setEnabled(false);
//                es.submit(new TaskPrint(mPos));
//                break;
//        }
//    }

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
                    btAdapter.add(new BlueToothBean(name,address));
//                    Hawk.put(Config.S_BLUETOOTH, address);
//                    if ("dc:0d:30:0e:3e:0f".equals(address) || "DC:0D:30:0E:3E:0F".equals(address)) {
//                        Log.e("蓝牙：", "查到打印机");
//                        Toast.makeText(mActivity, "Connecting...", Toast.LENGTH_SHORT).show();
//                        btnDisconnect.setEnabled(false);
//                        btnPrint.setEnabled(false);
////                        es.submit(new TaskOpen(mBt,address, mActivity));
//                        es.submit(new TaskOpen(mBt, address, mActivity));
//                        //es.submit(new TaskTest(mPos, mBt, address, mActivity));
//                    }
                    ryBl.setRefreshing(true);
                } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED
                        .equals(action)) {
                    ryBl.setRefreshing(true);
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                        .equals(action)) {
                    ryBl.setRefreshing(false);
                }

            }

        };
        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void uninitBroadcast() {
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }

    @OnClick({R.id.btn_set_print_num, R.id.buttonPrint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_print_num:
                AlertDialog.Builder ab = new AlertDialog.Builder(mActivity);
                ab.setTitle("设置默认打印次数");
                View v = LayoutInflater.from(mActivity).inflate(R.layout.item_dg_print_num, null);
                final EditText etNum = v.findViewById(R.id.ed_num);
                etNum.setText(Hawk.get(Config.Print_num_Str,"1"));
                ab.setView(v);
                ab.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Hawk.put(Config.Print_num_Str,etNum.getText().toString());
                        nPrintCount=Integer.valueOf(etNum.getText().toString());
                    }
                });
                ab.setNegativeButton("取消",null);
                ab.setCancelable(false);
                ab.create().show();
                break;
            case R.id.buttonPrint:
                btnPrint.setEnabled(false);
                es.submit(new TaskPrint(mPos));
                break;
        }
    }

    public class TaskTest implements Runnable {
        Pos pos = null;
        BTPrinting bt = null;
        String address = null;
        Context context = null;

        public TaskTest(Pos pos, BTPrinting bt, String address, Context context) {
            this.pos = pos;
            this.bt = bt;
            this.address = address;
            this.context = context;
            pos.Set(bt);
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            for (int i = 0; i < 1000; ++i) {
                long beginTime = System.currentTimeMillis();
                if (bt.Open(address, context)) {
                    long endTime = System.currentTimeMillis();
                    pos.POS_S_Align(0);
                    pos.POS_S_TextOut(i + " " + "Open   UsedTime:" + (endTime - beginTime) + "\r\n", 0, 0, 0, 0, 0);
                    beginTime = System.currentTimeMillis();
                    boolean ticketResult = pos.POS_TicketSucceed(i, 30000);
                    endTime = System.currentTimeMillis();
                    pos.POS_S_TextOut(i + " " + "Ticket UsedTime:" + (endTime - beginTime) + " " + (ticketResult ? "Succeed" : "Failed") + "\r\n", 0, 0, 0, 0, 0);
                    pos.POS_Beep(1, 500);
                    bt.Close();
                }
            }
        }
    }

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
                    mActivity.btnPrint.setEnabled(bIsOpened);
                }
            });

        }


        public boolean PrintTicket(
                int nPrintWidth,
                boolean bCutter,
                boolean bDrawer,
                boolean bBeeper,
                int nCount,
                int nPrintContent,
                int nCompressMethod,
                boolean bCheckReturn
        ) {
            boolean bPrintResult = false;

            byte[] status = new byte[1];
            if (!bCheckReturn || (bCheckReturn && pos.POS_QueryStatus(status, 3000, 2))) {
//                Bitmap bm1 = mActivity.getTestImage1(nPrintWidth, nPrintWidth);
//                Bitmap bm2 = mActivity.getTestImage2(nPrintWidth, nPrintWidth);
//                Bitmap bmBlackWhite = getImageFromAssetsFile("blackwhite.png");
//                Bitmap bmIu = getImageFromAssetsFile("iu.jpeg");
//                Bitmap bmYellowmen = getImageFromAssetsFile("yellowmen.png");
                for (int i = 0; i < nCount; ++i) {
                    //如果已经连接到打印机
                    if (!pos.GetIO().IsOpened()) {
                        Log.e("asdf", "？？？？");
                        break;
                    }

                    //nOrgx:            指定x方向（水平）的起始点位置离右边界的点数
                    //nWidthTimes:      指定字符的宽度方向上的放大倍数  0-1
                    //nHeightTimes:     指定字符高度向上的放大倍数
                    //nFontType:        指定字符的字体类型  0x00 标准ASCII 12X24; 0x01 压缩ASCII 9X17
                    //nFontStyle:       指定字符的字体风格，可以多个 0x00正常,0x08加粗,0x80 1点粗下划线,
                    //                  0x100 2点粗下划线，0x400 反显，黑底白字，0x1000 每个字符顺时针旋转90度
                    if (nPrintContent >= 1) {
                        pos.POS_FeedLine();     //走纸一行
                        pos.POS_S_Align(1);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
                        pos.POS_S_TextOut("====测试成功====\n" ,
                                0, 1, 1, 0, 0x100);
                        pos.POS_FeedLine();     //走纸一行
                        pos.POS_FeedLine();     //走纸一行

//                        pos.POS_S_TextOut("REC" + String.format("%03d", i) +
//                                        "\r\nCaysn Printer\r\n测试页\r\n\r\n",
//                                0, 1, 1, 0, 0x100);
//                        pos.POS_S_TextOut("扫二维码下载苹果APP\r\n",
//                                0, 0, 0, 0, 0x100);
//                        pos.POS_S_SetQRcode("https://appsto.re/cn/2KF_bb.i",
//                                8, 0, 3);
//                        pos.POS_FeedLine();
//                        pos.POS_S_SetBarcode("20160618",
//                                0, 72, 3, 60, 0, 2);
//                        pos.POS_FeedLine();
                    }

//                    if(nPrintContent >= 2)
//                    {
//                        if(bm1 != null)
//                        {
//                            pos.POS_PrintPicture(bm1, nPrintWidth, 1, nCompressMethod);
//                        }else{
//                            Log.e("asdf","获取图片失败");
//                        }
//                        if(bm2 != null)
//                        {
//                            pos.POS_PrintPicture(bm2, nPrintWidth, 1, nCompressMethod);
//                        }else{
//
//                            Log.e("asdf","获取图片失败222");
//                        }
//                    }
//
//                    if(nPrintContent >= 3)
//                    {
//                        if(bmBlackWhite != null)
//                        {
//                            pos.POS_PrintPicture(bmBlackWhite, nPrintWidth, 1, nCompressMethod);
//                        }
//                        if(bmIu != null)
//                        {
//                            pos.POS_PrintPicture(bmIu, nPrintWidth, 0, nCompressMethod);
//                        }
//                        if(bmYellowmen != null)
//                        {
//                            pos.POS_PrintPicture(bmYellowmen, nPrintWidth, 0, nCompressMethod);
//                        }
//                    }
                }

                if (bBeeper)
                    pos.POS_Beep(1, 5);
                if (bCutter)
                    pos.POS_CutPaper();
                if (bDrawer)
                    pos.POS_KickDrawer(0, 100);

                if (bCheckReturn) {
                    int dwTicketIndex = dwWriteIndex++;
                    bPrintResult = pos.POS_TicketSucceed(dwTicketIndex, 30000);
                } else {
                    bPrintResult = pos.GetIO().IsOpened();
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


    //连接成功后
    @Override
    public void OnOpen() {
        // TODO Auto-generated method stub
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                btnPrint.setEnabled(true);
                Toast.makeText(mActivity, "打印机配置成功", Toast.LENGTH_SHORT).show();
                BlueToothBean bean=Hawk.get(Config.OBJ_BLUETOOTH,new BlueToothBean("",""));
                if (!"".equals(bean.getAddress())){
                    tvBluetooth.setText("已配置的打印机\n"+"名称："+bean.getName()+"\n地址："+bean.getAddress());
                }

//                if(bAutoPrint)
//                {
//                    btnPrint.performClick();
//                }
            }
        });
    }

    //连接失败
    @Override
    public void OnOpenFailed() {
        // TODO Auto-generated method stub
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                btnPrint.setEnabled(false);
                Toast.makeText(mActivity, "打印机配置失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //关闭或异常关闭
    @Override
    public void OnClose() {
        // TODO Auto-generated method stub
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
//                btnDisconnect.setEnabled(false);
//                btnPrint.setEnabled(false);
            }
        });
    }

    /**
     * 从Assets中读取图片
     */
    public Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    public Bitmap getTestImage1(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, width, height, paint);

        paint.setColor(Color.BLACK);
        for (int i = 0; i < 8; ++i) {
            for (int x = i; x < width; x += 8) {
                for (int y = i; y < height; y += 8) {
                    canvas.drawPoint(x, y, paint);
                }
            }
        }
        return bitmap;
    }

    public Bitmap getTestImage2(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, width, height, paint);

        paint.setColor(Color.BLACK);
        for (int y = 0; y < height; y += 4) {
            for (int x = y % 32; x < width; x += 32) {
                canvas.drawRect(x, y, x + 4, y + 4, paint);
            }
        }
        return bitmap;
    }
}
