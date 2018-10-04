package com.example.davee.footyappv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);



        Thread mThread = new Thread() {
            public void run() {
                try {
                    for (int i = 100; i < 105; i++) {
                        Thread.sleep(1000);
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    Intent mIntent = new Intent(SplashScreen.this, RegisterActivity.class);
                    startActivity(mIntent);
                    finish();
                }

            }
        };
        mThread.start();
    }
}
