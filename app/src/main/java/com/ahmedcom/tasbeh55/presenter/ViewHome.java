package com.ahmedcom.tasbeh55.presenter;

import com.ahmedcom.tasbeh55.models.IconsAndTitles;

import java.util.ArrayList;

public interface ViewHome {
    void setItems(ArrayList<IconsAndTitles> listTextAndIcon);
    void gotoSettingsActivity();
    void gotoRememberInfoActivity();
}
