package com.kelly.registerform.view.farming;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.kelly.registerform.adapters.modelsAdapter.MedidaChacraAdapter;
import com.kelly.registerform.dataAccess.practicas.AgricolaDA;
import com.kelly.registerform.dataAccess.practicas.BiodiversidadDA;
import com.kelly.registerform.dataAccess.practicas.ManejoSueloDA;
import com.kelly.registerform.dataAccess.practicas.PlagaDA;
import com.kelly.registerform.model.MedidaChacra;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.model.practicas.ManejoSuelo;
import com.thomashaertel.widget.MultiSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SystemProductionActivity extends AppCompatActivity {
    private Button b_next,b_back;
    private Context context=this;
    private String list;
    private MultiSpinner spinnerMulti1,spinnerMulti2,spinnerMulti3,spinnerMulti4;
    private ArrayAdapter adapter1,adapter2,adapter3,adapter4;
    private List<String>s1,s2,s3,s4;
    private ArrayList<Integer> i1,i2,i3,i4,selected1,selected2,selected3,selected4;
    private StringBuilder spinnerMulti1List1,spinnerMulti1List2,spinnerMulti1List3,spinnerMulti1List4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_production);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setElements();
        setActions();
    }
    private void setElements(){
        b_next= findViewById(R.id.b_next);
        list = getIntent().getStringExtra("list");
        b_back= findViewById(R.id.b_back);
        spinnerMulti1=findViewById(R.id.spinnerMulti1);
        spinnerMulti2=findViewById(R.id.spinnerMulti2);
        spinnerMulti3=findViewById(R.id.spinnerMulti3);
        spinnerMulti4=findViewById(R.id.spinnerMulti4);
        spinnerMulti1List1= new StringBuilder();
        spinnerMulti1List2= new StringBuilder();
        spinnerMulti1List3= new StringBuilder();
        spinnerMulti1List4= new StringBuilder();

        i1=new ArrayList<>();
        selected1=new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        s1=ManejoSueloDA.getListName();
        adapter1.addAll(s1);


        i2=new ArrayList<>();
        selected2=new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        s2= PlagaDA.getListName();
        adapter2.addAll(s2);

        i3=new ArrayList<>();
        s3=new ArrayList<>();
        selected3=new ArrayList<>();
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        s3= AgricolaDA.getListName();
        adapter3.addAll(s3);

        i4=new ArrayList<>();
        s4=new ArrayList<>();
        selected4=new ArrayList<>();
        adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        s4= BiodiversidadDA.getListName();
        adapter4.addAll(s4);


        spinnerMulti1.setAdapter(adapter1, false, onSelectedListener1);
        spinnerMulti2.setAdapter(adapter2, false, onSelectedListener2);
        spinnerMulti3.setAdapter(adapter3, false, onSelectedListener3);
        spinnerMulti4.setAdapter(adapter4, false, onSelectedListener4);
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
                sendMain();
                Intent intent = new Intent(context,FarmingCertificationActivity.class);
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
        JsonObject suelo = new JsonObject();
        for (int i=0;i<selected1.size();i++){
            suelo.addProperty(""+i,selected1.get(i));
        }
        MainJson.addChild("practicas_manejo_suelos",suelo);

        JsonObject plagas = new JsonObject();
        for (int i=0;i<selected2.size();i++){
            plagas.addProperty(""+i,selected2.get(i));
        }
        MainJson.addChild("practicas_plagas",plagas);

        JsonObject agricolas = new JsonObject();
        for (int i=0;i<selected3.size();i++){
            agricolas.addProperty(""+i,selected3.get(i));
        }
        MainJson.addChild("practicas_agricolas",agricolas);

        JsonObject biodiversidad = new JsonObject();
        for (int i=0;i<selected4.size();i++){
            biodiversidad.addProperty(""+i,selected4.get(i));
        }
        MainJson.addChild("practicas_biodiversidad",biodiversidad);
        MainJson.printBody();

    }
    private boolean validation(){
        if(selected1.size()==0){
            Toast.makeText(context, "Debe escoger al menos una práctica de conservación o manejo.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selected2.size()==0){
            Toast.makeText(context, "Debe escoger al menos una práctica manejo de plagas o enfermedades.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selected4.size()==0){
            Toast.makeText(context, "Debe escoger al menos una semilla o biodiversidad.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private MultiSpinner.MultiSpinnerListener onSelectedListener1 = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {

            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected1.add(Integer.parseInt(ManejoSueloDA.getIdByName(s1.get(i))));

                }
            }

        }
    };
    private MultiSpinner.MultiSpinnerListener onSelectedListener2 = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected2.add(Integer.parseInt(PlagaDA.getIdByName(s2.get(i))));
                }
            }
        }
    };
    private MultiSpinner.MultiSpinnerListener onSelectedListener3 = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected3.add(Integer.parseInt(AgricolaDA.getIdByName(s3.get(i))));
                }
            }
        }
    };
    private MultiSpinner.MultiSpinnerListener onSelectedListener4 = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected4.add(Integer.parseInt(BiodiversidadDA.getIdByName(s4.get(i))));
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

                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("practica");
                                    lista.add(name);
                                    lista_i.add(idDis);
                                }else{

                                    String name= (String)jsonObj2.get(key);
                                    lista.add(name);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
