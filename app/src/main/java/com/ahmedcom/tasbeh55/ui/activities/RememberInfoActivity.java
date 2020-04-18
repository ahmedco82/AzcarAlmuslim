package com.ahmedcom.tasbeh55.ui.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.databinding.ActivityDialogRememberInfoBinding;
import com.ahmedcom.tasbeh55.interfaces.HasBack;
import com.ahmedcom.tasbeh55.presenter.RememberInfoPresenter;
import com.ahmedcom.tasbeh55.presenter.ViewRememberInfo;
import com.ahmedcom.tasbeh55.services.Alarm;
import com.ahmedcom.tasbeh55.services.Alarm2;
import com.ahmedcom.tasbeh55.utils.App;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TextUtils;
import com.ahmedcom.tasbeh55.utils.TimeUtils;
import com.squareup.leakcanary.RefWatcher;

//https://academy.realm.io/posts/cool-constraintlayout-droidcon-boston-2017/
public class RememberInfoActivity extends AppCompatActivity implements View.OnClickListener, HasBack, ViewRememberInfo {

    Intent serviceIntent2 = null;
    RememberInfoPresenter rememberInfoPresenter;
    private ActivityDialogRememberInfoBinding binding;

   // static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dialog_remember_info);
        rememberInfoPresenter = new RememberInfoPresenter(this);
        initiViews();
    }

    private void initiViews() {
        binding.btnStartRememberMe4.setTag("Button_OK");
        binding.btnStartRememberMe4.setOnClickListener(this);
        binding.textSelectTimeFromDialog.setEnabled(false);
        binding.textSelectTimeToDialog.setEnabled(false);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
       getWindow().setLayout(width, height);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rememberInfoPresenter.putTimesInTextBoxes();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        /*
        Log.d("destroy-Activity","Don");
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
         */
    }

    @Override
    public void onClick(View v){
        rememberInfoPresenter.gotoHomeOrShowDialogInfo(v);
    }

    @Override
    public void smallerThanOreoDevice(){
      Intent serviceIntent = new Intent(getApplicationContext(), Alarm2.class);
       startService(serviceIntent);
    }

    @Override
    public void oreoDeviceOrBigger(){
        SharedPreferencesUtils.setServiceOnOff(getApplicationContext(), true);
        serviceIntent2 = new Intent(getApplicationContext(), Alarm.class);
        Alarm.enqueueWork(this, serviceIntent2);
    }

    @Override
    public void gotoHome(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }


    //
    @Override
    public void getValuesFromTextBoxesTime(){
        binding.textSelectTimeFromDialog.setText("" + TimeUtils.getTimeStartWithAmPm(getApplicationContext()));
        binding.textSelectTimeToDialog.setText("" + TimeUtils.getTimeEndWithAmPm(getApplicationContext()));
        binding.textMinute5.setText(TimeUtils.getEverytime().get(SharedPreferencesUtils.getTimes(getApplicationContext()).getEveryTime()));
    }

    @Override
    public void stopeAnyService(){
        Toast.makeText(getApplicationContext(), "تم أغلاق منبة الاذكار", Toast.LENGTH_SHORT).show();
        rememberInfoPresenter.stopServiceRunning();
    }

    @Override
    public void DialogFromSharPrefWithClock(){
        binding.btnStartRememberMe4.setText("ايقاف التشغيل");
        binding.textSelectTimeFromDialog.setText("" + TimeUtils.getTimeStartWithAmPm(getApplicationContext()));
        binding.textSelectTimeToDialog.setText("" + TimeUtils.getTimeEndWithAmPm(getApplicationContext()));
        binding.textMinute5.setText(TimeUtils.getEverytime().get(SharedPreferencesUtils.getTimes(getApplicationContext()).getEveryTime()));
    }


    @Override
    public void DialogFromSharPref(){
        binding.btnStartRememberMe4.setText("ايقاف التشغيل");
        binding.textSelectTimeFromDialog.setText("");
        binding.textSelectTimeToDialog.setText("");
        binding.textMinute5.setText(TimeUtils.getEverytime().get(SharedPreferencesUtils.getTimes(getApplicationContext()).getEveryTime()));
    }

    @Override
    public void showMsg(String text){
      Toast.makeText(getApplicationContext(),getString(Integer.parseInt(text)), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopSmallerThanOreo(){
        Intent serviceIntent = new Intent(getApplicationContext(), Alarm2.class);
        stopService(serviceIntent);
    }

    @Override
    public void stopOreoDeviceOrBigger(){
        SharedPreferencesUtils.setServiceOnOff(getApplicationContext(), false);
        Intent serviceIntent2 = new Intent(getApplicationContext(), Alarm.class);
        Alarm.enqueueWork(getApplicationContext(), serviceIntent2);
    }

    @Override
    public void showDialogInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.run_inbackground);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialoginterface, int i) {
                rememberInfoPresenter.runAlarmInBackground();
                builder.setCancelable(false);
            }
        });

        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialoginterface, int i) {
                builder.setCancelable(false);
            }
        });
        builder.show();
    }

}