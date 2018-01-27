package com.kelly.registerform.view.partner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.kelly.registerform.R;

public class RegistrationPartnerPartBActivity extends AppCompatActivity {
    private Spinner s_number_children;
    private LinearLayout ll_children;
    private Context context;
    private Button b_next,b_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_partner_part_b);
        setElements();
        setActions();
    }
    private void setElements(){
        context =this;
        s_number_children = (Spinner)findViewById(R.id.s_number_children);
        ll_children =  (LinearLayout)findViewById(R.id.ll_children);
        b_next = (Button)findViewById(R.id.b_next);
    }
    private void setActions(){
        s_number_children.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!((String)adapterView.getItemAtPosition(i)).equals("Elija")){
                    int val = Integer.parseInt((String)adapterView.getItemAtPosition(i));
                    if(ll_children.getChildCount()>0)ll_children.removeAllViews();
                    for (int index=0;index<val;index++){
                        View dynamicView = LayoutInflater.from(context).inflate(R.layout.item_child, null, false);
                        ll_children.addView(dynamicView);
                    }
                }else{
                    if(ll_children.getChildCount()>0)ll_children.removeAllViews();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,WelcomePartnerActivity.class);
                startActivity(intent);
            }
        });
    }
}
