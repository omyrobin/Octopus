package com.jinying.octopus.read.model;

import android.content.Context;

import com.jinying.octopus.App;
import com.jinying.octopus.api.GetService;
import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.read.model.impl.IReadModel;
import com.jinying.octopus.read.persenter.impl.IListener;
import com.jinying.octopus.read.ui.ReadActivity;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.NetworkUtil;
import com.jinying.octopus.util.SharedPreferencesUtil;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

public class ReadModel implements IReadModel {
	
	private Context context;
	//章节Index
	private int currIndex;
	//正文的页码
	private int pageNum;
	//正文文字大小
	private int textSize;
	//正文文字间距
	private int intervalMode;
	//正文文字颜色
	private int textColor;
	//正文行高
	private int lineHeight;
	//正文背景颜色的下标
	private int bgIndex;
	//正文日间背景颜色的下标
	private int beforeBgIndex;
	//背景亮度
	private int brightness;
	//是否开启自动购买
	private boolean isAuto;
	//是否开启音量键翻页
	private boolean isVolume;
	//阅读模式
	private int readMode;
	//是否跟随系统亮度
	private boolean  isFlowingSystem;
	
	private StoryVoBean mStoryVo;
	
	private IListener mPersenter;
	
//	private DBManager mDBManager;
	
	public ReadModel(IListener mPersenter, StoryVoBean mStoryVo) {
		this.mPersenter = mPersenter;
        this.mStoryVo = mStoryVo;
		context = App.getInstance();
//		mDBManager = DBManager.getInstance(context);
		initConfig();
	}
	
	private void initConfig(){
		readMode = (int) SharedPreferencesUtil.getParam(context, Constant.READ_MODE,1);
		currIndex = (int) SharedPreferencesUtil.getParam(context, mStoryVo.getId()+"-"+Constant.CHAPTER_INDEX,0);
		pageNum = (int) SharedPreferencesUtil.getParam(context, mStoryVo.getId()+"-"+Constant.CHAPTER_PAGE,-1);
		isVolume = (boolean) SharedPreferencesUtil.getParam(context, Constant.VOLUME,false);
		textSize = (int) SharedPreferencesUtil.getParam(context, Constant.TXT_SIZE, 19);
		textColor = (int) SharedPreferencesUtil.getParam(context, Constant.TXT_COLOR_INDEX, 0);
		bgIndex = (int) SharedPreferencesUtil.getParam(context, Constant.BG_INDEX, 0);
		beforeBgIndex = (int) SharedPreferencesUtil.getParam(context, Constant.BG_SUN_INDEX,bgIndex);
		brightness = (int) SharedPreferencesUtil.getParam(context, Constant.BRIGHTNESS, 150);
		isFlowingSystem = (boolean) SharedPreferencesUtil.getParam(context, Constant.FLOWING_SYSYTEM,true);
		intervalMode = (int) SharedPreferencesUtil.getParam(context, Constant.INTERVALMODE, 1);
	}

	@Override
	public void downChapterContent(final String chapterId, final int which,final int mode){
		boolean isConnected = NetworkUtil.isConnected(App.getInstance());
		if (!isConnected) {
			mPersenter.showNovelDialog();
			mPersenter.onContentFail();
			return;
		}
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<String>>> observable = getService.getChapterContent(Url.GET_CHAPTER_CONTENT, chapterId, RetrofitHeaderConfig.getHeaders());
        RetrofitClient.client().compose(observable).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String content) {
				mPersenter.onContentSuccess(content, which, chapterId, mode);
            }
        });
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
		setLineHeight(textSize + 8);
		SharedPreferencesUtil.setParam(context, Constant.TXT_SIZE, textSize);
	}
	
	public int getLineHeight() {
		switch (intervalMode){
			case ReadActivity.LINE_HEIGHT_DESCE:
				lineHeight = textSize+5;
				break;

			case ReadActivity.LINE_HEIGHT_NORMAL:
				lineHeight = textSize+10;
				break;

			case ReadActivity.LINE_HEIGHT_SPARSE:
				lineHeight = textSize+15;
				break;
		}
		return lineHeight;
	}

	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}
	
	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
		SharedPreferencesUtil.setParam(context, Constant.TXT_COLOR_INDEX, textColor);
	}
	
	public int getBgIndex() {
		return bgIndex;
	}

	public void setBgIndex(int bgIndex) {
		this.bgIndex = bgIndex;
		SharedPreferencesUtil.setParam(context, Constant.BG_INDEX, bgIndex);
	}

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
		SharedPreferencesUtil.setParam(context, Constant.BRIGHTNESS, brightness);
	}

	public boolean isVolume() {
		return isVolume;
	}

	public void setVolume(boolean isVolume) {
		this.isVolume = isVolume;
		SharedPreferencesUtil.setParam(context, Constant.VOLUME,isVolume);
	}

	public int getCurrIndex() {
		return currIndex;
	}

	public void setCurrIndex(String id, int currIndex) {
		this.currIndex = currIndex;
		SharedPreferencesUtil.setParam(context, id+"-"+Constant.CHAPTER_INDEX,currIndex);
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(String id, int pageNum) {
		this.pageNum = pageNum;
		SharedPreferencesUtil.setParam(context, id+"-"+ Constant.CHAPTER_PAGE,pageNum);
	}

	public void setChapterName(String id, String name) {
//		mStoryVo.setReadingChapterName(name);
//		StoryVoEntity s = mDBManager.queryById(StoryVoEntity.class, MyApp.getInstance().getLocalId(), mStoryVo.getId()+"");
//		if(s!=null){
//			mDBManager.newOrUpdate(mStoryVo, MyApp.getInstance().getLocalId());
//		}
	}
	
	public void setUnReadingCount(String id, int count) {
//		mStoryVo.setUnReadingCount(count);
//		StoryVoEntity s = mDBManager.queryById(StoryVoEntity.class, MyApp.getInstance().getLocalId(), mStoryVo.getId()+"");
//		if(s!=null){
//			mDBManager.newOrUpdate(mStoryVo, MyApp.getInstance().getLocalId());
//		}
	}

	public int getReadMode() {
		return readMode;
	}

	public void setReadMode(int mode) {
		this.readMode = mode;
		SharedPreferencesUtil.setParam(context, Constant.READ_MODE,mode);
	}

	public void setBeforeBgIndex(int beforeBgIndex) {
		this.beforeBgIndex = beforeBgIndex;
		SharedPreferencesUtil.setParam(context, Constant.BG_SUN_INDEX,beforeBgIndex);
	}

	public int getBeforeBgIndex() {
		return beforeBgIndex;
	}

	public boolean isFlowingSystem() {
		return isFlowingSystem;
	}

	public void setFlowingSystem(boolean isFlowingSystem) {
		this.isFlowingSystem = isFlowingSystem;
		SharedPreferencesUtil.setParam(context, Constant.FLOWING_SYSYTEM, isFlowingSystem);
	}

	public void setIntervals(int intervalMode) {
		this.intervalMode = intervalMode;
		SharedPreferencesUtil.setParam(context, Constant.INTERVALMODE, intervalMode);
	}

	public int getIntervalMode() {
		return intervalMode;
	}

	public void updatChapterId(String chapterId) {
		PostService postService = RetrofitManager.getClient().create(PostService.class);
		Observable<Response<BaseResponse<String>>> observable = postService.updatChapterId(Url.SVAE_READING, chapterId, RetrofitHeaderConfig.getHeaders());
		RetrofitClient.client().compose(observable).subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(String content) {

			}
		});
	}
}
