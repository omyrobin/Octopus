package com.jinying.octopus.bookshelf;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.jinying.octopus.App;
import com.jinying.octopus.BaseFragment;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.BannerBean;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bookdetails.BookDetailsActivity;
import com.jinying.octopus.bookmall.webactivity.WebActivity;
import com.jinying.octopus.event.EventAddToBook;
import com.jinying.octopus.event.EventNetConnection;
import com.jinying.octopus.search.SearchActivity;
import com.jinying.octopus.util.BackGroundAlphaUtils;
import com.jinying.octopus.util.DensityUtils;
import com.jinying.octopus.util.NetworkUtil;
import com.jinying.octopus.util.StringUtils;
import com.jinying.octopus.util.ToastUtil;
import com.jinying.octopus.view.BookCardsPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by omyrobin on 2017/8/11.
 */

public class BookShelfFragment extends BaseFragment implements BookShelfContract.View, ViewPager.OnPageChangeListener, View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_bookshelf_search)
    ImageView toolbar_search_img;
    @BindView(R.id.img_bookshelf_edit)
    ImageView toolbar_edit_img;
    @BindView(R.id.tv_bookshelf_cancel)
    TextView toolbar_cancel_tv;
    @BindView(R.id.rl_bookshelf_content)
    RelativeLayout mContentRlayout;
    @BindView(R.id.ll_bookshelf_cards_parent)
    LinearLayout mCardsParentLlayout;
    @BindView(R.id.vis_book_empty)
    ViewStub mBookShelfEmptyVis;
    @BindView(R.id.vis_book_net_error)
    ViewStub mBookShelfNetErrorVis;
    @BindView(R.id.tv_book_name)
    TextView mBookNameTv;
    @BindView(R.id.vp_bookcards)
    BookCardsPager mBookCardsVp;
    @BindView(R.id.tv_book_before_read_title)
    TextView mCardsReadTitleTv;
    @BindView(R.id.tv_book_before_read_content)
    TextView mCardsReadContentTv;
    @BindView(R.id.tv_book_readcount)
    TextView mCardsReadCountTv;
    @BindView(R.id.rl_viewpager_parent)
    RelativeLayout mViewPagerParentRlayout;
    @BindView(R.id.rl_indicator_parent)
    RelativeLayout mIndicatorParentRlayout;
    @BindView(R.id.rv_bookshelf)
    RecyclerView mBookShelfRv;
    @BindView(R.id.alpha_view)
    View mAlphaView;
    @NonNull
    private BookShelfContract.Presenter mPresenter;
    private BookShelfAdapter mAdapter;
    private ArrayList<BookShelfBean> bookList;
    private int bookShelfStyle;
    private BookShelfPageAdapter mBookCardsAdapter;
    private View bannerView;
    private TextView mBannerContentTv;
    private PopupWindow mEditPopView;
    private TextView mBookShelfEditTv,mBookShelfNineTv,mBookShelfCardsTv;
    private TextView[] mPopTvs;
    private int popIndex;
    public static final int GRID_STYLE = 0;
    public static final int CARDS_STYLE = 1;
    private ArrayList<BannerBean> bannerBeanList;
    private View emptyView;
    private View netErrorView;
    private TextView mEmptyToMallTv;

    private PopupWindow mDeletePopupWindow;
    private RelativeLayout bottomView;
    private RelativeLayout mSelectAllRl;
    private CheckBox mSelectAllChcBx;
    private ImageView mDeleteImg;
    private RelativeLayout mDeleteRl;
    private boolean isDeleteCache;
    private boolean isDeleteAll;
    private boolean isEdit;

    public BookShelfFragment(){

    }

    /**boolean
     * 观察----加入书架
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventAddToBook event) {
        mPresenter.getBookList();
    }

    /**
     *异常use状态 网络连接后
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(EventNetConnection event) {
        mPresenter.getBannerList();
    }

    @Override
    protected int provideContentViewId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_bookshelf;
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void setPresenter(@NonNull BookShelfContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    protected void initializeView() {
        getBookShelfStyle();
        initManager();
        initBannerView();
        initDeleteView();
        initBookCardsView();
        initAdapter();
        mPresenter.isNetError();
    }

    private void getBookShelfStyle(){
        bookShelfStyle = mPresenter.getBookShelfStyle();
        popIndex = bookShelfStyle + 1;
    }

    private void initBannerView(){
        bannerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_bookshelf_banner, null);
        mBannerContentTv = bannerView.findViewById(R.id.tv_bookshelf_banner);
        mBannerContentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bannerBeanList!=null && !bannerBeanList.isEmpty()){
                    if(bannerBeanList.get(0).getTargetType() == 0){
                        WebActivity.newInstance(getActivity(), bannerBeanList.get(0).getLinkUrl(), "活动");
                    }else if(bannerBeanList.get(0).getTargetType() == 1){
                        BookDetailsActivity.newInstance(getActivity(), bannerBeanList.get(0).getTargetId()+"");
                    }else if(bannerBeanList.get(0).getTargetType() == 2){
                        ToastUtil.showShort(getActivity(),"不支持该类型");
                    }else if(bannerBeanList.get(0).getTargetType() == 6){
                        ToastUtil.showShort(getActivity(),"不支持该类型");
                    }
                }
            }
        });

        if(!NetworkUtil.isConnected(getActivity())){
            mBannerContentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            mBannerContentTv.setText(getActivity().getString(R.string.no_have_net_connection));
        }
    }

    private void initDeleteView(){
        bottomView = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.pop_bookshelf_delete, null);
        mSelectAllRl = bottomView.findViewById(R.id.rl_bookshelf_select_parent);
        mSelectAllChcBx = bottomView.findViewById(R.id.chk_bookshelf_select_all);
        mDeleteImg = bottomView.findViewById(R.id.img_bookshelf_delete);
        mDeleteRl =  bottomView.findViewById(R.id.rl_bookshelf_delete_parent);
        mSelectAllRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectAllChcBx.performClick();
            }
        });
        mSelectAllChcBx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isDeleteAll = !isDeleteAll;
                if(bookShelfStyle == GRID_STYLE){
                    mAdapter.deleteAll(isDeleteAll);
                }else {
                    mBookCardsAdapter.deleteAll(isDeleteAll);
                    setBookCardsAdapter(mPresenter.getItemPositon());
                }
                mPresenter.deleteAllBook(isDeleteAll, bookList.size());
            }
        });
        mDeleteRl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!NetworkUtil.isConnected(getActivity())){
                    return;
                }
                showDeleteFileCacheDiaLog(mPresenter.getCountSelect());
            }
        });
    }

    private void initBookCardsView(){
        mViewPagerParentRlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mBookCardsVp.onTouchEvent(motionEvent);
            }
        });
    }

    private void initManager(){
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0){
                    return 3;
                }
                return 1;
            }
        });
        mBookShelfRv.setLayoutManager(layoutManager);
    }

    private void initAdapter(){
        mAdapter = new BookShelfAdapter(getActivity(), mPresenter);
        mBookShelfRv.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_bookshelf_menu_edit:
                mPresenter.showDeleteView();
                break;

            case R.id.tv_bookshelf_menu_nine:
                changeBookShelfConifg(1,GRID_STYLE);
                break;

            default:
                changeBookShelfConifg(2,CARDS_STYLE);
                break;
        }
    }

    @OnClick({R.id.img_bookshelf_search, R.id.img_bookshelf_edit, R.id.tv_bookshelf_cancel})
    public void search(View v){
        switch (v.getId()){
            case R.id.img_bookshelf_search:
                SearchActivity.newInstance(getActivity());
                break;

            case R.id.img_bookshelf_edit:
                showPopMenu().showAsDropDown((View) v.getParent());
                break;

            default:
                mPresenter.resetBookViewConfig();
                mPresenter.resetDeleteConfig();
                mPresenter.dismissDeleteView();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }

    @Override
    public void showNetErrorView() {
        if(mBookShelfNetErrorVis!=null && netErrorView == null){
            netErrorView = mBookShelfNetErrorVis.inflate();
            netErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addBook() {
        if(!isEdit)
            mPresenter.toBookMall();
    }

    @Override
    public void setBannerList(ArrayList<BannerBean> bannerBeanList) {
        if(netErrorView != null && netErrorView.getVisibility() == View.VISIBLE){
            netErrorView.setVisibility(View.GONE);
        }
        this.bannerBeanList = bannerBeanList;
        mBannerContentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mBannerContentTv.setText(bannerBeanList.get(0).getContent());
        mAdapter.setBannerView(bannerView);
    }

    @Override
    public void setBookList(ArrayList<BookShelfBean> bookList) {
        this.bookList = bookList;
        if(bookList.isEmpty()){
            setEmptyView();
        }else{
            if(emptyView != null) {
                emptyView.setVisibility(View.GONE);
            }
        }
        setBookShelfStyle(bookShelfStyle);
    }

    @Override
    public void setBookCardsVpConfig() {
        mBookCardsAdapter = new BookShelfPageAdapter(getActivity(), bookList, mPresenter);
        mBookCardsVp.setAdapter(mBookCardsAdapter);
        mBookCardsVp.setCurrentItem(mPresenter.getItemPositon(),false);
        if(bookList!=null && !bookList.isEmpty())
            setCardsBookInfo(bookList.get(mPresenter.getItemPositon()));
    }

    @Override
    public void setCardsBookInfo(BookShelfBean bookInfo) {
        mBookNameTv.setText(bookInfo.getStoryVo().getName());
        if("0" . equals(bookInfo.getReadingChapterId())){
            mCardsReadTitleTv.setText("开始阅读:  " + bookInfo.getReadingChapterName());
        }else{
            mCardsReadTitleTv.setText("上次读到:  " + bookInfo.getReadingChapterName());
        }
        mCardsReadContentTv.setText(StringUtils.editString(bookInfo.getChapterDesc()));
        mCardsReadCountTv.setText(bookInfo.getStoryVo().getPv()+"人阅读");
    }

    @Override
    public void setCardsBookAlpha(float alpha) {
        mBookNameTv.setAlpha(alpha);
        mCardsReadTitleTv.setAlpha(alpha);
        mCardsReadContentTv.setAlpha(alpha);
        mCardsReadCountTv.setAlpha(alpha);
    }

    @Override
    public void showGridDeleteView() {
        mAdapter.showGridDeleteView();
    }

    @Override
    public void showCardsDeleteView() {
        mBookCardsAdapter.showCardsDeleteView();
        setBookCardsAdapter(mPresenter.getItemPositon());
    }

    @Override
    public void deleteSelectBookComplete() {
        isDeleteAll = false;
        mDeletePopupWindow.dismiss();
        resetToolbar(false);
    }

    @Override
    public void dimissGridDeleteView() {
        mDeletePopupWindow.dismiss();
        mAdapter.dimissGridDeleteView();
    }

    @Override
    public void dimissCardsDeleteView() {
        mDeletePopupWindow.dismiss();
        mBookCardsAdapter.dimissCardsDeleteView();
        setBookCardsAdapter(0);
    }

    @Override
    public void resetDeleteCache() {
        isDeleteCache = false;
    }

    @Override
    public void resetBookViewConfig() {
        resetToolbar(false);
        resetBookChecked(false);
        resetDeleteAll(false);
    }

    /**
     * 重置Toolbar样式
     * @param isEdit
     */
    private void resetToolbar(boolean isEdit){
        this.isEdit = isEdit;
        if(isEdit){
            toolbar_search_img.setVisibility(View.GONE);
            toolbar_edit_img.setVisibility(View.GONE);
            mBannerContentTv.setVisibility(View.GONE);
            toolbar_cancel_tv.setVisibility(View.VISIBLE);
        }else{
            toolbar_cancel_tv.setVisibility(View.GONE);
            toolbar_search_img.setVisibility(View.VISIBLE);
            toolbar_edit_img.setVisibility(View.VISIBLE);
            mBannerContentTv.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void resetBookChecked(boolean checked) {
        if(bookShelfStyle == GRID_STYLE){
            mAdapter.deleteAll(checked);
        }else {
            mBookCardsAdapter.deleteAll(checked);
        }
    }

    private void resetDeleteAll(boolean isDeleteAll) {
        this.isDeleteAll = isDeleteAll;
        mSelectAllChcBx.setChecked(false);
    }

    @Override
    public void notifyBottomView(boolean isDeleteAll, boolean isDeleteSelect) {
        this.isDeleteAll = isDeleteAll;
        if(bottomView.getVisibility() == View.VISIBLE){
            mSelectAllChcBx.setChecked(isDeleteAll);
            mDeleteImg.setSelected(isDeleteSelect);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPresenter.onPageScrolled(position,positionOffset,mBookNameTv, bookList);
        mPresenter.animIndicator(position,positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        mBookCardsVp.setLastPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 显示右上角menu
     * @return
     */
    private PopupWindow showPopMenu(){
        if(mEditPopView ==null){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_bookshelf_edit, null);
            mEditPopView = new PopupWindow(view);
            mEditPopView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mEditPopView.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            mEditPopView.setFocusable(true);
            mEditPopView.setBackgroundDrawable(new ColorDrawable(0x00000000));
            mEditPopView.setOutsideTouchable(true);
            mEditPopView.setAnimationStyle(R.style.ActionPopupWindowAlpha);
            mEditPopView.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    BackGroundAlphaUtils.getInstatnce().brighten(mAlphaView);//TODO 将view变亮
                }
            });
            mBookShelfEditTv = view.findViewById(R.id.tv_bookshelf_menu_edit);
            mBookShelfNineTv = view.findViewById(R.id.tv_bookshelf_menu_nine);
            mBookShelfCardsTv = view.findViewById(R.id.tv_bookshelf_menu_cards);

            mBookShelfEditTv.setOnClickListener(this);
            mBookShelfNineTv.setOnClickListener(this);
            mBookShelfCardsTv.setOnClickListener(this);

            mPopTvs = new TextView[]{mBookShelfEditTv,mBookShelfNineTv,mBookShelfCardsTv};
            mPopTvs[popIndex].setSelected(true);
        }
        return mEditPopView;
    }

    /**
     * 改变书架的显示方式--进行相关配置
     * @param currPopIndex 当前选择文本的Index
     * @param currBookShelfStyle 当前选择文本的对应的BookShelfStyle
     */
    private void changeBookShelfConifg(int currPopIndex, int currBookShelfStyle){
        menuColorTv(currPopIndex);
        setBookShelfStyle(currBookShelfStyle);
        mPresenter.saveBookShelfConifg(currBookShelfStyle);//TODO 保存书架显示方式
        bookShelfStyle = mPresenter.getBookShelfStyle();
        mEditPopView.dismiss();
    }

    /**
     * 改变Menu文字颜色
     * @param popCurrIndex 当前选择文本的Index
     */
    private void menuColorTv(int popCurrIndex){
        if(popCurrIndex ==  popIndex){
            return;
        }else{
            mPopTvs[popCurrIndex].setSelected(true);
            mPopTvs[popIndex].setSelected(false);
        }
        popIndex = popCurrIndex;
    }

    private void setBookShelfStyle(int currBookShelfStyle) {
        if(currBookShelfStyle == CARDS_STYLE){
            mAdapter.setBookList(null);
            mPresenter.configIndicatorCount(mIndicatorParentRlayout);
            mPresenter.configBookCardsVp(mBookCardsVp,this);
            if(bookList.isEmpty()){
                mCardsParentLlayout.setVisibility(View.GONE);
            }else {
                mCardsParentLlayout.setVisibility(View.VISIBLE);
            }
        }else{
            mAdapter.setBookList(bookList);
            mCardsParentLlayout.setVisibility(View.GONE);
        }
    }

    private void setBookCardsAdapter(int itemIndex){
        mBookCardsVp.setAdapter(mBookCardsAdapter);//解决notify后transformer短暂失效问题
        mBookCardsVp.setCurrentItem(itemIndex);
    }

    /**
     * 设置EmptyView
     */
    private void setEmptyView(){
        if(mBookShelfEmptyVis != null && emptyView == null){
            emptyView = mBookShelfEmptyVis.inflate();
            mEmptyToMallTv = emptyView.findViewById(R.id.tv_bookshelf_empty_tobookmall);
            mEmptyToMallTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addBook();
                }
            });
        }
        emptyView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示删除bottomView
     */
    @Override
    public void showDeleteView(){
        mEditPopView.dismiss();
        if(mDeletePopupWindow !=null && mDeletePopupWindow.isShowing()){
            return;
        }else{
            resetToolbar(true);
        }
        if(mDeletePopupWindow == null){
            mDeletePopupWindow = new PopupWindow(bottomView, (int) App.width, DensityUtils.dp2px(getActivity(), 60));
            mDeletePopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
            mDeletePopupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.BOTTOM, (int) App.width, 0);
        }else{
            mDeletePopupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.BOTTOM, (int) App.width, 0);
        }
    }

    /**
     * 显示是否删除本地缓存文件
     */
    private void showDeleteFileCacheDiaLog(int num) {
        if(num==0){
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.BaseAlterDiaLog);  //先得到构造器
        builder.setTitle(getActivity().getString(R.string.delete_book,num)); //设置标题
        builder.setMultiChoiceItems(new String[]{"同时删除本地缓存文件"}, null, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                isDeleteCache = !isDeleteCache;
            }
        });
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteSelectBook(isDeleteCache);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
