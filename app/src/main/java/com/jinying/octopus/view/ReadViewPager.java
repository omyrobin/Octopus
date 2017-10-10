package com.jinying.octopus.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.jinying.octopus.read.PagerGestureListener;
import com.jinying.octopus.read.persenter.ReadPersenter;
import com.jinying.octopus.read.ui.ReadActivity;
import com.jinying.octopus.util.Logger;

/**
 * 重写，添加了判定滑动方向的方法
 *
 */
public class ReadViewPager extends ViewPager implements PagerGestureListener.IClickChageView{
	
	private Context context;
	
	private ReadPersenter mPresenter;
	
    private float mLastMotionX;
    
    private ChangeViewCallback changeViewCallback = null;
    
//    private VelocityTracker mVelocityTracker;
    
    private static final int MIN_DISTANCE_FOR_FLING = 25; // dips
    
    private static final int MIN_FLING_VELOCITY = 400; // dips
	
	private int mMinimumVelocity;
	
//	private int mMaximumVelocity;
	
//	private int mActivePointerId;
    
    private ViewConfiguration configuration;
    
	private int mTouchSlop;//测试发现这个值是24
	
	private float moveSlidingX;// X轴方向的滑动距离
	
	private int mFlingDistance;
	
	private GestureDetector mGestureDetector;
	
	private PagerGestureListener mPagerGestureListener;
	
	public boolean isGestureToPre = true ,isGestureToNext = true; //手势方向
	    
	private int currIndex, beforeIndex = -1;
	
	private boolean pageCanNotToChange;

    public ReadViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ReadViewPager(Context context) {
        super(context);
        this.context = context;
        init();
    }
    
	public void setPresenter(ReadPersenter mPresenter) {
		this.mPresenter = mPresenter;
	}
	
    private void init() {
    	mPagerGestureListener = new PagerGestureListener(this);
    	mGestureDetector = new GestureDetector(context, mPagerGestureListener);  
        configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        final float density = context.getResources().getDisplayMetrics().density;
        mMinimumVelocity = (int) (MIN_FLING_VELOCITY * density);//最低速率
//        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mFlingDistance = (int) (MIN_DISTANCE_FOR_FLING * density);//最小滑动距离
        mPagerGestureListener.setmFlingDistance(mFlingDistance);
        mPagerGestureListener.setmMinimumVelocity(mMinimumVelocity);
        setLongClickable(true);
        
        addOnPageChangeListener(listener);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	float x = ev.getX();
    	switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			moveSlidingX = 0;
			mLastMotionX = x;
			if(mPresenter.getPreChapter()){
				Logger.i("刷新首页 设置pageNum = 0");
				mPresenter.chapterCheck(mPresenter.getCurrIndex(),ReadPersenter.FIRST, ReadPersenter.PAGE_MODE);
			}
			if(mPresenter.getNextChapter()){
				Logger.i("刷新尾页 设置pageNum = 尾页");
				mPresenter.chapterCheck(mPresenter.getCurrIndex(),ReadPersenter.LASTE, ReadPersenter.PAGE_MODE);
			}
			//需要限制条件
			reSetAllState();
			break;
			
		case MotionEvent.ACTION_MOVE:
			moveSlidingX = mLastMotionX - x;
			
			if(!isGestureToPre && moveSlidingX > 0 && mPresenter.getPageNum() >= mPresenter.getPagesVe().size() - 1){
				pageCanNotToChange = true;
				return false;
			}
			
			if(!isGestureToNext && moveSlidingX < 0 && mPresenter.getPageNum()<=0){
				pageCanNotToChange = true;
				return false;
			}
			//手指方向  → 并且满足最小滑动距离   查看上一页
			if (moveSlidingX < 0 && Math.abs(moveSlidingX) > mTouchSlop) {
				//章节切换阻止反方向手势
//				if(!isGestureToNext && mPresenter.getPageNum()<=0){
//					pageCanNotToChange = true;
//					return false;
//				}
				if (isGestureToPre) {
					if (mPresenter.prePage(ReadActivity.SMOOTHNESS_MODE)) {
						if (changeViewCallback != null) {
							changeViewCallback.setPreBackGround();
						}
						isGestureToPre = false;
					} else {
						Logger.i("不可滑动到上一页==============不分发事件");
						return false;
					}
				}
			}
			//手指方向  ← 并且满足最小滑动距离  查看下一页
			if (mLastMotionX - x > 0 && Math.abs(moveSlidingX) > mTouchSlop) {
				//章节切换阻止反方向手势
//				if (!isGestureToPre && mPresenter.getPageNum() >= mPresenter.getPagesVe().size() - 1) {
//					pageCanNotToChange = true;
//					return false;
//				}
				if (isGestureToNext) {
					Logger.i("开始尝试翻到下一页");
					if (mPresenter.nextPage(ReadActivity.SMOOTHNESS_MODE)) {
						Logger.i("可以翻到下一页==============分发事件");
						if (changeViewCallback != null) {
							changeViewCallback.setNextBackGround();
						}
						isGestureToNext = false;
					} else {
						Logger.i("不可滑动到下一页==============不分发事件");
						return false;
					}
				}
			}
			break;
			
		case MotionEvent.ACTION_UP:
			if (Math.abs(moveSlidingX) < mTouchSlop) {
				if (x > getWidth() / 3 && x < (getWidth() / 3) * 2) {
					mPresenter.showControlWindow();
				}
			}
			mPagerGestureListener.setMoveSlidingX(moveSlidingX);
			if(pageCanNotToChange){
				return false;
			}
			break;
		}
    	return super.dispatchTouchEvent(ev);
    }
    
    private void reSetAllState(){
    	if(!mPagerGestureListener.isTargetPage()){
    		mPresenter.isNextChapter(false);
    		mPresenter.isPreChapter(false);
    	}else{
    		Logger.i(" 满足翻页条件不重新设置标识 ");
    	}
		isGestureToPre = true;
		isGestureToNext = true;
		pageCanNotToChange = false;
    }
    
    @SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
    	mGestureDetector.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}

    public OnPageChangeListener listener = new OnPageChangeListener() {
    	
        @Override
        public void onPageScrollStateChanged(int arg0) {
        	changeViewCallback.onPageScrollStateChanged(arg0);
        }
        
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
		public void onPageSelected(int arg0) {
			currIndex = arg0;
			if (changeViewCallback != null) {
				if (currIndex != beforeIndex) {
					isGestureToPre = true;
					isGestureToNext = true;
				}
				beforeIndex = currIndex;
				changeViewCallback.onPageSelected(arg0);
			} 
		}
    };
    
    /**
     *  滑动状态改变回调
     */
    public interface ChangeViewCallback{
    	void onPageScrollStateChanged(int arg0);
        void onPageSelected(int index);
        void setPreBackGround();
        void setNextBackGround();
    }
    
    public void setChangeViewCallback(ChangeViewCallback callback){
        changeViewCallback = callback;
    }

	@Override
	public void chickToChangeView(MotionEvent e) {
		float x = e.getX();
		if(x>(getWidth()/3)*2){
			nextPage();
		}else if(x<getWidth()/3){
			prePage();
		}
	}
	
	public void nextPage(){
		if(mPresenter.nextPage(ReadActivity.SMOOTHNESS_MODE)){
			if(changeViewCallback!=null){
				changeViewCallback.setNextBackGround();
			}
			arrowScroll(2);
		}
	}
	
	public void prePage(){
		if(mPresenter.prePage(ReadActivity.SMOOTHNESS_MODE)){
			if(changeViewCallback!=null){
				changeViewCallback.setPreBackGround();
			}
			arrowScroll(1);
		}
	}
}