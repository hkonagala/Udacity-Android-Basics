package com.example.harikakonagala.inventorymanager.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.harikakonagala.inventorymanager.data.ItemContract.InventoryEntry.CONTENT_ITEM_TYPE;
import static com.example.harikakonagala.inventorymanager.data.ItemContract.InventoryEntry.CONTENT_LIST_TYPE;
import static com.example.harikakonagala.inventorymanager.data.ItemContract.InventoryEntry.TABLE_NAME;

/**
 * Created by Harika Konagala on 7/11/2017.
 */

public class ItemProvider extends ContentProvider {

    private static final String TAG = ItemProvider.class.getSimpleName();
    private ItemDbHelper itemDbHelper;

    private static final int ITEM = 100;
    private static final int ITEM_ID = 101;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        mUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_INVENTORY, ITEM);
        mUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_INVENTORY + "/#", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        itemDbHelper = new ItemDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = itemDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = mUriMatcher.match(uri);
        switch(match){
            case ITEM:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case  ITEM_ID:
                selection = ItemContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("cannot query unknown uri " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
}

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = mUriMatcher.match(uri);
        switch (match){
            case ITEM:
                return CONTENT_LIST_TYPE;
            case ITEM_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("unknown uri " + uri + "with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
       final int match = mUriMatcher.match(uri);
        switch (match){
            case ITEM:
                return insertItem(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported" + uri);
        }
    }

    private Uri insertItem(Uri uri, ContentValues values) {
        String p_name = values.getAsString(ItemContract.InventoryEntry.COLUMN_PRODUCT_NAME);
        if (p_name == null) {
            throw new IllegalArgumentException("Product requires a name");
        }
        Integer quantity = values.getAsInteger(ItemContract.InventoryEntry.COLUMN_QUANTITY);
        if(quantity !=null && quantity <0){
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        }

        Float price = values.getAsFloat(ItemContract.InventoryEntry.COLUMN_PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("product requires valid price");
        }

        SQLiteDatabase db = itemDbHelper.getWritableDatabase();

        long rowId = db.insert(ItemContract.InventoryEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Log.i(TAG, "We inserted a record");

        return ContentUris.withAppendedId(uri, rowId);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = itemDbHelper.getWritableDatabase();

        final int match = mUriMatcher.match(uri);
        int rowsDeleted;

        switch (match){
            case ITEM:
                rowsDeleted = db.delete(ItemContract.InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = ItemContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(ItemContract.InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }

        if(rowsDeleted !=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = mUriMatcher.match(uri);
        switch (match){
            case ITEM:
                return updateItem(uri, values, selection, selectionArgs);
            case ITEM_ID:
                selection = ItemContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateItem(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateItem(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if(values.containsKey(ItemContract.InventoryEntry.COLUMN_PRODUCT_NAME)){
            String name = values.getAsString(ItemContract.InventoryEntry.COLUMN_PRODUCT_NAME);
            if(name == null){
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        if (values.containsKey(ItemContract.InventoryEntry.COLUMN_QUANTITY)) {
            Integer quantity = values.getAsInteger(ItemContract.InventoryEntry.COLUMN_QUANTITY);
            if (quantity == null || quantity <0 ) {
                throw new IllegalArgumentException("Quantity cannot be invalid");
            }
        }
        if (values.containsKey(ItemContract.InventoryEntry.COLUMN_PRICE)) {
            Integer price = values.getAsInteger(ItemContract.InventoryEntry.COLUMN_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Price cannot be invalid");
            }
        }


        if(values.size() == 0){
            return 0;
        }

        SQLiteDatabase db = itemDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(ItemContract.InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
}
