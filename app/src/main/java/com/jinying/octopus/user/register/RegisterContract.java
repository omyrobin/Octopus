package com.jinying.octopus.user.register;

import com.jinying.octopus.BasePresenter;
import com.jinying.octopus.BaseView;

/**
 * Created by omyrobin on 2017/8/28.
 */

public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        boolean checkInputString();

        void userNameIsNotNull();

        void phoneNubmerIsNotNull();

        void authCodeIsNoNull();

        void passwordMustBeEnough();

        void passwordUnanimously();

        String getAuthCode();

        void starTime();
    }

    interface Presenter extends BasePresenter{

        boolean checkInputString(String nickName, String phoneNubmer, String authCode, String newPassword, String affirmPassword);

        void bindMobile();

        void registerUser();

        void getAuthCode(String string);
    }
}
