package com.example.german.controlmascotas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;


/**
 * Created by German on 01/04/2016.
 */
public class ListaMascotas extends Fragment {

    SQLControlador dbconeccion;
    ListView lista;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lay_lista_mascotas, container, false);

        context = rootView.getContext();
        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();

        lista = (ListView) rootView.findViewById(R.id.listViewMascotas);

        listarMascotas();

        return rootView;
    }

    private void listarMascotas() {
        final ListViewAdapterMascotas adapter = new ListViewAdapterMascotas(context,dbconeccion.listarMascotas());
        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {"Eliminar mascota", "Modificar mascota"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Mascota");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Eliminar mascota")) {
                            String nomC = adapter.getItemName(position);
                            String identficador = Long.toString(adapter.getItemId(position));
                            dbconeccion.eliminarMascotaYCitas(Integer.parseInt(identficador));
                            Toast.makeText(context, "La mascota " + nomC + " ha sido eliminada", Toast.LENGTH_SHORT).show();
                            adapter.updateAdapter(dbconeccion.listarMascotas());
                        }
                        else if (items[which].equals("Modificar mascota")) {
                            Intent consultar_mascota = new Intent(context,ConsultarMascota.class);
                            String identficador = Long.toString(adapter.getItemId(position));
                            consultar_mascota.putExtra("idMascota", identficador);
                            getActivity().startActivity(consultar_mascota);
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
    }
}