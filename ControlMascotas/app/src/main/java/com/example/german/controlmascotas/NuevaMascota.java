package com.example.german.controlmascotas;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.app.DialogFragment;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by German on 01/04/2016.
 */
public class NuevaMascota extends Fragment {

    private Context context;

    private final int SELECT_PICTURE = 200;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private String APP_DIRECTORY = "MascotApps";
    private String PICTURE_NAME;

    private String Path;

    private SQLControlador dbconeccion;
    private TextView textVFecha;
    private Button butGuardarM;
    private ImageButton butAnadirImg, butFechaN, butListaT;
    private View rootView;

    private ImageView img;

    private EditText nombre,tipo,fecha,nchip,medicamento,alergia;

    private CharSequence mTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.lay_nueva_mascota, container, false);

        context = container.getContext();

        dbconeccion = new SQLControlador(context);
        dbconeccion.abrirBaseDatos();
        mTitle = "Nueva mascota";


        img = (ImageView) rootView.findViewById(R.id.image);
        img.setBackgroundResource(R.mipmap.img_def_01);


        textVFecha = (TextView) rootView.findViewById(R.id.textVFechaNac);
        nombre = (EditText) rootView.findViewById(R.id.editTNombreM);
        tipo = (EditText) rootView.findViewById(R.id.editTTipoM);
        fecha = (EditText) rootView.findViewById(R.id.editTextFechaN);
        nchip = (EditText) rootView.findViewById(R.id.editTNumChip);
        medicamento = (EditText) rootView.findViewById(R.id.editTMedicacion);
        alergia = (EditText) rootView.findViewById(R.id.editTAlergia);
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

        Path = "default";
        setHasOptionsMenu(true);

        return rootView;
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
        inflater.inflate(R.menu.menu_nueva_mascota, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_AyudaNuevaMascota) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("Para poder registrar una nueva mascota es imprescindible rellenar los campos nombre, tipo de mascota, fecha de nacimiento y número de chip.\n" +
                    "Si un tipo de animal no figura en la lista, se deberá añadir.");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addDate() {
        //Toast.makeText(getActivity(), "Funciona", Toast.LENGTH_SHORT).show();
        Calendar mcurrentDate=Calendar.getInstance();
        int mYear=mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                String date = "";
                if (selectedday < 10) date += "0" + selectedday + "/";
                else date += selectedday + "/";
                if ((selectedmonth+1) < 10) date += "0" + (selectedmonth+1) + "/";
                else date += selectedmonth + "/";
                date += selectedyear;
                TextView textVFecha = (TextView) getActivity().findViewById(R.id.editTextFechaN);
                textVFecha.setText(date);
            }
        },mYear, mMonth, mDay);
        mDatePicker.show();
    }

    private boolean tienenApostrofe() {
        CharSequence cs = "'";
        if (nombre.getText().toString().contains(cs)) return true;
        else if (tipo.getText().toString().contains(cs)) return true;
        else if (medicamento.getText().toString().contains(cs)) return true;
        else if (alergia.getText().toString().contains(cs)) return true;
        else return false;
    }

    public void addMascotaDB() {
        //Comprobar si el tipo de animal se ha seleccionado de una lista o se ha añadido manualmente
        if (!nombre.getText().toString().isEmpty() && !tipo.getText().toString().isEmpty() && !fecha.getText().toString().isEmpty() && !nchip.getText().toString().isEmpty()) {
            if (!tienenApostrofe()) {
                if (esCorrectaLaFecha()) {
                    String med = "No";
                    String aler = "No";
                    if (!medicamento.getText().toString().equals(""))
                        med = medicamento.getText().toString();
                    if (!alergia.getText().toString().equals(""))
                        aler = alergia.getText().toString();

                    Mascota m = new Mascota(nombre.getText().toString(), tipo.getText().toString(), fecha.getText().toString(), nchip.getText().toString(), med, aler, Path);

                    if (dbconeccion.insertarDatos(m)) {
                        //dbconeccion.cerrar();
                        Toast.makeText(getActivity(), "Mascota guardada", Toast.LENGTH_SHORT).show();
                        clear();
                    } else
                        Toast.makeText(getActivity(), "Existe una mascota con ese nombre", Toast.LENGTH_SHORT).show();
                } else {
                    //fecha incorrecta
                    Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    String endDate = mDay + "/" + (mMonth + 1) + "/" + mYear;

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Error");
                    builder.setMessage("La fecha es incorrecta, debe de estar estar entre 1/1/1900 y " + endDate + ".");
                    builder.setNeutralButton("Aceptar", null);
                    builder.show();
                }
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Error");
                builder.setMessage("El nombre de la mascota, el tipo de animal, la medicación y la alergia, no deben contener apóstrofes.");
                builder.setNeutralButton("Aceptar", null);
                builder.show();
            }

        }
        else {
            AlertDialog.Builder Adialog = new AlertDialog.Builder(context);
            Adialog.setTitle("Guardar mascota");
            Adialog.setMessage("Para registrar una nueva mascota necesario introducir su nombre, el tipo de animal, la fecha de nacimiento y el número del chip.");
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

    private boolean esCorrectaLaFecha() {
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");

        Calendar mcurrentDate=Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        String endDate = mDay + "/" + (mMonth+1) + "/" + mYear;
        String fStart = "1/1/1900";
        String f = fecha.getText().toString();

        boolean b = false;

        try {
            Date date = dfDate.parse(f);
            if(date.getClass().equals(Date.class)) {
                if (dfDate.parse(f).after(dfDate.parse(fStart)) && dfDate.parse(f).before(dfDate.parse(endDate))) b = true;
                else if (dfDate.parse(f).equals(dfDate.parse(fStart)) || dfDate.parse(f).equals(dfDate.parse(endDate))) b = true;
                else b = false;
            }
            else b = false;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    public void anadirImagen() {
        //Toast.makeText(getActivity(),"Llamo a la camara",Toast.LENGTH_SHORT).show();
        final CharSequence[] items = {"Hacer foto","Galería"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Añadir imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Hacer foto")) {
                    openCamera();
                } else if (items[which].equals("Galería")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), SELECT_PICTURE);
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), APP_DIRECTORY);
        file.mkdirs();
        String timeStamp = new SimpleDateFormat("ddmmyyyy_HHmmss").format(new Date());
        PICTURE_NAME = "CM_" + timeStamp + ".jpg";
        String path = Environment.getExternalStorageDirectory() + File.separator + APP_DIRECTORY + File.separator + PICTURE_NAME;
        File newFile = new File(path);

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); //Mediante este llamada se abirar la camara y captura la imagen
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile)); //Para almacenar imagen o video
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) { //Hacemos foto
            if (resultCode == Activity.RESULT_OK) {
                String dir = Environment.getExternalStorageDirectory() + File.separator + APP_DIRECTORY + File.separator + PICTURE_NAME;
                Bitmap bitmap;
                Path = dir;
                bitmap = BitmapFactory.decodeFile(dir);
                img.setImageBitmap(bitmap);
            }
        }

        else if (requestCode == SELECT_PICTURE) { //Elegimos imagen de la galeria
            if (resultCode == Activity.RESULT_OK) {
                Uri path = data.getData();
                InputStream is;
                try {
                    is = context.getContentResolver().openInputStream(path);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Cursor c = context.getContentResolver().query(path, null, null, null, null);
                c.moveToFirst();
                int index = c.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                Path = c.getString(index);
                //System.out.println("*************************** Path: " + Path);
            }
        }
    }


    public void listarTipoAnimales() {
        //Toast.makeText(getActivity(), "Listo todos los tipos de animales", Toast.LENGTH_SHORT).show();
        final ArrayList<String> tipoM = dbconeccion.listarTiposAnimales();

        //Creacion de sequencia de items
        final CharSequence[] Animals = tipoM.toArray(new String[tipoM.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Animales");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String animalSel = Animals[item].toString();  //Selected item in listview
                tipo.setText(animalSel);
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

    public void clear() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment listaM = new ListaMascotas();
        fragmentTransaction.replace(R.id.container, listaM);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
