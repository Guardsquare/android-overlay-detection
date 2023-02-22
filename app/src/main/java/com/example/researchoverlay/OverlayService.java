package com.example.researchoverlay;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class OverlayService extends Service {
    private WindowManager mWindowManager;
    private View mOverlayView;

    public int onStartCommand(Intent intent, int flags, int startId) {
        mOverlayView = LayoutInflater.from(this).inflate(R.layout.overlay, null);

        // NOTE: Create the window with flag:
        // - TYPE_APPLICATION_OVERLAY, API 26+
        // - TYPE_TOAST, API 1+ but deprecated since API 26
        // - TYPE_SYSTEM_ALERT, API 1+ but deprecated since API 26
        // - TYPE_SYSTEM_DIALOG, API 1+
        // - TYPE_SYSTEM_OVERLAY, API 1+ but deprecated since API 26
        // - TYPE_SYSTEM_ERROR, API 1+ but deprecated since API 26
        //
        // All deprecated flags are recommended to use TYPE_APPLICATION_OVERLAY instead
        //
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // API 26+
                //WindowManager.LayoutParams.TYPE_TOAST, // API 1 - 25
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, // for tapjacking
                PixelFormat.TRANSLUCENT
        );

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mOverlayView, params);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Remove the overlay view
        if (mOverlayView != null) {
            mWindowManager.removeView(mOverlayView);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
