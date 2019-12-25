package com.ahmedco.tasbeh_5;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetAzcar_ extends AppWidgetProvider {
/*

    private static long equ = 60;
    private static long i1 = 5000;
    private static long i2 = 15000;
    private static long i3 = 20000;
    private static long i4 = 30000;
    private static long i5 = 45000;
    private static long i6 = 60000;
    private static long i7 = 60000 * 2;
    private static long i8 = 30000 * 3;
    private static long i9 = 30000 * 4;
    private static long i10 = 30000 * 5;
    private static long i11 = 30000 * 10;

    private static long START_TIME_IN_MILLIS = 0;
    public MediaPlayer mp1, mp2, mp3, mp4, mp5, mp6, mp7;
    private MediaPlayer [] arrSelectSound;
    private List<MediaPlayer> arrSelectSound2;
    private List<Integer> arrSelectLoopSounds;


    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    public int j = 0;
    public int quiteLoop = 1;
    public int count = 0;

    public static final String ACTION_TEXT_CHANGED = "com.ahmedco.tasbeh_5.TEXT_CHANGED";

     //MediaPlayer [] arrSelectSound= {mp1,mp2,mp3,mp4,mp5,mp6,mp7};

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
      CharSequence widgetText = context.getString(R.string.appwidget_text);
      // Construct the RemoteViews object
       RemoteViews views = new RemoteViews(context.getPackageName() , R.layout.widget_azcar_);
       views.setTextViewText(R.id.appwidget_text, widgetText);
       // Instruct the widget manager to update the widget
      appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context , AppWidgetManager appWidgetManager , int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId:appWidgetIds) {
            updateAppWidget(context , appWidgetManager , appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Initi(context,intent);
        putVarTimer();
        setTime(START_TIME_IN_MILLIS);
        START_TIME_IN_MILLIS = 500 * equ;
        startTimer();

    }

    private void Initi(Context context , Intent intent){
        // Bundle extras = getIntent().getExtras();
        mp1 = (MediaPlayer) MediaPlayer.create(context, R.raw.a1);
        mp2 = (MediaPlayer) MediaPlayer.create(context, R.raw.a2);
        mp3 = (MediaPlayer) MediaPlayer.create(context, R.raw.a3);
        mp4 = (MediaPlayer) MediaPlayer.create(context, R.raw.a4);
        mp5 = (MediaPlayer) MediaPlayer.create(context, R.raw.a5);
        mp6 = (MediaPlayer) MediaPlayer.create(context, R.raw.a6);
        mp7 = (MediaPlayer) MediaPlayer.create(context, R.raw.a7);
        MediaPlayer[] arrSelectSound = {mp1, mp2, mp3, mp4, mp5, mp6, mp7};
        arrSelectSound2 = new ArrayList<MediaPlayer>();
        arrSelectLoopSounds = new ArrayList<Integer>();
        boolean[] arrayB = intent.getBooleanArrayExtra("soundsBoolean");
        //extras.getBooleanArray("soundsBoolean");
        int[] arrayLoopSound = intent.getIntArrayExtra("loopSound");
        //  Boolean [] arrayB = extras.getBooleanArray("soundsBoolean");
        if(arrayB != null){
         // do something with the data
           for(int i = 0; i<arrayB.length; i++){
             Log.i("SounBtn", "" + arrayB[i]);
               Log.i("arrayLoopSound00", "" + arrayLoopSound[i]);
                // arrayLoopSound ............
                if(arrayB[i] == true){
                  arrSelectSound2.add(arrSelectSound[i]);
                  arrSelectLoopSounds.add(arrayLoopSound[i]);
                }
             }
          }
       }

        private void putVarTimer(){
          long[] itemTimers = {i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11};
          START_TIME_IN_MILLIS = 500 * equ;
          // String currentEvertTime = String.valueOf(TimeSettingsActivity.ksp1.GetAll().get(0));
            // Log.i("fiveActivity000", "" + TimeSettingsActivity.ksp1.GetAll().get(0));

     }


    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        // updateWatchInterface();
    }

    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();
        //closeKeyboard();
    }

    private void startTimer(){
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                //checkCurrentTimer();
                updateCountDownText();
            }
            @Override
            public void onFinish(){
                mTimerRunning = false;
                //Toast.makeText(AzcarActivity.this, "انتهى", Toast.LENGTH_SHORT).show();
                setTime(START_TIME_IN_MILLIS);
               // mEditTextInput.setText("");
                setsound(arrSelectSound2.get(j));
                startTimer();
                // updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        // updateWatchInterface();
    }


    private void setsound(MediaPlayer key) {
        arrSelectSound2.get(j).start();
        arrSelectSound2.get(j).setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                // arrSelectLoopSounds 0 = 2
                if (arrSelectLoopSounds.get(j) != 0){
                    if (quiteLoop != 0){
                        count += 1;
                        if (count == arrSelectLoopSounds.get(j)){
                            count = 0;
                            quiteLoop = 0;
                        }
                        setsound(arrSelectSound2.get(j));
                    } else {
                        j += 1;
                        if (j<arrSelectSound2.size()) setsound(arrSelectSound2.get(j));
                        else
                            j = 0;
                        quiteLoop = 1;
                    }
                } else {
                    j += 1;
                    if (j<arrSelectSound2.size())setsound(arrSelectSound2.get(j));
                    else
                     j = 0;
                    quiteLoop = 1;
                }
            }
        });
    }


    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
    }

    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours>0){
            timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        }

    }
*/
}

         /*
            switch (currentEvertTime){
                case "0":
                    START_TIME_IN_MILLIS = i1 * equ;
                    break;
                case "1":
                    START_TIME_IN_MILLIS = i2 * equ;
                    break;
                case "2":
                    START_TIME_IN_MILLIS = i3 * equ;
                    break;
                case "3":
                    START_TIME_IN_MILLIS = i4 * equ;
                    break;
                case "4":
                    START_TIME_IN_MILLIS = i5 * equ;
                    break;
                case "5":
                    START_TIME_IN_MILLIS = i6 * equ;
                    break;
                case "6":
                    START_TIME_IN_MILLIS = i7 * equ;
                    break;
                case "7":
                    START_TIME_IN_MILLIS = i8 * equ;
                    break;
                case "8":
                    START_TIME_IN_MILLIS = i9 * equ;
                    break;
                case "9":
                    START_TIME_IN_MILLIS = i10 * equ;
                    break;
                case "10":
                    START_TIME_IN_MILLIS = i11 * equ;
                    break;
               default:
                // START_TIME_IN_MILLIS = i1 * equ;
                START_TIME_IN_MILLIS = 500 * equ;
               break;
          }
          */
