package com.fangzuo.assist.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miniprinter.App;
import com.fangzuo.assist.Beans.StorageCheckA;
import com.fangzuo.assist.Beans.StorageCheckB;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class DetailsStorageBAdapter extends RecyclerArrayAdapter<StorageCheckB> {
    public Context mContext;
    public DetailsStorageBAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MarkHolder(parent);
    }


    class MarkHolder extends BaseViewHolder<StorageCheckB> {

        private TextView name;
        private TextView radical;
        private TextView volume;
        private TextView model;
        private TextView wavehouse;

        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.item_detail_storage_b);
            name = $(R.id.tv_name);
            radical = $(R.id.tv_radical);
            volume = $(R.id.tv_volume);
            model = $(R.id.tv_model);
            wavehouse = $(R.id.tv_wavehouse);

        }

        @Override
        public void setData(StorageCheckB data) {
            super.setData(data);
            name.setText("物料名称："+data.getFName());
            radical.setText("根数："+data.getFRadical());
            volume.setText("体积："+data.getFVolume());
            model.setText("型号："+data.getFModel());
            wavehouse.setText(data.getFWaveHouseAll());
//            wavehouse.setText("阿里速度快解放阿斯利康发生了贷款发速度快解放阿斯兰的积分按理说地方阿拉山口大姐夫按理说地方阿拉山口的发了啥快递发生浪蝶狂蜂阿拉山口的发了啥快递就法拉看书都法拉克是假的弗拉克斯等级分类看水电费杰拉德");
//            wavehouse.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder change = new AlertDialog.Builder(mContext);
//                    change.setTitle("包含仓位");
//                    change.setMessage("阿里速度快解放阿斯利康发生了贷款发速度快解放阿斯兰的积分按理说地方阿拉山口大姐夫按理说地方阿拉山口的发了啥快递发生浪蝶狂蜂阿拉山口的发了啥快递就法拉看书都法拉克是假的弗拉克斯等级分类看水电费杰拉德");
//                    change.setPositiveButton("确认",null);
//                    change.create().show();
//                }
//            });



            //            name.setTextColor(App.getInstance().getColor(R.color.black));
//        //3、为集合添加值
//            isClicks = new ArrayList<>();
//            for(int i = 0;i<StorageCheckB.size();i++){
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
