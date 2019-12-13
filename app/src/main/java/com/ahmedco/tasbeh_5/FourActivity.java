package com.ahmedco.tasbeh_5;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
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

import java.util.ArrayList;
import java.util.List;


public class FourActivity extends AppCompatActivity{

        ToggleButton bt1,btnplay1,bt2,btnplay2,bt3,btnplay3,bt4,btnplay4;
        ToggleButton[] listBtnCheck;
        ToggleButton[] listBtnPlyStop;
        RadioGroup[] radioGroup;
        TextView[] textListTitle;
        int[] loopSound;
        String[] Azkar;
        boolean[] soundsBoolean;
        int currentBtn;
        MediaPlayer mp1;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_four);
            ActionBarSetting();

            mp1 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a1);
            final MediaPlayer mp2 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a2);
            final MediaPlayer mp3 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a3);
            final MediaPlayer mp4 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a4);
            final MediaPlayer mp5 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a5);
           // t1.setText(R.string.text_minute);
           // bt1 =(ToggleButton)viewR1.findViewById(R.id.bt_1);
           // btnplay1=(ToggleButton)viewR1.findViewById(R.id.bt_play);

            int[] rowId = new int[]{R.id.r1,R.id.r2,R.id.r3,R.id.r4,R.id.r5,R.id.r6};

            // View v=(View)findViewById(rowId[0]);
            /*
            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup , int i) {
                    View radioButton = radioGroup.findViewById(i);
                    int index = radioGroup.indexOfChild(radioButton);
                    Log.v("radio_trace0",""+index);
                }
            });
           */

            listBtnCheck = new ToggleButton[6];
            listBtnPlyStop = new ToggleButton[6];
            View [] viewR  = new View[6];
            textListTitle  = new TextView[6];
            soundsBoolean  = new boolean[6];
            radioGroup  = new RadioGroup[6];
            loopSound = new int[6];
            // RadioGroup group= (RadioGroup)v.findViewById(R.id.segmentedGroup);
            Resources res = getResources();
            Azkar = res.getStringArray(R.array.kind_of_azkar);
              for (int i = 0; i<listBtnCheck.length; i++){
                for(int j = 0; j<6; j++) {
                   viewR[j] = (View)findViewById(rowId[j]);
                     listBtnCheck[j] = (ToggleButton)viewR[j].findViewById(R.id.bt_check2);
                      listBtnPlyStop[j] = (ToggleButton)viewR[j].findViewById(R.id.bt_play);
                       radioGroup[j] =(RadioGroup)viewR[j].findViewById(R.id.segmentedGroup);
                        listBtnCheck[j].setTag(j);
                        listBtnPlyStop[j].setTag(j);
                         radioGroup[j].setTag(j);
                          soundsBoolean[j] = false;
                          loopSound[j] = 0;
                           //listBtnPlyStop[j].setText("i"+i);
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
                    // Intent k = new Intent(FourActivity.this , ThirdActivity.class);
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
             /*
              if(button clicked){
                list.add(editText.getText().toString());
              }
             */
             // for(ToggleButton btn2:listBtnPlyStop)
              //for(int j=0; j<listBtnPlyStop.length; j++) {
              // currentBtn = listBtnPlyStop[j] ;
               /*
                listBtnPlyStop[j].setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //rowId
                      //  Log.i("trace00",""+ v.getTag());
                        // int index = (Integer) v.getTag();
                    }
                });
              */
            for(ToggleButton btn2:listBtnPlyStop)
                btn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        //Intent k = new Intent(FourActivity.this , ThirdActivity.class);
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
                                mp1 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a1);
                                mp1.start();
                             // Log.i("isPlay_2","No");
                            }if(btn2.getTag().equals(1)){
                              if(mp1.isPlaying()){
                                mp1.stop();
                                // int b = R.drawable.btnplay;
                                  //btns[0].setBackgroundDrawable(getDrawable(b));
                                }
                                closeBtns((Integer) btn2.getTag());
                                mp1 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a2);
                                //btns.add(btn2);
                                mp1.start();
                            }if(btn2.getTag().equals(2)){
                                if(mp1.isPlaying()){
                                    mp1.stop();
                                  //  int b = R.drawable.btnplay;
                                    //btns.get(btns.size()-1).setBackgroundDrawable(getDrawable(b));
                                }
                                closeBtns((Integer) btn2.getTag());
                                mp1 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a3);
                                //btns.add(btn2);
                                mp1.start();
                            }
                            if(btn2.getTag().equals(3)){
                                if(mp1.isPlaying()){
                                    mp1.stop();
                                }
                                mp1 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a4);
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
                                mp1 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a5);
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
                                mp1 = (MediaPlayer) MediaPlayer.create(FourActivity.this,R.raw.a6);
                                //btns.add(btn2);
                                closeBtns((Integer)btn2.getTag());
                                mp1.start();
                            }
                        } else {
                            int b = R.drawable.btnplay;
                            btn2.setBackgroundDrawable(getDrawable(b));
                            Toast.makeText(FourActivity.this, "You have clicked Btn", Toast.LENGTH_LONG).show();
                        }
                    }
                });






             /*
            bt1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked){
                    // Change testLinearLayout direction based on toggle button checked status.
                    bt1.setText(null);
                    if(checked){
                        int d = R.drawable.chk;
                        bt1.setBackgroundDrawable(getDrawable(d));
                    }else{
                        int f = R.drawable.unchk;
                        bt1.setBackgroundDrawable(getDrawable(f));
                    }
                }
            });
            btnplay1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked){
                    // Change testLinearLayout direction based on toggle button checked status.
                    btnplay1.setText(null);
                    if(checked){
                        int a = R.drawable.btnpause;
                        btnplay1.setBackgroundDrawable(getDrawable(a));
                    }else{
                        int b = R.drawable.btnplay;
                        btnplay1.setBackgroundDrawable(getDrawable(b));
                        Toast.makeText(FourActivity.this, "You have clicked Btn", Toast.LENGTH_LONG).show();
                    }
                }
          });
          */
     }



    private void closeBtns(int i){
        for(int j = 0 ; j<listBtnPlyStop.length; j++){
            int b = R.drawable.btnplay;
            int a = R.drawable.btnpause;
            // i = 4
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
                Toast.makeText(FourActivity.this, "You have clicked Btn", Toast.LENGTH_SHORT).show();
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
                 // Toast.makeText(FourActivity.this, "You have clicked Back", Toast.LENGTH_SHORT).show();
                 // onBackPressed();
                 handleSetupWidget();
                }
            });
        }


        private void handleSetupWidget(){
         Intent intent = new Intent(WidgetAzcar_.ACTION_TEXT_CHANGED);
         intent.putExtra("soundsBoolean", soundsBoolean);
         intent.putExtra("loopSound", loopSound);
         getApplicationContext().sendBroadcast(intent);
    }
}



// http://thetechnocafe.com/how-to-create-widget-for-your-android-app/
            /*
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetAzcar_.class));
            for (int appWidgetId : widgetIds) {
                WidgetAzcar_.updateAppWidget(getApplicationContext() , appWidgetManager, appWidgetId);
            }
           */


   /*
         Intent intn = new Intent (this, WidgetAzcar.class);
         intn.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
         this.startActivity (intn);
         */