package com.ahmedco.tasbeh_5.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedco.tasbeh_5.R;

public class DefaultActivity extends AppCompatActivity {

    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        btnClose = (Button) findViewById(R.id.button_close_app);
        new ActionBarView(this, "صفحة أفتراضية");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // button_close_app
    }
}
