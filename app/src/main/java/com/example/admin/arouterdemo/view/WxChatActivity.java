package com.example.admin.arouterdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.admin.arouterdemo.MyApplication;
import com.example.admin.arouterdemo.R;
import com.example.admin.arouterdemo.utils.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：微信页面
 * <p>
 * create_time：2018/7/20 19:39
 * update_by：
 * update_time:
 */
@Route(path = "/activity/weixin")
public class WxChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_page_layout);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.login_btn, R.id.share_btn, R.id.pay_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                wxLogin();
                break;
            case R.id.share_btn:
                wxShare();
                break;
            case R.id.pay_btn:
                wxPay();
                break;
        }
    }

    /**
     * 微信登录
     */
    private void wxLogin(){
        if (!MyApplication.mWxApi.isWXAppInstalled()) {
            ToastUtil.showShort(getApplicationContext(), "您还未安装微信客户端");
            return;
        }
//        MyApplication.mWxApi.openWXApp(); // 启动微信
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "app_wechat_测试";
        MyApplication.mWxApi.sendReq(req);
    }

    /**
     * 微信分享
     */
    private void wxShare(){

    }

    /**
     * 微信支付
     */
    private void wxPay(){

    }

}
