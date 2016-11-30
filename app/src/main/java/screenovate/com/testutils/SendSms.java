package screenovate.com.testutils;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by aharon on 7/11/16.
 */
public class SendSms extends Service{
    private static final String TAG = "SendSmsTag";
    String phoneNo = null;
    String msg = null;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("phone")) {
                phoneNo = extras.getString("phone");
                Log.i(TAG, "Phone: " + extras.getString("phone"));
            }

            if (extras.containsKey("msg")) {
                msg = extras.getString("msg");
                Log.i(TAG, "message: " + extras.getString("msg"));

            }
            sendSMS(phoneNo, msg);
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendSMS(String phoneNo, String msg){

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


}
