package com.kelly.registerform.view.partner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.kelly.registerform.dataAccess.ProvinceDA;
import com.kelly.registerform.model.ubigeo.Departamento;
import com.kelly.registerform.model.ubigeo.Provincia;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegistrationPartnerActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    private static final int PICK_IMAGE = 100;
    private Button b_next,b_dni,b_photo;
    private Context context;
    private Spinner gender_spinner,regions_spinner,province_spinner,district_spinner,s_asoc,s_tipo,s_local,s_regional;
    private String lattitude,longitude;
    private TextView tv_dni,tv_photo;
    private LocationManager locationManager;
    private Uri imageUri;
    private ImageView foto_gallery;
    private ArrayList<String>arrayListDeparment,nameProvince,nameDistrict,nameAsoc,nameTipo,nameLocal,nameRegional;
    private ArrayList<Integer>idProvince,idDistrict,idAsoc,idTipo,idLocal,idRegional;
    private EditText et_name,et_ap,et_am,et_birthday,et_fijo,et_celular,et_email,et_direccion,
            et_comunidad,et_longitud,et_latitud;
    int id_dep,id_prov,id_dist,idTipoSocio,idAsociacion,idOrgLocal,idOrgRegional;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_partner);
        createArrays();
        setElements();
        setActions();
    }
    private void setElements(){
        context = this;
        b_next = (Button)findViewById(R.id.b_next);
        b_dni= (Button)findViewById(R.id.b_dni);
        b_photo= (Button)findViewById(R.id.b_photo);

        tv_dni = findViewById(R.id.tv_dni);
        tv_photo = findViewById(R.id.tv_photo);


        //gender_spinner=(Spinner)findViewById(R.id.gender_spinner);
        //String[] arraySpinner = new String[] {"Elija", "Femenino", "Masculino"};
        //gender_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner));
        regions_spinner=(Spinner)findViewById(R.id.regions_spinner);
        province_spinner=findViewById(R.id.province_spinner);
        district_spinner=findViewById(R.id.district_spinner);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayListDeparment);
        nameDistrict=new ArrayList<>();
        nameProvince=new ArrayList<>();
        idDistrict=new ArrayList<>();
        idProvince=new ArrayList<>();
        regions_spinner.setAdapter(spinnerArrayAdapter);
        gender_spinner =findViewById(R.id.gender_spinner);
        et_name=findViewById(R.id.et_name);
        et_ap=findViewById(R.id.et_ap);
        et_am=findViewById(R.id.et_am);
        et_birthday=findViewById(R.id.et_birthday);
        et_fijo=findViewById(R.id.et_fijo);
        et_celular = findViewById(R.id.et_celular);
        et_email = findViewById(R.id.et_email);
        et_direccion= findViewById(R.id.et_direccion);
        et_comunidad= findViewById(R.id.et_comunidad);
        et_latitud= findViewById(R.id.et_latitud);
        et_longitud= findViewById(R.id.et_longitud);

        s_asoc=findViewById(R.id.s_asociacion);
        s_tipo=findViewById(R.id.s_tipo);
        s_local=findViewById(R.id.s_local);
        s_regional=findViewById(R.id.s_regional);
        nameAsoc=new ArrayList<>(); nameTipo=new ArrayList<>();nameLocal=new ArrayList<>();nameRegional=new ArrayList<>();
        idAsoc=new ArrayList<>();idTipo=new ArrayList<>();idLocal=new ArrayList<>();idRegional=new ArrayList<>();
        fillTipo();
        fillAsoc();
        fillLocal();
        fillRegional();
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create json
                //JSONObject jsonObj = new JSONObject();

                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("nombres", et_name.getText().toString());
                jsonObject.addProperty("apellido_paterno", et_ap.getText().toString());
                jsonObject.addProperty("apellido_materno", et_am.getText().toString());
                jsonObject.addProperty("sexo", gender_spinner.getSelectedItem().toString());
                jsonObject.addProperty("fecha_nacimiento", et_birthday.getText().toString());
                jsonObject.addProperty("telefono", et_fijo.getText().toString());
                jsonObject.addProperty("celular", et_celular.getText().toString());
                jsonObject.addProperty("email", et_email.getText().toString());
                jsonObject.addProperty("id_departamento",id_dep+"");
                jsonObject.addProperty("id_provincia", id_prov+"");
                jsonObject.addProperty("id_distrito", id_dist);
                jsonObject.addProperty("comunidad", et_comunidad.getText().toString());
                jsonObject.addProperty("direccion", et_direccion.getText().toString());
                jsonObject.addProperty("ubicacion_latitud_gps", et_latitud.getText().toString());
                jsonObject.addProperty("ubicacion_longitud_gps", et_longitud.getText().toString());
                jsonObject.addProperty("id_tipo_socio", idTipoSocio);
                jsonObject.addProperty("id_tipo_asociacion", idAsociacion);
                jsonObject.addProperty("id_organizacion_local",1);//TODO change
                jsonObject.addProperty("id_organizacion_regional", 1);//TODO change

                Intent intent= new Intent(context,RegistrationPartnerPartBActivity.class);
                intent.putExtra("jsonBody",jsonObject.toString());
                startActivity(intent);
            }
        });

        b_dni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        b_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        regions_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String deparment =  regions_spinner.getSelectedItem().toString();
                //TODO make select in order to get all provionces belong to deparment
                List<Departamento> departamentoList = Departamento.findWithQuery(Departamento.class,
                        "Select * from Departamento where name = ?", deparment);
                fillProvinceList(departamentoList.get(0));
                id_dep=departamentoList.get(0).getId_departamento();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        province_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String data =  regions_spinner.getSelectedItem().toString();
                fillDistrict(idProvince.get(i));
                id_prov=idProvince.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_dist=idDistrict.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_asoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idAsociacion=idAsoc.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_local.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //idOrgLocal=idLocal.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_regional.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //idOrgRegional=idRegional.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                idTipoSocio=idTipo.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    public void createArrays(){
      List<Departamento> departamentoList = Departamento.listAll(Departamento.class);
        arrayListDeparment =new ArrayList<>();
      for (int i=0;i<departamentoList.size();i++){
          System.out.println(departamentoList.get(i).getName());
          arrayListDeparment.add(departamentoList.get(i).getName());
      }

    }
    private void fillProvinceList(Departamento departamento){

        int id=departamento.getId_departamento();

        RequestQueue queue = Volley.newRequestQueue(this.context);

        String url = BuildConfig.BASE_URL+"lista_ubigeo.php?tipo=PROV&idubigeosuperior="+id+"&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("departamentos");
                            Iterator<?> keys = jsonObj2.keys();
                            idProvince=new ArrayList<>();
                            nameProvince=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    idProvince.add(idDis);
                                    nameProvince.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameProvince);
                            province_spinner.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            province_spinner.setAdapter(null);
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

    private void fillDistrict(int idProvince){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_ubigeo.php?tipo=DIST&idubigeosuperior="+idProvince+"&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("departamentos");
                            Iterator<?> keys = jsonObj2.keys();
                            idDistrict=new ArrayList<>();
                            nameDistrict=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    idDistrict.add(idDis);
                                    nameDistrict.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameDistrict);
                            district_spinner.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            district_spinner.setAdapter(null);
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
    private void fillTipo(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_tipo_socio.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_socio");
                            Iterator<?> keys = jsonObj2.keys();
                            idTipo=new ArrayList<>();
                            nameTipo=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idTipo.add(Integer.parseInt(key));
                                    nameTipo.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameTipo);
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
    private void fillAsoc(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_tipo_asociacion.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("asociaciones");
                            Iterator<?> keys = jsonObj2.keys();
                            idAsoc=new ArrayList<>();
                            nameAsoc=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idAsoc.add(Integer.parseInt(key));
                                    nameAsoc.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameAsoc);
                            s_asoc.setAdapter(spinnerArrayAdapter);

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
    private void fillLocal(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_tipo_organizacion.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("organizaciones_locales");
                            Iterator<?> keys = jsonObj2.keys();
                            idLocal=new ArrayList<>();
                            nameLocal=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("organizacion");
                                    idLocal.add(id);
                                    nameLocal.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idLocal.add(Integer.parseInt(key));
                                    nameLocal.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameLocal);
                            s_local.setAdapter(spinnerArrayAdapter);

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
    private void fillRegional(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_tipo_organizacion_regional.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("organizaciones_regionales");
                            Iterator<?> keys = jsonObj2.keys();
                            idRegional=new ArrayList<>();
                            nameRegional=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("organizacion");
                                    idRegional.add(id);
                                    nameRegional.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idRegional.add(Integer.parseInt(key));
                                    nameRegional.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameRegional);
                            s_regional.setAdapter(spinnerArrayAdapter);

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
