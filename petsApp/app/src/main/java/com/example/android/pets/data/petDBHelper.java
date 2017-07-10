package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by Harika Konagala on 7/8/2017.
 */

public class petDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;

    public petDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + petContract.petEntry.TABLE_NAME + "("
                + petContract.petEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + petContract.petEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + petContract.petEntry.COLUMN_PET_BREED + " TEXT, "
                + petContract.petEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + petContract.petEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
