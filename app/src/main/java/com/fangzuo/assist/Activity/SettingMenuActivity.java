package com.fangzuo.assist.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fangzuo.assist.ABase.BaseActivity;
import com.fangzuo.assist.Adapter.SettingListAdapter;
import com.fangzuo.assist.Beans.CommonResponse;
import com.fangzuo.assist.Beans.DownloadReturnBean;
import com.fangzuo.assist.Beans.NewVersionBean;
import com.fangzuo.assist.R;
import com.fangzuo.assist.Server.WebAPI;
import com.fangzuo.assist.Utils.Asynchttp;
import com.fangzuo.assist.Utils.CallBack;
import com.fangzuo.assist.Utils.JsonCreater;
import com.fangzuo.assist.Utils.RetrofitUtil;
import com.fangzuo.assist.Utils.Toast;
import com.fangzuo.assist.Utils.WebApi;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.loopj.android.http.AsyncHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fangzuo.assist.Utils.GetSettingList.getList;

public class SettingMenuActivity extends BaseActivity {


    @BindView(R.id.lv_setting)
    ListView lvSetting;
    private ProgressDialog pDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting_menu);
        mContext = this;
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        SettingListAdapter ada = new SettingListAdapter(mContext,getList());
        lvSetting.setAdapter(ada);
        ada.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startNewActivity(SettingActivity.class,0,0,false,null);
                        break;
                    case 1:
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                        break;
                    case 2:
                        startActivity(new Intent(Settings.ACTION_SOUND_SETTINGS));
                        break;
                    case 3:
                        checkNewVersion();
                        break;
                    case 4:
                        startActivity(new Intent(mContext,IpPortActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(mContext,TestActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(mContext,PrintOutTestActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(mContext,PrintSetActivity.class));
                        break;
                }

            }
        });
    }

    @Override
    protected void OnReceive(String code) {

    }

    private void checkNewVersion() {
        Asynchttp.post(mContext, getBaseUrl() + WebApi.GETNEWVERSION, "", new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                final NewVersionBean nBean = new Gson().fromJson(cBean.returnJson,NewVersionBean.class);
                if(nBean.Version>getVersionCode()){
                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                    ab.setTitle("发现新版本");
                    ab.setMessage(nBean.Rem);
                    ab.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DownLoad(nBean.downLoadURL);
//                            getDown(nBean.downLoadURL);
//                            installApk("/storage/emulated/0/phone.apk");
//                            DownLoad("http://www.linspace.club/apk/myspace.apk");
                        }
                    });
                    ab.setNegativeButton("取消",null);
                    ab.create().show();
                }else{
                    Toast.showText(mContext,"无新版本");
                }
            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                Toast.showText(mContext,Msg);
            }
        });
    }

    private void DownLoad(String downLoadURL) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            pDialog = new ProgressDialog(mContext);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setTitle("下载中");
            pDialog.show();
            final String target = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "phone.apk";
            HttpUtils utils = new HttpUtils();
            utils.download(downLoadURL, target, new RequestCallBack<File>() {

                @Override
                public void onLoading(long total, long current,
                                      boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    System.out.println("下载进度:" + current + "/" + total);
                    pDialog.setProgress((int) (current*100/total));
                }

                @Override
                public void onSuccess(ResponseInfo<File> arg0) {
                    pDialog.dismiss();
                    Toast.showText(mContext,"下载完成");
                    System.out.println("下载完成"+arg0.toString());
                    System.out.println("下载完成22"+arg0.result);
                    installApk(arg0.result);
//                    installApk(arg0.result+"");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.addCategory(Intent.CATEGORY_DEFAULT);
//                    intent.setDataAndType(Uri.fromFile(arg0.result),
//                            "application/vnd.android.package-archive");
//                    startActivityForResult(intent, 0);
                }

                @Override
                public void onFailure(com.lidroid.xutils.exception.HttpException arg0, String arg1) {
                    pDialog.dismiss();
                    System.out.println("下载失败"+arg1);
                    System.out.println("下载失败22"+arg0.toString());
                    Toast.showText(mContext, "下载失败");
                }


            });
        } else {
            pDialog.dismiss();
            Toast.showText(mContext, "正在安装");

        }
    }

    /**id	Version	Rem	DownLoadURL
     1	999	新增牛逼功能	http://yzdzpda.imwork.net:11986/apk/assist.apk
     * 安装APK文件
     */
    private void installApk(String path) {


        File apkfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "","phone.apk");
        if (!apkfile.exists()) {
            return;
        }
        Uri photoURI = FileProvider.getUriForFile(mContext,
                mContext.getApplicationContext().getPackageName() + ".provider", apkfile);
//        String newpath = path+"/pad/pad.apk";
//        renameFile(apkfile.toString(),newpath);
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + photoURI),
                "application/vnd.android.package-archive");
        mContext.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    public void installApk(File apkFile) {
//        Intent installApkIntent = new Intent();
//        installApkIntent.setAction(Intent.ACTION_VIEW);
//        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//            installApkIntent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), "com.fangzuo.assist.zl.file_provider", apkFile), "application/vnd.android.package-archive");
//            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        } else {
//            installApkIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//        }
//
//        if (getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
//            startActivity(installApkIntent);
//        }



        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.fangzuo.assist.pad.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (getApplicationContext().getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            getApplicationContext().startActivity(intent);
        }
    }

    private void getDown(String fileUrl){
//        RetrofitUtil.getInstance(mContext).createReq(WebAPI.class).
//                downloadFileWithDynamicUrlSync("").enqueue(new CallBack() {
//            @Override
//            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
//                if (response.isSuccess()) {
//                    Log.d(TAG, "server contacted and has file");
//
//                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
//
//                    Log.d(TAG, "file download was a success? " + writtenToDisk);
//                } else {
//                    Log.d(TAG, "server contact failed");
//                }
//
//            }
//
//            @Override
//            public void onSucceed(CommonResponse cBean) {
//                DownloadReturnBean dBean = JsonCreater.gson.fromJson(cBean.returnJson, DownloadReturnBean.class);
//                container.addAll(dBean.storage);
//                storageSpAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void OnFail(String Msg) {
//                Toast.showText(context, Msg);
//            }
//        });

//        fileUrl="http://www.linspace.club/apk/myspace.apk";
        Call<ResponseBody> call = RetrofitUtil.getInstance(mContext).createReq(WebAPI.class).downloadFileWithDynamicUrlSync(fileUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccess()) {
                    Log.d(TAG, "server contacted and has file");

                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                    Log.d(TAG, "file download was a success? " + writtenToDisk);
//                } else {
                    Log.d(TAG, "server contact failed");
//                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            String cachePath = (
                    getExternalFilesDir("doapk") +
                            File.separator +
                            getPackageName() +
                            ".apk");
            String cachePath2 = (
                    getExternalFilesDir("doapk") +
                            File.separator +
                            "newapk"+
                            "aa.apk");
            String cachePath3 = (
                    getExternalFilesDir("doapk") +
                            File.separator +
                            "newapk"+
                            "aa");
            String cachePath4 = (
                    getExternalFilesDir("doapk") +
                            File.separator +
                            "newapk"+
                            ".apk");
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(cachePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                renameFile(cachePath,cachePath2);
                renameFile(cachePath2,cachePath3);
                renameFile(cachePath3,cachePath4);

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }





    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    public static void renameFile(String oldPath, String newPath) {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startNewActivity(LoginActivity.class,0,0,true,null);
    }
}
