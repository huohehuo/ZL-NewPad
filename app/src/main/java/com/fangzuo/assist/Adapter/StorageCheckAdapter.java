package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Beans.BlueToothBean;
import com.fangzuo.assist.Beans.InStorageNumListBean;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Utils.Lg;
import com.google.zxing.common.StringUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class StorageCheckAdapter extends RecyclerArrayAdapter<InStorageNumListBean.inStoreList> {
    String[] cuteName;
    public StorageCheckAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new StorageHolder(parent);
//        if (viewType==2){
//            Log.e("holder","返回图文布局");
//            return new MarkHolder(parent);
//        }else{
//            Log.e("holder","返回--文字--布局");
//            return new MainHolderForTxt(parent);
//        }

    }


    class StorageHolder extends BaseViewHolder<InStorageNumListBean.inStoreList> {

        private TextView name;
        private TextView code;
        private TextView model;
        private TextView unit;
        private TextView other_unit;

        public StorageHolder(ViewGroup parent) {
            super(parent, R.layout.item_storage_check);
            name = $(R.id.tv_name);
            code = $(R.id.tv_code);
            model = $(R.id.tv_model);
            unit = $(R.id.tv_unit);
            other_unit = $(R.id.tv_other_unit);

        }

        @Override
        public void setData(InStorageNumListBean.inStoreList data) {
            super.setData(data);
//            if (data.FName.contains(data.FModel)){
//                cuteName=data.FName.split(data.FModel);
//                if (cuteName.length>0){
//                    name.setText(cuteName[0]);
//                }else{
//                    name.setText(data.FName);
//                }
//            }else{
//                name.setText(data.FName);
//            }
            name.setText(data.FName);

//            code.setText("代码:"+data.FNumber);
//            model.setText("型号:"+data.FModel);
            if ("立方米".equals(data.FUnit)){
                unit.setText(data.FQty+" m³");
            }else{
                unit.setText(data.FQty+data.FUnit);
            }
//            other_unit.setText(data.FSecQty+data.FSecUnit);
            int toInt = (int)Math.ceil(Double.parseDouble(data.FSecQty));
            other_unit.setText(toInt+data.FSecUnit);
//            }


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
