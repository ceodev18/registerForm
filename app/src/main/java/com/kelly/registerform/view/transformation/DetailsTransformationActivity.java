package com.kelly.registerform.view.transformation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.kelly.registerform.adapters.modelsAdapter.EmpresaCertificadoraAdapter;
import com.kelly.registerform.adapters.modelsAdapter.GrupoProductoTransformadoAdapter;
import com.kelly.registerform.adapters.modelsAdapter.PresentacionAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProductoTransformadoAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProvinceAdapter;
import com.kelly.registerform.adapters.modelsAdapter.TipoPropiedadAdapter;
import com.kelly.registerform.model.GrupoProductoTransformado;
import com.kelly.registerform.model.ManejoCrianza;
import com.kelly.registerform.model.Presentacion;
import com.kelly.registerform.model.ProductoTransformado;
import com.kelly.registerform.model.TipoPropiedad;
import com.kelly.registerform.model.certifications.EmpresaCertificadora;
import com.kelly.registerform.model.certifications.TipoCertificadora;
import com.kelly.registerform.model.ubigeo.Provincia;
import com.kelly.registerform.view.MapsActivity;
import com.kelly.registerform.view.farming.ProductionActivity;
import com.kelly.registerform.view.livestock.LivestockProductionActivity;
import com.kelly.registerform.view.partner.RegistrationPartnerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class DetailsTransformationActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener{
    private Button b_photo,b_photo_final,b_file,b_save;
    private Context context=this;
    private String position;
    public static String dateString="";
    private String stringBirthday="";
    public static boolean stateDate=false;
    private static final String INDEX = "index";
    private static final int PICK_IMAGE = 100;
    private int color;
    private int index;
    private Button b_map;
    private EditText et_latitude,et_longitude,et_sanitario,et_ruc,et_razon,et_code;
    private ArrayList<TextView> listTextView;
    private TextView tv_show1,tv_show2,tv_show3,tv_show4,tv_show5,tv_show6,tv_title,tv_photo,
            tv_photo_final,tv_file,et_year;
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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
        refillData();
    }
    private void refillData(){
        JsonObject main =ProcessActivity.jsonObjectArrayList.get(Integer.parseInt(position));
        if(main.size()==0)return;
        et_latitude.setText(main.get("ubicacion_latitud_gps").getAsString());
        et_longitude.setText(main.get("ubicacion_longitud_gps").getAsString());
        s_type.setSelection(getIndexType(s_type, main.get("id_tipo_producto").getAsString()));
        s_product.setSelection(getIndexProducto(s_product, main.get("id_producto_transformado").getAsString()));
        s_month_start.setSelection(getIndex(s_month_start, main.get("produccion_mes_desde").getAsString()));
        s_month_end.setSelection(getIndex(s_month_end, main.get("produccion_mes_hasta").getAsString()));

        et_sanitario.setText(main.get("registro_sanitario").getAsString());
        et_ruc.setText(main.get("ruc").getAsString());
        et_razon.setText(main.get("razon_social").getAsString());
        et_code.setText(main.get("codigo_certificado").getAsString());

        if(main.get("tiene_certificado").getAsString().equals("Sí"))s_tiene.setSelection(1);
        else s_tiene.setSelection(2);
        s_tipo.setSelection(getIndex(s_tipo, main.get("id_tipo_certificadora").getAsString()));
        s_company.setSelection(getIndexCompany(s_company, main.get("id_empresa_certificadora").getAsString()));
        //Date
        stateDate= true;
        dateString= main.get("fecha_caducidad").getAsString();
        et_year.setText(dateString);
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

    private int getIndexCompany(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            EmpresaCertificadora empresaCertificadora = (EmpresaCertificadora)spinner.getAdapter().getItem(i);
            if (empresaCertificadora.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    //private method of your class
    private int getIndexType(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            GrupoProductoTransformado grupoProductoTransformado = (GrupoProductoTransformado)spinner.getAdapter().getItem(i);
            if (grupoProductoTransformado.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    //private method of your class
    private int getIndexProducto(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            ProductoTransformado productoTransformado = (ProductoTransformado)spinner.getAdapter().getItem(i);
            if (productoTransformado.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
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

        final GrupoProductoTransformadoAdapter adapter = new GrupoProductoTransformadoAdapter(context,
                R.layout.owner_spinner_item,
                GrupoProductoTransformado.listAll(GrupoProductoTransformado.class));

        s_type.setAdapter(adapter);
        s_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GrupoProductoTransformado grupoProductoTransformado= adapter.getItem(i);
                int id = Integer.parseInt(grupoProductoTransformado.getId_main());
                idtype_se =id;
                fillProduct(id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void setActions(){
        et_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(),"doit");
            }
        });
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




        s_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //idcomp_se= s_company.get(i);
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
                ProductoTransformado p =(ProductoTransformado)s_product.getSelectedItem();
                returnIntent.putExtra("result",p.getName()+","+position);
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
        Presentacion p1 = (Presentacion)spinner1.getSelectedItem();
        if(p1.getName().length()!=0
                && !p1.getName().equals("")){
            JsonObject aux = new JsonObject();
            if(p1.getName().equals("Botella de 1 litro"))
                aux.addProperty("id_tipo_presentacion",1);
            if(p1.getName().equals("Botella de medio litro"))
                aux.addProperty("id_tipo_presentacion",2);
            if(p1.getName().equals("caja"))
                aux.addProperty("id_tipo_presentacion",3);
            EditText et_cantidad = arrayPresentationList.get(0).findViewById(R.id.et_cantidad);
            aux.addProperty("cantidad",et_cantidad.getText().toString());
            response.add("1",aux);
        }

        Presentacion p2 = (Presentacion)spinner2.getSelectedItem();
        if(p2.getName().length()!=0
                && !p2.getName().equals("")){
            JsonObject aux = new JsonObject();
            if(p2.getName().equals("Botella de 1 litro"))
                aux.addProperty("id_tipo_presentacion",1);
            if(p2.getName().equals("Botella de medio litro"))
                aux.addProperty("id_tipo_presentacion",2);
            if(p2.getName().equals("caja"))
                aux.addProperty("id_tipo_presentacion",3);
            EditText et_cantidad = arrayPresentationList.get(1).findViewById(R.id.et_cantidad);
            aux.addProperty("cantidad",et_cantidad.getText().toString());
            response.add("2",aux);
        }

        Presentacion p3 = (Presentacion)spinner3.getSelectedItem();
        if(p3.getName().length()!=0
                && !p3.getName().equals("")){
            JsonObject aux = new JsonObject();
            if(p3.getName().equals("Botella de 1 litro"))
                aux.addProperty("id_tipo_presentacion",1);
            if(p3.getName().equals("Botella de medio litro"))
                aux.addProperty("id_tipo_presentacion",2);
            if(p3.getName().equals("caja"))
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

        EmpresaCertificadora e = (EmpresaCertificadora)s_company.getSelectedItem();
        response.addProperty("id_empresa_certificadora",e.getName());

        response.addProperty("codigo_certificado",idcomp_se);
        response.addProperty("fecha_caducidad",stringBirthday);
        response.addProperty("tipo_certificacion","transformacion");

        return response;
    }
    public boolean validation(){
        GrupoProductoTransformado g =(GrupoProductoTransformado)s_type.getSelectedItem();
        if(g.getName().equals("Elije")
                ||g.getName().length()==0){
            Toast.makeText(context, "Debe escoger tipo de producto", Toast.LENGTH_SHORT).show();
            return false;
        }
        ProductoTransformado p =(ProductoTransformado)s_product.getSelectedItem();
        if(p.getName().equals("Elija")
                ||p.getName().length()==0){
            Toast.makeText(context, "Debe escoger un producto", Toast.LENGTH_SHORT).show();
            return false;
        }
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





        if(s_tiene.getSelectedItem().toString().equals("Elija")
                ||s_tiene.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Debe escoger si tiene certificado", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!s_tiene.getSelectedItem().toString().equals("No")){

            if(s_tipo.getSelectedItem().toString().equals("Elija")
                    ||s_tipo.getSelectedItem().toString().length()==0){
                Toast.makeText(context, "Debe escoger el tipo de certificado", Toast.LENGTH_SHORT).show();
                return false;
            }
            EmpresaCertificadora e = (EmpresaCertificadora)s_company.getSelectedItem();
            if(e.getName().equals("Elije")
                    ||e.getName().length()==0){
                Toast.makeText(context, "Debe escoger la empresa", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(et_code.getText().length()==0){
                Toast.makeText(context, "Ingrese código", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(et_year.getText().length()==0){
                Toast.makeText(context, "Debe escoger año de caducidad", Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        return true;

    }
    private void fillProduct(int id){

        Iterator<?> keys = ProductoTransformado.findAll(ProductoTransformado.class);
        List<ProductoTransformado> currentList= new ArrayList<>();
        while(keys.hasNext()){
            ProductoTransformado p = (ProductoTransformado)keys.next();
            System.out.println("p.getId_parent "+p.getId_parent() +"\t"+id+"");
            if (p.getId_parent().equals(id+""))currentList.add(p);
        }

        final ProductoTransformadoAdapter adapter = new ProductoTransformadoAdapter(context,R.layout.owner_spinner_item,
                currentList);

        s_product.setAdapter(adapter);
        s_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductoTransformado productoTransformado =adapter.getItem(i);
                idprod_se= Integer.parseInt(productoTransformado.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        final PresentacionAdapter adapter = new PresentacionAdapter(context,
                R.layout.owner_spinner_item,
                Presentacion.listAll(Presentacion.class));

        spinner.setAdapter(adapter);

        /*RequestQueue queue = Volley.newRequestQueue(context);
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
        queue.add(stringRequest);*/
    }


    private void fillComapy(){
        final EmpresaCertificadoraAdapter adapter = new EmpresaCertificadoraAdapter(context,
                R.layout.owner_spinner_item,
                EmpresaCertificadora.listAll(EmpresaCertificadora.class));

        s_company.setAdapter(adapter);

        s_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = new GregorianCalendar(i, i1, i2);
        setDate(cal);
    }
    private void setDate(final Calendar calendar) {
        stringBirthday = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        dateString = stringBirthday;
        et_year.setText(stringBirthday);
        //b_birthday.setKeyListener(null);
        stateDate =true;
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if(!DetailsTransformationActivity.stateDate){
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), year, month, day);
            }else{
                String[] dateOwn=DetailsTransformationActivity.dateString.split("-");
                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), Integer.parseInt(dateOwn[0]), Integer.parseInt(dateOwn[1])-1, Integer.parseInt(dateOwn[2]));
            }

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
