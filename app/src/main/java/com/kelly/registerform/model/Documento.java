package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class Documento {
    public Documento(){

    }
    private int id;
    private int id_socio;
    private int id_owner;
    private String nombre_documento;
    private String ubicacion_documento;
    private String formato_documento;
    private String tipo_documento;

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

    public int getId_owner() {
        return id_owner;
    }

    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }

    public String getNombre_documento() {
        return nombre_documento;
    }

    public void setNombre_documento(String nombre_documento) {
        this.nombre_documento = nombre_documento;
    }

    public String getUbicacion_documento() {
        return ubicacion_documento;
    }

    public void setUbicacion_documento(String ubicacion_documento) {
        this.ubicacion_documento = ubicacion_documento;
    }

    public String getFormato_documento() {
        return formato_documento;
    }

    public void setFormato_documento(String formato_documento) {
        this.formato_documento = formato_documento;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }
}
