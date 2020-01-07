package com.ahmedco.tasbeh_5.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmedco.tasbeh_5.R;


public class DialogRememberInfoActivity extends Activity {

    EditText fromTime, toTime;
    TextView everyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_remember_info);
        fromTime = (EditText) findViewById(R.id.text_select_time_from);
        toTime = (EditText) findViewById(R.id.text_select_time_to);
        everyTime = (TextView) findViewById(R.id.text_minute_5);
        // text_minute_5
        fromTime.setEnabled(false);
        toTime.setEnabled(false);
        putTimes();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        this.getWindow().setLayout(width, height);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void putTimes() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String e_time = extras.getString("e_time");
            int h_s = extras.getInt("hour_star");
            int h_e = extras.getInt("hour_end");
            int m_s = extras.getInt("minute_start");
            int m_e = extras.getInt("minute_end");
            // Toast.makeText(getApplicationContext(),""+e_time,Toast.LENGTH_SHORT).show();
            // The key argument here must match that used in the other activity
            fromTime.setText("" + h_s + " : " + m_s);
            toTime.setText("" + h_e + " : " + m_e);
            everyTime.setText(e_time);
        }
    }

    public void Click_start_remember_me3(View V) {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }
}


        /*
        boolean checkArray = false;
        for(int i=0; i<6; i++){
            boolean getboolArrey = soundsSharedPref.getBool("soundsBoolean"+i);
            if(getboolArrey==true){
                checkArray =  true;
            }
        }
        return checkArray;
    }

    }

}
*/
