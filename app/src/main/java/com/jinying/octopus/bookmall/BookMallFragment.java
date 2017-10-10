package com.jinying.octopus.bookmall;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jinying.octopus.App;
import com.jinying.octopus.BaseFragment;
import com.jinying.octopus.R;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.bookmall.classifydetail.ClassifyDetailsActivity;
import com.jinying.octopus.bookmall.neworend.NewEndListActivity;
import com.jinying.octopus.bookmall.webactivity.WebActivity;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.event.EventNetConnection;
import com.jinying.octopus.search.SearchActivity;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.TagTypeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by omyrobin on 2017/8/18.
 */

public class BookMallFragment extends BaseFragment{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bookmall_male)
    TextView mBookMallMaleTv;
    @BindView(R.id.tv_bookmall_female)
    TextView mBookMallFeMaleTv;
    @BindView(R.id.tv_bookmall_indicator)
    TextView mBookMallIndicatorTv;
    @BindView(R.id.wb_bookmall)
    WebView mBookMallWebView;
    @BindView(R.id.fl_progress_parent)
    FrameLayout mProgressParentFl;
    @BindView(R.id.progress_view)
    View mProgressV;
    private int currIndicatorIndex;
    private int channel = 1;
//    private String errorHtml;

    @Override
    protected int provideContentViewId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_bookmall;
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    protected void initializeView() {
        mBookMallWebView.loadUrl(Url.BOOKMALL_URL);
        initProgress();
        settingWebView();
        setWebViewClient();
    }

    private void initProgress(){
        mProgressV.setTranslationX(-App.width);
        mBookMallWebView.setWebChromeClient(new WebChromeClient() {
            private boolean isFinish = false;

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (isFinish)
                    return;
                if (newProgress == 100) {
                    isFinish = true;
                    mProgressV.animate().setDuration(600)
                            .setListener(new Animator.AnimatorListener() {
                                public void onAnimationStart(Animator animation) {
                                }

                                public void onAnimationRepeat(Animator animation) {
                                }

                                public void onAnimationEnd(Animator animation) {
                                    if(mProgressV != null)
                                        mProgressV.animate().setDuration(200).alpha(0).start();
                                }

                                public void onAnimationCancel(Animator animation) {
                                }
                            }).translationX(0).start();
                } else {
                    mProgressV.setTranslationX(-App.width * (1 - newProgress / 100f));
                }
            }
        });
    }

    private void settingWebView(){
        WebSettings webSettings = mBookMallWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 设置WebView属性，能够执行Javascript脚本
        webSettings.setAllowFileAccess(true);// 设置可以访问文件
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mBookMallWebView.setInitialScale(25);
        webSettings.setDomStorageEnabled(true);// 使用localStorage则必须打开
        webSettings.setGeolocationEnabled(true);
    }

    private void setWebViewClient(){
        mBookMallWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.i("WebActivity : " + url);
                if(parseSchemeTypeDetail(url)){
                    String type = url.replaceAll("[^\\d]", "");
                    ClassifyDetailsActivity.newInstance(getActivity(), channel + "", TagTypeUtils.getTagType(channel+"",type));
                }else if(parseSchemeType(url)){
                    WebActivity.newInstance(getActivity(), Url.CLASSIFY_URL,channel,"分类");
                }else if(parseSchemeNewBook(url)){
                    NewEndListActivity.newInstance(getActivity(), channel + "", NewEndListActivity.NEW,"新书推荐");
                }else if(parseSchemeEndBook(url)){
                    NewEndListActivity.newInstance(getActivity(), channel + "", NewEndListActivity.END,"完本小说");
                }else if(parseSchemeRanking(url)){
                    WebActivity.newInstance(getActivity(),Url.RANKING_URL,channel,"排行榜");
                }else if(parseSchemeDetail(url)){
                    String id = url.replaceAll("[^\\d]", "");
                    BookDetailsActivity.newInstance(getActivity(),id);
                }else if(!Url.BOOKMALL_URL.equals(url)){
                    WebActivity.newInstance(getActivity(),url,"活动");
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadUrl("file:///android_asset/empty.html");
            }
        });
    }

    public boolean parseSchemeTypeDetail(String url) {
        return url.contains("appRetypeDetails");
    }

    public boolean parseSchemeType(String url) {
        return url.contains("appRetype");
    }

    public boolean parseSchemeNewBook(String url) {
        return url.contains("appReNewBook");
    }

    public boolean parseSchemeEndBook(String url) {
        return url.contains("appReEndBook");
    }

    public boolean parseSchemeRanking(String url) {
        return url.contains("appRerank");
    }

    public boolean parseSchemeDetail(String url) {
        return url.contains("appRedetail");
    }


    @OnClick({R.id.tv_bookmall_male, R.id.tv_bookmall_female, R.id.img_toolbar_search})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_bookmall_male:
                if(currIndicatorIndex!=0){
                    currIndicatorIndex = 0;
                    channel = currIndicatorIndex + 1;
                    ObjectAnimator.ofFloat(mBookMallIndicatorTv, "translationX",DensityUtils.dp2px(getActivity(),50), 0).start();
                    mBookMallMaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
                    mBookMallFeMaleTv.setTextColor(ContextCompat.getColor(getActivity(),android.R.color.white));
                    mBookMallWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            mBookMallWebView.loadUrl("javascript:isChannel('7404')");
                    }});
                }
                break;

            case R.id.tv_bookmall_female:
                if(currIndicatorIndex != 1){
                    currIndicatorIndex = 1;
                    channel = currIndicatorIndex + 1;
                    ObjectAnimator.ofFloat(mBookMallIndicatorTv, "translationX", 0, DensityUtils.dp2px(getActivity(),50)).start();
                    mBookMallFeMaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
                    mBookMallMaleTv.setTextColor(ContextCompat.getColor(getActivity(),android.R.color.white));
                    mBookMallWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            mBookMallWebView.loadUrl("javascript:isChannel('7504')");
                        }});
                }
                break;

            default:
                SearchActivity.newInstance(getActivity());
                break;
        }
    }

    /**
     *异常use状态 网络连接后
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventNetConnection event) {
        mBookMallWebView.loadUrl(Url.BOOKMALL_URL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
