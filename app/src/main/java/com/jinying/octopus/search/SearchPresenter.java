package com.jinying.octopus.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinying.octopus.bean.SearchAllBean;
import com.jinying.octopus.bean.SearchBookBean;
import com.jinying.octopus.search.source.SearchRepository;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private Context context;

    @NonNull
    private SearchContract.View mSearchView;

    @NonNull
    private final SearchRepository mSearchRepository;

    @NonNull
    private CompositeSubscription mSubscriptions;

    private ArrayList<String> searchRecords;

    public SearchPresenter(Context context, @NonNull SearchContract.View mSearchView) {
        this.context = context;
        this.mSearchView = mSearchView;

        mSearchView.setPresenter(this);
        mSearchRepository = new SearchRepository(context);
        mSubscriptions = new CompositeSubscription();

    }

    @Override
    public void subscribe() {
        Subscription subscription = mSearchRepository.getmRemoteDataSource().getSearchList().subscribe(new Subscriber<ArrayList<SearchAllBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<SearchAllBean> searchAllBeen) {
                mSearchView.addSearchAllToTagLayout(searchAllBeen);
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void searchBookList(String searchContent, final int pageNo) {
        if(TextUtils.isEmpty(searchContent.trim())){
            mSearchView.showSearchContentNull();
        }else{
            Subscription subscription = mSearchRepository.getmRemoteDataSource().getSearchBookList(searchContent, pageNo).subscribe(new Subscriber<SearchBookBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    mSearchView.setSearchResultListError();
                }

                @Override
                public void onNext(SearchBookBean searchBookBean) {
                    if(pageNo > 1){
                        mSearchView.addSearchResultList(searchBookBean);
                    }else{
                        mSearchView.setSearchResultList(searchBookBean);
                    }
                }
            });
            mSubscriptions.add(subscription);
        }
    }

    @Override
    public void searchKeyWord(String keyword) {
        Subscription subscription = mSearchRepository.getmRemoteDataSource().getSearchKeyword(keyword).subscribe(new Subscriber<ArrayList<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<String> list) {
                mSearchView.setSearchKeyWords(list);
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void setSearchRecord(String searchContent) {
        if(!searchRecords.contains(searchContent)){
            searchRecords.add(searchContent);
            String jsonRecord = new Gson().toJson(searchRecords);
            mSearchRepository.getmLocalDataSource().setSearchRecord(jsonRecord);
        }
    }

    @Override
    public void getSearchRecord() {
        String jsonRecord = mSearchRepository.getmLocalDataSource().getSearchRecord();
        ArrayList<String> recordList = new Gson().fromJson(jsonRecord ,new TypeToken<ArrayList<String>>(){}.getType());
        searchRecords = recordList;
        Subscription subscription =  Observable.just(recordList).subscribe(new Subscriber<ArrayList<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<String> strings) {
                if(strings == null){
                    searchRecords = new ArrayList<>();
                }
                mSearchView.initSearchRecordView(strings);
            }
        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void clearSearchRecord() {
        mSearchRepository.getmLocalDataSource().clearSearchRecord();
    }

}
