package com.jinying.octopus.bookmall.neworend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.adapter.BaseBookAdapter;
import com.jinying.octopus.api.GetService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.BookListBean;
import com.jinying.octopus.bookmall.rankdetail.RankDetailsActivity;
import com.jinying.octopus.constant.Constant;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.ProgressSubscriber;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.ToastUtil;
import com.jinying.octopus.widget.refresh.SimpleLoadView;
import com.jinying.octopus.widget.refresh.SimpleRefreshLayout;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class NewEndListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.srl_morelist_refresh)
    SimpleRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_morelist_ui)
    RecyclerView mBookRv;
    private BaseBookAdapter adapter;
    private int pageNo = 1;
    private String channel;
    private String type;
    private String title;

    public static final String NEW = "1";
    public static final String END = "2";

    public static void newInstance(Context context, String channel, String type, String title){
        Intent intern = new Intent(context, NewEndListActivity.class);
        intern.putExtra(Constant.CHANNEL, channel);
        intern.putExtra(Constant.TYPE, type);
        intern.putExtra(Constant.TITLE, title);
        context.startActivity(intern);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_list;
    }

    @Override
    protected void obtainIntent() {
        super.obtainIntent();
        channel = getIntent().getStringExtra(Constant.CHANNEL);
        type = getIntent().getStringExtra(Constant.TYPE);
        title = getIntent().getStringExtra(Constant.TITLE);
        Logger.i(" type " + type  + "  :   channel  " + channel);
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initializeView() {
        initRefreshView();
        initManager();
        getBookList();
    }

    private void initRefreshView(){
        mRefreshLayout.setScrollEnable(true);
        mRefreshLayout.setPullDownEnable(false);
        mRefreshLayout.setPullUpEnable(true);
        mRefreshLayout.setFooterView(new SimpleLoadView(this));
        mRefreshLayout.setOnSimpleRefreshListener(new SimpleRefreshLayout.OnSimpleRefreshListener() {

            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                pageNo ++;
                getBookList();
            }
        });
    }

    private void initManager(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mBookRv.setLayoutManager(manager);
    }

    private void getBookList(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNo", pageNo);
        params.put("pageSize", 10);
        params.put("type", type);
        params.put("channel", channel);
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        Observable<Response<BaseResponse<BookListBean>>> o = getService.getEndBookList(Url.GET_LISTPAGE, params, RetrofitHeaderConfig.getHeaders());
        RetrofitClient.client().request(o, new ProgressSubscriber<BookListBean>(this) {
            @Override
            protected void onSuccess(BookListBean bookListBean) {
                if(adapter == null){
                    adapter = new NewEndListAdapter(NewEndListActivity.this, bookListBean.getList());
                    mBookRv.setAdapter(adapter);
                }else{
                    if(bookListBean.getList().isEmpty()){
                        ToastUtil.showShort(NewEndListActivity.this, "已加载全部数据");
                    }
                    adapter.addBookList(bookListBean.getList());
                }
                mRefreshLayout.onLoadMoreComplete();
            }

            @Override
            protected void onFailure(String message) {
                ToastUtil.showShort(NewEndListActivity.this, message);
                mRefreshLayout.onLoadMoreComplete();
            }
        });
    }

}
