package com.ahmedcom.tasbeh55.utils;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {


    public static int getTimeInMinutes(Context context) {
        //  private WeakReference<MainActivity> mainActivity;
      int getTimes = SharedPreferencesUtils.getTimes(context).getEveryTime();
       int reuslt =1;
        getTimes = (getTimes==1) ? reuslt= 1 : (getTimes==1) ? reuslt= 2 : (getTimes==3) ? reuslt= 4 :
       (getTimes==5) ? reuslt= 10: (getTimes==6) ? reuslt= 15: (getTimes==7) ? reuslt= 30: (getTimes==8) ? reuslt= 60:
       (getTimes==9) ? reuslt=120:1;
        return reuslt;
    }

    public static ArrayList<String> getEverytime(){
        ArrayList<String> times = new ArrayList<String>();
        String[] Times = {" 1 دقيقة", " 2 دقيقة", " 3 دقيقة", " 4 دقيقة", "5 دقيقة", " 10 دقيقة", " 15 دقيقة", "30 دقيقة", " 1 ساعه", " 2 ساعه"};
        for (int i = 0; i < Times.length; i++) {
            times.add(Times[i]);
        }
        return  times;
    }

    public static String convert24HourTime(String val){
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = null;
        String result = null;
        try {
            date = parseFormat.parse(val);
            Log.v("Print_Val", ""+displayFormat.format(date));
            result =displayFormat.format(date);
            //  boolean isafter = parsedDate.after(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isCurrentTimeBtween(String start_time, String end_time){
        boolean isBetween = false;
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar cc = Calendar.getInstance();
        int mHour = cc.get(Calendar.HOUR_OF_DAY);
        int mMinute = cc.get(Calendar.MINUTE);
        int ampm= cc.get(Calendar.AM_PM);
        String currentampm = ampm==1 ? "PM": "AM";
        // System.out.println("time_format" + String.format("%02d:%02d", mHour , mMinute));
        String formatCurrnetTime=  mHour+":"+mMinute+" "+currentampm;
        try {
            Date s_time = sdf.parse(start_time);
            Date e_time = sdf.parse(end_time);
            Date c_time = sdf.parse(formatCurrnetTime);
            if(s_time.before(c_time) && e_time.after(c_time)){
                isBetween = true;
            }else {
                isBetween = false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return isBetween;
    }



    public static boolean checktTowTime(String startTime, String endTime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
           Date start_time = sdf.parse(startTime);
            Date end_time = sdf.parse(endTime);
            if(start_time.before(end_time)){
                return true;
            } else {
                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    public static String getTimeStartWithAmPm(Context context){
        String ampm ="";
        if(SharedPreferencesUtils.getTimes(context).getStart_AM_PM()==0)ampm="AM";
        else
        ampm="PM";
        String result  ="" + SharedPreferencesUtils.getTimes(context).getHour_start() + ":"+SharedPreferencesUtils.getTimes(context).getMinute_start() +" "+ ampm;
        return result;
    }


    public static String getTimeEndWithAmPm(Context context){
        String ampm ="";
        if(SharedPreferencesUtils.getTimes(context).getEnd_AM_PM()==0)ampm="AM";
        else
        ampm="PM";
        String result  ="" + SharedPreferencesUtils.getTimes(context).getHour_end() +":"+SharedPreferencesUtils.getTimes(context).getMinute_end() +" "+ampm;
        return result;
    }
}




















/*
     if(getTimes==0 ){
            getTimes= 1;
        }else if(getTimes==1){
            getTimes= 2;
        }else if(getTimes==3){
            getTimes= 4;
        }
        else if(getTimes==4){
            getTimes= 5;
        }
        else if(getTimes==5){
            getTimes= 10;
        }
        else if(getTimes==6){
            getTimes= 15;
        }
        else if(getTimes==7){
            getTimes= 30;
        }
        else if(getTimes==8){
            getTimes= 60;
        }
        else if(getTimes==9){
            getTimes= 120;
        }
 */
/*
 if(Times.getInstance().getStart_AM_PM()==1){
            textTimeTo ="" + Times.getInstance().getHour_end() + ":" + Times.getInstance().getMinute_end() + " " + "AM";
        }else{
            textTimeTo ="" + Times.getInstance().getHour_end() + ":" + Times.getInstance().getMinute_end() + " " + "PM";
        }
 */