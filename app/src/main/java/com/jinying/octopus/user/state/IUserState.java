package com.jinying.octopus.user.state;

import com.jinying.octopus.user.register.RegisterContract;

/**
 * Created by omyrobin on 2017/8/28.
 */

public interface IUserState {

    void registerUserOrBindMobile(RegisterContract.Presenter mPresenter);
}
