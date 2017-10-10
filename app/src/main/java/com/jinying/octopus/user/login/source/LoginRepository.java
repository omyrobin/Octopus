package com.jinying.octopus.user.login.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jinying.octopus.user.login.source.remote.LoginRemoteDataSource;

/**
 * Created by omyrobin on 2017/8/26.
 */

public class LoginRepository {

    private Context context;

    @NonNull
    private LoginRemoteDataSource mRemoteDataSource;

    public LoginRepository(Context context) {
        this.context = context;
        mRemoteDataSource = new LoginRemoteDataSource();
    }

    @NonNull
    public LoginRemoteDataSource getmRemoteDataSource() {
        return mRemoteDataSource;
    }
}
