package com.jinying.octopus.http;

import android.text.TextUtils;
import android.widget.TextView;

import com.jinying.octopus.bean.BaseResponse;

import org.json.JSONObject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ResponseHelper {
    /**
     * 对结果进行Transformer处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<Response<BaseResponse<T>>, T> transformerResult() {
        return new Observable.Transformer<Response<BaseResponse<T>>, T>() {
            @Override
            public Observable<T> call(Observable<Response<BaseResponse<T>>> tObservable) {
                return tObservable.flatMap(new Func1<Response<BaseResponse<T>>, Observable<T>>() {
                    @Override
                    public Observable<T> call(Response<BaseResponse<T>> result) {
                        if ("0".equals(result.body().getErrorCode())) {
                            String X_I =  result.headers().get("X-I");
                            String X_S =  result.headers().get("X-S");
                            if(!TextUtils.isEmpty(X_I) && !TextUtils.isEmpty(X_S)){
                                RetrofitHeaderConfig.setHeaderX(X_I, X_S);
                            }
                            return createData(result.body().getData());
                        }else {
                            return Observable.error(new RuntimeException(result.body().getMessage()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }


    /**
     * 对结果进行Transformer处理
     *
     * @param <T>
     * @return
     */
    public static Observable.Transformer<Response<JSONObject>, JSONObject> transformerThirdParty() {
        return new Observable.Transformer<Response<JSONObject>, JSONObject>(){
            @Override
            public Observable<JSONObject> call(Observable<Response<JSONObject>> responseObservable) {
                return responseObservable.flatMap(new Func1<Response<JSONObject>, Observable<JSONObject>>() {
                    @Override
                    public Observable<JSONObject> call(Response<JSONObject> jsonObjectResponse) {
                        return Observable.just(jsonObjectResponse.body());
                    }
                }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}