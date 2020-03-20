package com.ahmedcom.tasbeh55.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedcom.BackPressedCallingBack;
import com.ahmedcom.tasbeh55.utils.DataSharedPreferences;
import com.ahmedcom.tasbeh55.R;

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
    private MediaPlayer currentPlyStopBtn;
    private int oneTime;
    private boolean noSelectedSound;
    private DataSharedPreferences globalSharedPreferences = DataSharedPreferences.getInstance();
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_azcar);
        //globalSharedPreferences = new DataSharedPreferences(this);
        //new ActionBarView(this,"أذكار مع التكرار" ,R.drawable.left_arrow);
        setResult(RESULT_CANCELED);
        initiViewItems();
        putCountRepeatSound();
        selectNewSound();
        setPlayStopBtns();
    }

    private void initiViewItems() {
        // Put title and icon inside the action bar
        bitmap = BitmapFactory.decodeResource(ListAzcarActivity.this.getResources(), R.drawable.left_arrow);
        // new ActionBarView(this, "أذكار مع التكرار" , bitmap  , this);
        new ActionBarView(this, "أذكار مع التكرار", bitmap, new BackPressedCallingBack(this));
        lengthListSounds = 6;
        oneTime = 0;
        noSelectedSound = false;
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
                        setBground(selectedBtn, true);
                    } else {
                        setBground(selectedBtn, false);
                    }
                }
         });
     }

    private void setBground(ToggleButton selectedBtn_, boolean indexStatusBtn) {
        int bG = 0;
        selectedSound[(int) selectedBtn_.getTag()] = indexStatusBtn;
        if (indexStatusBtn) {
            bG = R.drawable.chk;
        } else {
            bG = R.drawable.unchk;
        }
        selectedBtn_.setBackgroundDrawable(getDrawable(bG));
    }

    private void putCountRepeatSound() {
        for (RadioGroup rowOneTowThree : radioGroup)
            rowOneTowThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    // Log.i("radio_Tag0",""+rBtn.getTag());
                    View radioButtonGetName = radioGroup.findViewById(i);
                    int indexRadioButn = radioGroup.indexOfChild(radioButtonGetName);
                    int oppositeNum = 0;
                    if (indexRadioButn == 0) oppositeNum = 2;
                    if (indexRadioButn == 2) oppositeNum = 0;
                    if (indexRadioButn == 1) oppositeNum = 1;
                    repeatEachSound[(int) rowOneTowThree.getTag()] = oppositeNum;
                }
            });
    }

    private void setPlayStopBtns() {
        for (ToggleButton playStopBtns : listBtnPlyStop)
            playStopBtns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    List<ToggleButton> btns = new ArrayList<>();
                    compoundButton.setText(null);
                    int[] soundsRowsId = new int[]{R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5, R.raw.a6};
                    if (checked) {
                        for (int i = 0; i < soundsRowsId.length; i++) {
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
    private void closeBtns(int i) {
        for (int j = 0; j < listBtnPlyStop.length; j++) {
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
        if(ensureTransportData() && LengthOfListCheckedSound) {
           Intent intent = new Intent(ListAzcarActivity.this, TimeSettingsActivity.class);
          startActivity(intent);
        }
        if (!LengthOfListCheckedSound) {
            Toast.makeText(ListAzcarActivity.this, " أختر عنصر واحد أو أثنين فقط", Toast.LENGTH_SHORT).show();
        }
        if (!ensureTransportData()) {
            Toast.makeText(ListAzcarActivity.this, "لا تترك الاختيارات فارغه ", Toast.LENGTH_SHORT).show();
        }
      }

    private void savDatainSharedPref(){
        for (int i = 0; i<selectedSound.length; i++) {
            globalSharedPreferences.setBool("btnsSoundChecked"+i, selectedSound[i]);
            globalSharedPreferences.setInt("loopSound" + i, repeatEachSound[i]);
        }
    }
    private boolean ensureTransportData() {
        boolean checkArray = false;
        for (int i = 0; i < selectedSound.length; i++) {
            boolean getboolArrey = globalSharedPreferences.getBool("btnsSoundChecked" + i);
            if (getboolArrey == true) {
                checkArray = true;
            }
        }
        return checkArray;
    }
    private boolean lengthSelectedSound(){
        int count = 0;
        for (int i = 0; i < selectedSound.length; i++) {
            if (selectedSound[i]) {
                count = count + 1;
            }
        }
        if (count > 2) {
            return false;
        } else {
            return true;
        }

 }




/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
      this.startService(new Intent(this, UpdateService.class));
       if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
         // String requiredValue = data.getStringExtra("key");
          appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
           //Retrieve the App Widget ID from the Intent that launched the Activity//
            Intent intent = getIntent();
             Bundle extras = intent.getExtras();
              if(extras != null) {
               appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                // If the intent doesn’t have a widget ID, then call finish()
                 if(appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                  finish();
                }
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
          }
     }

 */







/*
    @Override
    public void onClick(View v) {
        //We using switch by ID
        switch (v.getId()){
            case R.id.Icon:
                //Your Code
                Log.i("PRINT0","Gon");
                break;
            default:
                break;
        }
    }

 */
}