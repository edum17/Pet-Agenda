package com.example.german.controlmascotas;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * Created by German on 01/04/2016.
 */
public class Mascotas extends Fragment {

    SQLControlador dbconeccion;
    ListView lista;
    Context c;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lay_mascotas, container, false);

        c = container.getContext();
        dbconeccion = new SQLControlador(c);
        dbconeccion.abrirBaseDatos();

        lista = (ListView) rootView.findViewById(R.id.listViewMascotas);

        listarMascotas();

        return rootView;
    }

    private void listarMascotas() {
        final ListViewAdapterMascotas adapter = new ListViewAdapterMascotas(c,dbconeccion.listarMascotas());
        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);
    }
}
