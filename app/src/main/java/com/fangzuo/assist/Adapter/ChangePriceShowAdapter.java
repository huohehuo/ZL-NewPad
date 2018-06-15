package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Dao.TempChangePrice;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

public class ChangePriceShowAdapter extends RecyclerArrayAdapter<TempChangePrice> {
    Context context;
    List<Boolean> isCheck;

    public ChangePriceShowAdapter(Context context) {
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


    public void addCheck(List<Boolean> isCheck) {
        this.isCheck = isCheck;
        notifyDataSetChanged();
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//        MarkHolder holder = new MarkHolder(parent);
//        holder.checkBox.setChecked(isCheck.get());

        return new MarkHolder(parent);
//        if (viewType==2){
//            Log.e("holder","返回图文布局");
//            return new MarkHolder(parent);
//        }else{
//            Log.e("holder","返回--文字--布局");
//            return new MainHolderForTxt(parent);
//        }
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder1, int position, List<Object> payloads) {
        final MarkHolder holder = (MarkHolder) holder1;

        holder.FBillNo .setText("单据编号："+getAllData().get(position).getFBillNo());
//        holder.FDate .setText("日期："+getAllData().get(position).getFDate());
        holder.FModel .setText("型号："+getAllData().get(position).getFModel());
        holder.FNumber .setText("物料编码："+getAllData().get(position).getFNumber());
        holder.FName .setText("物料名称："+getAllData().get(position).getFName());
        holder.FPriceOld .setText("原价："+getAllData().get(position).getFPriceOld());
        holder.FPriceChange .setText("修改价："+getAllData().get(position).getFChangePrice());
        holder.FSupplier .setText("供应商："+getAllData().get(position).getFSupplier());
//        holder.FSpNo .setText("序号："+getAllData().get(position).getFSpNo());
        holder.ChangeDate .setText("修改时间："+getAllData().get(position).getChangeDate());
        holder.HasTax .setText(getAllData().get(position).getHasTax());

//        if (isCheck != null) {
//            if (isCheck.get(position)){
//                holder.checkBox.setBackgroundColor(Color.BLUE);
//            }else{
//                holder.checkBox.setBackgroundColor(Color.WHITE);
//            }
//        }

    }

    class MarkHolder extends BaseViewHolder<TempChangePrice> {

        private TextView FBillNo;
        private TextView FDate;
        private TextView FModel;
        private TextView FNumber;
        private TextView FName;
        private TextView FPriceOld;
        private TextView FPriceChange;
        private TextView FSupplier;
        private View checkBox;
        private TextView FSpNo;
        private TextView ChangeDate;
        private TextView HasTax;
        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.item_change_price_show);
            FBillNo= $(R.id.tv_billno);
//            FDate= $(R.id.tv_date);
            FModel= $(R.id.tv_model);
            FNumber= $(R.id.tv_number);
            FName= $(R.id.tv_name);
            FPriceOld= $(R.id.tv_price_old);
            FPriceChange= $(R.id.tv_price_change);
            FSupplier= $(R.id.tv_supplier);
//            FSpNo= $(R.id.tv_spno);
            ChangeDate= $(R.id.tv_changedate);
            HasTax= $(R.id.tv_hastax);

            checkBox = $(R.id.view_cb);
        }

        @Override
        public void setData(TempChangePrice data) {
            super.setData(data);
//            FItemID.setText(data.getFItemID());
//            FInterID.setText(data.getFInterID());
//            FChangePrice.setText(data.getFChangePrice());
//            FEntryID.setText(data.getFEntryID());
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
