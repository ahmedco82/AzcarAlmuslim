package com.ahmedcom.tasbeh55.presenter;

import android.content.Context;

import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.models.IconsAndTitles;
import com.ahmedcom.tasbeh55.services.Alarm2;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeInteractor {

    public interface OnActivityListener {
        void TimeSettings();
        void DialogRememberInfo();
    }

    public void chooseActivity(int position, Context context , OnActivityListener onActivityListener){
        if(position == 2) {
            if(SharedPreferencesUtils.getServiceOnOff(context) || SharedPreferencesUtils.isServiceRunning(Alarm2.class,context)) {
                onActivityListener.DialogRememberInfo();
            } else {
                onActivityListener.TimeSettings();
            }
        }
    }

    public interface OnFinishedListener {
        void onFinished(ArrayList<IconsAndTitles> listTextAndIcon);
    }

    public void findItems(final OnFinishedListener listener) {
        listener.onFinished(getListTextAndIcon());
    }

    private List<Integer> createArrayListIcons(){
        return Arrays.asList(R.drawable.icon_quran, R.drawable.icon_radio, R.drawable.zeker, R.drawable.icon_medal, R.drawable.icon_hands, R.drawable.icon_books, R.drawable.icon_convet, R.drawable.icon_azcartoday, R.drawable.icon_history);
    }

    private List<String> createArrayListText(){
        return Arrays.asList("قرءان كريم", "راديو", "تسبيح المسلم", "الأوائل", "الدعاء فى القرءان", "احاديث نبويه", "محول التقويم", "اذكار اليوم", "الاحاديث الاسلاميه");
    }

    private ArrayList<IconsAndTitles> getListTextAndIcon(){
     ArrayList<IconsAndTitles> list = new ArrayList<>();
       for(int i = 0; i< createArrayListIcons().size(); i++) {
         IconsAndTitles imageModel = new IconsAndTitles();
          imageModel.setName(createArrayListText().get(i));
           imageModel.setImage_drawable(createArrayListIcons().get(i));
            list.add(imageModel);
        }
        return list;
    }
}






    /*
    private boolean isServiceRunning(Class<?> serviceClass,Context mContext) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

     */