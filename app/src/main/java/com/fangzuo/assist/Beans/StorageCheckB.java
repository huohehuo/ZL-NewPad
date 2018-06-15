package com.fangzuo.assist.Beans;

public class StorageCheckB {

    public String FNumber;
    public String FModel;
    public String FName;
    public String FRadical;
    public String FVolume;
    public String FWaveHouseAll;

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }

    public String getFModel() {
        return FModel;
    }

    public void setFModel(String FModel) {
        this.FModel = FModel;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFRadical() {
        return FRadical;
    }

    public void setFRadical(String FRadical) {
        this.FRadical = FRadical;
    }

    public String getFVolume() {
        return FVolume;
    }

    public void setFVolume(String FVolume) {
        this.FVolume = FVolume;
    }

    public String getFWaveHouseAll() {
        return FWaveHouseAll;
    }

    public void setFWaveHouseAll(String FWaveHouseAll) {
        this.FWaveHouseAll = FWaveHouseAll;
    }

    @Override
    public String toString() {
        return "StorageCheckB{" +
                "FNumber='" + FNumber + '\'' +
                ", FModel='" + FModel + '\'' +
                ", FName='" + FName + '\'' +
                ", FRadical='" + FRadical + '\'' +
                ", FVolume='" + FVolume + '\'' +
                ", FWaveHouseAll='" + FWaveHouseAll + '\'' +
                '}';
    }
}
