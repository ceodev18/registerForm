package com.kelly.registerform.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kelly.registerform.R;
import com.kelly.registerform.utils.UlTagHandler;
import com.kelly.registerform.utils.Utils;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private Button b_continuar;
    private Context context;
    private TextView tv_list_details,tv_list_recomendation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setElements();
        setActions();
    }
    private void setElements(){
        context = this;
        b_continuar = (Button)findViewById(R.id.b_continuar);
        tv_list_recomendation = (TextView)findViewById(R.id.tv_list_recomendation);
        tv_list_details = (TextView)findViewById(R.id.tv_list_details);
        tv_list_recomendation.setText(Html.fromHtml(
                Utils.getStringFromFile("list_recomendations.html",context),
                null, new UlTagHandler()));
        tv_list_details.setText(Html.fromHtml(
                Utils.getStringFromFile("list_details.html",context),
                null, new UlTagHandler()));
    }
    private void setActions(){
        b_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RegistrationPartnerActivity.class);
                startActivity(intent);
            }
        });
    }
}