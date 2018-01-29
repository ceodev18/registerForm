package com.kelly.registerform.view.livestock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kelly.registerform.R;
import com.kelly.registerform.view.farming.FarmingCertificationActivity;

public class LiveStockEcoActivity extends AppCompatActivity {
    private Button b_next,b_back;
    private Context context=this;
    private String list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stock_eco);
        setElements();
        setActions();
    }
    private void setElements(){
        b_next= findViewById(R.id.b_next);
        list = getIntent().getStringExtra("list");
        b_back= findViewById(R.id.b_back);
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CertificationLiveStockActivity.class);
                intent.putExtra("list",list);
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
