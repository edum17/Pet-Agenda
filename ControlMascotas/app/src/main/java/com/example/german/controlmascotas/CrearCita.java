package com.example.german.controlmascotas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by German on 10/05/2016.
 */
public class CrearCita extends FragmentActivity {

    Context context;
    SQLControlador dbconeccion;


    TextView nombre, fechaC, horaIni, horaFin, tipoC;
    ImageButton nombresM,fecha, horaI, horaF, tiposC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_crear_cita);

        context = this;
        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();

        //TextView
        nombre = (TextView) findViewById(R.id.textViewNombreM);
        fechaC = (TextView) findViewById(R.id.textViewFechaCita);
        horaIni = (TextView) findViewById(R.id.textViewHoraIni);
        horaFin = (TextView) findViewById(R.id.textViewHoraFin);
        tipoC = (TextView) findViewById(R.id.textViewTipoE);

        //Buttons
        nombresM = (ImageButton) findViewById(R.id.butNombreM);
        nombresM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarAnimales();
            }
        });

        fecha = (ImageButton) findViewById(R.id.butFechaCita);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirFecha();
            }
        });

        horaI = (ImageButton) findViewById(R.id.butHoraIni);
        horaI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirHora();
            }
        });

        horaF = (ImageButton) findViewById(R.id.butHoraFin);
        horaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirHora();
            }
        });

        tiposC = (ImageButton) findViewById(R.id.butTipoC);
        tiposC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirTipoCita();
            }
        });
    }

    public void listarAnimales() {
        //Toast.makeText(getActivity(), "Listo todos los tipos de animales", Toast.LENGTH_SHORT).show();
        final ArrayList<String> tipoM = dbconeccion.listarNombresMascotas();

        //Creacion de sequencia de items
        final CharSequence[] Animals = tipoM.toArray(new String[tipoM.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Mascotas");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String animalSel = Animals[item].toString();  //Selected item in listview
                nombre.setText(animalSel);
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //Creacion del objeto alert dialog via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Mostramos el dialog
        alertDialogObject.show();
    }

    public void anadirFecha() {
        //Toast.makeText(getActivity(), "Funciona", Toast.LENGTH_SHORT).show();
        Calendar mcurrentDate=Calendar.getInstance();
        int mYear=mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker=new DatePickerDialog(CrearCita.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                String date = selectedday + "/" + (selectedmonth+1) + "/" + selectedyear;
                TextView textViewFechaC = (TextView) findViewById(R.id.textViewFechaCita);
                textViewFechaC.setText(date);
            }
        },mYear, mMonth, mDay);
        mDatePicker.show();
    }

    public void anadirHora() {

    }

    public void anadirTipoCita() {

    }
}
