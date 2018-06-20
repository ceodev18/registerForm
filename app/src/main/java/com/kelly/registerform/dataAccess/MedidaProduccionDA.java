package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.MedidaChacra;
import com.kelly.registerform.model.MedidaProduccion;

import java.util.List;

/**
 * Created by italo on 3/05/18.
 */

public class MedidaProduccionDA {
    public static List<MedidaProduccion> list=null;

    public MedidaProduccionDA(Context context){
        list = MedidaProduccion.listAll(MedidaProduccion.class);
    }
}
