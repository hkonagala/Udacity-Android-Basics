package com.example.harikakonagala.inventorymanager;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.harikakonagala.inventorymanager.data.ItemContract;
import com.example.harikakonagala.inventorymanager.data.ItemDbHelper;

public class InventoryCatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ITEM_LOADER = 0;
    private InventoryCursorAdapter inventoryCursorAdapter;
    private ItemDbHelper itemDbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_catalog);

        ListView inventory_list = (ListView) findViewById(R.id.inventory_list);
        View emptyView = findViewById(R.id.empty_view);
        inventory_list.setEmptyView(emptyView);

        inventoryCursorAdapter = new InventoryCursorAdapter(this, null);
        inventory_list.setAdapter(inventoryCursorAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryCatalogActivity.this, EditorActivity.class);
                startActivity(i);
            }
        });

        inventory_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InventoryCatalogActivity.this, EditorActivity.class);
                Uri currentItemUri = ContentUris.withAppendedId(ItemContract.InventoryEntry.CONTENT_URI, id);
                intent.setData(currentItemUri);
                startActivity(intent);
            }
        });

        itemDbHelper = new ItemDbHelper(this);
        getLoaderManager().initLoader(ITEM_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertStock();
                return true;
            case R.id.action_delete_all_entries:
                deleteStock();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void insertStock() {
        db = itemDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ItemContract.InventoryEntry.COLUMN_PRODUCT_NAME, "cracking the coding interview");
        values.put(ItemContract.InventoryEntry.COLUMN_PRICE, 23.34);
        values.put(ItemContract.InventoryEntry.COLUMN_QUANTITY, 1);
        //TODO get image from database
        values.put(ItemContract.InventoryEntry.COLUMN_IMAGE, R.drawable.if_document_image_information_103479);

        Uri newUri = getContentResolver().insert(ItemContract.InventoryEntry.CONTENT_URI, values);
    }

    private void deleteStock() {
        int rowsDeleted = getContentResolver().delete(ItemContract.InventoryEntry.CONTENT_URI, null, null);
        if(rowsDeleted == 0){
            Toast.makeText(this, "No Item(s) to delete", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Item(s) deleted successfully!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {ItemContract.InventoryEntry.ID,
                ItemContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                ItemContract.InventoryEntry.COLUMN_PRICE,
                ItemContract.InventoryEntry.COLUMN_QUANTITY,
                ItemContract.InventoryEntry.COLUMN_IMAGE};
        return new CursorLoader(this, ItemContract.InventoryEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        inventoryCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        inventoryCursorAdapter.swapCursor(null);
    }
}
