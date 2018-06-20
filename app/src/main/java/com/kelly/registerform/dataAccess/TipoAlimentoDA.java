package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.kelly.registerform.model.TipoAlimento;
import com.kelly.registerform.model.ubigeo.Distrito;
import com.kelly.registerform.utils.SaveDataBase;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class TipoAlimentoDA {
    public static List<TipoAlimento> list=null;

    public static final RequestQueue queue= SaveDataBase.queue;

    public TipoAlimentoDA(Context context){
        list = TipoAlimento.listAll(TipoAlimento.class);
    }
}
