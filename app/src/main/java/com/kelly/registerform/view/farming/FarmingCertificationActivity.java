package com.kelly.registerform.view.farming;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.BuildConfig;
import com.kelly.registerform.R;
import com.kelly.registerform.view.commerce.InformationActivity;
import com.kelly.registerform.view.livestock.LivestockProductionActivity;
import com.kelly.registerform.view.partner.ValidationActivity;
import com.kelly.registerform.view.transformation.ProcessActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class FarmingCertificationActivity extends AppCompatActivity {
    private String list;
    private Context context;
    private Button b_next,b_back,b_file;
    private int VALOR_RETORNO = 1;
    private Spinner s_tipo,s_company;
    private ArrayList<String> arrayListTipo,arrayListCompany;
    private ArrayList<Integer>idTipo,idCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farming_certification);
        setElements();
        setActions();
    }
    private void setElements(){
        context=this;
        b_next = findViewById(R.id.b_next);
        list= getIntent().getStringExtra("list");
        b_back = findViewById(R.id.b_back);
        b_file= findViewById(R.id.b_file);

        s_tipo=findViewById(R.id.s_tipo);
        arrayListTipo=new ArrayList<>();
        idTipo=new ArrayList<>();
        fillTipo();
        s_company=findViewById(R.id.s_company);
        arrayListCompany=new ArrayList<>();
        idCompany=new ArrayList<>();
        fillComapy();
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("La salidad es: "+list);
                if(list.equals("")){
                    Intent intent = new Intent(context, ValidationActivity.class);
                    startActivity(intent);
                }else{
                    String []listCheck =list.split(",");
                    int ini = Integer.parseInt(listCheck[0]);
                    String newList = reList(list);

                    if(ini==2){
                        Intent i = new Intent(context,LivestockProductionActivity.class);
                        i.putExtra("list",newList);
                        startActivity(i);
                    }
                    if(ini==3){
                        Intent i = new Intent(context,ProcessActivity.class);
                        i.putExtra("list",newList);
                        startActivity(i);
                    }
                    if(ini==4){
                        Intent i = new Intent(context,InformationActivity.class);
                        i.putExtra("list",newList);
                        startActivity(i);
                    }
                }
            }
        });
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        b_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Uri path= Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath());
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(path, "resource/folder");
                startActivity(Intent.createChooser(intent, "Open folder"));*/
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);
            }
        });
    }
    private String reList(String listSplit){
        String []listCheck =listSplit.split(",");
        String out ="";
        if(listCheck.length==1){
            return "";
        }else{
            for(int i=1;i<listCheck.length;i++){
                out+=listCheck[i]+",";
            }
            return out;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
        }
        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO )) {
            //Procesar el resultado
            Uri uri = data.getData(); //obtener el uri content
        }
    }
    private void fillTipo(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_empresas_certificadoras.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("empresas_certificadoras");
                            Iterator<?> keys = jsonObj2.keys();
                            idTipo=new ArrayList<>();
                            arrayListTipo=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idTipo.add(Integer.parseInt(key));
                                    System.out.println(name);
                                    arrayListTipo.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListTipo);
                            s_tipo.setAdapter(spinnerArrayAdapter);

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
    private void fillComapy(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_empresas_certificadoras_sgp.php?token=lpsk.21568$lsjANPIO022";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("empresas_sgp");
                            Iterator<?> keys = jsonObj2.keys();
                            idCompany=new ArrayList<>();
                            arrayListCompany=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idCompany.add(Integer.parseInt(key));
                                    System.out.println(name);
                                    arrayListCompany.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListCompany);
                            s_company.setAdapter(spinnerArrayAdapter);

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
