package com.ahmedco.tasbeh_5.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedco.tasbeh_5.adapters.gridViewHomeActivity;
import com.ahmedco.tasbeh_5.R;


//https://www.viralandroid.com/2016/04/android-gridview-with-image-and-text.html

public class HomeActivity extends AppCompatActivity {

    
    GridView gridViewItems;
    TextView textTitleBar;

     String[] gridViewTitles = {
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
       // Toast.makeText(HomeActivity.this, "Started 00" , Toast.LENGTH_LONG).show();
        ActionBarSetting();
        gridViewHomeActivity adapterViewAndroid = new gridViewHomeActivity(HomeActivity.this, gridViewTitles, gridViewImageId);
         gridViewItems=(GridView)findViewById(R.id.grid_view_image_text);
         gridViewItems.setAdapter(adapterViewAndroid);
         gridViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
          //Toast.makeText(HomeActivity.this, "GridView Item000000: " + gridViewStrings[+i], Toast.LENGTH_LONG).show();
          // Intent k = new Intent(HomeActivity.this , SecondActivity.class);
          // Intent k = new Intent(HomeActivity.this , DialogRememberInfoActivity.class);
           // Intent k = new Intent(HomeActivity.this , ListAzcarActivity.class);
           // startActivity(k);
           Intent intent = new Intent(HomeActivity.this , TimeSettingsActivity.class);
           startActivity(intent);
          }
        });

        textTitleBar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             //   Toast.makeText(HomeActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ActionBarSetting(){
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        textTitleBar = view.findViewById(R.id.text_title);
       // textTitle.setText("أذكار");
    }
}
