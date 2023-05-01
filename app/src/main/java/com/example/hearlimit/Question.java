// pertanyaan tentang berapa volume
package com.example.hearlimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Question extends AppCompatActivity {

    // Deklarasi variabel untuk TextView dan Button
    private Button volume80_100Button;
    private Button volume60_80Button;
    private Button volume40_60Button;
    private Button volume20_40Button;
    private Button volume0_20Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Menghubungkan variabel dengan elemen di layout XML
        volume80_100Button = findViewById(R.id.volume_80_100_button);
        volume60_80Button = findViewById(R.id.volume_60_80_button);
        volume40_60Button = findViewById(R.id.volume_40_60_button);
        volume20_40Button = findViewById(R.id.volume_20_40_button);
        volume0_20Button = findViewById(R.id.volume_0_20_button);
    }

    public void onVolumeButtonClick(View view) {
        // Mendapatkan ID dari button yang ditekan
        int buttonId = view.getId();
        String getNumber = "20";
        int score = Integer.parseInt(getNumber);
        // Menggunakan switch case untuk memproses input dari pengguna berdasarkan ID button
        switch (buttonId) {
            case R.id.volume_80_100_button:
                // Lakukan sesuatu untuk pilihan 80-100%
                break;
            case R.id.volume_60_80_button:
                // Lakukan sesuatu untuk pilihan 60-80%
                break;
            case R.id.volume_40_60_button:
                // Lakukan sesuatu untuk pilihan 40-60%
                break;
            case R.id.volume_20_40_button:

                Intent intent2 = new Intent(this, VolumeHeadphone.class);

                startActivity(intent2);
                break;
            case R.id.volume_0_20_button:

                Intent intent3 = new Intent(this, VolumeHeadphone.class);
                startActivity(intent3);
                break;
        }
    }
}