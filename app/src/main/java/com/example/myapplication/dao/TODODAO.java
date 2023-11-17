package com.example.myapplication.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.db.dbHelper;
import com.example.myapplication.model.TODO;

import java.util.ArrayList;

public class TODODAO {
    dbHelper dbHelper;
    SQLiteDatabase db;
    public TODODAO(Context context) {
        dbHelper= new dbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(TODO todo){
        ContentValues values = new ContentValues();
        values.put("title", todo.getTitle());
        values.put("content", todo.getContent());
        values.put("date", todo.getDate());
        values.put("type", todo.getType());
        values.put("status", todo.getStatus());
        return db.insert("TODO",null, values);
    }
    public long update(TODO todo){
        ContentValues values = new ContentValues();
        values.put("title", todo.getTitle());
        values.put("content", todo.getContent());
        values.put("date", todo.getDate());
        values.put("type", todo.getType());
        values.put("status", todo.getStatus());
        return db.update("TODO", values, "id=?", new String[]{String.valueOf(todo.getId())});

    }
    public long delete(int id){
        return db.delete("TODO", "id=?", new String[]{String.valueOf(id)});
    }
    public ArrayList<TODO> getAll(){
        String sql = "select * from TODO";
        return getData(sql);
    }
    @SuppressLint("Range")
    public ArrayList<TODO> getData(String sql, String...selectionArgs){
        ArrayList<TODO> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            TODO obj = new TODO();
            obj.setId(Integer.parseInt(c.getString(c.getColumnIndex("id"))));
            obj.setTitle(c.getString(c.getColumnIndex("title")));
            obj.setContent(c.getString(c.getColumnIndex("content")));
            obj.setDate(c.getString(c.getColumnIndex("date")));
            obj.setType(c.getString(c.getColumnIndex("type")));
            obj.setStatus(Integer.parseInt(c.getString(c.getColumnIndex("status"))));
            list.add(obj);
        }
        return list;
    }
}
