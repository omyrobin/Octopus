package com.jinying.octopus.bookshelf.source.local;

import android.content.Context;

import com.jinying.octopus.bean.BannerBean;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bookshelf.BookShelfFragment;
import com.jinying.octopus.bookshelf.source.BookShelfDataSource;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.util.SharedPreferencesUtil;

import java.util.ArrayList;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/21.
 */

public class BookShelfLocalDataSource implements BookShelfDataSource {

    private Context context;

    public BookShelfLocalDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Observable<ArrayList<BookShelfBean>> getBookList() {
        return null;
    }

    @Override
    public Observable<ArrayList<BannerBean>> getBannerList() {
        return null;
    }

    @Override
    public Observable<CatalogueBean> getBookCatalogue(String id) {
        return null;
    }

    @Override
    public int getBookShelfStyle() {
        return (int) SharedPreferencesUtil.getParam(context, Constant.BOOKSHELF_STYLE, BookShelfFragment.CARDS_STYLE);
    }

    @Override
    public void saveBookShelfConifg(int currBookShelfStyle) {
        SharedPreferencesUtil.setParam(context, Constant.BOOKSHELF_STYLE, currBookShelfStyle);
    }

    @Override
    public Observable<String> deleteSelectBook(String deleteParams) {
        return null;
    }
}
