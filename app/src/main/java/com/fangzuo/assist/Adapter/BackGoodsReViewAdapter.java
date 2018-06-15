package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Beans.SecondCheck;
import com.fangzuo.assist.Dao.BackGoodsBean;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class BackGoodsReViewAdapter extends RecyclerArrayAdapter<BackGoodsBean> {
    public BackGoodsReViewAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckHolder(parent);
    }
    class CheckHolder extends BaseViewHolder<BackGoodsBean> {

        TextView productName;
        TextView voleum;
        TextView radical;
        TextView diameter;
        TextView tvLength;

        public CheckHolder(ViewGroup parent) {
            super(parent, R.layout.item_backgoods_review);
            productName = $(R.id.tv_name);
            voleum = $(R.id.tv_voleum);
            radical = $(R.id.tv_radical);
            diameter = $(R.id.tv_diameter);
            tvLength = $(R.id.tv_length);

        }
        @Override
        public void setData(BackGoodsBean data) {
            super.setData(data);
//            diameter.setText((int) Math.ceil(Double.parseDouble(data.diameter))+"");
            diameter.setText("径直:"+data.diameter+"");
            productName.setText(data.FProductName);
            voleum.setText("体积"+data.FQuantity);
            radical.setText("根数:"+data.radical);
            tvLength.setText("长度:"+data.length);
        }
    }
}
