package com.ahmedco.tasbeh_5.activities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedco.tasbeh_5.R;

public class ActionBarView extends AppCompatActivity {

    private Context context;
    private String textTitle;
    private TextView textViewTitleBar;
    private ImageView imageIconBar;

    public ActionBarView(Context context, String textTitle) {
        this.context = context;
        this.textTitle = textTitle;
        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        actionBar.setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setElevation(1);
        View view = actionBar.getCustomView();
        // getSupportActionBar().setIcon(R.drawable.dot1);
        imageIconBar = (ImageView) view.findViewById(R.id.Icon);
        imageIconBar.setImageResource(R.drawable.arrow_left_black);
        textViewTitleBar = view.findViewById(R.id.text_title);
        view.findViewById(R.id.l_container).setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        textViewTitleBar.setText(textTitle);
    }
}
