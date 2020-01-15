package com.ahmedco.tasbeh_5.activities;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ahmedco.tasbeh_5.R;
import com.ahmedco.tasbeh_5.utils.DataSharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class UpdateService extends Service {

    private List<MediaPlayer> selectedSound;
    private List<Integer> repeatEachSound;
    public int currentSound, quiteSound, counterSound, getSeconds, Length;
    public MediaPlayer[] allSounds;
    private CountDownTimer countDownTimer;
    private DataSharedPreferences soundsSharedPref;
    private int stop_timer, lengtListSound;
    private int seconds, hours, tempMint, minutes;
    private Calendar startCalendar, endCalendar, currentCalendar;

    @Override
    public void onCreate() {
        super.onCreate();
        initiTimeValues();
        initiSounds();
        if (countDownTimer == null) {
            copySoundsFromSharedPrefToArraies(allSounds);
            startTimer(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.stopService(new Intent(this, UpdateService.class));
        cancelTimer();
    }

    private void initiTimeValues() {
        soundsSharedPref = new DataSharedPreferences(this);
        getSeconds = 0;
        Length = 6;
        lengtListSound = 7;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();
        getSeconds = getTimeInSeconds();
    }

    private void initiSounds() {
        currentSound = 0;
        quiteSound = 1;
        counterSound = 0;
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


    private void copySoundsFromSharedPrefToArraies(MediaPlayer[] allSounds) {
        int[] repeatingEverySound;
        repeatingEverySound = new int[Length];
        for (int i = 0; i < Length; i++) {
            repeatingEverySound[i] = soundsSharedPref.getInt("loopSound" + i);
            if (repeatingEverySound != null) {
                if (soundsSharedPref.getBool("btnsSoundChecked" + i) == true) {
                    selectedSound.add(allSounds[i]);
                    repeatEachSound.add(repeatingEverySound[i]);
                }
            } else {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    Toast.makeText(this, "مشكله فى تحميل البيانات", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private int getTimeInSeconds() {
        int Seconds = soundsSharedPref.getfromOject().getEveryTime();
        return Seconds + 1 * 60;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Log.i("trace","onStartCommand"+intent.getBooleanArrayExtra("data").length);
        // ArrayList<String> transferData = intent.getStringArrayListExtra("data");
        return START_STICKY;
    }

    public RemoteViews buildUpdate(final Context context, int h, int m, int s) {
        // Pick out month names from resources
        Resources res = context.getResources();
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent defineIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, defineIntent, 0);
        updateViews.setTextViewText(R.id.tV_timeElapsed, "" + h + " ساعة " + " و " + m + " دقيقة " + "و" + s + " ثانية ");
        // Create an Intent to launch ExampleActivity
        return updateViews;
    }

    private void startTimer(final Context context) {
        // totoal Seconds for all time : 2 hour = 7200, getSeconds
        countDownTimer = new CountDownTimer(getSeconds * 1000 + 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                seconds = (int) (millisUntilFinished / 1000);
                hours = seconds / (60 * 60);
                tempMint = (seconds - (hours * 60 * 60));
                minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);
                initiViewWidget(context, hours, minutes, seconds);
            }

            public void onFinish() {
                stop_timer = soundsSharedPref.getfromOject().getStopTimer();
                boolean checkCurrenTime = checkCurrentTimeBetweenTwoValues(
                        soundsSharedPref.getfromOject().getHour_start(),
                        soundsSharedPref.getfromOject().getMinute_start(),
                        soundsSharedPref.getfromOject().getHour_end(),
                        soundsSharedPref.getfromOject().getMinute_end());
                if (stop_timer == 1) {
                    if (checkCurrenTime == false) {
                        playSounds(selectedSound.get(currentSound));
                    }
                } else {
                    playSounds(selectedSound.get(currentSound));
                }
                startTimer(context);
            }
        }.start();
    }

    private void initiViewWidget(Context context, int hours, int minutes, int seconds) {
        ComponentName thisWidget = new ComponentName(context, AzcarWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        RemoteViews updateViews = buildUpdate(context, hours, minutes, seconds);
        manager.updateAppWidget(thisWidget, updateViews);
    }

    private void cancelTimer() {
        countDownTimer.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private void playSounds(MediaPlayer key) {
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
                    if (currentSound < selectedSound.size())
                        playSounds(selectedSound.get(currentSound));
                    else
                        currentSound = 0;
                    quiteSound = 1;
                }
            }
        });
    }

    // Build ;

    private boolean checkCurrentTimeBetweenTwoValues(int hStart, int mStart, int hEnd, int mEnd) {
        if (hStart > 12) hStart = hStart - 12;
        if (hEnd > 12) hEnd = hEnd - 12;
        startCalendar.setTimeZone(TimeZone.getDefault());
        startCalendar.set(Calendar.HOUR, hStart);
        startCalendar.set(Calendar.MINUTE, mStart);
        endCalendar.setTimeZone(TimeZone.getDefault());
        endCalendar.set(Calendar.HOUR, hEnd);
        endCalendar.set(Calendar.MINUTE, mEnd);
        if (currentCalendar.getTimeInMillis() > startCalendar.getTimeInMillis() && currentCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }
    }
}