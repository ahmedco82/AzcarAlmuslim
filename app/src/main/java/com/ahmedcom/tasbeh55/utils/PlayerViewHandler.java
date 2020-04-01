package com.ahmedcom.tasbeh55.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.ahmedcom.tasbeh55.adapters.AzcarListAdapter;
import com.ahmedcom.tasbeh55.interfaces.SoundAdapter;

public class PlayerViewHandler {

    private MediaPlayer mediaPlayer;

    SoundAdapter soundAdapter;
    public PlayerViewHandler(AzcarListAdapter soundAdapter){
        this.soundAdapter = soundAdapter;
    }
    public void startMediaPlayer(Context context, int audioResId) {
        mediaPlayer = MediaPlayer.create(context, audioResId);
           mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
          @Override
            public void onCompletion(MediaPlayer mp) {
               releaseMediaPlayer();
             }
          });
          mediaPlayer.start();
    }

    private void releaseMediaPlayer() {
        mediaPlayer.release();
        mediaPlayer = null;
        soundAdapter.refreshUiSound(false);
    }

    public void CheckisNull(){
        if (mediaPlayer != null) {
            soundAdapter.refreshUiSound(true);
        }
    }
    public void Pause() {
        mediaPlayer.pause();
    }
    public void Release() {
        mediaPlayer.release();
    }
    public void Start() {
        mediaPlayer.start();
    }

   public void checkIsPlaying(){
       if(mediaPlayer.isPlaying()) {
           mediaPlayer.pause();
       } else {
           mediaPlayer.start();
       }
   }
    public boolean getStatusPlaying(){
       if(mediaPlayer.isPlaying()){
         return true;
        }else{
         return false;
      }
   }

}
