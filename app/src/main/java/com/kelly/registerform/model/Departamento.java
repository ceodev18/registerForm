package com.kelly.registerform.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by KELLY on 13/02/2018.
 */

public class Departamento extends SugarRecord  {
    @Unique
    private int id_departamento;
    private  String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Departamento(){
    }
    public Departamento(int id_departamento,String name){
        this.setId_departamento(id_departamento);
        this.setName(name);
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }
}
