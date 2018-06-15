package com.fangzuo.assist.Beans;

public class StorageCheckA {

    public String FName;
    public String FRadical;
    public String FVolume;
    public String FStorageId;

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

    public String getFStorageId() {
        return FStorageId;
    }

    public void setFStorageId(String FStorageId) {
        this.FStorageId = FStorageId;
    }

    @Override
    public String toString() {
        return "StorageCheckA{" +
                "FName='" + FName + '\'' +
                ", FRadical='" + FRadical + '\'' +
                ", FVolume='" + FVolume + '\'' +
                ", FStorageId='" + FStorageId + '\'' +
                '}';
    }
}
