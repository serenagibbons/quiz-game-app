package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> scores;
    private final String FILENAME = "ScoreHistory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = findViewById(R.id.listHistory);

        scores = new ArrayList<>();

        // load scores into ArrayList
        loadScores();

        // display scores in ListView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scores);
        list.setAdapter(arrayAdapter);
    }

    // load previous scores from ScoreHistory file
    public void loadScores() {
        ArrayList<String> tempList = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            while ((text = br.readLine()) != null) {
                tempList.add(text);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = tempList.size()-1; i >=0; --i) {
            scores.add(tempList.get(i));
        }
    }
}
