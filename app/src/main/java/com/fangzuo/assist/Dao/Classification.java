package com.fangzuo.assist.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Classification {
    public String FItemID;
    public String FParentID;
    public String FName;
    public String FNumber;

    @Generated(hash = 1385435425)
    public Classification(String FItemID, String FParentID, String FName,
            String FNumber) {
        this.FItemID = FItemID;
        this.FParentID = FParentID;
        this.FName = FName;
        this.FNumber = FNumber;
    }

    @Generated(hash = 1739419247)
    public Classification() {
    }

    @Override
    public String toString() {
        return "Classification{" +
                "FItemID='" + FItemID + '\'' +
                ", FParentID='" + FParentID + '\'' +
                ", FName='" + FName + '\'' +
                ", FNumber='" + FNumber + '\'' +
                '}';
    }

    public String getFItemID() {
        return this.FItemID;
    }

    public void setFItemID(String FItemID) {
        this.FItemID = FItemID;
    }

    public String getFParentID() {
        return this.FParentID;
    }

    public void setFParentID(String FParentID) {
        this.FParentID = FParentID;
    }

    public String getFName() {
        return this.FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFNumber() {
        return this.FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }
}
