package com.jinying.octopus.widget.control;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;

import com.jinying.octopus.App;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.read.ReadContract;
import com.jinying.octopus.util.DensityUtils;

/**
 * 阅读页所有popWindows的管理类
 * @author Administrator
 *
 */
public class NovelPopWindowsManager{
	
	public NovelTopWindow mTop;
	
	public NovelBottomWindow mBottom;
	
	public NovelLeftWindow mLeft;
	
	public NovelTopChildWindows mTopChild;
	
	private Context context;
	
	private StoryVoBean mStoryInfo;
	
	public View view;
	
	public NovelPopWindowsManager(Context context, ReadContract.Presenter mPersenter, StoryVoBean mStoryInfo) {
		this.context = context;
		this.mStoryInfo = mStoryInfo;
		
		mTop = new NovelTopWindow(context,mPersenter,this);
		mBottom = new NovelBottomWindow(context,mPersenter,this);
		mLeft = new NovelLeftWindow(context,mPersenter);
		mTopChild = new NovelTopChildWindows(context, mPersenter);
	}
	
	public void show(int index){
		switch (index) {
		case Constant.POPUPWINDOW_LEFT:
			mLeft.initLeftView().showAtLocation(((FragmentActivity) context).findViewById(R.id.content), Gravity.START, 0, 0);
			break;
			
		case Constant.POPUPWINDOW_TOP_CHILD:
			int xpos= (int) (App.width/2 - mTopChild.initView().getWidth()/2);
			mTopChild.initView().showAtLocation(((FragmentActivity) context).findViewById(R.id.content), Gravity.TOP, xpos, DensityUtils.dp2px(context, 70));
			break;

		default:
			mTop.initTopView(mStoryInfo.getName()).showAtLocation(((FragmentActivity) context).findViewById(R.id.content), Gravity.TOP, 0, 0);
			mBottom.initBottomView().showAtLocation(((FragmentActivity) context).findViewById(R.id.content), Gravity.BOTTOM, 0, 0);
			break;
		}
	}
	
	public boolean dismiss(){
		if(mLeft.left!=null&&mLeft.left.isShowing()){
			mLeft.left.dismiss();
			return true;
		}
		
		if(mTop.top!=null&&mTop.top.isShowing()||mBottom.bottom!=null&&mBottom.bottom.isShowing()){
			if(mTopChild.topChild!=null&&mTopChild.topChild.isShowing()){
				mTopChild.topChild.dismiss();
			}
			mTop.top.dismiss();
			mBottom.bottom.dismiss();
			return true;
		}
		return false;
	}

	public void dismissAll() {
		if(mLeft.left!=null&&mLeft.left.isShowing()){
			mLeft.left.dismiss();
		}
		if(mTopChild.topChild!=null&&mTopChild.topChild.isShowing()){
			mTopChild.topChild.dismiss();
		}
		if(mTop.top!=null&&mTop.top.isShowing()){
			mTop.top.dismiss();
		}
		if(mBottom.bottom!=null&&mBottom.bottom.isShowing()){
			mBottom.bottom.dismiss();
		}
	}
	
//	setBgColorByShowModel

}
