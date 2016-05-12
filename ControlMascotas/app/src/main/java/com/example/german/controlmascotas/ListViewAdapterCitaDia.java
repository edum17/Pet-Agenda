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

    Context context;
    ArrayList<Cita> cita;
    LayoutInflater inflater;


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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView horaIni, horaFin,nomEvento;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_eventos_dia,parent,false);

        horaIni =  (TextView) itemView.findViewById(R.id.fila_evento_horaIni);
        horaFin =  (TextView) itemView.findViewById(R.id.fila_evento_horaFin);
        nomEvento = (TextView) itemView.findViewById(R.id.fila_evento_evento);

        horaIni.setText(cita.get(position).getHoraIni());
        horaFin.setText(cita.get(position).getHoraFin());
        nomEvento.setText(cita.get(position).getNomMascotaTipoE());

        return itemView;
    }
}