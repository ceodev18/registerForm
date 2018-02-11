package com.kelly.registerform.view.partner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kelly.registerform.R;
import com.kelly.registerform.view.ending.SuccessActivity;

public class ValidationActivity extends AppCompatActivity {
    private Button b_back,b_next;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        b_back = findViewById(R.id.b_back);
        b_next= findViewById(R.id.b_next);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SuccessActivity.class);
                startActivity(intent);
            }
        });
    }
}
