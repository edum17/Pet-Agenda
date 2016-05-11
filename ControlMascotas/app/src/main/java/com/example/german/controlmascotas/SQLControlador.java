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

    private String getDiaSemana(String fecha) {
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
        if (mes == 0) return "enero";
        else if (mes == 1) return "febrero";
        else if (mes == 2) return "marzo";
        else if (mes == 3) return "abril";
        else if (mes == 4) return "mayo";
        else if (mes == 5) return "junio";
        else if (mes == 6) return "julio";
        else if (mes == 7) return "agosto";
        else if (mes == 8) return "septiembre";
        else if (mes == 9) return "octubre";
        else if (mes == 10) return "noviembre";
        else return "diciembre";
    }

    private String getDiaFecha(String fecha) {
        String dia = getDiaSemana(fecha) + ", ";
        String mes = "";
        int i = 0;
        int cont = 0;
        boolean trobat = false;
        while (i < fecha.length()) {
            if (fecha.charAt(i) == '/') {
                if (cont < 1) dia += " ";
                else {
                    dia += " ";
                }
                ++cont;
            } else {
                if (cont == 1 && !trobat) {
                    dia += getMes(fecha);
                    trobat = true;
                    if (fecha.charAt(i+1) != '/') ++i;
                }
                else dia += fecha.charAt(i);
            }
            ++i;
        }
        return dia;
    }

    public ArrayList<Evento> listarDiasAgenda() {
        String query = "SELECT DISTINCT " + dbhelper.CN_FechaE + " FROM " + dbhelper.TABLA_EVENTOS + " ORDER BY " + dbhelper.CN_FechaE;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Evento> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Evento dfa = new Evento();
            dfa.setFecha(c.getString(c.getColumnIndex("_fechaE")));
            dfa.setNomDiaFecha(getDiaFecha(c.getString(c.getColumnIndex("_fechaE"))));
            res.add(dfa);
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<Evento> listarEventosDia(String fecha) {
        String query = "SELECT " + dbhelper.CN_NomME + "," + dbhelper.CN_HoraIniE + "," +dbhelper.CN_HoraFinE + "," + dbhelper.CN_TipoE + " FROM " + dbhelper.TABLA_EVENTOS + " WHERE " + dbhelper.CN_FechaE + " = '" + fecha + "' ORDER BY " + dbhelper.CN_HoraIniE;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Evento> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Evento dfa = new Evento();
            String s = "La mascota " + c.getString(c.getColumnIndex("_nomME")) + " tiene " + c.getString(c.getColumnIndex("_tipoE"));
            dfa.setHoraIni(c.getString(c.getColumnIndex("_horaIni")));
            dfa.setHoraFin(c.getString(c.getColumnIndex("_horaFin")));
            dfa.setNomMascotaTipoE(s);
            res.add(dfa);
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<String> listarNombresMascotas() {
        String query = "SELECT DISTINCT " + dbhelper.CN_NomM + " FROM " + dbhelper.TABLA_MASCOTAS + " ORDER BY " + dbhelper.CN_NomM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<String> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            String nomM;
            nomM = c.getString(c.getColumnIndex("_nomM"));
            res.add(nomM);
            c.moveToNext();
        }
        return res;
    }

}