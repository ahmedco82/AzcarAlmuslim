package com.ahmedco.tasbeh_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DataModel> arrayList = new ArrayList<>();
    private final OnItemClickListener listener;


    public DataAdapter(Context context, ArrayList<DataModel> arrayList, OnItemClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    private int lastCheckedPosition = -1;

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder viewHolder, int position) {
        // here i'm changing the state of radio button based on user select
        viewHolder.radioImage.setChecked(position == lastCheckedPosition);
        viewHolder.tvTextName.setText(arrayList.get(position).getName());
        // this is condition is used to hide line from thr last recyclerview item
        if (position == arrayList.size()-1) {
            viewHolder.bottomLine.setVisibility(View.GONE);
        } else {
            viewHolder.bottomLine.setVisibility(View.VISIBLE);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DataModel item, int position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View bottomLine;
        RadioButton radioImage;
        TextView tvTextName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bottomLine = itemView.findViewById(R.id.bottomLine);
            radioImage = itemView.findViewById(R.id.radioImage);
            tvTextName = itemView.findViewById(R.id.tvTextName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arrayList.get(getAdapterPosition()).setSelected(true);
                    lastCheckedPosition = getAdapterPosition();
                    // getting result when user selecte radio button in your activity
                    listener.onItemClick(arrayList.get(lastCheckedPosition), lastCheckedPosition);
                    notifyDataSetChanged();
                }
            });
        }
    }
}