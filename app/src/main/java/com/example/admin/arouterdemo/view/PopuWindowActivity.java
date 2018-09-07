package com.example.admin.arouterdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.admin.arouterdemo.R;
import com.example.admin.arouterdemo.utils.CustomPopuWindow;
import com.example.admin.arouterdemo.utils.PopuWindowUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：弹窗
 * <p>
 * create_time：2018/8/9 17:18
 * update_by：
 * update_time:
 */
@Route(path = "/activity/popu")
public class PopuWindowActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popu_window);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bottom_btn, R.id.center_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_btn:
//                new PopuWindowUtil().showWindow(this);
                new CustomPopuWindow().showPopuWindow(this, 0, 3, true);
                break;
            case R.id.center_btn:
                new CustomPopuWindow().showPopuWindow(this, 1, 2, false);
                break;
        }
    }
}
