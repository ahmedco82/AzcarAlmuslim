package com.ahmedco.tasbeh_5.activities;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedco.tasbeh_5.R;
import com.ahmedco.tasbeh_5.utils.DataSharedPreferences;
import com.gc.materialdesign.views.ButtonRectangle;

import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/5914234/how-to-share-data-between-activity-and-widget

public class ListAzcarActivity extends AppCompatActivity  {

    public static final int REQUEST_CODE = 1;
    private ToggleButton[] listBtnCheck;
    private ToggleButton[] listBtnPlyStop;
    private RadioGroup[] radioGroup;
    private TextView[] listOfTextAzcar;
    private int[] repeatEachSound;
    private String[] textListAzcar;
    private boolean[] selectedSound;
    private int lengthListSounds;
    private MediaPlayer currentPlyStopBtn;
    private DataSharedPreferences soundsSharedPref;
    private int oneTime;
    private boolean noSelectedSound;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    //button_row_listazcar
    private ButtonRectangle btnRowListazcar;

    // whatever code is needed for initialization goes here
    ButtonRectangle  buttonSelectColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_azcar);
        soundsSharedPref = new DataSharedPreferences(this);
        new ActionBarView(this, "أذكار مع التكرار");
        setResult(RESULT_CANCELED);
        initiViewItems();
        putCountRepeatSound();
        selectNewSound();
        setPlayStopBtns();
    }


    private void initiViewItems(){
        lengthListSounds = 6;
        oneTime = 0;
        noSelectedSound = false;
        //button_row_listazcar
        currentPlyStopBtn = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this, R.raw.a1);
        int[] rowView = new int[]{R.id.r1, R.id.r2, R.id.r3, R.id.r4, R.id.r5, R.id.r6};
        listBtnCheck = new ToggleButton[lengthListSounds];
        listBtnPlyStop = new ToggleButton[lengthListSounds];
        View[] viewContainer = new View[lengthListSounds];
        listOfTextAzcar = new TextView[lengthListSounds];
        selectedSound = new boolean[lengthListSounds];
        radioGroup = new RadioGroup[lengthListSounds];
        repeatEachSound = new int[lengthListSounds];
        Resources resArrayString = getResources();
        textListAzcar = resArrayString.getStringArray(R.array.kind_of_azkar);
        for (int i = 0; i < listBtnCheck.length; i++) {
            for (int j = 0; j < lengthListSounds; j++) {
                viewContainer[j] = (View) findViewById(rowView[j]);
                listBtnCheck[j] = (ToggleButton) viewContainer[j].findViewById(R.id.bt_check2);
                listBtnPlyStop[j] = (ToggleButton) viewContainer[j].findViewById(R.id.bt_play);
                radioGroup[j] = (RadioGroup) viewContainer[j].findViewById(R.id.segmentedGroup);
                listBtnCheck[j].setTag(j);
                listBtnPlyStop[j].setTag(j);
                radioGroup[j].setTag(j);
                selectedSound[j] = noSelectedSound;
                repeatEachSound[j] = oneTime;
                listOfTextAzcar[j] = (TextView) viewContainer[j].findViewById(R.id._txt_kind_of_azkar);
                listOfTextAzcar[j].setText(textListAzcar[j]);
            }
        }
    }

    private void selectNewSound(){
        for (ToggleButton selectedBtn : listBtnCheck)
            selectedBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    selectedBtn.setText(null);
                    if (checked) {
                        setBground(selectedBtn,true);
                    } else {
                        setBground(selectedBtn,false);
                    }
                }
            });
         }
    private void setBground(ToggleButton selectedBtn_,boolean indexStatusBtn){
        int bG = 0;
        selectedSound[(int)selectedBtn_.getTag()] = indexStatusBtn;
        if(indexStatusBtn){
            bG = R.drawable.chk;
        }else{
            bG = R.drawable.unchk;
        }
        selectedBtn_.setBackgroundDrawable(getDrawable(bG));
    }

    private void putCountRepeatSound() {
        for (RadioGroup rowOneTowThree:radioGroup)
            rowOneTowThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    //Log.i("radio_Tag0",""+rBtn.getTag());
                    View radioButtonGetName = radioGroup.findViewById(i);
                    int indexRadioButn = radioGroup.indexOfChild(radioButtonGetName);
                    int oppositeNum = 0;
                    if (indexRadioButn == 0)oppositeNum = 2;
                    if (indexRadioButn == 2)oppositeNum = 0;
                    if (indexRadioButn == 1)oppositeNum = 1;
                    repeatEachSound[(int)rowOneTowThree.getTag()] = oppositeNum;
                }
       });
    }

    private void setPlayStopBtns(){
        for (ToggleButton playStopBtns : listBtnPlyStop)
            playStopBtns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    List<ToggleButton> btns = new ArrayList<>();
                    compoundButton.setText(null);
                    int[] soundsRowsId = new int[]{R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5, R.raw.a6};
                    if (checked) {
                        for (int i = 0; i <soundsRowsId.length; i++) {
                            if (playStopBtns.getTag().equals(i)) {
                                if (currentPlyStopBtn.isPlaying()) {
                                    currentPlyStopBtn.stop();
                                }
                                closeBtns((Integer) playStopBtns.getTag());
                                currentPlyStopBtn = (MediaPlayer) MediaPlayer.create(ListAzcarActivity.this, soundsRowsId[i]);
                                currentPlyStopBtn.start();
                            }
                        }
                    } else {
                        int btnplay = R.drawable.btnplay;
                        playStopBtns.setBackgroundDrawable(getDrawable(btnplay));
                    }
                }
            });
       }

    private void closeBtns(int i){
        for (int j = 0; j < listBtnPlyStop.length; j++){
            int btnplay = R.drawable.btnplay;
            int btnpause = R.drawable.btnpause;
            if (j != i) {
                listBtnPlyStop[j].setBackgroundDrawable(getDrawable(btnplay));
            } else {
                listBtnPlyStop[j].setBackgroundDrawable(getDrawable(btnpause));
            }
        }
    }

    public void confirmConfiguration(View view) {
        savDatainSharedPref();
        boolean LengthOfListCheckedSound = lengthSelectedSound();
        if (ensureTransportData() && LengthOfListCheckedSound){
            Intent intent = new Intent(ListAzcarActivity.this, TimeSettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        if (!LengthOfListCheckedSound) {
            Toast.makeText(ListAzcarActivity.this, " أختر عنصر واحد أو أثنين فقط", Toast.LENGTH_SHORT).show();
        }
        if (!ensureTransportData()) {
            Toast.makeText(ListAzcarActivity.this, "لا تترك الاختيارات فارغه ", Toast.LENGTH_SHORT).show();
        }
    }
    private void savDatainSharedPref() {
        for (int i = 0; i < selectedSound.length; i++) {
            soundsSharedPref.setBool("btnsSoundChecked"+i, selectedSound[i]);
            soundsSharedPref.setInt("loopSound" + i, repeatEachSound[i]);
        }
    }

    private boolean ensureTransportData() {
        boolean checkArray = false;
        for (int i = 0; i < selectedSound.length; i++) {
            boolean getboolArrey = soundsSharedPref.getBool("btnsSoundChecked" + i);
            if (getboolArrey == true) {
                checkArray = true;
            }
        }
        return checkArray;
    }

    private boolean lengthSelectedSound() {
        int count = 0;
        for(int i = 0; i<selectedSound.length; i++) {
           if(selectedSound[i]) {
               count = count + 1;
            }
        }
        if (count > 2) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.startService(new Intent(this, UpdateService.class));
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // String requiredValue = data.getStringExtra("key");
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
        }
    }
}