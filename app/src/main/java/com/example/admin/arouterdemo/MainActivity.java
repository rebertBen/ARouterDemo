package com.example.admin.arouterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.js_btn)
    Button jsBtn;
    @BindView(R.id.locate_btn)
    Button locateBtn;
    @BindView(R.id.banner_btn)
    Button bannerBtn;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.wx_btn)
    Button wxBtn;
    @BindView(R.id.video_btn)
    Button videoBtn;
    @BindView(R.id.popu_btn)
    Button popuBtn;
    @BindView(R.id.wechat_btn)
    Button wechatBtn;
    @BindView(R.id.refresh_btn)
    Button refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        btn.setOnClickListener(this);
        wxBtn.setOnClickListener(this);
        jsBtn.setOnClickListener(this);
        locateBtn.setOnClickListener(this);
        bannerBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
        popuBtn.setOnClickListener(this);
        wechatBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
//                ARouter.getInstance().build("/hello/activity").navigation();
//                ARouter.getInstance().build("/hello/activity")
//                        .withString("name", "zhangsan")
//                        .withInt("age", 25)
//                        .navigation();
                ARouter.getInstance().build("/hello/activity")
                        .withString("name", "zhangsan")
                        .withInt("age", 25)
                        .withSerializable("person", new Person("李四", 25, 100.1f))
                        .navigation(this, 100);
                break;
            case R.id.wx_btn:
                ARouter.getInstance().build("/activity/photo").navigation();
                break;
            case R.id.wechat_btn:
                ARouter.getInstance().build("/activity/weixin").navigation();
                break;
            case R.id.js_btn:
                ARouter.getInstance().build("/activity/js").navigation();
                break;
            case R.id.locate_btn:
                ARouter.getInstance().build("/activity/locate").navigation();
                break;
            case R.id.banner_btn:
                ARouter.getInstance().build("/activity/banner").navigation();
                break;
            case R.id.video_btn:
                ARouter.getInstance().build("/activity/video").navigation();
                break;
            case R.id.popu_btn:
                ARouter.getInstance().build("/activity/popu").navigation();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                Toast.makeText(MainActivity.this, "1000", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
