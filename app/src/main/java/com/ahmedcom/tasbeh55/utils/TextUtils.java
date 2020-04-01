package com.ahmedcom.tasbeh55.utils;
import android.content.Context;
import android.widget.EditText;
import com.ahmedcom.tasbeh55.models.Times;


public class TextUtils {

    public static String getTimeFromFormatting(Context context){
        String textTimeFrom = "";
        textTimeFrom = SharedPreferencesUtils.getTimes(context).getStart_AM_PM()==1 ? SharedPreferencesUtils.getTimes(context).getHour_start() + ":" + SharedPreferencesUtils.getTimes(context).getMinute_start() + " " + "AM":SharedPreferencesUtils.getTimes(context).getHour_start() + ":" + SharedPreferencesUtils.getTimes(context).getMinute_start() + " " + "PM";
      return textTimeFrom;
    }

    public static String getTimeToFormatting(Context context){
        String textTimeTo = "";
       textTimeTo =  SharedPreferencesUtils.getTimes(context).getStart_AM_PM()==1 ? SharedPreferencesUtils.getTimes(context).getHour_end() + ":" + SharedPreferencesUtils.getTimes(context).getMinute_end() + " " + "AM":SharedPreferencesUtils.getTimes(context).getHour_end() + ":" + SharedPreferencesUtils.getTimes(context).getMinute_end() + " " + "PM";
       return textTimeTo;
    }

    public static boolean isEmpty(EditText fromTime_, EditText toTime_) {
        if (fromTime_.getText().toString().trim().length() == 0 || toTime_.getText().toString().trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
