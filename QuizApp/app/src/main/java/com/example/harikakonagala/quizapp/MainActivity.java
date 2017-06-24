package com.example.harikakonagala.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name;
    Button quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.et_username);
        quiz = (Button) findViewById(R.id.bt_homecontinue);
        name.getText().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        quiz.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        quiz.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this , Question1.class);
        i.putExtra("username", String.valueOf(name));
        startActivity(i);
    }
}
