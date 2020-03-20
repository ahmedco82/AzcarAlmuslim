package com.ahmedcom.tasbeh55.utils;

import com.ahmedcom.tasbeh55.models.Times;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeOptions {

    private static Calendar startCalendar;
    private static Calendar endCalendar;
    private static Calendar currentCalendar;

    private static DataSharedPreferences globalSharedPreferences = DataSharedPreferences.getInstance();

    public static String getTimeFromFormatting(){
        String textTimeFrom = "";
        textTimeFrom =Times.getInstance().getStart_AM_PM()==1 ? Times.getInstance().getHour_start() + ":" + Times.getInstance().getMinute_start() + " " + "AM":Times.getInstance().getHour_start() + ":" + Times.getInstance().getMinute_start() + " " + "PM";
        return textTimeFrom;
    }

    public static String getTimeToFormatting(){
        String textTimeTo = "";
        textTimeTo =  Times.getInstance().getStart_AM_PM()==1 ? Times.getInstance().getHour_end() + ":" + Times.getInstance().getMinute_end() + " " + "AM":Times.getInstance().getHour_end() + ":" + Times.getInstance().getMinute_end() + " " + "PM";
        return textTimeTo;
    }

    public static int getTimeInMinutes() {
       int getTimes = globalSharedPreferences.getfromOject().getEveryTime();
       int reuslt =1;
        getTimes = (getTimes==1) ? reuslt= 1 : (getTimes==1) ? reuslt= 2 : (getTimes==3) ? reuslt= 4 :
       (getTimes==5) ? reuslt= 10: (getTimes==6) ? reuslt= 15: (getTimes==7) ? reuslt= 30: (getTimes==8) ? reuslt= 60:
       (getTimes==9) ? reuslt=120:1;
        return reuslt;
    }


    //compare two time
    public static boolean compareBetweenTwoTime(int hStart, int mStart, int hEnd, int mEnd) {
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();
        startCalendar.setTimeZone(TimeZone.getDefault());
        startCalendar.set(Calendar.HOUR, hStart);
        startCalendar.set(Calendar.MINUTE, mStart);
        endCalendar.setTimeZone(TimeZone.getDefault());
        endCalendar.set(Calendar.HOUR, hEnd);
        endCalendar.set(Calendar.MINUTE, mEnd);

        if (hStart>12) hStart = hStart - 12;
        if (hEnd>12) hEnd = hEnd - 12;

        if (currentCalendar.getTimeInMillis()>startCalendar.getTimeInMillis() && currentCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }
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