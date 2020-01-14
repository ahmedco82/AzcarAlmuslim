package com.ahmedco.tasbeh_5.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


public class TimePickerDialog extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    public int currentEditText = 0;
    public String AM_PM = "";
    public int hourOfDay = 0;
    public int minute = 0;
    private EditText currentEditTixt;

    public TimePickerDialog(int id, EditText fromTime_) {
        currentEditTixt = fromTime_;
        currentEditText = id;
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
        if (hourOfDay_ > 12) {
            AM_PM = "PM";
            hourOfDay_ = hourOfDay_ - 12;
        } else {
            AM_PM = "AM";
        }
        hourOfDay = hourOfDay_;
        minute = minute_;
        currentEditTixt.setText("" + hourOfDay + ":" + minute + " " + AM_PM);
    }

}
