package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class ProductoTransformadoPresentacion {
    public ProductoTransformadoPresentacion(){}
    private int id_producto_transformado;
    private int id_transformacion;
    private int id_socio;
    private int id_tipo_presentacion;
    private int cantidad_producida_semana;

    public int getId_producto_transformado() {
        return id_producto_transformado;
    }

    public void setId_producto_transformado(int id_producto_transformado) {
        this.id_producto_transformado = id_producto_transformado;
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

    public int getId_tipo_presentacion() {
        return id_tipo_presentacion;
    }

    public void setId_tipo_presentacion(int id_tipo_presentacion) {
        this.id_tipo_presentacion = id_tipo_presentacion;
    }

    public int getCantidad_producida_semana() {
        return cantidad_producida_semana;
    }

    public void setCantidad_producida_semana(int cantidad_producida_semana) {
        this.cantidad_producida_semana = cantidad_producida_semana;
    }
}
