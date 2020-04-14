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
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TextUtils;
import com.ahmedcom.tasbeh55.utils.TimeUtils;

//https://academy.realm.io/posts/cool-constraintlayout-droidcon-boston-2017/
public class RememberInfoActivity extends AppCompatActivity implements View.OnClickListener, HasBack, ViewRememberInfo {

    Intent serviceIntent2 = null;
    RememberInfoPresenter rememberInfoPresenter;
    private ActivityDialogRememberInfoBinding binding;

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
        this.getWindow().setLayout(width, height);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rememberInfoPresenter.putTimesInTextBoxes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        rememberInfoPresenter.gotoHomeOrShowDialogInfo(v);
    }

    @Override
    public void smallerThanOreoDevice() {
        Intent serviceIntent = new Intent(getBaseContext(), Alarm2.class);
        startService(serviceIntent);
    }

    @Override
    public void oreoDeviceOrBigger() {
        SharedPreferencesUtils.setServiceOnOff(this, true);
        serviceIntent2 = new Intent(getBaseContext(), Alarm.class);
        Alarm.enqueueWork(this, serviceIntent2);
    }

    @Override
    public void gotoHome() {
        Intent intent = new Intent(RememberInfoActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void getValuesFromTextBoxesTime() {
        binding.textSelectTimeFromDialog.setText("" + TimeUtils.getTimeStartWithAmPm(this));
        binding.textSelectTimeToDialog.setText("" + TimeUtils.getTimeEndWithAmPm(this));
        binding.textMinute5.setText(TimeUtils.getEverytime().get(SharedPreferencesUtils.getTimes(this).getEveryTime()));
    }

    @Override
    public void stopeAnyService() {
        Toast.makeText(RememberInfoActivity.this, "تم أغلاق منبة الاذكار", Toast.LENGTH_SHORT).show();
        rememberInfoPresenter.stopServiceRunning();
    }

    @Override
    public void DialogFromSharPrefWithClock() {
        binding.btnStartRememberMe4.setText("ايقاف التشغيل");
        binding.textSelectTimeFromDialog.setText("" + TimeUtils.getTimeStartWithAmPm(this));
        binding.textSelectTimeToDialog.setText("" + TimeUtils.getTimeEndWithAmPm(this));
        binding.textMinute5.setText(TimeUtils.getEverytime().get(SharedPreferencesUtils.getTimes(this).getEveryTime()));
    }

    @Override
    public void DialogFromSharPref() {
        binding.btnStartRememberMe4.setText("ايقاف التشغيل");
        binding.textSelectTimeFromDialog.setText("");
        binding.textSelectTimeToDialog.setText("");
        binding.textMinute5.setText(TimeUtils.getEverytime().get(SharedPreferencesUtils.getTimes(this).getEveryTime()));
    }

    @Override
    public void showMsg(String text) {
      Toast.makeText(RememberInfoActivity.this,getString(Integer.parseInt(text)), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopSmallerThanOreo(){
        Intent serviceIntent = new Intent(RememberInfoActivity.this, Alarm2.class);
        stopService(serviceIntent);
    }

    @Override
    public void stopOreoDeviceOrBigger(){
        SharedPreferencesUtils.setServiceOnOff(RememberInfoActivity.this, false);
        Intent serviceIntent2 = new Intent(RememberInfoActivity.this, Alarm.class);
        Alarm.enqueueWork(RememberInfoActivity.this, serviceIntent2);
    }

    @Override
    public void showDialogInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RememberInfoActivity.this);
        builder.setMessage(R.string.run_inbackground);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                rememberInfoPresenter.runAlarmInBackground();
                builder.setCancelable(false);
            }
        });

        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                builder.setCancelable(false);
            }
        });
        builder.show();
    }
}