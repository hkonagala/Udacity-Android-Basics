package com.example.harikakonagala.businessinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        about = (TextView) findViewById(R.id.tv_textabout);
        about.setText("At Lulus, we all come to work every day because we want you to look your best.");
    }
}
