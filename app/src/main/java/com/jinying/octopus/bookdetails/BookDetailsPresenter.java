package com.jinying.octopus.bookdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.ChapterBean;
import com.jinying.octopus.bean.StoryListBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.VolumeBean;
import com.jinying.octopus.bookdetails.source.BookDetailsRepository;
import com.jinying.octopus.event.EventAddToBook;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.read.ui.ReadActivity;
import com.jinying.octopus.util.CatalogueUtil;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.FileUtil;
import com.jinying.octopus.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class BookDetailsPresenter implements BookDetailsContract.Presenter {

    @NonNull
    private BookDetailsContract.View mBookDetailsView;

    @NonNull
    private BookDetailsRepository mRepository;

    @NonNull
    private CompositeSubscription mSubscriptions;
    @NonNull
    private Context context;

    private StoryVoBean storyVoBean;

    private ArrayList<VolumeBean> volumeListBeen;

    private ArrayList<ChapterBean> chapterListBeen;

    private boolean isunfold;

    public BookDetailsPresenter(@NonNull BookDetailsContract.View mBookDetailsView, Context context) {
        this.mBookDetailsView = Preconditions.checkNotNull(mBookDetailsView);
        this.context = Preconditions.checkNotNull(context);

        mBookDetailsView.setPresenter(this);
        mRepository = new BookDetailsRepository();
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getBookDetails(String id) {
        Observable<StoryVoBean> ob1 = mRepository.getmDetailsRemoteDataSource().getBookDetails(id);
        Observable<CatalogueBean> ob2 = mRepository.getmDetailsRemoteDataSource().getBookCatalogue(id);
        Subscription subscription = Observable.concat(ob1,ob2).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if(o instanceof StoryVoBean){
                    storyVoBean = (StoryVoBean)o;
                    mBookDetailsView.setBookDetails(storyVoBean);
                }else{
                    CatalogueBean catalogueBean = (CatalogueBean) o;
                    mBookDetailsView.setCatalogue(catalogueBean);

                    volumeListBeen = CatalogueUtil.getVolumeList(catalogueBean.getVolumeList(), catalogueBean.getChapterList());
                    chapterListBeen = catalogueBean.getChapterList();
                }
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getRecommendStory(String type, int pageNo, String id){
        Subscription subscription = mRepository.getmDetailsRemoteDataSource().getRecommendStory(type, pageNo, id).subscribe(new Subscriber<ArrayList<StoryListBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
            }

            @Override
            public void onNext(ArrayList<StoryListBean> recommendStoryBeen) {
                mBookDetailsView.setRecommedStory(recommendStoryBeen);
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void setCatalogueCache() {
        Gson gson = new Gson();
        String mVolumeListJosn = gson.toJson(volumeListBeen);
        String mChapterListJosn = gson.toJson(chapterListBeen);
        //将目录写入缓存文件夹
        FileUtil.writeTextFile(FileUtil.getNovelChapterList(storyVoBean.getId()+"_volume"), mVolumeListJosn);
        FileUtil.writeTextFile(FileUtil.getNovelChapterList(storyVoBean.getId()+""), mChapterListJosn);


        Subscription subscription = mRepository.getmDetailsRemoteDataSource().addToBookShelf(storyVoBean.getId()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
            }

            @Override
            public void onNext(String s) {
                mBookDetailsView.isAddBookshelf();
                EventBus.getDefault().post(new EventAddToBook());
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void beginReadBook() {
        CatalogueUtil.addToNovelChapterCache(volumeListBeen, chapterListBeen);
        ReadActivity.newInstance(context,storyVoBean,0);
    }

    @Override
    public void toBookDetailsActivity(long id) {
        BookDetailsActivity.newInstance(context,id + "");
    }

    //简介超过4行显示可展开icon
    @Override
    public void getDescriptionTvLineCount(final TextView mBookBriefTv) {

        mBookBriefTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                int count = mBookBriefTv.getLineCount();
                if(count>4){
                    mBookBriefTv.setLines(4);
                    mBookBriefTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.selector_bookdetails_brief);
                    mBookBriefTv.setCompoundDrawablePadding(DensityUtils.dp2px(context, 10));
                }
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    mBookBriefTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mBookBriefTv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            }
        });
    }

    /**
     * 展开or收缩小说简介
     * @param mBookBriefTv
     */
    @Override
    public void isUnfold(TextView mBookBriefTv){
        if (isunfold) {
            mBookBriefTv.setSelected(false);
            mBookBriefTv.setLines(4);
        } else {
            mBookBriefTv.setSelected(true);
            mBookBriefTv.setMaxLines(context.getWallpaperDesiredMinimumHeight());
        }
        isunfold = !isunfold;
    }
}
