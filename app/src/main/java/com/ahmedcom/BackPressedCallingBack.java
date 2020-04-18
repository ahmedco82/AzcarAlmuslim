package com.ahmedcom;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.interfaces.CallingBack;
import com.ahmedcom.tasbeh55.interfaces.HasBack;
import com.ahmedcom.tasbeh55.ui.activities.ListAzcarActivity;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;

import java.lang.ref.WeakReference;

public class BackPressedCallingBack implements CallingBack{


    private final WeakReference<Activity> mActivity;

    public BackPressedCallingBack(Activity activity){
        this.mActivity = new WeakReference<>(activity);
    }


    @Override
    public void callingBackActivity(View v){
        new Handler().postDelayed(new Runnable(){
         @Override
            public void run(){
             Activity activity = mActivity.get();
                if (activity instanceof HasBack && !(activity instanceof ListAzcarActivity)){
                    activity.onBackPressed();
                }else if(activity instanceof ListAzcarActivity){
                    if(SharedPreferencesUtils.isOneORMoreSelected(activity)) {
                        activity.onBackPressed();
                    }else {
                       Toast.makeText(activity, R.string.choosemore, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        },100);
    }
}




/*
 if (activity instanceof ListAzcarActivity){
            activity.onBackPressed();
        }
        else if (activity instanceof TimeSettingsActivity){
          activity.onBackPressed();
       }
        else if (activity instanceof ListAzcarActivity){
         //Log.i("Test_activity","Don");
          /*
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("اذا اردت ان يعمل التطبيق فى الخلفية يجب ان تضغط على زر انتقل");
            builder.setPositiveButton("رجوع", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialoginterface, int i) {
                    //closeApp();
                    Intent serviceIntent = new Intent(activity, TimeSettingsActivity.class);
                    activity.startService(serviceIntent);
                    builder.setCancelable(false);
                }
            });
            builder.setNegativeButton("إغلاق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialoginterface, int i) {
                    builder.setCancelable(false);
                }
             });
             builder.show();
            */
// activity.onBackPressed();

