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
 * Created by German on 01/04/2016.
 */
public class ListViewAdapterMascotas extends BaseAdapter {

    Context context;
    ArrayList<Mascota> mascotas;
    LayoutInflater inflater;

    public ListViewAdapterMascotas(Context context,ArrayList<Mascota> mascotas) {
        this.context = context;
        this.mascotas = mascotas;
    }

    @Override
    public int getCount() {
        return mascotas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return mascotas.get(position).getId();
    }

    public String getItemName(int position) {
        return mascotas.get(position).getNombre();
    }

    public void updateAdapter(ArrayList<Mascota> arrylst) {
        mascotas = arrylst;
        notifyDataSetChanged();
    }

    public String getItemType(int position) {
        return mascotas.get(position).getTipo();
    }

    public String getItemDate(int position) {
        return mascotas.get(position).getFechaNac();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtNombre, txtTipo, txtFechaNac, txtNXip;
        ImageView imgImagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_lista_mascotas,parent,false);

        txtNombre = (TextView) itemView.findViewById(R.id.fila_nombre);
        txtTipo = (TextView) itemView.findViewById(R.id.fila_tipo);
        txtFechaNac = (TextView) itemView.findViewById(R.id.fila_fecha_nac);
        txtNXip = (TextView) itemView.findViewById(R.id.fila_numxip);
        imgImagen = (ImageView) itemView.findViewById(R.id.fila_imagen);

        txtNombre.setText(mascotas.get(position).getNombre());
        txtTipo.setText(mascotas.get(position).getTipo());
        txtFechaNac.setText(mascotas.get(position).getFechaNac());
        txtNXip.setText(mascotas.get(position).getNXip());

        String dir = mascotas.get(position).getPath();


        //System.out.println("*************************** dir: " + dir);
        //System.out.println("*************************** dir.equals(default): " + dir.equals("default"));
        if(dir.equals("default")) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.img_def_00);
            //imgImagen.setBackgroundResource(R.mipmap.img_def_00);
            imgImagen.setImageBitmap(bitmap);
        }
        else {
            Bitmap bitmap;
            bitmap = BitmapFactory.decodeFile(dir);
            imgImagen.setImageBitmap(bitmap);
        }

        return itemView;
    }
}
