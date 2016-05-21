package com.example.german.controlmascotas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by German on 21/05/2016.
 */
public class ConsultarCita extends FragmentActivity {

    SQLControlador dbconeccion;
    String nombreM;
    String fechaCita;
    String horaIni;

    EditText nombreMC, fechaC, horaIniC, horaFinC, tipoC;

    ImageButton nombresM,fecha, horaI, horaF, tiposC;
    Button guardarMod, cancel;

    Cita cita;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_consultar_cita);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDatos();
        nombreM = getIntent().getStringExtra("nombreM");
        fechaCita = getIntent().getStringExtra("fecha");
        horaIni = getIntent().getStringExtra("horaIni");


        nombreMC = (EditText) findViewById(R.id.editTextNombreMC);
        fechaC = (EditText) findViewById(R.id.editTextFechaCita);
        horaIniC = (EditText) findViewById(R.id.editTextHoraIniC);
        horaFinC = (EditText) findViewById(R.id.editTextHoraFinC);
        tipoC = (EditText) findViewById(R.id.editTextTipoC);

        guardarMod = (Button) findViewById(R.id.butGuardarMod);
        guardarMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCita();
            }
        });
        cancel = (Button) findViewById(R.id.butCancelaConsulta);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dbconeccion.cerrar();
            }
        });

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
                anadirHoraIni();
            }
        });

        horaF = (ImageButton) findViewById(R.id.butHoraFin);
        horaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirHoraFin();
            }
        });

        tiposC = (ImageButton) findViewById(R.id.butTipoC);
        tiposC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirTipoCita();
            }
        });

        //Boton desactivado
        guardarMod.setEnabled(false);

        cita = dbconeccion.consultarCita(nombreM,fechaCita,horaIni);

        nombreMC.setText(nombreM);
        fechaC.setText(fechaCita);
        horaIniC.setText(horaIni);
        horaFinC.setText(cita.getHoraFin());
        tipoC.setText(cita.getTipo());
    }

    private void updateCita() {
        if (!nombreMC.getText().toString().equals(cita.getNom()) ||
                !fechaC.getText().toString().equals(cita.getFecha()) ||
                !horaIniC.getText().toString().equals(cita.getHoraIni()) ||
                !horaFinC.getText().toString().equals(cita.getHoraFin()) ||
                !tipoC.getText().toString().equals(cita.getTipo())) {
            guardarMod.setEnabled(true);
        }
    }

    public void listarAnimales() {
        //Toast.makeText(getActivity(), "Listo todos los tipos de animales", Toast.LENGTH_SHORT).show();
        final ArrayList<String> tipoM = dbconeccion.listarNombresMascotas();

        //Creacion de sequencia de items
        final CharSequence[] Animals = tipoM.toArray(new String[tipoM.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Mascotas");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String animalSel = Animals[item].toString();  //Selected item in listview
                nombreMC.setText(animalSel);
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
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker=new DatePickerDialog(ConsultarCita.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                String date = selectedday + "/" + (selectedmonth+1) + "/" + selectedyear;
                fechaC.setText(date);
            }
        },mYear, mMonth, mDay);
        mDatePicker.show();
    }

    public void anadirHoraIni() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = "";
                if (hourOfDay < 10) hora += "0" + hourOfDay + ":";
                else hora += hourOfDay + ":";
                if (minute < 10) hora += "0" + minute;
                else hora += minute;
                horaIniC.setText(hora);
            }
        },hour,minute,false);
        mTimePicker.show();
    }

    public void anadirHoraFin() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = "";
                if (hourOfDay < 10) hora += "0" + hourOfDay + ":";
                else hora += hourOfDay + ":";
                if (minute < 10) hora += "0" + minute;
                else hora += minute;
                horaFinC.setText(hora);
            }
        },hour,minute,false);
        mTimePicker.show();
    }

    public void anadirTipoCita() {
        final ArrayList<String> tipoM = dbconeccion.listarTiposCitas();

        //Creacion de sequencia de items
        final CharSequence[] tiposEventos = tipoM.toArray(new String[tipoM.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Tipo de cita");
        dialogBuilder.setItems(tiposEventos, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String eventoSel = tiposEventos[item].toString();  //Selected item in listview
                tipoC.setText(eventoSel);
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
}
