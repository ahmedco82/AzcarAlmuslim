package com.ahmedcom.tasbeh55.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;
import com.ahmedcom.BackPressedCallingBack;
import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.adapters.AzcarListAdapter;
import com.ahmedcom.tasbeh55.interfaces.HasBack;
import com.ahmedcom.tasbeh55.interfaces.RecyclerViewClickListener;
import com.ahmedcom.tasbeh55.models.AudioItem;
import com.ahmedcom.tasbeh55.presenter.ListAzcarInteractor;
import com.ahmedcom.tasbeh55.presenter.ListAzcarPresenter;
import com.ahmedcom.tasbeh55.presenter.ViewListAzcar;
import com.ahmedcom.tasbeh55.ui.others.ActionBarView;
import com.ahmedcom.tasbeh55.utils.SharedPreferencesUtils;
import java.util.ArrayList;

public class ListAzcarActivity extends AppCompatActivity implements HasBack , RecyclerViewClickListener , ViewListAzcar {

    AzcarListAdapter adapter;
    RecyclerView recyclerView;
    public ListAzcarPresenter  listAzcarPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listazcar);
        // repeatEachSound =  new int[lengthListSounds];
        listAzcarPresenter = new ListAzcarPresenter(this,new ListAzcarInteractor());
        Bitmap bitmap = BitmapFactory.decodeResource(ListAzcarActivity.this.getResources(), R.drawable.left_arrow);
        new ActionBarView(this, "أذكار مع التكرار", bitmap, new BackPressedCallingBack(this));
        //currentSound = (MediaPlayer) MediaPlayer.create(this, R.raw.a1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listAzcarPresenter.FindItemsRecyclerView();
        listAzcarPresenter.defaultValuesToSounds();
        listAzcarPresenter.saveAllSounds();
    }

    @Override
    public void onClick(View view , int position){
         if (view.getTag().equals("toggleButton")){
          ToggleButton selectedBtn = (ToggleButton) view;
          listAzcarPresenter.selectedNewSound(position, selectedBtn.isChecked());
         } else if (view.getTag().equals("playBtn")){
         } else if (view.getTag().equals("radio_b")){
            listAzcarPresenter.repeatingSound(position, 1);
         } else if (view.getTag().equals("radio_c")){
            listAzcarPresenter.repeatingSound(position, 0);
         } else if (view.getTag().equals("radio_a")){
             listAzcarPresenter.repeatingSound(position, 2);
         }
        listAzcarPresenter.saveAllSounds();
    }

    @Override
    public void setRecyclerView(ArrayList<String> arrayListText, ArrayList<AudioItem> arrayListSounds) {
        adapter = new AzcarListAdapter(ListAzcarActivity.this, arrayListText, arrayListSounds);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}