package com.example.german.controlmascotas;

import android.app.AlertDialog;

/**
 * Created by German on 01/04/2016.
 */
public class Mascota {
    private int id;
    private String Nombre;
    private String Tipo;
    private String FechaNac;
    private String NXip;
    private String Path;
    private String Medicamento;
    private String Alergia;

    public Mascota() {

    }

    public Mascota(String nombre, String tipo, String fecha, String nxip, String medicamento, String alergia, String path){
        Nombre = nombre;
        Tipo = tipo;
        FechaNac = fecha;
        NXip = nxip;
        Medicamento = medicamento;
        Alergia = alergia;
        Path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }

    public String getNXip() {
        return NXip;
    }

    public void setNXip(String nxip) {
        NXip = nxip;
    }

    public String getMedicamento() {
        return Medicamento;
    }

    public void setMedicamento(String medicamento) {
        Medicamento = medicamento;
    }

    public String getAlergia() {
        return Alergia;
    }

    public void setAlergia(String alergia) {
        Alergia = alergia;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }
}
