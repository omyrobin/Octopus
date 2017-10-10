package com.jinying.octopus.launch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;
import android.text.TextUtils;

import com.jinying.octopus.App;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.event.EventNetConnection;
import com.jinying.octopus.http.ProgressSubscriber;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.launch.source.LaunchRepository;
import com.jinying.octopus.util.SharedPreferencesUtil;
import com.jinying.octopus.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by omyrobin on 2017/8/22.
 */

public class LaunchPresenter implements LaunchContract.Presenter{

    @NonNull
    private Context context;

    private LaunchContract.View mLaunchView;

    @NonNull
    private final LaunchRepository mRepository;

    @NonNull
    CompositeSubscription mSubscriptions;

    private boolean isNetConnention;

    public LaunchPresenter(@NonNull Context context, @NonNull LaunchContract.View mLaunchView, @NonNull LaunchRepository mRepository) {
        this.context = Preconditions.checkNotNull(context);
        this.mLaunchView = Preconditions.checkNotNull(mLaunchView);
        this.mRepository =  Preconditions.checkNotNull(mRepository);
        mSubscriptions = new CompositeSubscription();
        mLaunchView.setPresenter(this);
    }

    public LaunchPresenter(@NonNull Context context, @NonNull LaunchRepository mRepository) {
        this.context = Preconditions.checkNotNull(context);
        this.mRepository =  Preconditions.checkNotNull(mRepository);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        if(TextUtils.isEmpty(mRepository.getmLocalDataSource().getUserId())){
            registerUser();
        }else{
            loginUser();
        }

        mSubscriptions.add(Observable.timer(3, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                if(mLaunchView!=null)
                    mLaunchView.enterApp();
            }
        }));
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void registerUser() {
        Subscription subscription = mRepository.getmRemoteDataSource().register().filter(new Func1<UserBean, Boolean>() {
            @Override
            public Boolean call(UserBean userBean) {
                SharedPreferencesUtil.setParam(App.getInstance(), Constant.USER_ID, userBean.getUserId());
                App.getInstance().setUserBean(userBean);
                return isNetConnention;
            }
        }).flatMap(new Func1<UserBean, Observable<EventNetConnection>>() {
            @Override
            public Observable<EventNetConnection> call(UserBean userBean) {
                return Observable.just(new EventNetConnection());
            }
        }).subscribe(new Subscriber<EventNetConnection>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
            }

            @Override
            public void onNext(EventNetConnection netConnection) {
                EventBus.getDefault().post(netConnection);
                isNetConnention = false;
            }
        });
        mSubscriptions .add(subscription);
    }

    @Override
    public void loginUser() {
        Subscription subscription = mRepository.getmRemoteDataSource().longin().filter(new Func1<UserBean, Boolean>() {
            @Override
            public Boolean call(UserBean userBean) {
                App.getInstance().setUserBean(userBean);
                return isNetConnention;
            }
        }).flatMap(new Func1<UserBean, Observable<EventNetConnection>>() {
            @Override
            public Observable<EventNetConnection> call(UserBean userBean) {
                return Observable.just(new EventNetConnection());
            }
        }).subscribe(new Subscriber<EventNetConnection>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EventNetConnection netConnection) {
                EventBus.getDefault().post(netConnection);
                isNetConnention = false;
            }
        });
        mSubscriptions .add(subscription);
    }

    @Override
    public void regrestOrLogin(boolean isNetConnention) {
        this.isNetConnention = isNetConnention;
        if(!TextUtils.isEmpty(mRepository.getmLocalDataSource().getUserId())){
            loginUser();
        }else{
            registerUser();
        }
    }
}
