package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class VigenciaSocio {
    public VigenciaSocio(){}
    private int id_vigencia;
    private int id_socio;
    private int fecha_inscripcion;
    private float cuota_inscripcion;
    private float couta_anual;

    public int getId_vigencia() {
        return id_vigencia;
    }

    public void setId_vigencia(int id_vigencia) {
        this.id_vigencia = id_vigencia;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public int getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFecha_inscripcion(int fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    public float getCuota_inscripcion() {
        return cuota_inscripcion;
    }

    public void setCuota_inscripcion(float cuota_inscripcion) {
        this.cuota_inscripcion = cuota_inscripcion;
    }

    public float getCouta_anual() {
        return couta_anual;
    }

    public void setCouta_anual(float couta_anual) {
        this.couta_anual = couta_anual;
    }
}
