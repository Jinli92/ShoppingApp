package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jinliyu.shoppingapp_1.R;

public class SplashActivity extends AppCompatActivity {
ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent itnt = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(itnt);
                finish();
            }
        }, 3000);
    }
}
