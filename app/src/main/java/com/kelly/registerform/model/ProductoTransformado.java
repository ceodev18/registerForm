package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class ProductoTransformado {
    public ProductoTransformado(){}
    private int id_producto_transformado;
    private int id_tipo_producto_transformado;
    private int id_transformacion;
    private int id_socio;
    private String  ubicacion_gps;
    private int id_certificacion;
    private int id_producto_obtenido;

    public int getId_producto_transformado() {
        return id_producto_transformado;
    }

    public void setId_producto_transformado(int id_producto_transformado) {
        this.id_producto_transformado = id_producto_transformado;
    }

    public int getId_tipo_producto_transformado() {
        return id_tipo_producto_transformado;
    }

    public void setId_tipo_producto_transformado(int id_tipo_producto_transformado) {
        this.id_tipo_producto_transformado = id_tipo_producto_transformado;
    }

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

    public String getUbicacion_gps() {
        return ubicacion_gps;
    }

    public void setUbicacion_gps(String ubicacion_gps) {
        this.ubicacion_gps = ubicacion_gps;
    }

    public int getId_certificacion() {
        return id_certificacion;
    }

    public void setId_certificacion(int id_certificacion) {
        this.id_certificacion = id_certificacion;
    }

    public int getId_producto_obtenido() {
        return id_producto_obtenido;
    }

    public void setId_producto_obtenido(int id_producto_obtenido) {
        this.id_producto_obtenido = id_producto_obtenido;
    }
}
