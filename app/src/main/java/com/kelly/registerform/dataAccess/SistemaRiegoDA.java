package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.Asociacion;
import com.kelly.registerform.model.SistemaRiego;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class SistemaRiegoDA {
    public static List<SistemaRiego> list=null;

    public SistemaRiegoDA(Context context){
        list = SistemaRiego.listAll(SistemaRiego.class);
    }
}
