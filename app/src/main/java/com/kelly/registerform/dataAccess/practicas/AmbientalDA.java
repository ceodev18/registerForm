package com.kelly.registerform.dataAccess.practicas;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.practicas.Agricola;
import com.kelly.registerform.model.practicas.Ambiental;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class AmbientalDA {
    public static List<Ambiental> list=null;

    public static final RequestQueue queue= SaveDataBase.queue;

    public AmbientalDA(Context context){
        list = Ambiental.listAll(Ambiental.class);
    }

    public static List<String> getListName(){
        List<String> arrayList = new ArrayList<>();
        List<Ambiental> listN = Ambiental.listAll(Ambiental.class);
        for(Ambiental a : listN){
            arrayList.add(a.getName());
        }
        return arrayList;
    }
    public static String getIdByName(String name){
        List<String> arrayList = new ArrayList<>();
        for(Ambiental a : list){
            if(a.getName().equals(name))return a.getId_main();
        }
        return "0";

    }
}
