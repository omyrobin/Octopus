package com.jinying.octopus.launch.source.remote;

import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.launch.source.LaunchDataSource;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/22.
 */

public class LaunchRemoteDataSource implements LaunchDataSource{

    @Override
    public Observable<UserBean> register() {
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<UserBean>>> observable = postService.registerUser(Url.USER_REGISTER, "android", RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<UserBean> longin() {
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<UserBean>>> observable = postService.authLoginUser(Url.USER_AUTOLOGIN, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public String getUserId() {
        return "";
    }
}
