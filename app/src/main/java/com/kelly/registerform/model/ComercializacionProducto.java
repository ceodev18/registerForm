package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class ComercializacionProducto {
    public ComercializacionProducto(){

    }
    private int id_comercializacion;
    private int id_socio;
    private int id_producto_comercializado;
    private String es_solo_comprador;
    private String a_quien_compra_nombre;
    private String a_quien_compra_dni;
    private String a_quien_compra_certificado;
    private String a_quien_compra_lugar;
    private String ruc;
    private String marca_individual;
    private String boleta_factura;
    private String marca_colectiva;
    private int id_donde_vende;
    private String nombre_lugar_donde_vende;
    private float porcentaje_venta;
    private float cantidad;
    private String frecuencia;

    public int getId_comercializacion() {
        return id_comercializacion;
    }

    public void setId_comercializacion(int id_comercializacion) {
        this.id_comercializacion = id_comercializacion;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public int getId_producto_comercializado() {
        return id_producto_comercializado;
    }

    public void setId_producto_comercializado(int id_producto_comercializado) {
        this.id_producto_comercializado = id_producto_comercializado;
    }

    public String getEs_solo_comprador() {
        return es_solo_comprador;
    }

    public void setEs_solo_comprador(String es_solo_comprador) {
        this.es_solo_comprador = es_solo_comprador;
    }

    public String getA_quien_compra_nombre() {
        return a_quien_compra_nombre;
    }

    public void setA_quien_compra_nombre(String a_quien_compra_nombre) {
        this.a_quien_compra_nombre = a_quien_compra_nombre;
    }

    public String getA_quien_compra_dni() {
        return a_quien_compra_dni;
    }

    public void setA_quien_compra_dni(String a_quien_compra_dni) {
        this.a_quien_compra_dni = a_quien_compra_dni;
    }

    public String getA_quien_compra_certificado() {
        return a_quien_compra_certificado;
    }

    public void setA_quien_compra_certificado(String a_quien_compra_certificado) {
        this.a_quien_compra_certificado = a_quien_compra_certificado;
    }

    public String getA_quien_compra_lugar() {
        return a_quien_compra_lugar;
    }

    public void setA_quien_compra_lugar(String a_quien_compra_lugar) {
        this.a_quien_compra_lugar = a_quien_compra_lugar;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getMarca_individual() {
        return marca_individual;
    }

    public void setMarca_individual(String marca_individual) {
        this.marca_individual = marca_individual;
    }

    public String getBoleta_factura() {
        return boleta_factura;
    }

    public void setBoleta_factura(String boleta_factura) {
        this.boleta_factura = boleta_factura;
    }

    public String getMarca_colectiva() {
        return marca_colectiva;
    }

    public void setMarca_colectiva(String marca_colectiva) {
        this.marca_colectiva = marca_colectiva;
    }

    public int getId_donde_vende() {
        return id_donde_vende;
    }

    public void setId_donde_vende(int id_donde_vende) {
        this.id_donde_vende = id_donde_vende;
    }

    public String getNombre_lugar_donde_vende() {
        return nombre_lugar_donde_vende;
    }

    public void setNombre_lugar_donde_vende(String nombre_lugar_donde_vende) {
        this.nombre_lugar_donde_vende = nombre_lugar_donde_vende;
    }

    public float getPorcentaje_venta() {
        return porcentaje_venta;
    }

    public void setPorcentaje_venta(float porcentaje_venta) {
        this.porcentaje_venta = porcentaje_venta;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
}
