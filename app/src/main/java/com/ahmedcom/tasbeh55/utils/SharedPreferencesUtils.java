package com.ahmedcom.tasbeh55.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.ahmedcom.tasbeh55.models.Times;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtils {

    public final static String PREFS_NAME = "appname_prefs";
    //private static SharedPreferencesUtils instance;
    static final String mapKey = "map";
     private Context context;

    public SharedPreferencesUtils(Context context){
        this.context = context;
    }

    public static void setServiceOnOff(Context context,Boolean value){
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        // Boolean statusLocked = prefs.edit().putBoolean("locked", value).commit();
       prefs.edit().putBoolean("locked", value).apply();
    }

    public static Boolean getServiceOnOff(Context context){
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
       Boolean yourLocked = prefs.getBoolean("locked", false);
      return  yourLocked;
    }

    public static void setArrayIntPrefs(ArrayList<Integer> array, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("arrayNameInt", array.size());
        for(int i=0;i<array.size();i++)
            editor.putInt("arrayNameInt" + i, array.get(i));
        editor.apply();
    }

    public static ArrayList<Integer> getArrayIntPrefs(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt("arrayNameInt", 0);
        ArrayList<Integer> array = new ArrayList<>(size);
        for(int i=0;i<size;i++)
            array.add(prefs.getInt("arrayNameInt"+i, 0));
        return array;
    }

    public static void setArrayBooleanPrefs(ArrayList<Boolean> array, Context mContext) {
     SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
      SharedPreferences.Editor editor = prefs.edit();
       editor.putInt("arrayNameBool", array.size());
        for(int i=0;i<array.size();i++)
          editor.putBoolean("arrayNameBool" + i, array.get(i));
           editor.apply();
    }

    public static ArrayList<Boolean> getArrayBooleanPrefs(Context mContext) {
      SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt("arrayNameBool", 0);
          ArrayList<Boolean> array = new ArrayList<Boolean>(size);
           for(int i=0;i<size;i++)
          array.add(prefs.getBoolean("arrayNameBool"+i, false));
        return array;
    }

    public static void saveTimes(Context con , Times times){
        Gson gson = new Gson();
        String var ="variable";
        String stringUser = gson.toJson(times);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
        prefs.edit().putString(var, stringUser).apply();
    }

    public static Times getTimes(Context con){
      Times times = new Times();
        String var ="variable";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
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




















/*
    public void setInt(Context con,String key, int value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public  int getInt(Context con,String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
        return prefs.getInt(key , 0);
    }

    public void setStr(String key, String value) {
        SharedPreferences sharedPref = App.getAppContext().getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME , 0);
        return prefs.getString(key ,"DNF");
    }

    public  void setBool(String key , boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public  boolean getBool(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME , 0);
        return prefs.getBoolean(key,false);
    }
    */
/*

    public  void saveMap(Map<String, Integer> inputMap) {
        SharedPreferences pSharedPref = context.getSharedPreferences("Variables" , MODE_PRIVATE);
        if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove(mapKey).apply();
            editor.putString(mapKey, jsonString);
            editor.commit();
        }
    }


    public  Map< String , Integer > loadMap() {
        Map<String, Integer> outputMap = new HashMap<>();
        SharedPreferences pSharedPref = context.getSharedPreferences("Variables", MODE_PRIVATE);
        try{
            if(pSharedPref != null) {
                String jsonString = pSharedPref.getString(mapKey, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    outputMap.put(key,(Integer)jsonObject.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }




    public void saveOject(Times objTimes){
        SharedPreferences mPrefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("MyObject", objectToString(objTimes));
        prefsEditor.commit();
    }

    public Times getfromOject(){
      SharedPreferences mPrefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
       String value= mPrefs.getString("MyObject", "");
        Times obj = stringToObjectS(value);
      return obj;
    }

    public <T extends Serializable>T stringToObjectS(String string) {
        byte[] bytes = Base64.decode(string, 0);
        T object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            object = (T) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }


    public  String objectToString(Serializable object){
        String encoded = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            encoded = new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encoded;
    }
*/
