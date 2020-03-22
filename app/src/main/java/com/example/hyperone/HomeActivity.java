package com.example.hyperone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hyperone.databinding.ActivityLoginBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    SpalshViewModle spalshViewModle;
    ArrayList<User> arrayitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView imvQrCode = findViewById(R.id.imageView2);
        Button button=findViewById(R.id.button);

        arrayitems = new ArrayList<User>();
        DBmovie db = new DBmovie(getApplicationContext());
        arrayitems = db.select();

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
            }
        });


    }


    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.DATA_MATRIX.QR_CODE,
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


    public void exportEmailInCSV()  {

        arrayitems = new ArrayList<User>();
        DBmovie db = new DBmovie(getApplicationContext());
        arrayitems = db.select();

        String fileName = "hyperone.csv";

            File file = new File(HomeActivity.this.getFilesDir(), "text");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                File gpxfile = new File(file, "sample.csv");
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(arrayitems.get(0).getName().toString()+""+arrayitems.get(0).getAddress().toString());
                writer.flush();
                writer.close();
                Toast.makeText(HomeActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
            } catch (Exception e) { }

    }
}
