package com.ahmedco.tasbeh_5.activities;

import android.content.Context;
import android.content.Intent;
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

import com.ahmedco.tasbeh_5.R;
import com.ahmedco.tasbeh_5.models.Times;
import com.ahmedco.tasbeh_5.utils.DataSharedPreferences;

import java.util.HashMap;

import static com.ahmedco.tasbeh_5.activities.ListAzcarActivity.REQUEST_CODE;
//https://github.com/PuffoCyano/Range-Time-Picker-Dialog


public class TimeSettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static String AM_PM;
    private final int maxStopTime = 360;
    public HashMap<String, Integer> hmapTimes;
    public EditText fromTime, toTime;
    public DataSharedPreferences timesSharedPreferences;
    public DialogFragment timeFromPickerDialog, timeToPickerDialog;
    Times times;
    RelativeLayout rowTow;
    TextView txtRememberMeEvery;
    private ToggleButton checkBtn;
    private String everyTimeString;
    private int stopTimer;
    private Button startRemember_btn;
    private DialogeRepeatTime dialogeRepeatTime;
    private Context context;

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
        new ActionBarView(this, this.getResources().getString(R.string.select_azkar));
    }


    private void initiItems() {
        hmapTimes = new HashMap<String, Integer>();
        times = new Times();
        dialogeRepeatTime = null;
        timesSharedPreferences = new DataSharedPreferences(this);
        startRemember_btn = (Button) findViewById(R.id.startRemember_btn_);
        startRemember_btn.setText(R.string.stop_remeber);
        checkBtn = (ToggleButton) findViewById(R.id.bt_check2);
        fromTime = (EditText) findViewById(R.id.text_select_time_from);
        toTime = (EditText) findViewById(R.id.text_select_time_to);
        txtRememberMeEvery = (TextView) findViewById(R.id.text_minute);
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        fromTime.setText("");
        toTime.setText("");
        everyTimeString = "1 دقيقة";
        // rowOne = (RelativeLayout) findViewById(R.id.row_one);
        rowTow = (RelativeLayout) findViewById(R.id.row_everytime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_select_time_from:
                //Log.i("trace_Time0", "111");
                showTimePickerDialog(v, 1);
                break;
            case R.id.text_select_time_to:
                //Log.i("trace_Time2", "22");
                showTimePickerDialog(v, 2);
                break;
            case R.id.row_everytime:
                dialogeRepeatTime = new DialogeRepeatTime(this, txtRememberMeEvery);
                break;
            case R.id.startRemember_btn_:
                checkTimFromTo();
                break;
        }
    }

    private void checkTimFromTo() {
        boolean boxesTimeIsEmpty = isEmpty(fromTime, toTime);
        initValuesOfTimes();
        if (boxesTimeIsEmpty) {
            String fromTimeFormatting = (String) getTimeWithFormatting((TimePickerDialog) timeFromPickerDialog);
            String toTimeFormatting = (String) getTimeWithFormatting((TimePickerDialog) timeToPickerDialog);
            int getTimeBetweenTwoTimes = Integer.parseInt(new CompareTwoTime().compareTwoTimeAMPM(fromTimeFormatting, toTimeFormatting));
            if (getTimeBetweenTwoTimes > maxStopTime) {
                Toast.makeText(this, "لايجب ان يزيد وقت التوقف عن 6 ساعات", Toast.LENGTH_LONG).show();
            } else {
                gotoWidgetMode(stopTimer);
            }
        } else {
            if (stopTimer == 0) {
                gotoWidgetMode(stopTimer);
            } else {
                Toast.makeText(this, "يوجد اماكن فارغة", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void gotoWidgetMode(int Way) {
        if (Way == 0) {
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            showDialogRememberInfo();
        }
        storingTimesInSharedPreferences();
    }


    private void showDialogRememberInfo() {
        Intent intent = new Intent(TimeSettingsActivity.this, RememberInfoActivity.class);
        String fromTimeFormatting = (String) getTimeWithFormatting((TimePickerDialog) timeFromPickerDialog);
        String toTimeFormatting = (String) getTimeWithFormatting((TimePickerDialog) timeToPickerDialog);
        intent.putExtra("every_time", everyTimeString);
        intent.putExtra("starttime", fromTimeFormatting);
        intent.putExtra("endtime", toTimeFormatting);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void initValuesOfTimes() {

        if (dialogeRepeatTime == null)
            dialogeRepeatTime = new DialogeRepeatTime(this, txtRememberMeEvery);
        if (timeFromPickerDialog == null) timeFromPickerDialog = new TimePickerDialog(1, fromTime);
        if (timeToPickerDialog == null) timeToPickerDialog = new TimePickerDialog(2, toTime);

        int hourStartTime = ((TimePickerDialog) timeFromPickerDialog).hourOfDay;
        int minuteStartTime = ((TimePickerDialog) timeFromPickerDialog).minute;
        String startAM_PM = ((TimePickerDialog) timeFromPickerDialog).AM_PM;
        int hourEndTime = ((TimePickerDialog) timeToPickerDialog).hourOfDay;
        int minuteEndTimer = ((TimePickerDialog) timeToPickerDialog).minute;
        String endAM_PM2 = ((TimePickerDialog) timeToPickerDialog).AM_PM;

        int end_ampm = 0;
        int start_ampm = 0;

        if (endAM_PM2 == "AM") end_ampm = 1;
        if (endAM_PM2 == "PM") end_ampm = 2;
        if (startAM_PM == "AM") start_ampm = 1;
        if (startAM_PM == "PM") start_ampm = 2;

        everyTimeString = String.valueOf(dialogeRepeatTime.everyTime + 1) + " دقيقه ";

        if (stopTimer == 0) {
            times.setEveryTime(dialogeRepeatTime.everyTime);
            times.setStopTimer(stopTimer);
        } else {
            times.setEveryTime(dialogeRepeatTime.everyTime);
            times.setStopTimer(stopTimer);
            times.setEnd_AM_PM(end_ampm);
            times.setStart_AM_PM(start_ampm);
            times.setHour_start(hourStartTime);
            times.setHour_end(hourEndTime);
            times.setMinute_start(minuteStartTime);
            times.setMinute_end(minuteEndTimer);
        }
    }

    private void storingTimesInSharedPreferences() {
        timesSharedPreferences.saveOject(times);
    }

    private String getTimeWithFormatting(TimePickerDialog currentTimePickerDialog) {
        if (timeFromPickerDialog == null) timeFromPickerDialog = new TimePickerDialog(1, fromTime);
        if (timeToPickerDialog == null) timeToPickerDialog = new TimePickerDialog(2, toTime);
        int hourTimer = ((TimePickerDialog) currentTimePickerDialog).hourOfDay;
        int minuteTimer = ((TimePickerDialog) currentTimePickerDialog).minute;
        String timeAM_PM = ((TimePickerDialog) currentTimePickerDialog).AM_PM;
        return "" + hourTimer + ":" + minuteTimer + " " + timeAM_PM;
    }

    private boolean isEmpty(EditText fromTime_, EditText toTime_) {
        if (fromTime_.getText().toString().trim().length() == 0 || toTime_.getText().toString().trim().length() == 0) {
            return false;
        } else {
            return true;
        }
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
        //Toast.makeText(TimeSettingsActivity.this, "Clicked", Toast.LENGTH_LONG).show();
        checkBtn.setBackgroundDrawable(getDrawable(idBackground));
        fromTime.setEnabled(true);
        toTime.setEnabled(true);
    }

    private void showTimePickerDialog(View v, int editTextId) {
        if (editTextId == 1) {
            timeFromPickerDialog = new TimePickerDialog(editTextId, fromTime);
            timeFromPickerDialog.show(getSupportFragmentManager(), "timeFromPickerDialog");
        } else if (editTextId == 2) {
            timeToPickerDialog = new TimePickerDialog(editTextId, toTime);
            timeToPickerDialog.show(getSupportFragmentManager(), "timeToPickerDialog");
        }
    }

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
}