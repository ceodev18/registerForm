package com.kelly.registerform.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.BuildConfig;

/**
 * Created by KELLY on 13/02/2018.
 */

public class SaveDataBase {
    private Context context;
    public  static RequestQueue queue;

    private static final String BASE_URL = BuildConfig.BASE_URL;
    public SaveDataBase(Context context){
        this.context=context;
        queue = Volley.newRequestQueue(this.context);

    }
    /*public static void getDepartamentos(){
        String url =BASE_URL+Urls.GET_DEPARMENTS;
        //String url ="http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=DEPT";
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
    }*/
    /*public static boolean stateDeparment(int id){
        if (departamentoList.size()==0)return true;
        for (int i=0;i<departamentoList.size();i++){
            if(id==departamentoList.get(i).getId_departamento())return true;
        }
        return false;
    }*/
}
