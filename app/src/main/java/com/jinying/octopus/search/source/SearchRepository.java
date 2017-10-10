package com.jinying.octopus.search.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jinying.octopus.search.source.local.SearchLocalDataSource;
import com.jinying.octopus.search.source.remote.SearchRemoteDataSource;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchRepository {

    @NonNull
    private SearchRemoteDataSource mRemoteDataSource;
    @NonNull
    private SearchLocalDataSource mLocalDataSource;

    public SearchRepository(@NonNull Context context) {
        mRemoteDataSource = new SearchRemoteDataSource();
        mLocalDataSource = new SearchLocalDataSource();
    }

    @NonNull
    public SearchRemoteDataSource getmRemoteDataSource() {
        return mRemoteDataSource;
    }

    @NonNull
    public SearchLocalDataSource getmLocalDataSource() {
        return mLocalDataSource;
    }
}
