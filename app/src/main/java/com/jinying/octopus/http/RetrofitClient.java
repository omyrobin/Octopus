package com.jinying.octopus.http;

import com.jinying.octopus.App;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.util.NetworkUtil;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by omyrobin on 2017/4/13.
 */

public class RetrofitClient {

    private static volatile RetrofitClient client;

    public static RetrofitClient client(){
        if(client == null){
            synchronized (RetrofitClient.class){
                if(client == null){
                    client = new RetrofitClient();
                }
            }
        }
        return client;
    }

    public <T> Observable<T> compose(Observable<Response<BaseResponse<T>>> ob){
        if(!NetworkUtil.isConnected(App.getInstance())){
            return Observable.empty();
        }
        //数据预处理
        Observable.Transformer<Response<BaseResponse<T>>, T> result = ResponseHelper.transformerResult();
        return ob.compose(result);
    }

    public <T> Subscription request(Observable<Response<BaseResponse<T>>> ob, final ProgressSubscriber<T> subscriber){
        if(!NetworkUtil.isConnected(App.getInstance())){
            return null;
        }
        //数据预处理
        Observable.Transformer<Response<BaseResponse<T>>, T> result = ResponseHelper.transformerResult();
        Subscription subscription = ob.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        subscriber.showProgressDialog();
                    }
                })
                .subscribe(subscriber);

        return subscription;
    }

    public <T> Observable<T> thirdParty(Observable<T> ob){
        if(!NetworkUtil.isConnected(App.getInstance())){
            return null;
        }
        //数据预处理

        return ob.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
