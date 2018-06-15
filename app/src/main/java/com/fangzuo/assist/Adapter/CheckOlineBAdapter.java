package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Beans.SecondCheck;
import com.fangzuo.assist.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王璐阳 on 2018/3/27.
 */

public class CheckOlineBAdapter extends RecyclerView.Adapter<CheckOlineBAdapter.ViewHolder> {
    public ArrayList<SecondCheck> items;
    Context context;
    private OnItemClickListener onItemClickListener;

    public CheckOlineBAdapter(Context context, ArrayList<SecondCheck> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_rv_secondrv_long, parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public SecondCheck getItems(int position) {
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
        holder.diameter.setText(items.get(position).diameter);
        holder.productName.setText(items.get(position).model);
        holder.voleum.setText(items.get(position).TVoleum);
        holder.orderID.setText(items.get(position).orderID);
        holder.radical.setText(items.get(position).TRadical);
        holder.tvLength.setText(items.get(position).length);

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addNewItem(SecondCheck temp) {
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
        @BindView(R.id.diameter)
        TextView diameter;
        @BindView(R.id.tv_length)
        TextView tvLength;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
