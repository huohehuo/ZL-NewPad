package com.fangzuo.assist.Utils;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * Created by 王璐阳 on 2018/4/12.
 */

public class PgUtil {
    private static PgUtil mInstance;
    Context context;
    private final ProgressDialog pg;

    public PgUtil(Context context) {
        this.context = context;
        pg = new ProgressDialog(context);

    }

    public static synchronized PgUtil getInstance(Context context){
        if(mInstance == null){
            mInstance = new PgUtil(context);
        }
        return mInstance;
    }

    public void setText(String s){
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(s);
    }

    public void show(){
        pg.show();
    }

    public void dismiss(){
        if(pg!=null&&pg.isShowing()){
            pg.dismiss();
        }
    }
}
