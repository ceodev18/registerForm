package com.kelly.registerform.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by italo on 15/03/18.
 */

public class SharedPreferencesManager {
    private static final String PREFERENCES_NAME = "blcc";
    private static final String CURRENT_USER = "no";


    private static SharedPreferencesManager self;
    private final SharedPreferences mPreferences;

    private SharedPreferencesManager(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if (self == null) {
            self = new SharedPreferencesManager(context);
        }

        return self;
    }

    public void saveState(){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(CURRENT_USER, "cargado");
        editor.apply();
    }
    public String getState(){
        return mPreferences.getString(CURRENT_USER, "");
    }

}