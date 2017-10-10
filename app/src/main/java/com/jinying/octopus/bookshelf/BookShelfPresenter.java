package com.jinying.octopus.bookshelf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.BannerBean;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.bookshelf.source.BookShelfRepository;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.home.OnTabListener;
import com.jinying.octopus.read.ui.ReadActivity;
import com.jinying.octopus.util.CatalogueUtil;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.FileUtil;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.NetworkUtil;
import com.jinying.octopus.util.ParseUtil;
import com.jinying.octopus.util.ScreenUtil;
import com.jinying.octopus.util.SharedPreferencesUtil;
import com.jinying.octopus.util.ToastUtil;
import com.jinying.octopus.view.BookCardsPager;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by omyrobin on 2017/8/11.
 */

public class BookShelfPresenter implements BookShelfContract.Presenter  {

    @NonNull
    private Context context;
    @NonNull
    private final BookShelfContract.View mBookShelfView;
    @NonNull
    private final BookShelfRepository mBookShelfRepository;
    @NonNull
    private CompositeSubscription mSubscriptions;
    @NonNull
    private OnTabListener listener;

    private int indicatorChildWidth;//指示器滑动条的宽度

    private ImageView indicatorChild;//指示器滑动条

    private int pageCount;//mBookCardsVp的页数-1;

    private int itemPositon;

    private String volumeBeanJson, chapterBeanJson;

    private ArrayList<ChapterBean> mChapterList;

    private ArrayList<VolumeBean> mVolumeList;

    public int countSelect;//记录选择数

    private ArrayList<StoryVoBean> deleteStoryList;

    private ArrayList<BookShelfBean> bookList;

    public BookShelfPresenter(@NonNull Context context, @NonNull BookShelfContract.View view, @NonNull BookShelfRepository repository, @NonNull OnTabListener listener){
        this.context = Preconditions.checkNotNull(context);
        this.listener = Preconditions.checkNotNull(listener);
        this.mBookShelfView = Preconditions.checkNotNull(view);
        this.mBookShelfRepository = Preconditions.checkNotNull(repository);

        mBookShelfView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
        deleteStoryList = new ArrayList<>();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public boolean isNetError(){
        if(!NetworkUtil.isConnected(context)){
            mBookShelfView.showNetErrorView();
            return true;
        }else{
            getBannerList();
            return false;
        }
    }

    @Override
    public void getBannerList() {
        Subscription subscription = mBookShelfRepository.getmRemoteDataSource().getBannerList().subscribe(new Subscriber<ArrayList<BannerBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
                getBookList();
            }

            @Override
            public void onNext(ArrayList<BannerBean> bannerBeen) {
                mBookShelfView.setBannerList(bannerBeen);
                getBookList();
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getBookList() {
        Subscription subscription = mBookShelfRepository.getmRemoteDataSource().getBookList().subscribe(new Subscriber<ArrayList<BookShelfBean>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
            }

            @Override
            public void onNext(ArrayList<BookShelfBean> bookList) {
                setBookList(bookList);
                if(!bookList.isEmpty()){
                    pageCount = bookList.size()-1;
                }else{
                    pageCount = 0;
                }
                mBookShelfView.setBookList(bookList);
            }
        });
        mSubscriptions.add(subscription);
    }

    public void setBookList(ArrayList<BookShelfBean> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int getBookShelfStyle() {
        return mBookShelfRepository.getmLocalDataSource().getBookShelfStyle();
    }

    @Override
    public int onPageScrolled(int position, float positionOffset, TextView mBookNameTv, ArrayList<BookShelfBean> bookList) {
        if(positionOffset>= 0.5f){
            itemPositon = position+1;
        }else{
            itemPositon = position;
        }
        if(itemPositon > bookList.size() - 1){
            return itemPositon - 1;
        }
        mBookShelfView.setCardsBookInfo(bookList.get(itemPositon));

        if(positionOffset<=0.3f){
            mBookShelfView.setCardsBookAlpha(1.0f - (positionOffset / 0.3f));
        }else if(positionOffset>=0.7f){
            mBookShelfView.setCardsBookAlpha(1.0f - ((1.0f - positionOffset) / 0.3f));
        }else{
            mBookShelfView.setCardsBookAlpha(0.0f);
        }
        return itemPositon;
    }

    @Override
    public void animIndicator(int position, float positionOffset){
        if(indicatorChildWidth!=0){
            int currLeftMargin = indicatorChildWidth * position;
            double offset = positionOffset * indicatorChildWidth;
            RelativeLayout.LayoutParams indicatorParams = (RelativeLayout.LayoutParams) indicatorChild.getLayoutParams();
            indicatorParams.leftMargin = (int) (currLeftMargin + Math.ceil(offset)) + 2;
            indicatorChild.setLayoutParams(indicatorParams);
        }
    }

    /**
     * 配置mBookCardsVp的滑条数
     */
    @Override
    public void configIndicatorCount(RelativeLayout mIndicatorParentRlayout){
        mIndicatorParentRlayout.removeAllViews();
        if(pageCount >= 5){
            mIndicatorParentRlayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorIndicatorBg));
            mIndicatorParentRlayout.setGravity(Gravity.LEFT);

            indicatorChild = new ImageView(context);
            indicatorChild.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            indicatorChildWidth = (ScreenUtil.getScreenWidth(context)- DensityUtils.dp2px(context,90)) / (pageCount + 1);
            RelativeLayout.LayoutParams indicatorChildParams = new RelativeLayout.LayoutParams(indicatorChildWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
            indicatorChildParams.leftMargin = itemPositon * indicatorChildWidth;
            mIndicatorParentRlayout.addView(indicatorChild, indicatorChildParams);
        }else{
            mIndicatorParentRlayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            mIndicatorParentRlayout.setGravity(Gravity.CENTER);

            indicatorChildWidth = DensityUtils.dp2px(context, 35);
            int paddingLeft = DensityUtils.dp2px(context, 5);
            int paddingRight = paddingLeft;
            for (int i=0; i<pageCount+2; i++){
                ImageView indicatorSection = new ImageView(context);
                indicatorSection.setImageResource(R.color.colorIndicatorBg);
                indicatorSection.setPadding(paddingLeft,0,paddingRight,0);
                RelativeLayout.LayoutParams indicatorChildParams = new RelativeLayout.LayoutParams(indicatorChildWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
                if(i < pageCount+1) {
                    indicatorChildParams.leftMargin = i * indicatorChildWidth;
                }else {
                    indicatorChildParams.leftMargin = itemPositon * indicatorChildWidth;
                    indicatorSection.setImageResource(R.color.colorPrimary);
                    indicatorChild = indicatorSection;
                }
                mIndicatorParentRlayout.addView(indicatorSection, indicatorChildParams);
            }
        }
    }

    /**
     * 配置mBookCardsVp
     */
    @Override
    public void configBookCardsVp(BookCardsPager mBookCardsVp, ViewPager.OnPageChangeListener listener){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context,135), DensityUtils.dp2px(context,235));
        mBookCardsVp.setPageSize(pageCount + 1);
        mBookCardsVp.setOffscreenPageLimit(3);
        mBookCardsVp.setBookShelfContractView(mBookShelfView);
        mBookCardsVp.setPageMargin(DensityUtils.dp2px(context,10));
        int marginLeft = DensityUtils.dp2px(context,45);
        params.setMargins(marginLeft,0,0,0);
        mBookCardsVp.setLayoutParams(params);
        mBookCardsVp.setPageTransformer(false, new BookShelfTransformer(context));
        mBookCardsVp.addOnPageChangeListener(listener);

        mBookShelfView.setBookCardsVpConfig();
    }

    @Override
    public void toBookMall() {
        listener.toBookMall();
    }

    @Override
    public void beginReadBook(final StoryVoBean storyVo) {
        volumeBeanJson = FileUtil.readFileData(FileUtil.getNovelChapterList(storyVo.getId()+"_volume").getAbsolutePath());
        chapterBeanJson = FileUtil.readFileData(FileUtil.getNovelChapterList(storyVo.getId()+"").getAbsolutePath());

        if(NetworkUtil.isConnected(context)){
            Subscription subscription = mBookShelfRepository.getmRemoteDataSource().getBookCatalogue(storyVo.getId()).subscribe(new Subscriber<CatalogueBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ToastUtil.showShort(context, e.getMessage());
                }

                @Override
                public void onNext(CatalogueBean catalogueBean) {
                    Gson gson = new Gson();
                    mVolumeList = CatalogueUtil.getVolumeList(catalogueBean.getVolumeList(), catalogueBean.getChapterList());
                    mChapterList = catalogueBean.getChapterList();
                    FileUtil.writeTextFile(FileUtil.getNovelChapterList(storyVo.getId()+"_volume"),  gson.toJson(mVolumeList));
                    FileUtil.writeTextFile(FileUtil.getNovelChapterList(storyVo.getId()+""), gson.toJson(mChapterList));

                    setBookCatalogue(storyVo, mVolumeList, mChapterList);
                }
            });
            mSubscriptions.add(subscription);
        }else{
            if(!TextUtils.isEmpty(volumeBeanJson) && !TextUtils.isEmpty(chapterBeanJson)){
                mVolumeList = ParseUtil.getVolumeList(volumeBeanJson);
                mChapterList = ParseUtil.getChapterList(chapterBeanJson);
                setBookCatalogue(storyVo, mVolumeList, mChapterList);
            }
        }
    }
    @Override
    public void setBookCatalogue(StoryVoBean storyVo, ArrayList<VolumeBean> mVolumeList, ArrayList<ChapterBean> mChapterList){
        int currIndex = (int) SharedPreferencesUtil.getParam(context, storyVo.getId()+"-"+ Constant.CHAPTER_INDEX,0);
        CatalogueUtil.addToNovelChapterCache(mVolumeList, mChapterList);
        ReadActivity.newInstance(context, storyVo,currIndex);
    }

    @Override
    public void showDeleteView() {
        mBookShelfView.showDeleteView();
        switch (getBookShelfStyle()){
            case BookShelfFragment.GRID_STYLE:
                mBookShelfView.showGridDeleteView();
                break;

            default:
                mBookShelfView.showCardsDeleteView();
                break;
        }
    }

    @Override
    public void checkedDeleteChk(StoryVoBean storyVoBean, int dataSize) {
        //TODO notifyBottomView
        if(storyVoBean.isChecked()){
            countSelect += 1;
            deleteStoryList.add(storyVoBean);
        }else{
            countSelect -= 1;
            deleteStoryList.remove(storyVoBean);
        }
        //TODO notifyBottomView 全选
        Logger.i(" countSelect  : " +  countSelect  + " ----   dataSize :  " +  dataSize);
        if(countSelect == dataSize){
            mBookShelfView.notifyBottomView(true, true);
        }else if(countSelect==0){
            mBookShelfView.notifyBottomView(false, false);
        }else{
            mBookShelfView.notifyBottomView(false, true);
        }
    }

    @Override
    public void deleteAllBook(boolean isDeleteAll, int dataSize){
        if(isDeleteAll)
            resetDeleteConfig();
        for (int i=0 ; i<dataSize; i++){
            bookList.get(i).getStoryVo().setChecked(isDeleteAll);
            checkedDeleteChk(bookList.get(i).getStoryVo(), dataSize);
        }
    }

    @Override
    public void resetDeleteConfig() {
        countSelect = 0;
        deleteStoryList.clear();
    }

    @Override
    public void resetBookViewConfig() {
        mBookShelfView.resetBookViewConfig();
    }

    @Override
    public String getDeleteParams() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < deleteStoryList.size(); i++) {
            if(i == deleteStoryList.size()-1){
                sb.append(deleteStoryList.get(i).getId()+",");
            }else{
                sb.append(deleteStoryList.get(i).getId()+",");
            }
        }
        return sb.toString();
    }

    @Override
    public void deleteSelectBook(final Boolean isDeleteCache) {
        mBookShelfRepository.getmRemoteDataSource().deleteSelectBook(getDeleteParams()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
                resetDeleteConfig();
            }

            @Override
            public void onNext(String s) {
                itemPositon = 0;
                resetDeleteConfig();
                getBookList();
                dismissDeleteView();
                mBookShelfView.deleteSelectBookComplete();
                if(isDeleteCache){
                    for (int j = 0; j < countSelect; j++) {
                        FileUtil.clearFile(FileUtil.getNovelCacheFile(deleteStoryList.get(j).getName()+deleteStoryList.get(j).getId()));
                        FileUtil.clearFile(FileUtil.getNovelChapterList(deleteStoryList.get(j).getId()+""));
                    }
                    mBookShelfView.resetDeleteCache();
                }
            }
        });
    }

    @Override
    public int getItemPositon() {
        return itemPositon;
    }

    @Override
    public void saveBookShelfConifg(int currBookShelfStyle) {
        mBookShelfRepository.getmLocalDataSource().saveBookShelfConifg(currBookShelfStyle);
    }

    @Override
    public void dismissDeleteView(){
        if(getBookShelfStyle() == BookShelfFragment.GRID_STYLE){
            mBookShelfView.dimissGridDeleteView();
        }else{
            mBookShelfView.dimissCardsDeleteView();
        }
    }

    @Override
    public int getCountSelect() {
        return countSelect;
    }
}
