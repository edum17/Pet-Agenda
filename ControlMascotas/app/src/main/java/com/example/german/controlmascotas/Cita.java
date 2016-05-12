package com.example.german.controlmascotas;

/**
 * Created by German on 07/05/2016.
 */
public class Cita {

    private String nom;
    private String diaSemana;
    private String fecha;
    private String horaIni;
    private String horaFin;
    private String tipo;
    private String nomDiaFecha;
    private String nomMascotaTipoE;

    public Cita() {
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String hora) {
        this.horaIni = hora;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
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

}
