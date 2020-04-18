package com.ahmedcom.tasbeh55.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.models.Times;
import com.ahmedcom.tasbeh55.ui.activities.ListAzcarActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SharedPreferencesUtils {

     public final static String PREFS_NAME = "appname_prefs";
     static final String mapKey = "map";
     private Context context;

    public SharedPreferencesUtils(Context context){
        this.context = context;
    }

    public static void setServiceOnOff(Context context,Boolean value){
        final WeakReference<Context> mContext =  new WeakReference<>(context);
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.get());
       prefs.edit().putBoolean("locked", value).apply();
    }

    public static boolean isServiceRunning(Class<?> serviceClass , Context context) {
        final WeakReference<Context> mContext =  new WeakReference<>(context);

        ActivityManager manager = (ActivityManager)  mContext.get().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOneORMoreSelected(Context context){
        boolean checkArray = false;
        final WeakReference<Context> mContext =  new WeakReference<>(context);
        for(Boolean object:getArrayBooleanPrefs(mContext.get())) {
            if (object) {
                checkArray = true;
                break;
            } else {
                checkArray = false;
            }
        }
        return checkArray;
    }

    public static boolean getLengthSelectedSound(Context context) {
        final WeakReference<Context> mContext =  new WeakReference<>(context);
        int count = 0;
        for (int i = 0; i<getArrayBooleanPrefs(mContext.get()).size(); i++) {
            if (getArrayBooleanPrefs(context).get(i)) {
                count = count + 1;
            }
        }
        return (count >= 2) ? false : true;
    }


    public static Boolean getServiceOnOff(Context context){
        final WeakReference<Context> mContext =  new WeakReference<>(context);
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.get());
       Boolean yourLocked = prefs.getBoolean("locked", false);
      return  yourLocked;
    }

    public static void setArrayIntPrefs(ArrayList<Integer> array, Context context) {
        final WeakReference<Context> mContext =  new WeakReference<>(context);
        SharedPreferences prefs = mContext.get().getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("arrayNameInt", array.size());
        for(int i=0;i<array.size();i++)
            editor.putInt("arrayNameInt" + i, array.get(i));
        editor.apply();
    }

    public static ArrayList<Integer> getArrayIntPrefs(Context context) {
        final WeakReference<Context> mContext =  new WeakReference<>(context);
        SharedPreferences prefs = mContext.get().getSharedPreferences("preferencename", 0);
        int size = prefs.getInt("arrayNameInt", 0);
        ArrayList<Integer> array = new ArrayList<>(size);
        for(int i=0;i<size;i++)
            array.add(prefs.getInt("arrayNameInt"+i, 0));
        return array;
    }

    public static void setArrayBooleanPrefs(ArrayList<Boolean> array, Context context) {

     final WeakReference<Context> mContext =  new WeakReference<>(context);
     SharedPreferences prefs = mContext.get().getSharedPreferences("preferencename", 0);
      SharedPreferences.Editor editor = prefs.edit();
       editor.putInt("arrayNameBool", array.size());
        for(int i=0;i<array.size();i++)
          editor.putBoolean("arrayNameBool" + i, array.get(i));
           editor.apply();
    }

    public static ArrayList<Boolean> getArrayBooleanPrefs(Context context) {
       final WeakReference<Context> mContext =  new WeakReference<>(context);
        SharedPreferences prefs = mContext.get().getSharedPreferences("preferencename", 0);
        int size = prefs.getInt("arrayNameBool", 0);
          ArrayList<Boolean> array = new ArrayList<Boolean>(size);
           for(int i=0;i<size;i++)
          array.add(prefs.getBoolean("arrayNameBool"+i, false));
        return array;
    }

    public static void saveTimes(Context context , Times times){
        final WeakReference<Context> mContext =  new WeakReference<>(context);
        Gson gson = new Gson();
        String var ="variable";
        String stringUser = gson.toJson(times);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.get());
        prefs.edit().putString(var, stringUser).apply();
    }



    public static ArrayList<MediaPlayer> filterSelectedSound (Context context) {
     final WeakReference<Context> mContext =  new WeakReference<>(context);
     int[] soundsRowsId = new int[]{R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5, R.raw.a6};
       MediaPlayer mPlayer;
        ArrayList<Integer>repeatingEverySound= new ArrayList<Integer>();
         ArrayList<MediaPlayer> selectedSound = new ArrayList<MediaPlayer>();
           for(int i = 0; i <getArrayBooleanPrefs(mContext.get()).size(); i++) {
              if(getArrayBooleanPrefs(mContext.get()).get(i) == true) {
                  mPlayer = MediaPlayer.create(context, soundsRowsId[i]);
                  selectedSound.add(mPlayer);
                }
         }
       return new ArrayList<MediaPlayer>(selectedSound);
    }

    public static ArrayList<Integer> filterRepeatingSound (Context context) {
        final WeakReference<Context> mContext =  new WeakReference<>(context);
        ArrayList<Integer>repeatingEverySound= new ArrayList<Integer>();
        for(int i = 0; i < getArrayBooleanPrefs(mContext.get()).size(); i++) {
            if(getArrayBooleanPrefs(mContext.get()).get(i) == true) {
                repeatingEverySound.add(getArrayIntPrefs(mContext.get()).get(i));
            }
        }
        return new ArrayList<Integer>(repeatingEverySound);
    }

    public static Times getTimes(Context context){
      Times times = new Times();
        String var ="variable";
        final WeakReference<Context> mContext =  new WeakReference<>(context);
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.get());
          String data = prefs.getString(var, "");
            List<String> values = new ArrayList<String>();
             try{
              JSONObject obj = new JSONObject(data.toString());
               JSONObject jsonObject = new JSONObject(data);
                Iterator<String> keysItr = jsonObject.keys();
                 for(Iterator<String> iter = jsonObject.keys(); iter.hasNext();) {
                  String key = iter.next();
                   values.add(key);
                 }
          if(obj != null){
             times.setStopTimer(Integer.parseInt(obj.getString("stopTimer")));
              times.setEveryTime(Integer.parseInt(obj.getString("everyTime")));
                if(times.getStopTimer()==1){
                  times.setHour_start(Integer.parseInt(obj.getString("hour_start")));
                   times.setHour_end(Integer.parseInt(obj.getString("hour_end")));
                     times.setMinute_start(Integer.parseInt(obj.getString("minute_start")));
                      times.setMinute_end(Integer.parseInt(obj.getString("minute_end")));
                      times.setStart_AM_PM(Integer.parseInt(obj.getString("start_AM_PM")));
                         times.setEnd_AM_PM(Integer.parseInt(obj.getString("end_AM_PM")));
                    }
              }
            }catch (JSONException e) {
           e.printStackTrace();
         }
        return times;
    }
}