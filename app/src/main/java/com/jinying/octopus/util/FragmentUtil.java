package com.jinying.octopus.util;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtil {
	
	public static FragmentTransaction hideFragment(FragmentManager fm){
		if(fm != null){
			List<Fragment> fragments = fm.getFragments();
			FragmentTransaction transaction = fm.beginTransaction();
			if(fragments!=null)
			for(Fragment f : fragments){
				if(f!=null && !f.isHidden()){
					transaction.hide(f);
				}
			}
			return transaction;
		}else{
			throw new NullPointerException("FragmentManager is null!");
		}
	}
	
}
