package com.example.german.controlmascotas;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by German on 10/05/2016.
 */
public class CrearCita extends FragmentActivity{

    Context context;
    SQLControlador dbconeccion;

    ListView listaDia;

    TextView nombre, fechaC, horaIni, horaFin, tipoC;
    ImageButton nombresM,fecha, horaI, horaF, tiposC;
    Button butCrear,butCancelCrea;

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

        butCrear = (Button) findViewById(R.id.butCrea);
        butCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCita();
                Intent main = new Intent(context,MainActivity.class);
                startActivity(main);
                dbconeccion.cerrar();
            }
        });
        butCancelCrea = (Button) findViewById(R.id.butCancelaCrea);
        butCancelCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dbconeccion.cerrar();
            }
        });

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crear_cita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        else if (item.getItemId() == R.id.action_settings_CrearCita) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("Para listar una receta determinada, es necesario seleccionar una de las tres opciones disponible, escribir la palabra a buscar y clicar en el boton de buscar. Una vez obtenidas las recetas deseadas, podemos acceder a una receta clicando sobre ella.");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getActionBar().setDisplayHomeAsUpEnabled(canback);
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
        int mYear = mcurrentDate.get(Calendar.YEAR);
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

    public void anadirHoraIni() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(CrearCita.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = "";
                if (hourOfDay < 10) hora += "0" + hourOfDay + ":";
                else hora += hourOfDay + ":";
                if (minute < 10) hora += "0" + minute;
                else hora += minute;
                TextView textViewHoraI = (TextView) findViewById(R.id.textViewHoraIni);
                textViewHoraI.setText(hora);
            }
        },hour,minute,false);
        mTimePicker.show();
    }

    public void anadirHoraFin() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(CrearCita.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = "";
                if (hourOfDay < 10) hora += "0" + hourOfDay + ":";
                else hora += hourOfDay + ":";
                if (minute < 10) hora += "0" + minute;
                else hora += minute;
                TextView textViewHoraI = (TextView) findViewById(R.id.textViewHoraFin);
                textViewHoraI.setText(hora);
            }
        },hour,minute,false);
        mTimePicker.show();
    }

    public void anadirTipoCita() {
        final ArrayList<String> tipoM = dbconeccion.listarTiposCitas();

        //Creacion de sequencia de items
        final CharSequence[] tiposEventos = tipoM.toArray(new String[tipoM.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
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

    public void addCita() {
        Cita e = new Cita();
        e.setIdMascota(dbconeccion.getIdMascota(nombre.getText().toString()));
        e.setNombreM(nombre.getText().toString());
        e.setFecha(fechaC.getText().toString());
        e.setDiaC(getDia(fechaC.getText().toString()));
        e.setMesC(getMes(fechaC.getText().toString()));
        e.setAnyC(getAny(fechaC.getText().toString()));
        e.setHoraIni(horaIni.getText().toString());
        e.setHoraFin(horaFin.getText().toString());
        e.setTipo(tipoC.getText().toString());
        if (dbconeccion.insertarCita(e)) {
            //dbconeccion.cerrar();
            Toast.makeText(this, "Cita creada", Toast.LENGTH_SHORT).show();

        } else {
            String res = "La mascota " + nombre.getText().toString() + " tiene una cita el día " + fechaC.getText().toString() + " a la misma hora";
            Toast.makeText(this, res , Toast.LENGTH_SHORT).show();

        }
    }
}

/*
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Agenda agenda = new Agenda();
        fragmentTransaction.add(android.R.id.content, agenda);
        fragmentTransaction.commit();
        finish();
        http://www.mzan.com/article/6925941-get-fragments-container-view-id.shtml#sthash.JJPNm8VD.dpuf

 */