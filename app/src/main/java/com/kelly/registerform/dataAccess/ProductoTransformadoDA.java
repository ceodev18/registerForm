package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.ProductoCrianza;
import com.kelly.registerform.model.ProductoTransformado;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class ProductoTransformadoDA {
    public static List<ProductoTransformado> list=null;

    public ProductoTransformadoDA(Context context){
        list = ProductoTransformado.listAll(ProductoTransformado.class);
    }
}
