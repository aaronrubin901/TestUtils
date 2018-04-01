package com.screenovate.automation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class IsKeyboardOpened extends Service {
    public static final String TAG = "IsSoftKeyboard";
    public IsKeyboardOpened() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isActive()) {
            Toast.makeText(getApplicationContext(),"isActive TRUE",
                    Toast.LENGTH_LONG).show();
            Log.i(TAG, "Software Keyboard was shown");
        } else {
            Toast.makeText(getApplicationContext(),"isActive False",
                    Toast.LENGTH_LONG).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
