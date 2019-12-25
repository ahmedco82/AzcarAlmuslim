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
import android.util.Log;
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
public class TimeSettingsActivity extends AppCompatActivity implements View.OnClickListener,  RangeTimePickerDialog.ISelectedTime {

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
    public static DataSharedPreferences dataTimes;
    private static String AM_PM;
    private static int start_AM_PM,end_AM_PM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hourStart_=0;  minuteStart_=0; hourEnd_ =0; minuteEnd_=0;start_AM_PM = 0; start_AM_PM = 2;;
        dataTimes = new DataSharedPreferences(this);
        startRemember_btn = (Button) findViewById(R.id.btn_start_remember_me2);
        startRemember_btn.setText(R.string.stop_remeber);
        checkBtn = (ToggleButton)findViewById(R.id.bt_check2);
        fromTime =  (EditText)findViewById(R.id.text_select_time_azcar7);
        toTime =  (EditText)findViewById(R.id.text_select_time_azcar8);
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        // fromTime.setEnabled(false);
        // toTime.setEnabled(false);
        // btn_start_remember_me2
        fromTime.setText("");
        toTime.setText("");
        // ksp1 = new KSP(TimeSettingsActivity.this,"ksp");
        everyTimeString = "15 دقيقة";
        // RelativeLayout relativeclic1 =(RelativeLayout)findViewById(R.id.r_container_6);
        RelativeLayout layout2 = (RelativeLayout)findViewById(R.id.row_one);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.cont_click);
        // layout1.setOnClickListener(this);
        layout2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             Intent i = new Intent(TimeSettingsActivity.this , ListAzcarActivity.class);
             // startActivity(i);
             // do something
             Log.i("trace01_","Bom");
             //Log.i("trace0","Bom");
             //Toast.makeText(TimeSettingsActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
            }
        });


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


        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             // do something
             // Log.i("trace01_","Bom");
             // Log.i("trace0","Bom");
            //  Toast.makeText(TimeSettingsActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
             showDialogTimerSelected();
             // showTruitonTimePickerDialog(v);
            }
         });
           // btn.setOnClickListener(btnListener);
        checkBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked){
                // Change testLinearLayout direction based on toggle button checked status.
                // Intent k = new Intent(ListAzcarActivity.this , DialogRememberInfoActivity.class);
                // startActivity(k);
                checkBtn.setText(null);
                if(checked){
                    int d = R.drawable.chk;
                    // fromTime.setEnabled(true);
                    // toTime.setEnabled(true);
                    startRemember_btn.setText(R.string.start_remeber);
                    stopTimer=1;
                    Toast.makeText(TimeSettingsActivity.this, "Clicked", Toast.LENGTH_LONG).show();
                    checkBtn.setBackgroundDrawable(getDrawable(d));
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
        ActionBarSetting();
    }

    private void showTruitonTimePickerDialog(View v , int editTextId) {
       DialogFragment newFragment = new TimePickerFragment(editTextId);
       newFragment.show(getSupportFragmentManager(), "timePicker");
    }


    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
          int currentEditText = 0 ;

         boolean TimerOkyOrNot = true;

        public TimePickerFragment(int id) {
            currentEditText=id;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Log.i("trace_time-00000____: ",""+AmPm);
            return new TimePickerDialog(getActivity(), this , hour , minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay , int minute){
            // Do something with the time chosen by the user
            String LastAM_PM = AM_PM;
            // start_AM_PM,end_AM_PM
            if(hourOfDay<12) {
             AM_PM = "AM";
            }else {
              AM_PM = "PM";
           }
          if(currentEditText==1){
             fromTime.setText("");
             if(AM_PM =="AM")start_AM_PM = 1;
             if(AM_PM =="PM")start_AM_PM = 2;
             fromTime.setText(""+hourOfDay+" : "+minute+" "+AM_PM);
             hourStart_= hourOfDay;
             minuteStart_= minute;
             fromTime.setEnabled(false);

         }else if(currentEditText==2){
          if(AM_PM == LastAM_PM ){
              if(hourOfDay ==hourStart_ && minute<=minuteStart_) {
                  Toast.makeText(getActivity(), "ادخلتك قيمه خطأ", Toast.LENGTH_LONG).show();
                  return;
              }
              else if(hourOfDay<hourStart_) {
                  Toast.makeText(getActivity(), "ادخلتك قيمه خطأ", Toast.LENGTH_LONG).show();
                  return;
              }
             }
              if(AM_PM =="AM")end_AM_PM = 1;
              if(AM_PM =="PM")end_AM_PM = 2;
              toTime.setText("");
              toTime.setText(""+hourOfDay+" : "+minute+" "+AM_PM);
              hourEnd_=hourOfDay;
              minuteEnd_=minute;
              toTime.setEnabled(false);
           }
       }
    }
    //putEndTime

    public void  onRelativeLayoutClicked(View V){

        //  Log.i("trace0","Bom");

    }

    public void Click_start_remember_me2(View V){

        Intent intent = new Intent(TimeSettingsActivity.this , DialogRememberInfoActivity.class);
        intent.putExtra("e_time", everyTimeString);
        intent.putExtra("hour_star",hourStart_);
        intent.putExtra("hour_end",hourEnd_);
        intent.putExtra("minute_start",minuteStart_);
        intent.putExtra("minute_end",minuteEnd_);

        String[] NamseOfItems = {"everyTime","stopTimer","hour_start","hour_end","start_AM_PM","minute_start","minute_end","end_AM_PM"} ;
        int[] ValuesOfItems = {everyTimeNum,stopTimer , hourStart_, hourEnd_, start_AM_PM,minuteStart_, minuteEnd_, end_AM_PM};

        //start_AM_PM = 0; start_AM_PM
        for(int i=0; i<NamseOfItems.length; i++){
             dataTimes.setInt(NamseOfItems[i],ValuesOfItems[i]);
        }

       String BoxText_1 = fromTime.getText().toString();
       String BoxText_2 = toTime.getText().toString();

       if(stopTimer==1) {
           if (BoxText_1.matches("") || BoxText_2.matches("")) {
               Toast.makeText(this, "يوجد اماكن فارغة", Toast.LENGTH_LONG).show();
               return;
           }
       }
        startActivityForResult(intent, REQUEST_CODE);
    }

       /*
       intent = getIntent();
       //intent.putExtra("key", "value");
       setResult(RESULT_OK , intent);
       finish();
       */

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
    private void ActionBarSetting(){
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
    public void onClick(View v){

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
          }catch(Exception ex){

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



     /*
        String e1 , e2;
        e1 = fromTime.getText().toString();
        e2 = toTime.getText().toString();
        String [] items = {everyTime,stopTimer,e1,e2};
        */


        /*
        for(int i=0; i<10; i++){
           String[] Timers= {" 15 دقيقة"," 20 دقيقة"," 30 دقيقة"," 45 دقيقة","1 ساعه"," 2 ساعه"," 3 ساعه"," 4 ساعه"," 5 ساعه"," 10 ساعه"};
           lsitTimes.add(new ModelDialogTimer(""+Timers[i]));
        }
   */