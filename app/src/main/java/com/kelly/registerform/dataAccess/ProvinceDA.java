package com.kelly.registerform.dataAccess;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kelly.registerform.BuildConfig;
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
import static com.kelly.registerform.dataAccess.DepartmentDA.stateDeparment;

/**
 * Created by bethzabe on 21/02/2018.
 */

public class ProvinceDA {
    public static List<Provincia> provinciaList=null;
    public static RequestQueue queue= SaveDataBase.queue;
    public ProvinceDA(Context context){
        provinciaList=Provincia.listAll(Provincia.class);
    }
    private static void getProvincesByID(final String idParent){

        String url = BuildConfig.BASE_URL+"lista_ubigeo.php?tipo=PROV&idubigeosuperior="+idParent+"&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            System.out.println(jsonObj);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("ubigeos");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    Provincia provincia = new Provincia(id,idParent,name);
                                    if (!stateProvincia(id)) {
                                        provincia.save();
                                    }
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
    public  static void getProvinces(){

        List<Departamento>  list= Departamento.listAll(Departamento.class);
        for (Departamento d : list) {
            getProvincesByID(d.getId_departamento());
        }
    }

    public static boolean stateProvincia(String id){
        System.out.println("provinciaList size "+provinciaList.size());
        if (provinciaList.size()==0 || provinciaList==null)return false;
        for (int i=0;i<provinciaList.size();i++){
            if(id.equals(provinciaList.get(i).getId_provincia()))return true;
        }
        return false;
    }
}
