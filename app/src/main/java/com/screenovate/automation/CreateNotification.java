package com.screenovate.automation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;


public class CreateNotification extends Service {
    public int getRandom(){
        Random r = new Random();
        int i1 = r.nextInt(10000 - 100) + 100;
        return i1;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String randNumber = String.valueOf(getRandom());
        Log.i("AutomationNotification", "Random notifiction number is " + randNumber);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New test notification")
                .setContentText(randNumber)
                .setSmallIcon(R.drawable.icon)
                .setOngoing(false).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}