package com.ahmedco.tasbeh_5.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataSharedPreferences {

    private static Context context;
    static final String mapKey = "map";


    public DataSharedPreferences(Context context){

        this.context = context;
    }


    public final static String PREFS_NAME = "appname_prefs";


    public static void setInt(String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME ,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public static int getInt(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getInt(key , 0);
    }


    public static void setStr(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME , 0);
        return prefs.getString(key ,"DNF");
    }

    public static void setBool(String key , boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public static boolean getBool(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME , 0);
        return prefs.getBoolean(key,false);
    }



    public static void saveMap(Map<String, Integer> inputMap) {
        SharedPreferences pSharedPref = context.getSharedPreferences("Variables" , Context.MODE_PRIVATE);
        if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove(mapKey).apply();
            editor.putString(mapKey, jsonString);
            editor.commit();
        }
    }


    public static Map< String , Integer > loadMap() {
        Map<String, Integer> outputMap = new HashMap<>();
        SharedPreferences pSharedPref = context.getSharedPreferences("Variables", Context.MODE_PRIVATE);
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
}