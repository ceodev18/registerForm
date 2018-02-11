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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelly.registerform.R;
import com.kelly.registerform.view.MapsActivity;
import com.kelly.registerform.view.farming.ProductionActivity;

import java.util.ArrayList;

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
    private ArrayList<Boolean>listState;
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
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.page_farm1, container, false);

        // Show the current page index in the view
        //TextView tvIndex = (TextView) rootView.findViewById(R.id.tvIndex);
        //tvIndex.setText(String.valueOf(this.index));
        textViewArrayList =new ArrayList<>();
        linearLayoutArrayList=new ArrayList<>();
        listState = new ArrayList<>();

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
            textViewArrayList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listState.get(index)==false){

                        //ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1100);//(ViewGroup.MarginLayoutParams) rootView.getLayoutParams();
                        //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rootView.getLayoutParams();
                        //rootView.setLayoutParams(layoutParams);

                        linearLayoutArrayList.get(index).setVisibility(View.VISIBLE);
                        listState.set(index,true);
                    }else{
                        linearLayoutArrayList.get(index).setVisibility(View.GONE);
                        listState.set(index,false);
                    }
                }
            });
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
            Uri uri = data.getData(); //obtener el uri content

        }
    }
    private void setActions(){

    }
}