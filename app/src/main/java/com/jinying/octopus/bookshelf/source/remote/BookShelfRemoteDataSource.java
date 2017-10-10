package com.jinying.octopus.bookshelf.source.remote;

import android.content.Context;

import com.jinying.octopus.api.GetService;
import com.jinying.octopus.api.PostService;
import com.jinying.octopus.bean.BannerBean;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bookshelf.source.BookShelfDataSource;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.ProgressSubscriber;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;

import java.util.ArrayList;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/21.
 */

public class BookShelfRemoteDataSource implements BookShelfDataSource {

    private Context context;

    public BookShelfRemoteDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Observable<ArrayList<BookShelfBean>> getBookList() {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<ArrayList<BookShelfBean>>>> observable = getService.getBookList(Url.GET_BOOKLIST, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<ArrayList<BannerBean>> getBannerList() {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<ArrayList<BannerBean>>>> observable = getService.getBannerList(Url.GET_BANNER, 7401, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<CatalogueBean> getBookCatalogue(String id) {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<CatalogueBean>>> observable = getService.getBookChapter(Url.GET_CHAPTERS,id, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public int getBookShelfStyle() {
        return 0;
    }

    @Override
    public void saveBookShelfConifg(int currBookShelfStyle) {}

    @Override
    public Observable<String> deleteSelectBook(String deleteParams) {
        PostService postService = RetrofitManager.getClient().create(PostService.class);
        Observable<Response<BaseResponse<String>>> observable = postService.removeToBookShelf(Url.REMOVE_MYSHELF,deleteParams, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

}
