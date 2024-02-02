package com.example.massenger_application.Application;

import android.app.Service;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.example.massenger_application.Utils.FirebaseUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateStatusService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateFirestoreStatus();
        stopSelf();
        return START_NOT_STICKY;
    }

    private void updateFirestoreStatus() {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy hh:mm:a", Locale.getDefault());
        String formattedTime = "last seen " + sdf.format(currentTime);
        Log.e("MyApp", "Status update in service");
        FirebaseUtils.updateCurrentStatus(formattedTime);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
