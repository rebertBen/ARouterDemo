package com.example.admin.arouterdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.admin.arouterdemo.Constant;
import com.example.admin.arouterdemo.R;
import com.example.admin.arouterdemo.utils.ToastUtil;
import com.tencent.faceid.FaceIdClient;
import com.tencent.faceid.auth.CredentialProvider;
import com.tencent.faceid.exception.ClientException;
import com.tencent.faceid.exception.ServerException;
import com.tencent.faceid.model.VideoIdCardIdentityRequest;
import com.tencent.faceid.model.VideoIdCardIdentityResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：腾讯云实名认证
 * <p>
 * create_time：2018/7/30 17:57
 * update_by：
 * update_time:
 */
@Route(path = "/activity/tecent")
public class TecentVertifyActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_tip)
    EditText etTip;
    @BindView(R.id.select_btn)
    Button selectBtn;
    @BindView(R.id.video_btn)
    Button videoBtn;
    @BindView(R.id.et_tag)
    EditText etTag;
    @BindView(R.id.path_tv)
    TextView pathTv;
    @BindView(R.id.send_btn)
    Button sendBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;

    private String mVideoPath;
    private int currentRequestId;
    private FaceIdClient mFaceIdClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecent_vertify);
        ButterKnife.bind(this);
        mFaceIdClient = new FaceIdClient(getApplicationContext(), "1251414411L");
    }


    @OnClick({R.id.iv_back, R.id.select_btn, R.id.video_btn, R.id.send_btn, R.id.cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.select_btn:
                break;
            case R.id.video_btn:
                ARouter.getInstance().build("/activity/record").navigation(this, 100);
                break;
            case R.id.send_btn:
                String name = etName.getText().toString().trim();
                if (null == name || "".equals(name)){
                    ToastUtil.showShort(this, "请输入姓名");
                    return;
                }
                String num = etNumber.getText().toString().trim();
                if (null == num || "".equals(num)){ // TODO 身份证号码待检测
                    ToastUtil.showShort(this, "请正确输入身份证件号码");
                    return;
                }
                String tip = etTip.getText().toString().trim();
                if (null == tip || "".equals(tip)){
                    ToastUtil.showShort(this, "请输入唇语验证码");
                    return;
                }
                String tag = etTag.getText().toString().trim();
                String sign = getSignature();
                sendRequest(name, num, tip, mVideoPath, "ocr", tag, sign);
                break;
            case R.id.cancel_btn:
                if (0 == currentRequestId)
                    return;
                boolean success = mFaceIdClient.cancel(currentRequestId); // 通过请求ID号取消任务
                if (success){
                    ToastUtil.showShort(this, "取消成功");
                } else {
                    ToastUtil.showShort(this, "取消失败");
                }
                break;
        }
    }

    /**
     * 活体核身（通过视频和身份证信息）
     * <ol>
     *     <li>校验身份证姓名和号码是否正确</li>
     *     <li>视频活体检测</li>
     *     <li>视频与公安照片相似度检测</li>
     * </ol>
     * @param name 姓名
     * @param number 身份证号码
     * @param lip 唇语验证码
     * @param videoPath 视频文件路径
     * @param seq 请求标识，用于日志查询
     * @param sign 鉴权签名
     */
    private void sendRequest(String name, String number, String lip, String videoPath, String seq, String bucketName, String sign) {
        final VideoIdCardIdentityRequest request = new VideoIdCardIdentityRequest(bucketName, lip, videoPath, number, name, seq);
        request.setSign(sign);
        currentRequestId = request.getRequestId();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VideoIdCardIdentityResult result = mFaceIdClient.videoIdCardIdentity(request);
                    if (result != null) {
                        print(result.toString());
                    } else {
                        print("result == null");
                    }
                } catch (ClientException e) {
                    e.printStackTrace();
                    print(e.toString());
                } catch (ServerException e) {
                    e.printStackTrace();
                    print(e.toString());
                }
            }
        }).start();
    }

    private String getSignature(){
        String appid = "1251414411L";
        String secretId = "AKIDv364ddH8XNuVmKKTBIxA8J66QG8QtOY5";
        String secretKey = "fdAkeJh28hhtSSuydm1I3KI57cQ0SyPC";
        String bucket = "ocr";
        long duration = 3600; //签名有效期为3600秒

//        CredentialProvider credentialProvider = new CredentialProvider(Constant.APP_ID,
//                Constant.SECRET_ID, Constant.SECRET_KEY);
        CredentialProvider credentialProvider = new CredentialProvider(appid, secretId, secretKey);
        return credentialProvider.getMultipleSign(bucket, duration); // 生成签名
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data)
            return;
        switch (requestCode){
            case 100:
                onVideoRecordResult(data);
                break;
        }
    }

    public void onVideoRecordResult(Intent data) {
        try {
            mVideoPath = data.getStringExtra(RecordVideoActivity.INTENT_KEY_VIDEO_PATH);
            long fileSize = getFileSize(new File(mVideoPath));
            String msg = String.format("文件路径 %s 大小%s", mVideoPath, getFileSizeString(fileSize));
            pathTv.setText(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFileSizeString(long fileSize) {
        if (fileSize < 0) {
            return "无法获取文件大小";
        }
        long KB = 1024L;
        long MB = 1024 * KB;
        if (fileSize < MB) {
            return (fileSize * 1f / KB) + "KB";
        } else {
            return (fileSize * 1f / MB) + "MB";
        }
    }

    public static long getFileSize(File file) {
        long size = -1;
        if (file.exists()) {
            size = file.length();
        }
        return size;
    }

    private void print(final String msg) {
        pathTv.post(new Runnable() {
            @Override
            public void run() {
                pathTv.append(msg + "\n");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFaceIdClient.release();
    }
}
