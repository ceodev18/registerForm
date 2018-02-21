package com.kelly.registerform.dataAccess;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kelly.registerform.model.Departamento;
import com.kelly.registerform.utils.SaveDataBase;
import com.kelly.registerform.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import static com.kelly.registerform.BuildConfig.BASE_URL;

/**
 * Created by KELLY on 20/02/2018.
 */

public class DepartmentDA {
    public static List<Departamento> departamentoList;
    public static final RequestQueue queue=SaveDataBase.queue;
    public DepartmentDA(Context context){
        departamentoList = Departamento.listAll(Departamento.class);
    }
    public static void getDepartaments(){
        //String url =BASE_URL+ Urls.GET_DEPARMENTS;
        String url ="http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=DEPT";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
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
