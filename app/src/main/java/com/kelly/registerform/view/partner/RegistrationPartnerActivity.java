package com.kelly.registerform.view.partner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.kelly.registerform.view.MapsActivity;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import static android.provider.Telephony.ThreadsColumns.DATE;

public class RegistrationPartnerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static final int REQUEST_LOCATION = 1;
    private static final int PICK_IMAGE = 100;
    private int VALOR_RETORNO = 1;
    private Button b_next,b_dni,b_photo,b_map;

    //////////////DATEPICKER
    public static boolean stateDate=false;
    public static String dateString="";
    private Button b_birthday;
    private String stringBirthday="";
    private Context context;
    private Spinner gender_spinner,regions_spinner,province_spinner,district_spinner,s_asoc,s_tipo,
            s_local,s_regional;
    private String lattitude,longitude,id_dep,id_prov,id_dist;
    private TextView tv_dni,tv_photo;
    private LocationManager locationManager;
    private Uri imageUri;
    private ImageView foto_gallery;
    private ArrayList<String>arrayListDeparment,nameDeparment,nameProvince,nameDistrict,nameAsoc,
            nameTipo,nameLocal,nameRegional,idDeparment,idProvince,idDistrict;
    private ArrayList<Integer>idAsoc,idTipo,idLocal,idRegional;
    private EditText et_name,et_ap,et_am,et_fijo,et_celular,et_email,et_direccion,
            et_comunidad,et_longitud,et_latitud,et_dni,et_text_birthday;
    int idTipoSocio=0,idAsociacion=0,idOrgLocal=0,idOrgRegional=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_partner);
        setElements();
        setActions();
    }
    private void setElements(){
        context = this;
        b_next =findViewById(R.id.b_next);
        b_dni= findViewById(R.id.b_dni);
        b_photo= findViewById(R.id.b_photo);
        b_birthday=findViewById(R.id.b_birthday);
        et_text_birthday=findViewById(R.id.et_text_birthday);

        tv_dni = findViewById(R.id.tv_dni);
        tv_photo = findViewById(R.id.tv_photo);
        b_map= (Button)findViewById(R.id.b_map);

        regions_spinner=(Spinner)findViewById(R.id.regions_spinner);
        province_spinner=findViewById(R.id.province_spinner);
        district_spinner=findViewById(R.id.district_spinner);


        nameDistrict=new ArrayList<>();
        nameDeparment =  new ArrayList<>();
        nameProvince=new ArrayList<>();

        idDeparment=new ArrayList<>();
        idDistrict=new ArrayList<>();
        idProvince=new ArrayList<>();
        gender_spinner =findViewById(R.id.gender_spinner);
        et_name=findViewById(R.id.et_name);
        et_ap=findViewById(R.id.et_ap);
        et_am=findViewById(R.id.et_am);

        et_fijo=findViewById(R.id.et_fijo);
        et_dni=findViewById(R.id.et_dni);
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

        nameAsoc=new ArrayList<>();
        nameTipo=new ArrayList<>();
        nameLocal=new ArrayList<>();
        nameRegional=new ArrayList<>();

        idAsoc=new ArrayList<>();
        idTipo=new ArrayList<>();
        idLocal=new ArrayList<>();
        idRegional=new ArrayList<>();

        fillLocal();
        fillRegional();
        fillDeparment();
        fillTipo();
        fillAsoc();

    }
    private boolean validation(){
        if(et_name.getText().equals("") || et_name.getText().length()==0){
            Toast.makeText(context, "Falta nombre de socio.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_ap.getText().equals("") || et_ap.getText().length()==0){
            Toast.makeText(context, "Falta apellido paterno.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_am.getText().equals("") || et_am.getText().length()==0){
            Toast.makeText(context, "Falta apellido materno.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(gender_spinner.getSelectedItem().toString().equals("Elija")){
            Toast.makeText(context, "Falta elejir el sexo.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_dni.getText().length()<8){
            Toast.makeText(context, "El DNI debe tener 8 dígitos", Toast.LENGTH_SHORT).show();

            return false;
        }
        if(et_dni.getText().length()==0){
            Toast.makeText(context, "Falta ingresar DNI.", Toast.LENGTH_SHORT).show();

            return false;
        }
        if(stringBirthday.length()==0){
            Toast.makeText(context, "Falta ingresar fecha de cumpleaños.", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if(et_fijo.getText().length()==0){
            Toast.makeText(context, "Falta ingresar teléfono.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_celular.getText().length()==0){
            Toast.makeText(context, "Falta ingresar celular.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_email.getText().length()==0){
            Toast.makeText(context, "Falta ingresar celular.", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        if(regions_spinner.getSelectedItem().toString().equals("Elija") ||regions_spinner.getSelectedItem().toString().length()==0 ){
            Toast.makeText(context, "Falta elejir Departamento.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!regions_spinner.getSelectedItem().toString().equals("Elija") && regions_spinner.getSelectedItem().toString().length()>0){

            if(province_spinner.getSelectedItem().toString().equals("Elija") ||province_spinner.getSelectedItem().toString().length()==0){
                Toast.makeText(context, "Falta elejir Provincia.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(district_spinner.getSelectedItem().toString().equals("Elija") ||district_spinner.getSelectedItem().toString().length()==0 ){
                Toast.makeText(context, "Falta elejir Distrito.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }



        if(et_direccion.getText().length()==0){
            Toast.makeText(context, "Falta dirección.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_direccion.getText().length()<4){
            Toast.makeText(context, "Debe ingresar una dirección válida.", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if(et_comunidad.getText().length()<5|| et_comunidad.getText().length()==0){
            Toast.makeText(context, "Falta comunidad.", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        if(s_tipo.getSelectedItem().toString().equals("Elija") ||s_tipo.getSelectedItem().toString().length()==0 ){
            Toast.makeText(context, "Falta elejir el tipo de socio.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s_asoc.getSelectedItem().toString().equals("Elija") ||s_asoc.getSelectedItem().toString().length()==0 ){
            Toast.makeText(context, "Falta elejir el tipo de asociación.", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if(s_local.getSelectedItem().toString().equals("Elija") ||s_local.getSelectedItem().toString().length()==0 ){
            Toast.makeText(context, "Falta elejir la organización local.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s_regional.getSelectedItem().toString().equals("Elija") ||s_regional.getSelectedItem().toString().length()==0 ){
            Toast.makeText(context, "Falta elejir la organización regional.", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }
    private void setActions(){
        b_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(),"doit");
            }
        });
        b_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("nombres", et_name.getText().toString());
                jsonObject.addProperty("apellido_paterno", et_ap.getText().toString());
                jsonObject.addProperty("apellido_materno", et_am.getText().toString());
                jsonObject.addProperty("sexo", gender_spinner.getSelectedItem().toString());
                jsonObject.addProperty("fecha_nacimiento",stringBirthday);
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
                tv_dni.setText("Se ha registrado la imagen correctamente!");
            }
        });
        b_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
                tv_photo.setText("Se ha registrado la imagen correctamente!");
            }
        });
        regions_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillProvinceList(idDeparment.get(i));
                id_dep=idDeparment.get(i);
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

    private void fillProvinceList(String idDeparment){

        String id=idDeparment;

        RequestQueue queue = Volley.newRequestQueue(this.context);

        String url = BuildConfig.BASE_URL+"lista_ubigeo.php?tipo=PROV&idubigeosuperior="+id+"&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("ubigeos");
                            Iterator<?> keys = jsonObj2.keys();
                            idProvince=new ArrayList<>();
                            idProvince.add("0");
                            nameProvince=new ArrayList<>();
                            nameProvince.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    //int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    idProvince.add((jsonDepartment.get("id")).toString());
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

    private void fillDistrict(String idProvince){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_ubigeo.php?tipo=DIST&idubigeosuperior="+idProvince+"&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("ubigeos");
                            Iterator<?> keys = jsonObj2.keys();
                            idDistrict=new ArrayList<>();
                            idDistrict.add("0");
                            nameDistrict=new ArrayList<>();
                            nameDistrict.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    //int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    idDistrict.add((jsonDepartment.get("id")).toString());
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
                            idTipo.add(0);
                            nameTipo=new ArrayList<>();
                            nameTipo.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("tipo_socio");
                                    idTipo.add(id);
                                    //idRegional.get(id);
                                    nameTipo.add(name);
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
                            idAsoc.add(0);
                            nameAsoc=new ArrayList<>();
                            nameAsoc.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("asociacion");
                                    idAsoc.add(id);
                                    nameAsoc.add(name);
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
                            s_asoc.setAdapter(null);
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
                            idLocal.add(0);
                            nameLocal=new ArrayList<>();
                            nameLocal.add("Elija");
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
                            s_local.setAdapter(null);
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
                            idRegional.add(0);
                            nameRegional=new ArrayList<>();
                            nameRegional.add("Elija");
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
                            //s_regional.setAdapter(null);
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
    public void fillDeparment(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=DEPT&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("ubigeos");
                            Iterator<?> keys = jsonObj2.keys();
                            idDeparment=new ArrayList<>();
                            idDeparment.add("0");
                            nameDeparment=new ArrayList<>();
                            nameDeparment.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String name =(String)jsonDepartment.get("ubigeo");
                                    idDeparment.add(jsonDepartment.get("id").toString());
                                    nameDeparment.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameDeparment);
                            regions_spinner.setAdapter(spinnerArrayAdapter);
                        } catch (JSONException e) {
                            regions_spinner.setAdapter(null);
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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = new GregorianCalendar(i, i1, i2);
        setDate(cal);
    }
    private void setDate(final Calendar calendar) {
        stringBirthday = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        dateString = stringBirthday;
        et_text_birthday.setText(stringBirthday);
        //b_birthday.setKeyListener(null);
        stateDate =true;
    }
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if(!RegistrationPartnerActivity.stateDate){
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), year, month, day);
            }else{
                String[] dateOwn=RegistrationPartnerActivity.dateString.split("-");
                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), Integer.parseInt(dateOwn[0]), Integer.parseInt(dateOwn[1])-1, Integer.parseInt(dateOwn[2]));
            }

        }

    }
    public void showDatePickerDialog(View v) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"doit");

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO )) {
            //Procesar el resultado
            Uri uri = data.getData(); //obtener el uri content
        }

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                String[]datos=result.split(",");
                et_longitud.setText(datos[1]);
                et_latitud.setText(datos[0]);
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

    }
}
