package com.example.udoncar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Screen_SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_sign_up);

    // Spinner
        Spinner agesSpinner = (Spinner)findViewById(R.id.spinner_ages);
        ArrayAdapter agesAdapter = ArrayAdapter.createFromResource(this, R.array.ages, android.R.layout.simple_spinner_item);
        agesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agesSpinner.setAdapter(agesAdapter);

    }

}