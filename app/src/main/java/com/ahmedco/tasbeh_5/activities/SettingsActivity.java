package com.ahmedco.tasbeh_5.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ahmedco.tasbeh_5.adapters.AdapterDialogTimer;
import com.ahmedco.tasbeh_5.utils.KSP;
import com.ahmedco.tasbeh_5.R;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

//https://github.com/PuffoCyano/Range-Time-Picker-Dialog
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener,  RangeTimePickerDialog.ISelectedTime {

    private ToggleButton checkBtn;
    private int hourStart_ ,  minuteStart_ ,  hourEnd_ ,  minuteEnd_;
    // text_select_time_azcar7
    private String everyTime ,stopTimer;
    private EditText fromTime,toTime;
    public static KSP ksp1;
    private Button startRemember_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        startRemember_btn = (Button) findViewById(R.id.btn_start_remember_me2);
        startRemember_btn.setText(R.string.stop_remeber);
        checkBtn = (ToggleButton)findViewById(R.id.bt_check2);
        fromTime =  (EditText)findViewById(R.id.text_select_time_azcar7);
        toTime =  (EditText)findViewById(R.id.text_select_time_azcar8);
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        //btn_start_remember_me2
        fromTime.setText("");
        toTime.setText("");
         ksp1 = new KSP(SettingsActivity.this,"ksp");
        everyTime = "5 دقيقة";
        // RelativeLayout relativeclic1 =(RelativeLayout)findViewById(R.id.r_container_6);
        RelativeLayout layout2 = (RelativeLayout)findViewById(R.id.row_one);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.cont_click);
        // layout1.setOnClickListener(this);
        layout2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             Intent i = new Intent(SettingsActivity.this , ListAzcarActivity.class);
             startActivity(i);
             // do something
             Log.i("trace01_","Bom");
            // Log.i("trace0","Bom");
             //Toast.makeText(SettingsActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();

            }
        });


        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // do something
               //  Log.i("trace01_","Bom");
               // Log.i("trace0","Bom");
              //Toast.makeText(SettingsActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
              showDialogTimerSelected();
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
                    setTimer();
                    startRemember_btn.setText(R.string.start_remeber);
                    stopTimer="on";
                    Toast.makeText(SettingsActivity.this, "Clicked", Toast.LENGTH_LONG).show();
                    checkBtn.setBackgroundDrawable(getDrawable(d));
                }else{
                    int f = R.drawable.unchk;
                    stopTimer="off";
                    fromTime.setEnabled(false);
                    startRemember_btn.setText(R.string.stop_remeber);
                    toTime.setEnabled(false);
                    fromTime.setText("");
                    toTime.setText("");
                    Toast.makeText(SettingsActivity.this, "UnClicked", Toast.LENGTH_LONG).show();
                    checkBtn.setBackgroundDrawable(getDrawable(f));
                }
            }
        });
        ActionBarSetting();
    }


    private void setTimer(){
     // Create an instance of the dialog fragment and show it
      // Create an instance of the dialog fragment and show it
        RangeTimePickerDialog dialog = new RangeTimePickerDialog();
        dialog.newInstance(R.color.CyanWater, R.color.White, R.color.Yellow, R.color.colorPrimaryDark, true);
      FragmentManager fragmentManager = getFragmentManager();
     dialog.show(fragmentManager, "");
    }

    public void  onRelativeLayoutClicked(View V){
        //  Log.i("trace0","Bom");
    }

    public void Click_start_remember_me2(View V){
       /*
        String e1 , e2;
        e1 = fromTime.getText().toString();
        e2 = toTime.getText().toString();
        String [] items = {everyTime,stopTimer,e1,e2};
        //  String []items = {"a1","a2","a3","a4"};
        for(int i=0; i<items.length; i++){
            ksp1.saveFor("key"+i,items[i]);
        }
        Log.i("trec_x0 ", "" + ksp1.GetAll());
        */
        /*
        for(int j=0; j<items.length; j++){
            Log.i("trec_x0_ ", "" + ksp1.GetAll().get(0));
        }
        */
        // Log.i("trec_x0_ 1", "" + ksp1.GetAll().get(0));
        // Intent k = new Intent(SettingsActivity.this , ListAzcarActivity.class);
        Intent intent = new Intent(SettingsActivity.this , DialogRememberInfoActivity.class);
        intent.putExtra("e_time", everyTime);
        intent.putExtra("hour_star",hourStart_);
        intent.putExtra("hour_end",hourEnd_);
        intent.putExtra("minute_start",minuteStart_);
        intent.putExtra("minute_end",minuteEnd_);
        // k.putExtra("key",value);
        // hourStart_,  minuteStart_,  hourEnd_,  minuteEnd_
        if(stopTimer=="on") {
            startActivity(intent);
        }
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
                everyTime=""+item;
                Toast.makeText(SettingsActivity.this, ""+item, Toast.LENGTH_LONG).show();
            }
        });
         RV_dialogTimerSelected.setLayoutManager(new LinearLayoutManager(this));
         RV_dialogTimerSelected.setAdapter(adapterDialogTimer);
         btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent k = new Intent(SecondActivity.this ,SettingsActivity.class);
                //startActivity(k);
                dialogTimerSelected.dismiss();
            }
        });
        // used for display Dialog
        dialogTimerSelected.show();
  }
    //  Toast.makeText(SettingsActivity.this, ""+item.getName(), Toast.LENGTH_LONG).show();
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
        for(int i=0; i<10; i++){
           String[] Timers= {" 15 دقيقة"," 20 دقيقة"," 30 دقيقة"," 45 دقيقة","1 ساعه"," 2 ساعه"," 3 ساعه"," 4 ساعه"," 5 ساعه"," 10 ساعه"};
           lsitTimes.add(new ModelDialogTimer(""+Timers[i]));
        }
   */