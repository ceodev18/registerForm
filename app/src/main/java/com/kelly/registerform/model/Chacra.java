package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class Chacra {
    public Chacra(){

    }
    private int id;
    private int id_produccion;
    private int id_socio;
    private String nombre;
    private String fail;
    private int id_tipo_propiedad;
    private double latitud;
    private double longitud;
    private float medida;
    private int tipo_medida;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_produccion() {
        return id_produccion;
    }

    public void setId_produccion(int id_produccion) {
        this.id_produccion = id_produccion;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_tipo_propiedad() {
        return id_tipo_propiedad;
    }

    public void setId_tipo_propiedad(int id_tipo_propiedad) {
        this.id_tipo_propiedad = id_tipo_propiedad;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public float getMedida() {
        return medida;
    }

    public void setMedida(float medida) {
        this.medida = medida;
    }

    public int getTipo_medida() {
        return tipo_medida;
    }

    public void setTipo_medida(int tipo_medida) {
        this.tipo_medida = tipo_medida;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }
}
