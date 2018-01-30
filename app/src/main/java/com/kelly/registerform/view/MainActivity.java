package com.kelly.registerform.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kelly.registerform.R;
import com.kelly.registerform.interfaces.PermissionResultCallback;
import com.kelly.registerform.utils.PermissionUtils;
import com.kelly.registerform.utils.UlTagHandler;
import com.kelly.registerform.utils.Utils;
import com.kelly.registerform.view.partner.RegistrationPartnerActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,PermissionResultCallback {
    private ArrayList<String> permissions=new ArrayList<>();
    private PermissionUtils permissionUtils;
    private Button b_continuar;
    private Context context=this;
    private TextView tv_list_details,tv_list_recomendation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        setElements();
        setActions();
    }
    private void getPermission(){
        permissionUtils=new PermissionUtils(context);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionUtils.check_permission(permissions,"Explain here why the app needs permissions",1);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        // redirects to utils

        permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    // Callback functions


    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
    }
}