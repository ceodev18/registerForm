package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.ProductoDerivado;
import com.kelly.registerform.model.practicas.Agricola;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class ProductoDerivadoDA {
    public static List<ProductoDerivado> list=null;

    public static final RequestQueue queue= SaveDataBase.queue;

    public ProductoDerivadoDA(Context context){
        list = ProductoDerivado.listAll(ProductoDerivado.class);
    }

    public static List<String> getListName(){
        List<String> arrayList = new ArrayList<>();
        List<ProductoDerivado> listN = ProductoDerivado.listAll(ProductoDerivado.class);
        System.out.println("listN "+listN.size());
        for(ProductoDerivado a : listN){
            arrayList.add(a.getName());
        }
        return arrayList;
    }
    public static List<String> getListIds(){
        List<String> arrayList = new ArrayList<>();
        List<ProductoDerivado> listN = ProductoDerivado.listAll(ProductoDerivado.class);
        System.out.println("listN "+listN.size());
        for(ProductoDerivado a : listN){
            arrayList.add(a.getId_main());
        }
        return arrayList;
    }
    public static String getIdByName(String name){
        List<String> arrayList = new ArrayList<>();
        for(ProductoDerivado a : list){
            if(a.getName().equals(name))return a.getId_main();
        }
        return "0";

    }
}
