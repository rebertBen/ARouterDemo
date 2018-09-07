package com.example.admin.arouterdemo.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.admin.arouterdemo.R;
import com.tencent.cloud.cameralib.ICamcorder;
import com.tencent.cloud.cameralib.impl.CamcorderImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：视频录制
 * <p>
 * create_time：2018/7/30 19:46
 * update_by：
 * update_time:
 */
@Route(path = "/activity/record")
public class RecordVideoActivity extends AppCompatActivity {
    public static final String INTENT_KEY_VIDEO_PATH = "INTENT_KEY_VIDEO_PATH";

    @BindView(R.id.surface_view)
    TextureView surfaceView;
    @BindView(R.id.button_capture)
    Button mBtn;
    @BindView(R.id.use_video)
    Button useVideo;

    private ICamcorder mCamcorder;
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;
    private static final int REQUEST_PERMISSION_MIC_CODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        if (hasCameraPermission() && hasMicPermission()) {
            init();
        }
    }

    private boolean hasCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA_CODE);
                return false;
            }
        }
        return true;
    }

    private void init() {
        mCamcorder = new CamcorderImpl();
        mCamcorder.init(surfaceView);
        mCamcorder.setOnStateListener(new ICamcorder.OnStateListener() {
            @Override
            public void onCamcorderPrepared() {
                Toast.makeText(RecordVideoActivity.this, "Ready", Toast.LENGTH_SHORT).show();
                if (mBtn.isPressed()) {
                    mCamcorder.startRecord();
                }
            }

            @Override
            public void onLog(String tag, String msg) {
                Log.d(tag, msg);
            }

            @Override
            public void onAnythingFailed(Exception e) {
//                Toast.makeText(RecordVideoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });


        mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                    mCamcorder.prepareAsync();
                } else if (action == MotionEvent.ACTION_UP) {
                    mCamcorder.stopRecord();
                    v.setPressed(false);
                } else if (action == MotionEvent.ACTION_OUTSIDE || action == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }
                return true;
            }
        });

        mCamcorder.prepareAsync();
    }


    private boolean hasMicPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_MIC_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE
                || requestCode == REQUEST_PERMISSION_MIC_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initView();
            }
        }
    }


    @OnClick({R.id.button_capture, R.id.use_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_capture:
                break;
            case R.id.use_video:
                Intent intent = getIntent();
                Bundle b = new Bundle();
                b.putString(INTENT_KEY_VIDEO_PATH, mCamcorder.getVideoFile().getAbsolutePath());
                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
