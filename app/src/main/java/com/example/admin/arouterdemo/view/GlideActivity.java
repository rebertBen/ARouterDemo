package com.example.admin.arouterdemo.view;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.admin.arouterdemo.R;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：Glide图片加载
 * <p>
 * create_time：2018/8/2 11:38
 * update_by：
 * update_time:
 */
@Route(path = "/activity/glide")
public class GlideActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;

    private String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533268478859&di=f1eed418a8aa0984af3113fe4332d6ba&imgtype=0&src=http%3A%2F%2F2017.zcool.com.cn%2Fcommunity%2F037b7355775cafb0000018c1b222864.gif";
    private String defaultUrl = "http://static.bbs.nubia.cn/forum/201712/29/20171229104456jluywghkpyuy.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        glideIntoImg();
    }

    private void glideIntoImg(){
//        Glide.with(this).load(defaultUrl).into(image);
        RequestOptions option = new RequestOptions()
                .error(R.drawable.error)
                .placeholder(R.mipmap.ic_launcher);

        Glide.with(this)
                .load(defaultUrl)
                .apply(option)
                .transition(new DrawableTransitionOptions().crossFade(2000))
                .into(image);
    }

}
