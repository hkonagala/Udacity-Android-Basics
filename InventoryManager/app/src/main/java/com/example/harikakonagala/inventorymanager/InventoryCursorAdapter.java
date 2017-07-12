package com.example.harikakonagala.inventorymanager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harikakonagala.inventorymanager.data.ItemContract;

/**
 * Created by Harika Konagala on 7/11/2017.
 */

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView list_pname = (TextView) view.findViewById(R.id.list_product_name);
        TextView list_price = (TextView) view.findViewById(R.id.list_price);
        TextView list_quan = (TextView) view.findViewById(R.id.list_quantity);
        ImageView list_img = (ImageView) view.findViewById(R.id.listitem_image);

        String pName = cursor.getString(cursor.getColumnIndex(ItemContract.InventoryEntry.COLUMN_PRODUCT_NAME));
        String price = cursor.getString(cursor.getColumnIndex(ItemContract.InventoryEntry.COLUMN_PRICE));
        String quant = cursor.getString(cursor.getColumnIndex(ItemContract.InventoryEntry.COLUMN_QUANTITY));
        int img_thumbnail = cursor.getColumnIndex(ItemContract.InventoryEntry.COLUMN_IMAGE);
        Uri uri = Uri.parse(cursor.getString(img_thumbnail));


        list_pname.setText(pName);
        list_price.setText(price);
        list_quan.setText(quant);
        //list_img.setImageURI();
        list_img.setImageURI(uri);
        //TODO store images in correct format

    }
}
