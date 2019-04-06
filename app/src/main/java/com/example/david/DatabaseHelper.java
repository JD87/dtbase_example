package com.example.david.dtbase_example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by david on 9/23/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "people_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "name";
    private static final String COL2 = "latitude";
    private static final String COL3 = "longitude";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL1 +" TEXT, " + COL2 + " FLOAT, " + COL3 + " FLOAT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, float lat, float lon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL1, item);
        contentValues.put(COL2, lat);
        contentValues.put(COL3, lon);


        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 + " = '" + newName + "' WHERE " + COL0 + " = '" + id + "'" + " AND " + COL1 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + " = '" + id + "'" + " AND " + COL1 + " = '" + name + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}