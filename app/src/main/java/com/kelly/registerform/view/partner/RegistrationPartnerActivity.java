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
    private Spinner gender_spinner,regions_spinner,province_spinner,district_spinner;
    private String lattitude,longitude;
    private TextView tv_dni,tv_photo;
    private LocationManager locationManager;
    private Uri imageUri;
    private ImageView foto_gallery;
    private ArrayList<String>arrayListDeparment,nameProvince,nameDistrict;
    private ArrayList<Integer>idProvince,idDistrict;
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

    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,RegistrationPartnerPartBActivity.class);
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
}
