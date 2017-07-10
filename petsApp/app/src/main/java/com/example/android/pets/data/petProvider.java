package com.example.android.pets.data;

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
import android.widget.Toast;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.android.pets.data.petContract.petEntry.CONTENT_ITEM_TYPE;
import static com.example.android.pets.data.petContract.petEntry.CONTENT_LIST_TYPE;
import static com.example.android.pets.data.petContract.petEntry.TABLE_NAME;

/**
 * Created by Harika Konagala on 7/9/2017.
 */

public class petProvider extends ContentProvider {

    public static final String LOG_TAG = petProvider.class.getSimpleName();
    private petDBHelper petHelper;

    private static final int PET = 100;
    private static final int PET_ID = 101;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(petContract.CONTENT_AUTHORITY, petContract.PATH_PETS, PET);
        uriMatcher.addURI(petContract.CONTENT_AUTHORITY, petContract.PATH_PETS + "/#", PET_ID);

    }

    @Override
    public boolean onCreate() {
        petHelper = new petDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteDatabase db = petHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch(match){
            case PET:
                cursor = db.query(TABLE_NAME, strings, s, strings1, null, null, s1);
                break;
            case  PET_ID:
                s = petContract.petEntry._ID + "=?";
                strings1 = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TABLE_NAME, strings, s, strings1, null, null, s1);
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
        final int match = uriMatcher.match(uri);
        switch (match){
            case PET:
                return CONTENT_LIST_TYPE;
            case PET_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("unknown uri " + uri + "with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final int match = uriMatcher.match(uri);
        switch (match){
            case PET:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    private Uri insertPet(Uri uri, ContentValues contentValues) {
        String name = contentValues.getAsString(petContract.petEntry.COLUMN_PET_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        // Check that the gender is valid
        Integer gender = contentValues.getAsInteger(petContract.petEntry.COLUMN_PET_GENDER);
        if (gender == null || !petContract.petEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Pet requires valid gender");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer weight = contentValues.getAsInteger(petContract.petEntry.COLUMN_PET_WEIGHT);
        if (weight != null && weight < 0) {
            throw new IllegalArgumentException("Pet requires valid weight");
        }


        SQLiteDatabase db = petHelper.getWritableDatabase();

        long id = db.insert(petContract.petEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;

        }

        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {


        SQLiteDatabase db = petHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = uriMatcher.match(uri);

        switch (match){
            case PET:
                rowsDeleted = db.delete(petContract.petEntry.TABLE_NAME, s, strings);
                break;
            case PET_ID:
                s = petContract.petEntry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(petContract.petEntry.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException(" Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {getContext().getContentResolver().notifyChange(uri, null);}
        return rowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final int match = uriMatcher.match(uri);
        switch (match){
            case PET:
                return updatePet(uri, contentValues, s, strings);
            case PET_ID:
                s = petContract.petEntry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, s, strings);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues contentValues, String s, String[] strings) {

        if(contentValues.containsKey(petContract.petEntry.COLUMN_PET_NAME)){
            String name = contentValues.getAsString(petContract.petEntry.COLUMN_PET_NAME);
            if(name == null){
                throw new IllegalArgumentException("pet requires a name");
            }
        }

        if (contentValues.containsKey(petContract.petEntry.COLUMN_PET_GENDER)) {
            Integer gender = contentValues.getAsInteger(petContract.petEntry.COLUMN_PET_GENDER);
            if (gender == null || !petContract.petEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }
        if (contentValues.containsKey(petContract.petEntry.COLUMN_PET_WEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer weight = contentValues.getAsInteger(petContract.petEntry.COLUMN_PET_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }
        }


        if(contentValues.size() == 0){
            return 0;
        }

        SQLiteDatabase db = petHelper.getWritableDatabase();

        int rowsUpdated = db.update(petContract.petEntry.TABLE_NAME, contentValues, s, strings);
        if(rowsUpdated !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }


}
