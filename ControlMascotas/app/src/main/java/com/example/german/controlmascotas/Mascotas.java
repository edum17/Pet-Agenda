package com.example.german.controlmascotas;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * Created by German on 01/04/2016.
 */
public class Mascotas extends Fragment {

    SQLControlador dbconeccion;
    ListView lista;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lay_mascotas, container, false);

        context = container.getContext();
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
    }
}


/*

Intent conultar_mascota = new Intent(context,ConsultarMascota.class);
                String nombre = adapter.getItemName(position);
                consultar_mascota.putExtra("nombreM",nombre);
                startActivity(conultar_mascota);
 */