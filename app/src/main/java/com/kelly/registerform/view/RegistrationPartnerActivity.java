package com.kelly.registerform.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kelly.registerform.R;

public class RegistrationPartnerActivity extends AppCompatActivity {
    private Spinner gender_spinner,regions_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_partner);
        setElements();
    }
    private void setElements(){
        gender_spinner=(Spinner)findViewById(R.id.gender_spinner);
        String[] arraySpinner = new String[] {"Elija", "Femenino", "Masculino"};
        gender_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner));
        regions_spinner=(Spinner)findViewById(R.id.regions_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.regions_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regions_spinner.setAdapter(adapter);
    }
}
