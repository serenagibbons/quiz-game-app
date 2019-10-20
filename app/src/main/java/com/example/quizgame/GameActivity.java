package com.example.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private Button answer1, answer2, answer3, answer4;
    private TextView quesNumber, question;

    private int num = 1;
    private int score = 0;
    private String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        answer1 = findViewById(R.id.btnAns1);
        answer2 = findViewById(R.id.btnAns2);
        answer3 = findViewById(R.id.btnAns3);
        answer4 = findViewById(R.id.btnAns4);

        quesNumber= findViewById(R.id.txtNum);
        question = findViewById(R.id.txtQues);

        nextQuestion(num);


        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer1.getText().toString().equals(answer)) {
                    score++;
                }
                if (num < questions.length) {
                    nextQuestion(++num);
                }
                else {
                    gameOver();
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer2.getText().toString().equals(answer)) {
                    score++;
                }
                if (num < questions.length) {
                    nextQuestion(++num);
                }
                else {
                    gameOver();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer3.getText().toString().equals(answer)) {
                    score++;
                }
                if (num < questions.length) {
                    nextQuestion(++num);
                }
                else {
                    gameOver();
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer4.getText().toString().equals(answer)) {
                    score++;
                }
                if (num < questions.length) {
                    nextQuestion(++num);
                }
                else {
                    gameOver();
                }
            }
        });
    }

    private String[] questions = {
            "How many times can you subtract 10 from 100?",
            "In the final stretch of a race, you run by the person who is in second place, what place are you in?"
    };

    private String [][] answerChoices = {
            {"10 times", "9 times", "once", "infinite times"},
            {"first", "second", "third", "fourth"}
    };

    private String[] correctAnswers = {
            "once", "second"
    };

    public void nextQuestion(int n) {

        quesNumber.setText(String.format(getResources().getString(R.string.question_number), n));
        question.setText(questions[n-1]);
        answer1.setText((answerChoices[n-1][0]));
        answer2.setText((answerChoices[n-1][1]));
        answer3.setText((answerChoices[n-1][2]));
        answer4.setText((answerChoices[n-1][3]));

        answer = correctAnswers[n-1];
    }

    public void gameOver() {
        AlertDialog.Builder adb = new AlertDialog.Builder(GameActivity.this);
        adb.setMessage("Game over! Your score is " + score + ".")
                .setCancelable(false)
                .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), GameActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
        AlertDialog alert = adb.create();
        alert.show();
    }
}
