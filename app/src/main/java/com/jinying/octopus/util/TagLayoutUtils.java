package com.jinying.octopus.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;

import com.jinying.octopus.search.ITagListener;
import com.jinying.octopus.view.TagLayout;

public class TagLayoutUtils {
	private Context context;
	
	private ITagListener mListener;
	
	public TagLayoutUtils(Context context, ITagListener mListener) {
		this.context = context;
		this.mListener = mListener;
	}

	public void addToTagLayout(String lable, int colorResId, int drawableResId, TagLayout tagLayout){
		MarginLayoutParams params = new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		int marginLR = DensityUtils.dp2px(context, 3);
		int marginTB = DensityUtils.dp2px(context, 5);
		params.leftMargin = marginLR;  
		params.rightMargin = marginLR;  
		params.topMargin = marginTB;  
		params.bottomMargin = marginTB; 
		
		int padLR = DensityUtils.dp2px(context, 11);
		int padTB = DensityUtils.dp2px(context, 4);
		TextView child = new TextView(context);
		child.setPadding(padLR, padTB, padLR, padTB);
		child.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		child.setTextColor(context.getResources().getColor(colorResId));
		child.setBackgroundResource(drawableResId);
		child.setText(lable);
		child.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener!=null)
					mListener.getClickTxt(((TextView)v).getText().toString());
				
			}
		});
		tagLayout.addView(child, params);
	}

}
