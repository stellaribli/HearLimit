package com.example.hearlimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class hasil extends AppCompatActivity {
    TextView scoreResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        Intent intent3 = getIntent();
        String getNumber = intent3.getStringExtra("number");
        scoreResult = findViewById(R.id.score);
        scoreResult.setText(getNumber);
    }
}