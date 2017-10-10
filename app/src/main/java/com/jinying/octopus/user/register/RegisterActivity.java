package com.jinying.octopus.user.register;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.user.ILogin;
import com.jinying.octopus.user.TencentIUiListener;
import com.jinying.octopus.user.state.UserState;
import com.jinying.octopus.util.ToastUtil;
import com.jinying.octopus.view.TimerButton;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by omyrobin on 2017/8/26.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View, View.OnClickListener, ILogin {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.et_register_name)
    EditText mRegisterNameEt;
    @BindView(R.id.et_register_phongnumber)
    EditText mRegisterPhoneNumberEt;
    @BindView(R.id.et_register_newpassword)
    EditText mRegisterNewPasswordEt;
    @BindView(R.id.et_register_affirm_password)
    EditText mRegisterAffirmPasswordEt;
    @BindView(R.id.et_register_authcode)
    EditText mRegisterAutoCodeEt;
    @BindView(R.id.btn_register_authcode)
    TimerButton mRegistAuthCodeBtn;
    private ImageView mWxLoginImg;
    private ImageView mSinaLoginImg;
    private ImageView mQQLoginImg;
    @NonNull
    private RegisterContract.Presenter mPresenter;
    private String nickName;
    private String phoneNubmer;
    private String newPassword;
    private String affirmPassword;
    private String authCode;

    public static void newInstance(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        EventBus.getDefault().register(this);
        return R.layout.activity_register;
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(getString(R.string.register));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initializeView() {
        mPresenter = new RegisterPresenter(this,this);
        initAuthCodeBtn();

        mWxLoginImg = (ImageView) findViewById(R.id.img_wx_login);
        mWxLoginImg.setOnClickListener(this);
        mSinaLoginImg = (ImageView) findViewById(R.id.img_sina_login);
        mSinaLoginImg.setOnClickListener(this);
        mQQLoginImg = (ImageView) findViewById(R.id.img_qq_login);
        mQQLoginImg.setOnClickListener(this);
    }

    private void initAuthCodeBtn(){
        mRegistAuthCodeBtn.setLen(60);
        mRegistAuthCodeBtn.setAfterText(getString(R.string.get_auth_code));
    }

    @Override
    public boolean checkInputString(){
        nickName = mRegisterNameEt.getText().toString();
        phoneNubmer = mRegisterPhoneNumberEt.getText().toString();
        newPassword = mRegisterNewPasswordEt.getText().toString();
        affirmPassword = mRegisterAffirmPasswordEt.getText().toString();
        authCode = mRegisterAutoCodeEt.getText().toString();
        return mPresenter.checkInputString(nickName, phoneNubmer, authCode, newPassword, affirmPassword);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void userNameIsNotNull() {
        ToastUtil.showShort(this,getString(R.string.user_name_null));
    }

    @Override
    public void phoneNubmerIsNotNull() {
        ToastUtil.showShort(this,getString(R.string.true_phone_number));
    }

    @Override
    public void authCodeIsNoNull() {
        ToastUtil.showShort(this,getString(R.string.true_auth_code));
    }

    @Override
    public void passwordMustBeEnough() {
        ToastUtil.showShort(this,getString(R.string.password_minlength));
    }

    @Override
    public void passwordUnanimously() {
        ToastUtil.showShort(this,getString(R.string.consistent_password));
    }

    @Override
    public String getAuthCode() {
        return mRegisterAutoCodeEt.getText().toString();
    }


    @Override
    public void starTime() {
        mRegistAuthCodeBtn.setEnabled(false);
        mRegistAuthCodeBtn.startTimer();
    }

    @OnClick({R.id.tv_register_finish,R.id.btn_register_authcode})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_register_finish:
                if(checkInputString()){
                    UserState.getInstance().getState().registerUserOrBindMobile(mPresenter);//App类中设置状态
                }
                break;

            case R.id.btn_register_authcode:
                mPresenter.getAuthCode(mRegisterPhoneNumberEt.getText().toString());
                break;

            case R.id.img_wx_login:
                LoginToWx();
                break;

            case R.id.img_sina_login:
                ToastUtil.showShort(this, "暂不支持新浪微博登录，请关注后续版本...");
                break;

            case R.id.img_qq_login:
//                ToastUtil.showShort(this, "暂不支持QQ登录，请关注后续版本...");
                LoginToQQ();
                break;
        }
    }

    @Override
    public void LoginToWx() {
        if (!api.isWXAppInstalled()) {
            ToastUtil.showShort(this,"您尚未安装微信");
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    @Override
    public void LoginToQQ() {
        tencentIUiListener = new TencentIUiListener(this,mTencent);
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", tencentIUiListener);
        }
    }

    /**
     * 观察----用户状态改变
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventCutId event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(mRegistAuthCodeBtn!=null)
            mRegistAuthCodeBtn.stopTimer();
    }
}

