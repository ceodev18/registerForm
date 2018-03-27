package com.kelly.registerform.view.livestock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.kelly.registerform.BuildConfig;
import com.kelly.registerform.R;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.view.farming.FarmingCertificationActivity;
import com.thomashaertel.widget.MultiSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class LiveStockEcoActivity extends AppCompatActivity {
    private Button b_next,b_back;
    private Context context=this;
    private String list;
    private MultiSpinner spinnerMulti1,spinnerMulti2,spinnerMulti3;
    private ArrayAdapter adapter1,adapter2,adapter3;
    private ArrayList<String> s1,s2,s3;
    private StringBuilder spinnerMulti1List1,spinnerMulti1List2,spinnerMulti1List3;
    private ArrayList<Integer> i1,i2,i3,selected1,selected2,selected3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stock_eco);
        setElements();
        setActions();
    }
    private void setElements(){
        b_next= findViewById(R.id.b_next);
        list = getIntent().getStringExtra("list");
        b_back= findViewById(R.id.b_back);
        spinnerMulti1 =findViewById(R.id.spinnerMulti1);
        spinnerMulti2 =findViewById(R.id.spinnerMulti2);
        spinnerMulti3 =findViewById(R.id.spinnerMulti3);
        spinnerMulti1List1= new StringBuilder();
        spinnerMulti1List2= new StringBuilder();
        spinnerMulti1List3= new StringBuilder();

        i1=new ArrayList<>();
        s1=new ArrayList<>();
        selected1=new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        fillContent(s1,"ambientales",adapter1,i1);

        i2=new ArrayList<>();
        s2=new ArrayList<>();
        selected2=new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        fillContent(s2,"alimentacion",adapter2,i2);

        i3=new ArrayList<>();
        s3=new ArrayList<>();
        selected3=new ArrayList<>();
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        fillContent(s3,"sanitario",adapter3,i3);

        spinnerMulti1.setAdapter(adapter1, false, onSelectedListener1);
        spinnerMulti2.setAdapter(adapter2, false, onSelectedListener2);
        spinnerMulti3.setAdapter(adapter3, false, onSelectedListener3);
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
                sendMain();
                Intent intent = new Intent(context,CertificationLiveStockActivity.class);
                intent.putExtra("list",list);
                startActivity(intent);
            }
        });
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void sendMain(){
        JsonObject sanitario = new JsonObject();
        for (int i=0;i<selected1.size();i++){
            sanitario.addProperty(""+i,selected1.get(i));
        }
        MainJson.addChild("practicas_sanitario",sanitario);

        JsonObject alimentacion = new JsonObject();
        for (int i=0;i<selected2.size();i++){
            alimentacion.addProperty(""+i,selected2.get(i));
        }
        MainJson.addChild("practicas_alimentacion",alimentacion);

        JsonObject ambientales = new JsonObject();
        for (int i=0;i<selected3.size();i++){
            ambientales.addProperty(""+i,selected3.get(i));
        }
        MainJson.addChild("practicas_ambientales",ambientales);

        MainJson.printBody();

    }
    private boolean validation(){
        if(selected1.size()==0){
            Toast.makeText(context, "Debe escoger al menos una práctica de sanitaria.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selected2.size()==0){
            Toast.makeText(context, "Debe escoger al menos una práctica de alimentación.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selected3.size()==0){
            Toast.makeText(context, "Debe escoger al menos una práctica ambiental.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private MultiSpinner.MultiSpinnerListener onSelectedListener1 = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected1.add(i1.get(i));

                }
            }
        }
    };
    private MultiSpinner.MultiSpinnerListener onSelectedListener2 = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected2.add(i2.get(i));

                }
            }
        }
    };
    private MultiSpinner.MultiSpinnerListener onSelectedListener3= new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected3.add(i3.get(i));
                }
            }
        }
    };
    private void fillContent(final ArrayList<String>lista,String value,final ArrayAdapter adapter,final ArrayList<Integer>lista_i){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_practicas_agroecologicas.php?tipo="+value+"&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("practicas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("practica");
                                    lista.add(name);
                                    lista_i.add(idDis);
                                }else{

                                    String name= (String)jsonObj2.get(key);
                                    lista.add(name);
                                    System.out.println(lista.size());
                                }
                            }
                            adapter.addAll(lista);

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
}
