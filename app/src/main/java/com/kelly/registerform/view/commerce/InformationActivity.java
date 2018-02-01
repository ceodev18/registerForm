package com.kelly.registerform.view.commerce;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelly.registerform.R;
import com.kelly.registerform.view.partner.ValidationActivity;

import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity {
    private Context context;
    private TextView tv_show1,tv_show2,tv_show3,tv_show4,tv_show5,tv_show6,
            tv_show7,tv_show8,tv_show9,tv_show10,tv_show11,tv_show12;
    private LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5,ll_6,ll_7,ll_8,ll_9,ll_10,ll_11,ll_12;
    private ArrayList<Boolean>listState;
    private ArrayList<TextView>listTextView;
    private ArrayList<LinearLayout>linearLayoutArrayList;
    private Button b_next,b_back;
    private String list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        setElements();
        setActions();
    }
    private void setElements(){
        context = this;
        list = getIntent().getStringExtra("list");
        listState = new ArrayList<>();
        listTextView = new ArrayList<>();
        linearLayoutArrayList= new ArrayList<>();
        tv_show1 = (TextView)findViewById(R.id.tv_show1);
        tv_show2 = (TextView)findViewById(R.id.tv_show2);
        tv_show3 = (TextView)findViewById(R.id.tv_show3);
        tv_show4 = (TextView)findViewById(R.id.tv_show4);
        tv_show5 = (TextView)findViewById(R.id.tv_show5);
        tv_show6 = (TextView)findViewById(R.id.tv_show6);
        tv_show7 = (TextView)findViewById(R.id.tv_show7);
        tv_show8 = (TextView)findViewById(R.id.tv_show8);
        tv_show9 = (TextView)findViewById(R.id.tv_show9);
        tv_show10 = (TextView)findViewById(R.id.tv_show10);
        tv_show11 = (TextView)findViewById(R.id.tv_show11);
        tv_show12 = (TextView)findViewById(R.id.tv_show12);

        listTextView.add(tv_show1);
        listTextView.add(tv_show2);
        listTextView.add(tv_show3);
        listTextView.add(tv_show4);
        listTextView.add(tv_show5);
        listTextView.add(tv_show6);
        listTextView.add(tv_show7);
        listTextView.add(tv_show8);
        listTextView.add(tv_show9);
        listTextView.add(tv_show10);
        listTextView.add(tv_show11);
        listTextView.add(tv_show12);

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

        ll_1= (LinearLayout)findViewById(R.id.ll_1);
        ll_2= (LinearLayout)findViewById(R.id.ll_2);
        ll_3= (LinearLayout)findViewById(R.id.ll_3);
        ll_4= (LinearLayout)findViewById(R.id.ll_4);
        ll_5= (LinearLayout)findViewById(R.id.ll_5);
        ll_6= (LinearLayout)findViewById(R.id.ll_6);
        ll_7= (LinearLayout)findViewById(R.id.ll_7);
        ll_8= (LinearLayout)findViewById(R.id.ll_8);
        ll_9= (LinearLayout)findViewById(R.id.ll_9);
        ll_10= (LinearLayout)findViewById(R.id.ll_10);
        ll_11= (LinearLayout)findViewById(R.id.ll_11);
        ll_12= (LinearLayout)findViewById(R.id.ll_12);
        linearLayoutArrayList.add(ll_1);
        linearLayoutArrayList.add(ll_2);
        linearLayoutArrayList.add(ll_3);
        linearLayoutArrayList.add(ll_4);
        linearLayoutArrayList.add(ll_5);
        linearLayoutArrayList.add(ll_6);
        linearLayoutArrayList.add(ll_7);
        linearLayoutArrayList.add(ll_8);
        linearLayoutArrayList.add(ll_9);
        linearLayoutArrayList.add(ll_10);
        linearLayoutArrayList.add(ll_11);
        linearLayoutArrayList.add(ll_12);

        b_next =(Button)findViewById(R.id.b_next);
        b_back =(Button)findViewById(R.id.b_back);
    }
    private void setActions(){
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
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ValidationActivity.class);
                startActivity(intent);

            }
        });
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
