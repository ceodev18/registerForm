package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class Transformacion {
    public Transformacion(){}
    private int id_transformacion;
    private int id_socio;
    private int id_certificacion;
    private int total_productos_transformados;

    public int getId_transformacion() {
        return id_transformacion;
    }

    public void setId_transformacion(int id_transformacion) {
        this.id_transformacion = id_transformacion;
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

    public int getTotal_productos_transformados() {
        return total_productos_transformados;
    }

    public void setTotal_productos_transformados(int total_productos_transformados) {
        this.total_productos_transformados = total_productos_transformados;
    }
}
