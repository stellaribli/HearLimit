package com.example.hearlimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Assessment1 extends AppCompatActivity {
    Button button;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String getNumber = "100";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment1);
        button = findViewById(R.id.lanjutbutton);
        editText = findViewById(R.id.edit_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();
//                String getNumber = "100";
//                int score = Integer.parseInt(getNumber);
                if (input.isEmpty()) {
                    editText.setError("Masukkan nilai");
                    editText.requestFocus();
                } else {
                    int value = Integer.parseInt(input);
                    if (value < 2 || value > 100) {
                        editText.setError("Masukkan nilai antara 2 dan 100");
                        editText.requestFocus();
                    }
                    else {
                        Intent intent5 = new Intent(Assessment1.this, HeadphoneActivity.class);
                        startActivity(intent5);}
                }
            }
        });
    }
}