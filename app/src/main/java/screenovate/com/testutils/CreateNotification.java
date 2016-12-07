package screenovate.com.testutils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.support.annotation.Nullable;


public class CreateNotification extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New test notification")
                .setContentText("Test")
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