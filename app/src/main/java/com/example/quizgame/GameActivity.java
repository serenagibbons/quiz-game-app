package com.example.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class GameActivity extends AppCompatActivity {

    private Chronometer timer;
    private Button answer1, answer2, answer3, answer4, submit;
    private CheckBox check1, check2, check3, check4;
    private TextView quesNumber, question;
    private final String FILENAME = "ScoreHistory";

    private int num = 1;
    private int score = 0;
    private String answer = "";
    private String strTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        answer1 = findViewById(R.id.btnAns1);
        answer2 = findViewById(R.id.btnAns2);
        answer3 = findViewById(R.id.btnAns3);
        answer4 = findViewById(R.id.btnAns4);

        check1 = findViewById(R.id.checkbox1);
        check2 = findViewById(R.id.checkbox2);
        check3 = findViewById(R.id.checkbox3);
        check4 = findViewById(R.id.checkbox4);

        quesNumber= findViewById(R.id.txtNum);
        question = findViewById(R.id.txtQues);

        submit = findViewById(R.id.btnSubmit);

        timer = findViewById(R.id.cmTimer);

        // display questions/answers and start timer
        nextQuestion(num);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

        // timer handler
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long minutes = ((SystemClock.elapsedRealtime()-timer.getBase())/1000)/60;
                long seconds = ((SystemClock.elapsedRealtime()-timer.getBase())/1000)%60;
                strTime = minutes + ":" + seconds;
             }
        });

        // button handlers
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] s = {"","","",""};
                StringBuilder selection = new StringBuilder();

                if (check1.isChecked()) {
                    s[0] = check1.getText().toString();
                }
                if (check2.isChecked()) {
                    s[1] = check2.getText().toString();
                }
                if (check3.isChecked()) {
                    s[2] = check3.getText().toString();
                }
                if (check4.isChecked()) {
                    s[3] = check4.getText().toString();
                }

                for (int i = 0; i < 4; ++i) {
                    if (!s[i].isEmpty()) {
                        if (selection.length() == 0) {
                            selection.append(" ");
                            selection.append(s[i]);
                        }
                        else {
                            selection.append(" and ");
                            selection.append(s[i]);
                        }
                    }
                }
                AlertDialog.Builder adb = new AlertDialog.Builder(GameActivity.this);
                adb.setMessage("You selected" + selection + ". Is this correct?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // if the user selects the correct answer, increment score
                                if (!check1.isChecked() && check2.isChecked() && check3.isChecked() && !check4.isChecked()) {
                                    ++score;
                                }
                                // if there are more questions, go to next question
                                if (num < questions.length) {
                                    nextQuestion(++num);
                                }
                                // else game is over, show score and save to history
                                else {
                                    timer.stop();
                                    saveScore(score);
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
        });
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
            "A man looks at a portrait and says, " +
                    "\"Brothers and sisters, I have none.  But that man's father is my father's son.\"" +
                    "  What is the relationship of the man in the portrait to the speaker?",
            "How many times can you subtract 10 from 100?",
            "In the final stretch of a race, you run by the person who is in second place, what place are you in?",
            "How many of each kind of animal did Moses take on the ark?",
            "On average, how many birthdays does a man have?",
            "If you write all the numbers from 300 to 400, how many times do you have to write the number 3?",
            "Someone at a party introduces you to your mother's only sister's husband's sister in law. He has no brothers. What do you call this lady?",
            "The letters A, B, C, D, E, F, and G, stand for seven consecutive integers from 1 to 7 (not necessarily in that order).\n" +
                    "D is 3 less than A\n" +
                    "B is the middle term\n" +
                    "F is as much less than B as C is greater than D\n" +
                    "G is greater than F\n" +
                    "Select the true statements."
    };

    // button answer choices
    private String [][] answerChoices = {
            {"His father", "Himself", "His son", "His uncle"},
            {"10 times", "9 times", "once", "infinite times"},
            {"first", "second", "third", "fourth"},
            {"0", "1", "2", "3"},
            {"72", "50", "1", "65"},
            {"100", "110", "119", "120"},
            {"Aunt", "Mother", "Sister-in-law", "Sister"},
            {"A = 5", "A = 6", "C = 5", "F = 1"}
    };

    // correct answer strings
    private String[][] correctAnswers = {
            {"Himself"},
            {"once"},
            {"second"},
            {"0"},
            {"1"},
            {"120"},
            {"Mother"},
            {"A = 6","C = 5"}
    };

    // go to the next question
    public void nextQuestion(int n) {
        // update question number and text
        quesNumber.setText(String.format(getResources().getString(R.string.question_number), n));
        question.setText(questions[n - 1]);

        // show single-choice answers
        if (n <= 7) {
            // update answer buttons
            answer1.setText((answerChoices[n - 1][0]));
            answer2.setText((answerChoices[n - 1][1]));
            answer3.setText((answerChoices[n - 1][2]));
            answer4.setText((answerChoices[n - 1][3]));
            // update correct answer
            answer = correctAnswers[n - 1][0];
        }
        // show multiple-answer choices
        else {
            answer1.setVisibility(View.INVISIBLE);
            answer2.setVisibility(View.INVISIBLE);
            answer3.setVisibility(View.INVISIBLE);
            answer4.setVisibility(View.INVISIBLE);

            check1.setVisibility(View.VISIBLE);
            check2.setVisibility(View.VISIBLE);
            check3.setVisibility(View.VISIBLE);
            check4.setVisibility(View.VISIBLE);

            submit.setVisibility(View.VISIBLE);

            check1.setText((answerChoices[n - 1])[0]);
            check2.setText((answerChoices[n - 1])[1]);
            check3.setText((answerChoices[n - 1])[2]);
            check4.setText((answerChoices[n - 1])[3]);

        }
    }

    public void confirmAnswer(final String selection) {
        AlertDialog.Builder adb = new AlertDialog.Builder(GameActivity.this);
        adb.setMessage("You selected " + selection + ". Is this correct?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // if the user selects the correct answer, increment score
                        if (num <= 7) {
                            if (selection.equals(answer)) {
                                score++;
                            }
                        }
                        // if there are more questions, go to next question
                        if (num < questions.length) {
                            nextQuestion(++num);
                        }
                        // else game is over, show score and save to history
                        else {
                            timer.stop();
                            saveScore(score);
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
        adb.setMessage("Game over!\n" +
                "Your score is " + score + "/" + questions.length + (".\n" +
                "Your time is " + strTime))
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

    public void saveScore(int score) {
        // get date and time
        Date dateTime = Calendar.getInstance().getTime();

        // save score to file ScoreHistory
        String s = dateTime +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tScore: " + score +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTime: " + strTime + "\n";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, MODE_APPEND);
            fos.write(s.getBytes());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
