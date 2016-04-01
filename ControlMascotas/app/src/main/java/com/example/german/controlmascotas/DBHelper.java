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
    public static final String CN_Path = "_path";

    //Tabla Eventos
    public static final String TABLA_EVENTOS = "Eventos";
    public static final String CN_NomE = "_nomE";
    public static final String CN_FechaE = "_fechaE";
    public static final String CN_HoraE = "_hora";
    public static final String CN_TipoE = "_tipoE";

    //Tabla Eventos
    public static final String TABLA_TIPO_EVENTO = "TipoEventos";
    public static final String CN_NomTE = "_nomTE";
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Creacion de las tablas

    public static final String CREA_TABLA_MASCOTAS = "CREATE TABLE " + TABLA_MASCOTAS + "(" +
            CN_NomM + " TEXT PRIMARY KEY, " +
            CN_TipoM + " TEXT NOT NULL, " +
            CN_FechaNac + " DATE NOT NULL, " +
            CN_NXip + " INTEGER NOT NULL, " +
            CN_Path + " TEXT);";

    public static final String CREA_TABLA_TIPO_EVENTO = "CREATE TABLE " + TABLA_TIPO_EVENTO + "(" +
            CN_NomTE + "TEXT PRIMARY KEY);";

    public static final String CREA_TABLA_EVENTOS = "CREATE TABLE " + TABLA_EVENTOS + "(" +
            CN_NomE + " TEXT NOT NULL, " +
            CN_FechaE + " TEXT NOT NULL, " +
            CN_HoraE + " TEXT NOT NULL, " +
            CN_TipoE + " TEXT NOT NULL, " +
            "PRIMARY KEY (" + CN_NomE + "," + CN_FechaE + "," + CN_HoraE + "), " +
            "FOREIGN KEY (" + CN_NomE + ") REFERENCES " + TABLA_MASCOTAS + " (" + CN_NomM + "), " +
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
        mascota1.put(CN_Path,"storage");
        db.insert(TABLA_MASCOTAS,null,mascota1);

    }
}
