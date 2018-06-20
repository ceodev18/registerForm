package com.kelly.registerform.model.ubigeo;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by bethzabe on 21/02/2018.
 */

public class Distrito extends SugarRecord{
    @Unique
    private String id_ditrito;
    private String id_parent;
    private String name;
    public Distrito(){

    }
    public Distrito(String id_ditrito,String id_parent,String name){
        this.setId_ditrito(id_ditrito);
        this.setName(name);
        this.id_parent=id_parent;
    }

    public String getId_ditrito() {
        return id_ditrito;
    }

    public void setId_ditrito(String id_ditrito) {
        this.id_ditrito = id_ditrito;
    }

    public String getId_parent() {
        return id_parent;
    }

    public void setId_parent(String id_parent) {
        this.id_parent = id_parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


