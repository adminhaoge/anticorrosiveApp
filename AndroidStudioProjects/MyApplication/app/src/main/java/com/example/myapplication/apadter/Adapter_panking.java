package com.example.myapplication.apadter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.bean.Panking_bean;

import java.util.List;

public class Adapter_panking extends ArrayAdapter<Panking_bean> {
    private int resourceId;
    private int pankId =0;
    public Adapter_panking(@NonNull Context context, int resource, @NonNull List<Panking_bean> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        pankId ++;
        Panking_bean item = getItem(position);
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        TextView pank = view.findViewById(R.id.pank_txt);
        TextView score = view.findViewById(R.id.score_txt);
        TextView duration = view.findViewById(R.id.duration_txt);
        pank.setText(pankId+"");
        score.setText(item.getScore()+"");
        duration.setText(item.getDuration()+"");

        return view;
    }
}
