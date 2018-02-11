package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class ProductoTransformadoProduccion {
    public ProductoTransformadoProduccion(){}
    private int id_producto_transformado;
    private int id_transformacion   ;
    private int id_socio;
    private String mes_produccion_desde;
    private String mes_produccion_hasta;
    private String registro_sanitario;
    private String ruc;
    private String usa_codigo_barras;
    private int a_los_cuantos_meses_vence;
    private String razon_social_empresa;

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

    public String getMes_produccion_desde() {
        return mes_produccion_desde;
    }

    public void setMes_produccion_desde(String mes_produccion_desde) {
        this.mes_produccion_desde = mes_produccion_desde;
    }

    public String getMes_produccion_hasta() {
        return mes_produccion_hasta;
    }

    public void setMes_produccion_hasta(String mes_produccion_hasta) {
        this.mes_produccion_hasta = mes_produccion_hasta;
    }

    public String getRegistro_sanitario() {
        return registro_sanitario;
    }

    public void setRegistro_sanitario(String registro_sanitario) {
        this.registro_sanitario = registro_sanitario;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getUsa_codigo_barras() {
        return usa_codigo_barras;
    }

    public void setUsa_codigo_barras(String usa_codigo_barras) {
        this.usa_codigo_barras = usa_codigo_barras;
    }

    public int getA_los_cuantos_meses_vence() {
        return a_los_cuantos_meses_vence;
    }

    public void setA_los_cuantos_meses_vence(int a_los_cuantos_meses_vence) {
        this.a_los_cuantos_meses_vence = a_los_cuantos_meses_vence;
    }

    public String getRazon_social_empresa() {
        return razon_social_empresa;
    }

    public void setRazon_social_empresa(String razon_social_empresa) {
        this.razon_social_empresa = razon_social_empresa;
    }
}
