package com.fangzuo.assist.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.ABase.BaseFragment;
import com.fangzuo.assist.Activity.BackGoodsActivity;
import com.fangzuo.assist.Activity.LongTreeOutAddMoreActivity;
import com.fangzuo.assist.Activity.LookActivity;
import com.fangzuo.assist.Activity.ShortTreeOutAddMoreActivity;
import com.fangzuo.assist.Adapter.ThirdRvAdapter;
import com.fangzuo.assist.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.SecondCheck;
import com.fangzuo.assist.Dao.Client;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.Dao.T_main;
import com.fangzuo.assist.Dao.TempDetail;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.CalculateUtil;
import com.fangzuo.assist.Utils.Config;
import com.fangzuo.assist.Utils.EventBusUtil;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.assist.Utils.Lg;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.greendao.gen.ClientDao;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.fangzuo.greendao.gen.TempDetailDao;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends BaseFragment implements ThirdRvAdapter.RvInnerClickListener {


    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    @BindView(R.id.btn_deleteAll)
    Button btnDeleteAll;
    @BindView(R.id.et_baoshu)
    EditText etBaoshu;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tv_v)
    TextView tvV;
    @BindView(R.id.tv_r)
    TextView tvR;
    @BindView(R.id.tv_clientName)
    TextView tv_clientName;
    @BindView(R.id.et_diameter)
    EditText etDiameter;
    @BindView(R.id.et_lengh)
    EditText etLengh;
    @BindView(R.id.btn_add_more)
    Button btnAddMore;
    @BindView(R.id.btn_backgoods)
    Button btnBackgoods;
    private FragmentActivity mContext;
    private String orderID;
    private int tag;
    private DaoSession daoSession;
    private T_mainDao t_mainDao;
    private T_DetailDao t_detailDao;
    private TempDetailDao tempDetailDao;
    private LinearLayoutManager mLinearLayoutManager;
    private ThirdRvAdapter thirdRvAdapter;
    private List<T_Detail> t_details;
    private LookActivity lookActivity;
    private ArrayList<T_Detail> container;
    private DecimalFormat df;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        orderID = ((LookActivity) activity).getOrderID();
        tag = ((LookActivity) activity).getTag();
        lookActivity = ((LookActivity) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third2, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBusUtil.register(this);
        mContext = getActivity();
//        tv_clientName.setText(lookActivity.getClientName());
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

        mContext = getActivity();
        daoSession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        t_mainDao = daoSession.getT_mainDao();
        t_detailDao = daoSession.getT_DetailDao();
        tempDetailDao = daoSession.getTempDetailDao();

        //非红单才有追加
        if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT || tag == Config.CHOOSE_SECOND_FRAGMENT_LONG){
            btnAddMore.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        if (cName != null) {
            cName.setText(lookActivity.getClientName());
            tv_clientName.setText("客户：" + lookActivity.getClientName());
        }

    }

    //获取最大的报数，用于追加时使用
    private String baoshu="";
    private void getMaxBaoshu(){
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                "MAX(FIDENTITY) as MaxBaoShu " +
                "FROM T__DETAIL " +
                "WHERE ACTIVITY=?" +
                " AND FORDER_ID = ? "
                , new String[]{tag + "",orderID});

        while (cursor.moveToNext()) {
            FirstCheck f = new FirstCheck();
            f.orderID = cursor.getString(cursor.getColumnIndex("MaxBaoShu"));
            Lg.e("最大报数："+cursor.getString(cursor.getColumnIndex("MaxBaoShu")));
            baoshu = cursor.getString(cursor.getColumnIndex("MaxBaoShu"));
        }
    }

    @Override
    protected void initData() {
        List<T_main> t_mains = t_mainDao.queryBuilder().where(
                T_mainDao.Properties.Activity.eq(tag),
                T_mainDao.Properties.OrderId.eq(orderID)
        ).build().list();
        if (t_mains.size() > 0) {
            tv_clientName.setText("客户：" + t_mains.get(0).supplier);
        } else {
            tv_clientName.setText("客户：");
        }
        container = new ArrayList<>();
        thirdRvAdapter = new ThirdRvAdapter(mContext, container, true);
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        rv.setAdapter(thirdRvAdapter);
        thirdRvAdapter.setInnerClickListener(this);
        searchList();
        getMaxBaoshu();
    }

    //查询
    public void searchList() {
        int r = 0;
        double v = 0d;
        container.clear();
        String a = etDiameter.getText().toString();
        String b = etLengh.getText().toString();
        String c = etBaoshu.getText().toString();
        T_DetailDao t_detailDao1 = daoSession.getT_DetailDao();
        if (!a.equals("") && !b.equals("") && !c.equals("")) {
            t_details = t_detailDao
                    .queryBuilder()
                    .where(T_DetailDao.Properties.Activity.eq(tag),
                            T_DetailDao.Properties.Diameter.eq(a),
                            T_DetailDao.Properties.Length.eq(b),
                            T_DetailDao.Properties.FOrderId.eq(orderID),
                            T_DetailDao.Properties.FIdentity.eq(c))
                    .orderAsc(T_DetailDao.Properties.Diameter)
                    .build()
                    .list();
        } else if (!a.equals("") && !b.equals("")) {
            t_details = t_detailDao
                    .queryBuilder()
                    .where(T_DetailDao.Properties.Activity.eq(tag),
                            T_DetailDao.Properties.Diameter.eq(a),
                            T_DetailDao.Properties.Length.eq(b),
                            T_DetailDao.Properties.FOrderId.eq(orderID))
                    .orderAsc(T_DetailDao.Properties.Diameter)
                    .build()
                    .list();
        } else if (!a.equals("") && !c.equals("")) {
            t_details = t_detailDao
                    .queryBuilder()
                    .where(T_DetailDao.Properties.Activity.eq(tag),
                            T_DetailDao.Properties.Diameter.eq(a),
                            T_DetailDao.Properties.FOrderId.eq(orderID),
                            T_DetailDao.Properties.FIdentity.eq(c))
                    .orderAsc(T_DetailDao.Properties.Diameter)
                    .build()
                    .list();
        } else if (!b.equals("") && !c.equals("")) {
            t_details = t_detailDao
                    .queryBuilder()
                    .where(T_DetailDao.Properties.Activity.eq(tag),
                            T_DetailDao.Properties.Length.eq(b),
                            T_DetailDao.Properties.FOrderId.eq(orderID),
                            T_DetailDao.Properties.FIdentity.eq(c))
                    .orderAsc(T_DetailDao.Properties.Diameter)
                    .build()
                    .list();
        } else if (!a.equals("")) {
            t_details = t_detailDao
                    .queryBuilder()
                    .where(T_DetailDao.Properties.Activity.eq(tag),
                            T_DetailDao.Properties.Diameter.eq(a),
                            T_DetailDao.Properties.FOrderId.eq(orderID))
                    .orderAsc(T_DetailDao.Properties.Diameter)
                    .build()
                    .list();
        } else if (!b.equals("")) {
            t_details = t_detailDao
                    .queryBuilder()
                    .where(T_DetailDao.Properties.Activity.eq(tag),
                            T_DetailDao.Properties.Length.eq(b),
                            T_DetailDao.Properties.FOrderId.eq(orderID))
                    .orderAsc(T_DetailDao.Properties.Diameter)
                    .build()
                    .list();
        } else if (!c.equals("")) {
            t_details = t_detailDao
                    .queryBuilder()
                    .where(T_DetailDao.Properties.Activity.eq(tag),
                            T_DetailDao.Properties.FOrderId.eq(orderID),
                            T_DetailDao.Properties.FIdentity.eq(c))
                    .orderAsc(T_DetailDao.Properties.Diameter)
                    .build()
                    .list();
        } else {
            t_details = t_detailDao
                    .queryBuilder()
                    .where(T_DetailDao.Properties.Activity.eq(tag),
                            T_DetailDao.Properties.FOrderId.eq(orderID))
                    .orderAsc(T_DetailDao.Properties.Diameter)
                    .build()
                    .list();
            Lg.e("all：" + t_details.toString());
        }

        for (int i = 0; i < t_details.size(); i++) {
            r += Integer.parseInt(t_details.get(i).radical);
            v += Double.parseDouble(t_details.get(i).FQuantity);
        }
        if (t_details.size() != 0 && "红字".equals(t_details.get(0).FRedBlue)) {
            tvR.setText("总根数:" + "-" + r + "");
            tvR.setTextColor(Color.RED);
        } else {
            tvR.setText("总根数:" + r + "");
        }
        tvV.setText("总体积:" + df.format(v));
        Lg.e("数据3：" + t_details.toString());
        container.addAll(t_details);
        thirdRvAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchList();
            }
        });

        thirdRvAdapter.setOnItemClickListener(new ThirdRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Lg.e("点击。。。。。");
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                final T_Detail t_detail = thirdRvAdapter.getItems(position);
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                Log.e(TAG, "长按获取：" + t_detail.toString());
                ab.setTitle("全部删除警告");
                StringBuilder msg = new StringBuilder();
                msg.append("物料名称：" + t_detail.FProductName + "\n");
                msg.append("规格型号：" + t_detail.model + "\n");
                msg.append("物料径直：" + t_detail.diameter + "\n");
                msg.append("物料根数：" + t_detail.radical + "\n");
                msg.append("物料体积：" + t_detail.FQuantity + "\n");
                msg.append("物料长度：" + t_detail.length + "\n");
                ab.setMessage(msg);
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        t_detailDao.delete(t_detail);
                        thirdRvAdapter.deleteItem(position);
                    }
                }).setNegativeButton("取消", null).create().show();
            }
        });
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
        BlankFragment blankFragment = new BlankFragment();
        lookActivity.setf(blankFragment);
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in, R.anim.activity_slide_left_out).remove(this).commit();
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_slide_right_in, R.anim.activity_slide_left_out).add(R.id.fragmenthost, blankFragment).commit();
    }


    private String VNo;
    private String VNanme;
    private String clientName;
    private String clientId = "";
    View changeClientView;
    EditText cName;

    @OnClick({R.id.btn_deleteAll, R.id.btn_bus_change, R.id.btn_client_change, R.id.btn_backgoods, R.id.btn_add_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_deleteAll:
                final List<T_Detail> t_detailList = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.Activity.eq(tag),
                        T_DetailDao.Properties.FOrderId.eq(orderID)
                ).build().list();
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("全部删除警告").setMessage(
                        "确认删除" + t_detailList.size() + "条数据?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        t_detailDao.deleteInTx(t_detailList);

                        t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
                                T_mainDao.Properties.Activity.eq(tag),
                                T_mainDao.Properties.OrderId.eq(orderID)
                        ).build().list());

                        if (t_detailList != null && t_detailList.size() > 0) {
                            t_detailDao.deleteInTx(t_detailList);
                            initData();
                        }
                    }
                }).setNegativeButton("取消", null).create().show();
                break;
            case R.id.btn_bus_change:
                final List<T_main> t_mains = t_mainDao.queryBuilder().where(
                        T_mainDao.Properties.Activity.eq(tag),
                        T_mainDao.Properties.OrderId.eq(orderID)
                ).build().list();
                if (t_mains.size() > 0) {
                    VNo = t_mains.get(0).VanNo;
                    VNanme = t_mains.get(0).VanDriver;
                }
                AlertDialog.Builder change = new AlertDialog.Builder(mContext);
                change.setTitle("修改数据");
                View changeview = LayoutInflater.from(mContext).inflate(R.layout.item_changebus, null);
                final EditText name = changeview.findViewById(R.id.et_bus_name);
                final EditText vanNo = changeview.findViewById(R.id.et_VanNo);
                name.setText(VNanme == null ? "" : VNanme);
                vanNo.setText(VNo == null ? "" : VNo);
                change.setView(changeview);
                change.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT) {
                            share.setSVanNo(vanNo.getText().toString());
                            share.setSVanDriver(name.getText().toString());
                            Hawk.put(Config.has_Change_Bus_Short, true);
                        }
                        if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT_RED) {
                            Hawk.put(Config.Short_red_VanNo_s, vanNo.getText().toString());
                            Hawk.put(Config.Short_red_Driver_s, name.getText().toString());
                            Hawk.put(Config.has_Change_Bus_Short_Red, true);
                        }
                        if (tag == Config.CHOOSE_SECOND_FRAGMENT_LONG) {
                            share.setLVanNo(vanNo.getText().toString());
                            share.setLVanDriver(name.getText().toString());
                            Hawk.put(Config.has_change_bus_Long, true);
                        }
                        if (tag == Config.CHOOSE_SECOND_FRAGMENT_LONG_RED) {
                            Hawk.put(Config.Long_red_VanNo_s, vanNo.getText().toString());
                            Hawk.put(Config.Long_red_Driver_s, name.getText().toString());
                            Hawk.put(Config.has_Change_Bus_Long_Red, true);
                        }
                        for (int i = 0; i < t_mains.size(); i++) {
                            t_mains.get(i).VanNo = vanNo.getText().toString();
                            t_mains.get(i).VanDriver = name.getText().toString();
                            t_mainDao.update(t_mains.get(i));
                        }
                    }
                });
                change.setNegativeButton("取消", null);
                change.create().show();
                break;
            case R.id.btn_client_change:
                final List<T_main> mains = t_mainDao.queryBuilder().where(
                        T_mainDao.Properties.Activity.eq(tag),
                        T_mainDao.Properties.OrderId.eq(orderID)
                ).build().list();
                if (mains.size() > 0) {
                    clientName = mains.get(0).supplier;
                }
                AlertDialog.Builder changeClient = new AlertDialog.Builder(mContext);
                changeClient.setTitle("修改数据");
                changeClientView = LayoutInflater.from(mContext).inflate(R.layout.item_change_client, null);
                cName = changeClientView.findViewById(R.id.et_client_name);
                final RelativeLayout search = changeClientView.findViewById(R.id.search_supplier);
                cName.setText(clientName == null ? "" : clientName);
                changeClient.setView(changeClientView);
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lookActivity.startActivityTo(cName);
                    }
                });
                changeClient.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        if (!"".equals(cName.getText().toString().trim())) {
                            ClientDao clientDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getClientDao();
                            List<Client> clients = clientDao.queryBuilder().where(
                                    ClientDao.Properties.FName.eq(cName.getText().toString().trim()
                                    )).list();
                            if (clients.size() > 0) {
                                clientId = clients.get(0).FItemID;
                                clientName = clients.get(0).FName;
                                final List<T_main> mains = t_mainDao.queryBuilder().where(
                                        T_mainDao.Properties.Activity.eq(tag),
                                        T_mainDao.Properties.OrderId.eq(orderID)
                                ).build().list();
                                List<T_Detail> t_details = t_detailDao.queryBuilder().where(
                                        T_DetailDao.Properties.Activity.eq(tag),
                                        T_DetailDao.Properties.FOrderId.eq(orderID)
                                ).build().list();
                                for (int i = 0; i < mains.size(); i++) {
                                    mains.get(i).supplierId = clientId;
                                    mains.get(i).supplier = clientName;
                                    t_mainDao.update(mains.get(i));
                                }
                                for (int i = 0; i < t_details.size(); i++) {
                                    t_details.get(i).clientName = clientName;
                                    t_detailDao.update(t_details.get(i));
                                }
                                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT) {
                                    share.setSClientID(clientId);
                                    share.setSClient(clientName);
                                    Toast.showText(mContext, "更新成功");
                                    cName.setText(clientName);
                                }
                                if (tag == Config.CHOOSE_SECOND_FRAGMENT_LONG) {
                                    share.setLClient(clientName);
                                    share.setLClientID(clientId);
                                    Toast.showText(mContext, "更新成功");
                                    cName.setText(clientName);
                                }
                                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT_RED) {
                                    Hawk.put(Config.Short_red_ClientId_s, clientId);
                                    Hawk.put(Config.Short_red_ClientName_s, clientName);
                                    Toast.showText(mContext, "更新成功");
                                    cName.setText(clientName);
                                }
                                if (tag == Config.CHOOSE_SECOND_FRAGMENT_LONG_RED) {
                                    Hawk.put(Config.Long_red_ClientId_s, clientId);
                                    Hawk.put(Config.Long_red_ClientName_s, clientName);
                                    Toast.showText(mContext, "更新成功");
                                    cName.setText(clientName);
                                }
                            } else {
                                Toast.showText(mContext, "客户信息有误");
                            }
                        }

                    }
                });
                changeClient.setNegativeButton("取消", null);
                changeClient.create().show();
                break;
            case R.id.btn_backgoods:
                List<TempDetail> tempDetails = tempDetailDao.queryBuilder().where(
                        TempDetailDao.Properties.Activity.eq(tag)
                ).build().list();
                List<T_Detail> list = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.Activity.eq(tag)
                ).build().list();
                if (tempDetails.size() == 0) {
                    if (list.size() != 0) {
                        BackGoodsActivity.start(lookActivity,tag,orderID,lookActivity.getClientName());
//                        lookActivity.startActivityToBackGoods(orderID, tag);
                    } else {
                        Toast.showText(mContext, "不存在单据信息");
                    }
                } else {
                    Toast.showText(mContext, "存在未添加的物料");
                }
                break;
            case R.id.btn_add_more:
                if (tag == Config.CHOOSE_SECOND_FRAGMENT_SHORT){
                    ShortTreeOutAddMoreActivity.start(mContext,tag,orderID,lookActivity.getClientName(),baoshu);
                }else if (tag == Config.CHOOSE_SECOND_FRAGMENT_LONG){
                    LongTreeOutAddMoreActivity.start(mContext,tag,orderID,lookActivity.getClientName(),baoshu);
                }

                break;
        }


    }

    @Override
    public void InnerItemOnClick(View v) {
        final int position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.btn_plus:
                final T_Detail t_detail = thirdRvAdapter.getItems(position);
                Log.e("click", t_detail.toString());
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("是否添加");
                StringBuilder msg = new StringBuilder();
                msg.append("物料名称：" + t_detail.FProductName + "\n");
                msg.append("规格型号：" + t_detail.model + "\n");
                msg.append("物料径直：" + t_detail.diameter + "\n");
                msg.append("物料根数：" + t_detail.radical + "\n");
                msg.append("物料体积：" + t_detail.FQuantity + "\n");
                msg.append("物料长度：" + t_detail.length + "\n");
                ab.setMessage(msg);
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RadicalAdd(position);
                    }
                }).setNegativeButton("取消", null).create().show();
                break;

            case R.id.btn_enduce:
                final T_Detail t_detail2 = thirdRvAdapter.getItems(position);
                AlertDialog.Builder ab2 = new AlertDialog.Builder(mContext);
                ab2.setTitle("是否减少");
                StringBuilder msg2 = new StringBuilder();
                msg2.append("物料名称：" + t_detail2.FProductName + "\n");
                msg2.append("规格型号：" + t_detail2.model + "\n");
                msg2.append("物料径直：" + t_detail2.diameter + "\n");
                msg2.append("物料根数：" + t_detail2.radical + "\n");
                msg2.append("物料体积：" + t_detail2.FQuantity + "\n");
                msg2.append("物料长度：" + t_detail2.length + "\n");
                ab2.setMessage(msg2);
                ab2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RadicalDel(position);
                    }
                }).setNegativeButton("取消", null).create().show();
                break;
        }
    }

    private void RadicalDel(int position) {
        T_Detail items = thirdRvAdapter.getItems(position);
        if (Integer.parseInt(items.radical) == 1) {
            t_detailDao.delete(items);
        } else {
            items.radical = (Integer.parseInt(items.radical) - 1) + "";
            items.FQuantity = (CalculateUtil.getVoleum(items.length, items.diameter + "") * Integer.parseInt(items.radical)) + "";
            t_detailDao.update(items);
        }

        thirdRvAdapter.notifyDataSetChanged();
    }

    private void RadicalAdd(int position) {
        T_Detail items = thirdRvAdapter.getItems(position);
        items.radical = (Integer.parseInt(items.radical) + 1) + "";
        items.FQuantity = (CalculateUtil.getVoleum(items.length, items.diameter + "") * Integer.parseInt(items.radical)) + "";
        t_detailDao.update(items);
        thirdRvAdapter.notifyDataSetChanged();
    }

}
