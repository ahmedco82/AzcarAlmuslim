package com.ahmedco.tasbeh_5.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class S_pref {

       Context context;

    public S_pref(Context context) {
        this.context=context;
    }

    public void packagesharedPreferences(SharedPreferences shared, ArrayList arrPackage) {
        SharedPreferences.Editor editor = shared.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(arrPackage);
        editor.putStringSet("DATE_LIST",set);
        editor.apply();
        //Log.d("storesharedPreferences",""+set);
    }

    public void retriveSharedValue(SharedPreferences shared , ArrayList  arrPackage) {
        Set<String> set = shared.getStringSet("DATE_LIST", null);
        arrPackage.addAll(set);
        Log.v("trace00000_000",""+set);
        // Log.d("retrivesharedPreferences",""+set);
    }


}
