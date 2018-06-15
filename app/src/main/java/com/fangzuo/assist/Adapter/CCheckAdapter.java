package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzuo.assist.Beans.SecondCheck;
import com.fangzuo.assist.Beans.ThirdCheck;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;


public class CCheckAdapter extends RecyclerArrayAdapter<ThirdCheck> {
    public CCheckAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckHolder(parent);
    }
    class CheckHolder extends BaseViewHolder<ThirdCheck> {

        TextView orderID;
        TextView queue;
        TextView productName;
        TextView model;
        TextView diameter;
        TextView tvLength;
        TextView voleum;
        TextView radical;
        ImageView Plus;
        ImageView enduce;

        public CheckHolder(ViewGroup parent) {
            super(parent, R.layout.item_rv_c_check);
            orderID     = $(R.id.orderID);
            queue       = $(R.id.queue);
            productName = $(R.id.productName);
            model       = $(R.id.model);
            diameter    = $(R.id.diameter);
            tvLength    = $(R.id.tv_length);
            voleum      = $(R.id.voleum);
            radical     = $(R.id.radical);
            Plus        = $(R.id.btn_plus);
            enduce      = $(R.id.btn_enduce);

        }
        @Override
        public void setData(ThirdCheck data) {
            super.setData(data);
            productName.setText(data.productname+data.model);   //物料名称
            voleum.setText(data.voleum);                //体积
            orderID.setText(data.FOrderId+"");        //订单编号
            radical.setText((int) Math.ceil(Double.parseDouble(data.radical))+"");            //根数
            model.setText(data.model);                //型号
            diameter.setText((int) Math.ceil(Double.parseDouble(data.diameter))+"");          //径直
            queue.setText(data.FIdentity);            //报数
            tvLength.setText(data.length);

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
