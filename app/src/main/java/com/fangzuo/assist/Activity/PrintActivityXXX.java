package com.fangzuo.assist.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.ThirdRvAdapter;
import com.fangzuo.assist.Beans.BlueToothBean;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.Dao.T_main;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.lvrenyang.io.BTPrinting;
import com.lvrenyang.io.IOCallBack;
import com.lvrenyang.io.Pos;
import com.orhanobut.hawk.Hawk;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrintActivityXXX extends BaseActivity implements IOCallBack {


    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.tv_client)
    TextView tvClient;
    @BindView(R.id.tv_VanNo)
    TextView tvVanNo;
    @BindView(R.id.vanDriver)
    TextView vanDriver;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.print)
    Button btnPrint;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.btn_try)
    Button btnTry;
    @BindView(R.id.tv_storage)
    TextView tvStorage;
    private String orderID;
    //    private PosApi mPosApi;
//    private PrintQueue mPrintQueue;
    private int tag;
    private DaoSession daoSession;
    private T_mainDao t_mainDao;
    private T_DetailDao t_detailDao;
    private List<T_Detail> t_details;
    private ThirdRvAdapter thirdRvAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private FirstCheck firstCheckBean;
    private String client;
    private String vanNo;
    private String driver;
    private String printDate;
    private String salesManName;
    private String FRedBlue="";
    private ArrayList<FirstCheck> fContainer;
    private String TV;
    private String TR;
    private DecimalFormat df;
    private BroadcastReceiver broadcastReceiver = null;
    private IntentFilter intentFilter = null;
    ExecutorService es = Executors.newScheduledThreadPool(30);
    Pos mPos = new Pos();
    BTPrinting mBt = new BTPrinting();
    PrintActivityXXX mActivity;
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
    @Override
    protected void initView() {
        setContentView(R.layout.activity_print);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        nPrintCount = Integer.valueOf(Hawk.get(Config.Print_num_Str,"1"));
        mPos.Set(mBt);
        mBt.SetCallBack(this);
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        t_mainDao = daoSession.getT_mainDao();
        t_detailDao = daoSession.getT_DetailDao();
        Bundle b = getIntent().getExtras();
        firstCheckBean = (FirstCheck) b.getSerializable("item");
        df = new DecimalFormat("#0.000");
        orderID = firstCheckBean.orderID;
        tag = b.getInt("tag");

        btnPrint.setEnabled(false);
        btnPrint.setText("正在初始化...");
        bean = Hawk.get(Config.OBJ_BLUETOOTH, new BlueToothBean("", ""));

        es.submit(new TaskOpen(mBt, bean.getAddress(), mActivity));

//        /*
//        获取打印机接口
//         */
//        try {
//            FileWriter localFileWriterOn = new FileWriter(new File("/proc/gpiocontrol/set_sam"));
//            localFileWriterOn.write("1");
//            localFileWriterOn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //init
//        mPosApi = App.getInstance().getPosApi();
//        mPosApi.setOnComEventListener(mCommEventListener);
//        //get interface
//        mPosApi.initDeviceEx("/dev/ttyMT2");
//
//        //建立打印队列
//        mPrintQueue = new PrintQueue(this, mPosApi);
//        mPrintQueue.init();
//        //设置打印机队列监听
//        mPrintQueue.setOnPrintListener(new PrintQueue.OnPrintListener() {
//            @Override
//            public void onGetState(int state) {
//                switch (state) {
//                    case 0:
//                        Toast.showText(mContext, "有纸");
//
//                        break;
//                    case 1:
//
//                        Toast.showText(mContext, "没有纸");
//
//                        break;
//
//                }
//            }
//
//            @Override
//            public void onPrinterSetting(int state) {
//                switch (state) {
//                    case 0:
//                        Toast.showText(mContext, "有纸");
//                        break;
//                    case 1:
//                        Toast.showText(mContext, "没有纸");
//                        break;
//                    case 2:
//                        Toast.showText(mContext, "Detected black mark");
//                        break;
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                // TODO Auto-generated method stub
//                Toast.showText(mContext, "打印完成");
//                finish();
//            }
//
//            @Override
//            public void onFailed(int state) {
//                // TODO Auto-generated method stub
//                switch (state) {
//                    case PosApi.ERR_POS_PRINT_NO_PAPER:
//                        Toast.showText(mContext, "打印失败，错误原因:无纸");
//                        break;
//                    case PosApi.ERR_POS_PRINT_FAILED:
//                        Toast.showText(mContext, "打印失败，错误原因:未知");
//                        break;
//                    case PosApi.ERR_POS_PRINT_VOLTAGE_LOW:
//                        Toast.showText(mContext, "打印失败，错误原因:低电压");
//                        break;
//                    case PosApi.ERR_POS_PRINT_VOLTAGE_HIGH:
//                        Toast.showText(mContext, "打印失败，错误原因:高电压");
//                        break;
//                }
//            }
//        });

    }
//    //打印机初始哈广播
//    PosApi.OnCommEventListener mCommEventListener = new PosApi.OnCommEventListener() {
//        @Override
//        public void onCommState(int cmdFlag, int state, byte[] resp, int respLen) {
//            // TODOuto- Agenerated method stub
//            switch (cmdFlag) {
//                case PosApi.POS_INIT:
//                    if (state == PosApi.COMM_STATUS_SUCCESS) {
//                        Toast.showText(mContext, "打印机初始化成功");
//                    } else {
//                        Toast.showText(mContext, "打印机初始化失败");
//                    }
//                    break;
//            }
//        }
//
//
//    };

    @Override
    protected void initData() {
        List<T_main> list = t_mainDao.queryBuilder().where(
                T_mainDao.Properties.Activity.eq(tag),
                T_mainDao.Properties.OrderId.eq(orderID)
        ).build().list();
        if (list.size() > 0) {
            client = list.get(0).supplier;
            vanNo = list.get(0).VanNo;
            driver = list.get(0).VanDriver;
            printDate = list.get(0).orderDate;
            salesManName = list.get(0).FSalesMan;
            FRedBlue = list.get(0).FRedBlue;
            Lg.e("salesManName"+salesManName);
            date.setText(list.get(0).orderDate);
            tvClient.setText(client);
            tvVanNo.setText(vanNo);
            vanDriver.setText(driver);
//            total.setText("总根数:" + firstCheckBean.TRadical + "    总体积:" + firstCheckBean.TVoleum);
            total.setText(LookActivity.allTjAndGs==null?"":LookActivity.allTjAndGs);
        }
        t_details = t_detailDao
                .queryBuilder()
                .where(T_DetailDao.Properties.Activity.eq(tag),
                        T_DetailDao.Properties.FOrderId.eq(orderID))
                .orderAsc(T_DetailDao.Properties.FIdentity)
                .build()
                .list();
        thirdRvAdapter = new ThirdRvAdapter(mContext, t_details, false);
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        rv.setAdapter(thirdRvAdapter);

        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                "LENGTH," +
                "DIAMETER," +
                "MODEL, " +
                "FRED_BLUE, " +
                "FSTORAGE, " +
                "FORDER_ID," +
                "FPRODUCT_NAME," +
                "FPRODUCT_ID," +
                "SUM(FQUANTITY) AS FQTY," +
                "SUM(RADICAL) AS RADICAL " +
                "FROM " +
                "T__DETAIL WHERE " +
                "ACTIVITY=? AND " +
                "FORDER_ID = ?  GROUP BY " +
                "FPRODUCT_NAME," +
                "LENGTH," +
                "FPRODUCT_ID,DIAMETER ORDER BY FPRODUCT_ID ASC,cast(DIAMETER as int) ASC", new String[]{tag + "", firstCheckBean.orderID});
        fContainer = new ArrayList<>();
        while (cursor.moveToNext()) {
            FirstCheck f = new FirstCheck();
            f.orderID = cursor.getString(cursor.getColumnIndex("FORDER_ID"));
            f.productname = cursor.getString(cursor.getColumnIndex("FPRODUCT_NAME"));
            f.TRadical = cursor.getString(cursor.getColumnIndex("RADICAL"));
            f.TVoleum = cursor.getString(cursor.getColumnIndex("FQTY"));
            f.itemID = cursor.getString(cursor.getColumnIndex("FPRODUCT_ID"));
            f.model = cursor.getString(cursor.getColumnIndex("MODEL"));
            f.diameter = cursor.getString(cursor.getColumnIndex("DIAMETER"));
            f.length = cursor.getString(cursor.getColumnIndex("LENGTH"));
            f.fstorageName = cursor.getString(cursor.getColumnIndex("FSTORAGE"));
            f.FRedBlue = cursor.getString(cursor.getColumnIndex("FRED_BLUE"));
            storageName=f.fstorageName;
            fContainer.add(f);
        }

        Cursor cursor1 = daoSession.getDatabase().rawQuery("SELECT SUM(FQUANTITY) AS " +
                "FQTY,SUM(RADICAL) AS RADICAL FROM T__DETAIL WHERE ACTIVITY=? AND FORDER_ID = ?", new String[]{tag + "", firstCheckBean.orderID});
        if (cursor.moveToNext()) {
            TV = cursor.getString(cursor.getColumnIndex("FQTY"));
            TR = cursor.getString(cursor.getColumnIndex("RADICAL"));
        }
        tvStorage.setText(storageName);

    }

    @Override
    protected void initListener() {
//        thirdRvAdapter.setOnItemClickListener(new ThirdRvAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag == 6452) {
//                    printTextWithInput();
                    es.submit(new TaskPrint(mPos));

                } else {
                    es.submit(new TaskPrint(mPos));

//                    printTextWithInputLong();
                }
            }
        });
        //当断开连接时，重新扫描蓝牙，并自动连接打印机
        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isConnect=false;
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivity.btnPrint.setText("正在重新连接");
                        mActivity.btnTry.setVisibility(View.GONE);
                    }
                });
                if (!isConnect){
                    initWifiAndBT();

                }
            }
        });
    }

    @Override
    protected void OnReceive(String code) {

    }


    Double e = 0d;
    int f = 0;
    //打印短材
    private void printShort(Pos pos) {
        pos.POS_FeedLine();     //走纸一行
        pos.POS_S_Align(1);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
        if ("红字".equals(FRedBlue)){
            pos.POS_S_TextOut("-------"+Hawk.get(Config.Print_Head_Text_Str, "广东中梁客户确认单")+"(红单)"+"-------\n",
                    0, 0, 1, 0, 0x00);
        }else{
            pos.POS_S_TextOut("-------"+Hawk.get(Config.Print_Head_Text_Str, "广东中梁客户确认单")+"-------\n",
                    0, 0, 1, 0, 0x00);
        }

        pos.POS_FeedLine();     //走纸一行
        pos.POS_S_Align(0);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
        pos.POS_S_TextOut("仓库:" + storageName  + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("日期:" + printDate + "  客户:" + client + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("车    号：" + vanNo + "     ", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("司机姓名：" + driver + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("订单号：" + firstCheckBean.orderID + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("----------------------------\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("长度  径级  根数   |  长度  径级  根数 \n", 0, 0, 0, 0, 0x00);
        String A = "";
        String B = "";
        String C = "";
        Double g = 0d;
        int h = 0;
        for (int i = 0; i < fContainer.size(); i++) {
            e += Double.parseDouble(fContainer.get(i).TVoleum);     //体积
            g += Double.parseDouble(fContainer.get(i).TVoleum);     //总体积
            f += Integer.parseInt(fContainer.get(i).TRadical);      //根数
            h += Integer.parseInt(fContainer.get(i).TRadical);      //总根数
            FirstCheck t_detail = fContainer.get(i);
            A = t_detail.diameter + "            ";
            A = A.substring(0, 3);
            B = t_detail.TRadical + "            ";
            if ("红字".equals(fContainer.get(i).FRedBlue)){
                B="-"+B;
            }
            B = B.substring(0, 4);
            C = t_detail.length + "             ";
            C = C.substring(0, 5);
            pos.POS_S_TextOut(C + "  " + A + "  " + B + "    |   ", 0, 0, 0, 0, 0x00);

            Lg.e("体积根数："+f+"--"+e);
            Lg.e("型号："+fContainer.get(i).model+"----"+i);
            if (i % 2 != 0) {
                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
            }

            if (i < fContainer.size()-2 && !fContainer.get(i).itemID.equals(fContainer.get(i + 1).itemID)){
                Lg.e("进入11111：");
                printMsg(pos,t_detail);
            }else if ( i == fContainer.size() - 2 && !fContainer.get(i).model.equals(fContainer.get(fContainer.size()-1).model)){
                Lg.e("进入22222：");
                printMsg(pos,t_detail);
            }else if ( i == fContainer.size() - 1){
                Lg.e("进入33333：");
                printMsg(pos,t_detail);
            }

//            if (i < fContainer.size() - 2 && !fContainer.get(i).itemID.equals(fContainer.get(i + 1).itemID)) {
//                Lg.e("进入11111：");
//                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("体积：" + df.format(e) + "  " + "根数： " + f, 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
//                e = 0d;
//                f = 0;
//            } else if (i == fContainer.size() - 1) {
//                Lg.e("进入22222：");
//                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
//                pos.POS_FeedLine();
//                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("体积：" + df.format(e) + "  " + "根数：" + f, 0, 0, 0, 0, 0x00);
//                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
//                e = 0d;
//                f = 0;
//            }

        }
        String allGs="";
        if ("红字".equals(fContainer.get(0).FRedBlue)){
            allGs = "-"+h;
        }else{
            allGs=h+"";
        }
        pos.POS_S_TextOut("总体积:" + df.format(g) + " " + "总根数：" + allGs, 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_S_TextOut("现场人员确认:" + salesManName + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_S_TextOut("客户确认", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_S_TextOut("客户电话：\n", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_S_TextOut("司机电话：", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_S_TextOut("销售电话："+Hawk.get(Config.Print_Phone_Text_Str, "潘生 13811719983")+"\n" , 0, 0, 1, 0, 0x00);
        pos.POS_S_TextOut("地址："+Hawk.get(Config.Print_Adress_Text_Str, "沙田大道与进港南路交叉口北200米"), 0, 0, 1, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();


    }
    private void printMsg(Pos pos,FirstCheck t_detail){
        pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
        if ("红字".equals(t_detail.FRedBlue)){
            pos.POS_S_TextOut("体积：" + df.format(e) + "  " + "根数： -" + f, 0, 0, 0, 0, 0x08);
        }else{
            pos.POS_S_TextOut("体积：" + df.format(e) + "  " + "根数： " + f, 0, 0, 0, 0, 0x08);
        }        pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
        e = 0d;
        f = 0;
    }

    //打印长材（暂时不用)
    private void printLong(Pos pos) {
        pos.POS_FeedLine();     //走纸一行
        pos.POS_S_Align(1);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
        pos.POS_S_TextOut("-------"+Hawk.get(Config.Print_Head_Text_Str)+"-------\n",
                0, 0, 1, 0, 0x00);
        pos.POS_FeedLine();     //走纸一行
        pos.POS_FeedLine();     //走纸一行
        pos.POS_S_Align(0);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
        pos.POS_S_TextOut("日期:" + printDate + "  客户:" + client + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("车    号：" + vanNo + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("司机姓名：" + driver + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("订单号：" + firstCheckBean.orderID + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("----------------------------\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("长度  径级  根数   |  长度  径级  根数 \n", 0, 0, 0, 0, 0x00);
        String A = "";
        String B = "";
        String C = "";
        Double e = 0d;
        int f = 0;
        Double g = 0d;
        int h = 0;
        for (int i = 0; i < fContainer.size(); i++) {
            e += Double.parseDouble(fContainer.get(i).TVoleum);
            g += Double.parseDouble(fContainer.get(i).TVoleum);
            f += Integer.parseInt(fContainer.get(i).TRadical);
            h += Integer.parseInt(fContainer.get(i).TRadical);
            FirstCheck t_detail = fContainer.get(i);
            A = t_detail.diameter + "            ";
            A = A.substring(0, 3);
            B = t_detail.TRadical + "            ";
            B = B.substring(0, 3);
            C = t_detail.length + "             ";
            C = C.substring(0, 5);
            pos.POS_S_TextOut(C + "  " + A + "  " + B + "    |   ", 0, 0, 0, 0, 0x00);

//                sb.append(A).append("  ").append(C).append(" ").append(B).append("|");

            if (i % 2 != 0) {
                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
            }
            if (i < fContainer.size() - 2 && !fContainer.get(i).itemID.equals(fContainer.get(i + 1).itemID)) {
                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
                pos.POS_S_TextOut("体积：" + df.format(e) + "  " + "根数： " + f, 0, 0, 0, 0, 0x00);
                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
                e = 0d;
                f = 0;
            } else if (i == fContainer.size() - 1) {
                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
                pos.POS_FeedLine();
                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
                pos.POS_S_TextOut("体积：" + df.format(e) + "  " + "根数：" + f, 0, 0, 0, 0, 0x00);
                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
                e = 0d;
                f = 0;
            }
        }
        pos.POS_S_TextOut("总体积:" + df.format(g) + " " + "总根数：" + h, 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_S_TextOut("现场人员确认:" + share.getUserName() + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_S_TextOut("客户确认", 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_S_TextOut("销售电话："+Hawk.get(Config.Print_Phone_Text_Str)+"\n" , 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("地址："+Hawk.get(Config.Print_Adress_Text_Str), 0, 0, 0, 0, 0x00);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();


    }



    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        if (mPosApi != null) {
//            mPosApi.closeDev();
//        }
        es.submit(new TaskClose(mBt));

        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }


//        try {
//            FileWriter localFileWriterOn = new FileWriter(new File("/proc/gpiocontrol/set_sam"));
//            localFileWriterOn.write("0");
//            localFileWriterOn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
                    mActivity.btnPrint.setEnabled(true);
                    mActivity.btnPrint.setText("打印成功");
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
                mActivity.btnPrint.setEnabled(true);
                mActivity.btnPrint.setText("确认单据并打印");
                mActivity.btnTry.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnOpenFailed() {
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(mActivity, "打印机配置失败", Toast.LENGTH_SHORT).show();
                mActivity.btnPrint.setEnabled(false);
                mActivity.btnPrint.setText("初始化打印机失败");
                mActivity.btnTry.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void OnClose() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(mActivity, "已断开连接", Toast.LENGTH_SHORT).show();
                mActivity.btnPrint.setEnabled(false);
                mActivity.btnPrint.setText("打印机已断开");
                mActivity.btnTry.setVisibility(View.VISIBLE);
            }
        });
    }



    //----------------------------------旧版本遗留----------------

    //打印设置  字体  浓度
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
//            mPrintQueue.addText(concentration, mData);
        } else if (size == 2) {
            mData = new byte[3 + data.length];
            System.arraycopy(_2x, 0, mData, 0, _2x.length);
            System.arraycopy(data, 0, mData, _2x.length, data.length);
//            mPrintQueue.addText(concentration, mData);
        }

    }

    //打印短材
    private void printTextWithInput() {
        try {
            int concentration = 15;
            StringBuilder sb = new StringBuilder();
            sb.append("-------广东中梁客户确认单-------");
            sb.append("\n");
            sb.append("日期:").append(printDate).append("  ");
            sb.append("客户:").append(client);
            sb.append("\n");
            sb.append("车    号：").append(vanNo);
            sb.append("\n");
            sb.append("司机姓名：").append(driver);
            sb.append("\n");
            sb.append("订单号：").append(firstCheckBean.orderID);
            sb.append("\n");
            sb.append("-------------------------------");
            sb.append("\n");
            sb.append("径级").append(" ").append("长度").append(" ").append("根数").append("|").append("径级").append(" ").append("长度").append(" ").append("根数");
            sb.append("\n");
            String A = "";
            String B = "";
            String C = "";
            Double e = 0d;
            int f = 0;
            Double g = 0d;
            int h = 0;
            for (int i = 0; i < fContainer.size(); i++) {
                e += Double.parseDouble(fContainer.get(i).TVoleum);
                g += Double.parseDouble(fContainer.get(i).TVoleum);
                f += Integer.parseInt(fContainer.get(i).TRadical);
                h += Integer.parseInt(fContainer.get(i).TRadical);
                FirstCheck t_detail = fContainer.get(i);
                A = t_detail.diameter + "            ";
                A = A.substring(0, 3);
                B = t_detail.TRadical + "            ";
                B = B.substring(0, 3);
                C = t_detail.length + "             ";
                C = C.substring(0, 5);
                sb.append(A)
                        .append("  ").append(C).append(" ")
                        .append(B)
                        .append("|");

                if (i % 2 != 0) sb.append("\n");
                if (i < fContainer.size() - 2 && !fContainer.get(i).itemID.equals(fContainer.get(i + 1).itemID)) {
                    sb.append("\n");
                    sb.append(t_detail.productname).append(" ").append(t_detail.model).append("\n");
                    sb.append("体积:").append(df.format(e)).append("  ").append("根数:").append(f);
                    sb.append("\n").append("——————————————").append("\n");
                    e = 0d;
                    f = 0;
                } else if (i == fContainer.size() - 1) {
                    sb.append("\n");
                    sb.append(t_detail.productname).append(" ").append(t_detail.model).append("\n");
                    sb.append("体积:").append(df.format(e)).append("  ").append("根数:").append(f);
                    sb.append("\n").append("——————————————").append("\n");
                    e = 0d;
                    f = 0;
                }
            }
            sb.append("总体积:").append(df.format(g)).append(" ").append("总根数:").append(h);
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("现场人员确认：").append(share.getUserName());
            sb.append("\n");
            sb.append("客户确认:");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");


            byte[] text = null;
            text = sb.toString().getBytes("GBK");
//            addPrintTextWithSize(1, concentration, text);
//            mPrintQueue.printStart();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void printTextWithInputLong() {
        try {
            int concentration = 15;
            StringBuilder sb = new StringBuilder();
            sb.append("-------广东中梁客户确认单-------");
            sb.append("\n");
            sb.append("日期:").append(printDate).append("  ");
            sb.append("客户:").append(client);
            sb.append("\n");
            sb.append("车    号：").append(vanNo);
            sb.append("\n");
            sb.append("司机姓名：").append(driver);
            sb.append("\n");
            sb.append("订单号：").append(firstCheckBean.orderID);
            sb.append("\n");
            sb.append("-------------------------------");
            sb.append("\n");
            sb.append("径级").append(" ").append("长度").append(" ").append("根数").append("|").append("径级").append(" ").append("长度").append(" ").append("根数");
            sb.append("\n");
            String A="";
            String B="";
            String C= "";
            Double e=0d;
            int f = 0;
            Double g = 0d;
            int h=0;
            for(int i = 0;i<fContainer.size();i++){
                e+=Double.parseDouble(fContainer.get(i).TVoleum);
                g+=Double.parseDouble(fContainer.get(i).TVoleum);
                f+=Integer.parseInt(fContainer.get(i).TRadical);
                h+=Integer.parseInt(fContainer.get(i).TRadical);
                FirstCheck t_detail = fContainer.get(i);
                A=t_detail.diameter+"            ";
                A=A.substring(0,3);
                B=t_detail.TRadical+"            ";
                B = B.substring(0,3);
                C = t_detail.length+"             ";
                C = C.substring(0,5);
                sb.append(A)
                        .append("  ").append(C).append(" ")
                        .append(B)
                        .append("|");

                if (i % 2 != 0) sb.append("\n");
                if (i < fContainer.size() - 2 && !fContainer.get(i).itemID.equals(fContainer.get(i + 1).itemID)) {
                    sb.append("\n");
                    sb.append(t_detail.productname).append(" ").append(t_detail.model).append("\n");
                    sb.append("体积:").append(df.format(e)).append("  ").append("根数:").append(f);
                    sb.append("\n").append("——————————————").append("\n");
                    e = 0d;
                    f = 0;
                } else if (i == fContainer.size() - 1) {
                    sb.append("\n");
                    sb.append(t_detail.productname).append(" ").append(t_detail.model).append("\n");
                    sb.append("体积:").append(df.format(e)).append("  ").append("根数:").append(f);
                    sb.append("\n").append("——————————————").append("\n");
                    e = 0d;
                    f = 0;
                }
            }
            sb.append("总体积:").append(df.format(g)).append(" ").append("总根数:").append(h);
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("现场人员确认：").append(share.getUserName());
            sb.append("\n");
            sb.append("客户确认:");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");


            byte[] text = null;
            text = sb.toString().getBytes("GBK");
//            addPrintTextWithSize(1, concentration, text);
//            mPrintQueue.printStart();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
