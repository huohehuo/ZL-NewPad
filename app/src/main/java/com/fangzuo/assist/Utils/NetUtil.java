package com.fangzuo.assist.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.example.miniprinter.App;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Administrator on 2017/4/13.
 */

public class NetUtil {

    private static ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

    public static boolean isNetworkAvailable() {
        if (connectivityManager == null) {
            return false;
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 connectivityManager.getActiveNetworkInfo().isAvailable();
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();//判断是否有网
            }
        }
        return false;
    }

    public static boolean is3rd() {
        NetworkInfo networkINfo = connectivityManager.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public static boolean isWifi() {
        NetworkInfo networkINfo = connectivityManager.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    //扫描所有网络接口，查看是否有使用VPN的（接口名为tun0或ppp0）
    public static boolean isVpnConnected() {
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if (niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                        return true;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    //用于判断服务器是否可以连接（成功200）
    public static boolean isConnectionOK(String url) {
        HttpURLConnection connection = null;
        try {
            URL server = new URL(url);
            connection = (HttpURLConnection) server.openConnection();
            connection.setRequestMethod("GET");
            // 判断请求是否成功
//            //Log.e("http",connection.getResponseCode()+"");
            if (connection.getResponseCode() == 200) {
                // 成功
//                //Log.e("http","请求成功");
                connection.disconnect();
                return true;
            } else {
//                //Log.e("http","请求失败");
                connection.disconnect();
                return false;
            }
            // 关闭连接
        } catch (IOException e) {
            e.printStackTrace();
            connection.disconnect();
        }
        return false;
    }

    //用于判断服务器是否可以连接（成功200）
    public static boolean isConnectionYouToBe(String url) {
        HttpURLConnection connection = null;
        try {
            URL server = new URL(url);
            connection = (HttpURLConnection) server.openConnection();
            connection.setRequestMethod("GET");
            // 判断请求是否成功
            //Log.e("http",connection.getResponseCode()+"");
            if (connection.getResponseCode() == 200) {
                // 成功
                //Log.e("http","请求成功");
                connection.disconnect();
                return true;
            } else {
                //Log.e("http","请求失败");
                connection.disconnect();
                return false;
            }
            // 关闭连接
        } catch (IOException e) {
            e.printStackTrace();
            connection.disconnect();
        }
        return false;
    }

    //返回版本名称3.0.0
    public static String getVersionName(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi.versionName;
    }


     //获取应用程序版本编号300
    public static int getVersinoCode(Context context){
        int intVersioinCode=0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            intVersioinCode=info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return intVersioinCode;
//        return  208;
    }
//    //获取每个使用者的唯一UUID值
//    public static String getUserNoId() {
//        String usernoid = App.getKeyValue("user.usernoid");
//        if (usernoid.equals("")) {
//            usernoid = UUID.randomUUID().toString();
//            App.setKeyValue("user.usernoid", usernoid);
//        }
//        return usernoid;
//    }

    //获取所属语言区域 CN
    public static String getLang() {
        return Locale.getDefault().getLanguage();
    }

    //获取包名最后的名称作为标识(com.quseit.xsocks)  xsocks
    public static String getCode(Context context) {
        String packageName = context.getPackageName();
        String[] xcode = packageName.split("\\.");
        String code = xcode[xcode.length-1];
        return code;
    }
    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }


    //获取手机的ip地址
    public static String getPhoneIp() {
        if (is3rd()){
            //Log.e("net","当前为：移动网络");
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface
                        .getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()
                                && inetAddress instanceof Inet4Address) {
                            // if (!inetAddress.isLoopbackAddress() && inetAddress
                            // instanceof Inet6Address) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (isWifi()){
            //Log.e("net","当前为：WIFI网络");
            WifiManager wifiManager = (WifiManager) App.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String str=(ipAddress & 0xFF)+ "." +((ipAddress >> 8) & 0xFF)+ "." + ((ipAddress >> 16) & 0xFF)+ "."
                    + (((ipAddress >> 24) & 0xFF));
            //此处格式化ip地址，app端应与服务器连接在统一网络，并且ip要改成服务器电脑一样的ip地址才能有效连接
            if (str!=null){
                return str;
            }else{
                return "0.0.0.0";
            }
        }else{
            //Log.e("net","当前为：位置网络");
            return "0.0.0.0";
        }
        return "0.0.0.0";
    }


//    public static String getUserUrl(Context context) {
//        String sdk = "0";
//        try {
//            sdk = Build.VERSION.SDK;
//        } catch (Exception e) {
//
//        }
//        return "uid=&token=&userno="+NetUtil.getUserNoId()+"&lang="+NetUtil.getLang()+
//                "&ver="+NetUtil.getVersinoCode(context)+"&code="+NetUtil.getCode(context)+"&sdk="+sdk+"&appid="+context.getPackageName();
//    }
//
//    //用于辨别是否为当天（活跃度统计一天一次）
//    public static void updataUserLiveness(){
//        String backTime =AdUtil.checkAdDataForHour(Config.LIVENESS_UPDATA_TIME,6.00);//设置最小时限后更新广告
//        if ("".equals(backTime)) {//为空时，已超过时限
//            XApp.e("执行用户活跃度数据推送");
//            updataLiveness();
//        }else{
//            XApp.e(" 暂时不执行用户活跃度数据推送 time < 6 hour left:"+backTime);
//        }
//
////        String getTime=XApp.getKeyValue("livedapp");
////        if ("".equals(getTime)){//当为空，则true执行更新活跃度
////            XApp.e("执行用户活跃度数据推送");
////            updataLiveness();
////        }else if (!getDataForLive().equals(getTime)){//当不等值，则为不同一天，执行更新活跃度
////            XApp.e("执行用户活跃度数据推送");
////            updataLiveness();
////        }else{//不为空，时间与保存的时间一致，为同一天，false不执行更新活跃度
////            XApp.e("当天：不再执行用户活跃度数据推送");
////        }
//    }

    //用于辨别是否为当天（活跃度统计一天一次）
    private static String getDataForLive(){
        Calendar cal = Calendar.getInstance();
        java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String cdate = sdf.format(cal.getTime());
        return cdate;
    }
    //获取时间（活跃度统计时需要）
    public static final String getDateMin(){
        Calendar cal = Calendar.getInstance();
        java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String cdate = sdf.format(cal.getTime());
        return cdate;
    }

//    //执行更新用户活跃度至后台
//    public static void updataLiveness() {
//        StringBuilder tempParams = new StringBuilder();
//        tempParams.append("time=" + getDateMin() + "&");
//        tempParams.append("logs=&");
//        /* 获取手机客户信息类型 */
//        String collectInfos = Config.COLLECT_INFO;
//        //获取系统信息文件数据（包含手机的基础信息）
//        Field[] fields = Build.class.getDeclaredFields();
//        for (Field field : fields) {
//            try {
//                field.setAccessible(true);
//                String k = field.getName();
//                if (k != null) {
//                    k = k.toLowerCase().trim();
//                }
//                if (collectInfos.contains("#" + k + "#")) {
//                    tempParams.append(k + "=" + field.get(null).toString() + "&");
//                }
//            } catch (Exception e) {
//                XApp.e("获取配置信息错误", "an error occured when collect crash info：" + e.toString());
//            }
//        }
//        //生成参数
//        String params = tempParams.toString().substring(0, tempParams.length() - 1);
//        //创建一个请求实体对象 RequestBody
//        RequestBody body = RequestBody.create(Config.MEDIA_TYPE_JSON, params);
//
//        String updateUrl = Config.UPDATE_URL + XApp.getContext().getPackageName() + "/" + getVersinoCode(XApp.getContext()) + "?" + getUserUrl(XApp.getContext());
//        XApp.e("执行用户活跃度数据推送：" + updateUrl);
//        XApp.e("执行用户活跃度数据推送2：" + params);
//        Request request = new Request.Builder().url(updateUrl).post(body).build();
//        XApp.getOkHttpClient().newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                XApp.e("firebase", "失败：活跃度发送数据出错");
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                AdUtil.addUserToAdData(Config.LIVENESS_UPDATA_TIME);//存入当前时间，用于辨识时间区间，以获取新数据
//                Log.e("执行用户活跃度数据推送2：", "" + response.body().string());
//                XApp.e("firebase", "成功：活跃度发送数据成功");
//            }
//        });
//    }
//
//    public static boolean checkIfUpdataFlow(int limit){
//        long nowFlow= TrafficStats.getTotalRxBytes();
//        String saveFlow=XApp.getKeyValue(Config.CHECK_TOTAL_FLOW_TAG);
//        XApp.e("检测流量使用度：\n本来："+saveFlow+"\n现在："+nowFlow);
//        if (!"".equals(saveFlow)){
//            long result=nowFlow- Long.valueOf(saveFlow);
//            if (result>0){
//                int res=(int)result/1024/1024;
//                if (res>limit){
//                    XApp.setKeyValue(Config.CHECK_TOTAL_FLOW_TAG,nowFlow+"");
//                    return true;
//                }
//                return false;
//            }else{
//                //当重启设备时，设备记录的流量值会归零，这种情况就会为负数
//                XApp.setKeyValue(Config.CHECK_TOTAL_FLOW_TAG,nowFlow+"");
//            }
//            return false;
//        }else{
//            //首次时，存入新的值
//            XApp.setKeyValue(Config.CHECK_TOTAL_FLOW_TAG,nowFlow+"");
//        }
//        return false;
//    }

}