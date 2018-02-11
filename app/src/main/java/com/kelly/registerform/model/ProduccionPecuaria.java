package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class ProduccionPecuaria {
    public  ProduccionPecuaria(){

    }
    private int id_produccion_pecuaria;
    private int id_socio;
    private int id_certificacion;
    private int total_especies_crianza;

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

    public int getId_certificacion() {
        return id_certificacion;
    }

    public void setId_certificacion(int id_certificacion) {
        this.id_certificacion = id_certificacion;
    }

    public int getTotal_especies_crianza() {
        return total_especies_crianza;
    }

    public void setTotal_especies_crianza(int total_especies_crianza) {
        this.total_especies_crianza = total_especies_crianza;
    }
}
