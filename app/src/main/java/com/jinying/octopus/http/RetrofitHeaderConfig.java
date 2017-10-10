package com.jinying.octopus.http;

import android.content.Context;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.jinying.octopus.App;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.util.AppUtils;
import com.jinying.octopus.util.SharedPreferencesUtil;

import java.util.Map;

/**
 * Created by omyrobin on 2017/8/21.
 */
public class RetrofitHeaderConfig {

    private static ArrayMap<String, String> headers = new ArrayMap<>();
    public static final String X_I = "X-I";
    public static final String X_S = "X-S";
    public static final String C = "C";
    public static String getHeaderC() {
        String clientHeader = "android/model:" + Build.MODEL + ",SDK:Android"
                + Build.VERSION.RELEASE + "(API" + Build.VERSION.SDK_INT
                + ")-huoxing/"
                + AppUtils.getVersionName(App.getInstance())+"/"
                + AppUtils.getAppMetaData(App.getInstance().getApplicationContext(), "UMENG_CHANNEL")+ "/"
                + AppUtils.getDeviceId();
        return clientHeader;
    }

    public static Map<String, String> getHeaderX(){
        String xi = (String) SharedPreferencesUtil.getParam(App.getInstance(), X_I, "");
        String xs = (String) SharedPreferencesUtil.getParam(App.getInstance(), X_S, "");
        if(!TextUtils.isEmpty(xi) && !TextUtils.isEmpty(xs)){
            setHeaderX(xi,xs);
        }
        return headers;
    }

    public static Map<String, String> getHeaders(){
        getHeaderX();
        headers.put(C, getHeaderC());
        return headers;
    }
    
    public static void setHeaderX(String xi, String xs){
        headers.put(X_I, xi);
        headers.put(X_S, xs);
        SharedPreferencesUtil.setParam(App.getInstance(), X_I, xi);
        SharedPreferencesUtil.setParam(App.getInstance(), X_S, xs);
    }
}
