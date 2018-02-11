package com.kelly.registerform.view.partner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kelly.registerform.R;
import com.kelly.registerform.example.MapaGoogle;
import com.kelly.registerform.view.MapsActivity;

public class RegistrationPartnerActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    private static final int PICK_IMAGE = 100;
    private Button b_next,b_dni,b_photo;
    private Context context;
    private Spinner gender_spinner,regions_spinner;
    private String lattitude,longitude;
    private TextView tv_dni,tv_photo;
    private LocationManager locationManager;
    private Uri imageUri;
    private ImageView foto_gallery;
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
        b_dni= (Button)findViewById(R.id.b_dni);
        b_photo= (Button)findViewById(R.id.b_photo);


        tv_dni = findViewById(R.id.tv_dni);
        tv_photo = findViewById(R.id.tv_photo);


        //gender_spinner=(Spinner)findViewById(R.id.gender_spinner);
        //String[] arraySpinner = new String[] {"Elija", "Femenino", "Masculino"};
        //gender_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner));
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

        b_dni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        b_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
    }
    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
