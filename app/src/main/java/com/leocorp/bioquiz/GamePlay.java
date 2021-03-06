package com.leocorp.bioquiz;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

/**
 * Created by charank on 17-04-2018.
 */

public class GamePlay extends Activity implements View.OnClickListener {
    TextView QuestionTextBox;
    DBHelper databaseHelper;
    Button option1;
    Button option2;
    Button option3;
    Button option4;

    private static String answer = null;
    private static int questionNumber = 1;
    final Handler handler = new Handler();
    int i = 0;
    ProgressBar mProgressBar;
    private TextView questionCounter;
    private TextView scoreCounter, timerInText;
    private int currentScore = 0;
    private static final long COUNTDOWN_IN_MILLIS = 20000;
    private int[] optionButtons = {R.id.option1, R.id.option2, R.id.option3, R.id.option4};
    private static String hintText = null;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private static String link;
    long time = 12000;
    private boolean hintUsed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay);

        QuestionTextBox = findViewById(R.id.questionTextBox);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        questionCounter = findViewById(R.id.question_counter);
        scoreCounter = findViewById(R.id.score_display);
        timerInText = findViewById(R.id.timer_text);

        databaseHelper = new DBHelper(this);
        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mProgressBar = findViewById(R.id.progressBar3);

        updateQuestion(questionNumber);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
    }

    public void updateQuestion(int id) {
        hintUsed = false;
        if(id==26){
            Intent intent = new Intent(GamePlay.this,HighScoreActivity.class);
            intent.putExtra("score",currentScore);
            startActivity(intent);
        }
        timeLeftInMillis = COUNTDOWN_IN_MILLIS ;
        startCountDown(COUNTDOWN_IN_MILLIS);
        questionCounter.setText(String.valueOf(questionNumber)+"/25");
        TranslateAnimation animate = new TranslateAnimation(-option1.getWidth(), 0, 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);

        option1.setBackgroundResource(R.drawable.custombutton_default);
        option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_happy_smiley, 0);
        option1.startAnimation(animate);

        TranslateAnimation animate2 = new TranslateAnimation(-option1.getWidth(), 0, 0, 0);
        animate2.setDuration(600);
        animate2.setFillAfter(true);

        option2.setBackgroundResource(R.drawable.custombutton_default);
        option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_happy_smiley, 0);
        option2.startAnimation(animate2);

        TranslateAnimation animate3 = new TranslateAnimation(-option1.getWidth(), 0, 0, 0);
        animate3.setDuration(700);
        animate3.setFillAfter(true);

        option3.setBackgroundResource(R.drawable.custombutton_default);
        option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_happy_smiley, 0);
        option3.startAnimation(animate3);

        TranslateAnimation animate4 = new TranslateAnimation(-option1.getWidth(), 0, 0, 0);
        animate4.setDuration(800);
        animate4.setFillAfter(true);

        option4.setBackgroundResource(R.drawable.custombutton_default);
        option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_happy_smiley, 0);
        option4.startAnimation(animate4);


        Cursor cursor = databaseHelper.getQuestionOptions(id);
        if (cursor.getCount() == 0) {
            QuestionTextBox.setText("Empty");
            return;
        }

        while (cursor.moveToNext()) {
            QuestionTextBox.setText(cursor.getString(0));
            option1.setText(cursor.getString(1));
            option2.setText(cursor.getString(2));
            option3.setText(cursor.getString(3));
            option4.setText(cursor.getString(4));
            answer = cursor.getString(5);
            link = cursor.getString(6);
            hintText = cursor.getString(7);
            Log.d("answer here :", answer);
        }
    }

    @Override
    public void onClick(View view) {
//        mProgressBar.setProgress(0);
        Log.d("answer here :", answer);
        switch (view.getId()) {
            case R.id.option1:
                countDownTimer.cancel();
                if (answer.equals(option1.getText())) {
                    option1.setBackgroundResource(R.drawable.custombutton_success);
                    option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
                    showNextQuestion();
                    updateScore(true);
                } else {
                    option1.setBackgroundResource(R.drawable.custombutton_wrong);
                    option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_sad_smiley, 0);
                    showAnswer();
                }
                break;
            case R.id.option2:
                countDownTimer.cancel();
                if (answer.equals(option2.getText())) {
                    option2.setBackgroundResource(R.drawable.custombutton_success);
                    option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
                    showNextQuestion();
                    updateScore(true);
                } else {
                    option2.setBackgroundResource(R.drawable.custombutton_wrong);
                    option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_sad_smiley, 0);
                    showAnswer();
                }
                break;
            case R.id.option3:
                countDownTimer.cancel();
                if (answer.equals(option3.getText())) {
                    option3.setBackgroundResource(R.drawable.custombutton_success);
                    option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
                    showNextQuestion();
                    updateScore(true);
                } else {
                    option3.setBackgroundResource(R.drawable.custombutton_wrong);
                    option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_sad_smiley, 0);
                    showAnswer();
                }
                break;
            case R.id.option4:
                countDownTimer.cancel();
                if (answer.equals(option4.getText())) {
                    option4.setBackgroundResource(R.drawable.custombutton_success);
                    option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
                    showNextQuestion();
                    showAnswer();
                    updateScore(true);
                } else {
                    option4.setBackgroundResource(R.drawable.custombutton_wrong);
                    option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_sad_smiley, 0);
                    showAnswer();
                }
                break;
        }
    }

    private void updateScore(boolean ansCorrect) {
        if(ansCorrect&&!hintUsed)
            currentScore+=10;
        if(ansCorrect&&hintUsed)
            currentScore+=5;
        scoreCounter.setText(String.valueOf(currentScore));
    }

    private void showNextQuestion() {
        questionNumber++;
        final int question_number = questionNumber;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateQuestion(question_number);
            }
        }, 600);
    }

    private void startCountDown(long timeStartFrom) {
        countDownTimer = new CountDownTimer(timeStartFrom,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                time = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateTimer();
                showAnswer();
            }
        }.start();
    }

    public void showHint(View view){
        hintUsed = true;
        timerPause();
        AlertDialog alertDialog = new AlertDialog.Builder(GamePlay.this).create();
        alertDialog.setTitle("Hint!");
        alertDialog.setMessage(hintText);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        timerResume();
                    }
                });
        alertDialog.show();
    }

    private void timerResume() {
        startCountDown(timeLeftInMillis);
    }

    private void timerPause() {
        countDownTimer.cancel();
    }

    private void updateTimer() {
        int progress = 100;
        timerInText.setTextColor(Color.WHITE);
        mProgressBar.setProgress(100);
        int seconds = (int) (timeLeftInMillis/1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(),"%02d",seconds);
        if(seconds<5)
            timerInText.setTextColor(Color.RED);
        timerInText.setText(timeFormatted);

        progress = (int)(( COUNTDOWN_IN_MILLIS - timeLeftInMillis ) /(double)COUNTDOWN_IN_MILLIS * 100);
        mProgressBar.setProgress(progress);
//        mProgressBar.setProgress((int) (timeLeftInMillis/1000));
    }

    public void showAnswer() {
        if (answer.equals(option1.getText().toString().trim())) {
            option1.setBackgroundResource(R.drawable.custombutton_success);
            option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
        } else if (answer.equals(option2.getText().toString().trim())) {
            option2.setBackgroundResource(R.drawable.custombutton_success);
            option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
        } else if (answer.equals(option3.getText().toString().trim())) {
            option3.setBackgroundResource(R.drawable.custombutton_success);
            option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
        } else if (answer.equals(option4.getText().toString().trim())) {
            option4.setBackgroundResource(R.drawable.custombutton_success);
            option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
        }

        if(hintUsed) {
            currentScore -= 5;
            scoreCounter.setText(String.valueOf(currentScore));
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Title here");

        WebView wv = new WebView(this);
        wv.loadUrl(link);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                showNextQuestion();
            }
        });
        alert.show();

    }

    public void skipQuestion(View v){
        showNextQuestion();
    }

    public void homeButtonClick(View v){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Quit playing");
        alert.setMessage("Are you sure you want to quit the game? Your progress will not be saved!");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(GamePlay.this,MainActivity.class);
//                intent.putExtra("score",currentScore);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    public void setFiftyFifty(View v){
        Vector optionsVector = new Vector();

        optionsVector.addElement(option1.getText());
        optionsVector.addElement(option2.getText());
        optionsVector.addElement(option3.getText());
        optionsVector.addElement(option4.getText());

        optionsVector.remove(answer);
        Log.d("optionsVector:",answer);
        Log.d("optionsVector:",optionsVector.toString());

        Random randomGenerator = new Random();

        int firstRandomNum = randomGenerator.nextInt(2);

        optionsVector.remove(optionsVector.get(firstRandomNum));
        Log.d("optionsVector:",optionsVector.toString());

        for(int i=0;i<optionButtons.length;i++){
            int tvId = optionButtons[i];
            TextView tv = findViewById(tvId);
            tv.clearAnimation();
            String textValue = tv.getText().toString();
            Log.d("optText:",tv.getText().toString());
            String vectorValue = (String) optionsVector.get(0);
            Log.d("optVect:",(String) optionsVector.get(0));
            if(textValue.equals(vectorValue)) {
                tv.setVisibility(View.INVISIBLE);
                optionsVector.remove(0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
//        countDownTimer.cancel();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Quit playing");
        alert.setMessage("Are you sure you want to quit the game? Your progress will not be saved!");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(GamePlay.this,HighScoreActivity.class);
                intent.putExtra("score",currentScore);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }
}
