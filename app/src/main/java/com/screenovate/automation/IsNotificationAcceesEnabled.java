package com.screenovate.automation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class IsNotificationAcceesEnabled extends Service {
    public static String TAG = "NotificationAccess";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String enabledAppList = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        Log.i(TAG, enabledAppList);
        boolean isEnabled = enabledAppList.contains("com.screenovate.BluePhone");
        Log.i(TAG, "Is Notification access enabled ? " + isEnabled);
        return super.onStartCommand(intent, flags, startId);
    }

    public IsNotificationAcceesEnabled() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
