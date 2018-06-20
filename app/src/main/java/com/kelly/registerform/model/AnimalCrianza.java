package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class AnimalCrianza   {
    public AnimalCrianza(){
        this.nombre="";
    }
    private int id_animal_crianza;
    private int id_produccion_pecuaria;
    private int id_lugar_crianza;
    private int id_socio;
    private int id_tipo_animal;
    private int id_animal;
    private String nombre;

    public int getId_animal_crianza() {
        return id_animal_crianza;
    }

    public void setId_animal_crianza(int id_animal_crianza) {
        this.id_animal_crianza = id_animal_crianza;
    }

    public int getId_produccion_pecuaria() {
        return id_produccion_pecuaria;
    }

    public void setId_produccion_pecuaria(int id_produccion_pecuaria) {
        this.id_produccion_pecuaria = id_produccion_pecuaria;
    }

    public int getId_lugar_crianza() {
        return id_lugar_crianza;
    }

    public void setId_lugar_crianza(int id_lugar_crianza) {
        this.id_lugar_crianza = id_lugar_crianza;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public int getId_tipo_animal() {
        return id_tipo_animal;
    }

    public void setId_tipo_animal(int id_tipo_animal) {
        this.id_tipo_animal = id_tipo_animal;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
