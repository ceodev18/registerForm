package com.kelly.registerform.example;

/**
 * Created by KELLY on 27/01/2018.
 */

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.kelly.registerform.model.ubigeo.Departamento;
import com.kelly.registerform.model.ubigeo.Provincia;
import com.kelly.registerform.view.MapsActivity;
import com.kelly.registerform.view.farming.ProductionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ScreenSlidePageFragment extends Fragment {

    /**
     * Key to insert the background color into the mapping of a Bundle.
     */
    private static final String BACKGROUND_COLOR = "color";

    /**
     * Key to insert the index page into the mapping of a Bundle.
     */
    private static final String INDEX = "index";
    private int VALOR_RETORNO = 1;
    private int color;
    private int index;
    public int indexPage=1;
    private LinearLayout ll_1,ll_2;
    private TextView tv_show1,tv_show2;
    private ArrayList<TextView> textViewArrayList;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private ArrayList<String>arrayListType,arrayListSize;
    private ArrayList<Integer>idType,idSize;
    private ArrayList<Boolean>listState;
    private Spinner s_type,s_size;
    private  int[] viewList;
    private  ViewGroup rootView;
    private ArrayList<Integer>g1,g2,p1,p2;
    private ArrayList<String>nameListG1,nameListG2,nameListP1,nameListp2;
    private Spinner spinnerGroup1,spinnerGroup2,spinnerProduct1,spinnerProduct2;
    private View v1,v2;
    private Button b_map;
    private EditText et_latitude,et_longitude;
    /**
     * Instances a new fragment with a background color and an index page.
     *
     * @param color
     *            background color

     *            index page
     * @return a new page
     */
    public static ScreenSlidePageFragment newInstance(int color) {

        // Instantiate a new fragment
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putInt(BACKGROUND_COLOR, color);
        //bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        // Load parameters when the initial creation of the fragment is done
        this.color = (getArguments() != null) ? getArguments().getInt(
                BACKGROUND_COLOR) : Color.GRAY;
        this.index = (getArguments() != null) ? getArguments().getInt(INDEX)
                : -1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button b_file;
        TextView tv_title;
        rootView = (ViewGroup) inflater.inflate(
                R.layout.page_farm1, container, false);
        initializeArrays();
        b_map=rootView.findViewById(R.id.b_map);
        et_latitude=rootView.findViewById(R.id.et_latitud);
        et_longitude=rootView.findViewById(R.id.et_longitud);
        b_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        // Show the current page index in the view
        //TextView tvIndex = (TextView) rootView.findViewById(R.id.tvIndex);
        //tvIndex.setText(String.valueOf(this.index));
        setElements(rootView);


        tv_show1 = rootView.findViewById(R.id.tv_show1);
        tv_show2 = rootView.findViewById(R.id.tv_show2);
        textViewArrayList.add(tv_show1);
        textViewArrayList.add(tv_show2);
        listState.add(false);
        listState.add(false);

        ll_1 = rootView.findViewById(R.id.ll_1);
        ll_2= rootView.findViewById(R.id.ll_2);
        linearLayoutArrayList.add(ll_1);
        linearLayoutArrayList.add(ll_2);

        b_file = rootView.findViewById(R.id.b_file);
        tv_title= rootView.findViewById(R.id.tv_title);
        tv_title.setText("CHACRA/PARCELA #"+indexPage);

        for (int i=0;i<textViewArrayList.size();i++){
            final int index=i;
            /*textViewArrayList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listState.get(index)==false){
                        linearLayoutArrayList.get(index).setVisibility(View.VISIBLE);
                        listState.set(index,true);
                    }else{
                        linearLayoutArrayList.get(index).setVisibility(View.GONE);
                        listState.set(index,false);
                    }
                }
            });*/
        }

        b_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);
            }
        });
        // Change the background color
        rootView.setBackgroundColor(this.color);

        return rootView;

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
            String[]datos=result.split(",");
            et_longitude.setText(datos[1]);
            et_latitude.setText(datos[0]);

        }
    }
    private void setElements(ViewGroup rootView){
        s_type=rootView.findViewById(R.id.s_type);
        fillType();
        s_size=rootView.findViewById(R.id.s_size);
        fillSize();
        setLinearLayoutDetails();
    }

    public  void fillType(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_tipo_propiedad.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_propiedad");
                            Iterator<?> keys = jsonObj2.keys();
                            idType=new ArrayList<>();
                            arrayListType=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();

                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("tipo_propiedad");

                                    idType.add(id);
                                    arrayListType.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idType.add(Integer.parseInt(key));
                                    arrayListType.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListType);
                            s_type.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
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
    public  void fillSize(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_tipo_medida.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_medidas");
                            Iterator<?> keys = jsonObj2.keys();
                            idSize=new ArrayList<>();
                            arrayListSize=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();

                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idSize.add(Integer.parseInt(key));
                                    arrayListSize.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListSize);
                            s_size.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
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
    private void setLinearLayoutDetails(){
        View viewBussiness  = new View(getContext());

        v1 = (View) rootView.findViewById(R.id.ll_detail1);
        v2 = (View) rootView.findViewById(R.id.ll_detail2);

        spinnerGroup1=(Spinner) v1.findViewById(R.id.s_group_farming);
        spinnerGroup2=(Spinner) v2.findViewById(R.id.s_group_farming);
        spinnerProduct1=(Spinner) v1.findViewById(R.id.s_group_farming);
        spinnerProduct2=(Spinner) v2.findViewById(R.id.s_group_farming);

        g1=new ArrayList<>();
        nameListG1=new ArrayList<>();
        fillSpinnersGroup(spinnerGroup1,g1,nameListG1);

        g2=new ArrayList<>();
        nameListG2=new ArrayList<>();
        fillSpinnersGroup(spinnerGroup2,g2,nameListG2);

        //spinnerProd1=(Spinner) viewBussiness.findViewById(R.id.s_production);
        //spinnerArrayListProduction.add(spinnerProd1);
        /*viewBussiness = (View) rootView.findViewById(viewList[1]);
        Spinner spinnerGroup2=(Spinner) viewBussiness.findViewById(R.id.s_group_farming);
        //fillSpinnersGroup(spinnerGroup2,g2,nameListG2);
        spinnerArrayListGroup.add(spinnerGroup2);*/

      //setActionsSpinner();

    }
    private void fillSpinnersGroup(final Spinner spinner,final ArrayList<Integer>gId,final ArrayList<String>nameList){
        //fill group
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_grupos_productos_agricolas.php?&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("grupos_productos_agricolas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    //TODO when you already know the keys
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    gId.add(Integer.parseInt(key));
                                    nameList.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameList);
                            spinner.setAdapter(spinnerArrayAdapter1);

                        } catch (JSONException e) {
                            e.printStackTrace();
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
    private void initializeArrays(){
        textViewArrayList =new ArrayList<>();
        linearLayoutArrayList=new ArrayList<>();
        listState = new ArrayList<>();
    }
    private void setActionsSpinner(){

        spinnerGroup1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getContext(), ""+g1.get(i), Toast.LENGTH_SHORT).show();
                //fillSpinnerProducto(g1.get(i),spinnerProduct1,p1,nameListP1);
                String url = BuildConfig.BASE_URL+"lista_productos_agricolas.php?grupo="+g1.get(i)+"&token=lpsk.21568$lsjANPIO02";
                System.out.println(url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    Iterator<?> keys = jsonObj.keys();
                                    String key="";
                                    while (keys.hasNext()){key = (String)keys.next();}

                                    JSONObject jsonObj2 = (JSONObject) jsonObj.get(key);
                                    Iterator<?> keysv2 = jsonObj2.keys();
                                    ArrayList<String>lsita=new ArrayList<>();
                                    p1=new ArrayList<>();
                                    nameListP1=new ArrayList<>();
                                    while (keysv2.hasNext()){
                                        String val = (String)keysv2.next();
                                        if ( jsonObj2.get(val) instanceof JSONObject ) {
                                            JSONObject jsonDepartment = (JSONObject) jsonObj2.get(val);
                                    /*if(jsonDepartment.get(val) instanceof  JSONObject){

                                        JSONObject jsonbLost = (JSONObject) jsonDepartment.get(val);
                                        System.out.println(jsonbLost.toString());
                                        int  id = (int)jsonbLost.get("id");
                                        String name =(String)jsonbLost.get("producto");
                                        p2.add(id);
                                        nameListP1.add(name);
                                    }else{
                                        System.out.println(jsonDepartment.toString());
                                        int  id = (int)jsonDepartment.get("id");
                                        String name =(String)jsonDepartment.get("producto");
                                        p2.add(id);
                                        nameListP1.add(name);
                                    }*/
                                            //System.out.println(jsonDepartment.toString());


                                        }else{
                                            String name= (String)jsonObj2.get(val);
                                            System.out.println(name);
                                            p1.add(Integer.parseInt(val));
                                            nameListP1.add(name);
                                        }
                                    }
                                    ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item,
                                            nameListP1);
                                    spinnerProduct1.setAdapter(spinnerArrayAdapter1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Fail","That didn't work!");

                    }
                });
                //queue.add(stringRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerGroup2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                p2=new ArrayList<>();
                nameListp2=new ArrayList<>();
                Toast.makeText(getContext(), ""+g2.get(i), Toast.LENGTH_SHORT).show();
                //fillSpinnerProducto(g2.get(i),spinnerProduct2,p2,nameListp2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}