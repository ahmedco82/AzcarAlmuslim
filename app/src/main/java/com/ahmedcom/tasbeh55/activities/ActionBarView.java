package com.ahmedcom.tasbeh55.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedcom.BackPressedCallingBack;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.interfaces.CallingBack;

public class ActionBarView implements View.OnClickListener{

    private Context context;
    private String textTitle;
    private TextView textViewTitleBar;
    private ImageView imageIconBar;
    private CallingBack callingBackRoot;
    private BackPressedCallingBack backPressedCallingBack;

    public ActionBarView(Context context, String textTitle , Bitmap bitmap , BackPressedCallingBack callingBack) {
        this.context = context;
        this.textTitle = textTitle;
        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setElevation(1);
        View view = actionBar.getCustomView();
        // getSupportActionBar().setIcon(R.drawable.dot1);
        imageIconBar = (ImageView)view.findViewById(R.id.Icon);
        backPressedCallingBack = callingBack;
        imageIconBar.setOnClickListener(this);
        imageIconBar.setImageBitmap(bitmap);
        textViewTitleBar = view.findViewById(R.id.text_title);
        view.findViewById(R.id.l_container).setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        textViewTitleBar.setText(textTitle);
    }

    @Override
    public void onClick(View v){
      backPressedCallingBack.callingBackActivity(imageIconBar);
    }

}



// backPressedCallingBack.callingBackActivity();
//callingBackRoot = callingBack;
// new BackPressedCallingBack((Activity)context).onClick();

// callingBackRoot.callingBackActivity(imageIconBar);
// imageIconBar.setOnClickListener(new ButtonsListener(context));
// int getIcon= iconId ;
// Drawable res = getResources().getDrawable(imageResource);
