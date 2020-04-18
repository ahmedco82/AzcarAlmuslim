package com.ahmedcom.tasbeh55.presenter;

import android.view.View;

import java.lang.ref.WeakReference;

public class TimeSettingsInteractor {

   // OnStopTimeAlertListener mListenerOnStopTimeAlert = null;
  //  OnPickerClockDialogListener mListenerOnPickerClockDialog= null;
   // WeakReference<OnStopTimeAlertListener>mListenerOnStopTimeAlert;

    public interface OnStopTimeAlertListener {
        void openStopTimeAlert();
        void closeStopTimeAlert();
    }
    public interface OnPickerClockDialogListener {
        void pickerClockDialogFrom();
        void pickerClockDialogTo();
    }
    public void setStopTimeAlert(boolean setOrNot , OnStopTimeAlertListener listener){
       final WeakReference<OnStopTimeAlertListener>mListenerOnStopTimeAlert =new WeakReference<>(listener);
        if (setOrNot == true) {
            mListenerOnStopTimeAlert.get().openStopTimeAlert();
        } else {
            mListenerOnStopTimeAlert.get().closeStopTimeAlert();
        }
    }
    public void chosePickerClockDialog( int i , OnPickerClockDialogListener listener){
        final WeakReference<OnPickerClockDialogListener>mListenerOnPickerClockDialog =new WeakReference<>(listener);
        if(i==1){
            mListenerOnPickerClockDialog.get().pickerClockDialogFrom();
        }else {
            mListenerOnPickerClockDialog.get().pickerClockDialogTo();
        }
    }
}
