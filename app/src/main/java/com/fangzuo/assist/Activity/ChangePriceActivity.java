package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.miniprinter.App;
import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.ChangePriceAdapter;
import com.fangzuo.assist.Adapter.ChangePriceShowAdapter;
import com.fangzuo.assist.Adapter.SpAdapter;
import com.fangzuo.assist.Beans.ChangePriceFindBean;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.PurchaseInStoreUploadBean;
import com.fangzuo.assist.Dao.ChangePrice;
import com.fangzuo.assist.Dao.Client;
import com.fangzuo.assist.Dao.Product;
import com.fangzuo.assist.Dao.SpBean;
import com.fangzuo.assist.Dao.TempChangePrice;
import com.fangzuo.assist.R;
import com.fangzuo.assist.RxSerivce.MySubscribe;
import com.fangzuo.assist.Utils.CommonUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.greendao.gen.ChangePriceDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.TempChangePriceDao;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePriceActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_num_show)
    TextView tvNumShow;
    @BindView(R.id.ry_change_price)
    EasyRecyclerView ryChange;
    @BindView(R.id.ry_change_price_show)
    EasyRecyclerView ryChangeShow;
    @BindView(R.id.ll_show)
    LinearLayout llShow;
    @BindView(R.id.btn_push)
    Button btnPush;
    @BindView(R.id.sp_bill)
    Spinner spBill;
    @BindView(R.id.sp_supplier)
    Spinner spSupplier;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.ll_findview)
    LinearLayout llFindView;
    @BindView(R.id.et_supplier)
    EditText etSupplier;
    @BindView(R.id.btn_supplier)
    Button btnSupplier;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.btn_company)
    Button btnCompany;
    @BindView(R.id.sp_company)
    Spinner spCompany;
    @BindView(R.id.tv_date_start)
    TextView tvDateStart;
    @BindView(R.id.tv_date_end)
    TextView tvDateEnd;
    @BindView(R.id.et_billno)
    EditText etBillno;
    @BindView(R.id.tv_date_start_clear)
    TextView tvDateStartClear;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_date_end_clear)
    TextView tvDateEndClear;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.rl_search)
    RelativeLayout search;
    private ChangePriceAdapter adapter;
    private ChangePriceShowAdapter adapterShow;
    ExecutorService es = Executors.newScheduledThreadPool(30);
    ChangePriceActivity mActivity;
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

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_price);
        ButterKnife.bind(this);
        mActivity = this;

        tvTitle.setText("修改价格");
        tvRight.setText("查看修改历史");

        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        tempChangePriceDao = daoSession.getTempChangePriceDao();
        changePriceDao = daoSession.getChangePriceDao();

        ryChange.setAdapter(adapter = new ChangePriceAdapter(this));
        ryChange.setLayoutManager(new LinearLayoutManager(this));
        ryChangeShow.setAdapter(adapterShow = new ChangePriceShowAdapter(this));
        ryChangeShow.setLayoutManager(new LinearLayoutManager(this));
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        isCheck = new ArrayList<>();
        spBillList = new ArrayList<>();

    }

    @Override
    protected void initData() {
        setfocus(btnSupplier);
//        isFirst = true;
            loading();

    }
    private void loading(){
        if ("".equals(etCompany.getText().toString())) {
            companyId = "";
        }
        if ("".equals(etSupplier.getText().toString())) {
            clientId = "";
        }
        if ("".equals(edCode.getText().toString())) {
            productId = "";
        }
        if ("".equals(dateStart) && !"".equals(dateEnd)) {
            Toast.showText(mContext, "请选择完整时间段");
            return;
        }
        if (!"".equals(dateStart) && "".equals(dateEnd)) {
            Toast.showText(mContext, "请选择完整时间段");
            return;
        }
        ChangePriceFindBean bean = new ChangePriceFindBean();
        bean.clientId = clientId;
        bean.companyId = companyId;
        bean.dateStart = dateStart;
        bean.dateEnd = dateEnd;
        bean.FProductId = productId;
        App.getRService().getChangePrice(new Gson().toJson(bean), new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                final DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (dBean.changePrices.size() >= 1) {
                    adapter.clear();
                    adapter.addAll(dBean.changePrices);
                    tvNum.setText("共有数据：" + dBean.changePrices.size());
//                    initSpinnerDate();
                    //存入本地数据
//                    changePriceDao.deleteAll();
//                    changePriceDao.insertOrReplaceInTx(dBean.changePrices);
//                    changePriceDao.detachAll();
//                    //刷新列表时，重新回到刚才筛选的单号的数据
//                    refreshAdapter();
                } else {
//                    tvMsg.setText("无数据");
                    adapter.clear();
                    tvNum.setText("无数据");
                }
            }

            @Override
            public void onError(Throwable e) {
                Lg.e("请求错误");

            }
        });
    }
//    private boolean checkInput() {
//
//    }
//    //初始化获取所有的单据编号，客户，公司信息到spinner
//    private void initSpinnerDate() {
//        TreeSet<String> billNo = new TreeSet<>();
//        TreeSet<String> supplierTree = new TreeSet<>();
//        for (int i = 0; i < adapter.getAllData().size(); i++) {
//            billNo.add(adapter.getAllData().get(i).FBillNo);
//            supplierTree.add(adapter.getAllData().get(i).FSupplier);
//        }
//        spBillList.clear();
//        spSupplierList.clear();
//        spBillList.add(new SpBean("", ""));
//        spSupplierList.add(new SpBean("", ""));
//        for (String string : billNo) {
//            spBillList.add(new SpBean("", string));
//        }
//        for (String string : supplierTree) {
//            spSupplierList.add(new SpBean("", string));
//        }
//        billNoSpAdapter = new SpAdapter(mContext, spBillList);
//        supplierSpAdapter = new SpAdapter(mContext, spSupplierList);
//        spBill.setAdapter(billNoSpAdapter);
//        spSupplier.setAdapter(supplierSpAdapter);
//    }


    private ChangePrice items;
    private TempChangePrice tempChangePrice;
    private boolean isShow = true;

    @Override
    protected void initListener() {
        ryChange.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Lg.e("adapter点击数据" + adapter.getAllData().get(position).toString());
                items = adapter.getAllData().get(position);
                AlertDialog.Builder change = new AlertDialog.Builder(mContext);
                change.setMessage("原价：" + items.getFPriceOld());
                change.setTitle("修改数据");
                View changeview = LayoutInflater.from(mContext).inflate(R.layout.item_change_price_dg, null);
                final EditText price = changeview.findViewById(R.id.et_price);
                final CheckBox checkBox = changeview.findViewById(R.id.cb_has_shui);
                change.setView(changeview);
                if ("含税".equals(items.getFHasTax())){
                    checkBox.setChecked(true);
                }
                change.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        if (checkBox.isChecked()){
                            Lg.e("含税");
                            AddTempOrder(price.getText().toString(),"0");
                        }else{
                            AddTempOrder(price.getText().toString(),"1");
                            Lg.e("不含税");
                        }
                    }
                });
                change.setNegativeButton("取消", null);
                change.create().show();
            }
        });

        ryChange.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadByOrderId("");
            }
        });

//        spBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                SpBean spBean = (SpBean) billNoSpAdapter.getItem(i);
//                oldBill = spBean.spName;
//                if (!isFirst) {
//                    refreshAdapter();
//                } else {
//                    isFirst = false;
//                }
//                Lg.e(isFirst + "");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        spSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Client spBean = (Client) supplierSpAdapter.getItem(i);
//                clientName = spBean.FName;
//                clientId = spBean.FItemID;
//                if (!isFirst2) {
//                    refreshAdapter();
//                } else {
//                    isFirst2 = false;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }

//    private boolean isFirst = true;   //用于首次进入，不去筛选单号
//    private boolean isFirst2 = true;   //用于首次进入，不去筛选单号

    private void refreshAdapter() {
        if ("".equals(oldBill) && "".equals(oldSupplier)) {
            adapter.clear();
            adapter.addAll(changePriceDao.loadAll());
        } else if (!"".equals(oldBill) && !"".equals(oldSupplier)) {
            adapter.clear();
            adapter.addAll(changePriceDao.queryBuilder().where(
                    ChangePriceDao.Properties.FBillNo.eq(oldBill),
                    ChangePriceDao.Properties.FSupplier.eq(oldSupplier)
            ).build().list());
        } else if ("".equals(oldBill) && !"".equals(oldSupplier)) {
            adapter.clear();
            adapter.addAll(changePriceDao.queryBuilder().where(
                    ChangePriceDao.Properties.FSupplier.eq(oldSupplier)
            ).build().list());
        } else if (!"".equals(oldBill) && "".equals(oldSupplier)) {
            adapter.clear();
            adapter.addAll(changePriceDao.queryBuilder().where(
                    ChangePriceDao.Properties.FBillNo.eq(oldBill)
            ).build().list());
        }

//        //定位spinner到之前的单号
//        for (int i = 0; i < supplierSpAdapter.getCount(); i++) {
//            if (oldSupplier.equals(((SpBean) supplierSpAdapter.getItem(i)).spName)) {
//                spSupplier.setSelection(i);
//            }
//        }
//        //定位spinner到之前的单号
//        for (int i = 0; i < billNoSpAdapter.getCount(); i++) {
//            if (oldBill.equals(((SpBean) billNoSpAdapter.getItem(i)).spName)) {
//                spBill.setSelection(i);
//            }
//        }
    }

    //刷新列表时，重新回到刚才筛选的单号的数据
    private void refreshAdapter(String oldBill) {
        if ("".equals(oldBill)) {
            return;
        }
        adapter.clear();
        adapter.addAll(changePriceDao.queryBuilder().where(
                ChangePriceDao.Properties.FBillNo.eq(oldBill)
        ).build().list());
        //定位spinner到之前的单号
        for (int i = 0; i < billNoSpAdapter.getCount(); i++) {
            if (oldBill.equals(((SpBean) billNoSpAdapter.getItem(i)).spName)) {
                spBill.setSelection(i);
            }
        }
    }

    //筛选修改的历史记录，按修改先后排序
    private void loadPriceChange() {
        List<TempChangePrice> details = tempChangePriceDao.queryBuilder()
                .orderDesc(TempChangePriceDao.Properties._id)
                .build().list();
        Lg.e("prici" + details.toString());
        adapterShow.clear();
        adapterShow.addAll(details);
        tvNumShow.setText("修改记录:" + details.size() + " 条");
//        for (int i = 0; i < details.size(); i++) {
//            isCheck.add(false);
//        }
    }

    private List<TempChangePrice> pushList = new ArrayList<>();

    public String AddPushTemp(TempChangePrice tempChangePrice, int position) {
//        if (isCheck.get(position)) {
//            isCheck.set(position, false);
//        } else {
//            isCheck.set(position, true);
//        }
//        adapterShow.addCheck(isCheck);
        if (pushList.size() > 0) {
            boolean has = false;
            for (int i = 0; i < pushList.size(); i++) {
                if (tempChangePrice.FInterID.equals(pushList.get(i).FInterID) && tempChangePrice.FEntryID.equals(pushList.get(i).FEntryID)) {
                    pushList.remove(pushList.get(i));
                    has = true;
                }
            }
            if (!has) {
                pushList.add(tempChangePrice);
            }
        } else {
            pushList.add(tempChangePrice);
        }

        Lg.e("pushData.size:" + pushList.size());
        Lg.e("pushData:" + pushList.toString());
        return "添加完成";
    }

    public void AddTempOrder(String price,String hasTax) {
//        List<TempMain> tempMains = tempMainDao.loadAll();
//        if (tempMains.size() > 0) {
//            tempMainDao.deleteAll();
//        }
//        TempMain tempMain = new TempMain();
//        tempMain.activity = Config.CHOOSE_SECOND_FRAGMENT_SHORT;
//        tempMain.orderID = ordercode;
//        tempMain.FIndex = getTimesecond();
//        tempMain.FDepartment = departmentName == null ? "" : departmentName;
//        tempMain.FDepartmentId = departmentId == null ? "" : departmentId;
//        tempMain.FPaymentDate = tvDatePay.getText().toString();
//        tempMain.orderDate = tvDate.getText().toString();
//        tempMain.FPurchaseUnit = unitName == null ? "" : unitName;
//        tempMain.FSalesMan = employeeName == null ? "" : employeeName;
//        tempMain.FSalesManId = employeeId == null ? "" : employeeId;
//        tempMain.FMaker = share.getUserName();
//        tempMain.FMakerId = share.getsetUserID();
//        tempMain.FDirector = ManagerName == null ? "" : ManagerName;
//        tempMain.FDirectorId = ManagerId == null ? "" : ManagerId;
//        tempMain.saleWay = SaleMethodName == null ? "" : SaleMethodName;
//        tempMain.FDeliveryAddress = "";
//        tempMain.FRemark = edZhaiyao.getText().toString();
//        tempMain.saleWayId = SaleMethodId == null ? "" : SaleMethodId;
//        tempMain.FCustody = saleRangeName == null ? "" : saleRangeName;
//        tempMain.FCustodyId = saleRangeId == null ? "" : saleRangeId;
//        tempMain.FAcount = sendMethodName == null ? "" : sendMethodName;
//        tempMain.FAcountID = sendMethodId == null ? "" : sendMethodId;
//        tempMain.FRedBlue = redblue == null ? "蓝字" : redblue;
//        tempMain.Rem = edZhaiyao.getText().toString();
//        tempMain.supplier = clientName == null ? "" : clientName;
//        tempMain.supplierId = clientId == null ? "" : clientId;
//        tempMain.FSendOutId = payTypeId == null ? "" : payTypeId;
//        tempMain.sourceOrderTypeId = yuandanID == null ? "" : yuandanID;
//        tempMain.VanNo = etVanNo.getText().toString();
//        tempMain.VanDriver = etDriver.getText().toString();
//        tempMain.companyId = CompanyId==null?"":CompanyId;
//        long insert = tempMainDao.insert(tempMain);

        TempChangePrice tempDetail;
//        List<TempChangePrice> list = tempChangePriceDao.queryBuilder().where(
//                TempChangePriceDao.Properties.FInterID.eq(items.FInterID),
//                TempChangePriceDao.Properties.FEntryID.eq(items.FEntryID)
//        ).build().list();
//        if (list.size() > 0) {
//            list.get(0).FChangePrice = price;
//            tempChangePriceDao.update(list.get(0));
//            tempDetail = list.get(0);
//        } else {
        tempDetail = new TempChangePrice();
        tempDetail.FItemID = items.FItemID;
        tempDetail.FInterID = items.FInterID;
        tempDetail.FChangePrice = price;
        tempDetail.FEntryID = items.FEntryID;

        tempDetail.FDate = items.FDate;
        tempDetail.FName = items.FName;
        tempDetail.FPriceOld = items.FPriceOld;
        tempDetail.FSupplier = items.FSupplier;
        tempDetail.FNumber = items.FNumber;
        tempDetail.FModel = items.FModel;
        tempDetail.FBillNo = items.FBillNo;
        tempDetail.FSpNo = items.FSpNo;
        tempDetail.hasTax = hasTax.equals("0")?"含税":"不含税";
        tempDetail.ChangeDate = CommonUtil.getLongTime();
        Lg.e("TempChangePrice:" + tempDetail.toString());
        tempChangePriceDao.insert(tempDetail);
//        }


        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
        PurchaseInStoreUploadBean.purchaseInStore puBean = pBean.new purchaseInStore();
        ArrayList<String> detailContainer = new ArrayList<>();
        ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data = new ArrayList<>();
        detailContainer.clear();
        String main;
        String detail = "";
        puBean.main = "";
        detail = detail +
                tempDetail.FItemID + "|" +
                tempDetail.FInterID + "|" +
                tempDetail.FChangePrice + "|" +
                tempDetail.FEntryID + "|" +
                hasTax + "|"
        ;
        detailContainer.add(detail);
        puBean.detail = detailContainer;
        data.add(puBean);
        pBean.list = data;
        String json = new Gson().toJson(pBean);
        Lg.e("Push 数据:" + json);
        App.getRService().pushChangePrice(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                Lg.e("上传成功");
                initData();
//                refreshAdapter(oldBill);
            }

            @Override
            public void onError(Throwable e) {
                Lg.e("上传失败");
            }
        });

//        MediaPlayer.getInstance(mContext).ok();
//        VibratorUtil.Vibrate(mContext, 200);
//        return "添加完成";
    }

    private void uploadByOrderId(String orderId) {
        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
        PurchaseInStoreUploadBean.purchaseInStore puBean = pBean.new purchaseInStore();
//        t_mainDao = daoSession.getT_mainDao();
//        t_detailDao = daoSession.getT_DetailDao();
        ArrayList<String> detailContainer = new ArrayList<>();
        ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data = new ArrayList<>();
//        List<T_main> mains = t_mainDao.queryBuilder().where(
//                T_mainDao.Properties.OrderId.eq(orderId),
//                T_mainDao.Properties.Activity.eq(Config.CHOOSE_SECOND_FRAGMENT_SHORT)
//        ).build().list();
//        for (int i = 0; i < mains.size(); i++) {
//            if (i > 0 && mains.get(i).orderId == mains.get(i - 1).orderId) {
//            } else {
        detailContainer.clear();
        String main;
        String detail = "";
//                T_main t_main = mains.get(i);
//                main = t_main.FMakerId + "|" +
//                        t_main.orderDate + "|" +
//                        t_main.FPaymentDate + "|" +
//                        t_main.saleWayId + "|" +
//                        t_main.FDeliveryAddress + "|" +
//                        t_main.FDepartmentId + "|" +
//                        t_main.FSalesManId + "|" +
//                        t_main.FDirectorId + "|" +
//                        t_main.supplierId + "|" +
//                        t_main.Rem + "|" +
//                        t_main.FSalesManId + "|" +
//                        t_main.sourceOrderTypeId + "|" +
//                        t_main.FRedBlue + "|" +
//                        t_main.FCustodyId + "|" +
//                        t_main.sourceOrderTypeId + "|"+
//                        t_main.VanDriver+"|"+
//                        t_main.VanNo+"|"+
//                        t_main.orderId+"|"+
//                        t_main.companyId+"|";
        puBean.main = "";
//                List<TempChangePrice> details = tempChangePriceDao.queryBuilder().where(
//                        TempChangePriceDao.Properties.FItemID.eq(orderId),
//                        TempChangePriceDao.Properties.FInterID.eq(orderId)
//                ).build().list();
//                List<TempChangePrice> details =tempChangePriceDao.loadAll();
        List<TempChangePrice> details = pushList;
        for (int j = 0; j < details.size(); j++) {
            if (j != 0 && j % 49 == 0) {
                Log.e("j%49", j % 49 + "");
                TempChangePrice t_detail = details.get(j);
                detail = detail +
                        t_detail.FItemID + "|" +
                        t_detail.FInterID + "|" +
                        t_detail.FChangePrice + "|" +
                        t_detail.FEntryID + "|"
                ;
                detail = detail.subSequence(0, detail.length() - 1).toString();
                detailContainer.add(detail);
                detail = "";
            } else {
                Log.e("j", j + "");
                Log.e("details.size()", details.size() + "");
                TempChangePrice t_detail = details.get(j);
                detail = detail +
                        t_detail.FItemID + "|" +
                        t_detail.FInterID + "|" +
                        t_detail.FChangePrice + "|" +
                        t_detail.FEntryID + "|"
                ;
                Log.e("detail1", detail);
//                    }

//                }

            }
        }
        //去掉最后一个竖杠
        if (detail.length() > 0) {
            detail = detail.subSequence(0, detail.length() - 1).toString();
        }

        Log.e("总detail", detail);
        detailContainer.add(detail);
        puBean.detail = detailContainer;
        data.add(puBean);

//        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
        pBean.list = data;
        String json = new Gson().toJson(pBean);
        Lg.e("Push 数据:" + json);
        App.getRService().pushChangePrice(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
//        postToServerByOrderId(data,orderId);
    }

//    private void postToServerByOrderId(ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data, final String orderId) {
//
//        Asynchttp.post(mContext, getBaseUrl() + WebApi.UPLOADSOUT, gson.toJson(pBean), new Asynchttp.Response() {
//            @Override
//            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
//                MediaPlayer.getInstance(mContext).ok();
//                Toast.showText(mContext, "上传成功");
//                t_detailDao.deleteInTx(t_detailDao.queryBuilder().where(
//                        T_DetailDao.Properties.FOrderId.eq(orderId),
//                        T_DetailDao.Properties.Activity.eq(Config.CHOOSE_SECOND_FRAGMENT_SHORT)
//                ).build().list());
//                t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
//                        T_mainDao.Properties.OrderId.eq(orderId),
//                        T_mainDao.Properties.Activity.eq(Config.CHOOSE_SECOND_FRAGMENT_SHORT)
//                ).build().list());
//
//                isc=true;
//                getHuiDanData();
//
////                padapter.notifyDataSetChanged();
//                canclePg.dismiss();
//                btnBackorder.performClick();
//                finishOrder();
//            }
//
//            @Override
//            public void onFailed(String Msg, AsyncHttpClient client) {
//                isc=true;
//                canclePg.dismiss();
//
//                MediaPlayer.getInstance(mContext).error();
//                Toast.showText(mContext, Msg);
//            }
//        });
//    }


    private Client client;
    private LayoutInflater inflater;

    @OnClick({R.id.btn_back, R.id.tv_right, R.id.btn_supplier, R.id.btn_company, R.id.tv_date_start, R.id.tv_date_end, R.id.tv_date_start_clear, R.id.tv_date_end_clear, R.id.tv_billno_clear,R.id.rl_search})
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
            case R.id.rl_search:
                Log.e("search", "onclick");
                Bundle b1 = new Bundle();
                String sech="";
                sech=edCode.getText().toString();
                b1.putString("search", sech);
                Log.e("search", "onclick"+sech);
                b1.putInt("where", Info.SEARCHPRODUCT);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULT, b1);
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
                loading();
                break;
            case R.id.tv_date_end_clear:
                dateEnd = "";
                tvDateEnd.setText("");
                loading();
                break;
            case R.id.tv_billno_clear:
                etBillno.setText("");
                break;
            case R.id.tv_right:
                if (isShow) {
                    tvRight.setText("返回");
                    tvTitle.setText("修改历史");
                    btnBack.setVisibility(View.GONE);
                    ryChange.setVisibility(View.GONE);
                    llShow.setVisibility(View.VISIBLE);
                    loadPriceChange();
                    isShow = false;
                } else {
                    btnBack.setVisibility(View.VISIBLE);
                    tvRight.setText("查看修改历史");
                    tvTitle.setText("修改价格");
                    llShow.setVisibility(View.GONE);
                    ryChange.setVisibility(View.VISIBLE);
                    initData();
                    isShow = true;
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("code", requestCode + "" + "    " + resultCode);
        if (requestCode == Info.SEARCHFORRESULTCLIRNT) {
            if (resultCode == Info.SEARCHFORRESULTCLIRNT) {
                Bundle b = data.getExtras();
                clientId = b.getString("001");
                clientName = b.getString("002");
                etSupplier.setText(clientName);
                loading();
            }
        } else if (requestCode == Config.Search_Company) {
            if (resultCode == Config.Search_Company) {
                Bundle b = data.getExtras();
                companyId = b.getString("001");
                companyName = b.getString("002");
                etCompany.setText(companyName);
                loading();
            }
        } else if (requestCode == Info.SEARCHFORRESULT) {
            if (resultCode == 9998) {
//                Bundle b = data.getExtras();
//                product = (Product) b.getSerializable("001");
                Product product = Hawk.get(Config.For_Back_Data_InStorageNum, null);
                Lg.e("返回Product：" + product.toString());
                if (product!=null){
                    productId = product.FItemID;
                    productName = product.FName;
                    edCode.setText(productName);
                    loading();
                }
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
