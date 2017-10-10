package com.jinying.octopus.mine;

import com.jinying.octopus.BasePresenter;
import com.jinying.octopus.BaseView;

/**
 * Created by omyrobin on 2017/8/21.
 */

public interface MineContract {

    interface View extends BaseView<MineContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
