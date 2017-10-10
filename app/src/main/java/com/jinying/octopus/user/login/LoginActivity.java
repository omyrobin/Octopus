package com.jinying.octopus.user.login;

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
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.user.ILogin;
import com.jinying.octopus.user.TencentIUiListener;
import com.jinying.octopus.user.register.RegisterActivity;
import com.jinying.octopus.util.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by omyrobin on 2017/8/26.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View, ILogin, View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.et_login_phongnumber)
    EditText mLoginPhongNumberEt;
    @BindView(R.id.et_login_password)
    EditText mLoginPasswordEt;
    private ImageView mWxLoginImg;
    private ImageView mSinaLoginImg;
    private ImageView mQQLoginImg;

    @NonNull
    private LoginContract.Presenter mPresenter;

    public static void newInstance(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        EventBus.getDefault().register(this);
        return R.layout.activity_login;
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(getString(R.string.login));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initializeView() {
        mWxLoginImg = (ImageView) findViewById(R.id.img_wx_login);
        mWxLoginImg.setOnClickListener(this);
        mSinaLoginImg = (ImageView) findViewById(R.id.img_sina_login);
        mSinaLoginImg.setOnClickListener(this);
        mQQLoginImg = (ImageView) findViewById(R.id.img_qq_login);
        mQQLoginImg.setOnClickListener(this);
        mPresenter = new LoginPresenter(this,this);
    }


    public void login(){
        String phoneNumber = mLoginPhongNumberEt.getText().toString();
        String password = mLoginPasswordEt.getText().toString();
        mPresenter.loginUser(phoneNumber,password);
    }

    @OnClick({R.id.tv_login_register,R.id.tv_login_finish})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_login_register:
                RegisterActivity.newInstance(this);
                break;

            case R.id.tv_login_finish:
                String phoneNumber = mLoginPhongNumberEt.getText().toString();
                String password = mLoginPasswordEt.getText().toString();
                mPresenter.loginUser(phoneNumber, password);
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
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void usernameIsNull() {
        ToastUtil.showShort(this,getString(R.string.username_null));
    }

    @Override
    public void userPasswordIsNull() {
        ToastUtil.showShort(this,getString(R.string.password_null));
    }

    @Override
    public void isNotMoblieNumber() {
        ToastUtil.showShort(this,getString(R.string.true_phone_number));
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
    }
}
