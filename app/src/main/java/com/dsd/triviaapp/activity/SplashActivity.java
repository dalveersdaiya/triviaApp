package com.dsd.triviaapp.activity;

import android.os.Bundle;
import android.os.Handler;

import com.dsd.triviaapp.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        navigateToHome();
    }

    private void navigateToHome() {
        int splashDuration = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intentPass(SplashActivity.this, MainActivity.class);
            }
        }, splashDuration);
    }

}

