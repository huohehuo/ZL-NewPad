package com.fangzuo.assist.Beans;

import java.io.Serializable;

/**
 * Created by 王璐阳 on 2018/4/10.
 */

public class FirstCheck implements Serializable {
    private static final long serialVersionUID = -4899363884344250104L;
    public String orderID;          //订单号
    public String productname;      //商品名称
    public String TRadical;         //总根数
    public String TVoleum;          //总体积
    public String radical;         //根数
    public String voleum;          //体积
    public String itemID;           //
    public String model;            //规格型号
    public String length;           //长度

    public String diameter;         //
    public String clientName;       //客户名称
    public String fstorageName;       //仓库

    public String date;             //制单日期
    public String FBillNo ;             //销售出库单号
    public String FInterID ;             //制单日期
    public String FMaker;           //制单人

    public String FRedBlue;
    public int printType=0;          //用于打印时的多布局辨别
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getPrintType() {
        return printType;
    }

    public void setPrintType(int printType) {
        this.printType = printType;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getTRadical() {
        return TRadical;
    }

    public void setTRadical(String TRadical) {
        this.TRadical = TRadical;
    }

    public String getTVoleum() {
        return TVoleum;
    }

    public void setTVoleum(String TVoleum) {
        this.TVoleum = TVoleum;
    }

    public String getRadical() {
        return radical;
    }

    public void setRadical(String radical) {
        this.radical = radical;
    }

    public String getVoleum() {
        return voleum;
    }

    public void setVoleum(String voleum) {
        this.voleum = voleum;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getFstorageName() {
        return fstorageName;
    }

    public void setFstorageName(String fstorageName) {
        this.fstorageName = fstorageName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFBillNo() {
        return FBillNo;
    }

    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }

    public String getFInterID() {
        return FInterID;
    }

    public void setFInterID(String FInterID) {
        this.FInterID = FInterID;
    }

    public String getFMaker() {
        return FMaker;
    }

    public void setFMaker(String FMaker) {
        this.FMaker = FMaker;
    }

    public String getFRedBlue() {
        return FRedBlue;
    }

    public void setFRedBlue(String FRedBlue) {
        this.FRedBlue = FRedBlue;
    }

    @Override
    public String toString() {
        return "FirstCheck{" +
                "orderID='" + orderID + '\'' +
                ", productname='" + productname + '\'' +
                ", TRadical='" + TRadical + '\'' +
                ", TVoleum='" + TVoleum + '\'' +
                ", radical='" + radical + '\'' +
                ", voleum='" + voleum + '\'' +
                ", itemID='" + itemID + '\'' +
                ", model='" + model + '\'' +
                ", length='" + length + '\'' +
                ", diameter='" + diameter + '\'' +
                ", clientName='" + clientName + '\'' +
                ", fstorageName='" + fstorageName + '\'' +
                ", date='" + date + '\'' +
                ", FBillNo='" + FBillNo + '\'' +
                ", FInterID='" + FInterID + '\'' +
                ", FMaker='" + FMaker + '\'' +
                ", FRedBlue='" + FRedBlue + '\'' +
                '}';
    }
}
