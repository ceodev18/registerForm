package com.kelly.registerform.view.livestock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kelly.registerform.R;
import com.kelly.registerform.adapters.FragmentLiveStockPageAdapter;
import com.kelly.registerform.adapters.recyclerViewAdapters.FarmingAdapter;
import com.kelly.registerform.adapters.recyclerViewAdapters.LiveStockAdapter;
import com.kelly.registerform.example.MyFragmentPagerAdapter;
import com.kelly.registerform.example.ScreenSlidePageFragment;
import com.kelly.registerform.fragments.SlideLiveStockFragment;
import com.kelly.registerform.model.AnimalCrianza;
import com.kelly.registerform.model.Chacra;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.utils.recyclerview.RecyclerTouchListener;
import com.kelly.registerform.view.farming.DetailsFarmingActivity;
import com.kelly.registerform.view.farming.SystemProductionActivity;

import java.util.ArrayList;

public class LivestockProductionActivity extends AppCompatActivity {
    private static ArrayList<AnimalCrianza>arrayList;
    public ArrayList<Boolean>stateRV,addedList;
    private Button b_next,b_back;
    private String listAcvities = null;
    private ViewPager pager=null;
    private Context context=this;
    private Spinner s_numberFarm;
    private RecyclerView rv_especie;
    private RecyclerView.LayoutParams layoutParams;
    private LinearLayoutManager linearLayoutManager;
    private LiveStockAdapter liveStockAdapter;

    public static ArrayList<JsonObject>jsonObjectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livestock_production);
        setElements();
        setActions();
    }
    private  void setElements(){
        b_next =findViewById(R.id.b_next);
        b_back =findViewById(R.id.b_back);
        listAcvities = getIntent().getStringExtra("list");
        s_numberFarm=findViewById(R.id.s_numberFarm);

        arrayList = new ArrayList<>();
        jsonObjectArrayList = new ArrayList<>();
        jsonObjectArrayList.add(new JsonObject());
        addedList = new ArrayList<>();
        addedList.add(false);
        stateRV =new ArrayList<>();
        stateRV.add(false);
        AnimalCrianza animalCrianza = new AnimalCrianza();
        arrayList.add(animalCrianza);

        rv_especie = findViewById(R.id.rv_especie);
        liveStockAdapter = new LiveStockAdapter(context,arrayList);
        rv_especie.setHasFixedSize(true);
        linearLayoutManager  = new LinearLayoutManager(context);
        rv_especie.setLayoutManager(linearLayoutManager);
        rv_especie.setAdapter(liveStockAdapter);
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

                JsonObject granjasBody = new JsonObject();
                for(int i=0;i<jsonObjectArrayList.size();i++){
                    if(stateRV.get(i)==false){
                        Toast.makeText(context, "No se ha presionado la chacra #"+(i+1), Toast.LENGTH_SHORT).show();
                        return;
                    }


                    else if(addedList.get(i)==false && stateRV.get(i)==true){
                        addedList.set(i,true);
                        granjasBody.add(""+(i+1),jsonObjectArrayList.get(i));
                    }
                }
                MainJson.addChild("granjas",granjasBody);
                MainJson.printBody();
                Intent intent =new Intent(context,LiveStockEcoActivity.class);
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
                        arrayList.clear();
                        stateRV.clear();
                        addedList.clear();
                        jsonObjectArrayList.clear();

                        AnimalCrianza animalCrianza = new AnimalCrianza();
                        arrayList.add(animalCrianza);

                        stateRV.add(false);
                        addedList.add(false);
                        jsonObjectArrayList.add(new JsonObject());

                        liveStockAdapter.notifyDataSetChanged();
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (90 * scale + 0.5f);
                        rv_especie.getLayoutParams().height = pixels;
                    }
                    else {
                        arrayList.clear();
                        stateRV.clear();
                        addedList.clear();
                        jsonObjectArrayList.clear();

                        int size=0;
                        for(int index=0;index<val;index++){
                            AnimalCrianza animalCrianza = new AnimalCrianza();
                            arrayList.add(animalCrianza);
                            stateRV.add(false);
                            addedList.add(false);
                            jsonObjectArrayList.add(new JsonObject());
                            size+=90;
                        }

                        liveStockAdapter.notifyDataSetChanged();
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (size * scale + 0.5f);
                        rv_especie.getLayoutParams().height = pixels;
                    }
                }else{
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rv_especie.addOnItemTouchListener(new RecyclerTouchListener(context, rv_especie, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                stateRV.set(position,true);
                Intent intent = new Intent(context,DetailsLiveStockActivity.class);
                intent.putExtra("index",position+"");
                startActivityForResult(intent, 1);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onBackPressed() {

        /*// Return to previous page when we press back button
        if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);*/

    }
    public void updateViews(int val){
        /*System.out.println(adapter.getCount()+"");
        for (int i=0;i<val-1;i++){
            adapter.addFragment(SlideLiveStockFragment.newInstance(getResources()
                    .getColor(R.color.colorWhite)));
            System.out.println("Agregado");
        }
        System.out.println(adapter.getCount()+"");
        this.pager.setAdapter(adapter);*/
    }
    public void beforeOne(){
        /*adapter.beforeOne();
        adapter.addFragment(SlideLiveStockFragment.newInstance(getResources()
                .getColor(R.color.colorWhite)));
        this.pager.setAdapter(adapter);*/
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
}
