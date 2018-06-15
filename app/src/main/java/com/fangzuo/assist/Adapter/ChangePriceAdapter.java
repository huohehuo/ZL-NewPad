package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Dao.ChangePrice;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class ChangePriceAdapter extends RecyclerArrayAdapter<ChangePrice> {
    Context context;
    public ChangePriceAdapter(Context context) {
        super(context);
    }
    //
//    public MarkAdapter(Context context, MarkBean[] objects) {
//        super(context, objects);
//    }
//
//    public MarkAdapter(Context context, List<MarkBean> objects) {
//        super(context, objects);
//    }
//    @Override
//    public int getViewType(int position) {
//        return Integer.valueOf(getAllData().get(position).getType());
//
//    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MarkHolder(parent);
//        if (viewType==2){
//            Log.e("holder","返回图文布局");
//            return new MarkHolder(parent);
//        }else{
//            Log.e("holder","返回--文字--布局");
//            return new MainHolderForTxt(parent);
//        }

    }


    class MarkHolder extends BaseViewHolder<ChangePrice> {

        private TextView FBillNo;
        private TextView FDate;
        private TextView FModel;
        private TextView FNumber;
        private TextView FName;
        private TextView FUnit;
        private TextView FPriceOld;
        private TextView FPriceChange;
        private TextView FSupplier;
        private TextView FHaxTax;

        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.item_change_price);
            FBillNo= $(R.id.tv_billno);
            FDate= $(R.id.tv_date);
            FModel= $(R.id.tv_model);
            FNumber= $(R.id.tv_number);
            FName= $(R.id.tv_name);
            FUnit= $(R.id.tv_unit);
            FPriceOld= $(R.id.tv_price_old);
            FPriceChange= $(R.id.tv_price_change);
            FSupplier= $(R.id.tv_supplier);
            FHaxTax= $(R.id.tv_hastax);

        }

        @Override
        public void setData(ChangePrice data) {
            super.setData(data);
            FBillNo.setText(    "单据编号："+data.getFBillNo());
            FDate.setText(      "日期："+data.getFDate());
            FModel.setText(     "型号："+data.getFModel());
            FNumber.setText(    "物料编码："+data.getFNumber());
            FName.setText(      "物料名称："+data.getFName());
            FUnit.setText(      "单位："+data.getFUnit());
            FPriceOld.setText(  "原价："+data.getFPriceOld());
            FPriceChange.setText("修改价："+data.getFPriceChange());
            FSupplier.setText(  "客户："+data.getFSupplier());
            FHaxTax.setText(      data.getFHasTax());

//            name.setTextColor(App.getInstance().getColor(R.color.black));
//        //3、为集合添加值
//            isClicks = new ArrayList<>();
//            for(int i = 0;i<ChangePrice.size();i++){
//                isClicks.add(false);
//            }
//            name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    name.setTextColor(App.getInstance().getColor(R.color.cpb_blue));
//                }
//            });

//            num.setText(data.getFavour().get__op());

//            favour.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(App.getContext(), "喜欢+1", Toast.LENGTH_SHORT).show();
//                }
//            });

//            Glide.with(getContext())
//                    .load(data.getBg_pic())
//                    .placeholder(R.drawable.dog)
//                    .centerCrop()
//                    .into(img_bg);

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
