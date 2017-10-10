package com.jinying.octopus.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.bookmall.BookMallFragment;
import com.jinying.octopus.bookshelf.BookShelfFragment;
import com.jinying.octopus.bookshelf.BookShelfPresenter;
import com.jinying.octopus.bookshelf.source.BookShelfRepository;
import com.jinying.octopus.launch.LaunchContract;
import com.jinying.octopus.launch.LaunchPresenter;
import com.jinying.octopus.launch.source.LaunchRepository;
import com.jinying.octopus.mine.MineFragment;
import com.jinying.octopus.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabListener{

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @NonNull
    private LaunchContract.Presenter mLaunchPresenter;

    private Fragment[] mFragments;

    private int currTabIndex;

    public static final int BOOKSHELF = 0;

    public static final int BOOKSMALL = 1;

    public static final int MINE = 2;

    private long exitTime = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(BOOKSHELF);
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(BOOKSMALL);
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(MINE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeView() {
        registerReceiver();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragment();


    }

    private void registerReceiver(){
        mLaunchPresenter = new LaunchPresenter(this, new LaunchRepository(this));
        registerReceiver(mLaunchPresenter);
    }

    private void initFragment(){
        BookShelfFragment bookShelfFragment = new BookShelfFragment();
        BookShelfRepository bookShelfRepository = new BookShelfRepository(this);
        BookShelfPresenter bookShelfPresenter = new BookShelfPresenter(this,bookShelfFragment, bookShelfRepository,this);

        getSupportFragmentManager().beginTransaction().add(R.id.content, bookShelfFragment).show(bookShelfFragment).commit();

        BookMallFragment bookMallFragment = new BookMallFragment();
        MineFragment mineFragment = new MineFragment();

        mFragments = new Fragment[]{bookShelfFragment, bookMallFragment, mineFragment};
    }

    private void switchFragment(int tagIndex){
        if (currTabIndex != tagIndex) {
            FragmentTransaction mFt = getSupportFragmentManager().beginTransaction();
            mFt.hide(mFragments[currTabIndex]);
            if (!mFragments[tagIndex].isAdded()) {
                mFt.add(R.id.content, mFragments[tagIndex]);
            }
            mFt.show(mFragments[tagIndex]).commit();
        }
        currTabIndex = tagIndex;
    }

    @Override
    public void toBookMall() {
        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if((System.currentTimeMillis() - exitTime) > 2000){
                    ToastUtil.showShort(this, "再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    MobclickAgent.onKillProcess(this);
                    System.exit(0);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLaunchPresenter.unsubscribe();
        unregisterReceiver();
    }
}
