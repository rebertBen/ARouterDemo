package com.example.admin.arouterdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.admin.arouterdemo.R;
import com.example.admin.arouterdemo.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：图片轮询
 * <p>
 * create_time：2018/8/1 17:17
 * update_by：
 * update_time:
 */
@Route(path = "/activity/banner")
public class BannerActivity extends AppCompatActivity {

    @BindView(R.id.banner_view)
    Banner bannerView;

    private String[] urls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);

        initDate();
        initBanner();
    }

    private void initBanner() {
        bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        bannerView.setIndicatorGravity(BannerConfig.CENTER);
        bannerView.setImageLoader(new GlideImageLoader());
        bannerView.setImages(Arrays.asList(urls));
        bannerView.isAutoPlay(true);
        bannerView.setBannerAnimation(Transformer.DepthPage);
        bannerView.setDelayTime(3000);
    }

    private void initDate() {
        urls = new String[]{"http://img4.imgtn.bdimg.com/it/u=2493084575,3899981985&fm=27&gp=0.jpg",
                "http://img3.100bt.com/upload/ttq/20140419/1397902593310_middle.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1638092689,389447923&fm=27&gp=0.jpg",
                "http://www.jf258.com/uploads/2015-05-16/074404635.jpg",
                "http://img4.imgtn.bdimg.com/it/u=1605318774,919623526&fm=214&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3967548818,3419980996&fm=214&gp=0.jpg"};
    }

    @OnClick({R.id.img_btn, R.id.viewpager_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_btn:
                ARouter.getInstance().build("/activity/glide").navigation();
                break;
            case R.id.viewpager_btn:
                bannerView.start();
                break;
        }
    }
}
