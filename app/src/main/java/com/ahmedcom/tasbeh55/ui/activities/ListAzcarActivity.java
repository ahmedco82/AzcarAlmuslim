package com.ahmedcom.tasbeh55.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.ahmedcom.BackPressedCallingBack;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.adapters.AzcarListAdapter;
import com.ahmedcom.tasbeh55.interfaces.HasBack;
import com.ahmedcom.tasbeh55.interfaces.RecyclerViewClickListener;
import com.ahmedcom.tasbeh55.models.AudioItem;
import com.ahmedcom.tasbeh55.services.Alarm;
import com.ahmedcom.tasbeh55.services.Alarm2;
import com.ahmedcom.tasbeh55.ui.others.ActionBarView;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import java.util.ArrayList;

public class ListAzcarActivity extends AppCompatActivity implements HasBack , RecyclerViewClickListener {

    AzcarListAdapter adapter;
    MediaPlayer currentSound = null;
    RecyclerView recyclerView;
    Intent serviceIntent2 = null;
    ArrayList<AudioItem> audioItems = new ArrayList<>();
    public ArrayList<Boolean> selectedSound = new ArrayList<Boolean>();
    private ArrayList<Integer> repeatEachSound = new ArrayList<Integer>();
    private ArrayList<String> listText = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // repeatEachSound =  new int[lengthListSounds];
        Bitmap bitmap = BitmapFactory.decodeResource(ListAzcarActivity.this.getResources(), R.drawable.left_arrow);
        new ActionBarView(this, "أذكار مع التكرار", bitmap, new BackPressedCallingBack(this));
        currentSound = (MediaPlayer) MediaPlayer.create(this, R.raw.a1);
        int[] soundsRowsId = new int[]{R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5, R.raw.a6};
        String[] soundsRowsText = new String[]{"الله اكبر", "الحمد لله", "سبحان الله وبحمده", "لا اله الا الله", "استغفر الله", "الحمد لله الذي احيانا بعد مماتنا"};
        initiArraies(soundsRowsId , soundsRowsText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new AzcarListAdapter(ListAzcarActivity.this, listText, audioItems);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        saveDataInSharedPref();
    }

    private void initiArraies(int[] soundsRowsId, String[] soundsRowsText) {
        for (int i = 0; i < 6; i++) {
            listText.add(soundsRowsText[i]);
            audioItems.add(new AudioItem(soundsRowsId[i]));
            repeatEachSound.add(0);
            selectedSound.add(false);
        }
     }

    private void saveDataInSharedPref(){
        SharedPreferencesUtils.setArrayBooleanPrefs(selectedSound, ListAzcarActivity.this);
        SharedPreferencesUtils.setArrayIntPrefs(repeatEachSound, ListAzcarActivity.this);
    }

    public boolean ensureOneORMoreSelected(){
     boolean checkArray = false;
       for(Boolean object:SharedPreferencesUtils.getArrayBooleanPrefs(ListAzcarActivity.this)) {
            if (object) {
                checkArray = true;
                break;
            } else {
                checkArray = false;
            }
        }
        return checkArray;
    }

    public boolean lengthSelectedSound() {
       int count = 0;
        for (int i = 0; i<selectedSound.size(); i++) {
            if (selectedSound.get(i)) {
                count = count + 1;
            }
        }
        return (count >= 2) ? false : true;
    }

    @Override
    public void onClick(View view , int position) {
       // endOfSelection();
        if (view.getTag().equals("toggleButton")) {
         ToggleButton selectedBtn = (ToggleButton) view;
         selectedSound.set(position, selectedBtn.isChecked());
           //Log.i("getisChecked0 ",""+selectedSound.get(position));
        } else if (view.getTag().equals("playBtn")) {
        } else if (view.getTag().equals("radio_b")) {
            repeatEachSound.set(position, 1);
        } else if (view.getTag().equals("radio_c")) {
            repeatEachSound.set(position, 0);
        } else if (view.getTag().equals("radio_a")) {
            repeatEachSound.set(position, 2);
        }
       saveDataInSharedPref();
    }
}













/*
   private void chooseDevice() {
        if (Build.VERSION.SDK_INT <= 22) {
            OreoDeviceOrBigger();
        } else {
            smallerThanOreoDevice();
        }
        closeApp();
    }

    private void closeApp(){
        this.finishAffinity();
    }

    private void smallerThanOreoDevice(){
      SharedPreferencesUtils.setServiceOnOff(this, true);
        serviceIntent2 = new Intent(getBaseContext(), Alarm.class);
       Alarm.enqueueWork(this, serviceIntent2);
    }

    private void OreoDeviceOrBigger() {
        Intent serviceIntent = new Intent(getBaseContext(), Alarm2.class);
        if (isServiceOneRunning(serviceIntent.getClass())) {
            stopService(serviceIntent);
            startService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }
    private boolean isServiceOneRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
       return false;
   }
    private void isServiceTowRunning(){
        if (SharedPreferencesUtils.getServiceOnOff(getApplicationContext())) {
            SharedPreferencesUtils.setServiceOnOff(this, false);
            serviceIntent2 = new Intent(getBaseContext(), Alarm.class);
            Alarm.enqueueWork(this, serviceIntent2);
        }
    }
    private void showDialogInfo(){
      AlertDialog.Builder builder = new AlertDialog.Builder(ListAzcarActivity.this);
       builder.setMessage("هل تريد اغلاق التطبيق ثم تعمل الاذكار في خلفية الجهاز ؟");
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialoginterface, int i) {
            chooseDevice();
              builder.setCancelable(false);
           }
        });

        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                builder.setCancelable(false);
               // getFilterArries();
            }
        });
        builder.show();
    }
 */
/*

    public void endOfSelection(View v){
        saveDataInSharedPref();
        boolean LengthOfListCheckedSound = lengthSelectedSound();
        if(ensureOneORMoreSelected() && LengthOfListCheckedSound) {
          //showDialogInfo();
           Log.i("selection","Don");
        }
        if (!ensureOneORMoreSelected()) {
            Toast.makeText(ListAzcarActivity.this, "يجب اختيار عنصر واحد على الاقل", Toast.LENGTH_SHORT).show();
        }
    }
 */

/*
    private void setBackgroundPlayButtons(View compoundButton , int id) {
      ToggleButton selectedBtn = (ToggleButton) compoundButton;
        // playStopButtonTemp.add(selectedBtn);
         Log.i("printLength","Don"+playStopButtonTemp.size());
          for(int j=0; j<StatusButtons.size();j++) {
            if(j==id) {
              if(!StatusButtons.get(id)){
                 StatusButtons.set(id,true);
                }else {
                   StatusButtons.set(id,false);
                }
            }
            else {
                StatusButtons.set(j,true);
            }
        }
     adapter.notifyDataSetChanged();
    }
  */


        /*
        for(int j=0; j<listdata.length;j++) {
          if (j == id) {
                if (!listdata[id].getBool()) {
                    listdata[id].setBool(true);
                } else {
                    listdata[id].setBool(false);
                }
            } else {
                listdata[j].setBool(true);
            }
        }
*/


/*
private boolean ensureTransportData() {
        boolean checkArray = false;
        for (int i = 0; i<selectedSound.length; i++) {
            boolean getboolArrey = globalSharedPreferences.getBool("btnsSoundChecked"+i);
            checkArray =  (getboolArrey = true ) ? true : false;
        }
        return checkArray;
    }

 Log.i("TRACE00--",""+SharedPreferencesUtils.getArrayBooleanPrefs(MainActivity.this));
        Log.i("TRACEInt--",""+SharedPreferencesUtils.getArrayIntPrefs(MainActivity.this));
    private boolean lengthSelectedSound(){
        int count = 0;
        for (int i = 0; i<selectedSound.length; i++) {
            if(selectedSound[i]){
                count = count + 1;
            }
        }
        return (count>2) ? true:false;
    }

     */
/*
 for(int j =0; j<repeatEachSound.length; j++){
    Log.i("post",""+repeatEachSound[j]);
  }
 */
/*
new MyListData("a",true,soundsRowsId[0]),
     new MyListData("b",true,soundsRowsId[1]),
        new MyListData("c",true,soundsRowsId[2]),
          new MyListData("d",true,soundsRowsId[3]),
            new MyListData("f",true,soundsRowsId[4]),
  new MyListData("e",true,soundsRowsId[5]),
 */

         /*
                new MyListData("b",true),
                new MyListData("c",true),
                new MyListData("d",true),
                new MyListData("f",true),
                new MyListData("e",true),
          */


/*
         MyListData[] myListData = new MyListData[] {
         new MyListData("سبحان الله"),
         new MyListData("الحمد لله"),
         new MyListData("لا اله الا الله"),
         new MyListData("الله اكبر"),
         new MyListData("بسم الله"),
        };
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
          MyListAdapter adapter = new MyListAdapter(myListData, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
             //   Log.d("RecyclerView0", "onClick：" + position);
            }
        });
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
 */


 /*
        if(Build.VERSION.SDK_INT>=16 && Build.VERSION.SDK_INT<21){
           this.finishAffinity();
        } else if(Build.VERSION.SDK_INT>=21){
            this.finish();
            System.exit(0);
        }
   /*
     myListData = new MyListData[]{
       new MyListData("A", true, soundsRowsId[0]),
         new MyListData("B", true, soundsRowsId[1]),
          new MyListData("C", true, soundsRowsId[2]),
         new MyListData("D", true, soundsRowsId[3]),
       new MyListData("F", true, soundsRowsId[4]),
     new MyListData("E", true, soundsRowsId[5]),
  };
*/

