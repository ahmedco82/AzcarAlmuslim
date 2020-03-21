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
    }
}

