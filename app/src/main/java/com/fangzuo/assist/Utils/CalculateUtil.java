package com.fangzuo.assist.Utils;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by 王璐阳 on 2018/4/12.
 */

public class CalculateUtil {

    public static Double getVoleum(String length,String diameter){
        Double num = 0.0;
        Double l = Double.parseDouble(length==null||length.equals("")?"0":length);
        Double d = Double.parseDouble(diameter==null||diameter.equals("")?"0":diameter);
        if(l>=10.1){
            num = 0.8*l*(Math.pow(d+(0.5*l),2)/10000);
        }else if(l<10.1&&d<14){
            num = 0.7854*l*(Math.pow(d+(0.45*l)+0.2,2)/10000);
        }else if(l<10.1&&d>=14){
            Double sl = Math.pow(l,2);//长度的平方
            Double l14 = Math.pow(14-l,2);//14-l的平方
            num = 0.7854*l*(Math.pow(d+0.5*l+0.005*sl+0.000125*l*l14*(d-10),2)/10000);
        }
        DecimalFormat df = new DecimalFormat("#0.000");
        String str = df.format(num);
        Log.e("CalculateUtil",str);
        return Double.parseDouble(str);
    }

    //短材加减算法--球体积
    public static Double getVoleumdL(String length,String diameter){
        Double num = 0.0;
        Double l = Double.parseDouble(length==null||length.equals("")?"0":length);
        Double d = Double.parseDouble(diameter==null||diameter.equals("")?"0":diameter);
        if(l>=10.2){
            num = 0.8*l*(Math.pow(d+(0.5*l),2)/10000);
        }else if(l<10.2&&d<14){
            num = 0.7854*l*(Math.pow(d+(0.45*l)+0.2,2)/10000);
        }else if(l<10.2&&d>=14){
            Double sl = Math.pow(l,2);//长度的平方
            Double l14 = Math.pow(14-l,2);//14-l的平方
            num = 0.7854*l*(Math.pow(d+0.5*l+0.005*sl+0.000125*l*l14*(d-10),2)/10000);
        }
        DecimalFormat df = new DecimalFormat("#0.000");
        String str = df.format(num);
        Log.e("CalculateUtil",str);
        return Double.parseDouble(str);
    }

    //长材加减算法--求体积
    public static Double getVoleumLong(String length,String diameter){
        Double num = 0.0;
        Double l = Double.parseDouble(length==null||length.equals("")?"0":length);
        Double d = Double.parseDouble(diameter==null||diameter.equals("")?"0":diameter);
//        if(l>10.2 && d > 4 ){
//            num = 0.8*l*(Math.pow(d+(0.5*l),2)/10000);
//        }else if(l > 10.2 && d <= 4 ){
//            num =0.0;
//        }else if(l <= 10.2 && d < 14 ){
//            num = 0.7854*l*(Math.pow(d+(0.45*l)+0.2,2)/10000);
//            if(d <= 4 ){
//                num = 0.0;
//            }
//        }else if(l <= 10.2 && d >= 14 ){
//            Double sl = Math.pow(l,2);//长度的平方
//            Double l14 = Math.pow(14-l,2);//14-l的平方
//            num = 0.7854*l*(Math.pow(d+0.5*l+0.005*sl+0.000125*l*l14*(d-10),2)/10000);
//        }

        if(l>10.2)
        {
            if(d > 4) {
                num = 0.8*l*(Math.pow(d+(0.5*l),2)/10000);
            } else {
                num=0.0;
            }
        }else{
            //l <= 10.2
            if (d<14){
                if (d<=4){
                    num=0.0;
                }else{
                    num = 0.7854*l*(Math.pow(d+(0.45*l)+0.2,2)/10000);
                }
            }else{
                Double sl = Math.pow(l,2);//长度的平方
                Double l14 = Math.pow(14-l,2);//14-l的平方
                num = 0.7854*l*(Math.pow(d+0.5*l+0.005*sl+0.000125*l*l14*(d-10),2)/10000);
            }
        }
        DecimalFormat df = new DecimalFormat("#0.000");
        String str = df.format(num);
        Log.e("CalculateUtil",str);
        return Double.parseDouble(str);
    }


}
