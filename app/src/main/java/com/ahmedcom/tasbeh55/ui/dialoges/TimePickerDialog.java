package com.ahmedcom.tasbeh55.ui.dialoges;

import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ahmedcom.tasbeh55.models.Times;

import java.util.Calendar;


public class TimePickerDialog extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    public int currentEditText = 0;
    public String AM_PM = "";
    public int AM_PM_Num = 0;
    public int hourOfDay = 0;
    public int minute = 0;
    private EditText currentEditTixt;
    private Times times;

    public TimePickerDialog(Times times,int id, EditText fromTime) {
        this.times=times;
        this.currentEditTixt = fromTime;
        this.currentEditText = id;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new android.app.TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay_, int minute_) {
        if (hourOfDay_ >12) {
            AM_PM = "PM";
            AM_PM_Num =2;
            hourOfDay_ = hourOfDay_ - 12;
        } else {
            AM_PM = "AM";
            AM_PM_Num = 1;
        }
        hourOfDay = hourOfDay_;
        minute = minute_;
        currentEditTixt.setText("" + hourOfDay + ":" + minute + " " + AM_PM);
        putTimes();
    }
  private void putTimes(){
      if(currentEditText==1){
         times.setStart_AM_PM(AM_PM_Num);
         times.setHour_start(hourOfDay);
         times.setMinute_start(minute);
        }else{
         times.setEnd_AM_PM(AM_PM_Num);
         times.setHour_end(hourOfDay);
         times.setMinute_end(minute);
        }
    }
}