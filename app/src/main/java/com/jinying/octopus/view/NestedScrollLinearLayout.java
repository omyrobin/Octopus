package com.jinying.octopus.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.jinying.octopus.R;


public class NestedScrollLinearLayout extends LinearLayout{
	
	private ViewGroup headerView;
	
	private ViewGroup footerView;
	
	private LinearLayout titleView;
	
	private int headerHeight;
	
	private int footerHeight;
	
	private int titleHeight;
	
	private int canScrollHeight;
	
	private RecyclerView recyclerView;
	
	private TextView mScreenTv;
	
	private Context context;
	
	private int touchSlop;
	
	private Scroller mScroller;

	private float mRawY; //down事件Y轴坐标

	private boolean ableToDown;

	private float mLastMoveY;

	private boolean ableToUp;

	private int offsetY;

	private boolean refresh;
	
	private boolean isLoadMore;
	
	private OnRefreshLoadMore refreshListener;

	public NestedScrollLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		mScroller = new Scroller(context, new LinearInterpolator());
		headerView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.header_tag_layout, null);
		titleView =  headerView.findViewById(R.id.title_layout);
		mScreenTv = headerView.findViewById(R.id.tv_screen);
		footerView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.footer_tag_layout, null);
		recyclerView = new RecyclerView(context);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		setOrientation(LinearLayout.VERTICAL);
		addView(headerView, 0);
		addView(recyclerView);
		init();
	}
	
	private void init(){
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		titleView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(getScrollY() == 0){
					mScreenTv.setVisibility(View.INVISIBLE);
					mScroller.startScroll(0, 0, 0, -canScrollHeight);
					invalidate();
				}else{
					mScroller.startScroll(0, getScrollY(), 0, Math.abs(getScrollY()));
					invalidate();
				}
			}
		});
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		headerHeight = headerView.getMeasuredHeight();
		titleHeight = titleView.getMeasuredHeight();
		canScrollHeight = headerHeight - titleHeight;
		headerView.layout(l, -canScrollHeight, r,titleHeight);
		recyclerView.layout(l, titleHeight, r, b);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mRawY = ev.getRawY();
			mLastMoveY = mRawY;
			break;
			
		case MotionEvent.ACTION_MOVE:
			float mMoveY = ev.getRawY();
			float diff = mMoveY - mRawY;
			mLastMoveY = mMoveY;
			if(Math.abs(diff) >= touchSlop){
				if(diff > 0){
					ableToUp = false;
					ableToDown = isAbleToDown();
					if(ableToDown){
						mScreenTv.setVisibility(View.INVISIBLE);
						return true;
					}
				}else{
					if(getScrollY() != 0){
						ableToDown = false;
						ableToUp = isAblaleToUp();
						if(ableToUp){
							return true;
						}
					}else{
						refresh = isAblaleToUpRefresh();
						if(refresh){
							ableToDown = false;
							ableToUp = false;
							return true;
						}
					}
				}
			}
			break;

		}
		return super.onInterceptTouchEvent(ev);
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float mMoveY = event.getRawY();
			offsetY = (int) (mMoveY - mLastMoveY);
			if(ableToDown){
				if(offsetY>0 && Math.abs(getScrollY()) < canScrollHeight){
					scrollBy(0, -offsetY);
				}
				if(offsetY<0){
					ableToDown = false;
					ableToUp = true;
					scrollBy(0, -offsetY);
				}
			}else if(ableToUp){
				if(offsetY<0 && getScrollY() < 0){
					scrollBy(0, -offsetY);
				}
				if(offsetY>0){
					ableToUp = false;
					ableToDown = true;
					scrollBy(0, -offsetY);
				}
			}else if(refresh){
				scrollBy(0, -offsetY/2);
				if(getScrollY() > titleHeight*0.7 && !isLoadMore){
					isLoadMore = true;
					refreshListener.onLoadMore();
				}
			}
			mLastMoveY = mMoveY;
			break;

		default:
			if(ableToDown){
				mScroller.startScroll(0, getScrollY(), 0, -(canScrollHeight+getScrollY()));
				invalidate();
				ableToDown = false;
			}
			if(ableToUp){
				mScroller.startScroll(0, getScrollY(), 0,-getScrollY());
				invalidate();
				ableToUp = false;
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
		if(mScroller.isFinished() && getScrollY() == 0){
			mScreenTv.setVisibility(View.VISIBLE);
		}
	}
	
	private boolean isAbleToDown(){
		return !recyclerView.canScrollVertically(-1);
	}
	
	private boolean isAblaleToUp() {
		if(getScrollY()!=0){
			return true;
		}
		return false;
	}
	
	private boolean isAblaleToUpRefresh() {
		return !recyclerView.canScrollVertically(1);
	}
	
	public ViewGroup getHeaderView() {
		return headerView;
	}

	public RecyclerView getRecyclerView() {
		return recyclerView;
	}

	public void setConditionTxt(String condition) {
		mScreenTv.setText(condition);
	}
	
	public void setRefreshListener(OnRefreshLoadMore refreshListener){
		this.refreshListener = refreshListener;
	}
	
	public interface OnRefreshLoadMore{
		void onLoadMore();
	}
	
	public void setRefreshComplete(){
		if(refresh){
			mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
			invalidate();
			refresh = false;
			isLoadMore = false;
		}
	}

}
