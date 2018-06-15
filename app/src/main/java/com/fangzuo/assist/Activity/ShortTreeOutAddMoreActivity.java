package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.BillRvAdapter;
import com.fangzuo.assist.Adapter.CompanySpAdapter;
import com.fangzuo.assist.Adapter.DepartmentSpAdapter;
import com.fangzuo.assist.Adapter.EmployeeSpAdapter;
import com.fangzuo.assist.Adapter.NumRvAdapter;
import com.fangzuo.assist.Adapter.Padapter;
import com.fangzuo.assist.Adapter.PayMethodSpAdapter;
import com.fangzuo.assist.Adapter.PayTypeSpAdapter;
import com.fangzuo.assist.Adapter.PiciSpAdapter;
import com.fangzuo.assist.Adapter.ProductselectAdapter;
import com.fangzuo.assist.Adapter.ProductselectAdapter1;
import com.fangzuo.assist.Adapter.StorageSpAdapter;
import com.fangzuo.assist.Adapter.UnitSpAdapter;
import com.fangzuo.assist.Adapter.WaveHouseSpAdapter;
import com.fangzuo.assist.Adapter.YuandanSpAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.PurchaseInStoreUploadBean;
import com.fangzuo.assist.Dao.BarCode;
import com.fangzuo.assist.Dao.Client;
import com.fangzuo.assist.Dao.Company;
import com.fangzuo.assist.Dao.Department;
import com.fangzuo.assist.Dao.Employee;
import com.fangzuo.assist.Dao.PayType;
import com.fangzuo.assist.Dao.Product;
import com.fangzuo.assist.Dao.PurchaseMethod;
import com.fangzuo.assist.Dao.Storage;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.Dao.T_main;
import com.fangzuo.assist.Dao.TempDetail;
import com.fangzuo.assist.Dao.TempMain;
import com.fangzuo.assist.Dao.Unit;
import com.fangzuo.assist.Dao.WaveHouse;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.CalculateUtil;
import com.fangzuo.assist.Utils.CommonMethod;
import com.fangzuo.assist.Utils.CommonUtil;
import com.fangzuo.assist.Utils.Config;
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
import com.fangzuo.assist.zxing.activity.CaptureActivity;
import com.fangzuo.greendao.gen.BarCodeDao;
import com.fangzuo.greendao.gen.ClientDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.ProductDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.fangzuo.greendao.gen.TempDetailDao;
import com.fangzuo.greendao.gen.TempMainDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.hawk.Hawk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class ShortTreeOutAddMoreActivity extends BaseActivity implements BillRvAdapter.RvInnerClickListener {


    @BindView(R.id.scanbyCamera)
    RelativeLayout scanbyCamera;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.search)
    RelativeLayout search;
    @BindView(R.id.rv_bill)
    RecyclerView rvBill;
    @BindView(R.id.rv_numChoose)
    RecyclerView rvNumChoose;

    @BindView(R.id.sp_send_storage)
    Spinner spSendStorage;
    @BindView(R.id.sp_wavehouse)
    Spinner spWavehouse;

    @BindView(R.id.genshu)
    TextView genshu;
    @BindView(R.id.tiji)
    TextView tiji;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_orderID)
    TextView tvOrderId;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sp_unit)
    Spinner spUnit;
    private ShortTreeOutAddMoreActivity mContext;
    private DecimalFormat df;
    private DaoSession daoSession;
    private int year;
    private int month;
    private int day;
    private long ordercode;
    private StorageSpAdapter storageSpAdapter;
    private CommonMethod method;
    private PayMethodSpAdapter slaesRange;
    private PayMethodSpAdapter saleMethodSpAdapter;
    private CompanySpAdapter companySpAdapter;
    private EmployeeSpAdapter employeeAdapter;
    private EmployeeSpAdapter managerAdapter;
    private EmployeeSpAdapter yuandanAdapter;
    private YuandanSpAdapter yuandanSpAdapter;
    private PayTypeSpAdapter payTypeSpAdapter;
    private DepartmentSpAdapter departMentAdapter;
    private PayMethodSpAdapter getGoodsType;
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
    private WaveHouseSpAdapter waveHouseAdapter;
    private String storageId;
    private String storageName;
    private String wavehouseID = "0";
    private String wavehouseName;
    private String clientId;
    private String clientName;
    private String date;
    private String datePay;
    private boolean isHebing = true;
    public static final String TAG = "SOLDOUT";
    private Double qty;
    private T_mainDao t_mainDao;
    private T_DetailDao t_detailDao;
    private boolean isAuto;
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
    ProgressDialog canclePg;
    private int activity;

    @Override
    public void initView() {
        setContentView(R.layout.activity_short_tree_out_add_more);
        mContext = this;
        ButterKnife.bind(mContext);

        df = new DecimalFormat("######0.000");
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        method = CommonMethod.getMethod(mContext);


        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(mContext, 5);

        rvNumChoose.setVisibility(View.GONE);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        canclePg = new ProgressDialog(mContext);
        canclePg.setMessage("请稍后...");
        canclePg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        canclePg.setCancelable(false);

        Intent intent = getIntent();
        if (intent != null) {
            activity = intent.getIntExtra("activity", 0);
            ordercode = Long.parseLong(intent.getStringExtra("orderid"));
            clientName = intent.getStringExtra("clientName");
            num = Integer.parseInt(intent.getStringExtra("baoshu"));
            Lg.e(activity + "--" + ordercode + "--" + clientName + "--" + num);
        }
        tvTitle.setText("追加客户:" + clientName);
        tvRight.setText("追加");
        tvOrderId.setText(ordercode + "");
        num = num + 1;
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.PRODUCTRETURN:
                product = (Product) event.postEvent;
                setDATA("", true);
                break;
        }
    }


    @Override
    public void initData() {

        tempMainDao = daoSession.getTempMainDao();
        tempDetailDao = daoSession.getTempDetailDao();
        container = new ArrayList<>();
        fContainer = new ArrayList<>();
        huidanList = new ArrayList<>();
        padapter = new Padapter(huidanList, mContext);

        //根数列表
        billRvAdapter = new BillRvAdapter(mContext, container,true);
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
        RefreshList();

        LoadBasicData();
    }

    public static boolean isBack = true;

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void LoadBasicData() {
        storageSpAdapter = method.getStorageSpinner(spSendStorage);
    }

    @Override
    public void initListener() {
        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Unit unit = (Unit) unitAdapter.getItem(i);
                if (unit != null) {
                    unitId = unit.FMeasureUnitID;
                    unitName = unit.FName;
                    unitrate = Double.parseDouble(unit.FCoefficient);
                    Log.e("1111", unitrate + "");
                }
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
                final TempDetailDao tempDetailDao = daoSession.getTempDetailDao();
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("确认删除么").setMessage("是否删除本条数据？\r\n" + "物料:" + billRvAdapter.getItems(position).FProductName + "\r\n"
                        + "径级:" + billRvAdapter.getItems(position).diameter + "\r\n" + "长度:" + billRvAdapter.getItems(position).length + "\r\n" +
                        "根数:" + billRvAdapter.getItems(position).radical + "\r\n" + "体积:" + billRvAdapter.getItems(position).FQuantity).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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

                    if (null != storageId && "".equals(storageId)) {
                        Toast.showText(mContext, "仓库不能为空");
                        MediaPlayer.getInstance(mContext).error();
                        return;
                    }

                    if (product.FM == null || product.FM.equals("")) {
                        Toast.showText(mContext, "物料信息中找不到长度");
                        MediaPlayer.getInstance(mContext).error();
                        return;
                    }

                    pg = new ProgressDialog(mContext);
                    pg.setMessage("添加中...");
                    pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pg.setCancelable(false);
                    pg.show();
                    rvNumChoose.setClickable(false);
                    if ("添加完成".equals(AddTempOrder(numRvAdapter.getItem(position)))) {
                        RefreshList();
                        for (int j = 0; j < billRvAdapter.getItemCount(); j++) {
                            String posit = ((TempDetail) billRvAdapter.getItems(j)).diameter + "";
                            String model = ((TempDetail) billRvAdapter.getItems(j)).model;
                            if (posit.equals(numRvAdapter.getItem(position))) {
//                              Log.e("asdf",((TempDetail)billRvAdapter.getItems(j)).diameter+"--"+"j"+"---"+numRvAdapter.getItem(position));
                                Lg.e("model:" + model);
                                MoveToPosition(mLinearLayoutManager, rvBill, j);
                                billRvAdapter.goToPostion(numRvAdapter.getItem(position), product.FModel,storageId,product.FM);
                                break;
                            }
                        }
                        rvNumChoose.setClickable(true);
                        pg.dismiss();
                    } else {
                        Toast.showText(mContext, "添加出错");
                        rvNumChoose.setClickable(true);
                        pg.dismiss();
                    }

                } else {
                    Toast.showText(mContext, "未选择物料");
                }
            }
        });


        edCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 0 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    setDATA(edCode.getText().toString(), false);
                    setfocus(edCode);
                }
                return true;
            }
        });


        spSendStorage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                storage = (Storage) storageSpAdapter.getItem(i);
                waveHouseAdapter = CommonMethod.getMethod(mContext).getWaveHouseAdapter(storage, spWavehouse);
                wavehouseID = "0";
                storageId = storage.FItemID;
                storageName = storage.FName;
                Log.e("storageId", storageId);
                Log.e("storageName", storageName);

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
            edCode.setText("");
            setfocus(edCode);
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
                "FPRODUCT_NAME,FPRODUCT_ID ORDER BY FORDER_ID DESC", new String[]{activity + ""});

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
        padapter.notifyDataSetChanged();
    }

    private void tvorisAuto(final Product product) {
        edCode.setText(product.FName);
        rvNumChoose.scrollToPosition(Integer.parseInt(product.FMindiameter == null ? "0" : product.FMindiameter) / 2);
        rvNumChoose.setVisibility(View.VISIBLE);
        if (wavehouseID == null) {
            wavehouseID = "0";
        }
        if (isGetStorage) {
            for (int j = 0; j < storageSpAdapter.getCount(); j++) {
                if (((Storage) storageSpAdapter.getItem(j)).FItemID.equals(product.FDefaultLoc)) {
                    spSendStorage.setSelection(j);
                    break;
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < waveHouseAdapter.getCount(); j++) {
                        if (((WaveHouse) waveHouseAdapter.getItem(j)).FSPID.equals(product.FSPID)) {
                            spWavehouse.setSelection(j);
                            break;
                        }
                    }
                }
            }, 50);
        }

        unitAdapter = CommonMethod.getMethod(mContext).getUnitAdapter(product.FUnitGroupID, spUnit);
        if (default_unitID != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < unitAdapter.getCount(); i++) {
                        if (default_unitID.equals(((Unit) unitAdapter.getItem(i)).FMeasureUnitID)) {
                            spUnit.setSelection(i);
                        }
                    }
                }
            }, 100);
        }
    }

    LinearLayoutManager mLinearLayoutManager2;
    private ArrayList<FirstCheck> huidanList;
    private FirstCheck items;
    private LayoutInflater inflater;
    private Padapter padapter;
    boolean isc = true;

    @OnClick({R.id.scanbyCamera, R.id.search, R.id.btn_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.scanbyCamera:
                Intent in = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(in, 0);
                break;
            case R.id.search:
                Log.e("search", "onclick");
                Bundle b1 = new Bundle();
                String sech = "";
                sech = edCode.getText().toString();
                b1.putString("search", sech);
                Log.e("search", "onclick" + sech);
                b1.putInt("where", Info.SEARCHPRODUCT);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULT, b1);
                break;
//            case R.id.btn_add:
//                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                ab.setTitle("确认完成么");
//                ab.setMessage("确认添加数据?");
//                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Addorder();
//                    }
//                }).setNegativeButton("取消",null).create().show();
//                break;
//            case R.id.btn_finishorder:
//                finishOrder();
//                break;
//            case R.id.btn_backorder:
//                getHuiDanData();
//                if (!hasData){
//                    Toast.showText(mContext,"无回单信息");
//                    return;
//                }
//
//                final View v = inflater.inflate(R.layout.item_choosetoprint,null);
////                mLinearLayoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//                ListView r = v.findViewById(R.id.rv_choose);
//                r.setAdapter(padapter);
//                final AlertDialog.Builder clickDg = new AlertDialog.Builder(mContext);
//                clickDg.setTitle("选择打印单据").setView(v).create().show();
//                if (isc){
//                    r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            canclePg.show();
//                            items = padapter.getFirst(i);
//                            Log.e("getlist",items.toString());
//                            uploadByOrderId(items.orderID);
//                        }
//                    });
//                }
//
//                break;
//            case R.id.btn_checkorder:
//                isBack=true;
//                Bundle b2 = new Bundle();
//                b2.putInt("activity", activity);
//                startNewActivity(LookActivity.class, R.anim.activity_fade_in, R.anim.activity_fade_out, false, b2);
//                break;

            case R.id.btn_back:
                finish();
//                AlertDialog.Builder ab2 = new AlertDialog.Builder(mContext);
//                ab2.setTitle("退出确认");
//                ab2.setMessage("确认添加数据?");
//                ab2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Addorder();
//                    }
//                }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
//                    }
//                }).create().show();
                break;
            case R.id.tv_right:
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("确认追加");
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Addorder();
                    }
                }).setNegativeButton("取消", null).create().show();
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
                edCode.setText(message);
                Toast.showText(mContext, message);
                edCode.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
        }
    }

    public String AddTempOrder(String item) {
//        List<TempMain> tempMains = tempMainDao.loadAll();
//        if (tempMains.size() > 0) {
//            tempMainDao.deleteAll();
//        }
//        TempMain tempMain = new TempMain();
//        tempMain.activity = activity;
//        tempMain.orderID = ordercode;
//        tempMain.FIndex = getTimesecond();
//        tempMain.FMaker = share.getUserName();
//        tempMain.FMakerId = share.getsetUserID();
//        long insert = tempMainDao.insert(tempMain);


        List<TempDetail> list = tempDetailDao.queryBuilder().where(
                TempDetailDao.Properties.FOrderId.eq(ordercode),
                TempDetailDao.Properties.FProductId.eq(product.FItemID),
                TempDetailDao.Properties.FPositionId.eq(wavehouseID),
                TempDetailDao.Properties.FStorageId.eq(storageId),
                TempDetailDao.Properties.Activity.eq(activity),
                TempDetailDao.Properties.Diameter.eq(item)
        ).build().list();
        if (list.size() > 0) {
            list.get(0).radical = (Integer.parseInt(list.get(0).radical) + 1) + "";
            list.get(0).FQuantity = (Double.parseDouble(list.get(0).FQuantity) + CalculateUtil.getVoleum(product.FM, item)) + "";
            tempDetailDao.update(list.get(0));

        } else {
            TempDetail tempDetail = new TempDetail();
            tempDetail.FIndex = getTimesecond();
            tempDetail.length = product.FM == null || product.FM.equals("") ? "0" : product.FM;
            tempDetail.diameter = Integer.parseInt(item);
            tempDetail.FBatch = "";
            tempDetail.FOrderId = ordercode;
            tempDetail.FProductCode = edCode.getText().toString();
            tempDetail.FProductId = product.FItemID;
            tempDetail.model = product.FModel;
            tempDetail.FProductName = product.FName;
            tempDetail.FUnitId = unitId == null ? "" : unitId;
            tempDetail.FUnit = unitName == null ? "" : unitName;
            tempDetail.FStorage = storageName == null ? "" : storageName;
            tempDetail.FStorageId = storageId == null ? "0" : storageId;
            tempDetail.FPosition = wavehouseName == null ? "" : wavehouseName;
            tempDetail.FPositionId = wavehouseID == null ? "0" : wavehouseID;
            tempDetail.activity = activity;
            tempDetail.FDiscount = "100";
            tempDetail.FQuantity = CalculateUtil.getVoleum(product.FM, item) + "";
            tempDetail.radical = "1";
            tempDetail.num = num + "";
            tempDetail.unitrate = unitrate;
            tempDetail.FTaxUnitPrice = "0";
            Lg.e("tempDetail:" + tempDetail.toString());
            tempDetailDao.insert(tempDetail);
        }
        MediaPlayer.getInstance(mContext).ok();
        VibratorUtil.Vibrate(mContext, 200);

        return "添加完成";
    }

    //获取之前暂存在页面的单据列表数据（未添加状态的单据)
    private void RefreshList() {
        container.clear();
        int genshu1 = 0;
        Double zongtiji = 0.0;
        List<TempDetail> tempDetails = tempDetailDao.queryBuilder().where(
                TempDetailDao.Properties.Activity.eq(activity)
        ).build().list();
        if (tempDetails.size() == 0) {
            tempMainDao.deleteAll();
        }
        Log.e(TAG, "RefreshList: " + tempDetails.size());
        Log.e(TAG, "RefreshList: " + tempDetails.toString());
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
        if (tempDetails.size() == 0) {
            Toast.showText(mContext, "未选择单据信息无法添加");
            return;
        }

//        T_mainDao t_mainDao = daoSession.getT_mainDao();
        T_DetailDao t_detailDao = daoSession.getT_DetailDao();

//        //清除上一个单号的所有表头信息
//        t_mainDao.deleteInTx(
//                t_mainDao.queryBuilder().where(
//                        T_mainDao.Properties.Activity.eq(activity),
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
//        t_main.activity = activity;
//        t_main.sourceOrderTypeId = tempMain.sourceOrderTypeId;
//        t_main.companyId = tempMain.companyId;
//        t_mainDao.insert(t_main);
        for (int i = 0; i < tempDetails.size(); i++) {
            TempDetail tempDetail = tempDetails.get(i);
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
            t_detail.activity = activity;
            t_detail.FDiscount = tempDetail.FDiscount;
            t_detail.FQuantity = tempDetail.FQuantity;
            t_detail.unitrate = tempDetail.unitrate;
            t_detail.FTaxUnitPrice = tempDetail.FTaxUnitPrice;
            t_detail.FIdentity = tempDetail.num;
            t_detail.activity = activity;
            t_detail.clientName = clientName == null ? "" : clientName;
            Log.e(TAG, "T_Detail：" + t_detail.toString());
            t_detailDao.insert(t_detail);
        }
        //删除---表头和表体的备用信息表数据
//        tempMainDao.deleteAll();
        tempDetailDao.deleteAll();
        num++;
        RefreshList();
        Toast.showText(mContext, "单据插入成功");

    }


//
//    private void uploadByOrderId(String orderId) {
//        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
//        PurchaseInStoreUploadBean.purchaseInStore puBean = pBean.new purchaseInStore();
//        t_mainDao = daoSession.getT_mainDao();
//        t_detailDao = daoSession.getT_DetailDao();
//        ArrayList<String> detailContainer = new ArrayList<>();
//        ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data = new ArrayList<>();
//        List<T_main> mains = t_mainDao.queryBuilder().where(
//                T_mainDao.Properties.OrderId.eq(orderId),
//                T_mainDao.Properties.Activity.eq(activity)
//        ).build().list();
//        for (int i = 0; i < mains.size(); i++) {
//            if (i > 0 && mains.get(i).orderId == mains.get(i - 1).orderId) {
//            } else {
//                detailContainer.clear();
//                String main;
//                String detail = "";
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
//                puBean.main = main;
//                List<T_Detail> details = t_detailDao.queryBuilder().where(
//                        T_DetailDao.Properties.FOrderId.eq(t_main.orderId),
//                        T_DetailDao.Properties.Activity.eq(activity)
//                ).build().list();
//                for (int j = 0; j < details.size(); j++) {
//                    if (j != 0 && j % 49 == 0) {
//                        Log.e("j%49", j % 49 + "");
//                        T_Detail t_detail = details.get(j);
//                        detail = detail + t_detail.FProductId + "|" +
//                                t_detail.FUnitId + "|" +
//                                t_detail.FTaxUnitPrice + "|" +
//                                t_detail.FQuantity + "|" +
//                                t_detail.FDiscount + "|" +
//                                t_detail.FStorageId + "|" +
//                                t_detail.FBatch + "|" +
//                                t_detail.FPositionId + "|"+
//                                t_detail.FIdentity+"|"+
//                                t_detail.diameter+"|"+
//                                t_detail.FQuantity+"|"+
//                                t_detail.radical+"|"+
//                                t_detail.length+"|";
//                        detail = detail.subSequence(0, detail.length() - 1).toString();
//                        detailContainer.add(detail);
//                        detail = "";
//                    } else {
//                        Log.e("j", j + "");
//                        Log.e("details.size()", details.size() + "");
//                        T_Detail t_detail = details.get(j);
//                        detail = detail + t_detail.FProductId + "|" +
//                                t_detail.FUnitId + "|" +
//                                t_detail.FTaxUnitPrice + "|" +
//                                t_detail.FQuantity + "|" +
//                                t_detail.FDiscount + "|" +
//                                t_detail.FStorageId + "|" +
//                                t_detail.FBatch + "|" +
//                                t_detail.FPositionId + "|"+
//                                t_detail.FIdentity+"|"+
//                                t_detail.diameter+"|"+
//                                t_detail.FQuantity+"|"+
//                                t_detail.radical+"|"+
//                                t_detail.length+"|";
//                        Log.e("detail1", detail);
//                    }
//
//                }
//                //去掉最后一个竖杠
//                if (detail.length() > 0) {
//                    detail = detail.subSequence(0, detail.length() - 1).toString();
//                }
//
//                Log.e("总detail", detail);
//                detailContainer.add(detail);
//                puBean.detail = detailContainer;
//                data.add(puBean);
//            }
//
//        }
//        postToServerByOrderId(data,orderId);
//    }
//
//    private void postToServerByOrderId(ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data, final String orderId) {
//        if (!isc){
//            return;
//        }
//        isc=false;
//
//        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
//        pBean.list = data;
//        Gson gson = new Gson();
//        Asynchttp.post(mContext, getBaseUrl() + WebApi.UPLOADSOUT, gson.toJson(pBean), new Asynchttp.Response() {
//            @Override
//            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
//                MediaPlayer.getInstance(mContext).ok();
//                Toast.showText(mContext, "上传成功");
//                t_detailDao.deleteInTx(t_detailDao.queryBuilder().where(
//                        T_DetailDao.Properties.FOrderId.eq(orderId),
//                        T_DetailDao.Properties.Activity.eq(activity)
//                ).build().list());
//                t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
//                        T_mainDao.Properties.OrderId.eq(orderId),
//                        T_mainDao.Properties.Activity.eq(activity)
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
//
//    /**/
//    private void upload() {
//        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
//        PurchaseInStoreUploadBean.purchaseInStore puBean = pBean.new purchaseInStore();
//        t_mainDao = daoSession.getT_mainDao();
//        t_detailDao = daoSession.getT_DetailDao();
//        ArrayList<String> detailContainer = new ArrayList<>();
//        ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data = new ArrayList<>();
//        List<T_main> mains = t_mainDao.queryBuilder().where(
//                T_mainDao.Properties.Activity.eq(activity)
//        ).build().list();
//        for (int i = 0; i < mains.size(); i++) {
//            if (i > 0 && mains.get(i).orderId == mains.get(i - 1).orderId) {
//            } else {
//                detailContainer.clear();
//                String main;
//                String detail = "";
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
//                puBean.main = main;
//                List<T_Detail> details = t_detailDao.queryBuilder().where(
//                        T_DetailDao.Properties.FOrderId.eq(t_main.orderId),
//                        T_DetailDao.Properties.Activity.eq(activity)
//                ).build().list();
//                for (int j = 0; j < details.size(); j++) {
//                    if (j != 0 && j % 49 == 0) {
//                        Log.e("j%49", j % 49 + "");
//                        T_Detail t_detail = details.get(j);
//                        detail = detail + t_detail.FProductId + "|" +
//                                t_detail.FUnitId + "|" +
//                                t_detail.FTaxUnitPrice + "|" +
//                                t_detail.FQuantity + "|" +
//                                t_detail.FDiscount + "|" +
//                                t_detail.FStorageId + "|" +
//                                t_detail.FBatch + "|" +
//                                t_detail.FPositionId + "|"+
//                                t_detail.FIdentity+"|"+
//                                t_detail.diameter+"|"+
//                                t_detail.FQuantity+"|"+
//                                t_detail.radical+"|"+
//                                t_detail.length+"|";
//                        detail = detail.subSequence(0, detail.length() - 1).toString();
//                        detailContainer.add(detail);
//                        detail = "";
//                    } else {
//                        Log.e("j", j + "");
//                        Log.e("details.size()", details.size() + "");
//                        T_Detail t_detail = details.get(j);
//                        detail = detail + t_detail.FProductId + "|" +
//                                t_detail.FUnitId + "|" +
//                                t_detail.FTaxUnitPrice + "|" +
//                                t_detail.FQuantity + "|" +
//                                t_detail.FDiscount + "|" +
//                                t_detail.FStorageId + "|" +
//                                t_detail.FBatch + "|" +
//                                t_detail.FPositionId + "|"+
//                                t_detail.FIdentity+"|"+
//                                t_detail.diameter+"|"+
//                                t_detail.FQuantity+"|"+
//                                t_detail.radical+"|"+
//                                t_detail.length+"|";
//                        Log.e("detail1", detail);
//                    }
//
//                }
//                //去掉最后一个竖杠
//                if (detail.length() > 0) {
//                    detail = detail.subSequence(0, detail.length() - 1).toString();
//                }
//
//                Log.e("detail", detail);
//                detailContainer.add(detail);
//                puBean.detail = detailContainer;
//                data.add(puBean);
//            }
//
//        }
//        postToServer(data);
//    }
//    /**/
//    private void postToServer(ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data) {
//        final ProgressDialog pg = new ProgressDialog(mContext);
//        pg.setMessage("请稍后...");
//        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pg.show();
//        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
//        pBean.list = data;
//        Gson gson = new Gson();
//        Asynchttp.post(mContext, getBaseUrl() + WebApi.UPLOADSOUT, gson.toJson(pBean), new Asynchttp.Response() {
//            @Override
//            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
//                MediaPlayer.getInstance(mContext).ok();
//                Toast.showText(mContext, "上传成功");
//                List<T_Detail> list = t_detailDao.queryBuilder().where(
//                        T_DetailDao.Properties.Activity.eq(activity)
//                ).build().list();
//                List<T_main> list1 = t_mainDao.queryBuilder().where(
//                        T_mainDao.Properties.Activity.eq(activity)
//                ).build().list();
//                for (int i = 0; i < list.size(); i++) {
//                    t_detailDao.delete(list.get(i));
//                }
//                for (int i = 0; i < list1.size(); i++) {
//                    t_mainDao.delete(list1.get(i));
//                }
//                pg.dismiss();
//                finishOrder();
//            }
//
//            @Override
//            public void onFailed(String Msg, AsyncHttpClient client) {
//                MediaPlayer.getInstance(mContext).error();
//                Toast.showText(mContext, Msg);
//                pg.dismiss();
//            }
//        });
//    }

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
                items.FQuantity = (Double.parseDouble(items.radical)
//                        * Double.parseDouble(items.length == null ? "0" : items.length)
//                        * Double.parseDouble(items.diameter)) + "";
                        * (CalculateUtil.getVoleum(items.length, items.diameter + ""))) + "";
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
                    items.FQuantity = (Double.parseDouble(items.radical)
                            * (CalculateUtil.getVoleum(items.length, items.diameter + ""))) + "";
                    tempDetailDao.update(items);
                    billRvAdapter.goToPostion(billRvAdapter.getItems(position).diameter + "", items.model,storageId,items.length);
                    RefreshList();
                    MediaPlayer.getInstance(mContext).ok();
                    rvBill.scrollToPosition(position);
                }

                break;
        }
    }

    public static void start(Context context, int activity, String orderid, String clientName, String baoshu) {
        Intent starter = new Intent(context, ShortTreeOutAddMoreActivity.class);
        starter.putExtra("activity", activity);
        starter.putExtra("orderid", orderid);
        starter.putExtra("clientName", clientName);
        starter.putExtra("baoshu", baoshu);
        context.startActivity(starter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        tempDetailDao.deleteAll();
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

