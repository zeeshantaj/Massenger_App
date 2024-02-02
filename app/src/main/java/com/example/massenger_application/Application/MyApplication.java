package com.example.massenger_application.Application;

import android.app.Application;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new AppLifecycleTracker());
    }
}

