package com.kelly.registerform.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.BuildConfig;
import com.kelly.registerform.R;
import com.kelly.registerform.view.MapsActivity;
import com.thomashaertel.widget.MultiSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by KELLY on 29/01/2018.
 */


public class SlideLiveStockFragment extends Fragment {

    /**
     * Key to insert the background color into the mapping of a Bundle.
     */
    private int VALOR_RETORNO = 1;
    private static final String BACKGROUND_COLOR = "color";
    private ViewGroup rootView;
    private Spinner s_tipo,s_manejo,s_propio,s_venta;
    private ArrayList<Integer>idTipo,idManejo,idDerivado;
    private ArrayList<String>arrayListTipo,arrayListManejo,arrayListDerivado;
    private Button b_map;
    private EditText et_longitude,et_latitude;
    private MultiSpinner spinner;
    /**
     * Key to insert the index page into the mapping of a Bundle.
     */
    private static final String INDEX = "index";

    private int color;
    private int index;

    /**
     * Instances a new fragment with a background color and an index page.
     *
     * @param color
     *            background color

     *            index page
     * @return a new page
     */
    public static com.kelly.registerform.fragments.SlideLiveStockFragment newInstance(int color) {

        // Instantiate a new fragment
        com.kelly.registerform.fragments.SlideLiveStockFragment fragment = new com.kelly.registerform.fragments.SlideLiveStockFragment();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_livestock, container, false);
         setElements(rootView);
         fillDerivatesProducts();
         setActions();


        // Show the current page index in the view
        //TextView tvIndex = (TextView) rootView.findViewById(R.id.tvIndex);
        //tvIndex.setText(String.valueOf(this.index));

        // Change the background color
        rootView.setBackgroundColor(this.color);

        return rootView;

    }
    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            // Do something here with the selected items
        }
    };
    private void setActions(){
        s_propio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!s_propio.getSelectedItem().toString().equals("Elija")){
                    int val = 100-Integer.parseInt(s_propio.getSelectedItem().toString());
                    String arr[]={val+""};
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),   android.R.layout.simple_spinner_item, arr);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    s_venta.setAdapter(spinnerArrayAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void setElements(ViewGroup rootView){
        spinner = (MultiSpinner) rootView.findViewById(R.id.spinnerMulti);
        s_propio=rootView.findViewById(R.id.s_propio);
        s_venta=rootView.findViewById(R.id.s_venta);
        et_longitude = rootView.findViewById(R.id.et_longitud);
        et_latitude = rootView.findViewById(R.id.et_latitud);
        b_map=rootView.findViewById(R.id.b_map);
        b_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MapsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        s_tipo=rootView.findViewById(R.id.s_tipo);
        s_manejo=rootView.findViewById(R.id.s_manejo);
        fillTipo();
        fillManejo();
    }


    private void fillTipo(){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_tipo_alimento.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_alimento");
                            Iterator<?> keys = jsonObj2.keys();
                            idTipo=new ArrayList<>();
                            idTipo.add(0);
                            arrayListTipo=new ArrayList<>();
                            arrayListTipo.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idTipo.add(Integer.parseInt(key));
                                    arrayListTipo.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListTipo);
                            s_tipo.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            s_tipo.setAdapter(null);
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

    private void fillManejo(){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_tipo_manejo_crianza.php?token=lpsk.21568$lsjANPIO022";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_manejo_crianza");
                            Iterator<?> keys = jsonObj2.keys();
                            idManejo=new ArrayList<>();
                            idManejo.add(0);
                            arrayListManejo=new ArrayList<>();
                            arrayListManejo.add("Elija");
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idManejo.add(Integer.parseInt(key));
                                    arrayListManejo.add(name);
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    arrayListManejo);
                            s_manejo.setAdapter(spinnerArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            s_manejo.setAdapter(null);
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
    private void fillDerivatesProducts(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BuildConfig.BASE_URL+"lista_productos_derivados.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("productos_derivados");
                            Iterator<?> keys = jsonObj2.keys();
                            idDerivado=new ArrayList<>();
                            idDerivado.add(0);
                            arrayListDerivado=new ArrayList<>();
                            arrayListDerivado.add("Elija");
                            ArrayAdapter<String> adapter;
                            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    idDerivado.add(Integer.parseInt(key));
                                    arrayListDerivado.add(name);
                                    adapter.add(name);
                                }
                            }

                            spinner.setAdapter(adapter, false,onSelectedListener);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            spinner.setAdapter(null, false,onSelectedListener);
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
