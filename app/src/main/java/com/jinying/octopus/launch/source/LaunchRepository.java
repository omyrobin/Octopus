package com.jinying.octopus.launch.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jinying.octopus.launch.source.local.LaunchLocalDataSource;
import com.jinying.octopus.launch.source.remote.LaunchRemoteDataSource;

/**
 * Created by omyrobin on 2017/8/22.
 */

public class LaunchRepository {

    private Context context;

    @NonNull
    private final LaunchRemoteDataSource mRemoteDataSource;

    @NonNull
    private final LaunchLocalDataSource mLocalDataSource;

    public LaunchRepository(Context context) {
        this.context = context;
        mRemoteDataSource = new LaunchRemoteDataSource();
        mLocalDataSource = new LaunchLocalDataSource();
    }

    @NonNull
    public LaunchRemoteDataSource getmRemoteDataSource() {
        return mRemoteDataSource;
    }

    @NonNull
    public LaunchLocalDataSource getmLocalDataSource() {
        return mLocalDataSource;
    }
}

