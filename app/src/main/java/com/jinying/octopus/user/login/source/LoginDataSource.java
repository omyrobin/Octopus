package com.jinying.octopus.user.login.source;

import com.jinying.octopus.bean.UserBean;

import rx.Observable;

/**
 * Created by omyrobin on 2017/8/26.
 */

public interface LoginDataSource {

    Observable<UserBean> login(String phoneNumber, String password);
}
