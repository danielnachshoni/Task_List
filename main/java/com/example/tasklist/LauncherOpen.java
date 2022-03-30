package com.example.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;



public class LauncherOpen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_open);
        getSupportActionBar().hide();

    final Intent intentLauncher= new Intent(LauncherOpen.this,MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intentLauncher);
                finish();
            }
        }, 3000);
    }
};
