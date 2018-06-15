package com.fangzuo.assist.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.miniprinter.App;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.SquareRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王璐阳 on 2018/3/27.
 */

public class NumRv2Adapter extends RecyclerView.Adapter<NumRv2Adapter.NumRv2Holder> {
    public ArrayList<String> items;
    Context context;
    //1、定义一个集合，用来记录选中
    private List<Boolean> isClicks;
    private OnItemClickListener onItemClickListener;

    public NumRv2Adapter(Context context, ArrayList<String> items) {
        this.items = items;
        this.context = context;
        isClicks = new ArrayList<>();
        for(int i = 0;i<items.size();i++){
            isClicks.add(false);
        }
    }

    public String getItem(int position){
        return items.get(position);
    }

    @Override
    public NumRv2Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_rv_text, null);
        NumRv2Holder viewHolder = new NumRv2Holder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NumRv2Holder holder, int position) {
        holder.textView.setTextColor(App.getInstance().getResources().getColor(R.color.white));
        holder.textView.setText(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                for(int i = 0; i <isClicks.size();i++){
                    isClicks.set(i,false);
                }
                isClicks.set(pos,true);
                notifyDataSetChanged();


                onItemClickListener.onItemClick(holder.itemView,pos);
            }
        });
        //5、记录要更改属性的控件
        holder.itemView.setTag(holder.textView);
//        holder.itemView.setTag(holder.back);
        //6、判断改变属性
        if (isClicks.size()>0){
            if(isClicks.get(position)){
//                holder.itemView.setBackgroundColor(Color.parseColor("#ff00ddff"));
                holder.itemView.setBackground(App.getInstance().getDrawable(R.drawable.circlr_clicked));
            }else{
                holder.itemView.setBackground(App.getInstance().getDrawable(R.drawable.circlr));
            }
        }

    }



    @Override
    public int getItemCount() {
        for(int i = 0;i<items.size();i++){
            isClicks.add(false);
        }
        return items.size();
    }


    public void addNewItem(String temp) {
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
    }

//    static class ViewHolder extends RecyclerView.ViewHolder{
//        @BindView(R.id.textView)
//        TextView textView;
//        @BindColor(R.color.cpb_blue)
//                int cpb_blue;
//        @BindColor(R.color.white)
//        int white;
//        @BindView(R.id.bg_num)
//        SquareRelativeLayout squareRelativeLayout;
//        ViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }

    public class NumRv2Holder extends RecyclerView.ViewHolder {

        TextView textView;
        SquareRelativeLayout squareRelativeLayout;

        public NumRv2Holder(View itemView) {
            super(itemView);
            textView = (Button) itemView.findViewById(R.id.textView);
            squareRelativeLayout = itemView.findViewById(R.id.bg_num);
        }
        public void autoClick(){
            squareRelativeLayout.performClick();
        }
    }
}
