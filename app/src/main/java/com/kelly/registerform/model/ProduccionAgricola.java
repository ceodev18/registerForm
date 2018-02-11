package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class ProduccionAgricola {
    public ProduccionAgricola(){

    }
    private int id;
    private int id_socio;
    private int id_certificacion;
    private int total_chacras;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public int getId_certificacion() {
        return id_certificacion;
    }

    public void setId_certificacion(int id_certificacion) {
        this.id_certificacion = id_certificacion;
    }

    public int getTotal_chacras() {
        return total_chacras;
    }

    public void setTotal_chacras(int total_chacras) {
        this.total_chacras = total_chacras;
    }
}
