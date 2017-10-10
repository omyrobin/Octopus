package com.jinying.octopus.widget.control;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinying.octopus.R;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.bookdetails.catalogue.SectionAdatper;
import com.jinying.octopus.read.ReadContract;
import com.jinying.octopus.read.ui.ReadActivity;
import com.jinying.octopus.util.Logger;

import java.util.ArrayList;

public class NovelLeftWindow implements OnClickListener,OnGroupClickListener,OnChildClickListener,OnDismissListener{
	
	private Context context;
	
	private ImageView mLeftBackImg;
	
	private TextView mLeftTitleTv;
	
	private ExpandableListView mLeftCatalogueLv;
	
	private SectionAdatper adapter;
	
	public PopupWindow left;
	
	private ReadContract.Presenter mPersenter;

	private int currIndex;

	private View contentView;

	private RelativeLayout mLeftBackRlyt;
	
	public NovelLeftWindow(Context context,ReadContract.Presenter mPersenter) {
		this.context = context;
		this.mPersenter = mPersenter;
	}
	
	public PopupWindow initLeftView(){
		if(mPersenter.getReadMode() == ReadActivity.VERTICAL_MODE){
			this.currIndex = mPersenter.getClickCurrIndex();
		}else{
			this.currIndex = mPersenter.getCurrIndex();
		}
		if(left==null){
			contentView = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow_left,null);
			left = new PopupWindow(contentView);
			left.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);  
			left.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
			left.setAnimationStyle(R.style.ActionPopupWindowLeft);
			left.setFocusable(true);
			left.setBackgroundDrawable(new ColorDrawable(0x00000000));
			left.setOutsideTouchable(true);
			left.setOnDismissListener(this);
			initViewByLeft(contentView);
		}
		setBgColorByShowModel();
		initAdapter(mPersenter.getVolumeList());
		return left;
	}
	
	private void initViewByLeft(View contentView){
		mLeftBackRlyt = (RelativeLayout) contentView.findViewById(R.id.mLeftBackRlyt);
		mLeftCatalogueLv = (ExpandableListView) contentView.findViewById(R.id.mLeftCatalogueLv);
		mLeftBackImg = (ImageView) contentView.findViewById(R.id.mLeftBackImg);
		mLeftTitleTv = contentView.findViewById(R.id.mLeftTitleTv);
		//添加点击事件
		mLeftCatalogueLv.setOnGroupClickListener(this);
		mLeftCatalogueLv.setOnChildClickListener(this);;
		mLeftBackImg.setOnClickListener(this);
	}
	
	private void initAdapter(ArrayList<VolumeBean> mVolumeList){
		//设置适配器相关参数
		adapter = new SectionAdatper(context, mVolumeList);
		adapter.setBgIndex(mPersenter.getBgIndex());
		adapter.setCurrIndex(currIndex);
		adapter.setReadActivity(true);
		adapter.setNovelNameAndId(mPersenter.getStoryInfo().getName()+mPersenter.getStoryInfo().getId());
		//添加适配器
		mLeftCatalogueLv.setAdapter(adapter);
		mLeftCatalogueLv.setSelection(currIndex);
		Logger.i(currIndex   +  "      :      "  +  mLeftCatalogueLv.getSelectedPosition());
		//默认全部展开
		for (int i = 0; i < adapter.getGroupCount(); i++) {
			mLeftCatalogueLv.expandGroup(i);
		}
	}

	private void setBgColorByShowModel() {
		if(mPersenter.getBgIndex() == 4){
			mLeftCatalogueLv.setBackgroundResource(R.color.night_txt_bg);
			mLeftTitleTv.setBackgroundColor(ContextCompat.getColor(context, R.color.night_txt_bg));
			mLeftTitleTv.setTextColor(ContextCompat.getColor(context,R.color.textColorNight));
			mLeftBackImg.setBackgroundResource(R.drawable.btn_arrows_catalogue_back_night);
		}else{
			mLeftCatalogueLv.setBackgroundResource(android.R.color.white);
			mLeftTitleTv.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
			mLeftTitleTv.setTextColor(ContextCompat.getColor(context,R.color.textColorPrimary));
			mLeftBackImg.setBackgroundResource(R.drawable.btn_arrows_catalogue_back);
		}
	}

	@Override
	public void onClick(View v) {
		left.dismiss();
	}

	@Override
	public void onDismiss() {
		WindowManager.LayoutParams lp = ((FragmentActivity)context).getWindow().getAttributes();
        lp.alpha = 1f;
        ((FragmentActivity)context).getWindow().setAttributes(lp);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
		int position = mPersenter.getVolumeList().get(groupPosition).getChapterList().get(childPosition).getSortNo() - 1;
		mPersenter.readCurrChapter(position,true);
		return false;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		return false;
	}

}
