package com.ahmedco.tasbeh_5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import info.hoang8f.android.segmented.SegmentedGroup;

public class ThirdActivity extends Activity {

    Button btn , btn2;
    ToggleButton bt1;

    Drawable d1,d2,buttonBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.80);
        this.getWindow().setLayout(width,height);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void Click_start_remember_me3(View V){
        Intent k = new Intent(ThirdActivity.this ,FourActivity.class);
        startActivity(k);
    }
 }
