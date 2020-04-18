package com.ahmedcom.tasbeh55.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.models.Times;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TextUtils;

import java.lang.ref.WeakReference;

public class TimeSettingsPresenter implements TimeSettingsInteractor.OnStopTimeAlertListener, TimeSettingsInteractor.OnPickerClockDialogListener {

    //ViewTimeSettings viewItems;
    Times time;
    private TimeSettingsInteractor timeSettingsInteractor;
    final WeakReference<ViewTimeSettings>viewItems;

    public TimeSettingsPresenter(ViewTimeSettings viewItems, TimeSettingsInteractor timeSettingsInteractor) {
       // this.viewItems = viewItems;
        this.viewItems = new WeakReference<>(viewItems);
        this.timeSettingsInteractor = timeSettingsInteractor;
        this.viewItems.get().pickerDialogEveryTime();
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
        SharedPreferencesUtils.saveTimes((Context) viewItems.get(), time);
    }

    public void pickerDialogRememberInfo(AppCompatEditText textSelectTimeFrom, AppCompatEditText textSelectTimeTo) {
        boolean boxesTimeIsEmpty = TextUtils.isEmpty(textSelectTimeFrom, textSelectTimeTo);
        if(boxesTimeIsEmpty && SharedPreferencesUtils.isOneORMoreSelected((Context) viewItems.get())) {
            viewItems.get().showDialogRememberInfo();
        }else{
            if(SharedPreferencesUtils.getTimes((Context) viewItems.get()).getStopTimer() == 0 && SharedPreferencesUtils.isOneORMoreSelected((Context) viewItems.get())) {
                viewItems.get().showDialogRememberInfo();
            }else{
                Toast.makeText((Context) viewItems.get(), R.string.empty_spaces, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void stopTimeAlert(boolean setOrNot) {
        timeSettingsInteractor.setStopTimeAlert(setOrNot, this);
    }
    @Override
    public void openStopTimeAlert() {
        viewItems.get().clearTextBoxesOfTimes();
    }

    @Override
    public void closeStopTimeAlert(){
        viewItems.get().setStopTimeAlert();
    }

    public void pickerClockDialog(int i){
        timeSettingsInteractor.chosePickerClockDialog(i, this);
    }
    @Override
    public void pickerClockDialogFrom(){
        viewItems.get().showClockDialogFrom();
    }
    @Override
    public void pickerClockDialogTo(){
        viewItems.get().showClockDialogTo();
    }
    public void defaultTimesOfBoxes(){
        viewItems.get().disabledTimesOfBoxes();
        viewItems.get().clearTimesOfBoxes();
    }
}