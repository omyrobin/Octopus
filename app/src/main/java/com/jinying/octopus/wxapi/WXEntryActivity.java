package com.jinying.octopus.wxapi;

import com.jinying.octopus.App;
import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.api.GetService;
import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.bean.WxBean;
import com.jinying.octopus.bean.WxUserBean;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.event.EventCutId;
import com.jinying.octopus.http.ProgressSubscriber;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.util.AppUtils;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;


public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	
	private String access_token;
	
	private String openid;

	private JSONObject params;

	@Override
	protected int provideContentViewId() {
		return 0;
	}

	@Override
	protected void initializeView() {
		api.handleIntent(getIntent(), this);
	}


	@Override
	public void onReq(BaseReq req) {
		
	}

	@Override
	public void onResp(BaseResp baseResp) {
		switch (baseResp.getType()) {
			case ConstantsAPI.COMMAND_SENDAUTH:
				wxLogin(baseResp);
				break;
		}
	}

	private void wxLogin(BaseResp resp){
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			String code = ((SendAuth.Resp) resp).code;
			toRegiesterMyApp(code);
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			ToastUtil.showShort(this, getString(R.string.err_auth_denied));
			finish();
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			ToastUtil.showShort(this, getString(R.string.err_user_cancel));
			finish();
			break;
		}
	}
	
	private void toRegiesterMyApp(String code){
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ APP_ID
				+ "&secret="
				+ SECRET
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		GetService getService = RetrofitManager.getClient().create(GetService.class);
		RetrofitClient.client().thirdParty(getService.getWxInfo(url)).subscribe(new Subscriber<WxBean>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
			}

			@Override
			public void onNext(WxBean wxBean) {
				access_token = wxBean.getAccess_token();
				openid = wxBean.getOpenid();
				getWxInfo(access_token, openid);
			}
		});
	}
	
	private void getWxInfo(final String access_token, final String openid) {
		Logger.i(access_token  + "  ******  "    +   openid);
		String url = Url.WEIXIN_USERINFO+"access_token="+access_token+"&openid="+openid;
		GetService getService = RetrofitManager.getClient().create(GetService.class);
		RetrofitClient.client().thirdParty(getService.getWxUserInfo(url)).subscribe(new Subscriber<WxUserBean>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(WxUserBean wxUserBean) {
				String nickname = wxUserBean.getNickname();
				String headimgurl = wxUserBean.getHeadimgurl();
				int sex = wxUserBean.getSex();
				String city = wxUserBean.getCity();
				String province = wxUserBean.getProvince();
				String country = wxUserBean.getCountry();
				String unionid = wxUserBean.getUnionid();
				try {
					params = new JSONObject();
					params.put("openId", openid);
					params.put("pushToken", access_token);
					params.put("loginSource", "WX");
					params.put("nickName", nickname);
					params.put("gender", sex);
					params.put("location", province);
					params.put("summary", city);
					params.put("headImgUrl", headimgurl);
					params.put("country", country);
					params.put("description", "");
					params.put("other", unionid);
					params.put("type", "");
					params.put("createDate", System.currentTimeMillis() + "");
					params.put("phoneModel", "");
					params.put("pushToken", AppUtils.getDeviceId());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				wxRegisterToApp();
			}
		});
	}

	private void wxRegisterToApp(){
		PostService postService = RetrofitManager.getClient().create(PostService.class);
		RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),params.toString());
		Observable<Response<BaseResponse<UserBean>>> ob =  postService.thirdPartyRegisterToApp(Url.USER_THIRDLOGIN, body, RetrofitHeaderConfig.getHeaders());
		RetrofitClient.client().request(ob, new ProgressSubscriber<UserBean>(this) {
			@Override
			protected void onSuccess(UserBean userBean) {
				App.getInstance().setUserBean(userBean);
				ToastUtil.showShort(WXEntryActivity.this,getString(R.string.authorization_success));
				EventBus.getDefault().post(new EventCutId());
				finish();
			}

			@Override
			protected void onFailure(String message) {
				ToastUtil.showShort(WXEntryActivity.this,getString(R.string.authorization_failure));
				finish();
			}
		});
	}
}
