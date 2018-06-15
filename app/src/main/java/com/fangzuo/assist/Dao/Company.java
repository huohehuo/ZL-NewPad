package com.fangzuo.assist.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Company {

    public String FItemID;
    public String FNumber;
    public String FName;
    public String FDetail;


    @Generated(hash = 1415404518)
    public Company(String FItemID, String FNumber, String FName, String FDetail) {
        this.FItemID = FItemID;
        this.FNumber = FNumber;
        this.FName = FName;
        this.FDetail = FDetail;
    }


    @Generated(hash = 1096856789)
    public Company() {
    }


    @Override
    public String toString() {
        return "Company{" +
                "FItemID='" + FItemID + '\'' +
                ", FNumber='" + FNumber + '\'' +
                ", FName='" + FName + '\'' +
                ", FDetail='" + FDetail + '\'' +
                '}';
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


    public String getFName() {
        return this.FName;
    }


    public void setFName(String FName) {
        this.FName = FName;
    }


    public String getFDetail() {
        return this.FDetail;
    }


    public void setFDetail(String FDetail) {
        this.FDetail = FDetail;
    }
}
