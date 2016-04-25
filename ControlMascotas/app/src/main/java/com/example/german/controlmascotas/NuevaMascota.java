package com.example.german.controlmascotas;

import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by German on 01/04/2016.
 */
public class NuevaMascota extends Fragment {

    Context c;
    SQLControlador dbconeccion;
    TextView textVFecha;
    Button butGuardarM;
    ImageButton butAnadirImg, butFechaN, butListaT;

    ImageView img;

    EditText nombre,tipo,fecha,nchip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lay_nueva_mascota, container, false);

        c = container.getContext();
        dbconeccion = new SQLControlador(c);
        dbconeccion.abrirBaseDatos();

        img = (ImageView) rootView.findViewById(R.id.image);
        img.setBackgroundResource(R.mipmap.img_def_01);

        textVFecha = (TextView) rootView.findViewById(R.id.textVFechaNac);
        nombre = (EditText) rootView.findViewById(R.id.editTNombreM);
        tipo = (EditText) rootView.findViewById(R.id.editTTipoM);
        fecha = (EditText) rootView.findViewById(R.id.editTextFechaN);
        nchip = (EditText) rootView.findViewById(R.id.editTNumChip);
        butFechaN = (ImageButton) rootView.findViewById(R.id.butFecha);
        butFechaN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate();
            }
        });

        butGuardarM = (Button) rootView.findViewById(R.id.butGuardar);
        butGuardarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Funciona",Toast.LENGTH_SHORT).show();
                addMascotaDB();
            }
        });

        butAnadirImg = (ImageButton) rootView.findViewById(R.id.butImagen);
        butAnadirImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirImagen();
            }
        });

        butListaT = (ImageButton) rootView.findViewById(R.id.butListaTipo);
        butListaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarTipoAnimales();
            }
        });

        return rootView;
    }

    public void addDate() {
        Toast.makeText(getActivity(),"Funciona",Toast.LENGTH_SHORT).show();
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getFragmentManager(),"Fecha nacimiento");
    }

    public void addMascotaDB() {
        //Comprobar si el tipo de animal se ha seleccionado de una lista o se ha añadido manualmente
        Mascota m = new Mascota(nombre.getText().toString(),tipo.getText().toString(),fecha.getText().toString(),nchip.getText().toString(),"default");
        if (dbconeccion.insertarDatos(m)) {
            dbconeccion.cerrar();
            Toast.makeText(getActivity(),"Mascota guardada",Toast.LENGTH_SHORT).show();
            clear();
        }
        else Toast.makeText(getActivity(),"Existe una mascota con ese nombre",Toast.LENGTH_SHORT).show();

    }

    public void anadirImagen() {
        Toast.makeText(getActivity(),"Llamo a la camara",Toast.LENGTH_SHORT).show();
    }

    public void listarTipoAnimales() {
        Toast.makeText(getActivity(),"Listo todos los tipos de animales",Toast.LENGTH_SHORT).show();
    }

    public void clear() {

    }

}
