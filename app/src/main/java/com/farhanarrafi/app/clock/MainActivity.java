package com.farhanarrafi.app.clock;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DigitalClock;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "MainActivity";
    private TextView digitalClock;
    private ViewGroup rootLayout;
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_main);
        rootLayout = findViewById(R.id.rootLayout);
        rootLayout.setBackgroundColor(Color.WHITE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            digitalClock = (TextClock)findViewById(R.id.digitalClock);
        } else {
            digitalClock = (DigitalClock)findViewById(R.id.digitalClock);
        }
        detector = new GestureDetector(this, new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction) {
                if(direction == Direction.UP) {
                    toggleBackgroundAndFont();
                } else if (direction == Direction.DOWN) {
                    toggleBackgroundAndFont();
                } else if (direction == Direction.LEFT) {

                } else if (direction == Direction.RIGHT) {

                }
                return true;
            }
        });
        rootLayout.setOnTouchListener(this);
    }

    private void hideActionBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Never show Action Bar if status bar is hidden
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }


    /**
     * https://stackoverflow.com/a/26387629/3148856
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_UP:
                //toggleBackgroundAndFont();
                return true;
            case MotionEvent.ACTION_DOWN:
                toggleBackgroundAndFont();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void toggleBackgroundAndFont() {
        ColorDrawable colorDrawable = (ColorDrawable) rootLayout.getBackground();
        if(colorDrawable.getColor() == Color.WHITE) {
            rootLayout.setBackgroundColor(Color.BLACK);
        } else {
            rootLayout.setBackgroundColor(Color.WHITE);
        }
        int color = digitalClock.getCurrentTextColor();
        if(color == Color.WHITE) {
            digitalClock.setTextColor(Color.BLACK);
        } else {
            digitalClock.setTextColor(Color.WHITE);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }


}
