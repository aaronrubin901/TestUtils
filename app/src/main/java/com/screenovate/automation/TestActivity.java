package com.screenovate.automation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;


public class TestActivity extends Activity {

    private static final String TAG = TestActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent ev) {
        Log.d(TAG, ev.toString());
        return super.dispatchKeyEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        Log.d(TAG, ev.toString());
        return super.dispatchGenericMotionEvent(ev);
    }
}
