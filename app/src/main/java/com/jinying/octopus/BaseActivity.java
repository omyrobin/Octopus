package com.jinying.octopus;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.ProgressSubscriber;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.launch.LaunchContract;
import com.jinying.octopus.user.QQUserInfoIUiListener;
import com.jinying.octopus.user.TencentIUiListener;
import com.jinying.octopus.util.ToastUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/14.
 */

public abstract class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private AlertDialog premissionsDialog;

    Unbinder unbinder;

    protected static final String APP_ID = "wx290c4f0dc8596823";

    protected static final String SECRET = "70115acc782414c3399a3ee83c249871";

    /** IWXAPI 是第三方app和微信通信的openapi接口 */
    protected IWXAPI api;

    /** Application的实例*/
    protected void regToWx(){
        /** 通过WXAPIFactory工厂,获取IWXAPI的实例*/
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        /**将应用的appID*/
        api.registerApp(APP_ID);
    }

    protected static final String QQ_APPID = "1106290423";

    protected Tencent mTencent;

    protected IUiListener tencentIUiListener;

    protected  void regToQQ() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(QQ_APPID, this.getApplicationContext());
    }

    //网络监听
    protected ConnectionChangeReceiver myReceiver;

    protected void registerReceiver(LaunchContract.Presenter mPresneter){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver=new ConnectionChangeReceiver(mPresneter);
        this.registerReceiver(myReceiver, filter);
    }

    protected void unregisterReceiver(){
        this.unregisterReceiver(myReceiver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = provideContentViewId();
        if(layoutId !=0 ){
            setContentView(layoutId);
            unbinder = ButterKnife.bind(this);
        }
        regToWx();
        regToQQ();
        obtainIntent();
        initializeToolbar();
        initializeView();
    }

    protected abstract int provideContentViewId();//界面使用的布局ID;

    protected void obtainIntent(){}//获取传递过来的参数;

    protected void initializeToolbar(){}//初始化Toolbar内容

    protected abstract void initializeView();//初始化contentView内容

    public void getUserInfo() {
        UserInfo userInfo = new UserInfo(this, mTencent.getQQToken());
        tencentIUiListener = new QQUserInfoIUiListener(this,mTencent.getOpenId(),mTencent.getAccessToken());
        userInfo.getUserInfo(tencentIUiListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,tencentIUiListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null)
            unbinder.unbind();
    }



    /***申请----WRITE_EXTERNAL_STORAGE----权限***/
    protected boolean checkSelfPermissionWrite(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.MY_PERMISSIONS_WRITE);
            return false;
        }else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.MY_PERMISSIONS_WRITE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                showPremissionsDialog(getString(R.string.permission_denied_write));
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showPremissionsDialog(String message){
        premissionsDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.hint))
                .setMessage(message)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
                            // 进入设置系统应用权限界面
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
                            // 进入设置系统应用权限界面
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                        premissionsDialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.cancel),null)
                .create();
        premissionsDialog.show();
    }
}
