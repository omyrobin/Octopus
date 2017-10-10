package com.jinying.octopus.user.login;

import com.jinying.octopus.BasePresenter;
import com.jinying.octopus.BaseView;

/**
 * Created by omyrobin on 2017/8/26.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void usernameIsNull();

        void userPasswordIsNull();

        void isNotMoblieNumber();
    }
    interface Presenter extends BasePresenter{

        void loginUser(String phoneNumber, String password);
    }
}
