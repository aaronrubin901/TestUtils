package com.screenovate.automation;

import android.content.Context;
import android.util.Log;

import java.util.Date;

/**
 * Created by aharon on 7/27/16.
 */
public class CallReceiver extends PhonecallReceiver {
    private static final String TAG = "CallReceiver";
    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
    Log.i(TAG, "incoming call from number " + number);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.i(TAG, "outgoing call started to number " + number);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.i(TAG, "incoming call with number " + number + " ended");
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.i(TAG, "outgoing call with number " + number + " ended");
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.i(TAG, "missed call from " + number);
    }

}