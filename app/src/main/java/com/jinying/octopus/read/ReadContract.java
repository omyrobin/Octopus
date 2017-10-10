package com.jinying.octopus.read;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONObject;

import android.graphics.Canvas;

import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.read.persenter.impl.BasePresenter;
import com.jinying.octopus.read.ui.impl.BaseView;

public interface ReadContract {
	
	interface View extends BaseView<Presenter> {
		/**
		 * 小说信息
		 */
		StoryVoBean getStoryInfo();
		/**
		 * 小说目录信息
		 */
		ArrayList<ChapterBean> getChapterList();
		/**
		 * 数据库管理者
		 */
//		DBManager getDBManager();
		/**
		 * 获取虚拟机高度
		 */
		int getVirtualBarHeigh();
		/**
		 * 设置显示内容
		 */
		void setContent();
		/**
		 * 字体大小、颜色、背景颜色、左侧目录变更等后刷新内容
		 */
		void refreshContent();
		/**
		 * 显示左侧目录
		 */
		void showLeftWindows();
		/**
		 * 显示topChild
		 */
		void showTopChild();
		/**
		 * 隐藏所有windows
		 */
		void dismissAll();
		/**
		 * 进入详情界面
		 */
		void initNovelDetails();
		/**
		 * 当前是第一章;
		 */
		void isFirstChapter();
		/**
		 * 当前是第一页;
		 */
		void isFirstPage();
		/**
		 * 当前是最后一页;
		 */
		void isLastPage();
		/**
		 * 小说保存的文件夹名称
		 * @return
		 */
		String getStoryDir();
		/**
		 * 显示加入书架dialog
		 */
		void showAddDialog();
		/**
		 * 返回关闭activity
		 */
		void finishActivity();
		/**
		 * 设置阅读方式
		 * @param mode
		 */
		void setReadMode(int mode);
		/**
		 * 重新设置阅读方式
		 * @param mode
		 */
		void resetReadMode(int mode);
		/**
		 * 内容获取失败
		 */
		void onContentFail();
		/**
		 * 显示控制栏
		 */
		void showControlWindow();
		/**
		 * 设置阅读进度
		 * @param plan
		 */
		void setReadPlan(int plan);
		/**
		 * 隐藏plan Tv
		 */
		void setPlanTvGone();
		/**
		 * 获取分组的数据列表
		 * @return
		 */
		ArrayList<VolumeBean> getVolumeList();
		/**
		 * 滑动刷新数据
		 */
		void notifyData();
		/**
		 *
		 */
		void notifyPageNum();
		/**
		 * 刷新字体大小
		 */
		void notifySize();
		/**
		 * 滑动刷新数据展开
		 */
		void onExpandGroup();
		/**
		 * 设置分组目录
		 * @param mVolumeList
		 */
		void setVolumeList(ArrayList<VolumeBean> mVolumeList);
		/**
		 * 设置目录
		 * @param mChapterList
		 */
		void setChapterList(ArrayList<ChapterBean> mChapterList);
		/**
		 * 目录刷新失败
		 */
		void onChpterFail();
		/**
		 * 显示加载进度条
		 */
		void showLoadingPressbar();
		/**
		 * 隐藏
		 */
		void statusBar();
		/**
		 * 获取光感亮度
		 * @return
		 */
		int getBrightness();

		void hideButton();
	}
	
	interface Presenter extends BasePresenter {
		/**
		 * 初始化亮度
		 */
		void initBrightness();
		/**
		 * 初始化配置
		 */
		void initConfig();
		/**
		 * 初始化画笔
		 */
		void initPaint();
		/**
		 * 设置Chapter
		 */
		void setChapter(int currIndex);
		/**
		 * 设置Chapter内容
		 */
		void setChapterContent();
		/**
		 * 分割文本
		 */
		void slicePage(int index);
		/**
		 * 下一页
		 */
		boolean nextPage(int mode);
		/**
		 * 上一页
		 */
		boolean prePage(int mode);
		/**
		 * 下一章
		 */
		boolean nextChapter();
		/**
		 * 上一章
		 */
		boolean preChapter();
		/**
		 * 是第一页
		 */
		boolean isFirstPage();
		/**
		 * 是最后一页
		 */
		boolean isLastPage();
		/**
		 * 绘制内容
		 */
		void draw(Canvas c);
		/**
		 * 设置文字大小
		 * @param size
		 */
		void setTxtSize(int size);
		/**
		 * 设置文字颜色
		 * @param size
		 */
		void setTxtColor(int textColor);
		/**
		 * 设置背影颜色对应的下标
		 * @param resid 
		 */
		void setBgIndex(int bgIndex);
		/**
		 * 设置日间背影颜色对应的下标
		 * @param resid 
		 */
		void setBeforeBgIndex(int bgIndex);
		/**
		 * 获取文字大小
		 * @param size
		 */
		int getTxtSize();
		/**
		 * 获取文字颜色
		 * @param size
		 */
		int getTxtColor();
		/**
		 * 获取背景颜色对应的下标
		 * @param resid 
		 */
		int getBgIndex();
		/**
		 * 是否显示加入书架AlterDialog
		 */
		boolean finishActivity();
		/**
		 * 打开左侧目录
		 */
		void initLeft();
		/**
		 * 阅读当前章节
		 */
		void readCurrChapter(int index, boolean dismiss);
		/**
		 * 打开...
		 */
		void initTopChild();
		/**
		 * 书籍详情
		 */
		void initNovelDetails();
		/**
		 * 设置音量键翻页状态
		 */
		void setVolume(boolean isVolume);
		/**
		 * 获取音量键翻页状态
		 */
		boolean isVolume();
		/**
		 * 设置亮度
		 */
		void setBrightness(int brightness);
		/**
		 * 获取亮度
		 */
		int getBrightness();
		/**
		 * 获取当前章节
		 */
		Chapter getCurrChapter();
		/**
		 * 获取当前章节index
		 */
		int getCurrIndex();
		/**
		 * 获取当前章节index
		 */
		void setCurrIndex(int currIndex);
		/**
		 * 小说信息
		 */
		StoryVoBean getStoryInfo();
		/**
		 * 小说目录信息
		 */
		ArrayList<ChapterBean> getChapterList();
		/**
		 * 保存需要记录的数据(currIndex,pageNum)等
		 */
		void saveData();
		/**
		 * 设置阅读方式
		 */
		void setReadMode(int mode);
		/**
		 * 获取阅读方式
		 */
		int getReadMode();
		/**
		 * 增加PageNum
		 */
		void setPageNumUp();
		/**
		 * 减少PageNum
		 */
		void setPageNumDown();
		/**
		 * 设置电量
		 */
		void setPower(int power);
		/**
		 * 显示控制栏
		 */
		void showControlWindow();
		/**
		 * 上一章
		 */
		void readPreChapter();
		/**
		 * 下一章
		 */
		void readNextChapter();
		/**
		 * 设置阅读进度
		 */
		void setReadPlan(int plan);
		/**
		 * 阅读当前进度
		 */
		void readPlan(int plan);
		/**
		 * 获取当前阅读进度
		 */
		int getReadPlan();
		/**
		 * 获取日间的背景Index;
		 */
		int getSunBack();
		/**
		 * 获取分组的数据列表
		 * @return
		 */
		ArrayList<VolumeBean> getVolumeList();
		/**
		 * @return
		 */
		ArrayList<ChapterBean> getDatas();
		/**
		 * @return 行高
		 */
		int getLineHeight();
		/**
		 * 获取是否完成翻页成功
		 */
		boolean getIsNotChangePager();
		/**
		 * 设置是否翻页状态
		 */
		void setIsNotChangePager(boolean state);
		/**
		 * 获取首页向前翻动到尾页状态
		 * @return
		 */
		boolean isNextToPre();
		/**
		 * 设置首页向前翻动到尾页状态
		 * @param isNextToPre
		 */
		void setNextToPre(boolean isNextToPre);
		/**
		 * 获取尾页向前翻动到首页状态
		 * @return
		 */
		boolean isPretToNext();
		/**
		 * 设置尾页向前翻动到首页状态
		 * @param isNextToPre
		 */
		void setPretToNext(boolean isPretToNext);
		/**
		 * 设置上下滑动模式当前触发点对应的Index;
		 * @param xu
		 */
		void setClickCurrIndex(int xu);
		/**
		 * 获取上下滑动模式当前触发点对应的Index;
		 * @param xu
		 */
		int getClickCurrIndex();
		/**
		 * 设置当前Index +1
		 */
		void setCurrIndexUp();
		/**
		 * 设置当前Index -1
		 */
		void setCurrIndexDown();
		/**
		 * 获取当前的文本数据
		 */
		Vector<Vector<String>> getPagesVe();
		/**
		 * 触发下一章章节切换
		 * @param next
		 */
		void isNextChapter(boolean next);
		/**
		 * 触发上一章章节切换
		 * @param next
		 */
		void isPreChapter(boolean pre);
		/**
		 * 获取是否触发下一章章节切换状态
		 * @return
		 */
		boolean getNextChapter();
		/**
		 * 获取是否触发上一章章节切换状态
		 * @return
		 */
		boolean getPreChapter();
		/**
		 * 请求章节目录
		 */
		void requestChaterList(String id);
		/**
		 * 显示加载进度条
		 */
		void showNovelDialog();
		/**
		 * 是否跟随系统亮度
		 */
		boolean isFlowingSystem();
		/**
		 * 设置是否跟随系统亮度
		 */
		void setFlowingSystem(boolean isFlowingSystem);
		/**
		 * 修改亮度
		 */
		void updateSystemBrightness();
		/**
		 * 
		 */
		void setAutoBrightness(boolean isAutoBrightness);
		/**
		 * 
		 */
		boolean isAutoBrightness();
		/**
		 * 设置自动亮度
		 */
		void setSystemBrightness(int brightness);
		/**
		 * 获取光感亮度
		 */
		int getSystemBrightness();
		/**
		 * 设置初始plan值
		 */
		void setStarPlan(int progress);
		/**
		 * 获取初始plan值
		 */
		int getStarPlan();
		/**
		 * 设置间距
		 */
        void setIntervals(int lineHeightModle);
		/**
		 * 获取间距间距
		 */
		int getintervalMode();
	}
	
}
