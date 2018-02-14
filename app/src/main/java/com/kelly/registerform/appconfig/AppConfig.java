package com.kelly.registerform.appconfig;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by KELLY on 13/02/2018.
 */

public class AppConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
