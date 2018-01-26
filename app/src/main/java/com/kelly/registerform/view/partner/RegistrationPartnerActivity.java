package com.kelly.registerform.view.partner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.kelly.registerform.R;

public class RegistrationPartnerActivity extends AppCompatActivity {
    private Button b_next;
    private Context context;
    private Spinner gender_spinner,regions_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_partner);
        setElements();
        setActions();
    }
    private void setElements(){
        context = this;
        b_next = (Button)findViewById(R.id.b_next);
        gender_spinner=(Spinner)findViewById(R.id.gender_spinner);
        String[] arraySpinner = new String[] {"Elija", "Femenino", "Masculino"};
        gender_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner));
        regions_spinner=(Spinner)findViewById(R.id.regions_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.regions_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regions_spinner.setAdapter(adapter);
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,RegistrationPartnerPartBActivity.class);
                startActivity(intent);
            }
        });
    }
}
