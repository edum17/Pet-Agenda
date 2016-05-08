package com.example.german.controlmascotas;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by German on 01/04/2016.
 */
public class Agenda extends Fragment {

    Context context;
    SQLControlador dbconeccion;
    ListView listaDia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();
        View rootView = inflater.inflate(R.layout.lay_agenda, container, false);

        listaDia = (ListView) rootView.findViewById(R.id.listViewAgenda);

        listarEventos();

        return rootView;
    }

    private void listarEventos() {
        final ListViewAdapterDiasAgenda adapterDiaFecha = new ListViewAdapterDiasAgenda(context, dbconeccion.listarDiasAgenda());
        adapterDiaFecha.notifyDataSetChanged();
        listaDia.setAdapter(adapterDiaFecha);
    }
}
