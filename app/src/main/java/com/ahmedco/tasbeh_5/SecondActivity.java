package com.ahmedco.tasbeh_5;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ActionBarSetting();
        String[] listItems = getResources().getStringArray(R.array.kind_of_azkar);
    }


    private void showCustomDialog() {
        final Dialog myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RecyclerView myRecyclerView;
        DataAdapter dataAdapter;
        ArrayList<DataModel> arrayList = new ArrayList<>();
        Button btnCancel;
        // setting custom layout in out dialog
        myDialog.setContentView(R.layout.custom_dialog);
        // findViewById for views inside dialog
        myRecyclerView = myDialog.findViewById(R.id.MyRecyclerView);
        btnCancel = myDialog.findViewById(R.id.btnCancel);
        // programatically setting hight and witdh of dialog
        Window window = myDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // setting gravity of dialog so it will diaply in the center of screen
        window.setGravity(Gravity.CENTER);
        // setting TRANSPARENT Background to myDialog to display myDialog with rounded corner
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // adding data in to arrayList
        arrayList.add(new DataModel(" 1 دقيقة", false));
        arrayList.add(new DataModel(" 5 دقيقة", false));
        arrayList.add(new DataModel(" 15 دقيقة", true));
        arrayList.add(new DataModel(" 20 دقيقة", true));
        arrayList.add(new DataModel(" 30 دقيقة", true));
        arrayList.add(new DataModel(" 45 دقيقة", true));
        arrayList.add(new DataModel("1 ساعه", true));
        arrayList.add(new DataModel(" 2 ساعه", true));
        arrayList.add(new DataModel(" 3 ساعه", true));
        arrayList.add(new DataModel(" 4 ساعه", true));
        arrayList.add(new DataModel(" 5 ساعه", true));
        // using DataAdapter.OnItemClickListener() getting result which item is selected in recyclerview
        dataAdapter = new DataAdapter(this, arrayList, new DataAdapter.OnItemClickListener() {
          @Override
            public void onItemClick(DataModel item, int position) {
              // setting result in a textview when user select recyclerview item
              //  tvText.setText(item.getName());
            }
        });
        // setting LayoutManager in myRecyclerView
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // setting Adapter( in myRecyclerView
        myRecyclerView.setAdapter(dataAdapter);
        // click listner for btnCancel to dismiss myDialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent k = new Intent(SecondActivity.this ,FiveActivity.class);
              startActivity(k);
              myDialog.dismiss();
            }
        });
        // used for display Dialog
        myDialog.show();
  }


    private void ActionBarSetting(){
     this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
      getSupportActionBar().setDisplayShowCustomEnabled(true);
       getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(1);
         View view = getSupportActionBar().getCustomView();
         //getSupportActionBar().setIcon(R.drawable.dot1);
          ImageView imageButton = (ImageView)findViewById(R.id.Icon);
           imageButton.setImageResource(R.drawable.arrow_left_black);
            // imageButton.setPaddingRelative(20,20,20,20);
            //imageButton.setForegroundGravity(Ri);
            TextView textTitle = view.findViewById(R.id.text_title);
            view.findViewById(R.id.l_container).setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            textTitle.setText(R.string.select_azkar);
            textTitle.setGravity(Gravity.CENTER | Gravity.BOTTOM);
    }



   public void Click_start_remember_me(View V) {
        //showCustomDialog();
      // Intent k = new Intent(SecondActivity.this ,FiveActivity.class);
      // startActivity(k);
   }


}




