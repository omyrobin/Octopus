package com.jinying.octopus.widget.rowview;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jinying.octopus.R;
import com.jinying.octopus.util.DensityUtils;

public class GroupView extends LinearLayout{

	private ArrayList<RowDescript> descripts;
	
	private Context context;

	private OnRowClickListener listener;

	public GroupView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
	}

	public GroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public GroupView(Context context) {
		super(context);
		this.context = context;
		initView();
	}
	
	public void initView(){
		setOrientation(VERTICAL);
		setBackgroundResource(android.R.color.white);
	}
	
	public void initData(GroupDescript descript, OnRowClickListener listener){
		this.descripts = descript.descripts;
		this.listener = listener;
	}
	
	public void notifDataChange(){
		if(descripts!=null && descripts.size()>0){
			RowView row = null;
			View line = null;
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,1);
			if(descripts.get(0).iconResId==0){
				params.leftMargin = DensityUtils.dp2px(context, 10);
			}else{
				params.leftMargin = DensityUtils.dp2px(context, 40);
			}
			for (int i = 0; i < descripts.size(); i++) {
				RowDescript descript = descripts.get(i);
				row = new RowView(context);
				row.initData(descript,listener);
				row.notifDataChange();
				addView(row);
				if(i != descripts.size()-1){
					line = new View(context);
					line.setBackgroundResource(R.color.colorDivider);
					addView(line,params);
				}
			}
		}else{
			setVisibility(View.GONE);
		}
	}
	
}
