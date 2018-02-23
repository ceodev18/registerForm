package com.kelly.registerform.view.partner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.BuildConfig;
import com.kelly.registerform.R;
import com.kelly.registerform.model.ubigeo.Departamento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class RegistrationPartnerPartBActivity extends AppCompatActivity {
    private Spinner s_number_children;
    private LinearLayout ll_children;
    private Context context;
    private Button b_next,b_back;
    private Spinner s_marital;
    private ArrayList<String> arrayListMarital;
    private ArrayList<Integer>idMarital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_partner_part_b);
        setElements();
        setActions();
    }
    private void setElements(){
        context =this;
        s_number_children = (Spinner)findViewById(R.id.s_number_children);
        ll_children =  (LinearLayout)findViewById(R.id.ll_children);
        b_next = (Button)findViewById(R.id.b_next);
        b_back = (Button)findViewById(R.id.b_back);
        s_marital = findViewById(R.id.s_marital);
        fillMarital();
    }
    private void setActions(){
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        s_number_children.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!((String)adapterView.getItemAtPosition(i)).equals("Elija")){
                    int val = Integer.parseInt((String)adapterView.getItemAtPosition(i));
                    if(ll_children.getChildCount()>0)ll_children.removeAllViews();
                    for (int index=0;index<val;index++){
                        View dynamicView = LayoutInflater.from(context).inflate(R.layout.item_child, null, false);
                        ll_children.addView(dynamicView);
                    }
                }else{
                    if(ll_children.getChildCount()>0)ll_children.removeAllViews();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,WelcomePartnerActivity.class);
                startActivity(intent);
            }
        });
    }

    public  void fillMarital(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_estado_civil.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("estados_civiles");
                            Iterator<?> keys = jsonObj2.keys();
                            idMarital=new ArrayList<>();
                            arrayListMarital=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("estado_civil");
                                    System.out.println(name);
                                    idMarital.add(id);
                                    arrayListMarital.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idMarital.add(Integer.parseInt(key));
                                    System.out.println(name);
                                    arrayListMarital.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListMarital);
                            s_marital.setAdapter(spinnerArrayAdapter);

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
