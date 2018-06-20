package com.kelly.registerform.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by italo on 3/05/18.
 */

public class MedidaProduccion extends SugarRecord{
    @Unique
    private String id_main;
    private String name;

    public MedidaProduccion(){

    }

    public MedidaProduccion(String id_main, String name){
        this.id_main =id_main;
        this.name=name;
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
