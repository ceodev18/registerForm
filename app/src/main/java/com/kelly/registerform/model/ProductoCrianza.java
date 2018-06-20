package com.kelly.registerform.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by italo on 22/04/18.
 */

public class ProductoCrianza extends SugarRecord{
    @Unique
    private String id_main;
    private String id_parent;
    private String name;

    public ProductoCrianza(){

    }

    public ProductoCrianza(String id_main, String id_parent, String name){
        this.setId_main(id_main);
        this.setId_parent(id_parent);
        this.setName(name);
    }

    public String getId_main() {
        return id_main;
    }

    public void setId_main(String id_main) {
        this.id_main = id_main;
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
