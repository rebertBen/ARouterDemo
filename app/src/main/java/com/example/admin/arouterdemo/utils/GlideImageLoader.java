package com.example.admin.arouterdemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @author bobo
 * <p>
 * function：图片加载
 * <p>
 * create_time：2018/8/1 17:46
 * update_by：
 * update_time:
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
