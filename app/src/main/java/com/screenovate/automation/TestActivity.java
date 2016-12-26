package com.screenovate.automation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


public class TestActivity extends Activity {

    private static final String TAG = "TestActivity";

    KeyEvent event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "TEST ACTIVITY STARTTED");
        setContentView(R.layout.testactivity);
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }


    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                Log.i(TAG, "Menu key released");
                return true;
            case KeyEvent.KEYCODE_SEARCH:
                Log.i(TAG, "Search key released");
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (event.isTracking() && !event.isCanceled())
                    Log.i(TAG, "Volumen Up released");
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.i(TAG, "Volumen Down released");
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "KEY EVENT REGIRSTER" + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                Log.i(TAG, "Menu key pressed");
                return true;
            case KeyEvent.KEYCODE_SEARCH:
               Log.i(TAG, "Search key pressed");
                return true;
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                event.startTracking();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
               Log.i(TAG, "Volumen Down pressed");
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
       Log.i(TAG, "Pressed for a long time =) ");
        return true;
    }

    @Override
    public void onBackPressed() {
       Log.i(TAG, "Back key pressed =)");
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
       Log.i(TAG, "Touch press on x: " + x + " y: " + y);
        return true;
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
