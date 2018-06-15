package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzuo.assist.Beans.StorageCheckB;
import com.fangzuo.assist.Beans.StorageCheckC;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class DetailsStorageCAdapter extends RecyclerArrayAdapter<StorageCheckC> {
    public DetailsStorageCAdapter(Context context) {
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


    class MarkHolder extends BaseViewHolder<StorageCheckC> {

        private TextView name;
        private TextView radical;
        private TextView volume;
        private TextView model;
        private TextView wavehouse;
        private TextView storage;

        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.item_detail_storage_c);
            name = $(R.id.tv_name);
            radical = $(R.id.tv_radical);
            volume = $(R.id.tv_volume);
            model = $(R.id.tv_model);
            wavehouse = $(R.id.tv_wavehouse);
            storage = $(R.id.tv_storage);

        }

        @Override
        public void setData(StorageCheckC data) {
            super.setData(data);
            name.setText("物料名称:"+data.getFName());
            radical.setText("根数："+data.getFRadical());
            volume.setText("体积："+data.getFVolume());
            model.setText("型号："+data.getFModel());
            wavehouse.setText("仓位："+data.getFWaveHouseName());
            storage.setText("仓库："+data.getFStorageName());
//            name.setTextColor(App.getInstance().getColor(R.color.black));
//        //3、为集合添加值
//            isClicks = new ArrayList<>();
//            for(int i = 0;i<StorageCheckC.size();i++){
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
