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
import com.ahmedcom.tasbeh55.services.Alarm;
import com.ahmedcom.tasbeh55.services.Alarm2;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.utils.TimeUtils;

//https://academy.realm.io/posts/cool-constraintlayout-droidcon-boston-2017/
public class RememberInfoActivity extends AppCompatActivity implements View.OnClickListener, HasBack {

    private ActivityDialogRememberInfoBinding binding;
    Intent serviceIntent2 = null;
    boolean statusService =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dialog_remember_info);
        initiViews();
       // Bitmap bitmap = BitmapFactory.decodeResource(RememberInfoActivity.this.getResources(), R.drawable.left_arrow);
       // new ActionBarView(RememberInfoActivity.this, "تذكر", bitmap, new BackPressedCallingBack(RememberInfoActivity.this));
    }

    private void initiViews() {
      //  binding.btnStartRememberMe3.setTag("Button_NO");
        binding.btnStartRememberMe4.setTag("Button_OK");
       // binding.btnStartRememberMe3.setOnClickListener(this);
        binding.btnStartRememberMe4.setOnClickListener(this);
        binding.textSelectTimeFromDialog.setEnabled(false);
        binding.textSelectTimeToDialog.setEnabled(false);
        putTimesInTextBox();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.60);
        this.getWindow().setLayout(width, height);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(checkService()){
            binding.btnStartRememberMe4.setText("ايقاف التشغيل");
        }

    }


    private boolean checkService(){
        if (SharedPreferencesUtils.getServiceOnOff(getApplicationContext()) || isMyServiceRunning(Alarm2.class)){
            return true;
        }else {
            return false;
        }
    }


    public void putTimesInTextBox() {
        if(checkService()) {
            getValuesDialogFromSharPref();
        }else {
             getValuesFromTextBoxesTime();
        }
    }

    private void getValuesFromTextBoxesTime(){
        String every_time = getIntent().getExtras().getString("every_time");
        String starttime = getIntent().getExtras().getString("starttime");
        String endtime = getIntent().getExtras().getString("endtime");
        binding.textSelectTimeFromDialog.setText(starttime);
        binding.textSelectTimeToDialog.setText(endtime);
        binding.textMinute5.setText(every_time);

    }

    private void getValuesDialogFromSharPref(){
        String[] Times = {" 1 دقيقة", " 2 دقيقة", " 3 دقيقة", " 4 دقيقة", "5 دقيقة", " 10 دقيقة", " 15 دقيقة", "30 دقيقة", " 1 ساعه", " 2 ساعه"};
        if(SharedPreferencesUtils.getTimes(this).getStopTimer()==1){
           binding.textSelectTimeFromDialog.setText(""+TimeUtils.getTimeStartWithAmPm(this));
           binding.textSelectTimeToDialog.setText(""+TimeUtils.getTimeEndWithAmPm(this));
           binding.textMinute5.setText(Times[SharedPreferencesUtils.getTimes(this).getEveryTime()]);
       }else {
           binding.textSelectTimeFromDialog.setText("");
           binding.textSelectTimeToDialog.setText("");
           binding.textMinute5.setText(Times[SharedPreferencesUtils.getTimes(this).getEveryTime()]);
       }
   }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
      String getButtonName = String.valueOf(((Button)v).getTag());
      // Log.i("print ", "" + getButtonName);
        if(getButtonName.equals("Button_OK")) {
            if(checkService()){
                stopeServiceAndGotoHome();
            }else {
                showDialogInfo();
            }
        }
     }

    private void stopeServiceAndGotoHome() {
        Toast.makeText(RememberInfoActivity.this,"تم أغلاق منبة الاذكار", Toast.LENGTH_SHORT).show();
       stopServiceRunning();
       gotoHome();
    }

    private void gotoHome(){
        Intent intent = new Intent(RememberInfoActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void stopServiceRunning(){
        if (SharedPreferencesUtils.getServiceOnOff(getApplicationContext())) {
            SharedPreferencesUtils.setServiceOnOff(this, false);
            serviceIntent2 = new Intent(getBaseContext(), Alarm.class);
            Alarm.enqueueWork(this, serviceIntent2);
        }
        else if (isMyServiceRunning(Alarm2.class)){
            Intent serviceIntent = new Intent(getBaseContext(), Alarm2.class);
            stopService(serviceIntent);
        }
    }

    private void chooseDevice() {
        if (Build.VERSION.SDK_INT <= 22) {
            smallerThanOreoDevice();
        } else {
            OreoDeviceOrBigger();
        }
       // Log.i("prints_getTimes2 ",""+SharedPreferencesUtils.getTimes(this).getEveryTime());
        Intent intent = new Intent(RememberInfoActivity.this, HomeActivity.class);
        startActivity(intent);
        //closeApp();
     }

 private void smallerThanOreoDevice(){
     Intent serviceIntent = new Intent(getBaseContext(), Alarm2.class);
     startService(serviceIntent);
    }

    private void OreoDeviceOrBigger() {
        SharedPreferencesUtils.setServiceOnOff(this, true);
        serviceIntent2 = new Intent(getBaseContext(), Alarm.class);
        Alarm.enqueueWork(this, serviceIntent2);
     }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void showDialogInfo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RememberInfoActivity.this);
        builder.setMessage(R.string.run_inbackground);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialoginterface, int i) {
          // chooseDevice();
            boolean selectedAnyItem = ensureOneORMoreSelected();
             if(selectedAnyItem){
                 chooseDevice();
                 Toast.makeText(RememberInfoActivity.this,"تم تشغيل منبه الأذكار", Toast.LENGTH_SHORT).show();
             }else{
                 Toast.makeText(RememberInfoActivity.this,R.string.backtolistazcar, Toast.LENGTH_SHORT).show();
              }
              builder.setCancelable(false);
            }
         });

        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialoginterface, int i) {
                builder.setCancelable(false);
                // getFilterArries();
            }
        });
        builder.show();
    }

    public boolean ensureOneORMoreSelected(){
        boolean checkArray = false;
        for(Boolean object:SharedPreferencesUtils.getArrayBooleanPrefs(RememberInfoActivity.this)) {
            if (object) {
                checkArray = true;
                break;
            } else {
                checkArray = false;
            }
        }
        return checkArray;
    }
}



/*
   if(getButtonName.equals("Button_OK")) {
         Intent intent = new Intent(this, ListAzcarActivity.class);
          startActivity(intent);
        }else{
           finish();
            Intent intent = new Intent(this, TimeSettingsActivity.class);
            startActivity(intent);
        }
 */
