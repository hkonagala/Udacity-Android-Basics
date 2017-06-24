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

public class Question3 extends AppCompatActivity implements View.OnClickListener{

    TextView question;
    RadioButton ans1, ans2, ans3, ans4;
    Button next;
    String name;

    boolean answer = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);
        question = (TextView) findViewById(R.id.tv_q1);

        ans1 = (RadioButton) findViewById(R.id.rb_q3opt1);
        ans2 = (RadioButton) findViewById(R.id.rb_q3opt2);
        ans3 = (RadioButton) findViewById(R.id.rb_q3opt3);
        ans4 = (RadioButton) findViewById(R.id.rb_q3opt4);

        next = (Button) findViewById(R.id.bt_q1next);

        Intent i = getIntent();
        name = i.getExtras().getString("username");

        question.setText("Under what condition could the code sample below crash your application?");

        ans1.setText("Multiple Applications");
        ans2.setText("No Application");
        ans3.setText("Compiler Error");
        ans4.setText("Runtime Error");

    }

    @Override
    protected void onResume() {
        super.onResume();
        next.setOnClickListener(this);

        if(ans2.isChecked()){
            answer = true;
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
        if(answer == true){
            Intent intent = new Intent(Question3.this, EndActivity.class);
            intent.putExtra("username", name);
            startActivity(intent);
        }
    }
}
