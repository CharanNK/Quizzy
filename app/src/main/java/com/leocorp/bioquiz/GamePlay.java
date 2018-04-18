package com.leocorp.bioquiz;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

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
    private int colorGreen;
    private int colorRed;
    private int buttonBackground;
    private Animation animSlideLeftRight;
    private Animation animSlideRightLeft;
    private int[] optionButtons = {R.id.option1, R.id.option2, R.id.option3, R.id.option4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay);

        QuestionTextBox = findViewById(R.id.questionTextBox);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        colorGreen = Color.parseColor("#FF64DD");
        colorRed = Color.parseColor("#FFD500");
        buttonBackground = Color.parseColor("#03A9F4");

        databaseHelper = new DBHelper(this);
        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateQuestion(questionNumber);

        animSlideLeftRight = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation_enter);
        animSlideRightLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation_leave);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
    }

    public void updateQuestion(int id) {
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
            Log.d("answer here :", answer);
        }
    }

    @Override
    public void onClick(View view) {
        Log.d("answer here :", answer);
        switch (view.getId()) {
            case R.id.option1:
                if (answer.equals("A")) {
                    option1.setBackgroundResource(R.drawable.custombutton_success);
                    option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
                    questionNumber++;
                    showNextQuestion(questionNumber);
                } else {
                    option1.setBackgroundResource(R.drawable.custombutton_wrong);
                    option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_sad_smiley, 0);
                    showAnswer();
                }
                break;
            case R.id.option2:
                if (answer.equals("B")) {
                    option2.setBackgroundResource(R.drawable.custombutton_success);
                    option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
                    questionNumber++;
                    showNextQuestion(questionNumber);
                } else {
                    option2.setBackgroundResource(R.drawable.custombutton_wrong);
                    option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_sad_smiley, 0);
                    showAnswer();
                }
                break;
            case R.id.option3:
                if (answer.equals("C")) {
                    option3.setBackgroundResource(R.drawable.custombutton_success);
                    option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
                    questionNumber++;
                    showNextQuestion(questionNumber);
                } else {
                    option3.setBackgroundResource(R.drawable.custombutton_wrong);
                    option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_sad_smiley, 0);
                    showAnswer();
                }
                break;
            case R.id.option4:
                if (answer.equals("D")) {
                    option4.setBackgroundResource(R.drawable.custombutton_success);
                    option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
                    questionNumber++;
                    showNextQuestion(questionNumber);
                    showAnswer();
                } else {
                    option4.setBackgroundResource(R.drawable.custombutton_wrong);
                    option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_sad_smiley, 0);
                    showAnswer();
                }
                break;
        }
    }

    private void showNextQuestion(int questionNumber) {
        final int question_number = questionNumber;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateQuestion(question_number);
            }
        },600);
    }

    public void showAnswer() {
        questionNumber++;
        if (answer.equals("A")) {
            option1.setBackgroundResource(R.drawable.custombutton_success);
            option1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
        } else if (answer.equals("B")) {
            option2.setBackgroundResource(R.drawable.custombutton_success);
            option2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
        } else if (answer.equals("C")) {
            option3.setBackgroundResource(R.drawable.custombutton_success);
            option3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
        } else if (answer.equals("D")) {
            option4.setBackgroundResource(R.drawable.custombutton_success);
            option4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.green_happy_smiley, 0);
        }

        showNextQuestion(questionNumber);
    }
}
