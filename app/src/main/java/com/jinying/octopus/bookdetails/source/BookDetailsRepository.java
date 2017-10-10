package com.jinying.octopus.bookdetails.source;

import android.support.annotation.NonNull;

import com.jinying.octopus.bookdetails.source.remote.BookDetailsRemoteDataSource;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class BookDetailsRepository {

    @NonNull
    private BookDetailsRemoteDataSource mDetailsRemoteDataSource;

    public BookDetailsRepository() {
        mDetailsRemoteDataSource = new BookDetailsRemoteDataSource();
    }

    @NonNull
    public BookDetailsRemoteDataSource getmDetailsRemoteDataSource() {
        return mDetailsRemoteDataSource;
    }
}
