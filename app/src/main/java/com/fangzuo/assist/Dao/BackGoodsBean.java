package com.fangzuo.assist.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class BackGoodsBean {
    @Id
    public String FIndex;
    public long FOrderId;           ////订单编号
    public int diameter;             //径直
    public String FQuantity;            //体积
    public String radical;              //根数
    public String FIdentity;            //报数
    public String length;
    public String FStorageId;
    public String FStorage;
    public String FProductCode;
    public String FRedBlue;
    public String FProductId;
    public String FProductName;
    public String FUnitId;
    public String FUnit;
    public String FTaxUnitPrice;
    public String FDiscount;
    public String FBatch;
    public String FBillNo;
    public String FEntryID;
    public String FPositionId;
    public String FPosition;
    public String FInterID;
    public double unitrate;
    public int activity;
    public String model;
    public String clientName;
    public String FRemark;          //备注，（注意，表头也有一个同名，没办法
    @Generated(hash = 1840026574)
    public BackGoodsBean(String FIndex, long FOrderId, int diameter,
            String FQuantity, String radical, String FIdentity, String length,
            String FStorageId, String FStorage, String FProductCode,
            String FRedBlue, String FProductId, String FProductName, String FUnitId,
            String FUnit, String FTaxUnitPrice, String FDiscount, String FBatch,
            String FBillNo, String FEntryID, String FPositionId, String FPosition,
            String FInterID, double unitrate, int activity, String model,
            String clientName, String FRemark) {
        this.FIndex = FIndex;
        this.FOrderId = FOrderId;
        this.diameter = diameter;
        this.FQuantity = FQuantity;
        this.radical = radical;
        this.FIdentity = FIdentity;
        this.length = length;
        this.FStorageId = FStorageId;
        this.FStorage = FStorage;
        this.FProductCode = FProductCode;
        this.FRedBlue = FRedBlue;
        this.FProductId = FProductId;
        this.FProductName = FProductName;
        this.FUnitId = FUnitId;
        this.FUnit = FUnit;
        this.FTaxUnitPrice = FTaxUnitPrice;
        this.FDiscount = FDiscount;
        this.FBatch = FBatch;
        this.FBillNo = FBillNo;
        this.FEntryID = FEntryID;
        this.FPositionId = FPositionId;
        this.FPosition = FPosition;
        this.FInterID = FInterID;
        this.unitrate = unitrate;
        this.activity = activity;
        this.model = model;
        this.clientName = clientName;
        this.FRemark = FRemark;
    }
    @Generated(hash = 1688086162)
    public BackGoodsBean() {
    }
    public long getFOrderId() {
        return this.FOrderId;
    }
    public void setFOrderId(long FOrderId) {
        this.FOrderId = FOrderId;
    }
    public int getDiameter() {
        return this.diameter;
    }
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
    public String getFQuantity() {
        return this.FQuantity;
    }
    public void setFQuantity(String FQuantity) {
        this.FQuantity = FQuantity;
    }
    public String getRadical() {
        return this.radical;
    }
    public void setRadical(String radical) {
        this.radical = radical;
    }
    public String getFIdentity() {
        return this.FIdentity;
    }
    public void setFIdentity(String FIdentity) {
        this.FIdentity = FIdentity;
    }
    public String getLength() {
        return this.length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getFStorageId() {
        return this.FStorageId;
    }
    public void setFStorageId(String FStorageId) {
        this.FStorageId = FStorageId;
    }
    public String getFStorage() {
        return this.FStorage;
    }
    public void setFStorage(String FStorage) {
        this.FStorage = FStorage;
    }
    public String getFProductCode() {
        return this.FProductCode;
    }
    public void setFProductCode(String FProductCode) {
        this.FProductCode = FProductCode;
    }
    public String getFRedBlue() {
        return this.FRedBlue;
    }
    public void setFRedBlue(String FRedBlue) {
        this.FRedBlue = FRedBlue;
    }
    public String getFProductId() {
        return this.FProductId;
    }
    public void setFProductId(String FProductId) {
        this.FProductId = FProductId;
    }
    public String getFProductName() {
        return this.FProductName;
    }
    public void setFProductName(String FProductName) {
        this.FProductName = FProductName;
    }
    public String getFUnitId() {
        return this.FUnitId;
    }
    public void setFUnitId(String FUnitId) {
        this.FUnitId = FUnitId;
    }
    public String getFUnit() {
        return this.FUnit;
    }
    public void setFUnit(String FUnit) {
        this.FUnit = FUnit;
    }
    public String getFTaxUnitPrice() {
        return this.FTaxUnitPrice;
    }
    public void setFTaxUnitPrice(String FTaxUnitPrice) {
        this.FTaxUnitPrice = FTaxUnitPrice;
    }
    public String getFDiscount() {
        return this.FDiscount;
    }
    public void setFDiscount(String FDiscount) {
        this.FDiscount = FDiscount;
    }
    public String getFBatch() {
        return this.FBatch;
    }
    public void setFBatch(String FBatch) {
        this.FBatch = FBatch;
    }
    public String getFBillNo() {
        return this.FBillNo;
    }
    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }
    public String getFEntryID() {
        return this.FEntryID;
    }
    public void setFEntryID(String FEntryID) {
        this.FEntryID = FEntryID;
    }
    public String getFPositionId() {
        return this.FPositionId;
    }
    public void setFPositionId(String FPositionId) {
        this.FPositionId = FPositionId;
    }
    public String getFPosition() {
        return this.FPosition;
    }
    public void setFPosition(String FPosition) {
        this.FPosition = FPosition;
    }
    public String getFInterID() {
        return this.FInterID;
    }
    public void setFInterID(String FInterID) {
        this.FInterID = FInterID;
    }
    public double getUnitrate() {
        return this.unitrate;
    }
    public void setUnitrate(double unitrate) {
        this.unitrate = unitrate;
    }
    public int getActivity() {
        return this.activity;
    }
    public void setActivity(int activity) {
        this.activity = activity;
    }
    public String getModel() {
        return this.model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getClientName() {
        return this.clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getFRemark() {
        return this.FRemark;
    }
    public void setFRemark(String FRemark) {
        this.FRemark = FRemark;
    }

    @Override
    public String toString() {
        return "BackGoodsBean{" +
                "FOrderId=" + FOrderId +
                ", diameter=" + diameter +
                ", FQuantity='" + FQuantity + '\'' +
                ", radical='" + radical + '\'' +
                ", FIdentity='" + FIdentity + '\'' +
                ", length='" + length + '\'' +
                ", FStorageId='" + FStorageId + '\'' +
                ", FStorage='" + FStorage + '\'' +
                ", FProductCode='" + FProductCode + '\'' +
                ", FRedBlue='" + FRedBlue + '\'' +
                ", FProductId='" + FProductId + '\'' +
                ", FProductName='" + FProductName + '\'' +
                ", FUnitId='" + FUnitId + '\'' +
                ", FUnit='" + FUnit + '\'' +
                ", FTaxUnitPrice='" + FTaxUnitPrice + '\'' +
                ", FDiscount='" + FDiscount + '\'' +
                ", FBatch='" + FBatch + '\'' +
                ", FBillNo='" + FBillNo + '\'' +
                ", FEntryID='" + FEntryID + '\'' +
                ", FPositionId='" + FPositionId + '\'' +
                ", FPosition='" + FPosition + '\'' +
                ", FInterID='" + FInterID + '\'' +
                ", unitrate=" + unitrate +
                ", activity=" + activity +
                ", model='" + model + '\'' +
                ", clientName='" + clientName + '\'' +
                ", FRemark='" + FRemark + '\'' +
                '}';
    }
    public String getFIndex() {
        return this.FIndex;
    }
    public void setFIndex(String FIndex) {
        this.FIndex = FIndex;
    }
}
