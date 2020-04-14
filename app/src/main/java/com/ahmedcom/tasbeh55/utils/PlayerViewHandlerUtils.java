package com.ahmedcom.tasbeh55.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.ahmedcom.tasbeh55.adapters.AzcarListAdapter;
import com.ahmedcom.tasbeh55.interfaces.SoundAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlayerViewHandlerUtils {

    private MediaPlayer mediaPlayer;
    private int currentSound = 0;
    private int  quiteSound = 1;
    public int counterSound = 0;

    private List<Integer> repeatEachSound;
    public ArrayList<MediaPlayer> selectedSound;
    SoundAdapter soundAdapter;

    Context context;

    public PlayerViewHandlerUtils(Context context) {
        this.currentSound=0;
        this.counterSound=0;
        this.quiteSound= 1;
        this.context = context;
        selectedSound = new ArrayList<MediaPlayer>();
        selectedSound.addAll(SharedPreferencesUtils.filterSelectedSound(context));
        repeatEachSound = new ArrayList<Integer>();
        repeatEachSound.addAll(SharedPreferencesUtils.filterRepeatingSound(context));
    }

    public PlayerViewHandlerUtils(AzcarListAdapter soundAdapter){
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


    public void playGrupSounds(MediaPlayer key) {
     selectedSound.get(currentSound).start();
      selectedSound.get(currentSound).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
          public void onCompletion(MediaPlayer mp) {
            // sound 0 = 1
            if(repeatEachSound.get(currentSound) != 0){
               // quiteSound = 1
                if(quiteSound != 0){
                    counterSound += 1;
                      if(counterSound == repeatEachSound.get(currentSound)) {
                          counterSound = 0;
                            quiteSound = 0;
                              // quiteSound = 1;
                            }
                          playGrupSounds(selectedSound.get(currentSound));
                        }else{
                          currentSound += 1;
                          if(currentSound<selectedSound.size()) {
                              quiteSound = 1;
                            // playGrupSounds(selectedSound.get(currentSound));
                           }else{
                            currentSound = 0;
                            quiteSound = 1;
                        }
                    }
                 // repeatEachSound current == 0
                }else{
                    currentSound += 1;
                    // ---------------------- // ---------------------- //
                    if(currentSound<selectedSound.size()) {
                        //playGrupSounds(selectedSound.get(currentSound));
                        quiteSound = 1;
                    } else {
                        currentSound = 0;
                        quiteSound = 1;
                    }
                 }
             }
         });
     }





    /*
   public void playGrupSounds(MediaPlayer mediaPlayer){
    SharedPreferencesUtils.filterSelectedSound(context).get(currentSound).start();
     SharedPreferencesUtils.filterSelectedSound(context).get(currentSound).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
       @Override
         public void onCompletion(MediaPlayer mp) {
               if (SharedPreferencesUtils.filterRepeatingSound(context).get(currentSound) != 0) {
                   if (quiteSound != 0) {
                       counterSound += 1;
                       if (counterSound == SharedPreferencesUtils.filterRepeatingSound(context).get(currentSound)) {
                           counterSound = 0;
                           quiteSound = 0;
                       }
                       playGrupSounds(SharedPreferencesUtils.filterSelectedSound(context).get(currentSound));
                   } else {
                       currentSound += 1;
                       if (currentSound<SharedPreferencesUtils.filterSelectedSound(context).size())
                           playGrupSounds(SharedPreferencesUtils.filterSelectedSound(context).get(currentSound));
                       else
                         currentSound = 0;
                       quiteSound = 1;
                   }
               }else{
                   currentSound += 1;
                   if(currentSound<SharedPreferencesUtils.filterSelectedSound(context).size())
                       playGrupSounds(SharedPreferencesUtils.filterSelectedSound(context).get(currentSound));
                   else
                       currentSound = 0;
                   quiteSound = 1;
               }
           }
       });
   }

     */
}
