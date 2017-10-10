package com.jinying.octopus.mine;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

/**
 * Created by omyrobin on 2017/8/21.
 */

public class MinePresenter implements MineContract.Presenter {

    @NonNull
    private MineContract.View mMineView;

    public MinePresenter(@NonNull MineContract.View view){
        mMineView = Preconditions.checkNotNull(view);
        mMineView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
