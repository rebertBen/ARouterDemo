package com.example.admin.arouterdemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.arouterdemo.R;

import java.util.List;

/**
 * @author bobo
 * <p>
 * function：gridview 图片适配器
 * <p>
 * create_time：2018/9/3 11:16
 * update_by：
 * update_time:
 */
public class PhotoGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private PhotoInterface inter;

    public PhotoGridViewAdapter(Context context, List<String> list, PhotoInterface inter){
        this.context = context;
        this.list = list;
        this.inter = inter;
    }

    public void updateList(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.photo_grid_view_item, null);
            holder.img = convertView.findViewById(R.id.iv_img);
            holder.ivDelete = convertView.findViewById(R.id.iv_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list.size() == 1){
            Glide.with(context).load(Uri.parse(list.get(position)).toString()).into(holder.img);
            holder.ivDelete.setVisibility(View.GONE);
        } else if (list.size() < 9){
            if (position == list.size() -1){
                holder.ivDelete.setVisibility(View.GONE);
                Glide.with(context).load(Uri.parse(list.get(position)).toString()).into(holder.img);
            } else {
                holder.ivDelete.setVisibility(View.VISIBLE);
                Glide.with(context).load(list.get(position)).into(holder.img);
            }
        } else if (list.size() == 9){
            if (list.get(position).contains("android")){
                holder.ivDelete.setVisibility(View.GONE);
                Glide.with(context).load(Uri.parse(list.get(position)).toString()).into(holder.img);
            } else {
                holder.ivDelete.setVisibility(View.VISIBLE);
                Glide.with(context).load(list.get(position)).into(holder.img);
            }
        }

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.ivDelete(position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView img;
        ImageView ivDelete;
    }

    public interface PhotoInterface{
        /**
         * 图片删除
         */
        void ivDelete(int pos);

    }
}
