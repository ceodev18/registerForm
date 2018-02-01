package com.kelly.registerform.view.livestock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.kelly.registerform.R;
import com.kelly.registerform.view.farming.FarmingCertificationActivity;
import com.thomashaertel.widget.MultiSpinner;

public class LiveStockEcoActivity extends AppCompatActivity {
    private Button b_next,b_back;
    private Context context=this;
    private String list;
    private MultiSpinner spinnerMulti1,spinnerMulti2,spinnerMulti3;
    private ArrayAdapter<String> adapter;
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
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.add("Técnica #1");
        adapter.add("Técnica #2");
        adapter.add("Técnica #3");

        spinnerMulti1 =findViewById(R.id.spinnerMulti1);
        spinnerMulti2 =findViewById(R.id.spinnerMulti2);
        spinnerMulti3 =findViewById(R.id.spinnerMulti3);
        spinnerMulti1.setAdapter(adapter, false, onSelectedListener);
        spinnerMulti2.setAdapter(adapter, false, onSelectedListener);
        spinnerMulti3.setAdapter(adapter, false, onSelectedListener);
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
    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
            // Do something here with the selected items
        }
    };
}
