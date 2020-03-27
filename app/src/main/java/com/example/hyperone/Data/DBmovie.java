package com.example.hyperone.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hyperone.Model.User;

import java.util.ArrayList;

public class DBmovie extends SQLiteOpenHelper {
    public static final String name="movie.db";
    public static final int version=1;
    Context co;
    public DBmovie(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if NOT EXISTS movie(id INTEGER primary key,name TEXT,password TEXT,address TEXT,email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("Drop table if EXISTS movie");
        onCreate(db);
    }


    public void insert(String name,String email,String password,String address)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("address",address);
        db.insert("movie", null, cv);
    }

    public ArrayList select()
    {
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from movie",null);
        res.moveToFirst();
        while (res.isAfterLast()==false) {
            User item = new User();
            //Get the information form the cursor and initialize the item object
            item.setName(res.getString(res.getColumnIndex("name")));
            item.setAddress(res.getString(res.getColumnIndex("address")));
            item.setEmail(res.getString(res.getColumnIndex("email")));
            item.setPassword(res.getString(res.getColumnIndex("password")));
            arrayList.add(item);
            res.moveToNext();
        }
        return arrayList;
    }

    public void delete(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from movie where id="+Integer.toString(id));
    }

}
