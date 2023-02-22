package com.example.researchoverlay;

import static android.view.MotionEvent.FLAG_WINDOW_IS_OBSCURED;
import static android.view.MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

public class SecureTextView extends androidx.appcompat.widget.AppCompatTextView {

    public SecureTextView(Context context) {
        super(context);
    }

    public SecureTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecureTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int flags = event.getFlags();
        Toast.makeText(getContext(), "SecureTextView touched: " + flags,
                Toast.LENGTH_SHORT).show();

        return onFilterTouchEventForSecurity(event);
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        // Add custom security check
        int flags = event.getFlags();
        Boolean badTouch = (((flags & FLAG_WINDOW_IS_PARTIALLY_OBSCURED) != 0)
                         || ((flags & FLAG_WINDOW_IS_OBSCURED) != 0));
        if (badTouch)
        {
            // consume touch event to block touch
            return false;
        }
        return super.onFilterTouchEventForSecurity(event);
    }
}