package com.ahmedcom.tasbeh55.services;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;


//https://stackoverflow.com/questions/4134203/how-to-use-registerreceiver-method


 // https://stackoverflow.com/questions/52079212/ontaskremoved-in-android-o

public class Alarm  extends JobIntentService {

    public  int currentSound;
    public  int quiteSound;
    public  int counterSound;
    int endCount = 2;
    int loop = 0;
    int step = 0;
    private  List<MediaPlayer> selectedSound;
    private  List<Integer> repeatEachSound;
    private  int stop_timer;
    private  boolean betweenTowTime =false;
    public int getSeconds;
    public int Length;
    public MediaPlayer[] allSounds;
    private AlarmManager alarmManager;
    private int lengtListSound;
    private Context context;
    SharedPreferencesUtils globalSharedPreferences;


    private static final String TAG = "test1:";
     // sound0

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, Alarm.class, 123, work);
     }

       @Override
       public void onCreate() {
        super.onCreate();
          installation ();
           copySoundsFromSharedPrefToArraies(allSounds);
           Log.e(TAG, "onCreate");
       }

    private void installation(){
        globalSharedPreferences = new SharedPreferencesUtils(getApplicationContext());
        currentSound = 0;
        quiteSound = 1;
        counterSound = 0;
        Length = 6;
        lengtListSound = 7;
        allSounds = new MediaPlayer[lengtListSound];
        selectedSound = new ArrayList<MediaPlayer>();
        repeatEachSound = new ArrayList<Integer>();
        allSounds[0] = MediaPlayer.create(getApplicationContext(), R.raw.a1);
        allSounds[1] = MediaPlayer.create(getApplicationContext(), R.raw.a2);
        allSounds[2] = MediaPlayer.create(getApplicationContext(), R.raw.a3);
        allSounds[3] = MediaPlayer.create(getApplicationContext(), R.raw.a4);
        allSounds[4] = MediaPlayer.create(getApplicationContext(), R.raw.a5);
        allSounds[5] = MediaPlayer.create(getApplicationContext(), R.raw.a6);
        allSounds[6] = MediaPlayer.create(getApplicationContext(), R.raw.a7);
    }


    private void copySoundsFromSharedPrefToArraies(MediaPlayer[] allSounds) {
      ArrayList<Integer>repeatingEverySound= new ArrayList<Integer>(Length);
       ArrayList<Boolean>btnsSoundChecked= new ArrayList<Boolean>(Length);
        repeatingEverySound.addAll(SharedPreferencesUtils.getArrayIntPrefs(getApplicationContext()));
          btnsSoundChecked.addAll(SharedPreferencesUtils.getArrayBooleanPrefs(getApplicationContext()));
            for(int i = 0; i<Length; i++){
              if(repeatingEverySound != null){
                if(btnsSoundChecked.get(i) == true) {
                 selectedSound.add(allSounds[i]);
                 repeatEachSound.add(repeatingEverySound.get(i));
              }
          }
       }
     }

     public void excute(){
      stop_timer = SharedPreferencesUtils.getTimes(getApplicationContext()).getStopTimer();
        boolean currenTime = TimeUtils.compareBetweenTwoTime( SharedPreferencesUtils.getTimes(getApplicationContext()).getHour_start(), SharedPreferencesUtils.getTimes(getApplicationContext()).getMinute_start(), SharedPreferencesUtils.getTimes(getApplicationContext()).getHour_end(),SharedPreferencesUtils.getTimes(getApplicationContext()).getMinute_end());
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
      @Override
      protected void onHandleWork(@NonNull Intent intent) {
       boolean shouldContinue = true;
          while(shouldContinue){
              loop += 1;
               if(!SharedPreferencesUtils.getServiceOnOff(getApplicationContext())){
                   shouldContinue = false;
                   Log.d("Is_Stoped0:", "Don");
                   break;
               }
               endCount = TimeUtils.getTimeInMinutes(getApplicationContext());
              Log.i("Step :", "Don");
                if(loop == endCount * 60){
                   step += 1;
                   loop = 0;
                   Log.e(TAG, "onHandleWork0 "+step);
                   excute();
             }
             if(isStopped())return;
            SystemClock.sleep(1000);
        }
    }


    @Override
    public void onDestroy(){
      super.onDestroy();
       Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork");
        return super.onStopCurrentWork();
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
}











       /*
             loop += 1;
             // endCount = TimeUtils.getTimeInMinutes(new SharedPreferencesUtils(context));
              if (loop == 1) {
                step += 1;
                loop = 0;
                selectedSound.start();
                //   mainBackground.excute();
            }
          */







    /*
        Toast.makeText(getApplicationContext(), "اذكر الله", Toast.LENGTH_LONG).show();
        this.context=context;
        selectedSound = (MediaPlayer) MediaPlayer.create(getApplicationContext(), R.raw.a1);
        selectedSound.start();

     */
/*
    public void startAlarm(){
      Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
         alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
          //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 1 * 60 * 1000, pendingIntent);
          //ELAPSED_REALTIME_WAKEUP
           if(Build.VERSION.SDK_INT <23){
             if(Build.VERSION.SDK_INT >= 19){
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);
            }
            else{
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);
            }
        }
        else{
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP , SystemClock.elapsedRealtime(), pendingIntent);
        }
    }
/*
    public void stopAlarm(){
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent){
        startAlarm();
    }



   /*
    public class AlarmReceiver extends BroadcastReceiver {
      @Override
        public void onReceive(Context context, Intent arg1) {
          Toast.makeText(context, "Started", Toast.LENGTH_LONG).show();
           selectedSound.start();
            loop += 1;
            // endCount = TimeUtils.getTimeInMinutes(new SharedPreferencesUtils(context));
            if(loop == 1){
                step += 1;
                loop = 0;
                Toast.makeText(context, "اذكر الله", Toast.LENGTH_LONG).show();
                selectedSound = (MediaPlayer) MediaPlayer.create(context, R.raw.a1);
                selectedSound.start();
                //   mainBackground.excute();
            }
        }
    */
