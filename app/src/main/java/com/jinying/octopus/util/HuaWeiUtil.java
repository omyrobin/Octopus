package com.jinying.octopus.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

public class HuaWeiUtil {
	/**
	 * 是否是华为
	 */
	public static boolean isHUAWEI() {
	    return android.os.Build.MANUFACTURER.equals("HUAWEI");
	}
	
	//获取是否存在NavigationBar
//	public static boolean checkDeviceHasNavigationBar(Context context) {
//	    boolean hasNavigationBar = false;
//	    try {
//	        Resources rs = context.getResources();
//	        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
//	        if (id > 0) {
//	            hasNavigationBar = rs.getBoolean(id);
//	        }
//	        Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
//	        Method m = systemPropertiesClass.getMethod("get", String.class);
//	        String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
//	        if ("1".equals(navBarOverride)) {
//	            hasNavigationBar = false;
//	        } else if ("0".equals(navBarOverride)) {
//	            hasNavigationBar = true;
//	        }
//	    } catch (Exception e) {
//
//	    }
//	    return hasNavigationBar;
//	}
	
	@SuppressLint("NewApi")   
    public static boolean checkDeviceHasNavigationBar(Context activity) {  
  
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar  
        boolean hasMenuKey = ViewConfiguration.get(activity)  
                .hasPermanentMenuKey();  
        boolean hasBackKey = KeyCharacterMap  
                .deviceHasKey(KeyEvent.KEYCODE_BACK);  
  
        if (!hasMenuKey && !hasBackKey) {  
            // 做任何你需要做的,这个设备有一个导航栏  
            return true;  
        }  
        return false;  
    }  
	
	public static int getDaoHangHeight(Context context) {
		 int result = 0;
		 int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
		 if (rid != 0) {
			 int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
			 return context.getResources().getDimensionPixelSize(resourceId);
         } else
        	 return 0;
		 
	}
}
