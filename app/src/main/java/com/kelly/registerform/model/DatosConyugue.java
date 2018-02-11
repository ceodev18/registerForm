package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class DatosConyugue {
    public DatosConyugue(){

    }
    private int id;
    private String nombres;
    private String ap_paterno;
    private String ap_materno;
    private int id_socio;
    private int nro_hijos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public int getNro_hijos() {
        return nro_hijos;
    }

    public void setNro_hijos(int nro_hijos) {
        this.nro_hijos = nro_hijos;
    }
}
