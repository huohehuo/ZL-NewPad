package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


public class ACheckAdapter extends RecyclerArrayAdapter<FirstCheck> {
    public ACheckAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckHolder(parent);
    }
    class CheckHolder extends BaseViewHolder<FirstCheck> {

        TextView productName;
        TextView tv_maker;
        TextView orderID;
        TextView voleum;
        TextView tvoleum;
        TextView radical;
        TextView tradical;
        TextView model;
        TextView tv_clientName;
        TextView tv_date;

        public CheckHolder(ViewGroup parent) {
            super(parent, R.layout.item_rv_a_check);
            productName = $(R.id.productName);
            tv_maker = $(R.id.tv_maker);
            orderID = $(R.id.orderID);
            voleum = $(R.id.voleum);
            radical = $(R.id.radical);
            tvoleum = $(R.id.tv_all_voleum);
            tradical = $(R.id.tv_all_radical);
            model = $(R.id.model);
            tv_clientName = $(R.id.tv_clientName);
            tv_date = $(R.id.tv_date);

        }
        @Override
        public void setData(FirstCheck data) {
            super.setData(data);
            tv_clientName.setText("客户:"+data.clientName);
            tv_maker.setText("业务员:"+data.FMaker);
//            productName.setText("商品名称:"+data.productname);
            orderID.setText("订单号:"+data.orderID);
            voleum.setText("体积:"+data.voleum);
            radical.setText("根数:"+data.radical);
            tvoleum.setText("总体积:"+data.TVoleum);
            tradical.setText("总根数:"+data.TRadical);
            if (data.productname.contains(data.model)){
                model.setText("规格型号:"+data.productname);
            }else{
                model.setText("规格型号:"+data.productname+data.model);
            }
            tv_date.setText("制单日期:"+data.date);




//            favour.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(App.getContext(), "喜欢+1", Toast.LENGTH_SHORT).show();
//                }
//            });


        }
    }



//    //纯文字布局
//    class MainHolderForTxt extends BaseViewHolder<PlanBean> {
//
//        private TextView time;
//        private TextView eesay;
//        private ImageView favour;
//        private TextView num;
//        public MainHolderForTxt(ViewGroup parent) {
//            super(parent, R.layout.item_plan_for_txt);
//            time = $(R.id.tv_time);
//            eesay = $(R.id.tv_main_essay);
//            num = $(R.id.tv_favour);
//            favour = $(R.id.iv_favour);
//        }
//
//        @Override
//        public void setData(PlanBean data) {
//            super.setData(data);
//            eesay.setText(data.getEssay());
//            time.setText(data.getCreatedAt());
////            num.setText(data.getFavour().get__op());
//
//            favour.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(App.getContext(), "喜欢+1", Toast.LENGTH_SHORT).show();
//                }
//            });
//
////            Glide.with(getContext())
////                    .load(data.getPic())
////                    .placeholder(R.mipmap.ic_launcher)
////                    .centerCrop()
////                    .into(imageView);
//
//        }
//    }
}
