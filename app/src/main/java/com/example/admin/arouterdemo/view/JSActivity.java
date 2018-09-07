package com.example.admin.arouterdemo.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.admin.arouterdemo.R;
import com.example.admin.arouterdemo.bean.User;
import com.example.admin.arouterdemo.utils.SPUtil;
import com.example.admin.arouterdemo.utils.ToastUtil;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：js and android interact
 * <p>
 * create_time：2018/7/26 17:40
 * update_by：
 * update_time:
 */

@Route(path = "/activity/js")
public class JSActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.js_btn)
    Button jsBtn;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.js_btn, R.id.webview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.js_btn:
                initWebview();
                break;
            case R.id.webview:

                break;
        }
    }


    private void initWebview(){
        User user = new User("zhangsan", "123");
        String str = JSON.toJSONString(user);
        final JSONObject obj = JSON.parseObject(str);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true); // 为WebView使能JavaScript
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webview.addJavascriptInterface(new JSInterface(), "jsdemo");
        webview.loadUrl("http://10.50.12.18:8080/#/");
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webview.evaluateJavascript("javascript:test(" + obj + ")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
//                ToastUtil.showShort(JSActivity.this, value);
                    }
                });
            }
        });

        webview.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JSActivity.this);
                builder.setTitle("提示");
                builder.setMessage(message);//这个message就是alert传递过来的值
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //处理确定按钮了，且通过jsresult传递，告诉js点击的是确定按钮
                        result.confirm();
                    }
                });
                builder.show();
                //自己处理
                return true;
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            // 认证成功，通知 js页面
            String flag = "1";
            webview.evaluateJavascript("javascript:isSuccess(" + flag + ")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                }
            });
        }

    }

    class JSInterface{
        @JavascriptInterface
        public String dataFromAndroid(){
            User user = new User("zhangsan", "123");
            String str = JSON.toJSONString(user);
            ToastUtil.showShort(getApplicationContext(), str);
            return str;
        }

        @JavascriptInterface
        public String getUserName(){
            return "zhangsan";
        }

        @JavascriptInterface
        public String getUserPsw(){
            return "123";
        }

        @JavascriptInterface
        public void saveUser(String name, String psw){
            ToastUtil.showShort(getApplicationContext(), name + "=====" + psw);
        }

        @JavascriptInterface
        public void vertify(){
            ARouter.getInstance().build("/hello/activity").navigation(JSActivity.this, 1000);
        }

    }




}
