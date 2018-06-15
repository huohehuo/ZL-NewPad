package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fangzuo.assist.Dao.Product;
import com.fangzuo.assist.Dao.PushDownSub;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.GreenDaoManager;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.ProductDao;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NB on 2017/8/25.
 */

public class PushDownSubListAdapter extends BaseAdapter {
    Context context;
    List<PushDownSub> items;

    public PushDownSubListAdapter(Context context, List<PushDownSub> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.pushdown_sublist, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DaoSession daoSession = GreenDaoManager.getmInstance(context).getDaoSession();
        ProductDao productDao = daoSession.getProductDao();
        List<Product> list = productDao.queryBuilder().where(ProductDao.Properties.FItemID.eq(items.get(i).FItemID)).build().list();
        String productName;
        if(list.size()>0){
            productName = list.get(0).FName;
        }else{
            productName = items.get(i).FItemID;
        }
        if((int) (Double.parseDouble(items.get(i).FQtying)/Double.parseDouble(items.get(i).FAuxQty)*100)==100){
            view.setBackgroundColor(viewHolder.blue);
        }else{
            view.setBackgroundColor(viewHolder.white);
        }

        viewHolder.billNo.setText("订单号:"+items.get(i).FBillNo);
        viewHolder.numyanshou.setText("订单数量:"+items.get(i).FAuxQty);
        viewHolder.productId.setText("物料名称:"+ productName);
        viewHolder.numyanshouing.setText("已验数量:"+items.get(i).FQtying);
        viewHolder.pg.setProgress((int) (Double.parseDouble(items.get(i).FQtying)/Double.parseDouble(items.get(i).FAuxQty)*100));
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.billNo)
        TextView billNo;
        @BindView(R.id.productId)
        TextView productId;
        @BindView(R.id.numyanshou)
        TextView numyanshou;
        @BindView(R.id.numyanshouing)
        TextView numyanshouing;
        @BindView(R.id.progress)
        ProgressBar pg;
        @BindColor(R.color.cpb_blue)int blue;
        @BindColor(R.color.white)int white;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
