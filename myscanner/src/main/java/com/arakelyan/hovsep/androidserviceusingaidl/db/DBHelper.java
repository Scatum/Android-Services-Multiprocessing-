

package com.arakelyan.hovsep.androidserviceusingaidl.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.arakelyan.hovsep.androidserviceusingaidl.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrara_000 on 17.04.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FILE_INFO_DB";
    private static final String TABLE_FILE_INFO = "TABLE_FILE_INFO";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "KEY_NAME";
    private static final String KEY_PATH = "KEY_PATH";
    private static final String KEY_CREATED_DATE = "KEY_CREATED_DATE";
    private static final String KEY_CLICK_COUNT = "KEY_CLICK_COUNT";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FILE_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT ,"
                + KEY_PATH + " TEXT, " + KEY_CREATED_DATE + " TEXT, " + KEY_CLICK_COUNT + " INTEGER )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILE_INFO);
        // Create tables again
        onCreate(db);
    }


    public synchronized void saveFileInfo(List<FileInfo> fileInfoList) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        for (FileInfo fileInfo: fileInfoList){
            values.put(KEY_NAME, fileInfo.getName()); // file name
            values.put(KEY_PATH, fileInfo.getPath()); // file path
            values.put(KEY_CREATED_DATE, fileInfo.getCreatedDate());
            values.put(KEY_CLICK_COUNT, fileInfo.getClickedCount());
            Log.e("Info", fileInfo.getName()+ "*");

            /**
             * I Think that it is not correct way to check record  is exist or no :(
             * */
            if (!existData(fileInfo.getName())) {
                db.insert(TABLE_FILE_INFO, null, values);
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close(); // Closing database connection
    }


    public synchronized boolean existData(String name) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_FILE_INFO + " WHERE " + KEY_NAME + " = ?", new String[]{name + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return true;
            }
            return false;
        } finally {
            cursor.close();
        }
    }


    public synchronized List<FileInfo> getAllFeeds() {
        List<FileInfo> newsFeeds = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FILE_INFO;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String path = cursor.getString(cursor.getColumnIndex(KEY_PATH));
                String date = cursor.getString(cursor.getColumnIndex(KEY_CREATED_DATE));
                String clickedCount = cursor.getString(cursor.getColumnIndex(KEY_CLICK_COUNT));
                FileInfo info = new FileInfo(id, name, path, clickedCount, date);
                newsFeeds.add(info);
            } while (cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return newsFeeds;
    }

    public synchronized int getInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FILE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public synchronized void deletAllFeeds() {
        // DELETE All Query
        String deleteQUery = "DELETE FROM " + TABLE_FILE_INFO;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQUery);
    }

    public void updateClickCount(int id, int newCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CLICK_COUNT, newCount);
        db.update(TABLE_FILE_INFO, cv, KEY_ID +
                "=" + id, null);
        db.close();
    }

}
