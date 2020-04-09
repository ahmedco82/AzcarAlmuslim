package com.ahmedcom.tasbeh55.adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ahmedcom.tasbeh55.ui.activities.ListAzcarActivity;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.interfaces.RecyclerViewClickListener;
import com.ahmedcom.tasbeh55.interfaces.SoundAdapter;
import com.ahmedcom.tasbeh55.models.AudioItem;
import com.ahmedcom.tasbeh55.utils.PlayerViewHandler;
import java.util.ArrayList;


public class AzcarListAdapter extends RecyclerView.Adapter<AzcarListAdapter.ViewHolder> implements SoundAdapter {

    public int playingPosition, sheckBoxesPosition;
    public ViewHolder playingHolder, sheckBoxesHolder;
    public RecyclerViewClickListener mListener;
    public ArrayList<Boolean> tempCheckBoxes = new ArrayList<Boolean>();
    ToggleButton buttons[];
    ArrayList<AudioItem> audioItems;
    PlayerViewHandler soundUtlis;
    private ArrayList<String> listString;
    private ListAzcarActivity context;

    public AzcarListAdapter(ListAzcarActivity context, ArrayList<String> listText, ArrayList<AudioItem> audioItems) {
        this.listString = listText;
        this.context = context;
        this.playingPosition = -1;
        this.sheckBoxesPosition = -1;
        this.audioItems = audioItems;
        this.mListener = context;
        soundUtlis = new PlayerViewHandler(AzcarListAdapter.this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_list_azcar, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listString.get(position));
        setTage(holder, position);
        sheckBoxesHolder = holder;
        updateCheckeBoxesView(position);
        if (position == playingPosition) {
            playingHolder = holder;
            updatePlayingView();
        } else {
            updateNonPlayingView(holder);
        }
        holder.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButtonGetName = group.findViewById(checkedId);
                int indexRadioButn = group.indexOfChild(radioButtonGetName);
                mListener.onClick(radioButtonGetName, position);
            }
        });
        holder.playBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
               mListener.onClick(compoundButton, position);
             }
         });

            holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                  //   mListener.onClick(compoundButton, position);
              }
          });
      }


    private void updateNonPlayingView(ViewHolder holder) {
        holder.playBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btnplay));
    }
    // get the size of the list
    @Override
    public int getItemCount(){
        return listString == null ? 0 : listString.size();
    }
    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
         super.onViewRecycled(holder);
          if(playingPosition == holder.getAdapterPosition()){
           updateNonPlayingView(playingHolder);
           playingHolder = null;
        }
    }

    private void setTage(ViewHolder holder, int position) {
        tempCheckBoxes.add(true);
        holder.toggleButton.setTag("toggleButton");
        holder.playBtn.setTag("playBtn");
        holder.radiogroup.setTag("radiogroup");
        holder.radioButton1.setTag("radio_a");
        holder.radioButton2.setTag("radio_b");
        holder.radioButton3.setTag("radio_c");
    }
    private void updatePlayingView() {
        if (soundUtlis.getStatusPlaying()) {
            playingHolder.playBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btnpause));
        } else {
            playingHolder.playBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.btnplay));
        }
    }
    public void updateCheckeBoxesView(int index){
        if (!tempCheckBoxes.get(index)) {
           sheckBoxesHolder.toggleButton.setBackground(ContextCompat.getDrawable(context, R.drawable.chk));
        } else {
            sheckBoxesHolder.toggleButton.setBackground(ContextCompat.getDrawable(context, R.drawable.unchk));
        }

    }

    @Override
    public void refreshUiSound(Boolean release) {
      if(null != playingHolder){
         updateNonPlayingView(playingHolder);
        }
        if(!release) playingPosition = -1;
        else
        soundUtlis.Release();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public ImageView imageView;
        public TextView textView;
       // public LinearLayout linearLayout;
        public ToggleButton toggleButton, playBtn;
        public RadioButton radioButton1, radioButton2, radioButton3;
        RadioGroup radiogroup;
       // private RecyclerViewClickListener mListener;
        // itemView is coming from inside onCreateViewHolder
        public ViewHolder(View itemView) {
            super(itemView);
            //this.itemView = itemView;
            //linearLayout = (LinearLayout) itemView.findViewById(R.id.l_containe_row);
            this.toggleButton = (ToggleButton) itemView.findViewById(R.id.bt_check2);
            this.playBtn = (ToggleButton) itemView.findViewById(R.id.bt_play);
            this.radioButton1 = (RadioButton) itemView.findViewById(R.id.radio_btn1);
            this.radioButton2 = (RadioButton) itemView.findViewById(R.id.radio_btn2);
            this.radioButton3 = (RadioButton) itemView.findViewById(R.id.radio_btn3);
            this.radiogroup = (RadioGroup) itemView.findViewById(R.id.segmentedGroup);
            this.textView = (TextView) itemView.findViewById(R.id._txt_kind_of_azkar);
            this.playBtn.setOnClickListener(this);
            this.toggleButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
          if(v.getTag() == "playBtn"){
           clickedPlayStopButton();
            }else if (v.getTag() == "toggleButton"){
              if(!context.lengthSelectedSound() && !context.selectedSound.get(getAdapterPosition())){
                Toast.makeText(context ,R.string.chooseless, Toast.LENGTH_SHORT).show();
                }else {
                   mListener.onClick(v , getAdapterPosition());
                   clickedCheckBoxesButton();
                }
            }
        }

        private void clickedCheckBoxesButton() {
            sheckBoxesPosition = getAdapterPosition();
            setStatuesTempCheckBoxes();
            sheckBoxesHolder = this;
            updateCheckeBoxesView(getAdapterPosition());
        }

        private void setStatuesTempCheckBoxes() {
            if (tempCheckBoxes.get(getAdapterPosition())) {
                tempCheckBoxes.set(getAdapterPosition(), false);
            } else {
                tempCheckBoxes.set(getAdapterPosition(), true);
            }
        }
        private void clickedPlayStopButton() {
            if (getAdapterPosition() == playingPosition) {
                soundUtlis.checkIsPlaying();
            } else {
                playingPosition = getAdapterPosition();
                soundUtlis.CheckisNull();
                playingHolder = this;
                soundUtlis.startMediaPlayer(context, audioItems.get(playingPosition).audioResId);
            }
            updatePlayingView();
        }
    }
}





    /*
      this function work when at the beginning
      of the creation RecyclerView.
      This function is responsible for creating
      an object from row xml ,and we put a name on it
       ViewHolder
     */


  /*
      That method uses the view holder's position to determine
      what the contents should be, based on its list position.
      It going to put the items that
      you sees it in front of you on screen .
      then after you pull the side tape
      It going to put new items
      */





















//soundUtlis
       /*
         if(getAdapterPosition() == playingPosition) {
           // toggle between play / pause of audio
            // IscHECKED
            if(mediaPlayer.isPlaying()) {
               mediaPlayer.pause();
                } else {
                 mediaPlayer.start();
               }
            } else {
                playingPosition = getAdapterPosition();

                if (mediaPlayer != null) {
                    if (null != playingHolder) {
                        updateNonPlayingView(playingHolder);
                    }
                    mediaPlayer.release();
                }
                playingHolder = this;
                startMediaPlayer(audioItems.get(playingPosition).audioResId);
             }
            updatePlayingView();

        */


/*
    // --------------------------------------------------------------------------------------
    private void startMediaPlayer(int audioResId) {
        mediaPlayer = MediaPlayer.create(context, audioResId);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        });
        mediaPlayer.start();
    }
    //------------------------------------------------------------------------------------

    private void releaseMediaPlayer() {
        if (null != playingHolder) {
            updateNonPlayingView(playingHolder);
        }
        mediaPlayer.release();
        mediaPlayer = null;
        playingPosition = -1;
    }
 */







/*
    private void updateNonCheckBoxesView(ViewHolder holder) {
        holder.toggleButton.setBackground(ContextCompat.getDrawable(context, R.drawable.unchk));
    }

 */


    /*
    private void updateNonCheckBoxesView0(ViewHolder holder) {
        // ToggleButton selectedBtn = (ToggleButton) holder;
        if(holder.toggleButton.isChecked()){
            holder.toggleButton.setBackground(ContextCompat.getDrawable(context, R.drawable.unchk));
        }else{
            holder.toggleButton.setBackground(ContextCompat.getDrawable(context, R.drawable.unchk));
         }
      }
   */


// private void updateNonCheckBoxesView(ViewHolder holder) {
// ToggleButton selectedBtn = (ToggleButton) holder;
/*
     private void clickedCheckBoxesButton(View v){
         ToggleButton selectedBtn = (ToggleButton) v;
         if(!selectedBtn.isChecked()){
             selectedBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.unchk));
         }else{
             selectedBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.chk));
         }
     }

 */

/*
 if(StatusCheckBoxes.get(position)){
  // setImageResource
     // holder.toggleButton.setBackground(ContextCompat.getDrawable(context, R.drawable.unchk));
        }else {
           // holder.toggleButton.setBackground(ContextCompat.getDrawable(context, R.drawable.chk));
        }
        if(!statusPlayStopButton.get(position)){
          // setBackgroundPlayButtons(holder.playBtn,true);
            setBackgroundPlayButtons(holder.playBtn,true);
          }else{
            setBackgroundPlayButtons(holder.playBtn,false);
         }

 */






/*
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                //setBgroundCheckButtons(compoundButton,position);
               // mListener.onClick(compoundButton,position);
              //  setBgroundCheckButtons(compoundButton,position);
               // compoundButton.setBackground(context.getDrawable(R.drawable.chk));
            }
         });
*/

      /*
      public MyListAdapter(Context context ,  MyListData[] listdata  , RecyclerViewClickListener mListener) {
        this.mListener =mListener;
        this.listdata = listdata;
        this.context = context;
      }
     */

  /*
        holder.radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
              mListener.onClick(compoundButton,position);
                //setBgroundCheckButtons(compoundButton,position);
            }
        });
        holder.radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mListener.onClick(compoundButton,position);
                //setBgroundCheckButtons(compoundButton,position);
            }
        });
        holder.radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mListener.onClick(compoundButton,position);
                //setBgroundCheckButtons(compoundButton,position);
            }
        });
        */


/*
  private void setBgroundCheckButtons(CompoundButton selectedBtn_ , int position){
   // Boolean checkStateButton = false;
     if(!selectedBtn_.isChecked()) {
       selectedBtn_.setBackground(context.getDrawable(R.drawable.unchk));
          //checkStateButton =false;
        }else{
           selectedBtn_.setBackground(context.getDrawable(R.drawable.chk));
           // checkStateButton =true;
        }
    }

 */


/*
 private void setBackgroundPlayButtons(CompoundButton compoundButton , int id) {
  ToggleButton selectedBtn = (ToggleButton) compoundButton;
      // playStopButtonTemp.add(selectedBtn);
     Log.i("printLength","Don"+statusPlayStopButton.size());
      for(int j=0; j<statusPlayStopButton.size();j++) {
          if(j==id){
                if(!statusPlayStopButton.get(id)){
                    statusPlayStopButton.set(id,true);
                }else {
                    statusPlayStopButton.set(id,false);
                }
            }
            else {
              statusPlayStopButton.set(j,true);
             }
         }
        notifyDataSetChanged();
     }
*/



     /*
    private void setBooolenBgPlayButns(CompoundButton compoundButton , boolean checked , int id) {
        for(int j=0; j<listdata.length;j++) {
            if(j==id) {
                if(!listdata[id].getBool()){
                    listdata[id].setBool(true);
                }else {
                  listdata[id].setBool(false);
                }
            }
            else {
                listdata[j].setBool(true);
              }
           }
        notifyDataSetChanged();
       }
   */

// mListener.onClick(compoundButton,position);
// setBgBtn(compoundButton,checked,position);













/*
         @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            // selectedBtn.setText(null);
             if(checked){
              holder.toggleButton.setBackgroundResource(R.drawable.chk);
               }else{
                 holder.toggleButton.setBackgroundResource(R.drawable.unchk);
               }
           }
       });
 */
// holder.radioButton2.setOnClickListener(this);
//holder.radioButton3.setOnClickListener(this);
// holder.imageView.setImageResource(listdata[position].getImgId());
// holder.linearLayout.setOnClickListener(new View.OnClickListener() {
// mListener = listener;
        /*
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
         public void onClick(View view){
         String name = (String)view.getTag();
        Log.d("RecyclerView1:", "onClick：" + name);
      mListener.onClick(view , position);
     }
  });
*/