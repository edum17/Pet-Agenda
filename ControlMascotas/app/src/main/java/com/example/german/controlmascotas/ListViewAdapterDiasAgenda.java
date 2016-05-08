package com.example.german.controlmascotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by German on 07/05/2016.
 */
public class ListViewAdapterDiasAgenda extends BaseAdapter {

    Context context;
    ArrayList<Evento> evento;
    LayoutInflater inflater;
    SQLControlador dbconeccion;


    public ListViewAdapterDiasAgenda(Context context, ArrayList<Evento> evento) {
        this.context = context;
        this.evento = evento;
        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();
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
        TextView diaSemana;
        ListView eventosDia;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_dias_agenda,parent,false);

        diaSemana = (TextView) itemView.findViewById(R.id.fila_dia_diaSemana);
        eventosDia = (ListView) itemView.findViewById(R.id.listViewEventosDia);

        diaSemana.setText(evento.get(position).getNomDiaFecha());

        System.out.println("*************************** FECHA ADAPTER: " + evento.get(position).getFecha());
        final ListViewAdapterEventosDia adapterHoraEvento = new ListViewAdapterEventosDia(context, dbconeccion.listarEventosDia(evento.get(position).getFecha()));
        adapterHoraEvento.notifyDataSetChanged();
        eventosDia.setAdapter(adapterHoraEvento);


        return itemView;
    }
}
