package com.kelly.registerform.view.commerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelly.registerform.R;

public class InformationActivity extends AppCompatActivity {
    private TextView tv_show1;
    private LinearLayout ll_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        tv_show1 = (TextView)findViewById(R.id.tv_show1);
        ll_1= (LinearLayout)findViewById(R.id.ll_1);
        tv_show1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_1.setVisibility(View.VISIBLE);
            }
        });
    }
}
