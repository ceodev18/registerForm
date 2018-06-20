package com.kelly.registerform.view.livestock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kelly.registerform.BuildConfig;
import com.kelly.registerform.R;
import com.kelly.registerform.adapters.modelsAdapter.EspecieCrianzaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ManejoCrianzaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProductoAgricolaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.ProductoCrianzaAdapter;
import com.kelly.registerform.adapters.modelsAdapter.TipoAlimentoAdapter;
import com.kelly.registerform.adapters.modelsAdapter.TipoCertificadoraAdapter;
import com.kelly.registerform.dataAccess.ProductoDerivadoDA;
import com.kelly.registerform.dataAccess.practicas.AgricolaDA;
import com.kelly.registerform.dataAccess.practicas.BiodiversidadDA;
import com.kelly.registerform.model.AnimalCrianza;
import com.kelly.registerform.model.EspecieCrianza;
import com.kelly.registerform.model.ManejoCrianza;
import com.kelly.registerform.model.ProductoAgricola;
import com.kelly.registerform.model.ProductoCrianza;
import com.kelly.registerform.model.ProductoDerivado;
import com.kelly.registerform.model.TipoAlimento;
import com.kelly.registerform.model.TipoPropiedad;
import com.kelly.registerform.model.certifications.TipoCertificadora;
import com.kelly.registerform.view.MapsActivity;
import com.kelly.registerform.view.farming.ProductionActivity;
import com.thomashaertel.widget.MultiSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetailsLiveStockActivity extends AppCompatActivity {
    private Context context=this;
    private int VALOR_RETORNO = 1,id_animal_group,id_animal;
    private int total =100;
    private int val_venta,val_propio=0,val_reproductor=0;
    private String position;
    private static final String BACKGROUND_COLOR = "color";
    private ViewGroup rootView;
    private Spinner s_tipo,s_manejo,s_propio,s_venta,spinner_especie_animal,s_animal,s_total_animales,s_reproductores;
    private ArrayList<Integer> idTipo,idManejo,idDerivado;
    private ArrayList<String>arrayListTipo,arrayListManejo,arrayListDerivado;
    private Button b_map,b_save;
    private EditText et_longitude,et_latitude;
    private MultiSpinner spinner;

    private ArrayList<String>animalGroup,nameAnimal;
    private List<String>s1;
    private ArrayList<Integer> i1,selected1,idAnimalGroup,idAnimal;
    private ArrayAdapter<String> adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stock_farming);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        position=getIntent().getStringExtra("index");
        int aux = Integer.parseInt(position);
        aux++;
        ab.setTitle("Especie Animal "+aux);
        setElements();
        setActions();
        refillData();
    }

    private void  refillData(){
        JsonObject main =LivestockProductionActivity.jsonObjectArrayList.get(Integer.parseInt(position));
        if(main.size()==0)return;

        et_latitude.setText(main.get("ubicacion_latitud_gps").getAsString());
        et_longitude.setText(main.get("ubicacion_longitud_gps").getAsString());

        spinner_especie_animal.setSelection(getIndexEspecie(spinner_especie_animal, main.get("id_tipo_animal").getAsString()));
        s_animal.setSelection(getIndexAnimal(s_animal, main.get("id_animal").getAsString()));

        s_total_animales.setSelection(getIndex(s_total_animales, main.get("total_animales").getAsString()));
        s_tipo.setSelection(getIndexAlimento(s_tipo, main.get("id_tipo_alimento").getAsString()));
        s_propio.setSelection(getIndex(s_propio, main.get("total_animales").getAsString()));
        s_propio.setSelection(getIndex(s_propio, main.get("porcentaje_consumo_propio").getAsString()));
        s_reproductores.setSelection(getIndex(s_reproductores, main.get("porcentaje_para_reproductores").getAsString()));
        s_venta.setSelection(getIndex(s_venta, main.get("porcentaje_venta").getAsString()));
        s_manejo.setSelection(getIndexManejo(s_manejo, main.get("id_manejo_crianza").getAsString()));

        JsonObject jsonObject =  main.get("producto_derivado").getAsJsonObject();
        ArrayList<String>aux =new ArrayList<>();
        for(int i=0;i<jsonObject.size();i++){
            aux.add(jsonObject.get(i+"").getAsString());
        }

        List<String>listIds =ProductoDerivadoDA.getListIds();


        boolean[]listStates = new  boolean[listIds.size()];
        for (int i=0;i<listStates.length;i++)listStates[i]=false;

        for(int i=0;i<listIds.size();i++){
            for (int j=0;j<aux.size();j++){
                if(aux.get(j).equals(listIds.get(i)))listStates[i]=true;
            }
        }

        spinner.setSelected(listStates);

        //TODO work

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

    //private method of your class
    private int getIndexManejo(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            ManejoCrianza manejoCrianza = (ManejoCrianza)spinner.getAdapter().getItem(i);
            if (manejoCrianza.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    //private method of your class
    private int getIndexAlimento(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            TipoAlimento tipoAlimento = (TipoAlimento)spinner.getAdapter().getItem(i);
            if (tipoAlimento.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    //private method of your class
    private int getIndexEspecie(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            EspecieCrianza especieCrianza = (EspecieCrianza)spinner.getAdapter().getItem(i);
            if (especieCrianza.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    //private method of your class
    private int getIndexAnimal(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            ProductoCrianza productoCrianza = (ProductoCrianza)spinner.getAdapter().getItem(i);
            if (productoCrianza.getId_main().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


    private void setElements(){
        s_total_animales=findViewById(R.id.s_total_animales);
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Elija");
        for (int j=1;j<101;j++){
            lista.add(j+"");
        }
        for (int j=150;j<501;j+=50){
            lista.add(j+"");
        }
        ArrayAdapter<String> adapterSize = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,lista);

        s_total_animales.setAdapter(adapterSize);

        spinner = findViewById(R.id.spinnerMultiDetail);
        spinner_especie_animal =findViewById(R.id.spinner_especie_animal);
        s_animal =findViewById(R.id.s_animal);

        animalGroup = new ArrayList<>();
        nameAnimal = new ArrayList<>();
        idAnimalGroup = new ArrayList<>();
        idAnimal = new ArrayList<>();

        fillAnimalGroups();
        ArrayList<String> listaPercentage = new ArrayList<>();
        listaPercentage.add("Elija");
        for (int j=0;j<101;j++){
            listaPercentage.add(j+"");
        }
        ArrayAdapter<String> adapterPercentage = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercentage);

        s_propio=findViewById(R.id.s_propio);
        s_propio.setAdapter(adapterPercentage);
        s_reproductores=findViewById(R.id.s_reproductores);
        s_reproductores.setAdapter(adapterPercentage);
        s_venta=findViewById(R.id.s_venta);
        s_venta.setAdapter(adapterPercentage);

        et_longitude = findViewById(R.id.et_longitud);
        et_latitude = findViewById(R.id.et_latitud);
        b_map=findViewById(R.id.b_map);
        b_save=findViewById(R.id.b_save);
        b_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        s_tipo=findViewById(R.id.s_tipo);
        s_manejo=findViewById(R.id.s_manejo);
        fillTipo();
        fillManejo();

        i1=new ArrayList<>();
        s1=new ArrayList<>();
        selected1=new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        s1= ProductoDerivadoDA.getListName();
        adapter1.addAll(s1);
        spinner.setAdapter(adapter1, false, onSelectedListener);

    }
    private MultiSpinner.MultiSpinnerListener onSelectedListener1 = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected1.add(Integer.parseInt(ProductoDerivadoDA.getIdByName(s1.get(i))));
                }
            }
        }
    };
    private void fillTipo(){

        final TipoAlimentoAdapter adapter = new TipoAlimentoAdapter(context,
                R.layout.owner_spinner_item,
                TipoAlimento.listAll(TipoAlimento.class));

        s_tipo.setAdapter(adapter);

    }

    private void fillManejo(){
        final ManejoCrianzaAdapter adapter = new ManejoCrianzaAdapter(context,
                R.layout.owner_spinner_item,
                ManejoCrianza.listAll(ManejoCrianza.class));

        s_manejo.setAdapter(adapter);

    }

    private void setActions(){
        s_venta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_propio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!s_propio.getSelectedItem().toString().equals("Elija")){
                    val_propio = Integer.parseInt(s_propio.getSelectedItem().toString());


                    //si valor propio es tocado empieza el flujo
                    total=100 - Integer.parseInt(s_propio.getSelectedItem().toString());

                    val_reproductor = 0;

                    ArrayList<String> listaPercentage = new ArrayList<>();
                    listaPercentage.add("Elija");
                    for (int j=0;j<=total;j++){
                        listaPercentage.add(j+"");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercentage);
                    s_reproductores.setAdapter(adapter);
                    s_reproductores.setSelection(total+1);



                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_reproductores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!s_reproductores.getSelectedItem().toString().equals("Elija")){
                    val_reproductor = Integer.parseInt(s_reproductores.getSelectedItem().toString());

                    ArrayList<String> listaPercent = new ArrayList<>();
                    val_venta = 100 - val_reproductor - val_propio;
                    listaPercent.add(""+val_venta);
                    ArrayAdapter<String> adapt = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,listaPercent);
                    s_venta.setAdapter(adapt);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validation())return;
                JsonObject chacraData = recoverData();
                LivestockProductionActivity.jsonObjectArrayList.set(Integer.parseInt(position),chacraData);
                Intent returnIntent = new Intent();
                ProductoCrianza animalCrianza =(ProductoCrianza)s_animal.getSelectedItem();
                returnIntent.putExtra("result",animalCrianza.getName()+","+position);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


    }
    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    selected1.add(Integer.parseInt(ProductoDerivadoDA.getIdByName(s1.get(i))));
                }
            }
        }
    };

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

    public JsonObject recoverData(){
        JsonObject granja = new JsonObject();
        //if(!recoverData()) return null;

        granja.addProperty("id_tipo_animal",id_animal_group);
        granja.addProperty("id_animal",id_animal);
        granja.addProperty("ubicacion_latitud_gps",et_latitude.getText().toString());
        granja.addProperty("ubicacion_longitud_gps",et_longitude.getText().toString());
        granja.addProperty("total_animales",s_total_animales.getSelectedItem().toString());
        TipoAlimento t = (TipoAlimento)s_tipo.getSelectedItem();
        granja.addProperty("id_tipo_alimento",t.getId_main());
        granja.addProperty("porcentaje_consumo_propio",val_propio);
        granja.addProperty("porcentaje_para_reproductores",val_reproductor);
        granja.addProperty("porcentaje_venta",val_venta);
        ManejoCrianza m = (ManejoCrianza) s_manejo.getSelectedItem();
        granja.addProperty("id_manejo_crianza",m.getId_main());

        JsonObject derivados = new JsonObject();
        for (int i=0;i<selected1.size();i++){
            derivados.addProperty(""+i,selected1.get(i));
        }
        granja.add("producto_derivado",derivados);
        System.out.println(granja);
        return granja;
    }
    private boolean validation(){
        EspecieCrianza e = (EspecieCrianza)spinner_especie_animal.getSelectedItem();
        if(e.getName().toString().equals("Elija") ||
                e.getName().length()==0) {
            Toast.makeText(context, "Escoja un especie animal.", Toast.LENGTH_SHORT).show();
            return false;
        }

        ProductoCrianza p = (ProductoCrianza)s_animal.getSelectedItem();
        if(p.getName().equals("Elija") ||
                p.getName().length()==0) {
            Toast.makeText(context, "Escoja un animal", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_longitude.getText().toString().equals("") ||
                et_latitude.getText().toString().equals("")){
            Toast.makeText(context, "Presione el botón de ubicación", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_total_animales.getSelectedItem().toString().equals("Elija") ||
                s_total_animales.getSelectedItem().toString().length()==0) {
            Toast.makeText(context, "Escoja el total de animales", Toast.LENGTH_SHORT).show();
            return false;
        }
        TipoAlimento t = (TipoAlimento)s_tipo.getSelectedItem();
        if(t.getName().equals("Elija") ||
                t.getName().length()==0) {
            Toast.makeText(context, "Escoja el tipo de alimentos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(s_propio.getSelectedItem().toString().equals("Elija") ||
                s_propio.getSelectedItem().toString().length()==0) {
            Toast.makeText(context, "Escoja el % de consumo propio.", Toast.LENGTH_SHORT).show();
            return false;
        }
        ManejoCrianza m =(ManejoCrianza)s_manejo.getSelectedItem();
        if(m.getName().equals("Elija") ||
                m.getName().length()==0) {
            Toast.makeText(context, "Escoja el manejo de crianza", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private void fillAnimalGroups(){
        final EspecieCrianzaAdapter adapter = new EspecieCrianzaAdapter(context,
                R.layout.owner_spinner_item,
                EspecieCrianza.listAll(EspecieCrianza.class));

        spinner_especie_animal.setAdapter(adapter);

        spinner_especie_animal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                EspecieCrianza especieCrianza = adapter.getItem(i);
                id_animal_group=Integer.parseInt(especieCrianza.getId_main());
                fillAnimal(Integer.parseInt(especieCrianza.getId_main()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void fillAnimal(int id){
        Iterator<?> keys = ProductoCrianza.findAll(ProductoCrianza.class);
        List<ProductoCrianza> currentList= new ArrayList<>();
        ProductoCrianza pa = new ProductoCrianza("0","0","Elije");
        currentList.add(pa);
        while(keys.hasNext()){
            ProductoCrianza p = (ProductoCrianza)keys.next();
            if (p.getId_parent().equals(id+""))currentList.add(p);
        }

        final ProductoCrianzaAdapter adapter = new ProductoCrianzaAdapter(context,R.layout.owner_spinner_item,
                currentList);

        s_animal.setAdapter(adapter);
        s_animal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductoCrianza productoCrianza = adapter.getItem(i);
                id_animal=Integer.parseInt(productoCrianza.getId_main());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
