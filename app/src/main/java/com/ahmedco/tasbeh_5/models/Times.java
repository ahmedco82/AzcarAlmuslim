package com.ahmedco.tasbeh_5.models;

import java.io.Serializable;

public class Times implements Serializable{

    int hour_start;
    int hour_end;
    int minute_start;
    int minute_end;
    int start_AM_PM;
    int end_AM_PM;
    int stopTimer;
    int everyTime;
    public Times(){

    }
    public void setHour_start(int hour_start){
        this.hour_start = hour_start;
    }
    public void setHour_end(int hour_end){
        this.hour_end = hour_end;
    }
    public void setMinute_start(int minute_start){
        this.minute_start = minute_start;
    }
    public void setMinute_end(int minute_end){
        this.minute_end = minute_end;
    }
    public void setStart_AM_PM(int start_AM_PM){
        this.start_AM_PM = start_AM_PM;
    }
    public void setEnd_AM_PM(int end_AM_PM){
        this.end_AM_PM = end_AM_PM;
    }
    public void setStopTimer(int stopTimer){
        this.stopTimer = stopTimer;
    }
    public void setEveryTime(int everyTime){
        this.everyTime = everyTime;
    }
    public int getHour_start(){
        return hour_start;
    }
    public int getHour_end(){
        return hour_end;
    }
    public int getMinute_start(){
        return minute_start;
    }
    public int getMinute_end(){
        return minute_end;
    }
    public int getStart_AM_PM(){
        return start_AM_PM;
    }
    public int getEnd_AM_PM(){
        return end_AM_PM;
    }
    public int getStopTimer(){
        return stopTimer;
    }
    public int getEveryTime(){
        return everyTime;
    }
}
