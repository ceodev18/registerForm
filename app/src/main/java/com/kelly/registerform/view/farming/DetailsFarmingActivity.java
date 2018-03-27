package com.kelly.registerform.view.farming;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.kelly.registerform.view.MapsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class DetailsFarmingActivity extends AppCompatActivity {
    private Button b_save;
    private String position;
    private Context context=this;

    private static final String INDEX = "index";
    private int VALOR_RETORNO = 1;
    private int color;
    private int index,globalType,idTipoProducto1,idTipoProducto2,idProd1,idProd2;
    public int indexPage=1;
    private LinearLayout ll_1,ll_2;
    private TextView tv_show1,tv_show2,tv_metros,tv_hectareas,tv_yugadas;
    private ArrayList<TextView> textViewArrayList;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private ArrayList<String>arrayListType,arrayListSize;
    private ArrayList<Integer>idType,idSize;
    private ArrayList<Boolean>listState;
    private Spinner s_type,s_size;
    private int[] viewList;
    private ArrayList<Integer>g1,g2,p1,p2;
    private ArrayList<String>nameListG1,nameListG2,nameListP1,nameListp2;
    private Spinner spinnerGroup1,spinnerGroup2,spinnerProduct1,spinnerProduct2;
    private View v1,v2;
    private Button b_map;
    public EditText et_latitude,et_longitude,et_parcela_name,et_medida;
    public String name_chacra="";
    public JsonObject outputJson;
    Button b_file;
    private  TextView tv_title,tv_file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_farm1);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        position=getIntent().getStringExtra("index");
        int aux = Integer.parseInt(position);
        aux++;
        ab.setTitle("Chacra "+aux);
        setElements();
        setAction();
    }
    private void setElements(){
        outputJson = new JsonObject();
        b_save = findViewById(R.id.b_save);
        et_parcela_name = findViewById(R.id.et_parcela_name);

        initializeArrays();
        b_map=findViewById(R.id.b_map);
        et_latitude=findViewById(R.id.et_latitud);
        et_longitude=findViewById(R.id.et_longitud);
        b_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        tv_show1 = findViewById(R.id.tv_show1);
        tv_show2 = findViewById(R.id.tv_show2);
        textViewArrayList.add(tv_show1);
        textViewArrayList.add(tv_show2);
        listState.add(false);
        listState.add(false);

        ll_1 = findViewById(R.id.ll_1);
        ll_2= findViewById(R.id.ll_2);
        linearLayoutArrayList.add(ll_1);
        linearLayoutArrayList.add(ll_2);

        b_file = findViewById(R.id.b_file);
        tv_file = findViewById(R.id.tv_file);

        for (int i=0;i<textViewArrayList.size();i++){
            final int index=i;
        }

        b_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);
                tv_file.setText("Se ha registrado el documento correctamente!");

            }
        });

        tv_metros=findViewById(R.id.tv_metros);
        tv_hectareas=findViewById(R.id.tv_hectareas);
        tv_yugadas=findViewById(R.id.tv_yugadas);
        et_parcela_name = findViewById(R.id.et_parcela_name);
        et_parcela_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                name_chacra=s.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                name_chacra=s.toString();
                // TODO Auto-generated method stub
            }
        });
        s_type=findViewById(R.id.s_type);
        et_medida=findViewById(R.id.et_medida);
        fillType();
        s_size=findViewById(R.id.s_size);
        fillSize();
        setLinearLayoutDetails();
    }
    private void setAction(){
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            JsonObject chacraData = validation();
            if(chacraData ==null)return;
            ProductionActivity.jsonObjectArrayList.set(Integer.parseInt(position),chacraData);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",et_parcela_name.getText().toString()+","+position);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
            }
        });
        s_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                globalType=idType.get(i);
                if(!(et_medida.getText().length()==0) && !(s_type.getSelectedItem().toString().equals("Elija"))){
                    //if(s_type.getSelectedItem().toString().equals("Yugadas"))
                    //float val = Float.parseFloat(et_medida.getText()+"");
                    //tv_metros.setText("La chacra mide en metros "+val);
                    //tv_hectareas.setText("La chacra mide en hectáreas "+(val/100));
                    //tv_yugadas.setText("La chacra mide en yugadas "+(val/33));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(et_medida.getText().toString().length()==0 && !s_size.getSelectedItem().equals("Elija")){
                    Toast.makeText(context, "Debe ingresar la medida de la chacra", Toast.LENGTH_SHORT).show();
                }else{
                    if(s_size.getSelectedItem().equals("Metros cuadrados")){
                        double val = Double.parseDouble(et_medida.getText().toString());
                        DecimalFormat formatter = new DecimalFormat("#0.00");
                        tv_metros.setText("La chacra mide en metros "+formatter.format(val));
                        tv_hectareas.setText("La chacra mide en hectáreas "+formatter.format(val/100));
                        tv_yugadas.setText("La chacra mide en yugadas "+formatter.format(val/33));
                    }
                    if(s_size.getSelectedItem().equals("Yugadas")){
                        double val = Double.parseDouble(et_medida.getText().toString());
                        DecimalFormat formatter = new DecimalFormat("#0.00");
                        tv_metros.setText("La chacra mide en metros "+formatter.format(val*33));
                        tv_hectareas.setText("La chacra mide en hectáreas "+formatter.format(val*0.33));
                        tv_yugadas.setText("La chacra mide en yugadas "+formatter.format(val));
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void setActionsSpinner(){

        spinnerGroup1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTipoProducto1=g1.get(i);
                fillProduct1(g1.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerProduct1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProd1=p1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerGroup2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTipoProducto2=g2.get(i);
                fillProduct2(g2.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerProduct2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProd2=p2.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initializeArrays(){
        textViewArrayList =new ArrayList<>();
        linearLayoutArrayList=new ArrayList<>();
        listState = new ArrayList<>();
    }
    public  void fillType(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_propiedad.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_propiedad");
                            Iterator<?> keys = jsonObj2.keys();
                            idType=new ArrayList<>();
                            idType.add(0);
                            arrayListType=new ArrayList<>();
                            arrayListType.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();

                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("tipo_propiedad");

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
                            s_type.setAdapter(null);
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
    public  void fillSize(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_medida.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_medidas");
                            Iterator<?> keys = jsonObj2.keys();
                            idSize=new ArrayList<>();
                            idSize.add(0);
                            arrayListSize=new ArrayList<>();
                            arrayListSize.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("tipo_medida");

                                    idSize.add(id);
                                    arrayListSize.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idSize.add(Integer.parseInt(key));
                                    arrayListSize.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListSize);
                            s_size.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            s_size.setAdapter(null);
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
    private void fillSpinnersGroup(final Spinner spinner,final ArrayList<Integer>gId,final ArrayList<String>nameList){
        //fill group
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
                            gId.add(0);
                            nameList.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("grupo");
                                    gId.add(id);
                                    nameList.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    gId.add(Integer.parseInt(key));
                                    nameList.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameList);
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
    public void fillProduct1(int index){
        //spinnerProduct1
        int id=index;

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
                            p1=new ArrayList<>();
                            p1.add(0);
                            nameListP1=new ArrayList<>();
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
                            spinnerProduct1.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            spinnerProduct1.setAdapter(null);
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
    private void setLinearLayoutDetails(){
        View viewBussiness  = new View(context);

        v1 = (View) findViewById(R.id.ll_detail1);
        v2 = (View) findViewById(R.id.ll_detail2);

        spinnerGroup1=(Spinner) v1.findViewById(R.id.s_group_farming);
        spinnerGroup2=(Spinner) v2.findViewById(R.id.s_group_farming);
        spinnerProduct1=(Spinner) v1.findViewById(R.id.s_production);
        spinnerProduct2=(Spinner) v2.findViewById(R.id.s_production);

        g1=new ArrayList<>();
        nameListG1=new ArrayList<>();
        fillSpinnersGroup(spinnerGroup1,g1,nameListG1);

        g2=new ArrayList<>();
        nameListG2=new ArrayList<>();
        fillSpinnersGroup(spinnerGroup2,g2,nameListG2);


        setActionsSpinner();

    }
    public void fillProduct2(int index){
        //spinnerProduct1
        int id=index;

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
                            p2=new ArrayList<>();
                            p2.add(0);
                            nameListp2=new ArrayList<>();
                            nameListp2.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("producto");

                                    p2.add(id);
                                    nameListp2.add(name);
                                }else{
                                    p2.add(Integer.parseInt(key));
                                    nameListp2.add(jsonObj2.get(key).toString());
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameListp2);
                            spinnerProduct2.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            spinnerProduct2.setAdapter(null);
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
    public JsonObject validation(){
        if(!recoverData())return null;
        JsonObject productos = new JsonObject();

        productos.addProperty("ubicacion_latitud_gps",et_latitude.getText()+"");
        productos.addProperty("ubicacion_longitud_gps",et_latitude.getText()+"");
        productos.addProperty("nombre_chacra",et_parcela_name.getText()+"");
        productos.addProperty("id_tipo_propiedad",s_type.getSelectedItem()+"");
        productos.addProperty("id_tipo_propiedad",s_type.getSelectedItem()+"");
        productos.addProperty("medida_chacra",et_medida.getText()+"");
        productos.addProperty("id_tipo_medida",globalType+"");



        View viewKid1 = findViewById(R.id.ll_detail1);
        Spinner s_group_farming1= viewKid1.findViewById(R.id.s_group_farming);
        if(!(s_group_farming1.getSelectedItem().toString().equals("Elija"))){
            //TODO ADD DATA FORM SPINNER 1
            JsonObject prod1 = new JsonObject();
            prod1.addProperty("id_tipo_producto",idTipoProducto1+"");
            prod1.addProperty("id_producto_agroecologico",idProd1+"");
            prod1.addProperty("area_cultivo","");
            prod1.addProperty("id_tipo_medida_area_cultivo","");
            prod1.addProperty("fecha_siembra","");
            prod1.addProperty("fecha_cosecha","");
            prod1.addProperty("porcentaje_consumo_propio","");
            prod1.addProperty("porcentaje_venta","");
            prod1.addProperty("id_sistema_riego_usado","");
            prod1.addProperty("fecha_plantacion_mes","");
            prod1.addProperty("fecha_plantacion_ano","");
            prod1.addProperty("primera_cosecha_mes_desde","");
            prod1.addProperty("primera_cosecha_mes_hasta","");
            prod1.addProperty("segunda_cosecha_mes_desde","");
            prod1.addProperty("segunda_cosecha_mes_hasta","");
            prod1.addProperty("tercera_cosecha_mes_desde","");
            prod1.addProperty("tercera_cosecha_mes_hasta","");
            prod1.addProperty("produccion_estimada","");
            prod1.addProperty("id_tipo_medida_produccion_estimada","");
            productos.add("1",prod1);

            View viewKid2 = findViewById(R.id.ll_detail2);
            Spinner s_group_farming2= viewKid2.findViewById(R.id.s_group_farming);
            if(!(s_group_farming2.getSelectedItem().toString().equals("Elija"))){

                //TODO ADD DATA FORM SPINNER 1
                JsonObject prod2 = new JsonObject();
                prod2.addProperty("id_tipo_producto",idTipoProducto2+"");
                prod2.addProperty("id_producto_agroecologico",idProd2+"");
                prod2.addProperty("area_cultivo","");
                prod2.addProperty("id_tipo_medida_area_cultivo","");
                prod2.addProperty("fecha_siembra","");
                prod2.addProperty("fecha_cosecha","");
                prod2.addProperty("porcentaje_consumo_propio","");
                prod2.addProperty("porcentaje_venta","");
                prod2.addProperty("id_sistema_riego_usado","");
                prod2.addProperty("fecha_plantacion_mes","");
                prod2.addProperty("fecha_plantacion_ano","");
                prod2.addProperty("primera_cosecha_mes_desde","");
                prod2.addProperty("primera_cosecha_mes_hasta","");
                prod2.addProperty("segunda_cosecha_mes_desde","");
                prod2.addProperty("segunda_cosecha_mes_hasta","");
                prod2.addProperty("tercera_cosecha_mes_desde","");
                prod2.addProperty("tercera_cosecha_mes_hasta","");
                prod2.addProperty("produccion_estimada","");
                prod2.addProperty("id_tipo_medida_produccion_estimada","");
                productos.add("2",prod2);
            }
        }


        outputJson.add("productos",productos);


        return productos;
    }

    private boolean recoverData(){

        if(et_parcela_name.getText().length()==0){
            Toast.makeText(context, "Revisar nombre de parcela / Vista #"+index, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s_type.getSelectedItem().toString().equals("Elija") || s_type.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Revisar tipo de propiedad / Vista #"+index, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_medida.getText().toString().length()==0){
            Toast.makeText(context, "Ingresar medida / Vista #"+index, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s_size.getSelectedItem().toString().equals("")||s_size.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Ingresar tipo de medida / Vista #"+index, Toast.LENGTH_SHORT).show();
            return false;
        }
        // id ll_detail1
        View viewKid1 = findViewById(R.id.ll_detail1);
        Spinner s_group_farming1= viewKid1.findViewById(R.id.s_group_farming);
        if(s_group_farming1.getSelectedItem().toString().equals("Elija")){
            Toast.makeText(context, "Seleccione grupo agrícola / Vista #"+index, Toast.LENGTH_SHORT).show();
            return false;
        }

        Spinner s_production1 =viewKid1.findViewById(R.id.s_production);
        if(s_production1.getSelectedItem().toString().equals("Elija")){
            Toast.makeText(context, "Seleccione producto / Vista #"+index, Toast.LENGTH_SHORT).show();
            return false;
        }
        RadioButton rb_anual1 = viewKid1.findViewById(R.id.rb_anual);

        RadioButton rb_permanente1 = viewKid1.findViewById(R.id.rb_permanente);


        View viewKid2 = findViewById(R.id.ll_detail2);
        Spinner s_group_farming2= viewKid2.findViewById(R.id.s_group_farming);
        Spinner s_production2 =viewKid2.findViewById(R.id.s_production);
        RadioButton rb_anual2 = viewKid2.findViewById(R.id.rb_anual);
        RadioButton rb_permanente2 = viewKid2.findViewById(R.id.rb_permanente);

        //send to database
        return true;
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
}
