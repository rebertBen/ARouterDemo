package com.example.admin.arouterdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.admin.arouterdemo.Person;
import com.example.admin.arouterdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：实名认证
 * <p>
 * create_time：2018/7/20 11:22
 * update_by：
 * update_time:
 */
@Route(path = "/hello/activity")
public class ARouterActivity extends AppCompatActivity {
    @BindView(R.id.tecent_btn)
    Button tecentBtn;
    @BindView(R.id.ali_btn)
    Button aliBtn;
    private TextView tv;
    private ImageView ivBack;

    @Autowired
    int age;
    @Autowired
    String name;
    @Autowired()
    Person person;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_arouter);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        tv = findViewById(R.id.tv);
        ivBack = findViewById(R.id.iv_back);

    }

    @OnClick({R.id.iv_back, R.id.tecent_btn, R.id.ali_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.tecent_btn:
                ARouter.getInstance().build("/activity/tecent").navigation();
                break;
            case R.id.ali_btn:
                ARouter.getInstance().build("/activity/ali").navigation();
                break;
        }
    }

}
