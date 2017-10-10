package com.jinying.octopus.read.end;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinying.octopus.App;
import com.jinying.octopus.BaseActivity;
import com.jinying.octopus.R;
import com.jinying.octopus.api.GetService;
import com.jinying.octopus.bean.BaseResponse;
import com.jinying.octopus.bean.StoryListBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.constant.Url;
import com.jinying.octopus.http.RetrofitClient;
import com.jinying.octopus.http.RetrofitHeaderConfig;
import com.jinying.octopus.http.RetrofitManager;
import com.jinying.octopus.image.ImageLoader;
import com.jinying.octopus.util.DensityUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by omyrobin on 2017/9/4.
 */

public class KeepGoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.rv_guess_like)
    RecyclerView mGuessLikeRv;

    public static final String BEAN = "BEAN";

    private StoryVoBean bean;

    private GuessLikeAdapter adapter;

    public static void newInstance(Context context, StoryVoBean bean){
        Intent intent = new Intent(context, KeepGoActivity.class);
        intent.putExtra(BEAN, bean);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_keepgo;
    }

    @Override
    protected void obtainIntent() {
        super.obtainIntent();
        bean = (StoryVoBean) getIntent().getSerializableExtra(BEAN);
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(bean.getName());
        toolbar.setNavigationIcon(R.drawable.btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initializeView() {
        initLayoutManager();
        initData();
    }

    private void initLayoutManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mGuessLikeRv.setLayoutManager(layoutManager);
    }

    private void initData(){
        GetService getService = RetrofitManager.getClient().create(GetService.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", bean.getType());
        map.put("pageNo", 1);
        map.put("storyId", bean.getId());
        Observable<Response<BaseResponse<ArrayList<StoryListBean>>>> observable = getService.getRecommendStory(Url.GET_STORYRELATION, map, RetrofitHeaderConfig.getHeaders());
        RetrofitClient.client().compose(observable).subscribe(new Subscriber<ArrayList<StoryListBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<StoryListBean> recommedStory) {
                if(adapter == null){
                    adapter = new GuessLikeAdapter(KeepGoActivity.this, recommedStory);
                    mGuessLikeRv.setAdapter(adapter);
                }
            }
        });
    }

    static class GuessLikeAdapter extends RecyclerView.Adapter<RecommendViewHolder> {

        private Context context;

        private ArrayList<StoryListBean> recommedStory;

        public GuessLikeAdapter(Context context, ArrayList<StoryListBean> recommedStory) {
            this.context = context;
            this.recommedStory = recommedStory;
        }

        private int count;

        @Override
        public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_bookdetails_recommend,parent, false);
            return new RecommendViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecommendViewHolder holder, int position) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    if(count > recommedStory.size()-1){
                        return;
                    }
                    View childView = LayoutInflater.from(context).inflate(R.layout.adapter_item_bookdetails_gridlayout, null);
                    ImageView mBookCoverImg = childView.findViewById(R.id.img_book_cover);
                    TextView mBookNameTv = childView.findViewById(R.id.tv_book_name);
                    childView.setId(count);
                    ImageLoader.loadNovelCover(context,recommedStory.get(count).getStory().getCover(),mBookCoverImg);
                    mBookNameTv.setText(recommedStory.get(count).getStory().getName());

                    GridLayout.Spec rowSpec = GridLayout.spec(i);
                    final GridLayout.Spec columnSpec = GridLayout.spec(j);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,columnSpec);

                    holder.mBookDetailsListGl.setUseDefaultMargins(true);
                    params.width = (int) (App.width/4 - DensityUtils.dp2px(context, 10));
                    params.height = (int) (App.width/2 - DensityUtils.dp2px(context, 30));
                    holder.mBookDetailsListGl.addView(childView, params);

                    childView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            BookDetailsActivity.newInstance(context, recommedStory.get(view.getId()).getStory().getId()+"");
                        }
                    });
                    count ++;
                }
            }
        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.gl_bookdetails_like)
        GridLayout mBookDetailsListGl;
        public RecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
