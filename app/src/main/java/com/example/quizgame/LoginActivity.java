package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText e1, e2;
    private String userEmail, userPass;
    private final String FILE = "UserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1 = findViewById(R.id.edtLoginEmail);
        e2 = findViewById(R.id.edtLoginPassword);

        SharedPreferences sharedPref = getSharedPreferences(FILE, MODE_PRIVATE);
        userEmail = sharedPref.getString("Email", "");
        userPass = sharedPref.getString("Password", "");

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            if ((boolean) b.get("Success")) {
                // display toast if registration was successful
                Toast.makeText(this, getResources().getString(R.string.toast_success), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void logIn(View view) {
        // if either field is empty, make toast and return
        if (e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.toast_logFields), Toast.LENGTH_LONG).show();
            return;
        }
        // else if email and password don't match data from registration activity, make toast and return
        else if (!(e1.getText().toString().equals(userEmail) && e2.getText().toString().equals(userPass))) {
            Toast.makeText(this, getResources().getString(R.string.toast_login), Toast.LENGTH_LONG).show();
            return;
        }

        // email and password match so go to MainActivity
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void goToRegistration(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
