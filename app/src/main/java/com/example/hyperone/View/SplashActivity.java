package com.example.hyperone.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.hyperone.R;
import com.example.hyperone.SpalshViewModle;

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
