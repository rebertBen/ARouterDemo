package com.example.admin.arouterdemo.view.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.admin.arouterdemo.R;
import com.example.admin.arouterdemo.adapter.PhotoGridViewAdapter;
import com.example.admin.arouterdemo.dialog.PopuWindowShowPhoto;
import com.example.admin.arouterdemo.dialog.SelectPhotoDialog;
import com.example.admin.arouterdemo.utils.AlbumUtil;
import com.example.admin.arouterdemo.utils.ToastUtil;
import com.example.admin.arouterdemo.view.photo.adapter.SelectPhotoRecyclerAdapter;
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
 * create_time：2018/9/7 10:53
 * update_by：
 * update_time:
 */
@Route(path = "/activity/photo")
public class PhotoSelectActivity extends AppCompatActivity implements PhotoGridViewAdapter.PhotoInterface {
    private static final int IMG_SIZE = 9;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<AlbumFile> pathList = new ArrayList<>();
    private SelectPhotoRecyclerAdapter adapter;

    private String takePhotoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photos);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new SelectPhotoRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SelectPhotoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                showImage(list, pos);
            }

            @Override
            public void imgDelete(int pos) {
                ivDelete(pos);
            }
        });
    }

    private void showImage(ArrayList<String> list, int pos) {
        new PopuWindowShowPhoto()
                .showWindow(this, list, pos);
    }

    @OnClick(R.id.select_btn)
    public void onViewClicked() {
        if (list.size() >= IMG_SIZE){
            ToastUtil.showShort(this, "最多选择" + IMG_SIZE + "张图片");
            return;
        }

        SelectPhotoDialog.getInstance()
                .showDialog(this, "图片选择", 100);
    }

    public void getFlag(int item) {
        if (item == 0) {
            AlbumUtil.getInstance().getCameraType(this, false, 100, 9 - (list.size() - pathList.size()));
        } else if (item == 1) {
            AlbumUtil.getInstance().getCameraType(this, true, 100, 9 - (list.size() - pathList.size()));
        }
    }

    /**
     * 相册数据返回
     *
     * @param tag          true表示相册，false表示拍照
     * @param pathList
     * @param takePhotoUrl
     */
    public void getImgUrl(boolean tag, ArrayList<AlbumFile> pathList, String takePhotoUrl) {
        this.pathList = pathList;
        this.takePhotoUrl = takePhotoUrl;
        List<String> template = new ArrayList<>();
        template.addAll(list);
        if (null != pathList) {
            if (tag) {
                for (int i = 0, len = this.pathList.size(); i < len; i++) {
                    String url = "file:///" + pathList.get(i).getPath();
                    if (!list.contains(url))
                        list.add(url);
                }
            } else {
                list.add(this.takePhotoUrl);
            }
        }
        if (list.size() > IMG_SIZE) {
            ToastUtil.showShort(this, "最多选择" + IMG_SIZE + "张图片");
            list.clear();
            list.addAll(template);
            return;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ivDelete(int pos) {
        for (int i = 0, len = pathList.size(); i < len; i++){
            String url = list.get(pos).replace("file:///", "");
            if (pathList.get(i).getPath().equals(url)){
                pathList.remove(i);
                break;
            }
        }
        list.remove(pos);
        adapter.notifyDataSetChanged();
    }
}
