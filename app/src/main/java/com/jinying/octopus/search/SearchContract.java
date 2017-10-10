package com.jinying.octopus.search;

import com.jinying.octopus.BasePresenter;
import com.jinying.octopus.BaseView;
import com.jinying.octopus.bean.SearchAllBean;
import com.jinying.octopus.bean.SearchBookBean;

import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/23.
 */

public interface SearchContract {

    interface View extends BaseView<SearchContract.Presenter> {

        void addSearchAllToTagLayout(ArrayList<SearchAllBean> searchAllBeen);

        void showSearchContentNull();

        void setSearchResultList(SearchBookBean searchBookBean);

        void addSearchResultList(SearchBookBean searchBookBean);

        void setSearchResultListError();

        void setSearchKeyWords(ArrayList<String> list);

        void initSearchRecordView(ArrayList<String> strings);
    }

    interface Presenter extends BasePresenter {

        void searchBookList(String searchContent, int pageNo);

        void searchKeyWord(String string);

        void setSearchRecord(String searchContent);

        void getSearchRecord();

        void clearSearchRecord();
    }
}
