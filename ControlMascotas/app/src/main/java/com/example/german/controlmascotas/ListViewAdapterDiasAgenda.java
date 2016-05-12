package com.example.german.controlmascotas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by German on 07/05/2016.
 */
public class ListViewAdapterDiasAgenda extends BaseAdapter {

    Context context;
    ArrayList<Cita> cita;
    LayoutInflater inflater;
    SQLControlador dbconeccion;


    public ListViewAdapterDiasAgenda(Context context, ArrayList<Cita> cita) {
        this.context = context;
        this.cita = cita;
        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();
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
        TextView diaSemana;
        ListView eventosDia;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila_dias_agenda,parent,false);

        diaSemana = (TextView) itemView.findViewById(R.id.fila_dia_diaSemana);
        eventosDia = (ListView) itemView.findViewById(R.id.listViewEventosDia);

        diaSemana.setText(cita.get(position).getNomDiaFecha());

        //System.out.println("*************************** FECHA ADAPTER: " + cita.get(position).getFecha());
        final ListViewAdapterCitaDia adapterHoraEvento = new ListViewAdapterCitaDia(context, dbconeccion.listarCitasDia(cita.get(position).getFecha()));
        adapterHoraEvento.notifyDataSetChanged();
        eventosDia.setAdapter(adapterHoraEvento);
        ListViewSinScroll.setListViewHeightBasedOnItems(eventosDia);
        eventosDia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final CharSequence[] items = {"Eliminar cita", "Modificar cita"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cita");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Eliminar cita")) {
                        }
                        else if (items[which].equals("Modificar cita")) {
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        return itemView;
    }
}
