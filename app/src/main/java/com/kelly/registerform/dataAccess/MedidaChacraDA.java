package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.MedidaChacra;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class MedidaChacraDA {
    public static List<MedidaChacra> list=null;

    public MedidaChacraDA(Context context){
        list = MedidaChacra.listAll(MedidaChacra.class);
    }
}
