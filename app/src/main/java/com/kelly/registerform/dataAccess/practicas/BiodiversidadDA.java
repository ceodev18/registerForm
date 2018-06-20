package com.kelly.registerform.dataAccess.practicas;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.practicas.Biodiversidad;
import com.kelly.registerform.model.practicas.ManejoSuelo;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class BiodiversidadDA {
    public static List<Biodiversidad> list=null;

    public static final RequestQueue queue= SaveDataBase.queue;

    public BiodiversidadDA(Context context){
        list = Biodiversidad.listAll(Biodiversidad.class);
    }

    public static List<String> getListName(){
        List<String> arrayList = new ArrayList<>();
        List<Biodiversidad> listN = Biodiversidad.listAll(Biodiversidad.class);
        for(Biodiversidad b : listN){
            arrayList.add(b.getName());
        }
        return arrayList;
    }
    public static String getIdByName(String name){
        List<String> arrayList = new ArrayList<>();
        for(Biodiversidad b : list){
            if(b.getName().equals(name))return b.getId_main();
        }
        return "0";

    }


}
