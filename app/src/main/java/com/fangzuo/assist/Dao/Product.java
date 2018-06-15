package com.fangzuo.assist.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by NB on 2017/7/26.
 *
 * Error:Execution failed for task ':app:greendao'.
 > Found 1 problem(s) parsing "G:\project\fz\android\通用版androidPDA\app\src\main\java\com\fangzuo\assist\Dao\Product.java". First problem:
 Pb(96) The serializable class Product does not declare a static final serialVersionUID field of type long (536871008 at line 12).
 Run gradle with --info for more details.
 */
@Entity
public class Product{
    public String FItemID;
    public String FNumber;
    public String FModel;
    public String FName;
    public String FFullName;
    public String FUnitID;
    public String FUnitGroupID;
    public String FDefaultLoc;
    public String FProfitRate;
    public String FTaxRate;
    public String FOrderPrice;
    public String FSalePrice;
    public String FPlanPrice;
    public String FBarcode;
    public String FSPID;
    public String FBatchManager;
    public String FM;
    public String FMindiameter;
    public String FMaxdiameter;
    public String FISKFPeriod;
    public String FKFPeriod;
    @Generated(hash = 502608520)
    public Product(String FItemID, String FNumber, String FModel, String FName, String FFullName, String FUnitID, String FUnitGroupID,
            String FDefaultLoc, String FProfitRate, String FTaxRate, String FOrderPrice, String FSalePrice, String FPlanPrice, String FBarcode,
            String FSPID, String FBatchManager, String FM, String FMindiameter, String FMaxdiameter, String FISKFPeriod, String FKFPeriod) {
        this.FItemID = FItemID;
        this.FNumber = FNumber;
        this.FModel = FModel;
        this.FName = FName;
        this.FFullName = FFullName;
        this.FUnitID = FUnitID;
        this.FUnitGroupID = FUnitGroupID;
        this.FDefaultLoc = FDefaultLoc;
        this.FProfitRate = FProfitRate;
        this.FTaxRate = FTaxRate;
        this.FOrderPrice = FOrderPrice;
        this.FSalePrice = FSalePrice;
        this.FPlanPrice = FPlanPrice;
        this.FBarcode = FBarcode;
        this.FSPID = FSPID;
        this.FBatchManager = FBatchManager;
        this.FM = FM;
        this.FMindiameter = FMindiameter;
        this.FMaxdiameter = FMaxdiameter;
        this.FISKFPeriod = FISKFPeriod;
        this.FKFPeriod = FKFPeriod;
    }
    @Generated(hash = 1890278724)
    public Product() {
    }
    public String getFItemID() {
        return this.FItemID;
    }
    public void setFItemID(String FItemID) {
        this.FItemID = FItemID;
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
    public String getFName() {
        return this.FName;
    }
    public void setFName(String FName) {
        this.FName = FName;
    }
    public String getFFullName() {
        return this.FFullName;
    }
    public void setFFullName(String FFullName) {
        this.FFullName = FFullName;
    }
    public String getFUnitID() {
        return this.FUnitID;
    }
    public void setFUnitID(String FUnitID) {
        this.FUnitID = FUnitID;
    }
    public String getFUnitGroupID() {
        return this.FUnitGroupID;
    }
    public void setFUnitGroupID(String FUnitGroupID) {
        this.FUnitGroupID = FUnitGroupID;
    }
    public String getFDefaultLoc() {
        return this.FDefaultLoc;
    }
    public void setFDefaultLoc(String FDefaultLoc) {
        this.FDefaultLoc = FDefaultLoc;
    }
    public String getFProfitRate() {
        return this.FProfitRate;
    }
    public void setFProfitRate(String FProfitRate) {
        this.FProfitRate = FProfitRate;
    }
    public String getFTaxRate() {
        return this.FTaxRate;
    }
    public void setFTaxRate(String FTaxRate) {
        this.FTaxRate = FTaxRate;
    }
    public String getFOrderPrice() {
        return this.FOrderPrice;
    }
    public void setFOrderPrice(String FOrderPrice) {
        this.FOrderPrice = FOrderPrice;
    }
    public String getFSalePrice() {
        return this.FSalePrice;
    }
    public void setFSalePrice(String FSalePrice) {
        this.FSalePrice = FSalePrice;
    }
    public String getFPlanPrice() {
        return this.FPlanPrice;
    }
    public void setFPlanPrice(String FPlanPrice) {
        this.FPlanPrice = FPlanPrice;
    }
    public String getFBarcode() {
        return this.FBarcode;
    }
    public void setFBarcode(String FBarcode) {
        this.FBarcode = FBarcode;
    }
    public String getFSPID() {
        return this.FSPID;
    }
    public void setFSPID(String FSPID) {
        this.FSPID = FSPID;
    }
    public String getFBatchManager() {
        return this.FBatchManager;
    }
    public void setFBatchManager(String FBatchManager) {
        this.FBatchManager = FBatchManager;
    }
    public String getFM() {
        return this.FM;
    }
    public void setFM(String FM) {
        this.FM = FM;
    }
    public String getFMindiameter() {
        return this.FMindiameter;
    }
    public void setFMindiameter(String FMindiameter) {
        this.FMindiameter = FMindiameter;
    }
    public String getFMaxdiameter() {
        return this.FMaxdiameter;
    }
    public void setFMaxdiameter(String FMaxdiameter) {
        this.FMaxdiameter = FMaxdiameter;
    }
    public String getFISKFPeriod() {
        return this.FISKFPeriod;
    }
    public void setFISKFPeriod(String FISKFPeriod) {
        this.FISKFPeriod = FISKFPeriod;
    }
    public String getFKFPeriod() {
        return this.FKFPeriod;
    }
    public void setFKFPeriod(String FKFPeriod) {
        this.FKFPeriod = FKFPeriod;
    }

    @Override
    public String toString() {
        return "Product{" +
                "FItemID='" + FItemID + '\'' +
                ", FNumber='" + FNumber + '\'' +
                ", FModel='" + FModel + '\'' +
                ", FName='" + FName + '\'' +
                ", FFullName='" + FFullName + '\'' +
                ", FUnitID='" + FUnitID + '\'' +
                ", FUnitGroupID='" + FUnitGroupID + '\'' +
                ", FDefaultLoc='" + FDefaultLoc + '\'' +
                ", FProfitRate='" + FProfitRate + '\'' +
                ", FTaxRate='" + FTaxRate + '\'' +
                ", FOrderPrice='" + FOrderPrice + '\'' +
                ", FSalePrice='" + FSalePrice + '\'' +
                ", FPlanPrice='" + FPlanPrice + '\'' +
                ", FBarcode='" + FBarcode + '\'' +
                ", FSPID='" + FSPID + '\'' +
                ", FBatchManager='" + FBatchManager + '\'' +
                ", FM='" + FM + '\'' +
                ", FMindiameter='" + FMindiameter + '\'' +
                ", FMaxdiameter='" + FMaxdiameter + '\'' +
                ", FISKFPeriod='" + FISKFPeriod + '\'' +
                ", FKFPeriod='" + FKFPeriod + '\'' +
                '}';
    }
}
