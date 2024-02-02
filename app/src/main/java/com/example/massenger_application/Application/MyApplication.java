package com.example.massenger_application.Application;

import android.app.Application;
import android.content.Intent;

import com.example.massenger_application.StatusService.UpdateStatusService;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        startUpdateStatusService();
    }

    private void startUpdateStatusService() {
        Intent serviceIntent = new Intent(this, UpdateStatusService.class);
        startService(serviceIntent);
    }
}

