package com.fangzuo.assist.Beans;

import java.io.Serializable;

/**
 * Created by 王璐阳 on 2018/4/10.
 */

public class RegisterBean {
    public String Code;
    public String LastDate;
    public String PdaNo;
    public String PdaCompany;

    @Override
    public String toString() {
        return "RegisterBean{" +
                "Code='" + Code + '\'' +
                ", LastDate='" + LastDate + '\'' +
                ", PdaNo='" + PdaNo + '\'' +
                ", PadCompany='" + PdaCompany + '\'' +
                '}';
    }
}
