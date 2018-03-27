package com.kelly.registerform.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by KELLY on 29/01/2018.
 */


public class SlideProcessFragment extends Fragment {

    /**
     * Key to insert the background color into the mapping of a Bundle.
     */
    private static final String BACKGROUND_COLOR = "color";

    /**
     * Key to insert the index page into the mapping of a Bundle.
     */
    private static final String INDEX = "index";
    private static final int PICK_IMAGE = 100;
    private int color;
    private int index;
    private Button b_map;
    private Context context;
    private EditText et_latitude,et_longitude,et_sanitario,et_ruc,et_razon,et_year,et_code;
    private ArrayList<TextView> listTextView;
    private TextView tv_show1,tv_show2,tv_show3,tv_show4,tv_show5,tv_show6,tv_title,tv_photo,
            tv_photo_final,tv_file;
    private LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5,ll_6;
    private ArrayList<Boolean>listState;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private int VALOR_RETORNO = 1;
    private ViewGroup rootView;
    public int indexPage=1;
    private Spinner s_type,s_product,s_month_start,s_month_end,s_tipo,s_tiene,s_company;
    private ArrayList<String>arrayListType,arrayListProduct,nam1,nam2,nam3,arrayListCompany;
    private ArrayList<Integer>idType,idProduct,ll1,ll2,ll3,idCompany;
    private View v1,v2,v3;
    /**
     * Instances a new fragment with a background color and an index page.
     *
     * @param color
     *            background color

     *            index page
     * @return a new page
     */
    public static com.kelly.registerform.fragments.SlideProcessFragment newInstance(int color) {

        // Instantiate a new fragment
        com.kelly.registerform.fragments.SlideProcessFragment fragment = new com.kelly.registerform.fragments.SlideProcessFragment();

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
        Button b_photo,b_photo_final,b_file;
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_process, container, false);
        setElements();
        fillComapy();
        setActions();
        setSpecialViews();
        // Show the current page index in the view
        tv_show1 = (TextView) rootView.findViewById(R.id.tv_show1);
        tv_show2 = (TextView) rootView.findViewById(R.id.tv_show2);
        tv_show3 = (TextView) rootView.findViewById(R.id.tv_show3);
        tv_show4 = (TextView) rootView.findViewById(R.id.tv_show4);
        tv_show5 = (TextView) rootView.findViewById(R.id.tv_show5);
        tv_show6 = (TextView) rootView.findViewById(R.id.tv_show6);
        tv_title = rootView.findViewById(R.id.tv_title);
        tv_title.setText("Producto transformado #"+indexPage);
        listTextView= new ArrayList<>();
        listTextView.add(tv_show1);
        listTextView.add(tv_show2);
        listTextView.add(tv_show3);
        listTextView.add(tv_show4);
        listTextView.add(tv_show5);
        listTextView.add(tv_show6);

        ll_1= rootView.findViewById(R.id.ll_1);
        ll_2= rootView.findViewById(R.id.ll_2);
        ll_3= rootView.findViewById(R.id.ll_3);
        ll_4= rootView.findViewById(R.id.ll_4);
        ll_5= rootView.findViewById(R.id.ll_5);
        ll_6= rootView.findViewById(R.id.ll_6);
        linearLayoutArrayList= new ArrayList<>();
        linearLayoutArrayList.add(ll_1);
        linearLayoutArrayList.add(ll_2);
        linearLayoutArrayList.add(ll_3);
        linearLayoutArrayList.add(ll_4);
        linearLayoutArrayList.add(ll_5);
        linearLayoutArrayList.add(ll_6);
        listState = new ArrayList<>();
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);
        listState.add(false);

        for (int i=0;i<listTextView.size();i++){
            final int index=i;
            listTextView.get(i).setOnClickListener(new View.OnClickListener() {
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
            });
        }
        //tvIndex.setText(String.valueOf(this.index));


        b_photo_final=rootView.findViewById(R.id.b_photo_final);
        b_file=rootView.findViewById(R.id.b_file);
        b_photo=rootView.findViewById(R.id.b_photo);

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
            if(result!=null){
                String[]datos=result.split(",");
                et_longitude.setText(datos[1]);
                et_latitude.setText(datos[0]);
            }else{
                Uri uri = data.getData(); //obtener el uri content
            }

        }

    }
    private void setElements(){
        et_sanitario =rootView.findViewById(R.id.et_sanitario);
        et_ruc =rootView.findViewById(R.id.et_ruc);
        et_razon =rootView.findViewById(R.id.et_razon);
        et_year =rootView.findViewById(R.id.et_year);
        et_code =rootView.findViewById(R.id.et_code);
        s_tipo=rootView.findViewById(R.id.s_tipo);
        s_tiene=rootView.findViewById(R.id.s_tiene);
        s_company=rootView.findViewById(R.id.s_company);
        s_month_start=rootView.findViewById(R.id.s_month_start);
        s_month_end=rootView.findViewById(R.id.s_month_end);
        et_latitude=rootView.findViewById(R.id.et_latitud);
        et_longitude=rootView.findViewById(R.id.et_longitud);
        tv_photo=rootView.findViewById(R.id.tv_photo);
        tv_photo_final=rootView.findViewById(R.id.tv_photo_final);
        tv_file=rootView.findViewById(R.id.tv_file);
        b_map=rootView.findViewById(R.id.b_map);
        s_type=rootView.findViewById(R.id.s_type);
        s_product=rootView.findViewById(R.id.s_product);
        arrayListType = new ArrayList<>();
        arrayListProduct = new ArrayList<>();
        idType= new ArrayList<>();
        idProduct= new ArrayList<>();
        arrayListCompany= new ArrayList<>();
        idCompany= new ArrayList<>();
        fillTipo();
    }
    private void fillTipo(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_grupos_productos_transformados.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("grupo_productos_transformados");
                            Iterator<?> keys = jsonObj2.keys();
                            idType=new ArrayList<>();
                            arrayListType=new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
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
    private void setActions(){
        s_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id = idType.get(i);
                fillProduct(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void fillProduct(int id){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_productos_transformados.php?grupo=1&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("productos_transformados");
                            Iterator<?> keys = jsonObj2.keys();
                            idProduct = new ArrayList<>();
                            arrayListProduct = new ArrayList<>();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    int  id = (int)jsonDepartment.get("id");
                                    String name =(String)jsonDepartment.get("producto_derivado");
                                    idProduct.add(id);
                                    arrayListProduct.add(name);
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idProduct.add(Integer.parseInt(key));
                                    arrayListProduct.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListProduct);
                            s_product.setAdapter(spinnerArrayAdapter);

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
    private void setSpecialViews(){
        //v1,v2,v3
        v1  = new View(getContext());
        v2  = new View(getContext());
        v3  = new View(getContext());

        v1 = (View) rootView.findViewById(R.id.ll_pre1);
        v2 = (View) rootView.findViewById(R.id.ll_pre2);
        v3 = (View) rootView.findViewById(R.id.ll_pre3);

        Spinner spinner1=(Spinner) v1.findViewById(R.id.s_presentation);
        Spinner spinner2=(Spinner) v2.findViewById(R.id.s_presentation);
        Spinner spinner3=(Spinner) v3.findViewById(R.id.s_presentation);
        nam1=new ArrayList<>();
        ll1=new ArrayList<>();
        fillPresentation(spinner1,nam1,ll1);
        nam2=new ArrayList<>();
        ll2=new ArrayList<>();
        fillPresentation(spinner2,nam2,ll2);
        nam3=new ArrayList<>();
        ll3=new ArrayList<>();
        fillPresentation(spinner3,nam3,ll3);
    }
    private void fillPresentation(final Spinner spinner,final ArrayList<String>nameList,final ArrayList<Integer>idList){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_tipo_presentacion.php?&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_presentacion");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idList.add(Integer.parseInt(key));
                                    nameList.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    nameList);
                            spinner.setAdapter(spinnerArrayAdapter);

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
    public boolean validation(){
        /*if(s_type.getSelectedItem().toString().equals("Elija")
                ||s_type.getSelectedItem().toString().length()==0)return false;
        if(s_product.getSelectedItem().toString().equals("Elija")
                ||s_product.getSelectedItem().toString().length()==0)return false;
        if(s_month_start.getSelectedItem().toString().equals("Elija")
                ||s_month_start.getSelectedItem().toString().length()==0)return false;
        if(s_month_end.getSelectedItem().toString().equals("Elija")
                ||s_month_end.getSelectedItem().toString().length()==0)return false;*/

        if(et_sanitario.getText().length()==0)return false;
        if(et_ruc.getText().length()==0)return false;
        if(et_razon.getText().length()==0)return false;
        if(et_code.getText().length()==0)return false;
        if(et_year.getText().length()==0)return false;

        if(s_tipo.getSelectedItem().toString().equals("Elija")
                ||s_tipo.getSelectedItem().toString().length()==0)return false;

        if(s_tiene.getSelectedItem().toString().equals("Elija")
                ||s_tiene.getSelectedItem().toString().length()==0)return false;
        if(s_company.getSelectedItem().toString().equals("Elija")
                ||s_company.getSelectedItem().toString().length()==0)return false;

        return true;
    }
    private void fillComapy(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idCompany.add(Integer.parseInt(key));
                                    System.out.println(name);
                                    arrayListCompany.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
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
}