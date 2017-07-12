package com.example.harikakonagala.inventorymanager;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.harikakonagala.inventorymanager.data.ItemContract;
import com.example.harikakonagala.inventorymanager.data.ItemDbHelper;

public class InventoryCatalogActivity extends AppCompatActivity {

    private InventoryCursorAdapter inventoryCursorAdapter;
    private ItemDbHelper itemDbHelper;

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
                //TODO action
                return true;
            case R.id.action_delete_all_entries:
                //TODO action
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
