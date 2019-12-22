package com.ahmedco.tasbeh_5.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class SavePref {

    private Context context;

    public SavePref(Context context){
        this.context = context;
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }



}