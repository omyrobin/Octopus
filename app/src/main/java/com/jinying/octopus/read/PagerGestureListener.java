package com.jinying.octopus.read;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import com.jinying.octopus.util.Logger;

public class PagerGestureListener extends SimpleOnGestureListener{
	
	private IClickChageView mClick;
	
	private float velocityX;

	private double moveSlidingX;

	private double mFlingDistance;

	private float mMinimumVelocity;

	private boolean targetPage;

	public PagerGestureListener(IClickChageView mClick) {
		this.mClick = mClick;
	}
	
	public PagerGestureListener() {
		
	}
	
	public interface IClickChageView{
    	 void chickToChangeView(MotionEvent e);
    }

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
		if (Math.abs(moveSlidingX) > mFlingDistance && Math.abs(velocityX) > mMinimumVelocity) {
	     	targetPage = true;
	    }else{
	     	targetPage = false;
	    }
		Logger.i(Math.abs(velocityX) + "   ：    "+ mMinimumVelocity);
		Logger.i(" moveSlidingX : " + Math.abs(moveSlidingX) + ", mFlingDistance : "+ mFlingDistance);
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		if(mClick!=null){
			mClick.chickToChangeView(e);
		}
		return false;
	}

	public void setMoveSlidingX(double moveSlidingX) {
		this.moveSlidingX = moveSlidingX;
	}

	public void setmFlingDistance(double mFlingDistance) {
		this.mFlingDistance = mFlingDistance;
	}

	public void setmMinimumVelocity(float mMinimumVelocity) {
		this.mMinimumVelocity = mMinimumVelocity;
	}

	public boolean isTargetPage() {
		return targetPage;
	}
}
