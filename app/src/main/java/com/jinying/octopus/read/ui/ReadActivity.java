package com.jinying.octopus.read.ui;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jinying.octopus.App;
import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.ChapterAllBean;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.VolumeAllBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.event.EventChapterList;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.event.EventSortBook;
import com.jinying.octopus.read.NovelChapterCache;
import com.jinying.octopus.read.ReadContract;
import com.jinying.octopus.read.VerticalExpandAdapter;
import com.jinying.octopus.read.end.KeepGoActivity;
import com.jinying.octopus.read.persenter.ReadPersenter;
import com.jinying.octopus.util.AddToBookShelfUtil;
import com.jinying.octopus.util.BrightnessUtil;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.FileUtil;
import com.jinying.octopus.util.HuaWeiUtil;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.SharedPreferencesUtil;
import com.jinying.octopus.util.ToastUtil;
import com.jinying.octopus.view.PageWidget;
import com.jinying.octopus.view.ReadViewPager;
import com.jinying.octopus.widget.control.NovelPopWindowsManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ClickableViewAccessibility")
public class ReadActivity extends BaseActivity implements ReadContract.View, OnTouchListener,OnClickListener, ReadViewPager.ChangeViewCallback,OnScrollListener,OnGroupClickListener,OnChildClickListener{
	
	private int w = (int) App.width;
	
	private int h = (int) App.height;
	
	private int virtualBarHeigh = 96;
	
	private RelativeLayout mReadModeRlyt;
	
	private View mControlPanelView;
	
	private ArrayList<View> list;
	
	private ReadViewPager viewPager;
	
	private PageWidget pageWidget;
	
	private ExpandableListView mExpandableListView;
	
	private LinearLayout mPlanLayout;
	
	private TextView mPlanTv;
	
	private ImageButton mPlanBackImgBtn;
	
	private Bitmap preBitmap, curBitmap, nextBitmap;
	
	private Canvas preCanvas, curCanvas, nextCanvas;
	
	private Canvas bottomBarCanvas;
	
	private ReadPersenter mPresenter;
	
	private boolean isShow;

	private View view;
	
	private ImageView verticl_bottom_bar;
	
	private TextView verticl_top_bar;
	
	private View mControlPayView;//控制付费章节购买的view
	
	private View mControlBatchPayView;//控制批量优惠订阅的view
	
	private View mControlAutoPayView;//控制自动购买下一章的view

	private AddToBookShelfUtil addUtils;
	
	private int readMode, beforeReadMode;

	private VerticalExpandAdapter adapter;//滑动适配器
	
	private GestureDetector verticalGes;
	
	private int mListViewHeight;

	private Bitmap bottomBarBitmap;
	
	private int load_state;//滑动模式下加载状态
	
	private static final int IDEL = 0;
	
	private static final int LOADING = 1;
	
	private boolean isNotifyData;
	
	private int groupIndex, childIndex;

	private boolean isNotifySize;
	
	private boolean isNotifyPageNum;
	
	public static final int SIMULAT_MODE = 0;//仿真
	
	public static final int SMOOTHNESS_MODE = 1;//平滑
	
	public static final int VERTICAL_MODE = 2;//上下

	public static final int LINE_HEIGHT_DESCE = 0;//密集

	public static final int LINE_HEIGHT_NORMAL = 1;//平常

	public static final int LINE_HEIGHT_SPARSE = 2;//稀疏
	
	public static final int DEFAULT_PAGE = 9000;
	
	private int index = DEFAULT_PAGE,beforeIndex = DEFAULT_PAGE;

	private SensorManager sensorManager;

	private Sensor ligthSensor;

	private MySensorListener sensorListener;
	
	private int pageCount;
	
	private long packedPosition;

	private long time;
	/**小说资源**/
	protected StoryVoBean mStoryVo;
	/**小说目录资源**/
	protected ChapterAllBean mChapter;
	/**小说分组目录资源**/
	protected VolumeAllBean mVolume;
	/**小说目录资源列表**/
	protected ArrayList<ChapterBean> mChapterList;
	/**小说分组目录资源列表**/
	protected ArrayList<VolumeBean> mVolumeList;
	/**章节ID**/
	protected String chapterId;
	/**章节ID在目录中下标Index**/
	protected int currIndex = 0;
	/**界面相关popUpWindow**/
	protected NovelPopWindowsManager mNovelPopWindows;
	/**数据库单例**/
//	protected DBManager mDBManager;

	/**隐藏状态栏**/
	protected void hideStatusBar() {
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
//		attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
//		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}
	/**显示状态栏**/
	protected void showStatusBar() {
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
//		attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	/**
	 * 观察----用户状态改变
	 * @param event
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventCutId event) {
		refreshContent();//刷新绘制
    }
	
	/**观察目录变更,修改相应的数据**/
	@Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventChapterList event) {
		setChapterList(event.getChapterList());
		setVolumeList(event.getVolumeList());
		mPresenter.refreshList();
    }
	
	public static void newInstance(Context context, StoryVoBean mStoryInfo,int currIndex) {
		newInstance(context,mStoryInfo,currIndex,0);
	}
	
	public static void newInstance(Context context, StoryVoBean mStoryInfo,int currIndex,long time) {
		Intent intent = new Intent(context, ReadActivity.class);
		intent.putExtra("StoryVoEntity", mStoryInfo);
		intent.putExtra("currIndex", currIndex);
		intent.putExtra("time", time);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	
	/**获取虚拟功能键高度 */
	@Override
	public int getVirtualBarHeigh() {
        int vh = 0;
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_read;
	}

	@Override
	protected void obtainIntent() {
		super.obtainIntent();
//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕不变暗
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);//允许窗口扩展到屏幕之外。
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mStoryVo = (StoryVoBean) getIntent().getSerializableExtra("StoryVoEntity");
		mChapter = 	NovelChapterCache.getCacheInstance().getmChapterAllEntity();
		mVolume = NovelChapterCache.getCacheInstance().getmVolumeAllEntity();
		currIndex = getIntent().getIntExtra("currIndex", 0);
		mChapterList = mChapter.getChapterList();
		mVolumeList = mVolume.getVolumeList();
		if (mStoryVo == null ||"0".equals(mStoryVo.getId()) || mChapterList == null || mChapterList.isEmpty() || mVolumeList == null) {
			finish();
		}else{
			//获取单例
//			mDBManager = DBManager.getInstance(this);
			//初始化退出时加入书架
			addUtils = new AddToBookShelfUtil(this, mStoryVo);
			//添加到阅读记录表中
//			mStoryVo.setTimestamp(System.currentTimeMillis());
//			mDBManager.newOrUpdate(mStoryVo,Constant.ALL);
			//修改章节更新提示红点
//			mStoryVo.extra2 = mStoryVo.chapterCount+"";
//			mDBManager.newOrUpdate(mStoryVo,App.getInstance().getLocalId());

			getContentResolver().registerContentObserver(Settings.System.getUriFor("navigationbar_is_min"), true, mNavigationStatusObserver);
		}
	}

	@Override
	protected void initializeView() {
		EventBus.getDefault().register(this);
		regiestBrightness();
		initPersenter();
		initUtils();
		initMode();
		initCanvas();
		initReadMode();// 阅读控件初始化
		setContentView(R.layout.activity_read);
		initView();
		setReadMode(readMode);
		initData();
	}
	
	private void regiestBrightness(){
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);  
        //获取Sensor对象  
        ligthSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);  
        
        sensorListener = new MySensorListener();
        
        /**注册监听光感强度*/
        sensorManager.registerListener(sensorListener, ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	private void initPersenter(){
		mPresenter = new ReadPersenter(this,this);
	}
	
	private void initUtils(){
		mNovelPopWindows = new NovelPopWindowsManager(this,mPresenter,mStoryVo);
	}
	
	private void initMode(){
		readMode = mPresenter.getReadMode();
		beforeReadMode = readMode;
	}
	
	private void initCanvas(){
		if(curBitmap!=null){
			curBitmap = null;
		}
		if(nextBitmap!=null){
			nextBitmap = null;
		}
		if(preBitmap!=null){
			preBitmap = null;
		}
		if(HuaWeiUtil.isHUAWEI()){
			if(HuaWeiUtil.checkDeviceHasNavigationBar(this)){
				virtualBarHeigh = getVirtualBarHeigh();
				curBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
				nextBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
				preBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
				mPresenter.screenHeight =  h;
			}else{
				curBitmap = Bitmap.createBitmap(w, h+virtualBarHeigh, Bitmap.Config.ARGB_8888);
				nextBitmap = Bitmap.createBitmap(w, h+virtualBarHeigh, Bitmap.Config.ARGB_8888);
				preBitmap = Bitmap.createBitmap(w, h+virtualBarHeigh, Bitmap.Config.ARGB_8888);
				mPresenter.screenHeight =  h+virtualBarHeigh;
			}
		}else{
			curBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			nextBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			preBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mPresenter.screenHeight = h;
		}
		curCanvas = new Canvas(curBitmap);
		nextCanvas = new Canvas(nextBitmap);
		preCanvas = new Canvas(preBitmap);
		
		//上下滑动时底部bar使用的canvas
		bottomBarBitmap = Bitmap.createBitmap(w, DensityUtils.dp2px(this, 30), Bitmap.Config.ARGB_8888);
		bottomBarCanvas = new Canvas(bottomBarBitmap);
		
		mPresenter.start();
	}
	
	private void setPageWidgetScreen(){
		if(pageWidget!=null){
			if(HuaWeiUtil.isHUAWEI()){
				if(HuaWeiUtil.checkDeviceHasNavigationBar(this)){
					pageWidget.setScreen(w, h);
				}else{
					pageWidget.setScreen(w, h+virtualBarHeigh);
				}
			}else{
				pageWidget.setScreen(w,h);
			}
		}
	}
	
	private void initReadMode() {
		switch (readMode) {
		case SIMULAT_MODE:
			if(pageWidget==null){
				pageWidget = new PageWidget(this);
				setPageWidgetScreen();
				pageWidget.setBitmaps(curBitmap, curBitmap);
				pageWidget.setOnTouchListener(this);
			}
			break;

		case SMOOTHNESS_MODE:
			if(viewPager == null){
				list = new ArrayList<>();
				for (int i = 0; i < 3; i++) {
					View view = new View(this);
					list.add(view);
				}
				viewPager = new ReadViewPager(this);
				viewPager.setPresenter(mPresenter);
			}
			break;
			
		case VERTICAL_MODE:
			if(mExpandableListView == null){
				mExpandableListView = new ExpandableListView(this);
				AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
				mExpandableListView.setPadding(0, DensityUtils.dp2px(this, 30), 0, 0);
				mExpandableListView.setLayoutParams(params);
				mExpandableListView.setVerticalScrollBarEnabled(false);
				mExpandableListView.setClipToPadding(false);
				mExpandableListView.setClipChildren(false);//设置可以滚动到padding区域
				mExpandableListView.setScrollingCacheEnabled(false);
				mExpandableListView.setFadingEdgeLength(0);
				mExpandableListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
				mExpandableListView.setBackgroundResource(Constant.BACKGROUND_RES[mPresenter.getBgIndex()]);
				mExpandableListView.setSelector(ContextCompat.getDrawable(this, R.drawable.selector_vertical_read));
				mExpandableListView.setCacheColorHint(ContextCompat.getColor(this, android.R.color.transparent));
				mExpandableListView.setDivider(null);
				mExpandableListView.setGroupIndicator(null);
				mExpandableListView.setOnScrollListener(this);
				mExpandableListView.setOnGroupClickListener(this);
				mExpandableListView.setOnChildClickListener(this);
				mExpandableListView.setOnTouchListener(this);
				mExpandableListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
		            public void onGlobalLayout() {
		                mListViewHeight = mExpandableListView.getHeight();
		            }
		        });
				mExpandableListView.setOnTouchListener(this);
				verticalGes = new GestureDetector(this,new VertilGestureListener());
			}
			break;
		}
	}
	
	private void initView(){
		mControlPayView = findViewById(R.id.mControlPayView);
		mControlBatchPayView = findViewById(R.id.mControlBatchPayView);
		mControlAutoPayView = findViewById(R.id.mControlAutoPayView);
		mControlPanelView = findViewById(R.id.mControlPanelView);
		mReadModeRlyt = (RelativeLayout) findViewById(R.id.mReadModeRlyt);
		mPlanLayout = (LinearLayout) findViewById(R.id.mPlanLayout);
		mPlanTv = (TextView) findViewById(R.id.mPlanTv);
		mPlanBackImgBtn = (ImageButton) findViewById(R.id.mPlanBackImgBtn);
		verticl_bottom_bar = (ImageView) findViewById(R.id.verticl_bottom_bar);
		verticl_top_bar = (TextView) findViewById(R.id.verticl_top_bar);
		
		mControlPayView.setOnClickListener(this);
		mControlBatchPayView.setOnClickListener(this);
		mControlAutoPayView.setOnClickListener(this);
		mControlPanelView.setOnClickListener(this);
		mPlanBackImgBtn.setOnClickListener(this);

		showLoadingPressbar();
	}
	
	@Override
	public void setReadMode(int mode) {
		switch (mode) {
		case SIMULAT_MODE:
			showButton();
			hideBottomBar();
			mControlPanelView.setVisibility(View.VISIBLE);
			mReadModeRlyt.addView(pageWidget);
			break;

		case SMOOTHNESS_MODE:
			showButton();
			hideBottomBar();
			mControlPanelView.setVisibility(View.GONE);
			mReadModeRlyt.addView(viewPager);
			break;
			
		case VERTICAL_MODE:
			hideButton();
			showBottomBar();
			mControlPanelView.setVisibility(View.GONE);
			mReadModeRlyt.addView(mExpandableListView);
			break;
		}
	}
	
	// 重设阅读模式
	@Override
	public void resetReadMode(int mode) {
		readMode = mode;
		if(readMode==beforeReadMode){
			return;
		}
		// 初始化阅读模式
		initReadMode();
		setReadMode(readMode);
		setContent();
		switch (beforeReadMode) {
		case SIMULAT_MODE:
			mReadModeRlyt.removeView(pageWidget);
			break;

		case SMOOTHNESS_MODE:
			mReadModeRlyt.removeView(viewPager);
			break;
			
		case VERTICAL_MODE:
			mReadModeRlyt.removeView(mExpandableListView);
			break;
		}
		beforeReadMode = readMode;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void setContent() {
		switch (readMode) {
		case SIMULAT_MODE:
			mPresenter.draw(curCanvas);
			mPresenter.draw(nextCanvas);
			pageWidget.setBitmaps(curBitmap, curBitmap);
			pageWidget.postInvalidate();
			break;

		case SMOOTHNESS_MODE:
			viewPager.setAdapter(new PagerViewAdapter());
			viewPager.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					viewPager.setCurrentItem(DEFAULT_PAGE,false);
				}
			}, 1000);
			viewPager.setChangeViewCallback(this);
			if(Build.VERSION.SDK_INT > 16){
				mPresenter.draw(curCanvas);
				list.get(0).setBackground(new BitmapDrawable(getResources(),curBitmap));
				mPresenter.draw(nextCanvas);
				list.get(1).setBackground(new BitmapDrawable(getResources(),nextBitmap));
				mPresenter.draw(preCanvas);
				list.get(2).setBackground(new BitmapDrawable(getResources(),preBitmap));
			}else{
				mPresenter.draw(curCanvas);
				list.get(0).setBackgroundDrawable(new BitmapDrawable(getResources(),curBitmap));
				mPresenter.draw(nextCanvas);
				list.get(1).setBackgroundDrawable(new BitmapDrawable(getResources(),nextBitmap));
				mPresenter.draw(preCanvas);
				list.get(2).setBackgroundDrawable(new BitmapDrawable(getResources(),preBitmap));
			}
			break;
			
		case VERTICAL_MODE:
			setVerticalAdapter();
			mExpandableListView.setSelectedGroup(0);
			mExpandableListView.setSelectedChild(0, mPresenter.getPageNum(), true);
			
			mPresenter.draw(bottomBarCanvas);
			verticl_bottom_bar.setImageBitmap(bottomBarBitmap);
			verticl_top_bar.setTextColor(ContextCompat.getColor(this,Constant.TEXT_COLORS[mPresenter.getTxtColor()]));
			verticl_top_bar.setText(mChapterList.get(mPresenter.getCurrIndex()).getName());
			break;
		}
	}
	
	private void initData(){
//		Logger.i("currIndex  " + currIndex  + "当前书的目录长度" + mChapterList.size());
		mPresenter.setCurrIndex(currIndex);
		mPresenter.setChapter(currIndex);
		mPresenter.chapterCheck(currIndex,ReadPersenter.NEXT,ReadPersenter.FIRST_MODE);
	}
	
	@Override
	public void setPresenter(ReadContract.Presenter presenter) {
		refreshContent();
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void refreshContent() {
		switch (readMode) {
		case SIMULAT_MODE:
			mPresenter.draw(curCanvas);
			mPresenter.draw(nextCanvas);
			pageWidget.setBitmaps(curBitmap, nextBitmap);
			pageWidget.postInvalidate();
			break;

		case SMOOTHNESS_MODE:
			mPresenter.configurationInfo(mPresenter.getCurrIndex());
//			mPresenter.setChapter(mPresenter.getCurrIndex());
			viewPager.isGestureToNext = true;
			viewPager.isGestureToPre = true;
			if(Build.VERSION.SDK_INT > 16){
				mPresenter.draw(curCanvas);
				list.get(0).setBackground(new BitmapDrawable(getResources(),curBitmap));
				mPresenter.draw(nextCanvas);
				list.get(1).setBackground(new BitmapDrawable(getResources(),nextBitmap));
				mPresenter.draw(preCanvas);
				list.get(2).setBackground(new BitmapDrawable(getResources(),preBitmap));
			}else{
				mPresenter.draw(curCanvas);
				list.get(0).setBackgroundDrawable(new BitmapDrawable(getResources(),curBitmap));
				mPresenter.draw(nextCanvas);
				list.get(1).setBackgroundDrawable(new BitmapDrawable(getResources(),nextBitmap));
				mPresenter.draw(preCanvas);
				list.get(2).setBackgroundDrawable(new BitmapDrawable(getResources(),preBitmap));
			}
			break;
			
		case VERTICAL_MODE:
			showBottomBar();
			mPresenter.draw(bottomBarCanvas);//刷新上下bar
			verticl_bottom_bar.setImageBitmap(bottomBarBitmap);
			verticl_top_bar.setTextColor(ContextCompat.getColor(this,Constant.TEXT_COLORS[mPresenter.getTxtColor()]));
			verticl_top_bar.setText(mChapterList.get(mPresenter.getCurrIndex()).getName());
			//背景
			mExpandableListView.setBackgroundResource(Constant.BACKGROUND_RES[mPresenter.getBgIndex()]);
			
			adapter.setTextColor(Constant.TEXT_COLORS[mPresenter.getTxtColor()]);
			adapter.setLineHeight(mPresenter.getLineHeight());
			
			if(isNotifySize){
				setVerticalAdapter();
				isNotifySize = false;
			}else if(isNotifyData){//刷新内容
				setVerticalAdapter();
				mExpandableListView.setSelectedGroup(0);
				mExpandableListView.setSelectedChild(0, 0, true);
				isNotifyData = false;
			}else if(isNotifyPageNum){//刷新进度
				adapter.notifyDataSetChanged();
				mExpandableListView.setSelectedGroup(groupIndex);
				mExpandableListView.setSelectedChild(groupIndex, mPresenter.getPageNum(), false);
				isNotifyPageNum = false;
			}else{
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}
	
	@Override
	public void showLoadingPressbar(){
		hideStatusBar();
	}

	private void setVerticalAdapter(){
		//获取数据
		ArrayList<ArrayList<ChapterBean>> datasArray = new ArrayList<>();
		ArrayList<ChapterBean> datas = mPresenter.getDatas();
		datasArray.add(datas);
		
		adapter = new VerticalExpandAdapter(datasArray,this, mPresenter);
		adapter.setTextSize(mPresenter.getTxtSize());
		adapter.setTextColor(Constant.TEXT_COLORS[mPresenter.getTxtColor()]);
		adapter.setLineHeight(mPresenter.getLineHeight());
		mExpandableListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent e) {
		boolean ret = false;
		if (v == pageWidget) {
			if (e.getAction() == MotionEvent.ACTION_DOWN) {
				dismissAll();
				hideStatusBar();
				if(mPresenter.isLastChapterPage() && e.getX()>w/2){ // 防止翻至最后一章最后一页时卡住
					isLastPage();
					return false;
				}
				pageWidget.abortAnimation();
				pageWidget.calcCornerXY(e.getX(), e.getY());
				mPresenter.draw(curCanvas);
				if (pageWidget.DragToRight()) {
					if(mPresenter.prePage(ReadActivity.SIMULAT_MODE)){
						mPresenter.draw(nextCanvas);
					}else{
						return false;
					}
				} else {
					if (mPresenter.nextPage(ReadActivity.SIMULAT_MODE)){
						mPresenter.draw(nextCanvas);
					}else{
						return false;
					}
				}
				pageWidget.setBitmaps(curBitmap, nextBitmap);
			}
			ret = pageWidget.doTouchEvent(e);
			if(e.getAction() == MotionEvent.ACTION_UP){
				pageWidget.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mPresenter.draw(curCanvas);
						pageWidget.setBitmaps(curBitmap, nextBitmap);
					}
				}, 200);
				
			}
			return ret;
		}else if(v == mExpandableListView){
			return verticalGes.onTouchEvent(e);
		}
		return false;
	}
	
	private int firstVisibleItem;

	private int visibleItemCount;

	private int totalItemCount;
	
	class VertilGestureListener extends SimpleOnGestureListener{
		
		private static final int FLING_MIN_DISTANCE = 20;//mListView  滑动最小距离
	    private static final int FLING_MIN_VELOCITY = 200;//mListView 滑动最大速度

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,float distanceX, float distanceY) {
			dismissAll();
			hideStatusBar();
			// 向下滑动
            if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE ) {
            	// 判断滚动到顶部
				if (firstVisibleItem == 0) {
				    View firstVisibleItemView = mExpandableListView.getChildAt(0);
				    if (firstVisibleItemView != null && firstVisibleItemView.getTop()-mExpandableListView.getPaddingTop() == 0) {
				    	//TODO 设置当前Index
				    	mPresenter.setChapter(adapter.getDatas().get(0).get(0).getGroupId());
		            	loadingPreChapter();
				    }
				}
            }
			//向上滑动
			else if(e1.getY() - e2.getY() > FLING_MIN_DISTANCE) {
				// 判断滚动到底部
				if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
					View lastVisibleItemView = mExpandableListView.getChildAt(mExpandableListView.getChildCount() - 1);
					if ((lastVisibleItemView != null && lastVisibleItemView.getBottom() == mListViewHeight) || 
							(adapter.getGroupCount()==1&&adapter.getChildrenCount(0)==1)) {
						if (mExpandableListView.getLastVisiblePosition() == mExpandableListView.getCount() - 1) {
							//TODO 设置当前Index
					    	int lastGroupIndex = adapter.getDatas().size()-1;
					    	mPresenter.setChapter(adapter.getDatas().get(lastGroupIndex).get(0).getGroupId());
							loadingNextChapter();
						}
					}
				}
	        }
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);//先刷新Intent
		obtainIntent();
		mPresenter.initConfig();
		initData();
	}


	@Override
	protected void onResume() {
		super.onResume();
		register();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregister();
		//防止窗体内存泄露
		if(addUtils!=null){
			addUtils.isShow();
		}
		if(mNovelPopWindows!=null){
			dismissAll();
		}
		if(mPresenter!=null){
			if(readMode == VERTICAL_MODE){
				if((adapter.getDatas()!=null)){
					mPresenter.setChapter(adapter.getDatas().get(groupIndex).get(0).getGroupId());
				}
			}
			mPresenter.saveData();
		}
		EventBus.getDefault().post(new EventSortBook());
		saveOutData();
		
	}
	
	private boolean isFirstRegister = true;//首次接收到电池电量广播
	
	private void register() {  
		/**注册电池电量*/
        registerReceiver(batteryChangedReceiver,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));  
        
        /**注册监听系统亮度改变事件*/
		getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS),true, Brightness);
		
		/**注册监听系统亮度模式改变事件*/
		getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE),true, BrightnessMode);
    }  
      
    private void unregister() {  
    	/**取消注册电池电量*/
        unregisterReceiver(batteryChangedReceiver);  
        
        /**取消注册监听系统亮度改变事件*/
        getContentResolver().unregisterContentObserver(Brightness);
        
        /**取消注册监听系统亮度模式改变事件*/
        getContentResolver().unregisterContentObserver(BrightnessMode);
        
        /**取消注册监听底部虚拟导航栏高度事件*/
        getContentResolver().unregisterContentObserver(mNavigationStatusObserver);
    }  
    
    /**
     * 时刻监听系统亮度改变事件
     */
    private ContentObserver Brightness = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            System.out.println("亮度发生改变");
            if(mPresenter.isFlowingSystem()){
            	mPresenter.updateSystemBrightness();
            }
        }
    };
    
    /**
     * 时刻监听系统亮度模式改变事件
     */
    private ContentObserver BrightnessMode = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            boolean isAuto = BrightnessUtil.IsAutoBrightness(ReadActivity.this);
            System.out.println("自动亮度调节是否开启：" + isAuto);
            /**注册监听光感强度*/
            sensorManager.registerListener(sensorListener, ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
            /**取消注册监听系统亮度模式改变事件*/
            getContentResolver().unregisterContentObserver(BrightnessMode);
            
            mPresenter.setAutoBrightness(isAuto);
            if(isAuto){
            	mPresenter.setSystemBrightness(brightness);
            }else{
            	mPresenter.updateSystemBrightness();
            }
            /**注册监听系统亮度模式改变事件*/
    		getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE),true, BrightnessMode);
    		
        }
    };
    
    private int brightness;
    
    @Override
	public int getBrightness() {
		return brightness;
	}

	public class MySensorListener implements SensorEventListener {  

		public void onAccuracyChanged(Sensor sensor, int accuracy) {  
			
        }  
  
        public void onSensorChanged(SensorEvent event) {  
            //获取精度  
            float acc = event.accuracy;  
            //获取光线强度  
            float lux = event.values[0];
            if(lux<200){
            	brightness = 85;
            }else if(lux < 400){
            	brightness = 115;
            }else if(lux < 1000){
            	brightness = 120;
            }else if(lux < 3000){
            	brightness = 150;
            }else {
            	brightness = 250;
            }
            sensorManager.unregisterListener(sensorListener, ligthSensor);
            Logger.i("brightness : " + lux);
        }  
    }  

	// 接收广播
    private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {  

		public void onReceive(Context context, Intent intent) {  
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {  
                int level = intent.getIntExtra("level", 0);  
                int scale = intent.getIntExtra("scale", 100);  
                int power = level * 100 / scale;  
                mPresenter.setPower(power);  
            }  
            if(isFirstRegister){//解决接收延迟,界面已经绘制问题
            	mPresenter.draw(curCanvas);
            	isFirstRegister = false;
            }
        }  
    };

	@Override
	public StoryVoBean getStoryInfo() {
		return mStoryVo;
	}
	
//	@Override
//	public DBManager getDBManager() {
//		return mDBManager;
//	}
	
	@Override
	public String getStoryDir() {
		return mStoryVo.getName()+mStoryVo.getId();
	}
	
	@Override
	public ArrayList<ChapterBean> getChapterList() {
		return mChapterList;
	}
	
	@Override
	public ArrayList<VolumeBean> getVolumeList() {
		return mVolumeList;
	}
	
	@Override
	public void setVolumeList(ArrayList<VolumeBean> mVolumeList) {
		this.mVolumeList = mVolumeList;
		mVolume.setVolumeList(mVolumeList);
	}

	@Override
	public void setChapterList(ArrayList<ChapterBean> mChapterList) {
		this.mChapterList = mChapterList;
		mChapter.setChapterList(mChapterList);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mControlPanelView:
			showControlWindow();
			break;
			
		case R.id.mPlanBackImgBtn:
			mPresenter.readPlan(mPresenter.getStarPlan());
			setReadPlan(mPresenter.getStarPlan());
//			mNovelPopWindows.mBottom.setStarPaln();
			break;
		}
	}
	
	@Override
	public void showControlWindow(){
		isShow = !isShow;
		if(isShow){
			mNovelPopWindows.show(Constant.POPUPWINDOW_TOB_BOTTOM);
			showStatusBar();
		}else{
			dismissAll();
			hideStatusBar();
		}
	}

	public void showButton() {
		if(mControlPayView!=null && mControlAutoPayView!=null){
			mControlPayView.setVisibility(View.VISIBLE);
			mControlBatchPayView.setVisibility(View.VISIBLE);
			mControlAutoPayView.setVisibility(View.VISIBLE);
		}
	}

	public void hideButton() {
		if(mControlPayView!=null && mControlAutoPayView!=null){
			mControlPayView.setVisibility(View.GONE);
			mControlBatchPayView.setVisibility(View.GONE);
			mControlAutoPayView.setVisibility(View.GONE);
		}
	}
	
	private void showBottomBar() {
		verticl_top_bar.setVisibility(View.VISIBLE);
		verticl_top_bar.setBackgroundResource(Constant.TOPBAR_BACKGROUND_RES[mPresenter.getBgIndex()]);
		
		verticl_bottom_bar.setVisibility(View.VISIBLE);
		verticl_bottom_bar.setBackgroundResource(Constant.BOTTOMBAR_BACKGROUND_RES[mPresenter.getBgIndex()]);
	}
	
	private void hideBottomBar() {
		if(verticl_bottom_bar == null){
			return;
		}
		if(verticl_top_bar == null){
			return;
		}
		verticl_top_bar.setVisibility(View.GONE);
		verticl_bottom_bar.setVisibility(View.GONE);
	}

	@Override
	public void showLeftWindows() {
		mNovelPopWindows.dismissAll();
		mNovelPopWindows.show(Constant.POPUPWINDOW_LEFT);
		
		WindowManager.LayoutParams lp = getWindow().getAttributes();//背景变暗
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
	}
	
	@Override
	public void showTopChild() {
		mNovelPopWindows.show(Constant.POPUPWINDOW_TOP_CHILD);
	}

	@Override
	public void dismissAll() {
		isShow = false;
		mNovelPopWindows.dismissAll();
		setPlanTvGone();
	}
	
	@Override
	public void initNovelDetails() {
		dismissAll();
		hideStatusBar();
		BookDetailsActivity.newInstance(this, mStoryVo.getId()+"");
	}
	
	@Override
	public void onContentFail() {
		ToastUtil.showShort(this,"内容获取失败,请检查网络");
	}

	@Override
	public void onChpterFail() {
//		Toaster.showShort("目录刷新失败,请检查网络");
	}

	@Override
	public void setReadPlan(int plan) {
		// TODO 设置一个现实的TextView 阅读进度 或者章节标题
		if(mPlanLayout.getVisibility() == View.GONE){
			mPlanLayout.setVisibility(View.VISIBLE);
		}
		mPlanTv.setText(mChapterList.get(plan).getName());
		
//		if(plan == -1){
//			mPlanTv.setText(mChapterList.get(mPresenter.getCurrIndex()).name);
//		}else{
//			mPlanTv.setText(mChapterList.get(mPresenter.getCurrIndex()).name+" : " + plan+"%");
//		}
	}
	
	@Override
	public void setPlanTvGone() {
		if(mPlanTv!=null && mPlanLayout.getVisibility() == View.VISIBLE){
			mPlanLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void isFirstChapter() {
		ToastUtil.showShort(this,"已经是第一章了");
	}

	@Override
	public void isFirstPage() {
		ToastUtil.showShort(this,"已经是第一页了");
	}

	@Override
	public synchronized void isLastPage() {
		KeepGoActivity.newInstance(this, mStoryVo);
	}

	@Override
	public void showAddDialog() {
		addUtils.show();
	}

	@Override
	public void finishActivity() {
		finish();
	}

	public class PagerViewAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		}
		
		@SuppressWarnings("deprecation")
		@SuppressLint("NewApi")
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ViewParent parent = list.get(position%list.size()).getParent();
			Drawable background = list.get(position%list.size()).getBackground();
			if(parent!=null){
				container.removeView(list.get(position%list.size()));
			}
			if(Build.VERSION.SDK_INT > 16){
				list.get(position%list.size()).setBackground(background);
			}else{
				list.get(position%list.size()).setBackgroundDrawable(background);
			}
			container.addView(list.get(position%list.size()));
			return list.get(position%list.size());
		}
		
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (arg0 == 1){
			dismissAll();
			hideStatusBar();
		}
	}

	@Override
	public void onPageSelected(int index) {
		if(index != beforeIndex){
			dismissAll();
		}
		hideStatusBar();
		this.index = index;
		if(index<beforeIndex){
			mPresenter.setPageNumDown();
		}else if(index>beforeIndex){
			mPresenter.setPageNumUp();
		}else{
			return;
		}
		if (mPresenter.getPageNum() <= 0 || mPresenter.getPageNum() >= mPresenter.getPagesVe().size() - 1) {
			if (mPresenter.getNextChapter()) {
				Logger.i("omyrobin", " ++currIndex");
				mPresenter.setCurrIndexUp();
			} else if (mPresenter.getPreChapter()) {
				Logger.i("omyrobin", "--currIndex");
				mPresenter.setCurrIndexDown();
			}
		}
		beforeIndex = index;
		mPresenter.setNextToPre(false);
		mPresenter.setPretToNext(false);
		mPresenter.setIsNotChangePager(false);
		mPresenter.isNextChapter(false);
		mPresenter.isPreChapter(false);
		Logger.i("执行结束 ： onPageSelected");
	}

	@Override
	public void setPreBackGround() {
		int pageIndex = (index-1)%list.size();
		setScrollModeBackGroud(pageIndex);
	}

	@Override
	public void setNextBackGround() {
		int pageIndex = (index+1)%list.size();
		setScrollModeBackGroud(pageIndex);
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void setScrollModeBackGroud(int pageIndex){
		View view = list.get(pageIndex);
		switch (pageIndex) {
		case 0:
			mPresenter.draw(curCanvas);
			if(Build.VERSION.SDK_INT > 16){
				view.setBackground(new BitmapDrawable(getResources(),curBitmap));
			}else{
				view.setBackgroundDrawable(new BitmapDrawable(getResources(),curBitmap));
			}
			break;
			
		case 1:
			mPresenter.draw(nextCanvas);
			if(Build.VERSION.SDK_INT > 16){
				view.setBackground(new BitmapDrawable(getResources(),nextBitmap));
			}else{
				view.setBackgroundDrawable(new BitmapDrawable(getResources(),nextBitmap));
			}
			break;
			
		case 2:
			mPresenter.draw(preCanvas);
			if(Build.VERSION.SDK_INT > 16){
				view.setBackground(new BitmapDrawable(getResources(),preBitmap));
			}else{
				view.setBackgroundDrawable(new BitmapDrawable(getResources(),preBitmap));
			}
			break;
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		this.visibleItemCount = visibleItemCount;
		this.totalItemCount = totalItemCount;
		if(adapter!=null && verticl_top_bar!=null && adapter.getDatas()!=null){
			long packedPosition = mExpandableListView.getExpandableListPosition(firstVisibleItem);
			if(this.packedPosition != packedPosition){
				int groupIndex = ExpandableListView.getPackedPositionGroup(packedPosition);
				int childIndex = ExpandableListView.getPackedPositionChild(packedPosition);
				
//				Logger.i("childIndex :  " + childIndex);
				if(childIndex>=0){
					verticl_top_bar.setText(adapter.getDatas().get(groupIndex).get(childIndex).getName());
				}else{
					childIndex = 0;
				}
				mPresenter.setPageNum(childIndex);//切换有异常
				mPresenter.draw(bottomBarCanvas);
				verticl_bottom_bar.setImageBitmap(bottomBarBitmap);
				
				this.childIndex = childIndex;
				this.groupIndex = groupIndex;
				this.packedPosition = packedPosition;
			}
		}
	}
	
	private void loadingPreChapter(){
		Logger.i("开始加载上一章的数据");
		if(load_state == IDEL && mPresenter.scrollPre()){
			load_state = LOADING;
			ArrayList<ChapterBean> datas = new ArrayList<>();
    		datas = mPresenter.getDatas();
    		
			if(adapter!=null){
    			adapter.addPreDatas(datas);
    			mExpandableListView.setSelectedGroup(0);
    			mExpandableListView.setSelectedChild(0, datas.size()-1, false);
				load_state = IDEL;
			}
		}else{
			if(mPresenter.getCurrIndex() == 0){
				isFirstPage();
			}
		}
	}
	
	private void loadingNextChapter(){
		Logger.i("开始加载下一章的数据");
    	if(load_state == IDEL && mPresenter.scrollNext() ){
    		load_state = LOADING;
    		ArrayList<ChapterBean> datas = new ArrayList<>();
    		datas = mPresenter.getDatas();
    		
			if(adapter!=null){
				adapter.addNextDatas(datas);
				load_state = IDEL;
			}
		}else{
			if(mPresenter.getCurrIndex() == mChapterList.size()-1){
				isLastPage();
			}
		}
	}
	
	@Override
	public void notifySize() {
		isNotifySize = true;
	}
	
	@Override
	public void notifyData() {
		isNotifyData = true;
	}
	
	@Override
	public void notifyPageNum() {
		isNotifyPageNum = true;
	}

	@Override
	public void onExpandGroup(){
		for (int i = 0; i < adapter.getGroupCount(); i++) {
			mExpandableListView.expandGroup(i);
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
		mPresenter.setClickCurrIndex(adapter.getDatas().get(groupIndex).get(childIndex).getGroupId());
		showControlWindow();
		return false;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
		return true;
	}
	
	private boolean isCancle;//取消
	
	private void openVolumeRead(){
		//使用v7的dialog
		AlertDialog.Builder builder=new AlertDialog.Builder(this, R.style.BaseAlterDiaLog);  //先得到构造器
        builder.setTitle("提示"); //设置标题  
        builder.setMessage("是否启用音量按键翻页阅读"); //设置内容
        builder.setPositiveButton("启用", new DialogInterface.OnClickListener() { //设置确定按钮  
            @Override  
            public void onClick(DialogInterface dialog, int which) {
            	mPresenter.setVolume(true);
            }  
        });  
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置确定按钮  
			@Override  
            public void onClick(DialogInterface dialog, int which) {
            	isCancle = true;
            }  
        });  
        builder.create().show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//点击触发先收回控制栏
		if(mNovelPopWindows!=null && mNovelPopWindows.dismiss()){
			hideStatusBar();
			return false;
		}
		
		switch (keyCode) {  
        case KeyEvent.KEYCODE_VOLUME_DOWN:
        	if(isCancle && !mPresenter.isVolume()){
        		return false;
        	}
        	setPlanTvGone();
        	if(!mPresenter.isVolume()  && mPresenter.getReadMode() != VERTICAL_MODE){
        		openVolumeRead();
        		return true;
        	}
        	if(event.getRepeatCount()==0){
	        	switch (mPresenter.getReadMode()) {
				case SIMULAT_MODE:
					pageWidget.abortAnimation();
					pageWidget.calcCornerXY(w-1, h-1);
					mPresenter.draw(curCanvas);
					if (mPresenter.nextPage(ReadActivity.SIMULAT_MODE)){
						mPresenter.draw(nextCanvas);
					}else{
						return false;
					}
					pageWidget.setBitmaps(curBitmap, nextBitmap);
					pageWidget.doTouchEvent(w-1,h-1);
					break;
					
				case SMOOTHNESS_MODE:
					viewPager.nextPage();
					break;
	
				default:
					ToastUtil.showShort(this,"上下滑动不支持音量键翻页");
					break;
				}
        	}
            return true;  
        case KeyEvent.KEYCODE_VOLUME_UP:  
        	if(isCancle  && !mPresenter.isVolume()){
        		return false;
        	}
        	if(!mPresenter.isVolume() && mPresenter.getReadMode() != VERTICAL_MODE){
        		openVolumeRead();
        		return true;
        	}
        	if(event.getRepeatCount()==0){
        		switch (mPresenter.getReadMode()) {
    			case SIMULAT_MODE:
    				pageWidget.abortAnimation();
    				pageWidget.calcCornerXY(1, h-1);
    				mPresenter.draw(curCanvas);
    				if(mPresenter.prePage(ReadActivity.SIMULAT_MODE)){
    					mPresenter.draw(nextCanvas);
    				}else{
    					return false;
    				}
    				pageWidget.setBitmaps(curBitmap, nextBitmap);
    				pageWidget.doTouchEvent(1,h-1);
    				break;
    				
    			case SMOOTHNESS_MODE:
    				viewPager.prePage();
    				break;

    			default:
    				ToastUtil.showShort(this,"上下滑动不支持音量键翻页");
    				break;
    			}
        	}
            return true;  
        case KeyEvent.KEYCODE_BACK:
			List<Fragment> fragments = getSupportFragmentManager().getFragments();
			if(fragments!=null && !fragments.isEmpty()){
				for (int i = 0; i < fragments.size(); i++) {
					fragments.remove(null);
				}
			}
			if(fragments!=null && fragments.size()>0){
				return super.onKeyDown(keyCode, event);
			}
			mPresenter.finishActivity();
        	break;
        }  
		return super.onKeyDown(keyCode, event);
	}
	
	private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
	    @Override
	    public void onChange(boolean selfChange) {
	        int navigationBarIsMin = Settings.System.getInt(getContentResolver(),"navigationbar_is_min", 0);
	        initCanvas();
	        setPageWidgetScreen();
	        new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
//					mPresenter.initConfig();
//		        	mPresenter.initPaint();
		        	mPresenter.chapterCheck(mPresenter.getCurrIndex(),ReadPersenter.NEXT,ReadPersenter.FIRST_MODE);
		        	refreshContent();
				}
			}, 30);        	
	    }
	};
	
	@Override
	public void statusBar() {
		hideStatusBar();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//"结束阅读"事例
		EventBus.getDefault().unregister(this);
		if(preBitmap!=null){
			preBitmap.recycle();
			preBitmap = null;
		}
		if(curBitmap!=null){
			curBitmap.recycle();
			curBitmap = null;
		}
		if(nextBitmap!=null){
			nextBitmap.recycle();
			nextBitmap = null;
		}
//		clearOutData();
	}
	
	private void saveOutData(){
		Gson gson = new Gson();
		String jsonStr = gson.toJson(mStoryVo);
		FileUtil.writeTextFile(FileUtil.getReadCacheFile(), jsonStr);
		SharedPreferencesUtil.setParam(this, Constant.OUT_INDEX, mPresenter.getCurrIndex());
		
		String mVolumeListJosn = gson.toJson(mVolume.getVolumeList());
		String mChapterListJosn = gson.toJson(mChapter.getChapterList());
		FileUtil.writeTextFile(FileUtil.getNovelOutChapterList(mStoryVo.getId()+"_volume"), mVolumeListJosn);
		FileUtil.writeTextFile(FileUtil.getNovelOutChapterList(mStoryVo.getId()), mChapterListJosn);
		
		int currIndex;
		switch (mStoryVo.getChannel()) {
		case Constant.CHANNEL_MALE:
			currIndex = 0;
			break;

		default:
			currIndex = 1;
			break;
		}
		SharedPreferencesUtil.setParam(this, Constant.CHANNEL_SELECT, currIndex);
	}
}