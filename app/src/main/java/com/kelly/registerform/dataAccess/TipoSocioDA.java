package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.TipoSocio;
import com.kelly.registerform.model.ubigeo.Departamento;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class TipoSocioDA {
    public static List<TipoSocio> list=null;

    public TipoSocioDA(Context context){
        list = TipoSocio.listAll(TipoSocio.class);
    }
}
