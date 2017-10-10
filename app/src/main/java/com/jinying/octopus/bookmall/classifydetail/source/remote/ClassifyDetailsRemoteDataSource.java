package com.jinying.octopus.bookmall.classifydetail.source.remote;

import com.jinying.octopus.api.GetService;
import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.TagBookBean;
import com.jinying.octopus.bean.TagListBean;
import com.jinying.octopus.bookmall.classifydetail.source.ClassifyDetailsDataSource;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class ClassifyDetailsRemoteDataSource implements ClassifyDetailsDataSource {

    @Override
    public Observable<TagListBean> getTagList(String channel) {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<TagListBean>>> observable = getService.getTagList(Url.GET_TYPEN,channel, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<TagBookBean> postTagBookList(HashMap<String, Object> params) {
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<TagBookBean>>> observable = postService.postTagBookList(Url.TYPEN_SEARCH,params, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }
}
