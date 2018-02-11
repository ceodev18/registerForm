package com.kelly.registerform.model;

/**
 * Created by KELLY on 11/02/2018.
 */

public class DatosHijos {
    public DatosHijos(){

    }
    private int id;
    private int id_socio;
    private String nombres_apellidos;
    private int fecha_nacimiento;
    private boolean dependiente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public String getNombres_apellidos() {
        return nombres_apellidos;
    }

    public void setNombres_apellidos(String nombres_apellidos) {
        this.nombres_apellidos = nombres_apellidos;
    }

    public int getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(int fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public boolean isDependiente() {
        return dependiente;
    }

    public void setDependiente(boolean dependiente) {
        this.dependiente = dependiente;
    }
}
