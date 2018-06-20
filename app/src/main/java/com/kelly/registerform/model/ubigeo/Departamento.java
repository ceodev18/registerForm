package com.kelly.registerform.model.ubigeo;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by KELLY on 13/02/2018.
 */

public class Departamento extends SugarRecord  {
    @Unique
    private String id_departamento;
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Departamento(){
    }
    public Departamento(String id_departamento,String name){
        this.setId_departamento(id_departamento);
        this.setName(name);
    }

    public String getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(String id_departamento) {
        this.id_departamento = id_departamento;
    }
}
