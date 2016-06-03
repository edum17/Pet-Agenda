package com.example.german.controlmascotas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

    Context context;

    SQLControlador dbconeccion;
    String nombreM;
    String fechaCita;
    String horaIni;

    EditText nombreMC, horaIniC, tipoC;
    TextView fechaC;

    ImageButton fecha, horaI, tiposC;
    Button guardarMod;

    Cita cita;
    String idMascota;
    String fechaFiltro;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_consultar_cita);

        context = this;
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDatos();
        //Obtenemos el id de la mascota
        idMascota = getIntent().getStringExtra("idMC");
        fechaCita = getIntent().getStringExtra("fecha");
        horaIni = getIntent().getStringExtra("horaIni");
        //Obtenemos el nombre del id de la mascota
        nombreM = dbconeccion.getNomMascota(Integer.parseInt(idMascota));

        nombreMC = (EditText) findViewById(R.id.editTextNombreMC);
        fechaC = (TextView) findViewById(R.id.editTextFechaCita);
        horaIniC = (EditText) findViewById(R.id.editTextHoraIniC);
        tipoC = (EditText) findViewById(R.id.editTextTipoC);

        guardarMod = (Button) findViewById(R.id.butGuardarMod);
        guardarMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCita();
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

        cita = dbconeccion.consultarCita(Integer.parseInt(idMascota),fechaCita,horaIni);
        fechaFiltro = cita.getFechaFiltro();


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

        tipoC.setText(cita.getTipo());
        tipoC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!tipoC.getText().toString().equals(cita.getTipo())) {
                    guardarMod.setEnabled(true);
                    guardarMod.setFocusable(true);
                }
            }
        });

        //Boton de atras y modificación del fondo de
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Verde)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_consular_cita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        else if (item.getItemId() == R.id.action_settings_ConsultarCita) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("ConsultarCita");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getDia(String fecha) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        if (!fechaActual.equals(null)) fechaCalendario.setTime(fechaActual);
        int dia = fechaCalendario.get(Calendar.DAY_OF_MONTH);
        return dia;
    }

    private int getMes(String fecha) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        if (!fechaActual.equals(null))  fechaCalendario.setTime(fechaActual);
        int mes = fechaCalendario.get(Calendar.MONTH);
        return mes+1;
    }

    private int getAny(String fecha) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        if (fechaActual != null)  fechaCalendario.setTime(fechaActual);
        int year = fechaCalendario.get(Calendar.YEAR);
        return year;
    }

    private boolean esCorrectaLaFecha() {
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");

        Calendar mcurrentDate=Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        String startDate = mDay + "/" + (mMonth+1) + "/" + mYear;
        String endDate = fechaC.getText().toString();

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = true;  // If two dates are equal.
            } else {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    public void updateCita() {

        if (esCorrectaLaFecha()) {
            boolean existeixCita = dbconeccion.existeixCita(Integer.parseInt(idMascota),fechaC.getText().toString(),horaIniC.getText().toString());
            if (!existeixCita || (existeixCita && !tipoC.getText().toString().equals(cita.getTipo()))) {

                //Eliminar tipo de cita
                //if (dbconeccion.numeroDeTipoCEnCitas(cita.getTipo()) == 1)dbconeccion.eliminarTipoCita(cita.getTipo());

                dbconeccion.eliminarCita(Integer.parseInt(idMascota), fechaCita, horaIni);

                Cita e = new Cita();
                e.setIdMascota(Integer.parseInt(idMascota));
                e.setFecha(fechaC.getText().toString());
                e.setDiaC(getDia(fechaC.getText().toString()));
                e.setMesC(getMes(fechaC.getText().toString()));
                e.setAnyC(getAny(fechaC.getText().toString()));
                e.setFechaFiltro(fechaFiltro);
                e.setHoraIni(horaIniC.getText().toString());
                e.setTipo(tipoC.getText().toString());
                dbconeccion.insertarCita(e);
                if (!dbconeccion.existeixTipoC(tipoC.getText().toString())) dbconeccion.insertTipoC(tipoC.getText().toString());

                dbconeccion.cerrar();
                Intent main = new Intent(this,MainActivity.class);
                startActivity(main);
                Toast.makeText(this, "Cita actualizada" , Toast.LENGTH_SHORT).show();
            }
            else {

                String res = "La mascota " + nombreM + " tiene una cita el día " + fechaC.getText().toString() + " a la misma hora";
                Toast.makeText(this, res , Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Calendar mcurrentDate=Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth=mcurrentDate.get(Calendar.MONTH);
            int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
            String fechaAct = mDay + "/" + mMonth + "/" + mYear;
            //Creamos el AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Error");
            builder.setMessage("La fecha de la cita tiene que ser posterior o igual a la fecha " + fechaAct + ".");
            builder.setNeutralButton("Aceptar", null);
            builder.show();
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
                fechaFiltro = selectedyear + "-";
                if ((selectedmonth+1) < 10) fechaFiltro += "0" + (selectedmonth+1) + "-";
                else fechaFiltro += (selectedmonth+1) + "-";
                if (selectedday < 10) fechaFiltro += "0" + selectedday;
                else fechaFiltro += selectedday;
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
