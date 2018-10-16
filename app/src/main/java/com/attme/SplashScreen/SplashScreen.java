package com.attme.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.attme.HomeScreen.Home;
import com.attme.LoginScreen.LoginActivity;
import com.attme.R;
import com.attme.util.ShardPref;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new ShardPref(SplashScreen.this).getValue("isLogin", "").equals("true")) {
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}