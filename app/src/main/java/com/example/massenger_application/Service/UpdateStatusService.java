package com.example.massenger_application.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

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
        String formattedTime = sdf.format(currentTime);

        FirebaseUtils.updateCurrentStatus(formattedTime);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
