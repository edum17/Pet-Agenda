package com.example.german.controlmascotas;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by German on 01/04/2016.
 */
public class NuevaMascota extends Fragment {

    Context c;
    SQLControlador dbconeccion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        c = container.getContext();
        dbconeccion = new SQLControlador(c);
        dbconeccion.abrirBaseDatos();
        View rootView = inflater.inflate(R.layout.lay_nueva_mascota, container, false);
        return rootView;
    }
}
