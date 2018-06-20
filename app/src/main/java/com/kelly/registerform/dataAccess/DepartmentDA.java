package com.kelly.registerform.dataAccess;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kelly.registerform.adapters.modelsAdapter.SpinAdapter;
import com.kelly.registerform.model.ubigeo.Departamento;
import com.kelly.registerform.utils.SaveDataBase;
import com.kelly.registerform.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import static com.kelly.registerform.BuildConfig.BASE_URL;
import static com.kelly.registerform.model.main.MainJson.context;

/**
 * Created by KELLY on 20/02/2018.
 */

public class DepartmentDA {
    public static List<Departamento> departamentoList=null;

    public static final RequestQueue queue=SaveDataBase.queue;

    public DepartmentDA(Context context){
        departamentoList = Departamento.listAll(Departamento.class);
    }

    public static void getDepartaments(){
        //String url =BASE_URL+ Urls.GET_DEPARMENTS;
        String url ="http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=DEPT&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("ubigeos");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id =jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    Departamento departamento = new Departamento(id, name);
                                    if (!stateDeparment(id))departamento.save();
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

    public static boolean stateDeparment(String id){
        if(departamentoList==null)return false;
        if(departamentoList.size()==0)return false;
        for (int i=0;i<departamentoList.size();i++){
            if(id.equals(departamentoList.get(i).getId_departamento()))return true;
        }
        return false;
    }


}
