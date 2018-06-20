package com.kelly.registerform.view.farming;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.kelly.registerform.adapters.modelsAdapter.EmpresaCertificadoraAdapter;
import com.kelly.registerform.adapters.modelsAdapter.MedidaChacraAdapter;
import com.kelly.registerform.adapters.modelsAdapter.TipoCertificadoraAdapter;
import com.kelly.registerform.model.MedidaChacra;
import com.kelly.registerform.model.TipoPropiedad;
import com.kelly.registerform.model.certifications.EmpresaCertificadora;
import com.kelly.registerform.model.certifications.TipoCertificadora;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.view.commerce.ComercializacionActivity;
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
    private EditText et_code;
    private int VALOR_RETORNO = 1;
    private Spinner s_tipo,s_company,s_tipo_certificado;
    private String dateCertification="";
    public static String dateString="";
    private TextView et_caducidad;
    private ArrayList<String> arrayListTipo,arrayListCompany;
    private ArrayList<Integer>idTipo,idCompany;
    private TextView tv_file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farming_certification);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setElements();
        setActions();
        refillData();
    }
    private void refillData(){/*
        JsonObject main = MainJson.getByKey("certificado_produccion_agricola");
        if(main ==null)return;
        et_code.setText(main.get("codigo_certificado").getAsString());
        stateDate=true;
        dateString=main.get("fecha_caducidad").getAsString();
        if(main.get("tiene_certificado").getAsString().equals("Sí"))
            s_tipo_certificado.setSelection(1);
        else s_tipo_certificado.setSelection(2);

        s_tipo.setSelection(getIndexCertificadora(s_tipo, main.get("id_tipo_certificadora").getAsString()));


        s_company.setSelection(getIndexCompany(s_company, main.get("id_empresa_certificadora").getAsString()));*/
    }
    private int getIndexCertificadora(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            EmpresaCertificadora empresaCertificadora = (EmpresaCertificadora)spinner.getAdapter().getItem(i);
            if (empresaCertificadora.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
    private int getIndexCompany(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            TipoCertificadora tipoCertificadora = (TipoCertificadora)spinner.getAdapter().getItem(i);
            if (tipoCertificadora.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private void setElements(){
        context=this;
        b_next = findViewById(R.id.b_next);
        list= getIntent().getStringExtra("list");
        b_back = findViewById(R.id.b_back);


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

        et_caducidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(),"doit");
            }
        });

        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
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
                        Intent i = new Intent(context,ComercializacionActivity.class);
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

        TipoCertificadora t = (TipoCertificadora)s_tipo.getSelectedItem();
        certificado.addProperty("id_tipo_certificadora",t.getName());

        EmpresaCertificadora e = (EmpresaCertificadora)s_company.getSelectedItem();
        certificado.addProperty("id_empresa_certificadora",e.getName());

        certificado.addProperty("codigo_certificado",et_code.getText().toString());
        certificado.addProperty("fecha_caducidad",dateCertification);
        MainJson.addChild("certificado_produccion_agricola",certificado);
        MainJson.printBody();

    }
    private boolean validation(){

        if(s_tipo_certificado.getSelectedItem().toString().equals("Elija")
                ||s_tipo_certificado.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Indique si tiene certificado.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_tipo_certificado.getSelectedItem().toString().equals("Sí")){
            TipoCertificadora t = (TipoCertificadora)s_tipo.getSelectedItem();
            if(t.getName().equals("Elije")
                    ||t.getName().length()==0 && !t.getName().equals("No")){
                Toast.makeText(context, "Seleccione tipo de certificado.", Toast.LENGTH_SHORT).show();
                return false;
            }
            EmpresaCertificadora e = (EmpresaCertificadora)s_company.getSelectedItem();
            if(e.getName().equals("Elije")
                    ||e.getName().length()==0){
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

        }

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
        final TipoCertificadoraAdapter adapter = new TipoCertificadoraAdapter(context,
                R.layout.owner_spinner_item,
                TipoCertificadora.listAll(TipoCertificadora.class));

        s_tipo.setAdapter(adapter);

        s_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
