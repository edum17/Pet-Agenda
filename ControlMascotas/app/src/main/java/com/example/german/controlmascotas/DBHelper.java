package com.example.german.controlmascotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by German on 01/04/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "ControlMascotas.sqlite";
    private static final int DB_Version = 1;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Definicion de los parametros de cada tabla:

    //Tabla ListaMascotas
    public static final String TABLA_MASCOTAS = "Mascotas";
    public static final String CN_NomM = "_nomM";
    public static final String CN_TipoM = "_tipoM";
    public static final String CN_FechaNac = "_fechaNac";
    public static final String CN_NXip = "_nxip";
    public static final String CN_Med = "_medicacion";
    public static final String CN_Aler = "_alergia";
    public static final String CN_Path = "_path";

    //Tabla Citas
    public static final String TABLA_CITA = "Cita";
    public static final String CN_NomMC = "_nomMC";
    public static final String CN_FechaC = "_fechaC";
    public static final String CN_DiaC = "_diaC";
    public static final String CN_MesC = "_mesC";
    public static final String CN_AnyC = "_anyC";
    public static final String CN_HoraIniC = "_horaIni";
    public static final String CN_HoraFinC = "_horaFin";
    public static final String CN_TipoC = "_tipoC";

    //Tabla TipoCita
    public static final String TABLA_TIPO_CITA = "TipoCita";
    public static final String CN_NomTC = "_nomTC";
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

    public static final String CREA_TABLA_TIPO_CITA = "CREATE TABLE " + TABLA_TIPO_CITA + "(" +
            CN_NomTC + " TEXT PRIMARY KEY);";

    public static final String CREA_TABLA_EVENTOS = "CREATE TABLE " + TABLA_CITA + "(" +
            CN_NomMC + " TEXT NOT NULL, " +
            CN_FechaC + " TEXT NOT NULL, " +
            CN_DiaC + " TEXT NOT NULL, " +
            CN_MesC + " TEXT NOT NULL, " +
            CN_AnyC + " TEXT NOT NULL, " +
            CN_HoraIniC + " TEXT NOT NULL, " +
            CN_HoraFinC + " TEXT NOT NULL, " +
            CN_TipoC + " TEXT NOT NULL, " +
            "PRIMARY KEY (" + CN_NomMC + "," + CN_FechaC + "," + CN_HoraIniC + "), " +
            "FOREIGN KEY (" + CN_NomMC + ") REFERENCES " + TABLA_MASCOTAS + " (" + CN_NomM + "), " +
            "FOREIGN KEY (" + CN_TipoC + ") REFERENCES " + TABLA_TIPO_CITA + " (" + CN_NomTC + "));";



    ////////////////////////////////////////////////////////////////////////////////////////////////

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREA_TABLA_MASCOTAS);
        db.execSQL(CREA_TABLA_TIPO_CITA);
        db.execSQL(CREA_TABLA_EVENTOS);
        insertarMascota1(db);
        insertarMascota2(db);
        insertarTiposCita(db);
        insertarCitas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXEISTS " + TABLA_MASCOTAS);
        db.execSQL("DROP TABLE IF EXEISTS " + TABLA_CITA);
        db.execSQL("DROP TABLE IF EXEISTS " + TABLA_TIPO_CITA);
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

    private void insertarTiposCita(SQLiteDatabase db) {
        ContentValues tcita1 = new ContentValues();
        tcita1.put(CN_NomTC, "Vacunación");
        db.insert(TABLA_TIPO_CITA, null, tcita1);

        ContentValues tcita2 = new ContentValues();
        tcita2.put(CN_NomTC, "Desparacitación");
        db.insert(TABLA_TIPO_CITA, null, tcita2);

        ContentValues tcita3 = new ContentValues();
        tcita3.put(CN_NomTC, "Veterinario");
        db.insert(TABLA_TIPO_CITA, null, tcita3);

        ContentValues tcita4 = new ContentValues();
        tcita4.put(CN_NomTC, "Peluquería");
        db.insert(TABLA_TIPO_CITA,null,tcita4);

        ContentValues tcita5 = new ContentValues();
        tcita5.put(CN_NomTC, "Adiestrador");
        db.insert(TABLA_TIPO_CITA,null,tcita5);
    }

    private void insertarCitas(SQLiteDatabase db) {
        //Cita 1
        ContentValues cita = new ContentValues();
        cita.put(CN_NomMC, "A");
        cita.put(CN_FechaC, "11/11/1111");
        cita.put(CN_DiaC,"11");
        cita.put(CN_MesC,"11");
        cita.put(CN_AnyC,"1111");
        cita.put(CN_HoraIniC, "08:00");
        cita.put(CN_HoraFinC, "09:00");
        cita.put(CN_TipoC, "Vacunación");
        db.insert(TABLA_CITA, null, cita);

        //Cita 2
        cita = new ContentValues();
        cita.put(CN_NomMC, "A");
        cita.put(CN_FechaC, "11/11/1111");
        cita.put(CN_DiaC,"11");
        cita.put(CN_MesC,"11");
        cita.put(CN_AnyC,"1111");
        cita.put(CN_HoraIniC, "09:00");
        cita.put(CN_HoraFinC, "09:30");
        cita.put(CN_TipoC, "Desparacitación");
        db.insert(TABLA_CITA, null, cita);

        //Cita 3
        cita = new ContentValues();
        cita.put(CN_NomMC, "A");
        cita.put(CN_FechaC, "12/11/1111");
        cita.put(CN_DiaC,"12");
        cita.put(CN_MesC,"11");
        cita.put(CN_AnyC,"1111");
        cita.put(CN_HoraIniC, "09:00");
        cita.put(CN_HoraFinC, "09:30");
        cita.put(CN_TipoC,"Veterinario");
        db.insert(TABLA_CITA, null, cita);

        //Cita 4
        cita = new ContentValues();
        cita.put(CN_NomMC, "B");
        cita.put(CN_FechaC, "12/11/1111");
        cita.put(CN_DiaC,"12");
        cita.put(CN_MesC,"11");
        cita.put(CN_AnyC,"1111");
        cita.put(CN_HoraIniC, "09:30");
        cita.put(CN_HoraFinC, "10:00");
        cita.put(CN_TipoC,"Peluquería");
        db.insert(TABLA_CITA, null, cita);

        //Cita 5
        cita = new ContentValues();
        cita.put(CN_NomMC, "A");
        cita.put(CN_FechaC, "13/11/1111");
        cita.put(CN_DiaC,"13");
        cita.put(CN_MesC,"11");
        cita.put(CN_AnyC,"1111");
        cita.put(CN_HoraIniC, "09:30");
        cita.put(CN_HoraFinC, "10:00");
        cita.put(CN_TipoC,"Adiestrador");
        db.insert(TABLA_CITA, null, cita);

        //Cita 6
        cita = new ContentValues();
        cita.put(CN_NomMC, "B");
        cita.put(CN_FechaC, "11/11/1111");
        cita.put(CN_DiaC,"11");
        cita.put(CN_MesC,"11");
        cita.put(CN_AnyC,"1111");
        cita.put(CN_HoraIniC, "07:00");
        cita.put(CN_HoraFinC, "08:00");
        cita.put(CN_TipoC,"Adiestrador");
        db.insert(TABLA_CITA, null, cita);
    }
}