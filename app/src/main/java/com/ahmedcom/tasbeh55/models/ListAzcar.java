package com.ahmedcom.tasbeh55.models;

public class ListAzcar {
    private String description;
    private int imgId;
    private Boolean Bool;
    int soundsRowsId;
    public ListAzcar(String description, Boolean bool , int i) {
        this.description = description;
        this.Bool = bool;
        this.imgId = imgId;
        this.soundsRowsId = i;
    }
    public Boolean getBool(){
        return Bool;
    }
    public void setBool(Boolean bool){
        Bool = bool;
    }
    public int getSoundsRowsId(){
        return soundsRowsId;
    }
    public void setSoundsRowsId(int soundsRowsId){
        this.soundsRowsId = soundsRowsId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getImgId(){
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
