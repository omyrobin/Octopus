package com.jinying.octopus.bookmall.classifydetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinying.octopus.R;
import com.jinying.octopus.bean.TagBean;
import com.jinying.octopus.bean.TagBookBean;
import com.jinying.octopus.bean.TagListBean;
import com.jinying.octopus.bookmall.classifydetail.source.ClassifyDetailsRepository;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class ClassifyDetailsPresenter implements ClassifyDetailsContract.Presenter {

    private String condition;

    private String paramsType;
    private String paramsTag;
    private String paramsUpdateState;
    private String paramsSort;

    private String paramsTypeName = "";
    private String paramsTagName = "";
    private String paramsUpdateStateName = "";
    private String paramsSortName = "";
    @NonNull
    private ClassifyDetailsRepository mRepository;
    @NonNull
    private ClassifyDetailsContract.View mDetailsView;
    @NonNull
    private CompositeSubscription mSubscription;

    private Context context;
    private String channel;
    private String type;
    private ArrayList<List<TagBean>> datas;


    public ClassifyDetailsPresenter(@NonNull ClassifyDetailsContract.View mDetailsView, Context context,String channel, String type) {
        this.mDetailsView = mDetailsView;
        this.context = context;
        this.channel = channel;
        this.type = type;

        mRepository = new ClassifyDetailsRepository(context);
        mDetailsView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getTagList(String channel) {
        Subscription subscription = mRepository.getmDetailsRemoteDataSource().getTagList(channel).flatMap(new Func1<TagListBean, Observable<ArrayList<List<TagBean>>>>() {
            @Override
            public Observable<ArrayList<List<TagBean>>> call(TagListBean tagListBean) {
                datas = new ArrayList<>();
                datas.add(tagListBean.getCategorys());
                datas.add(tagListBean.getTags());
                datas.add(tagListBean.getOverList());
                datas.add(tagListBean.getSortList());
                return Observable.just(datas);
            }
        }).subscribe(new Subscriber<ArrayList<List<TagBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
            }

            @Override
            public void onNext(ArrayList<List<TagBean>> list) {
                mDetailsView.initTagLayout();
            }
        });
        mSubscription.add(subscription);
    }

    @Override
    public LinearLayout initTagLayout(final int index){
        int padding = DensityUtils.dp2px(context, 10);
        int paddintTB = DensityUtils.dp2px(context, 5);
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < datas.get(index).size(); i++) {
            TextView tagTv = new TextView(context);
            tagTv.setId(i);
            tagTv.setBackgroundResource(R.drawable.selector_classifydetails_tag);
            tagTv.setPadding(padding, paddintTB, padding, paddintTB);
            tagTv.setText(datas.get(index).get(i).getName());
            tagTv.setTextColor(ContextCompat.getColor(context,R.color.textColorPrimary));
            tagTv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDetailsView.resetPageNo();
                    mDetailsView.changeTextSelector(index, v.getId());
                    setParamsData(index,v);
                    postTagBookList(1);
                }
            });
            linearLayout.addView(tagTv,params);

            if(i == 0 ){
                setParamsData(index,tagTv);

                tagTv.setSelected(true);
                tagTv.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
            }

            if(type.equals(datas.get(index).get(i).getName())){
                setParamsData(index,tagTv);

                tagTv.setSelected(true);
                tagTv.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                linearLayout.getChildAt(0).setSelected(false);
                ((TextView) linearLayout.getChildAt(0)).setTextColor(ContextCompat.getColor(context,R.color.textColorPrimary));
            }
        }
        return linearLayout;
    }

    @Override
    public String setParamsData(int j,View v){
        switch (j) {
            case 0:
                paramsType = datas.get(j).get(v.getId()).getId();
                paramsTypeName = datas.get(j).get(v.getId()).getName();
                break;

            case 1:
                paramsTag = datas.get(j).get(v.getId()).getId();
                paramsTagName = datas.get(j).get(v.getId()).getName();
                break;

            case 2:
                paramsUpdateState = datas.get(j).get(v.getId()).getId();
                paramsUpdateStateName = datas.get(j).get(v.getId()).getName();
                break;

            case 3:
                paramsSort = datas.get(j).get(v.getId()).getId();
                paramsSortName = datas.get(j).get(v.getId()).getName();
                break;

            default:
                break;
        }
        condition = paramsTypeName+"、"+ paramsTagName+"、"+ paramsUpdateStateName+"、"+paramsSortName;
        ArrayList<String> conditionList = new ArrayList<>();
        List<String> list = Arrays.asList(condition.split("、"));
        for (int i = 0; i < list.size(); i++) {
            if(!list.get(i).contains("全部")){
                conditionList.add(list.get(i));
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < conditionList.size(); i++) {
            if(i==conditionList.size()-1){
                builder.append(conditionList.get(i));
            }else{
                builder.append(conditionList.get(i)+"、");
            }
        }
        condition = builder.toString();
        return condition;
    }

    @Override
    public void postTagBookList(int pageNo){
        Logger.i(paramsType +  "  :  " +  paramsTag + "  :  " +  paramsUpdateState);
        HashMap<String, Object> params = new HashMap<>();
        params.put("channel", channel);
        params.put("pageNo", pageNo );
        params.put("pageSize", 10);
        params.put("type", paramsType);
        params.put("tag", paramsTag);
        params.put("over", paramsUpdateState);
        params.put("sort", "pv");
        mDetailsView.setConditionTxt(condition);
        Subscription subscription = mRepository.getmDetailsRemoteDataSource().postTagBookList(params).subscribe(new Subscriber<TagBookBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(context, e.getMessage());
                mDetailsView.setRefreshComplete();
            }

            @Override
            public void onNext(TagBookBean tagBookBean) {
                mDetailsView.setTagBookList(tagBookBean.getList());
            }
        });

        mSubscription.add(subscription);
    }

}
