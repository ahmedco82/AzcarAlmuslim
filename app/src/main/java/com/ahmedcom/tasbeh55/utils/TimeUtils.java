package com.ahmedcom.tasbeh55.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.ahmedcom.tasbeh55.models.Times;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {


    public static int getTimeInMinutes(Context context) {
      int getTimes = SharedPreferencesUtils.getTimes(context).getEveryTime();
       int reuslt =1;
        getTimes = (getTimes==1) ? reuslt= 1 : (getTimes==1) ? reuslt= 2 : (getTimes==3) ? reuslt= 4 :
       (getTimes==5) ? reuslt= 10: (getTimes==6) ? reuslt= 15: (getTimes==7) ? reuslt= 30: (getTimes==8) ? reuslt= 60:
       (getTimes==9) ? reuslt=120:1;
        return reuslt;
    }

    public static String getCurrentDateUsingCalendar(){
        Date mDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy"); // getting date in this format
        return mSimpleDateFormat.format(mDate.getTime());
    }

    public static String getNextDateUsingCalendar() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DAY_OF_YEAR, 1);
        Date mStrTomorrow = mCalendar.getTime();
        @SuppressLint("SimpleDateFormat") DateFormat mDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return mDateFormat.format(mStrTomorrow);
    }

    public static String compareTwoTime(String mStrStartTime, String mStrEndTime) {
        Calendar startCalendar;
        Calendar endCalendar;
        Calendar currentCalendar;
        String mStrToday = "";
        String mStrTomorrow = "";
        String mStrCompareStartTime[] = mStrStartTime.split(" ");
        String mStrCompareEndTime[] = mStrEndTime.split(" ");
        int mIStartTime = Integer.parseInt(mStrCompareStartTime[0].replace(":", ""));
        int mIEndTime = Integer.parseInt(mStrCompareEndTime[0].replace(":", ""));
        if (mIStartTime < mIEndTime && mStrCompareStartTime[1].equals("PM") && mStrCompareEndTime[1].equals("PM")) {
            mStrToday = getCurrentDateUsingCalendar();
            mStrTomorrow = getCurrentDateUsingCalendar();
        } else if (mIStartTime < mIEndTime && mStrCompareStartTime[1].equals("AM") && mStrCompareEndTime[1].equals("AM")) {

            mStrToday = getCurrentDateUsingCalendar();
            mStrTomorrow = getCurrentDateUsingCalendar();

        } else if (mIStartTime > mIEndTime && mStrCompareStartTime[1].equals("PM") && mStrCompareEndTime[1].equals("PM")) {
            String mStrTime12[] = mStrCompareStartTime[0].split(":");
            if (mStrTime12[0].equals("12")) {
                mStrToday = getNextDateUsingCalendar();
                mStrTomorrow = getNextDateUsingCalendar();
            } else {
                mStrToday = getCurrentDateUsingCalendar();
                mStrTomorrow = getNextDateUsingCalendar();
            }
        } else if (mIStartTime > mIEndTime && mStrCompareStartTime[1].equals("AM") && mStrCompareEndTime[1].equals("AM")) {
            String mStrTime12[] = mStrCompareStartTime[0].split(":");
            if (mStrTime12[0].equals("12")) {
                mStrToday = getNextDateUsingCalendar();
                mStrTomorrow = getNextDateUsingCalendar();
            } else {
                mStrToday = getCurrentDateUsingCalendar();
                mStrTomorrow = getNextDateUsingCalendar();
            }
        } else if (mStrCompareStartTime[1].equals("PM") && mStrCompareEndTime[1].equals("AM")) {
            mStrToday = getCurrentDateUsingCalendar();
            mStrTomorrow = getNextDateUsingCalendar();
        } else if (mStrCompareStartTime[1].equals("AM") && mStrCompareEndTime[1].equals("PM")) {
            mStrToday = getCurrentDateUsingCalendar();
            mStrTomorrow = getCurrentDateUsingCalendar();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String mStrDifference = "";
        try {
            Date date1 = simpleDateFormat.parse(mStrToday + " " + mStrStartTime);
            Date date2 = simpleDateFormat.parse(mStrTomorrow + " " + mStrEndTime);
            mStrDifference = differenceDatesAndTime(date1, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mStrDifference;
    }

    public static String differenceDatesAndTime(Date mDateStart, Date mDateEnd) {
        long different = mDateEnd.getTime() - mDateStart.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        long minutes = elapsedHours * 60 + elapsedMinutes;
        long result = elapsedDays * 24 * 60 + minutes;
        if (0 > result) {
            result = result + 720;  //result is minus then add 12*60 minutes
        }
        return result + "";
    }

    //is value btween tow times ?
    public static boolean compareBetweenTwoTime(int hStart, int mStart, int hEnd, int mEnd) {
        String mStrToday = "";
        String mStrTomorrow = "";
        Calendar  startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();
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