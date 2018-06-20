package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.Asociacion;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */


public class AsociacionDA {
    public static List<Asociacion> list=null;

    public AsociacionDA(Context context){
        list = Asociacion.listAll(Asociacion.class);
    }
}
