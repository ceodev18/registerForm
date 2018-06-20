package com.kelly.registerform.dataAccess.practicas;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.practicas.ManejoSuelo;
import com.kelly.registerform.model.ubigeo.Distrito;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class ManejoSueloDA {
    public static List<ManejoSuelo> list=null;

    public static final RequestQueue queue= SaveDataBase.queue;

    public ManejoSueloDA(Context context){
        list = ManejoSuelo.listAll(ManejoSuelo.class);
    }

    public static List<String> getListName(){
        List<ManejoSuelo> listN = ManejoSuelo.listAll(ManejoSuelo.class);
        list = ManejoSuelo.listAll(ManejoSuelo.class);
        List<String> arrayList = new ArrayList<>();
        for(ManejoSuelo m : listN){
            arrayList.add(m.getName());
        }
        return arrayList;
    }
    public static String getIdByName(String name){
        List<String> arrayList = new ArrayList<>();
        for(ManejoSuelo m : list){
            if(m.getName().equals(name))return m.getId_main();
        }
        return "0";

    }
}
