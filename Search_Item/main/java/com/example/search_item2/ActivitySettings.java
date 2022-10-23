package com.example.search_item2;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySettings extends AppCompatActivity {
    Activity myActivity;
    public ActivitySettings(Activity myActivity){this.myActivity=myActivity;}
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            myActivity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            ((AppCompatActivity)myActivity).getSupportActionBar().hide();
            myActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
