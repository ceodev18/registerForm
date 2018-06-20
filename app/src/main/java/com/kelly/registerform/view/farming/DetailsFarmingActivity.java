package com.kelly.registerform.view.farming;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.kelly.registerform.adapters.modelsAdapter.DistrictAdapter;
import com.kelly.registerform.adapters.modelsAdapter.EstadoCivilAdapter;
import com.kelly.registerform.adapters.modelsAdapter.GrupoAgricolaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.MedidaChacraAdapter;
import com.kelly.registerform.adapters.modelsAdapter.MedidaProduccionAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProductoAgricolaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.SistemaRiegoAdapter;
import com.kelly.registerform.adapters.modelsAdapter.TipoPropiedadAdapter;
import com.kelly.registerform.model.EstadoCivil;
import com.kelly.registerform.model.GrupoAgricola;
import com.kelly.registerform.model.MedidaChacra;
import com.kelly.registerform.model.MedidaProduccion;
import com.kelly.registerform.model.ProductoAgricola;
import com.kelly.registerform.model.SistemaRiego;
import com.kelly.registerform.model.TipoPropiedad;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.model.ubigeo.Distrito;
import com.kelly.registerform.view.MainActivity;
import com.kelly.registerform.view.MapsActivity;
import com.kelly.registerform.view.partner.RegistrationPartnerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class DetailsFarmingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Button b_save;
    private String position;
    private Context context=this;

    private static final String INDEX = "index";
    private int VALOR_RETORNO = 1;
    private int color,total_percentage1=100,total_percentage2=100;
    private int total_percentage1_p=100,total_percentage2_p=100;
    private int index,globalType,idTipoProducto1,idTipoProducto2,idProd1,idProd2,globalSize;
    public int indexPage=1;
    private LinearLayout ll_1,ll_2,anual_option1,anual_option2,permanent_option1,permanent_option2;
    private TextView tv_show1,tv_show2,tv_metros,tv_hectareas,tv_yugadas;
    private ArrayList<TextView> textViewArrayList;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private ArrayList<String>arrayListType,arrayListSize,arrayListRiegoPermanente;
    private ArrayList<Integer>idType,idSize,idRiegoPermanente;
    private ArrayList<Boolean>listState;
    private Spinner s_type,s_size;
    private int[] viewList;
    private ArrayList<Integer>g1,g2,p1,p2;
    private ArrayList<String>nameListG1,nameListG2,nameListP1,nameListp2,tipoCultivo1,tipoCultivo2;
    private Spinner spinnerGroup1,spinnerGroup2,spinnerProduct1,spinnerProduct2;
    private View v1,v2,anual1,anual2,permanente1,permanente2;
    private Button b_map;
    public EditText et_latitude,et_longitude,et_parcela_name,et_medida;
    public String name_chacra="";
    public JsonObject outputJson;
    Button b_file;
    private  TextView tv_title,tv_file,tv_siembra_date1,tv_siembra_date2,tv_cosecha_date1,tv_cosecha_date2;

    public static String dateString="";
    public static boolean stateDate=false;
    private String stringBirthday="";
    private static boolean[] listDatesFarming={false,false,false,false};
    private static boolean[] clicks={false,false,false,false};
    private static String[] listDatesFarmingString={"","","",""};

    int s_propio1 = 0,s_semilla1 = 0,s_venta1 = 0;
    int s_propio1_p = 0,s_semilla1_p = 0,s_venta1_p = 0;
    int s_propio2 = 0,s_semilla2 = 0,s_venta2 = 0;
    int s_propio2_p = 0,s_semilla2_p = 0,s_venta2_p = 0;


    //details
    int size1 =0 ,size2 = 0,riego_usado1 = 0,riego_usado2 = 0,
            riego_usado1P=0,riego_usado2P=0,tipo_medida1=0,tipo_medida2=0,tipo_medida_permanente1=0,tipo_medida_permanente2=0;
    ArrayList<Integer>idRiego = new ArrayList<>();
    ArrayList<String>arrayListRiego = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_farm1);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        position=getIntent().getStringExtra("index");

        int aux = Integer.parseInt(position);
        aux++;
        ab.setTitle("Chacra "+aux);

        setElements();
        setAction();
        refillData();
    }
    private void  refillData(){
        JsonObject main =ProductionActivity.jsonObjectArrayList.get(Integer.parseInt(position));
        if(main.size()==0)return;

        et_latitude.setText(main.get("ubicacion_latitud_gps").getAsString());
        et_longitude.setText(main.get("ubicacion_longitud_gps").getAsString());
        et_parcela_name.setText(main.get("nombre_chacra").getAsString());
        et_medida.setText(main.get("medida_chacra").getAsString());
        s_type.setSelection(getIndexType(s_type, main.get("id_tipo_propiedad").getAsString()));
        s_size.setSelection(getIndexSize(s_size, main.get("id_tipo_medida").getAsString()));
        JsonObject jsonObject = main.get("productos").getAsJsonObject();

        for(int i=0;i<jsonObject.size();i++){
            //permanente 20
        }

    }


    //private method of your class
    private int getIndexType(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            TipoPropiedad tipoPropiedad = (TipoPropiedad)spinner.getAdapter().getItem(i);
            if (tipoPropiedad.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    //private method of your class
    private int getIndexSize(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            MedidaChacra medidaChacra = (MedidaChacra)spinner.getAdapter().getItem(i);
            if (medidaChacra.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
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


    }
    private void setActionsSpinner(){

    }
    public void setDataAnual(final int id){


        View view;
        view =(id==1)?anual1:anual2;
        final Spinner s_size_i= view.findViewById(R.id.s_size);
        final Spinner s_medida_produccion= view.findViewById(R.id.s_medida_produccion);
        final Spinner s_riego_usado_i= view.findViewById(R.id.s_riego_usado);

        ArrayList<String> lista = new ArrayList<>();
        lista.add("Elija");
        for (int j=0;j<101;j++){
            lista.add(j+"");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,lista);

        final Spinner s_propio = view.findViewById(R.id.s_propio);
        s_propio.setAdapter(adapter);
        final Spinner s_semilla= view.findViewById(R.id.s_semilla);
        s_semilla.setAdapter(adapter);
        final Spinner s_venta= view.findViewById(R.id.s_venta);
        s_venta.setAdapter(adapter);

        s_propio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!s_propio.getSelectedItem().toString().equals("Elija")){

                    if(id==1) {
                        total_percentage1 = 100 - Integer.parseInt(s_propio.getSelectedItem().toString());
                        s_propio1 = Integer.parseInt(s_propio.getSelectedItem().toString());
                        s_venta1 =0;



                        ArrayList<String> listaPercentage = new ArrayList<>();
                        listaPercentage.add("Elija");
                        for (int j=0;j<=total_percentage1;j++){
                            listaPercentage.add(j+"");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercentage);
                        s_semilla.setAdapter(adapter);
                        s_semilla.setSelection(total_percentage1+1);



                    }else {
                        total_percentage2-=Integer.parseInt(s_propio.getSelectedItem().toString());
                        s_propio2 = Integer.parseInt(s_propio.getSelectedItem().toString());
                        s_venta2 =0;



                        ArrayList<String> listaPercentage = new ArrayList<>();
                        listaPercentage.add("Elija");
                        for (int j=0;j<=total_percentage2;j++){
                            listaPercentage.add(j+"");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercentage);
                        s_semilla.setAdapter(adapter);
                        s_semilla.setSelection(total_percentage2+1);


                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_semilla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(!s_semilla.getSelectedItem().toString().equals("Elija")){
                    if(id==1){
                        s_semilla1 = Integer.parseInt(s_semilla.getSelectedItem().toString());

                        ArrayList<String> listaPercent = new ArrayList<>();
                        s_venta1 = 100 - s_semilla1 - s_propio1;
                        listaPercent.add(""+s_venta1);
                        ArrayAdapter<String> adapt = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercent);
                        s_venta.setAdapter(adapt);
                    }else{
                        s_semilla2 = Integer.parseInt(s_semilla.getSelectedItem().toString());

                        ArrayList<String> listaPercent = new ArrayList<>();
                        s_venta2 = 100 - s_semilla2 - s_propio2;
                        listaPercent.add(""+s_venta2);
                        ArrayAdapter<String> adapt = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercent);
                        s_venta.setAdapter(adapt);
                    }


                }


                
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_venta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tv_siembra_date1 = anual1.findViewById(R.id.tv_siembra);
        tv_siembra_date2 = anual2.findViewById(R.id.tv_siembra);
        tv_siembra_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment fragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 0);
                clicks[0]=true;
                clicks[1]=false;
                clicks[2]=false;
                clicks[3]=false;

                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(),"doit");
            }
        });
        tv_siembra_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment fragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 1);
                clicks[0]=false;
                clicks[1]=true;
                clicks[2]=false;
                clicks[3]=false;

                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(),"doit");
            }
        });


        tv_cosecha_date1 = anual1.findViewById(R.id.tv_cosecha);
        tv_cosecha_date2 = anual2.findViewById(R.id.tv_cosecha);
        tv_cosecha_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 2);
                clicks[0]=false;
                clicks[1]=false;
                clicks[2]=true;
                clicks[3]=false;
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(),"doit");

            }
        });
        tv_cosecha_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 3);
                clicks[0]=false;
                clicks[1]=false;
                clicks[2]=false;
                clicks[3]=true;
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(),"doit");
            }
        });
        final ArrayList<Integer>idSize = new ArrayList<>();
        final ArrayList<String>arrayListSize = new ArrayList<>();


        final MedidaChacraAdapter adapterMedida = new MedidaChacraAdapter(context,
                R.layout.owner_spinner_item,
                MedidaChacra.listAll(MedidaChacra.class));

        s_size_i.setAdapter(adapterMedida);

        s_size_i.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MedidaChacra m = adapterMedida.getItem(i);

                if(id==1)size1=Integer.parseInt(m.getId_main());
                else size2=Integer.parseInt(m.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final SistemaRiegoAdapter sistemaRiegoAdapter = new SistemaRiegoAdapter(context,
                R.layout.owner_spinner_item,
                SistemaRiego.listAll(SistemaRiego.class));

        s_riego_usado_i.setAdapter(sistemaRiegoAdapter);

        s_riego_usado_i.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SistemaRiego sistemaRiego = sistemaRiegoAdapter.getItem(i);
                if(id==1)riego_usado1=Integer.parseInt(sistemaRiego.getId_main());
                else riego_usado2=Integer.parseInt(sistemaRiego.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final MedidaProduccionAdapter medidaProduccionAdapter = new MedidaProduccionAdapter(context,
                R.layout.owner_spinner_item,
                MedidaProduccion.listAll(MedidaProduccion.class));

        s_medida_produccion.setAdapter(medidaProduccionAdapter);

        s_medida_produccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MedidaProduccion medidaProduccion = medidaProduccionAdapter.getItem(i);
                if(id==1)tipo_medida1=Integer.parseInt(medidaProduccion.getId_main());
                else tipo_medida2=Integer.parseInt(medidaProduccion.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    public void setDataPermanent(final int id){
        View view;
        view =(id==1)?permanente1:permanente2;
        final Spinner s_riego_usado_i= view.findViewById(R.id.s_riego);

        final SistemaRiegoAdapter sistemaRiegoAdapter = new SistemaRiegoAdapter(context,
                R.layout.owner_spinner_item,
                SistemaRiego.listAll(SistemaRiego.class));

        s_riego_usado_i.setAdapter(sistemaRiegoAdapter);

        s_riego_usado_i.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SistemaRiego sistemaRiego = sistemaRiegoAdapter.getItem(i);
                if(id==1)riego_usado1P=Integer.parseInt(sistemaRiego.getId_main());
                else riego_usado2P=Integer.parseInt(sistemaRiego.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final Spinner s_medida_produccion= view.findViewById(R.id.s_medida_produccion);

        final MedidaProduccionAdapter medidaProduccionAdapter = new MedidaProduccionAdapter(context,
                R.layout.owner_spinner_item,
                MedidaProduccion.listAll(MedidaProduccion.class));

        s_medida_produccion.setAdapter(medidaProduccionAdapter);

        s_medida_produccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MedidaProduccion medidaProduccion = medidaProduccionAdapter.getItem(i);
                if(id==1)tipo_medida_permanente1=Integer.parseInt(medidaProduccion.getId_main());
                else tipo_medida_permanente2=Integer.parseInt(medidaProduccion.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> lista = new ArrayList<>();
        lista.add("Elija");
        for (int j=0;j<101;j++){
            lista.add(j+"");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,lista);

        final Spinner s_propio_p = view.findViewById(R.id.s_propio_p);
        s_propio_p.setAdapter(adapter);

        final Spinner s_semilla_p= view.findViewById(R.id.s_semilla_p);
        s_semilla_p.setAdapter(adapter);

        final Spinner s_venta_p= view.findViewById(R.id.s_venta_p);
        s_venta_p.setAdapter(adapter);

        s_propio_p.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!s_propio_p.getSelectedItem().toString().equals("Elija")){

                    if(id==1) {
                        total_percentage1_p = 100 - Integer.parseInt(s_propio_p.getSelectedItem().toString());
                        s_propio1_p = Integer.parseInt(s_propio_p.getSelectedItem().toString());
                        s_venta1_p =0;



                        ArrayList<String> listaPercentage = new ArrayList<>();
                        listaPercentage.add("Elija");
                        for (int j=0;j<=total_percentage1_p;j++){
                            listaPercentage.add(j+"");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercentage);
                        s_semilla_p.setAdapter(adapter);
                        s_semilla_p.setSelection(total_percentage1_p+1);



                    }else {
                        total_percentage2_p-=Integer.parseInt(s_propio_p.getSelectedItem().toString());
                        s_propio2_p = Integer.parseInt(s_propio_p.getSelectedItem().toString());
                        s_venta2_p =0;



                        ArrayList<String> listaPercentage = new ArrayList<>();
                        listaPercentage.add("Elija");
                        for (int j=0;j<=total_percentage2_p;j++){
                            listaPercentage.add(j+"");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercentage);
                        s_semilla_p.setAdapter(adapter);
                        s_semilla_p.setSelection(total_percentage2_p+1);


                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_semilla_p.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(!s_semilla_p.getSelectedItem().toString().equals("Elija")){
                    if(id==1){
                        s_semilla1_p = Integer.parseInt(s_semilla_p.getSelectedItem().toString());

                        ArrayList<String> listaPercent = new ArrayList<>();
                        s_venta1_p = 100 - s_semilla1_p - s_propio1_p;
                        listaPercent.add(""+s_venta1_p);
                        ArrayAdapter<String> adapt = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercent);
                        s_venta_p.setAdapter(adapt);
                    }else{
                        s_semilla2_p = Integer.parseInt(s_semilla_p.getSelectedItem().toString());

                        ArrayList<String> listaPercent = new ArrayList<>();
                        s_venta2_p = 100 - s_semilla2_p - s_propio2_p;
                        listaPercent.add(""+s_venta2_p);
                        ArrayAdapter<String> adapt = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercent);
                        s_venta_p.setAdapter(adapt);
                    }


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_venta_p.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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

        final TipoPropiedadAdapter adapter = new TipoPropiedadAdapter(context,
                R.layout.owner_spinner_item,
                TipoPropiedad.listAll(TipoPropiedad.class));

        s_type.setAdapter(adapter);


        s_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TipoPropiedad tipoPropiedad = adapter.getItem(i);
                globalType=Integer.parseInt(tipoPropiedad.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public  void fillSize(){
        final MedidaChacraAdapter adapter = new MedidaChacraAdapter(context,
                R.layout.owner_spinner_item,
                MedidaChacra.listAll(MedidaChacra.class));

        s_size.setAdapter(adapter);

        s_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MedidaChacra medidaChacra = adapter.getItem(i);
                globalSize = Integer.parseInt(medidaChacra.getId_main());
                if(medidaChacra.getName().length()==0 && !medidaChacra.getName().equals("Elije")){
                    Toast.makeText(context, "Debe ingresar la medida de la chacra", Toast.LENGTH_SHORT).show();
                }else{
                    if(medidaChacra.getName().equals("Metros cuadrados")){
                        double val = Double.parseDouble(et_medida.getText().toString());
                        DecimalFormat formatter = new DecimalFormat("#0.00");
                    }
                    if(medidaChacra.getName().equals("Yugadas")){
                        double val = Double.parseDouble(et_medida.getText().toString());
                        DecimalFormat formatter = new DecimalFormat("#0.00");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void fillSpinnersGroup(final Spinner spinner,final ArrayList<Integer>gId,final ArrayList<String>nameList,final int order){

        final GrupoAgricolaAdapter adapter = new GrupoAgricolaAdapter(context,
                R.layout.owner_spinner_item,
                GrupoAgricola.listAll(GrupoAgricola.class));

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GrupoAgricola grupoAgricola = adapter.getItem(i);


                if(order==1) {
                    if(grupoAgricola.getName().equals("Elija")){
                        anual_option1.setVisibility(View.GONE);
                        permanent_option1.setVisibility(View.GONE);
                    }
                    idTipoProducto1=Integer.parseInt(grupoAgricola.getId_main());
                    fillProduct1(Integer.parseInt(grupoAgricola.getId_main()));
                }else{
                    if(grupoAgricola.getName().equals("Elija")){
                        anual_option2.setVisibility(View.GONE);
                        permanent_option2.setVisibility(View.GONE);
                    }
                    idTipoProducto2=Integer.parseInt(grupoAgricola.getId_main());
                    fillProduct2(Integer.parseInt(grupoAgricola.getId_main()));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void fillProduct1(int index){
        Iterator<?> keys = ProductoAgricola.findAll(ProductoAgricola.class);
        List<ProductoAgricola> currentList= new ArrayList<>();
        ProductoAgricola pa = new ProductoAgricola("0","0","Elije","Vacio");
        currentList.add(pa);
        while(keys.hasNext()){
            ProductoAgricola p = (ProductoAgricola)keys.next();
            if (p.getId_parent().equals(index+""))currentList.add(p);
        }

        final ProductoAgricolaAdapter adapter = new ProductoAgricolaAdapter(context,R.layout.owner_spinner_item,
                currentList);

        spinnerProduct1.setAdapter(adapter);

        spinnerProduct1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductoAgricola p = adapter.getItem(i);
                idProd1=Integer.parseInt(p.getId_main());

                if(!p.getName().equals("Elije")){

                    if(p.getTipo_cultivo().equals("permanente")){
                        permanent_option1.setVisibility(View.VISIBLE);
                        anual_option1.setVisibility(View.GONE);
                        setDataPermanent(1);
                    }else{
                        anual_option1.setVisibility(View.VISIBLE);
                        permanent_option1.setVisibility(View.GONE);
                        setDataAnual(1);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void setLinearLayoutDetails(){
        View viewBussiness  = new View(context);

        v1 = (View) findViewById(R.id.ll_detail1);
        v2 = (View) findViewById(R.id.ll_detail2);

        spinnerGroup1=(Spinner) v1.findViewById(R.id.s_group_farming);
        spinnerGroup2=(Spinner) v2.findViewById(R.id.s_group_farming);

        spinnerProduct1=(Spinner) v1.findViewById(R.id.s_production);
        spinnerProduct2=(Spinner) v2.findViewById(R.id.s_production);

        anual_option1 = (LinearLayout) v1.findViewById(R.id.anual_option);
        anual_option2 = (LinearLayout) v2.findViewById(R.id.anual_option);

        permanent_option1 = (LinearLayout) v1.findViewById(R.id.permanente_option);
        permanent_option2 = (LinearLayout) v2.findViewById(R.id.permanente_option);

        anual1 = v1.findViewById(R.id.l_anual);
        permanente1 = v1.findViewById(R.id.l_permanente);

        anual2 = v2.findViewById(R.id.l_anual);
        permanente2 = v2.findViewById(R.id.l_permanente);



        g1=new ArrayList<>();
        nameListG1=new ArrayList<>();
        fillSpinnersGroup(spinnerGroup1,g1,nameListG1,1);

        g2=new ArrayList<>();
        nameListG2=new ArrayList<>();
        fillSpinnersGroup(spinnerGroup2,g2,nameListG2,2);


        setActionsSpinner();

    }
    public void fillProduct2(int index){

        Iterator<?> keys = ProductoAgricola.findAll(ProductoAgricola.class);
        List<ProductoAgricola> currentList= new ArrayList<>();
        ProductoAgricola pa = new ProductoAgricola("0","0","Elije","Vacio");
        currentList.add(pa);
        while(keys.hasNext()){
            ProductoAgricola p = (ProductoAgricola)keys.next();
            if (p.getId_parent().equals(index+""))currentList.add(p);
        }

        final ProductoAgricolaAdapter adapter = new ProductoAgricolaAdapter(context,R.layout.owner_spinner_item,
                currentList);

        spinnerProduct2.setAdapter(adapter);

        spinnerProduct2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductoAgricola p = adapter.getItem(i);
                idProd2=Integer.parseInt(p.getId_main());

                if(!p.getName().equals("Elije")){

                    if(p.getTipo_cultivo().equals("permanente")){
                        permanent_option2.setVisibility(View.VISIBLE);
                        anual_option2.setVisibility(View.GONE);
                        setDataPermanent(2);
                    }else{
                        anual_option2.setVisibility(View.VISIBLE);
                        permanent_option2.setVisibility(View.GONE);
                        setDataAnual(2);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getDataAnual(JsonObject body,int id){

        View view;
        view =(id==1)?anual1:anual2;
        EditText et_area = view.findViewById(R.id.et_area);
        EditText et_estimado = view.findViewById(R.id.et_estimado);


        Spinner s_propio = view.findViewById(R.id.s_propio);
        Spinner s_semilla = view.findViewById(R.id.s_semilla);
        Spinner s_venta = view.findViewById(R.id.s_venta);

        body.addProperty("area_cultivo",et_area.getText().toString());
        if(id==1){
            body.addProperty("id_tipo_medida_area_cultivo",size1);
            body.addProperty("fecha_siembra",listDatesFarmingString[0]);
            body.addProperty("fecha_cosecha",listDatesFarmingString[2]);
            body.addProperty("porcentaje_consumo_propio",s_propio1);
            body.addProperty("porcentaje_para_semilla",s_semilla1);
            body.addProperty("porcentaje_venta",s_venta1);
            body.addProperty("id_sistema_riego_usado",riego_usado1);
            body.addProperty("produccion_estimada",et_estimado.getText().toString());
            body.addProperty("id_tipo_medida_produccion_estimada",tipo_medida1);

        }else{
            body.addProperty("id_tipo_medida_area_cultivo",size2);
            body.addProperty("fecha_siembra",listDatesFarmingString[1]);
            body.addProperty("fecha_cosecha",listDatesFarmingString[3]);
            body.addProperty("porcentaje_consumo_propio",s_propio2);
            body.addProperty("porcentaje_para_semilla",s_semilla2);
            body.addProperty("porcentaje_venta",s_venta2);
            body.addProperty("id_sistema_riego_usado",riego_usado2);
            body.addProperty("produccion_estimada",et_estimado.getText().toString());
            body.addProperty("id_tipo_medida_produccion_estimada",tipo_medida2);
        }


    }
    private void getDataPermanent(JsonObject body,int id){
        View view;
        view =(id==1)?permanente1:permanente2;
        Spinner s_from_1c = view.findViewById(R.id.s_from_1c);
        Spinner s_to_1c = view.findViewById(R.id.s_to_1c);
        Spinner s_from_2c = view.findViewById(R.id.s_from_2c);
        Spinner s_to_2c = view.findViewById(R.id.s_to_2c);
        Spinner s_from_3c = view.findViewById(R.id.s_from_3c);
        Spinner s_to_3c = view.findViewById(R.id.s_to_3c);
        EditText et_estimado = findViewById(R.id.et_estimado);
        body.addProperty("primera_cosecha_mes_desde",s_from_1c.getSelectedItem().toString());
        body.addProperty("primera_cosecha_mes_hasta",s_to_1c.getSelectedItem().toString());
        body.addProperty("segunda_cosecha_mes_desde",s_from_2c.getSelectedItem().toString());
        body.addProperty("segunda_cosecha_mes_hasta",s_to_2c.getSelectedItem().toString());
        body.addProperty("tercera_cosecha_mes_desde",s_from_3c.getSelectedItem().toString());
        body.addProperty("tercera_cosecha_mes_hasta",s_to_3c.getSelectedItem().toString());
        if(id==1){
            body.addProperty("id_sistema_riego_usado",riego_usado1P);
            body.addProperty("produccion_estimada",et_estimado.getText().toString());
            body.addProperty("id_tipo_medida_produccion_estimada",tipo_medida_permanente1);
            body.addProperty("porcentaje_consumo_propio",s_propio1_p);
            body.addProperty("porcentaje_para_semilla",s_semilla1_p);
            body.addProperty("porcentaje_venta",s_venta1_p);
        }else{
            body.addProperty("id_sistema_riego_usado",riego_usado2P);
            body.addProperty("produccion_estimada",et_estimado.getText().toString());
            body.addProperty("id_tipo_medida_produccion_estimada",tipo_medida_permanente2);
            body.addProperty("porcentaje_consumo_propio",s_propio2_p);
            body.addProperty("porcentaje_para_semilla",s_semilla2_p);
            body.addProperty("porcentaje_venta",s_venta2_p);
        }







    }
    public JsonObject validation(){
        if(!recoverData())return null;
        JsonObject productos = new JsonObject();

        productos.addProperty("ubicacion_latitud_gps",et_latitude.getText()+"");
        productos.addProperty("ubicacion_longitud_gps",et_latitude.getText()+"");
        productos.addProperty("nombre_chacra",et_parcela_name.getText()+"");
        productos.addProperty("id_tipo_propiedad",globalType+"");
        productos.addProperty("medida_chacra",et_medida.getText()+"");
        productos.addProperty("id_tipo_medida",globalSize+"");

        JsonObject prod_internos = new JsonObject();
        if(anual_option1.getVisibility()==View.VISIBLE){
            JsonObject prod1 = new JsonObject();
            prod1.addProperty("id_tipo_producto",idTipoProducto1+"");
            prod1.addProperty("id_producto_agroecologico",idProd1+"");
            getDataAnual(prod1,1);
            prod_internos.add("1",prod1);
            System.out.println(prod1);
        }
        if(permanent_option1.getVisibility()==View.VISIBLE){
            JsonObject prod1 = new JsonObject();
            prod1.addProperty("id_tipo_producto",idTipoProducto1+"");
            prod1.addProperty("id_producto_agroecologico",idProd1+"");
            getDataPermanent(prod1,1);
            prod_internos.add("1",prod1);
            System.out.println(prod1);
        }
        if(anual_option2.getVisibility()==View.VISIBLE){
            JsonObject prod2 = new JsonObject();
            prod2.addProperty("id_tipo_producto",idTipoProducto2+"");
            prod2.addProperty("id_producto_agroecologico",idProd2+"");
            getDataAnual(prod2,2);
            prod_internos.add("2",prod2);
            System.out.println(prod2);
        }
        if(permanent_option2.getVisibility()==View.VISIBLE){
            JsonObject prod1 = new JsonObject();
            prod1.addProperty("id_tipo_producto",idTipoProducto1+"");
            prod1.addProperty("id_producto_agroecologico",idProd1+"");
            getDataPermanent(prod1,2);
            prod_internos.add("2",prod1);
            System.out.println(prod1);
        }



        productos.add("productos",prod_internos);


        return productos;
    }

    private boolean recoverData(){

        if(et_parcela_name.getText().length()==0){
            Toast.makeText(context, "Revisar nombre de parcela", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(s_type.getSelectedItem().toString().equals("Elija") || s_type.getSelectedItem().toString().length()==0){
            Toast.makeText(context, "Revisar tipo de propiedad", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(et_medida.getText().toString().length()==0){
            Toast.makeText(context, "Ingresar medida", Toast.LENGTH_SHORT).show();
            return false;
        }
        MedidaChacra medidaChacra = (MedidaChacra)s_size.getSelectedItem();
        if(medidaChacra.getName().equals("")||medidaChacra.getName().toString().length()==0){
            Toast.makeText(context, "Ingresar tipo de medida", Toast.LENGTH_SHORT).show();
            return false;
        }
        // id ll_detail1
        View viewKid1 = findViewById(R.id.ll_detail1);
        Spinner s_group_farming1= viewKid1.findViewById(R.id.s_group_farming);
        GrupoAgricola g1 = (GrupoAgricola)s_group_farming1.getSelectedItem();
        if(g1.getName().equals("Elije")){
            Toast.makeText(context, "Seleccione grupo agrícola", Toast.LENGTH_SHORT).show();
            return false;
        }

        Spinner s_production1 =viewKid1.findViewById(R.id.s_production);
        ProductoAgricola pa = (ProductoAgricola)s_production1.getSelectedItem();
        if(pa.getName().equals("Elija")){
            Toast.makeText(context, "Seleccione producto", Toast.LENGTH_SHORT).show();
            return false;
        }

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
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = new GregorianCalendar(i, i1, i2);
        setDate(cal);
    }
    private void setDate(final Calendar calendar) {
        int in=0;
        while(in<4){
            if(clicks[in]==true){
                listDatesFarmingString[in]=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
                break;
            }
            in++;
        }
        if(clicks[0])tv_siembra_date1.setText(listDatesFarmingString[0]);
        if(clicks[1])tv_siembra_date2.setText(listDatesFarmingString[1]);
        if(clicks[2])tv_cosecha_date1.setText(listDatesFarmingString[2]);
        if(clicks[3])tv_cosecha_date2.setText(listDatesFarmingString[3]);

        listDatesFarming[in] =true;
    }
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int index=0;
            if (savedInstanceState != null) {
                index = savedInstanceState.getInt("id", 0);
            }

            if(!DetailsFarmingActivity.listDatesFarming[index]){
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), year, month, day);
            }else{
                String[] dateOwn=DetailsFarmingActivity.listDatesFarmingString[index].split("-");
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
