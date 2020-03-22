package com.example.hyperone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class SpalshViewModle extends ViewModel {


    public void getRecipe(final Context mContext){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(mContext,LoginActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mainIntent);

            }
        }, 2000);
    }


    public void pushData(String name,String email,String password,String address){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getInstance().getReference().child("result");
        myRef.keepSynced(true);
        User item = new User(name, email, password, address);
        myRef.push().setValue(item);

    }

    public void validation(String email, final String pass,final Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getInstance().getReference();
        Query query = myRef.child("result").orderByChild("email").equalTo(email.toString().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        User usersBean = user.getValue(User.class);
                        if (usersBean.password.equals(pass.toString().trim())) {
                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            DBmovie db = new DBmovie(context);
                            db.insert(usersBean.getName().toString(),usersBean.getEmail().toString(),usersBean.getPassword().toString(),usersBean.getAddress().toString());

                        } else {

                            Toast.makeText(context, "Password is wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void movescreen(final Context mContext){
                Intent mainIntent = new Intent(mContext,LoginActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mainIntent);

    }

    public void movescreen2(final Context mContext){
        Intent mainIntent = new Intent(mContext,SignUpActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mainIntent);

    }





}
