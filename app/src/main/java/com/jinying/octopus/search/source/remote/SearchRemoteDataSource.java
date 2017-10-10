package com.jinying.octopus.search.source.remote;

import com.jinying.octopus.api.GetService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.SearchAllBean;
import com.jinying.octopus.bean.SearchBookBean;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.search.source.SearchDataSource;
import com.jinying.octopus.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchRemoteDataSource implements SearchDataSource{

    @Override
    public String getSearchRecord() {
        return null;
    }

    @Override
    public Observable<ArrayList<SearchAllBean>> getSearchList() {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<ArrayList<SearchAllBean>>>> observable = getService.getSearchAll(Url.GET_SEARCH_KEYWORDS, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    public Observable<SearchBookBean> getSearchBookList(String searchContent, int pageNo) {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        HashMap<String, Object> params  = new HashMap<>();
        params.put("keyword",  StringUtils.encodeParams(searchContent));
        params.put("pageNo",  pageNo);
        params.put("pageSize",  10);
        Observable<Response<BaseResponse<SearchBookBean>>> observable = getService.getSearchBook(Url.GET_SEARCH_BYWORD, params, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public Observable<ArrayList<String>> getSearchKeyword(String keyword) {
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<ArrayList<String>>>> observable = getService.getSearchKeyword(Url.GET_SEARCH_NAMES, keyword, RetrofitHeaderConfig.getHeaders());
        return RetrofitClient.client().compose(observable);
    }

    @Override
    public void setSearchRecord(String jsonRecord) {

    }

    @Override
    public void clearSearchRecord() {

    }

}
