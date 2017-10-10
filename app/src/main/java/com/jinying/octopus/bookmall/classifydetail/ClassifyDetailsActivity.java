package com.jinying.octopus.bookmall.classifydetail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Preconditions;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.TagBean;
import com.jinying.octopus.bean.TagBookBean;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.view.NestedScrollLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class ClassifyDetailsActivity extends BaseActivity implements ClassifyDetailsContract.View, NestedScrollLinearLayout.OnRefreshLoadMore{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.nested_view)
    NestedScrollLinearLayout nestedView;
    @NonNull
    private ClassifyDetailsContract.Presenter mPresenter;
    private ViewGroup headerView;
    private RecyclerView recyclerView;
    private String channel;
    private String type;
    private ClassifyDetailsAdapter adapter;
    private int pageNo = 1;

    public static void newInstance(Context context, String channel, String type){
        Intent intent = new Intent(context, ClassifyDetailsActivity.class);
        intent.putExtra(Constant.CHANNEL, channel);
        intent.putExtra(Constant.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_classifydetail;
    }

    @Override
    protected void obtainIntent() {
        super.obtainIntent();
        channel = getIntent().getStringExtra(Constant.CHANNEL);
        type = getIntent().getStringExtra(Constant.TYPE);
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("章鱼书库");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initializeView() {
        recyclerView = nestedView.getRecyclerView();
        headerView = nestedView.getHeaderView();
        nestedView.setRefreshListener(this);

        mPresenter = new ClassifyDetailsPresenter(this,this,channel,type);
        getTagList();
    }

    private void getTagList(){
        mPresenter.getTagList(channel);
    }

    @Override
    public void initTagLayout(){
        for (int i = 0; i < headerView.getChildCount()-1; i++) {
            HorizontalScrollView hView = (HorizontalScrollView) headerView.getChildAt(i);
            hView.addView(mPresenter.initTagLayout(i));
        }
        mPresenter.postTagBookList(1);
    }

    @Override
    public void setConditionTxt(String condition){
        nestedView.setConditionTxt(condition);
    }

    @Override
    public void changeTextSelector(int index, int id) {
        HorizontalScrollView hView = (HorizontalScrollView) headerView.getChildAt(index);
        LinearLayout layout = (LinearLayout) hView.getChildAt(0);
        for (int j = 0; j < layout.getChildCount(); j++) {
            TextView tv = (TextView) layout.getChildAt(j);
            if(j == id){
                tv.setSelected(true);
                tv.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
                int scrollViewWidth = hView.getWidth();
                int tv_px = (int)tv.getX() + tv.getWidth() / 2;
                hView.scrollTo( tv_px - scrollViewWidth / 2 , 0);
//                hView.smoothScrollTo(tv.getWidth() * (index - 1), 0);
            }else{
                tv.setSelected(false);
                tv.setTextColor(ContextCompat.getColor(this,R.color.textColorPrimary));
            }
        }
    }

    @Override
    public void setPresenter(ClassifyDetailsContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void setTagBookList(List<TagBookBean.ListBean> tagBookBeanList) {
        if(adapter == null){
            adapter = new ClassifyDetailsAdapter(this, tagBookBeanList);
            recyclerView.setAdapter(adapter);
        }else if(pageNo !=1){
            adapter.addNotifyList(tagBookBeanList);
        }else{
            adapter.setNotifyList(tagBookBeanList);
        }
        setRefreshComplete();
    }

    @Override
    public void resetPageNo() {
        pageNo = 1;
    }

    @Override
    public void setRefreshComplete() {
        nestedView.setRefreshComplete();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        mPresenter.postTagBookList(pageNo);
    }
}
