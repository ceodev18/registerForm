package com.kelly.registerform.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

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
    public static ProgressDialog createProgressDialog(Activity activity, String message) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }
    public static ProgressDialog createProgressDialog(Context activity, String message) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
