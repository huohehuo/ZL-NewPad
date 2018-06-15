package com.fangzuo.assist.Utils;

import android.util.Log;

import com.example.miniprinter.App;


public class Lg {

    public static void e(String string){
        if (App.isDebug){
            if (string!=null){
                Log.e("TEST",string);
            }
        }
    }
    public static void e(String tag,String string){
        if (App.isDebug){
            if (string!=null){
                Log.e(tag,string);
            }
        }
    }
}
