package com.kelly.registerform.view.partner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.kelly.registerform.R;
import com.kelly.registerform.view.MainActivity;
import com.kelly.registerform.view.commerce.ComercializacionActivity;
import com.kelly.registerform.view.commerce.InformationActivity;
import com.kelly.registerform.view.farming.ProductionActivity;
import com.kelly.registerform.view.livestock.LivestockProductionActivity;
import com.kelly.registerform.view.transformation.ProcessActivity;

import java.util.ArrayList;

public class WelcomePartnerActivity extends AppCompatActivity {
    private Button b_next;
    private Context context;
    private CheckBox cb_1,cb_2,cb_3,cb_4;
    private boolean state1,state2,state3,state4;
    private ArrayList<Integer> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_partner);
        setElements();
        setActions();
    }
    private void setElements(){
        context = this;
        list =new ArrayList<>();
        b_next=(Button) findViewById(R.id.b_next);
        cb_1 = (CheckBox) findViewById(R.id.cb_1);
        cb_2 = (CheckBox) findViewById(R.id.cb_2);
        cb_3 = (CheckBox) findViewById(R.id.cb_3);
        cb_4 = (CheckBox) findViewById(R.id.cb_4);
        state1=false;
        state2=false;
        state3=false;
        state4=false;

    }
    private void setActions(){
        cb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                state1=true;
            }
        });
        cb_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                state2=true;
            }
        });
        cb_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                state3=true;
            }
        });
        cb_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                state4=true;
            }
        });


        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list=new ArrayList<>();
                if(state1)list.add(1);
                if(state2)list.add(2);
                if(state3)list.add(3);
                if(state4)list.add(4);

                if(list.size()>0){
                    if(list.get(0)==1){
                        Intent i = new Intent(context,ProductionActivity.class);
                        i.putExtra("list",listChecks());
                        startActivity(i);

                    }
                    if(list.get(0)==2){
                        Intent i = new Intent(context,LivestockProductionActivity.class);
                        i.putExtra("list",listChecks());
                        startActivity(i);

                    }
                    if(list.get(0)==3){
                        Intent i = new Intent(context,ProcessActivity.class);
                        i.putExtra("list",listChecks());
                        startActivity(i);

                    }
                    if(list.get(0)==4){
                        Intent i = new Intent(context,ComercializacionActivity.class);
                        i.putExtra("list",listChecks());
                        startActivity(i);

                    }

                }else{
                    //Toast.makeText(context, "Debe seleccionar al menos un opción", Toast.LENGTH_SHORT).show();
                    //open last form
                    Intent i = new Intent(context,ValidationActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

            }
        });
    }
    private String listChecks(){
        String listCheck="";
        if(list.size()>1){
            for(int i=1;i<list.size();i++){
                listCheck+=list.get(i)+",";
            }
            return listCheck;
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        setElements();
        setActions();
    }
}
