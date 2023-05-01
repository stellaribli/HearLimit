package com.example.hearlimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HeadphoneActivity extends AppCompatActivity {
    Button button;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Intent intent = new Intent(HeadphoneActivity.this, Question.class);
//        String getNumber = intent.getStringExtra("number1");
//        TextView scoreResult = findViewById(R.id.assessment1);
//        scoreResult.setText(getNumber);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headphone);
        button = findViewById(R.id.lanjutbutton);
        radioGroup = findViewById(R.id.radio_group);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId == -1){
                    Toast.makeText(HeadphoneActivity.this, "Silakan pilih salah satu opsi",Toast.LENGTH_SHORT) .show();
                } else {
                    Intent intent7 = new Intent(HeadphoneActivity.this, Question.class);
                    startActivity(intent7);
                }
            }
        });
    }
}