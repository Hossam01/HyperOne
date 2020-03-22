package com.example.hyperone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.example.hyperone.databinding.ActivitySignUp2Binding;
import com.google.firebase.database.DataSnapshot;

public class SignUpActivity extends AppCompatActivity {

    SpalshViewModle spalshViewModle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivitySignUp2Binding binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up2);
        spalshViewModle= ViewModelProviders.of(this).get(SpalshViewModle.class);
        binding.setViewModel(spalshViewModle);
        binding.setLifecycleOwner(this);

        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spalshViewModle.pushData(binding.nametxt.getText().toString(),binding.emailtxt.getText().toString(),binding.passtxt.getText().toString(),binding.addresstxt.getText().toString());
            }
        });

        binding.logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spalshViewModle.movescreen2(getApplicationContext());
            }
        });


    }
}
