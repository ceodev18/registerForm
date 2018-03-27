package com.kelly.registerform.view.partner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.kelly.registerform.R;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.view.ending.SuccessActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ValidationActivity extends AppCompatActivity{
    private Button b_back,b_next;
    private Context context=this;
    private EditText et_inscripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        setElements();
        setActions();

    }
    private void setElements(){
        b_back = findViewById(R.id.b_back);
        b_next= findViewById(R.id.b_next);
        et_inscripcion = findViewById(R.id.et_inscripcion);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String date =year+"-";
        month++; // real month
        if((month)>=10){
            date+=""+month;
        }else{
            date+="0"+month;
        }
        if(day>=10){
            date+="-"+day;
        }else{
            date+="-0"+day;
        }
        et_inscripcion.setText(date);
    }
    private void setActions(){
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainJson.sendDB();
                Intent intent = new Intent(context, SuccessActivity.class);
                startActivity(intent);
            }
        });

    }

}
