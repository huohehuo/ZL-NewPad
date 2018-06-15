package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Beans.SecondCheck;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;


public class BCheckAdapter extends RecyclerArrayAdapter<SecondCheck> {
    public BCheckAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckHolder(parent);
    }
    class CheckHolder extends BaseViewHolder<SecondCheck> {

        TextView productName;
        TextView voleum;
        TextView orderID;
        TextView radical;
        TextView diameter;
        TextView tvLength;

        public CheckHolder(ViewGroup parent) {
            super(parent, R.layout.item_rv_b_check);
            productName = $(R.id.productName);
            voleum = $(R.id.voleum);
            orderID = $(R.id.orderID);
            radical = $(R.id.radical);
            diameter = $(R.id.diameter);
            tvLength = $(R.id.tv_length);

        }
        @Override
        public void setData(SecondCheck data) {
            super.setData(data);
            diameter.setText((int) Math.ceil(Double.parseDouble(data.diameter))+"");
            productName.setText(data.model);
            voleum.setText(data.TVoleum);
            orderID.setText(data.orderID);
            radical.setText((int) Math.ceil(Double.parseDouble(data.TRadical))+"");
            tvLength.setText(data.length);
        }
    }
}
