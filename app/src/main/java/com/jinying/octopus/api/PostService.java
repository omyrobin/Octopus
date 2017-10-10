package com.jinying.octopus.api;

import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.TagBookBean;
import com.jinying.octopus.bean.UserBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by omyrobin on 2017/4/16.
 */

public interface PostService {

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<UserBean>>> registerUser(@Url String registerUrl, @Field("os") String os, @HeaderMap Map<String, String> headers);

    @POST
    Observable<Response<BaseResponse<UserBean>>> authLoginUser(@Url String loginUrl, @HeaderMap Map<String, String> headers);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<UserBean>>> loginUser(@Url String loginUrl, @FieldMap HashMap<String, Object> params, @HeaderMap Map<String, String> headers);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<TagBookBean>>> postTagBookList(@Url String bookListUrl, @FieldMap HashMap<String, Object> params, @HeaderMap Map<String, String> headerX);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<String>>> postAuthCode(@Url String authCodeUrl, @Field("mobilePhone") String channel, @HeaderMap Map<String, String> headers);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<UserBean>>> haveUserRegisterUser(@Url String registerUrl, @FieldMap HashMap<String, Object> params, @HeaderMap Map<String, String> headers);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<String>>> bindMobile(@Url String registerUrl, @FieldMap HashMap<String, Object> params, @HeaderMap Map<String, String> headers);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<String>>> addToBookShelf(@Url String addBookUrl, @Field("storyId") String storyId, @HeaderMap Map<String, String> headers);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<String>>> removeToBookShelf(@Url String removeBookUrl, @Field("storyIds") String storyIds, @HeaderMap Map<String, String> headers);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<String>>> updatChapterId(@Url String updatChapterUrl, @Field("chapterId") String chapterId, @HeaderMap Map<String, String> headers);


    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST
    Observable<Response<BaseResponse<UserBean>>> thirdPartyRegisterToApp(@Url String updatChapterUrl, @Body RequestBody route, @HeaderMap Map<String, String> headers);

    @POST
    @FormUrlEncoded
    Observable<Response<BaseResponse<String>>> opinionToUs(@Url String updatChapterUrl, @FieldMap HashMap<String, Object> params, @HeaderMap Map<String, String> headers);
}
