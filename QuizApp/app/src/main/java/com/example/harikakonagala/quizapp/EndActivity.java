package com.example.harikakonagala.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity implements View.OnClickListener{

    TextView message;
    Button home;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        message = (TextView) findViewById(R.id.tv_congrats);
        home = (Button) findViewById(R.id.bt_main);

        Intent i = getIntent();
        name = i.getExtras().getString("username");

        message.setText("Congratulations " + name + " you finished!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        home.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        home.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(EndActivity.this, MainActivity.class);
        startActivity(i);
    }
}
