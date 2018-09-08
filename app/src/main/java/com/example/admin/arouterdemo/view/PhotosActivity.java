package com.example.admin.arouterdemo.view;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.admin.arouterdemo.R;
import com.example.admin.arouterdemo.utils.GlideImageLoader;
import com.example.admin.arouterdemo.utils.MediaLoader;
import com.example.admin.arouterdemo.utils.PerimissionsUtil;
import com.example.admin.arouterdemo.utils.PopuWindowCenterUtil;
import com.example.admin.arouterdemo.utils.ToastUtil;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bobo
 * <p>
 * function：图片选择
 * <p>
 * create_time：2018/8/7 10:02
 * update_by：
 * update_time:
 */
@Route(path = "/activity/photo")
public class PhotosActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_url)
    TextView tvUrl;

    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private ArrayList<AlbumFile> pathList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);

        initPermission();
    }

    private void initPermission() {
        PerimissionsUtil.startRequestPermission(this, permissions);
        // 图片选择器初始化
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader()).build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (!PerimissionsUtil.isPermissionGranted(this, permissions[i])) {
                PerimissionsUtil.showPermissionDialog(this);
            }
        }
    }

    @OnClick({R.id.upload_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.upload_btn:
                showDialog();
                break;
        }
    }

    private List<String> strList = new ArrayList<>();

    private void showDialog() {
        if (strList.size() > 0)
            strList.clear();
        strList.add("拍照");
        strList.add("相册选择");

        new PopuWindowCenterUtil().showWindow(PhotosActivity.this, strList, " 选择照片");
    }

    public void getFlag(int tag) {
        if (tag == 1) {
            Album.image(this)
                    .multipleChoice()
                    .camera(true)
                    .selectCount(9)
                    .checkedList(pathList)
                    .afterFilterVisibility(true)
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            pathList = result;
//                            image.setImageURI(Uri.parse(result.get(0).getPath()));
                            new GlideImageLoader().displayImage(PhotosActivity.this, result.get(0).getPath(), image);
                            getImgUrl();
                        }
                    }).onCancel(new Action<String>() {
                @Override
                public void onAction(@NonNull String result) {

                }
            }).start();
        } else if (tag == 0) {
            Album.camera(this) // Camera function.
                    .image() // Take Picture.
//                    .filePath() // File save path, not required.
                    .onResult(new Action<String>() {
                        @Override
                        public void onAction(@NonNull String result) {
                            if (!pathList.contains(result)) {
                                AlbumFile album = new AlbumFile();
                                album.setPath(result);
                                pathList.add(album);
                            }
                            ToastUtil.showShort(PhotosActivity.this, result);
                            image.setImageURI(Uri.parse(result));
                            getImgUrl();
                        }
                    })
                    .onCancel(new Action<String>() {
                        @Override
                        public void onAction(@NonNull String result) {
                        }
                    })
                    .start();
        }

    }

    /**
     * list清除
     */
    private void clearList() {
        if (null == pathList) {
            pathList = new ArrayList<>();
        } else if (pathList.size() > 0) {
            pathList.clear();
        }
    }

    private void getImgUrl() {
        if (null != pathList) {
            ToastUtil.showShort(this, "图片个数：" + pathList.size());
            StringBuilder sb = new StringBuilder();
            for (int i = 0, len = pathList.size(); i < len; i++) {
                String url = pathList.get(i).getPath();
                url = url.substring(url.lastIndexOf("/") + 1);
                if (i == len - 1) {
                    sb.append(url);
                } else {
                    sb.append(url).append(";\n");
                }
            }
            tvUrl.setText(sb.toString());
        }
    }

}
