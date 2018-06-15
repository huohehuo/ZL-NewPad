package com.fangzuo.assist.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.fangzuo.assist.Beans.SettingList;
import com.fangzuo.assist.R;

import java.util.ArrayList;

/**
 * Created by NB on 2017/7/28.
 */

public class GetSettingList {
    public static ArrayList<SettingList> getList() {
        ArrayList<SettingList> items = new ArrayList<>();
        SettingList s = new SettingList();
        s.ImageResourse = R.mipmap.download;
        s.tv = "下载配置";
        items.add(s);
        SettingList s1 = new SettingList();
        s1.ImageResourse = R.mipmap.wifi;
        s1.tv = "wifi连接";
        items.add(s1);
        SettingList s2 = new SettingList();
        s2.ImageResourse = R.mipmap.sound;
        s2.tv = "声音设置";
        items.add(s2);
        SettingList s3 = new SettingList();
        s3.ImageResourse = R.mipmap.getnewversion;
        s3.tv = "更新版本";
        items.add(s3);
        SettingList s4 = new SettingList();
        s4.ImageResourse = R.mipmap.tomcat;
        s4.tv = "服务器设置";
        items.add(s4);
        SettingList s5 = new SettingList();
        s5.ImageResourse = R.mipmap.test;
        s5.tv = "网络测试";
        items.add(s5);
        SettingList s6 = new SettingList();
        s6.ImageResourse = R.mipmap.test;
        s6.tv = "打印机配置与测试";
        items.add(s6);
        SettingList s7 = new SettingList();
        s7.ImageResourse = R.mipmap.test;
        s7.tv = "打印文本设置";
        items.add(s7);
        return items;


    }
    public static ArrayList<SettingList> getPurchaseList() {
        ArrayList<SettingList> items = new ArrayList<>();

        return items;


    }

    public static ArrayList<SettingList> getSaleList(String[] ary) {
        ArrayList<SettingList> items = new ArrayList<>();
        //最终返回的选项
        ArrayList<SettingList> backItems = new ArrayList<>();
        SettingList s1 = new SettingList();
        s1.ImageResourse = R.mipmap.sellinout;
        s1.tv = "短材出库";
        s1.tag="1";
        items.add(s1);
        SettingList s2 = new SettingList();
        s2.ImageResourse = R.mipmap.sellinout;
        s2.tv = "长材出库";
        s2.tag="2";
        items.add(s2);
        SettingList s3 = new SettingList();
        items.add(s3);
        SettingList s1b = new SettingList();
        s1b.ImageResourse = R.mipmap.sellinout;
        s1b.tv = "   短材出库(红字)";
        s1b.tag="3";
        items.add(s1b);
        SettingList s2b = new SettingList();
        s2b.ImageResourse = R.mipmap.sellinout;
        s2b.tv = "   长材出库(红字)";
        s2b.tag="4";
        items.add(s2b);
        SettingList s3b = new SettingList();
        items.add(s3b);
        SettingList s4 = new SettingList();
        s4.ImageResourse = R.mipmap.sellinout;
        s4.tv = "库存查询";
        s4.tag="5";
        items.add(s4);
        SettingList s5 = new SettingList();
        s5.ImageResourse = R.mipmap.sellinout;
        s5.tv = "在线查询";
        s5.tag="6";
        items.add(s5);

        SettingList s6 = new SettingList();
        s6.ImageResourse = R.mipmap.sellinout;
        s6.tv = "新增价格";
        s6.tag="7";
        items.add(s6);

        SettingList s7 = new SettingList();
        s7.ImageResourse = R.mipmap.sellinout;
        s7.tv = "详情查询";
        s7.tag="8";
        items.add(s7);
        SettingList s8 = new SettingList();
        s8.ImageResourse = R.mipmap.sellinout;
        s8.tv = "客户对账单";
        s8.tag="9";
        items.add(s8);

        for (int i=0; i<items.size();i++){
//            Log.e("test","定位ary:"+ary[i]);
//            Log.e("test","定位items:"+items.get(i).tag);
            //根据ary的值，遍历list符合的item并添加
            for (int j=0;j<ary.length;j++){
                if (TextUtils.equals(items.get(i).tag,ary[j])){
                    Log.e("test","222加入："+items.get(i).toString());
                    backItems.add(items.get(i));
                }
            }
        }
//        backItems.add(s2);

        return backItems;


    }

    public static ArrayList<SettingList> getStorageList() {
        ArrayList<SettingList> items = new ArrayList<>();

        return items;


    }

    public static ArrayList<SettingList> GetPushDownList() {
        ArrayList<SettingList> items = new ArrayList<>();
        return items;


    }


}
