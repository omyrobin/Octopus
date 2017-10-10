package com.jinying.octopus.bookmall.classifydetail.source;

import com.jinying.octopus.bean.TagBookBean;
import com.jinying.octopus.bean.TagListBean;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;

/**
 * Created by omyrobin on 2017/8/25.
 */

public interface ClassifyDetailsDataSource {

    Observable<TagListBean> getTagList(String channel);

    Observable<TagBookBean> postTagBookList(HashMap<String, Object> params);
}
