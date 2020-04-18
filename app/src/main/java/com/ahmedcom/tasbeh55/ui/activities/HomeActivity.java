package com.ahmedcom.tasbeh55.ui.activities;
import android.app.Activity;
import android.content.Context;
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
import com.ahmedcom.tasbeh55.utils.App;
import com.squareup.leakcanary.RefWatcher;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity implements ViewHome {

   private final WeakReference<Context> mActivity = null;
   private GridViewAdapter gridViewAdapter;
   HomePresenter homeActivityPresenter;
   @BindView(R.id.gridview) GridView ButtonsOfIcons;

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
     super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home);
       initiItems();
        ButtonsOfIcons.setOnItemClickListener(new AdapterView.OnItemClickListener(){
         @Override
          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
           homeActivityPresenter.navigateToActivity(position);
            // finish();
          }
      });
   }


    private void initiItems(){
        ButterKnife.bind(this);
        //WeakReference<Context> weakReference = new WeakReference<>(HomeActivity.this);
        homeActivityPresenter = new HomePresenter(this,new HomeInteractor());
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() , R.drawable.like_);
        new ActionBarView(this, getString(R.string.alarm_stoped), bitmap, new BackPressedCallingBack(this));
        homeActivityPresenter.FindItemsGridview();
    }

    @Override
    public void setItems(ArrayList<IconsAndTitles> listTextAndIcon) {
      gridViewAdapter = new GridViewAdapter(this, listTextAndIcon);
      ButtonsOfIcons.setAdapter(gridViewAdapter);
    }

    @Override
    public void gotoSettingsActivity(){
        Intent intent = new Intent(getApplicationContext() , TimeSettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotoRememberInfoActivity(){
        Intent intent = new Intent(getApplicationContext(), RememberInfoActivity.class);
        startActivity(intent);
    }

}















