package com.example.german.controlmascotas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by German on 26/05/2016.
 */
public class ListViewAdapterNavDrawer extends BaseAdapter {

    private Context context;
    private String[] siteNames;
    private int[] images;
    private LayoutInflater inflater;



    public ListViewAdapterNavDrawer(Context context, String[] siteNames, int[] images) {
        this.context = context;
        this.siteNames = siteNames;
        this.images = images;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.formato_fila_nav_draw_item, null);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.text_section);
        mViewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.image_section);
        mViewHolder.tvTitle.setText(siteNames[position]);
        mViewHolder.ivIcon.setImageResource(images[position]);

        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }
}