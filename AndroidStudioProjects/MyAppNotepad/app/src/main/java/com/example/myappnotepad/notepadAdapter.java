package com.example.myappnotepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myappnotepad.R;
import com.example.myappnotepad.bean.TitleBean;

import java.util.List;

public class notepadAdapter extends ArrayAdapter<TitleBean> {
    private int resourceId ;
    public notepadAdapter(@NonNull Context context, int resource, @NonNull List<TitleBean> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TitleBean item = getItem(position);
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        TextView title = view.findViewById(R.id.tiitle_body);
        title.setText(item.getTitle());
        return view;
    }


}
