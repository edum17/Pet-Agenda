package com.example.german.controlmascotas;

/**
 * Created by German on 01/04/2016.
 */
public class Mascota {
    private String Nombre;
    private String Tipo;
    private String FechaNac;
    private Integer NXip;
    private String Path;

    public Mascota() {

    }

    public Mascota(String nombre, String tipo, String fecha, Integer nxip, String path){
        Nombre = nombre;
        Tipo = tipo;
        FechaNac = fecha;
        NXip = nxip;
        Path = path;
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

    public Integer getNXip() {
        return NXip;
    }

    public void setNXip(Integer nxip) {
        NXip = nxip;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }
}
