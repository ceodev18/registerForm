package com.kelly.registerform.model.ubigeo;

import com.google.gson.JsonObject;

/**
 * Created by italo on 7/03/18.
 */

public class Principal {
    private JsonObject json;
    private int id;
    public Principal(){

    }
    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void insertJson(){

    }
}
