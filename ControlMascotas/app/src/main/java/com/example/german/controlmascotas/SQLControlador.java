package com.example.german.controlmascotas;

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

    public ArrayList<ItemMascotas> listarMascotas() {
        String query = "SELECT " + dbhelper.CN_NomM + "," + dbhelper.CN_TipoM + "," + dbhelper.CN_FechaNac + "," + dbhelper.CN_Path + " FROM " + dbhelper.TABLA_MASCOTAS + " ORDER BY " + dbhelper.CN_NomM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<ItemMascotas> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            ItemMascotas i = new ItemMascotas();
            i.setNombre(c.getString(c.getColumnIndex("_nomM")));
            i.setTipo(c.getString(c.getColumnIndex("_tipoM")));
            i.setFechaNac(c.getString(c.getColumnIndex("_fechaNac")));
            i.setPath(c.getString(c.getColumnIndex("_path")));
            res.add(i);
            c.moveToNext();
        }
        return res;
    }
}
