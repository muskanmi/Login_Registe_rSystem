package com.example.loginregistersystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME= "UserDatabase";
    public static final String TABLE_NAME= "UserTable";
    public static final String TABLE_Column_ID= "id";
    public static final String TABLE_Column_1_Name= "name";
    public static final String TABLE_Column_2_Email= "email";
    public static final String TABLE_Column_3_Password= "password";
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+TABLE_Column_ID+" INTEGER PRIMARY KEY, "+TABLE_Column_1_Name+" VARCHAR, "+TABLE_Column_2_Email+" VARCHAR, "+TABLE_Column_3_Password+" VARCHAR)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
}
