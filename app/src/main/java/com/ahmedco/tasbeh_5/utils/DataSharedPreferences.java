package com.ahmedco.tasbeh_5.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.ahmedco.tasbeh_5.models.Times;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

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
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
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
    public static Map< String , Integer > loadMap() {
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


    public static void saveOject(Times objTimes){
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

    public static <T extends Serializable> T stringToObjectS(String string) {
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

    public static String objectToString(Serializable object) {
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

}