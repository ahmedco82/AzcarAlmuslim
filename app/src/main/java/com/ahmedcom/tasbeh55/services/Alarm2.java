package com.ahmedcom.tasbeh55.services;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm2 extends Service {
    public int counter = 0;
    Context context;
    ArrayList<MediaPlayer> selectedSound;

    public  int currentSound;
    public  int quiteSound;
    public  int counterSound;
    int endCount = 2;
    int loop = 0;
    int step = 0;
    private  List<Integer> repeatEachSound;
    private  int stop_timer;
    private  boolean betweenTowTime =false;
    public int getSeconds;
    public int Length;
    public MediaPlayer[] allSounds;
    private AlarmManager alarmManager;
    private int lengtListSound;
    SharedPreferencesUtils globalSharedPreferences;

    public Alarm2(Context context) {
        super();
        this.context = context;
        Log.i("HERE", "here service created!");
    }

    public Alarm2(){

    }
//getBaseContext()

    private void installation(){
        globalSharedPreferences = new SharedPreferencesUtils(this);
        currentSound = 0;
        quiteSound = 1;
        counterSound = 0;
        Length = 6;
        lengtListSound = 7;
        allSounds = new MediaPlayer[lengtListSound];
        selectedSound = new ArrayList<MediaPlayer>();
        repeatEachSound = new ArrayList<Integer>();
        allSounds[0] = MediaPlayer.create(this, R.raw.a1);
        allSounds[1] = MediaPlayer.create(this, R.raw.a2);
        allSounds[2] = MediaPlayer.create(this, R.raw.a3);
        allSounds[3] = MediaPlayer.create(this, R.raw.a4);
        allSounds[4] = MediaPlayer.create(this, R.raw.a5);
        allSounds[5] = MediaPlayer.create(this, R.raw.a6);
        allSounds[6] = MediaPlayer.create(this, R.raw.a7);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        installation();
        copySoundsFromSharedPrefToArraies(allSounds);
    }

    private void copySoundsFromSharedPrefToArraies(MediaPlayer[] allSounds) {
        ArrayList<Integer>repeatingEverySound= new ArrayList<Integer>(Length);
        ArrayList<Boolean>btnsSoundChecked= new ArrayList<Boolean>(Length);
        repeatingEverySound.addAll(SharedPreferencesUtils.getArrayIntPrefs(this));
        btnsSoundChecked.addAll(SharedPreferencesUtils.getArrayBooleanPrefs(this));
        for(int i = 0; i<Length; i++){
            if(repeatingEverySound != null){
                if(btnsSoundChecked.get(i) == true) {
                    selectedSound.add(allSounds[i]);
                    repeatEachSound.add(repeatingEverySound.get(i));
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags , int startId) {
        super.onStartCommand(intent, flags, startId);
       //get the attached extras from the intent
          //we should use the same key as we used to attach the data.
         //  context= getApplicationContext();
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stoptimertask();
    }
    /*
     Log.i("EXIT", "ondestroy!");
     Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
     sendBroadcast(broadcastIntent);
     stoptimertask();
   */


    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                loop += 1;
                endCount = TimeUtils.getTimeInMinutes(getApplicationContext());
                if(loop == endCount * 60){
                    step += 1;
                    loop = 0;
                   excute();
                }
            }
        };
     }

    public void excute(){
        stop_timer = SharedPreferencesUtils.getTimes(getApplicationContext()).getStopTimer();
        boolean currenTime = TimeUtils.compareBetweenTwoTime( SharedPreferencesUtils.getTimes(this).getHour_start(),
                SharedPreferencesUtils.getTimes(this).getMinute_start(),
                SharedPreferencesUtils.getTimes(this).getHour_end(),SharedPreferencesUtils.getTimes(this).getMinute_end());
        if(stop_timer == 1){
            if(currenTime == betweenTowTime){

            }
        }else{
            if(selectedSound.size()>0){
                if(selectedSound.get(currentSound) != null) {
                    playSounds(selectedSound.get(currentSound));
                }
            } else{
                Toast.makeText(context, "لم تقم باختيار بعض العناصر", Toast.LENGTH_LONG).show();
            }
        }
    }

    private  void playSounds(MediaPlayer key) {
        selectedSound.get(currentSound).start();
        selectedSound.get(currentSound).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (repeatEachSound.get(currentSound) != 0) {
                    if (quiteSound != 0) {
                        counterSound += 1;
                        if (counterSound == repeatEachSound.get(currentSound)) {
                            counterSound = 0;
                            quiteSound = 0;
                        }
                        playSounds(selectedSound.get(currentSound));
                    } else {
                        currentSound += 1;
                        if (currentSound < selectedSound.size())
                            playSounds(selectedSound.get(currentSound));
                        else
                            currentSound = 0;
                        quiteSound = 1;
                    }
                }else{
                    currentSound += 1;
                    if(currentSound<selectedSound.size())
                        playSounds(selectedSound.get(currentSound));
                    else
                        currentSound = 0;
                    quiteSound = 1;
                 }
             }
         });
     }

    public void stoptimertask(){
      if(timer != null) {
            timer.cancel();
            timer = null;
         }
        if(selectedSound.get(currentSound).isPlaying()) {
         selectedSound.get(currentSound).pause();
       }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
}