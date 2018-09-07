package com.example.admin.arouterdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.admin.arouterdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：地图定位
 * <p>
 * create_time：2018/7/27 16:25
 * update_by：
 * update_time:
 */
@Route(path = "/activity/locate")
public class LocateActivity extends AppCompatActivity {

    @BindView(R.id.baidu_btn)
    Button baiduBtn;
    @BindView(R.id.gaode_btn)
    Button gaodeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.baidu_btn, R.id.gaode_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baidu_btn:
                break;
            case R.id.gaode_btn:
                break;
        }
    }
}
