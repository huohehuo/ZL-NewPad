package com.fangzuo.assist.Beans;

public class AccountCheckData {
    public String FBillNoData;      //单据日期
    public String FAbstract;        //摘要
    public String FOrderNo;          //订单号
    public String FCarNo;            //车号
    public String FStorage;            //仓库
    public String FWaveHouse;            //仓位
    public String FProductName;            //产品名称
    public String FModel;            //规格型号
    public String FAuxNum;            //辅助数量
    public String FRealNum;            //实发数量
    public String FSalePrice;            //销售单价
    public String FHasTax;            //是否含税





    public String FShouldMoney;     //应收金额
    public String FGiveMoney;       //收款金额
    public String FFirstMoney;       //期初金额
    public String FLastMoney;       //期末金额

    public String backDateType;       //返回类型，1、2，用于决定前端显示的数据


    @Override
    public String toString() {
        return "AccountCheckData{" +
                "FBillNoData='" + FBillNoData + '\'' +
                ", FAbstract='" + FAbstract + '\'' +
                ", FOrderNo='" + FOrderNo + '\'' +
                ", FCarNo='" + FCarNo + '\'' +
                ", FStorage='" + FStorage + '\'' +
                ", FWaveHouse='" + FWaveHouse + '\'' +
                ", FProductName='" + FProductName + '\'' +
                ", FModel='" + FModel + '\'' +
                ", FAuxNum='" + FAuxNum + '\'' +
                ", FRealNum='" + FRealNum + '\'' +
                ", FSalePrice='" + FSalePrice + '\'' +
                ", FHasTax='" + FHasTax + '\'' +
                ", FShouldMoney='" + FShouldMoney + '\'' +
                ", FGiveMoney='" + FGiveMoney + '\'' +
                ", FFirstMoney='" + FFirstMoney + '\'' +
                ", FLastMoney='" + FLastMoney + '\'' +
                ", backDateType='" + backDateType + '\'' +
                '}';
    }

    public String getFBillNoData() {
        return FBillNoData;
    }

    public void setFBillNoData(String FBillNoData) {
        this.FBillNoData = FBillNoData;
    }

    public String getFAbstract() {
        return FAbstract;
    }

    public void setFAbstract(String FAbstract) {
        this.FAbstract = FAbstract;
    }

    public String getFOrderNo() {
        return FOrderNo;
    }

    public void setFOrderNo(String FOrderNo) {
        this.FOrderNo = FOrderNo;
    }

    public String getFCarNo() {
        return FCarNo;
    }

    public void setFCarNo(String FCarNo) {
        this.FCarNo = FCarNo;
    }

    public String getFStorage() {
        return FStorage;
    }

    public void setFStorage(String FStorage) {
        this.FStorage = FStorage;
    }

    public String getFWaveHouse() {
        return FWaveHouse;
    }

    public void setFWaveHouse(String FWaveHouse) {
        this.FWaveHouse = FWaveHouse;
    }

    public String getFProductName() {
        return FProductName;
    }

    public void setFProductName(String FProductName) {
        this.FProductName = FProductName;
    }

    public String getFModel() {
        return FModel;
    }

    public void setFModel(String FModel) {
        this.FModel = FModel;
    }

    public String getFAuxNum() {
        return FAuxNum;
    }

    public void setFAuxNum(String FAuxNum) {
        this.FAuxNum = FAuxNum;
    }

    public String getFRealNum() {
        return FRealNum;
    }

    public void setFRealNum(String FRealNum) {
        this.FRealNum = FRealNum;
    }

    public String getFSalePrice() {
        return FSalePrice;
    }

    public void setFSalePrice(String FSalePrice) {
        this.FSalePrice = FSalePrice;
    }

    public String getFHasTax() {
        return FHasTax;
    }

    public void setFHasTax(String FHasTax) {
        this.FHasTax = FHasTax;
    }

    public String getFShouldMoney() {
        return FShouldMoney;
    }

    public void setFShouldMoney(String FShouldMoney) {
        this.FShouldMoney = FShouldMoney;
    }

    public String getFGiveMoney() {
        return FGiveMoney;
    }

    public void setFGiveMoney(String FGiveMoney) {
        this.FGiveMoney = FGiveMoney;
    }

    public String getFFirstMoney() {
        return FFirstMoney;
    }

    public void setFFirstMoney(String FFirstMoney) {
        this.FFirstMoney = FFirstMoney;
    }

    public String getFLastMoney() {
        return FLastMoney;
    }

    public void setFLastMoney(String FLastMoney) {
        this.FLastMoney = FLastMoney;
    }

    public String getBackDateType() {
        return backDateType;
    }

    public void setBackDateType(String backDateType) {
        this.backDateType = backDateType;
    }
}
