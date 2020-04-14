package com.ahmedcom.tasbeh55.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.models.Times;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TextUtils;

public class TimeSettingsPresenter implements TimeSettingsInteractor.OnStopTimeAlertListener, TimeSettingsInteractor.OnPickerClockDialogListener {

    ViewTimeSettings viewItems;
    Times time;
    private TimeSettingsInteractor timeSettingsInteractor;

    public TimeSettingsPresenter(ViewTimeSettings viewItems, TimeSettingsInteractor timeSettingsInteractor) {
        this.viewItems = viewItems;
        this.timeSettingsInteractor = timeSettingsInteractor;
        viewItems.pickerDialogEveryTime();
        time = new Times();
    }

    public void setStartTime(int hourOfDay, int minute, int AM_PM_Num) {
        time.setStart_AM_PM(AM_PM_Num);
        time.setHour_start(hourOfDay);
        time.setMinute_start(minute);
    }

    public void setEndTime(int hourOfDay, int minute, int AM_PM_Num) {
        time.setEnd_AM_PM(AM_PM_Num);
        time.setHour_end(hourOfDay);
        time.setMinute_end(minute);
    }

    public void saveTimes(int everyTime, int stopTimer) {
        time.setEveryTime(everyTime);
        time.setStopTimer(stopTimer);
        SharedPreferencesUtils.saveTimes((Context) viewItems, time);
    }

    public void pickerDialogRememberInfo(AppCompatEditText textSelectTimeFrom, AppCompatEditText textSelectTimeTo) {
      boolean boxesTimeIsEmpty = TextUtils.isEmpty(textSelectTimeFrom, textSelectTimeTo);
        if(boxesTimeIsEmpty && SharedPreferencesUtils.isOneORMoreSelected((Context) viewItems)) {
         viewItems.showDialogRememberInfo();
        }else{
          if(SharedPreferencesUtils.getTimes((Context) viewItems).getStopTimer() == 0 && SharedPreferencesUtils.isOneORMoreSelected((Context) viewItems)) {
            viewItems.showDialogRememberInfo();
            }else{
              Toast.makeText((Context) viewItems, R.string.empty_spaces, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void stopTimeAlert(boolean setOrNot) {
        timeSettingsInteractor.setStopTimeAlert(setOrNot, this);
    }
    @Override
    public void openStopTimeAlert() {
        viewItems.clearTextBoxesOfTimes();
    }

    @Override
    public void closeStopTimeAlert(){
        viewItems.setStopTimeAlert();
    }

    public void pickerClockDialog(int i){
        timeSettingsInteractor.chosePickerClockDialog(i, this);
    }
    @Override
    public void pickerClockDialogFrom(){
        viewItems.showClockDialogFrom();
    }
    @Override
    public void pickerClockDialogTo(){
        viewItems.showClockDialogTo();
    }
    public void defaultTimesOfBoxes(){
        viewItems.disabledTimesOfBoxes();
        viewItems.clearTimesOfBoxes();
    }
}
