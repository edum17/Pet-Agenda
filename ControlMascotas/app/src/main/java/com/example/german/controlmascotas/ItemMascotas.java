package com.example.german.controlmascotas;

/**
 * Created by German on 01/04/2016.
 */
public class ItemMascotas {
    private String Nombre;
    private String Tipo;
    private String FechaNac;
    private Integer NXip;
    private Integer Path;

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

    public void setNXip(Integer NXip) {
        this.NXip = NXip;
    }

    public Integer getPath() {
        return Path;
    }

    public void setPath(Integer path) {
        Path = path;
    }
}
