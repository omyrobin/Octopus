package com.jinying.octopus.launch;

import com.jinying.octopus.BasePresenter;
import com.jinying.octopus.BaseView;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bookshelf.BookShelfContract;

import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/22.
 */

public interface LaunchContract {

    interface View extends BaseView<LaunchContract.Presenter> {
        void enterApp(); //进入MainActitivty
    }

    interface Presenter extends BasePresenter {
        void registerUser();

        void loginUser();

        void regrestOrLogin(boolean isNetConnection);
    }

}
