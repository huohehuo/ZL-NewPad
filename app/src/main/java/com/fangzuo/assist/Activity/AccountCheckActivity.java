package com.fangzuo.assist.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.miniprinter.App;
import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.AccountCheckAdapter;
import com.fangzuo.assist.Adapter.ChangePriceShowAdapter;
import com.fangzuo.assist.Adapter.SpAdapter;
import com.fangzuo.assist.Beans.AccountCheckBean;
import com.fangzuo.assist.Beans.AccountCheckData;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Dao.Client;
import com.fangzuo.assist.Dao.SpBean;
import com.fangzuo.assist.Dao.TempChangePrice;
import com.fangzuo.assist.R;
import com.fangzuo.assist.RxSerivce.MySubscribe;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.widget.LoadingUtil;
import com.fangzuo.assist.widget.SmoothCheckBox;
import com.fangzuo.greendao.gen.ChangePriceDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.TempChangePriceDao;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.hawk.Hawk;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountCheckActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_supplier)
    EditText etSupplier;
    @BindView(R.id.btn_supplier)
    Button btnSupplier;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.btn_company)
    Button btnCompany;
    @BindView(R.id.tv_date_start)
    TextView tvDateStart;
    @BindView(R.id.tv_date_start_clear)
    TextView tvDateStartClear;
    @BindView(R.id.tv_date_end)
    TextView tvDateEnd;
    @BindView(R.id.tv_date_end_clear)
    TextView tvDateEndClear;
    @BindView(R.id.ll_findview)
    LinearLayout llFindview;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.ry_accountcheck)
    EasyRecyclerView ryAcount;
    @BindView(R.id.cb_all_msg)
    SmoothCheckBox cbAllMsg;
    @BindView(R.id.cb_all_money)
    SmoothCheckBox cbAllMoney;
    @BindView(R.id.cb_huizong)
    SmoothCheckBox cbHuiZong;
    @BindView(R.id.ry_top_a)
    LinearLayout ryLltopA;
    @BindView(R.id.ry_top_b)
    LinearLayout ryLltopB;
    @BindView(R.id.ry_top_c)
    LinearLayout ryLltopC;
    private AccountCheckAdapter adapter;
    private ChangePriceShowAdapter adapterShow;
    ExecutorService es = Executors.newScheduledThreadPool(30);
    AccountCheckActivity mActivity;
    private DaoSession daoSession;
    private TempChangePriceDao tempChangePriceDao;
    private ChangePriceDao changePriceDao;

    private ArrayList<Boolean> isCheck;
    private ArrayList<SpBean> spBillList;
    private ArrayList<Client> spSupplierList;
    private SpAdapter billNoSpAdapter;
    private SpBean spBean;
    private String oldBill = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String oldSupplier = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String clientId = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String clientName = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String companyName = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String companyId = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String dateStart = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String dateEnd = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String productName = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String productId = "";      //用于定位到编号时，刷新了列表，重新进入这个单号的筛选
    private String type = "1";
    @Override
    protected void initView() {
        setContentView(R.layout.activity_account_check);
        ButterKnife.bind(this);
        mActivity = this;

        tvTitle.setText("客户对账单");
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        tempChangePriceDao = daoSession.getTempChangePriceDao();
        changePriceDao = daoSession.getChangePriceDao();

        ryAcount.setAdapter(adapter = new AccountCheckAdapter(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        ryAcount.setLayoutManager(layoutManager);
//        ryAcount.setNestedScrollingEnabled(false);
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        isCheck = new ArrayList<>();
        spBillList = new ArrayList<>();

        clientId = Hawk.get(Config.AccountCheck_Client_ID_s,"");
        clientName = Hawk.get(Config.AccountCheck_Client_Name_s,"");
        companyId = Hawk.get(Config.AccountCheck_Company_ID_s,"");
        companyName = Hawk.get(Config.AccountCheck_Company_Name_s,"");
        dateStart = Hawk.get(Config.AccountCheck_StartTime_s,"");
        dateEnd = Hawk.get(Config.AccountCheck_EndTime_s,"");
        type = Hawk.get(Config.AccountCheck_Type_s,"1");
        etSupplier.setText(clientName);
        etCompany.setText(companyName);
        tvDateStart.setText(dateStart);
        tvDateEnd.setText(dateEnd);
        if ("1".equals(type)){
            cbAllMsg.setChecked(true);
        }else if ("2".equals(type)){
            cbAllMoney.setChecked(true);
        }else{
            cbHuiZong.setChecked(true);
        }
        loading();
    }


    @Override
    protected void initData() {
        setfocus(btnSupplier);
//        isFirst = true;
    }
    private void loading(){
        if ("".equals(etCompany.getText().toString())) {
            companyId = "";
        }
        if ("".equals(etSupplier.getText().toString())) {
            clientId = "";
        }
        if (!checkInput()){
            if (ryAcount!=null){
                ryAcount.setRefreshing(false);
            }
            return;
        }
//        Lg.e(type);
//        clientId = "001";
//        companyId = "001";
//        dateEnd = "2018-06-13";
//        dateStart = "2018-01-01";
//        type="1";
        LoadingUtil.showDialog(mContext,"正在加载...");
        AccountCheckBean bean = new AccountCheckBean();
        bean.clientId = clientId;
        bean.companyId = companyId;
        bean.dateStart = dateStart;
        bean.dateEnd = dateEnd;
        bean.resultNo = type;
        App.getRService().accountCheck(new Gson().toJson(bean), new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                final DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (dBean.accountCheckDatas.size() >= 1) {
                    if ("1".equals(dBean.accountCheckDatas.get(0).getBackDateType())) {
                        ryLltopA.setVisibility(View.VISIBLE);
                        ryLltopB.setVisibility(View.GONE);
                        ryLltopC.setVisibility(View.GONE);
                    }else if ("2".equals(dBean.accountCheckDatas.get(0).getBackDateType())){
                        ryLltopA.setVisibility(View.GONE);
                        ryLltopB.setVisibility(View.VISIBLE);
                        ryLltopC.setVisibility(View.GONE);
                    }else{
                        ryLltopA.setVisibility(View.GONE);
                        ryLltopB.setVisibility(View.GONE);
                        ryLltopC.setVisibility(View.VISIBLE);
                    }
                    Lg.e(new Gson().toJson(dBean.accountCheckDatas));
                    adapter.clear();
                    adapter.addAll(dBean.accountCheckDatas);
                    tvNum.setText("共有数据：" + dBean.accountCheckDatas.size());

                    Hawk.put(Config.AccountCheck_Client_ID_s,clientId);
                    Hawk.put(Config.AccountCheck_Client_Name_s,clientName);
                    Hawk.put(Config.AccountCheck_Company_ID_s,companyId);
                    Hawk.put(Config.AccountCheck_Company_Name_s,companyName);
                    Hawk.put(Config.AccountCheck_StartTime_s,dateStart);
                    Hawk.put(Config.AccountCheck_EndTime_s,dateEnd);
                    Hawk.put(Config.AccountCheck_Type_s,type);

                } else {
                    tvNum.setText("无数据");

//                    tvMsg.setText("无数据");
                    adapter.clear();
                }
                LoadingUtil.closeDialog();
            }

            @Override
            public void onError(Throwable e) {
                Lg.e("请求错误");
                Toast.showText(mContext,"网络请求超时，请重试...");
                adapter.clear();
                LoadingUtil.closeDialog();
            }
        });
    }

    private boolean checkInput() {
        if (!cbAllMoney.isChecked() && !cbAllMsg.isChecked() && !cbHuiZong.isChecked()) {
            Toast.showText(mContext, "请选择类型");
            return false;
        }
        if ("".equals(dateStart) || "".equals(dateEnd)) {
            Toast.showText(mContext, "请选择时间段");
            return false;
        }
        if (cbHuiZong.isChecked()) {
            type = "3";
            return true;
        }

        if ("".equals(clientId)) {
            Toast.showText(mContext, "请选择客户");
            return false;
        }
        if ("".equals(companyId)) {
            Toast.showText(mContext, "请选择公司");
            return false;
        }

        if ("".equals(companyId)) {
            Toast.showText(mContext, "请选择公司");
            return false;
        }

        if (cbAllMsg.isChecked()) {
            type = "1";
        }
        if (cbAllMoney.isChecked()) {
            type = "2";
        }

        return true;
    }


    private AccountCheckData items;
    private TempChangePrice tempChangePrice;
    private boolean isShow = true;

    @Override
    protected void initListener() {
//        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Lg.e("adapter点击数据" + adapter.getAllData().get(position).toString());
//                items = adapter.getAllData().get(position);
//                AlertDialog.Builder change = new AlertDialog.Builder(mContext);
//                change.setMessage("原价：" + items.getFPriceOld());
//                change.setTitle("修改数据");
//                View changeview = LayoutInflater.from(mContext).inflate(R.layout.item_change_price_dg, null);
//                final EditText price = changeview.findViewById(R.id.et_price);
//                change.setView(changeview);
//                change.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int position) {
//                        AddTempOrder(price.getText().toString());
//                    }
//                });
//                change.setNegativeButton("取消", null);
//                change.create().show();
//            }
//        });

        cbAllMsg.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    cbAllMoney.setChecked(false);
                    cbHuiZong.setChecked(false);
                    loading();
                }
            }
        });
        cbAllMoney.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    cbAllMsg.setChecked(false);
                    cbHuiZong.setChecked(false);
                    loading();
                }

            }
        });
        cbHuiZong.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    cbAllMsg.setChecked(false);
                    cbAllMoney.setChecked(false);
                    loading();
                }

            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Lg.e(ryLltopC.getVisibility()+":第三种布局");
                if (ryLltopC.getVisibility()==View.VISIBLE){
                    AccountCheckData data=adapter.getAllData().get(position);
                    clientId = data.getClientCode();
                    clientName = data.getClientName();
                    etSupplier.setText(clientName);
                    companyId = data.getCompanyCode();
                    companyName = data.getCompanyName();
                    etCompany.setText(companyName);
                    cbAllMsg.setChecked(true);
                    LoadingUtil.closeDialog();
                    loading();
                }
            }
        });
    }



    @OnClick({R.id.btn_back, R.id.tv_right, R.id.btn_supplier, R.id.btn_company, R.id.tv_date_start, R.id.tv_date_start_clear, R.id.tv_date_end, R.id.tv_date_end_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_supplier:
                Bundle b = new Bundle();
                b.putString("search", etSupplier.getText().toString());
                b.putInt("where", Info.SEARCHCLIENT);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTCLIRNT, b);
                break;
            case R.id.btn_company:
                Bundle b2 = new Bundle();
                b2.putString("search", etCompany.getText().toString());
                b2.putInt("where", Config.Search_Company);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Config.Search_Company, b2);
                break;
            case R.id.tv_date_start:
                getStartDate();
                break;
            case R.id.tv_date_end:
                getEndDate();
                break;
            case R.id.tv_date_start_clear:
                dateStart = "";
                tvDateStart.setText("");
                break;
            case R.id.tv_date_end_clear:
                dateEnd = "";
                tvDateEnd.setText("");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("code", requestCode + "" + "    " + resultCode);
        if (requestCode == Info.SEARCHFORRESULTCLIRNT) {
            if (resultCode == Info.SEARCHFORRESULTCLIRNT) {
                Bundle b = data.getExtras();
                clientId = b.getString("003");
                clientName = b.getString("002");
                etSupplier.setText(clientName);
                loading();
            }
        } else if (requestCode == Config.Search_Company) {
            if (resultCode == Config.Search_Company) {
                Bundle b = data.getExtras();
                companyId = b.getString("003");
                companyName = b.getString("002");
                etCompany.setText(companyName);
                loading();
            }
        }
    }


    private int year;
    private int month;
    private int day;

    private void getStartDate() {
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
                dateStart = year + "-" + ((month < 10) ? "0" + (month + 1) : (month + 1)) + "-" + ((day < 10) ? "0" + day : day);
                tvDateStart.setText(dateStart);
                loading();
                datePickerDialog.dismiss();

            }
        });
        datePickerDialog.show();
    }

    private void getEndDate() {
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
                dateEnd = year + "-" + ((month < 10) ? "0" + (month + 1) : (month + 1)) + "-" + ((day < 10) ? "0" + day : day);
                tvDateEnd.setText(dateEnd);
                loading();
                datePickerDialog.dismiss();


            }
        });
        datePickerDialog.show();
    }


    public ExecutorService getEx() {
        return es;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void OnReceive(String code) {

    }


}
