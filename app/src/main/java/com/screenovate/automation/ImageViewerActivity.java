package com.screenovate.automation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ImageViewerActivity extends AppCompatActivity {
    public static final String TAG = "IMAGEVIEWERLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);
        Log.i(TAG, "ImageViewerActivityOpened");
    }

        }


