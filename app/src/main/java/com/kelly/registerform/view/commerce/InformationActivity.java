package com.kelly.registerform.view.commerce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.kelly.registerform.adapters.modelsAdapter.EspecieCrianzaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.GrupoAgricolaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.GrupoProductoTransformadoAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProductoAgricolaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProductoCrianzaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProductoTransformadoAdapter;
import com.kelly.registerform.model.EspecieCrianza;
import com.kelly.registerform.model.GrupoAgricola;
import com.kelly.registerform.model.GrupoProductoTransformado;
import com.kelly.registerform.model.ProductoAgricola;
import com.kelly.registerform.model.ProductoCrianza;
import com.kelly.registerform.model.ProductoTransformado;
import com.kelly.registerform.view.transformation.ProcessActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InformationActivity extends AppCompatActivity {
    final private Context context=this;
    private EditText et_name,et_dni,et_codigo,et_lugar,et_ruc,et_marca,et_marca_colectiva;
    private ArrayList<Boolean>listState;
    private ArrayList<TextView>listTextView;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private Button b_save;
    private String list,position,id_tipo_producto="",id_inner_producto="";
    private Spinner s_boleta;
    public  String key1="",key2="";
    private ArrayList<View> agroList;
    private ArrayList<String> tipoLista,nameProductos1,nameItem1;
    private ArrayList<Spinner>s_productos,s_grupos,s_items,s_grupoFalse,grupoCantidad,grupoKg,grupoFrecuencia;
    private ArrayList<Integer>idProductos1,idProductos2,idProductos3,idItem1,idItem2,idItem3,innerIds;
    private ArrayList<String>etiquetas,innerNames;
    private ArrayAdapter<String> spinnerArrayAdapter1,spinnerArrayAdapter2,spinnerArrayAdapter3,spinnerInner;
    private boolean state=true;
    private int  indice;
    int[]cantidad;
    int []kg;
    int []frecuencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        position=getIntent().getStringExtra("index");
        int aux = Integer.parseInt(position);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        int counter = Integer.parseInt(position);
        counter++;
        ab.setTitle("Producto Comercial "+counter);
        setElements();
        setActions();


    }
    private void refillData(){
        JsonObject main = ComercializacionActivity.jsonObjectArrayList.get(Integer.parseInt(position));
        if(main.size()==0)return;
        et_name.setText(main.get("aquien_compra").toString());
        et_dni.setText(main.get("aquien_dni").toString());
        et_name.setText(main.get("aquien_certificado").toString());
        et_codigo.setText(main.get("aquien_compra").toString());
        et_lugar.setText(main.get("aquien_donde").toString());
        et_ruc.setText(main.get("ruc").toString());
        et_marca.setText(main.get("marca").toString());
        et_marca_colectiva.setText(main.get("marca_colectiva").toString());

        if(main.get("documento").toString().equals("Sí"))s_boleta.setSelection(1);
        else s_boleta.setSelection(2);
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
        refillData();
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
        for (int j=1000;j<5001;j+=500){
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
                            indice=0;
                            state=false;
                            if(super_index==0)agro1(s_grupo);
                            s_productos.add(s_tipo);
                            s_grupos.add(s_grupo);

                        }
                        if(s_tipo.getSelectedItem().toString().equals("Producto Pecuario")){
                            indice=1;
                            state=false;
                            if(super_index==0)pecuario(s_grupo);
                            s_productos.add(s_tipo);
                            s_grupos.add(s_grupo);



                        }
                        if(s_tipo.getSelectedItem().toString().equals("Producto Transformado")){
                            indice=2;
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
                        if(indice==0){
                            GrupoAgricola grupoAgricola = (GrupoAgricola)s_grupo.getSelectedItem();
                            if(!grupoAgricola.getName().equals("Elije")){
                                id_tipo_producto = grupoAgricola.getId_main();
                                fillUnitarie(s_inner,indice);
                            }
                        }
                        if(indice==1){
                            EspecieCrianza especieCrianza = (EspecieCrianza)s_grupo.getSelectedItem();
                            if(!especieCrianza.getName().equals("Elije")){
                                id_tipo_producto = especieCrianza.getId_main();
                                fillUnitarie(s_inner,indice);
                            }
                        }
                        if(indice==2){
                            GrupoProductoTransformado grupoProductoTransformado = (GrupoProductoTransformado)s_grupo.getSelectedItem();
                            if(!grupoProductoTransformado.getName().equals("Elije")){
                                id_tipo_producto = grupoProductoTransformado.getId_main();
                                fillUnitarie(s_inner,indice);
                            }
                        }


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            s_inner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!state){
                        if(indice==0){
                            ProductoAgricola  productoAgricola = (ProductoAgricola)s_inner.getSelectedItem();
                            if(!productoAgricola.getName().equals("Elije")){
                                id_inner_producto = productoAgricola.getId_main();
                            }
                        }
                        if(indice==1){
                            ProductoCrianza  productoCrianza = (ProductoCrianza)s_inner.getSelectedItem();
                            if(!productoCrianza.getName().equals("Elije")){
                                id_inner_producto = productoCrianza.getId_main();
                            }
                        }
                        if(indice==2){
                            ProductoTransformado  productoTransformado = (ProductoTransformado)s_inner.getSelectedItem();
                            if(!productoTransformado.getName().equals("Elije")){
                                id_inner_producto = productoTransformado.getId_main();
                            }
                        }


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



        }
        
    }
    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
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
        if(s_productos.get(0).getSelectedItem().toString().equals("")
                || s_productos.get(0).getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Seleccione una clase de Producto", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(id_tipo_producto.equals("") || id_tipo_producto.length()==0){
            Toast.makeText(context, "Seleccione una clase de producto", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(id_inner_producto.equals("") || id_inner_producto.length()==0){
            Toast.makeText(context, "Seleccione un tipo de producto", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private JsonObject recoverData(){
        JsonObject body = new JsonObject();
        body.addProperty("clase_producto",s_productos.get(0).getSelectedItem().toString());
        body.addProperty("id_tipo_producto",id_tipo_producto);
        body.addProperty("id_producto",id_inner_producto);

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

        System.out.println(body);

        return body;
    }
    private void agro1(final Spinner spinner){
        final GrupoAgricolaAdapter adapter = new GrupoAgricolaAdapter(context,
                R.layout.owner_spinner_item,
                GrupoAgricola.listAll(GrupoAgricola.class));
        spinner.setAdapter(adapter);
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
        final GrupoProductoTransformadoAdapter adapter = new GrupoProductoTransformadoAdapter(context,
                R.layout.owner_spinner_item,
                GrupoProductoTransformado.listAll(GrupoProductoTransformado.class));

        spinner.setAdapter(adapter);

    }

    private void pecuario(final Spinner spinner){
        final EspecieCrianzaAdapter adapter = new EspecieCrianzaAdapter(context,
                R.layout.owner_spinner_item,
                EspecieCrianza.listAll(EspecieCrianza.class));

        spinner.setAdapter(adapter);

        /*final ArrayList<String>done=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_grupos_animales_crianza.php?token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("grupo_animales_crianza");
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
        queue.add(stringRequest);*/

    }

    public void fillUnitarie(final Spinner spinner,final  int index){
        String url="";
        if(index ==0){
            Iterator<?> keys = ProductoAgricola.findAll(ProductoAgricola.class);
            List<ProductoAgricola> currentList= new ArrayList<>();
            ProductoAgricola pa = new ProductoAgricola("0","0","Elije","Vacio");
            currentList.add(pa);
            while(keys.hasNext()){
                ProductoAgricola p = (ProductoAgricola)keys.next();
                if (p.getId_parent().equals(id_tipo_producto))currentList.add(p);
            }

            final ProductoAgricolaAdapter adapter = new ProductoAgricolaAdapter(context,R.layout.owner_spinner_item,
                    currentList);

            spinner.setAdapter(adapter);
        }
        if(index==1){
            Iterator<?> keys = ProductoCrianza.findAll(ProductoCrianza.class);
            List<ProductoCrianza> currentList= new ArrayList<>();
            ProductoCrianza pa = new ProductoCrianza("0","0","Elije");
            currentList.add(pa);
            while(keys.hasNext()){
                ProductoCrianza p = (ProductoCrianza)keys.next();
                if (p.getId_parent().equals(id_tipo_producto))currentList.add(p);
            }

            final ProductoCrianzaAdapter adapter = new ProductoCrianzaAdapter(context,R.layout.owner_spinner_item,
                    currentList);

            spinner.setAdapter(adapter);

        }
        if(index==2){
            Iterator<?> keys = ProductoTransformado.findAll(ProductoTransformado.class);
            List<ProductoTransformado> currentList= new ArrayList<>();
            ProductoTransformado aux = new ProductoTransformado("0","0","Elije");
            currentList.add(aux);
            while(keys.hasNext()){
                ProductoTransformado p = (ProductoTransformado)keys.next();
                if (p.getId_parent().equals(id_tipo_producto))currentList.add(p);
            }

            final ProductoTransformadoAdapter adapter = new ProductoTransformadoAdapter(context,R.layout.owner_spinner_item,
                    currentList);

            spinner.setAdapter(adapter);

        }

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
