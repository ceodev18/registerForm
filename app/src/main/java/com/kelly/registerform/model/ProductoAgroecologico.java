package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class ProductoAgroecologico {
    public ProductoAgroecologico(){}
    private int id;
    private int id_tipo_producto;
    private int id_chacra;
    private int id_socio;
    private int id_producto_agroecologico;
    private String nombre_producto;
    private String tipo_cultivo;
    private float area_cultivo;
    private int id_tipo_medida_area_cultivo;
    private int fecha_siembra;
    private int fecha_cosecha;
    private int porcentaje_consumo_propio;
    private int porcentaje_venta;
    private int id_sistema_riego_usado;
    private String fecha_plantacion_mes;
    private String fecha_plantacion_ano;
    private String primera_cosecha_mes_desde;
    private String primera_cosecha_mes_hasta;
    private String segunda_cosecha_mes_desde;
    private String segunda_cosecha_mes_hasta;
    private String tercera_cosecha_mes_desde;
    private String tercera_cosecha_mes_hasta;
    private float produccion_estimada;
    private String id_tipo_medida_produccion_estimada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tipo_producto() {
        return id_tipo_producto;
    }

    public void setId_tipo_producto(int id_tipo_producto) {
        this.id_tipo_producto = id_tipo_producto;
    }

    public int getId_chacra() {
        return id_chacra;
    }

    public void setId_chacra(int id_chacra) {
        this.id_chacra = id_chacra;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public int getId_producto_agroecologico() {
        return id_producto_agroecologico;
    }

    public void setId_producto_agroecologico(int id_producto_agroecologico) {
        this.id_producto_agroecologico = id_producto_agroecologico;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getTipo_cultivo() {
        return tipo_cultivo;
    }

    public void setTipo_cultivo(String tipo_cultivo) {
        this.tipo_cultivo = tipo_cultivo;
    }

    public float getArea_cultivo() {
        return area_cultivo;
    }

    public void setArea_cultivo(float area_cultivo) {
        this.area_cultivo = area_cultivo;
    }

    public int getId_tipo_medida_area_cultivo() {
        return id_tipo_medida_area_cultivo;
    }

    public void setId_tipo_medida_area_cultivo(int id_tipo_medida_area_cultivo) {
        this.id_tipo_medida_area_cultivo = id_tipo_medida_area_cultivo;
    }

    public int getFecha_siembra() {
        return fecha_siembra;
    }

    public void setFecha_siembra(int fecha_siembra) {
        this.fecha_siembra = fecha_siembra;
    }

    public int getFecha_cosecha() {
        return fecha_cosecha;
    }

    public void setFecha_cosecha(int fecha_cosecha) {
        this.fecha_cosecha = fecha_cosecha;
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

    public int getId_sistema_riego_usado() {
        return id_sistema_riego_usado;
    }

    public void setId_sistema_riego_usado(int id_sistema_riego_usado) {
        this.id_sistema_riego_usado = id_sistema_riego_usado;
    }

    public String getFecha_plantacion_mes() {
        return fecha_plantacion_mes;
    }

    public void setFecha_plantacion_mes(String fecha_plantacion_mes) {
        this.fecha_plantacion_mes = fecha_plantacion_mes;
    }

    public String getFecha_plantacion_ano() {
        return fecha_plantacion_ano;
    }

    public void setFecha_plantacion_ano(String fecha_plantacion_ano) {
        this.fecha_plantacion_ano = fecha_plantacion_ano;
    }

    public String getPrimera_cosecha_mes_desde() {
        return primera_cosecha_mes_desde;
    }

    public void setPrimera_cosecha_mes_desde(String primera_cosecha_mes_desde) {
        this.primera_cosecha_mes_desde = primera_cosecha_mes_desde;
    }

    public String getPrimera_cosecha_mes_hasta() {
        return primera_cosecha_mes_hasta;
    }

    public void setPrimera_cosecha_mes_hasta(String primera_cosecha_mes_hasta) {
        this.primera_cosecha_mes_hasta = primera_cosecha_mes_hasta;
    }

    public String getSegunda_cosecha_mes_desde() {
        return segunda_cosecha_mes_desde;
    }

    public void setSegunda_cosecha_mes_desde(String segunda_cosecha_mes_desde) {
        this.segunda_cosecha_mes_desde = segunda_cosecha_mes_desde;
    }

    public String getSegunda_cosecha_mes_hasta() {
        return segunda_cosecha_mes_hasta;
    }

    public void setSegunda_cosecha_mes_hasta(String segunda_cosecha_mes_hasta) {
        this.segunda_cosecha_mes_hasta = segunda_cosecha_mes_hasta;
    }

    public String getTercera_cosecha_mes_desde() {
        return tercera_cosecha_mes_desde;
    }

    public void setTercera_cosecha_mes_desde(String tercera_cosecha_mes_desde) {
        this.tercera_cosecha_mes_desde = tercera_cosecha_mes_desde;
    }

    public String getTercera_cosecha_mes_hasta() {
        return tercera_cosecha_mes_hasta;
    }

    public void setTercera_cosecha_mes_hasta(String tercera_cosecha_mes_hasta) {
        this.tercera_cosecha_mes_hasta = tercera_cosecha_mes_hasta;
    }

    public float getProduccion_estimada() {
        return produccion_estimada;
    }

    public void setProduccion_estimada(float produccion_estimada) {
        this.produccion_estimada = produccion_estimada;
    }

    public String getId_tipo_medida_produccion_estimada() {
        return id_tipo_medida_produccion_estimada;
    }

    public void setId_tipo_medida_produccion_estimada(String id_tipo_medida_produccion_estimada) {
        this.id_tipo_medida_produccion_estimada = id_tipo_medida_produccion_estimada;
    }
}
