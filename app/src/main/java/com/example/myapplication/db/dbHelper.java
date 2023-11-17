package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    public dbHelper(Context context) {
        super(context, "TODO", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
     String sql = "CREATE TABLE TODO (id integer primary key , title text,content text, date text, type text, status integer)";
     sqLiteDatabase.execSQL(sql);

     String data= "INSERT INTO TODO VALUES(1,'hoc java', 'hoc java co ban', '27/3/2023', 'binh thuong', 1)";
     sqLiteDatabase.execSQL(data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
     if(i!= i1){
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TODO");
         onCreate(sqLiteDatabase);
     }
    }
}
