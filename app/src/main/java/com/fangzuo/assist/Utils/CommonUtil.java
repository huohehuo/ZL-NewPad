package com.fangzuo.assist.Utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

        //单据编号生成。。。
    public static long getOrderCodeS(boolean isAdd){
        long ordercode=0;


        long ordercodeNoTime=Hawk.get(Config.OrderCode_Short_Tree_long,Long.parseLong("0"));
        if (ordercodeNoTime==0) {
            ordercode =Math.abs(Long.parseLong(getTime(false) +Hawk.get(Config.OrderCode_PdaNo_s,"000")+ "70001"));
            ordercodeNoTime = Math.abs(Long.parseLong("70001"));
            Log.e("初始化单号ID：ordercode", ordercodeNoTime + "");
            Hawk.put(Config.OrderCode_Short_Tree_long,ordercodeNoTime);
            return ordercode;
        } else {
            if (!isAdd){
                ordercode =Math.abs(Long.parseLong(getTime(false) +Hawk.get(Config.OrderCode_PdaNo_s,"000")+ ordercodeNoTime));
                Log.e("获得单号ID：ordercode", ordercode + "");
                Hawk.put(Config.OrderCode_Short_Tree_long,ordercodeNoTime);
                return ordercode;
            }else{
                ordercodeNoTime++;
                Hawk.put(Config.OrderCode_Short_Tree_long,ordercodeNoTime);
                ordercode =Math.abs(Long.parseLong(getTime(false) +Hawk.get(Config.OrderCode_PdaNo_s,"000")+ ordercodeNoTime));
                Log.e("获得单号ID：ordercode", ordercode + "");
                Hawk.put(Config.OrderCode_Short_Tree_long,ordercodeNoTime);
                return ordercode;
            }
        }
    }

    //单据编号生成。。。
    public static long getOrderCodeL(boolean isAdd){
        long ordercode=0;

        long ordercodeNoTime=Hawk.get(Config.OrderCode_Long_Tree_long,Long.parseLong("0"));
        if (ordercodeNoTime==0) {
            ordercode =Math.abs(Long.parseLong(getTime(false) +Hawk.get(Config.OrderCode_PdaNo_s,"000")+ "80001"));
            ordercodeNoTime = Math.abs(Long.parseLong("80001"));
            Log.e("初始化单号ID：ordercode", ordercodeNoTime + "");
            Hawk.put(Config.OrderCode_Long_Tree_long,ordercodeNoTime);
            return ordercode;
        } else {
            if (!isAdd){
                ordercode =Math.abs(Long.parseLong(getTime(false) +Hawk.get(Config.OrderCode_PdaNo_s,"000")+ ordercodeNoTime));
                Log.e("获得单号ID：ordercode", ordercode + "");
                Hawk.put(Config.OrderCode_Long_Tree_long,ordercodeNoTime);
                return ordercode;
            }else{
                ordercodeNoTime++;
                Hawk.put(Config.OrderCode_Long_Tree_long,ordercodeNoTime);
                ordercode =Math.abs(Long.parseLong(getTime(false) +Hawk.get(Config.OrderCode_PdaNo_s,"000")+ ordercodeNoTime));
                Log.e("获得单号ID：ordercode", ordercode + "");
                Hawk.put(Config.OrderCode_Long_Tree_long,ordercodeNoTime);
                return ordercode;
            }
        }
    }

    //根据注册代码，获取到相对应的PdaNo
    public static String setPdaNo(String no){
        if (no.length()==1){
            Hawk.put(Config.OrderCode_PdaNo_s,"00"+no);
            return "00"+no;
        }else if (no.length()==2){
            Hawk.put(Config.OrderCode_PdaNo_s,"0"+no);
            return "0"+no;
        }else{
            Hawk.put(Config.OrderCode_PdaNo_s,no);
            return no;
        }

    }


    //获取时间
    public static String getTime(boolean b) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(b ? "yyyy-MM-dd" : "yyyyMMdd");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }
    //获取时分秒
    public static String getTimeHMS(){
        Calendar c = Calendar.getInstance();//
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        int mMinute = c.get(Calendar.MINUTE);//分
        int miao = c.get(Calendar.SECOND);//分
        return " "+mHour+":"+mMinute+":"+miao;
    }
    //获取时间,带时分秒
    public static String getLongTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str+getTimeHMS();
    }

    //检测是否有字母；
    public static boolean checkHasABC(String str) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m=Pattern.compile(regex).matcher(str);
        return m.matches();
    }




}
