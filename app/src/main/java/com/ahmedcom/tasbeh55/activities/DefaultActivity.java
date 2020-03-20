package com.ahmedcom.tasbeh55.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedcom.BackPressedCallingBack;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.adapters.GridViewAdapter;
import com.ahmedcom.tasbeh55.models.Images;


import java.util.ArrayList;

public class DefaultActivity extends AppCompatActivity {

    private Button btnClose;

    private GridView gvIcons;
    private GridViewAdapter gridBaseAdapter;
    private ArrayList<Images> imageModelArrayList;

    private ArrayList<String> computer = new ArrayList<>();
    private ArrayList<String> food = new ArrayList<>();
    private ArrayList<String> clothes = new ArrayList<>();

 //   private OptionsPickerView pvNoLinkOptions;


    private int[] imageList = new int[]{
            R.drawable.zeker, R.drawable.icon_radio,
            R.drawable.icon_quran, R.drawable.icon_medal,
            R.drawable.icon_hands, R.drawable.icon_books,
            R.drawable.icon_convet, R.drawable.icon_azcartoday , R.drawable.icon_history};
            private String[] textList = new String[]{
            "تسبيح المسلم", "راديو",
            "قرءان كريم", "الأوائل",
            "الدعاء فى القرءان", "احاديث نبويه",
            "محول التقويم", "اذكار اليوم"
            ,"الاحاديث الاسلاميه"};


       @Override
       protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_default);

          //btnClose = (Button) findViewById(R.id.button_close_app);
           Bitmap bitmap= BitmapFactory.decodeResource(DefaultActivity.this.getResources(), R.drawable.like_);
           new ActionBarView(this, "تسبيح المسلم", bitmap ,  new BackPressedCallingBack(this));
          // Bitmap bitmap= BitmapFactory.decodeResource(DefaultActivity.this.getResources(), R.drawable.like_);
          // new ActionBarView(this, "أذكار المسلم",bitmap);
           gvIcons = findViewById(R.id.gridview);
           imageModelArrayList = getList();
           gridBaseAdapter = new GridViewAdapter(getApplicationContext(), imageModelArrayList);
           gvIcons.setAdapter(gridBaseAdapter);
           gvIcons.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
             if(position==2){
            //  initNoLinkOptionsPicker();
             //  pvNoLinkOptions.show();
               //Intent k = new Intent(DefaultActivity.this, ListAzcarActivity.class);
               Intent k = new Intent(DefaultActivity.this, TimeSettingsActivity.class);
               startActivity(k);
              }
             // Toast.makeText(DefaultActivity.this, "" + position, Toast.LENGTH_SHORT).show();
           }
        });
     }



    private ArrayList<Images> getList() {
        ArrayList<Images> list = new ArrayList<>();
        for(int i = 0; i<9; i++) {
            Images imageModel = new Images();
            imageModel.setName(textList[i]);
            imageModel.setImage_drawable(imageList[i]);
            list.add(imageModel);
        }
        return list;
    }
}
