package com.example.admin.arouterdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.admin.arouterdemo.R;

/**
 * @author bobo
 * <p>
 * function：弹窗
 * <p>
 * create_time：2018/8/10 10:43
 * update_by：
 * update_time:
 */
public class CustomPopuWindow extends PopupWindow {
    private PopupWindow popupWindow;
    private View contentView;



    /**
     * 初始化
     * @param mActivity
     * @param anim
     * @param pos
     * @param showBackground
     */
    public void showPopuWindow(final Activity mActivity, int anim, int pos, boolean showBackground) {
        contentView = LayoutInflater.from(mActivity)
                .inflate(R.layout.popu_window_center_layout, null);

        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(setAnim(anim));
        popupWindow.showAtLocation(contentView, setLocation(pos), 0, 0);
        if (!showBackground)
            return;
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mActivity.getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                mActivity.getWindow().setAttributes(lp);
            }
        });
    }

    private int setLocation(int pos){
        if (pos == 1){
            return Gravity.TOP;
        } else if (pos == 2){
            return Gravity.CENTER;
        } else {
            return Gravity.BOTTOM;
        }
    }

    private int setAnim(int tag){
        if (tag == 0){
            return R.style.translate_anim;
        } else if (tag == 1) {
            return R.style.alpha_anim;
        } else  {
            return R.style.translate_anim;
        }
    }

}
