package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.data.petContract;

/**
 * Created by Harika Konagala on 7/9/2017.
 */

public class petCursorAdapter extends CursorAdapter {

    public petCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView petName = (TextView) view.findViewById(R.id.pet_name);
        TextView petBreed = (TextView) view.findViewById(R.id.pet_breed);
        String pName = cursor.getString(cursor.getColumnIndex(petContract.petEntry.COLUMN_PET_NAME));
        String pBreed = cursor.getString(cursor.getColumnIndex(petContract.petEntry.COLUMN_PET_BREED));
        if(TextUtils.isEmpty(pBreed)){
            pBreed = context.getString(R.string.unknown_breed);
        }
        petName.setText(pName);
        petBreed.setText(pBreed);


    }
}
