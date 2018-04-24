package com.leocorp.bioquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {
    private TextView scoreDisplay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_layout);

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("score");

        scoreDisplay = findViewById(R.id.score_final);

        scoreDisplay.setText(String.valueOf(score));
    }

    public void onHomePressed(View view){
        Intent intent = new Intent(HighScoreActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
