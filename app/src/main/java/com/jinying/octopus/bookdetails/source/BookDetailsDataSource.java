package com.jinying.octopus.bookdetails.source;

import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.StoryListBean;
import com.jinying.octopus.bean.StoryVoBean;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by omyrobin on 2017/8/24.
 */

public interface BookDetailsDataSource {

    Observable<StoryVoBean> getBookDetails(String id);

    Observable<CatalogueBean> getBookCatalogue(String id);

    Observable<ArrayList<StoryListBean>> getRecommendStory(String type, int pageNo, String id);

    Observable<String> addToBookShelf(String id);
}
