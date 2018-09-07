package com.example.admin.arouterdemo.utils;

import android.app.Activity;
import android.content.Context;
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
 * create_time：2018/8/9 17:21
 * update_by：
 * update_time:
 */
public class PopuWindowUtil extends PopupWindow {
    private static PopupWindow popupWindow;

    public void showWindow(final Activity context) {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(context)
                .inflate(R.layout.popu_window_bottom_layout, null, false);

        popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.translate_anim);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.3f;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);

        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha=1.0f;
                context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                context.getWindow().setAttributes(lp);
            }
        });
    }

}
