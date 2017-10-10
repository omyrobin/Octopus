package com.jinying.octopus.bookmall.webactivity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
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
import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.bookmall.classifydetail.ClassifyDetailsActivity;
import com.jinying.octopus.bookmall.rankdetail.RankDetailsActivity;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.RankingUtil;
import com.jinying.octopus.util.TagTypeUtils;
import com.jinying.octopus.util.ToastUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class WebActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.wb_bookmall)
    WebView mBookMallWebView;
    @BindView(R.id.fl_progress_parent)
    FrameLayout mProgressParentFl;
    @BindView(R.id.progress_view)
    View mProgressV;
    private String url;
    private int channel;
    private String title;

    public static final String URL = "URL";
    public static final String CHANNEL = "CHANNEL";
    public static final String TITLE = "TITLE";

    public static void newInstance(Context context, String url,String title){
        newInstance(context, url, -1, title);
    }

    public static void newInstance(Context context, String url, int channel,String title){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(CHANNEL, channel);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void obtainIntent() {
        super.obtainIntent();
        url = getIntent().getStringExtra(URL);
        channel = getIntent().getIntExtra(CHANNEL,1);
        title = getIntent().getStringExtra(TITLE);
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initializeView() {
        mBookMallWebView.loadUrl(url);
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
                    if(mProgressV != null){
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
                    }
                } else {
                    if(mProgressV!=null)
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
        webSettings.setDomStorageEnabled(true);// 使用localStorage则必须打开
        webSettings.setGeolocationEnabled(true);
    }

    private void setWebViewClient(){
        mBookMallWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.i("url : " + url);
                if(parseSchemeRankDetails(url)){
                    String type_channel = url.replaceAll("[^\\d]", "");
                    char[] array = type_channel.toCharArray();
                    String type = array[0] + "";
                    String channel = array[1] + "";
                    RankDetailsActivity.newInstance(WebActivity.this, channel, type, RankingUtil.getRankingName(type));
                }else if(parseSchemeTypeDetail(url)){
                    String type_channel = url.replaceAll("[^\\d]", "");
                    char[] array = type_channel.toCharArray();
                    String type = array[0] + "";
                    String channel = array[1] + "";
                    ClassifyDetailsActivity.newInstance(WebActivity.this, channel, TagTypeUtils.getTagType(channel, type));
                }else if(parseSchemeDetail(url)){
                    String id = url.replaceAll("[^\\d]", "");
                    BookDetailsActivity.newInstance(WebActivity.this,id);
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

    public boolean parseSchemeRankDetails(String url) {
        return url.contains("appRerankDetails");
    }

    public boolean parseSchemeTypeDetail(String url) {
        return url.contains("appRetypeDetails");
    }

    public boolean parseSchemeDetail(String url) {
        return url.contains("appRedetail");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mProgressV.animate().cancel();
    }
}
