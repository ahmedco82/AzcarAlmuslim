package com.ahmedcom.tasbeh55.presenter;


import android.content.Context;

import com.ahmedcom.tasbeh55.models.AudioItem;

import java.util.ArrayList;

public class ListAzcarPresenter implements ListAzcarInteractor.OnFinishedListener {

    ViewListAzcar viewListAzcar;
    ListAzcarInteractor listAzcarInteractor;

    public ListAzcarPresenter(ViewListAzcar viewListAzcar , ListAzcarInteractor listAzcarInteractor){
        this.viewListAzcar = viewListAzcar;
        this.listAzcarInteractor = listAzcarInteractor;
    }
    public void FindItemsRecyclerView(){
        listAzcarInteractor.findItems(this);
    }
    @Override
    public void onFinished(ArrayList<String> arrayListText, ArrayList<AudioItem> arrayListSounds) {
        viewListAzcar.setRecyclerView(arrayListText,arrayListSounds);
    }
    public void defaultValuesToSounds(){
        listAzcarInteractor.defaultSelectedRepeatSounds(this);
    }

    public void selectedNewSound(int position, boolean checked){
        listAzcarInteractor.putNewSound(position, checked);
    }

    public void repeatingSound(int position, int i) {
        listAzcarInteractor.putRepeatingSound(position,i);
    }

    public void saveAllSounds(){
        listAzcarInteractor.saveSoundsInSharePref((Context) viewListAzcar);
    }
}
