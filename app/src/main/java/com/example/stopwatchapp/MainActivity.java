package com.example.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.stopwatchapp.R; // Replace 'com.example.stopwatchapp' with your actual package name

public class MainActivity extends AppCompatActivity {

    private TextView timeView;
    private Button startButton, stopButton, resetButton;

    private long startTime = 0;
    private boolean isRunning = false; // Flag to track running state
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long updatedTime = System.currentTimeMillis() - startTime;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timeView.setText(String.format("%02d:%02d:%03d", mins, secs, milliseconds));

            // Post the runnable again if still running
            if (isRunning) {
                handler.postDelayed(this, 0);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeView = findViewById(R.id.timeview);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis();
                    isRunning = true;
                    handler.postDelayed(runnable, 0);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                handler.removeCallbacks(runnable);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                handler.removeCallbacks(runnable);
                startTime = 0;
                timeView.setText("00:00:00");
            }
        });
    }
}