package com.jinying.octopus.user;

import android.content.Context;

import com.jinying.octopus.App;
import com.jinying.octopus.R;
import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.http.ProgressSubscriber;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.ToastUtil;
import com.jinying.octopus.wxapi.WXEntryActivity;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/9/3.
 */

public class QQUserInfoIUiListener implements IUiListener {

    private Context context;
    private String openId;
    private String accessToken;
    private JSONObject params;

    public QQUserInfoIUiListener(Context context, String openId, String accessToken) {
        this.context = context;
        this.openId = openId;
        this.accessToken = accessToken;
    }

    @Override
    public void onComplete(Object response) {
        JSONObject jo = (JSONObject)response;
        Logger.i("QQ授权" + jo.toString());
        params = new JSONObject();
        try {
            params.put("openId", openId);
            params.put("loginSource", "QQ");
            params.put("nickName", jo.optString("nickname", ""));
            params.put("gender", jo.optString("gender", "").equals("男") ? "1" : "2");
            params.put("location", jo.optString("province", ""));
            params.put("summary", "");
            params.put("headImgUrl", jo.optString("figureurl_qq_2", ""));
            params.put("country", "");
            params.put("pushToken", accessToken);
            params.put("phoneModel", "");
            params.put("description", "");
            params.put("other", "");
            params.put("createDate", System.currentTimeMillis()+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        qqRegisterToApp();
    }

    @Override
    public void onError(UiError uiError) {

    }

    @Override
    public void onCancel() {

    }

    private void qqRegisterToApp(){
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),params.toString());
        Observable<Response<BaseResponse<UserBean>>> ob =  postService.thirdPartyRegisterToApp(Url.USER_THIRDLOGIN, body, RetrofitHeaderConfig.getHeaders());
        RetrofitClient.client().request(ob, new ProgressSubscriber<UserBean>(context) {
            @Override
            protected void onSuccess(UserBean userBean) {
                App.getInstance().setUserBean(userBean);
                ToastUtil.showShort(context,context.getString(R.string.authorization_success));
                EventBus.getDefault().post(new EventCutId());
            }

            @Override
            protected void onFailure(String message) {
                ToastUtil.showShort(context,context.getString(R.string.authorization_failure));
            }
        });
    }
}
