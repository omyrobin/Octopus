package com.jinying.octopus.util;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class DateUtil {

    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat sf = new SimpleDateFormat(DATEFORMAT, Locale.getDefault());

    public static String time2DateStr(long time){
        String sd = sf.format(new Date(time));
        return sd;
    }

    public static String time2Date(long time){
        String sd = sf.format(new Date(time));
        return time2Tip(sd);
    }

    public static String time2Tip(String time){
        ArrayList<String> showTimeList = new ArrayList<String>();

        try {
            Date date_1 = new Date();
            Date date_2 = sf.parse(time);
            Time timer = new Time("GMT+8");
            long time1 = date_1.getTime();
            long time2 = date_2.getTime();
            timer.set(time2);
            long time3 = Math.abs(time1 - time2);
            int year = (int) (time3 / 1000 / 365 / 60 / 60 / 24);
            int month = (int) (time3 / 1000 / 60 / 60 / 24 / 30);
            int day = (int) (time3 / 1000 / 60 / 60 / 24);
            int hour = (int) (time3 / 1000 / 60 / 60);
            int minute = (int) (time3 / 1000 / 60);
            int second = (int) (time3 / 1000);
            if (year > 0) {
                showTimeList.add(0, "year");
                showTimeList.add(1, year + "");
            } else if (year <= 0 && month > 0) {
                showTimeList.add(0, "month");
                showTimeList.add(1, month + "");
            } else if (year <= 0 && month <= 0 && day > 0) {
                showTimeList.add(0, "day");
                showTimeList.add(1, (day) + "");
            } else if (year <= 0 && month <= 0 && day <= 0 && hour > 0) {
                showTimeList.add(0, "hour");
                showTimeList.add(1, (hour) + "");
            } else if (year <= 0 && month <= 0 && day <= 0 && hour <= 0 && minute > 0) {
                showTimeList.add(0, "minute");
                showTimeList.add(1, minute + "");
            } else if (year <= 0 && month <= 0 && day <= 0 && minute <= 0 && second >= 0) {
                showTimeList.add(0, "second");
                showTimeList.add(1, "刚刚");
            }
        } catch (Exception pe) {
            Logger.e("解析显示时间错误: " + pe.getMessage());
        }

        String showTime = "";

        if(showTimeList == null || showTimeList.isEmpty())
            return showTime;

        String type = showTimeList.get(0);
        if ("year".equals(type)) {
            showTime = time;
        }
        if ("month".equals(type)) {
            showTime = time;
        }
        if ("day".equals(type)) {
            showTime = time;
        }
        if ("hour".equals(type)) {
            showTime = (showTimeList.get(1) + "小时前");
        }
        if ("minute".equals(type)) {
            showTime = (showTimeList.get(1) + "分钟前");
        }
        if ("second".equals(type)) {
            showTime = (showTimeList.get(1));
        }
        Logger.i("showTime  ------ :  "  +  showTime);
        return showTime;
    }
}
