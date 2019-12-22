package com.ahmedco.tasbeh_5.activities;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedco.tasbeh_5.R;
import com.ahmedco.tasbeh_5.utils.DataProccessor;
import com.ahmedco.tasbeh_5.utils.KSP;
import com.ahmedco.tasbeh_5.utils.S_pref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static android.media.MediaExtractor.MetricsConstants.FORMAT;
import static com.ahmedco.tasbeh_5.activities.ListAzcarActivity.dataProccessor;

/**
 * Define a simple widget that shows the Wiktionary "Word of the day." To build
 * an update we spawn a background {@link Service} to perform the API queries.
 */
// https://android-developers.googleblog.com/2009/04/introducing-home-screen-widgets-and.html

public class WordWidget extends AppWidgetProvider {

    //KSP ksp_bool2;
    public static final String ACTION_TEXT_CHANGED = "com.ahmedco.tasbeh_5.activities.TEXT_CHANGED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
     // DataProccessor  dataProccessor1 = new DataProccessor(context);
       /*
       for(int i=0; i<6; i++) {

       }
      */
    }


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i("Server","onEnabled");
        // context.startService(new Intent(context.getApplicationContext() , UpdateService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i("Server","onDisabled");
        //Log.i("Server","onDisabled");
        //context.stopService(new Intent(context.getApplicationContext() , UpdateService.class));
    }

    public static class UpdateService extends Service {

        List<MediaPlayer> arrSelectSound2;
        List<Integer> arrSelectLoopSounds;
        public int j = 0;
        public int quiteLoop = 1;
        public int count = 0;


        @Override
        public void onCreate() {
          arrSelectSound2 = new ArrayList<MediaPlayer>();
           arrSelectLoopSounds = new ArrayList<Integer>();
          super.onCreate();
            Log.i("Server","onCreate");
            Log.v("trace_","onCreate");
            final MediaPlayer mp1 = (MediaPlayer) MediaPlayer.create(this, R.raw.a1);
            final MediaPlayer mp2 = (MediaPlayer) MediaPlayer.create(this, R.raw.a2);
            final MediaPlayer mp3 = (MediaPlayer) MediaPlayer.create(this, R.raw.a3);
            final MediaPlayer mp4 = (MediaPlayer) MediaPlayer.create(this, R.raw.a4);
            final MediaPlayer mp5 = (MediaPlayer) MediaPlayer.create(this, R.raw.a5);
            final MediaPlayer mp6 = (MediaPlayer) MediaPlayer.create(this, R.raw.a6);
            final MediaPlayer mp7 = (MediaPlayer) MediaPlayer.create(this, R.raw.a7);
            MediaPlayer[] arrSelectSound = { mp1,mp2,mp3,mp4,mp5,mp6,mp7 };
            int[] arrayLoopSound = new int[6];
            for(int i=0; i<6; i++){
              // d.setBool("soundsBoolean"+i,soundsBoolean[i]);
               Log.i("trace3","Yes_2"+ dataProccessor.getInt("loopSound"+i));
                // Log.v("trace3","Yes_2"+ dataProccessor.getBool("soundsBoolean"+i));
                arrayLoopSound[i] =  dataProccessor.getInt("loopSound"+i);
                if(dataProccessor.getBool("soundsBoolean"+i) == true){
                  arrSelectSound2.add(arrSelectSound[i]);
                    arrSelectLoopSounds.add(arrayLoopSound[i]);
                }else{
                 }
             }
          startTimer(this);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
           // Log.i("trace","onStartCommand"+intent.getBooleanArrayExtra("data").length);
           // ArrayList<String> transferData = intent.getStringArrayListExtra("data");
            return START_STICKY;//START_REDELIVER_INTENT;
        }

         /**
         * Build a widget update to show the current Wiktionary
         * "Word of the day." Will block until the online API returns.
         */

        public RemoteViews buildUpdate(final Context context, int h,int m,int s) {
            // Pick out month names from resources
            Resources res = context.getResources();
            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
             Intent defineIntent = new Intent(Intent.ACTION_VIEW);
             PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* no requestCode */, defineIntent, 0 /* no flags */);
             // updateViews.setOnClickPendingIntent(R.id.textView, pendingIntent);
             updateViews.setTextViewText(R.id.textView, "ساعة"+ h +" و "+m +"دقيقة "+ "و"+ s+"ثانية");
           return updateViews;
        }

        private void startTimer(final Context context){
            // totoal Seconds for all time : 2 hour = 7200,
            new CountDownTimer(40 * 1000 + 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                   // updateWidgets(context,millisUntilFinished);
                    //Log.i("tick00","")
                    int seconds = (int) (millisUntilFinished / 1000);
                    int hours = seconds / (60 * 60);
                    int tempMint = (seconds - (hours * 60 * 60));
                    int minutes = tempMint / 60;
                    seconds = tempMint - (minutes * 60);
                    Log.i("Timer0 "," H "+hours +" M "+minutes +" "+seconds );
                    //Log.i("Timer0 ","Hours: "+hours+" T: "+minutes+"  "+seconds);
                    RemoteViews updateViews = buildUpdate(context,hours,minutes,seconds);
                    // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                    // Push update for this widget to the home screen
                    ComponentName thisWidget = new ComponentName(context, WordWidget.class);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    manager.updateAppWidget(thisWidget, updateViews);
                }
                public void onFinish(){
                   setsound(arrSelectSound2.get(j));
                  startTimer(context);
                }
            }.start();
        }
        @Override
        public IBinder onBind(Intent intent) {
          // We don't need to bind to this service
            Log.i("trace","IBinder");
          return null;
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
    }
}



