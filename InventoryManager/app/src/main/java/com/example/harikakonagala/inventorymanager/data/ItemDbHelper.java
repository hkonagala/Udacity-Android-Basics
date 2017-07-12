package com.example.harikakonagala.inventorymanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by Harika Konagala on 7/11/2017.
 */

public class ItemDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "stock.db";
    public static final int DATABASE_VERSION = 1;

    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_ITEMS = "CREATE TABLE " + ItemContract.InventoryEntry.TABLE_NAME + "("
                + ItemContract.InventoryEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemContract.InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ItemContract.InventoryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ItemContract.InventoryEntry.COLUMN_PRICE + " REAL NOT NULL DEFAULT 0.0, "
                + ItemContract.InventoryEntry.COLUMN_SUPPLIER_NAME + " TEXT, "
                + ItemContract.InventoryEntry.COLUMN_SUPPLIER_PHONE + " TEXT, "
                + ItemContract.InventoryEntry.COLUMN_IMAGE + " TEXT NOT NULL DEFAULT 'no images');";

        db.execSQL(CREATE_TABLE_ITEMS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + ItemContract.InventoryEntry.TABLE_NAME);
        onCreate(db);
    }
}
