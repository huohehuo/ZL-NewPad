package com.fangzuo.assist.Beans;

import java.io.Serializable;

/**
 * Created by 王璐阳 on 2018/4/11.
 */

public class SecondCheck implements Serializable {
    private static final long serialVersionUID = -8143499640736757003L;
    public String orderID;          //订单号
    public String productname;      //商品名臣
    public String TRadical;         //总根数
    public String TVoleum;          //总体积
    public String itemID;
    public String model;            //规格型号
    public String diameter;

    public String length;           //长度
    public String FInterID;           //长度
    public String FBillNo;           //销售出库单号
    public String FRedBlue;

    @Override
    public String toString() {
        return "SecondCheck{" +
                "orderID='" + orderID + '\'' +
                ", productname='" + productname + '\'' +
                ", TRadical='" + TRadical + '\'' +
                ", TVoleum='" + TVoleum + '\'' +
                ", itemID='" + itemID + '\'' +
                ", model='" + model + '\'' +
                ", diameter='" + diameter + '\'' +
                ", length='" + length + '\'' +
                ", FInterID='" + FInterID + '\'' +
                ", FBillNo='" + FBillNo + '\'' +
                '}';
    }
}
