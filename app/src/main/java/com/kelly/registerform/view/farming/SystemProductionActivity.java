package com.kelly.registerform.view.farming;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.kelly.registerform.R;
import com.thomashaertel.widget.MultiSpinner;

public class SystemProductionActivity extends AppCompatActivity {
    private Button b_next,b_back;
    private Context context=this;
    private String list;
    private MultiSpinner spinnerMulti1,spinnerMulti2,spinnerMulti3,spinnerMulti4;
    private ArrayAdapter adapter1,adapter2,adapter3,adapter4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_production);
        setElements();
        setActions();
    }
    private void setElements(){
        b_next= findViewById(R.id.b_next);
        list = getIntent().getStringExtra("list");
        b_back= findViewById(R.id.b_back);
        spinnerMulti1=findViewById(R.id.spinnerMulti1);
        spinnerMulti2=findViewById(R.id.spinnerMulti2);
        spinnerMulti3=findViewById(R.id.spinnerMulti3);
        spinnerMulti4=findViewById(R.id.spinnerMulti4);

        adapter1 = ArrayAdapter.createFromResource(this, R.array.sa_conserv_pract, android.R.layout.simple_spinner_item);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.sa_driv_pract, android.R.layout.simple_spinner_item);
        adapter3 = ArrayAdapter.createFromResource(this, R.array.sa_other_pract, android.R.layout.simple_spinner_item);
        adapter4 = ArrayAdapter.createFromResource(this, R.array.sa_bio, android.R.layout.simple_spinner_item);

        spinnerMulti1.setAdapter(adapter1, false, onSelectedListener);
        spinnerMulti2.setAdapter(adapter2, false, onSelectedListener);
        spinnerMulti3.setAdapter(adapter3, false, onSelectedListener);
        spinnerMulti4.setAdapter(adapter4, false, onSelectedListener);
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FarmingCertificationActivity.class);
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
