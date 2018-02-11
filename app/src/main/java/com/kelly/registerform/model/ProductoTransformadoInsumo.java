package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class ProductoTransformadoInsumo {
    public ProductoTransformadoInsumo(){}
    private int id_producto_transformado;
    private int id_transformacion;
    private int id_socio;
    private int id_insumo;
    private String es_organico;
    private String propio_comprado;
    private String a_quien_compra;
    private String certificado_a_quien_compra;
    private String donde_compra_insumo;
    private int id_insumo_utilizado;

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

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public String getEs_organico() {
        return es_organico;
    }

    public void setEs_organico(String es_organico) {
        this.es_organico = es_organico;
    }

    public String getPropio_comprado() {
        return propio_comprado;
    }

    public void setPropio_comprado(String propio_comprado) {
        this.propio_comprado = propio_comprado;
    }

    public String getA_quien_compra() {
        return a_quien_compra;
    }

    public void setA_quien_compra(String a_quien_compra) {
        this.a_quien_compra = a_quien_compra;
    }

    public String getCertificado_a_quien_compra() {
        return certificado_a_quien_compra;
    }

    public void setCertificado_a_quien_compra(String certificado_a_quien_compra) {
        this.certificado_a_quien_compra = certificado_a_quien_compra;
    }

    public String getDonde_compra_insumo() {
        return donde_compra_insumo;
    }

    public void setDonde_compra_insumo(String donde_compra_insumo) {
        this.donde_compra_insumo = donde_compra_insumo;
    }

    public int getId_insumo_utilizado() {
        return id_insumo_utilizado;
    }

    public void setId_insumo_utilizado(int id_insumo_utilizado) {
        this.id_insumo_utilizado = id_insumo_utilizado;
    }
}
