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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class FiveActivity extends AppCompatActivity   implements View.OnClickListener {
    ToggleButton checkBtn;
    // text_select_time_azcar7
    String everyTime ,stopTimer;
    EditText fromTime,toTime;
    public static KSP ksp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        checkBtn = (ToggleButton)findViewById(R.id.bt_check2);
        fromTime =  (EditText)findViewById(R.id.text_select_time_azcar7);
        toTime =  (EditText)findViewById(R.id.text_select_time_azcar8);
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        fromTime.setText("");
        toTime.setText("");
        ksp1 = new KSP(FiveActivity.this,"ksp");
        // RelativeLayout relativeclic1 =(RelativeLayout)findViewById(R.id.r_container_6);
        //  RelativeLayout layout1 = (RelativeLayout)findViewById(R.id.row_tow);
        RelativeLayout layout2 = (RelativeLayout)findViewById(R.id.cont_click);
        // layout1.setOnClickListener(this);
        layout2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             // do something
              Log.i("trace01_","Bom");
              //  Log.i("trace0","Bom");
              //Toast.makeText(FiveActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
              showCustomDialog();
            }
         });
           // btn.setOnClickListener(btnListener);
        checkBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked){
                // Change testLinearLayout direction based on toggle button checked status.
                // Intent k = new Intent(FourActivity.this , ThirdActivity.class);
                // startActivity(k);
                checkBtn.setText(null);
                if(checked){
                    int d = R.drawable.chk;
                    fromTime.setEnabled(true);
                    toTime.setEnabled(true);
                    stopTimer="on";
                    Toast.makeText(FiveActivity.this, "Clicked", Toast.LENGTH_LONG).show();
                    checkBtn.setBackgroundDrawable(getDrawable(d));
                }else{
                    int f = R.drawable.unchk;
                    stopTimer="off";
                    fromTime.setEnabled(false);
                    toTime.setEnabled(false);
                    fromTime.setText("");
                    toTime.setText("");
                    Toast.makeText(FiveActivity.this, "UnClicked", Toast.LENGTH_LONG).show();
                    checkBtn.setBackgroundDrawable(getDrawable(f));
                }
            }
        });
        ActionBarSetting();
    }


    public void  onRelativeLayoutClicked(View V){

        //  Log.i("trace0","Bom");
    }

    public void Click_start_remember_me2(View V){
        String e1 , e2;
        e1 = fromTime.getText().toString();
        e2 = toTime.getText().toString();
        String []items={everyTime,stopTimer,e1,e2};
        //  String []items = {"a1","a2","a3","a4"};
        for(int i=0; i<items.length; i++){
            ksp1.saveFor("key"+i,items[i]);
        }
        Log.i("trec_x0 ", "" + ksp1.GetAll());
        /*
        for(int j=0; j<items.length; j++){
            Log.i("trec_x0_ ", "" + ksp1.GetAll().get(0));
        }
        */
        Log.i("trec_x0_ 1", "" + ksp1.GetAll().get(0));
        Intent k = new Intent(FiveActivity.this , FourActivity.class);
        startActivity(k);
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
        arrayList.add(new DataModel(" 5 دقيقة", true));
        arrayList.add(new DataModel(" 15 دقيقة", true));
        arrayList.add(new DataModel(" 20 دقيقة", true));
        arrayList.add(new DataModel(" 30 دقيقة", true));
        arrayList.add(new DataModel(" 45 دقيقة", true));
        arrayList.add(new DataModel("1 ساعه", true));
        arrayList.add(new DataModel(" 2 ساعه", true));
        arrayList.add(new DataModel(" 3 ساعه", true));
        arrayList.add(new DataModel(" 4 ساعه", true));
        arrayList.add(new DataModel(" 5 ساعه", true));
        arrayList.add(new DataModel(" 10 ساعه", true));
        // using DataAdapter.OnItemClickListener() getting result which item is selected in recyclerview
        dataAdapter = new DataAdapter(this, arrayList, new DataAdapter.OnItemClickListener() {
         @Override
          public void onItemClick(DataModel item, int position) {
           // setting result in a textview when user select recyclerview item
           // tvText.setText(item.getName());
              TextView txt_v =(TextView)findViewById(R.id.text_minute);
              txt_v.setText(item.getName());
              everyTime = "" + position;
              // txt_v.setText(""+position);
              // Toast.makeText(FiveActivity.this, "Selected "+item.getName(), Toast.LENGTH_LONG).show();
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
                //Intent k = new Intent(SecondActivity.this ,FiveActivity.class);
                //startActivity(k);
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


    @Override
    public void onClick(View v){


    }


}
