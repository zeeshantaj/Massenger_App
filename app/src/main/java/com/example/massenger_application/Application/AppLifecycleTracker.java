package com.example.massenger_application.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppLifecycleTracker implements Application.ActivityLifecycleCallbacks {
    private int numActivities = 0;
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (numActivities == 0) {
            // App went from the background to the foreground
            stopUpdateStatusService(activity);
        }
        numActivities++;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        numActivities--;
        if (numActivities == 0) {
            // App went to the background
            startUpdateStatusService(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }
    private void startUpdateStatusService(Context context) {
        Intent serviceIntent = new Intent(context, UpdateStatusService.class);
        context.startService(serviceIntent);
    }

    private void stopUpdateStatusService(Context context) {
        Intent serviceIntent = new Intent(context, UpdateStatusService.class);
        context.stopService(serviceIntent);
    }
}
