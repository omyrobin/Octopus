package com.jinying.octopus.mine.opinion;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.jinying.octopus.BaseFragment;
import com.jinying.octopus.R;
import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.ProgressSubscriber;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.util.KeyBoardUtil;
import com.jinying.octopus.util.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Observable;

/**
 * 意见反馈
 * @author guojiel
 *
 */
public class OpinionFragment extends BaseFragment{
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_toolbar_title)
	TextView toolbar_title;
	@BindView(R.id.et_opinion)
	EditText mOpinionEt;
	@BindView(R.id.et_contact_way)
	EditText mContactWayEt;
	@BindView(R.id.tv_toolbar_right)
	TextView toolbar_right;
	@Override
	protected int provideContentViewId() {
		return R.layout.fragment_opinion;
	}

	@Override
	protected void initializeToolbar() {
		super.initializeToolbar();
		((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.btn_back);
		((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
		toolbar_title.setText(getActivity().getString(R.string.advise_set));

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getFragmentManager().popBackStack();
			}
		});
	}

	@Override
	protected void initializeView() {
		mOpinionEt.requestFocus();
	}


	@OnClick(R.id.tv_toolbar_right)
	public void onClick(View v) {
		KeyBoardUtil.closeKeybord(mOpinionEt, getActivity());
		String adviseStr = mOpinionEt.getText().toString();
		String contactWay = mContactWayEt.getText().toString();
		if(TextUtils.isEmpty(adviseStr)) {
			ToastUtil.showShort(getActivity(),R.string.advise_is_empty);
			return;
		}
		if(TextUtils.isEmpty(contactWay)) {
			ToastUtil.showShort(getActivity(),R.string.contactway_is_empty);
			return;
		}

		HashMap<String, Object> params = new HashMap();
		params.put("descn", adviseStr);
		params.put("contact", contactWay);

		PostService postService = RetrofitManager.getClient().create(PostService.class);
		Observable<Response<BaseResponse<String>>> ob = postService.opinionToUs(Url.ADD_FEEDBACK, params, RetrofitHeaderConfig.getHeaders());
		RetrofitClient.client().request(ob, new ProgressSubscriber<String>(getActivity()) {
			@Override
			protected void onSuccess(String s) {
				ToastUtil.showShort(getActivity(),R.string.advise_success);
				mOpinionEt.setText("");
				mContactWayEt.setText("");
			}

			@Override
			protected void onFailure(String message) {
				ToastUtil.showShort(getActivity(),"发送失败");
			}
		});
	}
}
