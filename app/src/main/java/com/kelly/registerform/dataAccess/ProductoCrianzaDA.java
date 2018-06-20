package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.ProductoAgricola;
import com.kelly.registerform.model.ProductoCrianza;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class ProductoCrianzaDA {
    public static List<ProductoCrianza> list=null;

    public ProductoCrianzaDA(Context context){
        list = ProductoCrianza.listAll(ProductoCrianza.class);
    }
}
