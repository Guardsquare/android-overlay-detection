package com.example.researchoverlay;

import static android.view.MotionEvent.FLAG_WINDOW_IS_OBSCURED;
import static android.view.MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class SecureButton extends androidx.appcompat.widget.AppCompatButton {

    public SecureButton(Context context) {
        super(context);
    }

    public SecureButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecureButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
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
