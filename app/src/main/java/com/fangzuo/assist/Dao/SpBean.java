package com.fangzuo.assist.Dao;


public class SpBean {
    public String spName;
    public String spId;

    public SpBean(String spId,String billNo) {
        this.spName = billNo;
        this.spId = spId;
    }

    @Override
    public String toString() {
        return "SpBean{" +
                "spName='" + spName + '\'' +
                "spId='" + spId + '\'' +
                '}';
    }
}
