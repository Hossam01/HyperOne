package com.example.hyperone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {


    SpalshViewModle spalshViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spalshViewModle= ViewModelProviders.of(this).get(SpalshViewModle.class);
        spalshViewModle.getRecipe(getApplicationContext());
    }
}
