package com.example.german.controlmascotas;

/**
 * Created by German on 01/04/2016.
 */
public class ItemMascotas {
    private String Nombre;
    private String Tipo;
    private String FechaNac;
    private String Path;

    public ItemMascotas(){

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

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }
}
