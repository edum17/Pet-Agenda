package com.example.german.controlmascotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
        String query = "SELECT " + dbhelper.CN_NomM + "," + dbhelper.CN_TipoM + "," + dbhelper.CN_FechaNac + "," + dbhelper.CN_Path + " FROM " + dbhelper.TABLA_MASCOTAS + " ORDER BY " + dbhelper.CN_NomM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Mascota> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Mascota m = new Mascota();
            m.setNombre(c.getString(c.getColumnIndex("_nomM")));
            m.setTipo(c.getString(c.getColumnIndex("_tipoM")));
            m.setFechaNac(c.getString(c.getColumnIndex("_fechaNac")));
            m.setPath(c.getString(c.getColumnIndex("_path")));
            res.add(m);
            c.moveToNext();
        }
        return res;
    }
}
