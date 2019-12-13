package com.ahmedco.tasbeh_5;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


//https://www.viralandroid.com/2016/04/android-gridview-with-image-and-text.html
public class MainActivity extends AppCompatActivity {

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    int counter;

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    GridView gridView;
    TextView textTitle;
    String[] gridViewStrings = {
            "قرءان", "اذكار", "تسبيح", "ادعيه", "احاديث", "راديو",
            "اذكار اليوم", "الأوائل", "محول التقويم", "احاديث اليوم", "دعاء الصباح", "دعاء المساء"
    } ;

    int[] gridViewImageId = {
     R.drawable.support, R.drawable.loading1, R.drawable.about,
     R.drawable.zeker,R.drawable.support, R.drawable.loading1,
     R.drawable.about, R.drawable.zeker,R.drawable.zeker,
     R.drawable.support, R.drawable.loading1, R.drawable.about
 };

    @Override
    protected void onCreate(Bundle savedInstanceState){
     super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
        timer();
        ActionBarSetting();
        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(MainActivity.this, gridViewStrings, gridViewImageId);
         gridView=(GridView)findViewById(R.id.grid_view_image_text);
         gridView.setAdapter(adapterViewAndroid);
         gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
             Toast.makeText(MainActivity.this, "GridView Item000000: " + gridViewStrings[+i], Toast.LENGTH_LONG).show();
             // Intent k = new Intent(MainActivity.this , SecondActivity.class);
             // Intent k = new Intent(MainActivity.this , ThirdActivity.class);
           Intent k = new Intent(MainActivity.this , FourActivity.class);
           // startActivity(k);
           //  Intent k = new Intent(MainActivity.this ,FiveActivity.class);
           startActivity(k);
          }
        });

        textTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
            }
        });
    }


    void timer(){
        /*
        new CountDownTimer(30000, 1000){
            public void onTick(long millisUntilFinished){
             //textView.setText(String.valueOf(counter));
             Log.i("trace_09",""+counter);
             counter++;
            }
            public  void onFinish(){
                //textView.setText("FINISH!!");
            }
        }.start();
        */
    }


    private void ActionBarSetting() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        textTitle = view.findViewById(R.id.text_title);
       // textTitle.setText("أذكار");

    }
}

