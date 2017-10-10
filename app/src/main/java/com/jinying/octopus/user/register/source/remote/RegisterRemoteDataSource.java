package com.jinying.octopus.user.register.source.remote;

import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.user.register.source.RegisterDataSource;

import java.util.HashMap;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/28.
 */

public class RegisterRemoteDataSource implements RegisterDataSource {

    @Override
    public Observable<String> postAuthCode(String phoneNumber) {
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<String>>> observable = postService.postAuthCode(Url.USER_SMS_RB_CODE,phoneNumber, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<String> bindMobile(HashMap<String, Object> params) {
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<String>>> observable = postService.bindMobile(Url.USER_BIND, params, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<UserBean> registerUser(HashMap<String, Object> params) {
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<UserBean>>> observable = postService.haveUserRegisterUser(Url.USER_PHONEREGISTER, params, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }
}
