package com.kelly.registerform.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kelly.registerform.R;
import com.kelly.registerform.dataAccess.PresentacionDA;
import com.kelly.registerform.dataAccess.connectionDA.BigDataDA;
import com.kelly.registerform.interfaces.PermissionResultCallback;
import com.kelly.registerform.model.Presentacion;
import com.kelly.registerform.model.connection.BigData;
import com.kelly.registerform.model.customviews.ToolbarView;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.utils.PermissionUtils;
import com.kelly.registerform.utils.UlTagHandler;
import com.kelly.registerform.utils.Utils;
import com.kelly.registerform.view.ending.SuccessActivity;
import com.kelly.registerform.view.partner.RegistrationPartnerActivity;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,PermissionResultCallback {

    private final static int SPLASH_TIME_OUT = 3000;
    private ArrayList<String> permissions=new ArrayList<>();
    private PermissionUtils permissionUtils;
    private Button b_continuar;
    private Context context=this;
    private TextView tv_list_recomendation;
    private ToolbarView toolbarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getPermission();
        setElements();
        setActions();
        if(isNetworkConnected())insertLeftRequest();


    }

    // Verify connection to NETWORK OR WIFI
    private void insertLeftRequest(){



        List<BigData> list = BigDataDA.list;
        if(list.size()>0){

            final ProgressDialog progressDialog = Utils.createProgressDialog(context, "Registrando solicitudes anteriores");
            progressDialog.show();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    //Toast.makeText(context, "Solicitudes registradas", Toast.LENGTH_SHORT).show();
                }
            };

            Timer timer = new Timer();
            timer.schedule(task, SPLASH_TIME_OUT);


            for(int i=0;i<list.size();i++){
                MainJson.saveFromNoConnection(list.get(i).getMain());
                List<BigData>aux_list = BigData.find(BigData.class, "main = ?", list.get(i).getMain());
                aux_list.get(0).delete();
            }



        }





    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void getPermission(){
        permissionUtils=new PermissionUtils(context);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionUtils.check_permission(permissions,"Explain here why the app needs permissions",1);
    }

    private void setElements(){
        context = this;
        toolbarView =findViewById(R.id.tv_toolbar);
        b_continuar = (Button)findViewById(R.id.b_continuar);
        tv_list_recomendation = (TextView)findViewById(R.id.tv_list_recomendation);

        tv_list_recomendation.setText(Html.fromHtml(
                Utils.getStringFromFile("list_recomendations.html",context),
                null, new UlTagHandler()));
        /*tv_list_details.setText(Html.fromHtml(
                Utils.getStringFromFile("list_details.html",context),
                null, new UlTagHandler()));*/
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
    }
}