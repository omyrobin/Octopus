package com.jinying.octopus.api;

import com.jinying.octopus.bean.BannerBean;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.BookListBean;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.RankDetailBean;
import com.jinying.octopus.bean.StoryListBean;
import com.jinying.octopus.bean.SearchAllBean;
import com.jinying.octopus.bean.SearchBookBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bean.TagBookBean;
import com.jinying.octopus.bean.TagListBean;
import com.jinying.octopus.bean.WxBean;
import com.jinying.octopus.bean.WxUserBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface GetService {

    @GET
    Observable<Response<BaseResponse<ArrayList<BookShelfBean>>>> getBookList(@Url String bookListUrl, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<ArrayList<BannerBean>>>> getBannerList(@Url String bookListUrl, @Query("columnId") int columnId, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<ArrayList<SearchAllBean>>>> getSearchAll(@Url String searchAllUrl, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<SearchBookBean>>> getSearchBook(@Url String searchAllUrl, @QueryMap Map<String, Object> map, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<ArrayList<String>>>> getSearchKeyword(@Url String searchKeywordUrl, @Query("keyword") String keyword, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<StoryVoBean>>> getBookDetails(@Url String bookDetailsUrl, @Query("storyId") String storyId, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<CatalogueBean>>> getBookChapter(@Url String bookChapterUrl, @Query("storyId") String storyId, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<ArrayList<StoryListBean>>>> getRecommendStory(@Url String bookListUrl, @QueryMap Map<String, Object> map, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<BookListBean>>> getEndBookList(@Url String bookListUrl, @QueryMap Map<String, Object> map, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<RankDetailBean>>> getRankDetailList(@Url String bookListUrl, @QueryMap Map<String, Object> map, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<TagListBean>>> getTagList(@Url String bookListUrl, @Query("channel") String channel, @HeaderMap Map<String, String> headers);

    @GET
    Observable<Response<BaseResponse<String>>> getChapterContent(@Url String contentUrl, @Query("chapterId") String chapterId, @HeaderMap Map<String, String> headers);

    @GET
    Observable<WxBean> getWxInfo(@Url String wxUrl);

    @GET
    Observable<WxUserBean> getWxUserInfo(@Url String wxUserUrl);
}