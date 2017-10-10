package com.jinying.octopus.search.keyword;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jinying.octopus.BaseFragment;
import com.jinying.octopus.R;
import com.jinying.octopus.search.ISearchKeyWordListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class SearchKeyWordFragment extends BaseFragment {

    @BindView(R.id.rv_search_keyword)
    RecyclerView mSearchKeyWordRv;
    private String searchContent;
    private ArrayList<String> list;
    private SearchKeyWordAdapter adapter;
    public static final String CONTENT = "CONTENT";
    public static final String LIST = "LIST";
    private ISearchKeyWordListener listener;

    public static SearchKeyWordFragment newInstance(String searchContent, ArrayList<String> list){
        SearchKeyWordFragment fragment = new SearchKeyWordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT, searchContent);
        bundle.putStringArrayList(LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ISearchKeyWordListener) context;
    }

    @Override
    protected void obtainArguments() {
        super.obtainArguments();
        searchContent = getArguments().getString(CONTENT);
        list = getArguments().getStringArrayList(LIST);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_search_keyword;
    }

    @Override
    protected void initializeView() {
        initManager();
        initAdapter();
    }

    private void initManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSearchKeyWordRv.setLayoutManager(layoutManager);
    }

    private void initAdapter(){
        adapter = new SearchKeyWordAdapter(getActivity(),list, searchContent, listener);
        mSearchKeyWordRv.setAdapter(adapter);
    }
}
