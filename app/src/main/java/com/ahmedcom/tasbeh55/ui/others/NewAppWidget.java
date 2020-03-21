package com.ahmedcom.tasbeh55.ui.others;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TimeUtils;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.ALARM_SERVICE;

/**
*Implementation of App Widget functionality.
*/

public class NewAppWidget extends AppWidgetProvider {

    public static int currentSound;
    public static int quiteSound;
    public static int counterSound;
    static int endCount = 2;
    static int loop = 0;
    static int step = 0;
    private static List<MediaPlayer> selectedSound;
    private static List<Integer> repeatEachSound;
    private static int stop_timer;
    private static SharedPreferencesUtils globalSharedPreferences = SharedPreferencesUtils.getInstance();
    private static boolean betweenTowTime =false;
    public int getSeconds;
    public int Length;
    public MediaPlayer[] allSounds;
    private AlarmManager alarmManager;
    private int lengtListSound;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int imgRes, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, "" + imgRes);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    // to show/hide the water button
    public static void updateUIWidgets(Context context, AppWidgetManager appWidgetManager, int imgRes, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, imgRes, appWidgetId);
        }
    }

    private static void checkCurreTime(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
      stop_timer = globalSharedPreferences.getfromOject().getStopTimer();
       boolean currenTime = TimeUtils.compareBetweenTwoTime(globalSharedPreferences.getfromOject().getHour_start(), globalSharedPreferences.getfromOject().getMinute_start(), globalSharedPreferences.getfromOject().getHour_end(), globalSharedPreferences.getfromOject().getMinute_end());
         if(stop_timer == 1){
           if(currenTime == betweenTowTime) {
              PlayOrMsg(context, appWidgetManager, step, appWidgetIds);
             }
        } else {
            PlayOrMsg(context, appWidgetManager, step, appWidgetIds);
        }
    }

    private static void PlayOrMsg(Context context, AppWidgetManager appWidgetManager, int step, int[] appWidgetIds) {
         if(selectedSound.size()>0){
           if(selectedSound.get(currentSound) != null) {
               NewAppWidget.updateUIWidgets(context, appWidgetManager, step, appWidgetIds);
                playSounds(selectedSound.get(currentSound));
            }
         } else{
            Toast.makeText(context, "لم تقم باختيار بعض العناصر", Toast.LENGTH_LONG).show();
         }
      }

    private static void playSounds(MediaPlayer key) {
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
                } else {
                    currentSound += 1;
                    if (currentSound<selectedSound.size())
                        playSounds(selectedSound.get(currentSound));
                    else
                        currentSound = 0;
                    quiteSound = 1;
                }
            }
        });
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        initiSounds(context);
        copySoundsFromSharedPrefToArraies(allSounds);
    }

    @Override
    public void onEnabled(Context context){
        startAlarm(context);
    }

    private void startAlarm(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 1 * 60 * 1000, pendingIntent);
    }

    @Override
    public void onDisabled(Context context){
        stopAlarm(context);
    }

    public void stopAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void initiSounds(Context context) {
        currentSound = 0;
        quiteSound = 1;
        counterSound = 0;
        Length = 6;
        lengtListSound = 7;
        allSounds = new MediaPlayer[lengtListSound];
        selectedSound = new ArrayList<MediaPlayer>();
        repeatEachSound = new ArrayList<Integer>();
        allSounds[0] = MediaPlayer.create(context, R.raw.a1);
        allSounds[1] = MediaPlayer.create(context, R.raw.a2);
        allSounds[2] = MediaPlayer.create(context, R.raw.a3);
        allSounds[3] = MediaPlayer.create(context, R.raw.a4);
        allSounds[4] = MediaPlayer.create(context, R.raw.a5);
        allSounds[5] = MediaPlayer.create(context, R.raw.a6);
        allSounds[6] = MediaPlayer.create(context, R.raw.a7);
    }

    private void copySoundsFromSharedPrefToArraies(MediaPlayer[] allSounds) {
     int[] repeatingEverySound;
       repeatingEverySound = new int[Length];
        for(int i = 0; i<Length; i++) {
            repeatingEverySound[i] = globalSharedPreferences.getInt("loopSound"+i);
            if(repeatingEverySound != null) {
                if(globalSharedPreferences.getBool("btnsSoundChecked"+i) == true) {
                    selectedSound.add(allSounds[i]);
                    repeatEachSound.add(repeatingEverySound[i]);
                }
            }
        }
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NewAppWidget.class));
             loop += 1;
             endCount = TimeUtils.getTimeInMinutes();
             if(loop == endCount) {
              step += 1;
               loop = 0;
                checkCurreTime(context, appWidgetManager, appWidgetIds);
            }
        }
    }
}











/*
// Build ;
private static boolean checkCurrentTimeBetweenTwoValues(int hStart, int mStart, int hEnd, int mEnd) {
            if (hStart>12) hStart = hStart - 12;
            if (hEnd>12) hEnd = hEnd - 12;
            startCalendar.setTimeZone(TimeZone.getDefault());
            startCalendar.set(Calendar.HOUR, hStart);
            startCalendar.set(Calendar.MINUTE, mStart);
            endCalendar.setTimeZone(TimeZone.getDefault());
            endCalendar.set(Calendar.HOUR, hEnd);
            endCalendar.set(Calendar.MINUTE, mEnd);
            if (currentCalendar.getTimeInMillis()>startCalendar.getTimeInMillis() && currentCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
                return true;
            } else {
                return false;
            }
         }
    }

 */
// 1
// 2
//3
//4
//5
//10
//15
//30
//1
//2