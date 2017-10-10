package com.jinying.octopus.widget.rowview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinying.octopus.R;


public class RowView extends LinearLayout implements OnClickListener{
	
	private Context context;
	
	private ImageView mWidgetRowIconImg;
	
	private TextView mWidgetRowLableTv,mWidgetRowMoneyTv;
	
	private ImageButton mWidgetActionImgBtn;
	
	private TextView mWidgetActionTv;
	
	private OnRowClickListener listener;

	private RowDescript descript;

	public RowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
	}

	public RowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public RowView(Context context) {
		super(context);
		this.context = context;
		initView();
	}
	
	public void initView(){
		LayoutInflater.from(context).inflate(R.layout.widget_rowview, this);
		mWidgetRowIconImg = findViewById(R.id.mWidgetRowIconImg);
		mWidgetRowLableTv = findViewById(R.id.mWidgetRowLableTv);
		mWidgetRowMoneyTv = findViewById(R.id.mWidgetRowMoneyTv);
		mWidgetActionImgBtn = findViewById(R.id.mWidgetActionImgBtn);
	}
	
	@Override
	public void onClick(View v) {
		if(listener!=null){
			listener.onRowClick(descript.action);
		}
	}

	public void initData(RowDescript descript, OnRowClickListener listener) {
		this.descript = descript;
		this.listener = listener;
	}

	public void notifDataChange() {
		if(descript!=null){
			if(descript.iconResId==0){
				mWidgetRowIconImg.setVisibility(View.GONE);
			}else{
				mWidgetRowIconImg.setBackgroundResource(descript.iconResId);
			}
			mWidgetRowLableTv.setText(descript.lable);
			if(TextUtils.isEmpty(descript.money)){
				mWidgetRowMoneyTv.setVisibility(View.GONE);
			}else{
				mWidgetRowMoneyTv.setText(descript.money);
			}
			if(descript.action !=null){
				setOnClickListener(this);
//				setBackgroundResource(resid);
			}
			if(descript.visable==INVISIBLE){
				mWidgetActionImgBtn.setVisibility(View.INVISIBLE);
			}else{
//				mWidgetActionImgBtn.setImageResource(R.drawable.more);
			}
		}
		
	}

}
