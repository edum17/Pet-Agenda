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
public class ListViewAdapterDiasAgenda extends BaseAdapter {

    Context context;
    ArrayList<DiaFechaAgenda> diaFechaAgenda;
    LayoutInflater inflater;


    public ListViewAdapterDiasAgenda(Context context, ArrayList<DiaFechaAgenda> diaFechaAgenda) {
        this.context = context;
        this.diaFechaAgenda = diaFechaAgenda;
    }

    @Override
    public int getCount() {
        return diaFechaAgenda.size();
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
        TextView diaSemana,fecha;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_dias_agenda,parent,false);

        diaSemana = (TextView) itemView.findViewById(R.id.fila_dia_diaSemana);
        fecha = (TextView) itemView.findViewById(R.id.fila_dia_fecha);

        diaSemana.setText(diaFechaAgenda.get(position).getDiaSemana());
        fecha.setText(diaFechaAgenda.get(position).getFecha());

        return itemView;
    }
}
