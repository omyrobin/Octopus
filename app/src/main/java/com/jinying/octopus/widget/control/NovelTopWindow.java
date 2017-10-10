package com.jinying.octopus.widget.control;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jinying.octopus.R;
import com.jinying.octopus.read.ReadContract;

public class NovelTopWindow implements OnClickListener{
	
	private ImageView mPopBackImg;
	
	public TextView mPopTitleTv;
	
	private Context context;
	
	public PopupWindow top;

	private View contentView;

	private ReadContract.Presenter mPersenter;
	
	private NovelPopWindowsManager manager;

	public NovelTopWindow(Context context, ReadContract.Presenter mPersenter, NovelPopWindowsManager manager) {
		this.context = context;
		this.mPersenter = mPersenter;
		this.manager = manager;
	}

	public PopupWindow initTopView(String text) {
		if (top == null) {
			contentView = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow_top, null);
			top = new PopupWindow(contentView);
			top.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
			top.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
			top.setAnimationStyle(R.style.ActionPopupWindowTop);
			top.setBackgroundDrawable(new ColorDrawable(0x00000000));
			top.setFocusable(false);
			initViewByTop(contentView, text);
		}
		setBgColorByShowModel();
		return top;
	}

	private void initViewByTop(View contentView, String text) {
		mPopBackImg = contentView.findViewById(R.id.img_back);
		mPopTitleTv = contentView.findViewById(R.id._title);
		mPopTitleTv.setText(text);
		mPopBackImg.setOnClickListener(this);
	}
	
	public void setBgColorByShowModel() {
		if(mPersenter.getBgIndex() == 4){
			contentView.setBackgroundResource(R.color.colorControlNight);
			mPopBackImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.btn_back));
			mPopTitleTv.setTextColor(ContextCompat.getColor(context,R.color.textColorTitleNight));
		}else{
			contentView.setBackgroundResource(R.color.colorAccent);
			mPopBackImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.btn_back));
			mPopTitleTv.setTextColor(ContextCompat.getColor(context,android.R.color.white));
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			default:
				mPersenter.finishActivity();
				break;
		}
		
	}
}
