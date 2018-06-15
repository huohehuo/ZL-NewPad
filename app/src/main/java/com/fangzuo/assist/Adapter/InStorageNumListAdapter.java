package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fangzuo.assist.Beans.InStorageNumListBean;
import com.fangzuo.assist.R;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王璐阳 on 2017/12/19.
 */

public class InStorageNumListAdapter extends BaseAdapter {
    ArrayList<InStorageNumListBean.inStoreList> items;
    Context context;
    private ViewHolder viewHolder;

    public InStorageNumListAdapter(ArrayList<InStorageNumListBean.inStoreList> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size()+1;
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
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_instoragenum, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        if(i==0){
            viewHolder.productCode.setText("物料编码");
            viewHolder.productName.setText("物料名称");
            viewHolder.stock.setText("基本单位库存");
            viewHolder.stock1.setText("辅助单位库存");
            viewHolder.unit.setText("基本单位");
            viewHolder.storage.setText("仓库");
            viewHolder.wavehouse.setText("仓位");
            viewHolder.batchNo.setText("批次");
            viewHolder.FKFDate.setText("生产日期");
            viewHolder.FKPeriod.setText("保质期");
            view.setBackgroundColor(viewHolder.grey);
        }else{
            viewHolder.productCode.setText(items.get(i-1).FNumber);
            viewHolder.productName.setText(items.get(i-1).FName);
            viewHolder.stock.setText(items.get(i-1).FQty);
            viewHolder.stock1.setText(items.get(i-1).FSecQty);
            viewHolder.unit.setText(items.get(i-1).FUnit);
            viewHolder.storage.setText(items.get(i-1).FStockID);
            viewHolder.wavehouse.setText(items.get(i-1).FStockPlaceID);
            viewHolder.batchNo.setText(items.get(i-1).FBatchNo);
            viewHolder.FKFDate.setText(items.get(i-1).FKFDate);
            viewHolder.FKPeriod.setText(items.get(i-1).FKFPeriod);
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.productCode)
        TextView productCode;
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.stock)
        TextView stock;
        @BindView(R.id.stock1)
        TextView stock1;
        @BindView(R.id.unit)
        TextView unit;
        @BindView(R.id.storage)
        TextView storage;
        @BindView(R.id.wavehouse)
        TextView wavehouse;
        @BindView(R.id.batchNo)
        TextView batchNo;
        @BindView(R.id.FKFDate)
        TextView FKFDate;
        @BindView(R.id.FKPeriod)
        TextView FKPeriod;
        @BindView(R.id.mylistItem)
        TableLayout mylistItem;
        @BindColor(R.color.background_tab_pressed)int grey;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
