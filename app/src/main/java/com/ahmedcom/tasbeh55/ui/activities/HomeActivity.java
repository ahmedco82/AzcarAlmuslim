package com.ahmedcom.tasbeh55.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedcom.BackPressedCallingBack;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.adapters.GridViewAdapter;
import com.ahmedcom.tasbeh55.presenter.HomeInteractor;
import com.ahmedcom.tasbeh55.presenter.ViewHome;
import com.ahmedcom.tasbeh55.models.IconsAndTitles;
import com.ahmedcom.tasbeh55.presenter.HomePresenter;
import com.ahmedcom.tasbeh55.ui.others.ActionBarView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements ViewHome {

    private GridViewAdapter gridViewAdapter;
    HomePresenter homeActivityPresenter;
    @BindView(R.id.gridview) GridView ButtonsOfIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState){
     super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home);
       initiItems();
        ButtonsOfIcons.setOnItemClickListener(new AdapterView.OnItemClickListener(){
         @Override
         public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
         homeActivityPresenter.navigateToActivity(position);
         }
     });
  }

    private void initiItems(){
        ButterKnife.bind(this);
        homeActivityPresenter = new HomePresenter(this,new HomeInteractor());
        Bitmap bitmap = BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.like_);
        new ActionBarView(this, getString(R.string.alarm_stoped), bitmap, new BackPressedCallingBack(this));
        homeActivityPresenter.FindItemsGridview();
    }

    @Override
    public void setItems(ArrayList<IconsAndTitles> listTextAndIcon) {
        gridViewAdapter = new GridViewAdapter(HomeActivity.this,listTextAndIcon);
        ButtonsOfIcons.setAdapter(gridViewAdapter);
    }

    @Override
    public void gotoSettingsActivity() {
        Intent intent = new Intent(HomeActivity.this , TimeSettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotoRememberInfoActivity() {
        Intent intent = new Intent(HomeActivity.this, RememberInfoActivity.class);
        startActivity(intent);
    }
}















