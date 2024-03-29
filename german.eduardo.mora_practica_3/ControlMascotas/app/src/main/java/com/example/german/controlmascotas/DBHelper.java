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
    public static final String CN_idM = "_idM";
    public static final String CN_NomM = "_nomM";
    public static final String CN_TipoM = "_tipoM";
    public static final String CN_FechaNac = "_fechaNac";
    public static final String CN_NXip = "_nxip";
    public static final String CN_Med = "_medicacion";
    public static final String CN_Aler = "_alergia";
    public static final String CN_Path = "_path";

    //Tabla Citas
    public static final String TABLA_CITA = "Cita";
    public static final String CN_idMC = "_idMC";
    public static final String CN_FechaC = "_fechaC";
    public static final String CN_DiaC = "_diaC";
    public static final String CN_MesC = "_mesC";
    public static final String CN_AnyC = "_anyC";
    public static final String CN_FechaFiltro = "_fechaFil";
    public static final String CN_HoraIniC = "_horaIni";
    public static final String CN_TipoC = "_tipoC";

    //Tabla TipoCita
    public static final String TABLA_TIPO_CITA = "TipoCita";
    public static final String CN_NomTC = "_nomTC";
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Creacion de las tablas

    public static final String CREA_TABLA_MASCOTAS = "CREATE TABLE " + TABLA_MASCOTAS + "(" +
            CN_idM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CN_NomM + " TEXT NOT NULL, " +
            CN_TipoM + " TEXT NOT NULL, " +
            CN_FechaNac + " DATE NOT NULL, " +
            CN_NXip + " TEXT NOT NULL, " +
            CN_Med + " TEXT NOT NULL, " +
            CN_Aler + " TEXT NOT NULL, " +
            CN_Path + " TEXT);";

    public static final String CREA_TABLA_TIPO_CITA = "CREATE TABLE " + TABLA_TIPO_CITA + "(" +
            CN_NomTC + " TEXT PRIMARY KEY);";

    public static final String CREA_TABLA_CITAS = "CREATE TABLE " + TABLA_CITA + "(" +
            CN_idMC + " INTEGER NOT NULL, " +
            CN_FechaC + " TEXT NOT NULL, " +
            CN_DiaC + " INTEGER NOT NULL, " +
            CN_MesC + " INTEGER NOT NULL, " +
            CN_AnyC + " INTEGER NOT NULL, " +
            CN_FechaFiltro + " TEXT NOT NULL, " +
            CN_HoraIniC + " TEXT NOT NULL, " +
            CN_TipoC + " TEXT NOT NULL, " +
            "PRIMARY KEY (" + CN_idMC + "," + CN_FechaC + "," + CN_HoraIniC + "), " +
            "FOREIGN KEY (" + CN_idMC + ") REFERENCES " + TABLA_MASCOTAS + " (" + CN_idMC + "), " +
            "FOREIGN KEY (" + CN_TipoC + ") REFERENCES " + TABLA_TIPO_CITA + " (" + CN_NomTC + "));";



    ////////////////////////////////////////////////////////////////////////////////////////////////

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREA_TABLA_MASCOTAS);
        db.execSQL(CREA_TABLA_TIPO_CITA);
        db.execSQL(CREA_TABLA_CITAS);
        insertarMascota1(db);
        insertarMascota2(db);
        insertarMascota3(db);
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
        mascota1.put(CN_idM,1);
        mascota1.put(CN_NomM,"Acho");
        mascota1.put(CN_TipoM,"Gato");
        mascota1.put(CN_FechaNac,"02/04/2003");
        mascota1.put(CN_NXip,"6234569513542397");
        mascota1.put(CN_Med,"Antibiótico");
        mascota1.put(CN_Aler,"No");
        mascota1.put(CN_Path,"axo");
        db.insert(TABLA_MASCOTAS,null,mascota1);
    }


    private void insertarMascota2(SQLiteDatabase db) {
        ContentValues mascota2 = new ContentValues();
        mascota2.put(CN_idM,2);
        mascota2.put(CN_NomM,"Pixie");
        mascota2.put(CN_TipoM,"Perro");
        mascota2.put(CN_FechaNac,"30/03/2007");
        mascota2.put(CN_NXip,"852500015632195");
        mascota2.put(CN_Med,"Gelocatil");
        mascota2.put(CN_Aler,"No");
        mascota2.put(CN_Path,"pixie");
        db.insert(TABLA_MASCOTAS,null,mascota2);
    }

    private void insertarMascota3(SQLiteDatabase db) {
        ContentValues mascota3 = new ContentValues();
        mascota3.put(CN_idM, 3);
        mascota3.put(CN_NomM, "Pupete");
        mascota3.put(CN_TipoM, "Perro");
        mascota3.put(CN_FechaNac, "12/10/2013");
        mascota3.put(CN_NXip, "977200008513592");
        mascota3.put(CN_Med, "No");
        mascota3.put(CN_Aler, "Antibiótico");
        mascota3.put(CN_Path, "pupete");
        db.insert(TABLA_MASCOTAS,null,mascota3);
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
        cita.put(CN_idMC, 1);
        cita.put(CN_FechaC, "06/06/2016");
        cita.put(CN_DiaC, 6);
        cita.put(CN_MesC, 6);
        cita.put(CN_AnyC, 2016);
        cita.put(CN_FechaFiltro, "2016-06-06");
        cita.put(CN_HoraIniC, "08:00");
        cita.put(CN_TipoC, "Vacunación");
        db.insert(TABLA_CITA, null, cita);

        //Cita 2
        cita = new ContentValues();
        cita.put(CN_idMC, 1);
        cita.put(CN_FechaC, "09/06/2016");
        cita.put(CN_DiaC,9);
        cita.put(CN_MesC, 6);
        cita.put(CN_AnyC, 2016);
        cita.put(CN_FechaFiltro, "2016-06-09");
        cita.put(CN_HoraIniC, "09:00");
        cita.put(CN_TipoC, "Desparacitación");
        db.insert(TABLA_CITA, null, cita);

        //Cita 3
        cita = new ContentValues();
        cita.put(CN_idMC, 1);
        cita.put(CN_FechaC, "12/06/2016");
        cita.put(CN_DiaC,12);
        cita.put(CN_MesC,6);
        cita.put(CN_AnyC, 2016);
        cita.put(CN_FechaFiltro, "2016-06-12");
        cita.put(CN_HoraIniC, "09:00");
        cita.put(CN_TipoC,"Veterinario");
        db.insert(TABLA_CITA, null, cita);

        //Cita 4
        cita = new ContentValues();
        cita.put(CN_idMC, 2);
        cita.put(CN_FechaC, "12/06/2016");
        cita.put(CN_DiaC,12);
        cita.put(CN_MesC,6);
        cita.put(CN_AnyC,2016);
        cita.put(CN_FechaFiltro, "2016-06-12");
        cita.put(CN_HoraIniC, "09:30");
        cita.put(CN_TipoC,"Peluquería");
        db.insert(TABLA_CITA, null, cita);

        //Cita 5
        cita = new ContentValues();
        cita.put(CN_idMC, 1);
        cita.put(CN_FechaC, "13/11/2016");
        cita.put(CN_DiaC,13);
        cita.put(CN_MesC,11);
        cita.put(CN_AnyC,2016);
        cita.put(CN_FechaFiltro, "2016-11-13");
        cita.put(CN_HoraIniC, "09:30");
        cita.put(CN_TipoC,"Adiestrador");
        db.insert(TABLA_CITA, null, cita);

        //Cita 6
        cita = new ContentValues();
        cita.put(CN_idMC, 2);
        cita.put(CN_FechaC, "15/06/2016");
        cita.put(CN_DiaC,15);
        cita.put(CN_MesC,6);
        cita.put(CN_AnyC,2016);
        cita.put(CN_FechaFiltro, "2016-06-15");
        cita.put(CN_HoraIniC, "07:00");
        cita.put(CN_TipoC,"Adiestrador");
        db.insert(TABLA_CITA, null, cita);

        //Cita 7
        cita = new ContentValues();
        cita.put(CN_idMC, 3);
        cita.put(CN_FechaC, "06/06/2016");
        cita.put(CN_DiaC, 6);
        cita.put(CN_MesC, 6);
        cita.put(CN_AnyC, 2016);
        cita.put(CN_FechaFiltro, "2016-06-06");
        cita.put(CN_HoraIniC, "15:30");
        cita.put(CN_TipoC, "Peluquería");
        db.insert(TABLA_CITA, null, cita);
    }
}