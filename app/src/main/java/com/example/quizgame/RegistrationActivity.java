package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private boolean valid = true;
    private EditText e1, e2, e3, e4, e5;
    private String fName, lName, dob, email, pass;
    private final String FILE = "UserData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        e1 = findViewById(R.id.edtFName);
        e2 = findViewById(R.id.edtLName);
        e3 = findViewById(R.id.edtDOB);
        e4 = findViewById(R.id.edtRegEmail);
        e5 = findViewById(R.id.edtRegPassword);
    }

    public void registerUser(View view) {
        // reset valid boolean
        valid = true;

        if (e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty()
                || e3.getText().toString().isEmpty() || e4.getText().toString().isEmpty()
                || e5.getText().toString().isEmpty()) {
            // if any fields are empty display toast and return
            Toast.makeText(this, getResources().getString(R.string.toast_regFields), Toast.LENGTH_LONG).show();
            return;
        }

        // set variables using user's input
        fName = e1.getText().toString();
        lName = e2.getText().toString();
        dob = e3.getText().toString();
        email = e4.getText().toString();
        pass = e5.getText().toString();

        // test user input
        validateInput();

        // if any fields have incorrect input, display toast
        if (!valid) {
            Toast.makeText(this, getResources().getString(R.string.toast_valid), Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences sharedPref = getSharedPreferences(FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("First Name", fName);
        editor.putString("Last Name", lName);
        editor.putString("Date of Birth", dob);
        editor.putString("Email", email);
        editor.putString("Password", pass);
        editor.apply();

        // create new intent
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("Success", valid);
        startActivity(intent);
    }

    public boolean validateInput() {
        // check first name must be between 3 and 30 characters
        if (fName.length() < 3 || fName.length() > 30) {
            Toast.makeText(this, getResources().getString(R.string.toast_name), Toast.LENGTH_LONG).show();
            valid = false;
        }

        // try to parse dob to check for valid date of birth
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            sdf.setLenient(false);
            sdf.parse(dob);
        }
        catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.toast_date), Toast.LENGTH_LONG).show();
            valid = false;
        }

        // check for valid email address
        if (!(email.contains("@") || email.endsWith(".edu")
                || email.endsWith(".com") || email.endsWith(".org") || email.endsWith(".net"))) {
            Toast.makeText(this, getResources().getString(R.string.toast_email), Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }

    public void goToLogIn(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
