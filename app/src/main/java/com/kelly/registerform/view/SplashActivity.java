package com.kelly.registerform.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.R;
import com.kelly.registerform.controllers.DeparmentController;
import com.kelly.registerform.dataAccess.DepartmentDA;
import com.kelly.registerform.model.Book;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.utils.SaveDataBase;
import com.orm.SugarContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private Context context = this;
    private final static int SPLASH_TIME_OUT = 3000;
    private Activity self;
    private int numPermissions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        SugarContext.init(this);
        SaveDataBase saveDataBase = new SaveDataBase(context);
        //reviewDatabase();
        //saveDataBase.getDepartamentos();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            /*numPermissions = PermissionMethods.checkPermissions(this);
            if(numPermissions == 0){
                login();
            }*/
            runSplash();
        } else {
            runSplash();
        }
        MainJson mainJson = new MainJson(context);
    }
    private void runSplash() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent(context, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };
        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_TIME_OUT);
    }
    private void reviewDatabase(){
        DeparmentController deparmentController = new DeparmentController(context);
        deparmentController.getDepartment();
    }
}
