package com.example.myapplications.data;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplications.Params.Params;
import com.example.myapplications.string;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context)
    {
        super(context, Params.DB_NAME,null,4);
    }

    public static final  String a="favs";
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="CREATE TABLE "+Params.TABLE_NAME+"("+Params.KEY_ID+"INTEGER PRIMARY KEY,"+Params.KEY_NAME+" TEXT)";
        String creat="CREATE TABLE "+Params.TABLE_NAME1+"("+Params.KEY_ID+"INTEGER PRIMARY KEY,"+Params.KEY_NAME+" TEXT)";
        db.execSQL(create);
        db.execSQL(creat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void saveclock(string s)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_NAME,s.city);

        try{
            db.insert(Params.TABLE_NAME,null,values);
            Log.d("abc","Successfully inserted");
        }
        catch (Exception E)
        {
            Log.d("abc",E.getMessage());
        }

        db.close();
    }
    public void saveallclock(string s)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_NAME,s.city);

        try{
            db.insert(Params.TABLE_NAME1,null,values);
            Log.d("abcD","Successfully inserted");
        }
        catch (Exception E)
        {
            Log.d("abcE",E.getMessage());
        }

        db.close();
    }


    public List<string> getAllClocks(){
        List<string> clocklist=new ArrayList<>();
        SQLiteDatabase dbs=this.getReadableDatabase();
        String select="SELECT * FROM "+Params.TABLE_NAME;
        Cursor cursor= dbs.rawQuery(select, null);
         while(cursor.moveToNext())
      {
            string s=new string() ;
            s.city = cursor.getString(1);

            clocklist.add(s);
            Log.d("defg","Successfully retrieved");

        }
        cursor.close();



        return clocklist;
    }
    public List<string> GetAllClocks(){
        List<string> clocklist=new ArrayList<>();
        SQLiteDatabase dbs=this.getReadableDatabase();
        String select="SELECT * FROM "+Params.TABLE_NAME1;
        Cursor cursor= dbs.rawQuery(select, null);
        while(cursor.moveToNext())
        {
            string s=new string() ;
            s.city = cursor.getString(1);
            clocklist.add(s);
            Log.d("defg","Successfully retrieved");

        }
        cursor.close();



        return clocklist;
    }

    public void deleteclock(string s)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        try {
            db.delete(Params.TABLE_NAME, Params.KEY_NAME + "=?", new String[]{s.getName()});
            Log.d("efg","Deleted");
        }
        catch(Exception E) {
            Log.d("efg", E.getMessage());
        }
        db.close();

    }
    public void deleteclock(String s)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        try {
            db.delete(Params.TABLE_NAME, Params.KEY_NAME + "=?", new String[]{s});
            Log.d("efg","Deleted");
        }
        catch(Exception E) {
            Log.d("efg", E.getMessage());
        }
        db.close();

    }

}