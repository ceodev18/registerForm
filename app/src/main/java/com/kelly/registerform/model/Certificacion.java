package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class Certificacion {
    public Certificacion(){

    }
    private int id_certificacion;
    private int id_owner;
    private int id_socio;
    private boolean tiene_certificado;
    private int id_tipo_certificadora;
    private int id_empresa_certificadora;
    private String codigo_certificado;
    private int fecha_caducidad;
    private String  tipo_certificacion;

    public int getId_certificacion() {
        return id_certificacion;
    }

    public void setId_certificacion(int id_certificacion) {
        this.id_certificacion = id_certificacion;
    }

    public int getId_owner() {
        return id_owner;
    }

    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public boolean isTiene_certificado() {
        return tiene_certificado;
    }

    public void setTiene_certificado(boolean tiene_certificado) {
        this.tiene_certificado = tiene_certificado;
    }

    public int getId_tipo_certificadora() {
        return id_tipo_certificadora;
    }

    public void setId_tipo_certificadora(int id_tipo_certificadora) {
        this.id_tipo_certificadora = id_tipo_certificadora;
    }

    public int getId_empresa_certificadora() {
        return id_empresa_certificadora;
    }

    public void setId_empresa_certificadora(int id_empresa_certificadora) {
        this.id_empresa_certificadora = id_empresa_certificadora;
    }

    public String getCodigo_certificado() {
        return codigo_certificado;
    }

    public void setCodigo_certificado(String codigo_certificado) {
        this.codigo_certificado = codigo_certificado;
    }

    public int getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(int fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public String getTipo_certificacion() {
        return tipo_certificacion;
    }

    public void setTipo_certificacion(String tipo_certificacion) {
        this.tipo_certificacion = tipo_certificacion;
    }
}
