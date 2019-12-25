package com.ahmedco.tasbeh_5.activities;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedco.tasbeh_5.R;
import com.ahmedco.tasbeh_5.utils.DataSharedPreferences;
import java.util.ArrayList;
import java.util.List;


//https://stackoverflow.com/questions/5914234/how-to-share-data-between-activity-and-widget
public class ListAzcarActivity extends AppCompatActivity {

        public static final int REQUEST_CODE = 1;
        private ToggleButton[] listBtnCheck;
        private ToggleButton[] listBtnPlyStop;
        private RadioGroup[] radioGroup;
        private TextView[] listOfTextAzcar;
        private int[] repeatEachSound;
        private String[] textListAzcar;
        private boolean[] selectedSound;
        private int lengthListSounds;
        private MediaPlayer soundAzcar0 ,soundAzcar1,soundAzcar2, soundAzcar3,soundAzcar4,soundAzcar5;
        public static DataSharedPreferences soundsSharedPref;
        private int  oneTime;
        private boolean noSelectedSound;
        private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        // whatever code is needed for initialization goes here

        @Override
        protected void onCreate(Bundle savedInstanceState){
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_list_azcar);
          soundsSharedPref = new DataSharedPreferences(this);
          actionBarSetting();
          setResult(RESULT_CANCELED);
          initiItems();
          putCountRepeatSound();
          selectNewSound();
          setPlayStopBtns();
       }

    private void initiItems(){
        lengthListSounds = 6;
        oneTime =0;
        noSelectedSound =false;
        int[] soundsAzcarId = new int[]{ R.id.r1,R.id.r2,R.id.r3,R.id.r4,R.id.r5,R.id.r6 };
        listBtnCheck   = new ToggleButton[lengthListSounds];
        listBtnPlyStop = new ToggleButton[lengthListSounds];
        View [] viewContainer  = new View[lengthListSounds];
        listOfTextAzcar  = new TextView[lengthListSounds];
        selectedSound  = new boolean[lengthListSounds];
        radioGroup  = new RadioGroup[lengthListSounds];
        repeatEachSound = new int[lengthListSounds];
        Resources resArrayString = getResources();
        textListAzcar = resArrayString.getStringArray(R.array.kind_of_azkar);
        for (int i = 0; i<listBtnCheck.length; i++){
            for(int j = 0; j<lengthListSounds; j++) {
                viewContainer[j] = (View)findViewById(soundsAzcarId[j]);
                listBtnCheck[j] = (ToggleButton)viewContainer[j].findViewById(R.id.bt_check2);
                listBtnPlyStop[j] = (ToggleButton)viewContainer[j].findViewById(R.id.bt_play);
                radioGroup[j] =(RadioGroup)viewContainer[j].findViewById(R.id.segmentedGroup);
                listBtnCheck[j].setTag(j);
                listBtnPlyStop[j].setTag(j);
                radioGroup[j].setTag(j);
                selectedSound[j] = noSelectedSound;
                repeatEachSound[j] = oneTime;
                listOfTextAzcar[j] = (TextView)viewContainer[j].findViewById(R.id._txt_kind_of_azkar);
                listOfTextAzcar[j].setText(textListAzcar[j]);
            }
        }
    }


    private void selectNewSound() {
        for(ToggleButton selectedBtn:listBtnCheck)
            selectedBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked){
                    // Change testLinearLayout direction based on toggle button checked status.
                    selectedBtn.setText(null);
                    if(checked){
                        int d = R.drawable.chk;
                        selectedBtn.setBackgroundDrawable(getDrawable(d));
                        selectedSound[(int)selectedBtn.getTag()] = true;
                    }else{
                        int f = R.drawable.unchk;
                        selectedSound[(int)selectedBtn.getTag()] = false;
                        selectedBtn.setBackgroundDrawable(getDrawable(f));
                    }
                }
            });
        }
    private void putCountRepeatSound(){
        for(RadioGroup rowOneTowThree:radioGroup)
            rowOneTowThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup , int i) {
                    //Log.i("radio_Tag0",""+rBtn.getTag());
                    View radioButtonGetName = radioGroup.findViewById(i);
                    int indexRadioButn = radioGroup.indexOfChild(radioButtonGetName);
                    int oppositeNum = 0;
                    if(indexRadioButn==0)oppositeNum=2;
                    if(indexRadioButn==2)oppositeNum= 0;
                    if(indexRadioButn==1)oppositeNum= 1;
                    repeatEachSound[(int)rowOneTowThree.getTag()]=oppositeNum;
                }
            });
         }
    private void setPlayStopBtns() {
        for(ToggleButton playStopBtns:listBtnPlyStop)
            playStopBtns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    List<ToggleButton> btns = new ArrayList<>();
                    compoundButton.setText(null);
                    if (checked){
                        int a = R.drawable.btnpause;
                        if(playStopBtns.getTag().equals(0)){
                            if(soundAzcar0.isPlaying()){
                                soundAzcar0.stop();
                            }
                            closeBtns((Integer) playStopBtns.getTag());
                            soundAzcar0 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a1);
                            soundAzcar0.start();
                            // Log.i("isPlay_2","No");
                        }if(playStopBtns.getTag().equals(1)){
                            if(soundAzcar0.isPlaying()){
                                soundAzcar0.stop();
                            }
                            closeBtns((Integer)playStopBtns.getTag());
                            soundAzcar0 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a2);
                            soundAzcar0.start();
                        }if(playStopBtns.getTag().equals(2)){
                            if(soundAzcar0.isPlaying()){
                                soundAzcar0.stop();
                            }
                            closeBtns((Integer) playStopBtns.getTag());
                            soundAzcar0 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a3);
                            //btns.add(btn2);
                            soundAzcar0.start();
                        }
                        if(playStopBtns.getTag().equals(3)){
                            if(soundAzcar0.isPlaying()){
                                soundAzcar0.stop();
                            }
                            soundAzcar0 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a4);
                            closeBtns((Integer) playStopBtns.getTag());
                            soundAzcar0.start();
                        }
                        if(playStopBtns.getTag().equals(4)){
                            if(soundAzcar0.isPlaying()){
                                soundAzcar0.stop();
                            }
                            soundAzcar0 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a5);
                            closeBtns((Integer)playStopBtns.getTag());
                            soundAzcar0.start();
                        }
                        if(playStopBtns.getTag().equals(5)){
                            if(soundAzcar0.isPlaying()){
                                soundAzcar0.stop();
                            }
                            soundAzcar0 = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this,R.raw.a6);
                            closeBtns((Integer)playStopBtns.getTag());
                            soundAzcar0.start();
                        }
                    } else {
                        int b = R.drawable.btnplay;
                        playStopBtns.setBackgroundDrawable(getDrawable(b));
                        //Toast.makeText(ListAzcarActivity.this, "You have clicked Btn", Toast.LENGTH_LONG).show();
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
        private void actionBarSetting() {
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
                    //  handleSetupWidget();
                }
            });
         }
         
    public void confirmConfiguration(View V){
       for(int i=0; i<selectedSound.length; i++){
         soundsSharedPref.setBool("soundsBoolean"+i,selectedSound[i]);
           soundsSharedPref.setInt("loopSound"+i,repeatEachSound[i]);
             Log.i("trace0","Yes_0"+   soundsSharedPref.getBool("soundsBoolean"+i));
           }
         Intent intent = new Intent(ListAzcarActivity.this, TimeSettingsActivity.class);
        startActivityForResult(intent , REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            this.startService(new Intent(this , AzcarWidget.UpdateService.class));
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
                //Toast.makeText(ListAzcarActivity.this, "" + requiredValue.toString(), Toast.LENGTH_SHORT).show();
            }
        } catch(Exception ex){
           // Toast.makeText(ListAzcarActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}











     /*
        private View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 for(int i = 0 ; i < 4 ; i++){

                 }
                 // Toast.makeText(ListAzcarActivity.this, "You have clicked Btn", Toast.LENGTH_SHORT).show();
            }
        };
*/