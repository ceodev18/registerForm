package com.kelly.registerform.model;

/**
 * Created by italo on 18/03/18.
 */

public class Comercio {
    private String name;
    public Comercio(String name){
        this.setName(name);
    }
    public Comercio(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
