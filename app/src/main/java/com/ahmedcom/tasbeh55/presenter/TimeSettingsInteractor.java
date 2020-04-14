package com.ahmedcom.tasbeh55.presenter;

import android.view.View;

public class TimeSettingsInteractor {

    public interface OnStopTimeAlertListener {
        void openStopTimeAlert();
        void closeStopTimeAlert();
    }
    public interface OnPickerClockDialogListener {
        void pickerClockDialogFrom();
        void pickerClockDialogTo();
    }

    public void setStopTimeAlert(boolean setOrNot , OnStopTimeAlertListener listener){
        if (setOrNot == true) {
            listener.openStopTimeAlert();
        } else {
            listener.closeStopTimeAlert();
        }
    }
    public void chosePickerClockDialog( int i ,OnPickerClockDialogListener listener){
        if(i==1){
            listener.pickerClockDialogFrom();
        }else {
            listener.pickerClockDialogTo();
        }
    }
}
