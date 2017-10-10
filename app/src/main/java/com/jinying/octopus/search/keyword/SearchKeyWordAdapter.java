package com.jinying.octopus.search.keyword;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinying.octopus.R;
import com.jinying.octopus.search.ISearchKeyWordListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class SearchKeyWordAdapter extends RecyclerView.Adapter<SearchKeyWordAdapter.KeyWordViewHolder> {

    private Context context;

    private List<String> list;
    @NonNull
    private String searchKeyword;
    @NonNull
    private ISearchKeyWordListener listener;

    public SearchKeyWordAdapter(Context context, List<String> list, @NonNull String searchKeyword, @NonNull ISearchKeyWordListener listener) {
        this.context = context;
        this.list = list;
        this.searchKeyword = searchKeyword;
        this.listener = listener;
    }

    @Override
    public KeyWordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_item_search_keyword_layout ,parent, false);
        return new KeyWordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KeyWordViewHolder holder, final int position) {
        SpannableString msp = new SpannableString(list.get(position));
        char[] charArray = new char[searchKeyword.length()];
        for (int i = 0; i < searchKeyword.length(); i++) {
            charArray[i] = searchKeyword.charAt(i);
        }
        for (int i = 0; i < charArray.length; i++) {
            int index = list.get(position).indexOf(charArray[i]);
            if(index!=-1){
                msp.setSpan(new ForegroundColorSpan(Color.RED), index, index+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        holder.mSearchKeyWordTv.setText(msp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setSearchEditText(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void setData(@NonNull String searchKeyword, ArrayList<String> list) {
        this.searchKeyword = searchKeyword;
        this.list = list;
        notifyDataSetChanged();
    }

    static class KeyWordViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_search_keyword)
        TextView mSearchKeyWordTv;
        public KeyWordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
