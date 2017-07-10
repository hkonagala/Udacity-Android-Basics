/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.petContract;
import com.example.android.pets.data.petDBHelper;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.android.pets.data.petContract.petEntry.TABLE_NAME;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private petDBHelper petHelper;
    private SQLiteDatabase db;
    private petCursorAdapter petAdapter;
    private static final int PET_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Find ListView to populate

        ListView lvItems = (ListView) findViewById(R.id.pets_list);
        View emptyView = findViewById(R.id.empty_view);
        lvItems.setEmptyView(emptyView);

        petAdapter = new petCursorAdapter(this, null);
        lvItems.setAdapter(petAdapter);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(petContract.petEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });

        petHelper = new petDBHelper(this);
        getLoaderManager().initLoader(PET_LOADER, null, this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertPet() {

        db = petHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(petContract.petEntry.COLUMN_PET_NAME, "Toto");
        values.put(petContract.petEntry.COLUMN_PET_BREED, "Terrier");
        values.put(petContract.petEntry.COLUMN_PET_GENDER, petContract.petEntry.GENDER_MALE);
        values.put(petContract.petEntry.COLUMN_PET_WEIGHT, 7);

        Uri newUri = getContentResolver().insert(petContract.petEntry.CONTENT_URI, values);

    }

    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(petContract.petEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
        if (rowsDeleted == 0) {
            // If no rows were deleted, then
            Toast.makeText(this, getString(R.string.editor_no_pet),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the delete was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_all_pets_deleted),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {petContract.petEntry._ID,
                petContract.petEntry.COLUMN_PET_NAME,
                petContract.petEntry.COLUMN_PET_BREED};

        return new CursorLoader(this, petContract.petEntry.CONTENT_URI, projection, null, null, null);


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        petAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        petAdapter.swapCursor(null);
    }
}
