package com.screenovate.automation;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class SmsUtilService extends Service {
    private static final String TAG = "SmsUtil";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (extras.containsKey("SentSms")) {
                    Log.i(TAG, "Logging Last Sent Sms" + extras.getString("SentSms"));
                    getLastSentSMS();
                }

                if (extras.containsKey("ReceiveSms")) {
                    Log.i(TAG, "Logging Last Receive Sms");
                    getLastReceiveSMS();

                }
            }
        }
            return super.onStartCommand(intent, flags, startId);
        }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getLastReceiveSMS() {
        Uri sentURI = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(sentURI, null, null, null, "date desc limit 1");
        String str = "";
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            str = "Message receive: from address " + c.getString(c.getColumnIndex("address")) + " body: " +
                    c.getString(c.getColumnIndex("body"));
        }
        Log.i(TAG, str);

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
