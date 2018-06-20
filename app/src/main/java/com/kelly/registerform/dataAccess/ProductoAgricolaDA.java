package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.Asociacion;
import com.kelly.registerform.model.ProductoAgricola;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class ProductoAgricolaDA {
    public static List<ProductoAgricola> list=null;

    public ProductoAgricolaDA(Context context){
        list = ProductoAgricola.listAll(ProductoAgricola.class);
    }
}
