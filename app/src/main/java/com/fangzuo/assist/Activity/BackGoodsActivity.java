package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.BillRvAdapter;
import com.fangzuo.assist.Adapter.NumRvAdapter;
import com.fangzuo.assist.Adapter.NumSpAdapter;
import com.fangzuo.assist.Adapter.Padapter;
import com.fangzuo.assist.Adapter.PiciSpAdapter;
import com.fangzuo.assist.Adapter.ProductLongSpAdapter;
import com.fangzuo.assist.Adapter.ProductSpAdapter;
import com.fangzuo.assist.Adapter.ProductselectAdapter;
import com.fangzuo.assist.Adapter.ProductselectAdapter1;
import com.fangzuo.assist.Adapter.StorageSpAdapter;
import com.fangzuo.assist.Adapter.UnitSpAdapter;
import com.fangzuo.assist.Adapter.WaveHouseSpAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Dao.BackGoodsBean;
import com.fangzuo.assist.Dao.BarCode;
import com.fangzuo.assist.Dao.Classification;
import com.fangzuo.assist.Dao.Product;
import com.fangzuo.assist.Dao.Storage;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.Dao.T_main;
import com.fangzuo.assist.Dao.TempDetail;
import com.fangzuo.assist.Dao.WaveHouse;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Service.DeleteService;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.CalculateUtil;
import com.fangzuo.assist.Utils.CommonMethod;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.DoubleUtil;
import com.fangzuo.assist.Utils.EventBusInfoCode;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.MediaPlayer;
import com.fangzuo.assist.Utils.PgUtil;
import com.fangzuo.assist.Utils.RecyclerViewDivider;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.VibratorUtil;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.greendao.gen.BackGoodsBeanDao;
import com.fangzuo.greendao.gen.BarCodeDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.ProductDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.fangzuo.greendao.gen.TempDetailDao;
import com.fangzuo.greendao.gen.TempMainDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BackGoodsActivity extends BaseActivity implements BillRvAdapter.RvInnerClickListener {

    @BindView(R.id.rv_bill)
    RecyclerView rvBill;
    @BindView(R.id.rv_numChoose)
    RecyclerView rvNumChoose;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_finishorder)
    Button btnFinishorder;
    @BindView(R.id.btn_backorder)
    Button btnBackorder;
    @BindView(R.id.btn_checkorder)
    Button btnCheckorder;

    @BindView(R.id.genshu)
    TextView genshu;
    @BindView(R.id.tiji)
    TextView tiji;
    @BindView(R.id.tv_avg)
    TextView tvAvg;
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_orderID)
    TextView tvOrderId;
    @BindView(R.id.tv_driver)
    TextView tvDriver;
    @BindView(R.id.tv_VanNo)
    TextView tvVanNo;
    @BindView(R.id.sp_send_storage)
    Spinner spSendStorage;
    @BindView(R.id.sp_wavehouse)
    Spinner spWavehouse;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.sp_product)
    Spinner spProduct;
    @BindView(R.id.sp_num)
    Spinner spNum;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    private BackGoodsActivity mContext;
    private DecimalFormat df;
    private DaoSession daoSession;
    private int year;
    private int month;
    private int day;
    private CommonMethod method;
    private String departmentId;
    private String departmentName;
    private String SaleMethodId;
    private String SaleMethodName;

    private String CompanyId;
    private String CompanyName;

    private String saleRangeId;
    private String saleRangeName;
    private String yuandanID;
    private String yuandanName;
    private String payTypeId;
    private String payTypeName;
    private String employeeId;
    private String employeeName;
    private String ManagerId;
    private String ManagerName;
    private String sendMethodId;
    private String sendMethodName;
    private List<Product> products;
    private UnitSpAdapter unitAdapter;
    private String unitId;
    private String unitName;
    private double unitrate;
    private String pihao;
    private Storage storage;
    private String storageId;
    private String storageName;
    private String wavehouseID = "0";
    private String wavehouseName;
    private String clientId;
    private String clientName;
    private String date;
    private String datePay;
    public static final String TAG = "SOLDOUT";
    private Double qty;
    private T_mainDao t_mainDao;
    private T_DetailDao t_detailDao;
    private boolean isGetStorage;
    private Product product;
    private ProductselectAdapter productselectAdapter;
    private ProductselectAdapter1 productselectAdapter1;
    private PiciSpAdapter pici;

    private String default_unitID;
    private Double storenum;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<TempDetail> container;
    private BillRvAdapter billRvAdapter;
    private ArrayList<String> container_Width;
    private NumRvAdapter numRvAdapter;
    private TempDetailDao tempDetailDao;
    private TempMainDao tempMainDao;
    private int num;
    private PgUtil pgUtil;
    private Disposable disposable;
    private ProgressDialog pg;
    private String gotoPosition = "";
    private boolean isRed = false;
    private String redblue = "蓝字";
    private List<T_Detail> t_details;
    private List<T_main> mains;
    private String orderId = "";
    private int tag;
    private StorageSpAdapter storageSpAdapter;
    private WaveHouseSpAdapter waveHouseAdapter;
    private ProductSpAdapter productSpAdapter;
    private NumSpAdapter numSpAdapter;
    private String baoshu;
    private BackGoodsBeanDao backGoodsBeanDao;

    @Override
    public void initView() {
        setContentView(R.layout.activity_back_goods);
        mContext = this;
        ButterKnife.bind(mContext);
//        initDrawer(drawer);
        df = new DecimalFormat("######0.000");
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        tempMainDao = daoSession.getTempMainDao();
        tempDetailDao = daoSession.getTempDetailDao();
        t_detailDao = daoSession.getT_DetailDao();
        t_mainDao = daoSession.getT_mainDao();
        backGoodsBeanDao = daoSession.getBackGoodsBeanDao();

        method = CommonMethod.getMethod(mContext);
        Intent intent = getIntent();
        if (intent != null) {
            orderId =   intent.getStringExtra("orderid");
            tag =       intent.getIntExtra("activity", 0);
            clientName = intent.getStringExtra("clientName");
        }


//        initDrawer(drawer);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(mContext, 5);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.PRODUCTRETURN:
//                product = (Product) event.postEvent;
//                setDATA("", true);
                break;
        }
    }


    @Override
    public void initData() {
        container = new ArrayList<>();
        fContainer = new ArrayList<>();
        huidanList = new ArrayList<>();
        padapter = new Padapter(huidanList, mContext);

        //根数列表
        billRvAdapter = new BillRvAdapter(mContext, container,false);
        rvBill.setLayoutManager(mLinearLayoutManager);
        rvBill.setAdapter(billRvAdapter);
        rvBill.setItemAnimator(new DefaultItemAnimator());
        rvBill.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        billRvAdapter.setInnerClickListener(this);
        billRvAdapter.notifyDataSetChanged();

        //径级列表
        container_Width = new ArrayList<>();
        numRvAdapter = new NumRvAdapter(mContext, container_Width);
        rvNumChoose.setAdapter(numRvAdapter);

        rvNumChoose.addItemDecoration(new RecyclerViewDivider(3));
        rvNumChoose.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 150; i++) {
            if (i % 2 == 0) container_Width.add(i + "");
        }
        numRvAdapter.notifyDataSetChanged();
        gridLayoutManager.scrollToPosition(24);

        mains = t_mainDao.queryBuilder().where(
                T_mainDao.Properties.Activity.eq(tag),
                T_mainDao.Properties.OrderId.eq(orderId)
        ).build().list();
        t_details = t_detailDao
                .queryBuilder()
                .where(T_DetailDao.Properties.Activity.eq(tag),
                        T_DetailDao.Properties.FOrderId.eq(orderId))
                .orderAsc(T_DetailDao.Properties.Diameter)
                .build()
                .list();
        Lg.e("aaaaat_details" + t_details.toString());
        Lg.e("aaaaamains" + mains.toString());
        if (mains.size()>0){
            tvTitle.setText("退单客户:" + clientName);
            tvOrderId.setText("订单号：" + orderId);
            tvDriver.setText(mains.get(0).VanDriver);
            tvVanNo.setText(mains.get(0).VanNo);
            clientName = mains.get(0).supplier;
        }else{
            Toast.showText(mContext,"获取表头信息错误");
        }

        tvRight.setText("完成");

        //删除存在的ReView数据
        daoSession.getBackGoodsBeanDao().deleteInTx(backGoodsBeanDao.queryBuilder().where(
                BackGoodsBeanDao.Properties.Activity.eq(tag)
        ).build().list());
//        final ArrayList<Storage> container = new ArrayList<>();
//        container.add(new Storage("","","","","","","","","",""));
//        final StorageSpAdapter storageSpAdapter = new StorageSpAdapter(context, container);
//        sp.setAdapter(storageSpAdapter);
//        container.addAll(dBean.storage);
//        storageSpAdapter.notifyDataSetChanged();
        List<TempDetail> tempDetails = tempDetailDao.queryBuilder().where(
                TempDetailDao.Properties.Activity.eq(tag)
        ).build().list();
        tempDetailDao.deleteAll();

        TreeSet<String> storageTree = new TreeSet<>();
        TreeSet<String> waveHouseTree = new TreeSet<>();
        TreeSet<String> productTree = new TreeSet<>();
        TreeSet<String> baoshuTree = new TreeSet<>();
//        List<TempDetail> tempDetails = new ArrayList<>();
        for (T_Detail item : t_details) {
            TempDetail tempDetail = new TempDetail();
            tempDetail.FIndex = getTimesecond();
            tempDetail.length = item.length;
            tempDetail.diameter = item.diameter;
            tempDetail.FOrderId = Long.parseLong(orderId);
            tempDetail.FProductCode = item.FProductCode;
            tempDetail.FProductId = item.FProductId;
            tempDetail.model = item.model;
            tempDetail.FProductName = item.FProductName;
            tempDetail.FUnitId = item.FUnitId;
            tempDetail.FUnit = item.FUnit;
            tempDetail.FStorage = item.FStorage;
            tempDetail.FStorageId = item.FStorageId;
            tempDetail.FPosition = item.FPosition;
            tempDetail.FPositionId = item.FPositionId;
            tempDetail.activity = tag;
            tempDetail.FDiscount = item.FDiscount;
            tempDetail.FQuantity = item.FQuantity;
            tempDetail.radical = item.radical;
            tempDetail.num = item.FIdentity;
            storageTree.add(item.FStorageId);//添加仓库
            waveHouseTree.add(item.FPositionId);//添加仓位
            productTree.add(item.FProductId);//添加物料
            baoshuTree.add(item.FIdentity);//添加报数
            tempDetail.unitrate = item.unitrate;
            tempDetail.FTaxUnitPrice = item.FTaxUnitPrice;
            tempDetail.clientName = item.clientName;
            Lg.e("数据tempDetail:" + tempDetail.toString());
            tempDetailDao.insert(tempDetail);
//            tempDetails.add(tempDetail);
        }
        storageSpAdapter = method.getStorageSpForBackGoods(spSendStorage, storageTree);
        waveHouseAdapter = method.getWaveHouseSpForBackGoods(spWavehouse, waveHouseTree);
        productSpAdapter = method.getProductSpForBackGoods(spProduct, productTree);

        List<String> strings = new ArrayList<>();
        for (Iterator iter = baoshuTree.iterator(); iter.hasNext(); ) {
            strings.add(iter.next().toString());
        }

        numSpAdapter = new NumSpAdapter(this, strings);
        spNum.setAdapter(numSpAdapter);
        RefreshList();
    }

    public static boolean isBack = true;

    @Override
    protected void onResume() {
        super.onResume();
//        if (Hawk.get(Config.has_Change_Bus_Short, false)) {
//
//            Hawk.put(Config.has_Change_Bus_Short, false);
//        }
//        if (isBack) {
//            clientId = share.getSClientID();
//            clientName = share.getSClient();
////            edClient.setText(clientName);
//        }
        getHuiDanData();
    }

//    private void LoadBasicData() {
//        storageSpAdapter = method.getStorageSpinner(spSendStorage);
//        slaesRange = method.getPurchaseRange(spSaleScope);
//        saleMethodSpAdapter = method.getSaleMethodSpinner(spSaleMethod);
//
//        companySpAdapter = method.getComanySpinner(spCompany);
//
//        payTypeSpAdapter = method.getpayType(spPayMethod);
//        departMentAdapter = method.getDepartMentAdapter(spDepartment);
//        employeeAdapter = method.getEmployeeAdapter(spEmployee);
//        managerAdapter = method.getMannagerAdapter(spManager);
//        yuandanAdapter = method.getYuandanAdapter(spYuandan);
//        getGoodsType = method.getGoodsTypes(spSendMethod);
//        method.getEmployeeAdapter(spManager);
//
//        spManager.setAdapter(managerAdapter);
//        spEmployee.setAdapter(employeeAdapter);
//        spYuandan.setAdapter(yuandanAdapter);
//        spCompany.setAdapter(companySpAdapter);
//
//        spDepartment.setSelection(share.getSOUTDepartment());
//
//        if (saleMethodSpAdapter!=null){
//            for (int j = 0; j < saleMethodSpAdapter.getCount(); j++) {
//                if (((PurchaseMethod) saleMethodSpAdapter.getItem(j)).getFName().equals("赊销")) {
//                    spSaleMethod.setSelection(j);
//                    share.setSOUTsaleMethod(j);
//                    break;
//                }
//            }
//        }
////        spSaleMethod.setSelection(share.getSOUTsaleMethod());
//        spCompany.setSelection(share.getShortTreeOutCompany());
//        spSaleScope.setSelection(share.getSoutSaleRange());
//        spYuandan.setSelection(share.getSOUTYuandan());
//        spPayMethod.setSelection(share.getSOUTPayMethod());
////        spEmployee.setSelection(share.getSOUTEmployee());
//        spManager.setSelection(share.getSOUTManager());
//        spSendMethod.setSelection(share.getSOUTsendmethod());
//
//
//        tvDate.setText(share.getSOUTdate());
//        tvDatePay.setText(share.getSOUTdatepay());
//    }

    @Override
    public void initListener() {
        spSendStorage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                storage = (Storage) storageSpAdapter.getItem(i);
//                waveHouseAdapter = CommonMethod.getMethod(mContext).getWaveHouseAdapter(storage, spWavehouse);
                wavehouseID = "0";
                storageId = storage.FItemID;
                storageName = storage.FName;
                Log.e("storageId", storageId);
                Log.e("storageName", storageName);
                RefreshList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spWavehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                WaveHouse waveHouse = (WaveHouse) waveHouseAdapter.getItem(i);
                wavehouseID = waveHouse.FSPID;
                wavehouseName = waveHouse.FName;
                Log.e("wavehouseName", wavehouseName);
                RefreshList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Product item = (Product) productSpAdapter.getItem(i);
                    product = item;


                Log.e("product", product.toString());
                RefreshList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) numSpAdapter.getItem(i);
                baoshu = item;
                Log.e("baoshu", item);
                RefreshList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        billRvAdapter.setOnItemClickListener(new BillRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, final int position) {
//                final TempDetailDao tempDetailDao = daoSession.getTempDetailDao();
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("确认退单么").setMessage("是否退单本条数据？\r\n" + "物料:" + billRvAdapter.getItems(position).FProductName + "\r\n"
                        + "径级:" + billRvAdapter.getItems(position).diameter + "\r\n" + "长度:" + billRvAdapter.getItems(position).length + "\r\n" +
                        "根数:" + billRvAdapter.getItems(position).radical + "\r\n" + "体积:" + billRvAdapter.getItems(position).FQuantity).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addBackGoodsReViewForClick(billRvAdapter.getItems(position));
                        tempDetailDao.delete(billRvAdapter.getItems(position));

                        RefreshList();
                    }
                }).setNegativeButton("取消", null).create().show();

            }
        });
        numRvAdapter.setOnItemClickListener(new NumRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, final int position) {
                if (product != null) {
                    if (!diameterTree.contains(numRvAdapter.getItem(position))) {
                        Toast.showText(mContext, "该径级未包含在列表中");
                        MediaPlayer.getInstance(mContext).error();
                        return;
                    }
                    pg = new ProgressDialog(mContext);
                    pg.setMessage("添加中...");
                    pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pg.setCancelable(false);
                    pg.show();
//                    rvNumChoose.setVisibility(View.GONE);
                    rvNumChoose.setClickable(false);
                    if ("添加完成".equals(AddTempOrder(numRvAdapter.getItem(position)))) {
                        RefreshList();
                        for (int j = 0; j < billRvAdapter.getItemCount(); j++) {
                            String posit = ((TempDetail) billRvAdapter.getItems(j)).diameter + "";
                            if (posit.equals(numRvAdapter.getItem(position))) {
//                                            Log.e("asdf",((TempDetail)billRvAdapter.getItems(j)).diameter+"--"+"j"+"---"+numRvAdapter.getItem(position));
                                MoveToPosition(mLinearLayoutManager, rvBill, j);
                                billRvAdapter.goToPostion(numRvAdapter.getItem(position), product.FModel,storageId,((TempDetail) billRvAdapter.getItems(j)).length);
                                break;
                            }
                        }
//                        rvNumChoose.setVisibility(View.VISIBLE);
                        rvNumChoose.setClickable(true);
                        pg.dismiss();
//                        llSearch.setClickable(false);
                    } else {
                        Toast.showText(mContext, "添加出错");
//                        rvNumChoose.setVisibility(View.VISIBLE);
                        rvNumChoose.setClickable(true);
                        pg.dismiss();
                    }


//                    Observable
//                            .create(new ObservableOnSubscribe<String>() {
//                                @Override
//                                public void subscribe(ObservableEmitter<String> e) throws Exception {
//                                    if (product.FM == null || product.FM.equals("")) {
//                                        e.onNext("物料信息中找不到长度");
//                                        MediaPlayer.getInstance(mContext).error();
//                                        e.onComplete();
//                                    } else {
//                                        e.onNext(AddTempOrder(numRvAdapter.getItem(position)));
//                                        e.onComplete();
//                                    }
//
//
//                                }
//                            })
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeOn(Schedulers.io())
//                            .subscribe(new Observer<String>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//                                    disposable = d;
//                                    pg = new ProgressDialog(mContext);
//                                    pg.setMessage("添加中...");
//                                    pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                                    pg.setCancelable(false);
//                                    pg.show();
//                                    rvNumChoose.setVisibility(View.GONE);
//                                }
//
//                                @Override
//                                public void onNext(String s) {
//                                    Log.e("rx", "onNext");
//                                    Toast.showText(mContext, s);
//
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    Log.e("rx", "Error");
//                                    rvNumChoose.setVisibility(View.VISIBLE);
////                                    Toast.showText(mContext, "出错了");
//                                    RefreshList();
//                                    e.printStackTrace();
//
//                                    pg.dismiss();
//                                }
//
//                                @Override
//                                public void onComplete() {
//                                    Log.e("rx", "onComplete");
////                                    billRvAdapter.goToPostion("4");
//                                    RefreshList();
//                                    for (int j = 0; j < billRvAdapter.getItemCount(); j++) {
//                                        String posit = ((TempDetail) billRvAdapter.getItems(j)).diameter + "";
//                                        if (posit.equals(numRvAdapter.getItem(position))) {
////                                            Log.e("asdf",((TempDetail)billRvAdapter.getItems(j)).diameter+"--"+"j"+"---"+numRvAdapter.getItem(position));
//                                            MoveToPosition(mLinearLayoutManager, rvBill, j);
//                                            billRvAdapter.goToPostion(numRvAdapter.getItem(position));
//                                            break;
//                                        }
//                                    }
//
//
//                                    rvNumChoose.setVisibility(View.VISIBLE);
//                                    pg.dismiss();
//
//                                }
//                            });
                } else {
                    Toast.showText(mContext, "未选择物料");
                }
            }
        });


    }

    @Override
    protected void OnReceive(String code) {
        setDATA(code, false);
    }

    private void getProductOL(DownloadReturnBean dBean, int j) {
        product = dBean.products.get(j);
        tvorisAuto(product);
    }

    private void setDATA(String fnumber, boolean flag) {
        default_unitID = null;
        if (flag) {
            tvorisAuto(product);
        } else {
            if (BasicShareUtil.getInstance(mContext).getIsOL()) {
                Asynchttp.post(mContext, getBaseUrl() + WebApi.SEARCHPRODUCTS, fnumber, new Asynchttp.Response() {
                    @Override
                    public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                        final DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                        if (dBean.products.size() == 1) {
                            getProductOL(dBean, 0);
                            default_unitID = dBean.products.get(0).FUnitID;
                        } else if (dBean.products.size() > 1) {
                            AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                            ab.setTitle("请选择物料");
                            View v = LayoutInflater.from(mContext).inflate(R.layout.pd_alert, null);
                            ListView lv = v.findViewById(R.id.lv_alert);
                            productselectAdapter1 = new ProductselectAdapter1(mContext, dBean.products);
                            lv.setAdapter(productselectAdapter);
                            ab.setView(v);
                            final AlertDialog alertDialog = ab.create();
                            alertDialog.show();
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    getProductOL(dBean, i);
                                    default_unitID = dBean.products.get(i).FUnitID;
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailed(String Msg, AsyncHttpClient client) {
                        Toast.showText(mContext, Msg);
                    }
                });
            } else {
                final ProductDao productDao = daoSession.getProductDao();
                BarCodeDao barCodeDao = daoSession.getBarCodeDao();
                final List<BarCode> barCodes = barCodeDao.queryBuilder().where(BarCodeDao.Properties.FBarCode.eq(fnumber)).build().list();

                if (barCodes.size() > 0) {
                    if (barCodes.size() == 1) {
                        products = productDao.queryBuilder().where(ProductDao.Properties.FItemID.eq(barCodes.get(0).FItemID)).build().list();
                        default_unitID = barCodes.get(0).FUnitID;
                        getProductOFL(products);
                    } else {
                        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                        ab.setTitle("请选择物料");
                        View v = LayoutInflater.from(mContext).inflate(R.layout.pd_alert, null);
                        ListView lv = v.findViewById(R.id.lv_alert);
                        productselectAdapter = new ProductselectAdapter(mContext, barCodes);
                        lv.setAdapter(productselectAdapter);
                        ab.setView(v);
                        final AlertDialog alertDialog = ab.create();
                        alertDialog.show();
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                BarCode barCode = (BarCode) productselectAdapter.getItem(i);
                                products = productDao.queryBuilder().where(ProductDao.Properties.FItemID.eq(barCode.FItemID)).build().list();
                                default_unitID = barCode.FUnitID;
                                getProductOFL(products);
                                alertDialog.dismiss();
                            }
                        });
                    }
                } else {
                    MediaPlayer.getInstance(mContext).error();
                    Toast.showText(mContext, "未找到条码");
                }

            }
        }
    }

    private void getProductOFL(List<Product> list) {
        if (list != null && list.size() > 0) {
            product = list.get(0);
            tvorisAuto(product);
        } else {
            Toast.showText(mContext, "未找到物料");
//            edCode.setText("");
//            setfocus(edCode);
        }
    }

    private ArrayList<FirstCheck> fContainer;
    private boolean hasData = false;

    public void getHuiDanData() {
        fContainer.clear();
        huidanList.clear();
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                "FORDER_ID," +
                "CLIENT_NAME," +
                "MODEL," +
                "LENGTH," +
                "FPRODUCT_NAME," +
                "FPRODUCT_ID," +
                "SUM(FQUANTITY) AS FQTY," +
                "SUM(RADICAL) AS RADICAL " +
                "FROM T__DETAIL " +
                "WHERE ACTIVITY=? GROUP BY FORDER_ID," +
                "FPRODUCT_NAME,FPRODUCT_ID ORDER BY FORDER_ID DESC", new String[]{Config.CHOOSE_SECOND_FRAGMENT_SHORT + ""});

        while (cursor.moveToNext()) {
            FirstCheck f = new FirstCheck();
            f.orderID = cursor.getString(cursor.getColumnIndex("FORDER_ID"));
            f.productname = cursor.getString(cursor.getColumnIndex("FPRODUCT_NAME"));
            f.TRadical = cursor.getString(cursor.getColumnIndex("RADICAL"));
            f.TVoleum = cursor.getString(cursor.getColumnIndex("FQTY"));
            f.itemID = cursor.getString(cursor.getColumnIndex("FPRODUCT_ID"));
            f.model = cursor.getString(cursor.getColumnIndex("MODEL"));
            f.length = cursor.getString(cursor.getColumnIndex("LENGTH"));
            f.clientName = cursor.getString(cursor.getColumnIndex("CLIENT_NAME"));
            fContainer.add(f);
        }
//        Log.e(TAG,"查找到："+fContainer.toString());
        if (fContainer.size() > 0) {
            hasData = true;
        } else {
            hasData = false;
        }
        huidanList.addAll(fContainer);

    }

    private void tvorisAuto(final Product product) {
//        edCode.setText(product.FName);
        rvNumChoose.scrollToPosition(Integer.parseInt(product.FMindiameter == null ? "0" : product.FMindiameter) / 2);
        rvNumChoose.setVisibility(View.VISIBLE);
//        if (wavehouseID == null) {
//            wavehouseID = "0";
//        }
//        if (isGetStorage) {
//            for (int j = 0; j < storageSpAdapter.getCount(); j++) {
//                if (((Storage) storageSpAdapter.getItem(j)).FItemID.equals(product.FDefaultLoc)) {
//                    spSendStorage.setSelection(j);
//                    break;
//                }
//            }
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < waveHouseAdapter.getCount(); j++) {
//                        if (((WaveHouse) waveHouseAdapter.getItem(j)).FSPID.equals(product.FSPID)) {
//                            spWavehouse.setSelection(j);
//                            break;
//                        }
//                    }
//                }
//            }, 50);
//        }
//        unitAdapter = CommonMethod.getMethod(mContext).getUnitAdapter(product.FUnitGroupID, spUnit);
//        if (default_unitID != null) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    for (int i = 0; i < unitAdapter.getCount(); i++) {
//                        if (default_unitID.equals(((Unit) unitAdapter.getItem(i)).FMeasureUnitID)) {
//                            spUnit.setSelection(i);
//                        }
//                    }
//                }
//            }, 100);
//        }
    }

    LinearLayoutManager mLinearLayoutManager2;
    private ArrayList<FirstCheck> huidanList;
    private FirstCheck items;
    private LayoutInflater inflater;
    private Padapter padapter;

    @OnClick({R.id.btn_add, R.id.btn_finishorder, R.id.btn_backorder, R.id.btn_checkorder, R.id.btn_back, R.id.tv_right, R.id.btn_review})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
//                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                ab.setTitle("确认完成么");
//                ab.setMessage("确认添加数据?");
//                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Addorder();
//                    }
//                }).setNegativeButton("取消", null).create().show();
                break;
//            case R.id.btn_finishorder:
//                finishOrder();
//                break;
//            case R.id.btn_backorder:
//                getHuiDanData();
//                if (!hasData) {
//                    Toast.showText(mContext, "无回单信息");
//                    return;
//                }
//
//                final View v = inflater.inflate(R.layout.item_choosetoprint, null);
////                mLinearLayoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//                ListView r = v.findViewById(R.id.rv_choose);
//                r.setAdapter(padapter);
//                final AlertDialog.Builder clickDg = new AlertDialog.Builder(mContext);
//                clickDg.setTitle("选择打印单据").setView(v).create().show();
//                r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        items = padapter.getFirst(i);
//                        Log.e("getlist", items.toString());
//                        uploadByOrderId(items.orderID);
//                    }
//                });
//
////                AlertDialog.Builder ab2 = new AlertDialog.Builder(mContext);
////                ab2.setTitle("是否回单");
////                ab2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        upload();
////                    }
////                }).setNegativeButton("取消",null).create().show();
//                break;
//            case R.id.btn_checkorder:
//                isBack = true;
//                Bundle b2 = new Bundle();
//                b2.putInt("activity", Config.CHOOSE_SECOND_FRAGMENT_SHORT);
//                startNewActivity(LookActivity.class, R.anim.activity_fade_in, R.anim.activity_fade_out, false, b2);
//                break;
            case R.id.btn_back:
                AlertDialog.Builder ab2 = new AlertDialog.Builder(mContext);
                ab2.setTitle("退出确认");
                ab2.setMessage("确认添加数据?");
                ab2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Addorder();
                    }
                }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create().show();
                break;
            case R.id.tv_right:
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("确认完成么");
                ab.setMessage("确认添加数据?");
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Addorder();
                    }
                }).setNegativeButton("取消", null).create().show();
                break;
            case R.id.btn_review:
                Bundle b1 = new Bundle();
                startNewActivity(BackGoodsReViewActivity.class, R.anim.activity_open, 0, false, b1);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e("code", requestCode + "" + "    " + resultCode);
//        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//                Bundle b = data.getExtras();
//                String message = b.getString("result");
//                edCode.setText(message);
//                Toast.showText(mContext, message);
//                edCode.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
//            }
//        } else if (requestCode == Info.SEARCHFORRESULTCLIRNT) {
//            if (resultCode == Info.SEARCHFORRESULTCLIRNT) {
//                Bundle b = data.getExtras();
//                clientId = b.getString("001");
//                clientName = b.getString("002");
//                edClient.setText(clientName);
//                setfocus(edCode);
//            }
//        }
    }

    public String AddTempOrder(String item) {
        List<TempDetail> list = tempDetailDao.queryBuilder().where(
                TempDetailDao.Properties.FOrderId.eq(orderId),
                TempDetailDao.Properties.FProductId.eq(product.FItemID),
                TempDetailDao.Properties.FPositionId.eq(wavehouseID),
                TempDetailDao.Properties.FStorageId.eq(storageId),
                TempDetailDao.Properties.Activity.eq(tag),
                TempDetailDao.Properties.Diameter.eq(item)
        ).build().list();
        if (list.size() > 0) {
            if (!"0".equals(list.get(0).radical)) {
                Lg.e("product 数据:"+product.toString());
                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                    addBackGoodsReView(list.get(0), item, true);
                    list.get(0).radical = (Integer.parseInt(list.get(0).radical) - 1) + "";
                    list.get(0).FQuantity = (Double.parseDouble(list.get(0).radical)
                            * (CalculateUtil.getVoleum(product.FM, item))) + "";
//                list.get(0).FQuantity = DoubleUtil.sub(Double.parseDouble(list.get(0).FQuantity),CalculateUtil.getVoleum(product.FM, item)) + "";
                    Lg.e("体积："+list.get(0).FQuantity);
                    tempDetailDao.update(list.get(0));
                }else{

                    addBackGoodsReView(list.get(0), item, true);
                    list.get(0).radical = (Integer.parseInt(list.get(0).radical) - 1) + "";
                    list.get(0).FQuantity = (Double.parseDouble(list.get(0).radical)
                            * (CalculateUtil.getVoleumLong(list.get(0).length, item))) + "";
//                    list.get(0).FQuantity = DoubleUtil.sub(Double.parseDouble(list.get(0).FQuantity),CalculateUtil.getVoleumLong(product.FM, item)) + "";
                    Lg.e("体积l："+list.get(0).FQuantity);
                    tempDetailDao.update(list.get(0));
                }

            }
        }
//        else {
//            TempDetail tempDetail = new TempDetail();
//            tempDetail.FIndex = getTimesecond();
//            tempDetail.length = product.FM == null || product.FM.equals("") ? "0" : product.FM;
//            tempDetail.diameter = Integer.parseInt(item);
//            tempDetail.FBatch = "";
//            tempDetail.FOrderId = Long.parseLong(orderId);
//            tempDetail.FProductCode = product.FNumber;
//            tempDetail.FProductId = product.FItemID;
//            tempDetail.model = product.FModel;
//            tempDetail.FProductName = product.FName;
//            tempDetail.FUnitId = unitId == null ? "" : unitId;
//            tempDetail.FUnit = unitName == null ? "" : unitName;
//            tempDetail.FStorage = storageName == null ? "" : storageName;
//            tempDetail.FStorageId = storageId == null ? "0" : storageId;
//            tempDetail.FPosition = wavehouseName == null ? "" : wavehouseName;
//            tempDetail.FPositionId = wavehouseID == null ? "0" : wavehouseID;
//            tempDetail.activity = tag;
//            tempDetail.FDiscount = "100";
//            tempDetail.FQuantity = CalculateUtil.getVoleum(product.FM, item) + "";
//            tempDetail.radical = "1";
//            tempDetail.num = num + "";
//            tempDetail.unitrate = unitrate;
//            tempDetail.FTaxUnitPrice = "0";
//            Lg.e("数据tempDetail:" + tempDetail.toString());
//            tempDetailDao.insert(tempDetail);
//        }
        MediaPlayer.getInstance(mContext).ok();
        VibratorUtil.Vibrate(mContext, 200);
//        share.setSVanNo(etVanNo.getText().toString());
//        share.setSVanDriver(etDriver.getText().toString());
//        share.setSClient(clientName);
//        share.setSClientID(clientId);

        //用于下次进来时，锁定客户，司机，车牌和客户选择按钮
//        share.setSOUTDisEnable(false);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                etDriver.setEnabled(false);
//                etVanNo.setEnabled(false);
//
//            }
//        });
//        etDriver.setEnabled(share.getSOUTDisEnable());
//        etVanNo.setEnabled(share.getSOUTDisEnable());
//        edClient.setEnabled(share.getSOUTDisEnable());
//        searchSupplier.setEnabled(share.getSOUTDisEnable());

        return "添加完成";
    }

    TreeSet<String> diameterTree = new TreeSet<>();
    TreeSet<Integer> diameterTreeInt = new TreeSet<>();

    //获取之前暂存在页面的单据列表数据（未添加状态的单据)
    private void RefreshList() {
        container.clear();
        diameterTree.clear();
        if (product == null || storageId == null || baoshu == null) {
            return;
        }
        List<TempDetail> tempDetails;
        Lg.e("仓位:" + waveHouseAdapter.getCount());
        if (waveHouseAdapter.getCount() != 0 && "0".equals(wavehouseID)) {
            return;
        }
        int genshu1 = 0;
        Double zongtiji = 0.0;
        if (waveHouseAdapter.getCount() == 0) {
            Lg.e("仓位为空");
            tempDetails = tempDetailDao.queryBuilder().where(
                    TempDetailDao.Properties.Activity.eq(tag),
                    TempDetailDao.Properties.FProductId.eq(product.FItemID),
                    TempDetailDao.Properties.FStorageId.eq(storageId),
                    TempDetailDao.Properties.Num.eq(baoshu)
            ).build().list();
        } else {
            tempDetails = tempDetailDao.queryBuilder().where(
                    TempDetailDao.Properties.Activity.eq(tag),
                    TempDetailDao.Properties.FProductId.eq(product.FItemID),
                    TempDetailDao.Properties.FPositionId.eq(wavehouseID),
                    TempDetailDao.Properties.FStorageId.eq(storageId),
                    TempDetailDao.Properties.Num.eq(baoshu)
            ).build().list();
        }


//        tempDetailDao.deleteAll();
//        if (tempDetails.size() == 0) {
//            tempMainDao.deleteAll();
//        }

        Log.e(TAG, "RefreshList: " + tempDetails.size());
        Log.e(TAG, "RefreshList: " + tempDetails.toString());
        for (TempDetail str : tempDetails) {
            diameterTree.add(str.diameter + "");
            diameterTreeInt.add(str.diameter);
        }

//        //遍历存在的径级，并且过滤出来，求总的数据：体积，根数------------------------------------
//        String tempRadical="0";
//        String tempQuantity="0d";
//        for(Iterator iter = diameterTreeInt.iterator(); iter.hasNext(); ) {
//            int treeInt = (int) iter.next();
//            for (int i=0;i<tempDetails.size();i++){
//                TempDetail tempDetail = tempDetails.get(i);
//                if (treeInt==tempDetail.diameter){
//                    tempRadical = (Integer.parseInt(tempRadical)) + (Integer.parseInt(tempDetail.radical)) + "";
//                    tempQuantity = (Double.parseDouble(tempQuantity)) + (Double.parseDouble(tempDetail.FQuantity)) + "";
////                tempDetail.radical = (Integer.parseInt(tempRadical)) + (Integer.parseInt(tempDetail.radical)) + "";
////                tempDetail.FQuantity = (Double.parseDouble(tempQuantity)) + (Double.parseDouble(tempDetail.FQuantity)) + "";
//                }
//            }
//            //得到根数，体积总和之后，遍历所有并更新
//            for (int i=0;i<tempDetails.size();i++){
//                TempDetail tempDetail = tempDetails.get(i);
//                if (treeInt==tempDetail.diameter){
//                    tempDetail.radical = tempRadical;
//                    tempDetail.FQuantity = tempQuantity;
//                    tempDetailDao.update(tempDetail);
//                }
//            }
//            tempRadical="0";
//            tempQuantity="0d";
//        }
//        tempDetails = tempDetailDao.queryBuilder().where(
//                TempDetailDao.Properties.Activity.eq(tag),
//                TempDetailDao.Properties.FProductId.eq(product.FItemID),
//                TempDetailDao.Properties.FPositionId.eq(wavehouseID),
//                TempDetailDao.Properties.FStorageId.eq(storageId)
//        ).build().list();
//        //删除相同径级的数据
//        for (int i=0;i<tempDetails.size();i++){
//            TempDetail tempDetail = tempDetails.get(i);
//            for (int j=1;j<tempDetails.size()-1;j++){
//                if (tempDetails.get(i).diameter==tempDetails.get(j).diameter){
//                    Lg.e("删除："+tempDetails.get(j).diameter);
//                    tempDetailDao.delete(tempDetails.get(j));
//                }
//            }
//        }
//        tempDetails = tempDetailDao.queryBuilder().where(
//                TempDetailDao.Properties.Activity.eq(tag),
//                TempDetailDao.Properties.FProductId.eq(product.FItemID),
//                TempDetailDao.Properties.FPositionId.eq(wavehouseID),
//                TempDetailDao.Properties.FStorageId.eq(storageId)
//        ).build().list();
        container.addAll(tempDetails);
        billRvAdapter.notifyDataSetChanged();
        for (int i = 0; i < tempDetails.size(); i++) {
            genshu1 += Integer.parseInt(tempDetails.get(i).radical);
            zongtiji += Double.parseDouble(tempDetails.get(i).FQuantity);
        }
//        if (genshu1!=0 && zongtiji !=0){
//            tvAvg.setText("平均径级:"+zongtiji/Double.valueOf(genshu1));
//        }else{
//            tvAvg.setText("平均径级:"+0);
//        }
        genshu.setText("总根数:" + genshu1);
        tiji.setText("总体积:" + zongtiji);
    }

    //添加到
    private void Addorder() {

        List<TempDetail> tempDetails = tempDetailDao.loadAll();
//        List<TempMain> tempMains = tempMainDao.loadAll();
//        if (tempMains.size() == 0) {
//            Toast.showText(mContext, "未选择单据信息无法添加");
//            return;
//        }

//        T_mainDao t_mainDao = daoSession.getT_mainDao();


//        //清除上一个单号的所有表头信息
//        t_mainDao.deleteInTx(
//                t_mainDao.queryBuilder().where(
//                        T_mainDao.Properties.Activity.eq(Config.CHOOSE_SECOND_FRAGMENT_SHORT),
//                        T_mainDao.Properties.OrderId.eq(ordercode)
//                ).build().list());
//        T_main t_main = new T_main();
//        TempMain tempMain = tempMains.get(0);
//        t_main.VanNo = tempMain.VanNo;
//        t_main.VanDriver = tempMain.VanDriver;
//        t_main.FDepartment = tempMain.FDepartment;
//        t_main.FDepartmentId = tempMain.FDepartmentId;
//        t_main.FIndex = tempMain.FIndex;
//        t_main.FPaymentDate = tempMain.FPaymentDate;
//        t_main.orderId = tempMain.orderID;
//        t_main.orderDate = tempMain.orderDate;
//        t_main.FPurchaseUnit = tempMain.FPurchaseUnit;
//        t_main.FSalesMan = tempMain.FSalesMan;
//        t_main.FSalesManId = tempMain.FSalesManId;
//        t_main.FMaker = tempMain.FMaker;
//        t_main.FMakerId = tempMain.FMakerId;
//        t_main.FRedBlue = tempMain.FRedBlue;
//        t_main.FDirector = tempMain.FDirector;
//        t_main.FDirectorId = tempMain.FDirectorId;
//        t_main.saleWay = tempMain.saleWay;
//        t_main.FDeliveryAddress = tempMain.FDeliveryAddress;
//        t_main.FRemark = tempMain.FRemark;
//        t_main.saleWayId = tempMain.saleWayId;
//        t_main.FCustody = tempMain.FCustody;
//        t_main.FCustodyId = tempMain.FCustodyId;
//        t_main.FAcount = tempMain.FAcount;
//        t_main.FAcountID = tempMain.FAcountID;
//        t_main.Rem = tempMain.Rem;
//        t_main.supplier = tempMain.supplier;
//        t_main.supplierId = tempMain.supplierId;
//        t_main.FSendOutId = tempMain.FSendOutId;
//        t_main.activity = Config.CHOOSE_SECOND_FRAGMENT_SHORT;
//        t_main.sourceOrderTypeId = tempMain.sourceOrderTypeId;
//        t_main.companyId = tempMain.companyId;
//        t_mainDao.insert(t_main);

//        List<T_Detail> list = t_detailDao.queryBuilder().where(
//                T_DetailDao.Properties.FOrderId.eq(orderId),
//                T_DetailDao.Properties.FProductId.eq(product.FItemID),
//                T_DetailDao.Properties.FPositionId.eq(wavehouseID),
//                T_DetailDao.Properties.FStorageId.eq(storageId),
//                T_DetailDao.Properties.Activity.eq(tag),
//                T_DetailDao.Properties.Diameter.eq(item)
//        ).build().list();
//        if (list.size() > 0) {
//            list.get(0).radical = (Integer.parseInt(list.get(0).radical) - 1) + "";
//            list.get(0).FQuantity = (Double.parseDouble(list.get(0).FQuantity) + CalculateUtil.getVoleumL(product.FM, item)) + "";
//            tempDetailDao.update(list.get(0));
//
//        }


        List<T_Detail> list = t_detailDao.queryBuilder().where(
                T_DetailDao.Properties.FOrderId.eq(orderId),
                T_DetailDao.Properties.Activity.eq(tag)
        ).build().list();
        t_detailDao.deleteInTx(list);
        for (int i = 0; i < tempDetails.size(); i++) {
            TempDetail tempDetail = tempDetails.get(i);
            if (!"0".equals(tempDetail.radical)) {
                T_Detail t_detail = new T_Detail();
                t_detail.length = tempDetail.length;
                t_detail.radical = tempDetail.radical;
                t_detail.FBatch = tempDetail.FBatch;
                t_detail.diameter = tempDetail.diameter;
                t_detail.FOrderId = tempDetail.FOrderId;
                t_detail.FProductCode = tempDetail.FProductCode;
                t_detail.FProductId = tempDetail.FProductId;
                t_detail.model = tempDetail.model;
                t_detail.FProductName = tempDetail.FProductName;
                t_detail.FIndex = tempDetail.FIndex;
                t_detail.FUnitId = tempDetail.FUnitId;
                t_detail.FUnit = tempDetail.FUnit;
                t_detail.FStorage = tempDetail.FStorage;
                t_detail.FStorageId = tempDetail.FStorageId;
                t_detail.FPosition = tempDetail.FPosition;
                t_detail.FPositionId = tempDetail.FPositionId;
                t_detail.activity = tag;
                t_detail.FDiscount = tempDetail.FDiscount;
                t_detail.FQuantity = tempDetail.FQuantity;
                t_detail.unitrate = tempDetail.unitrate;
                t_detail.FTaxUnitPrice = tempDetail.FTaxUnitPrice;
                t_detail.FIdentity = tempDetail.num;
                t_detail.clientName = clientName == null ? "" : clientName;
                Log.e(TAG, "数据T_Detail：" + t_detail.toString());
                t_detailDao.insert(t_detail);
            }
        }
        //删除---表头和表体的备用信息表数据
//        tempMainDao.deleteAll();
//        tempDetailDao.deleteAll();
//        num++;
//        share.setSOUTOrderNo(num);
        RefreshList();
        Toast.showText(mContext, "单据插入成功");
        finish();

    }

    //列表中的小加号剑豪
    @Override
    public void InnerItemOnClick(View v) {
        int position = (int) v.getTag();
        TempDetailDao tempDetailDao = daoSession.getTempDetailDao();
        TempDetail items;
        switch (v.getId()) {
            case R.id.btn_plus:
                items = billRvAdapter.getItems(position);
                items.radical = (Integer.parseInt(items.radical) + 1) + "";
                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                    items.FQuantity = (Double.parseDouble(items.radical)
                            * (CalculateUtil.getVoleum(items.length, items.diameter + ""))) + "";
                }else{
                    items.FQuantity = (Double.parseDouble(items.radical)
                            * (CalculateUtil.getVoleumLong(items.length,items.diameter+""))) + "";
                }
                addBackGoodsReView(items, items.diameter + "", false);
                tempDetailDao.update(items);
                billRvAdapter.goToPostion(billRvAdapter.getItems(position).diameter + "", items.model,storageId,items.length);
                RefreshList();
                MediaPlayer.getInstance(mContext).ok();
                rvBill.scrollToPosition(position);

                break;
            case R.id.btn_enduce:
                items = billRvAdapter.getItems(position);
                if (Integer.parseInt(items.radical) != 0) {
                    items.radical = (Integer.parseInt(items.radical) - 1) + "";
                    if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                        items.FQuantity = (Double.parseDouble(items.radical)
                                * (CalculateUtil.getVoleum(items.length, items.diameter + ""))) + "";
                    }else{
                        items.FQuantity = (Double.parseDouble(items.radical)
                                * (CalculateUtil.getVoleumLong(items.length,items.diameter+""))) + "";
                    }
                    addBackGoodsReView(items, items.diameter + "", true);
                    tempDetailDao.update(items);
                    billRvAdapter.goToPostion(billRvAdapter.getItems(position).diameter + "", items.model,storageId,items.length);
                    RefreshList();
                    MediaPlayer.getInstance(mContext).ok();
                    rvBill.scrollToPosition(position);
                }else{
                    Toast.showText(mContext,"数量不能为负数");
                }

                break;
        }
    }

    /**
     *
     * @param temp     添加的暂存bean
     * @param item      径级
     * @param isadd     是否增加，true，添加数量到review表，flase，从review表减去数量
     */
    private void addBackGoodsReView(TempDetail temp, String item, boolean isadd) {

        List<BackGoodsBean> list = backGoodsBeanDao.queryBuilder().where(
                BackGoodsBeanDao.Properties.FOrderId.eq(temp.FOrderId),
                BackGoodsBeanDao.Properties.FProductId.eq(product.FItemID),
                BackGoodsBeanDao.Properties.FPositionId.eq(temp.FPositionId),
                BackGoodsBeanDao.Properties.FStorageId.eq(temp.FStorageId),
                BackGoodsBeanDao.Properties.Length.eq(temp.length),
                BackGoodsBeanDao.Properties.Activity.eq(tag),
                BackGoodsBeanDao.Properties.Diameter.eq(item)
        ).build().list();
        if (list.size() > 0) {
            if (!isadd) {
                if (list.get(0).radical.equals("0")) {
                    list.get(0).radical = "-" + (Integer.parseInt(list.get(0).radical) + 1);
                } else {
                    list.get(0).radical = (Integer.parseInt(list.get(0).radical) - 1) + "";
                }
                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                    list.get(0).FQuantity = (Double.parseDouble(list.get(0).FQuantity) - CalculateUtil.getVoleum(product.FM, item)) + "";
                }else{
                    list.get(0).FQuantity = (Double.parseDouble(list.get(0).FQuantity) - CalculateUtil.getVoleumLong(temp.length, item)) + "";
                }
                backGoodsBeanDao.update(list.get(0));
            } else {
                list.get(0).radical = (Integer.parseInt(list.get(0).radical) + 1) + "";
                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                    list.get(0).FQuantity = (Double.parseDouble(list.get(0).FQuantity) + CalculateUtil.getVoleum(product.FM, item)) + "";
                }else{
                    list.get(0).FQuantity = (Double.parseDouble(list.get(0).FQuantity) + CalculateUtil.getVoleumLong(temp.length, item)) + "";
                }
                backGoodsBeanDao.update(list.get(0));
            }

        } else {
            BackGoodsBean tempDetail = new BackGoodsBean();
            tempDetail.FIndex = getTimesecond();
            if (tag ==Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                tempDetail.length = product.FM == null || product.FM.equals("") ? "0" : product.FM;
            }else{
                tempDetail.length = temp.length;
            }
            tempDetail.diameter = temp.diameter;
            tempDetail.FBatch = "";
            tempDetail.FOrderId = temp.FOrderId;
            tempDetail.FProductCode = product.FNumber;
            tempDetail.FProductId = product.FItemID;
            tempDetail.model = product.FModel;
            tempDetail.FProductName = product.FName;
//            tempDetail.FUnitId = unitId == null ? "" : unitId;
//            tempDetail.FUnit = unitName == null ? "" : unitName;
            tempDetail.FStorage = temp.FStorage;
            tempDetail.FStorageId = temp.FStorageId;
            tempDetail.FPosition = temp.FPosition;
            tempDetail.FPositionId = temp.FPositionId;
            tempDetail.activity = tag;
            tempDetail.FDiscount = "100";
            if (!isadd) {
                tempDetail.radical = "-1";
                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                    tempDetail.FQuantity = "-" + CalculateUtil.getVoleum(product.FM, item);
                }else{
                    tempDetail.FQuantity = "-" + CalculateUtil.getVoleumLong(temp.length, item);
                }
            } else {
                tempDetail.radical = "1";
                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                    tempDetail.FQuantity = CalculateUtil.getVoleum(product.FM, item) + "";
                }else{
                    tempDetail.FQuantity = CalculateUtil.getVoleumLong(temp.length, item) + "";
                }
            }
            tempDetail.FIdentity = temp.FIdentity;
            tempDetail.unitrate = temp.unitrate;
            tempDetail.FTaxUnitPrice = "0";
            Lg.e("添加BackGoodsReView数据:" + tempDetail.toString());
            backGoodsBeanDao.insert(tempDetail);
        }
    }
    private void addBackGoodsReViewForClick(TempDetail temp) {
        String item = temp.diameter+"";
        List<BackGoodsBean> list = backGoodsBeanDao.queryBuilder().where(
                BackGoodsBeanDao.Properties.FOrderId.eq(temp.FOrderId),
                BackGoodsBeanDao.Properties.FProductId.eq(product.FItemID),
                BackGoodsBeanDao.Properties.FPositionId.eq(temp.FPositionId),
                BackGoodsBeanDao.Properties.FStorageId.eq(temp.FStorageId),
                BackGoodsBeanDao.Properties.Activity.eq(tag),
                BackGoodsBeanDao.Properties.Diameter.eq(item)
        ).build().list();
        if (list.size() > 0) {
                list.get(0).radical = (Integer.parseInt(list.get(0).radical) + Integer.parseInt(temp.radical)) + "";
                list.get(0).FQuantity = DoubleUtil.sum(Double.parseDouble(list.get(0).FQuantity),Double.parseDouble(temp.FQuantity)) + "";
                backGoodsBeanDao.update(list.get(0));
        } else {
            BackGoodsBean tempDetail = new BackGoodsBean();
            tempDetail.FIndex = getTimesecond();
            tempDetail.length = product.FM == null || product.FM.equals("") ? "0" : product.FM;
            tempDetail.diameter = temp.diameter;
            tempDetail.FBatch = "";
            tempDetail.FOrderId = temp.FOrderId;
            tempDetail.FProductCode = product.FNumber;
            tempDetail.FProductId = product.FItemID;
            tempDetail.model = product.FModel;
            tempDetail.FProductName = product.FName;
//            tempDetail.FUnitId = unitId == null ? "" : unitId;
//            tempDetail.FUnit = unitName == null ? "" : unitName;
            tempDetail.FStorage = temp.FStorage;
            tempDetail.FStorageId = temp.FStorageId;
            tempDetail.FPosition = temp.FPosition;
            tempDetail.FPositionId = temp.FPositionId;
            tempDetail.activity = tag;
            tempDetail.FDiscount = "100";
            tempDetail.radical =temp.radical;
            tempDetail.FQuantity = temp.FQuantity;
            tempDetail.FIdentity = temp.FIdentity;
            tempDetail.unitrate = temp.unitrate;
            tempDetail.FTaxUnitPrice = "0";
            Lg.e("添加BackGoodsReView数据:" + tempDetail.toString());
            backGoodsBeanDao.insert(tempDetail);
        }
    }
    public static void start(Context context, int activity, String orderid, String clientName) {
        Intent starter = new Intent(context, BackGoodsActivity.class);
        starter.putExtra("activity", activity);
        starter.putExtra("orderid", orderid);
        starter.putExtra("clientName", clientName);
//        starter.putExtra("baoshu", baoshu);
        context.startActivity(starter);
    }
    @Override
    protected void onDestroy() {
        DeleteService.deleteTempDetailByTag(mContext, tag);
        DeleteService.deleteBackGoodsByTag(mContext, tag);
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager       设置RecyclerView对应的manager
     * @param mRecyclerView 当前的RecyclerView
     * @param n             要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

}
