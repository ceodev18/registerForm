package com.kelly.registerform.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by italo on 22/04/18.
 */

public class TipoPropiedad extends SugarRecord {
    @Unique
    private String id_main;
    private String name;
    public TipoPropiedad(){

    }
    public TipoPropiedad(String id_main, String name){
        this.setId_main(id_main);
        this.setName(name);
    }

    public String getId_main() {
        return id_main;
    }

    public void setId_main(String id_main) {
        this.id_main = id_main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
