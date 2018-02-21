package com.kelly.registerform.dataAccess;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kelly.registerform.model.ubigeo.Departamento;
import com.kelly.registerform.model.ubigeo.Provincia;
import com.kelly.registerform.utils.SaveDataBase;
import com.kelly.registerform.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.kelly.registerform.BuildConfig.BASE_URL;

/**
 * Created by bethzabe on 21/02/2018.
 */

public class ProvinceDA {
    public   ArrayList<Provincia> provinciaList;
    public static RequestQueue queue= SaveDataBase.queue;
    public ProvinceDA(){
        provinciaList=new ArrayList<>();
    }
    public  ArrayList getDepartamentos(int id){
        //String url =BASE_URL+ Urls.GET_PROVINCES+id+Urls.TOKEN;
        String url ="http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=PROV&idubigeosuperior=150000&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            System.out.println(jsonObj);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("departamentos");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    provinciaList.add(new Provincia(id,name));
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
        queue.add(stringRequest);
        return provinciaList;
    }

}
