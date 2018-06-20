package com.kelly.registerform.view.farming;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kelly.registerform.R;
import com.kelly.registerform.adapters.recyclerViewAdapters.FarmingAdapter;
import com.kelly.registerform.example.MyFragmentPagerAdapter;
import com.kelly.registerform.example.ScreenSlidePageFragment;
import com.kelly.registerform.fragments.SlideProcessFragment;
import com.kelly.registerform.model.Chacra;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.utils.recyclerview.RecyclerTouchListener;

import java.util.ArrayList;

public class ProductionActivity extends AppCompatActivity {
    public static ArrayList<Chacra>chacraArrayList;
    public ArrayList<Boolean>stateRV,addedList;
    private Button b_next,b_back;
    private Context context=this;
    private FarmingAdapter farmingAdapter;
    private JsonObject body = new JsonObject();
    private RecyclerView.LayoutParams layoutParams;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rv_chacra;
    private Spinner s_numberFarm;
    private String listAcvities = null;

    public static ArrayList<JsonObject>jsonObjectArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setElements();
        setActions();

    }
    private  void setElements(){

        chacraArrayList = new ArrayList<>();
        jsonObjectArrayList = new ArrayList<>();
        addedList = new ArrayList<>();
        stateRV =new ArrayList<>();

        stateRV.add(false);
        addedList.add(false);
        Chacra chacra = new Chacra();
        chacra.setNombre("Chacra 1");
        chacraArrayList.add(chacra);
        jsonObjectArrayList.add(new JsonObject());

        b_next =findViewById(R.id.b_next);
        b_back= findViewById(R.id.b_back);
        listAcvities = getIntent().getStringExtra("list");
        s_numberFarm=findViewById(R.id.s_numberFarm);

        rv_chacra = findViewById(R.id.rv_chacra);
        farmingAdapter = new FarmingAdapter(context,chacraArrayList);
        rv_chacra.setHasFixedSize(true);
        linearLayoutManager  = new LinearLayoutManager(context);
        rv_chacra.setLayoutManager(linearLayoutManager);
        rv_chacra.setAdapter(farmingAdapter);
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

                JsonObject chacrasBody = new JsonObject();
                for(int i=0;i<jsonObjectArrayList.size();i++){
                    if(stateRV.get(i)==false){
                        Toast.makeText(context, "No se ha presionado la chacra #"+(i+1), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if(addedList.get(i)==false && stateRV.get(i)==true){
                        addedList.set(i,true);
                        chacrasBody.add(""+(i+1),jsonObjectArrayList.get(i));
                    }
                }
                MainJson.addChild("chacras",chacrasBody);
                MainJson.printBody();
                Intent intent =new Intent(context,SystemProductionActivity.class);
                intent.putExtra("list",listAcvities);
                startActivity(intent);
            }
        });
        final ViewPager pager;
        s_numberFarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!((String)adapterView.getItemAtPosition(i)).equals("Elija")){
                    int val = Integer.parseInt((String)adapterView.getItemAtPosition(i));

                    if(val==1){
                        chacraArrayList.clear();
                        stateRV.clear();
                        addedList.clear();
                        jsonObjectArrayList.clear();

                        Chacra chacra = new Chacra();
                        chacra.setNombre("Chacra 1");
                        chacraArrayList.add(chacra);

                        stateRV.add(false);
                        addedList.add(false);
                        jsonObjectArrayList.add(new JsonObject());

                        farmingAdapter.notifyDataSetChanged();
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (90 * scale + 0.5f);
                        rv_chacra.getLayoutParams().height = pixels;
                    }
                    else {
                        chacraArrayList.clear();
                        stateRV.clear();
                        addedList.clear();
                        jsonObjectArrayList.clear();

                        int size=0;
                        for(int index=0;index<val;index++){
                            Chacra chacra = new Chacra();
                            chacra.setNombre("Chacra "+(index+1));
                            chacraArrayList.add(chacra);
                            stateRV.add(false);
                            addedList.add(false);
                            jsonObjectArrayList.add(new JsonObject());
                            size+=90;
                        }

                        farmingAdapter.notifyDataSetChanged();
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (size * scale + 0.5f);
                        rv_chacra.getLayoutParams().height = pixels;
                    }
                }else{
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rv_chacra.addOnItemTouchListener(new RecyclerTouchListener(context, rv_chacra, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                stateRV.set(position,true);
                Intent intent = new Intent(context,DetailsFarmingActivity.class);
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
            String result=data.getStringExtra("result");
            String[]arr = result.split(",");
            chacraArrayList.get(Integer.parseInt(arr[1])).setNombre(arr[0]);
            farmingAdapter.notifyDataSetChanged();

            if(result!=null){

            }else{

            }

        }
    }
    private boolean validation(){
        /*body = new JsonObject();
        for(int i=1;i<adapter.getCount();i++){
            ScreenSlidePageFragment screenSlidePageFragment = (ScreenSlidePageFragment) adapter.getItem(i);

            if(!screenSlidePageFragment.validation()){

                return false;
            }
            body.add(""+i,screenSlidePageFragment.outputJson);
        }*/
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    public void updateViews(int val){
        /*for (int i=0;i<val-1;i++){
            ScreenSlidePageFragment screenSlidePageFragment=ScreenSlidePageFragment.newInstance(getResources()
                    .getColor(R.color.colorWhite));
            screenSlidePageFragment.indexPage=i+1;
            adapter.addFragment(screenSlidePageFragment);
        }
        this.pager.setAdapter(adapter);*/
    }
    public void beforeOne(){
        /*adapter.beforeOne();
        adapter.addFragment(ScreenSlidePageFragment.newInstance(getResources()
                .getColor(R.color.colorWhite)));
        this.pager.setAdapter(adapter);*/
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
