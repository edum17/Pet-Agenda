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
import android.support.v4.content.CursorLoader;
import android.util.Base64;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by German on 01/04/2016.
 */
public class NuevaMascota extends Fragment {

    Context context;

    private final int SELECT_PICTURE = 200;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private String APP_DIRECTORY = "Control de Mascotas";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";
    String Path;

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

        context = container.getContext();
        dbconeccion = new SQLControlador(context);
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
        Toast.makeText(getActivity(), "Funciona", Toast.LENGTH_SHORT).show();
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getFragmentManager(),"Fecha nacimiento");
    }

    public void addMascotaDB() {
        //Comprobar si el tipo de animal se ha seleccionado de una lista o se ha añadido manualmente
        Mascota m = new Mascota(nombre.getText().toString(),tipo.getText().toString(),fecha.getText().toString(),nchip.getText().toString(),Path);
        if (dbconeccion.insertarDatos(m)) {
            dbconeccion.cerrar();
            Toast.makeText(getActivity(), "Mascota guardada", Toast.LENGTH_SHORT).show();
            clear();
        }
        else Toast.makeText(getActivity(), "Existe una mascota con ese nombre", Toast.LENGTH_SHORT).show();

    }

    public void anadirImagen() {
        //Toast.makeText(getActivity(),"Llamo a la camara",Toast.LENGTH_SHORT).show();
        final CharSequence[] items = {"Hacer foto","Galería","Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Añadir imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Hacer foto")) {
                    //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //startActivityForResult(intent, 0);
                    openCamera();
                } else if (items[which].equals("Galería")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), SELECT_PICTURE);
                } else if (items[which].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) { //Hacemos foto
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG,100,baos);
                byte[] b = baos.toByteArray();
                Path = Base64.encodeToString(b,Base64.DEFAULT);
                img.setImageBitmap(bmp);
                //System.out.println("*************************** Path: " + Path.length());
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
        Toast.makeText(getActivity(), "Listo todos los tipos de animales", Toast.LENGTH_SHORT).show();
    }

    public void clear() {

    }

}
