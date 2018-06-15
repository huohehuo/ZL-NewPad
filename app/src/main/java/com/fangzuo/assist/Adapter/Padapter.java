package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王璐阳 on 2018/4/15.
 */

public class Padapter extends BaseAdapter {
    public ArrayList<FirstCheck> items;
    Context context;
    private ViewHolder viewHolder;

    public Padapter(ArrayList<FirstCheck> items, Context context) {
        this.items = items;
        this.context = context;
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

    public FirstCheck getFirst(int position){
        return items.get(position);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_rv_firstrv, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.clientName.setText("客户名称:"+items.get(i).clientName);
        viewHolder.productName.setText("商品名称:"+items.get(i).productname);
        viewHolder.voleum.setText("总体积:"+items.get(i).TVoleum);
        viewHolder.orderID.setText("订单号:"+items.get(i).orderID);
        viewHolder.radical.setText("总根数:"+items.get(i).TRadical);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.tv_clientName)
        TextView clientName;
        @BindView(R.id.orderID)
        TextView orderID;
        @BindView(R.id.voleum)
        TextView voleum;
        @BindView(R.id.radical)
        TextView radical;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
