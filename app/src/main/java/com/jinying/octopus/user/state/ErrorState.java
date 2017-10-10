package com.jinying.octopus.user.state;

import com.jinying.octopus.App;
import com.jinying.octopus.user.register.RegisterContract;
import com.jinying.octopus.util.ToastUtil;

/**
 * Created by omyrobin on 2017/8/28.
 */

public class ErrorState implements IUserState{

    @Override
    public void registerUserOrBindMobile(RegisterContract.Presenter mPresenter) {
        ToastUtil.showShort(App.getInstance(),"非法请求,请查看网络连接状态");
    }

}
