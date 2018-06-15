package com.fangzuo.assist.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 王璐阳 on 2018/3/28.
 *              短材出库的单据列表的暂存数据
 */
@Entity
public class TempDetail {
    public long FOrderId;
    @Id
    public String FIndex;
    public int diameter;
    public String length;
    public String FBarcode;
    public String FStorageId;
    public String FStorage;
    public String FProductCode;
    public String FRedBlue;
    public String FProductId;
    public String FProductName;
    public String FStandard;
    public String FUnitId;
    public String FUnit;
    public String FQuantity;
    public String FTaxUnitPrice;
    public String FDiscount;
    public String FIdentity;
    public String FDateDelivery;
    public String FBatch;
    public String FBillNo;
    public String FRate;
    public String FoutStoragename;
    public String FoutStorageid;
    public String Foutwavehouseid;
    public String Foutwavehousename;
    public String FEntryID;
    public String FPositionId;
    public String FPosition;
    public String FInterID;
    public String outStorage;
    public String radical;
    public String num;
    public String inStorage;
    public double unitrate;
    public int activity;
    public String model;
    public String clientName;


    @Generated(hash = 1253300200)
    public TempDetail(long FOrderId, String FIndex, int diameter, String length,
            String FBarcode, String FStorageId, String FStorage,
            String FProductCode, String FRedBlue, String FProductId,
            String FProductName, String FStandard, String FUnitId, String FUnit,
            String FQuantity, String FTaxUnitPrice, String FDiscount,
            String FIdentity, String FDateDelivery, String FBatch, String FBillNo,
            String FRate, String FoutStoragename, String FoutStorageid,
            String Foutwavehouseid, String Foutwavehousename, String FEntryID,
            String FPositionId, String FPosition, String FInterID,
            String outStorage, String radical, String num, String inStorage,
            double unitrate, int activity, String model, String clientName) {
        this.FOrderId = FOrderId;
        this.FIndex = FIndex;
        this.diameter = diameter;
        this.length = length;
        this.FBarcode = FBarcode;
        this.FStorageId = FStorageId;
        this.FStorage = FStorage;
        this.FProductCode = FProductCode;
        this.FRedBlue = FRedBlue;
        this.FProductId = FProductId;
        this.FProductName = FProductName;
        this.FStandard = FStandard;
        this.FUnitId = FUnitId;
        this.FUnit = FUnit;
        this.FQuantity = FQuantity;
        this.FTaxUnitPrice = FTaxUnitPrice;
        this.FDiscount = FDiscount;
        this.FIdentity = FIdentity;
        this.FDateDelivery = FDateDelivery;
        this.FBatch = FBatch;
        this.FBillNo = FBillNo;
        this.FRate = FRate;
        this.FoutStoragename = FoutStoragename;
        this.FoutStorageid = FoutStorageid;
        this.Foutwavehouseid = Foutwavehouseid;
        this.Foutwavehousename = Foutwavehousename;
        this.FEntryID = FEntryID;
        this.FPositionId = FPositionId;
        this.FPosition = FPosition;
        this.FInterID = FInterID;
        this.outStorage = outStorage;
        this.radical = radical;
        this.num = num;
        this.inStorage = inStorage;
        this.unitrate = unitrate;
        this.activity = activity;
        this.model = model;
        this.clientName = clientName;
    }

    @Generated(hash = 24535599)
    public TempDetail() {
    }


    @Override
    public String toString() {
        return "TempDetail{" +
                "FOrderId=" + FOrderId +
                ", FIndex='" + FIndex + '\'' +
                ", diameter='" + diameter + '\'' +
                ", length='" + length + '\'' +
                ", FBarcode='" + FBarcode + '\'' +
                ", FStorageId='" + FStorageId + '\'' +
                ", FStorage='" + FStorage + '\'' +
                ", FProductCode='" + FProductCode + '\'' +
                ", FRedBlue='" + FRedBlue + '\'' +
                ", FProductId='" + FProductId + '\'' +
                ", FProductName='" + FProductName + '\'' +
                ", FStandard='" + FStandard + '\'' +
                ", FUnitId='" + FUnitId + '\'' +
                ", FUnit='" + FUnit + '\'' +
                ", FQuantity='" + FQuantity + '\'' +
                ", FTaxUnitPrice='" + FTaxUnitPrice + '\'' +
                ", FDiscount='" + FDiscount + '\'' +
                ", FIdentity='" + FIdentity + '\'' +
                ", FDateDelivery='" + FDateDelivery + '\'' +
                ", FBatch='" + FBatch + '\'' +
                ", FBillNo='" + FBillNo + '\'' +
                ", FRate='" + FRate + '\'' +
                ", FoutStoragename='" + FoutStoragename + '\'' +
                ", FoutStorageid='" + FoutStorageid + '\'' +
                ", Foutwavehouseid='" + Foutwavehouseid + '\'' +
                ", Foutwavehousename='" + Foutwavehousename + '\'' +
                ", FEntryID='" + FEntryID + '\'' +
                ", FPositionId='" + FPositionId + '\'' +
                ", FPosition='" + FPosition + '\'' +
                ", FInterID='" + FInterID + '\'' +
                ", outStorage='" + outStorage + '\'' +
                ", radical='" + radical + '\'' +
                ", num='" + num + '\'' +
                ", inStorage='" + inStorage + '\'' +
                ", unitrate=" + unitrate +
                ", activity=" + activity +
                ", model='" + model + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }

    public long getFOrderId() {
        return this.FOrderId;
    }

    public void setFOrderId(long FOrderId) {
        this.FOrderId = FOrderId;
    }

    public String getFIndex() {
        return this.FIndex;
    }

    public void setFIndex(String FIndex) {
        this.FIndex = FIndex;
    }

    public int getDiameter() {
        return this.diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getFBarcode() {
        return this.FBarcode;
    }

    public void setFBarcode(String FBarcode) {
        this.FBarcode = FBarcode;
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

    public String getFStandard() {
        return this.FStandard;
    }

    public void setFStandard(String FStandard) {
        this.FStandard = FStandard;
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

    public String getFQuantity() {
        return this.FQuantity;
    }

    public void setFQuantity(String FQuantity) {
        this.FQuantity = FQuantity;
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

    public String getFIdentity() {
        return this.FIdentity;
    }

    public void setFIdentity(String FIdentity) {
        this.FIdentity = FIdentity;
    }

    public String getFDateDelivery() {
        return this.FDateDelivery;
    }

    public void setFDateDelivery(String FDateDelivery) {
        this.FDateDelivery = FDateDelivery;
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

    public String getFRate() {
        return this.FRate;
    }

    public void setFRate(String FRate) {
        this.FRate = FRate;
    }

    public String getFoutStoragename() {
        return this.FoutStoragename;
    }

    public void setFoutStoragename(String FoutStoragename) {
        this.FoutStoragename = FoutStoragename;
    }

    public String getFoutStorageid() {
        return this.FoutStorageid;
    }

    public void setFoutStorageid(String FoutStorageid) {
        this.FoutStorageid = FoutStorageid;
    }

    public String getFoutwavehouseid() {
        return this.Foutwavehouseid;
    }

    public void setFoutwavehouseid(String Foutwavehouseid) {
        this.Foutwavehouseid = Foutwavehouseid;
    }

    public String getFoutwavehousename() {
        return this.Foutwavehousename;
    }

    public void setFoutwavehousename(String Foutwavehousename) {
        this.Foutwavehousename = Foutwavehousename;
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

    public String getOutStorage() {
        return this.outStorage;
    }

    public void setOutStorage(String outStorage) {
        this.outStorage = outStorage;
    }

    public String getRadical() {
        return this.radical;
    }

    public void setRadical(String radical) {
        this.radical = radical;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getInStorage() {
        return this.inStorage;
    }

    public void setInStorage(String inStorage) {
        this.inStorage = inStorage;
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


}
