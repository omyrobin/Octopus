package com.jinying.octopus.user.register.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jinying.octopus.user.register.source.remote.RegisterRemoteDataSource;

/**
 * Created by omyrobin on 2017/8/28.
 */

public class RegisterRepository {

    @NonNull
    private RegisterRemoteDataSource mRemoteDataSource;

    private Context context;

    public RegisterRepository(Context context) {
        this.context = context;
        mRemoteDataSource = new RegisterRemoteDataSource();
    }

    @NonNull
    public RegisterRemoteDataSource getmRemoteDataSource() {
        return mRemoteDataSource;
    }
}
