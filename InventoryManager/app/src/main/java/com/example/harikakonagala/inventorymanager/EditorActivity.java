package com.example.harikakonagala.inventorymanager;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harikakonagala.inventorymanager.data.ItemContract;
import com.example.harikakonagala.inventorymanager.data.ItemDbHelper;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    private EditText p_name, s_name, s_no, price;
    private TextView quant;
    private ImageButton call, photo;
    private Button plus, minus;

    private ItemDbHelper itemDbHelper;
    private SQLiteDatabase db;
    private Uri currentItemUri;
    private boolean itemHasChanged = false;
    private static final int EXISTING_ITEM_LOADER =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        currentItemUri = intent.getData();

        if(currentItemUri == null){

            setTitle("Add Item");
        }else {
            setTitle("Edit Item");
            getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);
        }

        p_name = (EditText) findViewById(R.id.edit_product_name);
        s_name = (EditText) findViewById(R.id.edit_supplier_name);
        s_no = (EditText) findViewById(R.id.edit_supplier_contact);
        price = (EditText) findViewById(R.id.edit_product_price);
        quant = (TextView) findViewById(R.id.edit_quantity);
        call = (ImageButton) findViewById(R.id.call_button);
        photo = (ImageButton) findViewById(R.id.image_button);
        plus = (Button) findViewById(R.id.button_plus);
        minus = (Button) findViewById(R.id.button_minus);


        p_name.setOnTouchListener(mTouchListener);
        s_name.setOnTouchListener(mTouchListener);
        s_no.setOnTouchListener(mTouchListener);
        price.setOnTouchListener(mTouchListener);
        quant.setOnTouchListener(mTouchListener);
        call.setOnTouchListener(mTouchListener);
        photo.setOnTouchListener(mTouchListener);
        plus.setOnTouchListener(mTouchListener);
        minus.setOnTouchListener(mTouchListener);
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            itemHasChanged = true;
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveItem();
                finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case R.id.home:
                if (!itemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deletePet();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private void saveItem() {
        String product_name = p_name.getText().toString().trim();
        String supplier_name = s_name.getText().toString().trim();
        String supplier_contact = s_no.getText().toString().trim();
        String product_price = price.getText().toString().trim();
        String product_quant = quant.getText().toString().trim();
        //TODO add image

        if( currentItemUri == null && TextUtils.isEmpty(product_name) && TextUtils.isEmpty(supplier_name)
                && TextUtils.isEmpty(supplier_contact)){
            return;
        }

        int item_price = 0;
        int item_quant = 1;

        try {
            if (!TextUtils.isEmpty(product_price) && (!TextUtils.isEmpty(product_quant))) {
                item_price = Integer.parseInt(product_price);
                item_quant = Integer.parseInt(product_quant);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        itemDbHelper = new ItemDbHelper(this);
        db = itemDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ItemContract.InventoryEntry.COLUMN_PRODUCT_NAME, product_name);
        values.put(ItemContract.InventoryEntry.COLUMN_SUPPLIER_NAME, supplier_name);
        values.put(ItemContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, supplier_contact);
        values.put(ItemContract.InventoryEntry.COLUMN_PRICE, product_price);
        values.put(ItemContract.InventoryEntry.COLUMN_QUANTITY, product_quant);

        if(currentItemUri == null){
            Uri newUri = getContentResolver().insert(ItemContract.InventoryEntry.CONTENT_URI, values);

            if (newUri == null)
                Toast.makeText(this, getString(R.string.editor_insert_item_failed),
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, getString(R.string.editor_insert_item_successful),
                        Toast.LENGTH_SHORT).show();


        }else {
            int rowsUpdated = getContentResolver().update(currentItemUri, values, null, null);

            if(rowsUpdated == 0){
                Toast.makeText(this, getString(R.string.editor_update_item_failed),
                        Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(this, getString(R.string.editor_update_item_successful),
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void deletePet() {

        if(currentItemUri !=null){
            int rowsDeleted = getContentResolver().delete(currentItemUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_item_successful),
                        Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //hide the delete button for new item
        if(currentItemUri == null){
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!itemHasChanged){
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
