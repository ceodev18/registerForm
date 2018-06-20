package com.kelly.registerform.view.commerce;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kelly.registerform.R;
import com.kelly.registerform.adapters.recyclerViewAdapters.ComercioAdapter;
import com.kelly.registerform.adapters.recyclerViewAdapters.FarmingAdapter;
import com.kelly.registerform.model.AnimalCrianza;
import com.kelly.registerform.model.Chacra;
import com.kelly.registerform.model.Comercio;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.utils.recyclerview.RecyclerTouchListener;
import com.kelly.registerform.view.livestock.DetailsLiveStockActivity;
import com.kelly.registerform.view.livestock.LiveStockEcoActivity;
import com.kelly.registerform.view.partner.ValidationActivity;

import java.util.ArrayList;

public class ComercializacionActivity extends AppCompatActivity {
    public static ArrayList<Comercio> comercioArrayList;
    public ArrayList<Boolean>stateRV,addedList;
    private Button b_next,b_back;
    private Context context=this;
    private ComercioAdapter comercioAdapter;
    private JsonObject body = new JsonObject();
    private RecyclerView.LayoutParams layoutParams;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rv_comercio;
    private Spinner s_numberComercio;
    private String listAcvities = null;

    public static ArrayList<JsonObject>jsonObjectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comercializacion);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setElements();
        setActions();
    }
    private  void setElements(){

        comercioArrayList = new ArrayList<>();
        jsonObjectArrayList = new ArrayList<>();
        addedList = new ArrayList<>();
        stateRV =new ArrayList<>();

        stateRV.add(false);
        addedList.add(false);
        Comercio comercio = new Comercio();
        comercio.setName("Producto 1");
        comercioArrayList.add(comercio);
        jsonObjectArrayList.add(new JsonObject());

        b_next =findViewById(R.id.b_next);
        b_back= findViewById(R.id.b_back);
        listAcvities = getIntent().getStringExtra("list");
        s_numberComercio=findViewById(R.id.s_numberComercio);

        rv_comercio = findViewById(R.id.rv_comercio);
        comercioAdapter = new ComercioAdapter(context,comercioArrayList);
        rv_comercio.setHasFixedSize(true);
        linearLayoutManager  = new LinearLayoutManager(context);
        rv_comercio.setLayoutManager(linearLayoutManager);
        rv_comercio.setAdapter(comercioAdapter);
    }
    private void setActions(){
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonObject productBody = new JsonObject();
                for(int i=0;i<jsonObjectArrayList.size();i++){
                    if(stateRV.get(i)==false){
                        Toast.makeText(context, "No se ha presionado el producto #"+(i+1), Toast.LENGTH_SHORT).show();
                        return;
                    }


                    else if(addedList.get(i)==false && stateRV.get(i)==true){
                        addedList.set(i,true);
                        productBody.add(""+(i+1),jsonObjectArrayList.get(i));
                    }
                }

                MainJson.addChild("productos_comercializados",productBody);
                MainJson.printBody();
                Intent intent = new Intent(context, ValidationActivity.class);
                startActivity(intent);
            }
        });
        final ViewPager pager;
        s_numberComercio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!((String)adapterView.getItemAtPosition(i)).equals("Elija")){
                    int val = Integer.parseInt((String)adapterView.getItemAtPosition(i));

                    if(val==1){
                        comercioArrayList.clear();
                        stateRV.clear();
                        addedList.clear();
                        jsonObjectArrayList.clear();

                        Comercio comercio = new Comercio();
                        comercioArrayList.add(comercio);

                        stateRV.add(false);
                        addedList.add(false);
                        jsonObjectArrayList.add(new JsonObject());

                        comercioAdapter.notifyDataSetChanged();
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (90 * scale + 0.5f);
                        rv_comercio.getLayoutParams().height = pixels;
                    }
                    else {
                        comercioArrayList.clear();
                        stateRV.clear();
                        addedList.clear();
                        jsonObjectArrayList.clear();

                        int size=0;
                        for(int index=0;index<val;index++){
                            Comercio comercio = new Comercio();
                            comercioArrayList.add(comercio);
                            stateRV.add(false);
                            addedList.add(false);
                            jsonObjectArrayList.add(new JsonObject());
                            size+=90;
                        }

                        comercioAdapter.notifyDataSetChanged();
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (size * scale + 0.5f);
                        rv_comercio.getLayoutParams().height = pixels;
                    }
                }else{
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rv_comercio.addOnItemTouchListener(new RecyclerTouchListener(context, rv_comercio, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                stateRV.set(position,true);
                Intent intent = new Intent(context,InformationActivity.class);
                intent.putExtra("index",position+"");
                startActivityForResult(intent, 1);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
        }
        if ((resultCode == RESULT_OK)) {
            //Procesar el resultado
            //String result=data.getStringExtra("result");
            //String[]arr = result.split(",");
            // notify changes to rv afeter comeback from form
            //arrayList.get(Integer.parseInt(arr[1])).setNombre(arr[0]);
            //liveStockAdapter.notifyDataSetChanged();

            /*if(result!=null){

            }else{

            }*/

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
