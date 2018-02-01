package com.kelly.registerform.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelly.registerform.R;
import com.kelly.registerform.view.MapsActivity;

import java.util.ArrayList;

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
    private Context context;
    private ArrayList<TextView> listTextView;
    private TextView tv_show1,tv_show2,tv_show3,tv_show4,tv_show5,tv_show6;
    private LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5,ll_6;
    private ArrayList<Boolean>listState;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private int VALOR_RETORNO = 1;
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
        Button b_photo,b_getAddress,b_photo_final,b_file;
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_process, container, false);

        // Show the current page index in the view
        tv_show1 = (TextView) rootView.findViewById(R.id.tv_show1);
        tv_show2 = (TextView) rootView.findViewById(R.id.tv_show2);
        tv_show3 = (TextView) rootView.findViewById(R.id.tv_show3);
        tv_show4 = (TextView) rootView.findViewById(R.id.tv_show4);
        tv_show5 = (TextView) rootView.findViewById(R.id.tv_show5);
        tv_show6 = (TextView) rootView.findViewById(R.id.tv_show6);
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
        b_getAddress=rootView.findViewById(R.id.b_getAddress);
        b_photo_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        b_getAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        b_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
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
            Uri uri = data.getData(); //obtener el uri content
        }
    }
}