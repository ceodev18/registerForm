package com.kelly.registerform.model.main;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kelly.registerform.model.connection.BigData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by italo on 11/03/18.
 */

public class MainJson {
    private int id;
    public static ArrayList<JsonObject>arrayList;
    public static ArrayList<String>keyList;
    public static Context context;
    private ProgressDialog progressDoalog;
    public static String code="";
    public  MainJson(Context context){
        arrayList=new ArrayList<>();
        keyList=new ArrayList<>();
        this.context=context;
    }
    public static void addChild(String key, JsonObject content){
        arrayList.add(content);
        keyList.add(key);
    }
    public static void printBody(){
        for (int i=0;i<arrayList.size();i++){
            System.out.println(keyList.get(i).toString());
            System.out.println(arrayList.get(i).toString());
        }

    }
    public static void saveChild(){
        JsonObject main = new JsonObject();
        JsonObject info = new JsonObject();
        for (int i=0;i<arrayList.size();i++){
            if(i==0){
                try {
                    JSONObject jo2 = new JSONObject(arrayList.get(i).toString());
                    Iterator<?> keys = jo2.keys();
                    while( keys.hasNext() ) {
                        String key = (String)keys.next();
                        System.out.println(key);
                        if ( jo2.get(key) instanceof JSONObject ) {
                            JSONObject jsonCorpe = (JSONObject) jo2.get(key);
                            JsonParser jsonParser = new JsonParser();
                            JsonObject gsonObject = (JsonObject)jsonParser.parse(jsonCorpe.toString());
                            main.add(key,gsonObject);
                        }else{

                            String name= jo2.get(key)+"";
                            main.addProperty(key,name);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{
                main.add(keyList.get(i),arrayList.get(i));
            }
        }

        info.add("info",main);
        info.addProperty("token", "lpsk.21568$lsjANPIO02");
        BigData bigData = new BigData(info);
        bigData.save();
    }
    public static void sendDB(){
        JsonObject main = new JsonObject();
        JsonObject info = new JsonObject();
        for (int i=0;i<arrayList.size();i++){
            if(i==0){
                try {
                    JSONObject jo2 = new JSONObject(arrayList.get(i).toString());
                    Iterator<?> keys = jo2.keys();
                    while( keys.hasNext() ) {
                        String key = (String)keys.next();
                        System.out.println(key);
                        if ( jo2.get(key) instanceof JSONObject ) {
                            JSONObject jsonCorpe = (JSONObject) jo2.get(key);
                            JsonParser jsonParser = new JsonParser();
                            JsonObject gsonObject = (JsonObject)jsonParser.parse(jsonCorpe.toString());
                            main.add(key,gsonObject);
                        }else{

                            String name= jo2.get(key)+"";
                            main.addProperty(key,name);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{
                main.add(keyList.get(i),arrayList.get(i));
            }
        }

        info.add("info",main);
        info.addProperty("token", "lpsk.21568$lsjANPIO02");
        System.out.println(info.toString());
        sendDataBase(info);
    }
    public static void sendDataBase(JsonObject body){
        String url ="http://www.demodataexe.com/anpe/webservice/guardar_info_socio.php";
        JSONObject object=null;
        try {
            object= new JSONObject(body.toString());
            System.out.println(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    String code = response.getString("codigo");
                    MainJson.code=code;
                    System.out.println("This is the responde code from server"+code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jr);
    }
    public static void saveFromNoConnection(String info){
        String url ="http://www.demodataexe.com/anpe/webservice/guardar_info_socio.php";
        JSONObject object=null;
        try {
            object= new JSONObject(info);
            System.out.println(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    String code = response.getString("codigo");
                    MainJson.code=code;
                    System.out.println("This is the responde code from server"+code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jr);

    }
    public static JsonObject getByKey(String key) {
        if(arrayList.size()==0)return null;
        for (int i=0;i<arrayList.size();i++){
            if(keyList.get(i).equals(key))return arrayList.get(i);
        }
        return null;
    }
}
