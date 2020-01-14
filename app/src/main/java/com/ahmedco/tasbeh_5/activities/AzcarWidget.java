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