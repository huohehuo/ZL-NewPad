package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.ProductselectAdapter1;
import com.fangzuo.assist.Adapter.StorageCheckAdapter;
import com.fangzuo.assist.Adapter.StorageSpAdapter;
import com.fangzuo.assist.Adapter.UnitSpAdapter;
import com.fangzuo.assist.Adapter.WaveHouseSpAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.InStorageNumListBean;
import com.fangzuo.assist.Beans.InStoreNumBean;
import com.fangzuo.assist.Dao.Product;
import com.fangzuo.assist.Dao.Storage;
import com.fangzuo.assist.Dao.Unit;
import com.fangzuo.assist.Dao.WaveHouse;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.CommonMethod;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.EventBusInfoCode;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.greendao.gen.DaoSession;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.hawk.Hawk;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StorageCheckActivity extends BaseActivity {


    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.check)
    Button check;
    @BindView(R.id.tv_productName)
    TextView tvProductName;
    @BindView(R.id.tv_productCode)
    TextView tvProductCode;
    @BindView(R.id.sp_which_storage)
    Spinner spWhichStorage;
    @BindView(R.id.sp_wavehouse)
    Spinner spWavehouse;
    @BindView(R.id.tv_dateProduce)
    TextView tvDateProduce;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    @BindView(R.id.et_batchNo)
    TextView etBatchNo;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.ry_storage)
    EasyRecyclerView ryStorage;
    @BindView(R.id.sp_unit)
    Spinner spUnit;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.tv_storage_num)
    TextView tvStorageNum;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.btn_check_by)
    Button btnCheckBy;
    private String fkfDate;
    private String batchNo;
    private StorageSpAdapter storageSpAdapter;
    private Storage storage;
    private WaveHouseSpAdapter waveHouseAdapter;
    private String storageId;
    private String wavehouseID;
    private double qty;
    private Product product;
    private String default_unitID;
    private ProductselectAdapter1 productselectAdapter1;
    private boolean isPeriod;
    private DaoSession daoSession;
    private UnitSpAdapter unitAdapter;
    private String unitId;
    private double unitrate;
    private StorageCheckAdapter adapter;
    private DecimalFormat df;
    @Override
    public void initView() {
        setContentView(R.layout.activity_storage_check);
        mContext = this;
        ButterKnife.bind(this);
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();

        df = new DecimalFormat("######0.00000");
        ryStorage.setAdapter(adapter = new StorageCheckAdapter(this));
        ryStorage.setLayoutManager(new LinearLayoutManager(this));

        setfocus(btnCheckBy);
    }

    @Override
    public void initData() {
        storageSpAdapter = CommonMethod.getMethod(mContext).getStorageSpinner(spWhichStorage);

    }

    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.PRODUCTRETURN:
                product = (Product) event.postEvent;
                getProductOL(product);
                break;
        }
    }

    private boolean isFirst=true;
    @Override
    public void initListener() {
        ryStorage.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.removeAll();
                ryStorage.setRefreshing(true);
                getInstorageNumList(false);
            }
        });
        spWhichStorage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                storage = (Storage) storageSpAdapter.getItem(i);
                waveHouseAdapter = CommonMethod.getMethod(mContext).getWaveHouseAdapter(storage, spWavehouse);
                storageId = storage.FItemID;
                Log.e("storageId", storageId);
//                etCode.setText("");
//                if (!isFirst){
//                    getInstorageNumList(false);
//                }
//                    isFirst=false;
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
//                if (!isFirst){
//                    getInstorageNumList(false);
//                }
//                    isFirst=false;
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
                    unitrate = Double.parseDouble(unit.FCoefficient);
                    Log.e("unitId", unitId + "");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void OnReceive(String code) {
        getDate(code);
//        try {
//            StringBuilder logString = new StringBuilder();
//            String[] split = code.split("\\^");
//            Log.e("Split", "^");
//            Log.e("Split", split.length + "");
//            for (int i = 0; i < split.length; i++) {
//                logString.append(split[i] + "|||");
//            }
//            Log.e("Split", logString.toString());
//            if (split.length > 4) {
//                String productCode = split[0];
//                fkfDate = split[2];
//                batchNo = split[1];
//                tvDateProduce.setText(fkfDate);
//                etBatchNo.setText(batchNo);
//                etCode.setText(productCode);
//                getDate(productCode);
//            } else {
//                getDate(code);
//            }
//
//        } catch (NullPointerException e) {
//            Toast.showText(mContext, "条码数据缺失");
//            MediaPlayer.getInstance(mContext).error();
//        }
    }

    private void getDate(String productCode) {
        Asynchttp.post(mContext, getBaseUrl() + WebApi.SEARCHPRODUCTS, productCode, new Asynchttp.Response() {
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
                    lv.setAdapter(productselectAdapter1);
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
    }

    private void getProductOL(DownloadReturnBean dBean, int j) {
        product = dBean.products.get(j);
        etCode.setText(product.FNumber);
        tvProductName.setText(product.FName);
        tvProductCode.setText(product.FNumber);

        isPeriod = (product.FISKFPeriod) != null && (product.FISKFPeriod).equals("1");
        if (isPeriod) {
            tvPeriod.setText(product.FKFPeriod + "");
        }
        unitAdapter = CommonMethod.getMethod(mContext).getUnitAdapter(product.FUnitGroupID, spUnit);

        getInstorageNumList(false);

    }

    private void getProductOL(Product product) {
        Log.e("asdf", "asdf" + product.toString());
        etCode.setText(product.FNumber);
        tvProductName.setText(product.FName);
        tvProductCode.setText(product.FNumber);

        isPeriod = (product.FISKFPeriod) != null && (product.FISKFPeriod).equals("1");
        if (isPeriod) {
            tvPeriod.setText(product.FKFPeriod + "");
        }
        unitAdapter = CommonMethod.getMethod(mContext).getUnitAdapter(product.FUnitGroupID, spUnit);

        getInstorageNumList(false);

    }

    private String AllG="";
    private String AllM="";
    int allG;
    double allM;
    private void getInstorageNumList(boolean type) {
        Lg.e("getInstorageNumList", "进入");
        String json;
        String productId="";
        if (type){
            json=new Gson().toJson(new InStoreNumBean("","",""));
        }else{
            if (product == null) {
                if ("".equals(etCode.getText().toString().trim())){
                    productId="";
                }else{
                    productId=etCode.getText().toString().trim();
                }
            }else{
                productId = product.FItemID;
            }

            if (null ==storageId ||"".equals(storageId)){
                storageId="";
            }
            if (null ==wavehouseID ||"".equals(wavehouseID)){
                wavehouseID="";
            }

            InStoreNumBean iBean = new InStoreNumBean();
            iBean.FItemID = productId;
            iBean.FStockID = storageId;
            iBean.FStockPlaceID = wavehouseID;
            json = new Gson().toJson(iBean);
        }


        Log.e("获取库存明细-json：", json);
        Asynchttp.post(mContext, getBaseUrl() + WebApi.GetInStorageNumList, json, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                Lg.e("getInstorageNumList", "获取库存明细数据：" + cBean.returnJson);
                InStorageNumListBean iBean = new Gson().fromJson(cBean.returnJson, InStorageNumListBean.class);
                adapter.clear();
                adapter.addAll(iBean.list);
                tvStorageNum.setText("共有"+iBean.list.size()+"条商品");
                allG=0;
                allM=0;
                for (int i=0;i<iBean.list.size();i++){
                    allG=allG+(int)Math.ceil(Double.parseDouble(iBean.list.get(i).FSecQty));
                    allM=allM+Double.parseDouble(iBean.list.get(i).FQty);
                }
                tvAll.setText("汇总方数："+df.format(allM)+"  汇总根数："+allG);
                ryStorage.setRefreshing(false);
            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Lg.e("getInstorageNumList：获取库存明细数据失败：");
                Toast.showText(mContext, Msg);
                tvStorageNum.setText("无商品信息");
                tvAll.setText("无数据");
                adapter.clear();
                ryStorage.setRefreshing(false);
            }
        });
    }

    private void checkStorage(){

        InStoreNumBean iBean = new InStoreNumBean();
        iBean.FStockPlaceID = wavehouseID;
        iBean.FStockID = storageId;
        iBean.FItemID = product.FItemID;
        String json = new Gson().toJson(iBean);
        Log.e("获取库存-json：", json);
        Asynchttp.post(mContext, getBaseUrl() + WebApi.GETINSTORENUM, json, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                Lg.e("库存："+cBean.returnJson);
                qty = Double.parseDouble(cBean.returnJson);
            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Lg.e("获取库存失败。。。");
                qty = 0.0;
            }
        });
    }

//    private void getInstorageNum() {
////        batchNo = etBatchNo.getText().toString();
////        if (batchNo == null || batchNo.equals("")) {
////            batchNo = "0";
////        } else {
////            batchNo = "";
////        }
////        if (wavehouseID == null) {
////            wavehouseID = "0";
////        }
////        if (product ==null ){
////            return;
////        }
////        InStoreNumBean iBean = new InStoreNumBean();
////        iBean.FStockPlaceID = wavehouseID;
////        iBean.FBatchNo = batchNo;
////        iBean.FStockID = storageId;
////        iBean.FItemID = product.FItemID;
////        iBean.FKFDate = fkfDate;
////        String json = new Gson().toJson(iBean);
////        Log.e("获取库存-json：", json);
////        Asynchttp.post(mContext, getBaseUrl() + WebApi.GETINSTORENUM, json, new Asynchttp.Response() {
////            @Override
////            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
////                Lg.e("库存："+cBean.returnJson);
////                qty = Double.parseDouble(cBean.returnJson);
////            }
////
////            @Override
////            public void onFailed(String Msg, AsyncHttpClient client) {
////                Lg.e("获取库存失败。。。");
////                qty = 0.0;
////            }
////        });
////
////
////        tvTotalNum.setText(qty / unitrate + "");
//
//    }

    @OnClick(R.id.check)
    public void onViewClicked() {
        Log.e("search", "onclick");
        Bundle b1 = new Bundle();
        b1.putString("search", etCode.getText().toString());
        b1.putInt("where", Info.SEARCHPRODUCT);
        startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULT, b1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("code", requestCode + "" + "    " + resultCode);
        if (requestCode == Info.SEARCHFORRESULT) {
            if (resultCode == 9998) {
//                Bundle b = data.getExtras();
//                product = (Product) b.getSerializable("001");
                product = Hawk.get(Config.For_Back_Data_InStorageNum, null);
                Lg.e("返回Product：" + product.toString());
                etCode.setText(product.FNumber);
//                getProductOL(product);
//                getInstorageNumList(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_clear, R.id.btn_check_by})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                etCode.setText("");
                product=null;
                break;
            case R.id.btn_check_by:
                getInstorageNumList(false);
                break;
        }
    }

//    @OnClick(R.id.btn_clear)
//    public void onClick() {
//        etCode.setText("");
//    }
}
