package com.example.researchoverlay;

import static android.view.MotionEvent.FLAG_WINDOW_IS_OBSCURED;
import static android.view.MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.researchoverlay.databinding.ActivityMainBinding;

import java.util.List;

import kotlinx.coroutines.MainCoroutineDispatcher;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_OVERLAY_PERMISSION = 251;
    private ActivityMainBinding binding;
    private Intent overlayIntent = null;
    private boolean toggleOverlays = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // #===========================#
        // #     Overlay Detection     #
        // #===========================#
        ViewGroup view = findViewById(R.id.main_view);
        TextView secureText = findViewById(R.id.sample_text);
        TextView statusText = findViewById(R.id.textView);

        // protect buttons
        Button secureFilterButton = findViewById(R.id.secureFilterButton);
        Button obscureButton = findViewById(R.id.obscureButton);
        Button partialObscureButton = findViewById(R.id.partialObscureButton);

        // Filter obscured touches (might have undefined behavior on API 31+)
        secureFilterButton.setFilterTouchesWhenObscured(true);

        // detect FLAG_WINDOW_IS_OBSCURED
        obscureButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int flags = event.getFlags();
                // FLAG_WINDOW_IS_PARTIALLY_OBSCURED somehow works at API 26+
                Boolean theBadTouch = ((flags & FLAG_WINDOW_IS_OBSCURED) != 0);

                if (theBadTouch)
                {
                    statusText.setText("ObscureButton: Tapjack detected! (flags: " + flags + ")");
                    // do not consume
                    return true;
                }
                return false;
            }
        });

        partialObscureButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int flags = event.getFlags();
                Boolean theBadTouch = ((flags & FLAG_WINDOW_IS_PARTIALLY_OBSCURED) != 0);

                if (theBadTouch)
                {
                    statusText.setText("PartialObscureButton: Tapjack detected! (flags: " + flags + ")");
                    // do not consume
                    return true;
                }
                return false;
            }
        });

        // activate buttons
        Button toggleOverlayButton = (Button)findViewById(R.id.btnOverlay);
        toggleOverlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOverlayService();
            }
        });

        Button detectOverlayButton = (Button)findViewById(R.id.btnDetect);
        detectOverlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // NOTE: Can only be used on API 31 and higher!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    // toggle all non-system overlays
                    toggleOverlays = !toggleOverlays;
                    MainActivity.this.getWindow().setHideOverlayWindows(toggleOverlays);
                    statusText.setText((!toggleOverlays ? "Hiding" : "Showing" ) + " all non-system overlays");
                } else {
                    statusText.setText("Hiding only works on API 31 or higher!");
                }
                // Do not forget to set HIDE_OVERLAY_WINDOWS permission in AndroidManifest.xml
            }
        });
    }

    private void startOverlayService() {
        // Ckeck permissions and request here if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check if the SYSTEM_ALERT_WINDOW permission is granted
            if (!Settings.canDrawOverlays(this)) {
                // Request the permission
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
            } else {
                // Permission is granted, start the OverlayService
                toggleOverlay();
            }
        } else {
            // Permission is granted automatically for API 23+
            toggleOverlay();
        }
    }

    private void toggleOverlay()
    {
        if (overlayIntent == null)
        {
            overlayIntent = new Intent(this, OverlayService.class);
            startService(overlayIntent);
        } else {
            stopService(overlayIntent);
            overlayIntent = null;
        }
    }
}
