package com.jinying.octopus.search.source;

import com.jinying.octopus.bean.SearchAllBean;
import com.jinying.octopus.bean.SearchBookBean;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by omyrobin on 2017/8/23.
 */

public interface SearchDataSource {

    String getSearchRecord();

    Observable<ArrayList<SearchAllBean>> getSearchList();

    Observable<SearchBookBean> getSearchBookList(String searchContent, int pageNo);

    Observable<ArrayList<String>> getSearchKeyword(String keyword);

    void setSearchRecord(String jsonRecord);

    void clearSearchRecord();
}
