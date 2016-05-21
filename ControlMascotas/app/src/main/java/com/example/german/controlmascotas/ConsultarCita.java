package com.example.german.controlmascotas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by German on 21/05/2016.
 */
public class ConsultarCita extends FragmentActivity {

    SQLControlador dbconeccion;
    String nombreM;
    String fechaCita;
    String horaIni;

    EditText nombreMC, fechaC, horaIniC, horaFinC, tipoC;

    ImageButton fecha, horaI, horaF, tiposC;
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
        guardarMod.setFocusable(false);

        cita = dbconeccion.consultarCita(nombreM,fechaCita,horaIni);

        nombreMC.setText(nombreM);
        nombreMC.setEnabled(false);
        fechaC.setText(fechaCita);
        fechaC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!fechaC.getText().toString().equals(cita.getFecha())) {
                    guardarMod.setEnabled(true);
                    guardarMod.setFocusable(true);
                }
            }
        });
        horaIniC.setText(horaIni);
        horaIniC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!horaIniC.getText().toString().equals(cita.getHoraIni())) {
                    guardarMod.setEnabled(true);
                    guardarMod.setFocusable(true);
                }
            }
        });
        horaFinC.setText(cita.getHoraFin());
        horaFinC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!horaFinC.getText().toString().equals(cita.getHoraFin())) {
                    guardarMod.setEnabled(true);
                    guardarMod.setFocusable(true);
                }
            }
        });
        tipoC.setText(cita.getTipo());
        tipoC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (!tipoC.getText().toString().equals(cita.getTipo())) {
                    guardarMod.setEnabled(true);
                    guardarMod.setFocusable(true);
                }
            }
        });
    }

    private String getDia(String fecha) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int dia = fechaCalendario.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(dia);
    }

    private String getMes(String fecha) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int mes = fechaCalendario.get(Calendar.MONTH);
        if (mes == 0) return "1";
        else if (mes == 1) return "2";
        else if (mes == 2) return "3";
        else if (mes == 3) return "4";
        else if (mes == 4) return "5";
        else if (mes == 5) return "6";
        else if (mes == 6) return "7";
        else if (mes == 7) return "8";
        else if (mes == 8) return "9";
        else if (mes == 9) return "10";
        else if (mes == 10) return "11";
        else return "12";
    }

    private String getAny(String fecha) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int year = fechaCalendario.get(Calendar.YEAR);
        return Integer.toString(year);
    }

    public void updateCita() {
        dbconeccion.eliminarCita(nombreM,fechaCita,horaIni);
        Cita e = new Cita();
        e.setNom(nombreM);
        e.setFecha(fechaC.getText().toString());
        e.setDiaC(getDia(fechaC.getText().toString()));
        e.setMesC(getMes(fechaC.getText().toString()));
        e.setAnyC(getAny(fechaC.getText().toString()));
        e.setHoraIni(horaIniC.getText().toString());
        e.setHoraFin(horaFinC.getText().toString());
        e.setTipo(tipoC.getText().toString());
        if (dbconeccion.insertarCita(e)) {
            dbconeccion.cerrar();
            Intent main = new Intent(this,MainActivity.class);
            startActivity(main);

        } else {
            String res = "La mascota " + nombreM + " tiene una cita el día " + fechaC.getText().toString() + " a la misma hora";
            Toast.makeText(this, res , Toast.LENGTH_SHORT).show();

        }
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
