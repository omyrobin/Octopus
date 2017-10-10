package com.jinying.octopus.bookdetails.source.remote;

import com.jinying.octopus.api.GetService;
import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.StoryListBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bookdetails.source.BookDetailsDataSource;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class BookDetailsRemoteDataSource implements BookDetailsDataSource {

    @Override
    public Observable<StoryVoBean> getBookDetails(String id) {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<StoryVoBean>>> observable = getService.getBookDetails(Url.GET_STORYVO,id, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<CatalogueBean> getBookCatalogue(String id) {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<CatalogueBean>>> observable = getService.getBookChapter(Url.GET_CHAPTERS,id, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<ArrayList<StoryListBean>> getRecommendStory(String type, int pageNo, String id) {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("pageNo", pageNo);
        map.put("storyId", id);
        Observable<Response<BaseResponse<ArrayList<StoryListBean>>>> observable = getService.getRecommendStory(Url.GET_STORYRELATION, map, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<String> addToBookShelf(String id) {
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<String>>> observable = postService.addToBookShelf(Url.ADD_MYSHELF, id, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

}
