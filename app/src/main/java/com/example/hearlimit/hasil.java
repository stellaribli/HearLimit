package com.example.hearlimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class hasil extends AppCompatActivity {
    TextView scoreResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        Intent intent3 = getIntent();
        String getNumber = intent3.getStringExtra("number");
        TextView durationResult;
        TextView volumeResult;
//        scoreResult = findViewById(R.id.score);
//        scoreResult.setText(getNumber);
        int score = Integer.parseInt(getNumber);
        if (score >=70){
            scoreResult = findViewById(R.id.score);
            scoreResult.setText("Kondisi Pendengaran: Sehat");
            durationResult = findViewById(R.id.teksDurasiHasil);
            durationResult.setText("Maksimal 3 jam per hari");
            volumeResult = findViewById(R.id.teksVolumeHasil);
            volumeResult.setText("Maksimal Volume 80%");
        }
        if(score>=50 && score < 70){
            scoreResult = findViewById(R.id.score);
            scoreResult.setText("Kondisi Pendengaran: Perlu dijaga");
            durationResult = findViewById(R.id.teksDurasiHasil);
            durationResult.setText("Maksimal 2 jam per hari");
            volumeResult = findViewById(R.id.teksVolumeHasil);
            volumeResult.setText("Maksimal Volume 60%");
        }
        if(score<50){
            scoreResult = findViewById(R.id.score);
            scoreResult.setText("Kondisi Pendengaran: Dianjurkan untuk Melakukan Pengecekan ke Dokter");
            durationResult = findViewById(R.id.teksDurasiHasil);
            durationResult.setText("Maksimal 1 jam per hari");
            volumeResult = findViewById(R.id.teksVolumeHasil);
            volumeResult.setText("Maksimal Volume 40%");
        }
        Button button;
        button = findViewById(R.id.lanjutbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7 = new Intent(hasil.this, MainActivity.class);
                startActivity(intent7);
            }
        });

    }
}
