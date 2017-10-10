package com.jinying.octopus.user.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jinying.octopus.App;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.user.login.source.LoginRepository;
import com.jinying.octopus.util.MobileNumber;
import com.jinying.octopus.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by omyrobin on 2017/8/26.
 */

public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private LoginContract.View mLoginView;

    private Context context;

    @NonNull
    private LoginRepository mRepository;

    @NonNull
    CompositeSubscription mSubscriptions;

    public LoginPresenter(@NonNull LoginContract.View mLoginView, Context context) {
        this.mLoginView = mLoginView;
        this.context = context;
        mLoginView.setPresenter(this);
        mRepository = new LoginRepository(context);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loginUser(String phoneNumber, String password) {
        if(TextUtils.isEmpty(phoneNumber)){
            mLoginView.usernameIsNull();
            return;
        }

        if(TextUtils.isEmpty(password)){
            mLoginView.userPasswordIsNull();
            return;
        }

        if(!MobileNumber.isMobileNO(phoneNumber)){
            mLoginView.isNotMoblieNumber();
            return;
        }

        Subscription subscription = mRepository.getmRemoteDataSource().login(phoneNumber, password).subscribe(new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
            }

            @Override
            public void onNext(UserBean userBean) {
                App.getInstance().setUserBean(userBean);
                EventBus.getDefault().post(new EventCutId());
            }
        });
        mSubscriptions .add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
