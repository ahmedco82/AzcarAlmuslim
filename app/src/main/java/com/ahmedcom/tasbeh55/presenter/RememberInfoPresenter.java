package com.ahmedcom.tasbeh55.presenter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.services.Alarm;
import com.ahmedcom.tasbeh55.services.Alarm2;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;

import java.lang.ref.WeakReference;

public class RememberInfoPresenter {

    //ViewRememberInfo viewRememberInfo;

    final WeakReference<ViewRememberInfo>viewRememberInfo;

    public RememberInfoPresenter(ViewRememberInfo viewRememberInfo) {
        //this.viewRememberInfo = viewRememberInfo;
        this.viewRememberInfo=new WeakReference<>(viewRememberInfo);
    }

    private boolean checkService(){
        if (SharedPreferencesUtils.getServiceOnOff((Context) viewRememberInfo.get()) || SharedPreferencesUtils.isServiceRunning(Alarm2.class,(Context) viewRememberInfo.get())) {
            return true;
        } else {
            return false;
        }
    }
    public void putTimesInTextBoxes(){
      if(checkService()){
        if(SharedPreferencesUtils.getTimes((Context)  viewRememberInfo.get()).getStopTimer() == 1) {
            viewRememberInfo.get().DialogFromSharPrefWithClock();
          }else {
            viewRememberInfo.get().DialogFromSharPref();
         }
        } else {

          if(SharedPreferencesUtils.getTimes((Context)  viewRememberInfo.get()).getStopTimer() == 1) {
              viewRememberInfo.get().getValuesFromTextBoxesTimeWithClock();
          }else{
              viewRememberInfo.get().getValuesFromTextBoxesTime();
          }
           //viewRememberInfo.get().getValuesFromTextBoxesTime();
        }
    }



    public void gotoHomeOrShowDialogInfo(View v) {
      String getButtonName = String.valueOf(((Button)v).getTag());
        if(getButtonName.equals("Button_OK")){
            if (checkService()) {
                viewRememberInfo.get().stopeAnyService();
                viewRememberInfo.get().gotoHome();
            } else {
                viewRememberInfo.get().showDialogInfo();
            }
        }
    }
    public void stopServiceRunning(){
      if(SharedPreferencesUtils.getServiceOnOff((Context)viewRememberInfo.get())){
          viewRememberInfo.get().stopOreoDeviceOrBigger();
         }else if (SharedPreferencesUtils.isServiceRunning(Alarm2.class,(Context)viewRememberInfo.get())) {
          viewRememberInfo.get().stopSmallerThanOreo();
      }
    }
    public void runAlarmInBackground(){
        boolean selectedAnyItem = SharedPreferencesUtils.isOneORMoreSelected((Context)  viewRememberInfo.get());
        if(selectedAnyItem){
            chooseDevice();
            viewRememberInfo.get().showMsg(String.valueOf(R.string.alarm_isworking));
        }else{
            viewRememberInfo.get().showMsg(String.valueOf(R.string.backtolistazcar));
        }
    }
    private void chooseDevice(){
        if (Build.VERSION.SDK_INT <= 22) {
            viewRememberInfo.get().smallerThanOreoDevice();
        } else {
            viewRememberInfo.get().oreoDeviceOrBigger();
        }
        viewRememberInfo.get().gotoHome();
    }
}
