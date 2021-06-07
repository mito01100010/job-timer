package com.example.jobtimer.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jobtimer.R;
import com.example.jobtimer.activities.WorkFinishedDialog;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    TextView timerText;
    Button stopStartButton;
    Button resetButton;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        //getSupportActionBar().setTitle("Timer");

        Spinner taskSpinner = (Spinner) findViewById(R.id.tasksSpinner);
        timerText = (TextView) findViewById(R.id.timerText);
        stopStartButton = (Button) findViewById(R.id.startStopButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TimerActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tasks));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(adapter);
        timer = new Timer();

        timerText.addTextChangedListener(timeResetTextWatcher);

    }

    private TextWatcher timeResetTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            resetButton.setEnabled(time != 0.0);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void resetButton(View view) {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you finished your task?");
        resetAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(timerTask != null){
                    timerTask.cancel();
                    setButtonUI(stopStartButton, "Start Work");
                    time = 0.0;
                    timerStarted = false;
                    timerText.setText(formatTime(0,0,0));
                    workFinishedDialog();
                }
            }
        });

        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        resetAlert.show();
    }


    public void startStopButton(View view) {

        if(timerStarted == false){
            timerStarted = true;
            setButtonUI(stopStartButton, "Pause Work");

            startTimer();
        }else{
            timerStarted = false;
            setButtonUI(stopStartButton, "Continue Work");

            timerTask.cancel();
        }
    }

    private void setButtonUI(TextView stopStartButton, String start) {
        stopStartButton.setText(start);
        //може и смяна на цвят?
    }

    private void startTimer() {

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        setButtonUI(timerText, getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0,1000);
    }

    private String getTimerText() {

        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds,minutes,hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {

        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    private void workFinishedDialog() {
        WorkFinishedDialog wfd = new WorkFinishedDialog();
        wfd.show(getSupportFragmentManager(), "Work finished dialog");
    }

}