package com.jinying.octopus.user;

import android.content.Context;
import android.text.TextUtils;

import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.ToastUtil;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by omyrobin on 2017/9/3.
 */

public class TencentIUiListener implements IUiListener {

    private Context context;

    private Tencent mTencent;

    public TencentIUiListener(Context context, Tencent mTencent) {
        this.context = context;
        this.mTencent = mTencent;
    }


    @Override
    public void onComplete(Object response) {
        if (null == response) {
            ToastUtil.showShort(context, "授权失败");
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (jsonResponse.length() == 0) {
            ToastUtil.showShort(context, "授权失败");
            return;
        }

        String token = jsonResponse.optString(Constants.PARAM_ACCESS_TOKEN, null);
        String expires = jsonResponse.optString(Constants.PARAM_EXPIRES_IN, null);
        String openId = jsonResponse.optString(Constants.PARAM_OPEN_ID, null);
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openId);
            Logger.i("mTencent.setAccessToken(): " + token);
            Logger.i("mTencent.setOpenId(): " + openId);
        }
        ((BaseActivity)context).getUserInfo();
    }

    @Override
    public void onError(UiError uiError) {

    }

    @Override
    public void onCancel() {

    }

}
