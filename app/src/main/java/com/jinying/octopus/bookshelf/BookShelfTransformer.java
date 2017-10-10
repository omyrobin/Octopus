package com.jinying.octopus.bookshelf;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.jinying.octopus.util.DensityUtils;

/**
 * Created by omyrobin on 2017/8/14.
 */

public class BookShelfTransformer implements ViewPager.PageTransformer{

    private Context context;

    public BookShelfTransformer(Context context) {
        this.context = context;
    }
    @Override
    public void transformPage(View page, float position) {
        if(position>=1.0f){
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(0.85f);
            page.setScaleY(0.85f);
            page.setAlpha(0.7f);
        }else if(position>=0 && position<1){//0.85~1
            page.setAlpha(1.0f);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(0.85f + (1-position)*0.15f);
            page.setScaleY(0.85f + (1-position)*0.15f);
        }else {
            float scale = (page.getWidth() + DensityUtils.dp2px(context,15) * position) / (float) (page.getWidth());
            page.setAlpha(scale * 1.0f);
            if(position<-3.0f){
                page.setAlpha(0.0f);
            }
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale);
            page.setScaleY(scale);
            //修改过的代码
            page.setTranslationX(-page.getWidth() * position + (DensityUtils.dp2px(context,30) * scale) * position);
        }
    }
}
