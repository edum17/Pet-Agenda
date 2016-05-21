package com.example.german.controlmascotas;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by German on 30/04/2016.
 */
public class ConsultarMascota extends FragmentActivity {

    String nombreM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_consultar_mascota);

        nombreM = getIntent().getStringExtra("nombreM");

    }

    public void updateMascota() {}
}