package com.fangzuo.assist.Beans;

import java.io.Serializable;

/**
 * Created by 王璐阳 on 2018/4/11.
 */

public class ThirdCheck {
    public String productname;    //物料名称
    public String voleum;         //体积
    public String FOrderId;        //订单编号
    public String radical;        //根数
    public String model;          //型号
    public String diameter;       //径直
    public String FIdentity;          //报数
    public String length;       //长度
    public String FBillNo;       //销售出库单号
    public String FInterID;           //长度
    public String FItemID;           //商品id

    public String FDriverName;           //长度
    public String FVanNo;           //长度
    public String FDate;           //长度
    public String FStorageName;           //长度
    public String FMaker;           //长度
    public String FClientName;           //长度


    @Override
    public String toString() {
        return "ThirdCheck{" +
                "productname='" + productname + '\'' +
                ", voleum='" + voleum + '\'' +
                ", FOrderId='" + FOrderId + '\'' +
                ", radical='" + radical + '\'' +
                ", model='" + model + '\'' +
                ", diameter='" + diameter + '\'' +
                ", FIdentity='" + FIdentity + '\'' +
                ", length='" + length + '\'' +
                ", FBillNo='" + FBillNo + '\'' +
                ", FInterID='" + FInterID + '\'' +
                ", FDriverName='" + FDriverName + '\'' +
                ", FVanNo='" + FVanNo + '\'' +
                ", FDate='" + FDate + '\'' +
                ", FStorageName='" + FStorageName + '\'' +
                ", FMaker='" + FMaker + '\'' +
                ", FClientName='" + FClientName + '\'' +
                '}';
    }
}
