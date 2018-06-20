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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.kelly.registerform.adapters.modelsAdapter.AsociacionAdapter;
import com.kelly.registerform.adapters.modelsAdapter.DistrictAdapter;
import com.kelly.registerform.adapters.modelsAdapter.OrganizacionRegionalAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProvinceAdapter;
import com.kelly.registerform.adapters.modelsAdapter.SpinAdapter;
import com.kelly.registerform.adapters.modelsAdapter.TipoSocioAdapter;
import com.kelly.registerform.controllers.DeparmentController;
import com.kelly.registerform.dataAccess.DepartmentDA;
import com.kelly.registerform.dataAccess.ProvinceDA;
import com.kelly.registerform.model.Asociacion;
import com.kelly.registerform.model.OrganizacionRegional;
import com.kelly.registerform.model.TipoSocio;
import com.kelly.registerform.model.ubigeo.Departamento;
import com.kelly.registerform.model.ubigeo.Distrito;
import com.kelly.registerform.model.ubigeo.Principal;
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

    //////////////DATEPICKER////////////

    public static boolean stateDate=false;
    public static String dateString="";
    private Button b_birthday;
    private String stringBirthday="";
    private Context context;
    private Spinner gender_spinner,regions_spinner,province_spinner,district_spinner,s_asoc,s_tipo,
    s_regional;
    private String lattitude,longitude,id_dep,id_prov,id_dist;
    private TextView tv_dni,tv_photo,et_text_birthday;
    private LocationManager locationManager;
    private Uri imageUri;
    private ImageView foto_gallery;
    private ArrayList<String>arrayListDeparment,nameDeparment,nameProvince,nameDistrict,nameAsoc,
            nameTipo,nameRegional,idDeparment,idProvince,idDistrict;
    private ArrayList<Integer>idAsoc,idTipo,idRegional;
    private EditText et_name,et_ap,et_am,et_fijo,et_celular,et_email,et_direccion,
            et_comunidad,et_longitud,et_latitud,et_dni,s_local;
    int idTipoSocio=0,idAsociacion=0,idOrgRegional=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_partner);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setElements();
        setActions();
    }
    private void setElements(){
        context = this;
        b_next =findViewById(R.id.b_next);
        b_dni= findViewById(R.id.b_dni);
        b_photo= findViewById(R.id.b_photo);

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
        nameRegional=new ArrayList<>();

        idAsoc=new ArrayList<>();
        idTipo=new ArrayList<>();
        idRegional=new ArrayList<>();

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

        Departamento d = (Departamento)regions_spinner.getSelectedItem();
        if(d.getName().equals("Elije") ||d.getName().length()==0 ){
            Toast.makeText(context, "Falta elejir Departamento.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!d.getName().equals("Elije") && d.getName().length()>0){
            Provincia p = (Provincia) province_spinner.getSelectedItem();
            if(p.getName().equals("Elija") ||p.getName().length()==0){
                Toast.makeText(context, "Falta elejir Provincia.", Toast.LENGTH_SHORT).show();
                return false;
            }
            Distrito dist = (Distrito) district_spinner.getSelectedItem();
            if(dist.getName().equals("Elija") ||dist.getName().length()==0 ){
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
        TipoSocio t =(TipoSocio)s_tipo.getSelectedItem();
        if(t.getName().equals("Elije") ||t.getName().length()==0 ){
            Toast.makeText(context, "Falta elejir el tipo de socio.", Toast.LENGTH_SHORT).show();
            return false;
        }

        Asociacion a =(Asociacion)s_asoc.getSelectedItem();
        if(a.getName().equals("Elije") ||a.getName().length()==0 ){
            Toast.makeText(context, "Falta elejir el tipo de asociación.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void setActions(){
        et_text_birthday.setOnClickListener(new View.OnClickListener() {
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
                jsonObject.addProperty("id_organizacion_local",s_local.getText().toString());
                jsonObject.addProperty("id_organizacion_regional", idOrgRegional);//TODO change

                System.out.println("jsonBody \t"+jsonObject.toString());
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
        Iterator<?> keys = Provincia.findAll(Provincia.class);
        List<Provincia> currentList= new ArrayList<>();
        while(keys.hasNext()){
          Provincia p = (Provincia)keys.next();
            if (p.getId_parent().equals(idDeparment))currentList.add(p);
        }

        final ProvinceAdapter adapter = new ProvinceAdapter(context,R.layout.owner_spinner_item,
                currentList);

        province_spinner.setAdapter(adapter);

        province_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Provincia provincia = adapter.getItem(i);
                fillDistrict(provincia.getId_provincia());
                id_prov=provincia.getId_provincia();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void fillDistrict(String idProvince){

        Iterator<?> keys = Distrito.findAll(Distrito.class);
        List<Distrito> currentList= new ArrayList<>();
        while(keys.hasNext()){
            Distrito p = (Distrito)keys.next();
            if (p.getId_parent().equals(idProvince))currentList.add(p);
        }

        final DistrictAdapter adapter = new DistrictAdapter(context,R.layout.owner_spinner_item,
                currentList);

        district_spinner.setAdapter(adapter);

        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Distrito distrito = adapter.getItem(i);
                id_dist=distrito.getId_ditrito();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void fillTipo(){
        final TipoSocioAdapter adapter = new TipoSocioAdapter(context,
                R.layout.owner_spinner_item,
                TipoSocio.listAll(TipoSocio.class));

        s_tipo.setAdapter(adapter);

        s_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TipoSocio tipoSocio = adapter.getItem(i);
                idTipoSocio=Integer.parseInt(tipoSocio.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    private void fillAsoc(){
        final AsociacionAdapter adapter = new AsociacionAdapter(context,
                R.layout.owner_spinner_item,
                Asociacion.listAll(Asociacion.class));

        s_asoc.setAdapter(adapter);

        s_asoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Asociacion asociacion = adapter.getItem(i);
                idAsociacion=Integer.parseInt(asociacion.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    private void fillRegional(){
        final OrganizacionRegionalAdapter adapter = new OrganizacionRegionalAdapter(context,
                R.layout.owner_spinner_item,
                OrganizacionRegional.listAll(OrganizacionRegional.class));

        s_regional.setAdapter(adapter);

        s_regional.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OrganizacionRegional organizacionRegional = adapter.getItem(i);
                idOrgRegional=Integer.parseInt(organizacionRegional.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*
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
        queue.add(stringRequest);*/

    }
    public void fillDeparment(){
        final SpinAdapter adapter = new SpinAdapter(context,
                R.layout.owner_spinner_item,
                Departamento.listAll(Departamento.class));

        regions_spinner.setAdapter(adapter);

        regions_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Departamento departamento = adapter.getItem(i);
                fillProvinceList(departamento.getId_departamento());
                id_dep=departamento.getId_departamento();
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
        et_text_birthday.setText(stringBirthday);
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
                String[] dateOwn=dateString.split("-");
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
