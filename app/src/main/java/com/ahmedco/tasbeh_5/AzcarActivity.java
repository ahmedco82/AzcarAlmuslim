package com.ahmedco.tasbeh_5;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AzcarActivity extends AppCompatActivity {

    static long equ = 60;
    static long i1 = 5000;
    static long i2 = 15000;
    static long i3 = 20000;
    static long i4 = 30000;
    static long i5 = 45000;
    static long i6 = 60000;
    static long i7 = 60000*2;
    static long i8 = 30000*3;
    static long i9 = 30000*4;
    static long i10 = 30000*5;
    static long i11 = 30000*10;

    private static  long START_TIME_IN_MILLIS =0;

            //= i1  * equ;
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    List<MediaPlayer>arrSelectSound2;
    List<Integer>arrSelectLoopSounds;
    public int j = 0;
    public int loopSoundCounter = 0;
    public int quiteLoop = 1;
    public int count = 0;
    public int [] loops;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azcar);
        final MediaPlayer mp1 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this,R.raw.a1);
        final MediaPlayer mp2 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this,R.raw.a2);
        final MediaPlayer mp3 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this,R.raw.a3);
        final MediaPlayer mp4 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this,R.raw.a4);
        final MediaPlayer mp5 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this,R.raw.a5);
        final MediaPlayer mp6 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this,R.raw.a6);
        final MediaPlayer mp7 = (MediaPlayer) MediaPlayer.create(AzcarActivity.this,R.raw.a7);
        MediaPlayer []arrSelectSound= {mp1,mp2,mp3,mp4,mp5,mp6,mp7};
        arrSelectSound2 = new ArrayList<MediaPlayer>();
        arrSelectLoopSounds = new ArrayList<Integer>();
        long []itemTimers = {i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11};
        String currentEvertTime = String.valueOf(FiveActivity.ksp1.GetAll().get(0));
       // Log.i("fiveActivity000", "" + FiveActivity.ksp1.GetAll().get(0));
        switch(currentEvertTime) {
            case "0":
             START_TIME_IN_MILLIS = i1  * equ;;
                break;
            case  "1":
               START_TIME_IN_MILLIS= i2  * equ;;
                break;
            case  "2":
                START_TIME_IN_MILLIS= i3  * equ;;
                break;
            case  "3":
                START_TIME_IN_MILLIS= i4  * equ;;
                break;
            case "4":
                START_TIME_IN_MILLIS= i5  * equ;;
                break;
            case "5":
                START_TIME_IN_MILLIS= i6  * equ;;
                break;
            case "6":
                START_TIME_IN_MILLIS= i7  * equ;;
                break;
            case "7":
                START_TIME_IN_MILLIS= i8  * equ;;
                break;
            case "8":
                START_TIME_IN_MILLIS= i9  * equ;;
                break;
            case "9":
                START_TIME_IN_MILLIS= i10  * equ;;
                break;
            case "10":
                START_TIME_IN_MILLIS= i11 * equ;;
                break;
            default:
                START_TIME_IN_MILLIS = i1  * equ;;
                break;
        }

        Bundle extras = getIntent().getExtras();
        boolean[] arrayB = extras.getBooleanArray("soundsBoolean");
        int [] arrayLoopSound = extras.getIntArray("loopSound");

        // loopSoundCounter
        //  Boolean [] arrayB = extras.getBooleanArray("soundsBoolean");
        if(arrayB != null) {
         // do something with the data
          for (int i = 0; i<arrayB.length; i++) {
            //Log.i("SounBtn", "" + arrayB[i]);
            // Log.i("arrayLoopSound00", "" + arrayLoopSound[i]);
              // arrayLoopSound
              if(arrayB[i]==true){
                arrSelectSound2.add(arrSelectSound[i]);
                arrSelectLoopSounds.add(arrayLoopSound[i]);
              }
           }
        }
        // Log.i("SounBtn22_0", "" + arrSelectLoopSounds.get(0).toString());
        // Log.i("SounBtn22_1", "" + arrSelectLoopSounds.get(1).toString());
        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        setTime(START_TIME_IN_MILLIS);
        mEditTextInput.setText("");
        startTimer();
        mButtonSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              String input = mEditTextInput.getText().toString();
               // String input ="1";
                if(input.length() == 0){
                  Toast.makeText(AzcarActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                   return;
                }
               long millisInput = Long.parseLong(input) * 60000;
                //  long millisInput = 1 * 60000;
                // long millisInput =START_TIME_IN_MILLIS;
                if (millisInput == 0) {
                    Toast.makeText(AzcarActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                // setTime(millisInput);
                setTime(START_TIME_IN_MILLIS);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning) {
                  pauseTimer();
                } else {
                  startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetTimer();
            }
        });
    }

    private void setTime(long milliseconds){
     mStartTimeInMillis = milliseconds;
      resetTimer();
      //closeKeyboard();
    }
    private void startTimer(){
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                //checkCurrentTimer();
                updateCountDownText();
            }
            @Override
            public void onFinish(){
                mTimerRunning = false;
                Toast.makeText(AzcarActivity.this, "انتهى", Toast.LENGTH_SHORT).show();
                setTime(START_TIME_IN_MILLIS);
                mEditTextInput.setText("");
                setsound(arrSelectSound2.get(j));
                startTimer();
               // updateWatchInterface();
              }
          }.start();
        mTimerRunning = true;
       // updateWatchInterface();
    }

    private void setsound(MediaPlayer key){
        arrSelectSound2.get(j).start();
        arrSelectSound2.get(j).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // arrSelectLoopSounds 0 = 2
                if (arrSelectLoopSounds.get(j) != 0){
                    if (quiteLoop != 0){
                        count += 1;
                        if (count == arrSelectLoopSounds.get(j)){
                            count = 0;
                            quiteLoop = 0;
                        }
                        setsound(arrSelectSound2.get(j));
                    } else {
                        j += 1;
                        if (j<arrSelectSound2.size()) setsound(arrSelectSound2.get(j));
                        else
                        j = 0;
                        quiteLoop = 1;
                    }
                } else {
                    j += 1;
                    if (j<arrSelectSound2.size())setsound(arrSelectSound2.get(j));
                    else
                      j = 0;
                    quiteLoop = 1;
                }
            }
        });
    }










/*
    private void setsound(MediaPlayer key){
     arrSelectSound2.get(indxSound).start();
      arrSelectSound2.get(indxSound).setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
      int currentLoop=0;
       @Override
         public void onCompletion(MediaPlayer mp) {
           //arrSelectSound2.add(arrSelectSound[i]);
           //arrSelectLoopSounds.add(arrayLoopSound[i]);
           ///-----------------------------------------------------
            if(arrSelectLoopSounds.get(indxSound)==0)currentLoop= 2;
            if(arrSelectLoopSounds.get(indxSound)==2)currentLoop= 0;
            if(arrSelectLoopSounds.get(indxSound)==1)currentLoop= 1;
            //arrSelectLoopSounds.get(indxSound)
            if(currentLoop==0){
            //chang audio;
             indxSound+=1;
            setsound(arrSelectSound2.get(indxSound));
           }else {
          if(quiteLoop>0){
              setsound(arrSelectSound2.get(indxSound));
          }
          else{
              indxSound+=1;
              setsound(arrSelectSound2.get(indxSound));
           }
            quiteLoop = currentLoop-1;
          }
       }
       });
    }

*/
/*
    private void setsound(MediaPlayer key){
      arrSelectSound2.get(indxSound).start();
        arrSelectSound2.get(indxSound).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
          @Override
            public void onCompletion(MediaPlayer mp){
              indxSound  +=1;
               if(indxSound<arrSelectSound2.size()){
                setsound(arrSelectSound2.get(indxSound));
               }else{
                indxSound = 0;
             }
          }
       });

     }

     */

   // Toast.makeText(MainActivity.this, "onCompletion"+indxSound, Toast.LENGTH_SHORT).show();
    /*
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }
    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }
  */


    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
       // updateWatchInterface();
    }


    private void checkCurrentTimer(){
        Calendar cal = Calendar.getInstance();
        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR);
        // 24 hour format ...........................
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        Log.v("tr00","" + minute);
        if(minute <= 22){
            if (mTimerRunning) {
                pauseTimer();
            }
        }else {
          if (!mTimerRunning)startTimer();
        }
    }

    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
    }
    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours>0) {
            timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }


     /*
    private void updateWatchInterface(){
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");
            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateWatchInterface();
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }
    */
}


    /*
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        c = Calendar.getInstance();
        dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        datetime = dateformat.format(c.getTime().getHours());
        //////////
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        calendar.get(Calendar.HOUR);        // gets hour in 12h format
        calendar.get(Calendar.MONTH);       // gets month number, NOTE this is zero based!
        Log.i("trac_timer",""+  calendar.get(Calendar.MINUTE));
        mTextViewCountDown.setText(timeLeftFormatted);
    }
*/

/*
  //  Log.v("fiveActivity.ksp1", "" + FiveActivity.ksp1.GetAll().get(0));
        Bundle extras = getIntent().getExtras();
        boolean[] arrayB = extras.getBooleanArray("soundsBoolean");
        //  Boolean [] arrayB = extras.getBooleanArray("soundsBoolean");
        if (arrayB != null) {
            // do something with the data
            for (int i = 0; i < arrayB.length; i++) {
                Log.i("SounBtn", "" + arrayB[i]);
            }
        }

 */