package com.example.harikakonagala.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Question1 extends AppCompatActivity implements View.OnClickListener{

    TextView question;
    RadioButton ans1, ans2, ans3, ans4;
    Button next;
    String name;

    boolean correct= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        question = (TextView) findViewById(R.id.tv_q1);

        ans1 = (RadioButton) findViewById(R.id.rb_q1opt1);
        ans2 = (RadioButton) findViewById(R.id.rb_q1opt2);
        ans3 = (RadioButton) findViewById(R.id.rb_q1opt3);
        ans4 = (RadioButton) findViewById(R.id.rb_q1opt4);

        next = (Button) findViewById(R.id.bt_q1next);

        Intent i = getIntent();
        name = i.getExtras().getString("username");

        question.setText("How to pass the data between activities in Android?");

        ans1.setText("Intent");
        ans2.setText("Content Provider");
        ans3.setText("Broadcast receiver");
        ans4.setText("None of the Above");

    }

    @Override
    protected void onResume() {
        super.onResume();
        next.setOnClickListener(this);

       if(ans1.isChecked()){
           correct = true;
           Toast.makeText(getApplicationContext(), "Correct Answer! Click Next", Toast.LENGTH_LONG).show();
       }
       else {
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

        if(correct == true){

            Intent intent = new Intent(Question1.this, Question2.class);
            intent.putExtra("username", name);
            startActivity(intent);

        }
    }
}
