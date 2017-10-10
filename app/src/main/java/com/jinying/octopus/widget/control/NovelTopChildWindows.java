package com.jinying.octopus.widget.control;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jinying.octopus.R;
import com.jinying.octopus.read.ReadContract;
import com.jinying.octopus.util.DensityUtils;

public class NovelTopChildWindows implements OnClickListener{
	
	private Context context;
	
	private View contentView;
	
	public PopupWindow topChild;
	
	private ReadContract.Presenter mPersenter;
	
	private TextView mBookDetialsTv;

	public NovelTopChildWindows(Context context, ReadContract.Presenter mPersenter){
		this.context = context;
		this.mPersenter = mPersenter;
	}
	
	public PopupWindow initView(){
		if(topChild==null){
			contentView = LayoutInflater.from(context).inflate(R.layout.layout_content_popwindow, null);
			topChild = new PopupWindow(contentView);
			topChild.setWidth(DensityUtils.dp2px(context, 120));
			topChild.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
			topChild.setFocusable(true);
			topChild.setBackgroundDrawable(new ColorDrawable(0x00000000));
			topChild.setOutsideTouchable(true);
			initViewByTopChild();
		}
		return topChild;
	}

	private void initViewByTopChild() {
		mBookDetialsTv = contentView.findViewById(R.id.mBookDetialsTv);

		mBookDetialsTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mBookDetialsTv:
			mPersenter.initNovelDetails();
			break;
		}
	}
}
