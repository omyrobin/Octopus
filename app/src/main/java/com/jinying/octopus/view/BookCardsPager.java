package com.jinying.octopus.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import com.jinying.octopus.App;
import com.jinying.octopus.bookshelf.BookShelfContract;
import com.jinying.octopus.util.Logger;

/**
 * Created by omyrobin on 2017/8/15.
 */

public class BookCardsPager extends ViewPager {

    private int lastPosition;

    private int pageSize;

    private float beforeX;

    private BookShelfContract.View mView;

    private VelocityTracker mVelocityTracker;

    private int mPointerId;//第一个触摸点

    private int mMaxVelocity;

    private float downX;

    private float moveX;

    private long currentMS;

    public BookCardsPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMaxVelocity = ViewConfiguration.get(context).getMaximumFlingVelocity();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();//获得VelocityTracker类实例
        }
        mVelocityTracker.addMovement(ev);//将事件加入到VelocityTracker类实例中
        final VelocityTracker verTracker = mVelocityTracker;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPointerId = ev.getPointerId(0);
                beforeX = ev.getX();
                downX = ev.getX();
                currentMS = System.currentTimeMillis();//long currentMS     获取系统时间
                break;

            case MotionEvent.ACTION_MOVE:
                verTracker.computeCurrentVelocity(1000, mMaxVelocity);
                final float velocityX = verTracker.getXVelocity(mPointerId);
                if(Math.abs(velocityX) >= mMaxVelocity - 1000){
                    return false;
                }
                float motionValue = ev.getX() - beforeX;
                moveX = ev.getX() - downX;
                if (lastPosition >= pageSize - 1){
                    if (motionValue < 0) {//禁止左滑
                        return false;
                    }
                }
                beforeX = ev.getX();
                break;

            case MotionEvent.ACTION_UP:
                //处理添加图书Item的点击事件
                long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                //判断是否继续传递信号
                if(moveTime>200 && Math.abs(moveX)>20){
                    return super.onTouchEvent(ev);
                }else if (lastPosition == pageSize - 1 && beforeX > App.width/2){
                    mView.addBook();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setBookShelfContractView(BookShelfContract.View mView){
        this.mView = mView;
    }
}
