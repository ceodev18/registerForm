package com.kelly.registerform.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelly.registerform.R;

import java.util.ArrayList;

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

    private int color;
    private int index;
    private Context context;
    private ArrayList<TextView> listTextView;
    private TextView tv_show1,tv_show2,tv_show3,tv_show4,tv_show5,tv_show6;
    private LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5,ll_6;
    private ArrayList<Boolean>listState;
    private ArrayList<LinearLayout>linearLayoutArrayList;
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

        // Change the background color
        rootView.setBackgroundColor(this.color);

        return rootView;

    }
}