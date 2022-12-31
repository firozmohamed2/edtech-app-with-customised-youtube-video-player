package com.learn.ytbplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, videoPageActivity.class));
                finish();
            }
        }, secondsDelayed * 3000);
    }
}
