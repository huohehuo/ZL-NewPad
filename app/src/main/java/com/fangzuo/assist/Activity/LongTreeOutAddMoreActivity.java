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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.BillRvAdapter;
import com.fangzuo.assist.Adapter.CSpAdapter;
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
import com.fangzuo.assist.Beans.BbbBean;
import com.fangzuo.assist.Beans.ClassificationBean;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.PurchaseInStoreUploadBean;
import com.fangzuo.assist.Dao.Classification;
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
import com.fangzuo.assist.Service.BackDataService;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.CalculateUtil;
import com.fangzuo.assist.Utils.CommonMethod;
import com.fangzuo.assist.Utils.CommonUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.MediaPlayer;
import com.fangzuo.assist.Utils.OkgoUtil;
import com.fangzuo.assist.Utils.PgUtil;
import com.fangzuo.assist.Utils.RecyclerViewDivider;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.VibratorUtil;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.greendao.gen.ClientDao;
import com.fangzuo.greendao.gen.DaoSession;
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

public class LongTreeOutAddMoreActivity extends BaseActivity implements BillRvAdapter.RvInnerClickListener {




    @BindView(R.id.classification)
    Spinner classification;
    @BindView(R.id.et_length)
    EditText etLength;
    @BindView(R.id.genshu)
    TextView genshu;
    @BindView(R.id.tiji)
    TextView tiji;
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

    @BindView(R.id.sp_send_storage)
    Spinner spSendStorage;
    @BindView(R.id.sp_wavehouse)
    Spinner spWavehouse;
    @BindView(R.id.sp_unit)
    Spinner spUnit;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_orderID)
    TextView tvOrderId;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private LongTreeOutAddMoreActivity mContext;
    private DecimalFormat df;
    private DecimalFormat df2;
    private DaoSession daoSession;
    private int year;
    private int month;
    private int day;
    private long ordercode;
    private StorageSpAdapter storageSpAdapter;
    private CommonMethod method;
    private PayMethodSpAdapter slaesRange;
    private PayMethodSpAdapter saleMethodSpAdapter;
    private EmployeeSpAdapter employeeAdapter;
    private EmployeeSpAdapter managerAdapter;
    private EmployeeSpAdapter yuandanAdapter;
    private CompanySpAdapter companySpAdapter;
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
    public String diameter;

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
    private LinearLayoutManager mLayoutManager;
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
    private ArrayList<Classification> c_container;
    private CSpAdapter cSpAdapter;
    private ProgressDialog pg_getPorduct;
    private String parentID;
    private String result;

    private String CompanyId;
    private String CompanyName;
    ProgressDialog canclePg;
    private int activity;
    @Override
    public void initView() {
        setContentView(R.layout.activity_long_tree_out_add_more);
        mContext = this;
        ButterKnife.bind(mContext);
        df = new DecimalFormat("######0.000");
        df2 = new DecimalFormat("######0.0");
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(mContext, 5);
        getClassification();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        canclePg= new ProgressDialog(mContext);
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
    //获取物料分类
    public void getClassification(){
        c_container = new ArrayList<>();
        cSpAdapter = new CSpAdapter(mContext, c_container);
        classification.setAdapter(cSpAdapter);

        OkgoUtil.getInstance(mContext).post("GetClassification", "{}", new OkgoUtil.ApiResponse() {
            @Override
            public void doBefore(Disposable d) {

            }

            @Override
            public void onSucceed(String returnJson) {
                ClassificationBean c = new Gson().fromJson(returnJson,ClassificationBean.class);
                c_container.addAll(c.list);
                cSpAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String errorMsg) {
                Toast.showText(mContext,errorMsg);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void initData() {

        tempMainDao = daoSession.getTempMainDao();
        tempDetailDao = daoSession.getTempDetailDao();
        container = new ArrayList<>();
        fContainer = new ArrayList<>();
        huidanList = new ArrayList<>();
        padapter = new Padapter(huidanList,mContext);

        billRvAdapter = new BillRvAdapter(mContext, container,false);
        rvBill.setLayoutManager(mLinearLayoutManager);
        rvBill.setAdapter(billRvAdapter);
        rvBill.setItemAnimator(new DefaultItemAnimator());
        rvBill.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        billRvAdapter.setInnerClickListener(this);
        billRvAdapter.notifyDataSetChanged();

        container_Width = new ArrayList<>();
        numRvAdapter = new NumRvAdapter(mContext, container_Width);
        rvNumChoose.setAdapter(numRvAdapter);

        rvNumChoose.addItemDecoration(new RecyclerViewDivider(3));
        rvNumChoose.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 120; i++) {
            if (i % 2 == 0) container_Width.add(i + "");
        }
        numRvAdapter.notifyDataSetChanged();
        gridLayoutManager.scrollToPosition(24);
        RefreshList();
        method = CommonMethod.getMethod(mContext);


        LoadBasicData();
    }

    private ArrayList<FirstCheck> fContainer;
    private boolean hasData=false;
    public void getHuiDanData() {
        container.clear();
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
                "FPRODUCT_NAME,FPRODUCT_ID ORDER BY FORDER_ID DESC", new String[]{Config.CHOOSE_SECOND_FRAGMENT_LONG + ""});

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
        if (fContainer.size()>0){
            hasData=true;
        }else{
            hasData=false;
        }
        huidanList.addAll(fContainer);
        padapter.notifyDataSetChanged();
    }
    private void LoadBasicData() {
        storageSpAdapter = CommonMethod.getMethod(mContext).getStorageSpinner(spSendStorage);

    }

    //获取物料后
    public void Add(final String item){
        if (product != null) {
            String thisModel = product.FModel;
            if (product.FM == null || product.FM.equals("")) {
                Toast.showText(mContext,"物料信息中找不到长度");
                MediaPlayer.getInstance(mContext).error();
                return;
            }
            Lg.e("product"+product.toString());


            pg = new ProgressDialog(mContext);
            pg.setMessage("添加中...");
            pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pg.setCancelable(false);
            pg.show();
            rvNumChoose.setClickable(false);
            if ("添加完成".equals(AddTempOrder(numRvAdapter.getItem(posi)))){
                RefreshList();
                for (int j = 0; j < billRvAdapter.getItemCount(); j++) {
                    String posit =((TempDetail) billRvAdapter.getItems(j)).diameter+"";
                    if (posit.equals(numRvAdapter.getItem(posi))) {
//                                            Log.e("asdf",((TempDetail)billRvAdapter.getItems(j)).diameter+"--"+"j"+"---"+numRvAdapter.getItem(position));

                        MoveToPosition(mLinearLayoutManager,rvBill,j);
                        billRvAdapter.goToPostion(numRvAdapter.getItem(posi),thisModel,storageId,nowLength);

                        break;
                    }
                }
//                        rvNumChoose.setVisibility(View.VISIBLE);
                rvNumChoose.setClickable(true);
                pg.dismiss();
            }else{
                Toast.showText(mContext,"添加出错");
//                        rvNumChoose.setVisibility(View.VISIBLE);
                rvNumChoose.setClickable(true);
                pg.dismiss();
            }

//            Observable.create(new ObservableOnSubscribe<String>() {
//                        @Override
//                        public void subscribe(ObservableEmitter<String> e) throws Exception {
//                            if ("".equals(etLength.getText().toString())){
//                                e.onNext("长度不能为空");
//                                MediaPlayer.getInstance(mContext).error();
//                                e.onComplete();
//                            }
//                            if (etVanNo.getText().toString().equals("")) {
//                                e.onNext("车牌号不能为空");
//                                MediaPlayer.getInstance(mContext).error();
//                                e.onComplete();
//                            } else if (edClient.getText().toString().equals("")) {
//                                e.onNext("客户不能为空");
//                                MediaPlayer.getInstance(mContext).error();
//                                e.onComplete();
//                            }else if (!"".equals(edClient.getText().toString().trim())) {
//                                ClientDao clientDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getClientDao();
//                                List<Client> clients = clientDao.queryBuilder().where(
//                                        ClientDao.Properties.FName.eq(edClient.getText().toString().trim()
//                                        )).list();
//                                if (clients.size() > 0) {
//                                    e.onNext(AddTempOrder(item));
//                                    e.onComplete();
//                                } else {
//                                    e.onNext("客户信息有误");
//                                    MediaPlayer.getInstance(mContext).error();
//                                    e.onComplete();
//                                }
//
//                            }  else if (product.FM == null || product.FM.equals("")) {
//                                e.onNext("物料信息中找不到长度");
//                                MediaPlayer.getInstance(mContext).error();
//                                e.onComplete();
//                            } else if (etDriver.getText().toString().equals("")) {
//                                e.onNext("司机不能为空");
//                                MediaPlayer.getInstance(mContext).error();
//                                e.onComplete();
//                            } else {
//                                e.onNext(AddTempOrder(item));
//                                e.onComplete();
//                            }
//
//
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Observer<String>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            disposable = d;
//                            pg = new ProgressDialog(mContext);
//                            pg.setMessage("添加中...");
//                            pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                            pg.setCancelable(false);
//                            pg.show();
//                            rvNumChoose.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onNext(String s) {
//                            Log.e("rx", "onNext");
//                            Toast.showText(mContext, s);
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e("rx", "Error");
//                            rvNumChoose.setVisibility(View.VISIBLE);
////                            Toast.showText(mContext, "出错了");
//                            RefreshList();
//                            e.printStackTrace();
//
//                            pg.dismiss();
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            Log.e("rx", "onComplete");
//                            RefreshList();
//                            for (int j = 0; j < billRvAdapter.getItemCount(); j++) {
//                                String posit =((TempDetail) billRvAdapter.getItems(j)).diameter+"";
//                                if (posit.equals(numRvAdapter.getItem(posi))) {
//                                    MoveToPosition(mLinearLayoutManager,rvBill,j);
//                                    billRvAdapter.goToPostion(numRvAdapter.getItem(posi));
//                                    break;
//                                }
//                            }
//                            rvNumChoose.setVisibility(View.VISIBLE);
//                            pg.dismiss();
//                        }
//                    });
        } else {
            Toast.showText(mContext, "未选择物料");
        }
    }

    private int posi;
    @Override
    public void initListener() {
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
            public void onItemClick(View view, final int position) {
                posi=position;
                String item = numRvAdapter.getItem(position);
                if(!etLength.getText().toString().equals("")){


                    if (null!=storageId&&"".equals(storageId)){
                        Toast.showText(mContext,"仓库不能为空");
                        MediaPlayer.getInstance(mContext).error();
                        return;
                    }
                    if (etLength.getText().toString().contains(".")){
                        Toast.showText(mContext,"请输入以分米为单位的长度");
                        MediaPlayer.getInstance(mContext).error();
                        return;
                    }

                    getProduct(item);
                }else{
                    MediaPlayer.getInstance(mContext).error();
                    Toast.showText(mContext,"长度不能为空");
                }
            }
        });

        classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Classification c = cSpAdapter.getItembyPosition(i);
                parentID = c.FItemID;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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


    boolean isOk = true;
    private void getProduct(final String item) {
        if (!isOk){
            return ;
        }
        isOk=false;
        result = "";
        BbbBean bbbBean = new BbbBean();
        bbbBean.diameter = item==null?"-1":item;
        bbbBean.parentID = parentID==null?"0":parentID;
        OkgoUtil.getInstance(mContext).post("SearchProductByClass",
                new Gson().toJson(bbbBean),
                new OkgoUtil.ApiResponse() {
                    @Override
                    public void doBefore(Disposable d) {
                        pg_getPorduct = new ProgressDialog(mContext);
                        pg_getPorduct.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pg_getPorduct.setMessage("正在获取物料...");
                        pg_getPorduct.show();
                    }

                    @Override
                    public void onSucceed(String returnJson) {
                        //添加到本地表，供退单使用
                        BackDataService.saveProductOfLong(mContext,returnJson);
                        product = new Gson().fromJson(returnJson,Product.class);
                        pg_getPorduct.dismiss();
                        result = "1";
                        pg_getPorduct.dismiss();

                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        isOk=true;
                        Log.e("pro",errorMsg);
                        Toast.showText(mContext,errorMsg);
                        pg_getPorduct.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        isOk=true;
                        Add(item);
                    }
                });
//        return "";
    }

    @Override
    protected void OnReceive(String code) {

    }
    public static boolean isBack=true;
    @Override
    protected void onResume() {
        super.onResume();

        getHuiDanData();
    }

    private void tvorisAuto() {
        rvNumChoose.scrollToPosition(Integer.parseInt(product.FMindiameter == null ? "0" : product.FMindiameter) / 2);
        rvNumChoose.setVisibility(View.VISIBLE);
        if (wavehouseID == null) {
            wavehouseID = "0";
        }
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
    private Padapter padapter;
    private LayoutInflater inflater;
    boolean isc=true;
    @OnClick({R.id.btn_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("确认追加");
//                ab.setMessage("确认添加数据?");
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            Addorder();
                        }catch (Exception e){
                            Toast.showText(mContext,"无数据添加");
                        }
                    }
                }).setNegativeButton("取消", null).create().show();
                break;
            case R.id.btn_back:
                finish();
                break;

        }
    }


    double getLength = 0d;
    private String nowLength="";
    //增加到临时表
    public String AddTempOrder(String item) {
//        List<TempMain> tempMains = tempMainDao.loadAll();
//        if (tempMains.size() > 0) {
//            tempMainDao.deleteAll();
//        }

        String length = etLength.getText().toString();
        getLength = Double.parseDouble(length);
        getLength = getLength/10;
        length = df2.format(getLength);
        nowLength = length;

//        TempMain tempMain = new TempMain();
//        tempMain.activity = Config.CHOOSE_SECOND_FRAGMENT_LONG;
//        tempMain.orderID = ordercode;
//        tempMain.FIndex = getTimesecond();
//        tempMain.FDepartment = departmentName == null ? "" : departmentName;
////        tempMain.FDepartmentId = departmentId == null ? "" : departmentId;
////        tempMain.FPaymentDate = tvDatePay.getText().toString();
////        tempMain.orderDate = tvDate.getText().toString();
//        tempMain.FPurchaseUnit = unitName == null ? "" : unitName;
////        tempMain.FSalesMan = employeeName == null ? "" : employeeName;
////        tempMain.FSalesManId = employeeId == null ? "" : employeeId;
//        tempMain.FMaker = share.getUserName();
//        tempMain.FMakerId = share.getsetUserID();
////        tempMain.FDirector = ManagerName == null ? "" : ManagerName;
////        tempMain.FDirectorId = ManagerId == null ? "" : ManagerId;
//        tempMain.saleWay = SaleMethodName == null ? "" : SaleMethodName;
//        tempMain.FDeliveryAddress = "";
////        tempMain.FRemark = edZhaiyao.getText().toString();
//        tempMain.saleWayId = SaleMethodId == null ? "" : SaleMethodId;
//        tempMain.FCustody = saleRangeName == null ? "" : saleRangeName;
//        tempMain.FCustodyId = saleRangeId == null ? "" : saleRangeId;
//        tempMain.FAcount = sendMethodName == null ? "" : sendMethodName;
////        tempMain.FAcountID = sendMethodId == null ? "" : sendMethodId;
////        tempMain.Rem = edZhaiyao.getText().toString();
////        tempMain.supplier = clientName == null ? "" : clientName;
////        tempMain.supplierId = clientId == null ? "" : clientId;
////        tempMain.FSendOutId = payTypeId == null ? "" : payTypeId;
////        tempMain.sourceOrderTypeId = yuandanID == null ? "" : yuandanID;
////        tempMain.VanNo = etVanNo.getText().toString();
////        tempMain.VanDriver = etDriver.getText().toString();
////        tempMain.companyId = CompanyId==null?"":CompanyId;
//
//        long insert = tempMainDao.insert(tempMain);


        List<TempDetail> list = tempDetailDao.queryBuilder().where(
                TempDetailDao.Properties.Length.eq(length),
                TempDetailDao.Properties.FOrderId.eq(ordercode),
                TempDetailDao.Properties.FProductId.eq(product.FItemID),
                TempDetailDao.Properties.FPositionId.eq(wavehouseID),
                TempDetailDao.Properties.FStorageId.eq(storageId),
                TempDetailDao.Properties.Activity.eq(activity),
                TempDetailDao.Properties.Diameter.eq(item)
        ).build().list();
        if (list.size() > 0) {
            list.get(0).radical = (Integer.parseInt(list.get(0).radical) + 1) + "";
            list.get(0).FQuantity = (Double.parseDouble(list.get(0).FQuantity) + CalculateUtil.getVoleum(length, item)) + "";
            tempDetailDao.update(list.get(0));

        } else {
            TempDetail tempDetail = new TempDetail();
            tempDetail.FIndex = getTimesecond();
            tempDetail.length = length.equals("")?"0":length;
            tempDetail.diameter = Integer.parseInt(item);
            tempDetail.FBatch = "";
            tempDetail.FOrderId = ordercode;
            tempDetail.FProductCode = product.FNumber;
            tempDetail.FProductId = product.FItemID;
            tempDetail.model = product.FModel;
            tempDetail.FProductName = product.FName;
            tempDetail.FUnitId = product.FUnitID;
            tempDetail.FUnit = unitName == null ? "" : unitName;
            tempDetail.FStorage = storageName == null ? "" : storageName;
            tempDetail.FStorageId = storageId == null ? "0" : storageId;
            tempDetail.FPosition = wavehouseName == null ? "" : wavehouseName;
            tempDetail.FPositionId = wavehouseID == null ? "0" : wavehouseID;
            tempDetail.activity = Config.CHOOSE_SECOND_FRAGMENT_LONG;
            tempDetail.FDiscount = "100";
            tempDetail.FQuantity = CalculateUtil.getVoleumLong(length, item) + "";
            tempDetail.radical = "1";
            tempDetail.num = num + "";
            tempDetail.unitrate = unitrate;
            tempDetail.FTaxUnitPrice = "0";
            tempDetailDao.insert(tempDetail);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //清空长度框
                etLength.setText("");
            }
        });

        MediaPlayer.getInstance(mContext).ok();
        VibratorUtil.Vibrate(mContext, 200);

        product=null;
        return "添加完成";
    }

    private void RefreshList() {
        container.clear();
        int genshu1 = 0;
        Double zongtiji = 0.0;
        List<TempDetail> tempDetails = tempDetailDao.queryBuilder().where(
                TempDetailDao.Properties.Activity.eq(activity)
        ).build().list();

        Log.e(TAG, "RefreshList:缓存数据： " + tempDetails.toString());
        container.addAll(tempDetails);
        billRvAdapter.notifyDataSetChanged();
        for (int i = 0; i < tempDetails.size(); i++) {
            genshu1 += Integer.parseInt(tempDetails.get(i).radical);
            zongtiji += Double.parseDouble(tempDetails.get(i).FQuantity);
        }
        genshu.setText("总根数:" + genshu1);
        tiji.setText("总体积:" + df.format(zongtiji));
    }

    private void Addorder() {

        List<TempDetail> tempDetails = tempDetailDao.loadAll();
//        List<TempMain> tempMains = tempMainDao.loadAll();
//        T_mainDao t_mainDao = daoSession.getT_mainDao();
        T_DetailDao t_detailDao = daoSession.getT_DetailDao();
//        t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
//                T_mainDao.Properties.Activity.eq(activity),
//                T_mainDao.Properties.OrderId.eq(ordercode))
//                .build().list());
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
//        t_main.activity = Config.CHOOSE_SECOND_FRAGMENT_LONG;
//        t_main.sourceOrderTypeId = tempMain.sourceOrderTypeId;
//        t_main.companyId = tempMain.companyId;
//
//        t_mainDao.insert(t_main);
//
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
            t_detail.activity = Config.CHOOSE_SECOND_FRAGMENT_LONG;
            t_detail.FDiscount = tempDetail.FDiscount;
            t_detail.FQuantity = tempDetail.FQuantity;
            t_detail.unitrate = tempDetail.unitrate;
            t_detail.FTaxUnitPrice = tempDetail.FTaxUnitPrice;
            t_detail.FIdentity = tempDetail.num;
            t_detail.clientName = clientName==null?"":clientName;
            t_detailDao.insert(t_detail);
        }

//        tempMainDao.deleteAll();
        tempDetailDao.deleteAll();
        num++;
        RefreshList();
        Toast.showText(mContext, "单据插入成功");
    }
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
                        * (CalculateUtil.getVoleumLong(items.length,items.diameter+""))) + "";
                tempDetailDao.update(items);
                billRvAdapter.goToPostion(billRvAdapter.getItems(position).diameter+"",items.model,storageId,items.length);
                RefreshList();

                MediaPlayer.getInstance(mContext).ok();
                rvBill.scrollToPosition(position);
                break;
            case R.id.btn_enduce:
                items = billRvAdapter.getItems(position);
                if (Integer.parseInt(items.radical)!=0){
                    items.radical = (Integer.parseInt(items.radical) - 1) + "";
                    items.FQuantity = (Double.parseDouble(items.radical)
                            * (CalculateUtil.getVoleumLong(items.length,items.diameter+""))) + "";
                    tempDetailDao.update(items);
                    billRvAdapter.goToPostion(billRvAdapter.getItems(position).diameter+"",items.model,storageId,items.length);
                    RefreshList();
                    MediaPlayer.getInstance(mContext).ok();
                    rvBill.scrollToPosition(position);
                }

                break;
        }
    }

    public static void start(Context context, int activity, String orderid, String clientName, String baoshu) {
        Intent starter = new Intent(context, LongTreeOutAddMoreActivity.class);
        starter.putExtra("activity", activity);
        starter.putExtra("orderid", orderid);
        starter.putExtra("clientName", clientName);
        starter.putExtra("baoshu", baoshu);
        context.startActivity(starter);
    }



    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
