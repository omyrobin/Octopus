package com.jinying.octopus.user.register.source;

import com.jinying.octopus.bean.UserBean;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by omyrobin on 2017/8/28.
 */

public interface RegisterDataSource {

    Observable<String> postAuthCode(String phoneNumber);

    Observable<String> bindMobile(HashMap<String, Object> params);

    Observable<UserBean> registerUser(HashMap<String, Object> params);
}
