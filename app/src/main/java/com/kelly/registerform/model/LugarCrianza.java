package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class LugarCrianza {
    public LugarCrianza(){

    }
    private int id_lugar_crianza;
    private int id_produccion_pecuaria;
    private int id_socio;
    private double latitud;
    private double longitud;
    private int total_animales;
    private int id_tipo_alimento;
    private int porcentaje_consumo_propio;
    private int porcentaje_venta;
    private int id_manejo_crianza;

    public int getId_lugar_crianza() {
        return id_lugar_crianza;
    }

    public void setId_lugar_crianza(int id_lugar_crianza) {
        this.id_lugar_crianza = id_lugar_crianza;
    }

    public int getId_produccion_pecuaria() {
        return id_produccion_pecuaria;
    }

    public void setId_produccion_pecuaria(int id_produccion_pecuaria) {
        this.id_produccion_pecuaria = id_produccion_pecuaria;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
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

    public int getTotal_animales() {
        return total_animales;
    }

    public void setTotal_animales(int total_animales) {
        this.total_animales = total_animales;
    }

    public int getId_tipo_alimento() {
        return id_tipo_alimento;
    }

    public void setId_tipo_alimento(int id_tipo_alimento) {
        this.id_tipo_alimento = id_tipo_alimento;
    }

    public int getPorcentaje_consumo_propio() {
        return porcentaje_consumo_propio;
    }

    public void setPorcentaje_consumo_propio(int porcentaje_consumo_propio) {
        this.porcentaje_consumo_propio = porcentaje_consumo_propio;
    }

    public int getPorcentaje_venta() {
        return porcentaje_venta;
    }

    public void setPorcentaje_venta(int porcentaje_venta) {
        this.porcentaje_venta = porcentaje_venta;
    }

    public int getId_manejo_crianza() {
        return id_manejo_crianza;
    }

    public void setId_manejo_crianza(int id_manejo_crianza) {
        this.id_manejo_crianza = id_manejo_crianza;
    }
}
