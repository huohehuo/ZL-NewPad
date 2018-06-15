package com.fangzuo.assist.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.CompanySpAdapter;
import com.fangzuo.assist.Adapter.SearchAdapter;
import com.fangzuo.assist.Adapter.SearchClientAdapter;
import com.fangzuo.assist.Adapter.SearchCompanyAdapter;
import com.fangzuo.assist.Adapter.SearchDepartmentAdapter;
import com.fangzuo.assist.Adapter.SearchSupplierAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Dao.Client;
import com.fangzuo.assist.Dao.Company;
import com.fangzuo.assist.Dao.GetGoodsDepartment;
import com.fangzuo.assist.Dao.Product;
import com.fangzuo.assist.Dao.Suppliers;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.BasicShareUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.EventBusInfoCode;
import com.fangzuo.assist.Utils.EventBusUtil;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Info;
import com.fangzuo.assist.Utils.JsonCreater;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.fangzuo.greendao.gen.ClientDao;
import com.fangzuo.greendao.gen.CompanyDao;
import com.fangzuo.greendao.gen.GetGoodsDepartmentDao;
import com.fangzuo.greendao.gen.ProductDao;
import com.fangzuo.greendao.gen.SuppliersDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductSearchActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.lv_result)
    ListView lvResult;
    @BindView(R.id.cancle)
    View cancle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.name)
    TextView name;
    private String searchString;
    private ProductSearchActivity mContext;
    private SearchAdapter ada;
    private List<Product> items;
    private List<Product> items1;
    private List<Product> itemAll;
    private int where;
    private List<Suppliers> itemAllSupplier;
    private List<Client> itemAllClient;
    private List<Company> itemAllCompany;
    private List<GetGoodsDepartment> alldepart;

    @Override
    public void initView() {
        setContentView(R.layout.activity_product_search);
        ButterKnife.bind(this);
        mContext = this;
        Intent in = getIntent();
        Bundle b = in.getExtras();
        searchString = b.getString("search", "");
        where = b.getInt("where");
        Log.e("searchString", searchString);
        if (where == Info.SEARCHPRODUCT) title.setText("查询结果(物料)");
        if (where == Info.SEARCHSUPPLIER) title.setText("查询结果(供应商)");
        if (where == Info.SEARCHCLIENT) title.setText("查询结果(客户)");
        if (where == Info.SEARCHGH) title.setText("查询结果(交货单位)");
        if (where == Info.Search_Product) title.setText("查询结果(物料)");
        if (where == Config.Search_Company) title.setText("查询结果(公司)");
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(ClassEvent event) {
        super.receiveEvent(event);
    }

    @Override
    public void initData() {
        if (where == Info.SEARCHPRODUCT) {
            model.setText("名称");
            name.setText("型号");
            if (BasicShareUtil.getInstance(mContext).getIsOL()) {
                Asynchttp.post(mContext, getBaseUrl() + WebApi.PRODUCTSEARCHLIKE, searchString, new Asynchttp.Response() {
                    @Override
                    public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                        DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                        Lg.e("search",cBean.returnJson);
                        items = dBean.products;
                        itemAll = new ArrayList<>();
                        itemAll.addAll(items);
                        if (itemAll.size() > 0) {
                            ada = new SearchAdapter(mContext, itemAll);
                            lvResult.setAdapter(ada);
                            ada.notifyDataSetChanged();
                        } else {
                            Toast.showText(mContext, "无数据");
                            setResult(9998, null);
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailed(String Msg, AsyncHttpClient client) {
                        Toast.showText(mContext, Msg);
                    }
                });
            } else {
                model.setText("名称");
                name.setText("型号");
                ProductDao productDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getProductDao();
                items = productDao.queryBuilder().whereOr(ProductDao.Properties.FNumber.like("%" + searchString + "%"),
                        ProductDao.Properties.FBarcode.like("%" + searchString + "%"),
                        ProductDao.Properties.FName.like("%" + searchString + "%")).
                        orderAsc(ProductDao.Properties.FNumber).limit(50).orderAsc(ProductDao.Properties.FNumber).build().list();
                itemAll = new ArrayList<>();
                itemAll.addAll(items);
                if (itemAll.size() > 0) {
                    ada = new SearchAdapter(mContext, itemAll);
                    lvResult.setAdapter(ada);
                    ada.notifyDataSetChanged();
                } else {
                    Toast.showText(mContext, "无数据");
                    setResult(-9998, null);
                    onBackPressed();
                }
            }


        } else if (where == Info.SEARCHSUPPLIER) {
            SuppliersDao suppliersDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getSuppliersDao();
            List<Suppliers> list = suppliersDao.queryBuilder().whereOr(SuppliersDao.Properties.FName.like("%" + searchString + "%"), SuppliersDao.Properties.FItemID.like("%" + searchString + "%")).orderAsc(SuppliersDao.Properties.FItemID).limit(50).build().list();
            itemAllSupplier = new ArrayList<>();
            itemAllSupplier.addAll(list);
            if (itemAllSupplier.size() > 0) {
                SearchSupplierAdapter ada1 = new SearchSupplierAdapter(mContext, itemAllSupplier);
                lvResult.setAdapter(ada1);
                ada1.notifyDataSetChanged();
            } else {
                Toast.showText(mContext, "未查询到数据");
                setResult(-9998, null);
                onBackPressed();
            }
        } else if (where == Info.SEARCHCLIENT) {
                ClientDao clientDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getClientDao();
                List<Client> clients = clientDao.queryBuilder().whereOr(
                        ClientDao.Properties.FName.like("%" + searchString + "%"),
                        ClientDao.Properties.FItemID.like("%" + searchString + "%")
                ).orderAsc(ClientDao.Properties.FItemID).build().list();
                itemAllClient = new ArrayList<>();
                itemAllClient.addAll(clients);
                if (itemAllClient.size() > 0) {
                    SearchClientAdapter ada2 = new SearchClientAdapter(mContext, itemAllClient);
                    lvResult.setAdapter(ada2);
                    ada2.notifyDataSetChanged();
                } else {
                    Toast.showText(mContext, "未查询到数据");
                    setResult(-9998, null);
                    onBackPressed();
                }
        }  else if (where == Config.Search_Company) {
            CompanyDao clientDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getCompanyDao();
//            final ArrayList<Company> container = new ArrayList<>();
//            final CompanySpAdapter companySpAdapter = new CompanySpAdapter(context, container);
//            sp.setAdapter(companySpAdapter);
//            if(BasicShareUtil.getInstance(mContext).getIsOL()){
//                ArrayList<Integer> choose = new ArrayList<>();
//                choose.add(20);
//                String json = JsonCreater.DownLoadData(share.getDatabaseIp(),
//                        share.getDatabasePort(), share.getDataBaseUser(), share.getDataBasePass(),
//                        share.getDataBase(), share.getVersion(),choose);
//                Asynchttp.post(context,share.getBaseURL()+WebApi.DOWNLOADDATA, json, new Asynchttp.Response() {
//                    @Override
//                    public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
//                        DownloadReturnBean dBean = JsonCreater.gson.fromJson(cBean.returnJson, DownloadReturnBean.class);
//                        CompanyDao employeeDao = daoSession.getCompanyDao();
//                        container.addAll(dBean.companies);
//                        companySpAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onFailed(String Msg, AsyncHttpClient client) {
//                        Toast.showText(context, Msg);
//                    }
//                });
//            }else{
//                CompanyDao employeeDao = daoSession.getCompanyDao();
                List<Company> clients = clientDao.queryBuilder().whereOr(
                        CompanyDao.Properties.FName.like("%" + searchString + "%"),
                        CompanyDao.Properties.FItemID.like("%" + searchString + "%")
                ).orderAsc(CompanyDao.Properties.FItemID).build().list();
            itemAllCompany = new ArrayList<>();
            itemAllCompany.addAll(clients);
            if (itemAllCompany.size() > 0) {
                SearchCompanyAdapter ada2 = new SearchCompanyAdapter(mContext, itemAllCompany);
                lvResult.setAdapter(ada2);
                ada2.notifyDataSetChanged();
            } else {
                Toast.showText(mContext, "未查询到数据");
                setResult(-9998, null);
                onBackPressed();
            }

//            }
        }else if (where == Info.SEARCHGH) {
            GetGoodsDepartmentDao getGoodsDepartmentDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getGetGoodsDepartmentDao();
            List<GetGoodsDepartment> list = getGoodsDepartmentDao.queryBuilder().whereOr(GetGoodsDepartmentDao.Properties.FNumber.like("%" + searchString + "%"), GetGoodsDepartmentDao.Properties.FName.like("%" + searchString + "%")).build().list();
            alldepart = new ArrayList<>();
            alldepart.addAll(list);
            if (alldepart.size() > 0) {
                SearchDepartmentAdapter ada3 = new SearchDepartmentAdapter(mContext, alldepart);
                lvResult.setAdapter(ada3);
                ada3.notifyDataSetChanged();
            } else {
                Toast.showText(mContext, "未查询到数据");
                setResult(-9998, null);
                onBackPressed();
            }
        }else if (where == Info.Search_Product) {
            model.setText("名称");
            name.setText("型号");
            if (BasicShareUtil.getInstance(mContext).getIsOL()) {
                Asynchttp.post(mContext, getBaseUrl() + WebApi.PRODUCTSEARCHLIKE, searchString, new Asynchttp.Response() {
                    @Override
                    public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                        DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                        Lg.e("search",cBean.returnJson);
                        items = dBean.products;
                        itemAll = new ArrayList<>();
                        itemAll.addAll(items);
                        if (itemAll.size() > 0) {
                            ada = new SearchAdapter(mContext, itemAll);
                            lvResult.setAdapter(ada);
                            ada.notifyDataSetChanged();
                        } else {
                            Toast.showText(mContext, "无数据");
                            setResult(Info.Search_Product, null);
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailed(String Msg, AsyncHttpClient client) {
                        Toast.showText(mContext, Msg);
                    }
                });
            } else {
                model.setText("名称");
                name.setText("型号");
                ProductDao productDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getProductDao();
                items = productDao.queryBuilder().whereOr(ProductDao.Properties.FNumber.like("%" + searchString + "%"),
                        ProductDao.Properties.FBarcode.like("%" + searchString + "%"),
                        ProductDao.Properties.FName.like("%" + searchString + "%")).
                        orderAsc(ProductDao.Properties.FNumber).limit(50).orderAsc(ProductDao.Properties.FNumber).build().list();
                itemAll = new ArrayList<>();
                itemAll.addAll(items);
                if (itemAll.size() > 0) {
                    ada = new SearchAdapter(mContext, itemAll);
                    lvResult.setAdapter(ada);
                    ada.notifyDataSetChanged();
                } else {
                    Toast.showText(mContext, "无数据");
                    setResult(-9998, null);
                    onBackPressed();
                }
            }


        }


    }

    @Override
    public void initListener() {
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent();
                Bundle b = new Bundle();
                if (where == Info.SEARCHPRODUCT) {
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.PRODUCTRETURN,itemAll.get(i)));
                    Hawk.put(Config.For_Back_Data_InStorageNum,itemAll.get(i));
                    setResult(Info.SEARCHFORRESULT, mIntent);
                    onBackPressed();
                } else if (where == Info.SEARCHSUPPLIER) {
                    b.putString("001", itemAllSupplier.get(i).FItemID);
                    b.putString("002", itemAllSupplier.get(i).FName);
                    mIntent.putExtras(b);
                    setResult(Info.SEARCHFORRESULTPRODUCT, mIntent);
                    onBackPressed();
                } else if (where == Info.SEARCHCLIENT) {
                    b.putString("001", itemAllClient.get(i).FItemID);
                    b.putString("002", itemAllClient.get(i).FName);
                    b.putString("003", itemAllClient.get(i).FNumber);
                    mIntent.putExtras(b);
                    setResult(Info.SEARCHFORRESULTCLIRNT, mIntent);
                    onBackPressed();
                } else if (where == Info.SEARCHGH) {
                    b.putString("001", alldepart.get(i).FItemID);
                    b.putString("002", alldepart.get(i).FName);
                    mIntent.putExtras(b);
                    setResult(Info.SEARCHFORRESULTJH, mIntent);
                    onBackPressed();
                } else if (where == Info.Search_Product) {
                    b.putString("001", itemAll.get(i).FItemID);
                    b.putString("002", itemAll.get(i).FModel);
                    mIntent.putExtras(b);
                    setResult(Info.Search_Product, mIntent);
                    onBackPressed();
                } else if (where == Config.Search_Company) {
                    b.putString("001", itemAllCompany.get(i).FItemID);
                    b.putString("002", itemAllCompany.get(i).FName);
                    b.putString("003", itemAllCompany.get(i).FNumber);
                    mIntent.putExtras(b);
                    setResult(Config.Search_Company, mIntent);
                    onBackPressed();
                }

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void OnReceive(String code) {

    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
        this.overridePendingTransition(R.anim.bottom_end, 0);
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(0, R.anim.bottom_end);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


}
