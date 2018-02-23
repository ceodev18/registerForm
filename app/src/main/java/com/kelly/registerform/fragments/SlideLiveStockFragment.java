package com.kelly.registerform.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

/**
 * Created by KELLY on 29/01/2018.
 */


public class SlideLiveStockFragment extends Fragment {

    /**
     * Key to insert the background color into the mapping of a Bundle.
     */
    private static final String BACKGROUND_COLOR = "color";
    private ViewGroup rootView;
    private Spinner s_tipo,s_manejo;
    private ArrayList<Integer>idTipo,idManejo;
    private ArrayList<String>arrayListTipo,arrayListManejo;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_livestock, container, false);
         setElements(rootView);
        MultiSpinner spinner;
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
        adapter.add("Leche");
        adapter.add("Huevos");
        adapter.add("Lana");
        spinner = (MultiSpinner) rootView.findViewById(R.id.spinnerMulti);
        spinner.setAdapter(adapter, false,onSelectedListener);
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
    private void setElements(ViewGroup rootView){
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
                            arrayListTipo=new ArrayList<>();
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
                            arrayListManejo=new ArrayList<>();
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
