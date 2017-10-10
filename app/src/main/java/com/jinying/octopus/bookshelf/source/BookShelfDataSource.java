package com.jinying.octopus.bookshelf.source;

import com.jinying.octopus.bean.BannerBean;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bean.CatalogueBean;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by omyrobin on 2017/8/21.
 */

public interface BookShelfDataSource {

    Observable<ArrayList<BookShelfBean>> getBookList();

    Observable<ArrayList<BannerBean>> getBannerList();

    Observable<CatalogueBean> getBookCatalogue(String id);

    int getBookShelfStyle();

    void saveBookShelfConifg(int currBookShelfStyle);

    Observable<String> deleteSelectBook(String deleteParams);
}
