package com.ahmedcom.tasbeh55.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.ahmedcom.BackPressedCallingBack;
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
// https://github.com/PuffoCyano/Range-Time-Picker-Dialog


public class TimeSettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static String AM_PM;
    private final int maxStopTime = 360;
    public HashMap<String, Integer> hmapTimes;
    public EditText fromTime, toTime;
    // public DataSharedPreferences globalSharedPreferences;
    public DialogFragment timeFromPickerDialog, timeToPickerDialog;
    public int everyTime;
    public String everyTimeTxt, pramEveryTime;
    //Times times;
    RelativeLayout rowTow;
    TextView txtRememberMeEvery;
    TextView titleRow;
    private ToggleButton checkBtn;
    private String everyTimeString;
    private int stopTimer;
    private Button startRemember_btn;
    private DialogeRepeatTime dialogeRepeatTime;
    private Context context;
    // private int everyTimeMenu;
    private ArrayList<String> times;
    private ArrayList<String> food;
    private ArrayList<String> clothes;
    private OptionsPickerView optionsPickerTimes;
    private Times time;
    private String fromTimeFormatting;
    private String toTimeFormatting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initiItems();
        fromTime.setOnClickListener(this);
        toTime.setOnClickListener(this);
        rowTow.setOnClickListener(this);
        startRemember_btn.setOnClickListener(this);
        checkBtn.setOnCheckedChangeListener(this);
        // new ActionBarView(this, this.getResources().getString(R.string.select_azkar));
    }

    private void initiItems() {
        if(timeFromPickerDialog == null)
        timeFromPickerDialog = new TimePickerDialog(time, 1, fromTime);
        if (timeToPickerDialog == null) timeToPickerDialog = new TimePickerDialog(time, 2, toTime);
        everyTime = 0;
        everyTimeTxt = "1 دقيقة";
        pramEveryTime = "1 دقيقة";
        times = new ArrayList<>();
        food = new ArrayList<>();
        clothes = new ArrayList<>();
        titleRow = (TextView) findViewById(R.id.text_minute);
        String[] Times = {" 1 دقيقة", " 2 دقيقة", " 3 دقيقة", " 4 دقيقة", "5 دقيقة", " 10 دقيقة", " 15 دقيقة", "30 دقيقة", " 1 ساعه", " 2 ساعه"};
        for (int i = 0; i < Times.length; i++) {
            times.add(Times[i]);
        }
        optionsPicker();
        Bitmap bitmap = BitmapFactory.decodeResource(TimeSettingsActivity.this.getResources(), R.drawable.left_arrow);
        new ActionBarView(TimeSettingsActivity.this, "أوقات الاذكار", bitmap, new BackPressedCallingBack(TimeSettingsActivity.this));
        hmapTimes = new HashMap<String, Integer>();
        time = new Times();
        dialogeRepeatTime = null;
        startRemember_btn = (Button) findViewById(R.id.startRemember_btn_);
        checkBtn = (ToggleButton) findViewById(R.id.bt_check2);
        fromTime = (EditText) findViewById(R.id.text_select_time_from);
        toTime = (EditText) findViewById(R.id.text_select_time_to);
        // txtRememberMeEvery = (TextView) findViewById(R.id.text_minute);
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        fromTime.setText("");
        toTime.setText("");
        everyTimeString = "1 دقيقة";
        rowTow = (RelativeLayout) findViewById(R.id.row_everytime);
    }

    private void optionsPicker(){
       optionsPickerTimes = new OptionsPickerBuilder(this , new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int index, View v) {
                everyTimeTxt = times.get(index);
                everyTime = index;
                everyTimeString = times.get(index);
                titleRow.setText(everyTimeTxt);
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
            case R.id.row_selectAzcar:
                break;
            case R.id.text_select_time_to:
                showTimePickerDialog(v, 2);
                break;
            case R.id.row_everytime:
                optionsPickerTimes.show();
                break;
            case R.id.startRemember_btn_:
                //test0();
                checkTimFromTo();
                break;
        }
    }

    public void clickRowSelectAzcar(View view) {
        Intent intent = new Intent(TimeSettingsActivity.this, ListAzcarActivity.class);
        startActivity(intent);
    }

    private void checkTimFromTo() {
        boolean boxesTimeIsEmpty = TextUtils.isEmpty(fromTime, toTime);
        if (boxesTimeIsEmpty) {
            saveItems();
            fromTimeFormatting = TextUtils.getTimeFromFormatting(TimeSettingsActivity.this);
            toTimeFormatting = TextUtils.getTimeToFormatting(TimeSettingsActivity.this);
            int getTimeBetweenTwoTimes = Integer.parseInt(TimeUtils.compareTwoTime(fromTimeFormatting, toTimeFormatting));
            if (getTimeBetweenTwoTimes > maxStopTime) {
                Toast.makeText(this, "لايجب ان يزيد وقت التوقف عن 6 ساعات", Toast.LENGTH_LONG).show();
            } else {
                showDialogRememberInfo();
            }
        } else {
            if (stopTimer == 0) {
                showDialogRememberInfo();
            } else {
                Toast.makeText(this, "يوجد اماكن فارغة", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveItems() {
        time.setEveryTime(everyTime);
        time.setStopTimer(stopTimer);
        SharedPreferencesUtils.saveTimes(TimeSettingsActivity.this, time);
    }

    private void showDialogRememberInfo() {
        Intent intent = new Intent(TimeSettingsActivity.this, RememberInfoActivity.class);
        intent.putExtra("every_time", everyTimeString);
        intent.putExtra("starttime", fromTimeFormatting);
        intent.putExtra("endtime", toTimeFormatting);
        startActivity(intent);
        //finish();
        //startActivityForResult(intent , 1);
        //finish();
        //Toast.makeText(this,getString(R.string.text_btn_stop_remember), Toast.LENGTH_LONG).show();
        //closeApp();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkBtn.setText(null);
        if (isChecked == true) {
            openStopTime();
        } else {
            closeStopTime();
        }
    }

    private void closeStopTime() {
        int f = R.drawable.unchk;
        fromTime.setText("");
        toTime.setText("");
        stopTimer = 0;
        startRemember_btn.setText(R.string.stop_remeber);
        fromTime.setText("");
        toTime.setText("");
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        Toast.makeText(TimeSettingsActivity.this, "UnClicked", Toast.LENGTH_LONG).show();
        checkBtn.setBackgroundDrawable(getDrawable(f));
    }

    private void openStopTime() {
        int idBackground = R.drawable.chk;
        startRemember_btn.setText(R.string.start_remeber);
        stopTimer = 1;
        // Toast.makeText(TimeSettingsActivity.this, "Clicked", Toast.LENGTH_LONG).show();
        checkBtn.setBackgroundDrawable(getDrawable(idBackground));
        fromTime.setEnabled(true);
        toTime.setEnabled(true);
    }

    private void showTimePickerDialog(View v, int editTextId) {
        if (editTextId == 1) {
            timeFromPickerDialog = new TimePickerDialog(time, editTextId, fromTime);
            timeFromPickerDialog.show(getSupportFragmentManager(), "timeFromPickerDialog");
        } else if (editTextId == 2) {
            timeToPickerDialog = new TimePickerDialog(time, editTextId, toTime);
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