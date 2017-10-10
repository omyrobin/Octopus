package com.jinying.octopus.util;

import android.text.TextUtils;

import java.io.BufferedReader;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class StringUtils {
    /**
     * 处理get请求中特殊字符
     *
     * @param params
     * @return
     */
    public static String encodeParams(String params) {

        String param = "";
        if (TextUtils.isEmpty(params))
            return param;
        try {
            String mParams = params.replaceAll("%", "%25")
                    .replaceAll("\\+", "%2B").replaceAll(" ", "+")
                    .replaceAll("\\/", "%2F").replaceAll("\\?", "%3F")
                    .replaceAll("\\#", "%23").replaceAll("\\&", "%26")
                    .replaceAll("\\=", "%3D").replaceAll("\\}", "%7B")
                    .replaceAll("\\{", "%7D").replaceAll("\'", "%27")
                    .replaceAll("\\^", "%5E").replaceAll("\\;", "%3B")
                    .replaceAll("\\|", "%7C").replaceAll("\\[", "%5B")
                    .replaceAll("\\]", "%5D").replaceAll("\\@", "%40")
                    .replaceAll("\\$", "%24").replaceAll("\\`", "%60")
                    .replaceAll("\\,", "%2C").replaceAll("\\:", "%3A")
                    .replaceAll("\\\\", "%5C");
            return mParams;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

    public static String editString(String params) {
        if (TextUtils.isEmpty(params))
            return params;
        StringBuffer buffer = new StringBuffer();
        String[] descs = BCConvertUtil.half2Fullchange(params).split("\n");
        for (int i = 0; i < descs.length; i++) {
            if(descs[i].trim().replaceAll("\\s*", "").equals("")){
                continue;
            }
           if(i>1){
                buffer.append(descs[i].trim().replaceAll("\\s*", ""));
            }
        }
        return buffer.toString();
    }
}
