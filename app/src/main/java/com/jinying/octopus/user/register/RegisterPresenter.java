package com.jinying.octopus.user.register;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jinying.octopus.App;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.user.register.source.RegisterRepository;
import com.jinying.octopus.user.state.BindState;
import com.jinying.octopus.user.state.UserState;
import com.jinying.octopus.util.MobileNumber;
import com.jinying.octopus.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by omyrobin on 2017/8/28.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    @NonNull
    private RegisterContract.View mRegisterView;

    @NonNull
    private RegisterRepository mRepository;

    @NonNull
    private CompositeSubscription mSubscription;

    private Context context;

    private HashMap<String, Object> params;

    private String nickName;

    public RegisterPresenter(@NonNull RegisterContract.View mRegisterView, Context context) {
        this.mRegisterView = mRegisterView;
        this.context = context;

        mRegisterView.setPresenter(this);
        mRepository = new RegisterRepository(context);

        mSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }

    @Override
    public void getAuthCode(String phoneNumber) {
        mRepository.getmRemoteDataSource().postAuthCode(phoneNumber).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
            }

            @Override
            public void onNext(String s) {
                mRegisterView.starTime();
            }
        });
    }

    @Override
    public boolean checkInputString(String nickName, String phoneNubmer, String authCode, String newPassword, String affirmPassword){
        if(TextUtils.isEmpty(nickName)){
            mRegisterView.userNameIsNotNull();
            return false;
        }
        this.nickName = nickName;

        if(TextUtils.isEmpty(phoneNubmer)||!MobileNumber.isMobileNO(phoneNubmer)){
            mRegisterView.phoneNubmerIsNotNull();
            return false;
        }

        if(TextUtils.isEmpty(authCode)){ //判断是否与 获取验证码的手机号一致
            mRegisterView.authCodeIsNoNull();
            return false;
        }

        if(TextUtils.isEmpty(newPassword)||newPassword.length()<6){
            mRegisterView.passwordMustBeEnough();
            return false;
        }

        if(!affirmPassword.equals(newPassword)){
            mRegisterView.passwordUnanimously();
            return false;
        }
        if(newPassword.length() < 6){
            return false;
        }

        params = new HashMap();
        params.put("nickName", nickName);
        params.put("mobilePhone", phoneNubmer);
        params.put("password", newPassword);
        params.put("clientCode", mRegisterView.getAuthCode());
        params.put("os", "android");
        return true;
    }

    @Override
    public void bindMobile() {
        mRepository.getmRemoteDataSource().bindMobile(params).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
            }

            @Override
            public void onNext(String str) {
                UserState.getInstance().setState(new BindState());
                App.getInstance().getUserBean().setNickName(nickName);
                App.getInstance().getUserBean().setIsBinding(1);
                EventBus.getDefault().post(new EventCutId());
            }
        });
    }

    @Override
    public void registerUser() {
        mRepository.getmRemoteDataSource().registerUser(params).subscribe(new Subscriber<UserBean>() {
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
    }
}
