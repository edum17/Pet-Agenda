package com.example.german.controlmascotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by German on 07/05/2016.
 */
public class ListViewAdapterCitaDia extends BaseAdapter {

    private Context context;
    private ArrayList<Cita> cita;
    private LayoutInflater inflater;


    public ListViewAdapterCitaDia(Context context, ArrayList<Cita> cita) {
        this.context = context;
        this.cita = cita;
    }

    @Override
    public int getCount() {
        return cita.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return cita.get(position).getIdMascota();
    }

    public String getItemHoraIni(int position) {
        return cita.get(position).getHoraIni();
    }

    public String getItemFecha(int position) {return cita.get(position).getFecha();}

    public void updateAdapter(ArrayList<Cita> arrylst) {
        cita = arrylst;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView horaIni, nomEvento;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_eventos_dia,parent,false);

        horaIni =  (TextView) itemView.findViewById(R.id.fila_evento_horaIni);
        nomEvento = (TextView) itemView.findViewById(R.id.fila_evento_evento);

        horaIni.setText(cita.get(position).getHoraIni());
        nomEvento.setText(cita.get(position).getNomMascotaTipoE());

        return itemView;
    }
}