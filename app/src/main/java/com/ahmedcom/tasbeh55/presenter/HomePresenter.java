package com.ahmedcom.tasbeh55.presenter;

import android.content.Context;

import com.ahmedcom.tasbeh55.models.IconsAndTitles;

import java.util.ArrayList;

public class HomePresenter implements HomeInteractor.OnFinishedListener, HomeInteractor.OnActivityListener {

    HomeInteractor findItemsInteractor;
    ViewHome viewHome;

    public HomePresenter(ViewHome viewHome, HomeInteractor findItemsInteractor) {
         this.viewHome =viewHome;
         this.findItemsInteractor = findItemsInteractor;
    }

    public void FindItemsGridview(){
        findItemsInteractor.findItems(this);
    }

    public void  navigateToActivity(int position){
        findItemsInteractor.chooseActivity(position, (Context) viewHome,this);
    }

    @Override
    public void onFinished(ArrayList<IconsAndTitles> listTextAndIcon) {
        viewHome.setItems(listTextAndIcon);
    }
    @Override
    public void TimeSettings(){
        viewHome.gotoSettingsActivity();
    }
    @Override
    public void DialogRememberInfo() {
        viewHome.gotoRememberInfoActivity();
    }
}


