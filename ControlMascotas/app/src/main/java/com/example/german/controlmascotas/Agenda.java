package com.example.german.controlmascotas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.logging.Handler;

/**
 * Created by German on 01/04/2016.
 */
public class Agenda extends Fragment {

    Context context;
    SQLControlador dbconeccion;
    ListView listaDia;
    Button crearcita;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();
        rootView = inflater.inflate(R.layout.lay_agenda, container, false);

        listaDia = (ListView) rootView.findViewById(R.id.listViewAgenda);
        crearcita = (Button) rootView.findViewById(R.id.butCrearCita);
        crearcita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCita();
            }
        });

        listarCitas();

        return rootView;
    }

    public void listarCitas() {
        final ListViewAdapterDiasAgenda adapterDiaFecha = new ListViewAdapterDiasAgenda(context, dbconeccion.listarDiasAgenda());
        adapterDiaFecha.notifyDataSetChanged();
        listaDia.setAdapter(adapterDiaFecha);
    }

    private void crearCita() {
        //Singleton.getInstance().setContext(context);
        //Singleton.getInstance().setContainerId(((ViewGroup)getView().getParent()).getId());
        //System.out.println("*************************** ((ViewGroup)getView().getParent()).getId(): " + ((ViewGroup)getView().getParent()).getId());
        Intent creaCita = new Intent(context,CrearCita.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(creaCita);
    }
}
