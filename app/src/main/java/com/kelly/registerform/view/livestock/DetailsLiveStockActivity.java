package com.kelly.registerform.view.livestock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.kelly.registerform.view.MapsActivity;
import com.kelly.registerform.view.farming.ProductionActivity;
import com.thomashaertel.widget.MultiSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DetailsLiveStockActivity extends AppCompatActivity {
    private Context context=this;
    private int VALOR_RETORNO = 1;
    private String position;
    private static final String BACKGROUND_COLOR = "color";
    private ViewGroup rootView;
    private Spinner s_tipo,s_manejo,s_propio,s_venta,spinner_especie_animal,s_animal,s_total_animales;
    private ArrayList<Integer> idTipo,idManejo,idDerivado;
    private ArrayList<String>arrayListTipo,arrayListManejo,arrayListDerivado;
    private Button b_map,b_save;
    private EditText et_longitude,et_latitude;
    private MultiSpinner spinner;

    private ArrayList<String>s1;
    private ArrayList<Integer> i1,selected1;
    private ArrayAdapter<String> adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stock_farming);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        position=getIntent().getStringExtra("index");
        int aux = Integer.parseInt(position);
        aux++;
        ab.setTitle("Granja "+aux);
        setElements();
        //fillDerivatesProducts();
        setActions();
    }

    private void setElements(){
        s_total_animales=findViewById(R.id.s_total_animales);
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Elija");
        for (int j=1;j<101;j++){
            lista.add(j+"");
        }
        for (int j=150;j<501;j+=50){
            lista.add(j+"");
        }
        ArrayAdapter<String> adapterSize = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,lista);

        s_total_animales.setAdapter(adapterSize);

        spinner = findViewById(R.id.spinnerMultiDetail);
        spinner_especie_animal =findViewById(R.id.spinner_especie_animal);
        s_animal =findViewById(R.id.s_animal);


        s_propio=findViewById(R.id.s_propio);
        s_venta=findViewById(R.id.s_venta);
        et_longitude = findViewById(R.id.et_longitud);
        et_latitude = findViewById(R.id.et_latitud);
        b_map=findViewById(R.id.b_map);
        b_save=findViewById(R.id.b_save);
        b_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        s_tipo=findViewById(R.id.s_tipo);
        s_manejo=findViewById(R.id.s_manejo);
        fillTipo();
        fillManejo();

        i1=new ArrayList<>();
        s1=new ArrayList<>();
        selected1=new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        fillContent(s1,adapter1,i1);
        spinner.setAdapter(adapter1, false, onSelectedListener);
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
    private void fillTipo(){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_alimento.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_alimento");
                            Iterator<?> keys = jsonObj2.keys();
                            idTipo=new ArrayList<>();
                            idTipo.add(0);
                            arrayListTipo=new ArrayList<>();
                            arrayListTipo.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                //System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("alimento");
                                    idTipo.add(id);
                                    arrayListTipo.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idTipo.add(Integer.parseInt(key));
                                    arrayListTipo.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListTipo);
                            s_tipo.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            s_tipo.setAdapter(null);
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

    private void fillManejo(){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_manejo_crianza.php?token=lpsk.21568$lsjANPIO022";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_manejo_crianza");
                            Iterator<?> keys = jsonObj2.keys();
                            idManejo=new ArrayList<>();
                            idManejo.add(0);
                            arrayListManejo=new ArrayList<>();
                            arrayListManejo.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                //System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("manejo_crianza");
                                    idManejo.add(id);
                                    arrayListManejo.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idManejo.add(Integer.parseInt(key));
                                    arrayListManejo.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListManejo);
                            s_manejo.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            s_manejo.setAdapter(null);
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

    private void setActions(){
        s_propio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!s_propio.getSelectedItem().toString().equals("Elija")){
                    int val = 100-Integer.parseInt(s_propio.getSelectedItem().toString());
                    String arr[]={val+""};
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,   android.R.layout.simple_spinner_item, arr);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    s_venta.setAdapter(spinnerArrayAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
                JsonObject chacraData = recoverData();
                LivestockProductionActivity.jsonObjectArrayList.set(Integer.parseInt(position),chacraData);
                Intent returnIntent = new Intent();
                //returnIntent.putExtra("result",et_parcela_name.getText().toString()+","+position)
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected1.add(i1.get(i));
                }
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
        }
        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO )) {
            //Procesar el resultado
            String result=data.getStringExtra("result");
            if(result!=null){
                String[]datos=result.split(",");
                et_longitude.setText(datos[1]);
                et_latitude.setText(datos[0]);
            }else{
                Uri uri = data.getData(); //obtener el uri content
            }

        }
    }
    private void fillContent(final ArrayList<String>lista,final ArrayAdapter adapter,final ArrayList<Integer>lista_i){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_productos_derivados.php?token=lpsk.21568$lsjANPIO02";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("productos_derivados");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();

                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("producto_derivado");
                                    System.out.println("derivado "+name);
                                    lista.add(name);
                                    lista_i.add(idDis);
                                }else{

                                    String name= (String)jsonObj2.get(key);
                                    lista_i.add(Integer.parseInt(key+""));
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
    public JsonObject recoverData(){
        JsonObject granja = new JsonObject();
        //if(!recoverData()) return null;

        granja.addProperty("id_tipo_animal",spinner_especie_animal.getSelectedItem().toString());
        granja.addProperty("id_animal",s_animal.getSelectedItem().toString());
        granja.addProperty("ubicacion_latitud_gps",et_latitude.getText().toString());
        granja.addProperty("ubicacion_longitud_gps",et_longitude.getText().toString());
        granja.addProperty("total_animales",s_total_animales.getSelectedItem().toString());
        granja.addProperty("id_tipo_alimento",s_tipo.getSelectedItem().toString());
        granja.addProperty("porcentaje_consumo_propio",s_propio.getSelectedItem().toString());
        granja.addProperty("porcentaje_venta",s_venta.getSelectedItem().toString());
        granja.addProperty("id_manejo_crianza",s_manejo.getSelectedItem().toString());

        JsonObject derivados = new JsonObject();
        for (int i=0;i<selected1.size();i++){
            derivados.addProperty(""+i,selected1.get(i));
        }
        granja.add("producto_derivado",derivados);
        System.out.println(granja);
        return granja;
    }
    private boolean validation(){
        if(spinner_especie_animal.getSelectedItem().toString().equals("Elija") ||
                spinner_especie_animal.getSelectedItem().toString().length()==0) {
            Toast.makeText(context, "Escoja un especie animal.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_animal.getSelectedItem().toString().equals("Elija") ||
                s_animal.getSelectedItem().toString().length()==0) {
            Toast.makeText(context, "Escoja un animal", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_longitude.getText().toString().equals("") ||
                et_latitude.getText().toString().equals("")){
            Toast.makeText(context, "Presione el botón de ubicación", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_total_animales.getSelectedItem().toString().equals("Elija") ||
                s_total_animales.getSelectedItem().toString().length()==0) {
            Toast.makeText(context, "Escoja el total de animales", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_tipo.getSelectedItem().toString().equals("Elija") ||
                s_tipo.getSelectedItem().toString().length()==0) {
            Toast.makeText(context, "Escoja el tipo de alimentos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_tipo.getSelectedItem().toString().equals("Elija") ||
                s_tipo.getSelectedItem().toString().length()==0) {
            Toast.makeText(context, "Escoja el % de consumo propio.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_manejo.getSelectedItem().toString().equals("Elija") ||
                s_manejo.getSelectedItem().toString().length()==0) {
            Toast.makeText(context, "Escoja el manejo de crianza", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}
