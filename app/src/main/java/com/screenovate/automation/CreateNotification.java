package com.screenovate.automation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;


public class CreateNotification extends Service {
    public static final String TAG = "AutomationNotification";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String randomNumber = "";
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("random")) {
                randomNumber = extras.getString("random");
                Log.i(TAG, "Creating notification with random number " + randomNumber);
                createNotification(randomNumber);
            } else {
                Log.w(TAG, "Please insert Random number");
            }
        }

        return START_NOT_STICKY;
    }

    private void createNotification(String randomNumber) {
        Log.i(TAG, "Random notification number is " + randomNumber);
        Intent intent = new Intent(this, ImageViewerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification noti = new Notification.Builder(this)
                .setContentTitle("Automation Notification")
                .setContentText(randomNumber)
                .setSmallIcon(R.mipmap.ic_notify)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_notify))
                .setContentIntent(pendingIntent)
                .setOngoing(false).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, noti);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}