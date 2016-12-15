package com.screenovate.automation;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageViewerActivity extends AppCompatActivity {
    public static final String TAG = "IMAGEVIEWERLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String imagePath = Environment.getExternalStorageDirectory().toString() + "checkerboard.bmp";
        Log.i(TAG, imagePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ImageView jpgView = (ImageView)findViewById(R.id.imageView);
        jpgView.setImageBitmap(bitmap);

            }

        }


