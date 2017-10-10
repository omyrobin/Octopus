package com.jinying.octopus.bookmall.classifydetail;

import android.view.View;

import com.jinying.octopus.BasePresenter;
import com.jinying.octopus.BaseView;
import com.jinying.octopus.bean.TagBean;
import com.jinying.octopus.bean.TagBookBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omyrobin on 2017/8/25.
 */

public interface ClassifyDetailsContract  {

    interface View extends BaseView<Presenter>{

        void initTagLayout();

        void changeTextSelector(int j, int id);

        void setConditionTxt(String s);

        void setTagBookList(List<TagBookBean.ListBean> tagBookBeanList);

        void resetPageNo();

        void setRefreshComplete();
    }

    interface Presenter extends BasePresenter{
        String setParamsData(int j, android.view.View v);

        void getTagList(String channel);

        android.view.View initTagLayout(int i);

        void postTagBookList(int pageNo);
    }
}
