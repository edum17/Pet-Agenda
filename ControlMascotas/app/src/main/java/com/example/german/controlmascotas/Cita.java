package com.example.german.controlmascotas;

/**
 * Created by German on 07/05/2016.
 */
public class Cita {

    private int idMascota;
    private String nombreM;
    private String diaSemana;
    private String fecha;
    private String fechaFiltro;
    private int diaC;
    private int mesC;
    private int anyC;
    private String horaIni;
    private String tipo;
    private String nomDiaFecha;
    private String nomMascotaTipoE;

    public Cita() {
    }

    public String getNombreM() {
        return nombreM;
    }

    public void setNombreM(String nombreM) {
        this.nombreM = nombreM;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String hora) {
        this.horaIni = hora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getNomMascotaTipoE() {
        return nomMascotaTipoE;
    }

    public void setNomMascotaTipoE(String nomMascotaTipoE) {
        this.nomMascotaTipoE = nomMascotaTipoE;
    }

    public String getNomDiaFecha() {
        return nomDiaFecha;
    }

    public void setNomDiaFecha(String nomDiaFecha) {
        this.nomDiaFecha = nomDiaFecha;
    }

    public int getDiaC() {
        return diaC;
    }

    public void setDiaC(int diaC) {
        this.diaC = diaC;
    }

    public int getMesC() {
        return mesC;
    }

    public void setMesC(int mesC) {
        this.mesC = mesC;
    }

    public int getAnyC() {
        return anyC;
    }

    public void setAnyC(int anyC) {
        this.anyC = anyC;
    }


    public String getFechaFiltro() {
        return fechaFiltro;
    }

    public void setFechaFiltro(String fechaFiltro) {
        this.fechaFiltro = fechaFiltro;
    }

}
