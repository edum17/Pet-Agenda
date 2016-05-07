package com.example.german.controlmascotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by German on 01/04/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "ControlMascotas.sqlite";
    private static final int DB_Version = 1;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Definicion de los parametros de cada tabla:

    //Tabla Mascotas
    public static final String TABLA_MASCOTAS = "Mascotas";
    public static final String CN_NomM = "_nomM";
    public static final String CN_TipoM = "_tipoM";
    public static final String CN_FechaNac = "_fechaNac";
    public static final String CN_NXip = "_nxip";
    public static final String CN_Med = "_medicacion";
    public static final String CN_Aler = "_alergia";
    public static final String CN_Path = "_path";

    //Tabla Eventos
    public static final String TABLA_EVENTOS = "Eventos";
    public static final String CN_NomME = "_nomME";
    public static final String CN_FechaE = "_fechaE";
    public static final String CN_HoraE = "_hora";
    public static final String CN_TipoE = "_tipoE";

    //Tabla TipoEventos
    public static final String TABLA_TIPO_EVENTO = "TipoEventos";
    public static final String CN_NomTE = "_nomTE";
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Creacion de las tablas

    public static final String CREA_TABLA_MASCOTAS = "CREATE TABLE " + TABLA_MASCOTAS + "(" +
            CN_NomM + " TEXT PRIMARY KEY, " +
            CN_TipoM + " TEXT NOT NULL, " +
            CN_FechaNac + " DATE NOT NULL, " +
            CN_NXip + " TEXT NOT NULL, " +
            CN_Med + " TEXT NOT NULL, " +
            CN_Aler + " TEXT NOT NULL, " +
            CN_Path + " TEXT);";

    public static final String CREA_TABLA_TIPO_EVENTO = "CREATE TABLE " + TABLA_TIPO_EVENTO + "(" +
            CN_NomTE + " TEXT PRIMARY KEY);";

    public static final String CREA_TABLA_EVENTOS = "CREATE TABLE " + TABLA_EVENTOS + "(" +
            CN_NomME + " TEXT NOT NULL, " +
            CN_FechaE + " TEXT NOT NULL, " +
            CN_HoraE + " TEXT NOT NULL, " +
            CN_TipoE + " TEXT NOT NULL, " +
            "PRIMARY KEY (" + CN_NomME + "," + CN_FechaE + "," + CN_HoraE + "), " +
            "FOREIGN KEY (" + CN_NomME + ") REFERENCES " + TABLA_MASCOTAS + " (" + CN_NomM + "), " +
            "FOREIGN KEY (" + CN_TipoE + ") REFERENCES " + TABLA_TIPO_EVENTO + " (" + CN_NomTE + "));";



    ////////////////////////////////////////////////////////////////////////////////////////////////

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREA_TABLA_MASCOTAS);
        db.execSQL(CREA_TABLA_TIPO_EVENTO);
        db.execSQL(CREA_TABLA_EVENTOS);
        insertarMascota1(db);
        insertarMascota2(db);
        insertarTiposEventos(db);
        insertarEventos(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXEISTS " + TABLA_MASCOTAS);
        db.execSQL("DROP TABLE IF EXEISTS " + TABLA_EVENTOS);
        db.execSQL("DROP TABLE IF EXEISTS " + TABLA_TIPO_EVENTO);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Insertamos valores predeterminados en las tablas

    private void insertarMascota1(SQLiteDatabase db) {
        ContentValues mascota1 = new ContentValues();
        mascota1.put(CN_NomM,"A");
        mascota1.put(CN_TipoM,"A");
        mascota1.put(CN_FechaNac,"11/11/1111");
        mascota1.put(CN_NXip,"1");
        mascota1.put(CN_Med,"No");
        mascota1.put(CN_Aler,"Paracetamol");
        mascota1.put(CN_Path,"default");
        db.insert(TABLA_MASCOTAS,null,mascota1);
    }


    private void insertarMascota2(SQLiteDatabase db) {
        ContentValues mascota2 = new ContentValues();
        mascota2.put(CN_NomM,"B");
        mascota2.put(CN_TipoM,"B");
        mascota2.put(CN_FechaNac,"22/22/2222");
        mascota2.put(CN_NXip,"2");
        mascota2.put(CN_Med,"Gelocatil");
        mascota2.put(CN_Aler,"Paracetamol");
        mascota2.put(CN_Path,"default");
        db.insert(TABLA_MASCOTAS,null,mascota2);
    }

    private void insertarTiposEventos(SQLiteDatabase db) {
        ContentValues tevento1 = new ContentValues();
        tevento1.put(CN_NomTE,"Vacunación");
        db.insert(TABLA_TIPO_EVENTO,null,tevento1);

        ContentValues tevento2 = new ContentValues();
        tevento2.put(CN_NomTE,"Desparacitación");
        db.insert(TABLA_TIPO_EVENTO,null,tevento2);

        ContentValues tevento3 = new ContentValues();
        tevento3.put(CN_NomTE,"Veterinario");
        db.insert(TABLA_TIPO_EVENTO,null,tevento3);

        ContentValues tevento4 = new ContentValues();
        tevento4.put(CN_NomTE,"Peluquería");
        db.insert(TABLA_TIPO_EVENTO,null,tevento4);

        ContentValues tevento5 = new ContentValues();
        tevento5.put(CN_NomTE,"Adiestrador");
        db.insert(TABLA_TIPO_EVENTO,null,tevento5);
    }

    private void insertarEventos(SQLiteDatabase db) {
        ContentValues evento1 = new ContentValues();
        evento1.put(CN_NomME,"A");
        evento1.put(CN_FechaE,"11/11/1111");
        evento1.put(CN_HoraE,"08:00");
        evento1.put(CN_TipoE,"Vacunación");
        db.insert(TABLA_EVENTOS,null,evento1);

        ContentValues evento2 = new ContentValues();
        evento2.put(CN_NomME,"A");
        evento2.put(CN_FechaE,"11/11/1111");
        evento2.put(CN_HoraE,"09:00");
        evento2.put(CN_TipoE,"Desparacitación");
        db.insert(TABLA_EVENTOS,null,evento2);

        ContentValues evento3 = new ContentValues();
        evento3.put(CN_NomME,"A");
        evento3.put(CN_FechaE,"12/11/1111");
        evento3.put(CN_HoraE,"08:30");
        evento3.put(CN_TipoE,"Veterinario");
        db.insert(TABLA_EVENTOS,null,evento3);

        ContentValues evento4 = new ContentValues();
        evento4.put(CN_NomME,"B");
        evento4.put(CN_FechaE,"12/11/1111");
        evento4.put(CN_HoraE,"09:30");
        evento4.put(CN_TipoE,"Peluquería");
        db.insert(TABLA_EVENTOS,null,evento4);

        ContentValues evento5 = new ContentValues();
        evento5.put(CN_NomME,"A");
        evento5.put(CN_FechaE,"13/11/1111");
        evento5.put(CN_HoraE,"08:30");
        evento5.put(CN_TipoE,"Adiestrador");
        db.insert(TABLA_EVENTOS,null,evento5);
    }
}
