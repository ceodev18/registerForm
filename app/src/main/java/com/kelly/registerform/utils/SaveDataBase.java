package com.kelly.registerform.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.model.Book;
import com.kelly.registerform.model.Departamento;
import com.orm.SugarContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * Created by KELLY on 13/02/2018.
 */

public class SaveDataBase {
    private Context context;
    public static RequestQueue queue;
    public static List<Departamento> departamentoList;
    public SaveDataBase(Context context){
        this.context=context;
        queue = Volley.newRequestQueue(this.context);
        departamentoList = Departamento.listAll(Departamento.class);
    }
    public static void getDepartamentos(){

        Book.findById(Departamento.class, (long) 1);

// Instantiate the RequestQueue.

        String url ="http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=DEPT";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        //System.out.println(response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            //System.out.println("detalle");
                            //System.out.println(jsonObj.get("departamentos"));

                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("departamentos");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    Departamento departamento = new Departamento(id, name);
                                    //
                                    if (!stateDeparment(id)) departamento.save();
                                    //creamos un departamento y lo guardamos
                                    //System.out.println(jsonObj2.get(key));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public static boolean stateDeparment(int id){
        if (departamentoList.size()==0)return true;
        for (int i=0;i<departamentoList.size();i++){
            if(id==departamentoList.get(i).getId_departamento())return true;
        }
        return false;
    }
}
