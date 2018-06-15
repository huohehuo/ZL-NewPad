package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Lg;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王璐阳 on 2018/3/27.
 */

public class ThirdRvAdapter extends RecyclerView.Adapter<ThirdRvAdapter.ViewHolder> implements View.OnClickListener {
    public List<T_Detail> items;
    Context context;
    private OnItemClickListener onItemClickListener;
    private RvInnerClickListener mListener;
    boolean isShown;
    public ThirdRvAdapter(Context context, List<T_Detail> items,boolean isShown) {
        this.items = items;
        this.context = context;
        this.isShown = isShown;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_rv_third, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public T_Detail getItems(int position) {
        return items.get(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }else{
                    Lg.e("onItemClickListener is null");
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }else{

                    Lg.e("onItemClickListener long is null");
                }
                return true;
            }
        });

        holder.productName.setText(items.get(position).FProductName);   //物料名称
        holder.voleum.setText(items.get(position).FQuantity);           //体积
        holder.orderID.setText(items.get(position).FOrderId+"");        //订单编号
        holder.model.setText(items.get(position).model);                //型号
        holder.diameter.setText(items.get(position).diameter+"");          //径直
        holder.queue.setText(items.get(position).FIdentity);            //报数
        holder.tvLength.setText(items.get(position).length);
        if ("红字".equals(items.get(position).FRedBlue)){
            holder.radical.setTextColor(Color.RED);
            holder.radical.setText("-"+items.get(position).radical);            //根数
        }else{
            holder.radical.setText(items.get(position).radical);            //根数
        }
        if(!isShown){
            holder.enduce.setVisibility(View.GONE);
            holder.Plus.setVisibility(View.GONE);
        }
        holder.enduce.setOnClickListener(this);
        holder.enduce.setTag(position);
        holder.Plus.setOnClickListener(this);
        holder.Plus.setTag(position);


    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addNewItem(T_Detail temp) {
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


    @Override
    public void onClick(View view) {
        mListener.InnerItemOnClick(view);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    public interface RvInnerClickListener {
        void InnerItemOnClick(View v);
    }
    public void setInnerClickListener(RvInnerClickListener mListener){
        this.mListener = mListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.orderID)
        TextView orderID;
        @BindView(R.id.queue)
        TextView queue;
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.model)
        TextView model;
        @BindView(R.id.diameter)
        TextView diameter;
        @BindView(R.id.tv_length)
        TextView tvLength;
        @BindView(R.id.voleum)
        TextView voleum;
        @BindView(R.id.radical)
        TextView radical;
        @BindView(R.id.btn_plus)
        ImageView Plus;
        @BindView(R.id.btn_enduce)
        ImageView enduce;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
