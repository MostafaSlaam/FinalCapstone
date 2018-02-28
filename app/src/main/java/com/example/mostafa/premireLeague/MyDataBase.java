package com.example.mostafa.premireLeague;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mostafa on 2/17/2018.
 */

public class MyDataBase extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "User.DB";
    private static final int DATABASE_VERSION = 2;


    public MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CreateMyTable = "create table " + DataBaseContract.DataBaseEntry.TABLE_name + "(" +
                DataBaseContract.DataBaseEntry._ID + " integer primary key ," +
                DataBaseContract.DataBaseEntry.COLUMN_id + " text not null," +
                DataBaseContract.DataBaseEntry.COLUMN_title + " text not null," +
                DataBaseContract.DataBaseEntry.COLUMN_team + " text not null," +
                DataBaseContract.DataBaseEntry.COLUMN_image + " text not null," +
                DataBaseContract.DataBaseEntry.COLUMN_postion + " text not null" +
                "); ";
        db.execSQL(CreateMyTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.DataBaseEntry.TABLE_name);
        onCreate(sqLiteDatabase);
    }
}
