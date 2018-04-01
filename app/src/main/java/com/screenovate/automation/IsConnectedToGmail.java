package com.screenovate.automation;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Pattern;

public class IsConnectedToGmail extends Service {
    public static String TAG = "GmailUtils";
    public IsConnectedToGmail() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String gmail = null;

        Pattern gmailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (gmailPattern.matcher(account.name).matches()) {
                gmail = account.name;
            }
        }

        Toast.makeText(this, gmail, Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }
}
