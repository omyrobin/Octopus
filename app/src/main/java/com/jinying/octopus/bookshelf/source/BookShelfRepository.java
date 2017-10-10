package com.jinying.octopus.bookshelf.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jinying.octopus.bookshelf.source.local.BookShelfLocalDataSource;
import com.jinying.octopus.bookshelf.source.remote.BookShelfRemoteDataSource;

/**
 * Created by omyrobin on 2017/8/21.
 */

public class BookShelfRepository{

    private Context context;

    @NonNull
    private final BookShelfRemoteDataSource mRemoteDataSource;

    @NonNull
    private final BookShelfLocalDataSource mLocalDataSource;

    public BookShelfRepository(Context context) {
        mRemoteDataSource = new BookShelfRemoteDataSource(context);
        mLocalDataSource = new BookShelfLocalDataSource(context);
    }

    @NonNull
    public BookShelfRemoteDataSource getmRemoteDataSource() {
        return mRemoteDataSource;
    }

    @NonNull
    public BookShelfLocalDataSource getmLocalDataSource() {
        return mLocalDataSource;
    }
}
