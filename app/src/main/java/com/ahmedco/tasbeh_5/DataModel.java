package com.ahmedco.tasbeh_5;

public class DataModel {
String name;

Boolean TrueOrFalse;
    public DataModel(String name, Boolean trueOrFalse) {
        this.name = name;
        TrueOrFalse = trueOrFalse;
    }

    public String getName(){
        return name;
    }
    public Boolean getTrueOrFalse(){
        return TrueOrFalse;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setTrueOrFalse(Boolean trueOrFalse) {
        TrueOrFalse = trueOrFalse;
    }


    public void setSelected(boolean b){


    }
}
