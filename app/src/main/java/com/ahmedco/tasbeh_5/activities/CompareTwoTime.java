package com.ahmedco.tasbeh_5.activities;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompareTwoTime {

    public static String mStrToday = "";
    public static String mStrTomorrow = "";

    public static String getCurrentDateUsingCalendar() {
        Date mDate = new Date();  // to get the date
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

    public static String compareTwoTimeAMPM(String mStrStartTime, String mStrEndTime) {
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
}
