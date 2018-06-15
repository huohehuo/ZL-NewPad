package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
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
import com.fangzuo.assist.Adapter.DepartmentSpAdapter;
import com.fangzuo.assist.Adapter.EmployeeSpAdapter;
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
import com.fangzuo.assist.Beans.GetBatchNoBean;
import com.fangzuo.assist.Beans.InStoreNumBean;
import com.fangzuo.assist.Beans.PurchaseInStoreUploadBean;
import com.fangzuo.assist.Dao.BarCode;
import com.fangzuo.assist.Dao.Department;
import com.fangzuo.assist.Dao.Employee;
import com.fangzuo.assist.Dao.InStorageNum;
import com.fangzuo.assist.Dao.PayType;
import com.fangzuo.assist.Dao.Product;
import com.fangzuo.assist.Dao.PurchaseMethod;
import com.fangzuo.assist.Dao.Storage;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.Dao.T_main;
import com.fangzuo.assist.Dao.Unit;
import com.fangzuo.assist.Dao.WaveHouse;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.CommonMethod;
import com.fangzuo.assist.Utils.EventBusInfoCode;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.MediaPlayer;
import com.fangzuo.assist.Utils.ShareUtil;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.assist.zxing.activity.CaptureActivity;
import com.fangzuo.greendao.gen.BarCodeDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.InStorageNumDao;
import com.fangzuo.greendao.gen.ProductDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SoldOutActivity extends BaseActivity {


    @BindView(R.id.drawer)
    DrawerLayout mDrawer;
    @BindView(R.id.ishebing)
    CheckBox cbHebing;
    @BindView(R.id.sp_send_storage)
    Spinner spSendStorage;              //发货仓库
    @BindView(R.id.ed_client)
    EditText edClient;                  //客户输入框
    @BindView(R.id.search_supplier)
    RelativeLayout searchSupplier;
    @BindView(R.id.scanbyCamera)
    RelativeLayout scanbyCamera;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.search)
    RelativeLayout search;
    @BindView(R.id.tv_goodName)
    TextView tvGoodName;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_numinstorage)
    TextView tvNuminstorage;            //库存
    @BindView(R.id.sp_wavehouse)
    Spinner spWavehouse;
    @BindView(R.id.ed_onsale)
    EditText edOnsale;                  //折扣率
    @BindView(R.id.ed_num)
    EditText edNum;
    @BindView(R.id.sp_unit)
    Spinner spUnit;
    @BindView(R.id.ed_pricesingle)
    EditText edPricesingle;
    @BindView(R.id.ed_zhaiyao)
    EditText edZhaiyao;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_finishorder)
    Button btnFinishorder;
    @BindView(R.id.btn_backorder)
    Button btnBackorder;
    @BindView(R.id.btn_checkorder)
    Button btnCheckorder;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_date_pay)
    TextView tvDatePay;
    @BindView(R.id.sp_sale_scope)
    Spinner spSaleScope;             //销售范围
    @BindView(R.id.sp_saleMethod)
    Spinner spSaleMethod;           //销售方式
    @BindView(R.id.sp_yuandan)
    Spinner spYuandan;              //保管
    @BindView(R.id.sp_sendMethod)
    Spinner spSendMethod;           //交货方式
    @BindView(R.id.sp_payMethod)
    Spinner spPayMethod;            //结算方式
    @BindView(R.id.sp_sendplace)
    Spinner spSendplace;            //交货地点
    @BindView(R.id.sp_department)
    Spinner spDepartment;           //部门
    @BindView(R.id.sp_employee)
    Spinner spEmployee;             //业务员
    @BindView(R.id.sp_manager)
    Spinner spManager;              //主管
    @BindView(R.id.isAutoAdd)
    CheckBox autoAdd;
    @BindView(R.id.cb_isStorage)
    CheckBox cbIsStorage;           //是否带出默认仓库
    @BindView(R.id.sp_pihao)
    Spinner spPihao;
    @BindView(R.id.et_beizhu)
    EditText etBeizhu;
    @BindView(R.id.blue)
    RadioButton blue;
    @BindView(R.id.red)
    RadioButton red;
    @BindView(R.id.redorBlue)
    RadioGroup redorBlue;
    private boolean isRed = false;
    private String redblue = "蓝字";
    private SoldOutActivity mContext;
    private DecimalFormat df;
    private DaoSession daoSession;
    private int year;
    private int month;
    private int day;
    private long ordercode;         //获取当前时间段
    private StorageSpAdapter storageSpAdapter;
    private CommonMethod method;
    private PayMethodSpAdapter slaesRange;
    private PayMethodSpAdapter saleMethodSpAdapter;
    private EmployeeSpAdapter employeeAdapter;
    private YuandanSpAdapter yuandanSpAdapter;
    private PayTypeSpAdapter payTypeSpAdapter;
    private DepartmentSpAdapter departMentAdapter;
    private PayMethodSpAdapter getGoodsType;
    private String departmentId;
    private String departmentName;
    private String SaleMethodId;
    private String SaleMethodName;
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
    private boolean fBatchManager;
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
    public static final int SOLDOUT = Info.SoldOut;
    private Double qty;
    private T_mainDao t_mainDao;
    private T_DetailDao t_detailDao;
    private boolean isAuto;
    private boolean isGetStorage = true;
    private Product product;
    private ProductselectAdapter productselectAdapter;
    private ProductselectAdapter1 productselectAdapter1;
    private PiciSpAdapter piciSpAdapter;

    private String default_unitID;
    private Double storenum;


    @Override
    public void initView() {
        setContentView(R.layout.activity_sold_out);
        mContext = this;
        ButterKnife.bind(mContext);
        share = ShareUtil.getInstance(mContext);

        initDrawer(mDrawer);
        df = new DecimalFormat("######0.00");
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        initDrawer(mDrawer);
        cbHebing.setChecked(true);
        cbIsStorage.setChecked(true);
        edOnsale.setText("0");

        autoAdd.setChecked(share.getSOUTisAuto());
        isAuto = share.getSOUTisAuto();
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
        method = CommonMethod.getMethod(mContext);
        //获取当前时间值
        if (share.getSOUTOrderCode() == 0) {
            ordercode = Long.parseLong(getTime(false) + "001");
            Log.e("ordercode", ordercode + "");
            share.setSOUTOrderCode(ordercode);
        } else {
            ordercode = share.getSOUTOrderCode();
            Log.e("ordercode", ordercode + "");
        }
        //初始化各种spinner
        LoadBasicData();
    }

    private void LoadBasicData() {

        storageSpAdapter = method.getStorageSpinner(spSendStorage);
        slaesRange = method.getPurchaseRange(spSaleScope);
        saleMethodSpAdapter = method.getSaleMethodSpinner(spSaleMethod);
        payTypeSpAdapter = method.getpayType(spPayMethod);
        departMentAdapter = method.getDepartMentAdapter(spDepartment);
        employeeAdapter = method.getEmployeeAdapter(spEmployee);
        method.getEmployeeAdapter(spManager);
        getGoodsType = method.getGoodsTypes(spSendMethod);


        spManager.setAdapter(employeeAdapter);
        spEmployee.setAdapter(employeeAdapter);
        spYuandan.setAdapter(employeeAdapter);

//        spDepartment.setSelection(share.getSOUTDepartment());
//        spSaleMethod.setSelection(share.getSOUTsaleMethod());
//        spSaleScope.setSelection(share.getSoutSaleRange());
//        spYuandan.setSelection(share.getSOUTYuandan());
//        spPayMethod.setSelection(share.getSOUTPayMethod());
//        spEmployee.setSelection(share.getSOUTEmployee());
//        spManager.setSelection(share.getSOUTManager());
//        spSendMethod.setSelection(share.getSOUTsendmethod());


        tvDate.setText(share.getSOUTdate());
        tvDatePay.setText(share.getSOUTdatepay());
    }

    @Override
    public void initListener() {
        redorBlue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                isRed = radioButton.getText().toString().equals("红字");
                redblue = isRed ? "红字" : "蓝字";
            }
        });
        cbIsStorage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isGetStorage = b;
            }
        });
        cbHebing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isHebing = b;
            }
        });
        autoAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAuto = b;
                share.setSOUTisAuto(b);
            }
        });

        //批号
        spPihao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InStorageNum inStorageNum = (InStorageNum) piciSpAdapter.getItem(i);
                pihao = inStorageNum.FBatchNo;
                getInstorageNum();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //扫码输入框
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

        //部门
        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Department department = (Department) departMentAdapter.getItem(i);
                departmentId = department.FItemID;
                departmentName = department.FName;
//                share.setSOUTDepartment(i);
                if (isFirst) {
                    share.setSOUTDepartment(i);
                    spDepartment.setSelection(i);
                } else {
                    spDepartment.setSelection(share.getSOUTDepartment());
                    isFirst = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //销售方式
        spSaleMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PurchaseMethod item = (PurchaseMethod) saleMethodSpAdapter.getItem(i);
                SaleMethodId = item.FItemID;
                SaleMethodName = item.FName;
//                share.setSOUTsaleMethod(i);
                if (isFirst2) {
                    share.setSOUTsaleMethod(i);
                    spSaleMethod.setSelection(i);
                } else {
                    spSaleMethod.setSelection(share.getSOUTsaleMethod());
                    isFirst2 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //销售范围
        spSaleScope.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PurchaseMethod purchaseMethod = (PurchaseMethod) slaesRange.getItem(i);
                saleRangeId = purchaseMethod.FItemID;
                saleRangeName = purchaseMethod.FName;
//                share.setSoutSaleRange(i);
                if (isFirst3) {
                    share.setSoutSaleRange(i);
                    spSaleScope.setSelection(i);
                } else {
                    spSaleScope.setSelection(share.getSoutSaleRange());
                    isFirst3 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //保管
        spYuandan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = (Employee) employeeAdapter.getItem(i);
                yuandanID = employee.FItemID;
                yuandanName = employee.FName;
//                share.setSOUTYuandan(i);
                if (isFirst4) {
                    share.setSOUTYuandan(i);
                    spYuandan.setSelection(i);
                } else {
                    spYuandan.setSelection(share.getSOUTYuandan());
                    isFirst4 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //结算方式
        spPayMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PayType payType = (PayType) payTypeSpAdapter.getItem(i);
                payTypeId = payType.FItemID;
                payTypeName = payType.FName;
//                share.setSOUTPayMethod(i);
                if (isFirst5) {
                    share.setSOUTPayMethod(i);
                    spPayMethod.setSelection(i);
                } else {
                    spPayMethod.setSelection(share.getSOUTPayMethod());
                    isFirst5 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //业务员
        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = (Employee) employeeAdapter.getItem(i);
                employeeId = employee.FItemID;
                employeeName = employee.FName;
//                share.setSOUTEmployee(i);
                if (isFirst6) {
                    share.setSOUTEmployee(i);
                    spEmployee.setSelection(i);
                } else {
                    spEmployee.setSelection(share.getSOUTEmployee());
                    isFirst6 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //主管
        spManager.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = (Employee) employeeAdapter.getItem(i);
                ManagerId = employee.FItemID;
                ManagerName = employee.FName;
//                share.setSOUTManager(i);
                if (isFirst7) {
                    share.setSOUTManager(i);
                    spManager.setSelection(i);
                } else {
                    spManager.setSelection(share.getSOUTManager());
                    isFirst7 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //交货方式
        spSendMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PurchaseMethod purchaseMethod = (PurchaseMethod) getGoodsType.getItem(i);
                sendMethodId = purchaseMethod.FItemID;
                sendMethodName = purchaseMethod.FName;
//                share.setSOUTsendmethod(i);
                if (isFirst8) {
                    share.setSOUTsendmethod(i);
                    spSendMethod.setSelection(i);
                } else {
                    spSendMethod.setSelection(share.getSOUTsendmethod());
                    isFirst8 = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //发货仓库
        spSendStorage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                storage = (Storage) storageSpAdapter.getItem(i);
                waveHouseAdapter = CommonMethod.getMethod(mContext).getWaveHouseAdapter(storage, spWavehouse);
                wavehouseID = "0";
                storageId = storage.FItemID;
                storageName = storage.FName;
                getPici();
                Log.e("点击仓库storageId", storageId);
                Log.e("点击仓库storageName", storageName);

                //获取仓库对应的仓位
                getInstorageNum();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //单位
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
                getInstorageNum();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //仓位
        spWavehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                WaveHouse waveHouse = (WaveHouse) waveHouseAdapter.getItem(i);
                wavehouseID = waveHouse.FSPID;
                wavehouseName = waveHouse.FName;
                Log.e("点击仓位wavehouse：", wavehouseName);
                getPici();
                getInstorageNum();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private String codeToPici = "";
    @Override
    protected void OnReceive(String code) {
//        setDATA(code, false);
        try {
            String[] split = code.split("\\^");
            if (split.length >= 3) {
                edCode.setText(split[0]);
//                edPihao.setText(split[1]);
                codeToPici = split[1];
//                codeToPiciSp(split[1]);
                edNum.setText(split[2]);
                Log.e(TAG, "OnReceive:" + "批号:" + split[1] + "|||条码:" + split[0]
                        + "|||数量:" + split[2]);
                setDATA(split[0], false);
            } else {
                MediaPlayer.getInstance(mContext).error();
                Toast.showText(mContext, "条码数据有误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MediaPlayer.getInstance(mContext).error();
            Toast.showText(mContext, "条码数据有误");
        }
    }

    //根据扫码中的批号定位到指定批号
    private void codeToPiciSp(String code) {
        Lg.e("定位批次：" + code);
        for (int j = 0; j < piciSpAdapter.getCount(); j++) {
            if (((InStorageNum) piciSpAdapter.getItem(j)).FBatchNo.equals(code)) {
                Lg.e("根据条码，获取到相应调出仓库:" + piciSpAdapter.getItem(j).toString());
                spPihao.setSelection(j);
                break;
            }
        }
    }


    private void getProductOL(DownloadReturnBean dBean, int j) {
        product = dBean.products.get(j);
        tvorisAuto(product);
    }

    //设置或查找product
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
                            Log.e(TAG, "setDATA联网获取product数据:" + dBean.products.get(0));
                            getProductOL(dBean, 0);
                            default_unitID = dBean.products.get(0).FUnitID;
                            chooseUnit(default_unitID);
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
                                    chooseUnit(default_unitID);
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
                final List<BarCode> barCodes = barCodeDao.queryBuilder().where(
                        BarCodeDao.Properties.FBarCode.eq(fnumber)
                ).build().list();

                if (barCodes.size() > 0) {
                    if (barCodes.size() == 1) {
                        products = productDao.queryBuilder().where(
                                ProductDao.Properties.FItemID.eq(barCodes.get(0).FItemID)
                        ).build().list();
                        default_unitID = barCodes.get(0).FUnitID;
                        Log.e(TAG, "setDATA联网获取product数据:" + products.get(0));
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

    //定位到指定单位
    private void chooseUnit(final String unitId) {
        if (unitId != null && !"".equals(unitId)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < unitAdapter.getCount(); i++) {
                        if (unitId.equals(((Unit) unitAdapter.getItem(i)).FMeasureUnitID)) {
                            spUnit.setSelection(i);
                            Log.e(TAG, "定位了单位：" + ((Unit) unitAdapter.getItem(i)).toString());
                        }
                    }
                }
            }, 100);
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

    //获取批次
    private void getPici() {
        List<InStorageNum> container1 = new ArrayList<>();
        piciSpAdapter = new PiciSpAdapter(mContext, container1);
        spPihao.setAdapter(piciSpAdapter);
        if (fBatchManager) {
            spPihao.setEnabled(true);
            if (!BasicShareUtil.getInstance(mContext).getIsOL()) {
                piciSpAdapter = CommonMethod.getMethod(mContext).getPici(storage, wavehouseID, product, spPihao);
                codeToPiciSp(codeToPici);
            } else {
                final List<InStorageNum> container = new ArrayList<>();
//                piciSpAdapter = new PiciSpAdapter(mContext, container);
//                spPihao.setAdapter(piciSpAdapter);

                GetBatchNoBean bean = new GetBatchNoBean();
                bean.ProductID = product.FItemID;
                bean.StorageID = storageId;
                bean.WaveHouseID = wavehouseID;
                String json = new Gson().toJson(bean);
                Log.e(TAG, "getPici批次提交：" + json);
                Asynchttp.post(mContext, getBaseUrl() + WebApi.GETPICI, json, new Asynchttp.Response() {
                    @Override
                    public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                        Log.e(TAG, "getPici获取数据：" + cBean.returnJson);
                        DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                        if (dBean.InstorageNum != null) {
                            for (int i = 0; i < dBean.InstorageNum.size(); i++) {
                                if (dBean.InstorageNum.get(i).FQty != null
                                        && Double.parseDouble(dBean.InstorageNum.get(i).FQty) > 0) {
                                    Log.e(TAG, "有库存的批次：" + dBean.InstorageNum.get(i).toString());
                                    container.add(dBean.InstorageNum.get(i));
                                }
                            }
                            piciSpAdapter = new PiciSpAdapter(mContext, container);
                            spPihao.setAdapter(piciSpAdapter);
                        }
                        piciSpAdapter.notifyDataSetChanged();
                        codeToPiciSp(codeToPici);
                    }

                    @Override
                    public void onFailed(String Msg, AsyncHttpClient client) {
                        Log.e(TAG, "getPici获取数据错误：" + Msg);
//                        Toast.showText(mContext, Msg);
                    }
                });
            }

        } else {
            spPihao.setEnabled(false);
        }

    }

    private void tvorisAuto(final Product product) {
        edCode.setText(product.FNumber);
        tvModel.setText(product.FModel);
        edPricesingle.setText(df.format(Double.parseDouble(product.FSalePrice)));
        tvGoodName.setText(product.FName);
        fBatchManager = (product.FBatchManager) != null && (product.FBatchManager).equals("1");
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
        getPici();
        unitAdapter = CommonMethod.getMethod(mContext).getUnitAdapter(product.FUnitGroupID, spUnit);
        chooseUnit(default_unitID);
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
        getInstorageNum();

        if (isAuto) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    edNum.setText("1.0");
                    edOnsale.setText("100");
                    Addorder();
                }
            }, 150);

        }
    }


    //获取库存
    private void getInstorageNum() {
        Log.e(TAG, "getInstorageNum");
        if (product == null) {
            return;
        }
        if (fBatchManager) {
            if (pihao == null || pihao.equals("")) {
                pihao = "0";
            }
        } else {
            pihao = "";
        }
        if (wavehouseID == null) {
            wavehouseID = "0";
        }
        if (BasicShareUtil.getInstance(mContext).getIsOL()) {
            InStoreNumBean iBean = new InStoreNumBean();
            iBean.FStockPlaceID = wavehouseID;      //仓位ID
            iBean.FBatchNo = pihao;         //批次
            iBean.FStockID = storage.FItemID;       //
            iBean.FItemID = product.FItemID;
            String json = new Gson().toJson(iBean);
            Log.e(TAG, "getInstorageNum库存提交：" + json);
            Asynchttp.post(mContext, getBaseUrl() + WebApi.GETINSTORENUM, json, new Asynchttp.Response() {
                @Override
                public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                    Log.e(TAG, "库存返回：" + cBean.returnJson);
                    storenum = Double.parseDouble(cBean.returnJson);
                    tvNuminstorage.setText((storenum / unitrate) + "");
                }

                @Override
                public void onFailed(String Msg, AsyncHttpClient client) {
                    Log.e(TAG, "库存错误返回：" + Msg);
//                    Toast.showText(mContext, Msg);
                    tvNuminstorage.setText("0");
                    storenum = 0.0;
                }
            });
        } else {
            InStorageNumDao inStorageNumDao = daoSession.getInStorageNumDao();
            List<InStorageNum> list1 = inStorageNumDao.queryBuilder().
                    where(InStorageNumDao.Properties.FItemID.eq(product.FItemID),
                            InStorageNumDao.Properties.FStockID.eq(storage.FItemID),
                            InStorageNumDao.Properties.FStockPlaceID.eq(wavehouseID),
                            InStorageNumDao.Properties.FBatchNo.eq(pihao)).build().list();
            Log.e("SoldOutActivity", "list1.size():" + list1.size());
            Log.e("SoldOutActivity", product.FItemID);
            Log.e("SoldOutActivity", wavehouseID);
            Log.e("SoldOutActivity", "pici:" + pihao);
            Log.e("SoldOutActivity", storage.FItemID);
            if (list1.size() > 0) {
                Log.e("FQty", list1.get(0).FQty);
                storenum = Double.parseDouble(list1.get(0).FQty);
                Log.e("qty", storenum + "");
                if (storenum != null) {
                    tvNuminstorage.setText((storenum / unitrate) + "");
                }

            } else {
                storenum = 0.0;
                tvNuminstorage.setText("0");
            }

        }


    }


    @OnClick({R.id.search_supplier, R.id.scanbyCamera, R.id.search, R.id.btn_add, R.id.btn_finishorder, R.id.btn_backorder, R.id.btn_checkorder, R.id.tv_date, R.id.tv_date_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择客户
            case R.id.search_supplier:
                Bundle b = new Bundle();
                b.putString("search", edClient.getText().toString());
                b.putInt("where", Info.SEARCHCLIENT);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTCLIRNT, b);
                break;
            case R.id.scanbyCamera:
                Intent in = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(in, 0);
                break;
            case R.id.search:
                Log.e("search", "onclick");
                Bundle b1 = new Bundle();
                b1.putString("search", edCode.getText().toString());
                b1.putInt("where", Info.SEARCHPRODUCT);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULT, b1);
                break;
            case R.id.btn_add:
                Addorder();
                break;
            case R.id.btn_finishorder:
                finishOrder();
                break;
            case R.id.btn_backorder:
                upload();
                break;
            case R.id.btn_checkorder:
                Bundle b2 = new Bundle();
                b2.putInt("activity", SOLDOUT);
                startNewActivity(ProductCheckActivity.class, R.anim.activity_fade_in, R.anim.activity_fade_out, false, b2);
                break;
            case R.id.tv_date:
                getdate();
                break;
            case R.id.tv_date_pay:
                getPaydate();
                break;
        }
    }

    public void finishOrder() {
        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
        ab.setTitle("确认使用完单");
        ab.setMessage("确认？");
        ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ordercode++;
                Log.e("ordercode", ordercode + "");
                share.setSOUTOrderCode(ordercode);
            }
        });
        ab.setNegativeButton("取消", null);
        ab.create().show();

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
        } else if (requestCode == Info.SEARCHFORRESULTCLIRNT) {
            if (resultCode == Info.SEARCHFORRESULTCLIRNT) {
                Bundle b = data.getExtras();
                clientId = b.getString("001");
                clientName = b.getString("002");
                edClient.setText(clientName);
                setfocus(edCode);
            }
        }
    }

    private void Addorder() {
        if (wavehouseID == null) {
            wavehouseID = "0";
        }
        String discount = edOnsale.getText().toString();
        String num="";
        if (isRed) {
            num = "-" + edNum.getText().toString();
        } else {
            num = edNum.getText().toString();
        }
        T_DetailDao t_detailDao = daoSession.getT_DetailDao();
        T_mainDao t_mainDao = daoSession.getT_mainDao();
        if (edClient.getText().toString().equals("") || edOnsale.getText().toString().equals("") || edCode.getText().toString().equals("") || edPricesingle.getText().toString().equals("") || edNum.getText().toString().equals("")) {
            MediaPlayer.getInstance(mContext).error();
            if (edCode.getText().toString().equals("")) {
                Toast.showText(mContext, "请输入物料编号");
            } else if (edPricesingle.getText().toString().equals("")) {
                Toast.showText(mContext, "请输入单价");
            } else if (edNum.getText().toString().equals("")) {
                Toast.showText(mContext, "请输入数量");
            } else if (edOnsale.getText().toString().equals("")) {
                Toast.showText(mContext, "请输入折扣");
            } else if (edClient.getText().toString().equals("")) {
                Toast.showText(mContext, "请输入客户");
            }
        } else {

            if ((storenum / unitrate) < Double.parseDouble(num)) {
                MediaPlayer.getInstance(mContext).error();
                Toast.showText(mContext, "大兄弟，库存不够了");
            } else {
                if (isHebing) {
                    List<T_Detail> detailhebing = t_detailDao.queryBuilder().where(
                            T_DetailDao.Properties.Activity.eq(SOLDOUT),
                            T_DetailDao.Properties.FOrderId.eq(ordercode),
                            T_DetailDao.Properties.FProductId.eq(product.FItemID),
                            T_DetailDao.Properties.FBatch.eq(pihao == null ? "" : pihao),
                            T_DetailDao.Properties.FUnitId.eq(unitId),
                            T_DetailDao.Properties.FStorageId.eq(storageId),
                            T_DetailDao.Properties.FDiscount.eq(discount),
                            T_DetailDao.Properties.FRemark.eq(etBeizhu.getText().toString()),
                            T_DetailDao.Properties.FPositionId.eq(wavehouseID),
                            T_DetailDao.Properties.FDiscount.eq(discount)
                    ).build().list();
                    if (detailhebing.size() > 0) {
                        for (int i = 0; i < detailhebing.size(); i++) {
                            num = (Double.parseDouble(num) + Double.parseDouble(detailhebing.get(i).FQuantity)) + "";
                            t_detailDao.delete(detailhebing.get(i));
                        }
                    }
                }
                List<T_main> dewlete = t_mainDao.queryBuilder().where(T_mainDao.Properties.OrderId.eq(ordercode)).build().list();
                t_mainDao.deleteInTx(dewlete);
                String second = getTimesecond();
                T_main t_main = new T_main();
                t_main.FDepartment = departmentName == null ? "" : departmentName;
                t_main.FDepartmentId = departmentId == null ? "" : departmentId;
                t_main.FIndex = second;
                t_main.FPaymentDate = tvDatePay.getText().toString();
                t_main.orderId = ordercode;
                t_main.orderDate = tvDate.getText().toString();
                t_main.FPurchaseUnit = unitName == null ? "" : unitName;
                t_main.FSalesMan = employeeName == null ? "" : employeeName;
                t_main.FSalesManId = employeeId == null ? "" : employeeId;
                t_main.FMaker = share.getUserName();
                t_main.FMakerId = share.getsetUserID();
                t_main.FDirector = ManagerName == null ? "" : ManagerName;
                t_main.FDirectorId = ManagerId == null ? "" : ManagerId;
                t_main.saleWay = SaleMethodName == null ? "" : SaleMethodName;
                t_main.FDeliveryAddress = "";
                t_main.FRemark = edZhaiyao.getText().toString();
                t_main.saleWayId = SaleMethodId == null ? "" : SaleMethodId;
                t_main.FCustody = saleRangeName == null ? "" : saleRangeName;
                t_main.FCustodyId = saleRangeId == null ? "" : saleRangeId;
                t_main.FAcount = sendMethodName == null ? "" : sendMethodName;
                t_main.FAcountID = sendMethodId == null ? "" : sendMethodId;
                t_main.Rem = edZhaiyao.getText().toString();
                t_main.supplier = clientName == null ? "" : clientName;
                t_main.supplierId = clientId == null ? "" : clientId;
                t_main.FSendOutId = payTypeId == null ? "" : payTypeId;
                t_main.activity = SOLDOUT;
                t_main.sourceOrderTypeId = yuandanID == null ? "" : yuandanID;


                T_Detail t_detail = new T_Detail();
                t_detail.FBatch = pihao == null ? "" : pihao;
                t_detail.FOrderId = ordercode;
                t_detail.FProductCode = edCode.getText().toString();
                t_detail.FProductId = product.FItemID;
                t_detail.model = product.FModel;
                t_detail.FProductName = product.FName;
                t_detail.FIndex = second;
                t_detail.FUnitId = unitId == null ? "" : unitId;
                t_detail.FUnit = unitName == null ? "" : unitName;
                t_detail.FStorage = storageName == null ? "" : storageName;
                t_detail.FStorageId = storageId == null ? "0" : storageId;
                t_detail.FPosition = wavehouseName == null ? "" : wavehouseName;
                t_detail.FPositionId = wavehouseID == null ? "0" : wavehouseID;
                t_detail.activity = SOLDOUT;
                t_detail.FDiscount = discount;
                t_detail.FQuantity = num;
                t_detail.unitrate = unitrate;
                t_detail.FTaxUnitPrice = edPricesingle.getText().toString();
                t_detail.FRemark = etBeizhu.getText().toString();

                if (!BasicShareUtil.getInstance(mContext).getIsOL()) {
                    long insert = t_detailDao.insert(t_detail);
                    long insert1 = t_mainDao.insert(t_main);
                    if (insert > 0 && insert1 > 0) {
                        InStorageNumDao inStorageNumDao = daoSession.getInStorageNumDao();
                        List<InStorageNum> innum = inStorageNumDao.queryBuilder().
                                where(InStorageNumDao.Properties.FItemID.eq(product.FItemID),
                                        InStorageNumDao.Properties.FStockID.eq(storageId),
                                        InStorageNumDao.Properties.FStockPlaceID.eq(wavehouseID == null ? "0" : wavehouseID),
                                        InStorageNumDao.Properties.FBatchNo.eq(pihao == null ? "" : pihao)).build().list();
                        Toast.showText(mContext, "添加成功");
                        MediaPlayer.getInstance(mContext).ok();
                        Log.e("qty_insert", Double.parseDouble(innum.get(0).FQty) + "");
                        Log.e("qty_insert", (Double.parseDouble(edNum.getText().toString()) * unitrate) + "");
                        Log.e("qty_insert", (unitrate) + "");
                        if (isRed) {
                            innum.get(0).FQty = String.valueOf(((Double.parseDouble(innum.get(0).FQty) + (Double.parseDouble(edNum.getText().toString()) * unitrate))));
                        } else {
                            innum.get(0).FQty = String.valueOf(((Double.parseDouble(innum.get(0).FQty) - (Double.parseDouble(edNum.getText().toString()) * unitrate))));
                        }
//                        innum.get(0).FQty = String.valueOf(((Double.parseDouble(innum.get(0).FQty) - (Double.parseDouble(edNum.getText().toString()) * unitrate))));
                        inStorageNumDao.update(innum.get(0));
                        resetAll();
                    } else {
                        Toast.showText(mContext, "添加失败");
                        MediaPlayer.getInstance(mContext).error();
                    }
                } else {
                    long insert = t_detailDao.insert(t_detail);
                    long insert1 = t_mainDao.insert(t_main);
                    resetAll();
                    if (insert > 0 && insert1 > 0) {
                        Toast.showText(mContext, "添加成功");
                        MediaPlayer.getInstance(mContext).ok();
                    }
                }
            }
        }
    }

    private void resetAll() {
        edNum.setText("");
        edPricesingle.setText("");
        edZhaiyao.setText("");
        edCode.setText("");
        tvNuminstorage.setText("");
        etBeizhu.setText("");
        tvGoodName.setText("");
        tvModel.setText("");
        edOnsale.setText("0");
        List<InStorageNum> container = new ArrayList<>();
        piciSpAdapter = new PiciSpAdapter(mContext, container);
        spPihao.setAdapter(piciSpAdapter);
        setfocus(edCode);
    }

    private void upload() {
        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
        PurchaseInStoreUploadBean.purchaseInStore puBean = pBean.new purchaseInStore();
        t_mainDao = daoSession.getT_mainDao();
        t_detailDao = daoSession.getT_DetailDao();
        ArrayList<String> detailContainer = new ArrayList<>();
        ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data = new ArrayList<>();
        List<T_main> mains = t_mainDao.queryBuilder().where(T_mainDao.Properties.Activity.eq(SOLDOUT)).build().list();
        for (int i = 0; i < mains.size(); i++) {
            if (i > 0 && mains.get(i).orderId == mains.get(i - 1).orderId) {
            } else {
                detailContainer.clear();
                String main;
                String detail = "";
                T_main t_main = mains.get(i);
                main = t_main.FMakerId + "|" +
                        t_main.orderDate + "|" +
                        t_main.FPaymentDate + "|" +
                        t_main.saleWayId + "|" +
                        t_main.FDeliveryAddress + "|" +
                        t_main.FDepartmentId + "|" +
                        t_main.FSalesManId + "|" +
                        t_main.FDirectorId + "|" +
                        t_main.supplierId + "|" +
                        t_main.Rem + "|" +
                        t_main.FSalesManId + "|" +
                        t_main.sourceOrderTypeId + "|" +
                        t_main.FRedBlue + "|" +
                        t_main.FCustodyId + "|" +
                        t_main.sourceOrderTypeId + "|";
                puBean.main = main;
                List<T_Detail> details = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.FOrderId.eq(t_main.orderId),
                        T_DetailDao.Properties.Activity.eq(SOLDOUT)
                ).build().list();
                for (int j = 0; j < details.size(); j++) {
                    if (j != 0 && j % 49 == 0) {
                        Log.e("j%49", j % 49 + "");
                        T_Detail t_detail = details.get(j);
                        detail = detail +
                                t_detail.FProductId + "|" +
                                t_detail.FUnitId + "|" +
                                t_detail.FTaxUnitPrice + "|" +
                                t_detail.FQuantity + "|" +
                                t_detail.FDiscount + "|" +
                                t_detail.FStorageId + "|" +
                                t_detail.FBatch + "|" +
                                t_detail.FPositionId + "|" +
                                t_detail.FRemark + "|";
                        detail = detail.subSequence(0, detail.length() - 1).toString();
                        detailContainer.add(detail);
                        detail = "";
                    } else {
                        Log.e("j", j + "");
                        Log.e("details.size()", details.size() + "");
                        T_Detail t_detail = details.get(j);
                        detail = detail +
                                t_detail.FProductId + "|" +
                                t_detail.FUnitId + "|" +
                                t_detail.FTaxUnitPrice + "|" +
                                t_detail.FQuantity + "|" +
                                t_detail.FDiscount + "|" +
                                t_detail.FStorageId + "|" +
                                t_detail.FBatch + "|" +
                                t_detail.FPositionId + "|" +
                                t_detail.FRemark + "|";
                        Log.e("detail1", detail);
                    }

                }
                if (detail.length() > 0) {
                    detail = detail.subSequence(0, detail.length() - 1).toString();
                }

                Log.e("detail", detail);
                detailContainer.add(detail);
                puBean.detail = detailContainer;
                data.add(puBean);
            }

        }
        postToServer(data);
    }

    private void postToServer(ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data) {
        final ProgressDialog pg = new ProgressDialog(mContext);
        pg.setMessage("请稍后...");
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.show();
        PurchaseInStoreUploadBean pBean = new PurchaseInStoreUploadBean();
        pBean.list = data;
        Gson gson = new Gson();
        Asynchttp.post(mContext, getBaseUrl() + WebApi.UPLOADSOUT, gson.toJson(pBean), new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                MediaPlayer.getInstance(mContext).ok();
                Toast.showText(mContext, "上传成功");
                List<T_Detail> list = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.Activity.eq(SOLDOUT)
                ).build().list();
                List<T_main> list1 = t_mainDao.queryBuilder().where(
                        T_mainDao.Properties.Activity.eq(SOLDOUT)
                ).build().list();
                for (int i = 0; i < list.size(); i++) {
                    t_detailDao.delete(list.get(i));
                }
                for (int i = 0; i < list1.size(); i++) {
                    t_mainDao.delete(list1.get(i));
                }
                pg.dismiss();
            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                MediaPlayer.getInstance(mContext).error();
                Toast.showText(mContext, Msg);
                pg.dismiss();
            }
        });
    }

    private void getPaydate() {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            }
        }, year, day, month);

        datePickerDialog.setButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int year = datePickerDialog.getDatePicker().getYear();
                int month = datePickerDialog.getDatePicker().getMonth();
                int day = datePickerDialog.getDatePicker().getDayOfMonth();
                datePay = year + "-" + ((month < 10) ? "0" + (month + 1) : (month + 1)) + "-" + ((day < 10) ? "0" + day : day);
                tvDatePay.setText(datePay);
                Toast.showText(mContext, datePay);
                datePickerDialog.dismiss();

            }
        });
        datePickerDialog.show();
    }

    private void getdate() {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            }
        }, year, day, month);

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

    //用于adpater首次更新时，不存入默认值，而是选中之前的选项
    private boolean isFirst = false;
    private boolean isFirst2 = false;
    private boolean isFirst3 = false;
    private boolean isFirst4 = false;
    private boolean isFirst5 = false;
    private boolean isFirst6 = false;
    private boolean isFirst7 = false;
    private boolean isFirst8 = false;
}

