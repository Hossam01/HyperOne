package com.example.hyperone.View;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperone.Adapter.MyRecyclerViewAdapter;
import com.example.hyperone.Data.DBmovie;
import com.example.hyperone.Model.User;
import com.example.hyperone.Model.UserData;
import com.example.hyperone.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    ArrayList<User> arrayitems;
    public TextView addresstxt;
    private StorageReference mStorageRef;
    ArrayList<UserData> arrayList;
    MyRecyclerViewAdapter adapter;
    Button add;
    LinearLayout linearLayout;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final ImageView imvQrCode = findViewById(R.id.imageView2);
        Button button=findViewById(R.id.button);
        addresstxt = findViewById(R.id.data);
        add = findViewById(R.id.add);
        editText = findViewById(R.id.textData);
        linearLayout = findViewById(R.id.layout);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        arrayitems = new ArrayList<User>();
        arrayList = new ArrayList<UserData>();
        final DBmovie db = new DBmovie(getApplicationContext());
        arrayitems = db.select();
        RecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        adapter = new MyRecyclerViewAdapter(HomeActivity.this, arrayList);
        recyclerView.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.insertUserData(editText.getText().toString(), arrayitems.get(0).getEmail());


                arrayList = db.selectUserData(arrayitems.get(0).getEmail());


                adapter.setList(arrayList);

            }
        });



        Bitmap bitmap = null;
        try {
            bitmap = textToImage(arrayitems.get(0).getName()+""+arrayitems.get(0).getAddress(), 600, 600);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        imvQrCode.setImageBitmap(bitmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportEmailInCSV();
                imvQrCode.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);

            }
        });


    }


    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,
                    width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    public void exportEmailInCSV() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        arrayitems = new ArrayList<User>();
        DBmovie db = new DBmovie(getApplicationContext());
        arrayitems = db.select();
        String path=Environment.getExternalStorageDirectory().getAbsolutePath() + "/hyperone" + ".csv";

        try {
            File file = new File(path);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
                file.mkdirs();

            }

            Log.i("D", Environment.getExternalStorageDirectory().getAbsolutePath() + "/hyperone" + ".csv");

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i <= arrayitems.size(); i++) {
                bw.write(  "Address:  "+arrayitems.get(i).getAddress()+",");
                bw.write(  "name:  "+arrayitems.get(i).getName()+",");
                bw.write(  "Email:  "+arrayitems.get(i).getEmail()+",");
                bw.write(  "Password:  "+arrayitems.get(i).getPassword()+",");

                bw.close();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
