package com.kelly.registerform.dataAccess.practicas;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.practicas.Biodiversidad;
import com.kelly.registerform.model.practicas.ManejoSuelo;
import com.kelly.registerform.model.practicas.Plaga;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class PlagaDA {
    public static List<Plaga> list=null;

    public static final RequestQueue queue= SaveDataBase.queue;

    public PlagaDA(Context context){
        list = Plaga.listAll(Plaga.class);
    }

    public static List<String> getListName(){
        List<String> arrayList = new ArrayList<>();
        List<Plaga> listN = Plaga.listAll(Plaga.class);
        for(Plaga p : listN){
            arrayList.add(p.getName());
        }
        return arrayList;
    }
    public static String getIdByName(String name){
        List<String> arrayList = new ArrayList<>();
        for(Plaga p : list){
            if(p.getName().equals(name))return p.getId_main();
        }
        return "0";

    }
}
