package com.ahmedcom.tasbeh55.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.ahmedcom.tasbeh55.R;
import com.ahmedcom.tasbeh55.models.IconsAndTitles;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<IconsAndTitles> iconsAndTitles;
    @BindView(R.id.ivGallery) ImageView iconButton;
    @BindView(R.id.tv)TextView titleIcon;



    public GridViewAdapter(Context ctx, ArrayList<IconsAndTitles> imageModelArrayList) {
        this.ctx = ctx;
        this.iconsAndTitles = imageModelArrayList;
    }

    @Override
    public int getCount(){
        return iconsAndTitles.size();
    }

    @Override
    public Object getItem(int position){
        return iconsAndTitles.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }


    @Override
    public View getView(int position , View convertView , ViewGroup parent){
         inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View itemView = inflater.inflate(R.layout.grid_item, parent, false);
         ButterKnife.bind(this, itemView);
         iconButton.setImageResource(iconsAndTitles.get(position).getImage_drawable());
         titleIcon.setText(iconsAndTitles.get(position).getName());
        return itemView;
    }
}
