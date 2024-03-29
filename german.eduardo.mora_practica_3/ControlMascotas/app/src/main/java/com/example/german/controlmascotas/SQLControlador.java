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
        String query = "SELECT " + dbhelper.CN_idM + "," + dbhelper.CN_NomM + "," + dbhelper.CN_TipoM + "," + dbhelper.CN_FechaNac + "," + dbhelper.CN_Path + "," + dbhelper.CN_NXip + " FROM " + dbhelper.TABLA_MASCOTAS + " ORDER BY " + dbhelper.CN_NomM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Mascota> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Mascota m = new Mascota();
            m.setId(Integer.parseInt(c.getString(c.getColumnIndex("_idM"))));
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

    public ArrayList<String> listarTratamientoAnimales() {
        String query = "SELECT DISTINCT " + dbhelper.CN_Med + " FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_Med + "<>'No' ORDER BY " + dbhelper.CN_Med;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<String> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            String medM;
            medM = c.getString(c.getColumnIndex("_medicacion"));
            res.add(medM);
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

    public ArrayList<Cita> listarDiasAgenda() {
        String query = "SELECT DISTINCT " + dbhelper.CN_FechaC + " FROM " + dbhelper.TABLA_CITA + " ORDER BY " + dbhelper.CN_FechaFiltro;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Cita> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Cita dfa = new Cita();
            dfa.setFecha(c.getString(c.getColumnIndex("_fechaC")));
            dfa.setNomDiaFecha(getDiaFecha(c.getString(c.getColumnIndex("_fechaC"))));
            res.add(dfa);
            c.moveToNext();
        }
        return res;
    }

    public String getNomMascota(int id) {
        String query = "SELECT " + dbhelper.CN_NomM + " FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_idM + " = " + id;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        String res = null;
        while (c.isAfterLast() == false) {
            res = c.getString(c.getColumnIndex("_nomM"));
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<Cita> listarCitasDia(String fecha) {
        String query = "SELECT " + dbhelper.CN_idMC+ "," + dbhelper.CN_FechaC + "," + dbhelper.CN_HoraIniC + "," + dbhelper.CN_TipoC + " FROM " + dbhelper.TABLA_CITA + " WHERE " + dbhelper.CN_FechaC + " = '" + fecha + "' ORDER BY " + dbhelper.CN_HoraIniC;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Cita> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Cita dfa = new Cita();
            String nombreMascota = getNomMascota(Integer.parseInt(c.getString(c.getColumnIndex("_idMC"))));
            String s = "La mascota " + nombreMascota + " tiene " + c.getString(c.getColumnIndex("_tipoC"));
            dfa.setFecha(c.getString(c.getColumnIndex("_fechaC")));
            dfa.setIdMascota(Integer.parseInt(c.getString(c.getColumnIndex("_idMC"))));
            dfa.setNombreM(nombreMascota);
            dfa.setHoraIni(c.getString(c.getColumnIndex("_horaIni")));
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

    public ArrayList<String> listarTiposCitas() {
        String query = "SELECT DISTINCT * FROM " + dbhelper.TABLA_TIPO_CITA + " ORDER BY " + dbhelper.CN_NomTC;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<String> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            String nomTC;
            nomTC = c.getString(c.getColumnIndex("_nomTC"));
            res.add(nomTC);
            c.moveToNext();
        }
        return res;
    }

    public int getIdMascota(String nomM) {
        String query = "SELECT " + dbhelper.CN_idM + " FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_NomM + " = '" + nomM + "'";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        int res = -1;
        while (c.isAfterLast() == false) {
            res = c.getInt(c.getColumnIndex("_idM"));
            c.moveToNext();
        }
        return res;
    }

    //Busquedas y altas de nuevas mascotas
    private ContentValues valoresCita(Cita c) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_idMC,c.getIdMascota());
        res.put(DBHelper.CN_FechaC,c.getFecha());
        res.put(DBHelper.CN_DiaC,c.getDiaC());
        res.put(DBHelper.CN_MesC,c.getMesC());
        res.put(DBHelper.CN_AnyC,c.getAnyC());
        res.put(DBHelper.CN_FechaFiltro,c.getFechaFiltro());
        res.put(DBHelper.CN_HoraIniC,c.getHoraIni());
        res.put(DBHelper.CN_TipoC,c.getTipo());
        return res;
    }

    public boolean existeixCita(int idMC, String fecha, String horaIni) {
        ArrayList<String> res = new ArrayList<>();
        String query = "SELECT " + dbhelper.CN_idMC + " FROM " + dbhelper.TABLA_CITA + " WHERE " + dbhelper.CN_idMC + "=" + idMC +" and " + dbhelper.CN_FechaC + "='" + fecha + "' and " + dbhelper.CN_HoraIniC + "='" + horaIni +"'";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        while (c.isAfterLast() == false) {
            res.add(c.getString(c.getColumnIndex("_idMC")));
            c.moveToNext();
        }
        if (res.size() == 0) return false;
        else return true;
    }

    public void insertarCita(Cita c) {
        database.insert(DBHelper.TABLA_CITA,null,valoresCita(c));
    }

    private ContentValues valoresTipoC(String tipo) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_NomTC, tipo);
        return res;
    }

    public void insertTipoC(String tipo) {
        database.insert(DBHelper.TABLA_TIPO_CITA, null, valoresTipoC(tipo));
    }

    /*
    Eliminar tipo de cita

    public void eliminarTipoCita(String tipo) {
        database.delete(DBHelper.TABLA_TIPO_CITA, DBHelper.CN_NomTC + "='" + tipo + "'", null);
    }

    public int numeroDeTipoCEnCitas(String tipo) {
        ArrayList<String> res = new ArrayList<>();
        String query = "SELECT * FROM " + dbhelper.TABLA_CITA + " WHERE " + dbhelper.CN_TipoC + "='" + tipo +"'";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        while (c.isAfterLast() == false) {
            res.add(c.getString(c.getColumnIndex("_tipoC")));
            c.moveToNext();
        }
        return res.size();
    }
    */

    public boolean existeixTipoC(String tipo) {
        ArrayList<String> res = new ArrayList<>();
        String query = "SELECT * FROM " + dbhelper.TABLA_TIPO_CITA + " WHERE " + dbhelper.CN_NomTC + "='" + tipo +"'";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        while (c.isAfterLast() == false) {
            res.add(c.getString(c.getColumnIndex("_nomTC")));
            c.moveToNext();
        }
        if (res.size() == 0) return false;
        else return true;
    }

    public void eliminarCita(int idMC, String fecha, String horaIni) {
        database.delete(DBHelper.TABLA_CITA, DBHelper.CN_idMC + "=" + idMC + " and " + DBHelper.CN_FechaC + "='" + fecha + "' and " + DBHelper.CN_HoraIniC + "='" + horaIni + "'", null);
    }

    public Cita consultarCita(int idMC,String fecha, String horaIni) {
        String query = "SELECT " + dbhelper.CN_FechaFiltro + "," + dbhelper.CN_TipoC + " FROM " + dbhelper.TABLA_CITA +
                " WHERE " + dbhelper.CN_idMC + "=" + idMC + " and " + dbhelper.CN_FechaC + "='" + fecha + "' and " +
                dbhelper.CN_HoraIniC + "='" + horaIni + "'";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        Cita res = new Cita();
        res.setIdMascota(idMC);
        res.setFecha(fecha);
        res.setHoraIni(horaIni);
        while (c.isAfterLast() == false) {
            String fechaF = c.getString(c.getColumnIndex("_fechaFil"));
            String tipoC = c.getString(c.getColumnIndex("_tipoC"));
            res.setFechaFiltro(fechaF);
            res.setTipo(tipoC);
            c.moveToNext();
        }
        return res;
    }

    public void eliminarMascotaYCitas(int idM) {
        database.delete(DBHelper.TABLA_MASCOTAS, DBHelper.CN_idM + "=" + idM, null);
        database.delete(DBHelper.TABLA_CITA,DBHelper.CN_idMC + "=" + idM,null);
    }

    public ArrayList<Cita> listarCitaMascota(int idMC) {
        String query = "SELECT " + dbhelper.CN_FechaC + "," + dbhelper.CN_HoraIniC + "," + dbhelper.CN_TipoC + " FROM " + dbhelper.TABLA_CITA + " WHERE " + dbhelper.CN_idMC + " = " + idMC + " ORDER BY " + dbhelper.CN_AnyC + "," + dbhelper.CN_MesC + "," + dbhelper.CN_DiaC + "," + dbhelper.CN_HoraIniC +  " ASC";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Cita> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Cita dfa = new Cita();
            dfa.setFecha(c.getString(c.getColumnIndex("_fechaC")));
            dfa.setHoraIni(c.getString(c.getColumnIndex("_horaIni")));
            dfa.setTipo(c.getString(c.getColumnIndex("_tipoC")));
            res.add(dfa);
            c.moveToNext();
        }
        return res;
    }

    public Mascota consultarMascota(int idM) {
        String query = "SELECT * FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_idM + "=" + idM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        Mascota res = new Mascota();

        while (c.isAfterLast() == false) {
            String nombreM = c.getString(c.getColumnIndex("_nomM"));
            String tipoM = c.getString(c.getColumnIndex("_tipoM"));
            String fechaNac = c.getString(c.getColumnIndex("_fechaNac"));
            String nxip = c.getString(c.getColumnIndex("_nxip"));
            String med = c.getString(c.getColumnIndex("_medicacion"));
            String aler = c.getString(c.getColumnIndex("_alergia"));
            String path = c.getString(c.getColumnIndex("_path"));

            res.setNombre(nombreM);
            res.setTipo(tipoM);
            res.setFechaNac(fechaNac);
            res.setNXip(nxip);
            res.setMedicamento(med);
            res.setAlergia(aler);
            res.setPath(path);

            c.moveToNext();
        }
        return res;
    }

    //Actulitacio de les dades dinaqiques d'una mascota
    public int updatePathM(int idMascota, String newPathM) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_Path,newPathM);
        int i = database.update(DBHelper.TABLA_MASCOTAS,res,  DBHelper.CN_idM + "=" + idMascota,null);
        return i;
    }

    public int updateMedM(int idMascota, String newMedM) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_Med,newMedM);
        int i = database.update(DBHelper.TABLA_MASCOTAS,res,  DBHelper.CN_idM + "=" + idMascota,null);
        return i;
    }

    public int updateAlerM(int idMascota, String newAlerM) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_Aler,newAlerM);
        int i = database.update(DBHelper.TABLA_MASCOTAS,res, DBHelper.CN_idM + "=" + idMascota,null);
        return i;
    }

    public ArrayList<Mascota> listarMascotasPorTipo(String tipo) {
        String query = "SELECT " + dbhelper.CN_idM + "," + dbhelper.CN_NomM + "," + dbhelper.CN_TipoM + "," + dbhelper.CN_FechaNac + "," + dbhelper.CN_Path + "," + dbhelper.CN_NXip + " FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_TipoM + "='" + tipo + "' ORDER BY " + dbhelper.CN_NomM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Mascota> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Mascota m = new Mascota();
            m.setId(Integer.parseInt(c.getString(c.getColumnIndex("_idM"))));
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

    public ArrayList<Mascota> listarMascotasPorTratamiento(String tratamiento) {
        String query = "SELECT " + dbhelper.CN_idM + "," + dbhelper.CN_NomM + "," + dbhelper.CN_TipoM + "," + dbhelper.CN_FechaNac + "," + dbhelper.CN_Path + "," + dbhelper.CN_NXip + " FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_Med + "='" + tratamiento + "' ORDER BY " + dbhelper.CN_NomM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Mascota> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Mascota m = new Mascota();
            m.setId(Integer.parseInt(c.getString(c.getColumnIndex("_idM"))));
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

    public ArrayList<Mascota> listarMascotasSinTratamiento() {
        String query = "SELECT " + dbhelper.CN_idM + "," + dbhelper.CN_NomM + "," + dbhelper.CN_TipoM + "," + dbhelper.CN_FechaNac + "," + dbhelper.CN_Path + "," + dbhelper.CN_NXip + " FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_Med + "='No' ORDER BY " + dbhelper.CN_NomM;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Mascota> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Mascota m = new Mascota();
            m.setId(Integer.parseInt(c.getString(c.getColumnIndex("_idM"))));
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

    public ArrayList<Mascota> listarMascotasEnElIntervalo(String fechaIni, String fechaFin) {
        String query = "SELECT DISTINCT * FROM " + dbhelper.TABLA_MASCOTAS + " WHERE " + dbhelper.CN_idM + " IN (SELECT " + dbhelper.CN_idMC + " FROM " + dbhelper.TABLA_CITA + " WHERE " + dbhelper.CN_TipoC + "='Vacunación' and " + dbhelper.CN_FechaFiltro + " BETWEEN '" + fechaIni + "' and '" + fechaFin + "')";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Mascota> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Mascota m = new Mascota();
            m.setId(Integer.parseInt(c.getString(c.getColumnIndex("_idM"))));
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

    public int updateFechaC(int idMascota, String fechaC, String horaIni, String newFechaC) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_FechaC, newFechaC);
        String where = DBHelper.CN_idMC + "=" + idMascota + " and " + DBHelper.CN_FechaC + "='" + fechaC + "' and " + DBHelper.CN_HoraIniC + "='" + horaIni + "'";
        int i = database.update(DBHelper.TABLA_CITA,res, where ,null);
        return i;
    }

    public int updateFechaFiltro(int idMascota, String fechaC, String horaIni, String newFechafiltro) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_FechaFiltro, newFechafiltro);
        String where = DBHelper.CN_idMC + "=" + idMascota + " and " + DBHelper.CN_FechaC + "='" + fechaC + "' and " + DBHelper.CN_HoraIniC + "='" + horaIni + "'";
        int i = database.update(DBHelper.TABLA_CITA,res, where ,null);
        return i;
    }

    public int updateHoraIniC(int idMascota, String fechaC, String horaIni, String newHoraIni) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_HoraIniC, newHoraIni);
        String where = DBHelper.CN_idMC + "=" + idMascota + " and " + DBHelper.CN_FechaC + "='" + fechaC + "' and " + DBHelper.CN_HoraIniC + "='" + horaIni + "'";
        int i = database.update(DBHelper.TABLA_CITA,res, where ,null);
        return i;
    }

    public int updateTipoC(int idMascota, String fechaC, String horaIni, String newTipoC) {
        ContentValues res = new ContentValues();
        res.put(DBHelper.CN_TipoC, newTipoC);
        String where = DBHelper.CN_idMC + "=" + idMascota + " and " + DBHelper.CN_FechaC + "='" + fechaC + "' and " + DBHelper.CN_HoraIniC + "='" + horaIni + "'";
        int i = database.update(DBHelper.TABLA_CITA,res, where ,null);
        return i;
    }
}