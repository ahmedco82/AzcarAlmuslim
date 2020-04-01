package com.ahmedcom.tasbeh55.ui.activities;

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
import com.ahmedcom.tasbeh55.models.ImagesGridView;
import com.ahmedcom.tasbeh55.ui.others.ActionBarView;


import java.util.ArrayList;

public class DefaultActivity extends AppCompatActivity {

    private Button btnClose;

    private GridView gvIcons;
    private GridViewAdapter gridBaseAdapter;
    private ArrayList<ImagesGridView> imageModelArrayList;

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
           gvIcons = findViewById(R.id.gridview);
           imageModelArrayList = getList();
           gridBaseAdapter = new GridViewAdapter(getApplicationContext(), imageModelArrayList);
           gvIcons.setAdapter(gridBaseAdapter);
           gvIcons.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
             if(position==2){
               Intent k = new Intent(DefaultActivity.this, TimeSettingsActivity.class);
               startActivity(k);
              }
           }
        });
     }


    private ArrayList<ImagesGridView> getList() {
        ArrayList<ImagesGridView> list = new ArrayList<>();
        for(int i = 0; i<9; i++) {
            ImagesGridView imageModel = new ImagesGridView();
            imageModel.setName(textList[i]);
            imageModel.setImage_drawable(imageList[i]);
            list.add(imageModel);
        }
        return list;
    }
}
