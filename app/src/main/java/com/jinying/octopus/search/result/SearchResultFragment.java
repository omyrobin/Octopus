package com.jinying.octopus.search.result;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jinying.octopus.BaseFragment;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.SearchBookBean;
import com.jinying.octopus.widget.refresh.SimpleLoadView;
import com.jinying.octopus.widget.refresh.SimpleRefreshLayout;

import butterknife.BindView;

/**
 * Created by omyrobin on 2017/8/23.
 */

public class SearchResultFragment extends BaseFragment {

    @BindView(R.id.srl_result_refresh)
    SimpleRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_search_result)
    RecyclerView mSearchResultRv;
    public static final String RESULT_BOOK = "RESULT_BOOK";
    private SearchBookBean bookBean;
    private SearchResultAdapter mAdapter;
    private ILoadMoreListener mListener;

    public static SearchResultFragment newInstance(SearchBookBean bean){
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RESULT_BOOK, bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (ILoadMoreListener) context;
    }

    @Override
    protected void obtainArguments() {
        super.obtainArguments();
        bookBean = (SearchBookBean) getArguments().getSerializable(RESULT_BOOK);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void initializeView() {
        initManager();
        initRefreshView();
    }

    private void initManager(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mSearchResultRv.setLayoutManager(manager);
        mAdapter = new SearchResultAdapter(getActivity(), bookBean.getList());
        mSearchResultRv.setAdapter(mAdapter);
    }

    private void initRefreshView(){
        mRefreshLayout.setScrollEnable(true);
        mRefreshLayout.setPullDownEnable(false);
        mRefreshLayout.setPullUpEnable(true);
        mRefreshLayout.setFooterView(new SimpleLoadView(getActivity()));
        mRefreshLayout.setOnSimpleRefreshListener(new SimpleRefreshLayout.OnSimpleRefreshListener() {

            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mListener.loadMore();
            }
        });
    }

    /**
     * 在当前列表数据后追加数据(加载更多）
     * @param searchBookBean
     */
    public void addSearchResultList(SearchBookBean searchBookBean) {
        mRefreshLayout.onLoadMoreComplete();
        mAdapter.addBookList(searchBookBean.getList());
    }

    public void addSearchResultListError() {
        mRefreshLayout.onLoadMoreComplete();
    }

    public interface ILoadMoreListener{
        void loadMore();
    }
}
