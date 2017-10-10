package com.jinying.octopus.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jinying.octopus.App;
import com.jinying.octopus.R;

public class ImageLoader {

    private static final RequestOptions options;

    static{
        options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(App.getInstance()))
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.error)
                .priority(Priority.HIGH);
//                .diskCacheStrategy(DiskCacheStrategy.NONE);
    }

    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }
    
    //加载drawable
    public static void loadImageView(Context mContext, int drawable, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(mContext))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(mContext).load(drawable).apply(options).into(mImageView);
    }

    //加载我的用户头像
    public static void loadUserImageMine(Context mContext, String path, ImageView mImageView) {
    	Glide.with(mContext)
    	.load(path)
    	.apply(options)
    	.into(mImageView);
    }
    
    //加载圆形用户头像
    public static void loadUserImage(Context mContext, String path, ImageView mImageView) {
    	Glide.with(mContext)
    	.load(path)
        .apply(options)
    	.into(mImageView);
    }
    
    //加载小说封面
    public static void loadNovelCover(Context mContext, String path, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(mContext))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if(path.contains("c.hotread.com/assets/images/default-cover-boy.jpg")){
            Glide.with(mContext).load(R.drawable.ic_book_default_cover_male).apply(options).into(mImageView);
        }else if(path.contains("c.hotread.com/assets/images/default-cover-girl.jpg")){
            Glide.with(mContext).load(R.drawable.ic_book_default_cover_female).apply(options).into(mImageView);
        }else{
            Glide.with(mContext).load(path).apply(options).into(mImageView);
        }
    }

    //加载高斯模糊的图片
    public static void gaussianBlur(Context mContext, String path, ImageView mImageView){
        RequestOptions options = new RequestOptions()
                .transform(new GlideBlurTransformation(mContext, 14,3))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    //设置缩略图支持
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}