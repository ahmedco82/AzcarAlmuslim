package com.ahmedco.tasbeh_5.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedco.tasbeh_5.R;
import com.ahmedco.tasbeh_5.adapters.AdapterDialogTimer;


public class DialogeRepeatTime extends Dialog {

    Button btnCancel, btnOk;
    RecyclerView recyclerViewDialoge;
    AdapterDialogTimer adapterDialogTimer;
    Window window;
    TextView titleRow;
    public String everyTimeTxt, pramEveryTime;
    private int everyTimeMenu;
    public int everyTime;
    private Context context_;

    public DialogeRepeatTime(Context context, TextView txtRememberMeEvery){
        super(context);
        this.context_ = context;
        initiItems(context_);
        this.titleRow = txtRememberMeEvery;

        String[] Times = {" 1 دقيقة", " 2 دقيقة", " 3 دقيقة", " 4 دقيقة", "5 دقيقة", " 10 دقيقة", " 15 دقيقة", "30 دقيقة", " 1 ساعه", " 2 ساعه"};

        adapterDialogTimer = new AdapterDialogTimer(context, Times, new AdapterDialogTimer.OnItemClickListener() {
            @Override
            public void onItemClick(String item, int position) {
                everyTimeTxt = "" + item;
                everyTimeMenu = position;
            }
        });

        recyclerViewDialoge.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewDialoge.setAdapter(adapterDialogTimer);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleRow.setText(everyTimeTxt);
                everyTime = everyTimeMenu;
                pramEveryTime = everyTimeTxt;
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Log.i("trace_everyTimeString0",""+everyTimeString);
                dismiss();
            }
        });
        show();
    }

    private void initiItems(Context cxt){
        everyTime = 0;
        everyTimeTxt = "1 دقيقة";
        pramEveryTime = "1 دقيقة";
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_timer_selected);
        titleRow = (TextView) findViewById(R.id.text_minute);
        recyclerViewDialoge = findViewById(R.id.rv_dialogTimerSelected);
        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOk);
        window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
