package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.EstadoCivil;
import com.kelly.registerform.model.TipoPropiedad;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class TipoPropiedadDA {
    public static List<TipoPropiedad> list=null;

    public TipoPropiedadDA(Context context){
        list = TipoPropiedad.listAll(TipoPropiedad.class);
    }
}
