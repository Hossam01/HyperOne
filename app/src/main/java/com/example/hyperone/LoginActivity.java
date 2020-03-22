package com.example.hyperone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.example.hyperone.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    SpalshViewModle spalshViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityLoginBinding activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        spalshViewModle= ViewModelProviders.of(this).get(SpalshViewModle.class);

        activityLoginBinding.setViewModel(spalshViewModle);
        activityLoginBinding.setLifecycleOwner(this);

        activityLoginBinding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spalshViewModle.validation(activityLoginBinding.emailedit.getText().toString(),activityLoginBinding.passedit.getText().toString(),getApplicationContext());
            }
        });

        activityLoginBinding.signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spalshViewModle.movescreen2(getApplicationContext());
            }
        });

    }
}
