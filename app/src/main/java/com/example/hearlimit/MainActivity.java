package com.example.hearlimit;

import static com.example.hearlimit.R.string.snooze;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.card.MaterialCardView;


public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "my_channel";
    private static final String EXTRA_SNOOZE_DURATION = "com.example.hearlimit.EXTRA_SNOOZE_DURATION";
    private static final String ACTION_OK = "com.example.hearlimit.ACTION_OK";

    int notificationId = 1;
    private TextView headphoneStatusTextView;
    private AudioManager audioManager;
    private HeadphoneReceiver headphoneReceiver;
    private TextView decibelTextView;
    Button assessbutton;
    public int state;

    private static final String ACTION_SNOOZE = "com.example.hearlimit.ACTION_SNOOZE";
    private static final String EXTRA_NOTIFICATION_ID = "com.example.hearlimit.EXTRA_NOTIFICATION_ID";

    private class HeadphoneReceiver extends BroadcastReceiver {
        public long headphonesPluggedInTime = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // Check which action was clicked
            if (action.equals(ACTION_SNOOZE)) {
                // Handle Snooze button click
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.cancelAll();

                // Schedule the next notification in 15 minutes
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendNotificationLimit();
                    }
                }, 1 * 60 * 1000);

            } else if (action.equals(ACTION_OK)) {
                // Handle Ok button click
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.cancelAll();
            }

            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                state = intent.getIntExtra("state", -1);
                if (state == 1) {
                    // Headphones are plugged in
                    headphoneStatusTextView.setText("Plugged In");
                    headphonesPluggedInTime = System.currentTimeMillis();
                } else {
                    // Headphones are unplugged
                    long duration = System.currentTimeMillis() - headphonesPluggedInTime; // Calculate the duration
                    if (duration != System.currentTimeMillis()) {
                        headphoneStatusTextView.setText("Not Plugged In\n Previous Duration: " + duration / 1000 + " seconds");
                    }
                }
            }
        }
    }
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel Description");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("MissingPermission")
    private void sendNotificationLimit(){
//        Intent snoozeIntent = new Intent(this, HeadphoneReceiver.class);
//        snoozeIntent.setAction(ACTION_SNOOZE);
//        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 1);
//        snoozeIntent.putExtra(EXTRA_SNOOZE_DURATION, 1 * 60 * 1000); // snooze selama 15 menit
//        PendingIntent snoozePendingIntent =
//                PendingIntent.getBroadcast(this, 3, snoozeIntent, 0);
//
//        // Intent for the Ok action
//        Intent okIntent = new Intent(this, HeadphoneReceiver.class);
//        okIntent.setAction(ACTION_OK);
//        okIntent.putExtra(EXTRA_NOTIFICATION_ID, 1);
//        PendingIntent okPendingIntent = PendingIntent.getBroadcast(this, 4, okIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "my_channel")
                .setSmallIcon(R.drawable.hearlimitlogo)
                .setContentTitle("Headphone volume warning")
                .setContentText("You have exceeded the safe headphone limit, please turn down the volume/stop using your headphone")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("You have exceeded the safe headphone limit, please turn down the volume/stop using your headphone"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(notificationId, builder.build());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        // Get the UI elements
        headphoneStatusTextView = (TextView) findViewById(R.id.headphone_status_textview);

        // Get the AudioManager service
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Set up the headphone receiver
        headphoneReceiver = new HeadphoneReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headphoneReceiver, filter);

        // Open Assessment Page
        assessbutton = findViewById(R.id.assessment_button);
        assessbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MulaiAssessment.class);
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

    private boolean isNotificationSent = false;
    private long lastNotificationTime = 0;
    private long NOTIFICATION_INTERVAL = 1 * 60 * 1000; // 1 minutes in milliseconds

    private void updateDecibelLevel() {
        // Get the current volume level for the music stream
        int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Convert the volume level to decibels
        float decibels = 20 * (float) Math.log10(volumeLevel);

        long currentTime = System.currentTimeMillis();

        Switch mySwitch = findViewById(R.id.switchNotification);
        if (state == 1 && mySwitch.isChecked() && decibels > 20 && currentTime - headphoneReceiver.headphonesPluggedInTime > 15000 && headphoneReceiver.headphonesPluggedInTime != 0) {
            sendNotificationLimit();
//
//        if (mySwitch.isChecked() && decibels > 20 && currentTime - headphoneReceiver.headphonesPluggedInTime > 15000 && headphoneReceiver.headphonesPluggedInTime != 0 && !isNotificationSent && currentTime - lastNotificationTime > NOTIFICATION_INTERVAL) {
//            sendNotificationLimit();
//            isNotificationSent = true;
//            lastNotificationTime = currentTime;
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isNotificationSent = false;
//                }
//            }, NOTIFICATION_INTERVAL);
        }

        // Update the UI to display the current decibel level
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                decibelTextView.setText("Current Decibel Level: " + decibels + " dB");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}