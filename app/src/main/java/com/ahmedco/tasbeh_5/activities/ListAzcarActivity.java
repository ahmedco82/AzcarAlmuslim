package com.ahmedco.tasbeh_5.activities;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedco.tasbeh_5.R;
import com.ahmedco.tasbeh_5.WidgetAzcar_;
import com.ahmedco.tasbeh_5.utils.DataProccessor;
import com.ahmedco.tasbeh_5.utils.KSP;
import com.ahmedco.tasbeh_5.utils.S_pref;
import com.ahmedco.tasbeh_5.utils.SavePref;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//https://stackoverflow.com/questions/5914234/how-to-share-data-between-activity-and-widget
public class ListAzcarActivity extends AppCompatActivity {

        public static final int REQUEST_CODE = 1;
        ToggleButton[] listBtnCheck;
        ToggleButton[] listBtnPlyStop;
        RadioGroup[] radioGroup;
        TextView[] textListTitle;
        int[] loopSound;
        String[] Azkar;
        boolean[] soundsBoolean;
        int lengthListSounds;
        MediaPlayer mp1;
        static DataProccessor dataProccessor;


    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        // whatever code is needed for initialization goes here

        @Override
        protected void onCreate(Bundle savedInstanceState){
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_list_azcar);
          dataProccessor = new DataProccessor(this);
          ActionBarSetting();
            // ---------------------------------------------------------------------------------------------
            setResult(RESULT_CANCELED);
            // -----------------------------------------------------------------------------------------------
            lengthListSounds = 6;
            mp1 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this , R.raw.a1);
            final MediaPlayer mp2 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a2);
            final MediaPlayer mp3 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a3);
            final MediaPlayer mp4 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a4);
            final MediaPlayer mp5 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a5);
            int[] rowId = new int[]{ R.id.r1,R.id.r2,R.id.r3,R.id.r4,R.id.r5,R.id.r6 };
            listBtnCheck   = new ToggleButton[lengthListSounds];
            listBtnPlyStop = new ToggleButton[lengthListSounds];
            View [] viewR  = new View[lengthListSounds];
            textListTitle  = new TextView[lengthListSounds];
            soundsBoolean  = new boolean[lengthListSounds];
            radioGroup  = new RadioGroup[lengthListSounds];
            loopSound = new int[lengthListSounds];
            // RadioGroup group= (RadioGroup)v.findViewById(R.id.segmentedGroup);
            Resources res = getResources();
            Azkar = res.getStringArray(R.array.kind_of_azkar);
              for (int i = 0; i<listBtnCheck.length; i++){
                for(int j = 0; j<lengthListSounds; j++) {
                   viewR[j] = (View)findViewById(rowId[j]);
                     listBtnCheck[j] = (ToggleButton)viewR[j].findViewById(R.id.bt_check2);
                      listBtnPlyStop[j] = (ToggleButton)viewR[j].findViewById(R.id.bt_play);
                       radioGroup[j] =(RadioGroup)viewR[j].findViewById(R.id.segmentedGroup);
                        listBtnCheck[j].setTag(j);
                        listBtnPlyStop[j].setTag(j);
                         radioGroup[j].setTag(j);
                          soundsBoolean[j] = false;
                          loopSound[j] = 0;
                           // listBtnPlyStop[j].setText("i"+i);
                           textListTitle[j] = (TextView)viewR[j].findViewById(R.id._txt_kind_of_azkar);
                            textListTitle[j].setText(Azkar[j]);
                  }
            }

            for(RadioGroup rBtn:radioGroup)
                rBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup , int i) {
                    Log.i("radio_Tag0",""+rBtn.getTag());
                   // radio_Tag0 index in array
                    View radioButton = radioGroup.findViewById(i);
                    int index = radioGroup.indexOfChild(radioButton);
                     Log.v("radio_trace0",""+index);
                     int oppositeNum = 0;
                     if(index==0)oppositeNum= 2;
                     if(index==2)oppositeNum= 0;
                     if(index==1)oppositeNum= 1;
                     loopSound[(int)rBtn.getTag()]=oppositeNum;
                    //radio_trace0 index in level
                    //loopSound
                }
            });
           // Log.i("loopSound_00",""+  loopSound[3]);
          for(ToggleButton btn:listBtnCheck)
             //btn.setOnClickListener(btnListener);
            btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked){
                     // Change testLinearLayout direction based on toggle button checked status.
                    // Intent k = new Intent(ListAzcarActivity.this , DialogRememberInfoActivity.class);
                    // startActivity(k);
                    btn.setText(null);
                    if(checked){
                        int d = R.drawable.chk;
                        btn.setBackgroundDrawable(getDrawable(d));
                        soundsBoolean[(int)btn.getTag()] = true;
                    }else{
                        int f = R.drawable.unchk;
                        soundsBoolean[(int)btn.getTag()] = false;
                        btn.setBackgroundDrawable(getDrawable(f));
                    }
                   // Log.i("trace111_0",""+ soundsBoolean[0]);
                }
            });

            for(ToggleButton btn2:listBtnPlyStop)
                btn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        //Intent k = new Intent(ListAzcarActivity.this , DialogRememberInfoActivity.class);
                        // startActivity(k);
                        List<ToggleButton> btns = new ArrayList<>();
                        compoundButton.setText(null);
                        if (checked){
                            int a = R.drawable.btnpause;
                            if(btn2.getTag().equals(0)){
                                if(mp1.isPlaying()){
                                    mp1.stop();
                                    //int b = R.drawable.btnplay;
                                   // btns.get(btns.size()-1).setBackgroundDrawable(getDrawable(b));
                                }
                                closeBtns((Integer) btn2.getTag());
                                mp1 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a1);
                                mp1.start();
                             // Log.i("isPlay_2","No");
                            }if(btn2.getTag().equals(1)){
                              if(mp1.isPlaying()){
                                mp1.stop();
                                // int b = R.drawable.btnplay;
                                  //btns[0].setBackgroundDrawable(getDrawable(b));
                                }
                                closeBtns((Integer) btn2.getTag());
                                mp1 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a2);
                                //btns.add(btn2);
                                mp1.start();
                            }if(btn2.getTag().equals(2)){
                                if(mp1.isPlaying()){
                                    mp1.stop();
                                  //  int b = R.drawable.btnplay;
                                    //btns.get(btns.size()-1).setBackgroundDrawable(getDrawable(b));
                                }
                                closeBtns((Integer) btn2.getTag());
                                mp1 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a3);
                                //btns.add(btn2);
                                mp1.start();
                            }
                            if(btn2.getTag().equals(3)){
                                if(mp1.isPlaying()){
                                    mp1.stop();
                                }
                                mp1 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a4);
                                closeBtns((Integer) btn2.getTag());
                                mp1.start();
                            }
                            if(btn2.getTag().equals(4)){
                                if(mp1.isPlaying()){
                                    mp1.stop();
                                    //int b = R.drawable.btnplay;
                                   // btns.get(btns.size()-1).setBackgroundDrawable(getDrawable(b));
                                }
                                //Log.i("trace00+++ ","44444");
                                mp1 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a5);
                                //btns.add(btn2);
                                closeBtns((Integer)btn2.getTag());
                                mp1.start();
                            }
                            if(btn2.getTag().equals(5)){
                                if(mp1.isPlaying()){
                                    mp1.stop();
                                    //int b = R.drawable.btnplay;
                                    // btns.get(btns.size()-1).setBackgroundDrawable(getDrawable(b));
                                }
                                mp1 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a6);
                                //btns.add(btn2);
                                closeBtns((Integer)btn2.getTag());
                                mp1.start();
                            }
                        } else {
                            int b = R.drawable.btnplay;
                            btn2.setBackgroundDrawable(getDrawable(b));
                            Toast.makeText(ListAzcarActivity.this, "You have clicked Btn", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
    private void closeBtns(int i){
        for(int j = 0; j<listBtnPlyStop.length; j++){
            int b = R.drawable.btnplay;
            int a = R.drawable.btnpause;
            if(j !=i ){
                listBtnPlyStop[j].setBackgroundDrawable(getDrawable(b));
            }else {
                listBtnPlyStop[j].setBackgroundDrawable(getDrawable(a));
            }
        }
     }
        private View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 for(int i = 0 ; i < 4 ; i++)
                  Toast.makeText(ListAzcarActivity.this, "You have clicked Btn", Toast.LENGTH_SHORT).show();
            }
        };

        private void ActionBarSetting() {
         this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
          getSupportActionBar().setDisplayShowCustomEnabled(true);
           getSupportActionBar().setCustomView(R.layout.toolbar);
            getSupportActionBar().setElevation(1);
              View view = getSupportActionBar().getCustomView();
              // getSupportActionBar().setIcon(R.drawable.dot1);
              ImageView imageButton = (ImageView)findViewById(R.id.Icon);
               imageButton.setImageResource(R.drawable.arrow_left_black);
              //imageButton.setPaddingRelative(0,0,0,0);
              //imageButton.setForegroundGravity(Ri);
               TextView name = view.findViewById(R.id.text_title);
               view.findViewById(R.id.l_container).setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
               name.setText("أذكار مع التكرار");
                 imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Toast.makeText(ListAzcarActivity.this, "You have clicked Back", Toast.LENGTH_SHORT).show();
                 // onBackPressed();
                 //handleSetupWidget();
                }
            });
         }


    public void confirmConfiguration(View V){
      // Log.i("gotoActivity","Yes0");

        for(int i=0; i<soundsBoolean.length; i++){
            dataProccessor.setBool("soundsBoolean"+i,soundsBoolean[i]);
            dataProccessor.setInt("loopSound"+i,loopSound[i]);
            Log.i("trace0","Yes_0"+   dataProccessor.getBool("soundsBoolean"+i));
        }

        Intent intent = new Intent(ListAzcarActivity.this, SettingsActivity.class);
        startActivityForResult(intent , REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            this.startService(new Intent(this , WordWidget.UpdateService.class));
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                String requiredValue = data.getStringExtra("key");
                appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
                //Retrieve the App Widget ID from the Intent that launched the Activity//
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    // If the intent doesn’t have a widget ID, then call finish()
                    if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                        finish();
                    }
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                    setResult(RESULT_OK, resultValue);
                    finish();
                }
                Toast.makeText(ListAzcarActivity.this, "" + requiredValue.toString(), Toast.LENGTH_SHORT).show();
            }
        } catch(Exception ex){
           // Toast.makeText(ListAzcarActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
