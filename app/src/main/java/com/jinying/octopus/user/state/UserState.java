package com.jinying.octopus.user.state;

/**
 * 状态模式
 * Created by omyrobin on 2017/8/28.
 */

public class UserState {

    public static UserState mUserState;

    private IUserState mState = new ErrorState();//默认异常状态(非游客，非登录)

    public static UserState getInstance(){
        if(mUserState == null){
            mUserState = new UserState();
        }
        return mUserState;
    }

    public void setState(IUserState mState) {
        this.mState = mState;
    }

    public IUserState getState() {
        return mState;
    }
}
