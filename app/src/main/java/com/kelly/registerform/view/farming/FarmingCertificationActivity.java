package com.kelly.registerform.view.farming;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.kelly.registerform.view.commerce.InformationActivity;
import com.kelly.registerform.view.livestock.LivestockProductionActivity;
import com.kelly.registerform.view.partner.RegistrationPartnerActivity;
import com.kelly.registerform.view.partner.ValidationActivity;
import com.kelly.registerform.view.transformation.ProcessActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class FarmingCertificationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private String list;
    private Context context;
    public static boolean stateDate=false;
    private Button b_next,b_back,b_file,b_date_certification;
    private EditText et_code,et_caducidad;
    private int VALOR_RETORNO = 1;
    private Spinner s_tipo,s_company,s_tipo_certificado;
    private String dateCertification="";
    public static String dateString="";
    private ArrayList<String> arrayListTipo,arrayListCompany;
    private ArrayList<Integer>idTipo,idCompany;
    private TextView tv_file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farming_certification);
        setElements();
        setActions();
    }
    private void setElements(){
        context=this;
        b_next = findViewById(R.id.b_next);
        list= getIntent().getStringExtra("list");
        b_back = findViewById(R.id.b_back);

        b_date_certification=findViewById(R.id.b_date_certification);
        et_caducidad=findViewById(R.id.et_caducidad);
        b_file= findViewById(R.id.b_file);
        tv_file = findViewById(R.id.tv_file);
        s_tipo=findViewById(R.id.s_tipo);
        arrayListTipo=new ArrayList<>();
        idTipo=new ArrayList<>();
        fillTipo();
        s_company=findViewById(R.id.s_company);
        s_tipo_certificado=findViewById(R.id.s_tipo_certificado);
        et_code = findViewById(R.id.et_code);
        arrayListCompany=new ArrayList<>();
        idCompany=new ArrayList<>();
        fillComapy();
    }

    private void setActions(){

        b_date_certification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(),"doit");
            }
        });

        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(!validation())return;
                boolean estate= validation();
                sendMain();
                if(list.equals("")){
                    Intent intent = new Intent(context, ValidationActivity.class);
                    startActivity(intent);
                }else{
                    String []listCheck =list.split(",");
                    int ini = Integer.parseInt(listCheck[0]);
                    String newList = reList(list);

                    if(ini==2){
                        Intent i = new Intent(context,LivestockProductionActivity.class);
                        i.putExtra("list",newList);
                        startActivity(i);
                    }
                    if(ini==3){
                        Intent i = new Intent(context,ProcessActivity.class);
                        i.putExtra("list",newList);
                        startActivity(i);
                    }
                    if(ini==4){
                        Intent i = new Intent(context,InformationActivity.class);
                        i.putExtra("list",newList);
                        startActivity(i);
                    }
                }
            }
        });
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        b_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);
                tv_file.setText("Se ha registrado el documento correctamente!");
            }
        });
    }
    private void sendMain(){
        JsonObject certificado = new JsonObject();
        certificado.addProperty("tiene_certificado",s_tipo_certificado.getSelectedItem().toString());
        certificado.addProperty("id_tipo_certificadora",s_tipo.getSelectedItem().toString());
        certificado.addProperty("id_empresa_certificadora",s_company.getSelectedItem().toString());
        certificado.addProperty("codigo_certificado",et_code.getText().toString());
        certificado.addProperty("fecha_caducidad",dateCertification);
        MainJson.addChild("certificado_produccion_pecuaria",certificado);
        MainJson.printBody();

    }
    private boolean validation(){
        if(s_tipo_certificado.getSelectedItem().toString().equals("Elija")
                ||s_tipo_certificado.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Indique si tiene certificado.", Toast.LENGTH_SHORT).show();
            return false;}
        if(s_tipo.getSelectedItem().toString().equals("Elija")
                ||s_tipo.getSelectedItem().toString().length()==0 && !s_tipo_certificado.getSelectedItem().toString().equals("No")){
            Toast.makeText(context, "Seleccione tipo de certificado.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s_company.getSelectedItem().toString().equals("Elija")
                ||s_company.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Debe escoger empresa.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_code.getText().length()==0){
            Toast.makeText(context, "Ingrese código", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dateCertification.length()==0){
            Toast.makeText(context, "Ingrese código", Toast.LENGTH_SHORT).show();
            return false;
        }
        //dateCertification
        return true;
    }
    private String reList(String listSplit){
        String []listCheck =listSplit.split(",");
        String out ="";
        if(listCheck.length==1){
            return "";
        }else{
            for(int i=1;i<listCheck.length;i++){
                out+=listCheck[i]+",";
            }
            return out;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
        }
        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO )) {
            //Procesar el resultado
            Uri uri = data.getData(); //obtener el uri content
        }
    }
    private void fillTipo(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_empresas_certificadoras.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("empresas_certificadoras");
                            Iterator<?> keys = jsonObj2.keys();
                            idTipo=new ArrayList<>();
                            idTipo.add(0);
                            arrayListTipo=new ArrayList<>();
                            arrayListTipo.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("empresa");
                                    idTipo.add(idDis);
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
    private void fillComapy(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
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
                                    int  idDis = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("empresa_sgp");
                                    idCompany.add(idDis);
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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = new GregorianCalendar(i, i1, i2);
        setDate(cal);
    }
    private void setDate(final Calendar calendar) {
        dateCertification = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        dateString = dateCertification;
        et_caducidad.setText(dateCertification);
        stateDate =true;
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if(!FarmingCertificationActivity.stateDate){
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), year, month, day);
            }else{
                String[] dateOwn=FarmingCertificationActivity.dateString.split("-");
                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), Integer.parseInt(dateOwn[0]), Integer.parseInt(dateOwn[1])-1, Integer.parseInt(dateOwn[2]));
            }

        }

    }
}
