package com.example.androiddatabase2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "USER";
    public static final int DB_VERSION = 1;

    public static String TABLE_NAME = "TBL_USER";
    public static String ID = "_id";
    public static String NAME = "name";
    public static String GENDER = "gender";
    public static String DES = "des";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ( " +
                ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT, " +
                GENDER + " TEXT, " +
                DES + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "  + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public String addUser(String user, String gender, String des) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, user);
        contentValues.put(GENDER, gender);
        contentValues.put(DES, des);
        long isAdd = db.insert(TABLE_NAME, null, contentValues);
        if (isAdd == -1) {
            return "Add Fail";
        }
        db.close();
        return "Add success";
    }

    public String updateUser(int id, String user, String gender, String des) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, user);
        contentValues.put(GENDER, gender);
        contentValues.put(DES, des);
        int isUpdate = db.update(TABLE_NAME, contentValues, ID + " = ? ", new String[] {id + ""});
        if (isUpdate > 0) {
            return "Update success";
        }
        db.close();
        return "Update Fail";
    }

    public String deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isDelete = db.delete(TABLE_NAME, ID + " = ? ", new String[] {id + ""});
        if (isDelete > 0) {
            return "Delete success";
        }
        db.close();
        return "Detele fail";
    }

    public Cursor getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROm " + TABLE_NAME;
        return db.rawQuery(sql, null);
    }
}
