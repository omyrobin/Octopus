package com.jinying.octopus.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.SearchAllBean;
import com.jinying.octopus.bean.SearchBookBean;
import com.jinying.octopus.search.keyword.SearchKeyWordFragment;
import com.jinying.octopus.search.result.SearchResultFragment;
import com.jinying.octopus.util.FragmentUtil;
import com.jinying.octopus.util.KeyBoardUtil;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.TagLayoutUtils;
import com.jinying.octopus.util.ToastUtil;
import com.jinying.octopus.view.TagLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View, ITagListener, SearchResultFragment.ILoadMoreListener, ISearchKeyWordListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search_book)
    EditText mSearchBookEt;
    @BindView(R.id.tl_search_all)
    TagLayout mSearchAllTl;
    @BindView(R.id.tv_search_record_clear)
    TextView mRecordClearTv;
    @BindView(R.id.tl_search_record)
    TagLayout mSearchRecordTl;
    @NonNull
    private SearchContract.Presenter mPresenter;
    private SearchResultFragment resultFragment;
    private SearchKeyWordFragment keyWordFragment;
    private boolean isFirstAdd = true;
    private TagLayoutUtils mUtils;
    private int pageNo = 1;
    private String searchKeyword;
    private boolean isTagClick;//是否点击标签

    public static void newInstance(Context context){
        Intent intent = new Intent(context , SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initializeView() {
        mUtils = new TagLayoutUtils(this,this);
        mPresenter = new SearchPresenter(this,this);

        initEditText();
        initSearchRecord();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    private void initEditText(){
        mSearchBookEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchContent = textView.getText().toString().trim();

                    mPresenter.searchBookList(searchContent, pageNo);
                    mPresenter.setSearchRecord(searchContent);
                    mPresenter.getSearchRecord();
                }
                return false;
            }
        });

        mSearchBookEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isTagClick){
                    if(charSequence.toString().trim().length() > 0){
                        searchKeyword = charSequence.toString().trim();
                        mPresenter.searchKeyWord(charSequence.toString());
                    }else{
                        if(keyWordFragment != null && keyWordFragment.isAdded()){
                            getSupportFragmentManager().popBackStack();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initSearchRecord() {
        mPresenter.getSearchRecord();
    }

    @Override
    public void initSearchRecordView(ArrayList<String> strings) {
        mSearchRecordTl.removeAllViews();
        for (int i = 0; i<strings.size(); i++) {
            if(!TextUtils.isEmpty(strings.get(i))){
                mUtils.addToTagLayout(strings.get(i), R.color.textColorSecondary, R.drawable.shape_search_tag_dark, mSearchRecordTl);
            }
        }
    }

    @Override
    public void addSearchAllToTagLayout(ArrayList<SearchAllBean> searchAllBeen) {
        for (int i = 0; i<searchAllBeen.size(); i++) {
            if(!TextUtils.isEmpty(searchAllBeen.get(i).getKeyWord()) && isFirstAdd ){
                mUtils.addToTagLayout(searchAllBeen.get(i).getKeyWord(), R.color.colorAccent, R.drawable.shape_search_tag_blue, mSearchAllTl);
            }
        }
        isFirstAdd = false;
    }

    @Override
    public void showSearchContentNull() {
        ToastUtil.showShort(this,"搜索内容不能为空");
        resultFragment.addSearchResultListError();
    }

    @Override
    public void loadMore() {
        pageNo ++;
        String searchContent = mSearchBookEt.getText().toString();
        mPresenter.searchBookList(searchContent, pageNo);
    }

    /**
     * 设置搜索图书结果，并展示到SearchResultFragment中
     * @param searchBookBean
     */
    @Override
    public void setSearchResultList(SearchBookBean searchBookBean) {
        if(resultFragment == null || !resultFragment.isAdded() && !searchBookBean.getList().isEmpty()){
            resultFragment = SearchResultFragment.newInstance(searchBookBean);
            FragmentUtil
                    .hideFragment(getSupportFragmentManager()).add(R.id.fl_content, resultFragment)
                    .addToBackStack(null)
                    .show(resultFragment)
                    .commit();
            isTagClick = false;
            KeyBoardUtil.closeKeybord(this);
        }
    }

    @Override
    public void addSearchResultList(SearchBookBean searchBookBean) {
        if(resultFragment!=null){
            resultFragment.addSearchResultList(searchBookBean);
        }
    }

    @Override
    public void setSearchResultListError() {
        ToastUtil.showShort(this,"已加载全部数据");
    }

    /**
     * 设置搜索关键字结果，并展示到SearchKeyWordFragment中
     * @param list
     */
    @Override
    public void setSearchKeyWords(ArrayList<String> list) {
        if(keyWordFragment == null || !keyWordFragment.isAdded()){
            keyWordFragment = SearchKeyWordFragment.newInstance(searchKeyword, list);
            FragmentUtil
                    .hideFragment(getSupportFragmentManager()).add(R.id.fl_content, keyWordFragment)
                    .addToBackStack(null)
                    .show(keyWordFragment)
                    .commit();
        }
    }

    @Override
    public void getClickTxt(String tag) {
        isTagClick = true;
        pageNo = 1;
        mPresenter.searchBookList(tag,pageNo);
        //设置EditText显示的文字和光标位置
        mSearchBookEt.setText(tag);
        mSearchBookEt.setSelection(tag.length());
    }

    @OnClick({R.id.et_search_book, R.id.tv_search_record_clear,R.id.tv_search, R.id.img_search_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.et_search_book:
                if(resultFragment!=null && resultFragment.isAdded()){
                    getSupportFragmentManager().popBackStack();
                }
                if(keyWordFragment!=null && keyWordFragment.isAdded()){
                    getSupportFragmentManager().popBackStack();
                }
                pageNo = 1;
                break;

            case R.id.tv_search_record_clear:
                mPresenter.clearSearchRecord();
                mPresenter.getSearchRecord();
                break;

            case R.id.tv_search:
                String searchContent = mSearchBookEt.getText().toString();
                if(!TextUtils.isEmpty(searchContent)){
                    mPresenter.searchBookList(searchContent, pageNo);
                    mPresenter.setSearchRecord(searchContent);
                    mPresenter.getSearchRecord();
                }
                break;

            default:
                finish();
                break;
        }

    }

    @Override
    public void setSearchEditText(String searchContent) {
        mSearchBookEt.setText(searchContent);
        mSearchBookEt.setSelection(searchContent.length());
        mPresenter.searchBookList(searchContent, 1);
        mPresenter.setSearchRecord(searchContent);
        mPresenter.getSearchRecord();
    }
}
