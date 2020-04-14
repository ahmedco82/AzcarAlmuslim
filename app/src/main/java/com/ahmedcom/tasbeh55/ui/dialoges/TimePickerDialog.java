package com.ahmedcom.tasbeh55.ui.dialoges;

import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ahmedcom.tasbeh55.presenter.TimeSettingsPresenter;

import java.util.Calendar;


public class TimePickerDialog extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    public int currentEditText = 0;
    public String AM_PM = "";
    public int AM_PM_Num = 0;
    public int hourOfDay = 0;
    public int minute = 0;
    private EditText currentEditTixt;
    TimeSettingsPresenter presenter;

    public TimePickerDialog(TimeSettingsPresenter presenter , int id , EditText fromTime) {
        this.currentEditText = id;
        this.presenter = presenter;
        this.currentEditTixt = fromTime;
    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int ampm = c.get(Calendar.AM_PM);
        AM_PM= ampm==1 ? "PM": "AM";
        return new android.app.TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        AM_PM = " AM";
        AM_PM_Num = 0;
        String mm_precede = "";
        if (hourOfDay >= 12) {
            AM_PM = " PM";
            AM_PM_Num = 1;
            if (hourOfDay >=13 && hourOfDay < 24) {
                hourOfDay -= 12;
            }
            else {
                hourOfDay = 12;
            }
           }else if (hourOfDay == 0) {
            hourOfDay = 12;
          }
          if(minute<10){
            mm_precede = "0";
         }
         // Toast.makeText(getActivity(), "" + hourOfDay + ":" + mm_precede + minute + AM_PM, Toast.LENGTH_SHORT).show();
         this.hourOfDay = hourOfDay;
         this.minute = minute;
         //Log.i("AM_PM_Num  :   ",""+AM_PM_Num);
         currentEditTixt.setText("" + hourOfDay + ":" + mm_precede + minute + AM_PM);
         putTimes();
    }

  private void putTimes(){
      if(currentEditText==1){
          presenter.setEndTime(hourOfDay , minute , AM_PM_Num);
        }else{
          presenter.setStartTime(hourOfDay , minute , AM_PM_Num);
        }
         //Log.i("AM_PM_Num:1: ",""+times.getStart_AM_PM()+ " "+times.getEnd_AM_PM());
    }
}