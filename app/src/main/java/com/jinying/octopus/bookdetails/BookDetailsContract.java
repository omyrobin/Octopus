package com.jinying.octopus.bookdetails;

import android.widget.TextView;

import com.jinying.octopus.BasePresenter;
import com.jinying.octopus.BaseView;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.StoryListBean;
import com.jinying.octopus.bean.StoryVoBean;

import java.util.ArrayList;

/**
 * Created by omyrobin on 2017/8/24.
 */

public interface BookDetailsContract {

    interface View extends BaseView<BookDetailsContract.Presenter> {
        void setBookDetails(StoryVoBean storyVoBean);

        void setCatalogue(CatalogueBean catalogueBean);

        void setRecommedStory(ArrayList<StoryListBean> recommendStoryBeen);

        void isAddBookshelf();
    }

    interface Presenter extends BasePresenter {

        void getBookDetails(String id);

        void getRecommendStory(String type, int pageNo, String id);

        void setCatalogueCache();

        void beginReadBook();

        void toBookDetailsActivity(long id);

        void getDescriptionTvLineCount(TextView mBookBriefTv);

        void isUnfold(TextView mBookBriefTv);
    }

}
