package com.fangzuo.assist.Beans;

public class StorageCheckC {

    public String FNumber;
    public String FModel;
    public String FName;
    public String FRadical;
    public String FVolume;
    public String FStorageName;
    public String FWaveHouseName;

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

    public String getFStorageName() {
        return FStorageName;
    }

    public void setFStorageName(String FStorageName) {
        this.FStorageName = FStorageName;
    }

    public String getFWaveHouseName() {
        return FWaveHouseName;
    }

    public void setFWaveHouseName(String FWaveHouseName) {
        this.FWaveHouseName = FWaveHouseName;
    }

    @Override
    public String toString() {
        return "StorageCheckC{" +
                "FNumber='" + FNumber + '\'' +
                ", FModel='" + FModel + '\'' +
                ", FName='" + FName + '\'' +
                ", FRadical='" + FRadical + '\'' +
                ", FVolume='" + FVolume + '\'' +
                ", FStorageName='" + FStorageName + '\'' +
                ", FWaveHouseName='" + FWaveHouseName + '\'' +
                '}';
    }
}
