package com.ahmedco.tasbeh_5.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ahmedco.tasbeh_5.adapters.AdapterDialogTimer;
import com.ahmedco.tasbeh_5.utils.DataSharedPreferences;
import com.ahmedco.tasbeh_5.R;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import java.util.Calendar;

import static com.ahmedco.tasbeh_5.activities.ListAzcarActivity.REQUEST_CODE;

//https://github.com/PuffoCyano/Range-Time-Picker-Dialog
public class TimeSettingsActivity extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime {

    private ToggleButton checkBtn;
    private static int hourStart_;
    private static int minuteStart_;
    private static int hourEnd_;
    private static int minuteEnd_;
    // text_select_time_azcar7 ---
    private String everyTimeString ;
    private int everyTimeNum=0;
    private int stopTimer = 0;
    public static EditText fromTime,toTime;
    //public static KSP ksp1;
    private Button startRemember_btn;
    public static DataSharedPreferences timesSharedPreferences;
    private static String AM_PM;
    private static int start_AM_PM,end_AM_PM;
    RelativeLayout rowOne ,rowTow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initiItems();
        fromTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             showTruitonTimePickerDialog(v,1);
            }
        });
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v,2);
            }
        });

        rowTow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             showDialogTimerSelected();
            }
         });
        checkBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton , boolean checked){
                checkBtn.setText(null);
                if(checked){
                    int idBackground = R.drawable.chk;
                    startRemember_btn.setText(R.string.start_remeber);
                    stopTimer=1;
                    //Toast.makeText(TimeSettingsActivity.this, "Clicked", Toast.LENGTH_LONG).show();
                    checkBtn.setBackgroundDrawable(getDrawable(idBackground));
                    fromTime.setEnabled(true);
                    toTime.setEnabled(true);
                }else{
                    int f = R.drawable.unchk;
                    fromTime.setText("");
                    toTime.setText("");
                    hourStart_= 0;
                    minuteStart_= 0;
                    hourEnd_=0;
                    minuteEnd_=0;
                    stopTimer=0;
                    startRemember_btn.setText(R.string.stop_remeber);
                    fromTime.setText("");
                    toTime.setText("");
                    Toast.makeText(TimeSettingsActivity.this, "UnClicked", Toast.LENGTH_LONG).show();
                    checkBtn.setBackgroundDrawable(getDrawable(f));
                }
            }
        });
        actionBarSetting();
    }

    private void initiItems(){
        hourStart_=0;  minuteStart_=0; hourEnd_ =0; minuteEnd_=0;start_AM_PM = 0; start_AM_PM = 2;;
        timesSharedPreferences = new DataSharedPreferences(this);
        startRemember_btn = (Button) findViewById(R.id.btn_start_remember_me2);
        startRemember_btn.setText(R.string.stop_remeber);
        checkBtn=(ToggleButton)findViewById(R.id.bt_check2);
        fromTime=(EditText)findViewById(R.id.text_select_time_from);
        toTime=(EditText)findViewById(R.id.text_select_time_to);
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        fromTime.setText("");
        toTime.setText("");
        everyTimeString = "15 دقيقة";
        rowOne = (RelativeLayout)findViewById(R.id.row_one);
        rowTow = (RelativeLayout)findViewById(R.id.row_three);
    }


    private void showTruitonTimePickerDialog(View v , int editTextId) {
       DialogFragment newFragment = new TimePickerFragment(editTextId);
       newFragment.show(getSupportFragmentManager(), "timePicker");
    }


    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        int currentEditText = 0 ;

        public TimePickerFragment(int id) {
            currentEditText = id;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this , hour , minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay , int minute){
            String LastAM_PM = AM_PM;
            setAM_PM(hourOfDay);
          if(currentEditText==1){
             putTimefrom(hourOfDay,minute);
         }else if(currentEditText==2){
              checkTime(hourOfDay,minute,LastAM_PM);
           }
       }
        private void checkTime(int  hourOfDay,int minute,String LastAM_PM){
            if(AM_PM == LastAM_PM ){
                if(hourOfDay ==hourStart_ && minute<=minuteStart_) {
                    Toast.makeText(getActivity(), "ادخلت قيمه خطأ", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(hourOfDay<hourStart_) {
                    Toast.makeText(getActivity(), "ادخلت قيمه خطأ", Toast.LENGTH_LONG).show();
                    return;
                }
                putTimeTo(hourOfDay,minute);
            }
        }
        private void putTimeTo(int hourOfDay,int minute){
            if(AM_PM =="AM")end_AM_PM = 1;
            if(AM_PM =="PM")end_AM_PM = 2;
            toTime.setText("");
            toTime.setText(""+hourOfDay+" : "+minute+" "+AM_PM);
            hourEnd_=hourOfDay;
            minuteEnd_=minute;
            toTime.setEnabled(false);
        }
        private void putTimefrom(int hourOfDay,int minute){
            fromTime.setText("");
            if(AM_PM =="AM")start_AM_PM = 1;
            if(AM_PM =="PM")start_AM_PM = 2;
            fromTime.setText(""+hourOfDay+" : "+minute+" "+AM_PM);
            hourStart_= hourOfDay;
            minuteStart_= minute;
            fromTime.setEnabled(false);
        }
        private String setAM_PM(int hourOfDay){
            if(hourOfDay<12){
                AM_PM = "AM";
            }else {
                AM_PM = "PM";
            }
            return AM_PM;
        }
    }

    public void gotoWidgetMode(View V){
        Intent intent = new Intent(TimeSettingsActivity.this , DialogRememberInfoActivity.class);
        intent.putExtra("e_time", everyTimeString);
        intent.putExtra("hour_star",hourStart_);
        intent.putExtra("hour_end",hourEnd_);
        intent.putExtra("minute_start",minuteStart_);
        intent.putExtra("minute_end",minuteEnd_);
        storingTimes();
       if(stopTimer==1) {
           checkEditBoxTime(intent);
        }else{
           startActivityForResult(intent , REQUEST_CODE);
       }
    }

    private void storingTimes(){
        String[] NamseOfItems = {"everyTime","stopTimer","hour_start","hour_end","start_AM_PM","minute_start","minute_end","end_AM_PM"} ;
        int[] ValuesOfItems = {everyTimeNum,stopTimer , hourStart_, hourEnd_, start_AM_PM,minuteStart_, minuteEnd_, end_AM_PM};
        for(int i=0; i<NamseOfItems.length; i++){
            timesSharedPreferences.setInt(NamseOfItems[i],ValuesOfItems[i]);
        }
    }

    private void checkEditBoxTime(Intent intent){
        String BoxText_1 = fromTime.getText().toString();
        String BoxText_2 = toTime.getText().toString();
        if (BoxText_1.matches("") || BoxText_2.matches("")) {
            Toast.makeText(this, "يوجد اماكن فارغة", Toast.LENGTH_LONG).show();
            return;
        }
        startActivityForResult(intent , REQUEST_CODE);
    }


    private void showDialogTimerSelected(){
        final Dialog dialogTimerSelected = new Dialog(this);
        dialogTimerSelected.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RecyclerView RV_dialogTimerSelected;
        AdapterDialogTimer adapterDialogTimer;
        Button btnCancel;
        // setting custom layout in out dialog
        dialogTimerSelected.setContentView(R.layout.dialog_timer_selected);
        // findViewById for views inside dialog
        RV_dialogTimerSelected = dialogTimerSelected.findViewById(R.id.rv_dialogTimerSelected);
        btnCancel = dialogTimerSelected.findViewById(R.id.btnCancel);
        // programatically setting hight and witdh of dialog
        Window window = dialogTimerSelected.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // setting gravity of dialog so it will diaply in the center of screen
        window.setGravity(Gravity.CENTER);
        // setting TRANSPARENT Background to myDialog to display myDialog with rounded corner
        dialogTimerSelected.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         // adding data in to arrayList
        String[] Timers= {" 15 دقيقة"," 20 دقيقة"," 30 دقيقة"," 45 دقيقة","1 ساعه"," 2 ساعه"," 3 ساعه"," 4 ساعه"," 5 ساعه"," 10 ساعه"};
        adapterDialogTimer = new AdapterDialogTimer(this, Timers, new AdapterDialogTimer.OnItemClickListener() {
            @Override
            public void onItemClick(String item, int position) {
              TextView txt_v=(TextView)findViewById(R.id.text_minute);
              txt_v.setText(item);
               everyTimeString=""+item;
                everyTimeNum = position;
                Toast.makeText(TimeSettingsActivity.this, ""+position, Toast.LENGTH_LONG).show();
            }
        });
         RV_dialogTimerSelected.setLayoutManager(new LinearLayoutManager(this));
         RV_dialogTimerSelected.setAdapter(adapterDialogTimer);
         btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent k = new Intent(SecondActivity.this ,TimeSettingsActivity.class);
                //startActivity(k);
                dialogTimerSelected.dismiss();
            }
        });
        // used for display Dialog
        dialogTimerSelected.show();
  }

    //  Toast.makeText(TimeSettingsActivity.this, ""+item.getName(), Toast.LENGTH_LONG).show();
    private void actionBarSetting(){
      this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
       getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
         getSupportActionBar().setElevation(1);
          View view = getSupportActionBar().getCustomView();
           //getSupportActionBar().setIcon(R.drawable.dot1);
            ImageView imageButton = (ImageView)findViewById(R.id.Icon);
             imageButton.setImageResource(R.drawable.arrow_left_black);
              // imageButton.setPaddingRelative(20,20,20,20);
               //imageButton.setForegroundGravity(Ri);
               TextView textTitle = view.findViewById(R.id.text_title);
                view.findViewById(R.id.l_container).setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                 textTitle.setText(R.string.select_azkar);
                  textTitle.setGravity(Gravity.CENTER | Gravity.BOTTOM);
   }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            this.startService(new Intent(this , AzcarWidget.UpdateService.class));
            if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                data = getIntent();
                setResult(RESULT_OK , data);
                finish();
            }
          }catch(Exception ex) {

      }
   }


    @Override
    public void onSelectedTime(int hourStart , int minuteStart , int hourEnd , int minuteEnd){
        fromTime.setText(""+hourStart+" : "+minuteStart);
        toTime.setText(""+hourEnd+" : "+minuteEnd);
        hourStart_=hourStart;
        hourEnd_=hourEnd;
        minuteStart_=minuteStart;
        minuteEnd_=minuteEnd;
    }
}
