package com.kelly.registerform.view.transformation;

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
import com.kelly.registerform.adapters.FragmentProcessPageAdapter;
import com.kelly.registerform.adapters.recyclerViewAdapters.FarmingAdapter;
import com.kelly.registerform.adapters.recyclerViewAdapters.TransformationAdapter;
import com.kelly.registerform.example.MyFragmentPagerAdapter;
import com.kelly.registerform.example.ScreenSlidePageFragment;
import com.kelly.registerform.fragments.SlideProcessFragment;
import com.kelly.registerform.model.Chacra;
import com.kelly.registerform.model.Comercializacion;
import com.kelly.registerform.model.TransformationOb;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.utils.recyclerview.RecyclerTouchListener;
import com.kelly.registerform.view.commerce.InformationActivity;
import com.kelly.registerform.view.farming.DetailsFarmingActivity;
import com.kelly.registerform.view.farming.SystemProductionActivity;
import com.kelly.registerform.view.livestock.LiveStockEcoActivity;
import com.kelly.registerform.view.partner.ValidationActivity;

import java.util.ArrayList;

public class ProcessActivity extends AppCompatActivity {
    public static ArrayList<TransformationOb>transformationObArrayList;
    public ArrayList<Boolean>stateRV,addedList;
    private Button b_next,b_back;
    private String listAcvities = null;
    private ViewPager pager=null;
    private Context context=this;
    private Spinner s_numberFarm;
    private RecyclerView rv_transformation;
    private LinearLayoutManager linearLayoutManager;
    private TransformationAdapter transformationAdapter;
    public static ArrayList<JsonObject> jsonObjectArrayList;
    //private FragmentProcessPageAdapter adapter=null;
    //private SlideProcessFragment slideProcessFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        setElements();
        setActions();
        //MainJson mainJson = new MainJson(context);
        // Instantiate a ViewPager
        pager = (ViewPager) this.findViewById(R.id.pager);

        // Create an adapter with the fragments we show on the ViewPager
        /*adapter = new FragmentProcessPageAdapter(
                getSupportFragmentManager());
        adapter.addFragment(SlideProcessFragment.newInstance(getResources()
                .getColor(R.color.colorWhite)));
        this.pager.setAdapter(adapter);*/
    }
    private  void setElements(){

        transformationObArrayList = new ArrayList<>();
        jsonObjectArrayList = new ArrayList<>();
        addedList = new ArrayList<>();
        stateRV =new ArrayList<>();

        stateRV.add(false);
        addedList.add(false);
        TransformationOb transformationOb = new TransformationOb();
        transformationOb.setName("Transformación 1");
        transformationObArrayList.add(transformationOb);
        jsonObjectArrayList.add(new JsonObject());


        b_next =findViewById(R.id.b_next);
        b_back =findViewById(R.id.b_back);
        listAcvities = getIntent().getStringExtra("list");
        s_numberFarm=findViewById(R.id.s_numberFarm);

        rv_transformation = findViewById(R.id.rv_transformation);
        transformationAdapter = new TransformationAdapter(context,transformationObArrayList);
        rv_transformation.setHasFixedSize(true);
        linearLayoutManager  = new LinearLayoutManager(context);
        rv_transformation.setLayoutManager(linearLayoutManager);
        rv_transformation.setAdapter(transformationAdapter);
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

                JsonObject transformationBody = new JsonObject();
                for(int i=0;i<jsonObjectArrayList.size();i++){
                    if(stateRV.get(i)==false){
                        Toast.makeText(context, "No se ha presionado la transformación #"+(i+1), Toast.LENGTH_SHORT).show();
                        return;
                    }


                    else if(addedList.get(i)==false && stateRV.get(i)==true){
                        addedList.set(i,true);
                        transformationBody.add(""+(i+1),jsonObjectArrayList.get(i));
                    }
                }
                MainJson.addChild("productos_transformados",transformationBody);
                MainJson.printBody();
                /*Intent intent =new Intent(context,LiveStockEcoActivity.class);

                startActivity(intent);*/
                if(stateRV.get(0)==false){
                    Toast.makeText(context, "No se ha presionado la transformación "+1, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(listAcvities.equals("")){
                    Intent intent =new Intent(context,ValidationActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent =new Intent(context,Comercializacion.class);
                    intent.putExtra("list",listAcvities);
                    startActivity(intent);
                }
            }
        });
        final ViewPager pager;
        s_numberFarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!((String)adapterView.getItemAtPosition(i)).equals("Elija")){
                    int val = Integer.parseInt((String)adapterView.getItemAtPosition(i));

                    if(val==1){
                        transformationObArrayList.clear();
                        stateRV.clear();
                        addedList.clear();
                        jsonObjectArrayList.clear();

                        TransformationOb transformationOb = new TransformationOb();
                        transformationOb.setName("Transformación 1");
                        transformationObArrayList.add(transformationOb);

                        stateRV.add(false);
                        addedList.add(false);
                        jsonObjectArrayList.add(new JsonObject());

                        transformationAdapter.notifyDataSetChanged();
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (90 * scale + 0.5f);
                        rv_transformation.getLayoutParams().height = pixels;
                    }
                    else {
                        transformationObArrayList.clear();
                        stateRV.clear();
                        addedList.clear();
                        jsonObjectArrayList.clear();

                        int size=0;
                        for(int index=0;index<val;index++){
                            TransformationOb transformationOb = new TransformationOb();
                            transformationOb.setName("Transformación "+ (index+1) );
                            transformationObArrayList.add(transformationOb);
                            stateRV.add(false);
                            addedList.add(false);
                            jsonObjectArrayList.add(new JsonObject());
                            size+=90;
                        }

                        transformationAdapter.notifyDataSetChanged();
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (size * scale + 0.5f);
                        rv_transformation.getLayoutParams().height = pixels;
                    }
                }else{
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rv_transformation.addOnItemTouchListener(new RecyclerTouchListener(context, rv_transformation, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                stateRV.set(position,true);
                Intent intent = new Intent(context,DetailsTransformationActivity.class);
                intent.putExtra("index",position+"");
                startActivityForResult(intent, 1);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    /*private boolean validation(){
        for(int i=0;i<adapter.getCount();i++){
            SlideProcessFragment slideProcessFragment = (SlideProcessFragment) adapter.getItem(i);
            System.out.println("frag "+slideProcessFragment.indexPage);
            if(!slideProcessFragment.validation()){
                Toast.makeText(context, "Revisar los datos de la pantalla #"+(i+1), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }*/
    //-----------------------------------------------------------------------------
    // Here's what the app should do to remove a view from the ViewPager.
    /*public void removeView (View defunctPage)
    {
        int pageIndex = pagerAdapter.removeView (pager, defunctPage);
        // You might want to choose what page to display, if the current page was "defunctPage".
        if (pageIndex == pagerAdapter.getCount())
            pageIndex--;
        pager.setCurrentItem (pageIndex);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to get the currently displayed page.
    public View getCurrentPage ()
    {
        return pagerAdapter.getView (pager.getCurrentItem());
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to set the currently displayed page.  "pageToShow" must
    // currently be in the adapter, or this will crash.
    public void setCurrentPage (View pageToShow)
    {
        pager.setCurrentItem (pagerAdapter.getItemPosition (pageToShow), true);
    }*/
    @Override
    public void onBackPressed() {

        // Return to previous page when we press back button
        /*if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);*/

    }
    public void updateViews(int val){
       /* System.out.println(adapter.getCount()+"");

        for (int i=0;i<val-1;i++){
            slideProcessFragment = SlideProcessFragment.newInstance(getResources()
                    .getColor(R.color.colorWhite));
            slideProcessFragment.indexPage=i+2;
            adapter.addFragment(slideProcessFragment);
            System.out.println("Agregado");
        }
        System.out.println(adapter.getCount()+"");
        this.pager.setAdapter(adapter);*/
    }
    public void beforeOne(){
        /*adapter.beforeOne();
        adapter.addFragment(SlideProcessFragment.newInstance(getResources()
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
            /*String result=data.getStringExtra("result");
            String[]arr = result.split(",");
            chacraArrayList.get(Integer.parseInt(arr[1])).setNombre(arr[0]);
            farmingAdapter.notifyDataSetChanged();

            if(result!=null){

            }else{

            }*/

        }
    }
}
