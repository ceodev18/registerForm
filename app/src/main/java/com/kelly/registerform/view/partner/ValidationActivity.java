package com.kelly.registerform.view.partner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.kelly.registerform.R;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.view.MainActivity;
import com.kelly.registerform.view.ending.SuccessActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class ValidationActivity extends AppCompatActivity{
    private boolean state=true;
    private Button b_back,b_next;
    private Context context=this;
    private EditText et_inscripcion;
    private ProgressDialog progressDoalog;

    private final static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_validation);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!state){
                    Toast.makeText(context, "La solicitud ya ha sido guardada.", Toast.LENGTH_SHORT).show();
                }
                if(state){
                    if(!isNetworkConnected()) {
                        //Guardar en tabla
                        state=false;
                        MainJson.saveChild();
                        Toast.makeText(context, "No cuenta con conexión, la solicitud se ha guardado.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }else{
                        //else Toast.makeText(context, "Not Internet :C", Toast.LENGTH_SHORT).show();
                        MainJson.sendDB();

                        progressDoalog = new ProgressDialog(context);
                        progressDoalog.setMessage("Cargando....");
                        progressDoalog.setTitle("Registrando Datos");

                        progressDoalog.show();

                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                progressDoalog.dismiss();
                                Intent intent = new Intent(context, SuccessActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        };

                        Timer timer = new Timer();
                        timer.schedule(task, SPLASH_TIME_OUT);
                    }
                }




            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onBackPressed() {

    }
}
