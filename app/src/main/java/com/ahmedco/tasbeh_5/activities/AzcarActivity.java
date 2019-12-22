package com.ahmedco.tasbeh_5.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedco.tasbeh_5.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AzcarActivity extends AppCompatActivity {

    static long equ = 60;
    static long i1 = 1000;
    //static long i1 = 5000;
    static long i2 = 15000;
    static long i3 = 20000;
    static long i4 = 30000;
    static long i5 = 45000;
    static long i6 = 60000;
    static long i7 = 60000 * 2;
    static long i8 = 30000 * 3;
    static long i9 = 30000 * 4;
    static long i10 = 30000 * 5;
    static long i11 = 30000 * 10;

    private static long START_TIME_IN_MILLIS = 0;

    //= i1  * equ;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    List<MediaPlayer> arrSelectSound2;
    List<Integer> arrSelectLoopSounds;
    public int j = 0;
    public int quiteLoop = 1;
    public int count = 0;
    public int loopSoundCounter = 0;

    public int[] loops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azcar);
        final MediaPlayer mp1 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this, R.raw.a1);
        final MediaPlayer mp2 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this, R.raw.a2);
        final MediaPlayer mp3 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this, R.raw.a3);
        final MediaPlayer mp4 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this, R.raw.a4);
        final MediaPlayer mp5 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this, R.raw.a5);
        final MediaPlayer mp6 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this, R.raw.a6);
        final MediaPlayer mp7 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this, R.raw.a7);
        MediaPlayer[] arrSelectSound = {mp1,mp2,mp3,mp4,mp5,mp6,mp7};
        arrSelectSound2 = new ArrayList<MediaPlayer>();
        arrSelectLoopSounds = new ArrayList<Integer>();
        long[] itemTimers = {i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11};
        // String currentEvertTime = String.valueOf(SettingsActivity.ksp1.GetAll().get(0));
        // Log.i("fiveActivity000", "" + SettingsActivity.ksp1.GetAll().get(0));
        String oop = "0";
        switch (oop) {
            case "0":
                START_TIME_IN_MILLIS = i1 * equ;
                ;
                break;
            case "1":
                START_TIME_IN_MILLIS = i2 * equ;
                ;
                break;
            case "2":
                START_TIME_IN_MILLIS = i3 * equ;
                ;
                break;
            case "3":
                START_TIME_IN_MILLIS = i4 * equ;
                ;
                break;
            case "4":
                START_TIME_IN_MILLIS = i5 * equ;
                ;
                break;
            case "5":
                START_TIME_IN_MILLIS = i6 * equ;
                ;
                break;
            case "6":
                START_TIME_IN_MILLIS = i7 * equ;
                ;
                break;
            case "7":
                START_TIME_IN_MILLIS = i8 * equ;
                ;
                break;
            case "8":
                START_TIME_IN_MILLIS = i9 * equ;
                ;
                break;
            case "9":
                START_TIME_IN_MILLIS = i10 * equ;
                ;
                break;
            case "10":
                START_TIME_IN_MILLIS = i11 * equ;
                ;
                break;
            default:
                START_TIME_IN_MILLIS = i1 * equ;
                ;
                break;
        }

        Bundle extras = getIntent().getExtras();

        boolean[] arrayB = extras.getBooleanArray("soundsBoolean");
        int[] arrayLoopSound = extras.getIntArray("loopSound");

        if(arrayB != null){
         // do something with the data
            for(int i = 0; i < arrayB.length; i++){
                //Log.i("SounBtn", "" + arrayB[i]);
                // Log.i("arrayLoopSound00", "" + arrayLoopSound[i]);
                // arrayLoopSound
                if (arrayB[i] == true) {
                    arrSelectSound2.add(arrSelectSound[i]);
                    arrSelectLoopSounds.add(arrayLoopSound[i]);
                }
            }
        }

        // Log.i("SounBtn22_0", "" + arrSelectLoopSounds.get(0).toString());
        // Log.i("SounBtn22_1", "" + arrSelectLoopSounds.get(1).toString());

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        setTime(START_TIME_IN_MILLIS);
        //mEditTextInput.setText("");
        startTimer();
    }



    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        //closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished){
                mTimeLeftInMillis = millisUntilFinished;
                //checkCurrentTimer();
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                Toast.makeText(AzcarActivity.this, "انتهى", Toast.LENGTH_SHORT).show();
                setTime(START_TIME_IN_MILLIS);
                // mEditTextInput.setText("");
                setsound(arrSelectSound2.get(j));
                startTimer();
                // updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
    }


    private void setsound(MediaPlayer key) {
        arrSelectSound2.get(j).start();
        arrSelectSound2.get(j).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // arrSelectLoopSounds 0 = 2
                if (arrSelectLoopSounds.get(j) != 0) {
                    if (quiteLoop != 0) {
                        count += 1;
                        if (count == arrSelectLoopSounds.get(j)) {
                            count = 0;
                            quiteLoop = 0;
                        }
                        setsound(arrSelectSound2.get(j));
                    } else {
                        j += 1;
                        if (j < arrSelectSound2.size()) setsound(arrSelectSound2.get(j));
                        else
                            j = 0;
                        quiteLoop = 1;
                    }
                } else {
                    j += 1;
                    if (j < arrSelectSound2.size()) setsound(arrSelectSound2.get(j));
                    else
                        j = 0;
                    quiteLoop = 1;
                }
            }
        });
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        // updateWatchInterface();
    }

    private void checkCurrentTimer() {
     Calendar cal = Calendar.getInstance();
       int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
         int minute = cal.get(Calendar.MINUTE);
         //12 hour format
         int hour = cal.get(Calendar.HOUR);
        // 24 hour format ...........................
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        //Log.v("tr00","" + minute);
        if (minute <= 22) {
            if (mTimerRunning) {
                pauseTimer();
            }
        } else {
            if (!mTimerRunning) startTimer();
        }
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }
}


