package com.jinying.octopus.launch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;

import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.home.MainActivity;
import com.jinying.octopus.launch.source.LaunchRepository;

/**
 * Created by omyrobin on 2017/8/22.
 */

public class LaunchActivity extends BaseActivity implements LaunchContract.View{

    @NonNull
    private LaunchContract.Presenter mLaunchPresenter;

    @NonNull
    private LaunchRepository mRepository;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initializeView() {
        checkSelfPermissionWrite();
        mRepository = new LaunchRepository(this);
        mLaunchPresenter = new LaunchPresenter(this, this, mRepository);
    }

    @Override
    public void enterApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setPresenter(LaunchContract.Presenter presenter) {
        mLaunchPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLaunchPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLaunchPresenter.unsubscribe();
    }
}
