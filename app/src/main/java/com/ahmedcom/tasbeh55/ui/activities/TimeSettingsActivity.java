package com.ahmedcom.tasbeh55.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import com.ahmedcom.BackPressedCallingBack;
import com.ahmedcom.tasbeh55.interfaces.HasBack;
import com.ahmedcom.tasbeh55.presenter.TimeSettingsInteractor;
import com.ahmedcom.tasbeh55.presenter.ViewTimeSettings;
import com.ahmedcom.tasbeh55.presenter.TimeSettingsPresenter;
import com.ahmedcom.tasbeh55.ui.others.ActionBarView;
import com.ahmedcom.tasbeh55.ui.dialoges.TimePickerDialog;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.utils.TimeUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.ahmedcom.tasbeh55.databinding.ActivitySettimesBinding;

// https://github.com/PuffoCyano/Range-Time-Picker-Dialog

public class TimeSettingsActivity extends AppCompatActivity implements HasBack, View.OnClickListener, CompoundButton.OnCheckedChangeListener, ViewTimeSettings {

    public DialogFragment timeFromPickerDialog, timeToPickerDialog;
    public int everyTimeIndex;
    public String everyTimeTxt;
    TimeSettingsPresenter presenter;
    Bitmap bitmap;
    private ActivitySettimesBinding binding;
    private String everyTimeString;
    private int stopTimer;
    private OptionsPickerView optionsPickerTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settimes);
        initiItems();
        binding.textSelectTimeFrom.setOnClickListener(this);
        binding.textSelectTimeTo.setOnClickListener(this);
        binding.rowEverytime.setOnClickListener(this);
        binding.threeRowEverytime.setOnClickListener(this);
        binding.buttonStartAlarm.setOnClickListener(this);
        binding.ButtonStopAlarm.setOnCheckedChangeListener(this);
    }

    private void initiItems() {
        everyTimeIndex = 0;
        everyTimeTxt = "1 دقيقة";
        presenter = new TimeSettingsPresenter(this, new TimeSettingsInteractor());
        if(timeFromPickerDialog == null)
          timeFromPickerDialog = new TimePickerDialog(presenter, 1, binding.textSelectTimeFrom);
        if(timeToPickerDialog == null)
          timeToPickerDialog = new TimePickerDialog(presenter, 2, binding.textSelectTimeTo);
        bitmap = BitmapFactory.decodeResource(TimeSettingsActivity.this.getResources(), R.drawable.left_arrow);
        new ActionBarView(TimeSettingsActivity.this, getString(R.string.times_of_azcar), bitmap, new BackPressedCallingBack(TimeSettingsActivity.this));
        presenter.defaultTimesOfBoxes();
        everyTimeString = "1 دقيقة";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_select_time_from:
                presenter.pickerClockDialog(1);
                break;
            case R.id.text_select_time_to:
                presenter.pickerClockDialog(2);
                break;
            case R.id.row_everytime:
                optionsPickerTimes.show();
                break;
            case R.id.three_row_everytime:
                presenter.saveTimes(everyTimeIndex , 0);
                navigateTolistAzcar();
                break;
            case R.id.button_startAlarm:
                presenter.saveTimes(everyTimeIndex , stopTimer);
                presenter.pickerDialogRememberInfo(binding.textSelectTimeFrom , binding.textSelectTimeTo);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        binding.ButtonStopAlarm.setText(null);
        presenter.stopTimeAlert(!isChecked);
    }

    @Override
    public void setStopTimeAlert() {
        int idBackground = R.drawable.chk;
        stopTimer = 1;
        binding.ButtonStopAlarm.setBackgroundDrawable(getDrawable(idBackground));
        binding.textSelectTimeFrom.setEnabled(true);
        binding.textSelectTimeTo.setEnabled(true);
    }

    @Override
    public void showDialogRememberInfo() {
        Intent intent = new Intent(TimeSettingsActivity.this, RememberInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void pickerDialogEveryTime() {
        optionsPickerTimes = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int index, View v) {
                everyTimeTxt = TimeUtils.getEverytime().get(index);
                everyTimeIndex = index;
                everyTimeString = TimeUtils.getEverytime().get(index);
                TextView insideTheIncludedLayout = (TextView) binding.rowEverytime.findViewById(R.id.text_minute);
                insideTheIncludedLayout.setText(everyTimeTxt);
            }
        }).setItemVisibleCount(5).setSelectOptions(0, 0, 0).build();
        optionsPickerTimes.setNPicker(TimeUtils.getEverytime());
        optionsPickerTimes.setSelectOptions(0, 0, 0);
    }

    @Override
    public void clearTextBoxesOfTimes() {
        int f = R.drawable.unchk;
        stopTimer = 0;
        presenter.defaultTimesOfBoxes();
        binding.ButtonStopAlarm.setBackgroundDrawable(getDrawable(f));
    }
    @Override
    public void showClockDialogFrom() {
        timeFromPickerDialog = new TimePickerDialog(presenter, 1, binding.textSelectTimeFrom);
        timeFromPickerDialog.show(getSupportFragmentManager(), "timeFromPickerDialog");
    }

    @Override
    public void showClockDialogTo(){
        timeToPickerDialog = new TimePickerDialog(presenter, 2, binding.textSelectTimeTo);
        timeToPickerDialog.show(getSupportFragmentManager(), "timeToPickerDialog");
    }

    @Override
    public void clearTimesOfBoxes() {
        binding.textSelectTimeFrom.setText("");
        binding.textSelectTimeTo.setText("");
    }
    @Override
    public void disabledTimesOfBoxes() {
        binding.textSelectTimeFrom.setEnabled(false);
        binding.textSelectTimeTo.setEnabled(false);
    }

    public void navigateTolistAzcar(){
        Intent intent = new Intent(this, ListAzcarActivity.class);
        startActivity(intent);
    }
}

























//  binding.textSelectTimeFrom.setEnabled(false);
// binding.textSelectTimeTo.setEnabled(false);
// binding.textSelectTimeFrom.setText("");
// binding.textSelectTimeTo.setText("");
   /*
        binding.textSelectTimeFrom.setText("");
        binding.textSelectTimeTo.setText("");
        binding.textSelectTimeFrom.setEnabled(false);
        binding.textSelectTimeTo.setEnabled(false);

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