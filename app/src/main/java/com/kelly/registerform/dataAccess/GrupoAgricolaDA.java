package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.EstadoCivil;
import com.kelly.registerform.model.GrupoAgricola;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class GrupoAgricolaDA {
    public static List<GrupoAgricola> list=null;

    public GrupoAgricolaDA(Context context){
        list = GrupoAgricola.listAll(GrupoAgricola.class);
    }
}
