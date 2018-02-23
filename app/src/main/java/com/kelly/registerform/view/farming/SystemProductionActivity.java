package com.kelly.registerform.view.farming;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.BuildConfig;
import com.kelly.registerform.R;
import com.thomashaertel.widget.MultiSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SystemProductionActivity extends AppCompatActivity {
    private Button b_next,b_back;
    private Context context=this;
    private String list;
    private MultiSpinner spinnerMulti1,spinnerMulti2,spinnerMulti3,spinnerMulti4;
    private ArrayAdapter adapter1,adapter2,adapter3,adapter4;
    private ArrayList<String>s1,s2,s3,s4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_production);
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
        s1=new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        fillContent(s1,"manejo_suelos",adapter1);

        s2=new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        fillContent(s2,"plagas",adapter2);


        s3=new ArrayList<>();
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        fillContent(s3,"agricolas",adapter3);


        s4=new ArrayList<>();
        adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        fillContent(s4,"biodiversidad",adapter4);


        spinnerMulti1.setAdapter(adapter1, false, onSelectedListener);
        spinnerMulti2.setAdapter(adapter2, false, onSelectedListener);
        spinnerMulti3.setAdapter(adapter3, false, onSelectedListener);
        spinnerMulti4.setAdapter(adapter4, false, onSelectedListener);
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            // Do something here with the selected items
        }
    };
    private void fillContent(final ArrayList<String>lista,String value,final ArrayAdapter adapter){
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
