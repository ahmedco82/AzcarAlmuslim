package com.ahmedco.tasbeh_5.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmedco.tasbeh_5.R;


public class RememberInfoActivity extends Activity implements View.OnClickListener {

    private EditText fromTime, toTime;
    private TextView everyTime;
    private Bundle extras;
    private Button btnFinsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_remember_info);
        initiViews();
    }

    private void initiViews() {
        extras = getIntent().getExtras();
        fromTime = (EditText) findViewById(R.id.text_select_time_from_dialog);
        toTime = (EditText) findViewById(R.id.text_select_time_to_dialog);
        everyTime = (TextView) findViewById(R.id.text_minute_5);
        btnFinsh = (Button) findViewById(R.id.btn_start_remember_me3);
        btnFinsh.setOnClickListener(this);
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        putTimesInTextBox(extras);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        this.getWindow().setLayout(width, height);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void putTimesInTextBox(Bundle ext) {
        if (ext != null) {
            String e_time = ext.getString("every_time");
            String from = ext.getString("starttime");
            String to = ext.getString("endtime");
            fromTime.setText(from);
            toTime.setText(to);
            everyTime.setText(e_time);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }
}

