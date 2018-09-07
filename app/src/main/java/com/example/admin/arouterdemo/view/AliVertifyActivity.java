package com.example.admin.arouterdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.security.rp.RPSDK;
import com.example.admin.arouterdemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：阿里人脸识别（实人认证）
 * <p>
 * create_time：2018/7/31 14:37
 * update_by：
 * update_time:
 */
@Route(path = "/activity/ali")
public class AliVertifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_vertify);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        RPSDK.initialize(AliVertifyActivity.this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        String verifyToken = "0315b5a2871f471da9976a49af341e7f";
        RPSDK.start(verifyToken, AliVertifyActivity.this,
                new RPSDK.RPCompletedListener() {
                    @Override
                    public void onAuditResult(RPSDK.AUDIT audit) {
                        Toast.makeText(AliVertifyActivity.this, audit + "", Toast.LENGTH_SHORT).show();
                        if(audit == RPSDK.AUDIT.AUDIT_PASS) { //认证通过
                        }
                        else if(audit == RPSDK.AUDIT.AUDIT_FAIL) { //认证不通过
                        }
                        else if(audit == RPSDK.AUDIT.AUDIT_IN_AUDIT) { //认证中，通常不会出现，只有在认证审核系统内部出现超时，未在限定时间内返回认证结果时出现。此时提示用户系统处理中，稍后查看认证结果即可。
                        }
                        else if(audit == RPSDK.AUDIT.AUDIT_NOT) { //未认证，用户取消
                        }
                        else if(audit == RPSDK.AUDIT.AUDIT_EXCEPTION){ //系统异常
                        }
                    }
                });
    }
}
