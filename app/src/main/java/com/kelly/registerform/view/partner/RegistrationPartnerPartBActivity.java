package com.kelly.registerform.view.partner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kelly.registerform.BuildConfig;
import com.kelly.registerform.R;
import com.kelly.registerform.adapters.modelsAdapter.EstadoCivilAdapter;
import com.kelly.registerform.adapters.modelsAdapter.OrganizacionRegionalAdapter;
import com.kelly.registerform.model.EstadoCivil;
import com.kelly.registerform.model.OrganizacionRegional;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.model.ubigeo.Departamento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.google.gson.internal.bind.TypeAdapters.URL;

public class RegistrationPartnerPartBActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener{
    private Spinner s_number_children;
    private LinearLayout ll_children;
    private Context context;
    private Button b_next,b_back;
    private Spinner s_marital;
    private ArrayList<String> arrayListMarital;
    private ArrayList<Integer>idMarital;
    private int estado_civil;
    private int tope=0;// number of children
    public static int indexDate;
    private EditText et_nombre,et_ap_conyuge,et_am_conyuge;
    private ArrayList<Boolean> checkList=new ArrayList<>();
    private ArrayList<CheckBox> checkBoxes=new ArrayList<>();
    private ArrayList<View>listaVista;
    private ArrayList<EditText>listaNombres;
    private ArrayList<TextView>listaCumple;

    private ArrayList<CheckBox>listaChecks;
    private View v1,v2,v3,v4,v5,v6,v7,v8,v9,v10;
    public static String dateString;

    /////////Arry Helpers/////////
    public static ArrayList<Boolean> listStateDate;
    public static ArrayList<String> listTextDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_partner_part_b);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setArrays();
        setElements();
        setActions();
    }
    private void setArrays(){
        listStateDate = new ArrayList<>();
        listTextDate = new ArrayList<>();
        for(int i=0;i<9;i++){
            listStateDate.add(false);
            listTextDate.add("-");
        }

    }
    private void setElements(){
        context =this;
        s_number_children = (Spinner)findViewById(R.id.s_number_children);
        ll_children =  (LinearLayout)findViewById(R.id.ll_children);
        b_next = (Button)findViewById(R.id.b_next);
        b_back = (Button)findViewById(R.id.b_back);
        s_marital = findViewById(R.id.s_marital);
        et_nombre= findViewById(R.id.et_nombre);
        et_ap_conyuge= findViewById(R.id.et_ap_conyuge);
        et_am_conyuge= findViewById(R.id.et_am_conyuge);
        fillMarital();
        listaVista=new ArrayList<>();
        listaNombres=new ArrayList<>();
        listaCumple=new ArrayList<>();
        listaChecks=new ArrayList<>();

        for (int i=0;i<10;i++)checkList.add(false);
        fillListaView();


    }
    private void fillListaView(){
        View v1 =new View(context);
        v1 =findViewById(R.id.child1);
        listaVista.add(v1);
        View v2 =new View(context);
        v2 =findViewById(R.id.child2);
        listaVista.add(v2);
        View v3 =new View(context);
        v3 =findViewById(R.id.child3);
        listaVista.add(v3);
        View v4 =new View(context);
        v4 =findViewById(R.id.child4);
        listaVista.add(v4);
        View v5 =new View(context);
        v5 =findViewById(R.id.child5);
        listaVista.add(v5);
        View v6 =new View(context);
        v6 =findViewById(R.id.child6);
        listaVista.add(v6);
        View v7 =new View(context);
        v7 =findViewById(R.id.child7);
        listaVista.add(v7);
        View v8 =new View(context);
        v8 =findViewById(R.id.child8);
        listaVista.add(v8);
        View v9 =new View(context);
        v9 =findViewById(R.id.child9);
        listaVista.add(v9);
        View v10 =new View(context);
        v10 =findViewById(R.id.child10);
        listaVista.add(v10);
        for(int i=0;i<listaVista.size();i++){
            final int index = i;
            EditText et_nombre_hijo=listaVista.get(i).findViewById(R.id.et_nombre_hijo);
            et_nombre_hijo.setHint("Nombre y Apellido del hijo #"+(i+1));
            listaNombres.add(et_nombre_hijo);
            TextView et_birthday_hijo=listaVista.get(i).findViewById(R.id.et_birthday_hijo);
            et_birthday_hijo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt("index",index);
                    DatePickerFragment fragment = new DatePickerFragment();
                    fragment.setArguments(args);
                    fragment.show(getSupportFragmentManager(),"doit");

                }
            });
            et_birthday_hijo.setHint("Fecha de Nacimiento del hijo #"+(i+1));
            listaCumple.add(et_birthday_hijo);
            CheckBox cb_hijo=listaVista.get(i).findViewById(R.id.cb_hijo);
            listaChecks.add(cb_hijo);

        }

    }
    private void setActions(){
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        s_number_children.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 if(i>0){
                     //1,2,3....
                     int val =Integer.parseInt(s_number_children.getSelectedItem().toString());
                     tope=val;
                     for(int index=0;index<10;index++){
                         listaVista.get(index).setVisibility(View.GONE);
                     }
                     for(int index=0;index<val;index++){
                         listaVista.get(index).setVisibility(View.VISIBLE);
                     }

                 }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
                String body =getIntent().getStringExtra("jsonBody");
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject)jsonParser.parse(body);
                jo.addProperty("id_estado_civil", estado_civil);

                JsonObject jsonObjConyugue=new JsonObject();
                jsonObjConyugue.addProperty("nombres", et_nombre.getText().toString());
                jsonObjConyugue.addProperty("apellido_paterno", et_ap_conyuge.getText().toString());
                jsonObjConyugue.addProperty("apellido_materno", et_am_conyuge.getText().toString());
                jsonObjConyugue.addProperty("numero_hijos", tope);
                jo.add("conyuge", jsonObjConyugue);

                JsonObject childrenList=new JsonObject();
                for (int i=0;i<tope;i++){
                    JsonObject son=new JsonObject();
                    son.addProperty("nombres_apellidos", listaNombres.get(i).getText().toString());
                    son.addProperty("fecha_nacimiento", listaCumple.get(i).getText()+"");
                    if(listaChecks.get(i).isChecked()){
                        son.addProperty("es_dependiente", "on");
                    }else {
                        son.addProperty("es_dependiente", "off");
                    }
                    childrenList.add(""+(i+1),son);
                }

                jo.add("hijos", childrenList);




                JsonObject jsonObjBody=new JsonObject();
                jsonObjBody.add("info", jo);
                jsonObjBody.addProperty("token", "lpsk.21568$lsjANPIO02");
                //sendDataBase(jsonObjBody);

                MainJson.addChild("form1",jo);
                System.out.println(jo);
                Intent intent = new Intent(context,WelcomePartnerActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean validation(){
        EstadoCivil estadoCivil = (EstadoCivil)s_marital.getSelectedItem();

        if(estadoCivil.getName().equals("Elija") ||estadoCivil.getName().length()==0 ){
            Toast.makeText(context, "Falta elejir el estado civil.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(estadoCivil.getName().equals("Casado")
                || estadoCivil.getName().equals("Conviviente")) {
            if(et_nombre.getText().length()==0){
                Toast.makeText(context, "Falta nombre de cónyuge.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(et_ap_conyuge.getText().length()==0){
                Toast.makeText(context, "Falta apellido paterno de cónyuge.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(et_am_conyuge.getText().length()==0){
                Toast.makeText(context, "Falta apellido materno de cónyuge.", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        return true;
    }
    public  void fillMarital(){
        final EstadoCivilAdapter adapter = new EstadoCivilAdapter(context,
                R.layout.owner_spinner_item,
                EstadoCivil.listAll(EstadoCivil.class));

        s_marital.setAdapter(adapter);

        s_marital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EstadoCivil estadoCivil = adapter.getItem(i);
                estado_civil=Integer.parseInt(estadoCivil.getId_main());
                if(!estadoCivil.getName().equals("Casado") &&
                        !estadoCivil.getName().equals("Conviviente") &&
                        !estadoCivil.getName().equals("Elija")){
                    et_nombre.setEnabled(false);
                    et_ap_conyuge.setEnabled(false);
                    et_am_conyuge.setEnabled(false);
                }else{
                    et_nombre.setEnabled(true);
                    et_ap_conyuge.setEnabled(true);
                    et_am_conyuge.setEnabled(true);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void sendDataBase(JsonObject body){
        String url ="http://www.demodataexe.com/anpe/webservice/guardar_info_socio.php";
        JSONObject object=null;
        try {
            object= new JSONObject(body.toString());
            System.out.println(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jr);
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = new GregorianCalendar(i, i1, i2);
        setDate(cal);
    }
    private void setDate(final Calendar calendar) {

        String stringBirthday = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        int position =getIndexDate();
        TextView tv =listaVista.get(position).findViewById(R.id.et_birthday_hijo);
        tv.setText(stringBirthday);
        listTextDate.set(position,stringBirthday);
    }
    public int getIndexDate(){
        for(int i=0;i<9;i++)
            if(listStateDate.get(i))return i;
        return 0;
    }
    public static void  setStateUnique(int index){
        for(int i=0;i<9;i++)listStateDate.set(i,false);
        listStateDate.set(index,true);
    }
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle b = getArguments();
            int position = b.getInt("index");
            setStateUnique(position);
            System.out.println(RegistrationPartnerPartBActivity.indexDate+"");
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            if(!listTextDate.get(position).equals("-")){
                String[] dateOwn=listTextDate.get(position).split("-");
                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), Integer.parseInt(dateOwn[0]), Integer.parseInt(dateOwn[1])-1, Integer.parseInt(dateOwn[2]));
            }else{
                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), year, month, day);
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
