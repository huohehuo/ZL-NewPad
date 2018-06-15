package com.fangzuo.assist.Dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ChangePrice {
    @Id(autoincrement = true)
    private Long _id;


    public String FBillNo;
    public String FDate;
    public String FModel;
    public String FNumber;
    public String FName;
    public String FUnit;
    public String FPriceOld;
    public String FPriceChange;
    public String FSupplier;
    public String FSpNo;
    public String FInterID;     //明细ID
    public String FEntryID;     //
    public String FItemID;      //商品ID
    public String FHasTax;      //是否含税 ，


    @Generated(hash = 1118439941)
    public ChangePrice(Long _id, String FBillNo, String FDate, String FModel,
            String FNumber, String FName, String FUnit, String FPriceOld,
            String FPriceChange, String FSupplier, String FSpNo, String FInterID,
            String FEntryID, String FItemID, String FHasTax) {
        this._id = _id;
        this.FBillNo = FBillNo;
        this.FDate = FDate;
        this.FModel = FModel;
        this.FNumber = FNumber;
        this.FName = FName;
        this.FUnit = FUnit;
        this.FPriceOld = FPriceOld;
        this.FPriceChange = FPriceChange;
        this.FSupplier = FSupplier;
        this.FSpNo = FSpNo;
        this.FInterID = FInterID;
        this.FEntryID = FEntryID;
        this.FItemID = FItemID;
        this.FHasTax = FHasTax;
    }


    @Generated(hash = 1189524179)
    public ChangePrice() {
    }


    @Override
    public String toString() {
        return "ChangePrice{" +
                "_id=" + _id +
                ", FBillNo='" + FBillNo + '\'' +
                ", FDate='" + FDate + '\'' +
                ", FModel='" + FModel + '\'' +
                ", FNumber='" + FNumber + '\'' +
                ", FName='" + FName + '\'' +
                ", FUnit='" + FUnit + '\'' +
                ", FPriceOld='" + FPriceOld + '\'' +
                ", FPriceChange='" + FPriceChange + '\'' +
                ", FSupplier='" + FSupplier + '\'' +
                ", FSpNo='" + FSpNo + '\'' +
                ", FInterID='" + FInterID + '\'' +
                ", FEntryID='" + FEntryID + '\'' +
                ", FItemID='" + FItemID + '\'' +
                ", FHasTax='" + FHasTax + '\'' +
                '}';
    }


    public Long get_id() {
        return this._id;
    }


    public void set_id(Long _id) {
        this._id = _id;
    }


    public String getFBillNo() {
        return this.FBillNo;
    }


    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }


    public String getFDate() {
        return this.FDate;
    }


    public void setFDate(String FDate) {
        this.FDate = FDate;
    }


    public String getFModel() {
        return this.FModel;
    }


    public void setFModel(String FModel) {
        this.FModel = FModel;
    }


    public String getFNumber() {
        return this.FNumber;
    }


    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }


    public String getFName() {
        return this.FName;
    }


    public void setFName(String FName) {
        this.FName = FName;
    }


    public String getFUnit() {
        return this.FUnit;
    }


    public void setFUnit(String FUnit) {
        this.FUnit = FUnit;
    }


    public String getFPriceOld() {
        return this.FPriceOld;
    }


    public void setFPriceOld(String FPriceOld) {
        this.FPriceOld = FPriceOld;
    }


    public String getFPriceChange() {
        return this.FPriceChange;
    }


    public void setFPriceChange(String FPriceChange) {
        this.FPriceChange = FPriceChange;
    }


    public String getFSupplier() {
        return this.FSupplier;
    }


    public void setFSupplier(String FSupplier) {
        this.FSupplier = FSupplier;
    }


    public String getFSpNo() {
        return this.FSpNo;
    }


    public void setFSpNo(String FSpNo) {
        this.FSpNo = FSpNo;
    }


    public String getFInterID() {
        return this.FInterID;
    }


    public void setFInterID(String FInterID) {
        this.FInterID = FInterID;
    }


    public String getFEntryID() {
        return this.FEntryID;
    }


    public void setFEntryID(String FEntryID) {
        this.FEntryID = FEntryID;
    }


    public String getFItemID() {
        return this.FItemID;
    }


    public void setFItemID(String FItemID) {
        this.FItemID = FItemID;
    }


    public String getFHasTax() {
        return this.FHasTax;
    }


    public void setFHasTax(String FHasTax) {
        this.FHasTax = FHasTax;
    }

}
