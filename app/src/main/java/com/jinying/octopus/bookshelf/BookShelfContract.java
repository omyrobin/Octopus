package com.jinying.octopus.bookshelf;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinying.octopus.BasePresenter;
import com.jinying.octopus.BaseView;
import com.jinying.octopus.bean.BannerBean;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.view.BookCardsPager;

import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/11.
 */

public interface BookShelfContract {

    interface View extends BaseView<Presenter> {
        void showNetErrorView();
        void addBook(); //添加图书
        void setBannerList(ArrayList<BannerBean> bannerBeen);
        void setBookList(ArrayList<BookShelfBean> bookList);
        void setBookCardsVpConfig();
        void setCardsBookInfo(BookShelfBean bookInfo);
        void setCardsBookAlpha(float alpha);
        void showGridDeleteView();
        void showCardsDeleteView();
        void notifyBottomView(boolean b, boolean b1);
        void deleteSelectBookComplete();
        void dimissGridDeleteView();
        void dimissCardsDeleteView();
        void resetDeleteCache();
        void showDeleteView();
        void resetBookViewConfig();
    }

    interface Presenter extends BasePresenter{
        boolean isNetError();
        void getBannerList();
        void getBookList();
        int getBookShelfStyle();//获取书架风格
        int onPageScrolled(int position, float positionOffset, TextView mBookNameTv, ArrayList<BookShelfBean> bookList);
        void animIndicator(int position, float positionOffset);
        void configIndicatorCount(RelativeLayout mIndicatorParentRlayout);
        void configBookCardsVp(BookCardsPager mBookCardsVp, ViewPager.OnPageChangeListener listener);
        void toBookMall();
        void beginReadBook(StoryVoBean storyVo);
        void setBookCatalogue(StoryVoBean storyVo, ArrayList<VolumeBean> mVolumeList, ArrayList<ChapterBean> mChapterList);
        void showDeleteView();
        void checkedDeleteChk(StoryVoBean storyVoBean, int dataSize);
        void saveBookShelfConifg(int currBookShelfStyle);
        String getDeleteParams();
        void deleteSelectBook(Boolean isDeleteCache);
        void dismissDeleteView();
        int getCountSelect();
        int getItemPositon();
        void deleteAllBook(boolean isDeleteAll, int dataSize);
        void resetDeleteConfig();
        void resetBookViewConfig();
    }
}
