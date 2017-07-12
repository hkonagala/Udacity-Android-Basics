package com.example.harikakonagala.inventorymanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.harikakonagala.inventorymanager.data.ItemDbHelper;

public class EditorActivity extends AppCompatActivity {


    private EditText p_name, s_name, s_no, price;
    private TextView quant;
    private ImageButton call, photo;
    private Button plus, minus;

    private ItemDbHelper itemDbHelper;
    private SQLiteDatabase db;
    private Uri currentItemUri;
    private boolean itemHasChanged = false;

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
                //TODO action
                return true;
            case R.id.action_delete:
                //TODO action
                return true;
            case R.id.home:
                //TODO action
                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
    }
}
