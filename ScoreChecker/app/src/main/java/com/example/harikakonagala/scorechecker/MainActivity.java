package com.example.harikakonagala.scorechecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView score_a, score_b;
    Button two_a, three_a, free_a;
    Button two_b, three_b, free_b;
    Button reset;

    int scoreTeamA =0;
    int scoreTeamB =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score_a = (TextView) findViewById(R.id.tv_score_a);
        score_b = (TextView) findViewById(R.id.tv_score_b);
        two_a =  (Button) findViewById(R.id.bt_twopointer_a);
        two_b =  (Button) findViewById(R.id.bt_twopointer_b);
        three_a = (Button) findViewById(R.id.bt_threepointer_a);
        three_b = (Button) findViewById(R.id.bt_threepointer_b);
        free_a = (Button) findViewById(R.id.bt_free_a);
        free_b = (Button) findViewById(R.id.bt_free_b);
        reset = (Button) findViewById(R.id.bt_reset);
    }

    @Override
    protected void onResume() {
        super.onResume();
        two_a.setOnClickListener(this);
        two_b.setOnClickListener(this);
        three_a.setOnClickListener(this);
        three_b.setOnClickListener(this);
        free_a.setOnClickListener(this);
        free_b.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        two_a.setOnClickListener(null);
        two_b.setOnClickListener(null);
        three_a.setOnClickListener(null);
        three_b.setOnClickListener(null);
        free_a.setOnClickListener(null);
        free_b.setOnClickListener(null);
        reset.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_threepointer_a:
                scoreTeamA = scoreTeamA + 3;
                score_a.setText(scoreTeamA);
                break;
            case R.id.bt_threepointer_b:
                scoreTeamB = scoreTeamB + 3;
                score_b.setText(scoreTeamB);
                break;
            case R.id.bt_twopointer_a:
                scoreTeamA = scoreTeamA + 2;
                score_a.setText(scoreTeamA);
                break;
            case R.id.bt_twopointer_b:
                scoreTeamB = scoreTeamB + 2;
                score_b.setText(scoreTeamB);
                break;
            case R.id.bt_free_a:
                scoreTeamA = scoreTeamA + 1;
                score_a.setText(scoreTeamA);
                break;
            case R.id.bt_free_b:
                scoreTeamB = scoreTeamB + 1;
                score_b.setText(scoreTeamB);
                break;
            case R.id.bt_reset:
                scoreTeamA =0;
                scoreTeamB =0;
                score_a.setText(scoreTeamA);
                score_b.setText(scoreTeamB);
                break;

        }

    }
}
