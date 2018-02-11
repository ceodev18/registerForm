package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class PracticaAgroecologica {
    public PracticaAgroecologica(){

    }
    private int id_practica;
    private int id_owner;
    private int id_socio;
    private int id_practica_agroecologica;
    private int id_tipo_practica_agroecologica;

    public int getId_practica() {
        return id_practica;
    }

    public void setId_practica(int id_practica) {
        this.id_practica = id_practica;
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

    public int getId_practica_agroecologica() {
        return id_practica_agroecologica;
    }

    public void setId_practica_agroecologica(int id_practica_agroecologica) {
        this.id_practica_agroecologica = id_practica_agroecologica;
    }

    public int getId_tipo_practica_agroecologica() {
        return id_tipo_practica_agroecologica;
    }

    public void setId_tipo_practica_agroecologica(int id_tipo_practica_agroecologica) {
        this.id_tipo_practica_agroecologica = id_tipo_practica_agroecologica;
    }
}
