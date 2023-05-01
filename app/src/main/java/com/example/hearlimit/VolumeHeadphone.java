package com.example.hearlimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VolumeHeadphone extends AppCompatActivity {

    private Button lebih_dari_10;
    private Button antara_5_10;
    private Button antara_3_5;
    private Button antara_1_3;
    private Button kurang_dari_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String getNumber = "100";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_headphone);

        lebih_dari_10 = findViewById(R.id.lebihdari10);
        antara_5_10 = findViewById(R.id.antara510);
        antara_3_5 = findViewById(R.id.antara35);
        antara_1_3 = findViewById(R.id.antara13);
        kurang_dari_1 = findViewById(R.id.kurangdari1);
    }
    public void onVolumeButtonClick(View view) {
        // Mendapatkan ID dari button yang ditekan
        int buttonId = view.getId();
        String getNumber = "100";
        int score = Integer.parseInt(getNumber);
        // Menggunakan switch case untuk memproses input dari pengguna berdasarkan ID button
        switch (buttonId) {
            case R.id.lebihdari10:
                Intent intent1 = new Intent(this, hasil.class);
                score = score - 50;
                getNumber = Integer.toString(score);
                intent1.putExtra("number", getNumber);
                startActivity(intent1);
                // Lakukan sesuatu untuk pilihan 80-100%
                break;
            case R.id.antara510:
                Intent intent2 = new Intent(this, hasil.class);
                score = score - 40;
                getNumber = Integer.toString(score);
                intent2.putExtra("number", getNumber);
                startActivity(intent2);
                // Lakukan sesuatu untuk pilihan 60-80%
                break;
            case R.id.antara35:
                Intent intent3 = new Intent(this, hasil.class);
                score = score - 30;
                getNumber = Integer.toString(score);
                intent3.putExtra("number", getNumber);
                startActivity(intent3);
                // Lakukan sesuatu untuk pilihan 40-60%
                break;
            case R.id.antara13:
                Intent intent4 = new Intent(this, hasil.class);
                score = score - 20;
                getNumber = Integer.toString(score);
                intent4.putExtra("number", getNumber);
                startActivity(intent4);
                break;
            case R.id.kurangdari1:
                score = score - 10;
                getNumber = Integer.toString(score);
                Intent intent5 = new Intent(this, hasil.class);
                intent5.putExtra("number", getNumber);
                startActivity(intent5);
                break;
        }
    }
}
