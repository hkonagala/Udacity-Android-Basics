package com.example.harikakonagala.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Question2 extends AppCompatActivity implements View.OnClickListener{

    TextView question;
    CheckBox ans1, ans2, ans3, ans4;
    Button next;
    String name;

    boolean answer = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        question = (TextView) findViewById(R.id.tv_q1);

        ans1 = (CheckBox) findViewById(R.id.ch_opt1);
        ans2 = (CheckBox) findViewById(R.id.ch_opt2);
        ans3 = (CheckBox) findViewById(R.id.ch_opt3);
        ans4 = (CheckBox) findViewById(R.id.ch_opt4);

        next = (Button) findViewById(R.id.bt_q2next);

        Intent i = getIntent();
        name = i.getExtras().getString("username");

        question.setText("What are the main components in android?");

        ans1.setText("Activity");
        ans2.setText("Content Provider");
        ans3.setText("Broadcast receiver");
        ans4.setText("Services");

    }

    @Override
    protected void onResume() {
        super.onResume();
        next.setOnClickListener(this);


        if(ans1.isChecked() && ans2.isChecked() && ans3.isChecked() && ans4.isChecked()){
            answer = true;
            Toast.makeText(getApplicationContext(), "Correct Answer! Click Next", Toast.LENGTH_LONG).show();
        }else
        {

            Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        next.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        if(answer == true){
            Intent intent = new Intent(Question2.this, Question3.class);
            intent.putExtra("username", name);
            startActivity(intent);
        }
    }
}
