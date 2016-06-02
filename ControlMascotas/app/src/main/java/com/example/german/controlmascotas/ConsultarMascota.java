package com.example.german.controlmascotas;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.Toolbar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by German on 30/04/2016.
 */
public class ConsultarMascota extends FragmentActivity {

    private final int SELECT_PICTURE = 200;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private String APP_DIRECTORY = "Control de ListaMascotas";
    private String PICTURE_NAME;

    String Path;

    SQLControlador dbconeccion;

    ImageView imagenMascotaCM;
    ImageButton anadirFoto, anadirTipo, anadirFecha;

    EditText nombreMCM, tipoMCM, fechaMCM, nxipMCM, medicacionMCM, alergiaMCM;
    ListView listaCitasMascota;

    Button guardar;

    Context context;

    Mascota mascotaOrg;
    String idMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_consultar_mascota);

        context = this;

        idMascota = getIntent().getStringExtra("idMascota");
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDatos();


        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        getFragmentManager().popBackStack();

        //Obtenemos los parametros de la mascota con identificador idMascota
        mascotaOrg = dbconeccion.consultarMascota(Integer.parseInt(idMascota));
        Path = mascotaOrg.getPath();
        imagenMascotaCM = (ImageView) findViewById(R.id.imageCM);
        anadirFoto = (ImageButton) findViewById(R.id.butImagenCM);
        anadirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirImagen();
            }
        });
        anadirTipo = (ImageButton) findViewById(R.id.butListaTipoCM);
        anadirTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarTipoAnimales();
            }
        });
        anadirFecha = (ImageButton) findViewById(R.id.butFechaCM);
        anadirFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate();
            }
        });

        nombreMCM = (EditText) findViewById(R.id.editTNombreCM);
        tipoMCM = (EditText) findViewById(R.id.editTTipoCM);
        fechaMCM = (EditText) findViewById(R.id.editTextFechaNCM);
        nxipMCM = (EditText) findViewById(R.id.editTNumChipCM);
        medicacionMCM  = (EditText) findViewById(R.id.editTMedicacionCM);
        alergiaMCM = (EditText) findViewById(R.id.editTAlergiaCM);


        nombreMCM.setText(mascotaOrg.getNombre());
        tipoMCM.setText(mascotaOrg.getTipo());
        fechaMCM.setText(mascotaOrg.getFechaNac());
        nxipMCM.setText(mascotaOrg.getNXip());
        medicacionMCM.setText(mascotaOrg.getMedicamento());
        medicacionMCM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!medicacionMCM.getText().toString().equals(mascotaOrg.getMedicamento())) {
                    guardar.setEnabled(true);
                    guardar.setFocusable(true);
                }
            }
        });
        alergiaMCM.setText(mascotaOrg.getAlergia());
        alergiaMCM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!alergiaMCM.getText().toString().equals(mascotaOrg.getAlergia())) {
                    guardar.setEnabled(true);
                    guardar.setFocusable(true);
                }
            }
        });

        //System.out.println("*************************** Path: " + mascotaOrg.getPath());


        if(Path.equals("default")) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.img_def_00);
            imagenMascotaCM.setImageBitmap(bitmap);
        }
        else {
            Bitmap bitmap;
            bitmap = BitmapFactory.decodeFile(Path);
            imagenMascotaCM.setImageBitmap(bitmap);
        }

        listaCitasMascota = (ListView) findViewById(R.id.listViewAgendaCM);

        guardar = (Button) findViewById(R.id.butGuardarModCM);
        guardar.setEnabled(false);
        guardar.setFocusable(false);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMascota();
            }
        });

        citasMascota();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_consultar_mascota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        else if (item.getItemId() == R.id.action_settings_ConsultarMascota) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("ConsultarMascota");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void citasMascota() {
        final ListViewAdapterCitasMascota adapter = new ListViewAdapterCitasMascota(this,dbconeccion.listarCitaMascota(Integer.parseInt(idMascota)));
        adapter.notifyDataSetChanged();
        listaCitasMascota.setAdapter(adapter);
        ListViewSinScroll.setListViewHeightBasedOnItems(listaCitasMascota);
        listaCitasMascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {"Eliminar cita"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cita");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Eliminar cita")) {
                            String fecha = adapter.getItemFechaC(position);
                            String horaIni = adapter.getItemHoraIni(position);
                            dbconeccion.eliminarCita(Integer.parseInt(idMascota), fecha, horaIni);
                            adapter.updateAdapter(dbconeccion.listarCitaMascota(Integer.parseInt(idMascota)));
                            Toast.makeText(getApplicationContext(), "La cita ha sido eliminada", Toast.LENGTH_SHORT).show();
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
        });
    }

    public void updateMascota() {
        //Toast.makeText(context, "Boton activado", Toast.LENGTH_SHORT).show();

        if (!medicacionMCM.getText().toString().equals(mascotaOrg.getMedicamento())){
            dbconeccion.updateMedM(Integer.parseInt(idMascota), medicacionMCM.getText().toString());
        }
        if (!alergiaMCM.getText().toString().equals(mascotaOrg.getAlergia())){
            dbconeccion.updateAlerM(Integer.parseInt(idMascota), alergiaMCM.getText().toString());
        }
        if (!mascotaOrg.getPath().equals(Path)) {
            dbconeccion.updatePathM(Integer.parseInt(idMascota),Path);
        }

        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
        Toast.makeText(this, "Mascota actualizada" , Toast.LENGTH_SHORT).show();
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
                String date = selectedday + "/" + (selectedmonth+1) + "/" + selectedyear;
                fechaMCM.setText(date);
            }
        },mYear, mMonth, mDay);
        mDatePicker.show();
    }

    public void anadirImagen() {
        //Toast.makeText(getActivity(),"Llamo a la camara",Toast.LENGTH_SHORT).show();
        final CharSequence[] items = {"Hacer foto","Galería","Eliminar imagen"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Añadir imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Hacer foto")) {
                    openCamera();
                    guardar.setEnabled(true);
                    guardar.setFocusable(true);
                    if(!mascotaOrg.getPath().equals(Path)) {
                        System.out.println("*************************** mascotaOrg.getPath(): " + mascotaOrg.getPath());
                        System.out.println("*************************** Path(): " + Path);
                        dbconeccion.updatePathM(mascotaOrg.getId(),Path);
                    }
                } else if (items[which].equals("Galería")) {
                    guardar.setEnabled(true);
                    guardar.setFocusable(true);
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), SELECT_PICTURE);
                    if(!mascotaOrg.getPath().equals(Path)) dbconeccion.updatePathM(mascotaOrg.getId(),Path);
                }
                else if (items[which].equals("Eliminar imagen")) {
                    guardar.setEnabled(true);
                    guardar.setFocusable(true);
                    Path = "default";
                    imagenMascotaCM.setBackgroundResource(R.mipmap.img_def_01);
                    if(!mascotaOrg.getPath().equals(Path)) dbconeccion.updatePathM(mascotaOrg.getId(),Path);
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
                imagenMascotaCM.setImageBitmap(bitmap);
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
                    imagenMascotaCM.setImageBitmap(bitmap);
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
                tipoMCM.setText(animalSel);
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