package com.example.hyperone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.hyperone.Data.DBmovie;
import com.example.hyperone.Model.User;
import com.example.hyperone.View.HomeActivity;
import com.example.hyperone.View.LoginActivity;
import com.example.hyperone.View.SignUpActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.util.Patterns.EMAIL_ADDRESS;


public class SpalshViewModle extends ViewModel {


    public void getRecipe(final Context mContext){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(mContext, LoginActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mainIntent);

            }
        }, 2000);
    }


    public void pushData(String name,String email,String password,String address,Context mContext){

        final ProgressDialog progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        if(email.isEmpty()) {
            Toast.makeText(mContext, "Please enter Email/Username", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
        else if(!EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(mContext, "Please enter valid email", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
            }
        else if (name.isEmpty()) {
                    Toast.makeText(mContext, "Please enter your name", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
                }
        else if (address.isEmpty()) {
                        Toast.makeText(mContext, "Please enter your address", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
                    }
        else if(password.isEmpty()) {
                            Toast.makeText(mContext, "Please enter Password", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
                        }
        else if(password.length()<6) {
                                Toast.makeText(mContext, "Password must contain 6  characters", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
                            }
        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getInstance().getReference().child("result");
            myRef.keepSynced(true);
            User item = new User(name, email, password, address);
            myRef.push().setValue(item);
            progressdialog.dismiss();
        }
    }

    public void validation(String email, final String pass,final Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getInstance().getReference();

        final ProgressDialog progressdialog = new ProgressDialog(context);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();

        if(email.isEmpty())
            Toast.makeText(context,"Please enter Email/Username",Toast.LENGTH_SHORT).show();
        else if(!EMAIL_ADDRESS.matcher(email).matches())
            Toast.makeText(context,"Please enter valid email",Toast.LENGTH_SHORT).show();
        else {
            Query query = myRef.child("result").orderByChild("email").equalTo(email.toString().trim());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.exists()) {
                        if(pass.toString().trim().isEmpty())
                            Toast.makeText(context,"Please enter Password",Toast.LENGTH_SHORT).show();
                        else if((pass.toString().trim().length()<6))
                        Toast.makeText(context,"Password must contain 6  characters",Toast.LENGTH_SHORT).show();
                        else {
                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                User usersBean = user.getValue(User.class);
                                if (usersBean.password.equals(pass.toString().trim())) {
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    DBmovie db = new DBmovie(context);
                                    db.insert(usersBean.getName().toString(), usersBean.getEmail().toString(), usersBean.getPassword().toString(), usersBean.getAddress().toString());
                                    progressdialog.dismiss();

                                } else {
                                    progressdialog.dismiss();
                                    Toast.makeText(context, "Password is wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else {
                        progressdialog.dismiss();
                        Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressdialog.dismiss();
                }
            });


        }
    }

    public void movescreen(final Context mContext){
                Intent mainIntent = new Intent(mContext,LoginActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mainIntent);

    }

    public void movescreen2(final Context mContext){
        Intent mainIntent = new Intent(mContext, SignUpActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mainIntent);

    }





}
