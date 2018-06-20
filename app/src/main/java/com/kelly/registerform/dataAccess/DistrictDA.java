package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.ubigeo.Departamento;
import com.kelly.registerform.model.ubigeo.Distrito;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class DistrictDA {
    public static List<Distrito> distritoList=null;

    public static final RequestQueue queue= SaveDataBase.queue;

    public DistrictDA(Context context){
        distritoList = Distrito.listAll(Distrito.class);
    }
}
