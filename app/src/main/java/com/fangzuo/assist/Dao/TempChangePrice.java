package com.fangzuo.assist.Dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class TempChangePrice {
    @Id(autoincrement = true)
    private Long _id;
    //1.FItemID（商品id）2.FInterID(明细ID)3.修改价4.FEntryID
    public String FItemID;          //商品ID
    public String FInterID;         //明细ID
    public String FChangePrice;
    public String FEntryID;         //排序
    //--------------------------------------------
    public String FDate;
    public String FName;
    public String FPriceOld;
    public String FSupplier;
    public String FNumber;
    public String FModel;
    public String FBillNo;
    public String FSpNo;
    public String ChangeDate;
    public String hasTax;
    @Generated(hash = 521912577)
    public TempChangePrice(Long _id, String FItemID, String FInterID,
            String FChangePrice, String FEntryID, String FDate, String FName,
            String FPriceOld, String FSupplier, String FNumber, String FModel,
            String FBillNo, String FSpNo, String ChangeDate, String hasTax) {
        this._id = _id;
        this.FItemID = FItemID;
        this.FInterID = FInterID;
        this.FChangePrice = FChangePrice;
        this.FEntryID = FEntryID;
        this.FDate = FDate;
        this.FName = FName;
        this.FPriceOld = FPriceOld;
        this.FSupplier = FSupplier;
        this.FNumber = FNumber;
        this.FModel = FModel;
        this.FBillNo = FBillNo;
        this.FSpNo = FSpNo;
        this.ChangeDate = ChangeDate;
        this.hasTax = hasTax;
    }
    @Generated(hash = 8627801)
    public TempChangePrice() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getFItemID() {
        return this.FItemID;
    }
    public void setFItemID(String FItemID) {
        this.FItemID = FItemID;
    }
    public String getFInterID() {
        return this.FInterID;
    }
    public void setFInterID(String FInterID) {
        this.FInterID = FInterID;
    }
    public String getFChangePrice() {
        return this.FChangePrice;
    }
    public void setFChangePrice(String FChangePrice) {
        this.FChangePrice = FChangePrice;
    }
    public String getFEntryID() {
        return this.FEntryID;
    }
    public void setFEntryID(String FEntryID) {
        this.FEntryID = FEntryID;
    }
    public String getFDate() {
        return this.FDate;
    }
    public void setFDate(String FDate) {
        this.FDate = FDate;
    }
    public String getFName() {
        return this.FName;
    }
    public void setFName(String FName) {
        this.FName = FName;
    }
    public String getFPriceOld() {
        return this.FPriceOld;
    }
    public void setFPriceOld(String FPriceOld) {
        this.FPriceOld = FPriceOld;
    }
    public String getFSupplier() {
        return this.FSupplier;
    }
    public void setFSupplier(String FSupplier) {
        this.FSupplier = FSupplier;
    }
    public String getFNumber() {
        return this.FNumber;
    }
    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }
    public String getFModel() {
        return this.FModel;
    }
    public void setFModel(String FModel) {
        this.FModel = FModel;
    }
    public String getFBillNo() {
        return this.FBillNo;
    }
    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }
    public String getFSpNo() {
        return this.FSpNo;
    }
    public void setFSpNo(String FSpNo) {
        this.FSpNo = FSpNo;
    }
    public String getChangeDate() {
        return this.ChangeDate;
    }
    public void setChangeDate(String ChangeDate) {
        this.ChangeDate = ChangeDate;
    }
    public String getHasTax() {
        return this.hasTax;
    }
    public void setHasTax(String hasTax) {
        this.hasTax = hasTax;
    }
   
   
}
