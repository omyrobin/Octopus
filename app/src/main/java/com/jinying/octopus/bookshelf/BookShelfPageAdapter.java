package com.jinying.octopus.bookshelf;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinying.octopus.R;
import com.jinying.octopus.bean.BookShelfBean;
import com.jinying.octopus.bean.StoryVoBean;
import com.jinying.octopus.image.ImageLoader;
import com.jinying.octopus.util.NetworkUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by omyrobin on 2017/8/14.
 */

public class BookShelfPageAdapter extends PagerAdapter {

    private Context context;

    private List<BookShelfBean> bookShelfInfoList;

    private BookShelfContract.Presenter mPresenter;

    private boolean isEdit;

    private int mChildCount;

    public BookShelfPageAdapter(Context context, List<BookShelfBean> bookShelfInfoList, BookShelfContract.Presenter mPresenter) {
        this.context = context;
        this.bookShelfInfoList = bookShelfInfoList;
        this.mPresenter = mPresenter;
    }

    @Override
    public int getCount() {
        return bookShelfInfoList!=null && !bookShelfInfoList.isEmpty() ? bookShelfInfoList.size() + 1 : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_bookshelf_cards_layout, container, false);
        ImageView mBookPicImg = view.findViewById(R.id.img_book_pic);
        final CheckBox mBookDeleteChk = view.findViewById(R.id.chk_book_delete);
        TextView mBookUnReadTv = view.findViewById(R.id.tv_book_unread);
        view.setScaleX(0.85f);
        view.setScaleY(0.85f);
        if(isEdit && position<getCount()-1){
            mBookDeleteChk.setVisibility(View.VISIBLE);
            mBookDeleteChk.setChecked(bookShelfInfoList.get(position).getStoryVo().isChecked());
        }else{
            mBookDeleteChk.setVisibility(View.GONE);
        }

        if(position == getCount()-1){
            mBookPicImg.setScaleType(ImageView.ScaleType.CENTER);
            mBookPicImg.setBackgroundResource(R.drawable.shape_add_book);
            mBookPicImg.setImageResource(R.drawable.btn_add_book);
            mBookDeleteChk.setVisibility(View.GONE);
            mBookUnReadTv.setVisibility(View.GONE);
        }else{
            ImageLoader.loadNovelCover(context,bookShelfInfoList.get(position).getStoryVo().getCover(), mBookPicImg);
            mBookUnReadTv.setText(bookShelfInfoList.get(position).getUnReadingCount() + "\n章未读" );
            mBookPicImg.setImageResource(0);
        }

        mBookDeleteChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookShelfInfoList.get(position).getStoryVo().setChecked(!bookShelfInfoList.get(position).getStoryVo().isChecked());
                mPresenter.checkedDeleteChk(bookShelfInfoList.get(position).getStoryVo(),getCount()-1);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEdit){
                    mBookDeleteChk.performClick();
                }else{
                    if(position > bookShelfInfoList.size()-1){
                        mPresenter.toBookMall();
                    }else{
                        mPresenter.beginReadBook(bookShelfInfoList.get(position).getStoryVo());
                    }
                }

            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void showCardsDeleteView() {
        isEdit = true;
        notifyDataSetChanged();
    }

    public void dimissCardsDeleteView() {
        isEdit = false;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public void deleteAll(boolean isDeleteAll) {
        for (int i = 0; i< bookShelfInfoList.size(); i++){
            bookShelfInfoList.get(i).getStoryVo().setChecked(isDeleteAll);
        }
        notifyDataSetChanged();
    }
}
