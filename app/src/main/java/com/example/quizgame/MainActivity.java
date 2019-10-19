package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToGame(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void goToHistory(View view) {
        startActivity(new Intent(this, HistoryActivity.class));
    }
}
