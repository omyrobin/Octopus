package com.jinying.octopus.launch.source;

import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.UserBean;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/22.
 */

public interface LaunchDataSource {

    Observable<UserBean> register();

    Observable<UserBean> longin();

    String getUserId();
}
