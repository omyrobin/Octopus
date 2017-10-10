package com.jinying.octopus.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jinying.octopus.App;
import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.user.login.LoginActivity;
import com.jinying.octopus.user.register.RegisterActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by omyrobin on 2017/8/26.
 */

public class UserActivity extends BaseActivity {

    @BindView(R.id.tv_user_login)
    TextView mUserLoginTv;
    @BindView(R.id.tv_user_register)
    TextView mUserRegisterTv;
    @BindView(R.id.tv_user_username)
    TextView mUserUserName;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initializeView() {
        UserBean userBean = App.getInstance().getUserBean();
        if(userBean != null){
            mUserUserName.setText(userBean.getNickName());
        }
    }

    @OnClick({R.id.tv_user_login, R.id.tv_user_register})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_user_login:
                LoginActivity.newInstance(this);
                break;

            case R.id.tv_user_register:
                RegisterActivity.newInstance(this);
                break;

            default:

                break;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
