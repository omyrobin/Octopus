package com.jinying.octopus.read.persenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.jinying.octopus.App;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.event.EventChapterList;
import com.jinying.octopus.read.Chapter;
import com.jinying.octopus.read.NovelChapterBean;
import com.jinying.octopus.read.ReadContract;
import com.jinying.octopus.read.model.ReadModel;
import com.jinying.octopus.read.persenter.impl.IListener;
import com.jinying.octopus.read.ui.ReadActivity;
import com.jinying.octopus.util.BrightnessUtil;
import com.jinying.octopus.util.CatalogueUtil;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.FileUtil;
import com.jinying.octopus.util.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

public class ReadPersenter implements ReadContract.Presenter, IListener {

	public static final int NEXT = 1;

	public static final int FIRST = 2;

//	public static final int CURR = 0;

	public static final int LASTE = -2;

	public static final int PRE = -1;

	public static final int CLICK_MODE = 100;// 点击模式

	public static final int PAGE_MODE = 200;// 翻页模式

	public static final int LOAD_MODE = 300;// 预加载模式

	public static final int FIRST_MODE = 400;// 首次加载模式

	public static final int VERTICAL_MODE = 500;// 滑动模式
	
	public static final int REFRESH_MODE = 600;// 刷新模式

	private Context context;

	private ArrayList<ChapterBean> mChapterList;

	private ReadContract.View mView;

	private ReadModel mModel;

	public int screenWidth = (int) App.width; // 屏幕宽度

	public int screenHeight; // 屏幕高度

	private int textSize; // 字体大小

	private int textColor; // 字体颜色

	private int lineHeight; // 每行的高度

	private int marginWidth; // 左右与边缘的距离

	private int marginHeight;// 上下与边缘的距离

//	private Bitmap bgBitmap; // 背景图片

	private int bgColor;

	private Paint paint, paintTop, paintBottom, paintBatteryOut, paintBatteryIn, paintBatteryHead;

	private int power = 100; // 电池电量

	private GregorianCalendar calendar;// 时间类

	private float visibleWidth; // 屏幕中可显示文本的宽度

	private float visibleHeight;

	private Chapter chapter; // 需要处理的章节对象

	private Vector<String> linesVe; // 将章节內容分成行，并将每页按行存储到vector对象中

	private int lineCount; // 一个章节在当前配置下一共有多少行

	private String content;

	private int chapterLen; // 章节的长度

	private Vector<Vector<String>> pagesVe;

	private int pageNum;

	private int currIndex;

	private int p;// 平滑时 临时记录页码
	
	private boolean readPreChapter, readNextChapter;

	private int clickIndex;

	private int BATTERY_TOP;

	private int BATTERY_WIDTH;

	private int BATTERY_HEIGHT;

	private int BATTERY_HEAD_WIDTH;

	private int BATTERY_HEAD_HEIGHT;

	private int BATTERY_INSIDE_MARGIN;

	private int plan;// 阅读进度

	private boolean isNotChangePage;

	private boolean isNextToPre;

	private boolean isPretToNext;

	private int copyPrePageNum;

	private int verticalCurrIndex;

	private boolean actionToNextChapter;

	private boolean actionToPreChapter;

	private int times; //进入段落测量方法的次数

	private int paragraphe;

	private boolean isAutoBrightness; //是否开启自动亮度调节
	
	private long createTime;

	private int intervalMode;//间距模式

	public ReadPersenter(Context context, ReadContract.View mView) {
		this.context = context;
		this.mView = mView;
		mModel = new ReadModel(this,mView.getStoryInfo());
		createTime = System.currentTimeMillis();
	}

	@Override
	public void start() {
		initBrightness();
		initConfig();
		initPaint();
		initBattery();
	}

	@Override
	public void initBrightness() {
		boolean isFlowingSystem = mModel.isFlowingSystem();
		isAutoBrightness = BrightnessUtil.IsAutoBrightness(context);
		if(isFlowingSystem){//跟随系统亮度
			if(!isAutoBrightness){//手动亮度
				BrightnessUtil.setCurWindowBrightness(context, BrightnessUtil.getScreenBrightness(context));
			}
		}else{
			BrightnessUtil.setCurWindowBrightness(context, mModel.getBrightness());
		}
		if(isAutoBrightness){
			BrightnessUtil.startAutoBrightness(context);
		}
	}

	/**
	 * 初始化配置
	 */
	@Override
	public void initConfig() {
		mChapterList = mView.getChapterList();
		marginWidth = DensityUtils.dp2px(context, 14);
		marginHeight = DensityUtils.dp2px(context, 60);
		visibleWidth = screenWidth - marginWidth * 2;
		visibleHeight = screenHeight - marginHeight * 2;
		linesVe = new Vector<>();
		pagesVe = new Vector<>();
		pageNum = mModel.getPageNum();
	}

	/**
	 * 初始化画笔
	 */
	@SuppressLint("NewApi")
	@Override
	public void initPaint() {
		textColor = mModel.getTextColor();
		textSize = DensityUtils.sp2px(context, mModel.getTextSize());
		bgColor = Constant.BACKGROUND_RES[mModel.getBgIndex()];
		initLineCount();
		//内容画笔
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextAlign(Align.LEFT);
		paint.setTextSize(textSize);
		paint.setColor(ContextCompat.getColor(context, Constant.TEXT_COLORS[textColor]));

		paintTop = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintTop.setTextAlign(Align.LEFT);
		paintTop.setTextSize(DensityUtils.sp2px(context, 10));
		paintTop.setColor(ContextCompat.getColor(context, Constant.TEXT_COLORS[textColor]));

		paintBottom = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintBottom.setTextAlign(Align.LEFT);
		paintBottom.setTextSize(DensityUtils.sp2px(context, 10));
		paintBottom.setColor(ContextCompat.getColor(context, Constant.TEXT_COLORS[textColor]));

		paintBatteryOut = new Paint();
		paintBatteryOut.setColor(ContextCompat.getColor(context, Constant.TEXT_COLORS[textColor]));
		paintBatteryOut.setAntiAlias(true);
		paintBatteryOut.setStyle(Style.STROKE);

		paintBatteryHead = new Paint(paintBatteryOut);
		paintBatteryHead.setStyle(Style.FILL);

		paintBatteryIn = new Paint(paintBatteryOut);
		paintBatteryIn.setStyle(Style.FILL);
	}

	public void initLineCount() {
		intervalMode = mModel.getIntervalMode();
		lineHeight = DensityUtils.sp2px(context, mModel.getLineHeight());
		switch (intervalMode){
			case ReadActivity.LINE_HEIGHT_DESCE:
				if (mModel.getTextSize() > 25) {
					lineCount = (int) (visibleHeight / lineHeight) + 1;
				} else if (mModel.getTextSize() < 19) {
					lineCount = (int) (visibleHeight / lineHeight + 3);
				} else {
					lineCount = (int) (visibleHeight / lineHeight + 2);
				}
				break;

			case ReadActivity.LINE_HEIGHT_NORMAL:
				if (mModel.getTextSize() > 25) {
					lineCount = (int) (visibleHeight / lineHeight);
				} else if (mModel.getTextSize() < 19) {
					lineCount = (int) (visibleHeight / lineHeight + 2);
				} else {
					lineCount = (int) (visibleHeight / lineHeight + 1);
				}
				break;

			case ReadActivity.LINE_HEIGHT_SPARSE:
				if (mModel.getTextSize() > 25) {
					lineCount = (int) (visibleHeight / lineHeight - 1);
				} else if (mModel.getTextSize() < 19) {
					lineCount = (int) (visibleHeight / lineHeight + 1);
				} else {
					lineCount = (int) (visibleHeight / lineHeight);
				}
				break;
		}
	}

	public void initBattery() {
		BATTERY_TOP = screenHeight - DensityUtils.dp2px(context, 4);
		
		BATTERY_WIDTH = DensityUtils.dp2px(context, 19);
		BATTERY_HEIGHT = DensityUtils.dp2px(context, 10);
		BATTERY_HEAD_WIDTH = DensityUtils.dp2px(context, 2);
		BATTERY_HEAD_HEIGHT = DensityUtils.dp2px(context, 5);
		BATTERY_INSIDE_MARGIN = 2;
	}

	public void initBattery2() {
		BATTERY_TOP = DensityUtils.dp2px(context, 20);
		
		BATTERY_WIDTH = DensityUtils.dp2px(context, 19);
		BATTERY_HEIGHT = DensityUtils.dp2px(context, 10);
		BATTERY_HEAD_WIDTH = DensityUtils.dp2px(context, 2);
		BATTERY_HEAD_HEIGHT = DensityUtils.dp2px(context, 5);
		BATTERY_INSIDE_MARGIN = 2;
	}

	public boolean chapterCheck(int currIndex, int which, int mode) {
		if (currIndex < 0 || currIndex >= mView.getChapterList().size()) {
			return false;
		}
		String chapterId = mView.getChapterList().get(currIndex).getId() + "";
		File file;
		file = FileUtil.getNovelChapterTxt(mView.getStoryDir(), chapterId);
		if (!FileUtil.isHaveNovelChapterTxt(mView.getStoryDir(), chapterId)) {
			Logger.i("下载文章 : " + mode + "   which : " + which);
			mModel.downChapterContent(chapterId, which, mode);
			return false;
		} else {
			Logger.i("显示文章 : " + mode + "   which : " + which);
			showChapterContent(file, which, mode);
			return true;
		}
	}
	
	public void showChapterContent(File file, int which, int mode) {
		if (mode == LOAD_MODE) {
			return;
		}

		content = FileUtil.readFileContent(file.getAbsolutePath());
		switch (mode) {
		case FIRST_MODE:
			Logger.i("首次模式  显示文章");
			configurationInfo(currIndex);
			if (!pagesVe.isEmpty()) {
				// 如果选择的章节和本地存储不一致,说明是从目录选择章节进入的,pageNum设置为起始
				if (currIndex != mModel.getCurrIndex()) {
					Logger.i("不一致");
					pageNum = -1;
				}

				if (pageNum == -1) {
					linesVe = pagesVe.get(++pageNum);
				} else {
					if (pageNum > pagesVe.size() - 1) {
						pageNum = pagesVe.size() - 1;
					}
					linesVe = pagesVe.get(pageNum);
					p = pageNum;
				}
			}
			mView.setContent();
			break;

		case CLICK_MODE:
			Logger.i("点击模式  显示文章");
			Logger.i("================  " +   clickIndex);
			currIndex = clickIndex;
			p = 0;
			if(readNextChapter){
//				mView.setReadPlan(-1);
				readNextChapter = false;
			}
			if(readPreChapter){
//				mView.setReadPlan(-1);
				readPreChapter = false;
			}
			configurationInfo(currIndex);
			pageNum = -1;
			if (!pagesVe.isEmpty()) {
				linesVe = pagesVe.get(++pageNum);
			}
			mView.notifyData();
			mView.refreshContent();
			break;

		case PAGE_MODE:
			Logger.i("翻页模式  显示文章");
			int index = 0;
			if (which == PRE) {
				if (mModel.getReadMode() == ReadActivity.SMOOTHNESS_MODE) {
					index = currIndex - 1;
				} else {
					currIndex -= 1;
					index = currIndex;
				}
				configurationInfo(index);

				if (!pagesVe.isEmpty()) {
					pageNum = pagesVe.size();
				}
			}
			if (which == NEXT) {
				if (mModel.getReadMode() == ReadActivity.SMOOTHNESS_MODE) {
					index = currIndex + 1;
				} else {
					currIndex += 1;
					index = currIndex;
				}
				configurationInfo(index);
				if (!pagesVe.isEmpty()) {
					pageNum = -1;
				}
			}

			if (which == FIRST) {
				configurationInfo(currIndex);
				if (!pagesVe.isEmpty()) {
					pageNum = 0;
				}
			}
			if (which == LASTE) {
				configurationInfo(currIndex);
				if (!pagesVe.isEmpty()) {
					pageNum = pagesVe.size() - 1;
				}
			}
			Logger.i("pageNum  : " + pageNum);
			break;

		case VERTICAL_MODE:
			Logger.i("滑动模式 显示文章");
			if (which == PRE) {
				currIndex -= 1;
				configurationInfo(currIndex);

				if (!pagesVe.isEmpty()) {
					pageNum = pagesVe.size();
				}
			}
			if (which == NEXT) {
				currIndex += 1;
				configurationInfo(currIndex);

				if (!pagesVe.isEmpty()) {
					pageNum = 0;
				}
			}
			break;
			
		case REFRESH_MODE:
			configurationInfo(currIndex);
			break;
		}
	}

	/**配置需要显示的相关信息**/
	public void configurationInfo(int index) {
		setChapter(index);
		setChapterContent();
		slicePage(index);
	}
	
	@Override
	public void setChapterContent() {
		chapter.setContent(content);
		chapterLen = TextUtils.isEmpty(content) ? 0 : content.length();
	}

	@Override
	public void setChapter(int currIndex) {
		int chapterLen = mChapterList.size() - 1;
		if (currIndex > chapterLen) {
			currIndex = chapterLen;
		}
		if (currIndex < 0) {
			currIndex = 0;
		}
		if(mModel.getReadMode() != ReadActivity.SMOOTHNESS_MODE){
			this.currIndex = currIndex;
		}
		// 设置章节信息
		chapter = new Chapter();
		chapter.setCurrIndex(currIndex);
		if (!mChapterList.isEmpty()) {
			chapter.setTitle(mChapterList.get(currIndex).getName());
			chapter.setContent(content);
		}
	}
	
	@Override
	public void slicePage(int index) {
		pagesVe.clear();
		initLineCount();
		int curPos = 0;
		while (curPos < chapterLen) {
			Vector<String> lines = new Vector<String>();
			while (lines.size() < lineCount && curPos < chapterLen) {
				int i = content.indexOf("\n", curPos);
				if (i == -1) {
					i = content.length();
				}
				// 获取一段落数据
				String paragraphStr = content.substring(curPos, i);
				// 如果是段落末尾,并且不是首行
				if (curPos == i && lines.size() > 0) {
					lines.add("");// 段间距用空行表示
					++paragraphe;
					//根据段落数量 增加行数 使视图饱满
					configurationSpace();
				}
				// 将读取的一段落分成若干行,直到文字全部存入后结束循环
				while (paragraphStr.length() > 0) {
					// 按宽度折行,并返回这一行最后一个字符在整个字符串中的位置
					int horSize = paint.breakText(paragraphStr, true,visibleWidth, null);
					lines.add(paragraphStr.substring(0, horSize));
					// 将原来的文本去掉已经已经加入lines的部分
					paragraphStr = paragraphStr.substring(horSize);
					// 重设开始位置
					curPos += horSize;
					// 如果超出总显示行数了就表示一页内容结束
					if (lines.size() > lineCount) {
						break;
					}

				}
				// 如果是把一整段读取完的话，需要给当前位置加1
				if (paragraphStr.length() == 0) {
					curPos += "\n".length();
				}
			}
			if (lines.size() != 0) {
				pagesVe.add(lines);

				// 再次初始化段落计数和行高
				paragraphe = 0;
				times = 0;
				initLineCount();
			}
		}
	}
	
	public void configurationSpace(){
		++times;
		if (mModel.getTextSize() > 25) {
			if (paragraphe == 2) {
				++lineCount;
				this.paragraphe = 0;
			}
		} else if (mModel.getTextSize() < 19) {
			if (paragraphe == 3) {
				if(times>1 && times<4){
					Logger.i("增加两行");
					lineCount += 2;
				}else{
					++lineCount;
				}
				this.paragraphe = 0;
			}
		} else {
			if (paragraphe == 2) {
				if(times>2 && times<4){
					Logger.i("增加两行");
					lineCount += 2;
				}else{
					++lineCount;
				}
				this.paragraphe = 0;
			}
		}
	}
	
	@Override
	public void onContentSuccess(String content, final int which,final String chapterId, final int mode){
		File file;
		file = FileUtil.getNovelChapterTxt(mView.getStoryDir(), chapterId);
		FileUtil.writeTextFile(file, content);
		if (mode == CLICK_MODE || mode == FIRST_MODE) {
			Logger.i("准备显示");
			showChapterContent(file, which, mode);
		}
//		mView.dismissLoadingPressbarShort();
	}

	@Override
	public void onContentFail() {
//		mView.onContentFail();
//		mView.dismissLoadingPressbar();
	}

	// 翻到上一页
	@Override
	public boolean prePage(int mode) {
		if (isFirstPage()) {
			if (!preChapter()) {// 如果已经到本书第一章,或获取不到上一章内容,就不能继续执行翻页代码
				Logger.i("上一章   暂时获取不到内容,阻止滑动");
				return false;
			}
		}
		// 章节切换时 pageNum的改变会阻止if条件
		switch (mode) {
		case ReadActivity.SIMULAT_MODE:
			if (pageNum > 0) {
				linesVe = pagesVe.get(--pageNum);
			}
			break;

		case ReadActivity.SMOOTHNESS_MODE:
			Logger.i(pageNum + " == 上一页  :  当前pageNum");
			if (pageNum > 0 && !pagesVe.isEmpty() && pageNum <= pagesVe.size()) {
				if (p == 0 && pageNum > 0) {
					// TODO 说明之前是章节首页滑动到上一章节的尾页 触发时设置状态
					Logger.i("说明之前是章节首页滑动到上一章节的尾页");
					isNextToPre = true;
				}
				if (isPretToNext && isNotChangePage) {
					isNextToPre = false;
					pageNum = copyPrePageNum;
					Logger.i(pageNum + " == 上一页经过处理后  :  当前pageNum");
				}
				p = pageNum - 1;
				if(p < 0){
					p = 0;
				}
				linesVe = pagesVe.get(p);
			}
			break;
		}
		return true;
	}

	// 翻到下一页
	@Override
	public boolean nextPage(int mode) {
		if (isLastPage()) {
			if (!nextChapter()) {// 如果已经到本书末尾,或获取不到下一章内容,那么不能继续执行翻页代码
				Logger.i("下一章   暂时获取不到内容,阻止滑动");
				return false;
			}
		}
		// 章节切换时 pageNum的改变会阻止if条件
		switch (mode) {
		case ReadActivity.SIMULAT_MODE:
			if (!pagesVe.isEmpty() && pageNum < pagesVe.size() - 1) {
				linesVe = pagesVe.get(++pageNum);
			}
			break;

		case ReadActivity.SMOOTHNESS_MODE:
			Logger.i(pageNum + " == 下一页 :  当前pageNum");
			if (!pagesVe.isEmpty() && pageNum < pagesVe.size() - 1) {
				if (p > 0 && pageNum == -1 && isNotChangePage) {
					// TODO 说明之前是章节末尾滑动到新章节的首页
					Logger.i("说明之前是章节末尾滑动到新章节的首页");
					isPretToNext = true;
				}
				if (isNextToPre && isNotChangePage) {
					isPretToNext = false;
					pageNum = 0;
					Logger.i(pageNum + " == 下一页经过处理后  :  当前pageNum");
				}
				p = pageNum + 1;
				
				if(p > pagesVe.size() - 1){
					p = pagesVe.size();
				}
				linesVe = pagesVe.get(p);
			}
			break;
		}
		return true;
	}

	@Override
	public synchronized boolean nextChapter() {
		if (currIndex == mChapterList.size() - 1) {
			mView.isLastPage();
			return false;
		}
		int nextCurrIndex = currIndex + 1;
		boolean next = chapterCheck(nextCurrIndex, NEXT, PAGE_MODE);
		if(mModel.getReadMode() == ReadActivity.SMOOTHNESS_MODE){
			isNextChapter(next);//为翻页标识设置状态
		}
		System.out.println("设置后一章翻页标识" + getNextChapter());
		chapterCheck(nextCurrIndex + 1, NEXT, LOAD_MODE);
		return next;
	}

	@Override
	public synchronized boolean preChapter() {
		if (currIndex == 0) {
//			mView.dismissLoadingPressbar();
			mView.isFirstPage();
			return false;
		}
		int preCurrIndex = currIndex - 1;
		boolean pre = chapterCheck(preCurrIndex, PRE, PAGE_MODE);
		if(mModel.getReadMode() == ReadActivity.SMOOTHNESS_MODE){
			isPreChapter(pre);//为翻页标识设置状态
		}
		System.out.println("设置前一章翻页标识" + getPreChapter());
		chapterCheck(preCurrIndex - 1, PRE, LOAD_MODE);
		return pre;
	}

	@Override
	public void isNextChapter(boolean actionToNextChapter) {
		System.out.println("设置翻到下一页的状态为 ： " + actionToNextChapter);
		this.actionToNextChapter = actionToNextChapter;
	}

	@Override
	public void isPreChapter(boolean actionToPreChapter) {
		this.actionToPreChapter = actionToPreChapter;
	}

	@Override
	public boolean getNextChapter() {
		return actionToNextChapter;
	}

	@Override
	public boolean getPreChapter() {
		return actionToPreChapter;
	}

	@Override
	public boolean isFirstPage() {
		if (pageNum <= 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isLastPage() {
		if (pageNum >= pagesVe.size() - 1) {
			copyPrePageNum = pageNum;
			return true;
		}
		return false;
	}

	@Override
	public void draw(Canvas c) {
		if (mModel.getReadMode() != ReadActivity.VERTICAL_MODE) {

			// 绘制纸张背景
//			if (bgBitmap == null) {
			c.drawColor(ContextCompat.getColor(context, bgColor));
//			}

			if (mModel.getReadMode() == ReadActivity.SMOOTHNESS_MODE) {
				mView.hideButton();
			}

			if (linesVe.size() > 0) {
				int y = marginHeight - DensityUtils.dp2px(context, 10);
				for (int i = 0; i < linesVe.size(); i++) {
					String text = linesVe.get(i);
					if (i == 0) {
						y += DensityUtils.dp2px(context, 10);
					}
					else if ("".equals(text)) {
						// 根据文字大小绘制段间距(字体范围从13~37 每次加减为 3);
						if (mModel.getTextSize() > 25) {// (28、31、34、37)
							y += lineHeight / 2;
						} else if (mModel.getTextSize() < 19) {// (13、16)
							y += lineHeight / 4;
						} else {
							y += lineHeight / 2;// (19、22、25)
						}
					}
					else {
						y += lineHeight;
					}
					c.drawText(text, marginWidth, y, paint);
				}
			}
			
			// 绘制章节名
			String title = chapter.getTitle();
			if (mModel.getReadMode() == ReadActivity.SIMULAT_MODE) {
				if (pageNum != 0) {
					c.drawText(title, marginWidth,DensityUtils.dp2px(context, 12), paintTop);
				}
			} else {
				c.drawText(title, marginWidth, DensityUtils.dp2px(context, 12),paintTop);
			}
			initBattery();
		} 
		
		else {
			initBattery2();
			c.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		}

		// 绘制电池
		Rect rect = new Rect(marginWidth, BATTERY_TOP, marginWidth+ BATTERY_WIDTH, BATTERY_TOP - BATTERY_HEIGHT);
		c.drawRect(rect, paintBatteryOut);

		// 绘制电池电量
		if (power != 0) {
			int p_left = marginWidth + BATTERY_INSIDE_MARGIN;
			int p_top = BATTERY_TOP + BATTERY_INSIDE_MARGIN - BATTERY_HEIGHT;
			int p_right = p_left- BATTERY_INSIDE_MARGIN+ (int) ((BATTERY_WIDTH - BATTERY_INSIDE_MARGIN) * power / 100);
			int p_bottom = p_top + BATTERY_HEIGHT - BATTERY_INSIDE_MARGIN * 2;
			Rect rect_power = new Rect(p_left, p_top, p_right, p_bottom);
			c.drawRect(rect_power, paintBatteryIn);
		}
		// 绘制电池正极
		int h_top = BATTERY_TOP - BATTERY_HEIGHT / 2 - BATTERY_HEAD_HEIGHT / 2;
		int h_bottom = h_top + BATTERY_HEAD_HEIGHT;
		Rect rect_head = new Rect(marginWidth + BATTERY_WIDTH, h_top,marginWidth + BATTERY_WIDTH + BATTERY_HEAD_WIDTH, h_bottom);
		c.drawRect(rect_head, paintBatteryHead);

		// 绘制时间
		float percent;
		if (!pagesVe.isEmpty()) {
			if (mModel.getReadMode() == ReadActivity.SMOOTHNESS_MODE) {
				percent = (float) ((p + 1) * 1.0 / pagesVe.size());
			} else if (mModel.getReadMode() == ReadActivity.VERTICAL_MODE) {
				percent = (float) ((pageNum) * 1.0 / pagesVe.size());
			} else {
				percent = (float) ((pageNum + 1) * 1.0 / pagesVe.size());
			}

			if (percent > 1) {
				percent = 1;
			}
		} else {
			percent = 1;
		}

		DecimalFormat df = new DecimalFormat("#0.0");
		String percetStr = df.format(percent * 100) + "%";

		calendar = new GregorianCalendar();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		String timeStr;
		if (minute < 10)
			timeStr = "" + hour + " : 0" + minute;
		else
			timeStr = "" + hour + " : " + minute;

		if (mModel.getReadMode() == ReadActivity.VERTICAL_MODE) {
			c.drawText(timeStr,marginWidth + BATTERY_WIDTH+ DensityUtils.dp2px(context, 5),DensityUtils.dp2px(context, 19), paintBottom);

			// 绘制百分比
			int pSWidth = (int) paintBottom.measureText("100.0%") + 2;
			c.drawText(percetStr, screenWidth - marginWidth / 2 - pSWidth,
					DensityUtils.dp2px(context, 19), paintBottom);
		}
		else {
			c.drawText(timeStr,marginWidth + BATTERY_WIDTH+ DensityUtils.dp2px(context, 5), screenHeight- DensityUtils.dp2px(context, 5), paintBottom);

			// 绘制百分比
			int pSWidth = (int) paintBottom.measureText("100.0%") + 2;
			c.drawText(percetStr, screenWidth - marginWidth / 2 - pSWidth,screenHeight - DensityUtils.dp2px(context, 5), paintBottom);
		}

	}

	@SuppressLint("NewApi")
	@Override
	public void setTxtSize(int textSize) {
		mModel.setTextSize(textSize);
		initPaint();
		slicePage(currIndex);
		if (pageNum > pagesVe.size() - 1) {
			pageNum = pagesVe.size() - 1;
		}
		if (!pagesVe.isEmpty()) {
			linesVe = pagesVe.get(pageNum);
		}
		mView.notifySize();
		mView.refreshContent();
	}

	@Override
	public void setTxtColor(int textColor) {
		mModel.setTextColor(textColor);
		initPaint();
	}

	@SuppressLint("ResourceAsColor")
	@Override
	// 背景颜色 影响文字颜色
	public void setBgIndex(int bgIndex) {
		mModel.setBgIndex(bgIndex);
//		bgBitmap = BitmapFactory.decodeResource(context.getResources(),Constant.BACKGROUND_RES[mModel.getBgIndex()]);
		setTxtColor(bgIndex);
		mView.refreshContent();
	}

	@Override
	public void setBeforeBgIndex(int bgIndex) {
		mModel.setBeforeBgIndex(bgIndex);
	}

	@Override
	public int getSunBack() {
		return mModel.getBeforeBgIndex();
	}

	@Override
	public int getTxtSize() {
		return mModel.getTextSize();
	}

	@Override
	public int getTxtColor() {
		return mModel.getTextColor();
	}

	@Override
	public int getBgIndex() {
		return mModel.getBgIndex();
	}
	
	@Override
	public int getLineHeight() {
		return lineHeight;
	}

	@Override
	public boolean finishActivity() {
		// 判断书架中是否有该数据
//		StoryVoBean s = mView.getDBManager().queryById(StoryVoEntity.class, MyApp.getInstance().getLocalId(),mView.getStoryInfo().getId() + "");
//		if (s == null) {
//			mView.showAddDialog();
//			return false;
//		} else {
//			mView.finishActivity();
//			return true;
//		}
		mView.finishActivity();
		return true;
	}

	@Override
	public void initLeft() {
		mView.showLeftWindows();
	}

	@Override
	public void readCurrChapter(int currIndex, boolean dismiss) {
		Logger.i("左侧点击-------currIndex : " + currIndex);
		mView.statusBar();
		clickIndex = currIndex;
		if (dismiss) {
			mView.dismissAll();
		}
		chapterCheck(currIndex, NEXT, CLICK_MODE);
		chapterCheck(currIndex - 1, PRE, LOAD_MODE);// 预加载上一章
		chapterCheck(currIndex + 1, NEXT, LOAD_MODE);// 预加载下一章
	}
	
	@Override
	public void readPreChapter() {
		if (currIndex > 0) {
			readPreChapter = true;
			readCurrChapter(currIndex - 1, false);
		} else {
			mView.isFirstChapter();
		}
	}

	@Override
	public void readNextChapter() {
		if (currIndex < mChapterList.size() - 1) {
			readNextChapter = true;
			readCurrChapter(currIndex + 1, false);
		} else {
			mView.isLastPage();
		}
	}

	@Override
	public void readPlan(int plan) {
		clickIndex = plan;
		currIndex = plan;
		mView.notifyPageNum();
		chapterCheck(currIndex, NEXT, CLICK_MODE);
	}
	
	int starPlan;
	
	@Override
	public void setStarPlan(int progress) {
		starPlan = progress;
	}

	@Override
	public int getStarPlan() {
		return starPlan;
	}

	@Override
	public void setIntervals(int intervalMode) {
		this.intervalMode = intervalMode;
		mModel.setIntervals(intervalMode);
		slicePage(currIndex);
		mView.refreshContent();
	}

	@Override
	public int getintervalMode() {
		return mModel.getIntervalMode();
	}

	@Override
	public void setReadPlan(int plan) {
		mView.setReadPlan(plan);
	}

	@Override
	public int getReadPlan() {
		return currIndex;
	}

	@Override
	public void initTopChild() {
		mView.showTopChild();
	}

	@Override
	public void initNovelDetails() {
		mView.initNovelDetails();
	}

	@Override
	public void setVolume(boolean isVolume) {
		mModel.setVolume(isVolume);
	}

	@Override
	public boolean isVolume() {
		return mModel.isVolume();
	}

	@Override
	public void setBrightness(int brightness) {
		mModel.setBrightness(brightness);
	}

	@Override
	public int getBrightness() {
		return mModel.getBrightness();
	}

	@Override
	public Chapter getCurrChapter() {
		return chapter;
	}

	@Override
	public void setCurrIndex(int currIndex) {
		if (currIndex > mChapterList.size() - 1) {
			currIndex = mChapterList.size() - 1;
		}
		if (currIndex < 0) {
			currIndex = 0;
		}
		this.currIndex = currIndex;
		Logger.i("mPersenter  currIndex :  "  +  currIndex);
	}

	@Override
	public int getCurrIndex() {
		return currIndex;
	}

	@Override
	public StoryVoBean getStoryInfo() {
		return mView.getStoryInfo();
	}

	@Override
	public ArrayList<ChapterBean> getChapterList() {
		return mView.getChapterList();
	}

	@Override
	public ArrayList<VolumeBean> getVolumeList() {
		return mView.getVolumeList();
	}

	@Override
	public void saveData() {
		if (mChapterList != null && !mChapterList.isEmpty()) {
			mModel.setCurrIndex(mView.getStoryInfo().getId(), getCurrIndex());
			mModel.setPageNum(mView.getStoryInfo().getId(), pageNum);
			int index = getCurrIndex();
			if(index > mChapterList.size()-1){
				index = mChapterList.size()-1;
			}
			mModel.setChapterName(mView.getStoryInfo().getId(),mChapterList.get(index).getName());
			mModel.setUnReadingCount(mView.getStoryInfo().getId(),mChapterList.size() - (index+1));

			mModel.updatChapterId(mChapterList.get(index).getId());
		}
	}

	@Override
	public void setReadMode(int mode) {
		if(mModel.getReadMode() == ReadActivity.VERTICAL_MODE){
			setCurrIndex(verticalCurrIndex);
		}else{
			setCurrIndex(currIndex);
		}
		chapterCheck(currIndex, NEXT, REFRESH_MODE);
		if(!pagesVe.isEmpty() && pageNum > pagesVe.size()-1){
			pageNum = pagesVe.size()-1;
		}
		if(!pagesVe.isEmpty()){
			linesVe = pagesVe.get(pageNum);
		}
		p = pageNum;
		//TODO 设置当前的currIndex 和 pageNum
		Logger.i("当前pageNum : " + pageNum);
		mModel.setReadMode(mode);
		mView.resetReadMode(mode);
	}

	@Override
	public int getReadMode() {
		return mModel.getReadMode();
	}

	@Override
	public void setPageNumUp() {
		if (pageNum > pagesVe.size() - 1) {
			return;
		}
		++pageNum;
		Logger.i("下一页动画完成   pageNum == " + pageNum);
	}

	@Override
	public void setPageNumDown() {
		if (pageNum <= 0) {
			return;
		}
		--pageNum;
		Logger.i("上一页动画完成   pageNum == " + pageNum);
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	@Override
	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public void showControlWindow() {
		mView.showControlWindow();
	}
	
	@Override
	public void requestChaterList(String id) {
//		mModel.requestChaterList(id);
	}

	@Override
	public ArrayList<ChapterBean> getDatas() {
		ArrayList<ChapterBean> datas = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pagesVe.size(); i++) {
			ChapterBean chapter = new ChapterBean();
			for (int j = 0; j < pagesVe.get(i).size(); j++) {
				if (j == pagesVe.get(i).size() - 1) {
					sb.append(pagesVe.get(i).get(j));
				} else {
					sb.append(pagesVe.get(i).get(j) + "\r\n");
				}

			}
			String id = currIndex + "" + i;
			chapter.setId(id);
			chapter.setName(mView.getChapterList().get(currIndex).getName());
			chapter.setContent(sb.toString());
			chapter.setGroupId(currIndex);
			chapter.setChildId(i);
			sb.setLength(0);
			datas.add(chapter);
		}
		return datas;
	}

	public boolean scrollNext() {
		int nextCurrIndex = currIndex + 1;
		boolean next = chapterCheck(nextCurrIndex, NEXT, VERTICAL_MODE);
		chapterCheck(nextCurrIndex + 1, NEXT, LOAD_MODE);
		return next;
	}

	public boolean scrollPre() {
		int preCurrIndex = currIndex - 1;
		boolean pre = chapterCheck(preCurrIndex, PRE, VERTICAL_MODE);
		chapterCheck(preCurrIndex - 1, PRE, LOAD_MODE);
		return pre;
	}

	public void onExpandGroup() {
		mView.onExpandGroup();
	}

	@Override
	public boolean getIsNotChangePager() {
		return isNotChangePage;
	}

	@Override
	public void setIsNotChangePager(boolean state) {
		isNotChangePage = state;
	}

	@Override
	public boolean isNextToPre() {
		return isNextToPre;
	}

	@Override
	public void setNextToPre(boolean isNextToPre) {
		this.isNextToPre = isNextToPre;
	}

	@Override
	public boolean isPretToNext() {
		return isPretToNext;
	}

	@Override
	public void setPretToNext(boolean isPretToNext) {
		this.isPretToNext = isPretToNext;
	}

	@Override
	public void setClickCurrIndex(int xu) {
		this.verticalCurrIndex = xu;
	}

	@Override
	public int getClickCurrIndex() {
		return verticalCurrIndex;
	}
	
	@Override
	public void setCurrIndexUp() {
		currIndex += 1;
	}

	@Override
	public void setCurrIndexDown() {
		currIndex -= 1;
	}

	@Override
	public Vector<Vector<String>> getPagesVe() {
		return pagesVe;
	}
	
	@Override
	public void showNovelDialog(){
		mView.showLoadingPressbar();
	}

	public boolean isLastChapterPage() {
		if (pageNum >= pagesVe.size() - 1 && currIndex == mChapterList.size() - 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFlowingSystem() {
		return mModel.isFlowingSystem();
	}

	@Override
	public void setFlowingSystem(boolean isFlowingSystem) {
		mModel.setFlowingSystem(isFlowingSystem);
	}
	
	@Override
	public void updateSystemBrightness() {
		BrightnessUtil.setCurWindowBrightness(context, BrightnessUtil.getScreenBrightness(context));
		if(isAutoBrightness){
			BrightnessUtil.startAutoBrightness(context);
		}
	}
	
	@Override
	public void setAutoBrightness(boolean isAutoBrightness) {
		this.isAutoBrightness = isAutoBrightness;
	}
	
	@Override
	public boolean isAutoBrightness() {
		return isAutoBrightness;
	}

	public void refreshList() {
		mChapterList = mView.getChapterList();
	}
	
	@Override
	public void setSystemBrightness(int brightness) {
		BrightnessUtil.setCurWindowBrightness(context, brightness);
		if(isAutoBrightness){
			BrightnessUtil.startAutoBrightness(context);
		}
	}
	
	@Override
	public int getSystemBrightness(){
		return mView.getBrightness();
	}

}
