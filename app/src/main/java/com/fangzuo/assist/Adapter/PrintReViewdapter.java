package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprinter.App;
import com.fangzuo.assist.Beans.BlueToothBean;
import com.fangzuo.assist.Beans.FirstCheck;
import com.fangzuo.assist.Dao.T_Detail;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

public class PrintReViewdapter extends RecyclerArrayAdapter<FirstCheck> {
    public PrintReViewdapter(Context context) {
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
    @Override
    public int getViewType(int position) {
        return getAllData().get(position).getPrintType();

    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MarkHolder(parent);
        if (viewType!=1){
            Log.e("holder","详情列表");
            return new MarkHolder(parent);
        }else{
            Log.e("holder","仓库名称");
            return new StorageHolder(parent);
        }

    }


    class MarkHolder extends BaseViewHolder<FirstCheck> {

        private TextView orderID;
//        private TextView queue;
        private TextView productName;
        private TextView model;
        private TextView diameter;
        private TextView tvLength;
        private TextView voleum;
        private TextView radical;

        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.item_ry_print_review);
            orderID     =    $(R.id.orderID);
//            queue       =    $(R.id.queue);
            productName =    $(R.id.productName);
            model       =    $(R.id.model);
            diameter    =    $(R.id.diameter);
            tvLength    =    $(R.id.tv_length);
            voleum      =    $(R.id.voleum);
            radical     =    $(R.id.radical);

        }

        @Override
        public void setData(FirstCheck data) {
            super.setData(data);
            productName.setText(    data.getProductname()  );       //物料名称
            voleum.setText(         data.getTVoleum()     );             //体积
            orderID.setText(        data.getOrderID()+""   );       //订单编号
            model.setText(          data.getModel()         );         //型号
            diameter.setText(       data.getDiameter()+""   );         //径直
//            queue.setText(          data.getFIdentity()     );         //报数
            tvLength.setText(       data.getLength()        );
            if ("红字".equals(data.getFRedBlue())){
                radical.setTextColor(Color.RED);
                radical.setText("-"+data.getTRadical());            //根数
            }else{
                radical.setText(data.getTRadical());            //根数
            }

//            name.setTextColor(App.getInstance().getColor(R.color.black));
//        //3、为集合添加值
//            isClicks = new ArrayList<>();
//            for(int i = 0;i<BlueToothBean.size();i++){
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




    class StorageHolder extends BaseViewHolder<FirstCheck> {

        private TextView storage;

        public StorageHolder(ViewGroup parent) {
            super(parent, R.layout.item_ry_print_review_storage);
            storage     =    $(R.id.tv_storage);
        }

        @Override
        public void setData(FirstCheck data) {
            super.setData(data);
            storage.setText(    data.getFstorageName()  );       //物料名称


//            name.setTextColor(App.getInstance().getColor(R.color.black));
//        //3、为集合添加值
//            isClicks = new ArrayList<>();
//            for(int i = 0;i<BlueToothBean.size();i++){
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
}
