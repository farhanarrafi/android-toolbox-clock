package com.farhanarrafi.app.clock;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AnalogClock;
import android.widget.DigitalClock;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "MainActivity";
    private TextView digitalClock;
    private AnalogClock analogClock;
    private ViewGroup rootLayout;
    private GestureDetector detector;
    private boolean isAnalog = false;
    private boolean isBlack = false;
    private static final String LAST_CLOCK_TYPE = "LAST_CLOCK_TYPE";
    private static final String LAST_BACKGROUND_COLOR = "LAST_BACKGROUND_COLOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            isAnalog = savedInstanceState.getBoolean(LAST_CLOCK_TYPE);
            isBlack = savedInstanceState.getBoolean(LAST_BACKGROUND_COLOR);
        }

        hideActionBar();
        setContentView(R.layout.activity_main);
        rootLayout = findViewById(R.id.rootLayout);
        rootLayout.setBackgroundColor(Color.WHITE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            digitalClock = (TextClock)findViewById(R.id.digitalClock);
        } else {
            digitalClock = (DigitalClock)findViewById(R.id.digitalClock);
        }
        analogClock = findViewById(R.id.analogClock);


        OnSwipeHandler handler = new OnSwipeHandler();
        detector = new GestureDetector(this, handler);
        rootLayout.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        if(isAnalog) {
            digitalClock.setVisibility(View.GONE);
            analogClock.setVisibility(View.VISIBLE);
        } else {
            digitalClock.setVisibility(View.VISIBLE);
            analogClock.setVisibility(View.GONE);
        }
        super.onResume();
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

    private void toggleAnalogAndDigital() {
        if(digitalClock.getVisibility() == View.VISIBLE) {
            digitalClock.setVisibility(View.GONE);
            analogClock.setVisibility(View.VISIBLE);
            isAnalog = true;
        } else {
            digitalClock.setVisibility(View.VISIBLE);
            analogClock.setVisibility(View.GONE);
            isAnalog = false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    private class OnSwipeHandler extends OnSwipeListener {
        @Override
        public boolean onSwipe(Direction direction) {
            if(direction == Direction.UP) {
                toggleBackgroundAndFont();
            } else if (direction == Direction.DOWN) {
                toggleBackgroundAndFont();
            } else if (direction == Direction.LEFT) {
                toggleAnalogAndDigital();
            } else if (direction == Direction.RIGHT) {
                toggleAnalogAndDigital();
            }
            return true;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isAnalog = savedInstanceState.getBoolean(LAST_CLOCK_TYPE);
        isBlack = savedInstanceState.getBoolean(LAST_BACKGROUND_COLOR);
        if(isAnalog) {
            digitalClock.setVisibility(View.GONE);
            analogClock.setVisibility(View.VISIBLE);
        } else {
            digitalClock.setVisibility(View.VISIBLE);
            analogClock.setVisibility(View.GONE);
        }
        if(isBlack) {
            rootLayout.setBackgroundColor(Color.BLACK);
            digitalClock.setTextColor(Color.WHITE);
        } else {
            rootLayout.setBackgroundColor(Color.WHITE);
            digitalClock.setTextColor(Color.BLACK);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(LAST_CLOCK_TYPE, isAnalog);
        outState.putBoolean(LAST_BACKGROUND_COLOR, isBlack);
        super.onSaveInstanceState(outState);
    }
}
