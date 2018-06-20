package com.kelly.registerform.view.ending;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kelly.registerform.R;
import com.kelly.registerform.model.customviews.ToolbarView;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.view.MainActivity;
import com.kelly.registerform.view.SplashActivity;

import java.util.Calendar;

public class SuccessActivity extends AppCompatActivity {
    private Context context=this;
    private Button b_next;
    private String code;
    private TextView tv_result,tv_date;
    private com.kelly.registerform.model.customviews.ToolbarView toolbarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        setElements();
        setActions();

    }
    private void setElements(){
        toolbarView = findViewById(R.id.tv_toolbar);

        b_next=findViewById(R.id.b_next);
        tv_result=findViewById(R.id.tv_result);
        tv_date=findViewById(R.id.tv_date);
        code =getIntent().getStringExtra("code");
        tv_result.setText("Su código de socio sería en caso de ser aceptado: "+ MainJson.code);
        //Fecha de registro de solicitud para ser socio de ANPE:

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
        tv_date.setText("Fecha de registro de solicitud para ser socio de ANPE: "+date);
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
