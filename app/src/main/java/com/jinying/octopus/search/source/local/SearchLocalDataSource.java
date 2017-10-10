package com.jinying.octopus.search.source.local;

import com.jinying.octopus.App;
import com.jinying.octopus.bean.SearchAllBean;
import com.jinying.octopus.bean.SearchBookBean;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.search.source.SearchDataSource;
import com.jinying.octopus.util.SharedPreferencesUtil;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchLocalDataSource implements SearchDataSource {

    @Override
    public String getSearchRecord() {
        String jsonRecord = (String) SharedPreferencesUtil.getParam(App.getInstance(), Constant.SEARCH_RECORD,"");
        return jsonRecord;
    }

    @Override
    public Observable<ArrayList<SearchAllBean>> getSearchList() {
        return null;
    }

    @Override
    public Observable<SearchBookBean> getSearchBookList(String searchContent, int pageNo) {
        return null;
    }

    @Override
    public Observable<ArrayList<String>> getSearchKeyword(String keyword) {
        return null;
    }

    @Override
    public void setSearchRecord(String jsonRecord) {
        SharedPreferencesUtil.setParam(App.getInstance(), Constant.SEARCH_RECORD,jsonRecord);
    }

    @Override
    public void clearSearchRecord() {
        setSearchRecord("");
    }
}
