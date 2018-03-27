package com.kelly.registerform.view.commerce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.kelly.registerform.view.partner.ValidationActivity;
import com.kelly.registerform.view.transformation.ProcessActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class InformationActivity extends AppCompatActivity {
    final private Context context=this;
    private EditText et_name,et_dni,et_codigo,et_lugar,et_ruc,et_marca,et_marca_colectiva;
    private ArrayList<Boolean>listState;
    private ArrayList<TextView>listTextView;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private Button b_save;
    private String list,position;
    private Spinner s_boleta;
    private ArrayList<View> agroList;
    private ArrayList<String> tipoLista,nameProductos1,nameItem1;
    private ArrayList<Spinner>s_productos,s_grupos,s_items,s_grupoFalse,grupoCantidad,grupoKg,grupoFrecuencia;
    private ArrayList<Integer>idProductos1,idProductos2,idProductos3,idItem1,idItem2,idItem3;
    private ArrayList<String>etiquetas;
    private ArrayAdapter<String> spinnerArrayAdapter1,spinnerArrayAdapter2,spinnerArrayAdapter3;
    private boolean state=true;
    private int  id_tipo_producto=1;
    int[]cantidad;
    int []kg;
    int []frecuencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        position=getIntent().getStringExtra("index");
        int aux = Integer.parseInt(position);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        int counter = Integer.parseInt(position);
        counter++;
        ab.setTitle("Producto Comercial "+counter);
        setElements();
        setActions();

    }
    private void setElements(){
        cantidad = new int[] {R.id.s_cantidad1,R.id.s_cantidad2,R.id.s_cantidad3,
                R.id.s_cantidad4,R.id.s_cantidad5,R.id.s_cantidad6,R.id.s_cantidad7};
        kg = new int[] {R.id.s_kg1,R.id.s_kg2,R.id.s_kg3,
                R.id.s_kg4,R.id.s_kg5,R.id.s_kg6,R.id.s_kg7};
        frecuencia = new int[] {R.id.s_frecuencia1,R.id.s_frecuencia2,R.id.s_frecuencia3,
                R.id.s_frecuencia4,R.id.s_frecuencia5,R.id.s_frecuencia6,R.id.s_frecuencia7};
        list = getIntent().getStringExtra("list");
        listState = new ArrayList<>();
        listTextView = new ArrayList<>();
        linearLayoutArrayList= new ArrayList<>();

        b_save =(Button)findViewById(R.id.b_save);
        agroList = new ArrayList<>();


        tipoLista = new ArrayList<>();
        s_productos= new ArrayList<>();
        s_grupos= new ArrayList<>();
        s_items= new ArrayList<>();
        etiquetas =new ArrayList<>();
        etiquetas.add("almacen");
        etiquetas.add("mayorista");
        etiquetas.add("mercado");
        etiquetas.add("feria");
        etiquetas.add("ecotienda");
        etiquetas.add("supermercado");
        etiquetas.add("exportacion");

        grupoCantidad = new ArrayList<>();
        grupoKg = new ArrayList<>();
        grupoFrecuencia = new ArrayList<>();


        nameProductos1= new ArrayList<>();
        nameItem1= new ArrayList<>();

        idProductos1= new ArrayList<>();

        idItem1= new ArrayList<>();


        setAgroList();
    }
    private void setAgroList(){
        View agro1 = new View(context);
        agro1= findViewById(R.id.ll_agro1);

        agroList.add(agro1);

        et_name=agro1.findViewById(R.id.et_name);
        et_dni=agro1.findViewById(R.id.et_dni);
        et_codigo=agro1.findViewById(R.id.et_codigo);
        et_lugar=agro1.findViewById(R.id.et_lugar);
        et_ruc=agro1.findViewById(R.id.et_ruc_item);
        et_marca=agro1.findViewById(R.id.et_marca);
        et_marca_colectiva=agro1.findViewById(R.id.et_marca);
        s_boleta=agro1.findViewById(R.id.s_boleta);

        setSpinners();
        setSpinnerCantidad();
    }
    private void setSpinnerCantidad(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Elija");
        for (int j=1;j<101;j++){
            lista.add(j+"");
        }
        for (int j=150;j<501;j+=50){
            lista.add(j+"");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,lista);


        for (int i=0;i<agroList.size();i++){
            for (int j=0;j<cantidad.length;j++){

                Spinner  spinner1= agroList.get(i).findViewById(cantidad[j]);
                spinner1.setAdapter(adapter);

                Spinner  spinner2= agroList.get(i).findViewById(kg[j]);
                Spinner  spinner3= agroList.get(i).findViewById(frecuencia[j]);

                grupoCantidad.add(spinner1);
                grupoKg.add(spinner2);
                grupoFrecuencia.add(spinner3);
            }
        }
    }
    private void setSpinners(){
        for (int index=0;index<agroList.size();index++){
            final int super_index = index;
            final Spinner s_tipo = agroList.get(index).findViewById(R.id.s_tipo);
            final Spinner  s_grupo = agroList.get(index).findViewById(R.id.s_grupos);
            final Spinner  s_inner = agroList.get(index).findViewById(R.id.s_inners);
            s_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!s_tipo.getSelectedItem().toString().equals("Elija")){
                        //tipoLista.add(s_tipo.getSelectedItem().toString());
                        if(s_tipo.getSelectedItem().toString().equals("Producto Agroecológico")){
                            state=false;
                            if(super_index==0)agro1(s_grupo);
                            s_productos.add(s_tipo);
                            s_grupos.add(s_grupo);

                        }
                        if(s_tipo.getSelectedItem().toString().equals("Producto Pecuario")){
                            ArrayAdapter  adapter1 = new ArrayAdapter(context, android.R.layout.simple_spinner_item,
                                    getResources().getStringArray(R.array.sa_type_animal));

                            ArrayAdapter  adapter2 = new ArrayAdapter(context, android.R.layout.simple_spinner_item,
                                    getResources().getStringArray(R.array.sa_animal));

                            s_grupo.setAdapter(adapter1);
                            s_inner.setAdapter(adapter2);
                            s_productos.add(s_tipo);
                            s_grupos.add(s_grupo);
                            s_items.add(s_inner);

                        }
                        if(s_tipo.getSelectedItem().toString().equals("Producto Transformado")){
                            state=false;
                            if(super_index==0)tranformado1(s_grupo);
                            s_productos.add(s_tipo);
                            s_grupos.add(s_grupo);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            s_grupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!state){
                        if(!s_grupo.getSelectedItem().toString().equals("Elija")){
                            id_tipo_producto = Integer.parseInt(spinnerArrayAdapter1.getItem(i).toString());
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



        }
    }
    private void setActions(){
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
                JsonObject comercioJson = recoverData();
                ComercializacionActivity.jsonObjectArrayList.set(Integer.parseInt(position),comercioJson);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });


    }
    private boolean validation(){
        return true;
    }
    private JsonObject recoverData(){
        JsonObject body = new JsonObject();
        body.addProperty("clase_producto",s_productos.get(0).getSelectedItem().toString());
        body.addProperty("id_tipo_producto",id_tipo_producto);
        body.addProperty("id_producto",1);

        for (int i=0;i<cantidad.length;i++){
            body.addProperty(etiquetas.get(i)+"_cantidad",grupoCantidad.get(i).getSelectedItem()+"");
            body.addProperty(etiquetas.get(i)+"_medida",grupoKg.get(i).getSelectedItem()+"");
            body.addProperty(etiquetas.get(i)+"_frecuencia",grupoFrecuencia.get(i).getSelectedItem()+"");
        }

        body.addProperty("aquien_compra",et_name.getText().toString());
        body.addProperty("aquien_dni",et_dni.getText().toString());
        body.addProperty("aquien_certificado",et_codigo.getText().toString());
        body.addProperty("aquien_donde",et_lugar.getText().toString());
        body.addProperty("ruc",et_ruc.toString().toString());
        body.addProperty("documento",s_boleta.getSelectedItem().toString());
        body.addProperty("marca",et_marca.getText().toString());
        body.addProperty("marca_colectiva",et_marca_colectiva.getText().toString());

        return body;
    }
    private void agro1(final Spinner spinner){
        //fill group
        final ArrayList<String>done=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_grupos_productos_agricolas.php?&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("grupos_productos_agricolas");
                            Iterator<?> keys = jsonObj2.keys();
                            ArrayList<Integer> idProductos1 = new ArrayList<>();
                            idProductos1.add(0);
                            ArrayList<String> nameProductos1= new ArrayList<>();
                            nameProductos1.add("Elija");
                            done.add(0+"");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("grupo");
                                    idProductos1.add(id);
                                    done.add(""+id);
                                    nameProductos1.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idProductos1.add(Integer.parseInt(key));
                                    done.add(""+Integer.parseInt(key));
                                    nameProductos1.add(name);
                                }
                            }

                            spinnerArrayAdapter1 = new ArrayAdapter<String>
                                    (context, android.R.layout.simple_spinner_item,
                                            done);

                            ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameProductos1);
                            spinner.setAdapter(spinnerArrayAdapter1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            spinner.setAdapter(null);
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

    private void fillGruposAgricolas(final Spinner inner,
                                     final ArrayList<Integer>p1,
                                     final ArrayList<String>nameListP1,final int id){

        RequestQueue queue = Volley.newRequestQueue(this.context);

        String url = BuildConfig.BASE_URL+"lista_productos_agricolas.php?grupo="+id+"&token=lpsk.21568$lsjANPIO02";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("productos_agricolas");
                            System.out.println(jsonObj2.toString());
                            Iterator<?> keys = jsonObj2.keys();
                            p1.add(0);
                            nameListP1.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("producto");

                                    p1.add(id);
                                    nameListP1.add(name);
                                }else{
                                    p1.add(Integer.parseInt(key));
                                    nameListP1.add(jsonObj2.get(key).toString());
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameListP1);
                            inner.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            inner.setAdapter(null);
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
    public void onBackPressed() {

    }
    private void tranformado1(final Spinner spinner){
        final ArrayList<String>done=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_grupos_productos_transformados.php?token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("grupo_productos_transformados");
                            Iterator<?> keys = jsonObj2.keys();
                            ArrayList<Integer> idProductos1 = new ArrayList<>();
                            idProductos1.add(0);
                            ArrayList<String> nameProductos1= new ArrayList<>();
                            nameProductos1.add("Elija");
                            done.add(0+"");

                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("grupo");
                                    idProductos1.add(id);
                                    nameProductos1.add(name);
                                    done.add(""+id);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idProductos1.add(Integer.parseInt(key));
                                    nameProductos1.add(name);
                                    done.add(Integer.parseInt(key)+"");
                                }
                            }
                            spinnerArrayAdapter1 = new ArrayAdapter<String>
                                    (context, android.R.layout.simple_spinner_item,
                                            done);
                            ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameProductos1);
                            spinner.setAdapter(spinnerArrayAdapter1);
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

    //http://demodataexe.com/anpe/webservice/lista_grupos_productos_transformados.php?token=lpsk.21568$lsjANPIO02

}
