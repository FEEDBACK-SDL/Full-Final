package com.example.fedsev.feedback;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


public class Tab1 extends Fragment
{
    boolean flag = false;
    private View v;
    private long  timeCountInMilliSeconds = 30*60*1000;
    private int maxtime = 1800;
    private ProgressBar progressBarCircle;
    private EditText editTextMinute;
    private TextView textViewTime;
    private CountDownTimer countDownTimer;
    private TextView complete;
    private TextView remaining;
    public Tab1()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_tab1, container, false);
        // method call to initialize the views
        initViews();
        LocalDateTime now = LocalDateTime.now();
        FancyToast.makeText(getContext(),String.valueOf(MainActivity.myAppDatabase.myDao().getcount()),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("private_data", Context.MODE_PRIVATE);
        timeCountInMilliSeconds = sharedPreferences.getLong("timeend", 0) - (now.getHour()*60*60*1000 + now.getMinute()*60*1000 + now.getSecond()*1000) ;
        remaining.setText(String.valueOf(MainActivity.myAppDatabase.myDao().getcount()));
        complete.setText(String.valueOf(15 - MainActivity.myAppDatabase.myDao().getcount()));
        setProgressBarValues();
        startCountDownTimer();


        return v;
    }






    /**
     * method to initialize the views
     */
    private void initViews() {
        progressBarCircle = (ProgressBar) v.findViewById(R.id.progressBarCircle);
        textViewTime = (TextView) v.findViewById(R.id.textViewTime);
        remaining = (TextView) v.findViewById(R.id.remaining);
        complete = (TextView) v.findViewById(R.id.complete);
//        imageViewReset = (ImageView) findViewById(R.id.imageViewReset);
//        imageViewStartStop = (ImageView) findViewById(R.id.imageViewStartStop);
    }






    /**
     * method to start count down timer
     */
    private void startCountDownTimer() {

        if (!flag){
            countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    textViewTime.setText(hmsTimeFormatter(millisUntilFinished));


                    progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

                }

                @Override
                public void onFinish() {

                    textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                    // call to initialize the progress bar values
                    setProgressBarValues();
                   // startCountDownTimer();
                    // hiding the reset icon
                    // imageViewReset.setVisibility(View.GONE);
                    // changing stop icon to start icon
                    // imageViewStartStop.setImageResource(R.drawable.icon_start);
                    // making edit text editable
                    // changing the timer status to stopped
                    //timerStatus = TimerStatus.STOPPED;
                }

            }.start();
            countDownTimer.start();
            flag = true;
        }

    }



    /**
     * method to set circular progress bar values
     */
    private void setProgressBarValues() {

        progressBarCircle.setMax(maxtime);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 100);
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }



}

