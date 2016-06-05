package com.example.german.controlmascotas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by German on 07/05/2016.
 */
public class ListViewAdapterDiasAgenda extends BaseAdapter{

    private Context context;
    private ArrayList<Cita> cita;
    private LayoutInflater inflater;
    private SQLControlador dbconeccion;


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

    public void updateAdapter(ArrayList<Cita> arrylst) {
        cita = arrylst;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final TextView diaSemana;
        final ListView eventosDia;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = inflater.inflate(R.layout.formato_fila_dias_agenda,parent,false);

        diaSemana = (TextView) itemView.findViewById(R.id.fila_dia_diaSemana);
        eventosDia = (ListView) itemView.findViewById(R.id.listViewEventosDia);

        diaSemana.setText(cita.get(position).getNomDiaFecha());

        //System.out.println("*************************** FECHA ADAPTER: " + cita.get(position).getFecha());
        final String fecha = cita.get(position).getFecha();
        //if (existeCitaFecha(dbconeccion.listarDiasAgenda(),dbconeccion.listarCitasDia(fecha))) diaSemana.setText(cita.get(position).getNomDiaFecha());
        //else diaSemana.setText(null);

        final ListViewAdapterCitaDia[] adapterCitasDia = {new ListViewAdapterCitaDia(context, dbconeccion.listarCitasDia(fecha))};
        adapterCitasDia[0].notifyDataSetChanged();
        eventosDia.setAdapter(adapterCitasDia[0]);
        ListViewSinScroll.setListViewHeightBasedOnItems(eventosDia);
        eventosDia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {"Eliminar cita", "Modificar cita"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cita");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Eliminar cita")) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Eliminar cita");
                            builder.setMessage("¿Desea eliminar ésta cita?");
                            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String idMC = Long.toString(adapterCitasDia[0].getItemId(position));
                                    String horaIni = adapterCitasDia[0].getItemHoraIni(position);
                                    dbconeccion.eliminarCita(Integer.parseInt(idMC), fecha, horaIni);
                                    adapterCitasDia[0].updateAdapter(dbconeccion.listarCitasDia(fecha));
                                    updateAdapter(dbconeccion.listarDiasAgenda());
                                    Toast.makeText(context, "La cita ha sido eliminada", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();

                        } else if (items[which].equals("Modificar cita")) {
                            Intent consultar_cita = new Intent(context, ConsultarCita.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            String idMC = Long.toString(adapterCitasDia[0].getItemId(position));
                            String fecha = adapterCitasDia[0].getItemFecha(position);
                            String horaIni = adapterCitasDia[0].getItemHoraIni(position);

                            consultar_cita.putExtra("idMC", idMC);
                            consultar_cita.putExtra("fecha", fecha);
                            consultar_cita.putExtra("horaIni", horaIni);

                            context.getApplicationContext().startActivity(consultar_cita);
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
