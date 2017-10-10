package com.jinying.octopus.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileNumber {
    /**
	 * 判断是否为手机号码
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(14[7])|(15[^4,\\D])|(17[0-8])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}