package com.kelly.registerform.dataAccess.practicas;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.practicas.Agricola;
import com.kelly.registerform.model.practicas.ManejoSuelo;
import com.kelly.registerform.model.practicas.Plaga;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class AgricolaDA {
    public static List<Agricola> list=null;

    public static final RequestQueue queue= SaveDataBase.queue;

    public AgricolaDA(Context context){
        list = Agricola.listAll(Agricola.class);
    }

    public static List<String> getListName(){
        List<String> arrayList = new ArrayList<>();
        List<Agricola> listN = Agricola.listAll(Agricola.class);
        for(Agricola a : listN){
            arrayList.add(a.getName());
        }
        return arrayList;
    }
    public static String getIdByName(String name){
        List<String> arrayList = new ArrayList<>();
        for(Agricola a : list){
            if(a.getName().equals(name))return a.getId_main();
        }
        return "0";

    }
}
