package com.jinying.octopus.launch.source.local;

import com.jinying.octopus.App;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.launch.source.LaunchDataSource;
import com.jinying.octopus.util.SharedPreferencesUtil;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/22.
 */

public class LaunchLocalDataSource implements LaunchDataSource{

    @Override
    public Observable<UserBean> register() {
        return null;
    }

    @Override
    public Observable<UserBean> longin() {
        return null;
    }

    @Override
    public String getUserId() {
        return (String) SharedPreferencesUtil.getParam(App.getInstance(), Constant.USER_ID, "");
    }
}
