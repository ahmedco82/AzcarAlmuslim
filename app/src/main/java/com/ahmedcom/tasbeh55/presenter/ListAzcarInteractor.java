package com.ahmedcom.tasbeh55.presenter;

import android.content.Context;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.models.AudioItem;
import com.ahmedcom.tasbeh55.ui.activities.ListAzcarActivity;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListAzcarInteractor {

    ArrayList<AudioItem> audioItems = new ArrayList<>();
    private ArrayList<String> listText = new ArrayList<String>();
    public ArrayList<Boolean> selectedSound = new ArrayList<Boolean>();
    private ArrayList<Integer> repeatEachSound = new ArrayList<Integer>();

    public void defaultSelectedRepeatSounds(ListAzcarPresenter listAzcarPresenter) {
        for(int i = 0; i<createArrayListSounds().length; i++){
            repeatEachSound.add(0);
            selectedSound.add(false);
        }
    }
    public void putNewSound(int position, boolean checked){
        selectedSound.set(position, checked);
    }
    public void putRepeatingSound(int position, int i) {

        repeatEachSound.set(position, i);
    }

    public void saveSoundsInSharePref(Context context) {
        SharedPreferencesUtils.setArrayBooleanPrefs(selectedSound, context);
        SharedPreferencesUtils.setArrayIntPrefs(repeatEachSound, context);
    }


    public interface OnFinishedListener {
        void onFinished(ArrayList<String> arrayListText, ArrayList<AudioItem> arrayListSounds);
    }
    public void findItems(OnFinishedListener onFinishedListener){
        initiArraies(createArrayListSounds(),createArrayListText());
        onFinishedListener.onFinished(listText , audioItems);
    }

    private int[] createArrayListSounds(){
        return new int[]{R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5, R.raw.a6};
    }

    private List<String> createArrayListText(){
        return Arrays.asList("الله اكبر", "الحمد لله", "سبحان الله وبحمده", "لا اله الا الله", "استغفر الله", "الحمد لله الذي احيانا بعد مماتنا");
    }

    private void initiArraies(int[] soundsRowsId, List<String> soundsRowsText) {
        for (int i = 0; i<createArrayListSounds().length; i++){
            listText.add(soundsRowsText.get(i));
            audioItems.add(new AudioItem(soundsRowsId[i]));
        }
    }
}