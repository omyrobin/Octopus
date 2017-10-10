package com.jinying.octopus.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.jinying.octopus.App;

public class AppUtils {
  
    private AppUtils() {
        /* cannot be instantiated */  
        throw new UnsupportedOperationException("cannot be instantiated");  
  
    }  
  
    /** 
     * 获取应用程序名称 
     */  
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);  
            int labelRes = packageInfo.applicationInfo.labelRes;  
            return context.getResources().getString(labelRes);  
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * [获取应用程序版本名称信息] 
     *  
     * @param context 
     * @return 当前应用的版本名称 
     */  
    public static String getVersionName(Context context) {
        try  
        {  
            PackageManager packageManager = context.getPackageManager();  
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;  
  
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();  
        }  
        return null;  
    }

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    public static String getDeviceId(){
        String id;
        //android.telephony.TelephonyManager
        TelephonyManager mTelephony = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            id = mTelephony.getDeviceId();
        } else {
            //android.provider.Settings;
            id = Settings.Secure.getString(App.getInstance().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return id;
    }
}