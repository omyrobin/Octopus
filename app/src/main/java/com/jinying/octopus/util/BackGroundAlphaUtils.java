package com.jinying.octopus.util;

import android.view.View;

public class BackGroundAlphaUtils {

    private static BackGroundAlphaUtils mUtils;
	
	public static BackGroundAlphaUtils getInstatnce(){
		if(mUtils == null){
			mUtils = new BackGroundAlphaUtils();
		}
		return mUtils;
	}
	
	public void darken(View mAlphaBg){
		mAlphaBg.setVisibility(View.VISIBLE);
		mAlphaBg.setAlpha(0f);
		mAlphaBg.animate().alpha(0.7f).start();
	}
	
	public void brighten(final View mAlphaBg){
		mAlphaBg.animate().alpha(0f).start();
	}
}