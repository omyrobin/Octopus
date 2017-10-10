package com.jinying.octopus.bookdetails;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Preconditions;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.CatalogueBean;
import com.jinying.octopus.bean.StoryListBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.image.ImageLoader;
import com.jinying.octopus.read.ui.ReadActivity;
import com.jinying.octopus.util.CatalogueUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class BookDetailsActivity extends BaseActivity implements BookDetailsContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_bookdetails_bg)
    ImageView mBackGroundImag;
    @BindView(R.id.rv_bookdetails)
    RecyclerView mBookDetailsRv;
    @BindView(R.id.tv_book_add)
    TextView mBookAddTv;
    @NonNull
    private BookDetailsContract.Presenter mPresenter;
    private StoryVoBean storyVoBean;
    private String id;
    private int pageNo = 1;
    public static final String BOOK_ID = "BOOK_ID";
    private BookDetailsAdapter adapter;
    private CatalogueBean catalogueBean;

    public static void newInstance(Context context, String id){
        Intent intent = new Intent(context,BookDetailsActivity.class);
        intent.putExtra(BOOK_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_details;
    }

    @Override
    protected void obtainIntent() {
        super.obtainIntent();
        id = getIntent().getExtras().getString(BOOK_ID);
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initializeView() {
        mPresenter = new BookDetailsPresenter(this,this);
        initManager();
        initAdapter();
        getBookDetails();
    }

    private void initManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBookDetailsRv.setLayoutManager(layoutManager);
    }

    private void initAdapter(){
        adapter = new BookDetailsAdapter(this, mPresenter);
        mBookDetailsRv.setAdapter(adapter);
    }

    @Override
    public void setPresenter(BookDetailsContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }

    private void getBookDetails(){
        mPresenter.getBookDetails(id);
    }

    @Override
    public void setBookDetails(StoryVoBean storyVoBean) {
        this.storyVoBean = storyVoBean;
        ImageLoader.gaussianBlur(this, storyVoBean.getCover(), mBackGroundImag);
        if(storyVoBean.isOnShelf()){
            isAddBookshelf();
        }
        adapter.setBookDetails(storyVoBean);
    }

    @Override
    public void setCatalogue(CatalogueBean catalogueBean) {
        this.catalogueBean = catalogueBean;
        adapter.setCatalogue(catalogueBean);
        mPresenter.getRecommendStory(storyVoBean.getType(), pageNo, id);
    }

    @Override
    public void isAddBookshelf() {
        mBookAddTv.setClickable(false);
        mBookAddTv.setTextColor(ContextCompat.getColor(this,R.color.textColorSecondary));
        mBookAddTv.setText(getString(R.string.already_add_bookshelf));
    }

    @Override
    public void setRecommedStory(ArrayList<StoryListBean> recommendStoryBeen) {
        adapter.setRecommedStory(recommendStoryBeen);
    }
    
    @OnClick({R.id.tv_book_read, R.id.tv_book_add})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_book_read:
                if(catalogueBean != null){
                    mPresenter.beginReadBook();
                }
                break;

            default:
                if(catalogueBean != null){
                    mPresenter.setCatalogueCache();
                }
                break;
        }
    }

}
