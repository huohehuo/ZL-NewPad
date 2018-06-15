package com.fangzuo.assist.Activity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.ACheckAdapter;
import com.fangzuo.assist.Beans.BlueToothBean;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.ThirdCheck;
import com.fangzuo.assist.Fragment.BlankFragment;
import com.fangzuo.assist.Fragment.CheckOLineBFragment;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.VibratorUtil;
import com.fangzuo.assist.Utils.WebApi;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.lvrenyang.io.BTPrinting;
import com.lvrenyang.io.IOCallBack;
import com.lvrenyang.io.Pos;
import com.orhanobut.hawk.Hawk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckOLineActivity extends BaseActivity  implements IOCallBack {


    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ry_check_a)
    EasyRecyclerView ryCheckA;
    @BindView(R.id.et_pda_no)
    EditText etPdaNo;
    @BindView(R.id.et_no)
    EditText etNo;
    @BindView(R.id.ll_search)
    LinearLayout ll2;
    @BindView(R.id.btn_clear_pda_no)
    Button btnClearPdaNo;
    @BindView(R.id.btn_clear_no)
    Button btnClearNo;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.fragmenthost)
    FrameLayout fragmenthost;
    private ACheckAdapter adapter;
    private String pdaNo = "";
    private String BillNo = "";
    private String order;
    private String clientName="";
    private String model="";

    private ArrayList<ThirdCheck> printDataList;
    private DecimalFormat df;
    private BroadcastReceiver broadcastReceiver = null;
    private IntentFilter intentFilter = null;
    ExecutorService es = Executors.newScheduledThreadPool(30);
    public Pos mPos = new Pos();
    public BTPrinting mBt = new BTPrinting();
    private BlueToothBean bean;
    CheckOLineActivity mActivity;

    public static boolean isOkPrint=false;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_oline);
        ButterKnife.bind(this);
        mActivity =this;
        mPos.Set(mBt);
        mBt.SetCallBack(this);

        ryCheckA.setAdapter(adapter = new ACheckAdapter(this));
        ryCheckA.setLayoutManager(new LinearLayoutManager(this));
        tvTitle.setText("在线查询");
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        tvDate.setText(getTime(true));
        bean = Hawk.get(Config.OBJ_BLUETOOTH, new BlueToothBean("", ""));
        if ("".equals(bean.getAddress())){
            Toast.showText(mContext,"请先配置打印机");
        }else{
            es.submit(new TaskOpen(mBt, bean.getAddress(), mActivity));
        }
    }

    @Override
    protected void initData() {
        setfocus(tvRight);
        ryCheckA.setRefreshing(true);
        FirstCheck bean = new FirstCheck();
        bean.clientName = etPdaNo.getText().toString();
        bean.model = etNo.getText().toString();
        bean.date = tvDate.getText().toString();
        String json =new Gson().toJson(bean);
        Asynchttp.post(mContext, getBaseUrl() + WebApi.CheckLike, json, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                final DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                if (dBean.firstChecks.size() >= 1) {
                    adapter.clear();
                    tvMsg.setText("共有数据："+dBean.firstChecks.size()+" 条");
                    adapter.addAll(dBean.firstChecks);
                } else {
                    tvMsg.setText("无数据");
                    adapter.clear();
                }
                ryCheckA.setRefreshing(false);
//                if (dBean.products.size() == 1) {
//                    getProductOL(dBean, 0);
//                    default_unitID = dBean.products.get(0).FUnitID;
//                } else if (dBean.products.size() > 1) {
//                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                    ab.setTitle("请选择物料");
//                    View v = LayoutInflater.from(mContext).inflate(R.layout.pd_alert, null);
//                    ListView lv = v.findViewById(R.id.lv_alert);
//                    productselectAdapter1 = new ProductselectAdapter1(mContext, dBean.products);
//                    lv.setAdapter(productselectAdapter);
//                    ab.setView(v);
//                    final AlertDialog alertDialog = ab.create();
//                    alertDialog.show();
//                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            getProductOL(dBean, i);
//                            default_unitID = dBean.products.get(i).FUnitID;
//                            alertDialog.dismiss();
//                        }
//                    });
//                }

            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext, Msg);
                ryCheckA.setRefreshing(false);
            }
        });
    }
    private FirstCheck items;
    private CheckOLineBFragment blankFragment;
    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Lg.e(adapter.getAllData().get(position).toString());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                items = adapter.getAllData().get(position);
                blankFragment = new CheckOLineBFragment();
                transaction.setCustomAnimations(R.anim.activity_slide_right_in, R.anim.activity_slide_left_out).add(R.id.fragmenthost, blankFragment).commit();
                VibratorUtil.Vibrate(mContext,500);

            }
        });
        ryCheckA.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    @Override
    protected void OnReceive(String code) {

    }


    @OnClick({R.id.btn_search, R.id.btn_back,R.id.btn_clear_pda_no, R.id.btn_clear_no,R.id.ll_date,R.id.btn_clear_date,R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                initData();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_clear_pda_no:
                Bundle b = new Bundle();
                b.putString("search", etPdaNo.getText().toString());
                b.putInt("where", Info.SEARCHCLIENT);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTCLIRNT, b);
                break;
            case R.id.btn_clear_no:
                Bundle b1 = new Bundle();
                String sech="";
                sech=etNo.getText().toString();
                b1.putString("search", sech);
                b1.putInt("where", Info.Search_Product);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.Search_Product, b1);
                break;
            case R.id.ll_date:
                getdate();
                break;
            case R.id.btn_clear_date:
                tvDate.setText("");
                break;
            case R.id.tv_right:
                if (!"OK".equals(tvRight.getText().toString())){
                    restartPrint();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("code", requestCode + "" + "    " + resultCode);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();
                String message = b.getString("result");
                etPdaNo.setText(message);
                Toast.showText(mContext, message);
                etPdaNo.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
        } else if (requestCode == Info.SEARCHFORRESULTCLIRNT) {
            if (resultCode == Info.SEARCHFORRESULTCLIRNT) {
                Bundle b = data.getExtras();
//                clientId = b.getString("001");
                clientName = b.getString("002");
                etPdaNo.setText(clientName);
                setfocus(etPdaNo);
            }
        }else if (requestCode == Info.Search_Product) {
            if (resultCode == Info.Search_Product) {
                if (data!=null){
                    Bundle b = data.getExtras();
//                clientId = b.getString("001");
                    model = b.getString("002");
                    etNo.setText(model);
                    setfocus(etNo);
                }

            }
        }
    }

    public void startActivityTo() {
        Bundle b = new Bundle();
        b.putString("search", "");
        b.putInt("where", Info.SEARCHCLIENT);
        startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTCLIRNT, b);
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public int getTag() {
        return Config.CHOOSE_SECOND_FRAGMENT_CheckOLine;
    }
    public FirstCheck getfirst() {
        return items;
    }
    public void setOrderID(String orderid) {
        this.order = orderid;
    }

    public String getOrderID() {
        return order;
    }

    public Pos getPos() {
        return mPos;
    }
    public ExecutorService getEx() {
        return es;
    }
    @Override
    public void onBackPressed() {
        if (blankFragment != null && blankFragment.isAdded()) {
            blankFragment.pressBack();
        } else {
            super.onBackPressed();
        }
    }


    private int year,month,day;
    private String date="";
    private void getdate() {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            }
        }, year, month, day);

        datePickerDialog.setButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int year = datePickerDialog.getDatePicker().getYear();
                int month = datePickerDialog.getDatePicker().getMonth();
                int day = datePickerDialog.getDatePicker().getDayOfMonth();
                date = year + "-" + ((month < 10) ? "0" + (month + 1) : (month + 1)) + "-" + ((day < 10) ? "0" + day : day);
                tvDate.setText(date);
                Toast.showText(mContext, date);
                datePickerDialog.dismiss();


            }
        });
        datePickerDialog.show();
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

    public void restartPrint(){
        tvRight.setText("重试中...");
        es.submit(new TaskOpen(mBt, bean.getAddress(), mActivity));
    }



    @Override
    protected void onDestroy() {
        //关闭BTPrinting
        mBt.Close();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void OnOpen() {
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mActivity.tvRight.setTextColor(getResources().getColor(R.color.white));
                mActivity.tvRight.setText("OK");
//                android.widget.Toast.makeText(mActivity, "打印机配置成功", android.widget.Toast.LENGTH_SHORT).show();
//                mActivity.btnPrint.setText("确认单据并打印");
//                mActivity.btnTry.setVisibility(View.GONE);
                isOkPrint = true;
//                es.submit(new OLinePrintActivity.TaskPrint(mPos));

            }
        });
    }

    @Override
    public void OnOpenFailed() {
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                    isOkPrint = false;
                    mActivity.tvRight.setText("print error");
                    mActivity.tvRight.setTextColor(getResources().getColor(R.color.red));
//                android.widget.Toast.makeText(mActivity, "打印机配置失败", android.widget.Toast.LENGTH_SHORT).show();
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
                isOkPrint = false;
                mActivity.tvRight.setText("print error");
                mActivity.tvRight.setTextColor(getResources().getColor(R.color.red));
//                Toast.makeText(mActivity, "已断开连接", Toast.LENGTH_SHORT).show();
//                mActivity.btnPrint.setEnabled(false);
//                mActivity.btnPrint.setText("打印机已断开");
//                mActivity.btnTry.setVisibility(View.VISIBLE);
            }
        });
    }
}
