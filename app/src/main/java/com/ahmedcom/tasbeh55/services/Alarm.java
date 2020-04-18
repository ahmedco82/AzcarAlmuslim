package com.ahmedcom.tasbeh55.services;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.ahmedcom.tasbeh55.utils.PlayerViewHandlerUtils;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TimeUtils;
import java.util.List;

public class Alarm  extends JobIntentService {

    public int currentSound;
    public int quiteSound;
    public int counterSound;
    int endCount = 2;
    int loop = 0;
    int step = 0;
    private  int stop_timer;
    private  boolean betweenTowTime =true;
    public int Length;
    private int lengtListSound;
    SharedPreferencesUtils globalSharedPreferences;
    PlayerViewHandlerUtils playerViewHandler;
    MediaPlayer m = null;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, Alarm.class, 123, work);
     }

       @Override
       public void onCreate() {
        super.onCreate();
          installation ();
           playerViewHandler = new PlayerViewHandlerUtils(getApplicationContext());
       }
    private void installation(){
        globalSharedPreferences = new SharedPreferencesUtils(getApplicationContext());
        currentSound = 0;
        quiteSound = 1;
        counterSound = 0;
        Length = 6;
        lengtListSound = 7;
    }

     public void excute(){
       stop_timer = SharedPreferencesUtils.getTimes(getApplicationContext()).getStopTimer();
         boolean currenTime= TimeUtils.isCurrentTimeBtween(TimeUtils.convert24HourTime(TimeUtils.getTimeStartWithAmPm(getApplicationContext())), TimeUtils.convert24HourTime(TimeUtils.getTimeEndWithAmPm(getApplicationContext())));
         if(stop_timer == 1){
          if(currenTime != betweenTowTime){
             playerViewHandler.playGrupSounds(m);
          }
        }else{
            playerViewHandler.playGrupSounds(m);
         }
      }

      @Override
      protected void onHandleWork(@NonNull Intent intent) {
       boolean shouldContinue = true;
          while(shouldContinue){
              loop += 1;
               if(!SharedPreferencesUtils.getServiceOnOff(getApplicationContext())){
                   shouldContinue = false;
                   break;
               }
               endCount = TimeUtils.getTimeInMinutes(getApplicationContext());
                if(loop == endCount * 60){
                   step += 1;
                   loop = 0;
                   excute();
             }
             if(isStopped())return;
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onDestroy(){
      super.onDestroy();
        if(playerViewHandler.selectedSound.get(currentSound).isPlaying()) {
            playerViewHandler.selectedSound.get(currentSound).stop();
        }
    }

    @Override
    public boolean onStopCurrentWork() {
        if(playerViewHandler.selectedSound.get(currentSound).isPlaying()) {
            playerViewHandler.selectedSound.get(currentSound).stop();
        }
        return super.onStopCurrentWork();
    }
}