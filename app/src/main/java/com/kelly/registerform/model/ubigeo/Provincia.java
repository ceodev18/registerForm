package com.kelly.registerform.model.ubigeo;

/**
 * Created by bethzabe on 21/02/2018.
 */

public class Provincia {
    private int id;
    private String name;
    public Provincia(int id, String name){
        this.setId(id);
        this.setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
