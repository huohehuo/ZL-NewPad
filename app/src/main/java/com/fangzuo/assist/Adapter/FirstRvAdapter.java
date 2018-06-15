package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王璐阳 on 2018/3/27.
 */

public class FirstRvAdapter extends RecyclerView.Adapter<FirstRvAdapter.ViewHolder> {
    public ArrayList<FirstCheck> items;
    Context context;
    private OnItemClickListener onItemClickListener;

    public FirstRvAdapter(Context context, ArrayList<FirstCheck> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_rv_firstrv, parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public FirstCheck getItems(int position) {
        return items.get(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                onItemClickListener.onItemClick(holder.itemView, pos);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                return true;
            }
        });
        holder.tv_clientName.setText("客户:"+items.get(position).clientName);
        holder.productName.setText("商品名称:"+items.get(position).productname);
        holder.voleum.setText("总体积:"+items.get(position).TVoleum);
        holder.orderID.setText("订单号:"+items.get(position).orderID);
        holder.radical.setText("总根数:"+items.get(position).TRadical);
        if ("红字".equals(items.get(position).FRedBlue)){
            holder.radical.setTextColor(Color.RED);
        }
        if (items.get(position).productname.contains(items.get(position).model)){
            holder.model.setText("规格型号:"+items.get(position).productname);
        }else{
            holder.model.setText("规格型号:"+items.get(position).productname+items.get(position).model);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addNewItem(FirstCheck temp) {
        if (items != null) {
            items.add(temp);
            notifyItemInserted(items.size() + 1);
        }
    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.voleum)
        TextView voleum;
        @BindView(R.id.orderID)
        TextView orderID;
        @BindView(R.id.radical)
        TextView radical;
        @BindView(R.id.model)
        TextView model;
        @BindView(R.id.tv_clientName)
        TextView tv_clientName;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
