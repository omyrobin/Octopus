package com.jinying.octopus.bookmall.classifydetail.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jinying.octopus.bookmall.classifydetail.source.remote.ClassifyDetailsRemoteDataSource;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class ClassifyDetailsRepository {

    private Context context;

    @NonNull
    private ClassifyDetailsRemoteDataSource mDetailsRemoteDataSource;

    public ClassifyDetailsRepository(Context context){
        this.context = context;
        mDetailsRemoteDataSource = new ClassifyDetailsRemoteDataSource();
    }

    @NonNull
    public ClassifyDetailsRemoteDataSource getmDetailsRemoteDataSource() {
        return mDetailsRemoteDataSource;
    }
}
