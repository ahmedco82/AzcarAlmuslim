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
public class RememberInfoPresenter {

    ViewRememberInfo viewRememberInfo;

    public RememberInfoPresenter(ViewRememberInfo viewRememberInfo) {
        this.viewRememberInfo = viewRememberInfo;
    }
    private boolean checkService(){
        if (SharedPreferencesUtils.getServiceOnOff((Context) viewRememberInfo) || SharedPreferencesUtils.isServiceRunning(Alarm2.class, (Context) viewRememberInfo)) {
            return true;
        } else {
            return false;
        }
    }
    public void putTimesInTextBoxes(){
      if(checkService()){
        if(SharedPreferencesUtils.getTimes((Context) viewRememberInfo).getStopTimer() == 1) {
            viewRememberInfo.DialogFromSharPrefWithClock();
          }else {
            viewRememberInfo.DialogFromSharPref();
         }
        } else {
            viewRememberInfo.getValuesFromTextBoxesTime();
        }
    }
    public void gotoHomeOrShowDialogInfo(View v) {
      String getButtonName = String.valueOf(((Button)v).getTag());
        if(getButtonName.equals("Button_OK")){
            if (checkService()) {
                viewRememberInfo.stopeAnyService();
                viewRememberInfo.gotoHome();
            } else {
                viewRememberInfo.showDialogInfo();
            }
        }
    }
    public void stopServiceRunning(){
      if(SharedPreferencesUtils.getServiceOnOff((Context) viewRememberInfo)){
          viewRememberInfo.stopOreoDeviceOrBigger();
         }else if (SharedPreferencesUtils.isServiceRunning(Alarm2.class, (Context) viewRememberInfo)) {
         viewRememberInfo.stopSmallerThanOreo();
      }
    }
    public void runAlarmInBackground(){
        boolean selectedAnyItem = SharedPreferencesUtils.isOneORMoreSelected((Context) viewRememberInfo);
        if(selectedAnyItem){
            chooseDevice();
            viewRememberInfo.showMsg(String.valueOf(R.string.alarm_isworking));
        }else{
            viewRememberInfo.showMsg(String.valueOf(R.string.backtolistazcar));
        }
    }
    private void chooseDevice(){
        if (Build.VERSION.SDK_INT <= 22) {
            viewRememberInfo.smallerThanOreoDevice();
        } else {
            viewRememberInfo.oreoDeviceOrBigger();
        }
        viewRememberInfo.gotoHome();
    }
}
