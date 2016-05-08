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
public class ListViewAdapterEventosDia  extends BaseAdapter {

    Context context;
    ArrayList<Evento> evento;
    LayoutInflater inflater;


    public ListViewAdapterEventosDia(Context context, ArrayList<Evento> evento) {
        this.context = context;
        this.evento = evento;
    }

    @Override
    public int getCount() {
        return evento.size();
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

        horaIni.setText(evento.get(position).getHoraIni());
        horaFin.setText(evento.get(position).getHoraFin());
        nomEvento.setText(evento.get(position).getNomMascotaTipoE());

        return itemView;
    }
}