package com.ahmedcom;

import android.app.Activity;
import android.view.View;

import com.ahmedcom.tasbeh55.ui.activities.ListAzcarActivity;
import com.ahmedcom.tasbeh55.ui.activities.TimeSettingsActivity;
import com.ahmedcom.tasbeh55.interfaces.CallingBack;

public class BackPressedCallingBack implements CallingBack{

    private final Activity activity;

    public BackPressedCallingBack(Activity activity){
        this.activity = activity;
    }

    @Override
    public void callingBackActivity(View v){
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
        }
    }
}

