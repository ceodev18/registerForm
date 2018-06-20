package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.EstadoCivil;
import com.kelly.registerform.model.GrupoProductoTransformado;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class GrupoProductoTransformadoDA {
    public static List<GrupoProductoTransformado> list=null;

    public GrupoProductoTransformadoDA(Context context){
        list = GrupoProductoTransformado.listAll(GrupoProductoTransformado.class);
    }
}
