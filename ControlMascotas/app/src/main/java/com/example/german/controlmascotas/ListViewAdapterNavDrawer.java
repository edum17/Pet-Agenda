package com.example.german.controlmascotas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    Context context;
    ArrayList<itemNavDrawer> items;
    LayoutInflater inflater;


    public ListViewAdapterNavDrawer(Context context, ArrayList<itemNavDrawer> items) {
        this.context = context;
        this.items = items;
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
        TextView section_text;
        ImageView section_image;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_nav_draw_item,parent,false);

        section_image = (ImageView) itemView.findViewById(R.id.image_section);
        section_text = (TextView) itemView.findViewById(R.id.text_section);

        section_image.setBackgroundResource(items.get(position).getIcon());

        section_text.setText(items.get(position).getTitle());

        return itemView;
    }
}
