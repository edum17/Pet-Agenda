package com.example.german.controlmascotas;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by German on 10/05/2016.
 */
public class NuevaCita extends Fragment{

    Context context;
    SQLControlador dbconeccion;

    View rootView;

    TextView nombre, fechaC, horaIni;
    EditText tipoC;
    ImageButton nombresM,fecha, horaI, tiposC;
    Button butCrear;
    String fechaFiltro;
    private CharSequence mTitle;
    public static final int NOTIFICACION_ID=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.lay_nueva_cita, container, false);
        context = container.getContext();

        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();

        mTitle = "Nueva cita";

        //TextView
        nombre = (TextView) rootView.findViewById(R.id.textViewNombreM);
        fechaC = (TextView) rootView.findViewById(R.id.textViewFechaCita);
        horaIni = (TextView) rootView.findViewById(R.id.textViewHoraIni);
        tipoC = (EditText) rootView.findViewById(R.id.eTextTipoE);

        //Buttons
        nombresM = (ImageButton) rootView.findViewById(R.id.butNombreM);
        nombresM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarAnimales();
            }
        });

        fecha = (ImageButton) rootView.findViewById(R.id.butFechaCita);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirFecha();
            }
        });

        horaI = (ImageButton) rootView.findViewById(R.id.butHoraIni);
        horaI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirHoraIni();
            }
        });

        tiposC = (ImageButton) rootView.findViewById(R.id.butTipoC);
        tiposC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirTipoCita();
            }
        });

        butCrear = (Button) rootView.findViewById(R.id.butCrea);
        butCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fechaC.getText().toString().isEmpty() && !horaIni.getText().toString().isEmpty() && !tipoC.getText().toString().isEmpty()) {
                    if (esCorrectaLaFecha()) {
                        addCita();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Fragment agenda = new Agenda();
                        fragmentTransaction.replace(R.id.container, agenda);
                        //fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        dbconeccion.cerrar();
                    } else {
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
                        fechaC.setText(null);
                    }
                }
                else{
                    AlertDialog.Builder Adialog = new AlertDialog.Builder(context);
                    Adialog.setTitle("Crear cita");
                    Adialog.setMessage("Es necesario rellenar todos los campos para registrar una nueva cita de la mascota " + nombre.getText().toString() + ".");
                    Adialog.setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog Alertdialog = Adialog.create();
                    Alertdialog.show();
                }
            }
        });

        setHasOptionsMenu(true);

        return rootView;
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

    public void restoreActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //Eliminamos el titulo de la app en todos los fragments
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        restoreActionBar();
        inflater.inflate(R.menu.menu_nueva_cita, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings_NuevaCita) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("NuevaCita");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }
        else if (id == R.id.action_settings_NuevaCita) {
            NotificationActivity nn = new NotificationActivity();
        }
        return super.onOptionsItemSelected(item);
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

        DatePickerDialog mDatePicker=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                fechaFiltro = selectedyear + "-";
                if ((selectedmonth+1) < 10) fechaFiltro += "0" + (selectedmonth+1) + "-";
                else fechaFiltro += (selectedmonth+1) + "-";
                if (selectedday < 10) fechaFiltro += "0" + selectedday;
                else fechaFiltro += selectedday;
                String date = selectedday + "/" + (selectedmonth+1) + "/" + selectedyear;
                TextView textViewFechaC = (TextView) rootView.findViewById(R.id.textViewFechaCita);
                textViewFechaC.setText(date);
            }
        },mYear, mMonth, mDay);
        mDatePicker.show();
    }

    public void anadirHoraIni() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = "";
                if (hourOfDay < 10) hora += "0" + hourOfDay + ":";
                else hora += hourOfDay + ":";
                if (minute < 10) hora += "0" + minute;
                else hora += minute;
                TextView textViewHoraI = (TextView) rootView.findViewById(R.id.textViewHoraIni);
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

    public void addCita() {
        Cita e = new Cita();
        e.setIdMascota(dbconeccion.getIdMascota(nombre.getText().toString()));
        e.setNombreM(nombre.getText().toString());
        e.setFecha(fechaC.getText().toString());
        e.setDiaC(getDia(fechaC.getText().toString()));
        e.setMesC(getMes(fechaC.getText().toString()));
        e.setAnyC(getAny(fechaC.getText().toString()));
        e.setFechaFiltro(fechaFiltro);
        e.setHoraIni(horaIni.getText().toString());
        e.setTipo(tipoC.getText().toString());

        //Generamos la notificacion
        Intent intent = new Intent(context, ConsultarCita.class);
        intent.putExtra("idMC", String.valueOf(e.getIdMascota()));
        intent.putExtra("fecha", e.getFecha());
        intent.putExtra("horaIni", e.getHoraIni());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //Construccion de la notificacion;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notifications);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        //Imagen de la app
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.img_def_00));
        //Titulo de la notificacion
        builder.setContentTitle("Cita programada");
        //Contenido de la app
        builder.setContentText("Momento para aprender mas sobre Android!");
        //Subcontenido de la app
        builder.setSubText("Toca para ver la documentacion acerca de Anndroid.");

        //Enviar la notificacion
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, builder.build());

        if (!dbconeccion.existeixCita(e.getIdMascota(),e.getFecha(),e.getHoraIni())) {
            //dbconeccion.cerrar();
            if (!dbconeccion.existeixTipoC(tipoC.getText().toString())) dbconeccion.insertTipoC(e.getTipo());
            dbconeccion.insertarCita(e);
            Toast.makeText(context, "Cita creada", Toast.LENGTH_SHORT).show();

        } else {
            String res = "La mascota " + nombre.getText().toString() + " tiene una cita el día " + fechaC.getText().toString() + " a la misma hora";
            Toast.makeText(context, res, Toast.LENGTH_SHORT).show();

        }
    }

}