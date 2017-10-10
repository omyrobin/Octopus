package com.jinying.octopus.widget.control;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jinying.octopus.R;
import com.jinying.octopus.read.ReadContract;
import com.jinying.octopus.read.ui.ReadActivity;
import com.jinying.octopus.util.BrightnessUtil;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class NovelBottomWindow implements OnClickListener,SeekBar.OnSeekBarChangeListener, ViewPager.OnPageChangeListener{

	public PopupWindow bottom;
	private NovelPopWindowsManager manager;
	private Context context;
	private int readMode, pageModeIndex;
	private int lineHeightMode, intervalMode;
	private int textSize;
	private int bgIndex;
	private ReadContract.Presenter mPersenter;
	private int brightness;
	private LinearLayout mIndicatorLl;
	private ViewPager mReadBottomVp;
	private View page1, page2;
	private List<View> viewList;
	private ImageView mLeftImg;
	private SeekBar mBrightnessSeekBar;
	private TextView mAaDownTv, mAaUpTv;
	private TextView [] mAas;
	private ImageButton mBgColorWhiteImgBtn, mBgColorYellowImgBtn, mBgColorGreenImgBtn, mBgColorBlueImgBtn, mBgColorNightImgBtn;
	private ImageButton [] mBgColors;
	private TextView mReadModePageTv, mReadModeSmoothnessTv, mReadModeUpDownTv;
	private TextView[] mReadMode;
	private ImageButton mIntervalDenseImgBtn, mIntervalNormalImgBtn, mIntervalSparseImgBtn;
	private ImageButton [] mIntervals;
	private ArrayList<ImageButton> mIndicatorButton;
	private ImageButton mReadVertivalImb, mReadHorizontalImb;
	private TextView mBottomBrightnessTitle;
	private TextView mBottomAaTitle;
	private TextView mBottomColorTitle;
	private TextView mBottomReadModeTitle;
	private TextView mBottomIntervalTitle;
	private TextView mBottomScreenTitle;

	private static final int MAX_TEXTSIZE = 37;
	private static final int MIN_TEXTSIZE = 13;
	private static final int VARIABLE_VALUE = 3;
	private int prePosition = 1;

	public NovelBottomWindow(Context context, ReadContract.Presenter mPersenter, NovelPopWindowsManager manager) {
		this.context = context;
		this.mPersenter = mPersenter;
		this.manager = manager;
		viewList = new ArrayList<>();
		initConfig();
	}

	private void initConfig(){
		textSize = mPersenter.getTxtSize();
		intervalMode = mPersenter.getintervalMode();
		brightness = mPersenter.getBrightness();
		bgIndex = mPersenter.getBgIndex();
		readMode = mPersenter.getReadMode();
		pageModeIndex = readMode;
	}

	public PopupWindow initBottomView(){
		if(bottom==null){
			View contentView = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow_bottom,null);
			bottom = new PopupWindow(contentView);
			bottom.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
			bottom.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
			bottom.setAnimationStyle(R.style.ActionPopupWindowBottom);
			bottom.setFocusable(false);
			bottom.setBackgroundDrawable(new ColorDrawable(0x00000000));
			initViewByBottom(contentView);
			initPageView();
			initPageAdapter();
			initIndicatorView();
		}
		return bottom;
	}

	private void initViewByBottom(View contentView){
		mReadBottomVp = contentView.findViewById(R.id.vp_read_bottom);
		mIndicatorLl = contentView.findViewById(R.id.ll_read_bottom_indicator);
		mLeftImg = contentView.findViewById(R.id.img_left);
		mLeftImg.setOnClickListener(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtils.dp2px(context, 180));
		mReadBottomVp.setLayoutParams(params);
		mReadBottomVp.addOnPageChangeListener(this);
	}

	private void initPageView(){
		page1 = LayoutInflater.from(context).inflate(R.layout.adapter_page_pop_read_bottom1, mReadBottomVp, false);
		//亮度相关View
		mBottomBrightnessTitle = page1.findViewById(R.id.tv_read_brightness);
		mBrightnessSeekBar = page1.findViewById(R.id.seek_bar_brightness);
		mBrightnessSeekBar.setOnSeekBarChangeListener(this);
		//字号相关View
		mBottomAaTitle = page1.findViewById(R.id.tv_read_text_size);
		mAaDownTv = page1.findViewById(R.id.tv_read_text_size_down);
		mAaUpTv = page1.findViewById(R.id.tv_read_text_size_up);
		mAaDownTv.setOnClickListener(this);
		mAaUpTv.setOnClickListener(this);
		mAas = new TextView[]{mAaDownTv,mAaUpTv};
		//颜色相关View
		mBottomColorTitle = page1.findViewById(R.id.tv_read_color);
		mBgColorWhiteImgBtn = page1.findViewById(R.id.imb_read_bg_white);
		mBgColorYellowImgBtn = page1.findViewById(R.id.imb_read_bg_yellow);
		mBgColorGreenImgBtn = page1.findViewById(R.id.imb_read_bg_green);
		mBgColorBlueImgBtn = page1.findViewById(R.id.imb_read_bg_blue);
		mBgColorNightImgBtn = page1.findViewById(R.id.imb_read_bg_night);
		mBgColorWhiteImgBtn.setOnClickListener(this);
		mBgColorYellowImgBtn.setOnClickListener(this);
		mBgColorGreenImgBtn.setOnClickListener(this);
		mBgColorBlueImgBtn.setOnClickListener(this);
		mBgColorNightImgBtn.setOnClickListener(this);
		mBgColors = new ImageButton[]{mBgColorWhiteImgBtn, mBgColorYellowImgBtn, mBgColorGreenImgBtn, mBgColorBlueImgBtn, mBgColorNightImgBtn};
		mBgColors[bgIndex].setSelected(true);

		page2 = LayoutInflater.from(context).inflate(R.layout.adapter_page_pop_read_bottom2, mReadBottomVp, false);
		//翻页相关View
		mBottomReadModeTitle = page2.findViewById(R.id.tv_read_mode);
		mReadModePageTv = page2.findViewById(R.id.tv_read_mode_simulat);
		mReadModeSmoothnessTv = page2.findViewById(R.id.tv_read_mode_smoothness);
		mReadModeUpDownTv = page2.findViewById(R.id.tv_read_mode_vertical);
		mReadModePageTv.setOnClickListener(this);
		mReadModeSmoothnessTv.setOnClickListener(this);
		mReadModeUpDownTv.setOnClickListener(this);

		mReadMode = new TextView[]{mReadModePageTv, mReadModeSmoothnessTv, mReadModeUpDownTv};
		mReadMode[readMode].setSelected(true);
		//间距相关View
		mBottomIntervalTitle  = page2.findViewById(R.id.tv_read_interval);
		mIntervalDenseImgBtn = page2.findViewById(R.id.imb_line_height_dense);
		mIntervalNormalImgBtn = page2.findViewById(R.id.imb_line_height_normal);
		mIntervalSparseImgBtn = page2.findViewById(R.id.imb_line_height_sparse);
		mIntervalDenseImgBtn.setOnClickListener(this);
		mIntervalNormalImgBtn.setOnClickListener(this);
		mIntervalSparseImgBtn.setOnClickListener(this);
		mIntervals = new ImageButton[]{mIntervalDenseImgBtn, mIntervalNormalImgBtn, mIntervalSparseImgBtn};
		mIntervals[intervalMode].setSelected(true);
		//横竖屏相关View
		mBottomScreenTitle  = page2.findViewById(R.id.tv_read_orientation);
		mReadVertivalImb = page2.findViewById(R.id.imb_read_vertical);
		mReadHorizontalImb = page2.findViewById(R.id.imb_read_horizontal);
		mReadVertivalImb.setSelected(true);
		mReadVertivalImb.setOnClickListener(this);
		mReadHorizontalImb.setOnClickListener(this);


		viewList.add(page1);
		viewList.add(page2);
	}

	private void initPageAdapter(){
		ReadBottomAdapter adapter = new ReadBottomAdapter();
		mReadBottomVp.setAdapter(adapter);
	}

	private void initIndicatorView(){
		mIndicatorButton = new ArrayList<>();
		for (int i=0 ; i<2 ; i++){
			ImageButton imageButton = new ImageButton(context);
			imageButton.setBackgroundResource(0);
			imageButton.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.selector_read_bottom_indicator));
			mIndicatorButton.add(imageButton);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dp2px(context,7), DensityUtils.dp2px(context,7));
			params.setMargins(DensityUtils.dp2px(context,10), 0, 0, 0);
			mIndicatorLl.addView(imageButton, params);
		}
		setIndicatorSelect(0);
		setBgColorByShowModel();
	}

    @Override
    public void onClick(View view) {
		switch (view.getId()){
			case R.id.img_left:
				mPersenter.initLeft();
				break;

			case R.id.tv_read_text_size_down:
				setTextSize(0);
				break;

			case R.id.tv_read_text_size_up:
				setTextSize(1);
				break;

			case R.id.imb_read_bg_white:
				setBgIndex(0);
				break;

			case R.id.imb_read_bg_yellow:
				setBgIndex(1);
				break;

			case R.id.imb_read_bg_green:
				setBgIndex(2);
				break;

			case R.id.imb_read_bg_blue:
				setBgIndex(3);
				break;

			case R.id.imb_read_bg_night:
				setBgIndex(4);
				break;

			case R.id.tv_read_mode_simulat:
				setReadMode(ReadActivity.SIMULAT_MODE);
				break;

			case R.id.tv_read_mode_smoothness:
				setReadMode(ReadActivity.SMOOTHNESS_MODE);
				break;

			case R.id.tv_read_mode_vertical:
				setReadMode(ReadActivity.VERTICAL_MODE);
				break;

			case R.id.imb_line_height_dense:
				setIntervals(ReadActivity.LINE_HEIGHT_DESCE);
				break;

			case R.id.imb_line_height_normal:
				setIntervals(ReadActivity.LINE_HEIGHT_NORMAL);
				break;

			case R.id.imb_line_height_sparse:
				setIntervals(ReadActivity.LINE_HEIGHT_SPARSE);
				break;

			case R.id.imb_read_horizontal:
				ToastUtil.showShort(context,"暂不支持横屏阅读，请关注后续版本...");
				break;

		}
    }

	/**
	 * 亮度调节监听
	 * @param seekBar
	 * @param progress
	 * @param b
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
		brightness = progress;
		BrightnessUtil.setCurWindowBrightness(context, progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mPersenter.setBrightness(brightness);
		//如果之前系统开启自动亮度，那么恢复开启状态
		if(mPersenter.isAutoBrightness()){
			BrightnessUtil.startAutoBrightness(context);
		}
	}

	/**
	 * 改变字号
	 * @param index
	 */
	private void setTextSize(int index){
		if(mPersenter!=null){
			switch (index) {
				case 0:
					textSize -= VARIABLE_VALUE;
					mAas[index+1].setEnabled(true);
					if(textSize <= MIN_TEXTSIZE){
						mAas[index].setEnabled(false);
					}
					break;

				case 1:
					textSize += VARIABLE_VALUE;
					mAas[index-1].setEnabled(true);
					if(textSize >= MAX_TEXTSIZE){
						mAas[index].setEnabled(false);
					}
					break;
			}
			mPersenter.setTxtSize(textSize);
		}
	}

	/**
	 * 阅读背景
	 * @param index
	 */
	private void setBgIndex(int index){
		if(index == bgIndex){
			return;
		}
		if(mPersenter!=null){
			mBgColors[bgIndex].setSelected(false);
			mBgColors[index].setSelected(true);
			if(index == 4){
//				isMoon = true;
				mPersenter.setBgIndex(index);
				mPersenter.setBeforeBgIndex(bgIndex);
			}else{
//				isMoon = false;
				mPersenter.setBgIndex(index);
			}
			manager.mTop.setBgColorByShowModel();
			setBgColorByShowModel();
		}
		bgIndex = index;
	}

	private void setBgColorByShowModel() {
		boolean isDay = mPersenter.getBgIndex() != 4;
		if(!isDay){
			page1.setBackgroundColor(ContextCompat.getColor(context,R.color.colorControlNight));
			page2.setBackgroundColor(ContextCompat.getColor(context,R.color.colorControlNight));
			mIndicatorLl.setBackgroundColor(ContextCompat.getColor(context,R.color.colorControlNight));
			((ImageButton)mIndicatorLl.getChildAt(0)).setImageDrawable(ContextCompat.getDrawable(context,R.drawable.selector_read_bottom_indicator_night));
			((ImageButton)mIndicatorLl.getChildAt(1)).setImageDrawable(ContextCompat.getDrawable(context,R.drawable.selector_read_bottom_indicator_night));
			mBottomBrightnessTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitleNight));
			mBottomAaTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitleNight));
			mBottomColorTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitleNight));
			mBottomReadModeTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitleNight));
			mBottomIntervalTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitleNight));
			mBottomScreenTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitleNight));

		}else{
			page1.setBackgroundColor(ContextCompat.getColor(context,android.R.color.white));
			page2.setBackgroundColor(ContextCompat.getColor(context,android.R.color.white));
			mIndicatorLl.setBackgroundColor(ContextCompat.getColor(context,android.R.color.white));
			((ImageButton)mIndicatorLl.getChildAt(0)).setImageDrawable(ContextCompat.getDrawable(context,R.drawable.selector_read_bottom_indicator));
			((ImageButton)mIndicatorLl.getChildAt(1)).setImageDrawable(ContextCompat.getDrawable(context,R.drawable.selector_read_bottom_indicator));
			mBottomBrightnessTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitle));
			mBottomAaTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitle));
			mBottomColorTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitle));
			mBottomReadModeTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitle));
			mBottomIntervalTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitle));
			mBottomScreenTitle.setTextColor(ContextCompat.getColor(context,R.color.textColorTitle));

		}
	}

	/**
	 * 翻页方式
	 * @param index
	 */
	private void setReadMode(int index) {
		if(readMode == index){
			return;
		}
		readMode = index;
		if(mPersenter!=null){
			mReadMode[index].setSelected(true);
			mReadMode[pageModeIndex].setSelected(false);
			mPersenter.setReadMode(index);
		}
		pageModeIndex = readMode;
	}

	/**
	 * 文字间距
	 * @param lineHeightModle
	 */
	private void setIntervals(int lineHeightModle) {
		if(intervalMode == lineHeightModle){
			return;
		}
		if(mPersenter!=null){
			mIntervals[lineHeightModle].setSelected(true);
			mIntervals[intervalMode].setSelected(false);
			mPersenter.setIntervals(lineHeightModle);
		}
		intervalMode = lineHeightModle;
	}

	private void setIndicatorSelect(int position){
		mIndicatorButton.get(position).setSelected(true);
		mIndicatorButton.get(prePosition).setSelected(false);
		prePosition = position;
	}

	class ReadBottomAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position));
			return viewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewList.get(position));
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		setIndicatorSelect(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

}
