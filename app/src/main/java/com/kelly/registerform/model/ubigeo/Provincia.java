package com.kelly.registerform.model.ubigeo;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by bethzabe on 21/02/2018.
 */

public class Provincia extends SugarRecord{
    @Unique
    private String id_provincia;
    private String id_parent;

    private String name;

    public Provincia(){
    }

    public Provincia(String id_provincia,String idParent ,String name){
        this.setId_provincia(id_provincia);
        this.setId_parent(idParent);
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(String id_provincia) {
        this.id_provincia = id_provincia;
    }

    public String getId_parent() {
        return id_parent;
    }

    public void setId_parent(String id_parent) {
        this.id_parent = id_parent;
    }
}


