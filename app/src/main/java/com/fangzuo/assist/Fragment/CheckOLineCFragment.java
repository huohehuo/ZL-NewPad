package com.fangzuo.assist.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseFragment;
import com.fangzuo.assist.Activity.CheckOLineActivity;
import com.fangzuo.assist.Activity.OLinePrintActivity;
import com.fangzuo.assist.Activity.PrintActivity;
import com.fangzuo.assist.Adapter.CCheckAdapter;
import com.fangzuo.assist.Adapter.ThirdRvAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.SecondCheck;
import com.fangzuo.assist.Beans.ThirdCheck;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.Dao.T_main;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.CalculateUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.EventBusUtil;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.loopj.android.http.AsyncHttpClient;
import com.lvrenyang.io.Pos;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckOLineCFragment extends BaseFragment{


    @BindView(R.id.ry_check_c)
    EasyRecyclerView ryCheckC;
    Unbinder unbinder;
    @BindView(R.id.et_baoshu)
    EditText etBaoshu;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.btn_print)
    Button btnPrint;
    @BindView(R.id.btn_change_client)
    Button btnChangeClient;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tv_v)
    TextView tvV;
    @BindView(R.id.tv_r)
    TextView tvR;
    @BindView(R.id.tv_clientName)
    TextView tvClientName;
    @BindView(R.id.et_diameter)
    EditText etDiameter;
    @BindView(R.id.et_lengh)
    EditText etLengh;
    private FragmentActivity mContext;
    private String orderID;
    private int tag;
    private DaoSession daoSession;
    private T_mainDao t_mainDao;
    private T_DetailDao t_detailDao;
    private LinearLayoutManager mLinearLayoutManager;
    private CCheckAdapter adapter;
    private List<T_Detail> t_details;
    private CheckOLineActivity lookActivity;
    private ArrayList<T_Detail> container;
    private DecimalFormat df;
    private String backData="";

    private ArrayList<ThirdCheck> printDataList;
    public static int nPrintWidth = 576;            //单据类型width
    public static boolean bCutter = false;          //切刀
    public static boolean bDrawer = false;          //钱箱
    public static boolean bBeeper = true;           //蜂鸣器
    public static int nPrintCount = 1;              //打印次数
    public static int nCompressMethod = 0;          //图形压缩
    public static boolean bAutoPrint = false;       //自动打印
    public static int nPrintContent = 2;            //打印内容，少量，中等，大量？
    public static boolean bCheckReturn = false;     //检查返回
    private String storageName="";
    private String json;
    private String client;
    private String vanNo;
    private String driver;
    private String printDate;
    private String maker;
    private boolean isRed=false;

    public CheckOLineCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        orderID = ((CheckOLineActivity) activity).getOrderID();
        tag = ((CheckOLineActivity) activity).getTag();
        lookActivity = ((CheckOLineActivity) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_oline_c, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBusUtil.register(this);
        mContext = getActivity();
        printDataList = new ArrayList<>();
        return view;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(ClassEvent event) {
        if (event != null) {
            switch (event.Msg) {
                case "1":
                    Log.e("123", "123");
                    Toast.showText(mContext, ((SecondCheck) event.postEvent).orderID);
                    break;
            }
        }
    }

    @Override
    protected void initView() {
        df = new DecimalFormat("######0.000");
    }

    @Override
    protected void initData() {
        ryCheckC.setAdapter(adapter = new CCheckAdapter(getActivity()));
        ryCheckC.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchList();
    }
//    String json;
    int genshu = 0;
    double tiji = 0d;
    //查询
    public void searchList(){
        ryCheckC.setRefreshing(true);
        int r = 0;
        double v = 0d;
        String a = etDiameter.getText().toString();
        String b = etLengh.getText().toString();
        String c = etBaoshu.getText().toString();
        ThirdCheck bean = new ThirdCheck();
        bean.length = b;
        bean.FIdentity = c;
        bean.diameter = a;
        bean.FOrderId = orderID;
        json =new Gson().toJson(bean);
        Asynchttp.post(mContext, BasicShareUtil.getInstance(mContext).getBaseURL() + WebApi.CheckLikeC, json, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                Lg.e("三级数据："+cBean.returnJson);
                final DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                if (dBean.thirdChecks.size() >= 1) {
                    backData = cBean.returnJson;
//                    printDataList=dBean.thirdChecks;
//                    initDataList(printDataList);
                    adapter.clear();
                    adapter.addAll(dBean.thirdChecks);
                    genshu=0;
                    tiji =0d;
                    for(int i = 0;i<dBean.thirdChecks.size();i++){
                        genshu+= Double.parseDouble(dBean.thirdChecks.get(i).radical);
                        tiji+= Double.parseDouble(dBean.thirdChecks.get(i).voleum);
                    }
                    tvR.setText("总根数:"+genshu+"");
                    tvV.setText("总体积:"+df.format(tiji));
                } else {
                    backData="";
                    adapter.clear();
                    tvR.setText("总根数:"+"0");
                    tvV.setText("总体积:"+"0");
                }
                ryCheckC.setRefreshing(false);

            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext, Msg);
                ryCheckC.setRefreshing(false);
                backData="";
            }
        });

        ThirdCheck printBean = new ThirdCheck();
        printBean.FOrderId = orderID;
        String jsonPrint =new Gson().toJson(bean);
        Asynchttp.post(mContext, BasicShareUtil.getInstance(mContext).getBaseURL() + WebApi.CheckLikeCPrint, jsonPrint, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                Lg.e("三级数据："+cBean.returnJson);
                final DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                if (dBean.thirdChecks.size() >= 1) {
                    backData = cBean.returnJson;
                    printDataList=dBean.thirdChecks;
                    initDataList(printDataList);

                } else {
//                    backData="";
//                    adapter.clear();
//                    tvR.setText("总根数:"+"0");
//                    tvV.setText("总体积:"+"0");
                }
//                ryCheckC.setRefreshing(false);

            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext, Msg);
//                ryCheckC.setRefreshing(false);
//                backData="";
            }
        });

    }

    private void initDataList(ArrayList<ThirdCheck> list){
        if (list.size() >= 1) {
            client = list.get(0).FClientName;
            vanNo = list.get(0).FVanNo;
            driver = list.get(0).FDriverName;
            printDate = list.get(0).FDate;
            storageName = list.get(0).FStorageName;
            orderID = list.get(0).FOrderId;
            maker = list.get(0).FMaker;
            if (list.get(0).radical.contains("-")){
                isRed=true;
            }else{
                isRed=false;
            }
            tvClientName.setText("客户:"+client);
        }
    }

    double e = 0d;
    double f = 0d;

    double checkGS=0d;
    double checkTJ=0d;
    //打印短材
    private void printShort(Pos pos) {
        pos.POS_FeedLine();     //走纸一行
        pos.POS_S_Align(1);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
        if (isRed){
            pos.POS_S_TextOut("-------"+Hawk.get(Config.Print_Head_Text_Str, "广东中梁客户确认单")+"(红单)"+"-------\n",
                    0, 0, 1, 0, 0x08);
        }else{
            pos.POS_S_TextOut("-------"+Hawk.get(Config.Print_Head_Text_Str, "广东中梁客户确认单")+"-------\n",
                    0, 0, 1, 0, 0x08);
        }
        pos.POS_FeedLine();     //走纸一行
        pos.POS_S_Align(0);     //对其方式  0：左对齐，1：居中对齐 2：右对齐
//        pos.POS_S_TextOut("仓库:" + storageName  + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("日期:" + printDate + "  客户:" + client + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("车    号：" + vanNo + "     ", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("司机姓名：" + driver + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("订单号：" + orderID + "\n", 0, 0, 0, 0, 0x00);
//        pos.POS_S_TextOut("----------------------------\n", 0, 0, 0, 0, 0x00);
//        pos.POS_S_TextOut("长度  径级  根数   |   长度  径级  根数 \n", 0, 0, 0, 0, 0x00);
        String A = "";
        String B = "";
        String C = "";
//        Double e = 0d;
//        int f = 0;
        Double g = 0d;
        int h = 0;

//存储唯一值，用于区分打印筛选类型
        TreeSet<String> storageTree=new TreeSet<>();
        TreeSet<String> modelTree=new TreeSet<>();
        //遍历并添加所有的仓库名称
        for (ThirdCheck t:printDataList) {
            storageTree.add(t.FStorageName);
        }

        Double tjMax = 0d;
        int gsMax = 0;
        //遍历出所有仓库名称
        Lg.e("遍历仓库名称："+storageTree.toString());
        for(Iterator iter = storageTree.iterator(); iter.hasNext(); ) {
            String thisStorage = iter.next().toString();
            pos.POS_S_TextOut("----------------------------------------------\n", 0, 0, 0, 0, 0x00);
            pos.POS_S_TextOut("仓库:"+thisStorage+"\n", 0, 0, 0, 0, 0x08);
            pos.POS_S_TextOut("长度  径级  根数    |  长度  径级  根数 \n", 0, 0, 0, 0, 0x00);
            pos.POS_S_TextOut("----------------------------------------------\n", 0, 0, 0, 0, 0x00);

            //遍历出 该仓库 的所有 规格型号-------------------------------------
            for (int i = 0; i <printDataList.size(); i++) {
                if (thisStorage.equals(printDataList.get(i).FStorageName)){
                    modelTree.add(printDataList.get(i).model);
                }
            }
            Lg.e("遍历规格型号："+modelTree.toString());
            //---------------------------------------------------------------
            for(Iterator iterModel = modelTree.iterator(); iterModel.hasNext(); ) {
                String thisModel = iterModel.next().toString();
                Double tj = 0d;
                int gs = 0;
                int position =0;
                int isoff =0;
                for (int j = 0; j < printDataList.size(); j++) {
                    if (thisStorage.equals(printDataList.get(j).FStorageName) && thisModel.equals(printDataList.get(j).model)){
                        tj += Double.parseDouble(printDataList.get(j).voleum);     //体积
                        gs += (int) Math.ceil(Double.parseDouble(printDataList.get(j).radical));      //根数
                        A =(int) Math.ceil(Double.parseDouble(printDataList.get(j).diameter))+"            ";
//                        A = A + "            ";
                        A = A.substring(0, 3);
                        B =(int) Math.ceil(Double.parseDouble(printDataList.get(j).radical))+"            ";
//                        B = printDataList.get(j).radical + "            ";
                        B = B.substring(0, 4);
                        C = printDataList.get(j).length + "             ";
                        C = C.substring(0, 5);
                        pos.POS_S_TextOut(C + "  " + A + "  " + (int) Math.ceil(Double.parseDouble(B)) + "    |   ", 0, 0, 0, 0, 0x00);
                        isoff++;
                        if (isoff==2){
                            pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
                            isoff=0;
                        }
                        position = j;
                    }
                }
                tjMax=tjMax+tj;
                gsMax=gsMax+gs;
                if (gs!=0){
                    printMsg(pos,printDataList.get(position),tj,gs);
                }
            }
        }

//        double L=0;//体积
//        double T=0;//长度
//        int isLn=0;
//        String FItemID=printDataList.get(0).FItemID;
//        for (int i = 0; i < printDataList.size(); i++) {
//            ThirdCheck t_detail = printDataList.get(i);
//            if(FItemID.equals(t_detail.FItemID)) {
//                L=L+Double.parseDouble(t_detail.radical);
//                T=T+Double.parseDouble(t_detail.voleum);
//                A =(int) Math.ceil(Double.parseDouble(t_detail.diameter))+"            ";
//                A = A.substring(0, 3);
//                B =(int) Math.ceil(Double.parseDouble(t_detail.radical))+"            ";
//                B = B.substring(0, 3);
//                C = t_detail.length + "             ";
//                C = C.substring(0, 5);
//                pos.POS_S_TextOut(C + "  " + A + "  " + B + "    |   ", 0, 0, 0, 0, 0x00);
//                isLn++;
//
//                if (i==printDataList.size()-1){
//                    printMsg(pos,printDataList.get(i),T,L);
//                }
//            } else {
//                printMsg(pos,printDataList.get(i-1),T,L);
//
//                L=Double.parseDouble(t_detail.radical);
//                T=Double.parseDouble(t_detail.voleum);
//                FItemID=t_detail.FItemID;
//                A =(int) Math.ceil(Double.parseDouble(t_detail.diameter))+"            ";
//                A = A.substring(0, 3);
//                B =(int) Math.ceil(Double.parseDouble(t_detail.radical))+"            ";
//                B = B.substring(0, 3);
//                C = t_detail.length + "             ";
//                C = C.substring(0, 5);
//                pos.POS_S_TextOut(C + "  " + A + "  " + B + "    |   ", 0, 0, 0, 0, 0x00);
//                isLn++;
//                if (i==printDataList.size()-1){
//                    printMsg(pos,printDataList.get(i),T,L);
//                }
//            }
//            if (isLn==2) {
//                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
//                isLn=0;
//            }
////            A =(int) Math.ceil(Double.parseDouble(t_detail.diameter))+"            ";
////            A = A.substring(0, 3);
////            B =(int) Math.ceil(Double.parseDouble(t_detail.radical))+"            ";
////            B = B.substring(0, 3);
////            C = t_detail.length + "             ";
////            C = C.substring(0, 5);
////            pos.POS_S_TextOut(C + "  " + A + "  " + B + "    |   ", 0, 0, 0, 0, 0x00);
////            if (i % 2 != 0) {
////                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
////            }
////            Double tj = 0d;
////            int fa = 0;
////            Double gs = 0d;
////            int ha = 0;
////            TreeSet<String> checkList=new TreeSet<>();
////            String itemid=printDataList.get(i).FItemID;
////            for (int j = 0; j < printDataList.size(); j++) {
////                if (itemid.equals(printDataList.get(j).FItemID)){
////                    e += Double.parseDouble(printDataList.get(j).voleum);
////                    f += Double.parseDouble(printDataList.get(j).radical);
////                }
////            }
//
////            Lg.e("体积根数："+f+"--"+e);
////            Lg.e("型号："+printDataList.get(i).model+"----"+i);
////            if (i % 2 != 0) {
////                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
////            }
////
////            if (i < printDataList.size()-2 && !printDataList.get(i).FItemID.equals(printDataList.get(i + 1).FItemID)){
////                Lg.e("进入11111：");
////                printMsg(pos,t_detail);
////            }else if ( i == printDataList.size() - 2 && !printDataList.get(i).model.equals(printDataList.get(printDataList.size()-1).model)){
////                Lg.e("进入22222：");
////                printMsg(pos,t_detail);
////            }else if ( i == printDataList.size() - 1){
////                Lg.e("进入33333：");
////                printMsg(pos,t_detail);
////            }
//////
//////            if (i < printDataList.size() - 2 && !printDataList.get(i).FItemID.equals(printDataList.get(i + 1).FItemID)) {
//////                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
//////                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
//////                pos.POS_S_TextOut("体积：" + df.format(tj) + "  " + "根数： " + gs, 0, 0, 0, 0, 0x00);
//////                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
//////                tj=0d;
//////                gs=0d;
//////            } else if (i == printDataList.size() - 1) {
//////                pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
//////                pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
//////                    pos.POS_S_TextOut("体积：" + df.format(tj) + "  " + "根数： " + gs, 0, 0, 0, 0, 0x00);
//////                pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
//////                tj=0d;
//////                gs=0d;
//////            }
////
//        }
        pos.POS_FeedLine();
        pos.POS_S_TextOut("总体积:" + df.format(tiji) + " " + "总根数：" + genshu, 0, 0, 0, 0, 0x08);
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
        pos.POS_S_TextOut("销售电话："+Hawk.get(Config.Print_Phone_Text_Str, "潘生 13811719983")+"\n" , 0, 0, 1, 0, 0x08);
        pos.POS_S_TextOut("地址："+Hawk.get(Config.Print_Adress_Text_Str, "沙田大道与进港南路交叉口北200米"), 0, 0, 1, 0, 0x08);
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();
        pos.POS_FeedLine();


    }

    private void printMsg(Pos pos,ThirdCheck t_detail,double tj,double l){
        pos.POS_S_TextOut("\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut(t_detail.productname + " " + t_detail.model + "\n", 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("体积：" + df.format(tj) + "  " + "根数： " + (int) Math.ceil(l), 0, 0, 0, 0, 0x00);
        pos.POS_S_TextOut("\n————————————————————\n", 0, 0, 0, 0, 0x00);
        e = 0d;
        f = 0d;
        checkTJ=0d;
        checkGS=0d;
    }

    @Override
    protected void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               searchList();
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(backData)){
//                    new TaskPrint(lookActivity.getPos());
                    lookActivity.getEx().submit(new TaskPrint(lookActivity.getPos()));


//                    Bundle b = new Bundle();
//                    b.putString("json", backData);
//                    b.putString("genshu", genshu+"");
//                    b.putString("tiji", tiji+"");
//                    Intent mIntent = new Intent(getActivity(),OLinePrintActivity.class);
//                    mIntent.putExtras(b);
//                    startActivity(mIntent);
                }else{
                    Toast.showText(mContext,"无打印数据");
                }

            }
        });

        btnChangeClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    changeClient =true;
                    lookActivity.startActivityTo();
            }
        });

    }
    boolean changeClient=false;
    @Override
    public void onResume() {
        super.onResume();
        if (changeClient){
            if (lookActivity.getClientName()!=null && !"".equals(lookActivity.getClientName())){
                client = lookActivity.getClientName();
                tvClientName.setText("客户:"+client);
                changeClient =false;
            }
        }
        Lg.e("客户："+client);
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

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    android.widget.Toast.makeText(getActivity().getApplicationContext(), bPrintResult ? "打印成功" : "打印失败", android.widget.Toast.LENGTH_SHORT).show();

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

    @Override
    protected void OnReceive(String barCode) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBusUtil.unregister(this);
    }

    public void PressThirdBack() {
//        BlankFragment blankFragment = new BlankFragment();
//        lookActivity.setf(blankFragment);
//        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in, R.anim.activity_slide_left_out).remove(this).commit();
//        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in, R.anim.activity_slide_left_out).add(R.id.fragmenthost, blankFragment).commit();
    }


    private String VNo;
    private String VNanme;
//    @OnClick({R.id.btn_deleteAll,R.id.btn_bus_change})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_deleteAll:
//                final List<T_Detail> t_detailList = t_detailDao.queryBuilder().where(
//                        T_DetailDao.Properties.Activity.eq(tag),
//                        T_DetailDao.Properties.FOrderId.eq(orderID)
//                ).build().list();
//                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                ab.setTitle("全部删除警告").setMessage(
//                        "确认删除" + t_detailList.size() + "条数据?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        t_detailDao.deleteInTx(t_detailList);
//
//                        t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
//                                T_mainDao.Properties.Activity.eq(tag),
//                                T_mainDao.Properties.OrderId.eq(orderID)
//                        ).build().list());
//
//                        if (t_detailList != null && t_detailList.size() > 0) {
//                            t_detailDao.deleteInTx(t_detailList);
//                            initData();
//                        }
//                    }
//                }).setNegativeButton("取消", null).create().show();
//                break;
//            case R.id.btn_bus_change:
//                final List<T_main> t_mains=t_mainDao.queryBuilder().where(
//                T_mainDao.Properties.Activity.eq(tag),
//                T_mainDao.Properties.OrderId.eq(orderID)
//                ).build().list();
//                if (t_mains.size()>0){
//                    VNo = t_mains.get(0).VanNo;
//                    VNanme = t_mains.get(0).VanDriver;
//                }
//                AlertDialog.Builder change = new AlertDialog.Builder(mContext);
//                change.setTitle("修改数据");
//                View changeview = LayoutInflater.from(mContext).inflate(R.layout.item_changebus, null);
//                final EditText name = changeview.findViewById(R.id.et_bus_name);
//                final EditText vanNo = changeview.findViewById(R.id.et_VanNo);
//                name.setText(VNanme==null?"":VNanme);
//                vanNo.setText(VNo==null?"":VNo);
//                change.setView(changeview);
//                change.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int position) {
//                            share.setSVanNo(vanNo.getText().toString());
//                            share.setSVanDriver(name.getText().toString());
//                            Hawk.put(Config.has_Change_Bus,true);
//                        for (int i=0;i<t_mains.size();i++){
//                            t_mains.get(i).VanNo=vanNo.getText().toString();
//                            t_mains.get(i).VanDriver = name.getText().toString();
//                            t_mainDao.update(t_mains.get(i));
//                        }
//                    }
//                });
//                change.setNegativeButton("取消", null);
//                change.create().show();
//
//
//
//                break;
//        }
//
//
//    }

}
