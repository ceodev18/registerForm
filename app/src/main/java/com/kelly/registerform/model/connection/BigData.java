package com.kelly.registerform.model.connection;

import com.google.gson.JsonObject;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.ArrayList;

/**
 * Created by italo on 29/04/18.
 */
@Table
public class BigData extends SugarRecord{
    private int id_position;
    private String  main;
    public  BigData(){

    }
    public  BigData(JsonObject  main){
        this.main=main.toString();
    }

    public int getId_position() {
        return id_position;
    }

    public void setId_position(int id_position) {
        this.id_position = id_position;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
