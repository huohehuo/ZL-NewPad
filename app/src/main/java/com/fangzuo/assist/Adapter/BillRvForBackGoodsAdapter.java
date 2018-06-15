package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Lg;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王璐阳 on 2018/3/27.
 */

    public class BillRvForBackGoodsAdapter extends RecyclerView.Adapter<BillRvForBackGoodsAdapter.ViewHolder> implements View.OnClickListener {
    public ArrayList<T_Detail> items;
    private RvInnerClickListener mListener;
    //1、定义一个集合，用来记录选中
    Context context;
    private String goToPosition="";//用于定位高亮那行数据
    private OnItemClickListener onItemClickListener;

    public BillRvForBackGoodsAdapter(Context context, ArrayList<T_Detail> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_bill_rv, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    public T_Detail getItems(int position){
        return items.get(position);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder,int position) {
        holder.tvLength.setText(items.get(position).length);
        holder.tvProductName.setText(items.get(position).FProductName);
        holder.tvWidth.setText(items.get(position).diameter+"");
        holder.tvVolume.setText(items.get(position).FQuantity);
        holder.tvNum.setText(items.get(position).radical);
        holder.btnEnduce.setTag(position);
        holder.btnPlus.setTag(position);
        holder.btnEnduce.setOnClickListener(this);
        holder.btnPlus.setOnClickListener(this);
        holder.tvModel.setText(items.get(position).model);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                onItemClickListener.onItemClick(holder.itemView,pos);
            }
        });

        if (!"".equals(goToPosition) && goToPosition.equals(items.get(position).diameter+"")){
//            Log.e("adapter",goToPosition);
            if ("红字".equals(items.get(position).FRedBlue)){
                holder.itemView.setBackgroundColor(Color.parseColor("#ffff4444"));
            }else{
                holder.itemView.setBackgroundColor(Color.parseColor("#ff00ddff"));
            }
        }else{
//            Log.e("adapter","????");
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    //用于设置哪行高亮
    public void goToPostion(String str){
        goToPosition = str;
        Lg.e("gotoPostion:"+str);
        notifyDataSetChanged();
    }
    public void clickTo(int position){

    }

    @Override
    public void onClick(View view) {
        mListener.InnerItemOnClick(view);
    }
    public interface RvInnerClickListener {
        void InnerItemOnClick(View v);
    }
    public void setInnerClickListener(RvInnerClickListener mListener){
        this.mListener = mListener;
    }
    public void addNewItem(T_Detail temp) {
        if(items!=null){
            items.add(temp);
            notifyItemInserted(items.size()+1);
        }
    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(BillRvForBackGoodsAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.btn_enduce)
        RelativeLayout btnEnduce;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.btn_plus)
        RelativeLayout btnPlus;
        @BindView(R.id.tv_productName)
        TextView tvProductName;
        @BindView(R.id.tv_length)
        TextView tvLength;
        @BindView(R.id.tv_width)
        TextView tvWidth;
        @BindView(R.id.tv_volume)
        TextView tvVolume;
        @BindView(R.id.tv_model)
        TextView tvModel;
        @BindView(R.id.ll_bill)
        LinearLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
