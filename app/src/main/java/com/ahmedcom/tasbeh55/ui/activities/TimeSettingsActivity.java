package com.ahmedcom.tasbeh55.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.ahmedcom.BackPressedCallingBack;
import com.ahmedcom.tasbeh55.interfaces.HasBack;
import com.ahmedcom.tasbeh55.ui.others.ActionBarView;
import com.ahmedcom.tasbeh55.ui.dialoges.DialogeRepeatTime;
import com.ahmedcom.tasbeh55.ui.dialoges.TimePickerDialog;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.models.Times;
import com.ahmedcom.tasbeh55.utils.TextUtils;
import com.ahmedcom.tasbeh55.utils.TimeUtils;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;

import com.ahmedcom.tasbeh55.databinding.ActivitySettimesBinding;
// https://github.com/PuffoCyano/Range-Time-Picker-Dialog

public class TimeSettingsActivity extends AppCompatActivity implements HasBack, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final int maxStopTime = 360;
    public HashMap<String, Integer> hmapTimes;
    public DialogFragment timeFromPickerDialog, timeToPickerDialog;
    public int everyTime;
    public String everyTimeTxt, pramEveryTime;
    private ActivitySettimesBinding binding;
    private String everyTimeString;
    private int stopTimer;
    private DialogeRepeatTime dialogeRepeatTime;
    private ArrayList<String> times;
    private OptionsPickerView optionsPickerTimes;
    private Times time;
    private String fromTimeFormatting;
    private String toTimeFormatting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settimes);
        initiItems();
        binding.textSelectTimeFrom.setOnClickListener(this);
        binding.textSelectTimeTo.setOnClickListener(this);
        binding.rowEverytime.setOnClickListener(this);
        binding.threeRowEverytime.setOnClickListener(this);
        binding.startRememberBtn.setOnClickListener(this);
        binding.btCheck2.setOnCheckedChangeListener(this);
        //  binding.rowEverytime.setOnClickListener(this);
    }

    private void initiItems() {
        if(timeFromPickerDialog == null)
        timeFromPickerDialog = new TimePickerDialog(time, 1, binding.textSelectTimeFrom);
        if(timeToPickerDialog == null)
        timeToPickerDialog = new TimePickerDialog(time, 2, binding.textSelectTimeTo);
        everyTime = 0;
        everyTimeTxt = "1 دقيقة";
        pramEveryTime = "1 دقيقة";
        times = new ArrayList<>();
        String[] Times = {" 1 دقيقة", " 2 دقيقة", " 3 دقيقة", " 4 دقيقة", "5 دقيقة", " 10 دقيقة", " 15 دقيقة", "30 دقيقة", " 1 ساعه", " 2 ساعه"};
        for (int i = 0; i < Times.length; i++) {
            times.add(Times[i]);
        }
        optionsPickerTime();
        Bitmap bitmap = BitmapFactory.decodeResource(TimeSettingsActivity.this.getResources(), R.drawable.left_arrow);
        new ActionBarView(TimeSettingsActivity.this, "أوقات الاذكار", bitmap, new BackPressedCallingBack(TimeSettingsActivity.this));
        hmapTimes = new HashMap<String, Integer>();
        time = new Times();
        dialogeRepeatTime = null;
        binding.textSelectTimeFrom.setEnabled(false);
        binding.textSelectTimeTo.setEnabled(false);
        binding.textSelectTimeFrom.setText("");
        binding.textSelectTimeTo.setText("");
        everyTimeString = "1 دقيقة";
    }

    private void optionsPickerTime(){
        optionsPickerTimes = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int index, View v) {
                everyTimeTxt = times.get(index);
                everyTime = index;
                everyTimeString = times.get(index);
                //includedLayout.setText(everyTimeTxt);
                TextView insideTheIncludedLayout = (TextView) binding.rowEverytime.findViewById(R.id.text_minute);
                insideTheIncludedLayout.setText(everyTimeTxt);
            }
        }).setItemVisibleCount(5).setSelectOptions(0, 0, 0).build();
        optionsPickerTimes.setNPicker(times);
        optionsPickerTimes.setSelectOptions(0, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_select_time_from:
                showTimePickerDialog(v, 1);
                break;


            case R.id.text_select_time_to:
                showTimePickerDialog(v, 2);
                break;
            case R.id.row_everytime:
                optionsPickerTimes.show();
                // Log.i("printDon ", " Yes");
                //optionsPickerTime();
                break;

            case R.id.three_row_everytime:
                goToListAzcar();
                break;
            case R.id.startRemember_btn_:
                //test0();
                checkTimFromTo();
                break;
        }
    }

    private void checkTimFromTo() {
        boolean boxesTimeIsEmpty = TextUtils.isEmpty(binding.textSelectTimeFrom , binding.textSelectTimeTo);
        if (boxesTimeIsEmpty) {
            saveItems();
            toTimeFormatting = TextUtils.getTimeFromFormatting(TimeSettingsActivity.this);
            fromTimeFormatting  = TextUtils.getTimeToFormatting(TimeSettingsActivity.this);
            // boolean compareTimes = TimeUtils.checktTowTime(TimeUtils.convert24HourTime(fromTimeFormatting),TimeUtils.convert24HourTime(toTimeFormatting));
            showDialogRememberInfo();
        } else {
            if (stopTimer==0) {
              showDialogRememberInfo();
            } else{
               Toast.makeText(this,getString(R.string.empty_spaces), Toast.LENGTH_LONG).show();
             }
         }
     }

    private void saveItems(){
       time.setEveryTime(everyTime);
       time.setStopTimer(stopTimer);
        SharedPreferencesUtils.saveTimes(TimeSettingsActivity.this, time);
    }



    private void goToListAzcar(){
        Intent intent = new Intent(this, ListAzcarActivity.class);
        startActivity(intent);
    }

    private void showDialogRememberInfo() {
        Intent intent = new Intent(TimeSettingsActivity.this, RememberInfoActivity.class);
        intent.putExtra("every_time", everyTimeString);
        intent.putExtra("starttime",fromTimeFormatting);
        intent.putExtra("endtime",toTimeFormatting);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        binding.btCheck2.setText(null);
        if (isChecked == true) {
            openStopTime();
        } else {
            closeStopTime();
        }
    }

    private void closeStopTime() {
        int f = R.drawable.unchk;
        binding.textSelectTimeFrom.setText("");
        binding.textSelectTimeTo.setText("");
        stopTimer = 0;
        //  binding.startRememberBtn.setText(R.string.stop_remeber);
        binding.textSelectTimeFrom.setText("");
        binding.textSelectTimeTo.setText("");
        binding.textSelectTimeFrom.setEnabled(false);
        binding.textSelectTimeTo.setEnabled(false);
        // Toast.makeText(TimeSettingsActivity.this, "UnClicked", Toast.LENGTH_LONG).show();
        binding.btCheck2.setBackgroundDrawable(getDrawable(f));
    }

    private void openStopTime() {
        int idBackground = R.drawable.chk;
        // binding.startRememberBtn.setText(R.string.start_remeber);
        stopTimer = 1;
        // Toast.makeText(TimeSettingsActivity.this, "Clicked", Toast.LENGTH_LONG).show();
        binding.btCheck2.setBackgroundDrawable(getDrawable(idBackground));
        binding.textSelectTimeFrom.setEnabled(true);
        binding.textSelectTimeTo.setEnabled(true);
    }

    private void showTimePickerDialog(View v, int Id) {
        if (Id == 1) {
            timeFromPickerDialog = new TimePickerDialog(time, Id, binding.textSelectTimeFrom);
            timeFromPickerDialog.show(getSupportFragmentManager(), "timeFromPickerDialog");
        } else if (Id == 2) {
            timeToPickerDialog = new TimePickerDialog(time,Id, binding.textSelectTimeTo);
            timeToPickerDialog.show(getSupportFragmentManager(), "timeToPickerDialog");
        }
    }

    private void closeApp() {
        //  this.finishAffinity();
    }
}

















/*
 private String getTimeWithFormatting(TimePickerDialog currentTimePickerDialog){
   if (timeFromPickerDialog == null) timeFromPickerDialog = new TimePickerDialog(1, fromTime);
     if (timeToPickerDialog == null) timeToPickerDialog = new TimePickerDialog(2, toTime);
       int hourTimer = ((TimePickerDialog) currentTimePickerDialog).hourOfDay;
        int minuteTimer = ((TimePickerDialog) currentTimePickerDialog).minute;
      String timeAM_PM = ((TimePickerDialog) currentTimePickerDialog).AM_PM;
    return "" + hourTimer + ":" + minuteTimer + " " + timeAM_PM;
  }
*/

//
// (String) getTimeWithFormatting((TimePickerDialog)timeFromPickerDialog);
// (String) getTimeWithFormatting((TimePickerDialog)timeToPickerDialog);
    /*
    private void putValuesOfTimes(){
        // if(dialogeRepeatTime == null)
        int hourStartTime = ((TimePickerDialog) timeFromPickerDialog).hourOfDay;
        int minuteStartTime = ((TimePickerDialog) timeFromPickerDialog).minute;
        String startAM_PM = ((TimePickerDialog) timeFromPickerDialog).AM_PM;
        int hourEndTime = ((TimePickerDialog) timeToPickerDialog).hourOfDay;
        int minuteEndTimer = ((TimePickerDialog) timeToPickerDialog).minute;
        String endAM_PM2 = ((TimePickerDialog) timeToPickerDialog).AM_PM;
        int end_ampm = 0;
        int start_ampm = 0;
        if(endAM_PM2 == "AM")end_ampm = 1;
        if(endAM_PM2 == "PM")end_ampm = 2;
        if(startAM_PM == "AM")start_ampm = 1;
        if(startAM_PM == "PM")start_ampm = 2;
        if(stopTimer == 0) {
            times.setEveryTime(everyTime);
            times.setStopTimer(stopTimer);
          }else {
            times.setEveryTime(everyTime);
            times.setStopTimer(stopTimer);
            times.setEnd_AM_PM(end_ampm);
            times.setStart_AM_PM(start_ampm);
            times.setHour_start(hourStartTime);
            times.setHour_end(hourEndTime);
            times.setMinute_start(minuteStartTime);
            times.setMinute_end(minuteEndTimer);
         }
        globalSharedPreferences.saveOject(times);
     }
*/


/*
Intent intent = new Intent(TimeSettingsActivity.this, RememberInfoActivity.class);
 String fromTimeFormatting = (String) getTimeWithFormatting((TimePickerDialog) timeFromPickerDialog);
   String toTimeFormatting = (String) getTimeWithFormatting((TimePickerDialog) timeToPickerDialog);
    intent.putExtra("every_time", everyTimeString);
   intent.putExtra("starttime", fromTimeFormatting);
  intent.putExtra("endtime", toTimeFormatting);
 startActivity(intent);
         */
//gettValuesOfTimes();
//  startActivityForResult(intent, REQUEST_CODE);

     /*
      public void gotoWidgetMode(int Way){
        if (Way == 0) {
            Intent intent = getIntent();
            setResult(RESULT_OK , intent);
            finish();
        } else {
            showDialogRememberInfo();
        }
        storingTimesInSharedPreferences();
    }
   */





/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.startService(new Intent(this, UpdateService.class));
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            data = getIntent();
            setResult(RESULT_OK, data);
            finish();
        }
    }

     */






























/*
        .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {
               String str = "options1: " +  "\noptions3: " + options3;

              //  Toast.makeText(TimeSettingsActivity.this, str, Toast.LENGTH_SHORT).show();
              }
           })
         */
//pvNoLinkOptions.setNPicker( food,clothes,computer);


//Toast.makeText(DefaultActivity.this, str, Toast.LENGTH_SHORT).show();
//everyTimeTxt = "" + item;
//Log.i("OnPreSS0","No");
//everyTimeMenu = options3;
//everyTimeTxt =computer.get(options3);