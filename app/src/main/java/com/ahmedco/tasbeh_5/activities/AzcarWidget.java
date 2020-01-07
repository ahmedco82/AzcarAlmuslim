package com.ahmedco.tasbeh_5.activities;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import com.ahmedco.tasbeh_5.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Define a simple widget that shows the Wiktionary "Word of the day." To build
 * an update we spawn a background {@link Service} to perform the API queries.
 */
// https://android-developers.googleblog.com/2009/04/introducing-home-screen-widgets-and.html

public class AzcarWidget extends AppWidgetProvider {

    //KSP ksp_bool2;
    //public static final String ACTION_TEXT_CHANGED = "com.ahmedco.tasbeh_5.activities.TEXT_CHANGED";

    @Override
    public void onUpdate(Context context , AppWidgetManager appWidgetManager , int[] appWidgetIds) {
      // Perform this loop procedure for each App Widget that belongs to this provider
//        context.startService(new Intent(UpdateService.ACTION_TEXT_CHANGED));
        //Log.i("onUpdate","onUpdate is work");
       //context.startService(new Intent(context.getApplicationContext() , UpdateService.class));
    }

    @Override
    public void onEnabled(Context context){
     super.onEnabled(context);
     // Log.i("onEnabled","onEnabled is work");
      //Log.i("Server","onEnabled");
      //context.startService(new Intent(UpdateService.ACTION_TEXT_CHANGED));
      //context.startService(new Intent(context.getApplicationContext() , UpdateService.class));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        //Log.i("onDeleted","onDeleted is work");
        context.stopService(new Intent(context.getApplicationContext() , UpdateService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        //Log.i("onDisabled","onDisabled is work");
        context.stopService(new Intent(context.getApplicationContext() , UpdateService.class));
    }
}







/*
 class UpdateService extends Service {
    List<MediaPlayer> selectedSound;
    List<Integer> repeatEachSound;
    public int j = 0;
    public int quiteLoop = 1;
    public int count = 0;
    public int getSeconds = 0;
    int Length = 6;
    public MediaPlayer[] allSounds;
    private static int start_AM_PM,end_AM_PM,everyTimeNum,stopTimer,hourStart_,minuteStart_,hourEnd_,minuteEnd_;
    private MediaPlayer s1,s2,s3,s4,s5,s6,s7;
    private String[] NamseOfItems;
    private int[] ValuesOfItems;

    @Override
    public void onCreate(){
        super.onCreate();
        initiItems();
        // Error ----------------------------------
        copySoundsFromSharedPrefToArraies(allSounds);
        int stop_timer = timesSharedPreferences.getInt(NamseOfItems[2]);
        if(stop_timer==1){
            getStoringTimes(NamseOfItems,ValuesOfItems);
        }
        getSeconds = getTimeInSeconds(NamseOfItems);
        startTimer(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.stopService(new Intent(this , UpdateService.class));
    }

    private void initiItems(){
        selectedSound = new ArrayList<MediaPlayer>();
        repeatEachSound = new ArrayList<Integer>();
        ValuesOfItems = new int[]{everyTimeNum, stopTimer, hourStart_, hourEnd_, start_AM_PM, minuteStart_, minuteEnd_, end_AM_PM};
        NamseOfItems = new String[]{"everyTime", "stopTimer", "hour_start", "hour_end", "start_AM_PM", "minute_start", "minute_end", "end_AM_PM"};
        s1 = (MediaPlayer) MediaPlayer.create(this, R.raw.a1);
        s2 = (MediaPlayer) MediaPlayer.create(this, R.raw.a2);
        s3 = (MediaPlayer) MediaPlayer.create(this, R.raw.a3);
        s4 = (MediaPlayer) MediaPlayer.create(this, R.raw.a4);
        s5 = (MediaPlayer) MediaPlayer.create(this, R.raw.a5);
        s6 = (MediaPlayer) MediaPlayer.create(this, R.raw.a6);
        s7 = (MediaPlayer) MediaPlayer.create(this, R.raw.a7);
        allSounds = new MediaPlayer[]{s1, s2, s3, s4, s5, s6, s7};
    }

    private void copySoundsFromSharedPrefToArraies(MediaPlayer[] allSounds) {

        int[] arrayLoopSound = new int[Length];

        for(int i=0; i<Length; i++){
            ///  Error  : arrayLoopSound[i] = soundsSharedPref.getInt("loopSound"+i);
            arrayLoopSound[i] = soundsSharedPref.getInt("loopSound"+i);
            if(soundsSharedPref.getBool("soundsBoolean"+i) == true){
                selectedSound.add(allSounds[i]);
                repeatEachSound.add(arrayLoopSound[i]);
            }
        }
    }



    private void getStoringTimes(String[] NamseOfItems,int[] ValuesOfItems){
        for(int i=0; i<NamseOfItems.length; i++){
            ValuesOfItems[i]= timesSharedPreferences.getInt(NamseOfItems[i]);
        }
    }


    private int getTimeInSeconds(String [] NamseOfItems){
        int Seconds = timesSharedPreferences.getInt(NamseOfItems[0]);
        switch (Seconds) {
            case 0:
                Seconds = 900;
                break;
            case 1:
                Seconds = 1200;
                break;
            case 2:
                Seconds = 1800;
                break;
            case 3:
                Seconds = 2700;
                break;
            case 4:
                Seconds = 3600;
                break;
            case 5:
                Seconds = 7200;
                break;
            case 6:
                Seconds = 10800;
                break;
            case 7:
                Seconds = 14400;
                break;
            case 8:
                Seconds = 18000;
                break;
            case 9:
                Seconds = 36000;
                break;
            default:
                break;
        }
        return Seconds;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // Log.i("trace","onStartCommand"+intent.getBooleanArrayExtra("data").length);
        // ArrayList<String> transferData = intent.getStringArrayListExtra("data");
        return START_STICKY;
    }


    public RemoteViews buildUpdate(final Context context, int h, int m, int s) {
        // Pick out month names from resources
        Resources res = context.getResources();
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent defineIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , defineIntent, 0 );
        updateViews.setTextViewText(R.id.tV_timeElapsed, "ساعة"+ h +" و "+m +"دقيقة "+ "و"+ s+"ثانية");
        // Create an Intent to launch ExampleActivity
        return updateViews;
    }


    private void startTimer(final Context context){
        // totoal Seconds for all time : 2 hour = 7200, getSeconds
        new CountDownTimer(40 * 1000 + 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);
                // Log.i("Timer0 "," H "+hours +" M "+minutes +" "+seconds );
                ComponentName thisWidget = new ComponentName(context, AzcarWidget.class);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                RemoteViews updateViews = buildUpdate(context , hours,minutes,seconds);
                manager.updateAppWidget(thisWidget , updateViews);
            }
            public void onFinish(){
                setsound(selectedSound.get(j));
                startTimer(context);
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't need to bind to this service
        // Log.i("trace","IBinder");
        return null;
    }

    private void setsound(MediaPlayer key) {
        selectedSound.get(j).start();
        selectedSound.get(j).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // arrSelectLoopSounds 0 = 2
                if (repeatEachSound.get(j) != 0) {
                    if (quiteLoop != 0) {
                        count += 1;
                        if (count == repeatEachSound.get(j)) {
                            count = 0;
                            quiteLoop = 0;
                        }
                        setsound(selectedSound.get(j));
                    } else {
                        j += 1;
                        if (j < selectedSound.size()) setsound(selectedSound.get(j));
                        else
                            j = 0;
                        quiteLoop = 1;
                    }
                } else {
                    j += 1;
                    if (j<selectedSound.size()) setsound(selectedSound.get(j));
                    else
                        j = 0;
                    quiteLoop = 1;
                }
            }
        });
    }

}
*/

















/*
Calendar now = Calendar.getInstance();
if(now.get(Calendar.AM_PM) == Calendar.AM){
// AM
//System.out.println(""+now.get(Calendar.HOUR)+":AM");
 Log.i("traceTime",""+now.get(Calendar.HOUR)+":AM");
}else{
// PM
// System.out.println(""+now.get(Calendar.HOUR)+":PM");
 Log.i("traceTime",""+now.get(Calendar.HOUR)+":PM");
}
*/

