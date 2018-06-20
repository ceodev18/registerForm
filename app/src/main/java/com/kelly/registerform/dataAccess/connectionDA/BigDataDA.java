package com.kelly.registerform.dataAccess.connectionDA;

import android.content.Context;

import com.kelly.registerform.model.Asociacion;
import com.kelly.registerform.model.connection.BigData;

import java.util.List;

/**
 * Created by italo on 29/04/18.
 */

public class BigDataDA {
    public static List<BigData> list=null;

    public BigDataDA(Context context){
        list = BigData.listAll(BigData.class);
    }
    public static void addChild(BigData bigData){
        bigData.save();
    }
}
