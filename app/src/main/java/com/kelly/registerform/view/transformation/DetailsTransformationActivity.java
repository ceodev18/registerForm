package com.kelly.registerform.view.transformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.kelly.registerform.view.MapsActivity;
import com.kelly.registerform.view.farming.ProductionActivity;
import com.kelly.registerform.view.livestock.LivestockProductionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DetailsTransformationActivity extends AppCompatActivity {
    private Button b_photo,b_photo_final,b_file,b_save;
    private Context context=this;
    private String position;
    private static final String INDEX = "index";
    private static final int PICK_IMAGE = 100;
    private int color;
    private int index;
    private Button b_map;
    private EditText et_latitude,et_longitude,et_sanitario,et_ruc,et_razon,et_year,et_code;
    private ArrayList<TextView> listTextView;
    private TextView tv_show1,tv_show2,tv_show3,tv_show4,tv_show5,tv_show6,tv_title,tv_photo,
            tv_photo_final,tv_file;
    private LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5,ll_6;
    private ArrayList<Boolean>listState;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private int VALOR_RETORNO = 1;
    public int indexPage=1;
    private Spinner s_type,s_product,s_month_start,s_month_end,s_tipo,s_tiene,s_company,spinner1,spinner2,spinner3;

    private ArrayList<String>arrayListType,arrayListProduct,nam1,nam2,nam3,arrayListCompany;
    private ArrayList<Integer>idType,idProduct,ll1,ll2,ll3,idCompany;
    private View v1,v2,v3;
    private ArrayList<View>arrayInsumoList,arrayPresentationList;

    //selecteds
    private int idtype_se,idprod_se,idcomp_se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_transformation);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        position=getIntent().getStringExtra("index");
        int aux = Integer.parseInt(position);
        aux++;
        ab.setTitle("Transformación "+aux);

        setElements();
        setActions();

        setSpecialViews();

        tv_show1 = (TextView) findViewById(R.id.tv_show1);
        tv_show2 = (TextView) findViewById(R.id.tv_show2);
        tv_show3 = (TextView) findViewById(R.id.tv_show3);
        tv_show4 = (TextView) findViewById(R.id.tv_show4);
        tv_show5 = (TextView) findViewById(R.id.tv_show5);
        tv_show6 = (TextView) findViewById(R.id.tv_show6);

        listTextView= new ArrayList<>();
        listTextView.add(tv_show1);
        listTextView.add(tv_show2);
        listTextView.add(tv_show3);
        listTextView.add(tv_show4);
        listTextView.add(tv_show5);
        listTextView.add(tv_show6);

        ll_1= findViewById(R.id.ll_1);
        ll_2= findViewById(R.id.ll_2);
        ll_3= findViewById(R.id.ll_3);
        ll_4= findViewById(R.id.ll_4);
        ll_5= findViewById(R.id.ll_5);
        ll_6= findViewById(R.id.ll_6);
        linearLayoutArrayList= new ArrayList<>();
        linearLayoutArrayList.add(ll_1);
        linearLayoutArrayList.add(ll_2);
        linearLayoutArrayList.add(ll_3);
        linearLayoutArrayList.add(ll_4);
        linearLayoutArrayList.add(ll_5);
        linearLayoutArrayList.add(ll_6);
        listState = new ArrayList<>();
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);

        for (int i=0;i<listTextView.size();i++){
            final int index=i;
            listTextView.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listState.get(index)==false){
                        linearLayoutArrayList.get(index).setVisibility(View.VISIBLE);
                        listState.set(index,true);
                    }else{
                        linearLayoutArrayList.get(index).setVisibility(View.GONE);
                        listState.set(index,false);
                    }
                }
            });
        }
        arrayInsumoList =new ArrayList<>();
        arrayPresentationList=new ArrayList<>();
        innerViews();
    }
    private void innerViews(){
        View insumo_v1= new View(context);
        insumo_v1 = findViewById(R.id.ll_insumo1);

        View insumo_v2= new View(context);
        insumo_v2 = findViewById(R.id.ll_insumo2);

        View insumo_v3= new View(context);
        insumo_v3 = findViewById(R.id.ll_insumo3);

        arrayInsumoList.add(insumo_v1);
        arrayInsumoList.add(insumo_v2);
        arrayInsumoList.add(insumo_v3);

        View ll_presentation1= new View(context);
        ll_presentation1 = findViewById(R.id.ll_presentation1);

        View ll_presentation2= new View(context);
        ll_presentation2 = findViewById(R.id.ll_presentation2);

        View ll_presentation3= new View(context);
        ll_presentation3 = findViewById(R.id.ll_presentation3);

        arrayPresentationList.add(ll_presentation1);
        arrayPresentationList.add(ll_presentation2);
        arrayPresentationList.add(ll_presentation3);

    }
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
    private void setElements(){
        et_sanitario =findViewById(R.id.et_sanitario);
        et_ruc =findViewById(R.id.et_ruc);
        et_razon =findViewById(R.id.et_razon);
        et_year =findViewById(R.id.et_year);
        et_code =findViewById(R.id.et_code);

        b_save=findViewById(R.id.b_save);
        b_photo_final=findViewById(R.id.b_photo_final);
        b_file=findViewById(R.id.b_file);
        b_photo=findViewById(R.id.b_photo);

        s_tipo=findViewById(R.id.s_tipo);
        s_tiene=findViewById(R.id.s_tiene);
        s_company=findViewById(R.id.s_company);
        s_month_start=findViewById(R.id.s_month_start);
        s_month_end=findViewById(R.id.s_month_end);


        et_latitude=findViewById(R.id.et_latitud);
        et_longitude=findViewById(R.id.et_longitud);
        tv_photo=findViewById(R.id.tv_photo);
        tv_photo_final=findViewById(R.id.tv_photo_final);
        tv_file=findViewById(R.id.tv_file);
        b_map=findViewById(R.id.b_map);
        s_type=findViewById(R.id.s_type);
        s_product=findViewById(R.id.s_product);
        arrayListType = new ArrayList<>();
        arrayListProduct = new ArrayList<>();
        idType= new ArrayList<>();
        idProduct= new ArrayList<>();
        idCompany= new ArrayList<>();
        idCompany.add(0);
        arrayListCompany= new ArrayList<>();
        arrayListCompany.add("Elija");
        fillTipo();
        fillComapy();
    }
    private void fillTipo(){
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
                            idType=new ArrayList<>();
                            idType.add(0);
                            arrayListType=new ArrayList<>();
                            arrayListType.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("grupo");

                                    idType.add(id);
                                    arrayListType.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idType.add(Integer.parseInt(key));
                                    arrayListType.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListType);
                            s_type.setAdapter(spinnerArrayAdapter);

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

    private void setActions(){
        b_photo_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
                tv_photo_final.setText("Se ha registrado la foto correctamente!");
            }
        });

        b_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
                tv_photo.setText("Se ha registrado la foto correctamente!");
            }
        });
        b_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);
                tv_file.setText("Se ha registrado la imagen correctamente");
            }
        });
        s_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id = idType.get(i);
                idtype_se =id;
                fillProduct(id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idprod_se= idType.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idcomp_se= idCompany.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
                JsonObject transformationJson = recoverData();
                ProcessActivity.jsonObjectArrayList.set(Integer.parseInt(position),transformationJson);
                Intent returnIntent = new Intent();
                //returnIntent.putExtra("result",et_parcela_name.getText().toString()+","+position)
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
    private JsonObject getInsumos(){
        //arrayInsumoList
        JsonObject response = new JsonObject();
          for (int i=0;i<arrayInsumoList.size();i++){
              JsonObject insumoJson = new JsonObject();
            EditText et_insumo = arrayInsumoList.get(i).findViewById(R.id.et_insumo);
            if(!et_insumo.getText().toString().equals("") ||
                    et_insumo.getText().toString().length()!=0){
                //get data
                insumoJson.addProperty("insumo",et_insumo.getText().toString());

                Spinner s_organico = arrayInsumoList.get(i).findViewById(R.id.s_organico);
                insumoJson.addProperty("es_organico",s_organico.getSelectedItem().toString());

                Spinner s_comprado = arrayInsumoList.get(i).findViewById(R.id.s_comprado);
                insumoJson.addProperty("es_comprado",s_comprado.getSelectedItem().toString());

                EditText et_vendedor = arrayInsumoList.get(i).findViewById(R.id.et_vendedor);
                insumoJson.addProperty("se_compro_a",et_vendedor.getText().toString());

                EditText et_codigo = arrayInsumoList.get(i).findViewById(R.id.et_codigo);
                insumoJson.addProperty("certificado",et_codigo.getText().toString());

                EditText et_lugar = arrayInsumoList.get(i).findViewById(R.id.et_lugar);
                insumoJson.addProperty("donde_se_compro",et_lugar.getText().toString());

                response.add(""+(i+1),insumoJson);
            }
        }
     return response;
    }
    private JsonObject getPresentaciones(){
        //arrayInsumoList
        JsonObject response = new JsonObject();
        if(spinner1.getSelectedItem().toString().length()!=0
                && !spinner1.getSelectedItem().toString().equals("")){
            JsonObject aux = new JsonObject();
            if(spinner1.getSelectedItem().toString().equals("Botella de 1 litro"))
                aux.addProperty("id_tipo_presentacion",1);
            if(spinner1.getSelectedItem().toString().equals("Botella de medio litro"))
                aux.addProperty("id_tipo_presentacion",2);
            if(spinner1.getSelectedItem().toString().equals("caja"))
                aux.addProperty("id_tipo_presentacion",3);
            EditText et_cantidad = arrayPresentationList.get(0).findViewById(R.id.et_cantidad);
            aux.addProperty("cantidad",et_cantidad.getText().toString());
            response.add("1",aux);
        }

        if(spinner2.getSelectedItem().toString().length()!=0
                && !spinner2.getSelectedItem().toString().equals("")){
            JsonObject aux = new JsonObject();
            if(spinner2.getSelectedItem().toString().equals("Botella de 1 litro"))
                aux.addProperty("id_tipo_presentacion",1);
            if(spinner2.getSelectedItem().toString().equals("Botella de medio litro"))
                aux.addProperty("id_tipo_presentacion",2);
            if(spinner2.getSelectedItem().toString().equals("caja"))
                aux.addProperty("id_tipo_presentacion",3);
            EditText et_cantidad = arrayPresentationList.get(1).findViewById(R.id.et_cantidad);
            aux.addProperty("cantidad",et_cantidad.getText().toString());
            response.add("2",aux);
        }

        if(spinner3.getSelectedItem().toString().length()!=0
                && !spinner3.getSelectedItem().toString().equals("")){
            JsonObject aux = new JsonObject();
            if(spinner3.getSelectedItem().toString().equals("Botella de 1 litro"))
                aux.addProperty("id_tipo_presentacion",1);
            if(spinner3.getSelectedItem().toString().equals("Botella de medio litro"))
                aux.addProperty("id_tipo_presentacion",2);
            if(spinner3.getSelectedItem().toString().equals("caja"))
                aux.addProperty("id_tipo_presentacion",3);
            EditText et_cantidad = arrayPresentationList.get(2).findViewById(R.id.et_cantidad);
            aux.addProperty("cantidad",et_cantidad.getText().toString());
            response.add("3",aux);
        }
        return response;
    }
    public JsonObject recoverData(){
        JsonObject response = new JsonObject();
        response.addProperty("id_tipo_producto",idtype_se);
        response.addProperty("id_producto_transformado",idprod_se);
        response.addProperty("ubicacion_latitud_gps",et_latitude.getText().toString());
        response.addProperty("ubicacion_longitud_gps",et_longitude.getText().toString());
        //insumos
        response.add("insumos",getInsumos());
        response.add("presentaciones",getPresentaciones());
        response.addProperty("produccion_mes_desde",s_month_start.getSelectedItem().toString());
        response.addProperty("produccion_mes_hasta",s_month_end.getSelectedItem().toString());
        response.addProperty("registro_sanitario",et_sanitario.getText().toString());
        response.addProperty("ruc",et_ruc.getText().toString());
        response.addProperty("razon_social",et_razon.getText().toString());
        response.addProperty("etiqueta","");
        response.addProperty("tiene_certificado",s_tiene.getSelectedItem().toString());
        response.addProperty("id_tipo_certificadora",s_tipo.getSelectedItem().toString());
        response.addProperty("id_empresa_certificadora",idcomp_se);
        response.addProperty("codigo_certificado",idcomp_se);
        response.addProperty("fecha_caducidad",et_year.getText()+"");
        response.addProperty("tipo_certificacion","transformacion");

        return response;
    }
    public boolean validation(){
        if(s_type.getSelectedItem().toString().equals("Elija")
                ||s_type.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Debe escoger tipo de producto", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if(s_product.getSelectedItem().toString().equals("Elija")
                ||s_product.getSelectedItem().toString().length()==0)return false;*/
        if(s_month_start.getSelectedItem().toString().equals("Elija")
                ||s_month_start.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Debe escoger mes de inicio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s_month_end.getSelectedItem().toString().equals("Elija")
                ||s_month_end.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Debe escoger mes de fin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_sanitario.getText().length()==0){
            Toast.makeText(context, "Debe ingresar el registro sanitario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_ruc.getText().length()==0){
            Toast.makeText(context, "Debe ingresar el RUC", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_razon.getText().length()==0){
            Toast.makeText(context, "Debe ingresar la razón social", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_code.getText().length()==0){
            Toast.makeText(context, "Debe ingresar código de Certificación", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_year.getText().length()==0){
            Toast.makeText(context, "Debe escoger año de caducidad", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_tipo.getSelectedItem().toString().equals("Elija")
                ||s_tipo.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Debe escoger el tipo de certificado", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_tiene.getSelectedItem().toString().equals("Elija")
                ||s_tiene.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Debe escoger si tiene certificado", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s_company.getSelectedItem().toString().equals("Elija")
                ||s_company.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Debe escoger la empresa", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }
    private void fillProduct(int id){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_productos_transformados.php?grupo="+id+"&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("productos_transformados");
                            Iterator<?> keys = jsonObj2.keys();
                            idProduct = new ArrayList<>();
                            idProduct.add(0);
                            arrayListProduct = new ArrayList<>();
                            arrayListProduct.add("Elije");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("producto_transformado");
                                    idProduct.add(id);
                                    arrayListProduct.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idProduct.add(Integer.parseInt(key));
                                    arrayListProduct.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListProduct);
                            s_product.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            s_product.setAdapter(null);
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

    private void setSpecialViews(){
        //v1,v2,v3
        v1  = new View(context);
        v2  = new View(context);
        v3  = new View(context);

        v1 = (View) findViewById(R.id.ll_presentation1);
        v2 = (View) findViewById(R.id.ll_presentation2);
        v3 = (View) findViewById(R.id.ll_presentation3);

        spinner1=(Spinner) v1.findViewById(R.id.s_presentation);
        spinner2=(Spinner) v2.findViewById(R.id.s_presentation);
        spinner3=(Spinner) v3.findViewById(R.id.s_presentation);
        nam1=new ArrayList<>();
        ll1=new ArrayList<>();
        fillPresentation(spinner1,nam1,ll1);
        nam2=new ArrayList<>();
        ll2=new ArrayList<>();
        fillPresentation(spinner2,nam2,ll2);
        nam3=new ArrayList<>();
        ll3=new ArrayList<>();
        fillPresentation(spinner3,nam3,ll3);
    }

    private void fillPresentation(final Spinner spinner,final ArrayList<String>nameList,final ArrayList<Integer>idList){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_presentacion.php?&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_presentacion");
                            Iterator<?> keys = jsonObj2.keys();
                            idList.add(0);
                            nameList.add("Elije");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("presentacion");
                                    idList.add(id);
                                    nameList.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idList.add(Integer.parseInt(key));
                                    nameList.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameList);
                            spinner.setAdapter(spinnerArrayAdapter);

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
        RequestQueue queue = Volley.newRequestQueue(context);
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
                            idCompany.add(0);
                            arrayListCompany=new ArrayList<>();
                            arrayListCompany.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("empresa_sgp");
                                    idCompany.add(id);
                                    arrayListCompany.add(name);
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
                            s_company.setAdapter(null);
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
