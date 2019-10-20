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

        // button handlers
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAnswer(answer1.getText().toString());
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAnswer(answer2.getText().toString());
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAnswer(answer3.getText().toString());
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAnswer(answer4.getText().toString());
            }
        });
    }

    // question strings
    private String[] questions = {
            "How many times can you subtract 10 from 100?",
            "In the final stretch of a race, you run by the person who is in second place, what place are you in?",
            "How many of each kind of animal did Moses take on the ark?",
            "On average, how many birthdays does a man have?",
            "If you write all the numbers from 300 to 400, how many times you have to write the number 3?"
    };

    // button answer choices
    private String [][] answerChoices = {
            {"10 times", "9 times", "once", "infinite times"},
            {"first", "second", "third", "fourth"},
            {"0", "1", "2", "3"},
            {"72", "50", "1", "65"},
            {"100", "110", "119", "120"}
    };

    // correct answer strings
    private String[] correctAnswers = {
            "once", "second", "0", "1", "120"
    };

    // go to the next question
    public void nextQuestion(int n) {
        // update question number and text
        quesNumber.setText(String.format(getResources().getString(R.string.question_number), n));
        question.setText(questions[n-1]);
        // update answer buttons
        answer1.setText((answerChoices[n-1][0]));
        answer2.setText((answerChoices[n-1][1]));
        answer3.setText((answerChoices[n-1][2]));
        answer4.setText((answerChoices[n-1][3]));
        // update correct answer
        answer = correctAnswers[n-1];
    }

    public void confirmAnswer(final String selection) {
        AlertDialog.Builder adb = new AlertDialog.Builder(GameActivity.this);
        adb.setMessage("You selected " + selection + ". Is this correct?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // if the user selects the correct answer, increment score
                        if (selection.equals(answer)) {
                            score++;
                        }
                        // if there are more questions, go to next question
                        if (num < questions.length) {
                            nextQuestion(++num);
                        }
                        // else game is over, show score
                        else {
                            gameOver();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing (return)
                    }
                });
        AlertDialog alert = adb.create();
        alert.show();
    }

    public void gameOver() {
        AlertDialog.Builder adb = new AlertDialog.Builder(GameActivity.this);
        // show user score
        adb.setMessage("Game over! Your score is " + score + "/" + questions.length + ".")
                .setCancelable(false)
                // allow user to play game again, restart GameActivity
                .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), GameActivity.class));
                    }
                })
                // exit game, return to MainActivity
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
