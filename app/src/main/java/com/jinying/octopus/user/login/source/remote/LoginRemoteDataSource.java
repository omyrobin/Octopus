package com.jinying.octopus.user.login.source.remote;

import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.user.login.source.LoginDataSource;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/26.
 */

public class LoginRemoteDataSource implements LoginDataSource {

    @Override
    public Observable<UserBean> login(String phoneNumber, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", phoneNumber);
        params.put("password", password);
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<UserBean>>> observable = postService.loginUser(Url.USER_PHONELOGIN, params,RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }
}
