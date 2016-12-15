package com.screenovate.automation;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LastSentSmsService extends Service {
    private static final String TAG = "SmsUtilTag";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getLastSentSMS();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getLastSentSMS() {
        Uri sentURI = Uri.parse("content://sms/sent");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(sentURI, null, null, null, "date desc limit 1");
        String str = "";
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            str = "Message sent: to address " + c.getString(c.getColumnIndex("address")) + " body: " +
                    c.getString(c.getColumnIndex("body"));
        }
        Log.i(TAG, str);

    }

}
