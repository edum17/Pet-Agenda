package com.example.german.controlmascotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by German on 01/04/2016.
 */
public class SQLControlador {
    private DBHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDatos() throws SQLException {
        dbhelper = new DBHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbhelper.close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Busquedas y altas de nuevas mascotas
    private ContentValues valoresMascota(Mascota im) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_NomM,im.getNombre());
        res.put(DBHelper.CN_TipoM,im.getTipo());
        res.put(DBHelper.CN_FechaNac,im.getFechaNac());
        res.put(DBHelper.CN_NXip,im.getNXip());
        res.put(DBHelper.CN_Med,im.getMedicamento());
        res.put(DBHelper.CN_Aler,im.getAlergia());
        res.put(DBHelper.CN_Path, im.getPath());
        return res;
    }

    public boolean existeixMascota(String nombreM) {
        ArrayList<String> res = new ArrayList<>();
        String query = "SELECT * FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_NomM + "='" + nombreM +"'";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        while (c.isAfterLast() == false) {
            res.add(c.getString(c.getColumnIndex("_nomM")));
            c.moveToNext();
        }
        if (res.size() == 0) return false;
        else return true;
    }

    public boolean insertarDatos(Mascota im) {
        if (!existeixMascota(im.getNombre())) {
            database.insert(DBHelper.TABLA_MASCOTAS,null,valoresMascota(im));
            return true;
        }
        else return false;
    }

    public ArrayList<Mascota> listarMascotas() {
        String query = "SELECT " + dbhelper.CN_NomM + "," + dbhelper.CN_TipoM + "," + dbhelper.CN_FechaNac + "," + dbhelper.CN_Path + "," + dbhelper.CN_NXip + " FROM " + dbhelper.TABLA_MASCOTAS + " ORDER BY " + dbhelper.CN_NomM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Mascota> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Mascota m = new Mascota();
            m.setNombre(c.getString(c.getColumnIndex("_nomM")));
            m.setTipo(c.getString(c.getColumnIndex("_tipoM")));
            m.setFechaNac(c.getString(c.getColumnIndex("_fechaNac")));
            m.setPath(c.getString(c.getColumnIndex("_path")));
            m.setNXip(c.getString(c.getColumnIndex("_nxip")));
            res.add(m);
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<String> listarTiposAnimales() {
        String query = "SELECT DISTINCT " + dbhelper.CN_TipoM + " FROM " + dbhelper.TABLA_MASCOTAS + " ORDER BY " + dbhelper.CN_TipoM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<String> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            String tipoM;
            tipoM = c.getString(c.getColumnIndex("_tipoM"));
            res.add(tipoM);
            c.moveToNext();
        }
        return res;
    }


    public String getDiaSemana(String fecha) {
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
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
        if (diaSemana == 1) return "Domingo";
        else if (diaSemana == 2) return "Lunes";
        else if (diaSemana == 3) return "Martes";
        else if (diaSemana == 4) return "Miércoles";
        else if (diaSemana == 5) return "Jueves";
        else if (diaSemana == 6) return "Viernes";
        else return "Sábado";
    }

    public ArrayList<DiaFechaAgenda> listarDiasAgenda() {
        String query = "SELECT DISTINCT " + dbhelper.CN_FechaE + " FROM " + dbhelper.TABLA_EVENTOS + " ORDER BY " + dbhelper.CN_FechaE;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<DiaFechaAgenda> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            DiaFechaAgenda dfa = new DiaFechaAgenda();
            dfa.setDiaSemana(getDiaSemana(c.getString(c.getColumnIndex("_fechaE"))));
            dfa.setFecha(c.getString(c.getColumnIndex("_fechaE")));
            res.add(dfa);
            c.moveToNext();
        }
        return res;
    }
}
