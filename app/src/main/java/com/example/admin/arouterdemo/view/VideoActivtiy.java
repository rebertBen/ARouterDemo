package com.example.admin.arouterdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.admin.arouterdemo.R;
import com.example.admin.arouterdemo.utils.MessageObservable;
import com.example.admin.arouterdemo.utils.StatusObservable;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.av.sdk.AVContext;
import com.tencent.av.sdk.AVError;
import com.tencent.av.sdk.AVRoomMulti;
import com.tencent.av.sdk.AVView;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveMemStatusLisenter;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.adapter.CommonConstants;
import com.tencent.ilivesdk.adapter.avsdk_impl.AVSDKContext;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.core.ILiveRoomOption;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.ilivesdk.view.BaseVideoView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.livesdk.ILVText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author bobo
 * <p>
 * function：实时直播
 * <p>
 * create_time：2018/8/8 10:01
 * update_by：
 * update_time:
 */
@Route(path = "/activity/video")
public class VideoActivtiy extends AppCompatActivity implements ILiveLoginManager.TILVBStatusListener,
        ILVLiveConfig.ILVLiveMsgListener {


    @BindView(R.id.av_view)
    AVRootView avView;
    @BindView(R.id.tv)
    TextView tv;

    private int appId = 1400122597;
    private int accoutype = 33334;
    private String username = "webrtc95";
    private String sig = "eJxNj11PgzAUhv*K6bUxbSnBLvGCfaCYLSobxO2GFFrcyRyU0k2Z8b9bCTGeu-M8ec-HF9os1zeiLJtTbXPba4UmCKPrAYNUtYUKlHHwQxXGltwfndAaZC5s7hn5L9LJQz4oxwjDmFDq82CU6lODUbmo7DDRw38peHP9apHO4tnTQ8-ZuWfm4kX3LF5lUdb6zbKCvTpQzuKdaOS0KE42CWERYgl9eEyzttV8R7avadldnu383ep9cvuC6bSVUbR5zNqkuRuXWTj*Pkl8zwsoDggZ*VmZDpoaTa4QxcQn1N3nCn3-AN2FWGM_";
    private int roomId = 100905;
    private String mMasterId = "webrtc96";

    private AVSDKContext mAVSDKContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);
        ButterKnife.bind(this);

        tv.setText(" iLiveSDK: "+ILiveSDK.getInstance().getVersion()+"\n IMSDK:"+
                TIMManager.getInstance().getVersion()+"\n AVSDK:"+
                AVContext.sdkVersion);

//        initSDK();
    }

    private void initSDK() {
        //iLiveSDK 初始化
        ILiveSDK.getInstance().initSdk(this, appId, accoutype);
        //初始化直播场景
        ILVLiveConfig liveConfig = new ILVLiveConfig();
        ILVLiveManager.getInstance().init(liveConfig);

        login();
    }

    private void login() {
        ILiveLoginManager.getInstance().iLiveLogin(username, sig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                initVideo();
                createRoom();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Log.e("video", module + "-----" + errCode + " ------" + errMsg);
            }
        });
    }

    private void initVideo() {
        ILVLiveManager.getInstance().setAvVideoView(avView);
        MessageObservable.getInstance().addObserver(this);
        StatusObservable.getInstance().addObserver(this);
        mAVSDKContext = (AVSDKContext) ILiveSDK.getInstance().getContextEngine();
        avView.setAutoOrientation(true);
        avView.setSubCreatedListener(new AVRootView.onSubViewCreatedListener() {
            @Override
            public void onSubViewCreated() {
                // 打开摄像头预览
                ILiveRoomManager.getInstance().enableCamera(ILiveConstants.FRONT_CAMERA, true);
            }
        });

    }

    private void createRoom() {
        ILVLiveRoomOption mLiveRoomOption = new ILVLiveRoomOption(username)
                .controlRole("LiveMaster")//角色设置
                .authBits(AVRoomMulti.AUTH_BITS_DEFAULT)//权限设置
                .cameraId(ILiveConstants.FRONT_CAMERA)//摄像头前置后置
                .videoRecvMode(AVRoomMulti.VIDEO_RECV_MODE_SEMI_AUTO_RECV_CAMERA_VIDEO);//是否开始半自动接收
        mLiveRoomOption.autoRender(false);
        mLiveRoomOption.setRoomMemberStatusLisenter(liveMemStatusLisenter);
        mLiveRoomOption.setRequestViewLisenter(requestViewListener);

        ILVLiveManager.getInstance().createRoom(roomId, mLiveRoomOption, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                requestVideo();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if (module.equals(ILiveConstants.Module_IMSDK) && 10021 == errCode) {
                    // 被占用，改加入
                    joinRoom();
                }
            }
        });
    }

    //  加入房间
    private void joinRoom() {
        ILVLiveRoomOption mLiveRoomOption = new ILVLiveRoomOption("")
                .autoCamera(ILiveConstants.NONE_CAMERA != ILiveRoomManager.getInstance().getActiveCameraId())
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .controlRole("Guest")
//                .audioCategory(ILiveConstants.TYPE_MEMBER_CHANGE_HAS_AUDIO)
                .autoFocus(true)
                .autoMic(true);
        mLiveRoomOption.autoRender(false);
        mLiveRoomOption.setRoomMemberStatusLisenter(liveMemStatusLisenter);
        mLiveRoomOption.setRequestViewLisenter(requestViewListener);

        ILVLiveManager.getInstance().joinRoom(roomId, mLiveRoomOption, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                requestVideo();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Log.e("video", "---join room fail-module---kill--- " + module + " ----errCode-- " + errCode + "---errorMsg---" + errMsg);
                finish();
            }
        });
    }

    private ILiveMemStatusLisenter liveMemStatusLisenter = new ILiveMemStatusLisenter() {
        @Override
        public boolean onEndpointsUpdateInfo(int eventid, String[] updateList) {
            Log.e("video", "[eventid=]" + eventid + "-----" + "[updateList=]" + updateList.toString());
            requestVideo();
            return false;
        }
    };

    /**
     * 请求视频
     */
    private void requestVideo() {
        ILiveRoomManager.getInstance().enableCamera(ILiveConstants.FRONT_CAMERA, true);
        mAVSDKContext.releaseUserVideoData();
        mAVSDKContext.releaseUserAudioData();
        mAVSDKContext.requestUserVideoData(mMasterId, CommonConstants.Const_VideoType_Camera);
    }

    /**
     * 请求画面接口回调
     */
    private ILiveRoomOption.onRequestViewListener requestViewListener = new ILiveRoomOption.onRequestViewListener() {

        @Override
        public void onComplete(String[] identifierList, AVView[] viewList, int count, int result, String errMsg) {
            if (result != AVError.AV_OK) {
                return;
            }
            //设置当事人位置
            avView.bindIdAndView(0, CommonConstants.Const_VideoType_Camera, mMasterId, true);
            avView.getViewByIndex(0).setSameDirectionRenderMode(BaseVideoView.BaseRenderMode.BLACK_TO_FILL);
            avView.bindIdAndView(1, CommonConstants.Const_VideoType_Camera, null);
        }
    };


    @Override
    public void onForceOffline(int error, String message) {

    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }
}
