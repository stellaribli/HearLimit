package com.example.hearlimit;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView headphoneStatusTextView;
    private Button checkHeadphoneButton;
    private AudioManager audioManager;
    private HeadphoneReceiver headphoneReceiver;
    private TextView decibelTextView;
    Button lanjutbutton;
    int score = 0;

    private class HeadphoneReceiver extends BroadcastReceiver {
        private long headphonesPluggedInTime;
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                if (state == 1) {
                    // Headphones are plugged in
                    headphoneStatusTextView.setText("Headphone Status: Plugged In");
                    headphonesPluggedInTime = System.currentTimeMillis();
//                    showNotification("Headphone Status: Plugged In");
                } else {
                    // Headphones are unplugged
                    long duration = System.currentTimeMillis() - headphonesPluggedInTime; // Calculate the duration
                    headphoneStatusTextView.setText("Headphone Status: Not Plugged In\nHeadphone Plugged In Duration: " + duration/1000 + " seconds");


                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the UI elements
        headphoneStatusTextView = (TextView) findViewById(R.id.headphone_status_textview);
        checkHeadphoneButton = (Button) findViewById(R.id.check_headphone_button);

        // Get the AudioManager service
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Set up the headphone receiver
        headphoneReceiver = new HeadphoneReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headphoneReceiver, filter);

        // Check the headphone status when the button is clicked
        checkHeadphoneButton.setOnClickListener(v -> checkHeadphoneStatus());

        lanjutbutton = findViewById(R.id.lanjutbutton);
        lanjutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MulaiAssessment.class);
                startActivity(intent);
            }
        });
        // Get the UI element
        decibelTextView = (TextView) findViewById(R.id.decibel_textview);

        // Get the AudioManager service
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Set up a periodic task to update the decibel level every second
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                updateDecibelLevel();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    private void updateDecibelLevel() {
        // Get the current volume level for the music stream
        int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Convert the volume level to decibels
        float decibels = 20 * (float) Math.log10(volumeLevel);

        // Update the UI to display the current decibel level
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                decibelTextView.setText("Current Decibel Level: " + decibels + " dB");
            }
        });
    }

    private boolean isHeadphonesPlugged(){
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        AudioDeviceInfo[] audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_INPUTS);
        for(AudioDeviceInfo deviceInfo : audioDevices){
            System.out.println (deviceInfo.getType());
            if(deviceInfo.getType()==AudioDeviceInfo.TYPE_WIRED_HEADPHONES
                    || deviceInfo.getType()==AudioDeviceInfo.TYPE_WIRED_HEADSET){
                return true;
            }
        }
        return false;
    }
    private void checkHeadphoneStatus() {
        if (isHeadphonesPlugged()) {
            // Headphones are plugged in
            headphoneStatusTextView.setText("Headphone Status: Plugged In");
        } else {
            // Headphones are not plugged in
            headphoneStatusTextView.setText("Headphone Status: Not Plugged In");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}