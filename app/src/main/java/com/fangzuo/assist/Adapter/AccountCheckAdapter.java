package com.fangzuo.assist.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprinter.App;
import com.fangzuo.assist.Beans.AccountCheckData;
import com.fangzuo.assist.Dao.ChangePrice;
import com.fangzuo.assist.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class AccountCheckAdapter extends RecyclerArrayAdapter<AccountCheckData> {
    Context context;
    public AccountCheckAdapter(Context context) {
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
        return Integer.valueOf(getAllData().get(position).getBackDateType());

    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MarkHolder(parent);
        if (viewType==1){
            return new MarkHolder(parent);
        }else{
            return new MainHolderForTxt(parent);
        }

    }


    class MarkHolder extends BaseViewHolder<AccountCheckData> {

        private TextView FBillNoData;      //单据日期
        private TextView FAbstract;        //摘要
        private TextView FOrderNo;          //订单号
        private TextView FCarNo;            //车号
        private TextView FStorage;            //仓库
        private TextView FWaveHouse;            //仓位
        private TextView FProductName;            //产品名称
        private TextView FModel;            //规格型号
        private TextView FAuxNum;            //辅助数量
        private TextView FRealNum;            //实发数量
        private TextView FSalePrice;            //销售单价
        private TextView FHasTax;            //是否含税
        private TextView FShouldMoney;     //应收金额
        private TextView FGiveMoney;       //收款金额
        private TextView FLastMoney;       //期末金额


        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.item_account_check_horizontal);
            FBillNoData        = $(R.id.tv_billdata);
            FAbstract          = $(R.id.tv_abstract);
            FOrderNo           = $(R.id.tv_orderno);
            FCarNo             = $(R.id.tv_carno);
            FStorage           = $(R.id.tv_storage);
            FWaveHouse         = $(R.id.tv_wavehouse);
            FProductName       = $(R.id.tv_productname);
            FModel             = $(R.id.tv_model);
            FAuxNum            = $(R.id.tv_auxnum);
            FRealNum           = $(R.id.tv_realnum);
            FSalePrice         = $(R.id.tv_saleprice);
            FHasTax            =  $(R.id.tv_hastax);

            FShouldMoney   = $(R.id.tv_shouldmoney);
            FGiveMoney= $(R.id.tv_givemoney);
            FLastMoney= $(R.id.tv_lastmoney);

        }

        @Override
        public void setData(AccountCheckData data) {
            super.setData(data);
            FBillNoData.setText(   data.getFBillNoData());
             FAbstract       .setText(   data.getFAbstract());
             FOrderNo        .setText(   data.getFOrderNo());
             FCarNo          .setText(   data.getFCarNo());
             FStorage        .setText(   data.getFStorage());
             FWaveHouse      .setText(   data.getFWaveHouse());
             FProductName    .setText(   data.getFProductName());
             FModel          .setText(   data.getFModel());
             FAuxNum         .setText(   data.getFAuxNum());
             FRealNum        .setText(   data.getFRealNum());
             FSalePrice      .setText(   data.getFSalePrice());
             FHasTax         .setText(   data.getFHasTax());

            FShouldMoney.setText(   data.getFShouldMoney());
            FGiveMoney.setText(    data.getFGiveMoney());
            FLastMoney.setText(    data.getFLastMoney());

//            name.setTextColor(App.getInstance().getColor(R.color.black));
//        //3、为集合添加值
//            isClicks = new ArrayList<>();
//            for(int i = 0;i<AccountCheckData.size();i++){
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




    //纯文字布局
    class MainHolderForTxt extends BaseViewHolder<AccountCheckData> {

        private TextView FFirstMoney;
        private TextView FShouldMoney;
        private TextView FGiveMoney;
        private TextView FLastMoney;

        public MainHolderForTxt(ViewGroup parent) {
            super(parent, R.layout.item_account_check_b);

            FFirstMoney= $(R.id.tv_fristmoney);
            FShouldMoney= $(R.id.tv_shouldmoney);
            FGiveMoney= $(R.id.tv_givemoney);
            FLastMoney= $(R.id.tv_lastmoney);
        }

        @Override
        public void setData(AccountCheckData data) {
            super.setData(data);

            FFirstMoney.setText(   data.getFFirstMoney());
            FShouldMoney.setText(   data.getFShouldMoney());
            FGiveMoney.setText(    data.getFGiveMoney());
            FLastMoney.setText(    data.getFLastMoney());



        }
    }
}
