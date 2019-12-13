package com.ahmedco.tasbeh_5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by khaled on 8/4/16.
 */

public class KSP {

    private Context _context;
    private String _key;


    public KSP(Context context , String key ){
        this._context = context;
        this._key = key;
    }


    public ArrayList GetAll(){
        ArrayList arr = new ArrayList();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        Map<String,?> keys_all = preferences.getAll();
        SharedPreferences pref = _context.getSharedPreferences(_key, _context.MODE_PRIVATE);
        Map<String,?> keys = pref.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
          //  klib.Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
            arr.add(entry.getValue());
        }
        return arr;
    }

    public void saveFor(String key , String value) {
        SharedPreferences pref = _context.getSharedPreferences(_key, _context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(key, String.valueOf(value));
            editor.commit();
    }

    public String getFor(String key ){
        SharedPreferences pref = _context.getSharedPreferences(_key, _context.MODE_PRIVATE);
        return pref.getString(key, "");
    }
    public String getFor(String key , String defValue ){
        SharedPreferences pref = _context.getSharedPreferences(_key, _context.MODE_PRIVATE);
        return pref.getString(key, defValue);
    }
    public void DeleteFor( String key ){
        SharedPreferences pref = _context.getSharedPreferences(_key, _context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(  key);
        editor.apply();
    }


//    public  Intent putExtra( String key , Serializable object , Class<?> cls)
//    {
//
//        Bundle args = new Bundle();
//        args.putSerializable(key,(Serializable) object);
//
//        Intent mainIntent = new Intent(_context,cls);
//        mainIntent.putExtra("KSP",args);
//
//        return  mainIntent;
//    }


    public Intent putExtra(String key , Object object , Class<?> cls) {
        Bundle args = new Bundle();
        args.putSerializable(key,(Serializable) object);
        Intent mainIntent = new Intent(_context,cls);
        mainIntent.putExtra("KSP",args);
        return  mainIntent;
    }

    public Serializable GETExtra(Intent intent , String key ) {
      Bundle args = intent.getBundleExtra("KSP");
       Object obj = null ;
        try{
            obj = args.getSerializable(key);
        }
        catch (Exception ex)
        {

        }
       return (Serializable) obj;
     }
     public static void openActivity(Activity act, Class<?> actTarget , Object object  ){
         String key = actTarget.getName();
         Intent mainIntent = new Intent(act,actTarget);
         if (object != null){
             Bundle args = new Bundle();
             args.putSerializable(key,(Serializable) object);
             mainIntent.putExtra("KSP",args);
         }
         act.startActivity(mainIntent);
     }

    public static void openActivityForResult(Activity act, Class<?> actTarget , Object object , int requestCode  ) {
        String key = actTarget.getName();
        Intent mainIntent = new Intent(act,actTarget);
        if (object != null) {
            Bundle args = new Bundle();
            args.putSerializable(key,(Serializable) object);
            mainIntent.putExtra("KSP",args);
        }
        act.startActivityForResult(mainIntent, requestCode );
    }
     public static Serializable getActivity(Activity actTarget ){
         Intent intent = actTarget.getIntent();
         String key = actTarget.getClass().getName();
         Bundle args = intent.getBundleExtra("KSP");
         Object obj = null ;
         try {
             obj = args.getSerializable(key);
         }
         catch (Exception ex)
         {

         }
         return (Serializable) obj;
     }
}
