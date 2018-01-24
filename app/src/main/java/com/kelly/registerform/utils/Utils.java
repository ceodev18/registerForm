package com.kelly.registerform.utils;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by KELLY on 24/01/2018.
 */

public class Utils {
    public static String getStringFromFile(String route, Context context){
        InputStream iS;
        String str=null;
        try{
            iS = context.getAssets().open(route);
            int size = iS.available();
            byte[] buffer = new byte[size];
            iS.read(buffer);
            iS.close();
            str = new String(buffer);
            return str;
        }catch(Exception e){

        }
        return str;
    }
}
